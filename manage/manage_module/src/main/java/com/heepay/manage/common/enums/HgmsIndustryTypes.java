package com.heepay.manage.common.enums;

/**
 *
 * 描    述：资金归集商户的事业类型
 *
 * 创 建 者： guozx
 * 创建时间： 2017年3月21日 10:44:17
 * 创建描述：    事业类型     1、通讯行业运营商 2、公共事业缴费类单位 3、保险公司 4、税务机关
 *                          5、学校 6、商业银行 7、烟草公司 8、直销企业 9、大型连锁销售企业
 *                          10、大型代销企业 11、大型生产企业 12、大型物业公司 13、小额信贷公司 14、其他
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
public enum HgmsIndustryTypes {

    /**
     *枚举常量
     */
    COMMUNICATIONS("1", " 通讯行业运营商"),
    UTILITIES("2", "公共事业缴费类单位"),
    INSURANCE("3", " 保险公司"),
    TAX("4", "税务机关"),
    SCHOOL("5", " 学校"),
    COMMERCIALBANK("6", "商业银行"),
    TOBACCO("7", " 烟草公司"),
    DIRECTSELLINGENTERPRISES("8", "直销企业"),
    HEADQUARTERS("9", " 大型连锁销售企业"),
    LARGECHAINSALESENTERPRISE("10", "大型代销企业"),
    LARGESCALEPRODUCTIONENTERPRISE("11", " 大型生产企业"),
    LARGEPROPERTYCOMPANY("12", "大型物业公司"),
    MICROCREDITCOMPANY("13", " 小额信贷公司"),
    OTHERS("14", "其他");

    String _value;
    /**
     * 存放内容
     */
    String _Content;

    private HgmsIndustryTypes(String value, String content) {
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
    public static HgmsIndustryTypes getBean(String value) {
        HgmsIndustryTypes[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            HgmsIndustryTypes e = var1[var3];
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
