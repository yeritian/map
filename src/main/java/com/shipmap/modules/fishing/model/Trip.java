package com.shipmap.modules.fishing.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author ${author}
 * @since 2018-12-25
 */
@TableName("tb_trip")
public class Trip extends Model<Trip> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    private String id;
    @TableField("ship_id")
    private String shipId;
    @TableField(exist = false)
    private String shipName;
    /*
     * 转载代理商
     * */
    @TableField("unloading_agent")
    private String unloadingAgent;
    /**
     * 序列，每年从1开始
     */
    @TableField("trip_no")
    private String tripNo;
    /**
     * 主捕鱼种
     */
    @TableField("target_spec")
    private String targetSpec;
    /**
     * 出发港或地点
     */
    private String departure;
    /**
     * 转载港或地点
     */
    @TableField("unloading_place")
    private String unloadingPlace;
    /**
     * 出发时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @TableField("departure_time")
    private Date departureTime;
    /**
     * 转载时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm")
    @TableField("unloading_time")
    private Date unloadingTime;
    /**
     * 开始时船上剩余鱼量
     */
    @TableField(exist = false)
    private List<TripCatch> fishStart = new ArrayList<>();
    /**
     * 转载鱼量
     */
    @TableField(exist = false)
    private List<TripCatch> fishUnloading = new ArrayList<>();
    ;
    /**
     * 卸载后船上剩余鱼量
     */
    @TableField(exist = false)
    private List<TripCatch> fishEnd = new ArrayList<>();
    @TableField(value = "create_time")
    private Date createTime;
    private String creator;
    @TableField(value = "update_time")
    private Date updateTime;
    private String updator;


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

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getUnloadingAgent() {
        return unloadingAgent;
    }

    public void setUnloadingAgent(String unloadingAgent) {
        this.unloadingAgent = unloadingAgent;
    }

    public String getTripNo() {
        return tripNo;
    }

    public void setTripNo(String tripNo) {
        this.tripNo = tripNo;
    }

    public List<TripCatch> getFishStart() {
        return fishStart;
    }

    public void setFishStart(List<TripCatch> fishStart) {
        this.fishStart = fishStart;
    }

    public List<TripCatch> getFishUnloading() {
        return fishUnloading;
    }

    public void setFishUnloading(List<TripCatch> fishUnloading) {
        this.fishUnloading = fishUnloading;
    }

    public List<TripCatch> getFishEnd() {
        return fishEnd;
    }

    public void setFishEnd(List<TripCatch> fishEnd) {
        this.fishEnd = fishEnd;
    }

    public String getTargetSpec() {
        return targetSpec;
    }

    public void setTargetSpec(String targetSpec) {
        this.targetSpec = targetSpec;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getUnloadingPlace() {
        return unloadingPlace;
    }

    public void setUnloadingPlace(String unloadingPlace) {
        this.unloadingPlace = unloadingPlace;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getUnloadingTime() {
        return unloadingTime;
    }

    public void setUnloadingTime(Date unloadingTime) {
        this.unloadingTime = unloadingTime;
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
        final StringBuilder sb = new StringBuilder("Trip{");
        sb.append("id='").append(id).append('\'');
        sb.append(", shipId='").append(shipId).append('\'');
        sb.append(", unloadingAgent='").append(unloadingAgent).append('\'');
        sb.append(", tripNo='").append(tripNo).append('\'');
        sb.append(", targetSpec='").append(targetSpec).append('\'');
        sb.append(", departure='").append(departure).append('\'');
        sb.append(", unloadingPlace='").append(unloadingPlace).append('\'');
        sb.append(", departureTime=").append(departureTime);
        sb.append(", unloadingTime=").append(unloadingTime);
        sb.append(", fishStart=").append(fishStart);
        sb.append(", fishUnloading=").append(fishUnloading);
        sb.append(", fishEnd=").append(fishEnd);
        sb.append(", createTime=").append(createTime);
        sb.append(", creator='").append(creator).append('\'');
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updator='").append(updator).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
