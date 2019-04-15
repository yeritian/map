package com.shipmap.modules.observer.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.ResultCode;
import com.shipmap.framework.utils.ApiUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.observer.model.Observerhooktime;
import com.shipmap.modules.observer.service.ObserverhooktimeService;
import io.swagger.annotations.Api;
import org.apache.poi.hssf.usermodel.*;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ${author}
 * @since 2019-03-28
 */
@Api(value = "Observerhooktime相关的接口", tags = "user")
@RestController
@RequestMapping("/observer/observerhooktime")
public class ObserverhooktimeController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(ObserverhooktimeController.class);

    @Autowired
    private ObserverhooktimeService observerhooktimeService;

    ApiUtil api = new ApiUtil();

    /**
     * 查询Observerhooktime列表
     */
    @GetMapping("list")
    public Result list(Integer page, Integer limit, String searchKey, String searchValue, String observerinfoid) {
        if (page == null) {
            page = 0;
            limit = 0;
        }
        if (StringUtil.isBlank(searchValue)) {
            searchKey = null;
        }
        Page<Observerhooktime> observerhooktimePage = observerhooktimeService.list(new Page<Observerhooktime>(page, limit), searchKey, searchValue, observerinfoid);
        return PageResult.toResult(observerhooktimePage.getRecords(), observerhooktimePage.getTotal());
    }

    /**
     * 添加Observerhooktime
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id，多个用','分割", required = true, dataType = "String"),
     **/
    @PostMapping()
    public Result add(Observerhooktime observerhooktime, HttpServletRequest request) {
        boolean result = observerhooktimeService.add(observerhooktime, this.getActiveUser(request));
        if (result) {
            //添加成功
            return Result.success();
        } else {
            //添加失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 修改Observerhooktime
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id", required = true, dataType = "String"),
     **/

    @PutMapping()
    public Result update(Observerhooktime observerhooktime, HttpServletRequest request) {
        boolean result = observerhooktimeService.update(observerhooktime, this.getActiveUser(request));
        if (result) {
            //修改成功
            return Result.success();
        } else {
            //修改失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 删除Observerhooktime
     **/

    @DeleteMapping("/{observerhookid}")
    public Result delete(@PathVariable("observerhookid") String id) {
        boolean result = observerhooktimeService.delete(id);
        if (result) {
            //删除成功
            return Result.success();
        } else {
            //删除失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /*删除Observerhooktime 多条*/
    @PutMapping("/deletAllobserverhookid")
    public Result deletAllobhooklogid(String observerhookid) {
        boolean result = observerhooktimeService.deletAllobhooklogid(observerhookid);
        if (result) {
            //删除成功
            return Result.success();
        } else {
            //删除失败
            return Result.failure(ResultCode.ERROR);
        }
    }


    /*详情ById*/
    @PutMapping("/{observerhookid}")
    public Observerhooktime selectByidhooktime(@PathVariable("observerhookid") String observerhookid) {
        return observerhooktimeService.selectByidhooktime(observerhookid);
    }


    /*导入*/
    @RequestMapping(value = "fileexcel")
    @ResponseBody
    public Map<String, Object> Excel(HttpServletRequest request, @RequestParam("file") MultipartFile file, String obid) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
        Map<String, Object> dataMap = new HashMap<String, Object>();

        boolean res = false;

        long start = new Date().getTime();
        List<Observerhooktime> list = new ArrayList<>();
        list = importExcel(file, 2, 1, Observerhooktime.class);
        // System.out.println(new Date().getTime() - start); 导入花费时间

        for (int i = 0; i < list.size(); i++) {
            Observerhooktime obkooktime = new Observerhooktime();
            obkooktime.setCreateTime(new Date());//创建时间
            obkooktime.setCreator(this.getActiveUser(request).getUsername());//创建人
            obkooktime.setObserverinfoid(obid);
            obkooktime.setHooktimes(list.get(i).getHooktimes());
            obkooktime.setObstartdatehook(sdf.format(list.get(i).getObstartdatehookfu()));//下钩日期
            obkooktime.setBuoybetween(list.get(i).getBuoybetween());
            obkooktime.setFloatlinelength(list.get(i).getFloatlinelength());
            obkooktime.setRopelength(list.get(i).getRopelength());
            obkooktime.setRopebetween(list.get(i).getRopebetween());
            obkooktime.setThrowropespeed(list.get(i).getThrowropespeed());
            obkooktime.setThrowropeshipspeed(list.get(i).getThrowropeshipspeed());
            obkooktime.setSinglebaskethook(list.get(i).getSinglebaskethook());
            obkooktime.setRealitytotalbaskets(list.get(i).getRealitytotalbaskets());
            obkooktime.setRealitytotalhook(list.get(i).getRealitytotalhook());
            obkooktime.setSharkhook(list.get(i).getSharkhook());
            obkooktime.setRealitysharkhook(list.get(i).getRealitysharkhook());
            obkooktime.setObbaskets(list.get(i).getObbaskets());
            obkooktime.setObservebasketsrate(list.get(i).getObservebasketsrate());      //观察筐比例
            obkooktime.setTargetspecies(list.get(i).getTargetspecies());

            obkooktime.setObstatedatehook(sdf1.format(list.get(i).getObstatedatehookfu()));
            obkooktime.setObenddatehook(sdf1.format(list.get(i).getObenddatehookfu()));
            obkooktime.setObupstatedate(sdf1.format(list.get(i).getObupstatedatefu()));
            obkooktime.setObupenddate(sdf1.format(list.get(i).getObupenddatefu()));

            obkooktime.setUphooksst(list.get(i).getUphooksst());
            obkooktime.setUphookpa(list.get(i).getUphookpa());
            obkooktime.setUphookweather(list.get(i).getUphookweather());
            obkooktime.setUphookbf(list.get(i).getUphookbf());
            obkooktime.setLday(list.get(i).getLday());
            obkooktime.setLatitudedegrees(list.get(i).getLatitudedegrees());
            obkooktime.setLatitudepoints(list.get(i).getLatitudepoints());
            obkooktime.setLongitudesdegrees(list.get(i).getLongitudesdegrees());
            obkooktime.setLongitudespoints(list.get(i).getLongitudespoints());
            obkooktime.setTunahooktype(list.get(i).getTunahooktype());
            obkooktime.setTunahooksize(list.get(i).getTunahooksize());
            obkooktime.setSharkhooktype(list.get(i).getSharkhooktype());
            obkooktime.setSharkhooksize(list.get(i).getSharkhooksize());
            obkooktime.setGlowsticks(list.get(i).getGlowsticks());
            obkooktime.setTurtlehook(list.get(i).getTurtlehook());
            obkooktime.setTurtlehooktype(list.get(i).getTurtlehooktype());
            obkooktime.setCatchbirds(list.get(i).getCatchbirds());
            obkooktime.setBaitfish1(list.get(i).getBaitfish1());
            obkooktime.setBaitfishavglength1(list.get(i).getBaitfishavglength1());
            obkooktime.setBaitfish2(list.get(i).getBaitfish2());
            obkooktime.setBaitfishavglength2(list.get(i).getBaitfishavglength2());
            obkooktime.setObserverremark(list.get(i).getObserverremark());
            res = observerhooktimeService.InsertExcel(obkooktime) > 0;
        }

        if (res) {
            return api.returnJson(1, "导入成功！", dataMap);
        } else {
            return api.returnJson(2, "导入失败！", dataMap);
        }

    }

    public <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /*导出*/
    @GetMapping("/putExp")
    public Result putExport(String ids, HttpServletResponse response) {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();

        sheet.setAutobreaks(true);//是否显示自动分页符
        sheet.setColumnWidth(43, 3000);//设置宽度


        CellStyle titleCellStyle = createStyles(wb).get("title");//设置标题
        HSSFRow oneRow = sheet.createRow(0);//创建行
        HSSFCell titleCell = oneRow.createCell(0);//创建列
        oneRow.setHeightInPoints((float) 26);//设置高度
        titleCell.setCellValue("作业钩次");//设置标题
        titleCell.setCellStyle(titleCellStyle);
        //合并单元格
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 20);
        sheet.addMergedRegion(cellRangeAddress);

        CellStyle dataStyle = createStyles(wb).get("dataStyle");

        //标题
          /* HSSFRow fourRow = sheet.createRow(1);
           HSSFCell fourCell = fourRow.createCell(0);*/

        HSSFRow fourRow = sheet.createRow(1);
        HSSFCell fourCell = fourRow.createCell(0);
        fourRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("作业钩次Set No.");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(1);
        fourCell.setCellValue("下钩开始日期");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(2);
        fourCell.setCellValue("两浮子间主绳长 m");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(3);
        fourCell.setCellValue("浮绳长m");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(4);
        fourCell.setCellValue("支绳长m");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(5);
        fourCell.setCellValue("支绳间距 m");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(6);
        fourCell.setCellValue("投绳速度(m/s)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(7);
        fourCell.setCellValue("投绳时船速(kt)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(8);
        fourCell.setCellValue("单筐钩数(金枪鱼钩)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(9);
        fourCell.setCellValue("实际总筐数");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(10);
        fourCell.setCellValue("实际总下钩数(金枪鱼钩)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(11);
        fourCell.setCellValue("是否采用了鲨鱼钩");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(12);
        fourCell.setCellValue("实际总下鲨鱼钩数");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(13);
        fourCell.setCellValue("观察筐数");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(14);
        fourCell.setCellValue("观察筐数比率(随机,0~1)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(15);
        fourCell.setCellValue("目标鱼种");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(16);
        fourCell.setCellValue("下钩开始时间（当地）");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(17);
        fourCell.setCellValue("下钩结束时间");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(18);
        fourCell.setCellValue("起钩开始时间");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(19);
        fourCell.setCellValue("起钩结束时间");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(20);
        fourCell.setCellValue("起钩开始SST");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(21);
        fourCell.setCellValue("起钩开始气压");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(22);
        fourCell.setCellValue("起钩开始天气");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(23);
        fourCell.setCellValue("起钩开始海况BF");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(24);
        fourCell.setCellValue("农历日");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(25);
        fourCell.setCellValue("纬度 度  (北纬+南纬-)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(26);
        fourCell.setCellValue("纬度 分  (北纬+南纬-)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(27);
        fourCell.setCellValue("经度 度  (东经+西经-)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(28);
        fourCell.setCellValue("经度 分  (东经+西经-)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(29);
        fourCell.setCellValue("金枪鱼钓钩类型");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(30);
        fourCell.setCellValue("金枪鱼钓钩尺寸");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(31);
        fourCell.setCellValue("鲨鱼钓钩类型");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(32);
        fourCell.setCellValue("鲨鱼钓钩尺寸");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(33);
        fourCell.setCellValue("是否采用荧光棒");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(34);
        fourCell.setCellValue("是否配备海龟脱钩器");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(35);
        fourCell.setCellValue("海龟脱钩器类型");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(36);
        fourCell.setCellValue("是否有海鸟钓获");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(37);
        fourCell.setCellValue("饵料鱼1");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(38);
        fourCell.setCellValue("平均长度(cm)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(39);
        fourCell.setCellValue("饵料鱼2");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(40);
        fourCell.setCellValue("平均长度(cm)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(41);
        fourCell.setCellValue("注释");
        fourCell.setCellStyle(dataStyle);


        String[] split = ids.split(",");
        //查询日志数据
        for (int x = 0; x < split.length; x++) {
            Observerhooktime obhooktimes = observerhooktimeService.selectByidhooktime(split[x]);
            HSSFRow xRow = sheet.createRow(x + 2);//行

            HSSFCell xCell = xRow.createCell(0);//列
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getHooktimes() != null) {
                xCell.setCellValue(obhooktimes.getHooktimes());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(1);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getObstartdatehook() != null) {
                xCell.setCellValue(obhooktimes.getObstartdatehook());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(2);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getBuoybetween() != null) {
                xCell.setCellValue(obhooktimes.getBuoybetween());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(3);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getFloatlinelength() != null) {
                xCell.setCellValue(obhooktimes.getFloatlinelength());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(4);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getRopelength() != null) {
                xCell.setCellValue(obhooktimes.getRopelength());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(5);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getRopebetween() != null) {
                xCell.setCellValue(obhooktimes.getRopebetween());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(6);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getThrowropespeed() != null) {
                xCell.setCellValue(obhooktimes.getThrowropespeed());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(7);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getThrowropeshipspeed() != null) {
                xCell.setCellValue(obhooktimes.getThrowropeshipspeed());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(8);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getSinglebaskethook() != null) {
                xCell.setCellValue(obhooktimes.getSinglebaskethook());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(9);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getRealitytotalbaskets() != null) {
                xCell.setCellValue(obhooktimes.getRealitytotalbaskets());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(10);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getRealitytotalhook() != null) {
                xCell.setCellValue(obhooktimes.getRealitytotalhook());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(11);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getSharkhook() != null) {
                xCell.setCellValue(obhooktimes.getSharkhook());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(12);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getRealitysharkhook() != null) {
                xCell.setCellValue(obhooktimes.getRealitysharkhook());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(13);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getObbaskets() != null) {
                xCell.setCellValue(obhooktimes.getObbaskets());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(14);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getObservebasketsrate() != null) {
                xCell.setCellValue(obhooktimes.getObservebasketsrate());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(15);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getTargetspecies() != null) {
                xCell.setCellValue(obhooktimes.getTargetspecies());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(16);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getObstatedatehook() != null) {
                xCell.setCellValue(obhooktimes.getObstatedatehook());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(17);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getObenddatehook() != null) {
                xCell.setCellValue(obhooktimes.getObenddatehook());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(18);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getObupstatedate() != null) {
                xCell.setCellValue(obhooktimes.getObupstatedate());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(19);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getObupenddate() != null) {
                xCell.setCellValue(obhooktimes.getObupenddate());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(20);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getUphooksst() != null) {
                xCell.setCellValue(obhooktimes.getUphooksst());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(21);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getUphookpa() != null) {
                xCell.setCellValue(obhooktimes.getUphookpa());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(22);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getUphookweather() != null) {
                xCell.setCellValue(obhooktimes.getUphookweather());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(23);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getUphookbf() != null) {
                xCell.setCellValue(obhooktimes.getUphookbf());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(24);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getLday() != null) {
                xCell.setCellValue(obhooktimes.getLday());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(25);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getLatitudedegrees() != null) {
                xCell.setCellValue(obhooktimes.getLatitudedegrees());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(26);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getLatitudepoints() != null) {
                xCell.setCellValue(obhooktimes.getLatitudepoints());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(27);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getLongitudesdegrees() != null) {
                xCell.setCellValue(obhooktimes.getLongitudesdegrees());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(28);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getLongitudespoints() != null) {
                xCell.setCellValue(obhooktimes.getLongitudespoints());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(29);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getTunahooktype() != null) {
                xCell.setCellValue(obhooktimes.getTunahooktype());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(30);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getTunahooksize() != null) {
                xCell.setCellValue(obhooktimes.getTunahooksize());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(31);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getSharkhooktype() != null) {
                xCell.setCellValue(obhooktimes.getSharkhooktype());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(32);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getSharkhooksize() != null) {
                xCell.setCellValue(obhooktimes.getSharkhooksize());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(33);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getGlowsticks() != null) {
                xCell.setCellValue(obhooktimes.getGlowsticks());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(34);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getTurtlehook() != null) {
                xCell.setCellValue(obhooktimes.getTurtlehook());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(35);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getTurtlehooktype() != null) {
                xCell.setCellValue(obhooktimes.getTurtlehooktype());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(36);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getCatchbirds() != null) {
                xCell.setCellValue(obhooktimes.getCatchbirds());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(37);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getBaitfish1() != null) {
                xCell.setCellValue(obhooktimes.getBaitfish1());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(38);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getBaitfishavglength1() != null) {
                xCell.setCellValue(obhooktimes.getBaitfishavglength1());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(39);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getBaitfish2() != null) {
                xCell.setCellValue(obhooktimes.getBaitfish2());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(40);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getBaitfishavglength2() != null) {
                xCell.setCellValue(obhooktimes.getBaitfishavglength2());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            //注释
            xCell = xRow.createCell(41);
            xRow.setHeightInPoints((float) 20);
            if (obhooktimes.getObserverremark() != null) {
                xCell.setCellValue(obhooktimes.getObserverremark());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

        }

        response.setContentType("application/ms-excel;charset=UTF-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("作业钩次.xls", "UTF-8"))));
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


}