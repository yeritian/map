package com.shipmap.modules.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.modules.base.model.Upload;
import com.shipmap.modules.system.model.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2019-03-12
 */
public interface UploadMapper extends BaseMapper<Upload> {

    Integer getUploadId();

    Upload selectUploadById(String id);

    List<Upload> selectUploadByNodeId(String nodeId);

    //    List<Upload> selectFolderIdByNodeid(Page<Upload> uploadPage, Wrapper<Upload> wrapper);
    List<Upload> selectFolderIdByNodeid(Page<Upload> page, @Param("ew") Wrapper<Upload> wrapper, String selectKey);

    void deletenodeid(String nodeId);

    //查询sys_role的id
    String querySysId();

    List<UserRole> querySysRoleId(@Param("userId") String userId);

//    List<Upload> selectFolderIdByNodeid(Page<Upload> uploadPage, Wrapper<Upload> wrapper ,String nid);

//    DtreeResult listAll();

    //    -----------------------------------------------
    //搜索
//    List<Upload> selectByName(Map<String,Object> map);
}
