package com.shipmap.modules.base.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.ResultCode;
import com.shipmap.framework.service.exception.RoleNotFoundException;
import com.shipmap.framework.utils.ApiUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.framework.utils.UUIDUtil;
import com.shipmap.modules.base.model.Port;
import com.shipmap.modules.base.model.PortDetail;
import com.shipmap.modules.base.service.PortDetailService;
import com.shipmap.modules.base.service.PortService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ${author}
 * @since 2019-03-27
 */
@RestController
@RequestMapping("base/port")
public class PortController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(PortController.class);
    @Autowired
    private PortService portService;
    @Autowired
    private PortDetailService portDetailService;

    ApiUtil api = new ApiUtil();

    /**
     * 导出
     *
     * @param request
     * @param response
     * @param id
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "export")
//    @ResponseBody
    public Map<String, Object> export(HttpServletRequest request, HttpServletResponse response, String id) throws Exception {

//       getActiveUser();
        Map<String, Object> dataMap = new HashMap<String, Object>();
//        System.out.println("id============================export===============export================" + id);
        Wrapper<Port> portwrapper = new EntityWrapper<>();

        if (StringUtil.isNotBlank(id)) {
            portwrapper.like("id", id);
        }
        List<Port> portlist = portService.selectList(portwrapper);
//       portService.selectAll(id);
//        System.out.println("list.size()=============================="+portlist.size());
//        list.get(0).getId();
//        System.out.println("  list.get(0).getId();====================="+  list.get(0).getId());
//        System.out.println(" list.get(0).getChinaname();=============="+ list.get(0).getChinaname());
        Wrapper<PortDetail> pdetailwrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(id)) {
            pdetailwrapper.like("portid", id);
        }
        List<PortDetail> pDetailist = portDetailService.selectList(pdetailwrapper);

//        System.out.println("=================ExcelOut================");
        String fileName = "港口采样信息" + ".xls";
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet");
        sheet.setDefaultRowHeightInPoints((float) 15.95);
//			sheet.protectSheet("yingtexun");
        sheet.setAutobreaks(true);
        sheet.setColumnWidth(0, 5500);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 5500);
        sheet.setColumnWidth(3, 5500);
        sheet.setColumnWidth(4, 5500);
        sheet.setColumnWidth(5, 3600);
        sheet.setColumnWidth(6, 2600);
        sheet.setColumnWidth(7, 2600);
        sheet.setColumnWidth(8, 2600);
        sheet.setColumnWidth(9, 2600);
        sheet.setColumnWidth(10, 2600);
        sheet.setColumnWidth(11, 5000);
        sheet.setColumnWidth(12, 5000);
        // 总共有多少列
        // int columnWidth = 13;
        // 标题行
        CellStyle titleCellStyle = createStyles(wb).get("title");
        HSSFRow titleRow = sheet.createRow(0);
        HSSFRow twoRow = sheet.createRow(1);
        HSSFCell titleCell = titleRow.createCell(0);
        titleRow.setHeightInPoints((float) 26);
        twoRow.setHeightInPoints((float) 13.5);
        titleCell.setCellValue("中国金枪鱼港口取样记录表");
        titleCell.setCellStyle(titleCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 1, 0, 12);
        sheet.addMergedRegion(cellRangeAddress);

        // 第2行
        CellStyle secondTitleCellStyle = createStyles(wb).get("secondTitle");
        HSSFRow row = sheet.createRow(2);
        HSSFCell cell = row.createCell(0);
        row.setHeightInPoints((float) 23);
        cell.setCellValue("CHINESE TUNA PORT SAMPLING RECORDING FORMS");
        cell.setCellStyle(secondTitleCellStyle);
        cellRangeAddress = new CellRangeAddress(2, 2, 0, 12);
        sheet.addMergedRegion(cellRangeAddress);

        // 查找存在的值
        String portVal = null;
        String companyVal = null;
        String fishingareaVal = null;
        String fishingtimeVal = null;
        String vesselvaVal = null;
        String sampltimeStrVal = null;
        String recordervaVal = null;
        String weatherVal = null;
        for (int j = 0; j < portlist.size(); j++) {

            String port = portlist.get(j).getPortname();
            if (port != null && !"".equals(port)) {
                portVal = port;
            } else {
                portVal = "";
            }

            String company = portlist.get(j).getFishery();
            if (company != null && !"".equals(company)) {
                companyVal = company;
            } else {
                companyVal = "";
            }

            String fishingarea = portlist.get(j).getJobarea();
            if (fishingarea != null && !"".equals(fishingarea)) {
                fishingareaVal = fishingarea;
            } else {
                fishingareaVal = "";
            }

            String fishingtime = portlist.get(j).getFishingtime();
            if (fishingtime != null && !"".equals(fishingtime)) {
                fishingtimeVal = fishingtime;
            } else {
                fishingtimeVal = "";
            }

            String vesselva = portlist.get(j).getFishname();
            if (vesselva != null && !"".equals(vesselva)) {
                vesselvaVal = vesselva;
            } else {
                vesselvaVal = "";
            }


            String sampltime = portlist.get(j).getSamplingtime();
            if (sampltime != null && !"".equals(sampltime)) {
                sampltimeStrVal = sampltime;
            } else {
                sampltimeStrVal = "";
            }

            String recorderva = portlist.get(j).getRecord();
            if (recorderva != null && !"".equals(recorderva)) {
                recordervaVal = recorderva;
            } else {
                recordervaVal = "";
            }

            String weather = portlist.get(j).getNwp();
            if (weather != null && !"".equals(weather)) {
                weatherVal = weather;
            } else {
                weatherVal = "";
            }
        }


        // 第3行
        CellStyle portCellStyle = createStyles(wb).get("menu");
        HSSFRow menurow = sheet.createRow(3);
        HSSFCell menucell = menurow.createCell(0);
        menurow.setHeightInPoints((float) 35);
        menucell.setCellValue("港口名称port");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 0, 0);
//        sheet.addMergedRegion(cellRangeAddress);

        menucell = menurow.createCell(1);
        menucell.setCellValue(portVal);
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 1, 1);
//        sheet.addMergedRegion(cellRangeAddress);

        menucell = menurow.createCell(2);
        menucell.setCellValue("渔业公司company");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 2, 2);
//        sheet.addMergedRegion(cellRangeAddress);

        menucell = menurow.createCell(3);
        menucell.setCellValue(companyVal);
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 3, 3);
//        sheet.addMergedRegion(cellRangeAddress);

        menucell = menurow.createCell(4);
        menucell.setCellValue("作业范围fishing area");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 4, 4);
//        sheet.addMergedRegion(cellRangeAddress);
        menucell = menurow.createCell(5);
        menucell.setCellValue(fishingareaVal);
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 5, 7);
        sheet.addMergedRegion(cellRangeAddress);
        menucell = menurow.createCell(11);


        menucell = menurow.createCell(6);
        menucell.setCellValue("");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 6, 6);
//        sheet.addMergedRegion(cellRangeAddress);

        menucell = menurow.createCell(7);
        menucell.setCellValue("");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 7, 7);
//        sheet.addMergedRegion(cellRangeAddress);

        menucell = menurow.createCell(8);
        menucell.setCellValue("");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 8, 8);
//        sheet.addMergedRegion(cellRangeAddress);


        menucell = menurow.createCell(9);
        menucell.setCellValue("");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 9, 9);
//        sheet.addMergedRegion(cellRangeAddress);


        menucell = menurow.createCell(10);
        menucell.setCellValue("");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 10, 10);
//        sheet.addMergedRegion(cellRangeAddress);

        menucell = menurow.createCell(11);
        menucell.setCellValue("作业时间fishing time");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 11, 11);
//        sheet.addMergedRegion(cellRangeAddress);


        menucell = menurow.createCell(12);
        menucell.setCellValue(fishingtimeVal);
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 12, 12);
//        sheet.addMergedRegion(cellRangeAddress);


        CellStyle fourCellStyle = createStyles(wb).get("menu");
        HSSFRow fourrow = sheet.createRow(4);
        HSSFCell fourcell = fourrow.createCell(0);
        fourrow.setHeightInPoints((float) 35);
        fourcell = fourrow.createCell(0);
        fourcell.setCellValue("渔船名Vessel");
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 0, 0);
//        sheet.addMergedRegion(cellRangeAddress);//合并单元格
        fourcell = fourrow.createCell(1);
        fourcell.setCellValue(vesselvaVal);
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 1, 1);
//        sheet.addMergedRegion(cellRangeAddress);

        fourcell = fourrow.createCell(2);
        fourcell.setCellValue("采样时间sampling time");
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 2, 2);
//        sheet.addMergedRegion(cellRangeAddress);

        fourcell = fourrow.createCell(3);
        fourcell.setCellValue(sampltimeStrVal);
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 3, 3);
//        sheet.addMergedRegion(cellRangeAddress);
        fourcell = fourrow.createCell(4);
        fourcell.setCellValue("记录者recorder");
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 4, 4);
//        sheet.addMergedRegion(cellRangeAddress);
        fourcell = fourrow.createCell(5);
        fourcell.setCellValue(recordervaVal);
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 5, 7);
        sheet.addMergedRegion(cellRangeAddress);
        fourcell = fourrow.createCell(11);

        fourcell = fourrow.createCell(8);
        fourcell.setCellValue("");
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 8, 8);
//        sheet.addMergedRegion(cellRangeAddress);

        fourcell = fourrow.createCell(9);
        fourcell.setCellValue("");
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 9, 9);
//        sheet.addMergedRegion(cellRangeAddress);

        fourcell = fourrow.createCell(10);
        fourcell.setCellValue("");
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 10, 10);
//        sheet.addMergedRegion(cellRangeAddress);

        fourcell = fourrow.createCell(11);
        fourcell.setCellValue("天气情况weather");
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 11, 11);
//        sheet.addMergedRegion(cellRangeAddress);

        fourcell = fourrow.createCell(12);
        fourcell.setCellValue(weatherVal);
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 12, 12);
//        sheet.addMergedRegion(cellRangeAddress);

        CellStyle fiveCellStyle = createStyles(wb).get("menu");
        HSSFRow fiverow = sheet.createRow(5);
        HSSFCell fivecell = fiverow.createCell(0);
        fiverow.setHeightInPoints((float) 25);
        fivecell = fiverow.createCell(0);
        fivecell.setCellValue("采样时间");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 0, 0);
//        sheet.addMergedRegion(cellRangeAddress);
        fivecell = fiverow.createCell(1);
        fivecell.setCellValue("序号");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 1, 1);
//        sheet.addMergedRegion(cellRangeAddress);
        fivecell = fiverow.createCell(2);
        fivecell.setCellValue("中文名");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 2, 2);
//        sheet.addMergedRegion(cellRangeAddress);
        fivecell = fiverow.createCell(3);
        fivecell.setCellValue("英文名");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 3, 3);
//        sheet.addMergedRegion(cellRangeAddress);
        fivecell = fiverow.createCell(4);
        fivecell.setCellValue("拉丁文名");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 4, 4);
//        sheet.addMergedRegion(cellRangeAddress);
        fivecell = fiverow.createCell(5);
        fivecell.setCellValue("缩写");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 5, 5);
//        sheet.addMergedRegion(cellRangeAddress);
        fivecell = fiverow.createCell(6);
        fivecell.setCellValue("长度(cm)");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 6, 10);
        sheet.addMergedRegion(cellRangeAddress);

        fivecell = fiverow.createCell(7);
        fivecell.setCellValue("");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 7, 7);
//        sheet.addMergedRegion(cellRangeAddress);

        fivecell = fiverow.createCell(11);
        fivecell.setCellValue("重量(KG)");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 11, 11);
//        sheet.addMergedRegion(cellRangeAddress);
        fivecell = fiverow.createCell(12);
        fivecell.setCellValue("备注");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 12, 12);
//        sheet.addMergedRegion(cellRangeAddress);

        //菜单
        CellStyle menuCellStyle = createStyles(wb).get("menut");
        row = sheet.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("SAMPLING TIME");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(1);
        cell.setCellValue("No.");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(2);
        cell.setCellValue("CHINESE NAME");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(3);
        cell.setCellValue("ENGLISH NAME ");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(4);
        cell.setCellValue("SCIENTIFIC NAME ");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(5);
        cell.setCellValue("SPECIESE CODE");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(6);
        cell.setCellValue("HL");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(7);
        cell.setCellValue("LTD");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(8);
        cell.setCellValue("BL(躯干长)");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(9);
        cell.setCellValue("GPL");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(10);
        cell.setCellValue("FL");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(11);
        cell.setCellValue("WEIGHT");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(12);
        cell.setCellValue("REMARKS");
        cell.setCellStyle(menuCellStyle);

        //no排序
        for (int x = 0; x < pDetailist.size() - 1; x++) {
            for (int j = 0; j < pDetailist.size() - x - 1; j++) {
                if (pDetailist.get(j).getNum() != null && !"".equals(pDetailist.get(j).getNum()) && pDetailist.get(j + 1).getNum() != null && !"".equals(pDetailist.get(j + 1).getNum())) {
                    if (pDetailist.get(j).getNum().matches("[0-9]\\d*") && pDetailist.get(j + 1).getNum().matches("[0-9]\\d*")) {
                        if (Integer.parseInt(pDetailist.get(j).getNum()) > Integer.parseInt(pDetailist.get(j + 1).getNum())) {
                            pDetailist.add(j, pDetailist.get(j + 1));
                            pDetailist.remove(j + 2);
                        }
                    }
                }
            }
        }


        CellStyle dataCellStyle = createStyles(wb).get("dataStyle");

        //插入数据
        for (int i = 0; i < pDetailist.size(); i++) {
            row = sheet.createRow(7 + i);
            row.setHeightInPoints((float) 15.95);
            cell = row.createCell(0);
            cell.setCellValue(pDetailist.get(i).getSamplingtime());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(1);
            cell.setCellValue(pDetailist.get(i).getNum());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(2);
            cell.setCellValue(pDetailist.get(i).getChinaname());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(3);
            cell.setCellValue(pDetailist.get(i).getEnglishname());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(4);
            cell.setCellValue(pDetailist.get(i).getLatinname());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(5);
            cell.setCellValue(pDetailist.get(i).getAbbreviation());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(6);
            cell.setCellValue(pDetailist.get(i).getHl());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(7);
            cell.setCellValue(pDetailist.get(i).getLtd());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(8);
            cell.setCellValue(pDetailist.get(i).getBl());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(9);
            cell.setCellValue(pDetailist.get(i).getGpl());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(10);
            cell.setCellValue(pDetailist.get(i).getFl());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(11);
            cell.setCellValue(pDetailist.get(i).getKg());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(12);
            cell.setCellValue(pDetailist.get(i).getAnnotation());
            cell.setCellStyle(dataCellStyle);
        }


        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
        try {
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return api.returnJson(2, "导出", dataMap);
    }


    /**
     * 导出模板下载
     *
     * @param request
     * @param response
     * @param id
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "exportTemp")
//    @ResponseBody
    public Map<String, Object> exportTemp(HttpServletRequest request, HttpServletResponse response, String id) throws Exception {

//       getActiveUser();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        System.out.println("id============================export===============export================" + id);
        Wrapper<Port> portwrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(id)) {
            portwrapper.like("id", id);
        }
        List<Port> portlist = portService.selectList(portwrapper);
//       portService.selectAll(id);
//        System.out.println("list.size()=============================="+portlist.size());
//        list.get(0).getId();
//        System.out.println("  list.get(0).getId();====================="+  list.get(0).getId());
//        System.out.println(" list.get(0).getChinaname();=============="+ list.get(0).getChinaname());
        Wrapper<PortDetail> pdetailwrapper = new EntityWrapper<>();
        if (StringUtil.isNotBlank(id)) {
            pdetailwrapper.like("portid", id);
        }
        List<PortDetail> pDetailist = portDetailService.selectList(pdetailwrapper);

        System.out.println("=================ExcelOut================");
        String fileName = "港口采样信息" + ".xls";
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet");
        sheet.setDefaultRowHeightInPoints((float) 15.95);
//			sheet.protectSheet("yingtexun");
        sheet.setAutobreaks(true);
        sheet.setColumnWidth(0, 5500);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 5500);
        sheet.setColumnWidth(3, 5500);
        sheet.setColumnWidth(4, 5500);
        sheet.setColumnWidth(5, 3600);
        sheet.setColumnWidth(6, 2600);
        sheet.setColumnWidth(7, 2600);
        sheet.setColumnWidth(8, 2600);
        sheet.setColumnWidth(9, 2600);
        sheet.setColumnWidth(10, 2600);
        sheet.setColumnWidth(11, 5000);
        sheet.setColumnWidth(12, 5000);
        // 总共有多少列
        // int columnWidth = 13;
        // 标题行
        CellStyle titleCellStyle = createStyles(wb).get("title");
        HSSFRow titleRow = sheet.createRow(0);
        HSSFRow twoRow = sheet.createRow(1);
        HSSFCell titleCell = titleRow.createCell(0);
        titleRow.setHeightInPoints((float) 26);
        twoRow.setHeightInPoints((float) 13.5);
        titleCell.setCellValue("中国金枪鱼港口取样记录表");
        titleCell.setCellStyle(titleCellStyle);
        // 起始行号，终止行号， 起始列号，终止列号
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 1, 0, 12);
        sheet.addMergedRegion(cellRangeAddress);

        // 第2行
        CellStyle secondTitleCellStyle = createStyles(wb).get("secondTitle");
        HSSFRow row = sheet.createRow(2);
        HSSFCell cell = row.createCell(0);
        row.setHeightInPoints((float) 23);
        cell.setCellValue("CHINESE TUNA PORT SAMPLING RECORDING FORMS");
        cell.setCellStyle(secondTitleCellStyle);
        cellRangeAddress = new CellRangeAddress(2, 2, 0, 12);
        sheet.addMergedRegion(cellRangeAddress);

       /* // 查找存在的值
        String portVal = null;
        String companyVal = null;
        String fishingareaVal = null;
        String fishingtimeVal = null;
        String vesselvaVal = null;
        String sampltimeStrVal = null;
        String recordervaVal = null;
        String weatherVal = null;
        for (int j = 0; j < portlist.size(); j++) {

            String port = portlist.get(j).getPortname();
            if (port != null && !"".equals(port)) {
                portVal = port;
            } else {
                portVal = "";
            }

            String company = portlist.get(j).getFishery();
            if (company != null && !"".equals(company)) {
                companyVal = company;
            } else {
                companyVal = "";
            }

            String fishingarea = portlist.get(j).getJobarea();
            if (fishingarea != null && !"".equals(fishingarea)) {
                fishingareaVal = fishingarea;
            } else {
                fishingareaVal = "";
            }

            String fishingtime = portlist.get(j).getCreatetime();
            if (fishingtime != null && !"".equals(fishingtime)) {
                fishingtimeVal = fishingtime;
            } else {
                fishingtimeVal = "";
            }

            String vesselva = portlist.get(j).getFishname();
            if (vesselva != null && !"".equals(vesselva)) {
                vesselvaVal = vesselva;
            } else {
                vesselvaVal = "";
            }


            String sampltime = portlist.get(j).getSamplingtime();
            if (sampltime != null && !"".equals(sampltime)) {
                sampltimeStrVal = sampltime;
            } else {
                sampltimeStrVal = "";
            }

            String recorderva = portlist.get(j).getRecord();
            if (recorderva != null && !"".equals(recorderva)) {
                recordervaVal = recorderva;
            } else {
                recordervaVal = "";
            }

            String weather = portlist.get(j).getNwp();
            if (weather != null && !"".equals(weather)) {
                weatherVal = weather;
            } else {
                weatherVal = "";
            }
        }*/


        // 第3行
        CellStyle portCellStyle = createStyles(wb).get("menu");
        HSSFRow menurow = sheet.createRow(3);
        HSSFCell menucell = menurow.createCell(0);
        menurow.setHeightInPoints((float) 35);
        menucell.setCellValue("港口名称port");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 0, 0);
//        sheet.addMergedRegion(cellRangeAddress);

        menucell = menurow.createCell(1);
//        menucell.setCellValue(portVal);
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 1, 1);
//        sheet.addMergedRegion(cellRangeAddress);

        menucell = menurow.createCell(2);
        menucell.setCellValue("渔业公司company");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 2, 2);
//        sheet.addMergedRegion(cellRangeAddress);

        menucell = menurow.createCell(3);
//        menucell.setCellValue(companyVal);
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 3, 3);
//        sheet.addMergedRegion(cellRangeAddress);

        menucell = menurow.createCell(4);
        menucell.setCellValue("作业范围fishing area");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 4, 4);
//        sheet.addMergedRegion(cellRangeAddress);
        menucell = menurow.createCell(5);
//        menucell.setCellValue(fishingareaVal);
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 5, 7);
        sheet.addMergedRegion(cellRangeAddress);
        menucell = menurow.createCell(11);


        menucell = menurow.createCell(6);
        menucell.setCellValue("");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 6, 6);
//        sheet.addMergedRegion(cellRangeAddress);

        menucell = menurow.createCell(7);
        menucell.setCellValue("");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 7, 7);
//        sheet.addMergedRegion(cellRangeAddress);

        menucell = menurow.createCell(8);
        menucell.setCellValue("");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 8, 8);
//        sheet.addMergedRegion(cellRangeAddress);


        menucell = menurow.createCell(9);
        menucell.setCellValue("");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 9, 9);
//        sheet.addMergedRegion(cellRangeAddress);


        menucell = menurow.createCell(10);
        menucell.setCellValue("");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 10, 10);
//        sheet.addMergedRegion(cellRangeAddress);

        menucell = menurow.createCell(11);
        menucell.setCellValue("作业时间fishing time");
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 11, 11);
//        sheet.addMergedRegion(cellRangeAddress);


        menucell = menurow.createCell(12);
//        menucell.setCellValue(fishingtimeVal);
        menucell.setCellStyle(portCellStyle);
        cellRangeAddress = new CellRangeAddress(3, 3, 12, 12);
//        sheet.addMergedRegion(cellRangeAddress);


        CellStyle fourCellStyle = createStyles(wb).get("menu");
        HSSFRow fourrow = sheet.createRow(4);
        HSSFCell fourcell = fourrow.createCell(0);
        fourrow.setHeightInPoints((float) 35);
        fourcell = fourrow.createCell(0);
        fourcell.setCellValue("渔船名Vessel");
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 0, 0);
//        sheet.addMergedRegion(cellRangeAddress);//合并单元格
        fourcell = fourrow.createCell(1);
//        fourcell.setCellValue(vesselvaVal);
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 1, 1);
//        sheet.addMergedRegion(cellRangeAddress);

        fourcell = fourrow.createCell(2);
        fourcell.setCellValue("采样时间sampling time");
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 2, 2);
//        sheet.addMergedRegion(cellRangeAddress);

        fourcell = fourrow.createCell(3);
//        fourcell.setCellValue(sampltimeStrVal);
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 3, 3);
//        sheet.addMergedRegion(cellRangeAddress);
        fourcell = fourrow.createCell(4);
        fourcell.setCellValue("记录者recorder");
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 4, 4);
//        sheet.addMergedRegion(cellRangeAddress);
        fourcell = fourrow.createCell(5);
//        fourcell.setCellValue(recordervaVal);
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 5, 7);
        sheet.addMergedRegion(cellRangeAddress);
        fourcell = fourrow.createCell(11);

        fourcell = fourrow.createCell(8);
        fourcell.setCellValue("");
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 8, 8);
//        sheet.addMergedRegion(cellRangeAddress);

        fourcell = fourrow.createCell(9);
        fourcell.setCellValue("");
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 9, 9);
//        sheet.addMergedRegion(cellRangeAddress);

        fourcell = fourrow.createCell(10);
        fourcell.setCellValue("");
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 10, 10);
//        sheet.addMergedRegion(cellRangeAddress);

        fourcell = fourrow.createCell(11);
        fourcell.setCellValue("天气情况weather");
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 11, 11);
//        sheet.addMergedRegion(cellRangeAddress);

        fourcell = fourrow.createCell(12);
//        fourcell.setCellValue(weatherVal);
        fourcell.setCellStyle(fourCellStyle);
        cellRangeAddress = new CellRangeAddress(4, 4, 12, 12);
//        sheet.addMergedRegion(cellRangeAddress);

        CellStyle fiveCellStyle = createStyles(wb).get("menu");
        HSSFRow fiverow = sheet.createRow(5);
        HSSFCell fivecell = fiverow.createCell(0);
        fiverow.setHeightInPoints((float) 25);
        fivecell = fiverow.createCell(0);
        fivecell.setCellValue("采样时间");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 0, 0);
//        sheet.addMergedRegion(cellRangeAddress);
        fivecell = fiverow.createCell(1);
        fivecell.setCellValue("序号");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 1, 1);
//        sheet.addMergedRegion(cellRangeAddress);
        fivecell = fiverow.createCell(2);
        fivecell.setCellValue("中文名");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 2, 2);
//        sheet.addMergedRegion(cellRangeAddress);
        fivecell = fiverow.createCell(3);
        fivecell.setCellValue("英文名");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 3, 3);
//        sheet.addMergedRegion(cellRangeAddress);
        fivecell = fiverow.createCell(4);
        fivecell.setCellValue("拉丁文名");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 4, 4);
//        sheet.addMergedRegion(cellRangeAddress);
        fivecell = fiverow.createCell(5);
        fivecell.setCellValue("缩写");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 5, 5);
//        sheet.addMergedRegion(cellRangeAddress);
        fivecell = fiverow.createCell(6);
        fivecell.setCellValue("长度(cm)");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 6, 10);
        sheet.addMergedRegion(cellRangeAddress);

        fivecell = fiverow.createCell(7);
        fivecell.setCellValue("");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 7, 7);
//        sheet.addMergedRegion(cellRangeAddress);

        fivecell = fiverow.createCell(11);
        fivecell.setCellValue("重量(KG)");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 11, 11);
//        sheet.addMergedRegion(cellRangeAddress);
        fivecell = fiverow.createCell(12);
        fivecell.setCellValue("备注");
        fivecell.setCellStyle(fiveCellStyle);
        cellRangeAddress = new CellRangeAddress(5, 5, 12, 12);
//        sheet.addMergedRegion(cellRangeAddress);

        //菜单
        CellStyle menuCellStyle = createStyles(wb).get("menut");
        row = sheet.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("SAMPLING TIME");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(1);
        cell.setCellValue("No.");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(2);
        cell.setCellValue("CHINESE NAME");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(3);
        cell.setCellValue("ENGLISH NAME ");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(4);
        cell.setCellValue("SCIENTIFIC NAME ");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(5);
        cell.setCellValue("SPECIESE CODE");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(6);
        cell.setCellValue("HL");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(7);
        cell.setCellValue("LTD");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(8);
        cell.setCellValue("BL(躯干长)");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(9);
        cell.setCellValue("GPL");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(10);
        cell.setCellValue("FL");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(11);
        cell.setCellValue("WEIGHT");
        cell.setCellStyle(menuCellStyle);
        cell = row.createCell(12);
        cell.setCellValue("REMARKS");
        cell.setCellStyle(menuCellStyle);

     /*   //no排序
        for (int x = 0; x < pDetailist.size() - 1; x++) {
            for (int j = 0; j < pDetailist.size() - x - 1; j++) {
                if (pDetailist.get(j).getNum() != null && !"".equals(pDetailist.get(j).getNum()) && pDetailist.get(j + 1).getNum() != null && !"".equals(pDetailist.get(j + 1).getNum())) {
                    if (pDetailist.get(j).getNum().matches("[0-9]\\d*") && pDetailist.get(j + 1).getNum().matches("[0-9]\\d*")) {
                        if (Integer.parseInt(pDetailist.get(j).getNum()) > Integer.parseInt(pDetailist.get(j + 1).getNum())) {
                            pDetailist.add(j, pDetailist.get(j + 1));
                            pDetailist.remove(j + 2);
                        }
                    }
                }
            }
        }
*/

        CellStyle dataCellStyle = createStyles(wb).get("dataStyle");

       /* //插入数据
        for (int i = 0; i < pDetailist.size(); i++) {
            row = sheet.createRow(7 + i);
            row.setHeightInPoints((float) 15.95);
            cell = row.createCell(0);
            cell.setCellValue(pDetailist.get(i).getSamplingtime());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(1);
            cell.setCellValue(pDetailist.get(i).getNum());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(2);
            cell.setCellValue(pDetailist.get(i).getChinaname());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(3);
            cell.setCellValue(pDetailist.get(i).getEnglishname());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(4);
            cell.setCellValue(pDetailist.get(i).getLatinname());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(5);
            cell.setCellValue(pDetailist.get(i).getAbbreviation());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(6);
            cell.setCellValue(pDetailist.get(i).getHl());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(7);
            cell.setCellValue(pDetailist.get(i).getLtd());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(8);
            cell.setCellValue(pDetailist.get(i).getBl());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(9);
            cell.setCellValue(pDetailist.get(i).getGpl());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(10);
            cell.setCellValue(pDetailist.get(i).getFl());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(11);
            cell.setCellValue(pDetailist.get(i).getKg());
            cell.setCellStyle(dataCellStyle);
            cell = row.createCell(12);
            cell.setCellValue(pDetailist.get(i).getAnnotation());
            cell.setCellStyle(dataCellStyle);
        }*/


        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
        try {
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return api.returnJson(2, "导出", dataMap);
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
        Font titleFont = wb.createFont();
        titleFont.setFontName("宋体");
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(titleFont);
        styles.put("title", style);

        //英文标题行
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        Font secondTitleFont = wb.createFont();
        secondTitleFont.setFontName("宋体");
        secondTitleFont.setFontHeightInPoints((short) 8);
        secondTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setWrapText(true);
        style.setFont(secondTitleFont);
        styles.put("secondTitle", style);


        //3 4 5 行
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
        menuFont.setFontHeightInPoints((short) 12);
        menuFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(menuFont);
        styles.put("menu", style);


        //6行
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
        Font meFont = wb.createFont();
        meFont.setFontName("宋体");
        meFont.setFontHeightInPoints((short) 10);
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillBackgroundColor(HSSFColor.YELLOW.index);
        style.setFont(meFont);
        styles.put("menut", style);

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
        datasFont.setFontHeightInPoints((short) 12);
        style.setFont(datasFont);
        styles.put("dataStyle", style);

        return styles;
    }


    /**
     * 处理Excel解析的方法
     *
     * @param file 前台上传的文件对象
     * @return
     */
    @RequestMapping(value = "fileUpload")
//    @ResponseBody
    @Transactional
    public Map<String, Object> Excel(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String type = request.getParameter("type");
        System.out.println("type==========Excel=======Excel===========" + type);
        try {
            //文件后缀名
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

            Workbook hwb = null;
            if (!file.isEmpty()) {
                if (!suffix.equals(".xls") && !suffix.equals(".xlsx")) {
                    return api.returnJson(2, "导入港口采样记录数据！失败信息：文档格式不正确！", dataMap);
                }
                InputStream inputStream = file.getInputStream();
                Map<String, Object> map1 = null;
                if (".xls".equals(suffix)) {
                    hwb = new HSSFWorkbook(inputStream);
                    map1 = ExcelParser(request, hwb, dataMap);
                    return map1;
//                return read2003Excel(file);
                } else if (".xlsx".equals(suffix)) {
                    hwb = new XSSFWorkbook(inputStream);
                    map1 = ExcelParser(request, hwb, dataMap);
                    return map1;
//                return map2;
//                return read2007Excel(file);
                } else {
                    return api.returnJson(2, "导入失败！", dataMap);
                }
            } else {
                return api.returnJson(2, "文件不存在", dataMap);
            }

        } catch (Exception e) {
            //打印错误堆栈信息
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return api.returnJson(2, "导入港口采样记录数据！失败信息：文档格式不正确！123", dataMap);
        }
//        ExcelParser(fileName);
    }

    @Transactional
    public Map<String, Object> ExcelParser(HttpServletRequest request, Workbook w, Map<String, Object> dataMap) throws Exception {
        if (w != null) {
            try {
                int a = w.getNumberOfSheets();
                System.out.println("=============getNumberOfSheets=========getNumberOfSheets==========" + a);
                String type = request.getParameter("type");
//            System.out.println("type==========Excel=======Excel==========="+type);


//        Sheet sheet1=w.getSheetAt(0);
//            Row r=sheet1.getRow(5);
//            Cell c=r.getCell(0);
//            System.out.println("c.toString()=================="+c.toString());
//            if (c.toString().equals("采样时间")){
//                System.out.println("相等=====================");
//            }


//        sheet1.getPhysicalNumberOfRows();
//        int rowNum=sheet1.getLastRowNum();
          /*  Sheet sheet0 =  w.getSheetAt(0);
            Row row0=sheet0.getRow(5);
            row0.getPhysicalNumberOfCells();
            System.out.println("row0.getPhysicalNumberOfCells()===row0.getPhysicalNumberOfCells()========="+row0.getPhysicalNumberOfCells());*/
                //todo 判断sheet是否为空
                for (int n = 0; n < w.getNumberOfSheets(); n++) {//获取每个Sheet表
                    Sheet sheet = w.getSheetAt(n);
                    if (sheet == null) {
                        continue;
                    }
       /* if (sheet==null) {
            return api.returnJson(2,"导入港口采样记录数据！失败信息：文档格式不正确！ sheet页为null",dataMap);
        }*/
          /*  Row r=sheet.getRow(5);
            System.out.println("====================================r"+r.toString());
            Cell c=r.getCell(0);
            System.out.println("c.toString()=================="+c.toString());
            if (c.toString().equals("采样时间")){
                System.out.println("相等=====================");
            }*/

                    Port port = new Port();
//            DateUtil.parseDate(new Date());
//        port.setCreatetime(DateUtil.parseDate(new Date().toString(),"yyyy-MM-dd HH:mm:ss"));
//        port.setUpdatetime(DateUtil.parseDate(new Date().toString(),"yyyy-MM-dd HH:mm:ss"));
//            port.setCreatetime(new Date());
                    port.setUpdatetime(new Date());
                    System.out.println("" + port.getUpdatetime());

                    port.setType(type);
                    Row row1 = sheet.getRow(3);
                    if (row1 == null) {
                        continue;
                    }
                    Cell cell0 = row1.getCell(0);
//            System.out.println("c.toString()=================="+cell0.toString());
                    if (!cell0.toString().equals("港口名称port")) {
//                System.out.println("==========================123");
                        return api.returnJson(2, "导入港口采样记录数据！失败信息：文档格式不正确！行：" + (cell0.getRowIndex() + 1) + "---列：" + (cell0.getColumnIndex() + 1), dataMap);
//                System.out.println("相等=====================");
                    }
                    Cell cell1 = row1.getCell(1);


                    port.setPortname(cell1.toString());
           /* if (row1.getCell(1)==null ){
                continue;
            //            row1.createCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
            }else{*/

//            }


//            Cell cell1=row1.getCell(1); // 获取单元格
       /* if (cell1==null){
            continue;
        }*/

                    Cell cell2 = row1.getCell(3); // 获取单元格
                    System.out.println("====================cell2==" + cell2 + "================");
        /*if(cell2!=null){

        }*/
                    port.setFishery(cell2.toString());
                    Cell cell3 = row1.getCell(5); // 获取单元格

                    port.setJobarea(cell3.toString());
    /*    Cell celll=row1.getCell(11); // 获取单元格
        if (!celll.toString().equals("作业时间fishing time")){
//                int =(int)(cell0.getRowIndex()+1);
//                int rowindex=cell0.getRowIndex()+1;
            return api.returnJson(2,"导入港口采样记录数据！失败信息：文档格式不正确！行："+(celll.getRowIndex()+1)+"---列："+(celll.getColumnIndex()+1),dataMap);
//                System.out.println("相等=====================");
        }*/
                    Cell cell4 = row1.getCell(12); // 获取单元格
//        port.setCreatetime(DateUtil.parseDate(cell4.toString()));

                    port.setFishingtime(cell4.toString());
                    Row row2 = sheet.getRow(4);
     /*       Cell ce=row1.getCell(0);
//            System.out.println("c.toString()=================="+cell0.toString());
            if (!ce.toString().equals("渔船名Vessel")){
//                System.out.println("==========================123");
                return api.returnJson(2,"导入港口采样记录数据！失败信息：文档格式不正确！行："+(ce.getRowIndex()+1)+"---列："+(ce.getColumnIndex()+1),dataMap);
            }*/
                    Cell cell5 = row2.getCell(1); // 获取单元格
                    port.setFishname(cell5.toString());
                    Cell cell6 = row2.getCell(3); // 获取单元格
                    port.setSamplingtime(cell6.toString());
                    Cell cell7 = row2.getCell(5); // 获取单元格
                    port.setRecord(cell7.toString());
                    Cell cel = row2.getCell(11); // 获取单元格
                    if (!cel.toString().equals("天气情况weather")) {
//                int =(int)(cell0.getRowIndex()+1);
//                int rowindex=cell0.getRowIndex()+1;
                        return api.returnJson(2, "导入港口采样记录数据！失败信息：文档格式不正确！行：" + (cel.getRowIndex() + 1) + "---列：" + (cel.getColumnIndex() + 1), dataMap);
//                System.out.println("相等=====================");
                    }
                    Cell cell8 = row2.getCell(12); // 获取单元格
                    System.out.println("=======ssssssssssss===========-cell8.toString()" + cell8.toString());

                    port.setNwp(cell8.toString());
                    Row row3 = sheet.getRow(5);
                    Cell cee = row3.getCell(0);
//            System.out.println("c.toString()=================="+cell0.toString());
                    if (!cee.toString().equals("采样时间")) {
//                System.out.println("==========================123");
                        return api.returnJson(2, "导入港口采样记录数据！失败信息：文档格式不正确！行：" + (cee.getRowIndex() + 1) + "---列：" + (cee.getColumnIndex() + 1), dataMap);
//                System.out.println("相等=====================");
                    }
                    Cell ceell = row3.getCell(12);
//            System.out.println("c.toString()=================="+cell0.toString());
                    if (!ceell.toString().equals("备注")) {
//                System.out.println("==========================123");
                        return api.returnJson(2, "导入港口采样记录数据！失败信息：文档格式不正确！行：" + (ceell.getRowIndex() + 1) + "---列：" + (ceell.getColumnIndex() + 1), dataMap);
//                System.out.println("相等=====================");
                    }

                    portService.insert(port);
//        }


                    int rowNum1 = sheet.getLastRowNum();
                    System.out.println("=====rowNum1========rowNum1===rowNum1========rowNum1============" + rowNum1);
                    PortDetail portDetail = new PortDetail();
//                    int ii = 1 / 0;
                    for (int i = 7; i < rowNum1 + 1; i++) {
                        Row row = sheet.getRow(i);
                        if (row == null) {

                            continue;
                        }
                        System.out.println("======= port.getId();=========" + port.getId());
                        portDetail.setPortid(port.getId());
//               if(row!=null){
                        portDetail.setId(UUIDUtil.randomUUID32());

//            System.out.println("=======getId==="+list.get(0).getId());
                        /*if(row.getCell(0).getDateCellValue()==null){
                            row.
                        }*/
                        row.getCell(0).getDateCellValue();
                        portDetail.setSamplingtime(row.getCell(0).getDateCellValue());

//            portDetail.setSamplingtime(new SimpleDateFormat().parse(row.getCell(0).toString()));
                        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                        System.out.println("row.getCell(1).setCellType(Cell.CELL_TYPE_STRING)===row.getCell(1).getStringCellValue()===========" + row.getCell(1).getStringCellValue());
                        portDetail.setNum(row.getCell(1).getStringCellValue());
                        System.out.println("================row.getCell(1).getCellFormula()===============" + row.getCell(1));
                        portDetail.setChinaname(row.getCell(2).toString());
                        portDetail.setEnglishname(row.getCell(3).toString());
                        portDetail.setLatinname(row.getCell(4).toString());
                        portDetail.setAbbreviation(row.getCell(5).toString());
                        portDetail.setHl(row.getCell(6).toString());
                        portDetail.setLtd(row.getCell(7).toString());
                        portDetail.setBl(row.getCell(8).toString());
                        portDetail.setGpl(row.getCell(9).toString());
                        portDetail.setFl(row.getCell(10).toString());
                        portDetail.setKg(row.getCell(11).toString());
                        portDetail.setAnnotation(row.getCell(12).toString());
//            boolean insert=  portService.insertEntity(port);
//            portService.insert(port);
//            portDetailService.insert(portDetail);
                        int b = portDetailService.insertEntity(portDetail);

//               }
//         }
                    }
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return api.returnJson(2, "导入港口采样记录数据！失败信息：文档格式不正确！", dataMap);
            } catch (Exception e) {
                //打印错误堆栈信息
                e.printStackTrace();
                return api.returnJson(2, "导入港口采样记录数据！失败信息：文档格式不正确！", dataMap);
            }
//        }
        } else {
            return api.returnJson(1, "导入失败", dataMap);
        }
        return api.returnJson(1, "导入成功", dataMap);
    }


//    @RequestMapping(value="fileUpload",method=RequestMethod.POST)
/*    public void upload(@RequestParam("file")MultipartFile file, HttpServletRequest request) throws FileNotFoundException, IOException {

        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<PortDetail> list;
        try {
            //excel的数据
            list = ExcelImportUtil.importExcel(
                    file.getInputStream(),
                    PortDetail.class, params);
            System.out.println(list.size());
            System.out.println(list.get(0));
            for (int i = 0; i < list.size(); i++) {
                PortDetail portDetail = new PortDetail();
                portDetail.setId(UUIDUtil.randomUUID8());
                System.out.println("=======getId===" + list.get(0).getId());
                portDetail.setPortName(list.get(i).getPortName());
                System.out.println("=======getPortName===" + portDetail.getPortName());
                portDetail.setChinaName(list.get(i).getChinaName());
                System.out.println("=======getChinaName===" + portDetail.getChinaName());
                portDetail.setEnglishName(list.get(i).getEnglishName());
                System.out.println("=======getEnglishName===" + portDetail.getEnglishName());
                portDetail.setLatinName(list.get(i).getLatinName());
                portDetail.setAbbreviation(list.get(i).getAbbreviation());
                portDetail.setHl(list.get(i).getHl());
                portDetail.setLtd(list.get(i).getLtd());
                portDetail.setBl(list.get(i).getBl());
                portDetail.setGpl(list.get(i).getGpl());
                portDetail.setFl(list.get(i).getFl());
                portDetail.setKg(list.get(i).getKg());
                portDetail.setAnnotation(list.get(i).getAnnotation());
                portDetail.setNum(list.get(i).getNum());
                portDetail.setPortid(list.get(i).getPortid());
                boolean insert = portDetailService.insert(portDetail);
//            System.out.println("================================="+);
                System.out.println("===========insert====ExcelParser===============" + insert);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }*/


    /**
     * 查询Port列表
     */
    @GetMapping()
    public Result list(Integer page, Integer limit, String searchKey, String searchValue) {
        if (page == null) {
            page = 0;
            limit = 0;
        }
        if (StringUtil.isBlank(searchValue)) {
            searchKey = null;
        }
        Page<Port> portPage = portService.list(new Page<Port>(page, limit), searchKey, searchValue);
        return PageResult.toResult(portPage.getRecords(), portPage.getTotal());
    }

    /**
     * 添加Port
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id，多个用','分割", required = true, dataType = "String"),
     **/
    @PostMapping()
    public Result add(Port port) {
        //非空判断
        if (port == null) {
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        //执行新增
        port.setUpdatetime(new Date());
        boolean result = portService.insert(port);
        if (result) {
            //新增成功
            return Result.success();
        }
        return Result.failure(ResultCode.ERROR);
    }

    /**
     * 修改Port
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id", required = true, dataType = "String"),
     **/
    @PutMapping()
    public Result update(Port port, HttpServletRequest request) {
        boolean result = portService.update(port, this.getActiveUser(request));
        if (result) {
            //修改成功
            return Result.success();
        } else {
            //修改失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 删除Port
     **/
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        //非空判断
        System.out.println("id==========delete===================================================" + id);
        if (StringUtil.isBlank(id)) {
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        try {
            //执行删除
            boolean result1 = portDetailService.deleteByPortId(id);
            boolean result = portService.deleteByPortId(id);
            if (result) {
                //删除成功
                return Result.success();
            } else {
                return Result.failure(ResultCode.ERROR);
            }
        } catch (RoleNotFoundException e) {
            //删除的角色id不存在
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }
    }

    /*  */

    /**
     * 删除Port
     **//*
    @DeleteMapping("deleteByPortId")
    public Result deleteByPortId (@PathVariable("id") String id){
        //非空判断
        if (StringUtil.isBlank(id)) {
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        try {
            //执行删除
            boolean result = portService.delete(id);


            if (result) {
                //删除成功
                return Result.success();
            } else {
                return Result.failure(ResultCode.ERROR);
            }
            *//*if (result1) {
                //删除成功
                return Result.success();
            } else {
                return Result.failure(ResultCode.ERROR);
            }*//*
        } catch (RoleNotFoundException e) {
            //删除的角色id不存在
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }
    }*/


    //批量删除
    @PutMapping("/batchelete")
    public Result batchDelete(String id) {
        boolean result = portService.batchDelete(id);
        if (result) {
            //删除成功
            return Result.success();
        } else {
            //删除失败
            return Result.failure(ResultCode.ERROR);
        }

    }


}