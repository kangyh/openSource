package com.heepay.manage.common.enums;

/**
 * 描述：规则的建立方式
 * 
 * 创建者: guozx@9186.com
 * 创建时间: 2017-06-27 17:29:36
 * 创建描述: 结算状态    "BULDBM", "后台建立"	 "BULDBU", "商户建立" "BULDNL", "不限定"
 * 
 * 审核者:
 * 审核时间:
 * 审核描述:
 * 
 * 修改者:
 * 修改时间:
 * 修改内容:
 */
public enum HgmsRuleBuildType {
    /**
     * 未知
     */
    BUILDBYMANAGE("BULDBM", "后台建立"),
    BUILDBYUSER("BULDBU", "商户建立"),
    BUILDNOLIMIT("BULDNL", "不限定");

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
    HgmsRuleBuildType(String value, String content) {
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

    public static HgmsRuleBuildType getBean(String value) {
        for (HgmsRuleBuildType e : HgmsRuleBuildType.values()) {
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
