package com.shipmap.modules.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.ResultCode;
import com.shipmap.framework.dtree.Status;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.DateUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.framework.utils.UUIDUtil;
import com.shipmap.modules.base.model.Folder;
import com.shipmap.modules.base.model.FolderResult;
import com.shipmap.modules.base.model.Upload;
import com.shipmap.modules.base.service.UploadService;
import com.shipmap.modules.system.model.UserRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author ${author}
 * @since 2019-03-12
 */
@Api(value = "Upload相关的接口", tags = "user")
@RestController
@RequestMapping("/upload")
public class UploadController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(UploadController.class);
    //    public final static String UPLOAD_FILE_PATH = "D:\\uploadFile\\";
//    public final static String UPLOAD_FILE_PATH = "C:\\tempFileBase\\";
    ;
    //    public final static String UPLOAD_FILE_PATH = "/usr/local/application/map/";
    @Autowired
    public UploadService uploadService;


    /**
     * 查询Upload列表
     */

    @ApiOperation(value = "查询所有Upload", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "searchKey", value = "筛选条件字段", dataType = "String"),
            @ApiImplicitParam(name = "searchValue", value = "筛选条件关键字", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @GetMapping("/list")
    public Result list(Integer page, Integer limit, String searchKey, String searchValue, String selectKey) {
//        System.out.println("list+++++++++++++777777777+++++++++++++++=================" + selectKey);
        if (page == null) {
            page = 0;
            limit = 0;
        }
        if (StringUtil.isBlank(searchValue)) {
            searchKey = null;
        }
//        Page<Upload> uploadPage = new Page<>(page, limit);
//        List<Upload> list = uploadService.list(uploadPage, searchKey, searchValue);
        Page<Upload> list = uploadService.list(new Page<Upload>(page, limit), searchKey, searchValue);
        return PageResult.toResult(list.getRecords(), list.getTotal());
    }

    /**
     * 添加Upload
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id，多个用','分割", required = true, dataType = "String"),
     **/

    @PostMapping()
    public Result add(Upload upload, HttpServletRequest req, HttpServletResponse response, String selectKey) {


        String fileName = req.getParameter("fileName");
        String fileSize = req.getParameter("fileSize");
        String fileType = req.getParameter("fileType");
        String serverName = req.getParameter("serverName");
        System.out.println("serverName===========serverName===" + serverName);
        System.out.println("fileName===========fileName===" + fileName);
//        System.out.println("nid===selectKey========selectKey======selectKey===selectKey==111111111111:" + selectKey);
        /*if (StringUtil.isNotBlank(nid)){
            upload.setNodeid(nid);
        }*/
        upload.setNodeid(selectKey);
        upload.setFilename(fileName);
        upload.setFilesize(fileSize);
        upload.setFiletype(fileType);

//        upload.setPath(UPLOAD_FILE_PATH + fileName + "." + fileType);
        upload.setPath(serverName);

        if (uploadService.add(upload, getActiveUser(req))) {
//            return JsonResult.ok("添加成功");
            return Result.success();
        } else {
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 修改Upload
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id", required = true, dataType = "String"),
     **/

    @PutMapping()
    public Result update(Upload upload, HttpServletRequest req, HttpServletResponse response) {
        String id = req.getParameter("id");
        Upload uploadById = uploadService.selectUploadById(id);
        //判断当前登录用户
      /*  if (uploadById.getOwer().equals(getActiveUser(req).getUserId())){//判断当前登录用户id和其他人的用户（或者是自己的）id 是否相等
            System.out.println("=判断当前登录用户id和其他人的用户（或者是自己的）id 是否相等=");
        }*/
//1.查询出sys_user_role的role_id （可能有多条）然后与sys_role表的管理员的id比较 来确定是不是管理员
        String SysId = uploadService.querySysId();
        System.out.println("getActiveUser(req).getUserId()==========================" + getActiveUser(req).getUserId());
        List<UserRole> RoleIdlist = uploadService.querySysRoleId(getActiveUser(req).getUserId());
        for (int i = 0; i < RoleIdlist.size(); i++) {
            if (SysId.equals(RoleIdlist.get(i).getRoleId()) || uploadById.getOwer().equals(getActiveUser(req).getUserId())) {//判断当前登录用户id和管理员用户id   --->相等则是管理员
//                System.out.println("=============update===相等则是管理员或者是自己能修改自己的======equals=============");
               /* if (!upload.getFilename().equals(uploadById.getFilename())) {
                    if (StringUtil.isNotBlank(uploadById.getPath())) {
                        //上传的文件重命名
                        File oldname = new File(uploadById.getPath());
                        if (oldname.exists()) {
                            File newName = new File(UPLOAD_FILE_PATH + upload.getFilename() + "." + uploadById.getFiletype());
                            oldname.renameTo(newName);
                        }
                    }
//                    upload.setPath(UPLOAD_FILE_PATH + upload.getFilename() + "." + uploadById.getFiletype());
                }*/


                upload.setDownloads(uploadById.getDownloads());
                if (uploadService.update(upload, getActiveUser(req))) {
//              //修改成功
                    return Result.success("prem", 0);
                } else {
//            return Result.failure(ResultCode.ERROR);
                    //修改失败
                    return Result.failure(ResultCode.ERROR);
                }
            } /*else {
                //提示用户无权限
//                System.out.println("提示用户无权限===========");
                return Result.success("prem", 1);
//                    return Result.failure(ResultCode.ERROR);
//                Result.failure()
            }*/
        }

        /*if(){

        }*/

//    if (SysId.equals())



        /*if (!upload.getFilename().equals(uploadById.getFilename())) {
            if (StringUtil.isNotBlank(uploadById.getPath())) {
                //上传的文件重命名
                File oldname = new File(uploadById.getPath());
                if (oldname.exists()) {
                    File newName = new File(UPLOAD_FILE_PATH + upload.getFilename() + "." + uploadById.getFiletype());
                    oldname.renameTo(newName);
                }
            }
            upload.setPath(UPLOAD_FILE_PATH + upload.getFilename() + "." + uploadById.getFiletype());
        }
        upload.setDownloads(uploadById.getDownloads());
        if (uploadService.update(upload, getActiveUser(req))) {
//              //修改成功
                return Result.success();
        } else {
//            return Result.failure(ResultCode.ERROR);
            //修改失败
            return Result.failure(ResultCode.ERROR);
        }*/
//        System.out.println("结束循环===============");
        return Result.success("prem", 1);

    }

    /**
     * 删除Upload
     *
     * @param id
     * @return
     */

    @DeleteMapping("/delete")
    public Result delete(String id, HttpServletRequest req) {
        Upload uploadById = uploadService.selectUploadById(id);
        String SysId = uploadService.querySysId();
//        System.out.println("getActiveUser(req).getUserId()=========================="+getActiveUser(req).getUserId());
        List<UserRole> RoleIdlist = uploadService.querySysRoleId(getActiveUser(req).getUserId());
        for (int i = 0; i < RoleIdlist.size(); i++) {
            if (SysId.equals(RoleIdlist.get(i).getRoleId()) || uploadById.getOwer().equals(getActiveUser(req).getUserId())) {//判断当前登录用户id和管理员用户id   --->相等则是管理员
//                System.out.println("=============update===相等则是管理员或者是自己能修改自己的======equals=============");
                if (StringUtil.isNotBlank(uploadById.getPath())) {
                    //判断是否存在文件，有就删除该文件
                    uploadService.getStorageClient().deleteFile(uploadById.getPath().split("###")[0], uploadById.getPath().split("###")[1]);
//                    File file = new File(uploadById.getPath());
//                        System.out.println("文件有路径====================");
              /*      File file = new File(uploadById.getPath().split("###")[0], uploadById.getPath().split("###")[1]);
                    if (file.exists()) {
                        file.delete();
                        System.out.println("文件有路径====================");
                    }*/
                }
                if (uploadService.delete(id)) {
                    return Result.success("dele", 0);
                } else {
                    return Result.failure(ResultCode.ERROR);
                }

            } /*else {
                //提示用户无权限
//                System.out.println("提示用户无权限===========");
                return Result.success("dele", 1);
//                    return Result.failure(ResultCode.ERROR);
//                Result.failure()
            }*/
        }
        return Result.success("dele", 1);
    }


    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file, Upload upload, HttpServletRequest request, String selectKey) {
//        return uploadService.uploadFile(file, upload, request, selectKey);
//        Object obj = request.getAttribute(JwtTokenUtil.TOKEN_HEADER);
//        System.out.println("---------uploadFile----uploadFile-------obj--------------" + obj);

        if (!file.isEmpty()) {
            Map<String, String> resObj = new HashMap<>();
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
            String str = "";
//                        String path= File.separator+"."+fileType;
//            File tarfile = new File(UPLOAD_FILE_PATH + newName);
            try {
                if (filesize < 1024) {
                    f = filesize + "B";
                } else {
                    f = filesize / 1024 + "KB";
                }
                //如果目录不存在，自动创建文件夹
               /* if (!tarfile.getParentFile().exists()) {
                    tarfile.getParentFile().mkdir();
                }*/
//                file.transferTo(tarfile);
                //将文件上传至文件服务器
                InputStream inputStream = file.getInputStream();
//                StorePath storePath = uploadService.getStorageClient().uploadFile(IOUtils.toByteArray(new FileInputStream(tarfile)), FilenameUtils.getExtension(file.getName()));
                StorePath storePath = uploadService.getStorageClient().uploadFile(IOUtils.toByteArray(inputStream), FilenameUtils.getExtension(file.getOriginalFilename()));
                String dfsPath = storePath.getPath();
                String dfsGroup = storePath.getGroup();
                str = dfsGroup + "###" + dfsPath;
//                str = uploadService.uploadMessageObj(UPLOAD_FILE_PATH + newName);


            } catch (Exception e) {
                resObj.put("msg", "error");
                resObj.put("code", "1");
                return JSONObject.toJSONString(resObj);
            }
            resObj.put("fileName", firstname + times);
            resObj.put("serverName", str);
//            resObj.put("fileName", newName);
            resObj.put("fileSize", f);
            resObj.put("fileType", fileType);
            resObj.put("msg", "ok");
            resObj.put("code", "0");
            return JSONObject.toJSONString(resObj);
        } else {
            return null;
        }
    }


    /**
     * 文件下载权限验证
     *
     * @param id
     * @return
     */

    @RequestMapping(value = "/dloadAuth")
    public Result dloadAuth(String id, HttpServletRequest req) {
        Upload uploadById = uploadService.selectUploadById(id);
        String SysId = uploadService.querySysId();
//        System.out.println("getActiveUser(req).getUserId()=========================="+getActiveUser(req).getUserId());
        List<UserRole> RoleIdlist = uploadService.querySysRoleId(getActiveUser(req).getUserId());
        for (int i = 0; i < RoleIdlist.size(); i++) {
            if (SysId.equals(RoleIdlist.get(i).getRoleId()) || uploadById.getOwer().equals(getActiveUser(req).getUserId())) {//判断当前登录用户id和管理员用户id   --->相等则是管理员
//                System.out.println("=============update===相等则是管理员或者是自己能修改自己的======equals=============");
                return Result.success("dload", 0);

            } /*else {
                //提示用户无权限
//                System.out.println("提示用户无权限===========");
                return Result.success("dload", 1);
            }*/
        }
        return Result.success("dload", 1);
    }


    /**
     * 文件下载
     *
     * @return
     */
    @GetMapping(value = "/download")
   /* public void download(HttpServletRequest request, HttpServletResponse response, String id) {
//        return uploadService.download(request, response);
//        String id = request.getParameter("id");
//        System.out.println("id=======download==========="+id);
//        System.out.println("=======id==========id======+_---------===id====" + id);
//        Upload uploadById = uploadService.selectUploadById(id);
        Upload uploadById = uploadService.selectUploadById(id);

    *//*    if (uploadById.getOwer().equals("admin")){
            System.out.println("相等则有权限，否则没有权限");*//*
        String filepath = uploadById.getPath();//路径
//        System.out.println("filepath========download=============="+filepath);
        String filename = uploadById.getFilename()+"."+uploadById.getFiletype();
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
            buffer = uploadService.getStorageClient().downloadFile(filepath.split("###")[0], filepath.split("###")[1]);
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                //下载次数
                int nums = uploadById.getDownloads() + 1;
                uploadById.setDownloads(nums);
                uploadService.update(uploadById, getActiveUser(request));
//                uploadMapper.updateById(uploadById);
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
        } *//*else {
            return Result.success("msg","用户没上传文件，下载失败");
//            return "用户没上传文件，下载失败";
        }
//                return api.returnJson(2,"fail"+realPath+fileName);
//        return "下载成功！";
        return Result.success("msg","下载成功");*//*
     *//*}else{
            System.out.println("没有权限");
        }*//*
    }
*/

    public void download(String id, HttpServletResponse resp, HttpServletRequest request) {
        Upload uploadById = uploadService.selectUploadById(id);
//        Long id = NumberUtil.trans62ToLong(index);
//        Message message = messageMapper.selectById(id);
        String filepath = uploadById.getPath();
        if (filepath != null) {
            try {
                byte[] fileBytes = null;
                fileBytes = uploadService.getStorageClient().downloadFile(filepath.split("###")[0], filepath.split("###")[1]);
//                fileBytes = this.getStorageClient().downloadFile(path.split("###")[0], path.split("###")[1]);
                resp.setContentType("application/force-download");
//                String filename = uploadById.getFilename()+"."+uploadById.getFiletype();
                String fileName = uploadById.getFilename() + filepath.substring(filepath.lastIndexOf('.'), filepath.length());
                resp.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
//                resp.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
                //下载次数
                int nums = uploadById.getDownloads() + 1;
                uploadById.setDownloads(nums);
                uploadService.update(uploadById, getActiveUser(request));
//                uploadMapper.updateById(uploadById);
//                this.update(uploadById, getActiveUser(request));
                Result.success("nums", nums);
                OutputStream out = resp.getOutputStream();
                out.write(fileBytes);
                out.flush();
                out.close();
//                return 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //TODO 无文件提示
//        return -1;
    }


    /**
     * 显示文件
     *
     * @return
     */

    @RequestMapping(value = "display")
//    public FolderResult display(HttpServletRequest request, HttpServletResponse response, Model model) {
    public FolderResult display() {
//                String addNodeName = request.getParameter("addNodeName");
//                System.out.println("=====addNodeName=======addNodeName==============++++++++++::"+addNodeName);
        FolderResult fr = new FolderResult();
        fr.setData(uploadService.listAll());
        Status s = new Status();
        s.setCode(200);
        fr.setStatus(s);
        return fr;
    }

    /**
     * 添加节点
     *
     * @param context
     * @param parentId
     * @param nodeId
     * @param context
     * @return
     */
    @RequestMapping(value = "addMenu")
    public Result addMenu(HttpServletRequest request, String parentId, String nodeId, String context) {
        String SysId = uploadService.querySysId();
//        System.out.println("getActiveUser(req).getUserId()=========================="+getActiveUser(req).getUserId());
        List<UserRole> RoleIdlist = uploadService.querySysRoleId(getActiveUser(request).getUserId());
        for (int i = 0; i < RoleIdlist.size(); i++) {
            if (SysId.equals(RoleIdlist.get(i).getRoleId())) {//判断当前登录用户id和管理员用户id   --->相等则是管理员

                Folder f = new Folder();
                f.setId(nodeId + UUIDUtil.randomUUID8());
//            f.setId(nodeId);
                f.setParentId(parentId);
                f.setTitle(context);
//        System.out.println("============addMenu========getUserId=============="+getActiveUser(request).getUserId());
//        System.out.println("===========addMenu================getUsername======="+getActiveUser(request).getUsername());
                uploadService.addFolder(f, getActiveUser(request));
                return Result.success("id", f.getId());
            } else {
//                String permission="0";
                return Result.success("permission", 0);

            }
        }


      /*  if(getActiveUser(request).getUsername().equals("admin")){
            Folder f = new Folder();
            f.setId(nodeId + UUIDUtil.randomUUID8());
//            f.setId(nodeId);
            f.setParentId(parentId);
            f.setTitle(context);
//        System.out.println("============addMenu========getUserId=============="+getActiveUser(request).getUserId());
//        System.out.println("===========addMenu================getUsername======="+getActiveUser(request).getUsername());
            uploadService.addFolder(f, getActiveUser(request));
            return Result.success("id", f.getId());
        }else{
            String permission="0";
            return Result.success("permission",permission);
        }*/
        return Result.success("permission", 0);
    }


    /**
     * 修改节点
     *
     * @param context
     * @param nodeId
     * @return
     */
    @RequestMapping(value = "updateMenu")
    public Result updateMenu(HttpServletRequest request, String context, String nodeId, String parentId) {
        Folder folder = uploadService.selectFolderById(nodeId);
//        System.out.println("parentId========modifyMenu=========-modifyMenu--------------+" + parentId);
        folder.setTitle(context);
        //权限
//        if(getActiveUser(request).getUsername().equals("admin")){
        String SysId = uploadService.querySysId();
//        System.out.println("getActiveUser(req).getUserId()=========================="+getActiveUser(req).getUserId());
        List<UserRole> RoleIdlist = uploadService.querySysRoleId(getActiveUser(request).getUserId());
        for (int i = 0; i < RoleIdlist.size(); i++) {
            if (SysId.equals(RoleIdlist.get(i).getRoleId())) {//判断当前登录用户id和管理员用户id   --->相等则是管理员
                if (uploadService.updateFolder(folder, getActiveUser(request))) {
//            return JsonResult.ok("修改成功！");
//                    return Result.success();
                    return Result.success("permission", 1);
                } else {
                    return Result.failure(ResultCode.ERROR);
                }
            }/* else {
//                String permission = "0";
                return Result.success("permission", 0);
            }*/
        }
        return Result.success("permission", 0);
    }


    /**
     * 删除节点
     *
     * @param nodeId
     * @return
     */
    @RequestMapping(value = "deleteMenu")
    public Result deleteMenu(HttpServletRequest request, String nodeId, String parentId, String id) {
//        System.out.println("===========deleteMenu=====parentId=======parentId=====:" + parentId);
//        System.out.println("===========deleteMenu=====nodeId=======nodeId=====:" + nodeId);
        System.out.println("===========deleteMenu=====id=======id=====:" + id);
        System.out.println("nodeId=============================" + nodeId);


        if ("-1".equals(parentId) && "1".equals(nodeId)) {
            return Result.failure(ResultCode.ERROR);
//            return JsonResult.error("此文件不允许删除");
        }
        String SysId = uploadService.querySysId();
//        System.out.println("getActiveUser(req).getUserId()=========================="+getActiveUser(req).getUserId());
        List<UserRole> RoleIdlist = uploadService.querySysRoleId(getActiveUser(request).getUserId());
        for (int i = 0; i < RoleIdlist.size(); i++) {
            if (SysId.equals(RoleIdlist.get(i).getRoleId())) {//判断当前登录用户id和管理员用户id   --->相等则是管理员
//        if(getActiveUser(request).getUsername().equals("admin")){
                uploadService.deleteFolder(nodeId);
//            return JsonResult.ok("删除成功");
//                return Result.success();
                return Result.success("permission", 1);
            } /*else {
//                String permission = "0";
                return Result.success("permission", 0);
            }*/
        }
        return Result.success("permission", 0);
    }

    /**
     * 显示文件夹下的文件
     *
     * @param
     * @return
     */
    @RequestMapping(value = "displaydoc")
    public Result displaydoc(Integer page, Integer limit, String searchKey, String searchValue, String selectKey, ActiveUser user) {
//        System.out.println("nid===================");
        System.out.println("=============displaydoc===========page=========" + page);
        System.out.println("=============displaydoc===========limit=========" + limit);

        if (page == null) {
            page = 0;
            limit = 0;
        }
   /*     if (!"1".equals(selectKey) && page>1){
            page = 1;
//            limit = 10;
        }*/
        if (StringUtil.isBlank(searchValue)) {
            searchKey = null;
        }
//        System.out.println("nid======displaydoc=======displaydoc9999999999999=========" + selectKey);
//        Page<Upload> uploadPage = new Page<>(page, limit);
        if ("1".equals(selectKey)) {
//            return null;
//            return uploadService.list(page, limit, searchKey, searchValue);
         /*    Upload upload=new Upload();
              upload.setSubmitter(user.getUsername());*/
            Page<Upload> list = uploadService.list(new Page<Upload>(page, limit), searchKey, searchValue);
            return PageResult.toResult(list.getRecords(), list.getTotal());
        } else {

            Page<Upload> uploadPage = uploadService.filelist(new Page<Upload>(page, limit), searchKey, searchValue, selectKey);
//        List<Upload> list = uploadService.filelist(uploadPage, searchKey, searchValue, selectKey);
            return PageResult.toResult(uploadPage.getRecords(), uploadPage.getTotal());
        }
    }


    //搜索
/*    @GetMapping("sect")
    public Result selectByName(@RequestParam("sect") String sect) {
        System.out.println("--------------------------------------------");
        Map<String, Object> map = new HashMap<>();
        map.put("key", sect);
        List<Upload> list = uploadService.selectByName(map);
        return PageResult.toResult(list);
    }*/

    /**
     * 设置提交者和年份
     *
     * @param
     * @return
     */
    @RequestMapping(value = "disYearName")
    public Result disYearName(ActiveUser user, HttpServletRequest request) {
        String Current = DateUtil.getCurrentYear() + "|" + getActiveUser(request).getUsername();
        return Result.success("Current", Current);
    }


}


