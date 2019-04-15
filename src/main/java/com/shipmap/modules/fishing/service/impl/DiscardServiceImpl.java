package com.shipmap.modules.fishing.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.fishing.dao.DiscardMapper;
import com.shipmap.modules.fishing.model.Discard;
import com.shipmap.modules.fishing.service.DiscardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-25
 */
@Service
public class DiscardServiceImpl extends ServiceImpl<DiscardMapper, Discard> implements DiscardService {

    @Autowired
    private DiscardMapper discardMapper;

    @Override
    public Page<Discard> list(Page<Discard> discardPage, String column, String value) {
        Wrapper<Discard> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        List<Discard> discardList = baseMapper.selectPage(discardPage, wrapper.orderBy("create_time", true));
        return discardPage.setRecords(discardList);
    }

    @Override
    public boolean add(Discard discard, ActiveUser activeUser) {
        return discardMapper.insert(discard) > 0;
    }

    @Override
    public boolean update(Discard discard, ActiveUser activeUser) {
        return discardMapper.updateById(discard) > 0;
    }

    @Override
    public Discard getById(String discardId) {
        return discardMapper.selectById(discardId);
    }

    @Override
    public boolean delete(String discardId) {
        return discardMapper.deleteById(discardId) > 0;
    }

}

