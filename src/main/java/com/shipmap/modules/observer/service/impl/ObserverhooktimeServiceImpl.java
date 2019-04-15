package com.shipmap.modules.observer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.JSONUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.observer.dao.ObserverhooktimeMapper;
import com.shipmap.modules.observer.model.Observerhooktime;
import com.shipmap.modules.observer.service.ObserverhooktimeService;
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
public class ObserverhooktimeServiceImpl extends ServiceImpl<ObserverhooktimeMapper, Observerhooktime> implements ObserverhooktimeService {

    @Autowired
    private ObserverhooktimeMapper observerhooktimeMapper;

    /**
     * 分页查询
     *
     * @param observerhooktimePage 分页对象
     * @param column               表的列名
     * @param value                匹配的值
     */
    @Override
    public Page<Observerhooktime> list(Page<Observerhooktime> observerhooktimePage, String column, String value, String id) {
        Wrapper<Observerhooktime> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        observerhooktimePage.setRecords(observerhooktimeMapper.selectObserverHoooktime(observerhooktimePage, wrapper, id));
        return observerhooktimePage;
    }

    /**
     * 添加
     *
     * @param observerhooktime 添加对象
     */
    @Override
    public boolean add(Observerhooktime observerhooktime, ActiveUser activeUser) {
        observerhooktime.setCreateTime(new Date());
        observerhooktime.setCreator(activeUser.getUsername());
        return observerhooktimeMapper.insert(observerhooktime) > 0;
    }

    /**
     * 更新
     *
     * @param observerhooktime 修改对象
     */
    @Override
    public boolean update(Observerhooktime observerhooktime, ActiveUser activeUser) {
        observerhooktime.setUpdator(activeUser.getUsername());
        return observerhooktimeMapper.updateById(observerhooktime) > 0;
    }

    /**
     * 根据id查询
     *
     * @param observerhooktimeId
     */
    @Override
    public Observerhooktime getById(String observerhooktimeId) {
        return observerhooktimeMapper.selectById(observerhooktimeId);

    }

    /**
     * 根据id删除
     *
     * @param observerhooktimeId
     */
    @Override
    public boolean delete(String observerhooktimeId) {
        return observerhooktimeMapper.deleteById(observerhooktimeId) > 0;
    }

    /**
     * 根据id删除---批量
     *
     * @param observerhooktimeId
     */
    @Override
    public boolean deletAllobhooklogid(String observerhooktimeId) {
        boolean res = false;
        List<String> list = JSONUtil.parseArray(observerhooktimeId);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            res = observerhooktimeMapper.deleteById(list.get(i)) > 0;
        }
        return res;
    }


    /*根据id 查询*/
    @Override
    public Observerhooktime selectByidhooktime(String id) {
        return observerhooktimeMapper.selectByidhooktime(id);
    }


    /*文件导入*/
    public int InsertExcel(Observerhooktime obtime) {
        return observerhooktimeMapper.insert(obtime);
    }

}

