package com.shipmap.framework.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tools {
    public static int dataSendIndex = 0;

    private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyMMdd");
    private static SimpleDateFormat sdf4 = new SimpleDateFormat("MMdd");
    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf5 = new SimpleDateFormat("MM月dd日");
    private static SimpleDateFormat sdf6 = new SimpleDateFormat("HH:mm:ss");
    private static SimpleDateFormat sdf7 = new SimpleDateFormat("yyyy年MM月dd日");
    private static SimpleDateFormat sdf_year = new SimpleDateFormat("yyyy");
    private static NumberFormat nf = new DecimalFormat("#.##");
    private static DecimalFormat df = new DecimalFormat("#.#####");
    private static SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
    private static SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
    private static SimpleDateFormat sdf8 = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat sdf9 = new SimpleDateFormat("HHmmss");

    public static String toSimpleDate9Str(Date date) {
        if (date != null) {
            return sdf9.format(date);
        }
        return "";
    }

    public static String toSimpleDate8Str(Date date) {
        if (date != null) {
            return sdf8.format(date);
        }
        return "";
    }

    public static String lonlat(Double d) {
        if (d == null) return "";
        return df.format(d);
    }

    public static String formatDouble(Double d) {
        if (d == null) return "";
        return nf.format(d);
    }

    /**
     * HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String toTimeStr(Date date) {
        if (date != null) {
            return sdf6.format(date);
        }
        return "";
    }

    //12月03日
    public static String toSimpleDateStr2(Date date) {
        if (date != null) {
            return sdf5.format(date);
        }
        return "";
    }

    //1213
    public static String toSimpleDateStr1(Date date) {
        if (date != null) {
            return sdf4.format(date);
        }
        return "";
    }

    public static String toSimpleDateStr(Date date) {
        if (date != null) {
            return sdf3.format(date);
        }
        return "";
    }

    /**
     * 得到UTC时间，类型为字符串，格式为"yyyy-MM-dd HH:mm"<br />
     * 如果获取失败，返回null
     *
     * @return
     */
    public static String getUTCTimeStr() {
        StringBuffer UTCTimeBuffer = new StringBuffer();
        // 1、取得本地时间：  
        Calendar cal = Calendar.getInstance();
        // 2、取得时间偏移量：  
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        // 3、取得夏令时差：  
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        // 4、从本地时间里扣除这些差量，即可以取得UTC时间：  
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        String m = month + "";
        if (month < 10) {
            m = "0" + m;
        }
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String d = day + "";
        if (day < 10) {
            d = "0" + d;
        }
        UTCTimeBuffer.append(year).append("-").append(m).append("-").append(d);
        try {
            sdf1.parse(UTCTimeBuffer.toString());
            return UTCTimeBuffer.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toDateStr(Date date) {
        if (date != null) {
            return sdf1.format(date);
        }
        return "";
    }

    public static String toChsDateStr(Date date) {
        if (date != null) {
            return sdf7.format(date);
        }
        return "";
    }

    public static String toDateTimeStr(Date date) {
        if (date != null) {
            return sdf2.format(date);
        }
        return "";
    }

    public static Date toDate(String date) {
        Date d = null;
        if (date != null && !"".equals(date)) {
            try {
                d = sdf1.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return d;
    }

    public static Date toDateTime(String date) {
        Date d = null;
        if (date != null && !"".equals(date)) {
            try {
                d = sdf2.parse(date);
            } catch (Exception e) {
            }
        }
        return d;
    }
	/*public static void main(String[] args){
		Calendar calendar = Calendar.getInstance();
		int offset = calendar.get(Calendar.ZONE_OFFSET); 
		calendar.add(Calendar.MILLISECOND, -offset);
		Date date = calendar.getTime(); 
		System.out.println(toDateTimeStr(date));
		
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date d = c.getTime();
		System.out.println(toDateTimeStr(d));
	}*/

    private final static double EARTH_RADIUS = 6378.137;// 地球半径

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两点间经纬度坐标（long值），计算两点间距离，单位为海里
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS * 0.53996;
        s = ((double) Math.round(s * 100000)) / 100000;
        return s;
    }
	
	/*public static void main(String[] args){
		double d = GetDistance(38d,121d,37d,121d);
		System.out.println(d);
	}*/

    public static String getSeasonStartDateStr() {
        String re = "";
        Date now = new Date();
        Date nowSeason = null;
        String year = sdf_year.format(now);
        try {
            Date first = sdf1.parse(year + "-01-01");
            nowSeason = first;
            Date second = sdf1.parse(year + "-04-01");
            if (second.before(now)) {
                nowSeason = second;
            }
            Date third = sdf1.parse(year + "-07-01");
            if (third.before(now)) {
                nowSeason = third;
            }
            Date fourth = sdf1.parse(year + "-11-01");
            if (fourth.before(now)) {
                nowSeason = fourth;
            }
            re = sdf1.format(nowSeason);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return re;
    }

    public static String toYear(Date date) {
        if (date != null) {
            return sdf_year.format(date);
        }
        return "";
    }

    public static String toMonth(Date date) {
        if (date != null) {
            return sdfMonth.format(date);
        }
        return "";
    }

    public static String toDay(Date date) {
        if (date != null) {
            return sdfDay.format(date);
        }
        return "";
    }

    public static String toSimpleLa(Integer lat) {
        int m = lat / 10000;
        int d = (int) (m / 60);
        int mi = m - d * 60;
        if (mi < 0) mi = -mi;
        String h = "N";
        if (d < 0) {
            h = "S";
            d = -d;
        }
        DecimalFormat df = new DecimalFormat("00");
        return h + df.format(d) + df.format(mi);
    }

    public static String toSimpleLo(Integer lon) {
        int m = lon / 10000;
        int d = (int) (m / 60);
        int mi = m - d * 60;
        if (mi < 0) mi = -mi;
        String h = "E";
        if (d < 0) {
            h = "W";
            d = -d;
        }
        DecimalFormat df = new DecimalFormat("000");
        DecimalFormat df1 = new DecimalFormat("00");
        return h + df.format(d) + df1.format(mi);
    }

    public static void main(String[] args) {
        System.out.println(toSimpleLo(12312111));
    }

    public static String toSimpleSpeed(Integer speed) {
        if (speed > 999) speed = 999;
        DecimalFormat df = new DecimalFormat("000");
        return df.format(speed);
    }

    public static String toSimpleCourse(Integer course) {
        if (course > 9999) course = 9999;
        int c = course / 10;
        DecimalFormat df = new DecimalFormat("000");
        return df.format(c);
    }

    public static String toSimpleLa2(Integer latitude) {
        double d = ((double) latitude) / 600000;
        DecimalFormat df = new DecimalFormat("00.000");
        String re = df.format(d);
        if (d >= 0) {
            re = "+" + re;
        }
        return re;
    }

    public static String toSimpleLo2(Integer longitude) {
        double d = ((double) longitude) / 600000;
        DecimalFormat df = new DecimalFormat("000.000");
        String re = df.format(d);
        if (d >= 0) {
            re = "+" + re;
        }
        return re;
    }
}
