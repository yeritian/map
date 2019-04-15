package com.shipmap.modules.observer.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.ResultCode;
import com.shipmap.framework.utils.ApiUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.observer.model.Fishcatch;
import com.shipmap.modules.observer.model.Fishtype;
import com.shipmap.modules.observer.service.FishcatchService;
import com.shipmap.modules.observer.service.FishtypeService;
import io.swagger.annotations.Api;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ${author}
 * @since 2019-03-28
 */
@Api(value = "Fishcatch相关的接口", tags = "user")
@RestController
@RequestMapping("/observer/fishcatch")
public class FishcatchController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(FishcatchController.class);

    @Autowired
    private FishcatchService fishcatchService;
    @Autowired
    private FishtypeService fishtypeService;

    ApiUtil api = new ApiUtil();

    /**
     * 查询Fishcatch列表
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
        Page<Fishcatch> fishcatchPage = fishcatchService.list(new Page<Fishcatch>(page, limit), searchKey, searchValue, observerinfoid);
        return PageResult.toResult(fishcatchPage.getRecords(), fishcatchPage.getTotal());
    }

    /**
     * 添加Fishcatch
     **/
    @PostMapping()
    public Result add(Fishcatch fishcatch) {
        boolean result = fishcatchService.add(fishcatch);
        if (result) {
            //添加成功
            return Result.success();
        } else {
            //添加失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 修改Fishcatch
     **/
    @PutMapping("/updateFishCatch")
    public Result update(String obfishcatchid, String obhks, String datehook, String obnum, String obtotsum, String remark, String dateupdate, HttpServletRequest request) {
        boolean result = fishcatchService.update(obfishcatchid, obhks, datehook, obnum, obtotsum, remark, dateupdate, this.getActiveUser(request));
        if (result) {
            //修改成功
            return Result.success();
        } else {
            //修改失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 删除Fishcatch
     **/
    @DeleteMapping("/deletefishcatch")
    public Result delete(String obfishcatchid) {
        boolean result = fishcatchService.delete(obfishcatchid);
        if (result) {
            //删除成功
            return Result.success();
        } else {
            //删除失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /**
     * 批量删除Fishcatch
     **/
    @PutMapping("/deletAllObfishcatchid")
    public Result deletAllObfishcatchid(String obfishcatchid) {
        boolean result = fishcatchService.deletAllObfishcatchid(obfishcatchid);
        if (result) {
            //删除成功
            return Result.success();
        } else {
            //删除失败
            return Result.failure(ResultCode.ERROR);
        }
    }

    /*详情*/
    @GetMapping("selectFishCatchById")
    public Result selectFishCatchById(String obfishcatchid) {
        List<Fishtype> list = fishcatchService.selectFishCatchById(obfishcatchid);
        return PageResult.toResult(list);
    }


    /*添加catch*/
    @PutMapping("/addFishCatch")
    public Result addFishCatch(String observerinfoid, String obhks, String datehook, String obnum, String obtotsum, String remark, String addstr, HttpServletRequest request) {
        boolean result = fishcatchService.addFishCatch(observerinfoid, obhks, datehook, obnum, obtotsum, remark, addstr, this.getActiveUser(request));
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
            String fishname = "";  //鱼中文名
            Row rowHead = sheet.getRow(3);//获得表头
            int hh = rowHead.getPhysicalNumberOfCells(); //列数

            //获得所有数据

            for (int i = 6; i <= totalRowNum; i++) {
                System.out.println(totalRowNum + "===========totalRowNum=========");
                //获得第i行对象
                Row rowt = sheet.getRow(i);
                //获得获得第i行第0列的 String类型对象
                Cell cellt = rowt.getCell(0);
                Cell cellt1 = rowt.getCell(1);
                Cell cellt2 = rowt.getCell(2);
                Cell cellt3 = rowt.getCell(3);

                String fishcatchhooks = String.valueOf((int) cellt.getNumericCellValue()); //int 转 Str
                Date fishcatchdatehook = cellt1.getDateCellValue(); //时间格式   调整
                String time = sdf.format(fishcatchdatehook).toString();
                //获得一个数字类型的数据
                int basketnum = (int) cellt2.getNumericCellValue();
                int baskettotsum = (int) cellt3.getNumericCellValue();

                Fishcatch ficatch = new Fishcatch();
                ficatch.setCreateTime(new Date());
                ficatch.setCreator(this.getActiveUser(request).getUsername());
                ficatch.setObserverinfoid(obid);  //观察员信息
                ficatch.setObfishcatchhooks(fishcatchhooks);
                ficatch.setObfishcatchdatehook(time);
                ficatch.setObbasketnum(basketnum);
                ficatch.setObbaskettotsum(baskettotsum);

                resone = fishcatchService.InsertLayer(ficatch);

                for (int j = 8; j <= hh; j = j + 4) {
                    //获得第i行对象
                    Row row = sheet.getRow(3);
                    Cell cell = row.getCell((short) (j - 4));
                    //获得一个String类型的数据
                    fishname = cell.getStringCellValue();//中文名
                    //获得第i行对象

                    Row row1 = sheet.getRow(i);

                    //获得获得第i行第0列的数据
                    Cell cell1 = row1.getCell((short) (j - 4));
                    Cell cell2 = row1.getCell((short) (j - 3));
                    Cell cell3 = row1.getCell((short) (j - 2));
                    Cell cell4 = row1.getCell((short) (j - 1));
                    //获得一个数字类型的数据
                    // name = (int) cell.getNumericCellValue();
                    int trailnum, Oboutput, btotletailnum, btotleoutput;
                    if (cell1 == null) {
                        trailnum = 0;
                    } else {
                        trailnum = (int) cell1.getNumericCellValue();
                    }
                    if (cell2 == null) {
                        Oboutput = 0;
                    } else {
                        Oboutput = (int) cell2.getNumericCellValue();
                    }
                    if (cell3 == null) {
                        btotletailnum = 0;
                    } else {
                        btotletailnum = (int) cell3.getNumericCellValue();
                    }
                    if (cell4 == null) {
                        btotleoutput = 0;
                    } else {
                        btotleoutput = (int) cell4.getNumericCellValue();
                    }
                    String aa = String.valueOf(trailnum);
                    String bb = String.valueOf(Oboutput);
                    String cc = String.valueOf(btotletailnum);
                    String dd = String.valueOf(btotleoutput);

                    Fishtype fty = new Fishtype();
                    fty.setObfishcatchid(ficatch.getobfishcatchid());//获取主键
                    fty.setFishsciencename(fishname);
                    fty.setChinesename(fishname);
                    fty.setObtrailnum(aa);
                    fty.setOboutput(bb);//重量
                    fty.setObtotletailnum(cc); //尾数
                    fty.setObtotleoutput(dd);
                    restwo = fishtypeService.insert(fty);
                }
            }
        }

        if (resone && restwo) {
            return true;
        } else {
            return false;
        }

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
        titleCell.setCellValue("渔捞努力量");//设置标题
        titleCell.setCellStyle(titleCellStyle);
        //合并单元格
        //  起始行号 终止行号 起始列号 终止列号
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 20);
        sheet.addMergedRegion(cellRangeAddress);

        CellStyle dataStyle = createStyles(wb).get("dataStyle");
        CellStyle menuTitleFont = createStyles(wb).get("menuTitleFont");
        //第二行
        HSSFRow twoRow = sheet.createRow(1);
        //第三行
        HSSFRow threeRow = sheet.createRow(2);
        //第四行
        HSSFRow fourRow = sheet.createRow(3);

        //编辑表格
        HSSFRow fiveRow = sheet.createRow(1);
        HSSFCell fourCell = fiveRow.createCell(0);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("作业钩次");
        cellRangeAddress = new CellRangeAddress(1, 3, 0, 0);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);
        fourCell = fiveRow.createCell(1);
        fourCell.setCellValue("下钩开始日期");
        cellRangeAddress = new CellRangeAddress(1, 3, 1, 1);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);
        fourCell = fiveRow.createCell(2);
        fourCell.setCellValue("观察筐数");
        cellRangeAddress = new CellRangeAddress(1, 3, 2, 2);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);
        fourCell = fiveRow.createCell(3);
        fourCell.setCellValue("总筐数");
        cellRangeAddress = new CellRangeAddress(1, 3, 3, 3);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);


        fourCell = fiveRow.createCell(4);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("大眼金枪鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 4, 7);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(8);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("黄鳍金枪鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 8, 11);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(12);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("长鳍金枪鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 12, 15);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(16);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("金枪鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 16, 19);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(20);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("鲣鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 20, 23);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(24);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("蓝枪鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 24, 27);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(28);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("条纹四鳍旗鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 28, 31);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(32);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("箭（剑）鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 32, 35);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(36);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("锯鳞四鳍旗鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 36, 39);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(40);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("旗鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 40, 43);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(44);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("白枪鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 44, 47);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(48);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("长鳍真鲨");
        cellRangeAddress = new CellRangeAddress(1, 1, 48, 51);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(52);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("镰状真鲨");
        cellRangeAddress = new CellRangeAddress(1, 1, 52, 55);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(56);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("大青鲨");
        cellRangeAddress = new CellRangeAddress(1, 1, 56, 59);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(60);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("尖吻青鲨（马加）");
        cellRangeAddress = new CellRangeAddress(1, 1, 60, 63);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(64);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("长鳍青鲨（马加舅）");
        cellRangeAddress = new CellRangeAddress(1, 1, 64, 67);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(68);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("大眼长尾鲨");
        cellRangeAddress = new CellRangeAddress(1, 1, 68, 71);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(72);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("鳄鲨");
        cellRangeAddress = new CellRangeAddress(1, 1, 72, 75);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(76);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("异鳞鲨");
        cellRangeAddress = new CellRangeAddress(1, 1, 76, 79);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(80);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("锤头双髻鲨");
        cellRangeAddress = new CellRangeAddress(1, 1, 80, 83);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(84);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("帆蜥鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 84, 87);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(88);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("凹尾长鳍乌鲂");
        cellRangeAddress = new CellRangeAddress(1, 1, 88, 91);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(92);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("红棱鲂");
        cellRangeAddress = new CellRangeAddress(1, 1, 92, 95);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(96);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("鲯鳅");
        cellRangeAddress = new CellRangeAddress(1, 1, 96, 99);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(100);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("沙氏刺鲅");
        cellRangeAddress = new CellRangeAddress(1, 1, 100, 103);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(104);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("异鳞蛇鲭");
        cellRangeAddress = new CellRangeAddress(1, 1, 104, 107);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(108);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("蛇鲭");
        cellRangeAddress = new CellRangeAddress(1, 1, 108, 111);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(112);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("斑点月鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 112, 115);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(116);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("日本福鲼");
        cellRangeAddress = new CellRangeAddress(1, 1, 116, 119);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(120);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("矛尾翻车鲀");
        cellRangeAddress = new CellRangeAddress(1, 1, 120, 123);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(124);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("翻车鲀");
        cellRangeAddress = new CellRangeAddress(1, 1, 124, 127);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(128);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("丽龟");
        cellRangeAddress = new CellRangeAddress(1, 1, 128, 131);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(132);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("棱皮龟");
        cellRangeAddress = new CellRangeAddress(1, 1, 132, 135);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(136);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("棘鳞蛇鲭");
        cellRangeAddress = new CellRangeAddress(1, 1, 136, 139);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(140);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("紫魟");
        cellRangeAddress = new CellRangeAddress(1, 1, 140, 143);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(144);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("小吻四鳍旗鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 144, 147);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(148);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("大盘短䲟");
        cellRangeAddress = new CellRangeAddress(1, 1, 148, 151);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(152);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("太平洋旗鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 152, 155);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(156);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("拟锤齿鲨");
        cellRangeAddress = new CellRangeAddress(1, 1, 156, 159);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(160);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("印度枪鱼");
        cellRangeAddress = new CellRangeAddress(1, 1, 160, 163);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);

        fourCell = fiveRow.createCell(164);
        fiveRow.setHeightInPoints((float) 26);
        fourCell.setCellValue("长体翻车鲀");
        cellRangeAddress = new CellRangeAddress(1, 1, 164, 167);
        sheet.addMergedRegion(cellRangeAddress);
        fourCell.setCellStyle(dataStyle);


        //第三行
        for (int i = 4; i < 45; i = i + 4) {
            HSSFCell threecell = threeRow.createCell(i);
            threecell.setCellValue("观察产量");
            cellRangeAddress = new CellRangeAddress(2, 2, i, (i + 1));
            sheet.addMergedRegion(cellRangeAddress);
            threecell.setCellStyle(dataStyle);
            threecell = threeRow.createCell(i + 2);
            threecell.setCellValue("总产量");
            cellRangeAddress = new CellRangeAddress(2, 2, (i + 2), (i + 3));
            sheet.addMergedRegion(cellRangeAddress);
            threecell.setCellStyle(dataStyle);
        }
        //第四行
        for (int j = 4; j <= 164; j = j + 4) {
            HSSFCell fourcell = fourRow.createCell(j);
            fourcell.setCellValue("尾数");
            fourCell.setCellStyle(dataStyle);
            fourcell = fourRow.createCell(j + 1);
            fourcell.setCellValue("重量");
            fourCell.setCellStyle(dataStyle);
            fourcell = fourRow.createCell(j + 2);
            fourcell.setCellValue("尾数");
            fourCell.setCellStyle(dataStyle);
            fourcell = fourRow.createCell(j + 3);
            fourcell.setCellValue("重量");
            fourCell.setCellStyle(dataStyle);
        }


        String[] split = ids.split(",");
        //查询日志数据
        for (int x = 0; x < split.length; x++) {
            Fishcatch fishcatch = fishcatchService.getById(split[x]);


            HSSFRow xRow = sheet.createRow(x + 4);//行
            HSSFCell xCell = xRow.createCell(0);//列

            xRow.setHeightInPoints((float) 20);
            if (fishcatch.getObfishcatchhooks() != null) {
                xCell.setCellValue(fishcatch.getObfishcatchhooks());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(1);
            xRow.setHeightInPoints((float) 20);
            if (fishcatch.getObfishcatchdatehook() != null) {
                xCell.setCellValue(fishcatch.getObfishcatchdatehook());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(2);
            xRow.setHeightInPoints((float) 20);
            if (fishcatch.getObbasketnum() != null) {
                xCell.setCellValue(fishcatch.getObbasketnum());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(3);
            xRow.setHeightInPoints((float) 20);
            if (fishcatch.getObbaskettotsum() != null) {
                xCell.setCellValue(fishcatch.getObbaskettotsum());
            } else {
                xCell.setCellValue("");
            }
            xCell.setCellStyle(dataStyle);

            List<Fishtype> fishtypelist = fishtypeService.getfishtype(split[x]);

            Map<String, Fishtype> appleMap = fishtypelist.stream().collect(Collectors.toMap(Fishtype::getChinesename, a -> a, (k1, k2) -> k1));

            System.out.println(appleMap.get("金枪鱼").getObtrailnum());

            xCell = xRow.createCell(4);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大眼金枪鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("大眼金枪鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(5);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大眼金枪鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("大眼金枪鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(6);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大眼金枪鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("大眼金枪鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(7);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大眼金枪鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("大眼金枪鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(8);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("黄鳍金枪鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("黄鳍金枪鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(9);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("黄鳍金枪鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("黄鳍金枪鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(10);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("黄鳍金枪鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("黄鳍金枪鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(11);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("黄鳍金枪鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("黄鳍金枪鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(12);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长鳍金枪鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("长鳍金枪鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(13);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长鳍金枪鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("长鳍金枪鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(14);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长鳍金枪鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("长鳍金枪鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(15);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长鳍金枪鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("长鳍金枪鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(16);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("金枪鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("金枪鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(17);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("金枪鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("金枪鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(18);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("金枪鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("金枪鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(19);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("金枪鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("金枪鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(20);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("鲣鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("鲣鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(21);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("鲣鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("鲣鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(22);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("鲣鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("鲣鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(23);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("鲣鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("鲣鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(24);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("蓝枪鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("蓝枪鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(25);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("蓝枪鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("蓝枪鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(26);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("蓝枪鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("蓝枪鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(27);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("蓝枪鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("蓝枪鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(28);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("条纹四鳍旗鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("条纹四鳍旗鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(29);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("条纹四鳍旗鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("条纹四鳍旗鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(30);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("条纹四鳍旗鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("条纹四鳍旗鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(31);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("条纹四鳍旗鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("条纹四鳍旗鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(32);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("箭（剑）鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("箭（剑）鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(33);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("箭（剑）鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("箭（剑）鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(34);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("箭（剑）鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("箭（剑）鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(35);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("箭（剑）鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("箭（剑）鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(36);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("锯鳞四鳍旗鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("锯鳞四鳍旗鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(37);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("锯鳞四鳍旗鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("锯鳞四鳍旗鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(38);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("锯鳞四鳍旗鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("锯鳞四鳍旗鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(39);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("锯鳞四鳍旗鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("锯鳞四鳍旗鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(40);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("旗鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("旗鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(41);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("旗鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("旗鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(42);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("旗鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("旗鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(43);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("旗鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("旗鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(44);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("白枪鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("白枪鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(45);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("白枪鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("白枪鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(46);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("白枪鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("白枪鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(47);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("白枪鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("白枪鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);


            xCell = xRow.createCell(48);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长鳍真鲨").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("长鳍真鲨").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(49);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长鳍真鲨").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("长鳍真鲨").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(50);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长鳍真鲨").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("长鳍真鲨").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(51);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长鳍真鲨").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("长鳍真鲨").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(52);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("镰状真鲨").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("镰状真鲨").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(53);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("镰状真鲨").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("镰状真鲨").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(54);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("镰状真鲨").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("镰状真鲨").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(55);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("镰状真鲨").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("镰状真鲨").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(56);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大青鲨").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("大青鲨").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(57);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大青鲨").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("大青鲨").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(58);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大青鲨").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("大青鲨").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(59);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大青鲨").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("大青鲨").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(60);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("尖吻青鲨（马加）").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("尖吻青鲨（马加）").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(61);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("尖吻青鲨（马加）").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("尖吻青鲨（马加）").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(62);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("尖吻青鲨（马加）").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("尖吻青鲨（马加）").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(63);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("尖吻青鲨（马加）").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("尖吻青鲨（马加）").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(64);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长鳍青鲨（马加舅）").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("长鳍青鲨（马加舅）").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(65);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长鳍青鲨（马加舅）").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("长鳍青鲨（马加舅）").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(66);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长鳍青鲨（马加舅）").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("长鳍青鲨（马加舅）").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(67);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长鳍青鲨（马加舅）").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("长鳍青鲨（马加舅）").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(68);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大眼长尾鲨").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("大眼长尾鲨").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(69);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大眼长尾鲨").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("大眼长尾鲨").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(70);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大眼长尾鲨").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("大眼长尾鲨").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(71);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大眼长尾鲨").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("大眼长尾鲨").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(72);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("鳄鲨").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("鳄鲨").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(73);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("鳄鲨").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("鳄鲨").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(74);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("鳄鲨").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("鳄鲨").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(75);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("鳄鲨").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("鳄鲨").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(76);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("异鳞鲨").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("异鳞鲨").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(77);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("异鳞鲨").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("异鳞鲨").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(78);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("异鳞鲨").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("异鳞鲨").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(79);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("异鳞鲨").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("异鳞鲨").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(80);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("锤头双髻鲨").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("锤头双髻鲨").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(81);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("锤头双髻鲨").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("锤头双髻鲨").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(82);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("锤头双髻鲨").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("锤头双髻鲨").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(83);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("锤头双髻鲨").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("锤头双髻鲨").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(84);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("帆蜥鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("帆蜥鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(85);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("帆蜥鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("帆蜥鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(86);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("帆蜥鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("帆蜥鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(87);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("帆蜥鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("帆蜥鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(88);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("凹尾长鳍乌鲂").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("凹尾长鳍乌鲂").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(89);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("凹尾长鳍乌鲂").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("凹尾长鳍乌鲂").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(90);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("凹尾长鳍乌鲂").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("凹尾长鳍乌鲂").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(91);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("凹尾长鳍乌鲂").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("凹尾长鳍乌鲂").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(92);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("红棱鲂").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("红棱鲂").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(93);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("红棱鲂").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("红棱鲂").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(94);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("红棱鲂").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("红棱鲂").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(95);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("红棱鲂").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("红棱鲂").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(96);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("鲯鳅").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("鲯鳅").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(97);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("鲯鳅").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("鲯鳅").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(98);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("鲯鳅").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("鲯鳅").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(99);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("鲯鳅").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("鲯鳅").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(100);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("沙氏刺鲅").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("沙氏刺鲅").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(101);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("沙氏刺鲅").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("沙氏刺鲅").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(102);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("沙氏刺鲅").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("沙氏刺鲅").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(103);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("沙氏刺鲅").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("沙氏刺鲅").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(104);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("异鳞蛇鲭").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("异鳞蛇鲭").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(105);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("异鳞蛇鲭").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("异鳞蛇鲭").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(106);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("异鳞蛇鲭").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("异鳞蛇鲭").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(107);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("异鳞蛇鲭").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("异鳞蛇鲭").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(108);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("蛇鲭").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("蛇鲭").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(109);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("蛇鲭").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("蛇鲭").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(110);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("蛇鲭").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("蛇鲭").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(111);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("蛇鲭").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("蛇鲭").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(112);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("斑点月鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("斑点月鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(113);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("斑点月鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("斑点月鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(114);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("斑点月鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("斑点月鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(115);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("斑点月鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("斑点月鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(116);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("日本福鲼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("日本福鲼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(117);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("日本福鲼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("日本福鲼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(118);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("日本福鲼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("日本福鲼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(119);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("日本福鲼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("日本福鲼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(120);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("矛尾翻车鲀").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("矛尾翻车鲀").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(121);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("矛尾翻车鲀").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("矛尾翻车鲀").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(122);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("矛尾翻车鲀").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("矛尾翻车鲀").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(123);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("矛尾翻车鲀").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("矛尾翻车鲀").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(124);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("翻车鲀").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("翻车鲀").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(125);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("翻车鲀").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("翻车鲀").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(126);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("翻车鲀").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("翻车鲀").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(127);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("翻车鲀").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("翻车鲀").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(128);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("丽龟").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("丽龟").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(129);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("丽龟").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("丽龟").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(130);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("丽龟").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("丽龟").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(131);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("丽龟").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("丽龟").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(132);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("棱皮龟").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("棱皮龟").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(133);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("棱皮龟").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("棱皮龟").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(134);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("棱皮龟").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("棱皮龟").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(135);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("棱皮龟").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("棱皮龟").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(136);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("棘鳞蛇鲭").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("棘鳞蛇鲭").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(137);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("棘鳞蛇鲭").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("棘鳞蛇鲭").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(138);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("棘鳞蛇鲭").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("棘鳞蛇鲭").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(139);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("棘鳞蛇鲭").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("棘鳞蛇鲭").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(140);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("紫魟").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("紫魟").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(141);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("紫魟").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("紫魟").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(142);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("紫魟").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("紫魟").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(143);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("紫魟").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("紫魟").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(144);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("小吻四鳍旗鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("小吻四鳍旗鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(145);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("小吻四鳍旗鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("小吻四鳍旗鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(146);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("小吻四鳍旗鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("小吻四鳍旗鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(147);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("小吻四鳍旗鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("小吻四鳍旗鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(148);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大盘短䲟").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("大盘短䲟").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(149);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大盘短䲟").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("大盘短䲟").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(150);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大盘短䲟").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("大盘短䲟").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(151);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("大盘短䲟").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("大盘短䲟").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(152);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("太平洋旗鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("太平洋旗鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(153);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("太平洋旗鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("太平洋旗鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(154);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("太平洋旗鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("太平洋旗鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(155);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("太平洋旗鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("太平洋旗鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(156);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("拟锤齿鲨").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("拟锤齿鲨").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(157);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("拟锤齿鲨").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("拟锤齿鲨").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(158);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("拟锤齿鲨").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("拟锤齿鲨").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(159);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("拟锤齿鲨").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("拟锤齿鲨").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(160);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("印度枪鱼").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("印度枪鱼").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(161);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("印度枪鱼").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("印度枪鱼").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(162);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("印度枪鱼").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("印度枪鱼").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(163);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("印度枪鱼").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("印度枪鱼").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);

            xCell = xRow.createCell(164);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长体翻车鲀").getObtrailnum() != null) {
                xCell.setCellValue(appleMap.get("长体翻车鲀").getObtrailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(165);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长体翻车鲀").getOboutput() != null) {
                xCell.setCellValue(appleMap.get("长体翻车鲀").getOboutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(166);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长体翻车鲀").getObtotletailnum() != null) {
                xCell.setCellValue(appleMap.get("长体翻车鲀").getObtotletailnum());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);
            xCell = xRow.createCell(167);
            xRow.setHeightInPoints((float) 20);
            if (appleMap.get("长体翻车鲀").getObtotleoutput() != null) {
                xCell.setCellValue(appleMap.get("长体翻车鲀").getObtotleoutput());
            } else {
                xCell.setCellValue("");
            }
            fourCell.setCellStyle(dataStyle);


        }

        response.setContentType("application/ms-excel;charset=UTF-8");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("渔捞努力量.xls", "UTF-8"))));
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