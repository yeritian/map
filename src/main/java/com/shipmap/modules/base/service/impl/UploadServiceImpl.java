package com.shipmap.modules.base.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.luhuiguo.fastdfs.conn.ConnectionManager;
import com.luhuiguo.fastdfs.conn.FdfsConnectionPool;
import com.luhuiguo.fastdfs.conn.TrackerConnectionManager;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.DefaultFastFileStorageClient;
import com.luhuiguo.fastdfs.service.DefaultTrackerClient;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import com.luhuiguo.fastdfs.service.TrackerClient;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.base.dao.FolderMapper;
import com.shipmap.modules.base.dao.UploadMapper;
import com.shipmap.modules.base.model.Folder;
import com.shipmap.modules.base.model.Upload;
import com.shipmap.modules.base.service.UploadService;
import com.shipmap.modules.system.model.UserRole;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2019-03-12
 */
@Service
public class UploadServiceImpl extends ServiceImpl<UploadMapper, Upload> implements UploadService {

    @Autowired
    private UploadMapper uploadMapper;
    @Autowired
    private FolderMapper folderMapper;

    @Override
    public String querySysId() {
        return uploadMapper.querySysId();
    }

    //通过sys_user的id查询出sys_user_role的role_id
    @Override
    public List<UserRole> querySysRoleId(String userId) {
        return uploadMapper.querySysRoleId(userId);
    }

    @Override
    public Page<Upload> list(Page<Upload> page, String column, String value) {
        Wrapper<Upload> wrapper = new EntityWrapper<>();
        wrapper.orderBy("upload_time", false);
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
//            wrapper.orderBy("upload_time","true");
        }
//        Page<Upload> uploadPage = new Page<>(pageNum, pageSize);
//        List<Upload> uploadList = baseMapper.selectPage(page, wrapper);
//        List<Upload> uploadList = baseMapper.selectPage(page, wrapper);
        page.setRecords(baseMapper.selectPage(page, wrapper));
        return page;

    }

    @Override
    public boolean add(Upload upload, ActiveUser u) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        upload.setUploadTime(sdf.format(date));
        upload.setOwer(u.getUserId());
        return uploadMapper.insert(upload) > 0;
    }

    @Override
    public boolean update(Upload upload, ActiveUser u) {

        return uploadMapper.updateById(upload) > 0;
    }

    @Override
    public Upload getById(String uploadId) {
        return uploadMapper.selectById(uploadId);
    }

    @Override
    public boolean delete(String uploadId) {
        return uploadMapper.deleteById(uploadId) > 0;
    }

    @Override
    public Upload selectUploadById(String id) {
        return uploadMapper.selectUploadById(id);
    }

    @Override
    public List<Folder> listAll() {
        List<Folder> list = folderMapper.selectAll();
        System.out.println("=====wwwwww===+++++++++++=list.size()+" + list.size());
        return buildByRecursive(list);
    }


    /**
     * 获取fastDFS存储客户端
     *
     * @return
     */

    public FastFileStorageClient getStorageClient() {
//         初始化连接池
        FdfsConnectionPool pool = new FdfsConnectionPool();
        // 设置tracker
        List<String> trackers = Arrays.asList(new String[]{"192.168.2.77:55122", "192.168.2.78:55122"});
        TrackerConnectionManager tcm = new TrackerConnectionManager(pool, trackers);
        TrackerClient trackerClient = new DefaultTrackerClient(tcm);
        // 得到storage客户端
        com.luhuiguo.fastdfs.conn.ConnectionManager cm = new ConnectionManager(pool);
        FastFileStorageClient storageClient = new DefaultFastFileStorageClient(trackerClient, cm);
//        File file= new File("E:\\tempFile\\1.jpg");
//        StorePath storePath = storageClient.uploadFile(IOUtils.toByteArray(new FileInputStream(file)), FilenameUtils.getExtension(file.getName()));
//        System.out.println(storePath.getPath());
//        System.out.println(storePath.getGroup());
        return storageClient;
    }

    public String uploadMessageObj(String filePath) {
        if (filePath != "" && new File(filePath).isFile() && new File(filePath).exists()) {
            // 定义操作的文件
            File file = new File(filePath);
            // 上传文件
            try {
                StorePath storePath = this.getStorageClient().uploadFile(IOUtils.toByteArray(new FileInputStream(file)), FilenameUtils.getExtension(file.getName()));
                String dfsPath = storePath.getPath();
                String dfsGroup = storePath.getGroup();
                String GPMesg = dfsGroup + "###" + dfsPath;
                return GPMesg;
//                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } else {
            return null;
        }
    }


    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public List<Folder> buildByRecursive(List<Folder> treeNodes) {
        List<Folder> trees = new ArrayList<Folder>();
        for (Folder treeNode : treeNodes) {
            if ("-1".equals(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public Folder findChildren(Folder treeNode, List<Folder> treeNodes) {
        for (Folder it : treeNodes) {
            if (treeNode.getId().equals(it.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<Folder>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        if (treeNode.getChildren().size() == 0) {
            treeNode.setLast(true);
        } else {
            treeNode.setLast(false);
        }
        return treeNode;
    }


   /* @Override
    public DtreeResult addMenu() {
        Wrapper<Upload> wrapper = new EntityWrapper<>();
        wrapper.orderBy("upload_time", true);
        DtreeResult dr = new DtreeResult();
        Status status = new Status();
        status.setCode(200);
        status.setMessage("");
        dr.setStatus(status);//状态
        List<NodeData> alist = new ArrayList<>();
        NodeData root = new NodeData();
        root.setId("0");
        root.setTitle("全部文件");
        alist.add(root); //保存NodeDate到list
        List<Upload> list = uploadMapper.selectList(wrapper);//根据create_time升序
        for(Upload upload:list){
            NodeData nd = new NodeData();
            nd.setId(upload.getId()+"");
//            nd.setTitle(upload.getName());
            nd.setTitle(upload.getAuthor());
            nd.setParentId("0");
            root.getChildren().add(nd); //dr.data<-- alist<--root.(childern)<--nd
            //DtreeResult.list_data<--list<--NodeData.list_childern<--NodeData
        }
        dr.setData(alist);
        return dr;
    }*/

    @Override
    public void addFolder(Folder f, ActiveUser u) {
//        f.setCreateTime(new Date());
//        f.setUpdateTime(new Date());
//        f.setOwner(loginUser.getId());
        f.setOwner(u.getUsername());
        folderMapper.insert(f);
    }

    @Override
    public boolean updateFolder(Folder f, ActiveUser u) {
//        folderMapper.update(f,loginUser);
        f.setUpdateTime(new Date());
        folderMapper.selectAll();
        return folderMapper.updateById(f) > 0;
    }

    @Override
    public Folder selectFolderById(String nodeId) {
        return folderMapper.selectFolderById(nodeId);
    }

    @Override
    public void deleteFolder(String nodeId) {

        System.out.println("getStorageClient=====" + this.getStorageClient().toString());
        System.out.println("getStorageClient=====" + this.getStorageClient());
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("id", nodeId);
        //将父文件夹下的文件夹和文件都删除
        List<Upload> up = uploadMapper.selectUploadByNodeId(nodeId);
//        System.out.println("nodeId============================="+nodeId);
//        System.out.println("===========deleteFolder======deleteFolder=========up.size()==========="+up.size());
        for (int j = 0; j < up.size(); j++) {
            if (StringUtil.isNotBlank(up.get(j).getPath())) {
//                System.out.println("getPATH================有路径===========");
                //判断是否存在文件，有就删除该文件
               /* File file = new File(up.get(j).getPath());
                if (file.exists()) {
//                    System.out.println("getPATH================文件有===========");
                    file.delete();
                }*/
                this.getStorageClient().deleteFile(up.get(j).getPath().split("###")[0], up.get(j).getPath().split("###")[1]);
            }
        }
        folderMapper.deletenodeid(nodeId);
        uploadMapper.deletenodeid(nodeId);


     /*   Upload up = uploadMapper.selectUploadByNodeId(nodeId);
        if (StringUtil.isNotBlank(up.getPath())){
            //判断是否存在文件，有就删除该文件
            File file=new File(up.getPath());
            if(file.exists()){
                file.delete();
            }
        }*/
        List<Folder> folders = folderMapper.selectAllBynid(nodeId);
        for (int i = 0; i < folders.size(); i++) {
            List<Upload> u = uploadMapper.selectUploadByNodeId(folders.get(i).getId());
//           System.out.println("folders.get(i).getId()========folders.get(i).getId()====================="+folders.get(i).getId());
//           System.out.println("===========deleteFolder======deleteFolder=========up.size()==========="+u.size());
            for (int n = 0; n < u.size(); n++) {
                if (StringUtil.isNotBlank(u.get(n).getPath())) {
//                   System.out.println("getPATH================有路径===========");
                    //判断是否存在文件，有就删除该文件
                  /* File file = new File(u.get(n).getPath());
                   if (file.exists()) {
//                       System.out.println("getPATH================文件有===========");
                       file.delete();
                   }*/
                    System.out.println("getPATH1=1===============文件有===111========");
                    this.getStorageClient().deleteFile(u.get(n).getPath().split("###")[0], u.get(n).getPath().split("###")[1]);
                    System.out.println("getPATH================文件有===========");
                }
            }
            folderMapper.deletenodeid(folders.get(i).getId());
            uploadMapper.deletenodeid(folders.get(i).getId());
       /*    Upload u = uploadMapper.selectUploadByNodeId(folders.get(i).getId());
           if (StringUtil.isNotBlank(u.getPath())){
               //判断是否存在文件，有就删除该文件
               File file=new File(u.getPath());
               if(file.exists()){
                   file.delete();
               }
           }*/
//           folders.get(i).getId();
        }
    }

    @Override
    public Folder selectByFolder() {
        return folderMapper.selectByFolder();

    }

    @Override
    public List<Upload> selectFolderIdByNodeid(String nid) {
//        return uploadMapper.selectFolderIdByNodeid(nid);
        return null;
    }

    @Override
    public Page<Upload> filelist(Page<Upload> page, String column, String value, String selectKey) {
        Wrapper<Upload> wrapper = new EntityWrapper<>();
        wrapper.orderBy("upload_time", false);
        if (StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
//        Page<Upload> uploadPage = new Page<>(page, pageSize);
//        List<Upload> uploadList = baseMapper.selectPage(uploadPage, wrapper);

//        List<Upload> uploadList = baseMapper.selectFolderIdByNodeid(page, wrapper,selectKey);
        page.setRecords(baseMapper.selectFolderIdByNodeid(page, wrapper, selectKey));
//        return uploadList;
//        List<Upload> uploadList=uploadMapper.selectFolderIdByNodeid(uploadPage,wrapper,selectKey);
        return page;
    }

    @Override
    public Integer findFolderName(String parentId, String context) {
        return folderMapper.findFolderName(parentId, context);
    }

/*    //搜索
    @Override
    public List<Upload> selectByName(Map<String, Object> map) {
        return uploadMapper.selectByName(map);
    }*/

  /*  @Override
    public String uploadFile(MultipartFile file, Upload upload, HttpServletRequest request, String selectKey) {
        return null;
    }*/

  /*  @Override
    public String download(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }*/

    /**
     *
     * 文件上传
     * @param file
     * @param upload
     * @param request
     * @param selectKey
     * @return
     */
/*    @Override
    public String uploadFile(@RequestParam("file") MultipartFile file, Upload upload, HttpServletRequest request, String selectKey) {
//        return null;
        if (!file.isEmpty()) {
            Map<String, String> resObj = new HashMap<>(MAP_SIZE);
            //上传目录地址
//                        String uploadDir = request.getSession().getServletContext().getRealPath("/") +"upload/";
            long filesize = file.getSize();
            String f = "";
            String originalFilename = file.getOriginalFilename();
            String firstname = originalFilename.substring(0, originalFilename.lastIndexOf("."));
//                        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));//.xml
            String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());//xml
            String times = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
            String newName = firstname + times + "." + fileType;//time.xml
//                        String path= File.separator+"."+fileType;
            File tarfile = new File(UPLOAD_FILE_PATH + newName);
            try {
                if (filesize < 1024) {
                    f = filesize + "B";
                } else {
                    f = filesize / 1024 + "KB";
                }
                //如果目录不存在，自动创建文件夹
                if (!tarfile.getParentFile().exists()) {
                    tarfile.getParentFile().mkdir();
                }
                file.transferTo(tarfile);

            } catch (IOException e) {
                resObj.put("msg", "error");
                resObj.put("code", "1");
                return JSONObject.toJSONString(resObj);
            }
            resObj.put("fileName", firstname+times);
//            resObj.put("fileName", newName);
            resObj.put("fileSize", f);
            resObj.put("fileType", fileType);
            resObj.put("msg", "ok");
            resObj.put("code", "0");
            return JSONObject.toJSONString(resObj);
        } else {
            return null;
        }
    }*/

    /**
     * 文件下载
     * @param request
     * @param response
     * @return
     */
   /* @Override
    public Result download(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
//        System.out.println("=======id==========id======+_---------===id====" + id);
//        Upload uploadById = uploadService.selectUploadById(id);
        Upload uploadById = uploadMapper.selectUploadById(id);
        String filepath = uploadById.getPath();//路径
        String filename = uploadById.getFilename();
//               String realname= UPLOAD_FILE_PATH+filename;//path路径字节 删除
//        System.out.println("filename++++++++++++++_-------------=-===--=:" + filename);
        File file = new File(filepath);
        if (file.exists()) { //判断文件父目录是否存在
//                if(StringUtil.isNotBlank(filepath)){
//                        response.setHeader("Content-Disposition", "attachment; fileName="+  filename +";filename*=utf-8''"+ URLEncoder.encode(filename,"UTF-8"));
            try {
                response.addHeader("Content-Disposition", "attachment;fileName=" + new String(filename.getBytes("UTF-8"), "iso-8859-1"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                //下载次数
                int nums = uploadById.getDownloads() + 1;
                uploadById.setDownloads(nums);
//                uploadService.update(uploadById, getActiveUser(request));
                uploadMapper.updateById(uploadById);
//                this.update(uploadById, getActiveUser(request));
                Result.success("nums", nums);
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return Result.success("msg","用户没上传文件，下载失败");
//            return "用户没上传文件，下载失败";
        }
//                return api.returnJson(2,"fail"+realPath+fileName);
//        return "下载成功！";
        return Result.success("msg","下载成功");

    }*/

}

