package com.shipmap.modules.observer.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.JSONUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.observer.dao.FishcatchMapper;
import com.shipmap.modules.observer.dao.FishtypeMapper;
import com.shipmap.modules.observer.model.Fishcatch;
import com.shipmap.modules.observer.model.Fishtype;
import com.shipmap.modules.observer.service.FishcatchService;
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
 * @since 2019-03-28
 */
@Service
public class FishcatchServiceImpl extends ServiceImpl<FishcatchMapper, Fishcatch> implements FishcatchService {

    @Autowired
    private FishcatchMapper fishcatchMapper;
    @Autowired
    private FishtypeMapper fishtypeMapper;

    /**
     * 分页查询
     *
     * @param fishcatchPage 分页对象
     * @param column        表的列名
     * @param value         匹配的值
     */
    @Override
    public Page<Fishcatch> list(Page<Fishcatch> fishcatchPage, String column, String value, String id) {
        Wrapper<Fishcatch> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        fishcatchPage.setRecords(fishcatchMapper.selectFishcatch(fishcatchPage, wrapper, id));
        return fishcatchPage;
    }

    /**
     * 添加
     *
     * @param fishcatch 添加对象
     */
    @Override
    public boolean add(Fishcatch fishcatch) {
        return fishcatchMapper.insert(fishcatch) > 0;
    }

    /**
     * 更新
     */
    @Override
    public boolean update(String obfishcatchid, String obhks, String datehook, String obnum, String obtotsum, String remark, String dateupdate, ActiveUser activeUser) {
        Fishcatch ff = new Fishcatch();
        ff.setUpdator(activeUser.getUsername());
        ff.setobfishcatchid(obfishcatchid);
        ff.setObfishcatchhooks(obhks);//钩次
        ff.setObfishcatchdatehook(datehook);//下钩日
        ff.setObbasketnum(Integer.parseInt(obnum));
        ff.setObbaskettotsum(Integer.parseInt(obtotsum));
        boolean aa = fishcatchMapper.updateById(ff) > 0;
        boolean bb = false;

        List list = JSONArray.parseArray(dateupdate);
        for (int i = 0; i < list.size(); i++) {
            String fishsciencename = JSONObject.parseObject(list.get(i).toString()).getString("fishsciencename");
            String obtotleoutput = JSONObject.parseObject(list.get(i).toString()).getString("obtotleoutput");
            String oboutput = JSONObject.parseObject(list.get(i).toString()).getString("oboutput");
            String obtrailnum = JSONObject.parseObject(list.get(i).toString()).getString("obtrailnum");
            String obtotletailnum = JSONObject.parseObject(list.get(i).toString()).getString("obtotletailnum");
            String fishtypeid = JSONObject.parseObject(list.get(i).toString()).getString("fishtypeid");

            Fishtype fty = new Fishtype();
            fty.setFishtypeid(fishtypeid);
            fty.setFishsciencename(fishsciencename);
            fty.setChinesename(fishsciencename);
            if (StringUtil.isNotBlank(obtrailnum)) {
                fty.setObtrailnum(obtrailnum);
            } else {
                fty.setObtrailnum("0");
            }
            if (StringUtil.isNotBlank(oboutput)) {
                fty.setOboutput(oboutput);
            } else {
                fty.setOboutput("0");
            }
            if (StringUtil.isNotBlank(obtotletailnum)) {
                fty.setObtotletailnum(obtotletailnum);
            } else {
                fty.setObtotletailnum("0");
            }
            if (StringUtil.isNotBlank(obtotleoutput)) {
                fty.setObtotleoutput(obtotleoutput);
            } else {
                fty.setObtotleoutput("0");
            }
            bb = fishtypeMapper.updateById(fty) > 0;
        }

        if (aa && bb) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据id查询
     *
     * @param fishcatchId
     */
    @Override
    public Fishcatch getById(String fishcatchId) {
        return fishcatchMapper.selectById(fishcatchId);
    }

    /**
     * 根据id删除
     *
     * @param fishcatchId
     */
    @Override
    public boolean delete(String fishcatchId) {
        boolean resone = fishcatchMapper.deleteById(fishcatchId) > 0;
        // boolean  res =   fishtypeMapper.deleteById(fishcatchId)>0; 逻辑删除不需要这个
        if (resone) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据id删除 -- 批量
     *
     * @param obfishcatchid
     */
    @Override
    public boolean deletAllObfishcatchid(String obfishcatchid) {
        boolean res = false;
        List<String> list = JSONUtil.parseArray(obfishcatchid);
        for (int i = 0; i < list.size(); i++) {
            res = fishcatchMapper.deleteById(list.get(i)) > 0;
        }
        return res;
    }

    /*详情ById
     * @param id
     * */
    @Override
    public List<Fishtype> selectFishCatchById(String id) {
        List<Fishtype> list = fishtypeMapper.selectAllFishType(id);
        return list;
    }


    /*添加fishcatch*/
    @Override
    public boolean addFishCatch(String observerinfoid, String obhks, String datehook, String obnum, String obtotsum, String remark, String addstr, ActiveUser activeUser) {


        Fishcatch ff = new Fishcatch();
        ff.setCreator(activeUser.getUsername());
        ff.setCreateTime(new Date());
        ff.setObserverinfoid(observerinfoid);
        ff.setObfishcatchhooks(obhks);//钩次
        ff.setObfishcatchdatehook(datehook);//下钩日
        ff.setObbasketnum(Integer.parseInt(obnum));
        ff.setObbaskettotsum(Integer.parseInt(obtotsum));
        if (remark != null && remark.equals(null)) {
            ff.setFcremark(remark);
        } else {
            ff.setFcremark("");
        }
        boolean aa = fishcatchMapper.insert(ff) > 0;
        boolean bb = false;
        List<String> strList = JSONUtil.parseArray(addstr);
        for (int i = 0; i < strList.size(); i += 5) {
            Fishtype fty = new Fishtype();
            fty.setObfishcatchid(ff.getobfishcatchid());//获取主键
            fty.setFishsciencename(strList.get(i));
            fty.setChinesename(strList.get(i));
            if (StringUtil.isNotBlank(strList.get(i + 1))) {
                fty.setObtrailnum(strList.get(i + 1));
            } else {
                fty.setObtrailnum("0");
            }
            //重量
            if (StringUtil.isNotBlank(strList.get(i + 2))) {
                fty.setOboutput(strList.get(i + 2));
            } else {
                fty.setOboutput("0");
            }
            //尾数
            if (StringUtil.isNotBlank(strList.get(i + 3))) {
                fty.setObtotletailnum(strList.get(i + 3));
            } else {
                fty.setObtotletailnum("0");
            }
            if (StringUtil.isNotBlank(strList.get(i + 4))) {
                fty.setObtotleoutput(strList.get(i + 4));
            } else {
                fty.setObtotleoutput("0");
            }
            bb = fishtypeMapper.insert(fty) > 0;
        }
        if (aa && bb) {
            return true;
        } else {
            return false;
        }

    }

    /*文件导入*/
    @Override
    public boolean InsertLayer(Fishcatch layer) {
        return fishcatchMapper.insert(layer) > 0;
    }


}

