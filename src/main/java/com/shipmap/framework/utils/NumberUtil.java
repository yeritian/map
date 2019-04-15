package com.shipmap.framework.utils;

import java.text.DecimalFormat;

/**
 * @author JunGao
 * @create 2018-12-19 15:51
 */
public class NumberUtil {

    public static String transIntTo62(Integer n) {
        if (n == null) return null;
        boolean neg = false;
        if (n < 0) {
            neg = true;
            n = -n;
        }
        StringBuilder sb = new StringBuilder(8);
        String a;
        char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        while (n != 0) {
            sb = sb.append(b[n % 62]);
            n = n / 62;
        }
        a = sb.reverse().toString();
        if (neg) {
            a = "-" + a;
        }
        return a;
    }

    public static String transLongTo62(Long n) {
        if (n == null) return null;
        boolean neg = false;
        if (n < 0) {
            neg = true;
            n = -n;
        }
        StringBuilder sb = new StringBuilder(8);
        String a;
        char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        while (n != 0) {
            sb = sb.append(b[(int) (n % 62)]);
            n = n / 62;
        }
        a = sb.reverse().toString();
        if (neg) {
            a = "-" + a;
        }
        return a;
    }

    public static Integer trans62ToInt(String str) {
        if (str == null) return null;
        String code = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        boolean neg = false;
        if (str.startsWith("-")) {
            neg = true;
            str = str.substring(1);
        }
        int i = 0, ans = 0;
        while (str.length() != i) {
            ans *= 62;
            ans += code.indexOf(str.charAt(i));
            i++;
        }
        if (neg) {
            ans = -ans;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(Double.parseDouble("2.3"));
        double d = 2.3;

        System.out.println(Math.round(d * 100));
        System.out.println(trans62ToInt("3H"));
        System.out.println(transIntTo62(230));
    }

    public static String formatDouble(Double d, String pattern) {
        if (d == null) return "";
        String s = new DecimalFormat(pattern).format(d);
        return s;
    }
}
