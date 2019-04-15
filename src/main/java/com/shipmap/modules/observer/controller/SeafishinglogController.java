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
import com.shipmap.modules.observer.model.Seafishinglog;
import com.shipmap.modules.observer.service.SeafishinglogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Seafishinglog相关的接口", tags = "user")
@RestController
@RequestMapping("observer/seafishinglog")
public class SeafishinglogController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(SeafishinglogController.class);

    @Autowired
    private SeafishinglogService seafishinglogService;


    ApiUtil api = new ApiUtil();

    /**
     * 查询Seafishinglog列表
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
        Page<Seafishinglog> seafishinglogPage = seafishinglogService.list(new Page<Seafishinglog>(page, limit), searchKey, searchValue, observerinfoid);
        return PageResult.toResult(seafishinglogPage.getRecords(), seafishinglogPage.getTotal());
    }

    /*根据id查询--详情*/
    @PutMapping("/{getinfobyid}")
    public Seafishinglog getinfobyid(@PathVariable("getinfobyid") String getinfobyid) {
        Seafishinglog selectByid = seafishinglogService.selectByid(getinfobyid);
        return selectByid;
    }

    /**
     * 添加Seafishinglog
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id，多个用','分割", required = true, dataType = "String"),
     **/
    @ApiOperation(value = "添加Seafishinglog", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seafishinglog", value = "Seafishinglog信息", required = true, dataType = "Seafishinglog"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @PostMapping()
    public Result add(Seafishinglog seafishinglog, HttpServletRequest request) {
        boolean result = seafishinglogService.add(seafishinglog, this.getActiveUser(request));
        if (result) {
            //添加成功
            return Result.success();
        } else {
            //添加失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 修改Seafishinglog
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id", required = true, dataType = "String"),
     **/
    @ApiOperation(value = "修改Seafishinglog", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Seafishinglog", value = "Seafishinglog信息", required = true, dataType = "Seafishinglog"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @PutMapping()
    public Result update(Seafishinglog seafishinglog, HttpServletRequest request) {
        boolean result = seafishinglogService.update(seafishinglog, this.getActiveUser(request));
        if (result) {
            //修改成功
            return Result.success();
        } else {
            //修改失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 删除Seafishinglog
     **/
    @DeleteMapping("/{seafishingid}")
    public Result delete(@PathVariable("seafishingid") String seafishingid) {
        boolean result = seafishinglogService.delete(seafishingid);
        if (result) {
            //删除成功
            return Result.success();
        } else {
            //删除失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /*删除Seafishinglog 多条*/
    @PutMapping("/deletAllSeafishingid")
    public Result delectAllSeafishingid(String dataSeafishingid) {
        boolean result = seafishinglogService.delectAllSeafishingid(dataSeafishingid);
        if (result) {
            //删除成功
            return Result.success();
        } else {
            //删除失败
            return Result.failure(ResultCode.ERROR);
        }

    }


    /*导入*/
    @RequestMapping(value = "fileexcel")
    @ResponseBody
    public Map<String, Object> Excel(HttpServletRequest request, @RequestParam("file") MultipartFile file, String obid) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> dataMap = new HashMap<String, Object>();

        boolean res = false;

        long start = new Date().getTime();
        List<Seafishinglog> list = new ArrayList<>();
        list = importExcel(file, 2, 1, Seafishinglog.class);
        System.out.println(new Date().getTime() - start);
        System.out.println(list);
        int testId = 1;
        int isInsert = 0;
        for (int i = 0; i < list.size(); i++) {
            Seafishinglog slog = new Seafishinglog();
            slog.setCreateTime(new Date());
            slog.setCreator(this.getActiveUser(request).getUsername());
            slog.setObserverinfoid(obid);
            slog.setHomeworkhook(list.get(i).getHomeworkhook());
            slog.setSeastartdatehook(sdf.format(list.get(i).getSeastartdatehookfu()));
            slog.setRealityhook(list.get(i).getRealityhook());
            slog.setChinesename(list.get(i).getChinesename());
            slog.setFullweightww(list.get(i).getFullweightww());
            slog.setWorkingweighgt(list.get(i).getWorkingweighgt());
            slog.setWorkingweighgx(list.get(i).getWorkingweighgx());
            slog.setExplain(list.get(i).getExplain());
            slog.setSeafishingremark(list.get(i).getSeafishingremark());
            res = seafishinglogService.InsertLayer(slog) > 0;
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
        titleCell.setCellValue("渔捞日志");//设置标题
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
        fourCell.setCellValue("作业钩次");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(1);
        fourCell.setCellValue("下钩开始日期");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(2);
        fourCell.setCellValue("实际下钩数");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(3);
        fourCell.setCellValue("中文名");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(4);
        fourCell.setCellValue("全重WW");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(5);
        fourCell.setCellValue("加工重量GT");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(6);
        fourCell.setCellValue("加工重量GX");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(7);
        fourCell.setCellValue("其它加工重量");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(8);
        fourCell.setCellValue("说明");
        fourCell.setCellStyle(dataStyle);


        String[] split = ids.split(",");
        //查询日志数据
        for (int x = 0; x < split.length; x++) {
            Seafishinglog seafishinglog = seafishinglogService.selectByid(split[x]);

            HSSFRow xRow = sheet.createRow(x + 2);//行
            HSSFCell xCell = xRow.createCell(0);//列

            xRow.setHeightInPoints((float) 20);
            if (seafishinglog.getHomeworkhook() != null) {
                xCell.setCellValue(seafishinglog.getHomeworkhook());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(1);
            xRow.setHeightInPoints((float) 20);
            if (seafishinglog.getSeastartdatehook() != null) {
                xCell.setCellValue(seafishinglog.getSeastartdatehook());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(2);
            xRow.setHeightInPoints((float) 20);
            if (seafishinglog.getRealityhook() != null) {
                xCell.setCellValue(seafishinglog.getRealityhook());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(3);
            xRow.setHeightInPoints((float) 20);
            if (seafishinglog.getChinesename() != null) {
                xCell.setCellValue(seafishinglog.getChinesename());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(4);
            xRow.setHeightInPoints((float) 20);
            if (seafishinglog.getFullweightww() != null) {
                xCell.setCellValue(seafishinglog.getFullweightww());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(5);
            xRow.setHeightInPoints((float) 20);
            if (seafishinglog.getWorkingweighgt() != null) {
                xCell.setCellValue(seafishinglog.getWorkingweighgt());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(6);
            xRow.setHeightInPoints((float) 20);
            if (seafishinglog.getWorkingweighgx() != null) {
                xCell.setCellValue(seafishinglog.getWorkingweighgx());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(7);
            xRow.setHeightInPoints((float) 20);
            if (seafishinglog.getOtherworkingweight() != null) {
                xCell.setCellValue(seafishinglog.getOtherworkingweight());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(8);
            xRow.setHeightInPoints((float) 20);
            if (seafishinglog.getExplain() != null) {
                xCell.setCellValue(seafishinglog.getExplain());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);


        }

        response.setContentType("application/ms-excel;charset=UTF-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("渔捞日志.xls", "UTF-8"))));
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