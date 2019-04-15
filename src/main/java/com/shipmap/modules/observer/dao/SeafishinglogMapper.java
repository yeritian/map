package com.shipmap.modules.observer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.modules.observer.model.Seafishinglog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-03-28
 */
public interface SeafishinglogMapper extends BaseMapper<Seafishinglog> {


    /*自增*/
    int selectFishIdSeq();

    /*查询渔捞记录*/
    List<Seafishinglog> selectSeafishlog(Page<Seafishinglog> page, @Param("ew") Wrapper<Seafishinglog> wrapper, String id);

    /*删*/
    int deleteSeafishlog(String id);

    /*批量删除
     * * @param map seafishinglogId
     * */
    int delectAllSeafishingid(List<String> list);

    /*根据id 查询*/
    Seafishinglog selectByid(String id);

    /*统计*/
    int selectfishlogCount(String id);

}
