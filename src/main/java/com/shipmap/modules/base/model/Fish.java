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
 * @since 2018-12-19
 */
@TableName("tb_fish")
public class Fish extends Model<Fish> {

    private static final long serialVersionUID = 1L;

    /**
     * 鱼种id
     */
    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 鱼种名称
     */
    private String name;
    /**
     * 父类鱼种名称
     */
    private String parentName;
    /**
     * 备注
     */
    private String remark;
    @TableField("update_time")
    private Date updateTime;
    private String updator;
    @TableField("create_time")
    private Date createTime;
    private String creator;

    @TableLogic
    private Integer del;

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "Fish{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", parentName='" + parentName + '\'' +
                ", remark='" + remark + '\'' +
                ", updateTime=" + updateTime +
                ", updator='" + updator + '\'' +
                ", createTime=" + createTime +
                ", creator='" + creator + '\'' +
                ", del=" + del +
                '}';
    }
}
