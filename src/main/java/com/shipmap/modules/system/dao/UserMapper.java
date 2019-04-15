package com.shipmap.modules.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.system.model.User;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户获取用户信息
     **/
    User getByUsername(String username);

    /**
     * @param rowBounds 分页查询条件（可以为 RowBounds.DEFAULT）
     * @param user      实体对象封装操作类（可以为 null）
     * @return List<User>
     */
    List<User> findByUser(RowBounds rowBounds, User user);
}
