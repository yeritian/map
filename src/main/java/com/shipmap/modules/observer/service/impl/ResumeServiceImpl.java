package com.shipmap.modules.observer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.observer.dao.ResumeMapper;
import com.shipmap.modules.observer.model.Resume;
import com.shipmap.modules.observer.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-04-09
 */
@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements ResumeService {

    @Autowired
    private ResumeMapper resumeMapper;

    /**
     * 分页查询
     *
     * @param resumePage 分页对象
     * @param column     表的列名
     * @param value      匹配的值
     */
    @Override
    public Page<Resume> list(Page<Resume> resumePage, String column, String value) {
        Wrapper<Resume> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        resumePage.setRecords(resumeMapper.selectPage(resumePage, wrapper));
        return resumePage;
    }

    /**
     * 添加
     *
     * @param resume     添加对象
     * @param activeUser 当前登录用户
     */
    @Override
    public boolean add(Resume resume, ActiveUser activeUser) {
        return resumeMapper.insert(resume) > 0;
    }

    /**
     * 更新
     *
     * @param resume     修改对象
     * @param activeUser 当前登录用户
     */
    @Override
    public boolean update(Resume resume, ActiveUser activeUser) {
        return resumeMapper.updateById(resume) > 0;
    }

    /**
     * 根据id查询
     *
     * @param resumeId
     */
    @Override
    public Resume getById(String resumeId) {
        return resumeMapper.selectById(resumeId);
    }

    /**
     * 根据id删除
     *
     * @param resumeId
     */
    @Override
    public boolean delete(String resumeId) {
        return resumeMapper.deleteById(resumeId) > 0;
    }

}

