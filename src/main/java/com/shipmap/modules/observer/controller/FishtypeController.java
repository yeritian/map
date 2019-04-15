package com.shipmap.modules.observer.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.ResultCode;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.observer.model.Fishtype;
import com.shipmap.modules.observer.service.FishtypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ${author}
 * @since 2019-03-28
 */
@Api(value = "Fishtype相关的接口", tags = "user")
@RestController
@RequestMapping("/observer/fishtype")
public class FishtypeController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(FishtypeController.class);

    @Autowired
    private FishtypeService fishtypeService;

    /**
     * 查询Fishtype列表
     */

    @ApiOperation(value = "查询所有Fishtype", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "searchKey", value = "筛选条件字段", dataType = "String"),
            @ApiImplicitParam(name = "searchValue", value = "筛选条件关键字", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @GetMapping("list")
    public Result list(Integer page, Integer limit, String searchKey, String searchValue) {
        if (page == null) {
            page = 0;
            limit = 0;
        }
        if (StringUtil.isBlank(searchValue)) {
            searchKey = null;
        }
        Page<Fishtype> fishtypePage = fishtypeService.list(new Page<Fishtype>(page, limit), searchKey, searchValue);
        return PageResult.toResult(fishtypePage.getRecords(), fishtypePage.getTotal());
    }

    /**
     * 添加Fishtype
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id，多个用','分割", required = true, dataType = "String"),
     **/
    @ApiOperation(value = "添加Fishtype", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fishtype", value = "Fishtype信息", required = true, dataType = "Fishtype"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @PostMapping()
    public Result add(Fishtype fishtype, HttpServletRequest request) {
        boolean result = fishtypeService.add(fishtype, this.getActiveUser(request));
        if (result) {
            //添加成功
            return Result.success();
        } else {
            //添加失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 修改Fishtype
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id", required = true, dataType = "String"),
     **/
    @ApiOperation(value = "修改Fishtype", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Fishtype", value = "Fishtype信息", required = true, dataType = "Fishtype"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @PutMapping()
    public Result update(Fishtype fishtype, HttpServletRequest request) {
        boolean result = fishtypeService.update(fishtype, this.getActiveUser(request));
        if (result) {
            //修改成功
            return Result.success();
        } else {
            //修改失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 删除Fishtype
     **/
    @ApiOperation(value = "修改Fishtype", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Fishtype", value = "Fishtype信息", required = true, dataType = "Fishtype"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        boolean result = fishtypeService.delete(id);
        if (result) {
            //删除成功
            return Result.success();
        } else {
            //删除失败
            return Result.failure(ResultCode.ERROR);
        }
    }
}