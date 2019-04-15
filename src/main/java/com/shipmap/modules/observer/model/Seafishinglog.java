package com.shipmap.modules.observer.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
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
@ExcelTarget("Seafishinglog")
@TableName("observer_seafishinglog")
public class Seafishinglog extends Model<Seafishinglog> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键-渔捞日志
     */
    @TableId(value = "seafishingid", type = IdType.UUID)
    private String seafishingid;
    /**
     * 外键-观察员信息
     */
    private String observerinfoid;
    /**
     * 作业钩次
     */
    @Excel(name = "作业钩次")
    private String homeworkhook;
    /**
     * 下钩开始日期
     */
    @Excel(name = "下钩开始日期", importFormat = "yyyy-MM-dd hh:mm:ss")
    private Date seastartdatehookfu;

    // @Excel(name="下钩开始日期",importFormat = "yyyy-MM-dd hh:mm:ss")
    private String seastartdatehook;
    /**
     * 实际下钩数
     */
    @Excel(name = "实际下钩数")
    private Integer realityhook;
    /**
     * 中文名
     */
    @Excel(name = "中文名")
    private String chinesename;
    /**
     * 全重WW
     */
    @Excel(name = "全重WW")
    private Integer fullweightww;
    /**
     * 加工重量GT
     */
    @Excel(name = "加工重量GT")
    private Integer workingweighgt;
    /**
     * 加工重量GX
     */
    @Excel(name = "加工重量GX")
    private Integer workingweighgx;
    /**
     * 其它加工重量
     */
    @Excel(name = "其它加工重量")
    private Integer otherworkingweight;
    /**
     * 说明
     */
    @Excel(name = "说明")
    private String explain;
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 注释
     */
    private String seafishingremark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 修改人
     */
    private String updator;
    /**
     * 删除
     */
    @TableLogic
    private Integer del;


    public String getSeafishingid() {
        return seafishingid;
    }

    public void setSeafishingid(String seafishingid) {
        this.seafishingid = seafishingid;
    }

    public String getObserverinfoid() {
        return observerinfoid;
    }

    public void setObserverinfoid(String observerinfoid) {
        this.observerinfoid = observerinfoid;
    }

    public String getHomeworkhook() {
        return homeworkhook;
    }

    public void setHomeworkhook(String homeworkhook) {
        this.homeworkhook = homeworkhook;
    }

    public String getSeastartdatehook() {
        return seastartdatehook;
    }

    public void setSeastartdatehook(String seastartdatehook) {
        this.seastartdatehook = seastartdatehook;
    }

    public Date getSeastartdatehookfu() {
        return seastartdatehookfu;
    }

    public void setSeastartdatehookfu(Date seastartdatehookfu) {
        this.seastartdatehookfu = seastartdatehookfu;
    }

    public Integer getRealityhook() {
        return realityhook;
    }

    public void setRealityhook(Integer realityhook) {
        this.realityhook = realityhook;
    }

    public String getChinesename() {
        return chinesename;
    }

    public void setChinesename(String chinesename) {
        this.chinesename = chinesename;
    }

    public Integer getFullweightww() {
        return fullweightww;
    }

    public void setFullweightww(Integer fullweightww) {
        this.fullweightww = fullweightww;
    }

    public Integer getWorkingweighgt() {
        return workingweighgt;
    }

    public void setWorkingweighgt(Integer workingweighgt) {
        this.workingweighgt = workingweighgt;
    }

    public Integer getWorkingweighgx() {
        return workingweighgx;
    }

    public void setWorkingweighgx(Integer workingweighgx) {
        this.workingweighgx = workingweighgx;
    }

    public Integer getOtherworkingweight() {
        return otherworkingweight;
    }

    public void setOtherworkingweight(Integer otherworkingweight) {
        this.otherworkingweight = otherworkingweight;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSeafishingremark() {
        return seafishingremark;
    }

    public void setSeafishingremark(String seafishingremark) {
        this.seafishingremark = seafishingremark;
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


    @Override
    protected Serializable pkVal() {
        return this.seafishingid;
    }

    @Override
    public String toString() {
        return "Seafishinglog{" +
                "seafishingid=" + seafishingid +
                ", observerinfoid=" + observerinfoid +
                ", homeworkhook=" + homeworkhook +
                ", seastartdatehook=" + seastartdatehook +
                ", realityhook=" + realityhook +
                ", chinesename=" + chinesename +
                ", fullweightww=" + fullweightww +
                ", workingweighgt=" + workingweighgt +
                ", workingweighgx=" + workingweighgx +
                ", otherworkingweight=" + otherworkingweight +
                ", explain=" + explain +
                ", updateTime=" + updateTime +
                ", seafishingremark=" + seafishingremark +
                "}";
    }
}
