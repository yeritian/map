package com.shipmap.modules.observer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.JSONUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.observer.dao.SeafishinglogMapper;
import com.shipmap.modules.observer.model.Seafishinglog;
import com.shipmap.modules.observer.service.SeafishinglogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class SeafishinglogServiceImpl extends ServiceImpl<SeafishinglogMapper, Seafishinglog> implements SeafishinglogService {

    @Autowired
    private SeafishinglogMapper seafishinglogMapper;

    /**
     * 分页查询
     *
     * @param seafishinglogPage 分页对象
     * @param column            表的列名
     * @param value             匹配的值
     */
    @Override
    public Page<Seafishinglog> list(Page<Seafishinglog> seafishinglogPage, String column, String value, String id) {
        Wrapper<Seafishinglog> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        seafishinglogPage.setRecords(seafishinglogMapper.selectSeafishlog(seafishinglogPage, wrapper, id));
        return seafishinglogPage;
    }

    /**
     * 添加
     *
     * @param seafishinglog 添加对象
     */
    @Override
    public boolean add(Seafishinglog seafishinglog, ActiveUser activeUser) {
        seafishinglog.setCreateTime(new Date());
        seafishinglog.setCreator(activeUser.getUsername());
        return seafishinglogMapper.insert(seafishinglog) > 0;
    }

    /**
     * 更新
     *
     * @param seafishinglog 修改对象
     */
    @Override
    public boolean update(Seafishinglog seafishinglog, ActiveUser activeUser) {
        seafishinglog.setUpdator(activeUser.getUsername());
        return seafishinglogMapper.updateById(seafishinglog) > 0;
    }

    /**
     * 根据id查询
     *
     * @param seafishinglogId
     */
    @Override
    public Seafishinglog getById(String seafishinglogId) {
        return seafishinglogMapper.selectById(seafishinglogId);
    }

    /**
     * 根据id删除
     *
     * @param seafishinglogId
     */
    @Override
    public boolean delete(String seafishinglogId) {
        return seafishinglogMapper.deleteById(seafishinglogId) > 0;
    }

    /*批量删除
     * * @param map seafishinglogId
     * */
    @Override
    public boolean delectAllSeafishingid(String seafishinglogId) {
        boolean res = false;
        List<String> list = JSONUtil.parseArray(seafishinglogId);
        for (int i = 0; i < list.size(); i++) {
            res = seafishinglogMapper.deleteById(list.get(i)) > 0;
        }
        return res;
    }

    /**
     * 根据id查询
     *
     * @param id id
     * @return
     */
    @Override
    public Seafishinglog selectByid(String id) {
        return seafishinglogMapper.selectByid(id);
    }

    /*文件导入*/
    @Override
    public int InsertLayer(Seafishinglog layer) {
        return seafishinglogMapper.insert(layer);
    }

}

