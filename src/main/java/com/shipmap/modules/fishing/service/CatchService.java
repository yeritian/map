package com.shipmap.modules.fishing.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.fishing.model.Catch;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-25
 */

public interface CatchService extends IService<Catch> {

    Page<Catch> list(Page<Catch> catchPage, String searchKey, String searchValue);

    Catch getById(String catchId);

    boolean add(Catch catch1, ActiveUser activeUser) throws BusinessException;

    boolean update(Catch catch1, ActiveUser activeUser);

    boolean delete(String catchId);
}
