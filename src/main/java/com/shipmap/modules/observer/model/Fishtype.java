package com.shipmap.modules.observer.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2019-03-28
 */
@ExcelTarget("Fishtype")
@TableName("observer_fishtype")
public class Fishtype extends Model<Fishtype> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键-鱼种类型
     */
    @TableId(value = "fishtypeid", type = IdType.UUID)
    private String fishtypeid;
    /**
     * 鱼种名称（科学名）
     */
    private String fishsciencename;
    /**
     * 鱼种代码
     */
    @TableField(exist = false)
    private String fishcode;
    /**
     * 中文名
     */
    @Excel(name = "中文名")
    private String chinesename;
    /**
     * 英文名
     */
    @TableField(exist = false)
    private String englishname;

    private String obtrailnum;
    private String oboutput;
    private String obtotletailnum;
    private String obtotleoutput;

    private String obfishcatchid;

    public String getFishtypeid() {
        return fishtypeid;
    }

    public void setFishtypeid(String fishtypeid) {
        this.fishtypeid = fishtypeid;
    }

    public String getFishsciencename() {
        return fishsciencename;
    }

    public void setFishsciencename(String fishsciencename) {
        this.fishsciencename = fishsciencename;
    }

    public String getFishcode() {
        return fishcode;
    }

    public void setFishcode(String fishcode) {
        this.fishcode = fishcode;
    }

    public String getChinesename() {
        return chinesename;
    }

    public void setChinesename(String chinesename) {
        this.chinesename = chinesename;
    }

    public String getEnglishname() {
        return englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname;
    }

    public String getObtrailnum() {
        return obtrailnum;
    }

    public void setObtrailnum(String obtrailnum) {
        this.obtrailnum = obtrailnum;
    }

    public String getOboutput() {
        return oboutput;
    }

    public void setOboutput(String oboutput) {
        this.oboutput = oboutput;
    }

    public String getObtotletailnum() {
        return obtotletailnum;
    }

    public void setObtotletailnum(String obtotletailnum) {
        this.obtotletailnum = obtotletailnum;
    }

    public String getObtotleoutput() {
        return obtotleoutput;
    }

    public void setObtotleoutput(String obtotleoutput) {
        this.obtotleoutput = obtotleoutput;
    }

    public String getObfishcatchid() {
        return obfishcatchid;
    }

    public void setObfishcatchid(String obfishcatchid) {
        this.obfishcatchid = obfishcatchid;
    }


    @Override
    protected Serializable pkVal() {
        return this.fishtypeid;
    }

    @Override
    public String toString() {
        return "Fishtype{" +
                "fishtypeid=" + fishtypeid +
                ", fishsciencename=" + fishsciencename +
                ", fishcode=" + fishcode +
                ", chinesename=" + chinesename +
                ", englishname=" + englishname +
                ", obtrailnum=" + obtrailnum +
                ", oboutput=" + oboutput +
                ", obtotletailnum=" + obtotletailnum +
                ", obtotleoutput=" + obtotleoutput +
                ", obfishcatchid=" + obfishcatchid +
                "}";
    }
}
