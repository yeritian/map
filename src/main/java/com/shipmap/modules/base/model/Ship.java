package com.shipmap.modules.base.model;

import com.alibaba.fastjson.annotation.JSONField;
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
 * @since 2018-12-12
 */
@TableName("tb_ship")
public class Ship extends Model<Ship> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    private String id;
    /**
     * 船名
     */
    private String name;
    /**
     * 英文名
     */
    @TableField("name_en")
    private String nameEn;
    /**
     * mmsi
     */
    private Integer mmsi;
    /**
     * 船舶类型
     */
    @TableField("ship_type")
    private String shipType;
    /**
     * 作业类型
     */
    @TableField("fishing_type")
    private String fishingType;
    /**
     * 公司
     */
    @TableField("company_id")
    private String companyId;
    @TableField(exist = false)
    private String company;
    /**
     * 出厂日期
     */
    @JSONField(format = "yyyy-MM-dd")
    @TableField("made_date")
    private Date madeDate;
    /**
     * 国籍
     */
    private String nationality;
    /**
     * IMO number
     */
    private String imo;
    /**
     * 呼号
     */
    private String callsign;
    /**
     * 船舶注册号
     */
    @TableField("reg_no")
    private String regNo;
    /**
     * 国籍证书编号
     */
    @TableField("nation_cer_no")
    private String nationCerNo;
    /**
     * 渔船编号
     */
    @TableField("fishery_no")
    private String fisheryNo;
    /**
     * 总吨
     */
    @TableField("total_ton")
    private Double totalTon;
    /**
     * FFA VID
     */
    @TableField("ffa_vid")
    private String ffaVid;
    /**
     * WCPFC认证号
     */
    @TableField("wcpfc_cer_no")
    private String wcpfcCerNo;
    /**
     * 船长
     */
    private String captain;
    /**
     * 入渔许可证编号
     */
    @TableField("fishing_permit_no")
    private String fishingPermitNo;
    /**
     * 船东
     */
    private String shipowner;
    /**
     * 航行宝编号
     */
    private String sailbox;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     * 修改者
     */
    private String updator;

    @TableLogic
    private Integer del;

    @TableField("setting_ver")
    private String settingVer;

    public String getSettingVer() {
        return settingVer;
    }

    public void setSettingVer(String settingVer) {
        this.settingVer = settingVer;
    }

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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Integer getMmsi() {
        return mmsi;
    }

    public void setMmsi(Integer mmsi) {
        this.mmsi = mmsi;
    }

    public String getShipType() {
        return shipType;
    }

    public void setShipType(String shipType) {
        this.shipType = shipType;
    }

    public String getFishingType() {
        return fishingType;
    }

    public void setFishingType(String fishingType) {
        this.fishingType = fishingType;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Date getMadeDate() {
        return madeDate;
    }

    public void setMadeDate(Date madeDate) {
        this.madeDate = madeDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getImo() {
        return imo;
    }

    public void setImo(String imo) {
        this.imo = imo;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getNationCerNo() {
        return nationCerNo;
    }

    public void setNationCerNo(String nationCerNo) {
        this.nationCerNo = nationCerNo;
    }

    public String getFisheryNo() {
        return fisheryNo;
    }

    public void setFisheryNo(String fisheryNo) {
        this.fisheryNo = fisheryNo;
    }

    public Double getTotalTon() {
        return totalTon;
    }

    public void setTotalTon(Double totalTon) {
        this.totalTon = totalTon;
    }

    public String getFfaVid() {
        return ffaVid;
    }

    public void setFfaVid(String ffaVid) {
        this.ffaVid = ffaVid;
    }

    public String getWcpfcCerNo() {
        return wcpfcCerNo;
    }

    public void setWcpfcCerNo(String wcpfcCerNo) {
        this.wcpfcCerNo = wcpfcCerNo;
    }

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }

    public String getFishingPermitNo() {
        return fishingPermitNo;
    }

    public void setFishingPermitNo(String fishingPermitNo) {
        this.fishingPermitNo = fishingPermitNo;
    }

    public String getShipowner() {
        return shipowner;
    }

    public void setShipowner(String shipowner) {
        this.shipowner = shipowner;
    }

    public String getSailbox() {
        return sailbox;
    }

    public void setSailbox(String sailbox) {
        this.sailbox = sailbox;
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
        return this.id;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", mmsi=" + mmsi +
                ", shipType='" + shipType + '\'' +
                ", fishingType='" + fishingType + '\'' +
                ", companyId='" + companyId + '\'' +
                ", company='" + company + '\'' +
                ", madeDate=" + madeDate +
                ", nationality='" + nationality + '\'' +
                ", imo='" + imo + '\'' +
                ", callsign='" + callsign + '\'' +
                ", regNo='" + regNo + '\'' +
                ", nationCerNo='" + nationCerNo + '\'' +
                ", fisheryNo='" + fisheryNo + '\'' +
                ", totalTon=" + totalTon +
                ", ffaVid='" + ffaVid + '\'' +
                ", wcpfcCerNo='" + wcpfcCerNo + '\'' +
                ", captain='" + captain + '\'' +
                ", fishingPermitNo='" + fishingPermitNo + '\'' +
                ", shipowner='" + shipowner + '\'' +
                ", sailbox='" + sailbox + '\'' +
                ", createTime=" + createTime +
                ", creator='" + creator + '\'' +
                ", updateTime=" + updateTime +
                ", updator='" + updator + '\'' +
                ", del=" + del +
                ", settingVer='" + settingVer + '\'' +
                '}';
    }
}
