package com.shipmap.modules.base.model;

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
 * 月报表
 * </p>
 *
 * @author ${author}
 * @since 2019-04-02
 */
@TableName("tb_monthly")
public class Monthly extends Model<Monthly> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 公司名称
     */
    @TableField("company_name")
    private String companyName;
    /**
     * 作业类型
     */
    @TableField("job_type")
    private String jobType;
    /**
     * 作业洋区
     */
    @TableField("job_the_area")
    private String jobTheArea;
    /**
     * 公海EEZ
     */
    @TableField("EEZ")
    private String eez;
    /**
     * 所属省份
     */
    private String province;
    /**
     * 作业时间
     */
    @TableField("job_time")
    private String jobTime;
    /**
     * 船名
     */
    @TableField("ship_name")
    private String shipName;
    /**
     * 作业天数
     */
    @TableField("job_num_of_days")
    private String jobNumOfDays;
    /**
     * 开始地点
     */
    @TableField("start_site")
    private String startSite;
    /**
     * 结束地点
     */
    @TableField("end_site")
    private String endSite;
    /**
     * 投钩数
     */
    @TableField("cast_hook_num")
    private String castHookNum;

    /**
     * 注释
     */
    private String annotation;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 修改人
     */
    private String updator;
    /**
     * 是否删除
     */
    @TableLogic
    private Integer del;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("elses")
    private String elseFish;

    /**
     * 渔船进港情况
     */
    @TableField("ship_put_in")
    private String shipPutIn;
    /**
     * 渔船专属经济区入渔情况
     */
    @TableField("ship_EEZ_the_fish")
    private String shipEezTheFish;
    /**
     * 渔船无产量原因
     */
    @TableField("ship_no_production")
    private String shipNoProduction;

    public String getShipPutIn() {
        return shipPutIn;
    }

    public void setShipPutIn(String shipPutIn) {
        this.shipPutIn = shipPutIn;
    }

    public String getShipEezTheFish() {
        return shipEezTheFish;
    }

    public void setShipEezTheFish(String shipEezTheFish) {
        this.shipEezTheFish = shipEezTheFish;
    }

    public String getShipNoProduction() {
        return shipNoProduction;
    }

    public void setShipNoProduction(String shipNoProduction) {
        this.shipNoProduction = shipNoProduction;
    }

    public String getElseFish() {
        return elseFish;
    }

    public void setElseFish(String elseFish) {
        this.elseFish = elseFish;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobTheArea() {
        return jobTheArea;
    }

    public void setJobTheArea(String jobTheArea) {
        this.jobTheArea = jobTheArea;
    }

    public String getEez() {
        return eez;
    }

    public void setEez(String eez) {
        this.eez = eez;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getJobTime() {
        return jobTime;
    }

    public void setJobTime(String jobTime) {
        this.jobTime = jobTime;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getJobNumOfDays() {
        return jobNumOfDays;
    }

    public void setJobNumOfDays(String jobNumOfDays) {
        this.jobNumOfDays = jobNumOfDays;
    }

    public String getStartSite() {
        return startSite;
    }

    public void setStartSite(String startSite) {
        this.startSite = startSite;
    }

    public String getEndSite() {
        return endSite;
    }

    public void setEndSite(String endSite) {
        this.endSite = endSite;
    }

    public String getCastHookNum() {
        return castHookNum;
    }

    public void setCastHookNum(String castHookNum) {
        this.castHookNum = castHookNum;
    }


    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Monthly{" +
                "id='" + id + '\'' +
                ", companyName='" + companyName + '\'' +
                ", jobType='" + jobType + '\'' +
                ", jobTheArea='" + jobTheArea + '\'' +
                ", eez='" + eez + '\'' +
                ", province='" + province + '\'' +
                ", jobTime='" + jobTime + '\'' +
                ", shipName='" + shipName + '\'' +
                ", jobNumOfDays='" + jobNumOfDays + '\'' +
                ", startSite='" + startSite + '\'' +
                ", endSite='" + endSite + '\'' +
                ", castHookNum='" + castHookNum + '\'' +
                ", annotation='" + annotation + '\'' +
                ", creator='" + creator + '\'' +
                ", updator='" + updator + '\'' +
                ", del=" + del +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", elseFish='" + elseFish + '\'' +
                ", shipPutIn='" + shipPutIn + '\'' +
                ", shipEezTheFish='" + shipEezTheFish + '\'' +
                ", shipNoProduction='" + shipNoProduction + '\'' +
                '}';
    }
}
