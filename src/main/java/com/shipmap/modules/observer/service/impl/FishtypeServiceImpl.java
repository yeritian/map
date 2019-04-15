package com.shipmap.modules.observer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.observer.dao.FishtypeMapper;
import com.shipmap.modules.observer.model.Fishtype;
import com.shipmap.modules.observer.service.FishtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-03-28
 */
@Service
public class FishtypeServiceImpl extends ServiceImpl<FishtypeMapper, Fishtype> implements FishtypeService {

    @Autowired
    private FishtypeMapper fishtypeMapper;

    /**
     * 分页查询
     *
     * @param fishtypePage 分页对象
     * @param column       表的列名
     * @param value        匹配的值
     */
    @Override
    public Page<Fishtype> list(Page<Fishtype> fishtypePage, String column, String value) {
        Wrapper<Fishtype> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        fishtypePage.setRecords(fishtypeMapper.selectPage(fishtypePage, wrapper));
        return fishtypePage;
    }

    /**
     * 添加
     *
     * @param fishtype   添加对象
     * @param activeUser 当前登录用户
     */
    @Override
    public boolean add(Fishtype fishtype, ActiveUser activeUser) {
        return fishtypeMapper.insert(fishtype) > 0;
    }

    /**
     * 更新
     *
     * @param fishtype   修改对象
     * @param activeUser 当前登录用户
     */
    @Override
    public boolean update(Fishtype fishtype, ActiveUser activeUser) {
        return fishtypeMapper.updateById(fishtype) > 0;
    }

    /**
     * 根据id查询
     *
     * @param fishtypeId
     */
    @Override
    public Fishtype getById(String fishtypeId) {
        return fishtypeMapper.selectById(fishtypeId);
    }

    /**
     * 根据id删除
     *
     * @param fishtypeId
     */
    @Override
    public boolean delete(String fishtypeId) {
        return fishtypeMapper.deleteById(fishtypeId) > 0;
    }


    /*文件导出*/
    @Override
    public List<Fishtype> getfishtype(String id) {
        Wrapper<Fishtype> wrapper = new EntityWrapper<>();
        wrapper.eq("obfishcatchid", id);
        return fishtypeMapper.getfishtype(wrapper);
    }

}

