package com.shipmap.modules.base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.JSONUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.base.dao.PortMapper;
import com.shipmap.modules.base.model.Port;
import com.shipmap.modules.base.service.PortDetailService;
import com.shipmap.modules.base.service.PortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-03-27
 */
@Service
public class PortServiceImpl extends ServiceImpl<PortMapper, Port> implements PortService {

    @Autowired
    private PortMapper portMapper;
    @Autowired
    private PortDetailService portDetailService;

    /**
     * 分页查询
     *
     * @param portPage 分页对象
     * @param column   表的列名
     * @param value    匹配的值
     */
    @Override
    public Page<Port> list(Page<Port> portPage, String column, String value) {
        Wrapper<Port> wrapper = new EntityWrapper<>();
//        wrapper.orderBy("updatetime",false);
        wrapper.orderBy("updatetime", false);  //wrapper.orderBy("upload_time", false);
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        portPage.setRecords(baseMapper.selectPage(portPage, wrapper));
        return portPage;
    }

    /**
     * 添加
     *
     * @param port 添加对象
     */
    @Override
    public boolean add(Port port, ActiveUser user) {
        port.setId(user.getUserId());
        return portMapper.insert(port) > 0;
    }


    /**
     * 更新
     *
     * @param port       修改对象
     * @param activeUser 当前登录用户
     */
    @Override
    public boolean update(Port port, ActiveUser activeUser) {
        return portMapper.updateById(port) > 0;
    }

    /**
     * 根据id查询
     *
     * @param portId
     */
    @Override
    public Port getById(String portId) {
        return portMapper.selectById(portId);
    }

    /**
     * 根据id删除
     *
     * @param portId
     */
    @Override
    public boolean delete(String portId) {
        return portMapper.deleteById(portId) > 0;
    }

    @Override
    public List<Port> selectAll(String id) {
        return portMapper.selectAll(id);
    }

    @Override
    public boolean insertEntity(Port port) {
        return portMapper.insertEntity(port);
    }

    @Override
    public boolean deleteByPortId(String id) {

        return portMapper.deleteByPortId(id) > 0;
//        return false;
    }

    //批量删除
    @Override
    public boolean batchDelete(String id) {
        boolean res = false;
        List<String> list = JSONUtil.parseArray(id);
        for (int i = 0; i < list.size(); i++) {
            res = portMapper.deleteById(list.get(i)) > 0;
        }
        return res;
    }


}

