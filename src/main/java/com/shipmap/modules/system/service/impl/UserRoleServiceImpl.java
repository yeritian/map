package com.shipmap.modules.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.modules.system.dao.UserRoleMapper;
import com.shipmap.modules.system.model.UserRole;
import com.shipmap.modules.system.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018-12-19 下午 4:10.
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    /**
     * 根据用户id查询用户角色id列表
     *
     * @param userId 用户id
     **/
    @Override
    public String[] getRoleIds(String userId) {
        List<UserRole> userRoles = this.baseMapper.listByUserId(userId);
        String[] roleIds = new String[userRoles.size()];
        for (int i = 0; i < userRoles.size(); i++) {
            roleIds[i] = userRoles.get(i).getRoleId();
        }
        return roleIds;
    }

    /**
     * 根据用户id查询用户角色列表
     *
     * @param userId 用户id
     **/
    @Override
    public List<UserRole> listByUserId(String userId) {
        return this.baseMapper.listByUserId(userId);
    }

    /**
     * 根据角色id删除
     *
     * @param roleId 角色id
     **/
    @Override
    public Integer deleteByRoleId(String roleId) {
        return this.baseMapper.deleteByUserId(roleId);
    }

}
