/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.common.enums;

/**
 * 描述：
 * <p>
 * 创建者: yangzd
 * 创建时间: 2017-03-25-12:07
 * 创建描述: 交易状态 状态 "SUCCES 付款成功" "FAILED 付款失败" "AUDING 审核中"  "AUDREJ 审核失败"  "PREPAY 待付款"  "PAYING 付款中" "PREAUD 待审核" "PROCES 处理中"  "FINISH 处理完成"  "SVRERR 服务器错误"
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者:
 * 修改时间:
 * 修改内容:
 */
public enum PayTransStatus {
    SUCCESS("SUCCES","成功"),
    FAILED("FAILED","失败"),
//    AUDING("AUDING","审核中"),
//    AUDREJ("AUDREJ","审核失败"),
//    PREPAY("PREPAY","待付款"),
//    PAYING("PAYING","付款中"),
//    PREAUD("PREAUD","待审核"),
    PROCES("PROCES","待处理");
//    FINISH("FINISH","处理完成"),
//    SVRERR("SVRERR","服务器错误");


    String _value;
    String _Content;

    PayTransStatus(String value, String content) {
        this._value = value;
        this._Content = content;
    }

    public String getValue() {
        return _value;
    }

    public String getContent() {
        return _Content;
    }

    public static PayTransStatus getBean(String value) {
        PayTransStatus[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            PayTransStatus e = var1[var3];
            if(value.equals(e.getValue())) {
                return e;
            }
        }
        return null;
    }
    public static String labelOf(String val) {
        return getBean(val) != null?getBean(val).getContent():null;
    }



}
