package com.shipmap.modules.fishing.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.shipmap.framework.utils.TypeUtil;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2018-12-25
 */
@TableName("tb_discard")
public class Discard extends Model<Discard> {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("fish_id")
    private String fishId;
    /**
     * 重量（克）
     */
    private Double weight;

    /**
     * 数量
     */
    private Integer sum;
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
    /**
     * 原因
     */
    private String reason;
    @TableField("reason_note")
    private String reasonNote;

    public String getReasonStr() {
        if ("4".equals(reason)) {
            return TypeUtil.getDiscardReasonType(reason) + reasonNote;
        } else {
            return TypeUtil.getDiscardReasonType(reason);
        }
    }

    @TableField("log_id")
    private String logId;

    @TableField(exist = false)
    private String fishName;

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

    public String getFishName() {
        return fishName;
    }

    public void setFishName(String fishName) {
        this.fishName = fishName;
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

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReasonNote() {
        return reasonNote;
    }

    public void setReasonNote(String reasonNote) {
        this.reasonNote = reasonNote;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Discard{" +
                "id=" + id +
                ", fishId=" + fishId +
                ", weight=" + weight +
                ", sum=" + sum +
                ", reason=" + reason +
                ", reasonNote=" + reasonNote +
                "}";
    }
}
