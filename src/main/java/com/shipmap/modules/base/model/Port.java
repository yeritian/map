package com.shipmap.modules.base.model;

import com.baomidou.mybatisplus.activerecord.Model;
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
 * @since 2019-03-27
 */
@TableName("tb_port")
public class Port extends Model<Port> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 序号
     */
    private String number;
    /**
     * 港口名称
     */
//    @TableField("portname")
    private String portname;
    /**
     * 渔业公司
     */
//    @Excel(name = "渔业公司company",isImportField = "true_st")
    private String fishery;
    /**
     * 作业范围
     */
//    @Excel(name = "作业范围fishing area",isImportField = "true_st")
//    @TableField("jobarea")
    private String jobarea;
    /**
     * 开始作业时间
     */
//    @TableField(value = "createtime", fill = FieldFill.INSERT)
    private String createtime;
    /**
     * 结束作业时间
     */
//    @TableField("end_time")
    private String endtime;
    /**
     * 渔船名
     */
//    @Excel(name = "渔船名Vessel",isImportField = "true_st")
//    @TableField("fish_name")
    private String fishname;
    /**
     * 采样时间
     */
//    @TableField("samplingtime")
    private String samplingtime;
    /**
     * 记录者
     */
//    @Excel(name = "记录者recorder",isImportField = "true_st")
    private String record;
    /**
     * 天气情况
     */
//    @Excel(name = "天气情况weather",isImportField = "true_st")
    private String nwp;
    /**
     * 类型
     */
    private String type;
    /**
     * 更新时间
     */
//    @TableField(value = "updatetime", fill = FieldFill.INSERT_UPDATE)
    private Date updatetime;
    /**
     * 是否删除
     */
    @TableLogic
    private Integer del;
    /**
     * 中文名
     */
//    @TableField("chinaname")
    private String chinaname;
    /**
     * 英文名
     */
//    @TableField("english_name")
    private String englishname;
    /**
     * 拉丁文名
     */
//    @TableField("latin_name")
    private String latinname;
    /**
     * 缩写
     */
    private String abbreviation;
    private String hl;
    private String ltd;
    private String bl;
    //    @Excel(name = "SPECIESE CODE",isImportField = "true_st")
    private String gpl;
    private String fl;
    private String kg;
    /**
     * 备注
     */
    private String annotation;

    //作业时间
    private String fishingtime;

    public String getChinaname() {
        return chinaname;
    }

    public void setChinaname(String chinaname) {
        this.chinaname = chinaname;
    }

    public String getEnglishname() {
        return englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname;
    }

    public String getLatinname() {
        return latinname;
    }

    public String getFishingtime() {
        return fishingtime;
    }

    public void setFishingtime(String fishingtime) {
        this.fishingtime = fishingtime;
    }

    public void setLatinname(String latinname) {
        this.latinname = latinname;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getHl() {
        return hl;
    }

    public void setHl(String hl) {
        this.hl = hl;
    }

    public String getLtd() {
        return ltd;
    }

    public void setLtd(String ltd) {
        this.ltd = ltd;
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }

    public String getGpl() {
        return gpl;
    }

    public void setGpl(String gpl) {
        this.gpl = gpl;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getKg() {
        return kg;
    }

    public void setKg(String kg) {
        this.kg = kg;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPortname() {
        return portname;
    }

    public void setPortname(String portname) {
        this.portname = portname;
    }

    public String getJobarea() {
        return jobarea;
    }

    public void setJobarea(String jobarea) {
        this.jobarea = jobarea;
    }

    public String getFishname() {
        return fishname;
    }

    public void setFishname(String fishname) {
        this.fishname = fishname;
    }

    public String getSamplingtime() {
        return samplingtime;
    }

    public void setSamplingtime(String samplingtime) {
        this.samplingtime = samplingtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getFishery() {
        return fishery;
    }

    public void setFishery(String fishery) {
        this.fishery = fishery;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getNwp() {
        return nwp;
    }

    public void setNwp(String nwp) {
        this.nwp = nwp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "Port{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", portname='" + portname + '\'' +
                ", fishery='" + fishery + '\'' +
                ", jobarea='" + jobarea + '\'' +
                ", createtime='" + createtime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", fishname='" + fishname + '\'' +
                ", samplingtime='" + samplingtime + '\'' +
                ", record='" + record + '\'' +
                ", nwp='" + nwp + '\'' +
                ", type='" + type + '\'' +
                ", updatetime=" + updatetime +
                ", del=" + del +
                ", chinaname='" + chinaname + '\'' +
                ", englishname='" + englishname + '\'' +
                ", latinname='" + latinname + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", hl='" + hl + '\'' +
                ", ltd='" + ltd + '\'' +
                ", bl='" + bl + '\'' +
                ", gpl='" + gpl + '\'' +
                ", fl='" + fl + '\'' +
                ", kg='" + kg + '\'' +
                ", annotation='" + annotation + '\'' +
                ", fishingtime='" + fishingtime + '\'' +
                '}';
    }
}
