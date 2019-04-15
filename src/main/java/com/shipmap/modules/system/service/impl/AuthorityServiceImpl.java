package com.shipmap.modules.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.modules.system.dao.AuthorityMapper;
import com.shipmap.modules.system.dao.RoleAuthorityMapper;
import com.shipmap.modules.system.model.Authority;
import com.shipmap.modules.system.service.AuthorityService;
import com.shipmap.modules.system.service.exception.AuthorityHaveChildNodesException;
import com.shipmap.modules.system.service.exception.AuthorityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {
    @Autowired
    RoleAuthorityMapper roleAuthorityMapper;
    private static final int SYSTEM_HOME_TYPE = 0;

    /**
     * 根据用户id查询功能信息列表
     *
     * @param userId 用户id
     **/
    @Override
    public List<Authority> listByUserId(String userId) {
        return baseMapper.listByUserId(userId);
    }

    /**
     * 根据角色id查询功能信息列表
     *
     * @param roleId 角色id
     **/
    @Override
    public List<String> listByRoleId(String roleId) {
        return baseMapper.listByRoleId(roleId);
    }

    /**
     * 增加菜单功能
     *
     * @param authority 菜单功能对象
     **/
    @Override
    public boolean addAuthority(Authority authority) {
        String parentId = authority.getParentId();
        if ("-1".equals(parentId)) {
            authority.setType(SYSTEM_HOME_TYPE);
        } else {
            Authority superiorAuthority = baseMapper.selectById(parentId);
            authority.setType(superiorAuthority.getType() + 1);
        }
        int number = baseMapper.insert(authority);
        return number > 0 ? true : false;
    }

    /**
     * 修改菜单功能
     *
     * @param authority 菜单功能对象
     **/
    public boolean updateAuthority(Authority authority) throws AuthorityNotFoundException {
        Authority tempAuthority = baseMapper.selectById(authority.getId());
        if (tempAuthority == null) {
            //权限信息不存在
            throw new AuthorityNotFoundException();
        }
        //修改项
        tempAuthority.setAuthority(authority.getAuthority());
        tempAuthority.setAuthorityName(authority.getAuthorityName());
        tempAuthority.setMenuIcon(authority.getMenuIcon());
        tempAuthority.setMenuUrl(authority.getMenuUrl());
        tempAuthority.setOrderNumber(authority.getOrderNumber());
        int number = baseMapper.updateById(tempAuthority);
        return number > 0 ? true : false;
    }

    /**
     * 删除菜单功能
     *
     * @param authorityId 菜单功能id
     **/
    public boolean delAuthority(String authorityId) throws AuthorityNotFoundException, AuthorityHaveChildNodesException {
        Authority tempAuthority = baseMapper.selectById(authorityId);
        if (tempAuthority == null) {
            //权限信息不存在
            throw new AuthorityNotFoundException();
        }
        List<Authority> authorityList = baseMapper.listByParentId(tempAuthority.getId());
        if (authorityList != null && authorityList.size() > 0) {
            //菜单为父级菜单
            throw new AuthorityHaveChildNodesException();
        }
        roleAuthorityMapper.deleteByAuthorityId(authorityId);
        Integer number = baseMapper.deleteById(authorityId);
        return number > 0 ? true : false;
    }

    /**
     * 根据菜单功能对象
     *
     * @param authority 菜单功能对象
     **/
    public List<Authority> findByAuthority(Authority authority) {
        return this.baseMapper.findByAuthority(authority);
    }
}
