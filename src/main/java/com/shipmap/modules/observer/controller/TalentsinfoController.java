package com.shipmap.modules.observer.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.ResultCode;
import com.shipmap.framework.utils.ApiUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.observer.model.Resume;
import com.shipmap.modules.observer.model.Talentsinfo;
import com.shipmap.modules.observer.service.TalentsinfoService;
import io.swagger.annotations.Api;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ${author}
 * @since 2019-04-09
 */
@Api(value = "Talentsinfo相关的接口", tags = "user")
@RestController
@RequestMapping("/observer/talentsinfo")
public class TalentsinfoController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(TalentsinfoController.class);

    @Autowired
    private TalentsinfoService talentsinfoService;

    ApiUtil api = new ApiUtil();

    /**
     * 查询Talentsinfo列表
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
        Page<Talentsinfo> talentsinfoPage = talentsinfoService.list(new Page<Talentsinfo>(page, limit), searchKey, searchValue);
        return PageResult.toResult(talentsinfoPage.getRecords(), talentsinfoPage.getTotal());
    }

    /**
     * 添加Talentsinfo
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id，多个用','分割", required = true, dataType = "String"),
     **/

    @PostMapping()
    public Result add(Talentsinfo talentsinfo, HttpServletRequest request) {
        boolean result = talentsinfoService.add(talentsinfo, this.getActiveUser(request));
        if (result) {
            //添加成功
            return Result.success();
        } else {
            //添加失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 修改Talentsinfo
     *
     * @ApiImplicitParam(name = "roleId", value = "用户角色id", required = true, dataType = "String"),
     **/

    @PutMapping()
    public Result update(Talentsinfo talentsinfo, HttpServletRequest request) {
        boolean result = talentsinfoService.update(talentsinfo, this.getActiveUser(request));
        if (result) {
            //修改成功
            return Result.success();
        } else {
            //修改失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 删除Talentsinfo
     **/

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id) {
        System.out.println(id);
        boolean result = talentsinfoService.delete(id);
        if (result) {
            //删除成功
            return Result.success();
        } else {
            //删除失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /*详情--履历*/
    @GetMapping("/selectTalenDetail")
    public Result selectTalenDetail(String id) {
        List<Resume> list = talentsinfoService.selectTalenDetail(id);
        return PageResult.toResult(list);
    }

    /*修改*/
    @PutMapping("/updateTalen")
    public Result updateTalen(String id, String name, String sex, String seamanbookno, String idnumber, String healthbookno, String dateupdate, HttpServletRequest request) {
        boolean res = talentsinfoService.updateTalen(id, name, sex, seamanbookno, idnumber, healthbookno, dateupdate, this.getActiveUser(request));
        if (res) {
            //修改成功
            return Result.success();
        } else {
            //修改失败
            return Result.failure(ResultCode.ERROR);
        }
    }


    /*导入*/
    @RequestMapping(value = "fileexcel")
    @ResponseBody
    public Map<String, Object> Excel(HttpServletRequest request, @RequestParam("file") MultipartFile file, String obid) throws Exception {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        //文件后缀名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        Workbook hwb = null;
        boolean res = false;
        if (!file.isEmpty()) {
            if (!suffix.equals(".xls") && !suffix.equals(".xlsx")) {
                return api.returnJson(2, "文件格式不正确,请重新选择", dataMap);
            } else {
                InputStream inputStream = file.getInputStream();
                if (".xls".equals(suffix)) {
                    hwb = new HSSFWorkbook(inputStream);
                    res = ExcelParser(request, hwb, obid);
//                return read2003Excel(file);
                }
                if (".xlsx".equals(suffix)) {
                    hwb = new XSSFWorkbook(inputStream);
                    res = ExcelParser(request, hwb, obid);
//                return read2007Excel(file);
                }
            }
        } else {
            return api.returnJson(2, "文件无数据", dataMap);
        }

        if (res) {
            return api.returnJson(1, "导入成功！", dataMap);
        } else {
            return api.returnJson(2, "导入失败！", dataMap);
        }

    }

    public boolean ExcelParser(HttpServletRequest request, Workbook w, String obid) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean resone = false;
        boolean restwo = false;
        if (w != null) {
            int a = w.getNumberOfSheets();
            //todo 判断sheet是否为空
            Sheet sheet = w.getSheetAt(0);       //得到一个工作表
            int totalRowNum = sheet.getLastRowNum();//总行数
            Row rowHead = sheet.getRow(3);//获得表头
            int hh = rowHead.getPhysicalNumberOfCells(); //列数

            System.out.println(hh + "hh");
            //System.out.println(rowHead+"rowHead");
            System.out.println(totalRowNum + "totalRowNum");

            //获得所有数据
            for (int i = 3; i <= totalRowNum; i++) {

                //获得第i行对象
                Row rowt = sheet.getRow(i);
                if (rowt == null) {
                    continue;
                } else {
                    //获得获得第i行第0列的 String类型对象
                    Cell cellt = rowt.getCell(0);

                    if (cellt == null) {
                        continue;
                    }

                    Cell cellt1 = rowt.getCell(1);
                    Cell cellt2 = rowt.getCell(2);
                    Cell cellt3 = rowt.getCell(3);
                    Cell cellt4 = rowt.getCell(4);
                    // cellt.getStringCellValue();

                    System.out.println(cellt.getStringCellValue());
                    System.out.println(cellt1.getStringCellValue());
                    System.out.println(cellt2.getNumericCellValue());
                    System.out.println(cellt3.getStringCellValue());
                    System.out.println(cellt4.getNumericCellValue());
                }
                //     String fishcatchhooks = String.valueOf((int)cellt.getNumericCellValue()); //int 转 Str
                //    Date fishcatchdatehook =  cellt1.getDateCellValue(); //时间格式   调整
                //  String time = sdf.format(fishcatchdatehook).toString();
                //获得一个数字类型的数据
                //     int basketnum = (int) cellt2.getNumericCellValue();
                //      int baskettotsum = (int) cellt3.getNumericCellValue();
                //  resone =  talentsinfoService.InsertLayer(ficatch);
                for (int j = 5; j < hh - 2; j = j + 2) {
                    //获得第i行对象
                    Row row = sheet.getRow(3);
                    Cell cell = row.getCell((short) (j));
                    //获得一个String类型的数据
                    //  fishname =  cell.getStringCellValue();//中文名
                    //获得第i行对象
                    Row row1 = sheet.getRow(i);
                    //获得获得第i行第0列的数据
                    Cell cell1 = row1.getCell((short) (5));
                    Cell cell2 = row1.getCell((short) (7));
                    Cell cell3 = row1.getCell((short) (9));
                    Cell cell4 = row1.getCell((short) (11));
                    //获得一个数字类型的数据
                    // name = (int) cell.getNumericCellValue();

                    System.out.println(cell1.getStringCellValue());
                    System.out.println(cell2.getNumericCellValue());
                    System.out.println(cell3.getDateCellValue());
                    System.out.println(cell4.getDateCellValue());


                }
            }


        }

       /* if (resone&&restwo) {
            return  true;
        }else{
            return  false;
        }*/
        return true;
    }


}