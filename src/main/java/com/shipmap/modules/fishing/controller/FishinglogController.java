package com.shipmap.modules.fishing.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.JsonResult;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.utils.DateUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.base.model.Company;
import com.shipmap.modules.base.model.NameVO;
import com.shipmap.modules.base.model.Ship;
import com.shipmap.modules.base.service.CompanyService;
import com.shipmap.modules.base.service.ShipService;
import com.shipmap.modules.fishing.model.*;
import com.shipmap.modules.fishing.service.FishinglogService;
import com.shipmap.modules.fishing.service.TripService;
import io.swagger.annotations.Api;
import net.sf.jxls.transformer.XLSTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ${author}
 * @since 2018-12-25
 */
@Api(value = "鱼捞日志接口", tags = "fishinglog")
@RestController
@RequestMapping("fishinglog")
public class FishinglogController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(FishinglogController.class);

    @Autowired
    public FishinglogService fishinglogService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ShipService shipService;
    @Autowired
    private TripService tripService;


    /**
     * 查询Fishinglog列表
     */
    @GetMapping("/discard")
    public Result discard(String id) {
        List<Discard> discards = fishinglogService.listDiscard(id);
        return PageResult.toResult(discards);
    }

    /**
     * 查询列表
     */
    @GetMapping("/fish")
    public Result fish(String id) {
        List<Catch> catches = fishinglogService.listFish(id);
        return PageResult.toResult(catches);
    }


    /**
     * 查询列表
     */
    @GetMapping("/fishType")
    public Result fishType(String id) {
        List<NameVO> nameVOS = fishinglogService.listFishType(id);
        return PageResult.toResult(nameVOS);
    }

    /**
     * 查询列表
     */
    @GetMapping("/standard")
    public Result standard(String id) {
        List<NameVO> nameVOS = fishinglogService.listStandard(id);
        return PageResult.toResult(nameVOS);
    }

    /**
     * 查询列表
     */
    @GetMapping("/unit")
    public Result unit(String id) {
        List<NameVO> nameVOS = fishinglogService.listUnit(id);
        return PageResult.toResult(nameVOS);
    }


    /**
     * 查询Fishinglog列表
     */
    @GetMapping("/list")
    public Result list(Integer page, Integer limit, String company, String ship, String actionType, String logType, String start, String end, Integer type) {
        if (page == null) {
            page = 0;
            limit = 0;
        }
        Page<Fishinglog> fishinglogPage = fishinglogService.list(new Page<>(page, limit), company, ship, actionType, logType, start, end, type);
        return PageResult.toResult(fishinglogPage.getRecords(), fishinglogPage.getTotal());
    }

    /**
     * 查询船舶日志航次数
     */
    @GetMapping("/selectTripLogCount")
    public Result selectTripLogCount(Integer page, Integer limit, String company, String ship) {
        if (page == null) {
            page = 0;
            limit = 0;
        }
        Page<FishingLogTongJi> fishinglogPage = fishinglogService.selectTripLogCount(new Page<>(page, limit), company, ship);
        return PageResult.toResult(fishinglogPage.getRecords(), fishinglogPage.getTotal());
    }

    /*
     * 查询所有公司名
     * */
    @GetMapping("/selectCompanyName")
    public List<Company> selectCompanyName() {
        return companyService.selectCompanyName();
    }

    /*
     * 查询所有船舶名
     * */
    @GetMapping("/selectShipName")
    public Result selectShipName() {
        List<Ship> ships = shipService.selectShipName("");
        return PageResult.toResult(ships);
    }


    /*
     * 查询公司的所有船舶
     * */
    @GetMapping("/selectCompanyShip")
    public List<Ship> selectCompanyShip(String companyId) {
        return shipService.selectShipName(companyId);
    }

    /**
     * 修改Fishinglog
     **/
    @PostMapping("/updateLog")
    public JsonResult updateLog(Fishinglog fishinglog, HttpServletRequest request) {
        if (fishinglogService.updateLog(fishinglog, getActiveUser(request))) {
            return JsonResult.ok("修改成功");
        } else {
            return JsonResult.error("修改失败");
        }
    }

    /**
     * 添加Fishinglog
     **/
    @PostMapping("/add")
    public JsonResult add(Fishinglog fishinglog, HttpServletRequest request) {
        if (fishinglogService.add(fishinglog, getActiveUser(request))) {
            return JsonResult.ok("添加成功");
        } else {
            return JsonResult.error("添加失败");
        }
    }

    /**
     * 删除Fishinglog
     **/
    @DeleteMapping("/delete")
    public JsonResult delete(String id) {
        if (fishinglogService.delete(id)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

    /**
     * 查询原始Fishinglog
     **/
    @GetMapping("/oneLog")
    public JsonResult oneLog(String id) {
        Fishinglog log = fishinglogService.getById(id);
        return JsonResult.ok("成功").put("data", log);
    }

    /**
     * 金枪鱼FFA导出
     */
    @RequestMapping(value = "exportExcel")
    public String exportExcel(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入导出方法！");

        String templateFileName = request.getSession().getServletContext().getRealPath("/") + "/xls/ffa.xls";
        System.out.println("templateFileName===========" + templateFileName);
        // 导出文件名，命名
        Date date = new Date();
        SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String destFileName = si.format(date) + ".xls";
        XLSTransformer transformer = new XLSTransformer();
        //定义流
        InputStream in = null;
        OutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //读取模版
        try {
            in = new BufferedInputStream(new FileInputStream(templateFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //sheetNames为分页工作簿名称，大小根据分页页数。
        String startTime = request.getParameter("start");
        String endTime = request.getParameter("end");
        String logType = request.getParameter("logType");
        String shipId = request.getParameter("ship");
        // String other = request.getParameter("othertype");
        //String isxiafa = request.getParameter("logIsXiafa");
        System.out.println("startTime====================" + startTime);
        System.out.println("end====================" + endTime);
        System.out.println("logType====================" + logType);
        System.out.println("ship====================" + shipId);


        //YtxHaixunLog ytxlog = new YtxHaixunLog();
        Fishinglog fishinglog = new Fishinglog();
        fishinglog.setStartTime(DateUtil.parseDate(startTime));
        fishinglog.setEndTime(DateUtil.parseDate(endTime));
        fishinglog.setLogType(logType);
        fishinglog.setShipId(shipId);
        //fishinglog.setLogOtherType(other);
        //fishinglog.setLogIsXiafa(isxiafa);
        //int size = getSheet(start,end,fishType,shippingName).size();

        List<String> sheetNames = new ArrayList<String>();
       /* int size = getSheet(fishinglog).size();
        logger.info("根据日志条件查询数据======================" + size);
        if (size == 0) {
            return "查询时间段没有渔捞日志信息！";
        }
        for (int o = 0; o < size; o++) {
            sheetNames.add("sheet" + o);
        }*/
//			System.out.println(sheetNames.size()+"--------------");
        //读取in中的模版并创建一个副本，getSheet是已分好的数据，sheetNames为分页名称，tablist为key，getHead为头部的值，0为从第0个sheet页开始。
        // Workbook worktab=transformer.transformXLS(in, getHead());
        /*try {
            Workbook worktab = transformer.transformMultipleSheetsList(in, getSheet(fishinglog), sheetNames, "tablist", getHead(fishinglog), 0);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }*/
        // 设置响应
        // 服务端要求客户端以下载文件的方式打开该文件
        response.setHeader("Content-Disposition", "attachment;filename=" + destFileName);
        // 设置数据类型
        response.setContentType("application/vnd.ms-excel");

        // 将内容写入输出流并把缓存的内容全部发出去
        if (out != null) {
            try {
                // worktab.write(out);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       /* System.out.println(shippingName);
        System.out.println("========================分割线==========================================");
        System.out.println("ytxLog===============" + ytxlog.toString());
        List<YtxHaixunLog> aaa = ytxHaixunLogService.findList(ytxlog);
        for (int l = 0; l < aaa.size(); l++) {
            YtxHaixunFishData yy = new YtxHaixunFishData();
            yy.setLogId(aaa.get(l).getId());
            List<YtxHaixunFishData> li = ytxHaixunFishDataService.findList(yy);
            Date time = aaa.get(l).getLogCreatetime();
            String fo = new SimpleDateFormat("yyyy-MM-dd").format(time);
            System.out.println("lsit.size----->" + li.size());
            for (int p = 0; p < li.size(); p++) {
                System.out.println(li.get(p).getFdName() + "--->>>");

            }
            System.out.println("========================分割线==========================================");

        }*/
        return "导出成功！";
    }

    // 头部数据
    private Map<String, Object> getHead(Fishinglog fishinglog) {
        Map<String, Object> tabtime = new HashMap<String, Object>();

        FishLogFFA fishLogFFA = new FishLogFFA();
        Company company = null;
        Ship ship = null;
        List<FishLogFFA> listship = new ArrayList<>();
        List<Fishinglog> fishinglogList = fishinglogService.selectList(fishinglog);
        //获取年
        Calendar yyy = Calendar.getInstance();
        String year = String.valueOf(yyy.get(Calendar.YEAR));
        fishLogFFA.setYear(year);
        if (fishinglogList != null && fishinglogList.size() > 0) {
            String shipId = fishinglogList.get(0).getShipId();
            if (StringUtil.isNotBlank(shipId)) {
                ship = shipService.getById(shipId);
                String companyId = ship.getCompanyId();
                if (StringUtil.isNotBlank(companyId)) {
                    company = companyService.getById(companyId);
                    //公司名（en）
                    fishLogFFA.setCompName(company.getNameEn());
                }
                //船名（en）
                fishLogFFA.setShipName(ship.getNameEn());
                //船旗国
                fishLogFFA.setShipRegistration(ship.getNationality());
                //无线电呼号
                fishLogFFA.setIntegerernational(ship.getCallsign());
                //船籍编号
                fishLogFFA.setRegistrationNumber(ship.getFisheryNo());
                //FFA注册号
                fishLogFFA.setFFANumber(ship.getFfaVid());
                //WCPFC认证号
                fishLogFFA.setIdentification(ship.getWcpfcCerNo());
                //入鱼证书号
                fishLogFFA.setFishNumber(ship.getFishingPermitNo());
            }
            String tripId = fishinglogList.get(0).getTripId();
            Trip trip = tripService.getById(tripId);
            if (trip != null) {
                //航次
                if (trip.getTripNo() != null) {
                    fishLogFFA.setTrip(trip.getTripNo().toString());
                }
                //主捕鱼种
                fishLogFFA.setSpecies(trip.getTargetSpec());
                //转载代理商
                fishLogFFA.setAgent(trip.getUnloadingAgent());
                //出发港口
                fishLogFFA.setDeparture(trip.getDeparture());
                //出发时间
                if (trip.getDepartureTime() != null) {
                    String departureTime = DateUtil.formatDate(trip.getDepartureTime());
                    fishLogFFA.setDeparturetime(departureTime);
                }
                //转载港口
                fishLogFFA.setPlaceUnload(trip.getUnloadingPlace());
                //转载时间
                if (trip.getUnloadingTime() != null) {
                    String PcTime = DateUtil.formatDate(trip.getUnloadingTime());
                    fishLogFFA.setPcTime(PcTime);
                }
                //活动类型
                fishLogFFA.setActivity(null);
            }
        }

        listship.add(fishLogFFA);
        tabtime.put("employess", listship);
        return tabtime;
    }


    // 中间日期数据
    /*private List<List> getSheet(Fishinglog fishinglog) {
        List<FishLogFFA> list2 = new ArrayList<>();
        Map<String, Object> map1 = null;
        int k = 0;

        List<List<Map<String, Object>>> stringlist = new ArrayList<List<Map<String, Object>>>();

        List<Fishinglog> logList = fishinglogService.selectList(fishinglog);
        if (logList.size() == 0) {
            List<List> ss = new ArrayList<>();
            return ss;
        }
        Collections.reverse(logList);
        // 查询数据源
        for (k = 0; k < logList.size(); k++) {
            Fishinglog fishinglog1 = new Fishinglog();
            fishinglog1 = logList.get(k);
            //String id = fishinglog1.getId();
            //Fishinglog fishinglog2 = new Fishinglog();
            //ytxHaixunLog2.setTempid(id);
            //ytxHaixunLog2.setLogStatu("3");
            //YtxHaixunLog byTempId3 = ytxHaixunLogService.getByTempId(ytxHaixunLog2);
            //ytxHaixunLog2.setLogStatu("4");
            //YtxHaixunLog byTempId4 = ytxHaixunLogService.getByTempId(ytxHaixunLog2);
            // if (byTempId4 != null && !"".equals(byTempId4)) {
            //     ytxHaixunLog = byTempId4;
            // }
            // if (byTempId3 != null && !"".equals(byTempId3)) {
            //     ytxHaixunLog = byTempId3;
            // }
            //获取激活代码
//			String activitycode = ytxHaixunLog.getLogStatu();
            // String logOtherType2 = ytxHaixunLog.getLogOtherType();
            //   System.err.println("ytxHaixunLog" + ytxHaixunLog.toString());
            String hooktime = "";
            FishLogFFA fishLogFFA = new FishLogFFA();
            //  Date time = ytxHaixunLog.getLogCreatetime();
            //   Date hook = ytxHaixunLog.getLogFishingStarttime();
            //   String formatter = new SimpleDateFormat("yyyy-MM-dd").format(time);

            if (fishinglog1.getStartTime() != null) {
                hooktime = DateUtil.formatDate(fishinglog1.getStartTime());
                //下钩月
                fishLogFFA.setMonth(hooktime.split("-")[1]);
                //下钩日
                fishLogFFA.setDat(hooktime.split("-")[2]);
                //下gou时间
                fishLogFFA.setHooktime(hooktime.split(" ")[1]);
            }
            //活动代码
            fishLogFFA.setActivity(fishinglog1.getActionType());
            String actionType = fishinglog1.getActionType();
            if (StringUtil.isNotBlank(actionType)) {
                if ("0".equals(actionType)) {
                    //作业
                    fishLogFFA.setActivity("1");
                } else if ("1".equals(actionType)) {
                    //航行
                    fishLogFFA.setActivity("3");
                } else if ("3".equals(actionType)) {
                    //在港
                    fishLogFFA.setActivity("4");
                } else {
                    fishLogFFA.setActivity("2");
                }
            }
            //下钩数
            fishLogFFA.setHooknum(fishinglog1.getHookSum());
            //两浮球间钩数
            fishLogFFA.setHookfloat(fishinglog1.getFloatHookSum());
            //经度
            Integer lon = fishinglog1.getLon();
            //纬度
            Integer lat = fishinglog1.getLat();
            if (lon > 0) {
                fishLogFFA.setLon("E");
            } else {
                fishLogFFA.setLon("W");
            }
            if (lat > 0) {
                fishLogFFA.setLat("N");
            } else {
                fishLogFFA.setLat("S");
            }
            Wrapper<Catch> wrapper = new EntityWrapper<>();
            wrapper.eq("log_id", fishinglog1.getId());
            List<Catch> li = catchService.selectList(wrapper);
            //初始化捕获数量
            int ALBSSUM = 0, BIGSUM = 0, PACSUM = 0, SKIPSUM = 0, YELLSUM = 0, BLACSUM = 0, BLUESUM = 0, STRISUM = 0;
            int SWORDSUM = 0, BSSUM = 0, HAMSUM = 0, MAKOSUM = 0, OCEASUM = 0, SAISUM = 0, SILSUM = 0, THRESUM = 0;
            int WHASUM = 0, DOLSUM = 0, WAHSUM = 0, OILSUM = 0, MOOSUM = 0, MAHSUM = 0, OTHSUM = 0;
            //初始化重量总和
            int ALBSweight = 0, BIGweight = 0, PACweight = 0, SKIPweight = 0, YELLweight = 0, BLACweight = 0, BLUEweight = 0, STRIweight = 0;
            int SWORDweight = 0, BSweight = 0, HAMweight = 0, MAKOweight = 0, OCEAweight = 0, SAIweight = 0, SILweight = 0, THREweight = 0;
            int WHAweght = 0, DOLweght = 0, WAHweght = 0, OILweght = 0, MOOweght = 0, MAHweght = 0, OTHweght = 0;
            //初始化丢弃尾数
            int ALBSdiuqi = 0, BIGdiuqi = 0, PACdiuqi = 0, SKIPdiuqi = 0, YELLdiuqi = 0, BLACdiuqi = 0, BLUEdiuqi = 0, STRIdiuqi = 0;
            int SWORDdiuqi = 0, BSdiuqi = 0, HAMdiuqi = 0, MAKOdiuqi = 0, OCEAdiuqi = 0, SAIdiuqi = 0, SILdiuqi = 0, THREdiuqi = 0;
            int WHAdiuqi = 0, DOLdiuqi = 0, WAHdiuqi = 0, OILdiuqi = 0, MOOdiuqi = 0, MAHdiuqi = 0, OTHdiuqi = 0;
            for (int j = 0; j < li.size(); j++) {
                System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//				if(li.get(j).getFdName().equals("ALBACORE(长鳍金枪鱼)")) {
                if (li.get(j).getFishName().contains("长鳍")) {

                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//						yiex.setAlbacore(null);
                        ALBSSUM = +0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//						yiex.setAlbacore(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        ALBSSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
//					String zl = li.get(j).getFdYuhuoliang()+"";
                    if (li.get(j).getWeight() != null) {
                        fishLogFFA.setAlbacore1(li.get(j).getWeight());
                        ALBSweight += li.get(j).getWeight();
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//						yiex.setAlbacore1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        ALBSweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//						yiex.setAlbacore2(null);
                        ALBSdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//						yiex.setAlbacore2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        ALBSdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("BIGEYE")) {
//					if(li.get(j).getFdName().equals("BIGEYE(大眼金枪鱼)")) {

                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//						yiex.setBigeye(null);
                        BIGSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//						yiex.setBigeye(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        BIGSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//						yiex.setBigeye1(null);
                        BIGweight += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//						yiex.setBigeye1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        BIGweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//						yiex.setBigeye222(null);
                        BIGdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//						yiex.setBigeye222(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        BIGdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("PACIFIC BLUEFIN")) {
                    //pacific

//					if(li.get(j).getFdName().equals("PACIFIC BLUEFIN太平洋蓝鳍")) {

                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setPacific(null);
                        PACSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setPacific(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        PACSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {

//							yiex.setPacific1(null);
                        PACweight += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setPacific1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        PACweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setPacific2(null);
                        PACdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setPacific2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        PACdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("SKIPJACK")) {

                    //
//					if(li.get(j).getFdName().equals("SKIPJACK（炸弹鱼）")) {

                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setYellowfin2(null);
                        SKIPSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setYellowfin2(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        SKIPSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {

//							yiex.setYellowfin21(null);
                        SKIPweight += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setYellowfin21(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        SKIPweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setYellowfin22(null);
                        SKIPdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setYellowfin22(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        SKIPdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("YELLOWFIN")) {
                    //YELLOWFIN(黄鳍金枪鱼)
//					if(li.get(j).getFdName().equals("YELLOWFIN(黄鳍金枪鱼)")) {


                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setYellowfin(null);
                        YELLSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setYellowfin(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        YELLSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setYellowfin1(null);
                        YELLweight += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setYellowfin1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        YELLweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                        System.err.println("YELLweight" + YELLweight);
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setYellowfin222(null);
                        YELLdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setYellowfin222(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        YELLdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("BLACK MARLIN")) {
                    //BLACK MARLIN(印度枪鱼)
//					if(li.get(j).getFdName().equals("BLACK MARLIN(印度枪鱼)")) {


                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setBigeye2(null);
                        BLACSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setBigeye2(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        BLACSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setBigeye21(null);
                        BLACweight += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
                        yiex.setBigeye21(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        BLACweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setBigeye22(null);
                        BLACdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setBigeye22(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        BLACdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("BLUE  MARLIN")) {

                    //BLUE  MARLIN（黑旗）
//					if(li.get(j).getFdName().equals("BLUE  MARLIN（黑旗）")) {


                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setBigeye3(null);
                        BLUESUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setBigeye3(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        BLUESUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setBigeye31(null);
                        BLUEweight += 0;

                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setBigeye31(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        BLUEweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setBigeye32(null);
                        BLUEdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setBigeye32(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        BLUEdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("STRIPED")) {
                    //STRIPED MARLIN（条纹四鳍旗鱼）
//					if(li.get(j).getFdName().equals("STRIPED MARLIN（条纹四鳍旗鱼）")) {


                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setBigeye4(null);
                        STRISUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setBigeye4(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        STRISUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setBigeye41(null);
                        STRIweight += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setBigeye41(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        STRIweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setBigeye42(null);
                        STRIdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setBigeye42(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        STRIdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("SWORDFISH")) {
                    //SWORDFISH(剑鱼)
//					if(li.get(j).getFdName().equals("SWORDFISH(剑鱼)")) {

                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setSwordfish(null);
                        SWORDSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setSwordfish(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        SWORDSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setSwordfish1(null);
                        SWORDweight += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setSwordfish1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        SWORDweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setSwordfish2(null);
                        SWORDdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setSwordfish2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        SWORDdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("BLUE SHARK")) {

                    //BLUE SHARK（大青鲨）
//					if(li.get(j).getFdName().equals("BLUE SHARK（大青鲨）")) {


                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setShark(null);
                        BSSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setShark(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        BSSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setShark1(null);
                        BSweight += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setShark1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        BSweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setShark2(null);
                        BSdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setShark2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        BSdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("HAMMERHEAD SHK")) {

                    //HAMMERHEAD SHK（双髻鲨）
                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setHammerhead(null);
                        HAMSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setHammerhead(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        HAMSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setHammerhead1(null);
                        HAMweight += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setHammerhead1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        HAMweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setHammerhead2(null);
                        HAMdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setHammerhead2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        HAMdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("MAKO   SHARKS")) {
                    //MAKO   SHARKS（灰鲭鲨）
//					if(li.get(j).getFdName().equals("MAKO   SHARKS（灰鲭鲨）")) {

                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setMakosharks(null);
                        MAKOSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setMakosharks(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        MAKOSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
                        yiex.setMakosharks1(null);
                        MAKOweight += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setMakosharks1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        MAKOweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setMakosharks2(null);
                        MAKOdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setMakosharks2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        MAKOdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("OCEANIC WHITETIP")) {
                    //OCEANIC WHITETIP（长鳍真鲨）
//					if(li.get(j).getFdName().equals("OCEANIC WHITETIP长鳍真鲨")) {

                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setOceanic(null);
                        OCEASUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setOceanic(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        OCEASUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setOceanic1(null);
                        OCEAweight += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setOceanic1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        OCEAweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setOceanic2(null);
                        OCEAdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setOceanic2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        OCEAdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("SAILFISH")) {
//					if(li.get(j).getFdName().equals("SAILFISH（伞旗）")) {

                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setBluemarlin(null);
                        SAISUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setBluemarlin(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        SAISUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setBluemarlin1(null);
                        SAIweight += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setBluemarlin1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        SAIweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setBluemarlin2(null);
                        SAIdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setBluemarlin2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        SAIdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("SILKY SHARK")) {
                    //SILKY SHARK（镰鳍真鲨）
//					if(li.get(j).getFdName().equals("SILKY SHARK（镰鳍真鲨）")) {

                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setSilkyshark(null);
                        SILSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setSilkyshark(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        SILSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setSilkyshark1(null);
                        SILweight += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setSilkyshark1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        SILweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setSilkyshark2(null);
                        SILdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setSilkyshark2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        SILdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("THRESHER SHARKS")) {
                    //THRESHER SHARKS （长尾鲨）

//					if(li.get(j).getFdName().equals("THRESHER SHARKS（长尾鲨）")) {

                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setThreshersharks(null);
                        THRESUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setThreshersharks(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        THRESUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setThreshersharks1(null);
                        THREweight += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setThreshersharks1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        THREweight += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setThreshersharks2(null);
                        THREdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setThreshersharks2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        THREdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("WHALE")) {

                    //WHALE鲸鱼
//					if(li.get(j).getFdName().equals("WHALE鲸鱼")) {

                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setSevefish(null);
                        WHASUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setSevefish(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        WHASUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setSevefish1(null);
                        WHAweght += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setSevefish1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        WHAweght += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setSevefish2(null);
                        WHAdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setSevefish2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        WHAdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("DOLPHIN")) {

                    //DOLPHIN海豚
//					if(li.get(j).getFdName().equals("DOLPHIN海豚")) {

                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setYellowfin3(null);
                        DOLSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setYellowfin3(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        DOLSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setYellowfin31(null);
                        DOLweght += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setYellowfin31(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        DOLweght += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setYellowfin32(null);
                        DOLdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setYellowfin32(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        DOLdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("WAHOO")) {
                    //WAHOO石乔
//					if(li.get(j).getFdName().equals("WAHOO(马交)")) {
                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//						yiex.setWahoo(null);
                        WAHSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setWahoo(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        WAHSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setWahoo1(null);
                        WAHweght += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setWahoo1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        WAHweght += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setWahoo2(null);
                        WAHdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setWahoo2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        WAHdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("OILFISH")) {
                    //OILFISH油甘
//					if(li.get(j).getFdName().equals("OILFISH(油甘)")) {

                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setOilfish(null);
                        OILSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setOilfish(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        OILSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setOilfish1(null);
                        OILweght += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setOilfish1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        OILweght += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setOilfish2(null);
                        OILdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setOilfish2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        OILdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("MOONFISH")) {

                    //MOONFISH红皮刀
//					if(li.get(j).getFdName().equals("MOONFISH(红皮刀)")) {
                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setMoonfish(null);
                        MOOSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setMoonfish(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        MOOSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setMoonfish1(null);
                        MOOweght += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setMoonfish1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        MOOweght += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setMoonfish2(null);
                        MOOdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setMoonfish2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        MOOdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else if (li.get(j).getFdName().contains("MAHIMAHI")) {
                    //MAHIMAHI鬼头刀
//					if(li.get(j).getFdName().equals("MAHIMAHI(鬼头刀)")) {
                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setMahimahi(null);
                        MAHSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setMahimahi(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        MAHSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setMahimahi1(null);
                        MAHweght += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setMahimahi1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        MAHweght += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setMahimahi2(null);
                        MAHdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setMahimahi2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        MAHdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                } else {
                    //OTHERS 其他杂鱼
//					if(li.get(j).getFdName().equals("BY-CATCH(杂鱼)")) {
                    if (li.get(j).getFdLiucangweishu().trim().isEmpty() || li.get(j).getFdLiucangweishu().trim() == null) {
//							yiex.setBycatch(null);
                        OTHSUM += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdLiucangweishu().trim());
//							yiex.setBycatch(Integer.parseInt(li.get(j).getFdLiucangweishu().trim()));
                        OTHSUM += Integer.parseInt(li.get(j).getFdLiucangweishu().trim());
                    }
                    if (li.get(j).getFdYuhuoliang() == null || li.get(j).getFdYuhuoliang().equals("null")) {
//							yiex.setBycatch1(null);
                        OTHweght += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdYuhuoliang());
//							yiex.setBycatch1(Integer.parseInt(li.get(j).getFdYuhuoliang().trim()));
                        OTHweght += Integer.parseInt(li.get(j).getFdYuhuoliang().trim());
                    }
                    if (li.get(j).getFdDiuqiweishu().trim().isEmpty() || li.get(j).getFdDiuqiweishu().trim() == null) {
//							yiex.setBycatch2(null);
                        OTHdiuqi += 0;
                    } else {
                        System.out.println("进来了---->" + li.get(j).getFdDiuqiweishu().trim());
//							yiex.setBycatch2(Integer.parseInt(li.get(j).getFdDiuqiweishu().trim()));
                        OTHdiuqi += Integer.parseInt(li.get(j).getFdDiuqiweishu().trim());
                    }

                }
            }
            if (ALBSSUM != 0) {
                yiex.setAlbacore(ALBSSUM);
//				yiex.setAlbacore(1);
            }
            if (ALBSweight != 0) {
                yiex.setAlbacore1(ALBSweight);
//				yiex.setAlbacore1(2);
            }
            if (ALBSdiuqi != 0) {
                yiex.setAlbacore2(ALBSdiuqi);
//				yiex.setAlbacore2(3);
            }


            if (BIGSUM != 0) {
                yiex.setBigeye(BIGSUM);
//				yiex.setBigeye(4);
            }
            if (BIGweight != 0) {
                yiex.setBigeye1(BIGweight);
//				yiex.setBigeye1(5);
            }
            if (BIGdiuqi != 0) {
                yiex.setBigeye222(BIGdiuqi);
//				yiex.setBigeye222(6);
            }


            if (PACSUM != 0) {
                yiex.setPacific(PACSUM);
//				yiex.setPacific(7);
            }
            if (PACweight != 0) {
                yiex.setPacific1(PACweight);
//				yiex.setPacific1(8);
            }
            if (PACdiuqi != 0) {
                yiex.setPacific2(PACdiuqi);
//				yiex.setPacific2(9);
            }


            if (SKIPSUM != 0) {
                yiex.setYellowfin2(SKIPSUM);
//				yiex.setYellowfin2(10);
            }
            if (SKIPweight != 0) {
                yiex.setYellowfin21(SKIPweight);
//				yiex.setYellowfin21(11);
            }
            if (SKIPdiuqi != 0) {
                yiex.setYellowfin22(SKIPdiuqi);
//				yiex.setYellowfin22(12);
            }


            if (YELLSUM != 0) {
                yiex.setYellowfin(YELLSUM);
//				yiex.setYellowfin(13);
            }
            if (YELLweight != 0) {
                yiex.setYellowfin1(YELLweight);
//				yiex.setYellowfin1(14);
            }
            if (YELLdiuqi != 0) {
                yiex.setYellowfin222(YELLdiuqi);
//				yiex.setYellowfin222(15);
            }

            if (BLACSUM != 0) {
                yiex.setBigeye2(BLACSUM);
//				yiex.setBigeye2(16);
            }
            if (BLACweight != 0) {
                yiex.setBigeye21(BLACweight);
//				yiex.setBigeye21(17);
            }
            if (BLACdiuqi != 0) {
                yiex.setBigeye22(BLACdiuqi);
//				yiex.setBigeye22(18);
            }

            if (BLUESUM != 0) {
                yiex.setBigeye3(BLUESUM);
//				yiex.setBigeye3(19);
            }
            if (BLUEweight != 0) {
                yiex.setBigeye31(BLUEweight);
//				yiex.setBigeye31(20);
            }
            if (BLUEdiuqi != 0) {
                yiex.setBigeye32(BLUEdiuqi);
//				yiex.setBigeye32(21);
            }

            if (STRISUM != 0) {
                yiex.setBigeye4(STRISUM);
//				yiex.setBigeye4(22);
            }
            if (STRIweight != 0) {
                yiex.setBigeye41(STRIweight);
//				yiex.setBigeye41(23);
            }
            if (STRIdiuqi != 0) {
                yiex.setBigeye42(STRIdiuqi);
//				yiex.setBigeye42(24);
            }

            if (SWORDSUM != 0) {
                yiex.setSwordfish(SWORDSUM);
//				yiex.setSwordfish(25);
            }
            if (SWORDweight != 0) {
                yiex.setSwordfish1(SWORDweight);
//				yiex.setSwordfish1(26);
            }
            if (SWORDdiuqi != 0) {
                yiex.setSwordfish2(SWORDdiuqi);
//				yiex.setSwordfish2(27);
            }

            if (BSSUM != 0) {
                yiex.setShark(BSSUM);
//				yiex.setShark(28);
            }
            if (BSweight != 0) {
                yiex.setShark1(BSweight);
//				yiex.setShark1(29);
            }
            if (BSdiuqi != 0) {
                yiex.setShark2(BSdiuqi);
//				yiex.setShark2(30);
            }

            if (HAMSUM != 0) {
                yiex.setHammerhead(HAMSUM);
//				yiex.setHammerhead(31);
            }
            if (HAMweight != 0) {
                yiex.setHammerhead1(HAMweight);
//				yiex.setHammerhead1(32);
            }
            if (HAMdiuqi != 0) {
                yiex.setHammerhead2(HAMdiuqi);
//				yiex.setHammerhead2(33);
            }

            if (MAKOSUM != 0) {
                yiex.setMakosharks(MAKOSUM);
//				yiex.setMakosharks(34);
            }
            if (MAKOweight != 0) {
                yiex.setMakosharks1(MAKOweight);
//				yiex.setMakosharks1(35);
            }
            if (MAKOdiuqi != 0) {
                yiex.setMakosharks2(MAKOdiuqi);
//				yiex.setMakosharks2(36);
            }

            if (OCEASUM != 0) {
                yiex.setOceanic(OCEASUM);
//				yiex.setOceanic(37);
            }
            if (OCEAweight != 0) {
                yiex.setOceanic1(OCEAweight);
//				yiex.setOceanic1(38);
            }
            if (OCEAdiuqi != 0) {
                yiex.setOceanic2(OCEAdiuqi);
//				yiex.setOceanic2(39);
            }

            if (SAISUM != 0) {
                yiex.setBluemarlin(SAISUM);
//				yiex.setBluemarlin(40);
            }
            if (SAIweight != 0) {
                yiex.setBluemarlin1(SAIweight);
//				yiex.setBluemarlin1(41);
            }
            if (SAIdiuqi != 0) {
                yiex.setBluemarlin2(SAIdiuqi);
//				yiex.setBluemarlin2(42);
            }

            if (SILSUM != 0) {
                yiex.setSilkyshark(SILSUM);
//				yiex.setSilkyshark(43);
            }
            if (SILweight != 0) {
                yiex.setSilkyshark1(SILweight);
//				yiex.setSilkyshark1(44);
            }
            if (SILdiuqi != 0) {
                yiex.setSilkyshark2(SILdiuqi);
//				yiex.setSilkyshark2(45);
            }

            if (THRESUM != 0) {
                yiex.setThreshersharks(THRESUM);
//				yiex.setThreshersharks(46);
            }
            if (THREweight != 0) {
                yiex.setThreshersharks1(THREweight);
//				yiex.setThreshersharks1(47);
            }
            if (THREdiuqi != 0) {
                yiex.setThreshersharks2(THREdiuqi);
//				yiex.setThreshersharks2(48);
            }

            if (WHASUM != 0) {
                yiex.setSevefish(WHASUM);
//				yiex.setSevefish(49);
            }
            if (WHAweght != 0) {
                yiex.setSevefish1(WHAweght);
//				yiex.setSevefish1(50);
            }
            if (WHAdiuqi != 0) {
                yiex.setSevefish2(WHAdiuqi);
//				yiex.setSevefish2(51);
            }

            if (DOLSUM != 0) {
                yiex.setYellowfin3(DOLSUM);
//				yiex.setYellowfin3(52);
            }
            if (DOLweght != 0) {
                yiex.setYellowfin31(DOLweght);
//				yiex.setYellowfin31(53);
            }
            if (DOLdiuqi != 0) {
                yiex.setYellowfin32(DOLdiuqi);
//				yiex.setYellowfin32(54);
            }

            if (WAHSUM != 0) {
                yiex.setWahoo(WAHSUM);
//				yiex.setWahoo(55);
            }
            if (WAHweght != 0) {
                yiex.setWahoo1(WAHweght);
//				yiex.setWahoo1(56);
            }
            if (WAHdiuqi != 0) {
                yiex.setWahoo2(WAHdiuqi);
//				yiex.setWahoo2(57);
            }

            if (OILSUM != 0) {
                yiex.setOilfish(OILSUM);
//				yiex.setOilfish(58);
            }
            if (OILweght != 0) {
                yiex.setOilfish1(OILweght);
//				yiex.setOilfish1(59);
            }
            if (OILdiuqi != 0) {
                yiex.setOilfish2(OILdiuqi);
//				yiex.setOilfish2(60);
            }

            if (MOOSUM != 0) {
                yiex.setMoonfish(MOOSUM);
//				yiex.setMoonfish(61);
            }
            if (MOOweght != 0) {
                yiex.setMoonfish1(MOOweght);
//				yiex.setMoonfish1(62);
            }
            if (MOOdiuqi != 0) {
                yiex.setMoonfish2(MOOdiuqi);
//				yiex.setMoonfish2(63);
            }

            if (MAHSUM != 0) {
                yiex.setMahimahi(MAHSUM);
//				yiex.setMahimahi(64);
            }
            if (MAHweght != 0) {
                yiex.setMahimahi1(MAHweght);
//				yiex.setMahimahi1(65);
            }
            if (MAHdiuqi != 0) {
                yiex.setMahimahi2(MAHdiuqi);
//				yiex.setMahimahi2(66);
            }

            if (OTHSUM != 0) {
                yiex.setBycatch(OTHSUM);
//				yiex.setBycatch(67);
            }
            if (OTHweght != 0) {
                yiex.setBycatch1(OTHweght);
//				yiex.setBycatch1(68);
            }

            if (OTHdiuqi != 0) {
                yiex.setBycatch2(OTHdiuqi);
//				yiex.setBycatch2(69);
            }
            list2.add(yiex);
        }
        double num = list2.size() / 10.0;
        //控制分页，数据大于10条进行分页。
        Date expordate = new Date();
        SimpleDateFormat sl = new SimpleDateFormat("yyyy.MM.dd");
        String thisdate = sl.format(expordate);

        for (k = 0; k < Math.ceil(num); k++) {
            int tep = (k + 1) * 10;
            int sum = (k * 10) + 10;
            if (sum > list2.size()) {
                tep = list2.size();
            }
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            map1 = new HashMap<String, Object>();
            int h = 0;
            //将数据分页 每10条作为一个对象。

            for (int j = k * 10; j < tep; j++) {
                YtxHaixunFishEx yiex2 = new YtxHaixunFishEx();
                yiex2.setDat(list2.get(j).getDat());
                yiex2.setMonth(list2.get(j).getMonth());
                yiex2.setHooklat(list2.get(j).getHooklat());
                yiex2.setHooklon(list2.get(j).getHooklon());
                yiex2.setLat(list2.get(j).getLat());
                yiex2.setLon(list2.get(j).getLon());
                yiex2.setActivity(list2.get(j).getActivity());
                yiex2.setHooktime(list2.get(j).getHooktime());
                yiex2.setHooknum(list2.get(j).getHooknum());
                yiex2.setPagesize((int) Math.ceil(num));
                yiex2.setHookfloat(list2.get(j).getHookfloat());
                yiex2.setPageno(k + 1);
                //长鳍金枪鱼
                yiex2.setAlbacore(list2.get(j).getAlbacore());
                yiex2.setAlbacore1(list2.get(j).getAlbacore1());
                yiex2.setAlbacore2(list2.get(j).getAlbacore2());

                yiex2.setYellowfin(list2.get(j).getYellowfin());
                yiex2.setYellowfin1(list2.get(j).getYellowfin1());
                yiex2.setYellowfin222(list2.get(j).getYellowfin222());

                yiex2.setYellowfin2(list2.get(j).getYellowfin2());
                yiex2.setYellowfin21(list2.get(j).getYellowfin21());
                yiex2.setYellowfin22(list2.get(j).getYellowfin22());

                yiex2.setYellowfin3(list2.get(j).getYellowfin3());
                yiex2.setYellowfin31(list2.get(j).getYellowfin31());
                yiex2.setYellowfin32(list2.get(j).getYellowfin32());

                yiex2.setBigeye(list2.get(j).getBigeye());
                yiex2.setBigeye1(list2.get(j).getBigeye1());
                yiex2.setBigeye222(list2.get(j).getBigeye222());

                yiex2.setBigeye2(list2.get(j).getBigeye2());
                yiex2.setBigeye21(list2.get(j).getBigeye21());
                yiex2.setBigeye22(list2.get(j).getBigeye22());

                yiex2.setBigeye3(list2.get(j).getBigeye3());
                yiex2.setBigeye31(list2.get(j).getBigeye31());
                yiex2.setBigeye32(list2.get(j).getBigeye32());

                yiex2.setBigeye4(list2.get(j).getBigeye4());
                yiex2.setBigeye41(list2.get(j).getBigeye41());
                yiex2.setBigeye42(list2.get(j).getBigeye42());


                yiex2.setPacific(list2.get(j).getPacific());
                yiex2.setPacific1(list2.get(j).getPacific1());
                yiex2.setPacific2(list2.get(j).getPacific2());

                yiex2.setSwordfish(list2.get(j).getSwordfish());
                yiex2.setSwordfish1(list2.get(j).getSwordfish1());
                yiex2.setSwordfish2(list2.get(j).getSwordfish2());

                yiex2.setShark(list2.get(j).getShark());
                yiex2.setShark1(list2.get(j).getShark1());
                yiex2.setShark2(list2.get(j).getShark2());

                yiex2.setHammerhead(list2.get(j).getHammerhead());
                yiex2.setHammerhead1(list2.get(j).getHammerhead1());
                yiex2.setHammerhead2(list2.get(j).getHammerhead2());

                yiex2.setMakosharks(list2.get(j).getMakosharks());
                yiex2.setMakosharks1(list2.get(j).getMakosharks1());
                yiex2.setMakosharks2(list2.get(j).getMakosharks2());

                yiex2.setMoonfish(list2.get(j).getMoonfish());
                yiex2.setMoonfish1(list2.get(j).getMoonfish1());
                yiex2.setMoonfish2(list2.get(j).getMoonfish2());

                yiex2.setBycatch(list2.get(j).getBycatch());
                yiex2.setBycatch1(list2.get(j).getBycatch1());
                yiex2.setBycatch2(list2.get(j).getBycatch2());

                yiex2.setMahimahi(list2.get(j).getMahimahi());
                yiex2.setMahimahi1(list2.get(j).getMahimahi1());
                yiex2.setMahimahi2(list2.get(j).getMahimahi2());

                yiex2.setOilfish(list2.get(j).getOilfish());
                yiex2.setOilfish1(list2.get(j).getOilfish1());
                yiex2.setOilfish2(list2.get(j).getOilfish2());

                yiex2.setSevefish(list2.get(j).getSevefish());
                yiex2.setSevefish1(list2.get(j).getSevefish1());
                yiex2.setSevefish2(list2.get(j).getSevefish2());

                yiex2.setThreshersharks(list2.get(j).getThreshersharks());
                yiex2.setThreshersharks1(list2.get(j).getThreshersharks1());
                yiex2.setThreshersharks2(list2.get(j).getThreshersharks2());

                yiex2.setSilkyshark(list2.get(j).getSilkyshark());
                yiex2.setSilkyshark1(list2.get(j).getSilkyshark1());
                yiex2.setSilkyshark2(list2.get(j).getSilkyshark2());

                yiex2.setBluemarlin(list2.get(j).getBluemarlin());
                yiex2.setBluemarlin1(list2.get(j).getBluemarlin1());
                yiex2.setBluemarlin2(list2.get(j).getBluemarlin2());

                yiex2.setOceanic(list2.get(j).getOceanic());
                yiex2.setOceanic1(list2.get(j).getOceanic1());
                yiex2.setOceanic2(list2.get(j).getOceanic2());

                yiex2.setWahoo(list2.get(j).getWahoo());
                yiex2.setWahoo1(list2.get(j).getWahoo1());
                yiex2.setWahoo2(list2.get(j).getWahoo2());

                map1.put("name" + h, yiex2);
                h++;
            }

            map1.put("exporttime", thisdate);
            list.add(map1);
            stringlist.add(list);
        }


        System.out.println(stringlist.size() + "stringlist----------------");
        ArrayList<List> objects = new ArrayList<List>();
        // 将stringlist塞入objects，将Objects返回传入分页方法。分页总页数就是objects的size();
        for (int p = 0; p < stringlist.size(); p++) {
            objects.add(stringlist.get(p));
        }
        return objects;

    }*/

}