package com.heepay.manage.modules.cbms.utils;

/**
 *
 * 描    述：数据处理工具
 *
 * 创 建 者： @author guozx
 * 创建时间： 2017年1月16日 09:51:25
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
public class DataprocessUtils {

    /**
     * 将为字符转换串字符串数组
     * 1,2,3
     * [1,2,3]
     * @param value
     * @return
     */
    public static String[] convertStrToArray(String value) {

        if(value == null || value.length() == 0) {
            return new String[]{""};
        }

        return value.split(",");
    }

    /**
     * 将字符串数组转换为字符串
     * [1,2,3]
     * 1,2,3
     * @param value
     * @return String
     */
    public static String convertArrToStr(String[] value) {

        if(value == null || value.length == 0) {
            return "";
        }
        //可变的字符串常量提高字符串的读写速度
        StringBuilder builder = new StringBuilder();
        for (String val : value) {
            builder.append(val).append(",");
        }
        //经过处理得到1,2,3,
        String temp = builder.toString();
        //去除字符串最后的一个字符
        return removeLast(temp);
    }

    /**
     * 去除字符串最后的一个字符
     * @param source
     * @return
     */
    public static String removeLast(String source) {

        if(source == null || source.length() == 0) {
            return source;
        }

        return source.substring(0, source.length()-1);
    }

}
