package com.shipmap.framework.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.model.AuthInfo;
import com.shipmap.framework.service.TokenService;
import com.shipmap.framework.service.exception.*;
import com.shipmap.framework.utils.DESUtil;
import com.shipmap.framework.utils.JwtTokenUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.system.dao.AuthorityMapper;
import com.shipmap.modules.system.dao.RoleMapper;
import com.shipmap.modules.system.dao.UserMapper;
import com.shipmap.modules.system.dao.UserRoleMapper;
import com.shipmap.modules.system.model.*;
import com.wangfan.endecrypt.utils.EndecryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Primary
@Service
public class TokenServiceImpl implements TokenService {
    private static final Logger log = LoggerFactory.getLogger("adminLogger");

    //redis 用户信息列表key前缀
    private static final String INFOLIST_KEY_PREFIX = "user:infoList:";
    //redis 在线列表key前缀
    private static final String ACTIVELIST_KEY_PREFIX = "user:activeList:";
    //用户状态正常
    private static final int USER_STATE_NORMAL = 0;

    /**
     * redis<String, ActiveUser> 操作对象
     **/
    @Autowired
    private RedisTemplate<String, ActiveUser> InfoListMap;

    /**
     * redis<String, String>操作对象
     **/
    @Autowired
    private RedisTemplate<String, String> activeListMap;

    // 过期时间是3600秒，既是1个小时
    @Value(value = "${expireSeconds:3600}")
    private Long expireSeconds;
    //选择了记住我之后的过期时间为7天
    @Value(value = "${rememberMeExpireSeconds:604800}")
    private Long rememberMeExpireSeconds;
    //刷新间隔时间 单位：毫秒
    private static final int validityTime = 10 * 1000 * 60;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private AuthorityMapper authorityMapper;
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 刷新过期时间
     *
     * @param token 认证token
     **/
    public boolean refreshExpirationDate(String token) {
        ActiveUser activeUser = getActiveUserByToken(token);
        if (activeUser != null) {
            String value = getTokenByUserId(activeUser.getUserId());
            //判断是否在线
            if (value != null) {
                Long expireTime = activeUser.getExpireTime();
                Long surplusTime = expireTime - System.currentTimeMillis();
                if (surplusTime <= validityTime) {
                    saveToken(value, activeUser);
                    return true;
                }
            }
        }
        return false;
    }

    /***
     * token作为key保存用户信息
     * @param token 认证token
     * @param activeUser 活跃用户
     * */
    public boolean saveToken(String token, ActiveUser activeUser) {
        Long expireTime = getExpireTimeByRememberMe(activeUser.getRememberMe());
        return saveToken(token, activeUser, expireTime);
    }

    /***
     * token作为key保存用户信息
     * @param token 认证token
     * @param activeUser; 用户活登录对象
     * @expireTime 缓存时长 单位：毫秒
     * */
    public boolean saveToken(String token, ActiveUser activeUser, Long expireTime) {
        if (activeUser != null && expireTime != null) {
            if (activeUser.getExpireTime() == null) {
                activeUser.setExpireTime(System.currentTimeMillis() + expireTime);
            } else {
                activeUser.setExpireTime(activeUser.getExpireTime() + expireTime);
            }
            activeListMap.boundValueOps(ACTIVELIST_KEY_PREFIX + activeUser.getUserId()).set(token, expireTime / 1000, TimeUnit.SECONDS);
            //key:token value:UserObject
            InfoListMap.boundValueOps(INFOLIST_KEY_PREFIX + token).set(activeUser, expireTime / 1000, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    /**
     * 获取登录信息
     *
     * @param token 认证token
     **/
    public ActiveUser getActiveUserByToken(String token) {
        return InfoListMap.boundValueOps(INFOLIST_KEY_PREFIX + token).get();
    }

    /**
     * 根据用户id获取缓存中token
     *
     * @param userId 用户id
     **/
    public String getTokenByUserId(String userId) {
        return activeListMap.boundValueOps(ACTIVELIST_KEY_PREFIX + userId).get();
    }

    /**
     * 根据用户id获取缓存中用户对象
     *
     * @param userId 用户id
     **/
    public ActiveUser getActiveUser(String userId) {
        String token = getTokenByUserId(userId);
        if (token != null) {
            return getActiveUserByToken(token);
        }
        return null;
    }

    /**
     * 根据用户id清除用户缓存
     *
     * @param userId 用户id
     **/
    public boolean clearUserCache(String userId) {
        String token = getTokenByUserId(userId);
        if (token != null) {
            ActiveUser activeUser = getActiveUserByToken(token);
            InfoListMap.delete(INFOLIST_KEY_PREFIX + token);
            activeListMap.delete(ACTIVELIST_KEY_PREFIX + activeUser.getUserId());
            return true;
        }
        return false;
    }

    /**
     * 认证授权
     *
     * @param username     用户名
     * @param password     明文密码
     * @param ipAddress    IP地址
     * @param isRememberMe 是否记住密码
     **/
    public AuthInfo auth(String username, String password, String ipAddress, boolean isRememberMe) throws UserNotFoundException, AuthFailException, UserDisabledException {
        User user = userMapper.getByUsername(username);
        if (user == null) {
            //用户不存在
            throw new UserNotFoundException();
        }
        //盐值
        String saltValue = user.getSaltValue();
        //客户密码
        String clientPassword = enablePasswordToEncryptionPassword(saltValue, password);
        if (!(user.getPassword().equals(clientPassword))) {
            //认证失败
            throw new AuthFailException("User password error!");
        }
        if (user.getState() == 1) {
            //用户禁止登陆
            throw new UserDisabledException();
        }
        String userId = user.getId();
        Long expireTime = getExpireTimeByRememberMe(isRememberMe);
        List<Authority> authorityList = authorityMapper.listByUserId(userId);
        String cssStr = getCssStr(authorityList);
        Map<String, NavigationBarVo> menuMap = getMenus(authorityList);
        String[] authoritys = getButtonAuthority(authorityList);
        String[] roles = arrayToString(userRoleMapper.listByUserId(userId));
        String token = JwtTokenUtil.createToken(user.getUsername(), JSONArray.toJSONString(roles), ipAddress, expireTime);
        ActiveUser activeUser = new ActiveUser(user, roles, authoritys, authorityList, menuMap, cssStr);
        AuthInfo authInfo = new AuthInfo();
        //清除服务端缓存信息
        boolean clearUserCache = clearUserCache(userId);
        authInfo.setMultiplexLogin(clearUserCache);
        //保存到缓存
        boolean saveState = saveToken(token, activeUser);
        if (saveState) {
            authInfo.setToken(JwtTokenUtil.TOKEN_PREFIX + token);
        }
        return authInfo;
    }

    /**
     * 获取用户菜单
     **/
    private Map<String, NavigationBarVo> getMenus(List<Authority> authorityList) {
        Map<String, NavigationBarVo> rootMap = new TreeMap<String, NavigationBarVo>();
        List<Authority> menuList = new ArrayList<Authority>();
        List<Authority> superiorList = new ArrayList<Authority>();
        for (int i = 0; i < authorityList.size(); i++) {
            Authority authority = authorityList.get(i);
            if (authority.getType() == 0) {
                //目录
                NavigationBarVo navigationBarVo = new NavigationBarVo();
                navigationBarVo.setName(authority.getAuthorityName());
                navigationBarVo.setMenuVoList(new ArrayList<MenuVo>());
                rootMap.put(authority.getId(), navigationBarVo);
            } else if (authority.getType() == 1) {
                //一级菜单
                superiorList.add(authority);
            } else if (authority.getType() == 2) {
                //二级菜单
                menuList.add(authority);
            }
        }
        //二级菜单
        Map<String, List<MenuVo>> menuGroupMap = new HashMap<String, List<MenuVo>>();
        for (int i = 0; i < menuList.size(); i++) {
            Authority authority = menuList.get(i);
            List<MenuVo> tempMenuList = menuGroupMap.get(authority.getParentId());
            MenuVo menuVo = new MenuVo();
            menuVo.setName(authority.getAuthorityName());
            menuVo.setUrl(authority.getMenuUrl());
            menuVo.setIcon(authority.getMenuIcon());
            if (tempMenuList == null) {
                tempMenuList = new ArrayList<MenuVo>();
                tempMenuList.add(menuVo);
                menuGroupMap.put(authority.getParentId(), tempMenuList);
            } else {
                tempMenuList.add(menuVo);
            }
        }
        //一级菜单
        for (int i = 0; i < superiorList.size(); i++) {
            Authority authority = superiorList.get(i);
            NavigationBarVo navigationBarVo = rootMap.get(authority.getParentId());
            if (navigationBarVo != null) {
                MenuVo menuVo = new MenuVo();
                menuVo.setName(authority.getAuthorityName());
                menuVo.setUrl(authority.getMenuUrl());
                menuVo.setIcon(authority.getMenuIcon());
                menuVo.setSubMenus(menuGroupMap.get(authority.getId()));
                navigationBarVo.getMenuVoList().add(menuVo);
            }
        }
        return rootMap;
    }

    /**
     * List<UserRole> 转 String[]
     **/
    private String[] arrayToString(List<UserRole> userRoles) {
        String[] roleIds = new String[userRoles.size()];
        for (int i = 0; i < userRoles.size(); i++) {
            roleIds[i] = String.valueOf(userRoles.get(i).getRoleId());
        }
        return roleIds;
    }

    /**
     * 根据权限生产用户css
     **/
    private String getCssStr(List<Authority> list) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            Authority authority = list.get(i);
            if (authority.getType() == 3) {
                if (StringUtil.isNotBlank(authority.getClassName())) {
                    builder.append(".").append(authority.getClassName()).append("{display: inline-block;} ");
                }
            }
        }
        return builder.toString();
    }

    /**
     * 获取按钮权限
     **/
    private String[] getButtonAuthority(List<Authority> list) {
        List<String> tempList = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            Authority authority = list.get(i);
            if (authority.getType() == 3) {
                //只记录按钮权限
                tempList.add(authority.getAuthority());
            }
        }
        String[] arr = new String[tempList.size()];
        tempList.toArray(arr);
        return arr;
    }

    /**
     * 注册认证信息
     *
     * @param user 用户对象
     **/
    public boolean addAuth(User user, String... roleIds) throws UserExistException, RoleNotFoundException {
        User tempUser = userMapper.getByUsername(user.getUsername());
        if (tempUser != null) {
            throw new UserExistException();
        }
        List<Role> roles = roleMapper.listByRoleIds(roleIds);
        if (roles == null || roles.size() == 0) {
            throw new RoleNotFoundException();
        }
        //随机盐值
        String saltValue = DESUtil.KeyCreate(32);
        //混淆加密密码 防止暴力破解
        String md5 = enablePasswordToEncryptionPassword(saltValue, user.getPassword());
        user.setSaltValue(saltValue);
        user.setPassword(md5);
        user.setState(USER_STATE_NORMAL);
        Integer number = userMapper.insert(user);
        for (int i = 0; i < roles.size(); i++) {
            //用户角色
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(roles.get(i).getId());
            userRoleMapper.insert(userRole);
        }
        return toResult(number);
    }

    /**
     * 修改认证信息
     *
     * @param user 用户对象
     **/
    public boolean updateAuth(User user, String... roleIds) throws UserExistException, RoleNotFoundException {
        User tempUser = userMapper.selectById(user.getId());
        if (tempUser == null) {
            //用户不存在
            throw new UserNotFoundException();
        }
        //新的权限列表
        List<Role> roles = roleMapper.listByRoleIds(roleIds);
        if (roles == null || roles.size() == 0) {
            throw new RoleNotFoundException();
        }
        //覆盖性更新用户权限
        Integer delNumber = userRoleMapper.deleteByUserId(user.getId());
        //更改项
        tempUser.setEmail(user.getEmail());
        tempUser.setNickName(user.getNickName());
        tempUser.setSex(user.getSex());
        tempUser.setRoles(roles);
        tempUser.setPhone(user.getPhone());
        Integer number = userMapper.updateById(tempUser);
        for (int i = 0; i < roles.size(); i++) {
            //用户角色
            UserRole userRole = new UserRole();
            userRole.setUserId(tempUser.getId());
            userRole.setRoleId(roles.get(i).getId());
            userRoleMapper.insert(userRole);
        }
        return toResult(number);
    }

    /**
     * 移除认证信息
     *
     * @param userId 用户id
     **/
    public boolean removeAuth(String userId) throws UserNotFoundException {
        User user = userMapper.selectById(userId);
        if (user == null) {
            //用户不存在
            throw new UserNotFoundException();
        }
        //清除服务端缓存信息
        clearUserCache(userId);
        Integer number = userMapper.deleteById(userId);
        return toResult(number);
    }

    /**
     * 设置认证信息状态
     *
     * @param userId 用户id
     * @param state  用户状态 0：正常，1：禁用
     **/
    public boolean setAuthState(String userId, Integer state) throws UserNotFoundException {
        User user = userMapper.selectById(userId);
        if (user == null) {
            //用户不存在
            throw new UserNotFoundException();
        }
        //设置状态
        user.setState(state);
        Integer number = userMapper.updateById(user);
        return toResult(number);
    }

    /**
     * 重置认证信息
     *
     * @param userId   用户id
     * @param password 明文密码
     * @return boolean
     **/
    public boolean setResetAuth(String userId, String password) throws UserNotFoundException {
        User user = userMapper.selectById(userId);
        if (user == null) {
            //用户不存在
            throw new UserNotFoundException();
        }
        //生产新的盐值
        String saltValue = DESUtil.KeyCreate(32);
        //混淆加密密码 防止暴力破解
        String md5 = enablePasswordToEncryptionPassword(saltValue, password);
        user.setSaltValue(saltValue);
        user.setPassword(md5);
        //重置
        Integer number = userMapper.updateById(user);
        return toResult(number);
    }

    /**
     * 更新认证密码
     *
     * @param userId 用户id
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     **/
    public boolean setAuthPassword(String userId, String oldPwd, String newPwd) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            //用户不存在
            throw new UserNotFoundException();
        }
        String saltValue = user.getSaltValue();
        //客户端密码
        String clientPassword = enablePasswordToEncryptionPassword(saltValue, oldPwd);
        if (!(user.getPassword().equals(clientPassword))) {
            //认证失败
            throw new AuthFailException("User password error!");
        }
        String md5 = enablePasswordToEncryptionPassword(saltValue, newPwd);
        user.setPassword(md5);
        Integer number = userMapper.updateById(user);
        return toResult(number);
    }

    /**
     * 查询认证信息是否存在
     *
     * @param username 用户名
     **/
    public boolean queryAuthIsExistsByUsername(String username) {
        User tempUser = userMapper.getByUsername(username);
        if (tempUser != null) {
            return true;
        }
        return false;
    }

    /**
     * 根据是否记住密码来决定缓存时间
     *
     * @param isRememberMe 是否记住密码
     **/
    private Long getExpireTimeByRememberMe(Boolean isRememberMe) {
        return (isRememberMe == null || isRememberMe == false ? expireSeconds : rememberMeExpireSeconds) * 1000;
    }

    /**
     * 明文密码加密
     *
     * @param saltValue
     * @param password
     **/
    private String enablePasswordToEncryptionPassword(String saltValue, String password) {
        return EndecryptUtils.encrytMd5(saltValue + EndecryptUtils.encrytMd5(password));
    }

    /**
     * 处理受影响条数
     *
     * @param number 受影响条数
     **/
    private boolean toResult(Integer number) {
        return number > 0 ? true : false;
    }
}
