package com.shipmap.modules.fishing.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2018-12-25
 */
@TableName("tb_catch")
public class Catch extends Model<Catch> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 鱼id
     */
    @TableField("fish_id")
    private String fishId;
    /**
     * 捕捞量
     */
    private Double weight;
    /**
     * 捕捞数量
     */
    private Integer num;
    /**
     * 鱼舱号
     */
    @TableField("well_no")
    private String wellNo;
    /**
     * 丢弃/放生数量
     */
    private Integer discarded;
    /**
     * 丢弃重量(克)
     */
    @TableField("discarded_weight")
    private Integer discardedWeight;
    /**
     * 规格
     */
    @TableField("standard_id")
    private String standardId;
    /**
     * 单位
     */
    @TableField("unit_id")
    private String unitId;
    @TableField("log_id")
    private String logId;

    @TableField(exist = false)
    private String fishName;

    @TableField(exist = false)
    private String standardName;

    @TableField(exist = false)
    private String unitName;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    private String creator;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private String updator;

    public String getFishName() {
        return fishName;
    }

    public void setFishName(String fishName) {
        this.fishName = fishName;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFishId() {
        return fishId;
    }

    public void setFishId(String fishId) {
        this.fishId = fishId;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getWellNo() {
        return wellNo;
    }

    public void setWellNo(String wellNo) {
        this.wellNo = wellNo;
    }

    public Integer getDiscarded() {
        return discarded;
    }

    public void setDiscarded(Integer discarded) {
        this.discarded = discarded;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Integer getDiscardedWeight() {
        return discardedWeight;
    }

    public void setDiscardedWeight(Integer discardedWeight) {
        this.discardedWeight = discardedWeight;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Catch{" +
                "id=" + id +
                ", fishId=" + fishId +
                ", weight=" + weight +
                ", num=" + num +
                ", wellNo=" + wellNo +
                ", discarded=" + discarded +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ", updateTime=" + updateTime +
                ", updator=" + updator +
                ", discardedWeight=" + discardedWeight +
                ", standardId=" + standardId +
                ", unitId=" + unitId +
                "}";
    }
}
