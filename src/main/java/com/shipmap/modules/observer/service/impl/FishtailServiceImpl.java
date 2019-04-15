package com.shipmap.modules.observer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.JSONUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.observer.dao.FishtailMapper;
import com.shipmap.modules.observer.model.Fishtail;
import com.shipmap.modules.observer.service.FishtailService;
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
public class FishtailServiceImpl extends ServiceImpl<FishtailMapper, Fishtail> implements FishtailService {

    @Autowired
    private FishtailMapper fishtailMapper;

    /**
     * 分页查询
     *
     * @param fishtailPage 分页对象
     * @param column       表的列名
     * @param value        匹配的值
     */
    @Override
    public Page<Fishtail> list(Page<Fishtail> fishtailPage, String column, String value, String id) {
        Wrapper<Fishtail> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        fishtailPage.setRecords(fishtailMapper.selectFishtaillist(fishtailPage, wrapper, id));
        return fishtailPage;
    }

    /**
     * 添加
     *
     * @param fishtail 添加对象
     */
    @Override
    public boolean add(Fishtail fishtail, ActiveUser activeUser) {
        fishtail.setCreator(activeUser.getUsername());
        fishtail.setCreateTime(new Date());
        return fishtailMapper.insert(fishtail) > 0;
    }

    /**
     * 更新
     *
     * @param fishtail   修改对象
     * @param activeUser 用户
     */
    @Override
    public boolean update(Fishtail fishtail, ActiveUser activeUser) {
        fishtail.setUpdator(activeUser.getUsername());
        return fishtailMapper.updateById(fishtail) > 0;
    }

    /**
     * 根据id查询
     *
     * @param fishtailId
     */
    @Override
    public Fishtail getById(String fishtailId) {
        return fishtailMapper.selectById(fishtailId);
    }

    /**
     * 根据id删除
     *
     * @param fishtailId
     */
    @Override
    public boolean delete(String fishtailId) {
        return fishtailMapper.deleteById(fishtailId) > 0;
    }

    /**
     * 根据id删除 -- 批量
     *
     * @param fishtailid
     */
    @Override
    public boolean deletAllfishtailid(String fishtailid) {
        boolean res = false;
        List<String> list = JSONUtil.parseArray(fishtailid);
        for (int i = 0; i < list.size(); i++) {
            res = fishtailMapper.deleteById(list.get(i)) > 0;
        }
        return res;

    }

    /*根据id 查询*/
    @Override
    public Fishtail selectByidFishtail(String id) {
        return fishtailMapper.selectByidFishtail(id);
    }

    /*文件导入*/
    @Override
    public int InsertExcel(Fishtail fishtail) {
        return fishtailMapper.insert(fishtail);
    }
}

