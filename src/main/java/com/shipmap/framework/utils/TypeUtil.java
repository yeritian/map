package com.shipmap.framework.utils;

/**
 * @author JunGao
 * @create 2018-12-27 10:00
 */
public class TypeUtil {
    public static String[] logType = new String[]{"", "公海围网", "金枪鱼延绳钓/超低温", "鱿钓", "远洋拖网", "金枪鱼围网", "竹筴鱼/鲐鱼"};

    public static String getLogType(String log) {
        if (StringUtil.isBlank(log)) return "";
        try {
            int i = Integer.parseInt(log);
            if (i < logType.length) {
                return logType[i];
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String[] actionType = new String[]{"作业", "航行", "漂流", "在港", "转载", "故障", "未作业-天气原因", "寻找鱼群", "清洁网具", "放置或回收"};

    public static String getActionType(String action) {
        if (StringUtil.isBlank(action)) return "";
        try {
            int i = Integer.parseInt(action);
            if (i < actionType.length) {
                return actionType[i];
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String[] schoolType = new String[]{"", "Unassociated (free school)", "Feeding on Baitfish (free school)", "Drifting log, debris or dead animal", "Drifting raft, FAD or payao", "Anchored raft, FAD or payao", "Live whale", "Live whale shark", "Other (please specify)", "No tuna associated"};

    public static String getSchoolType(String school) {
        if (StringUtil.isBlank(school)) return "";
        try {
            int i = Integer.parseInt(school);
            if (i < schoolType.length) {
                return schoolType[i];
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String[] discardReasonType = new String[]{"", "鱼体已损坏", "船舶已满载", "机械故障", "其他原因", "非目标鱼种"};

    public static String getDiscardReasonType(String r) {
        if (StringUtil.isBlank(r)) return "";
        try {
            int i = Integer.parseInt(r);
            if (i < discardReasonType.length) {
                return discardReasonType[i];
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String[] seaConditionType = new String[]{"轻浪", "中浪", "大浪"};

    public static String getSeaConditionType(String r) {
        if (StringUtil.isBlank(r)) return "";
        try {
            int i = Integer.parseInt(r);
            if (i < seaConditionType.length) {
                return seaConditionType[i];
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String[] weatherType = new String[]{"晴", "多云", "阴", "阵雨", "雷阵雨", "小雨", "中雨", "大雨", "暴雨"};

    public static String getWeatherType(String r) {
        if (StringUtil.isBlank(r)) return "";
        try {
            int i = Integer.parseInt(r);
            if (i < weatherType.length) {
                return weatherType[i];
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String[] timeZoneType = new String[]{"西十二区", "西十一区", "西十区", "西九区", "西八区", "西七区", "西六区", "西五区", "西四区", "西三区", "西二区", "西一区", "零时区(UTC时间)", "东一区", "东二区", "东三区", "东四区", "东五区", "东六区", "东七区", "东八区(北京时间)", "东九区", "东十区", "东十一区", "东十二区",};

    public static String getTimeZoneType(String r) {
        if (StringUtil.isBlank(r)) return "";
        try {
            int i = Integer.parseInt(r);
            if (i < timeZoneType.length) {
                return timeZoneType[i];
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getLonStr(Integer lon) {
        if (lon == null) return "";
        String ew = "E";
        int l = lon;
        if (lon < 0) {
            l = -l;
            ew = "W";
        }
        if (lon == 0) ew = "";
        double ld = l;
        int degree = (int) (ld / 10000 / 60);
        double minute = (ld / 10000 / 60 - degree) * 60;
        String f = NumberUtil.formatDouble(minute, "#.###");
        return degree + "°" + f + "′" + ew;
    }

    public static String getLatStr(Integer lat) {
        if (lat == null) return "";
        String ns = "N";
        int l = lat;
        if (lat < 0) {
            l = -l;
            ns = "S";
        }
        if (lat == 0) ns = "";
        double ld = l;
        int degree = (int) (ld / 10000 / 60);
        double minute = (ld / 10000 / 60 - degree) * 60;
        String f = NumberUtil.formatDouble(minute, "#.###");
        return degree + "°" + f + "′" + ns;
    }

    //度分转万分之一分
    public static Integer convertToMinute(String dfm) {
        if (StringUtil.isBlank(dfm)) return null;
        int neg = 1;
        if (dfm.endsWith("W") || dfm.endsWith("S") || dfm.endsWith("w") || dfm.endsWith("s")) {
            neg = -1;
        }
        //dfm = dfm.split(0,dfm.length()-1);
        String[] dfms = dfm.split("°");
        int d = 0;
        if (dfms.length == 2) {
            d = Integer.parseInt(dfms[0]);
            dfm = dfms[1];
        }
        double f = 0;
        dfms = dfm.split("′");
        if (dfms.length == 2) {
            f = Double.parseDouble(dfms[0]);
        }
        return (int) (Math.round(f * 10000 + d * 10000 * 60)) * neg;
    }

    //pad度分转万分之一分
    public static Integer convertToMinutePad(String degree) {
        if (StringUtil.isBlank(degree)) {
            return null;
        }
        double minute = Double.parseDouble(degree) * 10000 * 60;
        return Integer.parseInt(String.valueOf(minute));
    }

}
