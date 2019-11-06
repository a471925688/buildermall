package com.noah_solutions.util;

import com.github.houbb.opencc4j.util.ZhConverterUtil;

/**
 * 簡體繁體轉換
 */
public class ZJFConverterDemo {

    public static void main(String[] args) {
        System.out.println("'澳门'的繁体是：" + TraToSim("澳门"));
        System.out.println("'澳门'的简体是：" + SimToTra("澳门"));
    }

    /**
     * 简体转繁体
     *
     * @param simpStr
     *            简体字符串
     * @return 繁体字符串
     */
    public static String SimToTra(String simpStr) {
        return ZhConverterUtil.convertToSimple(simpStr);
    }

    /**
     * 繁体转简体
     *
     * @param tradStr
     *            繁体字符串
     * @return 简体字符串
     */
    public static String TraToSim(String tradStr) {
        return ZhConverterUtil.convertToTraditional(tradStr);
    }

}