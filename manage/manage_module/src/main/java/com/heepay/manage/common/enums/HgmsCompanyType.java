package com.heepay.manage.common.enums;

/**
 *
 * 描    述：资金归集商户的账户类型
 *
 * 创 建 者： guozx
 * 创建时间： 2017年3月21日 10:44:17
 * 创建描述：    账户类型     HQ（HEADQUAR）集团总部  DIVISION 分公司
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
public enum HgmsCompanyType {

    /**
     *枚举常量
     */
    HEADQUARTERS("HEADQUAR", " 总部集团"),
    DIVISION("DIVISION", "分公司"),
    PERSON("PERSON", "个人"),
    ENTERPRISE("ENTERP", "商户");

    String _value;
    /**
     * 存放内容
     */
    String _Content;

    private HgmsCompanyType(String value, String content) {
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
    public static HgmsCompanyType getBean(String value) {
        HgmsCompanyType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            HgmsCompanyType e = var1[var3];
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
