package com.shipmap.modules.fishing.model;

public class FishLogItem {

    private Integer id;

    private Integer logId;
    //日志类型：1为金枪鱼延绳钓，2为围网，3为拖网，4为鱿钓，5为金枪鱼围网
    private String type;
    //鱼种名称
    private String fishName;
    //渔获量，单位：0.1Kg
    private Long yuhuoliang;
    private String yuhuoliangStr;
    //留仓尾数
    private Integer liucangweishu;
    //留仓重量
    private Integer liucangzhongliang;
    private String liucangzhongliangStr;
    //丢弃尾数
    private Integer diuqiweishu;
    //鱼舱号
    private String fishholdno;
    //鱼舱号名称
    private String fishholdname;

    private String guige;
    private Integer shuliang;
    private String danwei;
    private Integer shifoudiuqi;
    private String diuqiyuanyin;

    public String getGuige() {
        return guige;
    }

    public void setGuige(String guige) {
        this.guige = guige;
    }

    public Integer getShuliang() {
        return shuliang;
    }

    public void setShuliang(Integer shuliang) {
        this.shuliang = shuliang;
    }

    public String getDanwei() {
        return danwei;
    }

    public void setDanwei(String danwei) {
        this.danwei = danwei;
    }

    public Integer getShifoudiuqi() {
        return shifoudiuqi;
    }

    public void setShifoudiuqi(Integer shifoudiuqi) {
        this.shifoudiuqi = shifoudiuqi;
    }

    public String getDiuqiyuanyin() {
        return diuqiyuanyin;
    }

    public void setDiuqiyuanyin(String diuqiyuanyin) {
        this.diuqiyuanyin = diuqiyuanyin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYuhuoliangStr() {
        if (yuhuoliang == null) return "";
        if ("2".equals(type) || "4".equals(type)) {
            return ((double) yuhuoliang) / 10000 + ""; //传过来单位为kg 转化为吨，再转化单位0.1kg
        }
        return ((double) yuhuoliang) / 10 + "";
    }

    public void setYuhuoliangStr(String yuhuoliangStr) {
        this.yuhuoliangStr = yuhuoliangStr;
    }

    public void setLiucangzhongliangStr(String liucangzhongliangStr) {
        this.liucangzhongliangStr = liucangzhongliangStr;
    }

    public String getLiucangzhongliangStr() {
        if (liucangzhongliang == null) {
            if (yuhuoliang == null) return "";
            return ((double) yuhuoliang) / 10 + "";
        }
        return ((double) liucangzhongliang) / 10 + "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getFishName() {
        return fishName;
    }

    public void setFishName(String fishName) {
        this.fishName = fishName == null ? null : fishName.trim();
    }

    public Long getYuhuoliang() {
        return yuhuoliang;
    }

    public void setYuhuoliang(Long yuhuoliang) {
        this.yuhuoliang = yuhuoliang;
    }

    public Integer getLiucangweishu() {
        return liucangweishu;
    }

    public void setLiucangweishu(Integer liucangweishu) {
        this.liucangweishu = liucangweishu;
    }

    public Integer getLiucangzhongliang() {
        return liucangzhongliang;
    }

    public void setLiucangzhongliang(Integer liucangzhongliang) {
        this.liucangzhongliang = liucangzhongliang;
    }

    public Integer getDiuqiweishu() {
        return diuqiweishu;
    }

    public void setDiuqiweishu(Integer diuqiweishu) {
        this.diuqiweishu = diuqiweishu;
    }

    public String getFishholdno() {
        return fishholdno;
    }

    public void setFishholdno(String fishholdno) {
        this.fishholdno = fishholdno == null ? null : fishholdno.trim();
    }

    public String getFishholdname() {
        return fishholdname;
    }

    public void setFishholdname(String fishholdname) {
        this.fishholdname = fishholdname == null ? null : fishholdname.trim();
    }
}