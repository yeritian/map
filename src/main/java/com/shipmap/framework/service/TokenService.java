package com.shipmap.framework.service;

import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.model.AuthInfo;
import com.shipmap.framework.service.exception.*;
import com.shipmap.modules.system.model.User;

/**
 * Token管理器<br>
 * 存储到<br>
 */
public interface TokenService {
    /**
     * 刷新过期时间
     *
     * @param token 认证token
     **/
    boolean refreshExpirationDate(String token);

    /***
     * token作为key保存用户信息
     * @param token 认证token
     * @param activeUser 活跃用户
     * */
    boolean saveToken(String token, ActiveUser activeUser);

    /**
     * 获取登录信息
     *
     * @param token 认证token
     **/
    ActiveUser getActiveUserByToken(String token);

    /**
     * 根据用户id获取缓存中用户对象
     *
     * @param userId 用户id
     **/
    ActiveUser getActiveUser(String userId);

    /**
     * 根据用户id获取缓存中token
     *
     * @param userId 用户id
     **/
    String getTokenByUserId(String userId);

    /**
     * 根据用户id清除用户缓存
     *
     * @param userId 用户id
     **/
    boolean clearUserCache(String userId);

    /**
     * 认证授权
     *
     * @param username     用户名
     * @param password     明文密码
     * @param ipAddress    IP地址
     * @param isRememberMe 是否记住密码
     **/
    AuthInfo auth(String username, String password, String ipAddress, boolean isRememberMe) throws UserNotFoundException, AuthFailException, UserDisabledException;

    /**
     * 新增认证信息
     *
     * @param user    用户对象
     * @param roleIds 角色id
     **/
    boolean addAuth(User user, String... roleIds) throws UserExistException, RoleNotFoundException;

    /**
     * 更新认证密码
     *
     * @param userId 用户id
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     **/
    boolean setAuthPassword(String userId, String oldPwd, String newPwd) throws UserNotFoundException, AuthFailException;

    /**
     * 重置认证信息
     *
     * @param userId   用户id
     * @param password 明文密码
     * @return boolean
     **/
    boolean setResetAuth(String userId, String password) throws UserNotFoundException;

    /**
     * 移除认证信息
     *
     * @param userId 用户id
     **/
    boolean removeAuth(String userId) throws UserNotFoundException;

    /**
     * 查询认证信息是否存在
     *
     * @param username 用户名
     **/
    boolean queryAuthIsExistsByUsername(String username);

    /**
     * 设置认证信息状态
     *
     * @param userId 用户id
     * @param state  用户状态 0：正常，1：禁用
     **/
    boolean setAuthState(String userId, Integer state) throws UserNotFoundException;

    /**
     * 修改认证信息
     *
     * @param user 用户对象
     **/
    boolean updateAuth(User user, String... roleIds) throws UserExistException, RoleNotFoundException;
}
