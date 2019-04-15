package com.shipmap.modules.fishing.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.fishing.model.FishinglogRaw;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-26
 */

public interface FishinglogRawService extends IService<FishinglogRaw> {

    Page<FishinglogRaw> list(Page<FishinglogRaw> fishinglogRawPage, String column, String searchValue);

    FishinglogRaw getById(String fishinglogRawId);

    boolean add(FishinglogRaw fishinglogRaw, ActiveUser activeUser) throws BusinessException;

    boolean update(FishinglogRaw fishinglogRaw, ActiveUser activeUser);

    boolean delete(String fishinglogRawId);
}
