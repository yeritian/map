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
@ExcelTarget("Observerhooktime")
@TableName("observer_observerhooktime")
public class Observerhooktime extends Model<Observerhooktime> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键-作业钩次表
     */
    @TableId(value = "observerhookid", type = IdType.UUID)
    private String observerhookid;
    /**
     * 外键-观察员信息
     */
    private String observerinfoid;
    /**
     * 作业钩次
     */
    @Excel(name = "作业钩次Set No.")
    private String hooktimes;
    /**
     * 下钩日期
     */
    @Excel(name = "下钩开始日期")//,format="yyyy-MM-dd HH:mm:ss"
    private Date obstartdatehookfu;

    private String obstartdatehook;
    /**
     * 两浮子间主绳长
     */

    @Excel(name = "两浮子间主绳长 m")
    private String buoybetween;
    /**
     * 浮绳长
     */
    @Excel(name = "浮绳长m")
    private String floatlinelength;
    /**
     * 支绳长
     */
    @Excel(name = "支绳长m")
    private String ropelength;
    /**
     * 支绳间距
     */
    @Excel(name = "支绳间距 m")
    private String ropebetween;
    /**
     * 投绳速度
     */
    @Excel(name = "投绳速度(m/s)")
    private String throwropespeed;
    /**
     * 投绳时船速
     */
    @Excel(name = "投绳时船速(kt)")
    private String throwropeshipspeed;
    /**
     * 单筐钩数(金枪鱼钩)
     */
    @Excel(name = "单筐钩数(金枪鱼钩)")
    private Integer singlebaskethook;
    /**
     * 实际总筐数
     */
    @Excel(name = "实际总筐数")
    private Integer realitytotalbaskets;
    /**
     * 实际总下钩数(金枪鱼钩)
     */
    @Excel(name = "实际总下钩数(金枪鱼钩)")
    private Integer realitytotalhook;
    /**
     * 是否采用了鲨鱼钩
     */
    @Excel(name = "是否采用了鲨鱼钩")
    private String sharkhook;
    /**
     * 实际总下鲨鱼钩数
     */
    @Excel(name = "实际总下鲨鱼钩数")
    private Integer realitysharkhook;
    /**
     * 观察筐数
     */
    @Excel(name = "观察筐数")
    private Integer obbaskets;
    /**
     * 观察筐数比率(随机,0~1)
     */
    @Excel(name = "观察筐数比率(随机,0~1)")
    private String observebasketsrate;
    /**
     * 目标鱼种
     */
    @Excel(name = "目标鱼种")
    private String targetspecies;
    /**
     * 下钩开始时间（当地）
     */
    @Excel(name = "下钩开始时间（当地）")
    private Date obstatedatehookfu;

    private String obstatedatehook;
    /**
     * 下钩结束时间
     */
    @Excel(name = "下钩结束时间")
    private Date obenddatehookfu;

    private String obenddatehook;
    /**
     * 起钩开始时间
     */
    @Excel(name = "起钩开始时间")
    private Date obupstatedatefu;

    private String obupstatedate;
    /**
     * 起钩结束时间
     */
    @Excel(name = "起钩结束时间")
    private Date obupenddatefu;

    private String obupenddate;
    /**
     * 起钩开始SST(海水温度表面)
     */
    @Excel(name = "起钩开始SST")
    private String uphooksst;
    /**
     * 起钩开始气压
     */
    @Excel(name = "起钩开始气压")
    private String uphookpa;
    /**
     * 起钩开始天气
     */
    @Excel(name = "起钩开始天气")
    private String uphookweather;
    /**
     * 起钩开始海况BF
     */
    @Excel(name = "起钩开始海况BF")
    private String uphookbf;
    /**
     * 农历日
     */
    @Excel(name = "农历日")
    private String lday;
    /**
     * 纬度 度 (北纬+南纬-)
     */
    @Excel(name = "纬度 度  (北纬+南纬-)")
    private String latitudedegrees;
    /**
     * 纬度 分 (北纬+南纬-)
     */
    @Excel(name = "纬度 分  (北纬+南纬-)")
    private String latitudepoints;
    /**
     * 经度 度 (东经+西经-)
     */
    @Excel(name = "经度 度  (东经+西经-)")
    private String longitudesdegrees;
    /**
     * 经度 分 (东经+西经-)
     */
    @Excel(name = "经度 分  (东经+西经-)")
    private String longitudespoints;
    /**
     * 金枪鱼钓钩类型
     */
    @Excel(name = "金枪鱼钓钩类型")
    private String tunahooktype;
    /**
     * 金枪鱼钓钩尺寸
     */
    @Excel(name = "金枪鱼钓钩尺寸")
    private String tunahooksize;
    /**
     * 鲨鱼钓钩类型
     */
    @Excel(name = "鲨鱼钓钩类型")
    private String sharkhooktype;
    /**
     * 鲨鱼钓钩尺寸
     */
    @Excel(name = "鲨鱼钓钩尺寸")
    private String sharkhooksize;
    /**
     * 是否采用荧光棒
     */
    @Excel(name = "是否采用荧光棒")
    private String glowsticks;
    /**
     * 是否配备海龟脱钩器
     */
    @Excel(name = "是否配备海龟脱钩器")
    private String turtlehook;
    /**
     * 海龟脱钩器类型
     */
    @Excel(name = "海龟脱钩器类型")
    private String turtlehooktype;
    /**
     * 是否有海鸟钓获
     */
    @Excel(name = "是否有海鸟钓获")
    private String catchbirds;
    /**
     * 饵料鱼1
     */
    @Excel(name = "饵料鱼1")
    private String baitfish1;
    /**
     * 饵料鱼1平均长度(cm)
     */
    @Excel(name = "平均长度(cm)")
    private String baitfishavglength1;
    /**
     * 饵料鱼2
     */
    @Excel(name = "饵料鱼2")
    private String baitfish2;
    /**
     * 饵料鱼2平均长度(cm)
     */
    @Excel(name = "平均长度(cm)")
    private String baitfishavglength2;
    /**
     * 注释
     */
    @Excel(name = "注释")
    private String observerremark;
    /**
     * 更新时间-
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


    public String getObserverhookid() {
        return observerhookid;
    }

    public void setObserverhookid(String observerhookid) {
        this.observerhookid = observerhookid;
    }

    public String getObserverinfoid() {
        return observerinfoid;
    }

    public void setObserverinfoid(String observerinfoid) {
        this.observerinfoid = observerinfoid;
    }

    public String getHooktimes() {
        return hooktimes;
    }

    public void setHooktimes(String hooktimes) {
        this.hooktimes = hooktimes;
    }

    public String getObstartdatehook() {
        return obstartdatehook;
    }

    public void setObstartdatehook(String obstartdatehook) {
        this.obstartdatehook = obstartdatehook;
    }

    public Date getObstartdatehookfu() {
        return obstartdatehookfu;
    }

    public void setObstartdatehookfu(Date obstartdatehookfu) {
        this.obstartdatehookfu = obstartdatehookfu;
    }


    public String getBuoybetween() {
        return buoybetween;
    }

    public void setBuoybetween(String buoybetween) {
        this.buoybetween = buoybetween;
    }

    public String getFloatlinelength() {
        return floatlinelength;
    }

    public void setFloatlinelength(String floatlinelength) {
        this.floatlinelength = floatlinelength;
    }

    public String getRopelength() {
        return ropelength;
    }

    public void setRopelength(String ropelength) {
        this.ropelength = ropelength;
    }

    public String getRopebetween() {
        return ropebetween;
    }

    public void setRopebetween(String ropebetween) {
        this.ropebetween = ropebetween;
    }

    public String getThrowropespeed() {
        return throwropespeed;
    }

    public void setThrowropespeed(String throwropespeed) {
        this.throwropespeed = throwropespeed;
    }

    public String getThrowropeshipspeed() {
        return throwropeshipspeed;
    }

    public void setThrowropeshipspeed(String throwropeshipspeed) {
        this.throwropeshipspeed = throwropeshipspeed;
    }

    public Integer getSinglebaskethook() {
        return singlebaskethook;
    }

    public void setSinglebaskethook(Integer singlebaskethook) {
        this.singlebaskethook = singlebaskethook;
    }

    public Integer getRealitytotalbaskets() {
        return realitytotalbaskets;
    }

    public void setRealitytotalbaskets(Integer realitytotalbaskets) {
        this.realitytotalbaskets = realitytotalbaskets;
    }

    public Integer getRealitytotalhook() {
        return realitytotalhook;
    }

    public void setRealitytotalhook(Integer realitytotalhook) {
        this.realitytotalhook = realitytotalhook;
    }

    public String getSharkhook() {
        return sharkhook;
    }

    public void setSharkhook(String sharkhook) {
        this.sharkhook = sharkhook;
    }

    public Integer getRealitysharkhook() {
        return realitysharkhook;
    }

    public void setRealitysharkhook(Integer realitysharkhook) {
        this.realitysharkhook = realitysharkhook;
    }

    public Integer getObbaskets() {
        return obbaskets;
    }

    public void setObbaskets(Integer obbaskets) {
        this.obbaskets = obbaskets;
    }

    public String getObservebasketsrate() {
        return observebasketsrate;
    }

    public void setObservebasketsrate(String observebasketsrate) {
        this.observebasketsrate = observebasketsrate;
    }

    public String getTargetspecies() {
        return targetspecies;
    }

    public void setTargetspecies(String targetspecies) {
        this.targetspecies = targetspecies;
    }

    public String getObstatedatehook() {
        return obstatedatehook;
    }

    public void setObstatedatehook(String obstatedatehook) {
        this.obstatedatehook = obstatedatehook;
    }

    public Date getObstatedatehookfu() {
        return obstatedatehookfu;
    }

    public void setObstatedatehookfu(Date obstatedatehookfu) {
        this.obstatedatehookfu = obstatedatehookfu;
    }


    public String getObenddatehook() {
        return obenddatehook;
    }

    public void setObenddatehook(String obenddatehook) {
        this.obenddatehook = obenddatehook;
    }

    public Date getObenddatehookfu() {
        return obenddatehookfu;
    }

    public void setObenddatehookfu(Date obenddatehookfu) {
        this.obenddatehookfu = obenddatehookfu;
    }

    public String getObupstatedate() {
        return obupstatedate;
    }

    public void setObupstatedate(String obupstatedate) {
        this.obupstatedate = obupstatedate;
    }

    public Date getObupstatedatefu() {
        return obupstatedatefu;
    }

    public void setObupstatedatefu(Date obupstatedatefu) {
        this.obupstatedatefu = obupstatedatefu;
    }

    public String getObupenddate() {
        return obupenddate;
    }

    public void setObupenddate(String obupenddate) {
        this.obupenddate = obupenddate;
    }

    public Date getObupenddatefu() {
        return obupenddatefu;
    }

    public void setObupenddatefu(Date obupenddatefu) {
        this.obupenddatefu = obupenddatefu;
    }

    public String getUphooksst() {
        return uphooksst;
    }

    public void setUphooksst(String uphooksst) {
        this.uphooksst = uphooksst;
    }

    public String getUphookpa() {
        return uphookpa;
    }

    public void setUphookpa(String uphookpa) {
        this.uphookpa = uphookpa;
    }

    public String getUphookweather() {
        return uphookweather;
    }

    public void setUphookweather(String uphookweather) {
        this.uphookweather = uphookweather;
    }

    public String getUphookbf() {
        return uphookbf;
    }

    public void setUphookbf(String uphookbf) {
        this.uphookbf = uphookbf;
    }

    public String getLday() {
        return lday;
    }

    public void setLday(String lday) {
        this.lday = lday;
    }

    public String getLatitudedegrees() {
        return latitudedegrees;
    }

    public void setLatitudedegrees(String latitudedegrees) {
        this.latitudedegrees = latitudedegrees;
    }

    public String getLatitudepoints() {
        return latitudepoints;
    }

    public void setLatitudepoints(String latitudepoints) {
        this.latitudepoints = latitudepoints;
    }

    public String getLongitudesdegrees() {
        return longitudesdegrees;
    }

    public void setLongitudesdegrees(String longitudesdegrees) {
        this.longitudesdegrees = longitudesdegrees;
    }

    public String getLongitudespoints() {
        return longitudespoints;
    }

    public void setLongitudespoints(String longitudespoints) {
        this.longitudespoints = longitudespoints;
    }

    public String getTunahooktype() {
        return tunahooktype;
    }

    public void setTunahooktype(String tunahooktype) {
        this.tunahooktype = tunahooktype;
    }

    public String getTunahooksize() {
        return tunahooksize;
    }

    public void setTunahooksize(String tunahooksize) {
        this.tunahooksize = tunahooksize;
    }

    public String getSharkhooktype() {
        return sharkhooktype;
    }

    public void setSharkhooktype(String sharkhooktype) {
        this.sharkhooktype = sharkhooktype;
    }

    public String getSharkhooksize() {
        return sharkhooksize;
    }

    public void setSharkhooksize(String sharkhooksize) {
        this.sharkhooksize = sharkhooksize;
    }

    public String getGlowsticks() {
        return glowsticks;
    }

    public void setGlowsticks(String glowsticks) {
        this.glowsticks = glowsticks;
    }

    public String getTurtlehook() {
        return turtlehook;
    }

    public void setTurtlehook(String turtlehook) {
        this.turtlehook = turtlehook;
    }

    public String getTurtlehooktype() {
        return turtlehooktype;
    }

    public void setTurtlehooktype(String turtlehooktype) {
        this.turtlehooktype = turtlehooktype;
    }

    public String getCatchbirds() {
        return catchbirds;
    }

    public void setCatchbirds(String catchbirds) {
        this.catchbirds = catchbirds;
    }

    public String getBaitfish1() {
        return baitfish1;
    }

    public void setBaitfish1(String baitfish1) {
        this.baitfish1 = baitfish1;
    }

    public String getBaitfishavglength1() {
        return baitfishavglength1;
    }

    public void setBaitfishavglength1(String baitfishavglength1) {
        this.baitfishavglength1 = baitfishavglength1;
    }

    public String getBaitfish2() {
        return baitfish2;
    }

    public void setBaitfish2(String baitfish2) {
        this.baitfish2 = baitfish2;
    }

    public String getBaitfishavglength2() {
        return baitfishavglength2;
    }

    public void setBaitfishavglength2(String baitfishavglength2) {
        this.baitfishavglength2 = baitfishavglength2;
    }

    public String getObserverremark() {
        return observerremark;
    }

    public void setObserverremark(String observerremark) {
        this.observerremark = observerremark;
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
        return this.observerhookid;
    }

    @Override
    public String toString() {
        return "Observerhooktime{" +
                "observerhookid=" + observerhookid +
                ", observerinfoid=" + observerinfoid +
                ", hooktimes=" + hooktimes +
                ", obstartdatehook=" + obstartdatehook +
                ", buoybetween=" + buoybetween +
                ", floatlinelength=" + floatlinelength +
                ", ropelength=" + ropelength +
                ", ropebetween=" + ropebetween +
                ", throwropespeed=" + throwropespeed +
                ", throwropeshipspeed=" + throwropeshipspeed +
                ", singlebaskethook=" + singlebaskethook +
                ", realitytotalbaskets=" + realitytotalbaskets +
                ", realitytotalhook=" + realitytotalhook +
                ", sharkhook=" + sharkhook +
                ", realitysharkhook=" + realitysharkhook +
                ", obbaskets=" + obbaskets +
                ", observebasketsrate=" + observebasketsrate +
                ", targetspecies=" + targetspecies +
                ", obstatedatehook=" + obstatedatehook +
                ", obenddatehook=" + obenddatehook +
                ", obupstatedate=" + obupstatedate +
                ", obupenddate=" + obupenddate +
                ", uphooksst=" + uphooksst +
                ", uphookpa=" + uphookpa +
                ", uphookweather=" + uphookweather +
                ", uphookbf=" + uphookbf +
                ", lday=" + lday +
                ", latitudedegrees=" + latitudedegrees +
                ", latitudepoints=" + latitudepoints +
                ", longitudesdegrees=" + longitudesdegrees +
                ", longitudespoints=" + longitudespoints +
                ", tunahooktype=" + tunahooktype +
                ", tunahooksize=" + tunahooksize +
                ", sharkhooktype=" + sharkhooktype +
                ", sharkhooksize=" + sharkhooksize +
                ", glowsticks=" + glowsticks +
                ", turtlehook=" + turtlehook +
                ", turtlehooktype=" + turtlehooktype +
                ", catchbirds=" + catchbirds +
                ", baitfish1=" + baitfish1 +
                ", baitfishavglength1=" + baitfishavglength1 +
                ", baitfish2=" + baitfish2 +
                ", baitfishavglength2=" + baitfishavglength2 +
                ", observerremark=" + observerremark +
                ", updateTime=" + updateTime +
                "}";
    }
}
