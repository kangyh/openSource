/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.common.enums;

/**
 * 描述：
 * <p>
 * 创建者: yangzd
 * 创建时间: 2017-03-24-17:44
 * 创建描述: 交易方式 (向目标银行卡或汇付宝账户发起交易)
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者:
 * 修改时间:
 * 修改内容:
 */
public enum TransWay {
    PAYHFB("PAYHFB","向汇付宝账户付款"),
    PAYBNK("PAYBNK","向银行卡付款"),
    CLTHFB("CLTHFB","向汇付宝账户收款"),
    CLTBNK("CLTBNK","向银行卡收款");

    String _value;
    String _Content;

    TransWay(String value, String content) {
        this._value = value;
        this._Content = content;
    }

    public String getValue() {
        return _value;
    }

    public String getContent() {
        return _Content;
    }

    public static TransWay getBean(String value) {
        TransWay[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            TransWay e = var1[var3];
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
