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
 * 创建描述: 任务类型-“CREATE” = 生成订单 “EXCUTE” = 执行订单
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者: guozx@9186.com
 * 修改时间: 2017-07-26 09:34:54
 * 修改内容: 同步web端的对应枚举类
 */
public enum HgmsOrderTaskType {
    CREATE("CREATE","生成订单"),
    EXCUTE("EXCUTE","执行订单");

    String _value;
    String _Content;

    HgmsOrderTaskType(String value, String content) {
        this._value = value;
        this._Content = content;
    }

    public String getValue() {
        return _value;
    }

    public String getContent() {
        return _Content;
    }

    public static HgmsOrderTaskType getBean(String value) {
        HgmsOrderTaskType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            HgmsOrderTaskType e = var1[var3];
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
