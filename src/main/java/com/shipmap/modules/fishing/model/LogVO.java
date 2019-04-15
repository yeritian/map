package com.shipmap.modules.fishing.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shipmap.framework.utils.DateUtil;
import com.shipmap.framework.utils.NumberUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.framework.utils.TypeUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JunGao
 * @create 2018-12-25 15:44
 */

public class LogVO {

    /**
     * token
     */
    public String a;
    /**
     * 渔捞日志类型
     */
    //@TableField("log_type")
    public String b;
    /**
     * 活动类型
     */
    //@TableField("action_type")
    public String c;

    /**
     * 网次
     */
    //@TableField("net_num")
    public String d;
    /**
     * 日志时间
     */
    //@TableField("log_date")
    public String e;

    /**
     * 经度
     */
    public String f;
    /**
     * 纬度
     */
    public String g;
    /**
     * 作业开始时间
     */
    //@TableField("start_time")
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    public String h;
    /**
     * 开始经度
     */
    //@TableField("start_lon")
    public String i;
    /**
     * 开始纬度
     */
    //@TableField("start_lat")
    public String j;
    /**
     * 结束时间
     */
    //@TableField("end_time")
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    public String k;
    /**
     * 结束经度
     */
    //@TableField("end_lon")
    public String l;
    /**
     * 结束纬度
     */
    //@TableField("end_lat")
    public String m;
    /**
     * 下钩数
     */
    //@TableField("hook_sum")
    public String n;
    /**
     * 两浮球间钩数
     */
    //@TableField("float_hook_sum")
    public String o;
    /**
     * 水深（厘米）
     */
    //@TableField("water_deep")
    public String p;
    /**
     * 水温（0.1度）
     */
    //@TableField("water_temp")
    public String q;
    /**
     * 开机数量
     */
    //@TableField("machine_sum")
    public String r;
    /**
     * 单钓钩数
     */
    //@TableField("machine_hook_sum")
    public String s;
    /**
     * 手钓人数
     */
    //@TableField("manual_sum")
    public String t;
    /**
     * 投钩水深
     */
    //@TableField("hook_deep")
    public String u;
    /**
     * 水表温度（0.1度）
     */
    //@TableField("surface_temp")
    public String v;
    /**
     * 功率（瓦）
     */
    //@TableField("light_power")
    public String w;
    /**
     * 盘重
     */
    //@TableField("plate_capacity")
    public String x;
    /**
     * 曳纲长度(厘米)
     */
    //@TableField("rope_length")
    public String y;
    /**
     * 拖速(0.1节)
     */
    //@TableField("drag_speed")
    public String z;
    /**
     * 开灯数
     */
    //@TableField("light_sum")
    public String A;
    /**
     * 风速(0.1m/s)
     */
    //@TableField("wind_velocity")
    public String B;
    /**
     * 流速（0.1m/s）
     */
    //@TableField("current_velocity")
    public String C;
    /**
     * 流向（0.1度）
     */
    //@TableField("current_direction")
    public String D;
    /**
     * 风向（0.1度）
     */
    //@TableField("wind_direction")
    public String E;
    /**
     * 围捕类型
     */
    //@TableField("school_code")
    public String F;
    /**
     * 鱼舱号
     */
    //@TableField("well_num")
    public String G;
    /**
     * 天气
     */
    public String H;
    /**
     * 捕捞网位,单位厘米
     */
    //@TableField("net_loc")
    public String I;
    /**
     * 海况
     */
    //@TableField("sea_condition")
    public String J;
    /**
     * 网口高度(厘米)
     */
    //@TableField("net_height")
    public String K;
    /**
     * 网口扩张（厘米）
     */
    //@TableField("net_expand")
    public String L;
    /**
     * 渔具规格
     */
    //@TableField("gear_standard")
    public String M;
    /**
     * 能见度（0.01海里）
     */
    public String N;
    /**
     * 气温(0.1度)
     */
    //@TableField("air_temp")
    public String O;
    /**
     * 浪向
     */
    //@TableField("wave_dir")
    public String P;
    /**
     * 浪高（cm）
     */
    //@TableField("wave_height")
    public String Q;
    /**
     * 提交人
     */
    public String R;
    /**
     * 罐头加工厂或船舶和目的地
     */
    //@TableField("unloading_dest")
    public String S;
    /**
     * 转载船舶呼号
     */
    //@TableField("unloading_callsign")
    public String T;
    /**
     * 转载鲣鱼
     */
    //@TableField("unloading_skipjack")
    public String U;
    /**
     * 转载黄鳍
     */
    //@TableField("unloading_yellowfin")
    public String V;
    /**
     * 转载大目
     */
    //@TableField("unloading_bigeye")
    public String W;
    /**
     * 转载混合
     */
    //@TableField("unloading_mix")
    public String X;
    /**
     * 转载其他
     */
    //@TableField("unloading_other")
    public String Y;
    /**
     * 转载不合格
     */
    //@TableField("unloading_reject")
    public String Z;

    //@TableField("unloading_agent")
    public String a1;
    /**
     * 序列，每年从1开始
     */
    //@TableField("trip_no")
    public String a2;
    /**
     * 主捕鱼种
     */
    //@TableField("target_spec")
    public String a3;
    /**
     * 出发港或地点
     * departure
     */
    public String a4;
    /**
     * 转载港或地点
     */
    //@TableField("unloading_place")
    public String a5;
    /**
     * 出发时间
     */
    //@TableField("departure_time")
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    public String a6;
    /**
     * 转载时间
     */
    //@TableField("unloading_time")
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    public String a7;
    /**
     * 开始时船上剩余鱼量（克）
     */
    //@TableField("a8")fishStart
    public String a8;
    /**
     * 卸载后船上剩余鱼量(克)
     */
    //@TableField("fish_end")
    public String a9;


    public List<CatchVO> a10 = new ArrayList<>();
    public List<DiscardVO> a11 = new ArrayList<>();

    /**
     * 鲣鱼渔获量（克）
     */
    //@TableField("a8")fishStart
    public String a12;

    /**
     * 黄鳍（≤9Kg）（克）
     */
    //@TableField("a8")fishStart
    public String a13;

    /**
     * 黄鳍（＞9Kg）（克）
     */
    //@TableField("a8")fishStart
    public String a14;

    /**
     * 大目（≤9Kg）（克）
     */
    //@TableField("a8")fishStart
    public String a15;
    /**
     * 大目（＞9Kg）（克）
     */
    //@TableField("a8")fishStart
    public String a16;

    /**
     * 备注
     */
    public String a17;

    /**
     * 手钓闲钩数
     */
    public String a20;

    /**
     * 是否漏网（0漏网，1不漏网）
     */
    public Integer a21;

    public Fishinglog getLog() {
        Fishinglog log = new Fishinglog();
        log.setLogType(b);
        log.setActionType(c);
        log.setNetNum(d);

        log.setLogDate(DateUtil.parseDate(e, "yyyyMMddHHmm"));
        log.setLon(TypeUtil.convertToMinutePad(f));
        log.setLat(TypeUtil.convertToMinutePad(g));
        if (StringUtil.isNotBlank(h)) {
            if ("5".equals(b) && "4".equals(c)) {
                log.setStartTime(DateUtil.parseDate(h, "yyyyMMdd"));
            } else {
                log.setStartTime(DateUtil.parseDate(h, "yyyyMMddHHmm"));
            }
        }

        log.setStartLon(TypeUtil.convertToMinutePad(i));
        if (log.getLon() == null) {
            log.setLon(log.getStartLon());
        }
        log.setStartLat(TypeUtil.convertToMinutePad(j));
        if (log.getLat() == null) {
            log.setLat(log.getStartLat());
        }
        if (StringUtil.isNotBlank(k)) {
            if ("5".equals(b) && "4".equals(c)) {
                log.setEndTime(DateUtil.parseDate(k, "yyyyMMdd"));
            } else {
                log.setEndTime(DateUtil.parseDate(k, "yyyyMMddHHmm"));
            }
        }
        log.setEndLon(TypeUtil.convertToMinutePad(l));
        log.setEndLat(TypeUtil.convertToMinutePad(m));
        log.setHookSum(NumberUtil.trans62ToInt(n));
        log.setFloatHookSum(NumberUtil.trans62ToInt(o));
        log.setWaterDeep(NumberUtil.trans62ToInt(p));
        log.setWaterTemp(NumberUtil.trans62ToInt(q));
        log.setMachineSum(NumberUtil.trans62ToInt(r));
        log.setMachineHookSum(NumberUtil.trans62ToInt(s));
        log.setManualSum(NumberUtil.trans62ToInt(t));
        log.setHookDeep(NumberUtil.trans62ToInt(u));
        log.setSurfaceTemp(NumberUtil.trans62ToInt(v));
        log.setLightPower(NumberUtil.trans62ToInt(w));
        log.setPlateCapacity(NumberUtil.trans62ToInt(x));
        log.setRopeLength(NumberUtil.trans62ToInt(y));
        log.setDragSpeed(NumberUtil.trans62ToInt(z));
        log.setLightSum(NumberUtil.trans62ToInt(A));
        log.setWindVelocity(NumberUtil.trans62ToInt(B));
        log.setCurrentVelocity(NumberUtil.trans62ToInt(C));
        log.setCurrentDirection(NumberUtil.trans62ToInt(D));
        log.setWindDirection(NumberUtil.trans62ToInt(E));
        log.setSchoolCode(F);
        log.setWellNum(G);
        log.setWeather(H);
        log.setNetLoc(NumberUtil.trans62ToInt(I) + "");
        log.setSeaCondition(J);
        log.setNetHeight(NumberUtil.trans62ToInt(K));
        log.setNetExpand(NumberUtil.trans62ToInt(L) + "");
        log.setGearStandard(M);
        log.setVisibility(NumberUtil.trans62ToInt(N) + "");
        log.setAirTemp(NumberUtil.trans62ToInt(O));
        log.setWaveDir(NumberUtil.trans62ToInt(P) + "");
        log.setWaveHeight(NumberUtil.trans62ToInt(Q));
        log.setSubmitter(R);
        log.setUnloadingDest(S);
        log.setUnloadingCallsign(T);
        log.setUnloadingSkipjack(NumberUtil.trans62ToInt(U));
        log.setUnloadingYellowfin(NumberUtil.trans62ToInt(V));
        log.setUnloadingBigeye(NumberUtil.trans62ToInt(W));
        log.setUnloadingMix(NumberUtil.trans62ToInt(X));
        log.setUnloadingOther(NumberUtil.trans62ToInt(Y));
        log.setUnloadingReject(NumberUtil.trans62ToInt(Z));
        log.setSkipjack(NumberUtil.trans62ToInt(a12));
        log.setYellowFinSmall(NumberUtil.trans62ToInt(a13));
        log.setYellowFinBig(NumberUtil.trans62ToInt(a14));
        log.setBigEyeSmall(NumberUtil.trans62ToInt(a15));
        log.setBigEyeBig(NumberUtil.trans62ToInt(a16));
        log.setRemark(a17);
        log.setIsSlip(a21);
        log.setManualIdleHookSum(NumberUtil.trans62ToInt(a20));
        if (a10.size() > 0) {
            for (CatchVO cv : a10) {
                Catch cc = new Catch();
                cc.setFishId(cv.a);
                //Integer w = NumberUtil.trans62ToInt(cv.c);
                if (StringUtil.isNotBlank(cv.b)) {
                    BigDecimal bigDecimal = new BigDecimal(cv.b);
                    double v = bigDecimal.doubleValue();
                    cc.setWeight(v / 100);
                }
                if (StringUtil.isNotBlank(cv.c)) {
                    cc.setNum(Integer.valueOf(cv.c));
                }
                cc.setWellNo(cv.d);
                if (StringUtil.isNotBlank(cv.e)) {
                    cc.setDiscarded(Integer.valueOf(cv.e));
                }
                cc.setDiscardedWeight(NumberUtil.trans62ToInt(cv.f));
                cc.setStandardId(cv.g);
                cc.setUnitId(cv.h);
                log.getCatches().add(cc);
            }
        }

        if (a11.size() > 0) {
            for (DiscardVO dv : a11) {
                Discard dc = new Discard();
                dc.setFishId(dv.a);
                Integer w = NumberUtil.trans62ToInt(dv.b);
                if (w != null) {
                    dc.setWeight((((double) w) / 1000));
                }
                dc.setSum(NumberUtil.trans62ToInt(dv.c));
                dc.setReason(dv.d);
                dc.setReasonNote(dv.e);
                log.getDiscards().add(dc);
            }
        }
        return log;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LogVO{");
        sb.append("a='").append(a).append('\'');
        sb.append(", b='").append(b).append('\'');
        sb.append(", c='").append(c).append('\'');
        sb.append(", d='").append(d).append('\'');
        sb.append(", e='").append(e).append('\'');
        sb.append(", f='").append(f).append('\'');
        sb.append(", g='").append(g).append('\'');
        sb.append(", h='").append(h).append('\'');
        sb.append(", i='").append(i).append('\'');
        sb.append(", j='").append(j).append('\'');
        sb.append(", k='").append(k).append('\'');
        sb.append(", l='").append(l).append('\'');
        sb.append(", m='").append(m).append('\'');
        sb.append(", n='").append(n).append('\'');
        sb.append(", o='").append(o).append('\'');
        sb.append(", p='").append(p).append('\'');
        sb.append(", q='").append(q).append('\'');
        sb.append(", r='").append(r).append('\'');
        sb.append(", s='").append(s).append('\'');
        sb.append(", t='").append(t).append('\'');
        sb.append(", u='").append(u).append('\'');
        sb.append(", v='").append(v).append('\'');
        sb.append(", w='").append(w).append('\'');
        sb.append(", x='").append(x).append('\'');
        sb.append(", y='").append(y).append('\'');
        sb.append(", z='").append(z).append('\'');
        sb.append(", A='").append(A).append('\'');
        sb.append(", B='").append(B).append('\'');
        sb.append(", C='").append(C).append('\'');
        sb.append(", D='").append(D).append('\'');
        sb.append(", E='").append(E).append('\'');
        sb.append(", F='").append(F).append('\'');
        sb.append(", G='").append(G).append('\'');
        sb.append(", H='").append(H).append('\'');
        sb.append(", I='").append(I).append('\'');
        sb.append(", J='").append(J).append('\'');
        sb.append(", K='").append(K).append('\'');
        sb.append(", L='").append(L).append('\'');
        sb.append(", M='").append(M).append('\'');
        sb.append(", N='").append(N).append('\'');
        sb.append(", O='").append(O).append('\'');
        sb.append(", P='").append(P).append('\'');
        sb.append(", Q='").append(Q).append('\'');
        sb.append(", R='").append(R).append('\'');
        sb.append(", S='").append(S).append('\'');
        sb.append(", T='").append(T).append('\'');
        sb.append(", U='").append(U).append('\'');
        sb.append(", V='").append(V).append('\'');
        sb.append(", W='").append(W).append('\'');
        sb.append(", X='").append(X).append('\'');
        sb.append(", Y='").append(Y).append('\'');
        sb.append(", Z='").append(Z).append('\'');
        sb.append(", a1='").append(a1).append('\'');
        sb.append(", a2='").append(a2).append('\'');
        sb.append(", a3='").append(a3).append('\'');
        sb.append(", a4='").append(a4).append('\'');
        sb.append(", a5='").append(a5).append('\'');
        sb.append(", a6='").append(a6).append('\'');
        sb.append(", a7='").append(a7).append('\'');
        sb.append(", a8='").append(a8).append('\'');
        sb.append(", a9='").append(a9).append('\'');
        sb.append(", a10=").append(a10);
        sb.append(", a11=").append(a11);
        sb.append(", a12='").append(a12).append('\'');
        sb.append(", a13='").append(a13).append('\'');
        sb.append(", a14='").append(a14).append('\'');
        sb.append(", a15='").append(a15).append('\'');
        sb.append(", a16='").append(a16).append('\'');
        sb.append(", a17='").append(a17).append('\'');
        sb.append(", a20='").append(a20).append('\'');
        sb.append(", a21=").append(a21);
        sb.append('}');
        return sb.toString();
    }
}
