package com.heepay.manage.common.enums;

/**
 * Created by NIUJUNPENG on 2017/5/24.
 */
public enum orderStatus {
    /**
     *订单状态
     */
    PENDINGAUDIT("1", "待审核"),
    CUSTOMSBROKER("2", "审核通过"),
    CUSTOMED("3", "已报关"),
    AUDITFAILED("4", "审核拒绝"),
    UNCUSTOMSBROKER("5", "不可审核通过");
    String _value;
    /**
     * 存放内容
     */
    String _Content;

    private orderStatus(String value, String content) {
        this._value = value;
        this._Content = content;
    }

    /**
     * 取得枚举对象值
     *
     * @return 枚举对象值
     */
    public String getValue() {
        return this._value;
    }

    /**
     * 取得内容
     *
     * @return 内容
     */
    public String getContent() {
        return this._Content;
    }

    /**
     * 根据值取得内容
     *
     * @return 内容
     */
    public static orderStatus getBean(String value) {
        orderStatus[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            orderStatus e = var1[var3];
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
