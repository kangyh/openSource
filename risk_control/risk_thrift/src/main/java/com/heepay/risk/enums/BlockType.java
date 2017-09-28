package com.heepay.risk.enums;

public enum BlockType {
    BLOCK("BLOCK","阻断"),
    WARN("WARN","预警");


    String _value;
    String _content;


    BlockType(String value, String content) {
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
