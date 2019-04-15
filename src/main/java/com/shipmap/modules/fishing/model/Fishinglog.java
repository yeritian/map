package com.shipmap.modules.fishing.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.shipmap.framework.utils.DateUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.framework.utils.TypeUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ${author}
 * @since 2018-12-25
 */

@TableName("tb_fishinglog")
public class Fishinglog extends Model<Fishinglog> {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 船舶id
     */
    @TableField("ship_id")
    private String shipId;
    /**
     * 渔捞日志类型(0电子渔捞日志1纸质渔捞日志)
     */
    private Integer type;
    /**
     * 渔捞日志类型(1"公海围网",2"金枪鱼延绳钓/超低温",3"鱿钓",4"远洋拖网",5"金枪鱼围网",6"竹筴鱼/鲐鱼")
     */
    @TableField("log_type")
    private String logType;

    @TableField(exist = false)
    private String logTypeStr;
    /**
     * 活动类型("0作业","1航行","2漂流","3在港","4转载","5故障","6未作业-天气原因","7寻找鱼群","8清洁网具","9放置或回收")
     */
    @TableField("action_type")
    private String actionType;

    @TableField(exist = false)
    private String actionTypeStr;
    /**
     * 航次id
     */
    @TableField("trip_id")
    private String tripId;
    /**
     * 网次
     */
    @TableField("net_num")
    private String netNum;
    /**
     * 日志时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @TableField("log_date")
    private Date logDate;

    @TableField(exist = false)
    private String logDateStr;
    /**
     * 经度
     */
    private Integer lon;
    @TableField(exist = false)
    private String lonStr;
    /**
     * 纬度
     */
    private Integer lat;
    @TableField(exist = false)
    private String latStr;
    /**
     * 作业开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @TableField("start_time")
    private Date startTime;

    @TableField(exist = false)
    private String startTimeStr;
    /**
     * 开始经度
     */
    @TableField("start_lon")
    private Integer startLon;
    @TableField(exist = false)
    private String startLonStr;
    /**
     * 开始纬度
     */
    @TableField("start_lat")
    private Integer startLat;
    @TableField(exist = false)
    private String startLatStr;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @TableField("end_time")
    private Date endTime;

    @TableField(exist = false)
    private String endTimeStr;
    /**
     * 结束经度
     */
    @TableField("end_lon")
    private Integer endLon;
    @TableField(exist = false)
    private String endLonStr;
    /**
     * 结束纬度
     */
    @TableField("end_lat")
    private Integer endLat;
    @TableField(exist = false)
    private String endLatStr;
    /**
     * 下钩数
     */
    @TableField("hook_sum")
    private Integer hookSum;
    /**
     * 两浮球间钩数
     */
    @TableField("float_hook_sum")
    private Integer floatHookSum;
    /**
     * 水深（厘米）
     */
    @TableField("water_deep")
    private Integer waterDeep;
    /**
     * 水温（0.1度）
     */
    @TableField("water_temp")
    private Integer waterTemp;
    /**
     * 开机数量
     */
    @TableField("machine_sum")
    private Integer machineSum;
    /**
     * 单钓钩数
     */
    @TableField("machine_hook_sum")
    private Integer machineHookSum;
    /**
     * 手钓人数
     */
    @TableField("manual_sum")
    private Integer manualSum;
    /**
     * 手钓闲钩数
     */
    @TableField("manual_idle_hook_sum")
    private Integer manualIdleHookSum;
    /**
     * 投钩水深
     */
    @TableField("hook_deep")
    private Integer hookDeep;
    /**
     * 水表温度（0.1度）
     */
    @TableField("surface_temp")
    private Integer surfaceTemp;
    /**
     * 功率（瓦）
     */
    @TableField("light_power")
    private Integer lightPower;
    /**
     * 盘重
     */
    @TableField("plate_capacity")
    private Integer plateCapacity;
    /**
     * 曳纲长度(厘米)
     */
    @TableField("rope_length")
    private Integer ropeLength;
    /**
     * 拖速(0.1节)
     */
    @TableField("drag_speed")
    private Integer dragSpeed;
    /**
     * 开灯数
     */
    @TableField("light_sum")
    private Integer lightSum;
    /**
     * 风速(0.1m/s)
     */
    @TableField("wind_velocity")
    private Integer windVelocity;
    /**
     * 流速（0.1m/s）
     */
    @TableField("current_velocity")
    private Integer currentVelocity;
    /**
     * 流向（0.1度）
     */
    @TableField("current_direction")
    private Integer currentDirection;
    /**
     * 风向（0.1度）
     */
    @TableField("wind_direction")
    private Integer windDirection;
    /**
     * 围捕类型
     */
    @TableField("school_code")
    private String schoolCode;

    @TableField(exist = false)
    private String schoolCodeStr;

    /**
     * 鱼舱号
     */
    @TableField("well_num")
    private String wellNum;
    /**
     * 天气
     */
    private String weather;

    public String getWeatherStr() {
        return TypeUtil.getWeatherType(weather);
    }

    /**
     * 捕捞网位
     */
    @TableField("net_loc")
    private String netLoc;
    /**
     * 海况
     */
    @TableField("sea_condition")
    private String seaCondition;

    public String getSeaConditionStr() {
        return TypeUtil.getSeaConditionType(seaCondition);
    }

    /**
     * 网口高度(厘米)
     */
    @TableField("net_height")
    private Integer netHeight;
    /**
     * 网口扩张
     */
    @TableField("net_expand")
    private String netExpand;
    /**
     * 渔具规格
     */
    @TableField("gear_standard")
    private String gearStandard;
    /**
     * 能见度
     */
    private String visibility;
    /**
     * 气温(0.1度)
     */
    @TableField("air_temp")
    private Integer airTemp;
    /**
     * 浪向
     */
    @TableField("wave_dir")
    private String waveDir;
    /**
     * 浪高（cm）
     */
    @TableField("wave_height")
    private Integer waveHeight;
    /**
     * 提交人
     */
    private String submitter;
    /**
     * 罐头加工厂或船舶和目的地
     */
    @TableField("unloading_dest")
    private String unloadingDest;
    /**
     * 转载船舶呼号
     */
    @TableField("unloading_callsign")
    private String unloadingCallsign;
    /**
     * 转载鲣鱼
     */
    @TableField("unloading_skipjack")
    private Integer unloadingSkipjack;
    /**
     * 转载黄鳍
     */
    @TableField("unloading_yellowfin")
    private Integer unloadingYellowfin;
    /**
     * 转载大目
     */
    @TableField("unloading_bigeye")
    private Integer unloadingBigeye;
    /**
     * 转载混合
     */
    @TableField("unloading_mix")
    private Integer unloadingMix;
    /**
     * 转载其他
     */
    @TableField("unloading_other")
    private Integer unloadingOther;
    /**
     * 转载不合格
     */
    @TableField("unloading_reject")
    private Integer unloadingReject;
    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @TableField("create_time")
    private Date createTime;
    @TableField(exist = false)
    private String createTimeStr;

    private String creator;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @TableField(value = "update_time")
    private Date updateTime;
    private String updator;

    @TableField(exist = false)
    private List<Catch> catches = new ArrayList<>();
    @TableField(exist = false)
    private List<Discard> discards = new ArrayList<>();

    @TableLogic
    private Integer del;

    @TableField(exist = false)
    private String shipName;
    /**
     * 鲣鱼渔获量
     */
    private Integer skipjack;

    @TableField("yellowfin_big")
    private Integer yellowFinBig;

    @TableField("yellowfin_small")
    private Integer yellowFinSmall;

    @TableField("bigeye_big")
    private Integer bigEyeBig;

    @TableField("bigeye_small")
    private Integer bigEyeSmall;

    @TableField("modify_log_id")
    private String modifyLogId;

    @TableField("submit_ver")
    private Integer submitVer;

    /**
     * 记录类型，0为原始记录，1为修改记录
     */
    @TableField("log_record_type")
    private Integer logRecordType;
    /**
     * 是否查询显示标识（0为查询，1为不查询）
     */
    @TableField("is_select")
    private Integer isSelect;
    /**
     * 是否漏网（0漏网，1不漏网）
     */
    @TableField("is_slip")
    private Integer isSlip;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 上报的国家
     */
    @TableField("report_country")
    private String reportCountry;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getReportCountry() {
        return reportCountry;
    }

    public void setReportCountry(String reportCountry) {
        this.reportCountry = reportCountry;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTime = DateUtil.parseDate(createTimeStr, "yyyy-MM-dd HH:mm");
        this.createTimeStr = createTimeStr;
    }

    public Integer getIsSlip() {
        return isSlip;
    }

    public void setIsSlip(Integer isSlip) {
        this.isSlip = isSlip;
    }

    public Integer getManualIdleHookSum() {
        return manualIdleHookSum;
    }

    public void setManualIdleHookSum(Integer manualIdleHookSum) {
        this.manualIdleHookSum = manualIdleHookSum;
    }

    public Integer getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Integer isSelect) {
        this.isSelect = isSelect;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLogDateStr() {
        return logDateStr;
    }

    public void setLogDateStr(String logDateStr) {
        this.logDate = DateUtil.parseDate(logDateStr, "yyyy-MM-dd HH:mm");
        this.logDateStr = logDateStr;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTime = DateUtil.parseDate(startTimeStr, "yyyy-MM-dd HH:mm");
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTime = DateUtil.parseDate(endTimeStr, "yyyy-MM-dd HH:mm");
        this.endTimeStr = endTimeStr;
    }

    public String getModifyLogId() {
        return modifyLogId;
    }

    public void setModifyLogId(String modifyLogId) {
        this.modifyLogId = modifyLogId;
    }

    public Integer getSubmitVer() {
        return submitVer;
    }

    public void setSubmitVer(Integer submitVer) {
        this.submitVer = submitVer;
    }

    public Integer getLogRecordType() {
        return logRecordType;
    }

    public void setLogRecordType(Integer logRecordType) {
        this.logRecordType = logRecordType;
    }

    public String getEndLonStr() {
        return TypeUtil.getLonStr(endLon);
    }

    public void setEndLonStr(String endLonStr) {
        this.endLon = TypeUtil.convertToMinute(endLonStr);
        this.endLonStr = endLonStr;
    }

    public String getEndLatStr() {
        return TypeUtil.getLatStr(endLat);
    }

    public void setEndLatStr(String endLatStr) {
        this.endLat = TypeUtil.convertToMinute(endLatStr);
        this.endLatStr = endLatStr;
    }

    public Integer getSkipjack() {
        return skipjack;
    }

    public void setSkipjack(Integer skipjack) {
        this.skipjack = skipjack;
    }

    public Integer getYellowFinBig() {
        return yellowFinBig;
    }

    public void setYellowFinBig(Integer yellowFinBig) {
        this.yellowFinBig = yellowFinBig;
    }

    public Integer getYellowFinSmall() {
        return yellowFinSmall;
    }

    public void setYellowFinSmall(Integer yellowFinSmall) {
        this.yellowFinSmall = yellowFinSmall;
    }

    public Integer getBigEyeBig() {
        return bigEyeBig;
    }

    public void setBigEyeBig(Integer bigEyeBig) {
        this.bigEyeBig = bigEyeBig;
    }

    public Integer getBigEyeSmall() {
        return bigEyeSmall;
    }

    public void setBigEyeSmall(Integer bigEyeSmall) {
        this.bigEyeSmall = bigEyeSmall;
    }

    public String getSchoolCodeStr() {
        return TypeUtil.getSchoolType(schoolCode);
    }

    public void setSchoolCodeStr(String schoolCodeStr) {
        this.schoolCodeStr = schoolCodeStr;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getLogTypeStr() {
        return TypeUtil.getLogType(logType);
    }

    public void setLogTypeStr(String logTypeStr) {
        this.logTypeStr = logTypeStr;
    }

    public String getStartLonStr() {
        return TypeUtil.getLonStr(startLon);
    }

    public void setStartLonStr(String startLonStr) {
        this.startLon = TypeUtil.convertToMinute(startLonStr);
        this.startLonStr = startLonStr;
    }

    public String getStartLatStr() {
        return TypeUtil.getLatStr(startLat);
    }

    public void setStartLatStr(String startLatStr) {
        this.startLat = TypeUtil.convertToMinute(startLatStr);
        this.startLatStr = startLatStr;
    }

    public String getLonStr() {
        return TypeUtil.getLonStr(lon);
    }

    public void setLonStr(String lonStr) {
        this.lon = TypeUtil.convertToMinute(lonStr);
        this.lonStr = lonStr;
    }

    public String getLatStr() {
        return TypeUtil.getLatStr(lat);
    }

    public void setLatStr(String latStr) {
        this.lat = TypeUtil.convertToMinute(latStr);
        this.latStr = latStr;
    }

    public String getActionTypeStr() {
        return TypeUtil.getActionType(actionType);
    }

    public void setActionTypeStr(String actionTypeStr) {
        this.actionTypeStr = actionTypeStr;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public List<Catch> getCatches() {
        return catches;
    }

    public void setCatches(List<Catch> catches) {
        this.catches = catches;
    }

    public List<Discard> getDiscards() {
        return discards;
    }

    public void setDiscards(List<Discard> discards) {
        this.discards = discards;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getNetNum() {
        return netNum;
    }

    public void setNetNum(String netNum) {
        this.netNum = netNum;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public Integer getLon() {
        return lon;
    }

    public void setLon(Integer lon) {
        this.lon = lon;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getStartLon() {
        return startLon;
    }

    public void setStartLon(Integer startLon) {
        this.startLon = startLon;
    }

    public Integer getStartLat() {
        return startLat;
    }

    public void setStartLat(Integer startLat) {
        this.startLat = startLat;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getEndLon() {
        return endLon;
    }

    public void setEndLon(Integer endLon) {
        this.endLon = endLon;
    }

    public Integer getEndLat() {
        return endLat;
    }

    public void setEndLat(Integer endLat) {
        this.endLat = endLat;
    }

    public Integer getHookSum() {
        return hookSum;
    }

    public void setHookSum(Integer hookSum) {
        this.hookSum = hookSum;
    }

    public Integer getFloatHookSum() {
        return floatHookSum;
    }

    public void setFloatHookSum(Integer floatHookSum) {
        this.floatHookSum = floatHookSum;
    }

    public Integer getWaterDeep() {
        return waterDeep;
    }

    public void setWaterDeep(Integer waterDeep) {
        this.waterDeep = waterDeep;
    }

    public Integer getWaterTemp() {
        return waterTemp;
    }

    public void setWaterTemp(Integer waterTemp) {
        this.waterTemp = waterTemp;
    }

    public Integer getMachineSum() {
        return machineSum;
    }

    public void setMachineSum(Integer machineSum) {
        this.machineSum = machineSum;
    }

    public Integer getMachineHookSum() {
        return machineHookSum;
    }

    public void setMachineHookSum(Integer machineHookSum) {
        this.machineHookSum = machineHookSum;
    }

    public Integer getManualSum() {
        return manualSum;
    }

    public void setManualSum(Integer manualSum) {
        this.manualSum = manualSum;
    }

    public Integer getHookDeep() {
        return hookDeep;
    }

    public void setHookDeep(Integer hookDeep) {
        this.hookDeep = hookDeep;
    }

    public Integer getSurfaceTemp() {
        return surfaceTemp;
    }

    public void setSurfaceTemp(Integer surfaceTemp) {
        this.surfaceTemp = surfaceTemp;
    }

    public Integer getLightPower() {
        return lightPower;
    }

    public void setLightPower(Integer lightPower) {
        this.lightPower = lightPower;
    }

    public Integer getPlateCapacity() {
        return plateCapacity;
    }

    public void setPlateCapacity(Integer plateCapacity) {
        this.plateCapacity = plateCapacity;
    }

    public Integer getRopeLength() {
        return ropeLength;
    }

    public void setRopeLength(Integer ropeLength) {
        this.ropeLength = ropeLength;
    }

    public Integer getDragSpeed() {
        return dragSpeed;
    }

    public void setDragSpeed(Integer dragSpeed) {
        this.dragSpeed = dragSpeed;
    }

    public Integer getLightSum() {
        return lightSum;
    }

    public void setLightSum(Integer lightSum) {
        this.lightSum = lightSum;
    }

    public Integer getWindVelocity() {
        return windVelocity;
    }

    public void setWindVelocity(Integer windVelocity) {
        this.windVelocity = windVelocity;
    }

    public Integer getCurrentVelocity() {
        return currentVelocity;
    }

    public void setCurrentVelocity(Integer currentVelocity) {
        this.currentVelocity = currentVelocity;
    }

    public Integer getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Integer currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getWellNum() {
        return wellNum;
    }

    public void setWellNum(String wellNum) {
        this.wellNum = wellNum;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getNetLoc() {
        return netLoc;
    }

    public void setNetLoc(String netLoc) {
        this.netLoc = netLoc;
    }

    public String getSeaCondition() {
        return seaCondition;
    }

    public void setSeaCondition(String seaCondition) {
        this.seaCondition = seaCondition;
    }

    public Integer getNetHeight() {
        return netHeight;
    }

    public void setNetHeight(Integer netHeight) {
        this.netHeight = netHeight;
    }

    public String getNetExpand() {
        return netExpand;
    }

    public void setNetExpand(String netExpand) {
        this.netExpand = netExpand;
    }

    public String getGearStandard() {
        return gearStandard;
    }

    public void setGearStandard(String gearStandard) {
        this.gearStandard = gearStandard;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Integer getAirTemp() {
        return airTemp;
    }

    public void setAirTemp(Integer airTemp) {
        this.airTemp = airTemp;
    }

    public String getWaveDir() {
        return waveDir;
    }

    public void setWaveDir(String waveDir) {
        this.waveDir = waveDir;
    }

    public Integer getWaveHeight() {
        return waveHeight;
    }

    public void setWaveHeight(Integer waveHeight) {
        this.waveHeight = waveHeight;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getUnloadingDest() {
        return unloadingDest;
    }

    public void setUnloadingDest(String unloadingDest) {
        this.unloadingDest = unloadingDest;
    }

    public String getUnloadingCallsign() {
        return unloadingCallsign;
    }

    public void setUnloadingCallsign(String unloadingCallsign) {
        this.unloadingCallsign = unloadingCallsign;
    }

    public Integer getUnloadingSkipjack() {
        return unloadingSkipjack;
    }

    public void setUnloadingSkipjack(Integer unloadingSkipjack) {
        this.unloadingSkipjack = unloadingSkipjack;
    }

    public Integer getUnloadingYellowfin() {
        return unloadingYellowfin;
    }

    public void setUnloadingYellowfin(Integer unloadingYellowfin) {
        this.unloadingYellowfin = unloadingYellowfin;
    }

    public Integer getUnloadingBigeye() {
        return unloadingBigeye;
    }

    public void setUnloadingBigeye(Integer unloadingBigeye) {
        this.unloadingBigeye = unloadingBigeye;
    }

    public Integer getUnloadingMix() {
        return unloadingMix;
    }

    public void setUnloadingMix(Integer unloadingMix) {
        this.unloadingMix = unloadingMix;
    }

    public Integer getUnloadingOther() {
        return unloadingOther;
    }

    public void setUnloadingOther(Integer unloadingOther) {
        this.unloadingOther = unloadingOther;
    }

    public Integer getUnloadingReject() {
        return unloadingReject;
    }

    public void setUnloadingReject(Integer unloadingReject) {
        this.unloadingReject = unloadingReject;
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
        return "Fishinglog{" +
                "id='" + id + '\'' +
                ", shipId='" + shipId + '\'' +
                ", logType='" + logType + '\'' +
                ", logTypeStr='" + logTypeStr + '\'' +
                ", actionType='" + actionType + '\'' +
                ", actionTypeStr='" + actionTypeStr + '\'' +
                ", tripId='" + tripId + '\'' +
                ", netNum='" + netNum + '\'' +
                ", logDate=" + logDate +
                ", logDateStr='" + logDateStr + '\'' +
                ", lon=" + lon +
                ", lonStr='" + lonStr + '\'' +
                ", lat=" + lat +
                ", latStr='" + latStr + '\'' +
                ", startTime=" + startTime +
                ", startTimeStr='" + startTimeStr + '\'' +
                ", startLon=" + startLon +
                ", startLonStr='" + startLonStr + '\'' +
                ", startLat=" + startLat +
                ", startLatStr='" + startLatStr + '\'' +
                ", endTime=" + endTime +
                ", endTimeStr='" + endTimeStr + '\'' +
                ", endLon=" + endLon +
                ", endLonStr='" + endLonStr + '\'' +
                ", endLat=" + endLat +
                ", endLatStr='" + endLatStr + '\'' +
                ", hookSum=" + hookSum +
                ", floatHookSum=" + floatHookSum +
                ", waterDeep=" + waterDeep +
                ", waterTemp=" + waterTemp +
                ", machineSum=" + machineSum +
                ", machineHookSum=" + machineHookSum +
                ", manualSum=" + manualSum +
                ", manualIdleHookSum=" + manualIdleHookSum +
                ", hookDeep=" + hookDeep +
                ", surfaceTemp=" + surfaceTemp +
                ", lightPower=" + lightPower +
                ", plateCapacity=" + plateCapacity +
                ", ropeLength=" + ropeLength +
                ", dragSpeed=" + dragSpeed +
                ", lightSum=" + lightSum +
                ", windVelocity=" + windVelocity +
                ", currentVelocity=" + currentVelocity +
                ", currentDirection=" + currentDirection +
                ", windDirection=" + windDirection +
                ", schoolCode='" + schoolCode + '\'' +
                ", schoolCodeStr='" + schoolCodeStr + '\'' +
                ", wellNum='" + wellNum + '\'' +
                ", weather='" + weather + '\'' +
                ", netLoc='" + netLoc + '\'' +
                ", seaCondition='" + seaCondition + '\'' +
                ", netHeight=" + netHeight +
                ", netExpand='" + netExpand + '\'' +
                ", gearStandard='" + gearStandard + '\'' +
                ", visibility='" + visibility + '\'' +
                ", airTemp=" + airTemp +
                ", waveDir='" + waveDir + '\'' +
                ", waveHeight=" + waveHeight +
                ", submitter='" + submitter + '\'' +
                ", unloadingDest='" + unloadingDest + '\'' +
                ", unloadingCallsign='" + unloadingCallsign + '\'' +
                ", unloadingSkipjack=" + unloadingSkipjack +
                ", unloadingYellowfin=" + unloadingYellowfin +
                ", unloadingBigeye=" + unloadingBigeye +
                ", unloadingMix=" + unloadingMix +
                ", unloadingOther=" + unloadingOther +
                ", unloadingReject=" + unloadingReject +
                ", createTime=" + createTime +
                ", creator='" + creator + '\'' +
                ", updateTime=" + updateTime +
                ", updator='" + updator + '\'' +
                ", catches=" + catches +
                ", discards=" + discards +
                ", del=" + del +
                ", shipName='" + shipName + '\'' +
                ", skipjack=" + skipjack +
                ", yellowFinBig=" + yellowFinBig +
                ", yellowFinSmall=" + yellowFinSmall +
                ", bigEyeBig=" + bigEyeBig +
                ", bigEyeSmall=" + bigEyeSmall +
                ", modifyLogId='" + modifyLogId + '\'' +
                ", submitVer=" + submitVer +
                ", logRecordType=" + logRecordType +
                ", isSelect=" + isSelect +
                ", isSlip=" + isSlip +
                ", remark='" + remark + '\'' +
                '}';
    }

    public FishLog getFishLog() {
        FishLog fl = new FishLog();
        //船名
        fl.setShipName(this.shipName);
        //呼号
        fl.setCallsign(this.unloadingCallsign);
        //日志类型：1为金枪鱼延绳钓，2为围网，3为拖网，4为鱿钓，5为金枪鱼围网,6为竹筴鱼/鲐鱼渔捞日志
        fl.setLogType(this.logType);
        //作业类型： 0作业1航行2漂流3转载4在港5故障6未作业-天气原因7寻找鱼群8清洁网具9放置或回收阀艇、人工聚鱼;
        fl.setActionType(this.actionType);
        // 作业时间
        fl.setLogDate(this.logDate);
        //经度
        fl.setLongitude(this.lon);
        //纬度
        fl.setLatitude(this.lat);
        //网次
        if (StringUtil.isNotBlank(this.netNum) && !"null".equals(this.netNum)) {
            fl.setNetTime(Integer.valueOf(this.netNum));
        }
        //开始时间
        fl.setStartTime(this.startTime);
        //开始经度
        fl.setStartLongitude(this.startLon);
        //开始纬度
        fl.setStartLatitude(this.startLat);
        //结束时间
        fl.setEndTime(this.endTime);
        //结束经度
        fl.setEndLongitude(this.endLon);
        //结束纬度
        fl.setEndLatitude(this.endLat);
        //曳纲长度，单位为厘米
        fl.setYegangchangdu(this.ropeLength);
        //拖速，单位为：0.01节
        if (this.dragSpeed != null) {
            fl.setTuosu(this.dragSpeed * 10);
        }
        //钓机台数
        fl.setDiaojitaishu(this.machineSum);
        //单钓钩数
        fl.setDandiaogoushu(this.machineHookSum);
        //手钓人数
        fl.setShoudiaorenshu(this.manualSum);
        //投钓水深，单位为厘米
        fl.setToudiaoshuishen(this.hookDeep);
        //水温，单位：0.1度
        fl.setShuiwen(this.waterTemp);
        //灯光功率，单位为瓦
        fl.setDengguanggonglv(this.lightPower);
        //盘重，单位：0.1千克
        fl.setPanzhong(this.plateCapacity);
        //钩数
        fl.setGoushu(this.hookSum);
        //浮球间钩数
        fl.setFuqiujiangoushu(this.floatHookSum);
        //水深，单位0.01米
        fl.setShuishen(this.waterDeep);
        //开灯数
        fl.setKaidengshu(this.lightSum);
        //风速，单位0.1米/秒
        fl.setFengsu(this.windVelocity);
        //风向，0.1度
        fl.setFengxiang(this.windDirection);
        //流速,单位：cm/s
        if (this.currentVelocity != null) {
            fl.setLiusu(this.currentVelocity * 10);
        }
        //流向，0.1度
        fl.setLiuxiang(this.currentDirection);
        //围捕类型
        fl.setWeibuleixing(this.schoolCode);
        fl.setWeibuleixingStr(this.schoolCodeStr);
        //鲣鱼渔获量，单位0.1Kg
        if (this.skipjack != null) {
            fl.setJianyu(this.skipjack / 100);
        }
        //≤9Kg黄鳍金枪鱼产量，单位0.1Kg
        if (this.yellowFinSmall != null) {
            fl.setHuangqi1(this.yellowFinSmall / 100);
        }
        //>9Kg黄鳍金枪鱼产量，单位0.1Kg
        if (this.yellowFinBig != null) {
            fl.setHuangqi2(this.yellowFinBig / 100);
        }
        //≤9Kg大目金枪鱼产量，单位0.1Kg
        if (this.bigEyeSmall != null) {
            fl.setDamu1(this.bigEyeSmall / 100);
        }
        //>9Kg大目金枪鱼产量，单位0.1Kg
        if (this.bigEyeBig != null) {
            fl.setDamu2(this.bigEyeBig / 100);
        }
        if (this.discards.size() > 0) {
            //丢弃金枪鱼种类
            fl.setDiuqijinqiangyu(discards.get(0).getFishName());
            //丢弃金枪鱼重量，单位0.1KG
            if (discards.get(0).getWeight() != null) {
                fl.setDiuqijinqiangyuzhongliang((int) Math.round(discards.get(0).getWeight() / 100));
            }
            //丢弃金枪鱼原因
            fl.setQiuqijinqiangyuyuanyin(discards.get(0).getReason());
        }
        if (this.discards.size() > 1) {
            //丢弃其他鱼种种类
            fl.setDiuqiqitayuzhongzhonglei(discards.get(1).getFishName());
            //丢弃其他鱼种数量
            fl.setDiuqiqitayuzhongshuliang(discards.get(1).getSum());
            //丢弃其他鱼种重量，单位0.1Kg
            if (discards.get(1).getWeight() != null) {
                fl.setDiuqijinqiangyuzhongliang((int) Math.round(discards.get(1).getWeight() / 100));
            }
        }
        //鱼仓
        if (this.wellNum != null) {
            fl.setYucang(String.valueOf(this.wellNum));
        }
        //激活代码
        fl.setJihuodaima(this.actionType);
        //航次号
        //出港名称
        //出港时间
        //卸鱼地点
        //进港时间
        //天气情况
        fl.setTianqiqingkuang(this.weather);
        //海况
        fl.setHaikuang(this.seaCondition);
        //开始捕捞时网位
        if (StringUtil.isNotBlank(this.netLoc) && !"null".equals(this.netLoc)) {
            fl.setKaishiwangwei(Integer.valueOf(this.netLoc));
        }
        //网口垂直高度
        fl.setWangkougaodu(this.netHeight);
        //网口水平扩张
        if (StringUtil.isNotBlank(this.netExpand) && !"null".equals(this.netExpand)) {
            fl.setWangkoukuozhang(Integer.valueOf(this.netExpand));
        }
        //鱼群深度
        fl.setYuqunshuishen(this.waterDeep);
        //渔具规格
        fl.setYujuguimo(this.gearStandard);
        //能见度
        if (StringUtil.isNotBlank(this.visibility) && !"null".equals(this.visibility)) {
            fl.setNengjiandu(Integer.valueOf(this.visibility));
        }
        //气温
        fl.setQiwen(this.airTemp);
        //浪向
        if (StringUtil.isNotBlank(this.waveDir) && !"null".equals(this.waveDir)) {
            fl.setLangxiang(Integer.valueOf(this.waveDir));
        }
        //浪高
        fl.setLanggao(this.waveHeight);
        //竹筴鱼风向
        if (this.windDirection != null) {
            fl.setFx(String.valueOf(this.windDirection));
        }
        if (this.catches.size() > 0) {
            for (Catch aCatch : catches) {
                FishLogItem fishLogItem = new FishLogItem();
                //鱼种名称
                fishLogItem.setFishName(aCatch.getFishName());
                //渔获量，单位：0.1Kg
                if (aCatch.getWeight() != null) {
                    fishLogItem.setYuhuoliang(aCatch.getWeight().longValue() / 100);
                }
                //留仓尾数
                if (aCatch.getNum() != null && aCatch.getDiscarded() != null) {
                    fishLogItem.setLiucangweishu(aCatch.getNum() - aCatch.getDiscarded());
                }
                //留仓重量
                if (aCatch.getWeight() != null && aCatch.getDiscardedWeight() != null) {
                    fishLogItem.setLiucangzhongliang(aCatch.getWeight().intValue() - aCatch.getDiscardedWeight());
                }
                //丢弃尾数
                fishLogItem.setDiuqiweishu(aCatch.getDiscarded());
                //鱼舱号
                fishLogItem.setFishholdno(aCatch.getWellNo());
                //鱼舱号名称
                //数量
                fishLogItem.setShuliang(aCatch.getNum());
                //规格
                fishLogItem.setGuige(aCatch.getStandardName());
                //单位
                fishLogItem.setDanwei(aCatch.getUnitName());
                //是否丢弃
                //丢弃原因
                fl.getItems().add(fishLogItem);
            }
        }
        return fl;
    }
}
