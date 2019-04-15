package com.shipmap.modules.observer.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.observer.dao.ResumeMapper;
import com.shipmap.modules.observer.dao.TalentsinfoMapper;
import com.shipmap.modules.observer.model.Resume;
import com.shipmap.modules.observer.model.Talentsinfo;
import com.shipmap.modules.observer.service.TalentsinfoService;
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
 * @since 2019-04-09
 */
@Service
public class TalentsinfoServiceImpl extends ServiceImpl<TalentsinfoMapper, Talentsinfo> implements TalentsinfoService {

    @Autowired
    private TalentsinfoMapper talentsinfoMapper;
    @Autowired
    private ResumeMapper resumeMapper;

    /**
     * 分页查询
     *
     * @param talentsinfoPage 分页对象
     * @param column          表的列名
     * @param value           匹配的值
     */
    @Override
    public Page<Talentsinfo> list(Page<Talentsinfo> talentsinfoPage, String column, String value) {
        Wrapper<Talentsinfo> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        talentsinfoPage.setRecords(talentsinfoMapper.selectTalentsinfo(talentsinfoPage, wrapper));
        return talentsinfoPage;
    }

    /**
     * 添加
     *
     * @param talentsinfo 添加对象
     * @param activeUser  当前登录用户
     */
    @Override
    public boolean add(Talentsinfo talentsinfo, ActiveUser activeUser) {
        talentsinfo.setCreator(activeUser.getUsername());
        return talentsinfoMapper.insert(talentsinfo) > 0;
    }

    /**
     * 更新
     *
     * @param talentsinfo 修改对象
     * @param activeUser  当前登录用户
     */
    @Override
    public boolean update(Talentsinfo talentsinfo, ActiveUser activeUser) {
        talentsinfo.setUpdataTime(new Date());
        talentsinfo.setUpdator(activeUser.getUsername());
        return talentsinfoMapper.updateById(talentsinfo) > 0;
    }

    /**
     * 根据id查询
     *
     * @param talentsinfoId
     */
    @Override
    public Talentsinfo getById(String talentsinfoId) {

        return talentsinfoMapper.selectById(talentsinfoId);
    }

    /**
     * 根据id删除
     *
     * @param talentsinfoId
     */
    @Override
    public boolean delete(String talentsinfoId) {
        return talentsinfoMapper.deleteById(talentsinfoId) > 0;
    }


    /*根据id查询履历（resume）*/
    @Override
    public List<Resume> selectTalenDetail(String talentsinfoId) {
        List<Resume> list = resumeMapper.selectTalenDetail(talentsinfoId);
        return list;
    }

    /*修改*/
    @Override
    public boolean updateTalen(String id, String name, String sex, String seamanbookno, String idnumber, String healthbookno, String dateupdate, ActiveUser activeUser) {

        boolean res = false;
        boolean restwo = false;

        try {

            Talentsinfo talentsinfo = new Talentsinfo();
            talentsinfo.setId(id);
            talentsinfo.setUpdataTime(new Date());
            talentsinfo.setUpdator(activeUser.getUsername());
            talentsinfo.setName(name);
            talentsinfo.setSex(sex);
            talentsinfo.setSeamanbookno(seamanbookno);
            talentsinfo.setIdnumber(idnumber);
            talentsinfo.setHealthbookno(healthbookno);

            res = talentsinfoMapper.updateById(talentsinfo) > 0;

            List list = JSONArray.parseArray(dateupdate);

            if (list.size() == 0) {
                restwo = true;
            } else {

                for (int i = 0; i < list.size(); i++) {

                    Resume resume = new Resume();

                    String shipname = JSONObject.parseObject(list.get(i).toString()).getString("shipname");
                    String voyage = JSONObject.parseObject(list.get(i).toString()).getString("voyage");
                    String starttime = JSONObject.parseObject(list.get(i).toString()).getString("starttime");
                    String endtime = JSONObject.parseObject(list.get(i).toString()).getString("endtime");
                    String idd = JSONObject.parseObject(list.get(i).toString()).getString("id");
                    resume.setId(idd);
                    resume.setShipname(shipname);
                    resume.setVoyage(voyage);
                    resume.setStarttime(starttime);
                    resume.setEndtime(endtime);
                    restwo = resumeMapper.updateById(resume) > 0;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (res && restwo) {
            return true;
        } else {
            return false;
        }
    }


}

