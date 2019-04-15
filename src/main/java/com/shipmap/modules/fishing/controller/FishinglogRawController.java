package com.shipmap.modules.fishing.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.JsonResult;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.fishing.model.FishinglogRaw;
import com.shipmap.modules.fishing.service.FishinglogRawService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ${author}
 * @since 2018-12-26
 */
@Api(value = "fishinglogRaw接口", tags = "fishinglogRaw")
@RestController
@RequestMapping("fishinglogRaw")
public class FishinglogRawController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(FishinglogRawController.class);

    @Autowired
    public FishinglogRawService fishinglogRawService;

    /**
     * 查询FishinglogRaw列表
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
        Page<FishinglogRaw> fishinglogRawPage = fishinglogRawService.list(new Page<>(page, limit), searchKey, searchValue);
        return PageResult.toResult(fishinglogRawPage.getRecords(), fishinglogRawPage.getTotal());
    }

    /**
     * 添加FishinglogRaw
     **/
    @PostMapping("/add")
    public JsonResult add(FishinglogRaw fishinglogRaw, HttpServletRequest request) {
        if (fishinglogRawService.add(fishinglogRaw, getActiveUser(request))) {
            return JsonResult.ok("添加成功");
        } else {
            return JsonResult.error("添加失败");
        }
    }

    /**
     * 修改FishinglogRaw
     **/
    @PutMapping("/update")
    public JsonResult update(FishinglogRaw fishinglogRaw, HttpServletRequest request) {
        if (fishinglogRawService.update(fishinglogRaw, getActiveUser(request))) {
            return JsonResult.ok("修改成功！");
        } else {
            return JsonResult.error("修改失败！");
        }
    }

    /**
     * 删除FishinglogRaw
     **/
    @DeleteMapping("/delete")
    public JsonResult delete(String id) {
        if (fishinglogRawService.delete(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

}