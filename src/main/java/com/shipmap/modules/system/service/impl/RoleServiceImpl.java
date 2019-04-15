package com.shipmap.modules.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.service.exception.RoleNotFoundException;
import com.shipmap.modules.system.dao.AuthorityMapper;
import com.shipmap.modules.system.dao.RoleAuthorityMapper;
import com.shipmap.modules.system.dao.RoleMapper;
import com.shipmap.modules.system.model.Authority;
import com.shipmap.modules.system.model.Role;
import com.shipmap.modules.system.model.RoleAuthority;
import com.shipmap.modules.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    RoleAuthorityMapper roleAuthorityMapper;
    @Autowired
    AuthorityMapper authorityMapper;

    /**
     * 根据角色id批量查询角色信息
     *
     * @param roleIds 角色信息列表
     **/
    @Override
    public List<Role> listByRoleIds(String... roleIds) {
        return this.baseMapper.listByRoleIds(roleIds);
    }

    /**
     * 根据用户id删除角色功能信息
     *
     * @param roleId 角色信息
     **/
    @Override
    public boolean delRoleAndAuthorityByRoleId(String roleId) throws RoleNotFoundException {
        //检查角色id
        Role role = this.baseMapper.selectById(roleId);
        if (role == null) {
            throw new RoleNotFoundException();
        }
        //删除角色功能中间信息
        roleAuthorityMapper.deleteByRoleId(role.getId());
        //删除角色信息
        Integer number = this.baseMapper.deleteById(role.getId());
        return number > 0 ? true : false;
    }

    /**
     * 设置角色功能
     *
     * @param roleId       角色id
     * @param authorityIds 功能列表
     **/
    public void setRoleAndAuthority(String roleId, String... authorityIds) throws RoleNotFoundException {
        //检查角色id
        Role role = this.baseMapper.selectById(roleId);
        if (role == null) {
            throw new RoleNotFoundException();
        }
        //覆盖性增加
        roleAuthorityMapper.deleteByRoleId(role.getId());
        if (authorityIds.length == 0) {
            //没有功能id
            return;
        }
        List<Authority> authorityList = authorityMapper.listByRoleIds(authorityIds);
        for (int i = 0; i < authorityList.size(); i++) {
            RoleAuthority roleAuthority = new RoleAuthority();
            roleAuthority.setRoleId(role.getId());
            roleAuthority.setAuthorityId(authorityList.get(i).getId());
            roleAuthorityMapper.insert(roleAuthority);
        }
    }
}
