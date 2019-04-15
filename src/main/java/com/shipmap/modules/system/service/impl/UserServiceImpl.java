package com.shipmap.modules.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.modules.system.dao.UserMapper;
import com.shipmap.modules.system.model.User;
import com.shipmap.modules.system.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 根据用户获取用户信息
     **/
    @Override
    public User getByUsername(String username) {
        return baseMapper.getByUsername(username);
    }

    /**
     * 查询用户列表
     *
     * @param page 分页对象
     * @param user 用户对象
     **/
    @Override
    public Page<User> findByUser(Page<User> page, User user) {
        return page.setRecords(this.baseMapper.findByUser(page, user));
    }

}
