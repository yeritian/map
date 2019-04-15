package com.shipmap.modules.fishing.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.JsonResult;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.fishing.model.Catch;
import com.shipmap.modules.fishing.service.CatchService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ${author}
 * @since 2018-12-25
 */
@Api(value = "捕获鱼接口", tags = "catch")
@RestController
@RequestMapping("catch")
public class CatchController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(CatchController.class);

    @Autowired
    public CatchService catchService;


    /**
     * 查询Catch列表
     */
    @GetMapping("/list")
    public Result list(Integer page, Integer limit, String searchKey, String searchValue) {
        if (page == null) {
            page = 0;
            limit = 0;
        }
        if (StringUtil.isBlank(searchValue)) {
            searchKey = null;
        }
        Page<Catch> catchPage = catchService.list(new Page<>(page, limit), searchKey, searchValue);
        return PageResult.toResult(catchPage.getRecords(), catchPage.getTotal());
    }

    /**
     * 添加Catch
     **/
    @PostMapping("/add")
    public JsonResult add(Catch catch1, HttpServletRequest request) {
        if (catchService.add(catch1, getActiveUser(request))) {
            return JsonResult.ok("添加成功");
        } else {
            return JsonResult.error("添加失败");
        }
    }

    /**
     * 修改Catch
     **/
    @PutMapping("/update")
    public JsonResult update(Catch catch1, HttpServletRequest request) {
        if (catchService.update(catch1, getActiveUser(request))) {
            return JsonResult.ok("修改成功！");
        } else {
            return JsonResult.error("修改失败！");
        }
    }

    /**
     * 删除Catch
     **/
    @DeleteMapping("/delete")
    public JsonResult delete(String id) {
        if (catchService.delete(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

}