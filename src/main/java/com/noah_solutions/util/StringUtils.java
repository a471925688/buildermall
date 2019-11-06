package com.noah_solutions.util;

public class StringUtils extends org.springframework.util.StringUtils {
    public static boolean isNumber(String str){
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }
}
