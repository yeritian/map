package com.shipmap.modules.observer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.observer.dao.*;
import com.shipmap.modules.observer.model.Fishcatch;
import com.shipmap.modules.observer.model.Observerhooktime;
import com.shipmap.modules.observer.model.Observerinfo;
import com.shipmap.modules.observer.model.VVO;
import com.shipmap.modules.observer.service.ObserverinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-03-28
 */
@Service
public class ObserverinfoServiceImpl extends ServiceImpl<ObserverinfoMapper, Observerinfo> implements ObserverinfoService {

    @Autowired
    private ObserverinfoMapper observerinfoMapper;
    @Autowired
    private SeafishinglogMapper seafishinglogMapper;
    @Autowired
    private FishtailMapper fishtailMapper;
    @Autowired
    private ObserverhooktimeMapper observerhooktimeMapper;
    @Autowired
    private FishcatchMapper fishcatchMapper;

    /**
     * 分页查询
     *
     * @param observerinfoPage 分页对象
     * @param column           表的列名
     * @param value            匹配的值
     */
    @Override
    public Page<Observerinfo> list(Page<Observerinfo> observerinfoPage, String column, String value) { //这个方法无用-c 到 d
        Wrapper<Observerinfo> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        observerinfoPage.setRecords(observerinfoMapper.selectObserverInfo(observerinfoPage, wrapper));
        return observerinfoPage;
    }


    @Override
    public Page<VVO> selectbufen(Page<VVO> voopage, String column, String value) {
        Wrapper<Observerinfo> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        List<VVO> allDataList = new ArrayList<>();

        List<Observerinfo> obsinfo = observerinfoMapper.selectbufen(voopage, wrapper);
        for (int i = 0; i < obsinfo.size(); i++) {
            VVO v = new VVO();

            List<Observerhooktime> hooktimes = observerhooktimeMapper.selectHooktimeCount(obsinfo.get(i).getObserverinfoid());//作业钩次
            int fishtailCount = fishtailMapper.seletcFishtailCount(obsinfo.get(i).getObserverinfoid());//逐尾记录
            int fishlogCount = seafishinglogMapper.selectfishlogCount(obsinfo.get(i).getObserverinfoid());//日志
            List<Fishcatch> fishcatchLsit = fishcatchMapper.selectFishcatchCount(obsinfo.get(i).getObserverinfoid()); //努力量

            v.setObserverinfoid(obsinfo.get(i).getObserverinfoid());//主键
            v.setObserverinfono(obsinfo.get(i).getObserverinfono()); //序号
            v.setObserveoceanarea(obsinfo.get(i).getObserveoceanarea()); //观察海域
            v.setObserverinfoname(obsinfo.get(i).getObserverinfoname()); //姓名
            v.setStartobservedate(obsinfo.get(i).getStartobservedate());//开始观察日期
            v.setEndobservedate(obsinfo.get(i).getEndobservedate());//结束观察日期
            v.setShipport(obsinfo.get(i).getShipport());//登船地点
            v.setShipname(obsinfo.get(i).getShipname());// 船名
            v.setShipdate(obsinfo.get(i).getShipdate());//登船时间
            v.setDestinationport(obsinfo.get(i).getDestinationport());//登陆地点（离船）
            v.setDisembarkdate(obsinfo.get(i).getDisembarkdate());//登陆日期
            v.setTrapezerange(obsinfo.get(i).getTrapezerange());//行程行程范围
            v.setCompany(obsinfo.get(i).getCompany());//公司
            v.setYears(obsinfo.get(i).getYears());//年份
            v.setObfishtype(obsinfo.get(i).getObfishtype());//类型
            v.setCounthooktimes(Integer.parseInt(hooktimes.get(0).getHooktimes()));//作业钩次
            v.setFthooktimes(String.valueOf(fishtailCount));//观察期间渔获记录
            v.setHooklog(fishlogCount);//日志
            v.setObfishcatch(Integer.parseInt(fishcatchLsit.get(0).getObfishcatchhooks()));//渔获量—捕捞努力量统计
            v.setObbasketnum(fishcatchLsit.get(0).getObbasketnum());//观察筐数obbasketnum
            v.setObbaskettotsum(fishcatchLsit.get(0).getObbaskettotsum());//总筐数obbaskettotsum
            v.setAvgsinglebaskethook(hooktimes.get(0).getSinglebaskethook());//单钩筐数HPB

            allDataList.add(v);
        }
        voopage.setRecords(allDataList);
        return voopage;
    }

    /**
     * 添加
     *
     * @param observerinfo 添加对象
     * @param activeUser   当前登录用户
     */
    @Override
    public boolean add(Observerinfo observerinfo, ActiveUser activeUser) {
        observerinfo.setCreateTime(new Date());
        observerinfo.setCreator(activeUser.getUsername());
        return observerinfoMapper.insert(observerinfo) > 0;
    }

    /**
     * 更新
     *
     * @param observerinfo 修改对象
     * @param activeUser   当前登录用户
     */
    @Override
    public boolean update(Observerinfo observerinfo, ActiveUser activeUser) {
        observerinfo.setUpdator(activeUser.getUsername());
        return observerinfoMapper.updateById(observerinfo) > 0;
    }

    /**
     * 根据id查询
     *
     * @param observerinfoId
     */
    @Override
    public Observerinfo getById(String observerinfoId) {
        return observerinfoMapper.selectById(observerinfoId);
    }

    /**
     * 根据id删除
     *
     * @param observerinfoId
     */
    @Override
    public boolean delete(String observerinfoId) {
        return observerinfoMapper.deleteById(observerinfoId) > 0;
    }

    /*根据id 查询*/
    @Override
    public Observerinfo selectByidObserve(String id) {
        return observerinfoMapper.selectByidObserve(id);
    }

}

