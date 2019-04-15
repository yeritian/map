package com.shipmap.modules.observer.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.ResultCode;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.observer.model.Observerinfo;
import com.shipmap.modules.observer.model.VVO;
import com.shipmap.modules.observer.service.ObserverinfoService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ${author}
 * @since 2019-03-28
 */
@Api(value = "Observerinfo相关的接口", tags = "user")
@RestController
@RequestMapping("/observer/observerinfo")
public class ObserverinfoController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(ObserverinfoController.class);

    @Autowired
    private ObserverinfoService observerinfoService;

    /**
     * 查询Observerinfo列表
     */
    @GetMapping("list")
    public Result list(Integer page, Integer limit, String searchKey, String searchValue) {
        if (page == null) {
            page = 0;
            limit = 0;
        }
        if (StringUtil.isBlank(searchValue)) {
            searchKey = null;
        }
        Page<Observerinfo> observerinfoPage = observerinfoService.list(new Page<Observerinfo>(page, limit), searchKey, searchValue);
        return PageResult.toResult(observerinfoPage.getRecords(), observerinfoPage.getTotal());
    }

    @GetMapping("selectbufen")
    public Result selectbufen(Integer page, Integer limit, String searchKey, String searchValue) {
        if (page == null) {
            page = 0;
            limit = 0;
        }
        if (StringUtil.isBlank(searchValue)) {
            searchKey = null;
        }
        Page<VVO> vvoPage = observerinfoService.selectbufen(new Page<VVO>(page, limit), searchKey, searchValue);
        return PageResult.toResult(vvoPage.getRecords(), vvoPage.getTotal());
    }


    /**
     * 添加Observerinfo
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id，多个用','分割", required = true, dataType = "String"),
     **/
    @PostMapping()
    public Result add(Observerinfo observerinfo, HttpServletRequest request) {
        boolean result = observerinfoService.add(observerinfo, this.getActiveUser(request));
        if (result) {
            //添加成功
            return Result.success();
        } else {
            //添加失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 修改Observerinfo
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id", required = true, dataType = "String"),
     **/
    @PutMapping()
    public Result update(Observerinfo observerinfo, HttpServletRequest request) {
        boolean result = observerinfoService.update(observerinfo, this.getActiveUser(request));
        if (result) {
            //修改成功
            return Result.success();
        } else {
            //修改失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 删除Observerinfo
     **/
    @DeleteMapping("/{observerinfoid}")
    public Result delete(@PathVariable("observerinfoid") String observerinfoid) {
        boolean result = observerinfoService.delete(observerinfoid);
        if (result) {
            //删除成功
            return Result.success();
        } else {
            //删除失败
            return Result.failure(ResultCode.ERROR);
        }
    }


    /*根据id 查询*/
    @PutMapping("/{observerinfoid}")
    public Observerinfo selectByidObserve(@PathVariable("observerinfoid") String observerinfoid) {
        return observerinfoService.selectByidObserve(observerinfoid);
    }


}