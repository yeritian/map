package com.shipmap.modules.base.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 鱼的重量和尾数表
 * </p>
 *
 * @author ${author}
 * @since 2019-04-02
 */
@TableName("tb_specification")
public class Specification extends Model<Specification> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 鱼的名称
     */
    @TableField("fish_name")
    private String fishName;
    /**
     * 重量
     */
    private String weight;
    /**
     * 尾数
     */
    private String mantissa;
    /**
     * 月报id
     */
    @TableField("monthly_id")
    private String monthlyId;

    /**
     * 鱼的类型
     *
     * @return
     */
    @TableField("super_fish")
    private String superFish;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFishName() {
        return fishName;
    }

    public void setFishName(String fishName) {
        this.fishName = fishName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMantissa() {
        return mantissa;
    }

    public void setMantissa(String mantissa) {
        this.mantissa = mantissa;
    }

    public String getMonthlyId() {
        return monthlyId;
    }

    public void setMonthlyId(String monthlyId) {
        this.monthlyId = monthlyId;
    }

    public String getSuperFish() {
        return superFish;
    }

    public void setSuperFish(String superFish) {
        this.superFish = superFish;
    }

    @Override
    public String toString() {
        return "Specification{" +
                "id='" + id + '\'' +
                ", fishName='" + fishName + '\'' +
                ", weight='" + weight + '\'' +
                ", mantissa='" + mantissa + '\'' +
                ", monthlyId='" + monthlyId + '\'' +
                ", superFish='" + superFish + '\'' +
                '}';
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
