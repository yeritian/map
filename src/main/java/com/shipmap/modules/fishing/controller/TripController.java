package com.shipmap.modules.fishing.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.JsonResult;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.fishing.model.Trip;
import com.shipmap.modules.fishing.model.TripCatch;
import com.shipmap.modules.fishing.service.TripService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ${author}
 * @since 2018-12-25
 */
@Api(value = "航次接口", tags = "trip")
@RestController
@RequestMapping("trip")
public class TripController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(TripController.class);

    @Autowired
    public TripService tripService;

    /**
     * 查询Trip列表
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
        Page<Trip> tripPage = tripService.list(new Page<>(page, limit), searchKey, searchValue);
        return PageResult.toResult(tripPage.getRecords(), tripPage.getTotal());
    }

    /**
     * 根据id查询Trip
     */
    @GetMapping("/selectByTripId")
    public Trip selectByTripId(String tripId) {
        if (StringUtil.isNotBlank(tripId)) {
            Trip byId = tripService.selectByTripId(tripId);
            return byId;
        } else {
            return new Trip();
        }
    }

    /**
     * 查询航次（开始时船上剩余量）
     */
    @GetMapping("/fishStart")
    public Result fishStart(String tripId) {
        List<TripCatch> fishStart = tripService.fishStart(tripId);
        return PageResult.toResult(fishStart);
    }

    /**
     * 查询航次（卸载/运输量）
     */
    @GetMapping("/fishUnloading")
    public Result fishUnloading(String tripId) {
        List<TripCatch> fishUnloading = tripService.fishUnloading(tripId);
        return PageResult.toResult(fishUnloading);
    }


    /**
     * 查询航次（卸载后船上剩余鱼量）
     */
    @GetMapping("/fishEnd")
    public Result fishEnd(String tripId) {
        List<TripCatch> fishEnd = tripService.fishEnd(tripId);
        return PageResult.toResult(fishEnd);
    }

    /**
     * 添加Trip
     **/
    @PostMapping("/add")
    public JsonResult add(Trip trip, HttpServletRequest request) {
        if (tripService.add(trip, getActiveUser(request))) {
            return JsonResult.ok("添加成功");
        } else {
            return JsonResult.error("添加失败");
        }
    }

    /**
     * 修改Trip
     **/
    @PutMapping("/update")
    public JsonResult update(Trip trip, HttpServletRequest request) {
        if (tripService.update(trip, getActiveUser(request))) {
            return JsonResult.ok("修改成功！");
        } else {
            return JsonResult.error("修改失败！");
        }
    }

    /**
     * 删除Trip
     **/
    @DeleteMapping("/delete")
    public JsonResult delete(String id) {
        if (tripService.delete(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

}