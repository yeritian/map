package com.shipmap.modules.base.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.ResultCode;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.utils.NumberUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.base.dao.MonthlyMapper;
import com.shipmap.modules.base.dao.SpecificationMapper;
import com.shipmap.modules.base.model.Monthly;
import com.shipmap.modules.base.model.MonthlyVO;
import com.shipmap.modules.base.model.Specification;
import com.shipmap.modules.base.service.MonthlyService;
import com.shipmap.modules.base.service.SpecificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ${author}
 * @since 2019-04-02
 */
@Api(value = "Monthly相关的接口", tags = "user")
@RestController
@RequestMapping("/monthly")
public class MonthlyController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(MonthlyController.class);

    @Autowired
    private MonthlyService monthlyService;

    @Autowired
    private SpecificationService specificationService;

    @Autowired
    private MonthlyMapper monthlyMapper;

    @Autowired
    private SpecificationMapper specificationMapper;

    /**
     * 查询Monthly列表
     */

    @ApiOperation(value = "查询所有Monthly", notes = "")
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
        Page<Monthly> monthlyPage = monthlyService.list(new Page<Monthly>(page, limit), searchKey, searchValue);
        return PageResult.toResult(monthlyPage.getRecords(), monthlyPage.getTotal());
    }

    @GetMapping("/getMonthly")
    public Result getMonthly(Integer page, Integer limit, String companyName, String jobType, String jobTheArea, String province, String jobTime, String shipName) {
        if (page == null) {
            page = 0;
            limit = 0;
        }
        Page<Monthly> monthlyPage = monthlyService.getMonthly(new Page<Monthly>(page, limit), companyName, jobType, jobTheArea, province, jobTime, shipName);

        return PageResult.toResult(monthlyPage.getRecords(), monthlyPage.getTotal());
    }

    /**
     * 添加Monthly
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id，多个用','分割", required = true, dataType = "String"),
     **/
    @ApiOperation(value = "添加Monthly", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "monthly", value = "Monthly信息", required = true, dataType = "Monthly"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @PostMapping()
    public Result add(Monthly monthly, HttpServletRequest request) {
        boolean result = monthlyService.add(monthly, this.getActiveUser(request));
        if (result) {
            //添加成功
            return Result.success();
        } else {
            //添加失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 添加公司月报
     *
     * @param companyName  公司名称
     * @param jobType      作业类型
     * @param jobTheArea   作业洋区
     * @param province     所属省份
     * @param jobTime      作业时间
     * @param shipName     船名
     * @param jobNumOfDays 作业天数
     * @param startSite    开始地点
     * @param endSite      结束地点
     * @param castHookNum  投钩数
     * @param annotation   注释
     * @param elses        其他鱼类重量
     * @param jsonArr      鱼种的规格json格式
     * @return
     */
    @PostMapping("addMonthly")
    public Result addMonthly(String companyName, String elseFish, String eez, String jobType, String jobTheArea, String province, String jobTime, String shipName, String jobNumOfDays, String startSite, String endSite, String castHookNum, String annotation, String elses, String jsonArr, String shipPutIn, String shipEezTheFish, String shipNoProduction, HttpServletRequest request) {

        ActiveUser activeUser = getActiveUser(request);

        Monthly monthly = new Monthly();
        monthly.setShipPutIn(shipPutIn);
        monthly.setShipEezTheFish(shipEezTheFish);
        monthly.setShipNoProduction(shipNoProduction);

        monthly.setAnnotation(annotation);
        monthly.setCastHookNum(castHookNum);
        monthly.setCompanyName(companyName);
        monthly.setElseFish(elseFish);
        monthly.setDel(0);
        monthly.setEndSite(endSite);
        monthly.setJobNumOfDays(jobNumOfDays);
        monthly.setJobTheArea(jobTheArea);
        monthly.setJobTime(jobTime);
        monthly.setJobType(jobType);
        monthly.setProvince(province);
        monthly.setShipName(shipName);
        monthly.setStartSite(startSite);
        monthly.setEez(eez);
        monthly.setUpdator(activeUser.getUsername());
        monthly.setCreator(activeUser.getUsername());
        monthly.setElseFish(elses);

        ArrayList<Specification> arrayList = new ArrayList<>();

        Map mapTypes = JSON.parseObject(jsonArr);
        for (Object obj : mapTypes.keySet()) {
            Map fishMap = JSON.parseObject(mapTypes.get(obj).toString());
            for (Object o : fishMap.keySet()) {
                Specification specification = new Specification();
                specification.setSuperFish(fishMap.get("name").toString());
                if (!o.toString().equals("name")) {
                    Map fish = JSON.parseObject(fishMap.get(o).toString());
                    for (Object s : fish.keySet()) {
                        specification.setFishName(fish.get("name").toString());
                        specification.setMantissa(fish.get("num").toString());
                        specification.setWeight(fish.get("weight").toString());
                    }
                    arrayList.add(specification);
                }
            }
        }
        if (monthlyService.addMonthly(monthly, arrayList, activeUser)) {
            //添加成功
            return Result.success();
        } else {
            //添加失败
            return Result.failure(ResultCode.ERROR);
        }

    }

    /**
     * 修改Monthly
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id", required = true, dataType = "String"),
     **/
    @ApiOperation(value = "修改Monthly", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Monthly", value = "Monthly信息", required = true, dataType = "Monthly"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @PutMapping()
    public Result update(Monthly monthly, HttpServletRequest request) {
        boolean result = monthlyService.update(monthly, this.getActiveUser(request));
        if (result) {
            //修改成功
            return Result.success();
        } else {
            //修改失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 删除Monthly
     **/
    @ApiOperation(value = "修改Monthly", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Monthly", value = "Monthly信息", required = true, dataType = "Monthly"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        boolean result = monthlyService.delete(id);
        if (result) {
            //删除成功
            return Result.success();
        } else {
            //删除失败
            return Result.failure(ResultCode.ERROR);
        }
    }


    /**
     * 删除
     *
     * @param id
     * @return
     */
    @GetMapping("/deleteMonthly")
    public Result deleteMonthly(String id) {
        boolean result = monthlyService.delete(id);
        if (result) {
            //删除成功
            return Result.success();
        } else {
            //删除失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 详情查询
     *
     * @param monthlyId
     * @return
     */
    @GetMapping("getMonthlyVO")
    public MonthlyVO getMonthlyVO(String monthlyId) {
        return monthlyService.getMonthlyVO(monthlyId);
    }

    /**
     * 通过月报id查询鱼种类型
     *
     * @param monthlyId
     * @return
     */
    @GetMapping("getSpecification")
    public Result getSpecification(String monthlyId) {
        List<Specification> specificationList = specificationService.getSpecification(monthlyId);
        return PageResult.toResult(specificationList);
    }

    /**
     * 修改
     *
     * @param monthlyId
     * @param elseFish
     * @param companyName
     * @param eez
     * @param jobType
     * @param jobTheArea
     * @param province
     * @param jobTime
     * @param shipName
     * @param jobNumOfDays
     * @param startSite
     * @param endSite
     * @param castHookNum
     * @param annotation
     * @param elses
     * @param fishData
     * @param request
     * @return
     */
    @GetMapping("upDataMonthly")
    public Result upDataMonthly(String monthlyId, String elseFish, String companyName, String eez, String jobType, String jobTheArea, String province, String jobTime, String shipName, String jobNumOfDays, String startSite, String endSite, String castHookNum, String annotation, String elses, String shipPutIn, String shipEezTheFish, String shipNoProduction, String fishData, HttpServletRequest request) {

        ActiveUser activeUser = getActiveUser(request);

        Monthly monthly = new Monthly();
        monthly.setId(monthlyId);
        monthly.setAnnotation(annotation);
        monthly.setCastHookNum(castHookNum);
        monthly.setCompanyName(companyName);
        monthly.setDel(0);
        monthly.setEndSite(endSite);
        monthly.setJobNumOfDays(jobNumOfDays);
        monthly.setJobTheArea(jobTheArea);
        monthly.setJobTime(jobTime);
        monthly.setJobType(jobType);
        monthly.setProvince(province);
        monthly.setShipName(shipName);
        monthly.setStartSite(startSite);
        monthly.setEez(eez);
        monthly.setElseFish(elseFish);
        monthly.setUpdator(activeUser.getUsername());
        monthly.setCreator(activeUser.getUsername());

        monthly.setShipPutIn(shipPutIn);
        monthly.setShipEezTheFish(shipEezTheFish);
        monthly.setShipNoProduction(shipNoProduction);

        boolean result = monthlyService.updateMonthly(monthly, fishData, activeUser);
        if (result) {
            //修改成功
            return Result.success();
        } else {
            //修改失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    @GetMapping("deleteAllMonthly")
    public Result deleteAllMonthly(String ids) {
        String[] split = ids.split(",");

        for (String s : split) {
            boolean result = monthlyService.delete(s);
            if (result) {
            } else {
                //删除失败
                return Result.failure(ResultCode.ERROR);
            }
        }
        //删除成功
        return Result.success();
    }

    /**
     * 导出上传文件的模板
     *
     * @return
     */
    @GetMapping("/butExportTemplate")
    public Result putExportTemplate(HttpServletResponse response) {
        System.out.println(1231223);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        sheet.setAutobreaks(true);//是否显示自动分页符
        sheet.setColumnWidth(43, 3000);//设置宽度

        // 标题行
        CellStyle titleCellStyle = createStyles(wb).get("title");
        HSSFRow twoRow = sheet.createRow(1);
        HSSFCell titleCell = twoRow.createCell(0);
        twoRow.setHeightInPoints((float) 26);
        titleCell.setCellValue("金枪鱼延绳钓渔船月报汇总表");
        titleCell.setCellStyle(titleCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        CellRangeAddress cellRangeAddress = new CellRangeAddress(1, 1, 0, 45);
        sheet.addMergedRegion(cellRangeAddress);

        CellStyle secondCellStyle = createStyles(wb).get("secondTitle");
        HSSFRow secondRow = sheet.createRow(2);
        HSSFCell secondCell = secondRow.createCell(0);
        secondRow.setHeightInPoints((float) 26);
        secondCell.setCellValue("作业洋区：");
        secondCell.setCellStyle(secondCellStyle);
        cellRangeAddress = new CellRangeAddress(2, 2, 0, 1);
        sheet.addMergedRegion(cellRangeAddress);

        CellStyle menuCellStyle = createStyles(wb).get("menu");
        secondCell = secondRow.createCell(1);
        secondRow.setHeightInPoints((float) 26);
        secondCell.setCellStyle(menuCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号

        secondCell = secondRow.createCell(2);
        secondRow.setHeightInPoints((float) 26);
        secondCell.setCellStyle(secondCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        cellRangeAddress = new CellRangeAddress(2, 2, 2, 13);
        sheet.addMergedRegion(cellRangeAddress);

        //空格样式
        for (int a = 3; a < 14; a++) {
            secondCell = secondRow.createCell(a);
            secondRow.setHeightInPoints((float) 26);
            secondCell.setCellStyle(menuCellStyle);
        }

        secondCell = secondRow.createCell(28);
        secondRow.setHeightInPoints((float) 26);
        secondCell.setCellValue("作业类型：");
        secondCell.setCellStyle(secondCellStyle);
        cellRangeAddress = new CellRangeAddress(2, 2, 28, 30);
        sheet.addMergedRegion(cellRangeAddress);

        secondCell = secondRow.createCell(29);
        secondRow.setHeightInPoints((float) 26);
        secondCell.setCellStyle(secondCellStyle);

        secondCell = secondRow.createCell(30);
        secondRow.setHeightInPoints((float) 26);
        secondCell.setCellStyle(secondCellStyle);

        secondCell = secondRow.createCell(31);
        secondRow.setHeightInPoints((float) 26);
        secondCell.setCellStyle(secondCellStyle);
        cellRangeAddress = new CellRangeAddress(2, 2, 31, 32);
        sheet.addMergedRegion(cellRangeAddress);

        secondCell = secondRow.createCell(32);
        secondRow.setHeightInPoints((float) 26);
        secondCell.setCellStyle(secondCellStyle);


        HSSFRow threeRow = sheet.createRow(3);
        HSSFCell threeCell = threeRow.createCell(0);
        threeRow.setHeightInPoints((float) 26);
        threeCell.setCellValue("公司名称：");
        threeCell.setCellStyle(secondCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 0, 1);
        sheet.addMergedRegion(cellRangeAddress);

        threeCell = threeRow.createCell(1);
        threeRow.setHeightInPoints((float) 26);
        threeCell.setCellStyle(menuCellStyle);

        threeCell = threeRow.createCell(2);
        threeRow.setHeightInPoints((float) 26);
        threeCell.setCellStyle(secondCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        cellRangeAddress = new CellRangeAddress(3, 3, 2, 13);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 3; a < 14; a++) {
            threeCell = threeRow.createCell(a);
            threeRow.setHeightInPoints((float) 26);
            threeCell.setCellStyle(menuCellStyle);
        }


        threeCell = threeRow.createCell(14);
        threeRow.setHeightInPoints((float) 26);
        threeCell.setCellValue("所属省份：");
        threeCell.setCellStyle(secondCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 14, 16);
        sheet.addMergedRegion(cellRangeAddress);

        threeCell = threeRow.createCell(15);
        threeRow.setHeightInPoints((float) 26);
        threeCell.setCellStyle(menuCellStyle);

        threeCell = threeRow.createCell(16);
        threeRow.setHeightInPoints((float) 26);
        threeCell.setCellStyle(menuCellStyle);


        threeCell = threeRow.createCell(17);
        threeRow.setHeightInPoints((float) 26);
        threeCell.setCellStyle(secondCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        cellRangeAddress = new CellRangeAddress(3, 3, 17, 18);
        sheet.addMergedRegion(cellRangeAddress);

        threeCell = threeRow.createCell(18);
        threeRow.setHeightInPoints((float) 26);
        threeCell.setCellStyle(menuCellStyle);

        threeCell = threeRow.createCell(28);
        threeRow.setHeightInPoints((float) 26);
        threeCell.setCellValue("作业时间：");
        threeCell.setCellStyle(secondCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 28, 30);
        sheet.addMergedRegion(cellRangeAddress);

        //空白样式
        threeCell = threeRow.createCell(29);
        threeRow.setHeightInPoints((float) 26);
        threeCell.setCellStyle(menuCellStyle);
        threeCell = threeRow.createCell(30);
        threeRow.setHeightInPoints((float) 26);
        threeCell.setCellStyle(menuCellStyle);

        threeCell = threeRow.createCell(31);
        threeRow.setHeightInPoints((float) 26);
        threeCell.setCellStyle(secondCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        cellRangeAddress = new CellRangeAddress(3, 3, 31, 32);
        sheet.addMergedRegion(cellRangeAddress);

	        /*threeCell = threeRow.createCell(32);
  	        threeRow.setHeightInPoints((float) 26);
  	        threeCell.setCellStyle(menuCellStyle);
	        cellRangeAddress = new CellRangeAddress(3, 3, 32, 32);
	        sheet.addMergedRegion(cellRangeAddress);*/

        threeCell = threeRow.createCell(33);
        threeRow.setHeightInPoints((float) 26);
        threeCell.setCellStyle(menuCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号


        HSSFRow fourRow = sheet.createRow(4);
        HSSFCell fourCell = fourRow.createCell(0);
        fourRow.setHeightInPoints((float) 26);
        fourCell.setCellStyle(menuCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        cellRangeAddress = new CellRangeAddress(4, 4, 0, 14);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 0; a < 15; a++) {
            fourCell = fourRow.createCell(a);
            fourRow.setHeightInPoints((float) 26);
            fourCell.setCellStyle(menuCellStyle);
            // 起始行号，终止行号， 起始列号，终止列号
        }


        fourCell = fourRow.createCell(15);
        fourRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("金枪鱼类");
        fourCell.setCellStyle(secondCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        cellRangeAddress = new CellRangeAddress(4, 4, 15, 22);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 16; a < 23; a++) {
            fourCell = fourRow.createCell(a);
            fourRow.setHeightInPoints((float) 26);
            fourCell.setCellStyle(menuCellStyle);
            // 起始行号，终止行号， 起始列号，终止列号
        }

        fourCell = fourRow.createCell(23);
        fourRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("旗鱼类");
        fourCell.setCellStyle(secondCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        cellRangeAddress = new CellRangeAddress(4, 4, 23, 34);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 24; a < 35; a++) {
            fourCell = fourRow.createCell(a);
            fourRow.setHeightInPoints((float) 26);
            fourCell.setCellStyle(menuCellStyle);
            // 起始行号，终止行号， 起始列号，终止列号
        }

        fourCell = fourRow.createCell(35);
        fourRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("鲨鱼类");
        fourCell.setCellStyle(secondCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        cellRangeAddress = new CellRangeAddress(4, 4, 35, 42);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 36; a < 43; a++) {
            fourCell = fourRow.createCell(a);
            fourRow.setHeightInPoints((float) 26);
            fourCell.setCellStyle(menuCellStyle);
        }

        fourCell = fourRow.createCell(43);
        fourRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("其他类");
        fourCell.setCellStyle(secondCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号


        CellStyle littleCellStyle = createStyles(wb).get("menuTitleFont");
        HSSFRow fiveRow = sheet.createRow(5);
        HSSFCell fiveCell = fiveRow.createCell(0);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("船名");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 7, 0, 0);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(1);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("作业天数");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 7, 1, 1);
        sheet.addMergedRegion(cellRangeAddress);


        fiveCell = fiveRow.createCell(2);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("作业海域");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 2, 13);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 3; a < 14; a++) {
            fiveCell = fiveRow.createCell(a);
            fiveRow.setHeightInPoints((float) 26);
            fiveCell.setCellStyle(littleCellStyle);
        }


        fiveCell = fiveRow.createCell(14);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("投钩数(枚/日)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 7, 14, 14);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(15);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("大   目");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 15, 16);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 16; a < 24; a = a + 2) {
            fiveCell = fiveRow.createCell(a);
            fiveRow.setHeightInPoints((float) 26);
            fiveCell.setCellStyle(littleCellStyle);
        }


        fiveCell = fiveRow.createCell(17);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("黄   鳍");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 17, 18);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(19);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("蓝   鳍");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 19, 20);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(21);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("长   鳍");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 21, 22);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(23);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("剑鱼");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 23, 24);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(25);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("旗鱼 (雨伞)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 25, 26);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(27);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("蓝枪 (黑旗)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 27, 28);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(29);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("印度枪 (立翅)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 29, 30);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(31);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("条纹(红肉) 或 白枪");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 31, 32);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(33);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("小吻或锯鳞");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 33, 34);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(35);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("大青鲨 (灰鲨)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 35, 36);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(37);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("尖吻鲭鲨 (马加)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 37, 38);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(39);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("长鳍真鲨(黑鲨)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 39, 40);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(41);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("长尾鲨");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 41, 42);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(42);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellStyle(littleCellStyle);

        fiveCell = fiveRow.createCell(43);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("重量(kg)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 7, 43, 43);
        sheet.addMergedRegion(cellRangeAddress);


        HSSFRow sixRow = sheet.createRow(6);
        HSSFCell sixCell = sixRow.createCell(2);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("经度范围");
        sixCell.setCellStyle(littleCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        cellRangeAddress = new CellRangeAddress(6, 6, 2, 7);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(0);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellStyle(littleCellStyle);

        sixCell = sixRow.createCell(8);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("纬度范围");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 8, 13);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(14);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellStyle(littleCellStyle);

        sixCell = sixRow.createCell(15);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Bigeye");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 15, 16);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(17);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Yellowfin");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 17, 18);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(19);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Bluefin");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 19, 20);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(21);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Albacore");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 21, 22);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(23);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Swordfish");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 23, 24);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 24; a < 43; a = a + 2) {
            sixCell = sixRow.createCell(a);
            sixRow.setHeightInPoints((float) 26);
            sixCell.setCellStyle(littleCellStyle);
        }

        sixCell = sixRow.createCell(25);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Sailfish");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 25, 26);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(27);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Blue Marlin");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 27, 28);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(29);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Black Marlin");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 29, 30);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(31);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Striped marlin or White marlin ");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 31, 32);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(33);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Shortbill or Longbill spearfish");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 33, 34);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(35);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Blue shark");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 35, 36);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(37);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Shortfin mako");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 37, 38);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(39);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Oceanic whitetip shark");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 39, 40);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(41);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Thresh shark");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 41, 42);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(43);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellStyle(littleCellStyle);

        HSSFRow sevenRow = sheet.createRow(7);
        HSSFCell sevenCell = sevenRow.createCell(2);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellValue("EW");
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(1);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(0);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);


        sevenCell = sevenRow.createCell(3);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellValue("度");
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(4);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellValue("分");
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(5);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellValue("EW");
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(6);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellValue("度");
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(7);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellValue("分");
        sevenCell.setCellStyle(littleCellStyle);

        for (int a = 8; a < 12; a = a + 3) {
            sevenCell = sevenRow.createCell(a);
            sevenRow.setHeightInPoints((float) 26);
            sevenCell.setCellValue("SN");
            sevenCell.setCellStyle(littleCellStyle);
            sevenCell = sevenRow.createCell(a + 1);
            sevenRow.setHeightInPoints((float) 26);
            sevenCell.setCellValue("度");
            sevenCell.setCellStyle(littleCellStyle);
            sevenCell = sevenRow.createCell(a + 2);
            sevenRow.setHeightInPoints((float) 26);
            sevenCell.setCellValue("分");
            sevenCell.setCellStyle(littleCellStyle);
        }

        sevenCell = sevenRow.createCell(14);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);

        for (int a = 15; a < 43; a = a + 2) {
            sevenCell = sevenRow.createCell(a);
            sevenRow.setHeightInPoints((float) 26);
            sevenCell.setCellValue("重量(kg)");
            sevenCell.setCellStyle(littleCellStyle);
            sevenCell = sevenRow.createCell(a + 1);
            sevenRow.setHeightInPoints((float) 26);
            sevenCell.setCellValue("尾数(尾)");
            sevenCell.setCellStyle(littleCellStyle);
        }

        sevenCell = sevenRow.createCell(43);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);

        CellStyle dataCellStyle = createStyles(wb).get("dataStyle");
        HSSFRow row = null;
        HSSFCell cell = null;
        //插入数据
        for (int i = 0; i < 2; i++) {
            row = sheet.createRow(8 + i);
            row.setHeightInPoints((float) 15.95);

            cell = row.createCell(0);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(1);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(2);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(3);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(4);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(5);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(6);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(7);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(8);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(9);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(10);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(11);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(12);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(13);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(14);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(15);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(16);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(17);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(18);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(19);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(20);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(21);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(22);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(23);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(24);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(25);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(26);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(27);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(28);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(29);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(30);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(31);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(32);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(33);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(34);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(35);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(36);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(37);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(38);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(39);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(40);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(41);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(42);
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(43);
            cell.setCellStyle(dataCellStyle);
        }


        dataCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        //渔船进港情况（渔船进港时间、港口名、进港事由）：
        HSSFRow xRow = sheet.createRow(10);
        HSSFCell xCell = xRow.createCell(0);
        xCell.setCellValue("渔船进港情况（渔船进港时间、港口名、进港事由）：");
        xCell.setCellStyle(dataCellStyle);
        cellRangeAddress = new CellRangeAddress(10, 10, 0, 43);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 1; a <= 43; a++) {
            xCell = xRow.createCell(a);
            xCell.setCellStyle(littleCellStyle);
            xCell.setCellStyle(dataCellStyle);
        }

        //渔船专属经济区入渔情况（入渔时间、入渔国、是否靠港转载等）：
        HSSFRow nineRow = sheet.createRow(11);
        HSSFCell nineCell = nineRow.createCell(0);
        nineCell.setCellValue("渔船专属经济区入渔情况（入渔时间、入渔国、是否靠港转载等）：");
        nineCell.setCellStyle(dataCellStyle);
        cellRangeAddress = new CellRangeAddress(11, 11, 0, 43);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 1; a <= 43; a++) {
            nineCell = nineRow.createCell(a);
            nineCell.setCellStyle(littleCellStyle);
            nineCell.setCellStyle(dataCellStyle);
        }

        //渔船无产量原因(修船、航行)：
        HSSFRow tenRow = sheet.createRow(12);
        HSSFCell tenCell = tenRow.createCell(0);
        tenCell.setCellValue("渔船无产量原因(修船、航行)：");
        tenCell.setCellStyle(dataCellStyle);
        cellRangeAddress = new CellRangeAddress(12, 12, 0, 43);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 1; a <= 43; a++) {
            tenCell = tenRow.createCell(a);
            tenCell.setCellStyle(littleCellStyle);
            tenCell.setCellStyle(dataCellStyle);
        }


        response.setContentType("application/ms-excel;charset=UTF-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("模板.xls", "UTF-8"))));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            ServletOutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 导出
     *
     * @param ids
     * @return
     */
    @GetMapping("/putExport")
    public Result putExport(String ids, HttpServletResponse response) {


        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();


        sheet.setAutobreaks(true);//是否显示自动分页符
        sheet.setColumnWidth(43, 3000);//设置宽度

        //第一行
        CellStyle titleCellStyle = createStyles(wb).get("title");//设置标题的格式
        HSSFRow twoRow = sheet.createRow(1);//创建行
        HSSFCell titleCell = twoRow.createCell(0);//创建列
        twoRow.setHeightInPoints((float) 26);//设置高度
        titleCell.setCellValue("月报汇总表");//设置标题
        titleCell.setCellStyle(titleCellStyle);
        //合并单元格
        CellRangeAddress cellRangeAddress = new CellRangeAddress(1, 1, 0, 45);
        sheet.addMergedRegion(cellRangeAddress);


        // 第二行
        CellStyle secondCellStyle = createStyles(wb).get("secondTitle");
        //第三行
        CellStyle menuCellStyle = createStyles(wb).get("menu");

        //第四行
        HSSFRow fourRow = sheet.createRow(4);
        HSSFCell fourCell = fourRow.createCell(0);
        fourRow.setHeightInPoints((float) 26);
        fourCell.setCellStyle(menuCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        cellRangeAddress = new CellRangeAddress(4, 4, 0, 14);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 0; a < 15; a++) {
            fourCell = fourRow.createCell(a);
            fourRow.setHeightInPoints((float) 26);
            fourCell.setCellStyle(menuCellStyle);
            // 起始行号，终止行号， 起始列号，终止列号
        }

        fourCell = fourRow.createCell(15);
        fourRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("金枪鱼类");
        fourCell.setCellStyle(secondCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        cellRangeAddress = new CellRangeAddress(4, 4, 15, 22);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 16; a < 23; a++) {
            fourCell = fourRow.createCell(a);
            fourRow.setHeightInPoints((float) 26);
            fourCell.setCellStyle(menuCellStyle);
            // 起始行号，终止行号， 起始列号，终止列号
        }

        fourCell = fourRow.createCell(23);
        fourRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("旗鱼类");
        fourCell.setCellStyle(secondCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        cellRangeAddress = new CellRangeAddress(4, 4, 23, 34);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 24; a < 35; a++) {
            fourCell = fourRow.createCell(a);
            fourRow.setHeightInPoints((float) 26);
            fourCell.setCellStyle(menuCellStyle);
            // 起始行号，终止行号， 起始列号，终止列号
        }

        fourCell = fourRow.createCell(35);
        fourRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("鲨鱼类");
        fourCell.setCellStyle(secondCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        cellRangeAddress = new CellRangeAddress(4, 4, 35, 42);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 36; a < 43; a++) {
            fourCell = fourRow.createCell(a);
            fourRow.setHeightInPoints((float) 26);
            fourCell.setCellStyle(menuCellStyle);
        }

        fourCell = fourRow.createCell(43);
        fourRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("其他类");
        fourCell.setCellStyle(secondCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号

        //第五行
        CellStyle littleCellStyle = createStyles(wb).get("menuTitleFont");

        HSSFRow fiveRow = sheet.createRow(5);
        HSSFCell fiveCell = fiveRow.createCell(0);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("船名");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 7, 0, 0);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(1);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("作业天数");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 7, 1, 1);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(2);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("公司名称");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 7, 2, 2);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(3);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("作业洋区");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 7, 3, 3);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(4);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("所属省份");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 7, 4, 4);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(5);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("作业类型");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 7, 5, 5);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(6);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("作业时间");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 7, 6, 6);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(7);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("公海EEZ");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 7, 7, 7);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(8);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("作业海域");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 8, 13);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 9; a < 14; a++) {
            fiveCell = fiveRow.createCell(a);
            fiveRow.setHeightInPoints((float) 26);
            fiveCell.setCellStyle(littleCellStyle);
        }


        fiveCell = fiveRow.createCell(14);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("投钩数(枚/日)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 7, 14, 14);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(15);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("大   目");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 15, 16);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 16; a < 24; a = a + 2) {
            fiveCell = fiveRow.createCell(a);
            fiveRow.setHeightInPoints((float) 26);
            fiveCell.setCellStyle(littleCellStyle);
        }


        fiveCell = fiveRow.createCell(17);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("黄   鳍");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 17, 18);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(19);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("蓝   鳍");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 19, 20);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(21);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("长   鳍");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 21, 22);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(23);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("剑鱼");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 23, 24);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(25);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("旗鱼 (雨伞)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 25, 26);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(27);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("蓝枪 (黑旗)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 27, 28);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(29);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("印度枪 (立翅)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 29, 30);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(31);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("条纹(红肉) 或 白枪");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 31, 32);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(33);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("小吻或锯鳞");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 33, 34);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(35);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("大青鲨 (灰鲨)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 35, 36);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(37);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("尖吻鲭鲨 (马加)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 37, 38);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(39);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("长鳍真鲨(黑鲨)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 39, 40);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(41);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("长尾鲨");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 41, 42);
        sheet.addMergedRegion(cellRangeAddress);

        fiveCell = fiveRow.createCell(42);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellStyle(littleCellStyle);

        fiveCell = fiveRow.createCell(43);
        fiveRow.setHeightInPoints((float) 26);
        fiveCell.setCellValue("重量(kg)");
        fiveCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 7, 43, 43);
        sheet.addMergedRegion(cellRangeAddress);

        //第六行
        HSSFRow sixRow = sheet.createRow(6);

        HSSFCell sixCell = sixRow.createCell(8);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("开始地点");
        sixCell.setCellStyle(littleCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        cellRangeAddress = new CellRangeAddress(6, 6, 8, 10);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(0);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellStyle(littleCellStyle);

        sixCell = sixRow.createCell(2);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellStyle(littleCellStyle);

        sixCell = sixRow.createCell(3);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellStyle(littleCellStyle);

        sixCell = sixRow.createCell(4);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellStyle(littleCellStyle);

        sixCell = sixRow.createCell(5);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellStyle(littleCellStyle);

        sixCell = sixRow.createCell(6);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellStyle(littleCellStyle);

        sixCell = sixRow.createCell(11);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("结束地点");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 11, 13);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(14);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellStyle(littleCellStyle);


        sixCell = sixRow.createCell(15);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Bigeye");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 15, 16);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(17);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Yellowfin");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 17, 18);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(19);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Bluefin");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 19, 20);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(21);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Albacore");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 21, 22);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(23);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Swordfish");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 23, 24);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 24; a < 43; a = a + 2) {
            sixCell = sixRow.createCell(a);
            sixRow.setHeightInPoints((float) 26);
            sixCell.setCellStyle(littleCellStyle);
        }

        sixCell = sixRow.createCell(25);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Sailfish");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 25, 26);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(27);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Blue Marlin");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 27, 28);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(29);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Black Marlin");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 29, 30);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(31);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Striped marlin or White marlin ");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 31, 32);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(33);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Shortbill or Longbill spearfish");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 33, 34);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(35);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Blue shark");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 35, 36);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(37);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Shortfin mako");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 37, 38);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(39);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Oceanic whitetip shark");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 39, 40);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(41);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellValue("Thresh shark");
        sixCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(6, 6, 41, 42);
        sheet.addMergedRegion(cellRangeAddress);

        sixCell = sixRow.createCell(43);
        sixRow.setHeightInPoints((float) 26);
        sixCell.setCellStyle(littleCellStyle);


        //第七行
        HSSFRow sevenRow = sheet.createRow(7);

        HSSFCell sevenCell = sevenRow.createCell(8);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellValue("经纬度");
        sevenCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(7, 7, 8, 10);
        sheet.addMergedRegion(cellRangeAddress);

        sevenCell = sevenRow.createCell(1);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(0);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);


        sevenCell = sevenRow.createCell(3);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(4);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(5);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(6);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(7);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(11);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellValue("经纬度");
        sevenCell.setCellStyle(littleCellStyle);
        cellRangeAddress = new CellRangeAddress(7, 7, 11, 13);
        sheet.addMergedRegion(cellRangeAddress);

        sevenCell = sevenRow.createCell(9);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(10);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(12);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(13);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);

        sevenCell = sevenRow.createCell(14);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);

        for (int a = 15; a < 43; a = a + 2) {
            sevenCell = sevenRow.createCell(a);
            sevenRow.setHeightInPoints((float) 26);
            sevenCell.setCellValue("重量(kg)");
            sevenCell.setCellStyle(littleCellStyle);

            sevenCell = sevenRow.createCell(a + 1);
            sevenRow.setHeightInPoints((float) 26);
            sevenCell.setCellValue("尾数(尾)");
            sevenCell.setCellStyle(littleCellStyle);
        }

        sevenCell = sevenRow.createCell(43);
        sevenRow.setHeightInPoints((float) 26);
        sevenCell.setCellStyle(littleCellStyle);

        //具体数据
        CellStyle dataCellStyle = createStyles(wb).get("dataStyle");
        HSSFRow row = null;
        HSSFCell cell = null;

        String[] split = ids.split(",");
        String companyName = "";

        String shipPutIn = "";
        String shipEezTheFish = "";
        String shipNoProduction = "";


        //查询monthly数据
        for (int x = 0; x < split.length; x++) {

            Monthly monthly = monthlyService.getById(split[x]);
            shipPutIn = monthly.getShipPutIn();
            shipEezTheFish = monthly.getShipEezTheFish();
            shipNoProduction = monthly.getShipNoProduction();


            List<Specification> list = specificationService.getSpecification(split[x]);

            HSSFRow xRow = sheet.createRow(x + 8);

            HSSFCell xCell = xRow.createCell(0);
            xCell.setCellValue(monthly.getShipName());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(1);
            xCell.setCellValue(monthly.getJobNumOfDays());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(2);
            companyName = monthly.getCompanyName();//公司名称
            xCell.setCellValue(companyName);
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(3);
            xCell.setCellValue(monthly.getJobTheArea());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(4);
            xCell.setCellValue(monthly.getProvince());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(5);
            xCell.setCellValue(monthly.getJobType());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(6);
            xCell.setCellValue(monthly.getJobTime());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(7);
            xCell.setCellValue(monthly.getEez());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(8);
            xCell.setCellValue(monthly.getStartSite());
            xCell.setCellStyle(dataCellStyle);
            cellRangeAddress = new CellRangeAddress(x + 8, x + 8, 8, 10);
            sheet.addMergedRegion(cellRangeAddress);

            xCell = xRow.createCell(9);
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(10);
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(11);
            xCell.setCellValue(monthly.getEndSite());
            xCell.setCellStyle(dataCellStyle);
            cellRangeAddress = new CellRangeAddress(x + 8, x + 8, 11, 13);
            sheet.addMergedRegion(cellRangeAddress);

            xCell = xRow.createCell(12);
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(13);
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(14);
            xCell.setCellValue(monthly.getCastHookNum());
            xCell.setCellStyle(dataCellStyle);

                /*for (Specification spe : list) {
                    System.out.println(spe);
                }*/
            Map<String, Specification> appleMap = list.stream().collect(Collectors.toMap(Specification::getFishName, a -> a, (k1, k2) -> k1));

            xCell = xRow.createCell(15);
            xCell.setCellValue(appleMap.get("大目").getWeight());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(16);
            xCell.setCellValue(appleMap.get("大目").getMantissa());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(17);
            xCell.setCellValue(appleMap.get("黄鳍").getWeight());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(18);
            xCell.setCellValue(appleMap.get("黄鳍").getMantissa());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(19);
            xCell.setCellValue(appleMap.get("蓝鳍").getWeight());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(20);
            xCell.setCellValue(appleMap.get("蓝鳍").getMantissa());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(21);
            xCell.setCellValue(appleMap.get("长鳍").getWeight());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(22);
            xCell.setCellValue(appleMap.get("长鳍").getMantissa());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(23);
            xCell.setCellValue(appleMap.get("剑鱼").getWeight());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(24);
            xCell.setCellValue(appleMap.get("剑鱼").getMantissa());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(25);
            xCell.setCellValue(appleMap.get("旗鱼(雨伞)").getWeight());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(26);
            xCell.setCellValue(appleMap.get("旗鱼(雨伞)").getMantissa());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(27);
            xCell.setCellValue(appleMap.get("蓝枪(黑旗)").getWeight());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(28);
            xCell.setCellValue(appleMap.get("蓝枪(黑旗)").getMantissa());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(29);
            xCell.setCellValue(appleMap.get("印度枪(立翅)").getWeight());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(30);
            xCell.setCellValue(appleMap.get("印度枪(立翅)").getMantissa());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(31);
            xCell.setCellValue(appleMap.get("条纹(红肉)或白枪").getWeight());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(32);
            xCell.setCellValue(appleMap.get("条纹(红肉)或白枪").getMantissa());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(33);
            xCell.setCellValue(appleMap.get("小吻或锯鳞").getWeight());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(34);
            xCell.setCellValue(appleMap.get("小吻或锯鳞").getMantissa());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(35);
            xCell.setCellValue(appleMap.get("大青鲨(灰鲨)").getWeight());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(36);
            xCell.setCellValue(appleMap.get("大青鲨(灰鲨)").getMantissa());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(37);
            xCell.setCellValue(appleMap.get("尖吻鲭鲨(马加)").getWeight());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(38);
            xCell.setCellValue(appleMap.get("尖吻鲭鲨(马加)").getMantissa());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(39);
            xCell.setCellValue(appleMap.get("长鳍真鲨(黑鲨)").getWeight());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(40);
            xCell.setCellValue(appleMap.get("长鳍真鲨(黑鲨)").getMantissa());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(41);
            xCell.setCellValue(appleMap.get("长尾鲨").getWeight());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(42);
            xCell.setCellValue(appleMap.get("长尾鲨").getMantissa());
            xCell.setCellStyle(dataCellStyle);

            xCell = xRow.createCell(43);
            xCell.setCellValue(monthly.getElseFish());
            xCell.setCellStyle(dataCellStyle);

        }

            /*String shipPutIn = "";
            String shipEezTheFish = "";
            String shipNoProduction = "";*/
        if (shipPutIn == null) {
            shipPutIn = "";
        }
        if (shipEezTheFish == null) {
            shipEezTheFish = "";
        }
        if (shipNoProduction == null) {
            shipNoProduction = "";
        }

        dataCellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        //渔船进港情况（渔船进港时间、港口名、进港事由）：
        HSSFRow xRow = sheet.createRow(split.length + 8);
        HSSFCell xCell = xRow.createCell(0);
        xCell.setCellValue("渔船进港情况（渔船进港时间、港口名、进港事由）：" + shipPutIn);
        xCell.setCellStyle(dataCellStyle);
        cellRangeAddress = new CellRangeAddress(split.length + 8, split.length + 8, 0, 43);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 1; a <= 43; a++) {
            xCell = xRow.createCell(a);
            xCell.setCellStyle(littleCellStyle);
            xCell.setCellStyle(dataCellStyle);
        }

        //渔船专属经济区入渔情况（入渔时间、入渔国、是否靠港转载等）：
        HSSFRow nineRow = sheet.createRow(split.length + 9);
        HSSFCell nineCell = nineRow.createCell(0);
        nineCell.setCellValue("渔船专属经济区入渔情况（入渔时间、入渔国、是否靠港转载等）：" + shipEezTheFish);
        nineCell.setCellStyle(dataCellStyle);
        cellRangeAddress = new CellRangeAddress(split.length + 9, split.length + 9, 0, 43);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 1; a <= 43; a++) {
            nineCell = nineRow.createCell(a);
            nineCell.setCellStyle(littleCellStyle);
            nineCell.setCellStyle(dataCellStyle);
        }

        //渔船无产量原因(修船、航行)：
        HSSFRow tenRow = sheet.createRow(split.length + 10);
        HSSFCell tenCell = tenRow.createCell(0);
        tenCell.setCellValue("渔船无产量原因(修船、航行)：" + shipNoProduction);
        tenCell.setCellStyle(dataCellStyle);
        cellRangeAddress = new CellRangeAddress(split.length + 10, split.length + 10, 0, 43);
        sheet.addMergedRegion(cellRangeAddress);

        for (int a = 1; a <= 43; a++) {
            tenCell = tenRow.createCell(a);
            tenCell.setCellStyle(littleCellStyle);
            tenCell.setCellStyle(dataCellStyle);
        }

        response.setContentType("application/ms-excel;charset=UTF-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(companyName + ".xls", "UTF-8"))));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            ServletOutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 创建表格样式
     *
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        //标题行
        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        Font titleFont = wb.createFont();
        titleFont.setFontName("宋体");
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(titleFont);
        styles.put("title", style);

        //da标题行
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        Font secondTitleFont = wb.createFont();
        secondTitleFont.setFontName("宋体");
        secondTitleFont.setFontHeightInPoints((short) 14);
        secondTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setWrapText(true);
        style.setFont(secondTitleFont);
        styles.put("secondTitle", style);


        //xiao标题行
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        Font menuTitleFont = wb.createFont();
        menuTitleFont.setFontName("宋体");
        menuTitleFont.setFontHeightInPoints((short) 10);
        menuTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setWrapText(true);
        style.setFont(menuTitleFont);
        styles.put("menuTitleFont", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        style.setWrapText(true);
        Font menuFont = wb.createFont();
        menuFont.setFontName("宋体");
        menuFont.setFontHeightInPoints((short) 14);
        menuFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(menuFont);
        styles.put("menu", style);


        //数据样式
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setWrapText(true);
        Font datasFont = wb.createFont();
        datasFont.setFontName("宋体");
        datasFont.setFontHeightInPoints((short) 10);
        style.setFont(datasFont);
        styles.put("dataStyle", style);

        return styles;
    }

    private String eez;

    public String getEez() {
        return eez;
    }

    public void setEez(String eez) {
        this.eez = eez;
    }

    //获取公海EEZ
    @GetMapping("getEEZ")
    public Result getEEZ(String eez) {
        if (eez == null) {
            eez = "公海";
        }
        setEez(eez);
        return Result.success();
    }

    //导入数据
    @PostMapping("upload")
    public Result uploadMonthly(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String leaEez = "公海";
        if (getEez() != null) {
            leaEez = getEez();
        }

        boolean result = true;
        try {
            InputStream inputStream = file.getInputStream();
            HSSFWorkbook wb = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum() + 1;

            ActiveUser activeUser = getActiveUser(request);

            Monthly monthly = new Monthly();
            monthly.setCreator(activeUser.getUsername());
            monthly.setUpdator(activeUser.getUsername());
            monthly.setDel(0);
            monthly.setEez(leaEez);

            HSSFRow twoRow = sheet.getRow(2);
            HSSFCell cell = twoRow.getCell(2);
            String jobTheArea = cell.getStringCellValue();
            //设置作业洋区
            monthly.setJobTheArea(jobTheArea);

            cell = twoRow.getCell(31);
            String jobType = cell.getStringCellValue();
            //设置作业类型
            monthly.setJobType(jobType);

            //获取行
            HSSFRow threeRow = sheet.getRow(3);
            cell = threeRow.getCell(2);
            //设置公司名称
            String companyName = cell.getStringCellValue();
            monthly.setCompanyName(companyName);

            cell = threeRow.getCell(17);
            String province = cell.toString();
            monthly.setProvince(province);

            String jobTime = "";
            cell = threeRow.getCell(31);
            jobTime += cell.toString().substring(0, 4) + "-";
            cell = threeRow.getCell(33);
            String day = "";
            if (cell.toString().indexOf(".") == -1) {
                day = cell.toString();
            } else {
                day = cell.toString().substring(0, cell.toString().indexOf("."));
            }
            jobTime += day;
            //设置作业时间
            monthly.setJobTime(jobTime);

            for (int x = 8; x < rowNum; x++) {
                HSSFRow fourRow = sheet.getRow(x);

                if (fourRow == null) {
                    continue;
                }

                /*for(int j = 0;j<43;j++){
                    cell = fourRow.getCell(j);

                    if(cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                        if (cell.toString() == null || cell.toString().equals("")) {
                            continue;
                        }
                        System.out.println("第"+x+"行，第"+j+"列："+cell.toString());
                    }else{
                        continue;
                    }

                }*/

                //获取行
//                HSSFRow fourRow = sheet.getRow(x);
                cell = fourRow.getCell(0);
                if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                    if (cell.toString() == null || cell.toString().equals("")) {
                        continue;
                    }
                } else {
                    continue;
                }
                String shipName = cell.toString();
                //设置船名
                monthly.setShipName(shipName);

                cell = fourRow.getCell(1);
                if (cell.toString() == null || cell.toString().equals("")) {
                    continue;
                }
                String jobNumOfDays = cell.toString().substring(0, cell.toString().indexOf("."));
                //设置作业天数
                monthly.setJobNumOfDays(jobNumOfDays);

                cell = fourRow.getCell(2);
                String s1 = cell.toString();
                cell = fourRow.getCell(3);
                String s2 = "";
                if (cell.toString().indexOf(".") == -1) {
                    s2 = cell.toString();
                } else {
                    s2 = cell.toString().substring(0, cell.toString().indexOf("."));
                }
                cell = fourRow.getCell(4);
                String s3 = "";
                if (cell.toString().indexOf(".") == -1) {
                    s3 = cell.toString();
                } else {
                    s3 = cell.toString().substring(0, cell.toString().indexOf("."));
                }

                cell = fourRow.getCell(5);
                String s4 = cell.toString();
                cell = fourRow.getCell(6);
                String s5 = "";
                if (cell.toString().indexOf(".") == -1) {
                    s5 = cell.toString();
                } else {
                    s5 = cell.toString().substring(0, cell.toString().indexOf("."));
                }
                cell = fourRow.getCell(7);
                String s6 = "";
                if (cell.toString().indexOf(".") == -1) {
                    s6 = cell.toString();
                } else {
                    s6 = cell.toString().substring(0, cell.toString().indexOf("."));
                }
                String startSite = s2 + "°" + s3 + s1 + "," + s5 + "°" + s6 + s4;
                //设置开始区域
                monthly.setStartSite(startSite);

                cell = fourRow.getCell(8);
                String s7 = cell.toString();
                cell = fourRow.getCell(9);
                String s8 = "";
                if (cell.toString().indexOf(".") == -1) {
                    s8 = cell.toString();
                } else {
                    s8 = cell.toString().substring(0, cell.toString().indexOf("."));
                }
                cell = fourRow.getCell(10);
                String s9 = "";
                if (cell.toString().indexOf(".") == -1) {
                    s9 = cell.toString();
                } else {
                    s9 = cell.toString().substring(0, cell.toString().indexOf("."));
                }
                cell = fourRow.getCell(11);
                String s10 = cell.toString();
                cell = fourRow.getCell(12);
                String s11 = "";
                if (cell.toString().indexOf(".") == -1) {
                    s11 = cell.toString();
                } else {
                    s11 = cell.toString().substring(0, cell.toString().indexOf("."));
                }
                cell = fourRow.getCell(13);
                String s12 = "";
                if (cell.toString().indexOf(".") == -1) {
                    s12 = cell.toString();
                } else {
                    s12 = cell.toString().substring(0, cell.toString().indexOf("."));
                }
                String endSite = s8 + "°" + s9 + s7 + "," + s11 + "°" + s12 + s10;
                //设置结束区域
                monthly.setEndSite(endSite);

                cell = fourRow.getCell(14);

                String castHookNum = "";
                if (cell.toString().indexOf(".") == -1) {
                    castHookNum = cell.toString();
                } else {
                    castHookNum = cell.toString().substring(0, cell.toString().indexOf("."));
                }

                monthly.setCastHookNum(castHookNum);

                cell = fourRow.getCell(43);
                String elseFish = cell.toString();
                monthly.setElseFish(elseFish);

                String monthlyId = NumberUtil.transIntTo62(monthlyMapper.getMonthlyId());
                monthly.setId(monthlyId);

                String shipPutIn = "";
                String shipEezTheFish = "";
                String shipNoProduction = "";

                for (int j = rowNum; j > 0; j--) {
                    HSSFRow afterThreeRow = sheet.getRow(j);
                    if (afterThreeRow != null) {
                        cell = afterThreeRow.getCell(0);
                        if (cell != null) {
                            shipNoProduction = cell.toString();
                            String[] split1 = shipNoProduction.split("：");
                            shipNoProduction = split1[1];
                            monthly.setShipNoProduction(shipNoProduction);
                            j--;
                            HSSFRow afterTwoRow = sheet.getRow(j);
                            cell = afterTwoRow.getCell(0);
                            shipEezTheFish = cell.toString();
                            String[] split2 = shipEezTheFish.split("：");
                            shipEezTheFish = split2[1];
                            monthly.setShipEezTheFish(shipEezTheFish);
                            j--;
                            HSSFRow afterOneRow = sheet.getRow(j);
                            cell = afterOneRow.getCell(0);
                            shipPutIn = cell.toString();
                            String[] split3 = shipPutIn.split("：");
                            shipPutIn = split3[1];
                            monthly.setShipPutIn(shipPutIn);

                            break;
                        }
                    }
                }


                System.out.println(monthly);
                result = monthlyService.insert(monthly);

                Specification spe = new Specification();
                for (int y = 15; y < 42; y++) {

                    String specId = NumberUtil.transIntTo62(specificationMapper.getSpecificationId());
                    spe.setId(specId);
                    spe.setMonthlyId(monthlyId);
                    String superFish = "";
                    if (y <= 22) {
                        superFish = "金枪鱼类";
                    } else if (y > 22 && y <= 34) {
                        superFish = "旗鱼类";
                    } else if (y > 34) {
                        superFish = "鲨鱼类";
                    }
                    spe.setSuperFish(superFish);

                    String fishName = "";

                    if (y <= 16) {
                        fishName = "大目";
                    } else if (y > 16 && y <= 18) {
                        fishName = "黄鳍";
                    } else if (y > 18 && y <= 20) {
                        fishName = "蓝鳍";
                    } else if (y > 20 && y <= 22) {
                        fishName = "长鳍";
                    } else if (y > 22 && y <= 24) {
                        fishName = "剑鱼";
                    } else if (y > 24 && y <= 26) {
                        fishName = "旗鱼(雨伞)";
                    } else if (y > 26 && y <= 28) {
                        fishName = "蓝枪(黑旗)";
                    } else if (y > 28 && y <= 30) {
                        fishName = "印度枪(立翅)";
                    } else if (y > 30 && y <= 32) {
                        fishName = "条纹(红肉)或白枪";
                    } else if (y > 32 && y <= 34) {
                        fishName = "小吻或锯鳞";
                    } else if (y > 34 && y <= 36) {
                        fishName = "大青鲨(灰鲨)";
                    } else if (y > 36 && y <= 38) {
                        fishName = "尖吻鲭鲨(马加)";
                    } else if (y > 38 && y <= 40) {
                        fishName = "长鳍真鲨(黑鲨)";
                    } else if (y > 40 && y <= 42) {
                        fishName = "长尾鲨";
                    }
                    spe.setFishName(fishName);

                    cell = fourRow.getCell(y);
                    String weight = cell.toString();
                    spe.setWeight(weight);
                    y++;
                    cell = fourRow.getCell(y);
                    String mantissa = cell.toString();
                    spe.setMantissa(mantissa);

                    specificationService.insert(spe);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }

        if (result) {
            //添加成功
            return Result.success();
        } else {
            //添加失败
            return Result.failure(ResultCode.ERROR);
        }
    }

}