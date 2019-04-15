package com.shipmap.modules.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.modules.system.model.User;

public interface UserService extends IService<User> {
    /**
     * 根据用户获取用户信息
     **/
    User getByUsername(String username);

    /**
     * 查询用户列表
     *
     * @param page 分页对象
     * @param user 用户对象
     **/
    Page<User> findByUser(Page<User> page, User user);
}
