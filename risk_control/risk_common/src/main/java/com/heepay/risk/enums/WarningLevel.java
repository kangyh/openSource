package com.heepay.risk.enums;

public enum WarningLevel {
    NO("No", "不报警"),
    NORMAL("Normal", "一般"),
    FATAL("Fatal", "严重");

    String  _value;

    /**
     * 存放内容
     */
    String _Content;

    /**
     * 私有构造函数
     * @param value 枚举值
     * @param content 缓存内容
     * @return
     */
    WarningLevel(String value, String content) {
        this._value = value;
        this._Content = content;
    }
    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public String getValue() {
        return this._value;
    }

    /**
     * 取得内容
     * @return 内容
     */
    public String getContent() {
        return this._Content;
    }

}
