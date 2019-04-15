package com.shipmap.modules.base.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import com.shipmap.framework.exception.BusinessException;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.modules.base.model.Folder;
import com.shipmap.modules.base.model.Upload;
import com.shipmap.modules.system.model.UserRole;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ${author}
 * @since 2019-03-12
 */

public interface UploadService extends IService<Upload> {


    Page<Upload> list(Page<Upload> page, String column, String value);
//        PageResult<Upload> list(int pageNum, int pageSize, String searchKey, String searchValue);

    Upload getById(String uploadId);

    boolean add(Upload upload, ActiveUser u) throws BusinessException;
//        boolean addupload(Upload upload, User u) throws BusinessException;

    boolean update(Upload upload, ActiveUser u);

    boolean delete(String uploadId);

    Upload selectUploadById(String id);

    List<Folder> listAll();

//        Map<String,Object> findTree();

//        DtreeResult addMenu();

    void addFolder(Folder f, ActiveUser u);

    boolean updateFolder(Folder f, ActiveUser u);

    Folder selectFolderById(String nodeId);

    void deleteFolder(String nodeId);

    Folder selectByFolder();

    List<Upload> selectFolderIdByNodeid(String nid);
//        List<Upload> selectFolderIdByNodeid(Page<Upload> uploadPage,String nid);

    Page<Upload> filelist(Page<Upload> page, String column, String value, String selectKey);

    Integer findFolderName(String parentId, String context);

//        String uploadFile(MultipartFile file, Upload upload, HttpServletRequest request, String selectKey);

//        String download(HttpServletRequest request, HttpServletResponse response);


    //搜索
//        List<Upload> selectByName(Map<String,Object> map);

    //查询sys_role的id
    String querySysId();

    //通过sys_user的id查询出sys_user_role的role_id
    List<UserRole> querySysRoleId(String userId);

    //上传至服务器
    public String uploadMessageObj(String filePath);

    //获取fastDFS存储客户端
    public FastFileStorageClient getStorageClient();
}
