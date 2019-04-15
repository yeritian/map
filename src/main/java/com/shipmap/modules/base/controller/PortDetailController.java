package com.shipmap.modules.base.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.ResultCode;
import com.shipmap.framework.service.exception.RoleNotFoundException;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.base.model.PortDetail;
import com.shipmap.modules.base.service.PortDetailService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ${author}
 * @since 2019-03-30
 */
@Api(value = "PortDetail相关的接口", tags = "user")
@RestController
@RequestMapping("/base/portDetail")
public class PortDetailController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(PortDetailController.class);

    @Autowired
    private PortDetailService portDetailService;

    /**
     * 查询PortDetail列表
     */
    @GetMapping()
    public Result list(Integer page, Integer limit, String searchKey, String searchValue, String id) {
        if (page == null) {
            page = 0;
            limit = 0;
        }
        if (StringUtil.isBlank(searchValue)) {
            searchKey = null;
        }
        Page<PortDetail> portDetailPage = portDetailService.list(new Page<PortDetail>(page, limit), searchKey, searchValue, id);
        return PageResult.toResult(portDetailPage.getRecords(), portDetailPage.getTotal());
    }

    /**
     * 添加PortDetail
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id，多个用','分割", required = true, dataType = "String"),
     **/
    @PostMapping()
    public Result add(PortDetail portDetail, String portid) {
        System.out.println("portid==================portid================" + portid);
        //非空判断
        if (portDetail == null) {

            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        //执行新增
        portDetail.setPortid(portid);

        boolean result = portDetailService.insert(portDetail);
        if (result) {
            //新增成功
            return Result.success();
        }
        return Result.failure(ResultCode.ERROR);
    }

    /**
     * 修改PortDetail
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id", required = true, dataType = "String"),
     **/
    @PutMapping()
    public Result update(PortDetail portDetail, HttpServletRequest request) {
        boolean result = portDetailService.update(portDetail, this.getActiveUser(request));
        if (result) {
            //修改成功
            return Result.success();
        } else {
            //修改失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 删除PortDetail
     **/
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        //非空判断
        if (StringUtil.isBlank(id)) {
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        try {
            //执行删除
            boolean result = portDetailService.delete(id);

            if (result) {
                //删除成功
                return Result.success();
            } else {
                return Result.failure(ResultCode.ERROR);
            }
        } catch (RoleNotFoundException e) {
            //删除的角色id不存在
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }
    }
}