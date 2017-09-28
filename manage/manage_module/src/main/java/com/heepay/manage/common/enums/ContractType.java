package com.heepay.manage.common.enums;


/**
 * ContractType 签约方式
 *
 * @author ly
 */
public enum ContractType {
    /**
     * 未知
     */
    ONLINE("ONLINE", "线上"),
    OFFLINE("OFLINE", "线下");

    String _value;

    /**
     * 存放内容
     */
    String _Content;

    /**
     * 私有构造函数
     *
     * @param value   枚举值
     * @param content 缓存内容
     * @return
     */
    ContractType(String value, String content) {
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

    public static ContractType getBean(String value) {
        for (ContractType e : ContractType.values()) {
            if (value.equals(e.getValue())) {
                return e;
            }
        }
        return null;
    }

    public static String labelOf(String val) {
        if (getBean(val) != null) {
            return getBean(val).getContent();
        }
        return null;
    }

    //查看是否存在某值
    public static boolean contains(String type) {
        for (ContractType certificateType : ContractType.values()) {
            if (certificateType.name().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
