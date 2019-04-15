package com.shipmap.modules.base.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2018-12-21
 */
@TableName("tb_ship_standard")
public class ShipStandard extends Model<ShipStandard> {

    private static final long serialVersionUID = 1L;

    @TableField("ship_id")
    private String shipId;
    @TableField("standard_id")
    private String standardId;
    @TableField("create_time")
    private Date createTime;
    private String creator;
    @TableField("update_time")
    private Date updateTime;
    private String updator;


    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
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

    @Override
    protected Serializable pkVal() {
        return this.shipId;
    }

    @Override
    public String toString() {
        return "ShipStandard{" +
                "shipId=" + shipId +
                ", standardId=" + standardId +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ", updateTime=" + updateTime +
                ", updator=" + updator +
                "}";
    }
}
