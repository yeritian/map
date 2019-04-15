package com.shipmap.modules.base.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.modules.base.model.NameVO;
import com.shipmap.modules.base.model.Ship;
import com.shipmap.modules.system.model.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-12
 */

public interface ShipService extends IService<Ship> {

    List<Ship> list(Page<Ship> page, String searchKey, String searchValue);

    Ship getById(String shipId);

    boolean add(Ship ship, User u) throws BusinessException;

    boolean update(Ship ship, User u);

    boolean delete(String shipId);

    List<NameVO> listFish(String id);

    boolean addFish(String fishId, String shipId, User u);

    boolean deleteFish(String shipId, String fishId);

    List<NameVO> listStandard(String id);

    List<NameVO> listUnit(String id);

    boolean addStandard(String standardId, String shipId, User loginUser);

    boolean addUnit(String unitId, String shipId, User loginUser);

    boolean deleteStandard(String shipId, String standardId);

    boolean deleteUnit(String shipId, String unitId);

    List<Ship> selectShipName(String company);
}
