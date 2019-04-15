package com.shipmap.modules.fishing.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.DateUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.base.dao.FishMapper;
import com.shipmap.modules.base.dao.StandardMapper;
import com.shipmap.modules.base.dao.UnitMapper;
import com.shipmap.modules.base.model.NameVO;
import com.shipmap.modules.fishing.dao.CatchMapper;
import com.shipmap.modules.fishing.dao.DiscardMapper;
import com.shipmap.modules.fishing.dao.FishinglogMapper;
import com.shipmap.modules.fishing.model.Catch;
import com.shipmap.modules.fishing.model.Discard;
import com.shipmap.modules.fishing.model.FishingLogTongJi;
import com.shipmap.modules.fishing.model.Fishinglog;
import com.shipmap.modules.fishing.service.FishinglogService;
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
public class FishinglogServiceImpl extends ServiceImpl<FishinglogMapper, Fishinglog> implements FishinglogService {

    @Autowired
    private FishinglogMapper fishinglogMapper;
    @Autowired
    private CatchMapper catchMapper;
    @Autowired
    private DiscardMapper discardMapper;
    @Autowired
    private FishMapper fishMapper;
    @Autowired
    private StandardMapper standardMapper;
    @Autowired
    private UnitMapper unitMapper;

    /*
     * 鱼捞日志条件分页查询
     * */
    @Override
    public Page<Fishinglog> list(Page<Fishinglog> page, String company, String ship, String actionType, String logType, String start, String end, Integer type) {
        Wrapper<Fishinglog> wrapper = new EntityWrapper<>();
        wrapper.eq("type", type);
        wrapper.eq("l.del", 0);
        wrapper.eq("l.is_select", 0);
        if (StringUtil.isNotBlank(ship)) {
            wrapper.eq("ship_id", ship);
        } else if (StringUtil.isNotBlank(company)) {
            wrapper.eq("company_id", company);
        }
        if (StringUtil.isNotBlank(logType)) {
            wrapper.eq("log_type", logType);
        }
        if (StringUtil.isNotBlank(actionType)) {
            wrapper.eq("action_type", actionType);
        }
        if (StringUtil.isNotBlank(start) && StringUtil.isNotBlank(end)) {
            wrapper.between("log_date", DateUtil.parseDate(start), DateUtil.parseDate(end));
        }
        List<Fishinglog> fishinglogList = fishinglogMapper.selectPages(page, wrapper.orderBy("l.create_time", false));
        return page.setRecords(fishinglogList);
    }

    /**
     * 查询船舶日志航次数
     */
    @Override
    public Page<FishingLogTongJi> selectTripLogCount(Page<FishingLogTongJi> page, String company, String ship) {
        Wrapper<Fishinglog> wrapper = new EntityWrapper<>();
        wrapper.eq("s.del", 0);
        if (StringUtil.isNotBlank(ship)) {
            wrapper.eq("s.id", ship);
        } else if (StringUtil.isNotBlank(company)) {
            wrapper.eq("company_id", company);
        }

        //wrapper.between()
        List<FishingLogTongJi> fishingLogTongJiList = fishinglogMapper.selectTongJiPages(page, wrapper);
        return page.setRecords(fishingLogTongJiList);
    }

    /*
     * 鱼捞日志条件查询(导出)
     * */
    @Override
    public List<Fishinglog> selectList(Fishinglog fishinglog) {
        Wrapper<Fishinglog> wrapper = new EntityWrapper<>();
        wrapper.eq("l.del", 0);
        wrapper.eq("l.is_select", 0);
        if (fishinglog != null) {
            wrapper.eq("ship_id", fishinglog.getShipId());
            wrapper.eq("action_type", fishinglog.getLogType());
            wrapper.between("log_date", fishinglog.getStartTime(), fishinglog.getEndTime());
        }
        return fishinglogMapper.selectList(wrapper.orderBy("l.log_date", true));

    }

    @Override
    public boolean add(Fishinglog fishinglog, ActiveUser activeUser) {
        fishinglog.setCreateTime(new Date());
        fishinglog.setCreator(activeUser.getUserId());
        fishinglog.setUpdateTime(new Date());
        fishinglog.setUpdator(activeUser.getUserId());
        return fishinglogMapper.insert(fishinglog) > 0;
    }

    @Override
    public Fishinglog getById(String fishinglogId) {
        return fishinglogMapper.selectOneById(fishinglogId);
    }

    @Override
    public boolean delete(String fishinglogId) {
        return fishinglogMapper.deleteById(fishinglogId) > 0;
    }

    @Override
    public List<Catch> listFish(String id) {
        Wrapper<Catch> wrapper = new EntityWrapper<>();
        wrapper.eq("log_id", id);
        return catchMapper.selectListByLogId(id);
    }

    @Override
    public List<Discard> listDiscard(String id) {
        Wrapper<Discard> wrapper = new EntityWrapper<>();
        wrapper.eq("log_id", id);
        return discardMapper.selectListByLogId(id);
    }

    @Override
    public List<NameVO> listFishType(String id) {
        return fishMapper.selectFishByShip(id);
    }

    @Override
    public List<NameVO> listStandard(String id) {
        return standardMapper.selectStandardByShip(id);
    }

    @Override
    public List<NameVO> listUnit(String id) {
        return unitMapper.selectUnitByShip(id);
    }

    /**
     * 修改渔捞日志
     */
    @Override
    public boolean updateLog(Fishinglog fishinglog, ActiveUser activeUser) {
        if (fishinglog.getLogRecordType() == 0) {//原始记录
            fishinglog.setModifyLogId(fishinglog.getId());
            fishinglog.setLogRecordType(1);
            String oid = fishinglog.getId();
            fishinglog.setId(null);
            fishinglog.setUpdateTime(new Date());
            fishinglog.setIsSelect(0);//是否查询显示标识（0为查询，1为不查询）
            fishinglog.setStartTime(dateReduction8(fishinglog.getStartTime()));
            fishinglog.setEndTime(dateReduction8(fishinglog.getEndTime()));
            fishinglog.setLogDate(dateReduction8(fishinglog.getLogDate()));
            fishinglogMapper.insert(fishinglog);
            fishinglogMapper.updateForModifyLogId(oid, fishinglog.getId());
            fishinglogMapper.updateForIsSelect(oid, 1);//是否查询显示标识（0为查询，1为不查询）
            if (fishinglog.getCatches().size() > 0) {
                for (Catch c : fishinglog.getCatches()) {
                    c.setLogId(fishinglog.getId());
                    catchMapper.insert(c);
                }
            }
            if (fishinglog.getDiscards().size() > 0) {
                for (Discard d : fishinglog.getDiscards()) {
                    d.setLogId(fishinglog.getId());
                    discardMapper.insert(d);
                }
            }
        } else {
            catchMapper.deleteByLogId(fishinglog.getId());
            discardMapper.deleteByLogId(fishinglog.getId());
            fishinglog.setUpdateTime(new Date());
            fishinglog.setStartTime(dateReduction8(fishinglog.getStartTime()));
            fishinglog.setEndTime(dateReduction8(fishinglog.getEndTime()));
            fishinglog.setLogDate(dateReduction8(fishinglog.getLogDate()));
            fishinglogMapper.updateById(fishinglog);
            if (fishinglog.getCatches().size() > 0) {
                for (Catch c : fishinglog.getCatches()) {
                    c.setLogId(fishinglog.getId());
                    catchMapper.insert(c);
                }
            }
            if (fishinglog.getDiscards().size() > 0) {
                for (Discard d : fishinglog.getDiscards()) {
                    d.setLogId(fishinglog.getId());
                    discardMapper.insert(d);
                }
            }
        }
        return true;
    }

    /**
     * 时间减8小时
     */
    public Date dateReduction8(Date date) {
        if (date != null) {
            return new Date(date.getTime() - 1000 * 60 * 60 * 8L);
        } else {
            return null;
        }
    }


}

