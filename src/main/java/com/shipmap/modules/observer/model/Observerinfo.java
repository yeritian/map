package com.shipmap.modules.observer.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2019-03-28
 */
@TableName("observer_observerinfo")
public class Observerinfo extends Model<Observerinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键--观察员信息
     */
    @TableId(value = "observerinfoid", type = IdType.UUID)
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
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
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

    /*创建时间*/
    @TableField(value = "create_time", fill = FieldFill.INSERT_UPDATE)
    private Date createTime;
    /*创建人*/
    private String creator;
    /*修改人*/
    private String updator;
    /*删除*/
    @TableLogic
    private Integer del;


    @TableField(exist = false)
    private Integer counthooktimes;
    @TableField(exist = false)
    private Integer avgsinglebaskethook;
    @TableField(exist = false)
    private Integer sumrealitytotalbaskets;
    @TableField(exist = false)
    private Integer sumobbaskets;
    @TableField(exist = false)
    private Integer hooklog;
    @TableField(exist = false)
    private Integer obfishcatch;
    @TableField(exist = false)
    private Integer obbasketnum;
    @TableField(exist = false)
    private Integer obbaskettotsum;
    @TableField(exist = false)
    private String fthooktimes;


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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }


    public Integer getCounthooktimes() {
        if (counthooktimes == null) {
            counthooktimes = 0;
        } else {
            counthooktimes = counthooktimes;
        }
        return counthooktimes;
    }

    public void setCounthooktimes(Integer counthooktimes) {
        this.counthooktimes = counthooktimes;
    }

    public Integer getAvgsinglebaskethook() {
        return avgsinglebaskethook;
    }

    public void setAvgsinglebaskethook(Integer avgsinglebaskethook) {
        this.avgsinglebaskethook = avgsinglebaskethook;
    }

    public Integer getSumrealitytotalbaskets() {
        return sumrealitytotalbaskets;
    }

    public void setSumrealitytotalbaskets(Integer sumrealitytotalbaskets) {
        this.sumrealitytotalbaskets = sumrealitytotalbaskets;
    }

    public Integer getSumobbaskets() {
        return sumobbaskets;
    }

    public void setSumobbaskets(Integer sumobbaskets) {
        this.sumobbaskets = sumobbaskets;
    }

    public Integer getHooklog() {
        if (hooklog == null) {
            hooklog = 0;
        } else {
            hooklog = hooklog;
        }
        return hooklog;
    }

    public void setHooklog(Integer hooklog) {
        this.hooklog = hooklog;
    }

    public Integer getObfishcatch() {
        if (obfishcatch == null) {
            obfishcatch = 0;
        } else {
            obfishcatch = obfishcatch;
        }
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

    public String getFthooktimes() {
        if (fthooktimes == null) {
            fthooktimes = "0";
        } else {
            fthooktimes = fthooktimes;
        }
        return fthooktimes;
    }

    public void setFthooktimes(String fthooktimes) {
        this.fthooktimes = fthooktimes;
    }


    @Override
    protected Serializable pkVal() {
        return this.observerinfoid;
    }

    @Override
    public String toString() {
        return "Observerinfo{" +
                "observerinfoid=" + observerinfoid +
                ", observerinfono=" + observerinfono +
                ", observeoceanarea=" + observeoceanarea +
                ", observerinfoname=" + observerinfoname +
                ", startobservedate=" + startobservedate +
                ", endobservedate=" + endobservedate +
                ", shipport=" + shipport +
                ", shipname=" + shipname +
                ", shipdate=" + shipdate +
                ", destinationport=" + destinationport +
                ", disembarkdate=" + disembarkdate +
                ", trapezerange=" + trapezerange +
                ", updateTime=" + updateTime +
                ", company=" + company +
                ", years=" + years +
                ", obfishtype=" + obfishtype +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ", updator=" + updator +
                ", del=" + del +
                ", counthooktimes=" + counthooktimes +
                ", avgsinglebaskethook=" + avgsinglebaskethook +
                ", sumrealitytotalbaskets=" + sumrealitytotalbaskets +
                ", sumobbaskets=" + sumobbaskets +

                ", hooklog=" + hooklog +
                ", obfishcatch=" + obfishcatch +
                ", obbasketnum=" + obbasketnum +
                ", obbaskettotsum=" + obbaskettotsum +
                ", fthooktimes=" + fthooktimes +
                "}";
    }
}
