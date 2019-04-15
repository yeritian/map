package com.shipmap.framework.service.impl;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    @Override
    public boolean insert(T entity) {
        return super.insert(entity);
    }
}
