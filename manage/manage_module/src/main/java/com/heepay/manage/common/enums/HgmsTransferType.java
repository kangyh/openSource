package com.heepay.manage.common.enums;

/**
 *
 * 描    述：转账类型
 *
 * 创 建 者： niujp
 * 创建时间： 2017年4月7日
 * 创建描述：
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
public enum HgmsTransferType {

    /**
     *枚举常量
     */
    CHTOSP("CHTOSP", " 现金账户到资金归集"),
    SPTOCH("SPTOCH", "资金归集到现金账户");




    String _value;
    /**
     * 存放内容
     */
    String _Content;

    private HgmsTransferType(String value, String content) {
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
    public static HgmsTransferType getBean(String value) {
        HgmsTransferType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            HgmsTransferType e = var1[var3];
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
