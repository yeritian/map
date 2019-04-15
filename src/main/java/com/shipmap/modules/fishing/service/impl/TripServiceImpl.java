package com.shipmap.modules.fishing.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.fishing.dao.TripCatchMapper;
import com.shipmap.modules.fishing.dao.TripMapper;
import com.shipmap.modules.fishing.model.Trip;
import com.shipmap.modules.fishing.model.TripCatch;
import com.shipmap.modules.fishing.service.TripService;
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
public class TripServiceImpl extends ServiceImpl<TripMapper, Trip> implements TripService {

    @Autowired
    private TripMapper tripMapper;
    @Autowired
    private TripCatchMapper tripCatchMapper;

    @Override
    public Page<Trip> list(Page<Trip> tripPage, String column, String value) {
        Wrapper<Trip> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        List<Trip> tripList = baseMapper.selectPage(tripPage, wrapper.orderBy("create_time", true));
        return tripPage.setRecords(tripList);
    }

    @Override
    public boolean add(Trip trip, ActiveUser activeUser) {
        trip.setCreateTime(new Date());
        trip.setCreator(activeUser.getUserId());
        trip.setUpdateTime(new Date());
        trip.setUpdator(activeUser.getUserId());
        return tripMapper.insert(trip) > 0;
    }

    @Override
    public boolean update(Trip trip, ActiveUser activeUser) {
        trip.setUpdateTime(new Date());
        trip.setUpdator(activeUser.getUserId());
        return tripMapper.updateById(trip) > 0;
    }

    @Override
    public Trip getById(String tripId) {
        return tripMapper.selectById(tripId);
    }

    @Override
    public boolean delete(String tripId) {
        return tripMapper.deleteById(tripId) > 0;
    }

    @Override
    public Trip selectByTripId(String tripId) {
        Trip trip = tripMapper.selectByTripId(tripId);
        return trip;
    }

    @Override
    public List<TripCatch> fishStart(String tripId) {
        List<TripCatch> tripCatches = tripCatchMapper.selectByTripId(tripId, "1");
        return tripCatches;
    }

    @Override
    public List<TripCatch> fishUnloading(String tripId) {
        List<TripCatch> tripCatches = tripCatchMapper.selectByTripId(tripId, "2");
        return tripCatches;
    }

    @Override
    public List<TripCatch> fishEnd(String tripId) {
        List<TripCatch> tripCatches = tripCatchMapper.selectByTripId(tripId, "3");
        return tripCatches;
    }
}

