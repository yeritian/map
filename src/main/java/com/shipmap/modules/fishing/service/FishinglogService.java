package com.shipmap.modules.fishing.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.base.model.NameVO;
import com.shipmap.modules.fishing.model.Catch;
import com.shipmap.modules.fishing.model.Discard;
import com.shipmap.modules.fishing.model.FishingLogTongJi;
import com.shipmap.modules.fishing.model.Fishinglog;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-25
 */

public interface FishinglogService extends IService<Fishinglog> {

    Page<Fishinglog> list(Page<Fishinglog> page, String company, String ship, String actionType, String logType, String start, String end, Integer type);

    List<Fishinglog> selectList(Fishinglog fishinglog);

    Fishinglog getById(String fishinglogId);

    boolean add(Fishinglog fishinglog, ActiveUser activeUser) throws BusinessException;

    boolean delete(String fishinglogId);

    List<Catch> listFish(String id);

    List<Discard> listDiscard(String id);

    List<NameVO> listFishType(String id);

    List<NameVO> listStandard(String id);

    List<NameVO> listUnit(String id);

    boolean updateLog(Fishinglog fishinglog, ActiveUser activeUser);

    Page<FishingLogTongJi> selectTripLogCount(Page<FishingLogTongJi> page, String company, String ship);

}
