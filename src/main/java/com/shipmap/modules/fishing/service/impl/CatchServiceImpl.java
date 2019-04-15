package com.shipmap.modules.fishing.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.fishing.dao.CatchMapper;
import com.shipmap.modules.fishing.model.Catch;
import com.shipmap.modules.fishing.service.CatchService;
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
 * @since 2018-12-25
 */
@Service
public class CatchServiceImpl extends ServiceImpl<CatchMapper, Catch> implements CatchService {

    @Autowired
    private CatchMapper catchMapper;

    @Override
    public Page<Catch> list(Page<Catch> catchPage, String column, String value) {
        Wrapper<Catch> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        List<Catch> catchList = baseMapper.selectPage(catchPage, wrapper.orderBy("create_time", true));
        return catchPage.setRecords(catchList);
    }

    @Override
    public boolean add(Catch catch1, ActiveUser activeUser) {
        catch1.setCreateTime(new Date());
        catch1.setCreator(activeUser.getUserId());
        catch1.setUpdateTime(new Date());
        catch1.setUpdator(activeUser.getUserId());
        return catchMapper.insert(catch1) > 0;
    }

    @Override
    public boolean update(Catch catch1, ActiveUser activeUser) {
        catch1.setUpdateTime(new Date());
        catch1.setUpdator(activeUser.getUserId());
        return catchMapper.updateById(catch1) > 0;
    }

    @Override
    public Catch getById(String catchId) {
        return catchMapper.selectById(catchId);
    }

    @Override
    public boolean delete(String catchId) {
        return catchMapper.deleteById(catchId) > 0;
    }

}

