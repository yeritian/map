package com.shipmap.modules.base.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
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
 * @since 2019-03-30
 */
//@ExcelTarget("PortDetail")
@TableName("tb_port_detail")
public class PortDetail extends Model<PortDetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 港口名称
     */
    @TableField("port_name")
    private String portName;
    /**
     * 中文名
     */
//    @TableField("china_name")
    private String chinaname;
    /**
     * 英文名
     */
//    @TableField("english_name")
//    @Excel(name = "fishing",isImportField = "true_st")
    private String englishname;
    /**
     * 拉丁文
     */
//    @TableField("latin_name")
//    @Excel(name = "time",isImportField = "true_st")
    private String latinname;
    /**
     * 缩写
     */

//    @Excel(name = "NO",isImportField = "true_st")
    private String abbreviation;
    /**
     * 备注
     */
//    @Excel(name = "SC",isImportField = "true_st")
    private String annotation;
    //    @Excel(name = "hl",isImportField = "true_st")
    private String hl;
    //    @Excel(name = "ltd",isImportField = "true_st")
    private String ltd;
    //    @Excel(name = "bl",isImportField = "true_st")
    private String bl;
    //    @Excel(name = "fl",isImportField = "true_st")
    private String gpl;
    //    @Excel(name = "we",isImportField = "true_st")
    private String fl;
    //    @Excel(name = "re",isImportField = "true_st")
    private String kg;
    //    @Excel(name = "sa",isImportField = "true_st")
    private String num;
    //    @Excel(name = "EN",isImportField = "true_st")
    private String portid;
    /**
     * 采样时间
     *
     * @return
     */

    private Date samplingtime;

    public String getPortid() {
        return portid;
    }

    public void setPortid(String portid) {
        this.portid = portid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

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

    @Override
    protected Serializable pkVal() {
        return null;
    }

    public Date getSamplingtime() {
        return samplingtime;
    }

    public void setSamplingtime(Date samplingtime) {
        this.samplingtime = samplingtime;
    }
}
