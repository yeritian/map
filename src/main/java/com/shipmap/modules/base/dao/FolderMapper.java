package com.shipmap.modules.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shipmap.modules.base.model.Folder;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-03-12
 */
public interface FolderMapper extends BaseMapper<Folder> {


    List<Folder> selectAll();

    Folder selectFolderById(String nodeId);

    Folder selectByFolder();

    Integer findFolderName(String parentId, String context);

    void deletenodeid(String nodeId);

    List<Folder> selectAllBynid(String nodeId);

//    Folder findpidbynid(String nodeId);
}
