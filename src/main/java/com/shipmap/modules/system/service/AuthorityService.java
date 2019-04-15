package com.shipmap.modules.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.shipmap.modules.system.model.Authority;
import com.shipmap.modules.system.service.exception.AuthorityHaveChildNodesException;
import com.shipmap.modules.system.service.exception.AuthorityNotFoundException;

import java.util.List;

public interface AuthorityService extends IService<Authority> {
    /**
     * 根据用户id查询
     *
     * @param userId 菜单功能对象
     **/
    List<Authority> listByUserId(String userId);

    /**
     * 根据角色id查询
     *
     * @param roleId 菜单功能对象
     **/
    List<String> listByRoleId(String roleId);

    /**
     * 增加菜单功能
     *
     * @param authority 菜单功能对象
     **/
    boolean addAuthority(Authority authority);

    /**
     * 修改菜单功能
     *
     * @param authority 菜单功能对象
     **/
    boolean updateAuthority(Authority authority) throws AuthorityNotFoundException;

    /**
     * 删除菜单功能
     *
     * @param authorityId 菜单功能id
     **/
    boolean delAuthority(String authorityId) throws AuthorityNotFoundException, AuthorityHaveChildNodesException;

    /**
     * 根据菜单功能对象
     *
     * @param authority 菜单功能对象
     **/
    List<Authority> findByAuthority(Authority authority);
}
