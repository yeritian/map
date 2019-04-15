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
 * @since 2019-04-09
 */
@TableName("observer_talentsinfo")
public class Talentsinfo extends Model<Talentsinfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private String sex;
    /**
     * 海员证件号
     */
    private String seamanbookno;
    /**
     * 身份证号
     */
    private String idnumber;
    /**
     * 健康证号
     */
    private String healthbookno;
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
     * 修改时间
     */
    @TableField(value = "updata_time", fill = FieldFill.INSERT)
    private Date updataTime;
    /**
     * 修改人
     */
    private String updator;
    /**
     * 删除
     */
    @TableLogic
    private Integer del;


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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSeamanbookno() {
        return seamanbookno;
    }

    public void setSeamanbookno(String seamanbookno) {
        this.seamanbookno = seamanbookno;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public String getHealthbookno() {
        return healthbookno;
    }

    public void setHealthbookno(String healthbookno) {
        this.healthbookno = healthbookno;
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

    public Date getUpdataTime() {
        return updataTime;
    }

    public void setUpdataTime(Date updataTime) {
        this.updataTime = updataTime;
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
        return this.id;
    }

    @Override
    public String toString() {
        return "Talentsinfo{" +
                "id=" + id +
                ", name=" + name +
                ", sex=" + sex +
                ", seamanbookno=" + seamanbookno +
                ", idnumber=" + idnumber +
                ", healthbookno=" + healthbookno +
                ", createTime=" + createTime +
                ", creator=" + creator +
                ", updataTime=" + updataTime +
                ", updator=" + updator +
                ", del=" + del +
                "}";
    }
}
