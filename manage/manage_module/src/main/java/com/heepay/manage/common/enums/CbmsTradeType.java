package com.heepay.manage.common.enums;

/**
 *
 * 描    述：商户贸易类型名称
 *
 * 创 建 者： guozx
 * 创建时间： 2017年1月7日 下午7:41:53
 * 创建描述：CbmsTradeType 商户贸易类型名称  cbms_trade_type_name
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
public enum CbmsTradeType {

    /**
     *枚举常量
     */
    MERCHANDISE("MERCHANDISE", " 货物"),
    STUDYABROAD("STUDYABROAD", "留学"),
    HOTEL("HOTEL", "酒店"),
    TICKET("TICKET", "机票"),
    INTERNATIONALEXHIBITION("INTERNATIONALEXHIBITION", "国际展览"),
    COMMUNICATIONSERVICE("COMMUNICATIONSERVICE", "通信服务");

    String _value;
    /**
     * 存放内容
     */
    String _Content;

    private CbmsTradeType(String value, String content) {
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
    public static CbmsTradeType getBean(String value) {
        CbmsTradeType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CbmsTradeType e = var1[var3];
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
