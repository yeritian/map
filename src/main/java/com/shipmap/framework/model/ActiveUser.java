package com.shipmap.framework.model;

import com.shipmap.modules.system.model.Authority;
import com.shipmap.modules.system.model.NavigationBarVo;
import com.shipmap.modules.system.model.User;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class ActiveUser implements Serializable {
    private static final long serialVersionUID = -2784018851609596802L;

    public ActiveUser(User user, String[] roles, String[] authorities, List<Authority> authorityList, Map<String, NavigationBarVo> menuMap, String cssStr) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.roles = roles;
        this.authorities = authorities;
        this.authorityList = authorityList;
        this.menuMap = menuMap;
        this.cssStr = cssStr;
    }

    public ActiveUser() {
    }

    private String userId;  // 用户id
    private String[] authorities;  //权限标识
    private List<Authority> authorityList; //权限
    private String[] roles;  // 角色
    private Boolean isRememberMe; //是否记住密码
    private Long expireTime;   //过期时间
    private String username;  // 账号
    private Map<String, NavigationBarVo> menuMap; //用户菜单
    private String cssStr; //用户css

    public String getCssStr() {
        return cssStr;
    }

    public void setCssStr(String cssStr) {
        this.cssStr = cssStr;
    }

    public Map<String, NavigationBarVo> getMenuMap() {
        return menuMap;
    }

    public void setMenuMap(Map<String, NavigationBarVo> menuMap) {
        this.menuMap = menuMap;
    }

    public List<Authority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<Authority> authorityList) {
        this.authorityList = authorityList;
    }

    public String[] getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String[] authorities) {
        this.authorities = authorities;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }


    public Boolean getRememberMe() {
        return isRememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        isRememberMe = rememberMe;
    }
}
