package com.shipmap.modules.base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.utils.NumberUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.base.dao.*;
import com.shipmap.modules.base.model.*;
import com.shipmap.modules.base.service.ShipService;
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
 * @since 2018-12-12
 */
@Service
public class ShipServiceImpl extends ServiceImpl<ShipMapper, Ship> implements ShipService {

    @Autowired
    private ShipMapper shipMapper;
    @Autowired
    private FishMapper fishMapper;
    @Autowired
    private ShipFishMapper shipFishMapper;
    @Autowired
    private UnitMapper unitMapper;
    @Autowired
    private ShipUnitMapper shipUnitMapper;
    @Autowired
    private StandardMapper standardMapper;
    @Autowired
    private ShipStandardMapper shipStandardMapper;

    @Override
    public List<Ship> list(Page<Ship> page, String column, String value) {
        Wrapper<Ship> wrapper = new EntityWrapper<>();
        wrapper.eq("s.del", 0);
        if (StringUtil.isNotBlank(column)) {
            if (column.equals("mmsi")) {
                wrapper.like("mmsi::varchar", value);
            } else {
                wrapper.like(column, value);
            }
        }
        List<Ship> portList = baseMapper.selectAll(page, wrapper);

        return portList;
    }

    @Override
    public boolean add(Ship ship, User u) {
        Integer sid = shipMapper.getShipId();
        String c = NumberUtil.transIntTo62(sid);
        ship.setId(c);
        ship.setCreateTime(new Date());
        ship.setUpdateTime(new Date());
        ship.setCreator(u.getId());
        ship.setUpdator(u.getId());
        return shipMapper.insert(ship) > 0;
    }

    @Override
    public boolean update(Ship ship, User u) {
        return shipMapper.updateById(ship) > 0;
    }

    @Override
    public Ship getById(String shipId) {
        return shipMapper.selectById(shipId);
    }

    @Override
    public boolean delete(String shipId) {


        return shipMapper.deleteById(shipId) > 0;
    }

    @Override
    public List<NameVO> listFish(String id) {
        List<NameVO> list = fishMapper.selectFishByShip(id);
        return list;
    }

    @Override
    public boolean addFish(String fishId, String shipId, User u) {
        ShipFish sf = new ShipFish();
        sf.setFishId(fishId);
        sf.setShipId(shipId);
        sf.setCreateTime(new Date());
        sf.setUpdateTime(new Date());
        sf.setCreator(u.getId());
        sf.setUpdator(u.getId());
        if (shipFishMapper.insert(sf) > 0) {
            String ver = shipMapper.getVer(shipId);
            int i = NumberUtil.trans62ToInt(ver) + 1;
            int j = i % 14776335;
            ver = NumberUtil.transIntTo62(j);
            shipMapper.setShipVer(shipId, ver);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteFish(String shipId, String fishId) {
        if (shipFishMapper.deleteByIds(shipId, fishId) > 0) {
            String ver = shipMapper.getVer(shipId);
            int i = NumberUtil.trans62ToInt(ver) + 1;
            int j = i % 14776335;
            ver = NumberUtil.transIntTo62(j);
            shipMapper.setShipVer(shipId, ver);
            return true;
        }
        return false;
    }

    @Override
    public List<NameVO> listStandard(String id) {
        List<NameVO> list = standardMapper.selectStandardByShip(id);
        return list;
    }

    @Override
    public List<NameVO> listUnit(String id) {
        List<NameVO> list = unitMapper.selectUnitByShip(id);
        return list;
    }

    @Override
    public boolean addStandard(String standardId, String shipId, User u) {
        ShipStandard sf = new ShipStandard();
        sf.setStandardId(standardId);
        sf.setShipId(shipId);
        sf.setCreateTime(new Date());
        sf.setUpdateTime(new Date());
        sf.setCreator(u.getId());
        sf.setUpdator(u.getId());
        if (shipStandardMapper.insert(sf) > 0) {
            String ver = shipMapper.getVer(shipId);
            int i = NumberUtil.trans62ToInt(ver) + 1;
            int j = i % 14776335;
            ver = NumberUtil.transIntTo62(j);
            shipMapper.setShipVer(shipId, ver);
            return true;
        }
        return false;
    }

    @Override
    public boolean addUnit(String unitId, String shipId, User u) {
        ShipUnit sf = new ShipUnit();
        sf.setUnitId(unitId);
        sf.setShipId(shipId);
        sf.setCreateTime(new Date());
        sf.setUpdateTime(new Date());
        sf.setCreator(u.getId());
        sf.setUpdator(u.getId());
        if (shipUnitMapper.insert(sf) > 0) {
            String ver = shipMapper.getVer(shipId);
            int i = NumberUtil.trans62ToInt(ver) + 1;
            int j = i % 14776335;
            ver = NumberUtil.transIntTo62(j);
            shipMapper.setShipVer(shipId, ver);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteStandard(String shipId, String standardId) {
        if (shipStandardMapper.deleteByIds(shipId, standardId) > 0) {
            String ver = shipMapper.getVer(shipId);
            int i = NumberUtil.trans62ToInt(ver) + 1;
            int j = i % 14776335;
            ver = NumberUtil.transIntTo62(j);
            shipMapper.setShipVer(shipId, ver);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUnit(String shipId, String unitId) {
        if (shipUnitMapper.deleteByIds(shipId, unitId) > 0) {
            String ver = shipMapper.getVer(shipId);
            int i = NumberUtil.trans62ToInt(ver) + 1;
            int j = i % 14776335;
            ver = NumberUtil.transIntTo62(j);
            shipMapper.setShipVer(shipId, ver);
            return true;
        }
        return false;
    }

    /*
     * 查询公司所有船舶名称
     * */
    @Override
    public List<Ship> selectShipName(String company) {
        Wrapper<Ship> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(company)) {
            wrapper.eq("company_id", company);
        }
        return shipMapper.selectList(wrapper);
    }

    public static void main(String[] args) {
        int i = 2;
        int j = 2 % 14776335;
        System.out.println("--->" + j);
    }
}

