package com.heepay.manage.common.enums;

/**
 *
 * 描    述：资金归集商户的信用评级
 *
 * 创 建 者： guozx
 * 创建时间： 2017年3月21日 10:44:17
 * 创建描述：    // 信用评级      0、未评级  1、一级    2、二级    3、三级
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
public enum HgmsCreditLevel {

    /**
     *枚举常量
     */
    LEVEL1("0", " 未评级"),
    LEVEL2("1", " 一级"),
    LEVEL3("2", " 二级"),
    LEVEL4("3", "三级");

    String _value;
    /**
     * 存放内容
     */
    String _Content;

    private HgmsCreditLevel(String value, String content) {
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
    public static HgmsCreditLevel getBean(String value) {
        HgmsCreditLevel[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            HgmsCreditLevel e = var1[var3];
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
