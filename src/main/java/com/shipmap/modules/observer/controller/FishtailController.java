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
import com.shipmap.modules.observer.model.Fishtail;
import com.shipmap.modules.observer.service.FishtailService;
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
@Api(value = "Fishtail相关的接口", tags = "user")
@RestController
@RequestMapping("/observer/fishtail")
public class FishtailController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(FishtailController.class);

    @Autowired
    private FishtailService fishtailService;

    ApiUtil api = new ApiUtil();

    /**
     * 查询Fishtail列表
     */

    @ApiOperation(value = "查询所有Fishtail", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "limit", value = "每页多少条", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "searchKey", value = "筛选条件字段", dataType = "String"),
            @ApiImplicitParam(name = "searchValue", value = "筛选条件关键字", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @GetMapping("list")
    public Result list(Integer page, Integer limit, String searchKey, String searchValue, String observerinfoid) {
        if (page == null) {
            page = 0;
            limit = 0;
        }
        if (StringUtil.isBlank(searchValue)) {
            searchKey = null;
        }
        Page<Fishtail> fishtailPage = fishtailService.list(new Page<Fishtail>(page, limit), searchKey, searchValue, observerinfoid);
        return PageResult.toResult(fishtailPage.getRecords(), fishtailPage.getTotal());
    }

    /**
     * 添加Fishtail
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id，多个用','分割", required = true, dataType = "String"),
     **/
    @ApiOperation(value = "添加Fishtail", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fishtail", value = "Fishtail信息", required = true, dataType = "Fishtail"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @PostMapping()
    public Result add(Fishtail fishtail, HttpServletRequest request) {
        boolean result = fishtailService.add(fishtail, this.getActiveUser(request));
        if (result) {
            //添加成功
            return Result.success();
        } else {
            //添加失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 修改Fishtail
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id", required = true, dataType = "String"),
     **/
    @ApiOperation(value = "修改Fishtail", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Fishtail", value = "Fishtail信息", required = true, dataType = "Fishtail"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @PutMapping()
    public Result update(Fishtail fishtail, HttpServletRequest request) {
        boolean result = fishtailService.update(fishtail, this.getActiveUser(request));
        if (result) {
            //修改成功
            return Result.success();
        } else {
            //修改失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 删除Fishtail
     **/
    @ApiOperation(value = "修改Fishtail", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Fishtail", value = "Fishtail信息", required = true, dataType = "Fishtail"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String")
    })
    @DeleteMapping("/{fishtailid}")
    public Result delete(@PathVariable("fishtailid") String fishtailid) {
        boolean result = fishtailService.delete(fishtailid);
        if (result) {
            //删除成功
            return Result.success();
        } else {
            //删除失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /*删除Fishtail 多条*/
    @PutMapping("/deletAllfishtailid")
    public Result deletAllfishtailid(String fishtailid) {
        boolean result = fishtailService.deletAllfishtailid(fishtailid);
        if (result) {
            //删除成功
            return Result.success();
        } else {
            //删除失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /*详情*/
    @PutMapping("/{fishtailid}")
    public Fishtail selectByidFishtail(@PathVariable("fishtailid") String fishtailid) {
        return fishtailService.selectByidFishtail(fishtailid);
    }


    /*导入*/
    @RequestMapping(value = "fileexcel")
    @ResponseBody
    public Map<String, Object> Excel(HttpServletRequest request, @RequestParam("file") MultipartFile file, String obid) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> dataMap = new HashMap<String, Object>();

        boolean res = false;

        long start = new Date().getTime();
        List<Fishtail> list = new ArrayList<>();
        list = importExcel(file, 2, 1, Fishtail.class);
        //System.out.println(new Date().getTime() - start);  导入花费时间
        // System.out.println(list);              数据
        int testId = 1;
        int isInsert = 0;
        for (int i = 0; i < list.size(); i++) {
            Fishtail fishtail = new Fishtail();
            fishtail.setCreator(this.getActiveUser(request).getUsername());
            fishtail.setCreateTime(new Date());
            fishtail.setObserverinfoid(obid);
            fishtail.setFtworkhooktimes(list.get(i).getFtworkhooktimes());
            fishtail.setFtstatehookdate(sdf.format(list.get(i).getFtstatehookdatefu()));
            fishtail.setFtbasketnum(list.get(i).getFtbasketnum());
            fishtail.setFtcatchhook(list.get(i).getFtcatchhook());
            fishtail.setChinesename(list.get(i).getChinesename());
            fishtail.setEnglishname(list.get(i).getEnglishname());
            fishtail.setScientificname(list.get(i).getScientificname());
            fishtail.setKindcode(list.get(i).getKindcode());
            fishtail.setCapturetimestate(list.get(i).getCapturetimestate());
            fishtail.setRetaindiscard(list.get(i).getRetaindiscard());
            fishtail.setFulllength(list.get(i).getFulllength());
            fishtail.setForeheadforklg(list.get(i).getForeheadforklg());
            fishtail.setLowerforklg(list.get(i).getLowerforklg());
            fishtail.setCaudalfin(list.get(i).getCaudalfin());
            fishtail.setConversionlbd(list.get(i).getConversionlbd());
            fishtail.setFtfullweight(list.get(i).getFtfullweight());
            fishtail.setFtprocesswgt(list.get(i).getFtprocesswgt());
            fishtail.setFtprocesswgx(list.get(i).getFtprocesswgx());
            fishtail.setFtprocesseachw(list.get(i).getFtprocesseachw());
            fishtail.setFtfoodclass(list.get(i).getFtfoodclass());
            fishtail.setFtsex(list.get(i).getFtsex());
            fishtail.setFtautumnperiod(list.get(i).getFtautumnperiod());
            fishtail.setEggtestisweight(list.get(i).getEggtestisweight());
            fishtail.setLiverweight(list.get(i).getLiverweight());
            fishtail.setPinnipedia(list.get(i).getPinnipedia());
            fishtail.setEggshellglandwidth(list.get(i).getEggshellglandwidth());
            fishtail.setOvarianeggsdiameter(list.get(i).getOvarianeggsdiameter());
            fishtail.setEmbryoswombnum(list.get(i).getEmbryoswombnum());
            fishtail.setSharkmantissa(list.get(i).getSharkmantissa());
            fishtail.setSexlength(list.get(i).getSexlength());
            fishtail.setFtdescribe(list.get(i).getFtdescribe());
            fishtail.setVertebraeno(list.get(i).getVertebraeno());
            fishtail.setFinspine(list.get(i).getFinspine());
            fishtail.setGastriccontents(list.get(i).getGastriccontents());
            fishtail.setGastriccontentsimg(list.get(i).getGastriccontentsimg());
            fishtail.setKindnum(list.get(i).getKindnum());
            fishtail.setGonadno(list.get(i).getGonadno());
            fishtail.setOrganizationno(list.get(i).getOrganizationno());
            fishtail.setFtrecord(list.get(i).getFtrecord());
            fishtail.setFtremark(list.get(i).getFtremark());
            res = fishtailService.InsertExcel(fishtail) > 0;
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
        titleCell.setCellValue("逐尾记录");//设置标题
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
        fourCell.setCellValue("观察筐数");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(3);
        fourCell.setCellValue("钓获钩位");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(4);
        fourCell.setCellValue("中文名");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(5);
        fourCell.setCellValue("英文名");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(6);
        fourCell.setCellValue("科学名");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(7);
        fourCell.setCellValue(" 种代码");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(8);
        fourCell.setCellValue("捕获时状态");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(9);
        fourCell.setCellValue("保留/丢弃");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(10);
        fourCell.setCellValue("全长TL (cm) ");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(11);
        fourCell.setCellValue("上额叉长FL");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(12);
        fourCell.setCellValue("下额叉长LJFL");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(13);
        fourCell.setCellValue("吻端-尾鳍凹洼长PCL");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(14);
        fourCell.setCellValue("转换长度（背鳍间长LBD）");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(15);
        fourCell.setCellValue("加工重量GT");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(16);
        fourCell.setCellValue("加工重量GX");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(17);
        fourCell.setCellValue("其它加工重量");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(18);
        fourCell.setCellValue("摄食等级");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(19);
        fourCell.setCellValue("性别");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(20);
        fourCell.setCellValue("成熟期");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(21);
        fourCell.setCellValue("卵/精巢重(g)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(22);
        fourCell.setCellValue("肝重(kg)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(23);
        fourCell.setCellValue("鳍脚内长(cm)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(24);
        fourCell.setCellValue("卵壳腺宽(mm)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(25);
        fourCell.setCellValue("卵巢卵径(mm)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(26);
        fourCell.setCellValue("子宫中胚胎个数");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(27);
        fourCell.setCellValue("子宫中幼鲨尾数");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(28);
        fourCell.setCellValue("幼鲨性别与长度(cm)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(29);
        fourCell.setCellValue("卵、胚胎、幼鲨描述");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(30);
        fourCell.setCellValue("椎骨编号(部位)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(31);
        fourCell.setCellValue("鳍棘编号(部位)");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(32);
        fourCell.setCellValue("胃含物编号");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(33);
        fourCell.setCellValue("胃含物图片编号");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(34);
        fourCell.setCellValue("胃含物明细（种类及其个体数）");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(35);
        fourCell.setCellValue("性腺编号");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(36);
        fourCell.setCellValue("组织编号");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(37);
        fourCell.setCellValue("船上是否记录");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(38);
        fourCell.setCellValue("观察到的其它现象");
        fourCell.setCellStyle(dataStyle);
        fourCell = fourRow.createCell(39);
        fourCell.setCellValue("备注");
        fourCell.setCellStyle(dataStyle);


        String[] split = ids.split(",");
        //查询日志数据
        for (int x = 0; x < split.length; x++) {
            Fishtail fishtail = fishtailService.selectByidFishtail(split[x]);

            HSSFRow xRow = sheet.createRow(x + 2);//行
            HSSFCell xCell = xRow.createCell(0);//列

            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFtworkhooktimes() != null) {
                xCell.setCellValue(fishtail.getFtworkhooktimes());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(1);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFtstatehookdate() != null) {
                xCell.setCellValue(fishtail.getFtstatehookdate());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(2);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFtbasketnum() != null) {
                xCell.setCellValue(fishtail.getFtbasketnum());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(3);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFtcatchhook() != null) {
                xCell.setCellValue(fishtail.getFtcatchhook());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(4);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getChinesename() != null) {
                xCell.setCellValue(fishtail.getChinesename());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(5);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getEnglishname() != null) {
                xCell.setCellValue(fishtail.getEnglishname());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(6);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getScientificname() != null) {
                xCell.setCellValue(fishtail.getScientificname());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(7);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getKindcode() != null) {
                xCell.setCellValue(fishtail.getKindcode());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(8);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getCapturetimestate() != null) {
                xCell.setCellValue(fishtail.getCapturetimestate());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(9);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getRetaindiscard() != null) {
                xCell.setCellValue(fishtail.getRetaindiscard());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(10);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFulllength() != null) {
                xCell.setCellValue(fishtail.getFulllength());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(11);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getForeheadforklg() != null) {
                xCell.setCellValue(fishtail.getForeheadforklg());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(12);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getLowerforklg() != null) {
                xCell.setCellValue(fishtail.getLowerforklg());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(13);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getCaudalfin() != null) {
                xCell.setCellValue(fishtail.getCaudalfin());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(14);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getConversionlbd() != null) {
                xCell.setCellValue(fishtail.getConversionlbd());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(15);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFtfullweight() != null) {
                xCell.setCellValue(fishtail.getFtfullweight());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(16);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFtprocesswgt() != null) {
                xCell.setCellValue(fishtail.getFtprocesswgt());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(17);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFtprocesswgx() != null) {
                xCell.setCellValue(fishtail.getFtprocesswgx());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(18);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFtprocesseachw() != null) {
                xCell.setCellValue(fishtail.getFtprocesseachw());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(19);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFtfoodclass() != null) {
                xCell.setCellValue(fishtail.getFtfoodclass());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(20);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFtsex() != null) {
                xCell.setCellValue(fishtail.getFtsex());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(21);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFtautumnperiod() != null) {
                xCell.setCellValue(fishtail.getFtautumnperiod());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(22);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getEggtestisweight() != null) {
                xCell.setCellValue(fishtail.getEggtestisweight());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(23);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getLiverweight() != null) {
                xCell.setCellValue(fishtail.getLiverweight());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(24);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getPinnipedia() != null) {
                xCell.setCellValue(fishtail.getPinnipedia());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(25);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getEggshellglandwidth() != null) {
                xCell.setCellValue(fishtail.getEggshellglandwidth());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(26);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getOvarianeggsdiameter() != null) {
                xCell.setCellValue(fishtail.getOvarianeggsdiameter());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(27);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getEmbryoswombnum() != null) {
                xCell.setCellValue(fishtail.getEmbryoswombnum());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(28);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getSharkmantissa() != null) {
                xCell.setCellValue(fishtail.getSharkmantissa());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(29);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getSexlength() != null) {
                xCell.setCellValue(fishtail.getSexlength());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(30);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFtdescribe() != null) {
                xCell.setCellValue(fishtail.getFtdescribe());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(31);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getVertebraeno() != null) {
                xCell.setCellValue(fishtail.getVertebraeno());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(32);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFinspine() != null) {
                xCell.setCellValue(fishtail.getFinspine());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(33);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getGastriccontents() != null) {
                xCell.setCellValue(fishtail.getGastriccontents());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(34);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getGastriccontentsimg() != null) {
                xCell.setCellValue(fishtail.getGastriccontentsimg());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(35);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getKindnum() != null) {
                xCell.setCellValue(fishtail.getKindnum());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(36);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getGonadno() != null) {
                xCell.setCellValue(fishtail.getGonadno());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(37);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getOrganizationno() != null) {
                xCell.setCellValue(fishtail.getOrganizationno());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(38);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFtrecord() != null) {
                xCell.setCellValue(fishtail.getFtrecord());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(39);
            xRow.setHeightInPoints((float) 20);
            if (fishtail.getFtremark() != null) {
                xCell.setCellValue(fishtail.getFtremark());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

        }

        response.setContentType("application/ms-excel;charset=UTF-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("逐尾记录.xls", "UTF-8"))));
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