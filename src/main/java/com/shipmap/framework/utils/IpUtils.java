package com.shipmap.framework.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

public class IpUtils {
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("client-ip");
        if ((StringUtils.isEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("x-forwarded-for");
        }
        if ((StringUtils.isEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((StringUtils.isEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((StringUtils.isEmpty(ip)) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static long ipToLong(String ipString) {
        long result = 0L;
        StringTokenizer token = new StringTokenizer(ipString, ".");
        result += (Long.parseLong(token.nextToken()) << 24);
        result += (Long.parseLong(token.nextToken()) << 16);
        result += (Long.parseLong(token.nextToken()) << 8);
        result += Long.parseLong(token.nextToken());
        return result;
    }

    public static String longToIp(long ipLong) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipLong >>> 24);
        sb.append(".");
        sb.append(String.valueOf((ipLong & 0xFFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((ipLong & 0xFFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf(ipLong & 0xFF));
        return sb.toString();
    }

    public static void main(String[] args) {
        String ip = "10.0.3.193";
        System.out.println(Long.toBinaryString(ipToLong(ip)));
        System.out.println(longToIp(ipToLong(ip)));
    }
}