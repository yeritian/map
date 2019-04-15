package com.shipmap.modules.observer.model;

public class VVO {


    /**
     * 主键--观察员信息
     */
    private String observerinfoid;
    /**
     * 序号
     */
    private String observerinfono;
    /**
     * 观察海域
     */
    private String observeoceanarea;
    /**
     * 姓名
     */
    private String observerinfoname;
    /**
     * 开始观察日期
     */
    private String startobservedate;
    /**
     * 结束观察日期
     */
    private String endobservedate;
    /**
     * 登船地点
     */
    private String shipport;
    /**
     * 船名
     */
    private String shipname;
    /**
     * 登船时间
     */
    private String shipdate;
    /**
     * 登陆地点（离船）
     */
    private String destinationport;
    /**
     * 登陆日期
     */
    private String disembarkdate;
    /**
     * 行程行程范围
     */
    private String trapezerange;

    /**
     * 公司
     */
    private String company;

    /**
     * 年份
     */
    private String years;
    /**
     * 类型
     */
    private String obfishtype;

    /*作业钩次*/
    private Integer counthooktimes;
    /*观察期间渔获记录*/
    private String fthooktimes;
    /*渔捞日志记录*/
    private Integer hooklog;
    /*渔获量—捕捞努力量统计*/
    private Integer obfishcatch;
    /*观察筐数*/
    private Integer obbasketnum;
    /*总筐数*/
    private Integer obbaskettotsum;
    /*单钩筐数HPB*/
    private Integer avgsinglebaskethook;


    public String getObserverinfoid() {
        return observerinfoid;
    }

    public void setObserverinfoid(String observerinfoid) {
        this.observerinfoid = observerinfoid;
    }

    public String getObserverinfono() {
        return observerinfono;
    }

    public void setObserverinfono(String observerinfono) {
        this.observerinfono = observerinfono;
    }

    public String getObserveoceanarea() {
        return observeoceanarea;
    }

    public void setObserveoceanarea(String observeoceanarea) {
        this.observeoceanarea = observeoceanarea;
    }

    public String getObserverinfoname() {
        return observerinfoname;
    }

    public void setObserverinfoname(String observerinfoname) {
        this.observerinfoname = observerinfoname;
    }

    public String getStartobservedate() {
        return startobservedate;
    }

    public void setStartobservedate(String startobservedate) {
        this.startobservedate = startobservedate;
    }

    public String getEndobservedate() {
        return endobservedate;
    }

    public void setEndobservedate(String endobservedate) {
        this.endobservedate = endobservedate;
    }

    public String getShipport() {
        return shipport;
    }

    public void setShipport(String shipport) {
        this.shipport = shipport;
    }

    public String getShipname() {
        return shipname;
    }

    public void setShipname(String shipname) {
        this.shipname = shipname;
    }

    public String getShipdate() {
        return shipdate;
    }

    public void setShipdate(String shipdate) {
        this.shipdate = shipdate;
    }

    public String getDestinationport() {
        return destinationport;
    }

    public void setDestinationport(String destinationport) {
        this.destinationport = destinationport;
    }

    public String getDisembarkdate() {
        return disembarkdate;
    }

    public void setDisembarkdate(String disembarkdate) {
        this.disembarkdate = disembarkdate;
    }

    public String getTrapezerange() {
        return trapezerange;
    }

    public void setTrapezerange(String trapezerange) {
        this.trapezerange = trapezerange;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getObfishtype() {
        return obfishtype;
    }

    public void setObfishtype(String obfishtype) {
        this.obfishtype = obfishtype;
    }

    public Integer getCounthooktimes() {
        return counthooktimes;
    }

    public void setCounthooktimes(Integer counthooktimes) {
        this.counthooktimes = counthooktimes;
    }

    public String getFthooktimes() {
        return fthooktimes;
    }

    public void setFthooktimes(String fthooktimes) {
        this.fthooktimes = fthooktimes;
    }

    public Integer getHooklog() {
        return hooklog;
    }

    public void setHooklog(Integer hooklog) {
        this.hooklog = hooklog;
    }

    public Integer getObfishcatch() {
        return obfishcatch;
    }

    public void setObfishcatch(Integer obfishcatch) {
        this.obfishcatch = obfishcatch;
    }

    public Integer getObbasketnum() {
        return obbasketnum;
    }

    public void setObbasketnum(Integer obbasketnum) {
        this.obbasketnum = obbasketnum;
    }

    public Integer getObbaskettotsum() {
        return obbaskettotsum;
    }

    public void setObbaskettotsum(Integer obbaskettotsum) {
        this.obbaskettotsum = obbaskettotsum;
    }

    public Integer getAvgsinglebaskethook() {
        return avgsinglebaskethook;
    }

    public void setAvgsinglebaskethook(Integer avgsinglebaskethook) {
        this.avgsinglebaskethook = avgsinglebaskethook;
    }

    @Override
    public String toString() {
        return "VVO{" +
                "observerinfoid='" + observerinfoid + '\'' +
                ", observerinfono='" + observerinfono + '\'' +
                ", observeoceanarea='" + observeoceanarea + '\'' +
                ", observerinfoname='" + observerinfoname + '\'' +
                ", startobservedate='" + startobservedate + '\'' +
                ", endobservedate='" + endobservedate + '\'' +
                ", shipport='" + shipport + '\'' +
                ", shipname='" + shipname + '\'' +
                ", shipdate='" + shipdate + '\'' +
                ", destinationport='" + destinationport + '\'' +
                ", disembarkdate='" + disembarkdate + '\'' +
                ", trapezerange='" + trapezerange + '\'' +
                ", company='" + company + '\'' +
                ", years='" + years + '\'' +
                ", obfishtype='" + obfishtype + '\'' +
                ", counthooktimes=" + counthooktimes +
                ", fthooktimes='" + fthooktimes + '\'' +
                ", hooklog=" + hooklog +
                ", obfishcatch=" + obfishcatch +
                ", obbasketnum=" + obbasketnum +
                ", obbaskettotsum=" + obbaskettotsum +
                ", avgsinglebaskethook=" + avgsinglebaskethook +
                '}';
    }
}
