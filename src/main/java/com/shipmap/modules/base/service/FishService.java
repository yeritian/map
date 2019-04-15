package com.shipmap.modules.base.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.modules.base.model.Fish;
import com.shipmap.modules.base.model.NameVO;
import com.shipmap.modules.system.model.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2018-12-19
 */

public interface FishService extends IService<Fish> {

    List<Fish> list(Page<Fish> page, String searchKey, String searchValue);

    Fish getById(String fishId);

    boolean add(Fish fish, User u) throws BusinessException;

    boolean update(Fish fish, User u);

    boolean delete(String fishId);

    List<NameVO> listAll();

    List<NameVO> listRemain(String id);
}
