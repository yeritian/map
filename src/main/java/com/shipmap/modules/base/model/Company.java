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
 * @since 2019-01-08
 */
@TableName("tb_company")
public class Company extends Model<Company> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    private String id;
    /*
     * 公司名称
     * */
    private String name;
    /**
     * 英文名
     */
    @TableField("name_en")
    private String nameEn;
    /*
     * 公司地址
     * */
    private String adress;
    /*
     * 公司电话
     * */
    private String telephone;
    /*
     * 公司负责人
     * */
    @TableField("company_lead")
    private String companyLead;
    /*
     * 公司技术负责人
     * */
    @TableField("tech_lead")
    private String techLead;
    /*
     * 公司负责人电话
     * */
    @TableField("company_lead_phone")
    private String companyLeadPhone;
    /*
     * 公司技术负责人电话
     * */
    @TableField("tech_lead_phone")
    private String techLeadPhone;
    /*
     * 公司邮箱
     * */
    private String email;
    /*
     * 省份
     * */
    private String province;
    /*
     * 备注
     * */
    private String remark;
    @TableField("update_time")
    private Date updateTime;
    private String updator;
    @TableField("create_time")
    private Date createTime;
    private String creator;
    @TableLogic
    private Integer del;

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCompanyLead() {
        return companyLead;
    }

    public void setCompanyLead(String companyLead) {
        this.companyLead = companyLead;
    }

    public String getTechLead() {
        return techLead;
    }

    public void setTechLead(String techLead) {
        this.techLead = techLead;
    }

    public String getCompanyLeadPhone() {
        return companyLeadPhone;
    }

    public void setCompanyLeadPhone(String companyLeadPhone) {
        this.companyLeadPhone = companyLeadPhone;
    }

    public String getTechLeadPhone() {
        return techLeadPhone;
    }

    public void setTechLeadPhone(String techLeadPhone) {
        this.techLeadPhone = techLeadPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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
        final StringBuilder sb = new StringBuilder("Company{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", nameEn='").append(nameEn).append('\'');
        sb.append(", adress='").append(adress).append('\'');
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", companyLead='").append(companyLead).append('\'');
        sb.append(", techLead='").append(techLead).append('\'');
        sb.append(", companyLeadPhone='").append(companyLeadPhone).append('\'');
        sb.append(", techLeadPhone='").append(techLeadPhone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", province='").append(province).append('\'');
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updator='").append(updator).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", creator='").append(creator).append('\'');
        sb.append(", del=").append(del);
        sb.append('}');
        return sb.toString();
    }
}
