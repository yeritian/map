package com.shipmap.modules.observer.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
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
@ExcelTarget("Fishtail")
@TableName("observer_fishtail")
public class Fishtail extends Model<Fishtail> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键-逐尾
     */
    @TableId(value = "fishtailid", type = IdType.UUID)
    private String fishtailid;
    /**
     * 外键--观察员
     */
    private String observerinfoid;
    /**
     * 作业钩次
     */
    @Excel(name = "作业钩次")
    private String ftworkhooktimes;
    /**
     * 下钩开始日期
     */
    @Excel(name = "下钩开始日期")
    private Date ftstatehookdatefu;

    private String ftstatehookdate;
    /**
     * 观察筐数
     */
    @Excel(name = "观察筐数")
    private Integer ftbasketnum;
    /**
     * 钓获钩位
     */
    @Excel(name = "钓获钩位")
    private String ftcatchhook;
    /**
     * 中文名
     */
    @Excel(name = "中文名")
    private String chinesename;
    /**
     * 英文名
     */
    @Excel(name = "英文名")
    private String englishname;
    /**
     * 科学名
     */
    @Excel(name = "科学名")
    private String scientificname;
    /**
     * 种代码
     */
    @Excel(name = "种代码")
    private String kindcode;
    /**
     * 捕获时状态
     */
    @Excel(name = "捕获时状态")
    private String capturetimestate;
    /**
     * 保留/丢弃
     */
    @Excel(name = "保留/丢弃")
    private String retaindiscard;
    /**
     * 全长TL(cm)
     */
    @Excel(name = "全长TL(cm)")
    private Double fulllength;
    /**
     * 上额叉长FL
     */
    @Excel(name = "上额叉长FL")
    private String foreheadforklg;
    /**
     * 下额叉长LJFL
     */
    @Excel(name = "下额叉长LJFL")
    private String lowerforklg;
    /**
     * 吻端-尾鳍凹洼长PCL
     */
    @Excel(name = "吻端-尾鳍凹洼长PCL")
    private String caudalfin;
    /**
     * 转换长度(背鳍间长LBD)
     */
    @Excel(name = "转换长度（背鳍间长LBD）")
    private String conversionlbd;
    /**
     * 全重WW(kg)
     */
    @Excel(name = "全重WW(kg)")
    private String ftfullweight;
    /**
     * 加工重量GT
     */
    @Excel(name = "加工重量GT")
    private String ftprocesswgt;
    /**
     * 加工重量GX
     */
    @Excel(name = "加工重量GX")
    private String ftprocesswgx;
    /**
     * 其它加工重量
     */
    @Excel(name = "其它加工重量")
    private String ftprocesseachw;
    /**
     * 摄食等级
     */
    @Excel(name = "摄食等级")
    private String ftfoodclass;
    /**
     * 性别
     */
    @Excel(name = "性别")
    private String ftsex;
    /**
     * 成熟期
     */
    @Excel(name = "成熟期")
    private String ftautumnperiod;
    /**
     * 卵/精巢重(g)
     */
    @Excel(name = "卵/精巢重(g)")
    private String eggtestisweight;
    /**
     * 肝重(一对)(kg)
     */
    @Excel(name = "肝重(kg)")
    private String liverweight;
    /**
     * 鳍脚内长(cm)
     */
    @Excel(name = "鳍脚内长(cm)")
    private String pinnipedia;
    /**
     * 卵壳腺宽(mm)
     */
    @Excel(name = "卵壳腺宽(mm)")
    private String eggshellglandwidth;
    /**
     * 卵巢卵径(mm)
     */
    @Excel(name = "卵巢卵径(mm)")
    private String ovarianeggsdiameter;
    /**
     * 子宫中胚胎个数
     */
    @Excel(name = "子宫中胚胎个数")
    private String embryoswombnum;
    /**
     * 子宫中幼鲨尾数
     */
    @Excel(name = "子宫中幼鲨尾数")
    private String sharkmantissa;
    /**
     * 幼鲨性别与长度(cm)
     */
    @Excel(name = "幼鲨性别与长度(cm)")
    private String sexlength;
    /**
     * 卵、胚胎、幼鲨描述
     */
    @Excel(name = "卵、胚胎、幼鲨描述")
    private String ftdescribe;
    /**
     * 椎骨编号(部位)
     */
    @Excel(name = "椎骨编号(部位)")
    private String vertebraeno;
    /**
     * 鳍棘编号(部位)
     */
    @Excel(name = "鳍棘编号(部位)")
    private String finspine;
    /**
     * 胃含物编号
     */
    @Excel(name = "胃含物编号")
    private String gastriccontents;
    /**
     * 胃含物图片编号
     */
    @Excel(name = "胃含物图片编号")
    private String gastriccontentsimg;
    /**
     * 胃含物明细(种类与个体数)
     */
    @Excel(name = "胃含物明细（种类及其个体数）")
    private String kindnum;
    /**
     * 性腺编号
     */
    @Excel(name = "性腺编号")
    private String gonadno;
    /**
     * 组织编号
     */
    @Excel(name = "组织编号")
    private String organizationno;
    /**
     * 船上是否记录
     */
    @Excel(name = "船上是否记录")
    private String ftrecord;
    /**
     * 观察到的其它现象
     */
    @Excel(name = "观察到的其它现象")
    private String ftotherrecord;
    /**
     * 注释
     */
    private String ftremark;
    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
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


    public String getFishtailid() {
        return fishtailid;
    }

    public void setFishtailid(String fishtailid) {
        this.fishtailid = fishtailid;
    }

    public String getObserverinfoid() {
        return observerinfoid;
    }

    public void setObserverinfoid(String observerinfoid) {
        this.observerinfoid = observerinfoid;
    }

    public String getFtworkhooktimes() {
        return ftworkhooktimes;
    }

    public void setFtworkhooktimes(String ftworkhooktimes) {
        this.ftworkhooktimes = ftworkhooktimes;
    }

    public String getFtstatehookdate() {
        return ftstatehookdate;
    }

    public void setFtstatehookdate(String ftstatehookdate) {
        this.ftstatehookdate = ftstatehookdate;
    }

    public Date getFtstatehookdatefu() {
        return ftstatehookdatefu;
    }

    public void setFtstatehookdatefu(Date ftstatehookdatefu) {
        this.ftstatehookdatefu = ftstatehookdatefu;
    }


    public Integer getFtbasketnum() {
        return ftbasketnum;
    }

    public void setFtbasketnum(Integer ftbasketnum) {
        this.ftbasketnum = ftbasketnum;
    }

    public String getFtcatchhook() {
        return ftcatchhook;
    }

    public void setFtcatchhook(String ftcatchhook) {
        this.ftcatchhook = ftcatchhook;
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

    public String getScientificname() {
        return scientificname;
    }

    public void setScientificname(String scientificname) {
        this.scientificname = scientificname;
    }

    public String getKindcode() {
        return kindcode;
    }

    public void setKindcode(String kindcode) {
        this.kindcode = kindcode;
    }

    public String getCapturetimestate() {
        return capturetimestate;
    }

    public void setCapturetimestate(String capturetimestate) {
        this.capturetimestate = capturetimestate;
    }

    public String getRetaindiscard() {
        return retaindiscard;
    }

    public void setRetaindiscard(String retaindiscard) {
        this.retaindiscard = retaindiscard;
    }

    public Double getFulllength() {
        return fulllength;
    }

    public void setFulllength(Double fulllength) {
        this.fulllength = fulllength;
    }

    public String getForeheadforklg() {
        return foreheadforklg;
    }

    public void setForeheadforklg(String foreheadforklg) {
        this.foreheadforklg = foreheadforklg;
    }

    public String getLowerforklg() {
        return lowerforklg;
    }

    public void setLowerforklg(String lowerforklg) {
        this.lowerforklg = lowerforklg;
    }

    public String getCaudalfin() {
        return caudalfin;
    }

    public void setCaudalfin(String caudalfin) {
        this.caudalfin = caudalfin;
    }

    public String getConversionlbd() {
        return conversionlbd;
    }

    public void setConversionlbd(String conversionlbd) {
        this.conversionlbd = conversionlbd;
    }

    public String getFtfullweight() {
        return ftfullweight;
    }

    public void setFtfullweight(String ftfullweight) {
        this.ftfullweight = ftfullweight;
    }

    public String getFtprocesswgt() {
        return ftprocesswgt;
    }

    public void setFtprocesswgt(String ftprocesswgt) {
        this.ftprocesswgt = ftprocesswgt;
    }

    public String getFtprocesswgx() {
        return ftprocesswgx;
    }

    public void setFtprocesswgx(String ftprocesswgx) {
        this.ftprocesswgx = ftprocesswgx;
    }

    public String getFtprocesseachw() {
        return ftprocesseachw;
    }

    public void setFtprocesseachw(String ftprocesseachw) {
        this.ftprocesseachw = ftprocesseachw;
    }

    public String getFtfoodclass() {
        return ftfoodclass;
    }

    public void setFtfoodclass(String ftfoodclass) {
        this.ftfoodclass = ftfoodclass;
    }

    public String getFtsex() {
        return ftsex;
    }

    public void setFtsex(String ftsex) {
        this.ftsex = ftsex;
    }

    public String getFtautumnperiod() {
        return ftautumnperiod;
    }

    public void setFtautumnperiod(String ftautumnperiod) {
        this.ftautumnperiod = ftautumnperiod;
    }

    public String getEggtestisweight() {
        return eggtestisweight;
    }

    public void setEggtestisweight(String eggtestisweight) {
        this.eggtestisweight = eggtestisweight;
    }

    public String getLiverweight() {
        return liverweight;
    }

    public void setLiverweight(String liverweight) {
        this.liverweight = liverweight;
    }

    public String getPinnipedia() {
        return pinnipedia;
    }

    public void setPinnipedia(String pinnipedia) {
        this.pinnipedia = pinnipedia;
    }

    public String getEggshellglandwidth() {
        return eggshellglandwidth;
    }

    public void setEggshellglandwidth(String eggshellglandwidth) {
        this.eggshellglandwidth = eggshellglandwidth;
    }

    public String getOvarianeggsdiameter() {
        return ovarianeggsdiameter;
    }

    public void setOvarianeggsdiameter(String ovarianeggsdiameter) {
        this.ovarianeggsdiameter = ovarianeggsdiameter;
    }

    public String getEmbryoswombnum() {
        return embryoswombnum;
    }

    public void setEmbryoswombnum(String embryoswombnum) {
        this.embryoswombnum = embryoswombnum;
    }

    public String getSharkmantissa() {
        return sharkmantissa;
    }

    public void setSharkmantissa(String sharkmantissa) {
        this.sharkmantissa = sharkmantissa;
    }

    public String getSexlength() {
        return sexlength;
    }

    public void setSexlength(String sexlength) {
        this.sexlength = sexlength;
    }

    public String getFtdescribe() {
        return ftdescribe;
    }

    public void setFtdescribe(String ftdescribe) {
        this.ftdescribe = ftdescribe;
    }

    public String getVertebraeno() {
        return vertebraeno;
    }

    public void setVertebraeno(String vertebraeno) {
        this.vertebraeno = vertebraeno;
    }

    public String getFinspine() {
        return finspine;
    }

    public void setFinspine(String finspine) {
        this.finspine = finspine;
    }

    public String getGastriccontents() {
        return gastriccontents;
    }

    public void setGastriccontents(String gastriccontents) {
        this.gastriccontents = gastriccontents;
    }

    public String getGastriccontentsimg() {
        return gastriccontentsimg;
    }

    public void setGastriccontentsimg(String gastriccontentsimg) {
        this.gastriccontentsimg = gastriccontentsimg;
    }

    public String getKindnum() {
        return kindnum;
    }

    public void setKindnum(String kindnum) {
        this.kindnum = kindnum;
    }

    public String getGonadno() {
        return gonadno;
    }

    public void setGonadno(String gonadno) {
        this.gonadno = gonadno;
    }

    public String getOrganizationno() {
        return organizationno;
    }

    public void setOrganizationno(String organizationno) {
        this.organizationno = organizationno;
    }

    public String getFtrecord() {
        return ftrecord;
    }

    public void setFtrecord(String ftrecord) {
        this.ftrecord = ftrecord;
    }

    public String getFtotherrecord() {
        return ftotherrecord;
    }

    public void setFtotherrecord(String ftotherrecord) {
        this.ftotherrecord = ftotherrecord;
    }

    public String getFtremark() {
        return ftremark;
    }

    public void setFtremark(String ftremark) {
        this.ftremark = ftremark;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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


    @Override
    protected Serializable pkVal() {
        return this.fishtailid;
    }

    @Override
    public String toString() {
        return "Fishtail{" +
                "fishtailid=" + fishtailid +
                ", observerinfoid=" + observerinfoid +
                ", ftworkhooktimes=" + ftworkhooktimes +
                ", ftstatehookdate=" + ftstatehookdate +
                ", ftbasketnum=" + ftbasketnum +
                ", ftcatchhook=" + ftcatchhook +
                ", chinesename=" + chinesename +
                ", englishname=" + englishname +
                ", scientificname=" + scientificname +
                ", kindcode=" + kindcode +
                ", capturetimestate=" + capturetimestate +
                ", retaindiscard=" + retaindiscard +
                ", fulllength=" + fulllength +
                ", foreheadforklg=" + foreheadforklg +
                ", lowerforklg=" + lowerforklg +
                ", caudalfin=" + caudalfin +
                ", conversionlbd=" + conversionlbd +
                ", ftfullweight=" + ftfullweight +
                ", ftprocesswgt=" + ftprocesswgt +
                ", ftprocesswgx=" + ftprocesswgx +
                ", ftprocesseachw=" + ftprocesseachw +
                ", ftfoodclass=" + ftfoodclass +
                ", ftsex=" + ftsex +
                ", ftautumnperiod=" + ftautumnperiod +
                ", eggtestisweight=" + eggtestisweight +
                ", liverweight=" + liverweight +
                ", pinnipedia=" + pinnipedia +
                ", eggshellglandwidth=" + eggshellglandwidth +
                ", ovarianeggsdiameter=" + ovarianeggsdiameter +
                ", embryoswombnum=" + embryoswombnum +
                ", sharkmantissa=" + sharkmantissa +
                ", sexlength=" + sexlength +
                ", ftdescribe=" + ftdescribe +
                ", vertebraeno=" + vertebraeno +
                ", finspine=" + finspine +
                ", gastriccontents=" + gastriccontents +
                ", gastriccontentsimg=" + gastriccontentsimg +
                ", kindnum=" + kindnum +
                ", gonadno=" + gonadno +
                ", organizationno=" + organizationno +
                ", ftrecord=" + ftrecord +
                ", ftotherrecord=" + ftotherrecord +
                ", ftremark=" + ftremark +
                ", updateTime=" + updateTime +
                "}";
    }
}
