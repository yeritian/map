package com.shipmap.modules.base.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

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
@TableName("tb_standard")
public class Standard extends Model<Standard> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    private String id;
    private String name;
    private String remark;
    @TableField("create_time")
    private Date createTime;
    private String creator;
    @TableField("update_time")
    private Date updateTime;
    private String updator;
    @TableLogic
    private Integer del;
    /**
     * 最小值，单位为g
     */
    private Integer minv;
    /**
     * 最大值，单位为g
     */
    private Integer maxv;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public Integer getMinv() {
        return minv;
    }

    public void setMinv(Integer minv) {
        this.minv = minv;
    }

    public Integer getMaxv() {
        return maxv;
    }

    public void setMaxv(Integer maxv) {
        this.maxv = maxv;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "Standard{" +
                "id=" + id +
                ", name=" + name +
                ", remark=" + remark +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ", updateTime=" + updateTime +
                ", updator=" + updator +
                ", del=" + del +
                ", minv=" + minv +
                ", maxv=" + maxv +
                "}";
    }
}
