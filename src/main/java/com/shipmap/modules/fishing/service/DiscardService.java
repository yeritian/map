package com.shipmap.modules.fishing.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.fishing.model.Discard;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-25
 */

public interface DiscardService extends IService<Discard> {

    Page<Discard> list(Page<Discard> discardPage, String searchKey, String searchValue);

    Discard getById(String discardId);

    boolean add(Discard discard, ActiveUser activeUser) throws BusinessException;

    boolean update(Discard discard, ActiveUser activeUser);

    boolean delete(String discardId);
}
