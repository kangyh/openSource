package com.heepay.risk.enums;

public enum BusinessType {
    MERCHANT_REGISTER("MERCHANT_REGISTER","商家注册"),
    MERCHANT_LOGIN("MERCHANT_LOGIN","商家登录"),
    MERCHANT_TRANS("MERCHANT_TRANS","商家交易");
    String _value;


    String _content;


    BusinessType(String value, String content) {
        this._value = value;
        this._content = content;
    }


    public String getValue() {
        return this._value;
    }


    public String getContent() {
        return this._content;
    }
}

