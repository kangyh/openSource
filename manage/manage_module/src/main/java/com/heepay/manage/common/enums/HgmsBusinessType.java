/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.common.enums;

/**
 * 描述：
 * <p>
 * 创建者: yangzd
 * 创建时间: 2017-03-29-16:01
 * 创建描述: 业务类型  "CASHSP 资金归集", "CASHBW","资金下放"
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者:
 * 修改时间:
 * 修改内容:
 */
public enum HgmsBusinessType {
    CASHSP("CASHSP","资金归集"),
    CASHBW("CASHBW","资金下放");

    String _value;
    String _Content;

    HgmsBusinessType(String value, String content) {
        this._value = value;
        this._Content = content;
    }

    public String getValue() {
        return _value;
    }

    public String getContent() {
        return _Content;
    }

    public static HgmsBusinessType getBean(String value) {
        HgmsBusinessType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            HgmsBusinessType e = var1[var3];
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
