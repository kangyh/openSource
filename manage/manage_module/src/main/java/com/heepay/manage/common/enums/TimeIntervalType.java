package com.heepay.manage.common.enums;

/**
 *
 * 描    述商户添加时间区间类型
 *
 * 创 建 者： niujp
 * 创建时间： 2017年7月24日
 * 创建描述：述商户添加时间区间类型
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
public enum TimeIntervalType {

    /**
     *枚举常量
     */
    ONEDAY("1天", "1"),
    SERVERDAY("7天", "7"),
    FIFTEEN("15天", "15"),
    OTHER("其他", "其他");

    String _value;
    /**
     * 存放内容
     */
    String _Content;

    private TimeIntervalType(String value, String content) {
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
    public static TimeIntervalType getBean(String value) {
        TimeIntervalType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            TimeIntervalType e = var1[var3];
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
