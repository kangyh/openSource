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
 * 创建描述: 定时任务执行状态 状态 "SUCCES 付款成功" "FAILED 付款失败""PROCES 处理中"
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者: guozx@9186.com
 * 修改时间: 2017-07-31 09:57:29
 * 修改内容: 由hgms_web复制
 */
public enum HgmsTaskStatus {
    SUCCESS("SUCCES","成功"),
    FAILED("FAILED","失败"),
    PROCES("PROCES","待处理");


    String _value;
    String _Content;

    HgmsTaskStatus(String value, String content) {
        this._value = value;
        this._Content = content;
    }

    public String getValue() {
        return _value;
    }

    public String getContent() {
        return _Content;
    }

    public static HgmsTaskStatus getBean(String value) {
        HgmsTaskStatus[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            HgmsTaskStatus e = var1[var3];
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
