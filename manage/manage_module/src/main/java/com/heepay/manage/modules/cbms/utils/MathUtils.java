/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.utils;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 名称：数学工具类
 *
 * 创建者  郭正新
 * 创建时间 2017年1月9日21:49:24
 * 创建描述：一些数学方面的方法
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
public class MathUtils {
    private MathUtils() {
    }

    /**
     * 获取6位随机数，不重复
     * @return
     */
    public static String randomSix() {
        Random rand = new Random();
        StringBuilder sbf = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sbf.append(rand.nextInt(9));
        }
        return sbf.toString();
    }

    /**
     *生成简单的随机字符串(a-zA-Z0-9)
     * @param length
     * @return
     */
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0 ; i < length; ++i){
            int number = random.nextInt(62);//[0,62)

            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    /**
     *两个值加法计算
     * @param
     * @return
     */
    public static BigDecimal addBigDecimal(String a ,BigDecimal b){
        BigDecimal decimala = new BigDecimal(a);
        b = b.add(decimala);
        return b;
    }

    /**
     *两个值加法计算
     * @param
     * @return
     */
    public static String randstr(int  length){
        String POSSIBLE_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        StringBuffer strbuf = new StringBuffer();
        for (int j = 0; j < length; j++) { //16位随机码
            strbuf.append(POSSIBLE_CHARS.charAt(rand.nextInt(POSSIBLE_CHARS.length())));
        }
        return strbuf.toString();
    }
}
