package com.shipmap.modules.observer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.observer.model.Resume;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-04-09
 */
public interface ResumeMapper extends BaseMapper<Resume> {


    /*根据talents id查询 resume*/
    List<Resume> selectTalenDetail(String talentsid);


}
