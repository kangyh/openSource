package com.heepay.manage.common.enums;

/**
 * 支付通道合作方
 * @author liumeng
 */
public enum CertifyChannelPartner {

    /**
     * 未知
     */
    //POLICE("POLICE","公安部"),
    //TOIPO("TOIPOD","中盛");
    HEEPAY("HEEPAY","汇付宝"),
    QAREDQ("QAREDQ","岂安"),
    LAKALA("LAKALA","考拉征信"),
    JIXIN("JIXIN","吉信");

    String _value;

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
    CertifyChannelPartner(String value, String content) {
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
    
    public static CertifyChannelPartner getBean(String value) {
      for (CertifyChannelPartner e : CertifyChannelPartner.values()) {
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
    
}
