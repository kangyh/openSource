/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.common.enums;

/**
 * 描述：
 * <p>
 * 创建者: guozx@9186.com
 * 创建时间: 2017-03-29-16:01
 * 创建描述: 订单来源-"AUTOTM","自动生成" "LAUNMA","手动执行"
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者: guozx@9186.com
 * 修改时间: 2017-07-26 09:34:54
 * 修改内容: 同步web端的对应枚举类
 */
public enum HgmsOrderSource {
    AUTOTIMING("AUTOTM", "自动生成"),
    LAUNCHMANUAL("LAUNMA", "手动执行");

    String _value;
    String _Content;

    HgmsOrderSource(String value, String content) {
        this._value = value;
        this._Content = content;
    }

    public String getValue() {
        return _value;
    }

    public String getContent() {
        return _Content;
    }

    public static HgmsOrderSource getBean(String value) {
        HgmsOrderSource[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            HgmsOrderSource e = var1[var3];
            if (value.equals(e.getValue())) {
                return e;
            }
        }
        return null;
    }

    public static String labelOf(String val) {
        return getBean(val) != null ? getBean(val).getContent() : null;
    }
}
