package com.shipmap.modules.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.shipmap.modules.system.model.UserRole;

import java.util.List;

/**
 * Created by Administrator on 2018-12-19 下午 4:09.
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     * 根据用户id查询用户角色id列表
     *
     * @param userId 用户id
     **/
    String[] getRoleIds(String userId);

    /**
     * 根据用户id查询用户角色列表
     *
     * @param userId 用户id
     **/
    List<UserRole> listByUserId(String userId);

    /**
     * 根据角色id删除
     *
     * @param roleId 角色id
     **/
    Integer deleteByRoleId(String roleId);

}
