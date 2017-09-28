package com.heepay.manage.common.enums;

/**
 * 描述：
 * <p>
 * 创建者: guozx
 * 创建时间: 2017-06-27 17:29:36
 * 创建描述: 规则形式    "ACCDAY", "按日" "ACWEEK", "按周" "ACMONT", "按月""ACCSEA", "按季 "ACYEAR", "按年" "ACAMOU", "所有余额" "ACOMOU", "超过余额"
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者:
 * 修改时间:
 * 修改内容:
 */
public enum HgmsRuleForms {
    /**
     * 未知
     */
    ACCORDINGDAY("ACCDAY", "按日"),
    ACCORDINGWEEK("ACWEEK", "按周"),
    ACCORDINGMONTH("ACMONT", "按月"),
    ACCORDINGSEASON("ACCSEA", "按季"),
    ACCORDINGYEAR("ACYEAR", "按年"),
    ACCORDINGALLAMOUNTS("ACAMOU", "所有余额"),
    ACCORDINGOVERAMOUNTS("ACOMOU", "超过余额");

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
    HgmsRuleForms(String value, String content) {
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

    public static HgmsRuleForms getBean(String value) {
        for (HgmsRuleForms e : HgmsRuleForms.values()) {
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
