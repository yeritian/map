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
@TableName("observer_fishcatch")
public class Fishcatch extends Model<Fishcatch> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键-渔获量-捕捞努力量id
     */
    @TableId(value = "obfishcatchid", type = IdType.UUID)
    private String obfishcatchid;
    /**
     * 钩次
     */
    private String obfishcatchhooks;
    /**
     * 下钩日期
     */
    private String obfishcatchdatehook;
    /**
     * 观察筐数
     */
    private Integer obbasketnum;
    /**
     * 总筐数
     */
    private Integer obbaskettotsum;
    /**
     * 外键--观察员信息
     */
    private String observerinfoid;
    /**
     * 观察产量-尾数
     */
    @TableField(exist = false)
    private Integer obtrailnum;
    /**
     * 观察产量-重量
     */
    @TableField(exist = false)
    private Integer oboutput;
    /**
     * 总产量-尾数
     */
    @TableField(exist = false)
    private Integer obtotletailnum;
    /**
     * 总产量-重量
     */
    @TableField(exist = false)
    private Integer obtotleoutput;
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 注释
     */
    private String fcremark;
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

    /*中文名*/
    // private String chinesename;

    public String getobfishcatchid() {
        return obfishcatchid;
    }

    public void setobfishcatchid(String obfishcatchid) {
        this.obfishcatchid = obfishcatchid;
    }

    public String getObfishcatchhooks() {
        return obfishcatchhooks;
    }

    public void setObfishcatchhooks(String obfishcatchhooks) {
        this.obfishcatchhooks = obfishcatchhooks;
    }

    public String getObfishcatchdatehook() {
        return obfishcatchdatehook;
    }

    public void setObfishcatchdatehook(String obfishcatchdatehook) {
        this.obfishcatchdatehook = obfishcatchdatehook;
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

    public String getObserverinfoid() {
        return observerinfoid;
    }

    public void setObserverinfoid(String observerinfoid) {
        this.observerinfoid = observerinfoid;
    }

    public Integer getObtrailnum() {
        return obtrailnum;
    }

    public void setObtrailnum(Integer obtrailnum) {
        this.obtrailnum = obtrailnum;
    }

    public Integer getOboutput() {
        return oboutput;
    }

    public void setOboutput(Integer oboutput) {
        this.oboutput = oboutput;
    }

    public Integer getObtotletailnum() {
        return obtotletailnum;
    }

    public void setObtotletailnum(Integer obtotletailnum) {
        this.obtotletailnum = obtotletailnum;
    }

    public Integer getObtotleoutput() {
        return obtotleoutput;
    }

    public void setObtotleoutput(Integer obtotleoutput) {
        this.obtotleoutput = obtotleoutput;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFcremark() {
        return fcremark;
    }

    public void setFcremark(String fcremark) {
        this.fcremark = fcremark;
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

   /* public String getChinesename() {
        return chinesename;
    }

    public void setChinesename(String chinesename) {
        this.chinesename = chinesename;
    }*/


    @Override
    protected Serializable pkVal() {
        return this.obfishcatchid;
    }

    @Override
    public String toString() {
        return "Fishcatch{" +
                " obfishcatchid=" + obfishcatchid +
                ", obfishcatchhooks=" + obfishcatchhooks +
                ", obfishcatchdatehook=" + obfishcatchdatehook +
                ", obbasketnum=" + obbasketnum +
                ", obbaskettotsum=" + obbaskettotsum +
                ", observerinfoid=" + observerinfoid +
                ", obtrailnum=" + obtrailnum +
                ", oboutput=" + oboutput +
                ", obtotletailnum=" + obtotletailnum +
                ", obtotleoutput=" + obtotleoutput +
                ", updateTime=" + updateTime +
                ", fcremark=" + fcremark +

                "}";
    }
}
