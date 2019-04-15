package com.shipmap.modules.fishing.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.fishing.model.Trip;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2018-12-25
 */
public interface TripMapper extends BaseMapper<Trip> {

    Integer getTripId();

    void insertTrip(Trip trip);

    Trip selectByShipIdAndTime(Trip t);

    void updateFieldsById(Trip t);

    Trip selectByTripId(String tripId);
}
