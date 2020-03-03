package com.demo.miaoshademo.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    public static String MD5Util(String str) {
        return DigestUtils.md5Hex(str);
    }

    private static final String salt = "a123456";

    public static String inputPassToFormPass(String password) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + password + salt.charAt(4) + salt.charAt(6);
        return MD5Util(str);
    }

    public static String fromPassToDBPass(String password, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + password + salt.charAt(4) + salt.charAt(6);
        return MD5Util(str);
    }

    public static String inputPassToDBPass(String password, String salt) {
        String formPassword = inputPassToFormPass(password);
        String dbPassword = fromPassToDBPass(formPassword, salt);
        return dbPassword;
    }

    public static void main(String[] args) {
        String password = "123456";
        String salt = "aaaaaaaaa";
        System.out.println(inputPassToFormPass(password));
        String t = inputPassToFormPass(password);
        System.out.println(fromPassToDBPass(t, salt));
        System.out.println(inputPassToDBPass(password, salt));
    }
}
