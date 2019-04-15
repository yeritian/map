package com.shipmap.modules.fishing.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.JsonResult;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.fishing.model.Discard;
import com.shipmap.modules.fishing.service.DiscardService;
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
@Api(value = "丢弃鱼接口", tags = "discard")
@RestController
@RequestMapping("discard")
public class DiscardController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(DiscardController.class);

    @Autowired
    public DiscardService discardService;

    /**
     * 查询Discard列表
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
        Page<Discard> discardPage = discardService.list(new Page<>(page, limit), searchKey, searchValue);
        return PageResult.toResult(discardPage.getRecords(), discardPage.getTotal());
    }

    /**
     * 添加Discard
     **/
    @PostMapping("/add")
    public JsonResult add(Discard discard, HttpServletRequest request) {
        if (discardService.add(discard, getActiveUser(request))) {
            return JsonResult.ok("添加成功");
        } else {
            return JsonResult.error("添加失败");
        }
    }

    /**
     * 修改Discard
     **/
    @PutMapping("/update")
    public JsonResult update(Discard discard, HttpServletRequest request) {
        if (discardService.update(discard, getActiveUser(request))) {
            return JsonResult.ok("修改成功！");
        } else {
            return JsonResult.error("修改失败！");
        }
    }

    /**
     * 删除Discard
     **/
    @DeleteMapping("/delete")
    public JsonResult delete(String id) {
        if (discardService.delete(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

}