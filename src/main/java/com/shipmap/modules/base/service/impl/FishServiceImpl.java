package com.shipmap.modules.base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.utils.NumberUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.base.dao.FishMapper;
import com.shipmap.modules.base.model.Fish;
import com.shipmap.modules.base.model.NameVO;
import com.shipmap.modules.base.service.FishService;
import com.shipmap.modules.system.model.User;
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
 * @since 2018-12-19
 */
@Service
public class FishServiceImpl extends ServiceImpl<FishMapper, Fish> implements FishService {

    @Autowired
    private FishMapper fishMapper;

    @Override
    public List<Fish> list(Page<Fish> page, String column, String value) {
        Wrapper<Fish> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
//        Page<Fish> fishPage = new Page<>(pageNum, pageSize);
        List<Fish> fishList = baseMapper.selectPage(page, wrapper.orderBy("create_time", true));
        return fishList;
    }

    @Override
    public boolean add(Fish fish, User u) {
        Integer fid = fishMapper.getFishId();
        String c = NumberUtil.transIntTo62(fid);
        fish.setId(c);
        fish.setCreateTime(new Date());
        fish.setCreator(u.getId());
        fish.setUpdateTime(new Date());
        fish.setUpdator(u.getId());
        return fishMapper.insert(fish) > 0;
    }

    @Override
    public boolean update(Fish fish, User u) {
        fish.setUpdateTime(new Date());
        fish.setUpdator(u.getId());
        return fishMapper.updateById(fish) > 0;
    }

    @Override
    public Fish getById(String fishId) {
        return fishMapper.selectById(fishId);
    }

    @Override
    public boolean delete(String fishId) {
        return fishMapper.deleteById(fishId) > 0;
    }

    @Override
    public List<NameVO> listAll() {
        List<NameVO> list = fishMapper.selectAll();
        return list;
    }

    public List<NameVO> listRemain(String id) {
        List<NameVO> Relist = fishMapper.selectRemaining(id);
        return Relist;
    }

}

