package com.shipmap.modules.observer.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.ResultCode;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.observer.model.Resume;
import com.shipmap.modules.observer.service.ResumeService;
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
 * @since 2019-04-09
 */
@Api(value = "Resume相关的接口", tags = "user")
@RestController
@RequestMapping("/observer/resume")
public class ResumeController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(ResumeController.class);

    @Autowired
    private ResumeService resumeService;

    /**
     * 查询Resume列表
     */

    @ApiOperation(value = "查询所有Resume", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "searchKey", value = "筛选条件字段", dataType = "String"),
            @ApiImplicitParam(name = "searchValue", value = "筛选条件关键字", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @GetMapping()
    public Result list(Integer page, Integer limit, String searchKey, String searchValue) {
        if (page == null) {
            page = 0;
            limit = 0;
        }
        if (StringUtil.isBlank(searchValue)) {
            searchKey = null;
        }
        Page<Resume> resumePage = resumeService.list(new Page<Resume>(page, limit), searchKey, searchValue);
        return PageResult.toResult(resumePage.getRecords(), resumePage.getTotal());
    }

    /**
     * 添加Resume
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id，多个用','分割", required = true, dataType = "String"),
     **/
    @ApiOperation(value = "添加Resume", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resume", value = "Resume信息", required = true, dataType = "Resume"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @PostMapping()
    public Result add(Resume resume, HttpServletRequest request) {
        boolean result = resumeService.add(resume, this.getActiveUser(request));
        if (result) {
            //添加成功
            return Result.success();
        } else {
            //添加失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 修改Resume
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id", required = true, dataType = "String"),
     **/
    @ApiOperation(value = "修改Resume", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Resume", value = "Resume信息", required = true, dataType = "Resume"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @PutMapping()
    public Result update(Resume resume, HttpServletRequest request) {
        boolean result = resumeService.update(resume, this.getActiveUser(request));
        if (result) {
            //修改成功
            return Result.success();
        } else {
            //修改失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 删除Resume
     **/
    @ApiOperation(value = "修改Resume", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Resume", value = "Resume信息", required = true, dataType = "Resume"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        boolean result = resumeService.delete(id);
        if (result) {
            //删除成功
            return Result.success();
        } else {
            //删除失败
            return Result.failure(ResultCode.ERROR);
        }
    }
}