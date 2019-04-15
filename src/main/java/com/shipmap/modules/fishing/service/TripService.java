package com.shipmap.modules.fishing.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.fishing.model.Trip;
import com.shipmap.modules.fishing.model.TripCatch;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-25
 */

public interface TripService extends IService<Trip> {

    Page<Trip> list(Page<Trip> tripPage, String searchKey, String searchValue);

    Trip getById(String tripId);

    boolean add(Trip trip, ActiveUser activeUser) throws BusinessException;

    boolean update(Trip trip, ActiveUser activeUser);

    boolean delete(String tripId);

    Trip selectByTripId(String tripId);

    List<TripCatch> fishStart(String tripId);

    List<TripCatch> fishUnloading(String tripId);

    List<TripCatch> fishEnd(String tripId);

}
