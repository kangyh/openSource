package com.heepay.risk.enums;

public enum DateQueryType {
    NOW("Now", "当前"),
    PREFIRST("Prefirst", "前一次"),
    PRESECOND("Presecond", "前两次"),
    YESTERDAY("Yesterday", "昨天"),
    BEFOREYESTERDAY("BeforeYesterday", "前天");
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
    DateQueryType(String value, String content) {
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
