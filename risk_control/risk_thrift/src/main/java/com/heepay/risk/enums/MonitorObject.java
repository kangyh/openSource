package com.heepay.risk.enums;

public enum MonitorObject {
    MERCHANT("MERCHANT","商户"),
    USER("USER","用户");


    String _value;
    String _content;


    MonitorObject(String value, String content) {
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
