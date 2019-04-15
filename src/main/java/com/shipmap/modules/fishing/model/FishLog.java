package com.shipmap.modules.fishing.model;


import com.shipmap.framework.utils.Tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FishLog {
    private Integer id;

    //船名
    private String shipName;

    private Integer shipId;

    //日志类型：1为金枪鱼延绳钓，2为围网，3为拖网，4为鱿钓，5为金枪鱼围网,6为竹筴鱼/鲐鱼渔捞日志
    private String logType;
    //'作业类型： 0作业1航行2漂流3转载4在港5故障6未作业-天气原因7寻找鱼群8清洁网具9放置或回收阀艇、人工聚鱼';
    private String actionType;
    private String actionTypeStr;

    //  '作业时间'
    private Date logDate;
    private String logDateStr;
    //'经度'
    private Integer longitude;
    private String longitudeStr;
    //纬度
    private Integer latitude;
    private String latitudeStr;
    //网次
    private Integer netTime;
    //开始时间
    private Date startTime;
    private String startTimeStr;
    //开始经度
    private Integer startLongitude;
    private String startLongitudeStr;
    //开始纬度
    private Integer startLatitude;
    private String startLatitudeStr;
    //结束时间
    private Date endTime;
    private String endTimeStr;
    //结束经度
    private Integer endLongitude;
    private String endLongitudeStr;
    //结束纬度
    private Integer endLatitude;
    private String endLatitudeStr;
    //曳纲长度，单位为厘米
    private Integer yegangchangdu;
    private String yegangchangduStr;
    //拖速，单位为：0.01节
    private Integer tuosu;
    private String tuosuStr;
    //钓机台数
    private Integer diaojitaishu;
    //单钓钩数
    private Integer dandiaogoushu;
    //手钓人数
    private Integer shoudiaorenshu;
    //投钓水深，单位为厘米
    private Integer toudiaoshuishen;
    private String toudiaoshuishenStr;
    //水温，单位：0.1度
    private Integer shuiwen;
    private String shuiwenStr;
    //灯光功率，单位为瓦
    private Integer dengguanggonglv;
    //盘重，单位：0.1千克
    private Integer panzhong;
    //钩数
    private Integer goushu;
    //浮球间钩数
    private Integer fuqiujiangoushu;
    //水深，单位0.01米
    private Integer shuishen;
    private String shuishenStr;
    //开灯数
    private Integer kaidengshu;
    //风速，单位0.1米/秒
    private Integer fengsu;
    private String fengsuStr;
    //风向，0.1度
    private Integer fengxiang;
    private String fengxiangStr;
    //流速,单位：0.1节
    private Integer liusu;
    //流向，0.1度
    private Integer liuxiang;
    //围捕类型
    private String weibuleixing;
    private String weibuleixingStr;
    //鲣鱼渔获量，单位0.1Kg
    private Integer jianyu;
    private String jianyuStr;
    //≤9Kg黄鳍金枪鱼产量，单位0.1Kg
    private Integer huangqi1;
    //>9Kg黄鳍金枪鱼产量，单位0.1Kg
    private Integer huangqi2;
    //≤9Kg大目金枪鱼产量，单位0.1Kg
    private Integer damu1;
    //>9Kg大目金枪鱼产量，单位0.1Kg
    private Integer damu2;
    //其他鱼种
    private String qitayuzhongzhonglei;
    //其他鱼种产量，单位：0.1Kg
    private Integer qitayuzhongchanliang;
    //丢弃金枪鱼种类
    private String diuqijinqiangyu;
    //丢弃金枪鱼重量，单位0.1KG
    private Integer diuqijinqiangyuzhongliang;
    //丢弃金枪鱼原因
    private String qiuqijinqiangyuyuanyin;
    private String qiuqijinqiangyuyuanyinStr;
    //丢弃其他鱼种种类
    private String diuqiqitayuzhongzhonglei;
    //丢弃其他鱼种数量
    private Integer diuqiqitayuzhongshuliang;
    //丢弃其他鱼种重量，单位0.1Kg
    private Integer diuqiqitayuzhongzhongliang;
    //呼号
    private String callsign;
    private Date submitTime;
    private String submitTimeStr;

    //创建者
    private String createUser;

    private List<FishLogItem> items = new ArrayList<FishLogItem>();

    private String yucang;
    private String jihuodaima;
    private String hangci;
    private String chugangmingcheng;
    private String chugangshijian;
    private String xieyudidian;
    private String jingangshijian;
    private String tianqiqingkuang;
    private String haikuang;
    private Integer kaishiwangwei;
    private String kaishiwangweiStr;
    private Integer wangkougaodu;
    private String wangkougaoduStr;
    private Integer wangkoukuozhang;
    private String wangkoukuozhangStr;
    private Integer yuqunshuishen;
    private String yuqunshuishenStr;
    private String yujuguimo;
    private Integer nengjiandu;
    private String nengjianduStr;
    private Integer qiwen;
    private String qiwenStr;
    private Integer langxiang;
    private String langxiangStr;
    private Integer langgao;
    private String langgaoStr;
    private String fx;

    private Date zhuanzaiStart;
    private Date zhuanzaiEnd;
    private String zhuanzaiMudi;
    private String zhuanzaiCallsign;
    private Integer zhuanzaiHuangqi;
    private Integer zhuanzaiDamu;
    private Integer zhuanzaiHunhe;
    private Integer zhuanzaiQita;
    private Integer zhuanzaiBuhege;
    private Integer zhuanzaiJianyu;


    public Date getZhuanzaiStart() {
        return zhuanzaiStart;
    }

    public void setZhuanzaiStart(Date zhuanzaiStart) {
        this.zhuanzaiStart = zhuanzaiStart;
    }

    public Date getZhuanzaiEnd() {
        return zhuanzaiEnd;
    }

    public void setZhuanzaiEnd(Date zhuanzaiEnd) {
        this.zhuanzaiEnd = zhuanzaiEnd;
    }

    public String getZhuanzaiMudi() {
        return zhuanzaiMudi;
    }

    public void setZhuanzaiMudi(String zhuanzaiMudi) {
        this.zhuanzaiMudi = zhuanzaiMudi;
    }

    public String getZhuanzaiCallsign() {
        return zhuanzaiCallsign;
    }

    public void setZhuanzaiCallsign(String zhuanzaiCallsign) {
        this.zhuanzaiCallsign = zhuanzaiCallsign;
    }

    public Integer getZhuanzaiHuangqi() {
        return zhuanzaiHuangqi;
    }

    public void setZhuanzaiHuangqi(Integer zhuanzaiHuangqi) {
        this.zhuanzaiHuangqi = zhuanzaiHuangqi;
    }

    public Integer getZhuanzaiDamu() {
        return zhuanzaiDamu;
    }

    public void setZhuanzaiDamu(Integer zhuanzaiDamu) {
        this.zhuanzaiDamu = zhuanzaiDamu;
    }

    public Integer getZhuanzaiHunhe() {
        return zhuanzaiHunhe;
    }

    public void setZhuanzaiHunhe(Integer zhuanzaiHunhe) {
        this.zhuanzaiHunhe = zhuanzaiHunhe;
    }

    public Integer getZhuanzaiQita() {
        return zhuanzaiQita;
    }

    public void setZhuanzaiQita(Integer zhuanzaiQita) {
        this.zhuanzaiQita = zhuanzaiQita;
    }

    public Integer getZhuanzaiBuhege() {
        return zhuanzaiBuhege;
    }

    public void setZhuanzaiBuhege(Integer zhuanzaiBuhege) {
        this.zhuanzaiBuhege = zhuanzaiBuhege;
    }

    public Integer getZhuanzaiJianyu() {
        return zhuanzaiJianyu;
    }

    public void setZhuanzaiJianyu(Integer zhuanzaiJianyu) {
        this.zhuanzaiJianyu = zhuanzaiJianyu;
    }

    public String getWangkoukuozhangStr() {
        if (wangkoukuozhang != null)
            return ((double) wangkoukuozhang) / 100 + "";
        return "";
    }

    public String getYuqunshuishenStr() {
        if (yuqunshuishen != null)
            return ((double) yuqunshuishen) / 100 + "";
        return "";
    }

    public String getNengjianduStr() {
        if (nengjiandu != null)
            return ((double) nengjiandu) / 100 + "";
        return "";
    }

    public String getQiwenStr() {
        return qiwenStr;
    }

    public String getLangxiangStr() {
        if (langxiang != null)
            return ((double) langxiang) / 100 + "";
        return "";
    }

    public String getLanggaoStr() {
        if (langgao != null)
            return ((double) langgao) / 100 + "";
        return "";
    }

    public String getWangkougaoduStr() {
        if (wangkougaodu != null)
            return ((double) wangkougaodu) / 100 + "";
        return "";
    }

    public String getKaishiwangweiStr() {
        if (kaishiwangwei != null)
            return ((double) kaishiwangwei) / 100 + "";
        return "";
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public String getYucang() {
        return yucang;
    }

    public void setYucang(String yucang) {
        this.yucang = yucang;
    }

    public String getJihuodaima() {
        return jihuodaima;
    }

    public void setJihuodaima(String jihuodaima) {
        this.jihuodaima = jihuodaima;
    }

    public String getHangci() {
        return hangci;
    }

    public void setHangci(String hangci) {
        this.hangci = hangci;
    }

    public String getChugangmingcheng() {
        return chugangmingcheng;
    }

    public void setChugangmingcheng(String chugangmingcheng) {
        this.chugangmingcheng = chugangmingcheng;
    }

    public String getChugangshijian() {
        return chugangshijian;
    }

    public void setChugangshijian(String chugangshijian) {
        this.chugangshijian = chugangshijian;
    }

    public String getXieyudidian() {
        return xieyudidian;
    }

    public void setXieyudidian(String xieyudidian) {
        this.xieyudidian = xieyudidian;
    }

    public String getJingangshijian() {
        return jingangshijian;
    }

    public void setJingangshijian(String jingangshijian) {
        this.jingangshijian = jingangshijian;
    }

    public String getTianqiqingkuang() {
        return tianqiqingkuang;
    }

    public void setTianqiqingkuang(String tianqiqingkuang) {
        this.tianqiqingkuang = tianqiqingkuang;
    }

    public String getHaikuang() {
        return haikuang;
    }

    public void setHaikuang(String haikuang) {
        this.haikuang = haikuang;
    }

    public Integer getKaishiwangwei() {
        return kaishiwangwei;
    }

    public void setKaishiwangwei(Integer kaishiwangwei) {
        this.kaishiwangwei = kaishiwangwei;
    }

    public Integer getWangkougaodu() {
        return wangkougaodu;
    }

    public void setWangkougaodu(Integer wangkougaodu) {
        this.wangkougaodu = wangkougaodu;
    }

    public Integer getWangkoukuozhang() {
        return wangkoukuozhang;
    }

    public void setWangkoukuozhang(Integer wangkoukuozhang) {
        this.wangkoukuozhang = wangkoukuozhang;
    }

    public Integer getYuqunshuishen() {
        return yuqunshuishen;
    }

    public void setYuqunshuishen(Integer yuqunshuishen) {
        this.yuqunshuishen = yuqunshuishen;
    }

    public String getYujuguimo() {
        return yujuguimo;
    }

    public void setYujuguimo(String yujuguimo) {
        this.yujuguimo = yujuguimo;
    }

    public Integer getNengjiandu() {
        return nengjiandu;
    }

    public void setNengjiandu(Integer nengjiandu) {
        this.nengjiandu = nengjiandu;
    }

    public Integer getQiwen() {
        return qiwen;
    }

    public void setQiwen(Integer qiwen) {
        this.qiwen = qiwen;
    }

    public Integer getLangxiang() {
        return langxiang;
    }

    public void setLangxiang(Integer langxiang) {
        this.langxiang = langxiang;
    }

    public Integer getLanggao() {
        return langgao;
    }

    public void setLanggao(Integer langgao) {
        this.langgao = langgao;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getSubmitTimeStr() {
        return submitTimeStr;
    }

    public void setSubmitTimeStr(String submitTimeStr) {
        this.submitTimeStr = submitTimeStr;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getJianyuStr() {
        if (jianyu != null)
            return ((double) jianyu) / 10 + "";
        return "";
    }

    public String getHuangqi1Str() {
        if (huangqi1 != null)
            return ((double) huangqi1) / 10 + "";
        return "";
    }

    public String getHuangqi2Str() {
        if (huangqi2 != null)
            return ((double) huangqi2) / 10 + "";
        return "";
    }

    public String getDamu1Str() {
        if (damu1 != null)
            return ((double) damu1) / 10 + "";
        return "";
    }

    public String getDamu2Str() {
        if (damu2 != null)
            return ((double) damu2) / 10 + "";
        return "";
    }

    public String getQitayuzhongchanliangStr() {
        if (qitayuzhongchanliang != null)
            return ((double) qitayuzhongchanliang) / 10 + "";
        return "";
    }

    public String getDiuqijinqiangyuzhongliangStr() {
        if (diuqijinqiangyuzhongliang != null)
            return ((double) diuqijinqiangyuzhongliang) / 10 + "";
        return "";
    }

    public String getDiuqiqitayuzhongzhongliangStr() {
        if (diuqiqitayuzhongzhongliang != null)
            return ((double) diuqiqitayuzhongzhongliang) / 10 + "";
        return "";
    }

    //1   鱼体太小             2   鱼体已损坏    3   船舶已满载         4   其它原因
    public String getQiuqijinqiangyuyuanyinStr() {
        if (qiuqijinqiangyuyuanyin == null) return "";
        else if ("0".equals(qiuqijinqiangyuyuanyin)) return "";
        else if ("1".equals(qiuqijinqiangyuyuanyin)) return "1（鱼体太小）";
        else if ("2".equals(qiuqijinqiangyuyuanyin)) return "2（鱼体已损坏）";
        else if ("3".equals(qiuqijinqiangyuyuanyin)) return "3（船舶已满载）";
        else if ("4".equals(qiuqijinqiangyuyuanyin)) return "4（其它原因）";
        return qiuqijinqiangyuyuanyin;
    }

    public void setQiuqijinqiangyuyuanyinStr(String qiuqijinqiangyuyuanyinStr) {
        this.qiuqijinqiangyuyuanyinStr = qiuqijinqiangyuyuanyinStr;
    }

    //1   浮水鱼群    2   喂食索饵鱼群    3   流木、残骸垃圾、或死         亡的动物尸体    4   漂流的阀艇、人工聚鱼         装置或PAYAO    5   锚链固定的阀艇、人工         聚鱼装置或PAYAO    6   鲸鱼跟随群    7   鲸鲨跟随群    8   其它
    public String getWeibuleixingStr() {
        if (weibuleixing == null) return "";
        else if ("0".equals(weibuleixing)) return "";
        else if ("1".equals(weibuleixing)) return "1（浮水鱼群）";
        else if ("2".equals(weibuleixing)) return "2（喂食索饵鱼群）";
        else if ("3".equals(weibuleixing)) return "3（流木、残骸垃圾、或死 亡的动物尸体）";
        else if ("4".equals(weibuleixing)) return "4（漂流的阀艇、人工聚鱼装置或PAYAO）";
        else if ("5".equals(weibuleixing)) return "5（锚链固定的阀艇、人工聚鱼装置或PAYAO）";
        else if ("6".equals(weibuleixing)) return "6（鲸鱼跟随群）";
        else if ("7".equals(weibuleixing)) return "7（鲸鲨跟随群）";
        else if ("8".equals(weibuleixing)) return "8（其它）";
        return weibuleixing;
    }

    public void setWeibuleixingStr(String weibuleixingStr) {
        this.weibuleixingStr = weibuleixingStr;
    }

    public String getTuosuStr() {
        if (tuosu != null)
            return ((double) tuosu) / 10 + "";
        return "";
    }

    public void setTuosuStr(String tuosuStr) {
        this.tuosuStr = tuosuStr;
    }

    public String getYegangchangduStr() {
        if (yegangchangdu != null)
            return ((double) yegangchangdu) / 100 + "";
        return "";
    }

    public void setYegangchangduStr(String yegangchangduStr) {
        this.yegangchangduStr = yegangchangduStr;
    }

    public String getFengsuStr() {
        if (fengsu != null)
            return ((double) fengsu) / 10 + "";
        return "";
    }

    public void setFengsuStr(String fengsuStr) {
        this.fengsuStr = fengsuStr;
    }

    public String getFengxiangStr() {
        if (fengxiang != null)
            return ((double) fengxiang) / 10 + "";
        return "";
    }

    public void setFengxiangStr(String fengxiangStr) {
        this.fengxiangStr = fengxiangStr;
    }

    public String getShuiwenStr() {
        if (shuiwen != null)
            return ((double) shuiwen) / 10 + "";
        return "";
    }

    public void setShuiwenStr(String shuiwenStr) {
        this.shuiwenStr = shuiwenStr;
    }

    public String getToudiaoshuishenStr() {
        if (toudiaoshuishen != null)
            return ((double) toudiaoshuishen) / 100 + "";
        return "";
    }

    public void setToudiaoshuishenStr(String toudiaoshuishenStr) {
        this.toudiaoshuishenStr = toudiaoshuishenStr;
    }

    public String getShuishenStr() {
        if (shuishen != null)
            return ((double) shuishen) / 100 + "";
        return "";
    }

    public void setShuishenStr(String shuishenStr) {
        this.shuishenStr = shuishenStr;
    }

    public String getLongitudeStr() {
        if (longitude != null)
            return Tools.lonlat((double) (Math.round((double) longitude * 1000000000 / 600000)) / 1000000000);
        return "";
    }

    public void setLongitudeStr(String longitudeStr) {
        this.longitudeStr = longitudeStr;
    }

    public String getLatitudeStr() {
        if (latitude != null)
            return Tools.lonlat((double) (Math.round((double) latitude * 1000000000 / 600000)) / 1000000000);
        return "";
    }

    public void setLatitudeStr(String latitudeStr) {
        this.latitudeStr = latitudeStr;
    }

    public String getStartLongitudeStr() {
        if (startLongitude != null)
            return Tools.lonlat((double) (Math.round((double) startLongitude * 1000000000 / 600000)) / 1000000000);
        return "";
    }

    public void setStartLongitudeStr(String startLongitudeStr) {
        this.startLongitudeStr = startLongitudeStr;
    }

    public String getStartLatitudeStr() {
        if (startLatitude != null)
            return Tools.lonlat((double) (Math.round((double) startLatitude * 1000000000 / 600000)) / 1000000000);
        return "";
    }

    public void setStartLatitudeStr(String startLatitudeStr) {
        this.startLatitudeStr = startLatitudeStr;
    }

    public String getEndLongitudeStr() {
        if (endLongitude != null)
            return Tools.lonlat((double) (Math.round((double) endLongitude * 1000000000 / 600000)) / 1000000000);
        return "";
    }

    public void setEndLongitudeStr(String endLongitudeStr) {
        this.endLongitudeStr = endLongitudeStr;
    }

    public String getEndLatitudeStr() {
        if (endLatitude != null)
            return Tools.lonlat((double) (Math.round((double) endLatitude * 1000000000 / 600000)) / 1000000000);
        return "";
    }

    public void setEndLatitudeStr(String endLatitudeStr) {
        this.endLatitudeStr = endLatitudeStr;
    }


    public String getStartTimeStr() {
        return Tools.toDateTimeStr(startTime);
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return Tools.toDateTimeStr(endTime);
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    // 0作业1航行2漂流3转载4在港5故障6未作业-天气原因7寻找鱼群8清洁网具9放置或回收阀艇、人工聚鱼';
    // 1   放网2   寻找鱼3   航行经过作业渔区4   未作业--船舶故障5   未作业--天气原因6   在港内--请注明7   清洁网具10  放置或回收阀艇、人工聚鱼装置或 PAYAOS
    //	1.  A SET 下钩作业
    //	2.  A DAY AT SEA BUT NOT FISHED AND NOT IN TRANSIT   在海上无作业且非转换渔场
    //	3.  TRANSIT 转换渔场
    //	4.  IN PORT*在港  *PLEASE SPECIFY 请说明在港原因
    public String getActionTypeStr() {
        if ("5".equals(logType)) {//金枪鱼围网
            if (actionType == null) return "";
            else if ("0".equals(actionType)) return "1（放网）";
            else if ("7".equals(actionType)) return "2（寻找鱼）";
            else if ("1".equals(actionType)) return "3（航行经过作业渔区）";
            else if ("5".equals(actionType)) return "4（未作业--船舶故障）";
            else if ("6".equals(actionType)) return "5（未作业--天气原因）";
            else if ("4".equals(actionType)) return "6（在港内--请注明）";
            else if ("8".equals(actionType)) return "7（清洁网具）";
            else if ("9".equals(actionType)) return "10（放置或回收阀艇、人工聚鱼装置或 PAYAOS）";
            else if ("3".equals(actionType)) return "11（转载）";
            else if ("2".equals(actionType)) return "12（漂流）";
        } else if ("1".equals(logType)) {//金枪鱼延绳钓
            if (actionType == null) return "";
            else if ("0".equals(actionType)) return "1（下钩作业）";
            else if ("2".equals(actionType)) return "2（在海上无作业且非转换渔场）";
            else if ("1".equals(actionType)) return "3（转换渔场）";
            else if ("4".equals(actionType)) return "4（在港）";
            else if ("3".equals(actionType)) return "5（转载）";
            else if ("5".equals(actionType)) return "6（故障）";
            else if ("6".equals(actionType)) return "7（未作业-天气原因）";
            else if ("7".equals(actionType)) return "8（寻找鱼群）";
            else if ("8".equals(actionType)) return "9（清洁网具）";
            else if ("9".equals(actionType)) return "10（放置或回收阀艇、人工聚鱼）";
        } else {
            if (actionType == null) return "";
            else if ("0".equals(actionType)) return "1（作业）";
            else if ("1".equals(actionType)) return "2（航行）";
            else if ("2".equals(actionType)) return "3（漂流）";
            else if ("3".equals(actionType)) return "4（转载）";
            else if ("4".equals(actionType)) return "5（在港）";
            else if ("5".equals(actionType)) return "6（故障）";
            else if ("6".equals(actionType)) return "7（未作业-天气原因）";
            else if ("7".equals(actionType)) return "8（寻找鱼群）";
            else if ("8".equals(actionType)) return "9（清洁网具）";
            else if ("9".equals(actionType)) return "10（放置或回收阀艇、人工聚鱼）";
        }
        return "";
    }

    public void setActionTypeStr(String actionTypeStr) {

    }

    public String getLogDateStr() {
        return Tools.toDateStr(logDate);
    }

    public void setLogDateStr(String logDateStr) {
        this.logDateStr = logDateStr;
    }

    public List<FishLogItem> getItems() {
        return items;
    }

    public void setItems(List<FishLogItem> items) {
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName == null ? null : shipName.trim();
    }

    public Integer getShipId() {
        return shipId;
    }

    public void setShipId(Integer shipId) {
        this.shipId = shipId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType == null ? null : logType.trim();
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType == null ? null : actionType.trim();
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getNetTime() {
        return netTime;
    }

    public void setNetTime(Integer netTime) {
        this.netTime = netTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(Integer startLongitude) {
        this.startLongitude = startLongitude;
    }

    public Integer getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(Integer startLatitude) {
        this.startLatitude = startLatitude;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(Integer endLongitude) {
        this.endLongitude = endLongitude;
    }

    public Integer getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(Integer endLatitude) {
        this.endLatitude = endLatitude;
    }

    public Integer getYegangchangdu() {
        return yegangchangdu;
    }

    public void setYegangchangdu(Integer yegangchangdu) {
        this.yegangchangdu = yegangchangdu;
    }

    public Integer getTuosu() {
        return tuosu;
    }

    public void setTuosu(Integer tuosu) {
        this.tuosu = tuosu;
    }

    public Integer getDiaojitaishu() {
        return diaojitaishu;
    }

    public void setDiaojitaishu(Integer diaojitaishu) {
        this.diaojitaishu = diaojitaishu;
    }

    public Integer getDandiaogoushu() {
        return dandiaogoushu;
    }

    public void setDandiaogoushu(Integer dandiaogoushu) {
        this.dandiaogoushu = dandiaogoushu;
    }

    public Integer getShoudiaorenshu() {
        return shoudiaorenshu;
    }

    public void setShoudiaorenshu(Integer shoudiaorenshu) {
        this.shoudiaorenshu = shoudiaorenshu;
    }

    public Integer getToudiaoshuishen() {
        return toudiaoshuishen;
    }

    public void setToudiaoshuishen(Integer toudiaoshuishen) {
        this.toudiaoshuishen = toudiaoshuishen;
    }

    public Integer getShuiwen() {
        return shuiwen;
    }

    public void setShuiwen(Integer shuiwen) {
        this.shuiwen = shuiwen;
    }

    public Integer getDengguanggonglv() {
        return dengguanggonglv;
    }

    public void setDengguanggonglv(Integer dengguanggonglv) {
        this.dengguanggonglv = dengguanggonglv;
    }

    public Integer getPanzhong() {
        return panzhong;
    }

    public void setPanzhong(Integer panzhong) {
        this.panzhong = panzhong;
    }

    public Integer getGoushu() {
        return goushu;
    }

    public void setGoushu(Integer goushu) {
        this.goushu = goushu;
    }

    public Integer getFuqiujiangoushu() {
        return fuqiujiangoushu;
    }

    public void setFuqiujiangoushu(Integer fuqiujiangoushu) {
        this.fuqiujiangoushu = fuqiujiangoushu;
    }

    public Integer getShuishen() {
        return shuishen;
    }

    public void setShuishen(Integer shuishen) {
        this.shuishen = shuishen;
    }

    public Integer getKaidengshu() {
        return kaidengshu;
    }

    public void setKaidengshu(Integer kaidengshu) {
        this.kaidengshu = kaidengshu;
    }

    public Integer getFengsu() {
        return fengsu;
    }

    public void setFengsu(Integer fengsu) {
        this.fengsu = fengsu;
    }

    public Integer getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(Integer fengxiang) {
        this.fengxiang = fengxiang;
    }

    public Integer getLiusu() {
        return liusu;
    }

    public void setLiusu(Integer liusu) {
        this.liusu = liusu;
    }

    public Integer getLiuxiang() {
        return liuxiang;
    }

    public void setLiuxiang(Integer liuxiang) {
        this.liuxiang = liuxiang;
    }

    public String getWeibuleixing() {
        return weibuleixing;
    }

    public void setWeibuleixing(String weibuleixing) {
        this.weibuleixing = weibuleixing == null ? null : weibuleixing.trim();
    }

    public Integer getJianyu() {
        return jianyu;
    }

    public void setJianyu(Integer jianyu) {
        this.jianyu = jianyu;
    }

    public Integer getHuangqi1() {
        return huangqi1;
    }

    public void setHuangqi1(Integer huangqi1) {
        this.huangqi1 = huangqi1;
    }

    public Integer getHuangqi2() {
        return huangqi2;
    }

    public void setHuangqi2(Integer huangqi2) {
        this.huangqi2 = huangqi2;
    }

    public Integer getDamu1() {
        return damu1;
    }

    public void setDamu1(Integer damu1) {
        this.damu1 = damu1;
    }

    public Integer getDamu2() {
        return damu2;
    }

    public void setDamu2(Integer damu2) {
        this.damu2 = damu2;
    }

    public String getQitayuzhongzhonglei() {
        return qitayuzhongzhonglei;
    }

    public void setQitayuzhongzhonglei(String qitayuzhongzhonglei) {
        this.qitayuzhongzhonglei = qitayuzhongzhonglei == null ? null : qitayuzhongzhonglei.trim();
    }

    public Integer getQitayuzhongchanliang() {
        return qitayuzhongchanliang;
    }

    public void setQitayuzhongchanliang(Integer qitayuzhongchanliang) {
        this.qitayuzhongchanliang = qitayuzhongchanliang;
    }

    public String getDiuqijinqiangyu() {
        return diuqijinqiangyu;
    }

    public void setDiuqijinqiangyu(String diuqijinqiangyu) {
        this.diuqijinqiangyu = diuqijinqiangyu == null ? null : diuqijinqiangyu.trim();
    }

    public Integer getDiuqijinqiangyuzhongliang() {
        return diuqijinqiangyuzhongliang;
    }

    public void setDiuqijinqiangyuzhongliang(Integer diuqijinqiangyuzhongliang) {
        this.diuqijinqiangyuzhongliang = diuqijinqiangyuzhongliang;
    }

    public String getQiuqijinqiangyuyuanyin() {
        return qiuqijinqiangyuyuanyin;
    }

    public void setQiuqijinqiangyuyuanyin(String qiuqijinqiangyuyuanyin) {
        this.qiuqijinqiangyuyuanyin = qiuqijinqiangyuyuanyin == null ? null : qiuqijinqiangyuyuanyin.trim();
    }

    public String getDiuqiqitayuzhongzhonglei() {
        return diuqiqitayuzhongzhonglei;
    }

    public void setDiuqiqitayuzhongzhonglei(String diuqiqitayuzhongzhonglei) {
        this.diuqiqitayuzhongzhonglei = diuqiqitayuzhongzhonglei == null ? null : diuqiqitayuzhongzhonglei.trim();
    }

    public Integer getDiuqiqitayuzhongshuliang() {
        return diuqiqitayuzhongshuliang;
    }

    public void setDiuqiqitayuzhongshuliang(Integer diuqiqitayuzhongshuliang) {
        this.diuqiqitayuzhongshuliang = diuqiqitayuzhongshuliang;
    }

    public Integer getDiuqiqitayuzhongzhongliang() {
        return diuqiqitayuzhongzhongliang;
    }

    public void setDiuqiqitayuzhongzhongliang(Integer diuqiqitayuzhongzhongliang) {
        this.diuqiqitayuzhongzhongliang = diuqiqitayuzhongzhongliang;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign == null ? null : callsign.trim();
    }
}