package com.shipmap.modules.fishing.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.fishing.dao.FishinglogRawMapper;
import com.shipmap.modules.fishing.model.FishinglogRaw;
import com.shipmap.modules.fishing.service.FishinglogRawService;
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
 * @since 2018-12-26
 */
@Service
public class FishinglogRawServiceImpl extends ServiceImpl<FishinglogRawMapper, FishinglogRaw> implements FishinglogRawService {

    @Autowired
    private FishinglogRawMapper fishinglogRawMapper;

    @Override
    public Page<FishinglogRaw> list(Page<FishinglogRaw> fishinglogRawPage, String column, String value) {
        Wrapper<FishinglogRaw> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        List<FishinglogRaw> fishinglogRawList = baseMapper.selectPage(fishinglogRawPage, wrapper.orderBy("create_time", true));
        return fishinglogRawPage.setRecords(fishinglogRawList);
    }

    @Override
    public boolean add(FishinglogRaw fishinglogRaw, ActiveUser activeUser) {
        fishinglogRaw.setCreateTime(new Date());
        fishinglogRaw.setUpdateTime(new Date());
        return fishinglogRawMapper.insert(fishinglogRaw) > 0;
    }

    @Override
    public boolean update(FishinglogRaw fishinglogRaw, ActiveUser activeUser) {
        fishinglogRaw.setUpdateTime(new Date());
        return fishinglogRawMapper.updateById(fishinglogRaw) > 0;
    }

    @Override
    public FishinglogRaw getById(String fishinglogRawId) {
        return fishinglogRawMapper.selectById(fishinglogRawId);
    }

    @Override
    public boolean delete(String fishinglogRawId) {
        return fishinglogRawMapper.deleteById(fishinglogRawId) > 0;
    }

}

