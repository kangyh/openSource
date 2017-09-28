package com.heepay.manage.common.enums;

/**
 *
 * 描    述：资金归集商户的风险类型
 *
 * 创 建 者： guozx
 * 创建时间： 2017年3月21日 10:44:17
 * 创建描述：    // 风险类型   1、低风险---LOW  2、一般风险---GENERAL 3、中等风险--MEDIUM 4、高等风险--HIGH
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
public enum HgmsRiskLevel {

    /**
     *枚举常量
     */
    LOW("1", " 低风险"),
    GENERAL("2", " 一般风险"),
    MEDIUM("3", " 中等风险"),
    HIGH("4", " 高等风险");

    String _value;
    /**
     * 存放内容
     */
    String _Content;

    private HgmsRiskLevel(String value, String content) {
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
    public static HgmsRiskLevel getBean(String value) {
        HgmsRiskLevel[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            HgmsRiskLevel e = var1[var3];
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
