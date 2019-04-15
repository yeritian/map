package com.shipmap.modules.base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.base.dao.PortDetailMapper;
import com.shipmap.modules.base.model.PortDetail;
import com.shipmap.modules.base.service.PortDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-03-30
 */
@Service
public class PortDetailServiceImpl extends ServiceImpl<PortDetailMapper, PortDetail> implements PortDetailService {

    @Autowired
    private PortDetailMapper portDetailMapper;

    /**
     * 分页查询
     *
     * @param portDetailPage 分页对象
     * @param column         表的列名
     * @param value          匹配的值
     */
    @Override
    public Page<PortDetail> list(Page<PortDetail> portDetailPage, String column, String value, String id) {
        Wrapper<PortDetail> wrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        portDetailPage.setRecords(baseMapper.selectAll(portDetailPage, wrapper, id));
        return portDetailPage;
    }

    /**
     * 添加
     *
     * @param portDetail 添加对象
     * @param activeUser 当前登录用户
     */
    @Override
    public boolean add(PortDetail portDetail, ActiveUser activeUser) {
        return portDetailMapper.insert(portDetail) > 0;
    }

    /**
     * 更新
     *
     * @param portDetail 修改对象
     * @param activeUser 当前登录用户
     */
    @Override
    public boolean update(PortDetail portDetail, ActiveUser activeUser) {
        return portDetailMapper.updateById(portDetail) > 0;
    }

    /**
     * 根据id查询
     *
     * @param portDetailId
     */
    @Override
    public PortDetail getById(String portDetailId) {
        return portDetailMapper.selectById(portDetailId);
    }

    /**
     * 根据id删除
     *
     * @param portDetailId
     */
    @Override
    public boolean delete(String portDetailId) {
        return portDetailMapper.deleteById(portDetailId) > 0;
    }

    @Override
    public int insertEntity(PortDetail portDetail) {
        return portDetailMapper.insertEntity(portDetail);
    }

    @Override
    public boolean deleteByPortId(String id) {
        return portDetailMapper.deleteByPortId(id) > 0;
    }

 /*   @Override
    public boolean deleteByPortId(String id) {
        return portDetailMapper.deleteByPortId(id)>0;
    }
*/

}

