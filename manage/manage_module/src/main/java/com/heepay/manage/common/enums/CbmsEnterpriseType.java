package com.heepay.manage.common.enums;

/**
 *
 * 描    述：商户企业类别名称
 *
 * 创 建 者： guozx
 * 创建时间： 2017年1月7日 下午7:41:53
 * 创建描述：INDIVIDUALUSER、个人用户  ELECTRICENTERPRISE、电子商务企业
 *          ELECTRICBUSINESSENTERPRISE、电子商务交易平台企业  LOGISTICSENTERPRISES、物流企业
 *          ELECTRICBUSINESSPLATFORM、电子商务通关服务平台
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
public enum CbmsEnterpriseType {

    /**
     *枚举常量
     */
    INDIVIDUALUSER("INDIVIDUALUSER", " 个人用户"),
    ELECTRICENTERPRISE("ELECTRICENTERPRISE", "电子商务企业"),
    ELECTRICBUSINESSENTERPRISE("ELECTRICBUSINESSENTERPRISE", "电子商务交易平台企业"),
    LOGISTICSENTERPRISES("LOGISTICSENTERPRISES", "物流企业"),
    ELECTRICBUSINESSPLATFORM("ELECTRICBUSINESSPLATFORM", "电子商务通关服务平台");
//    CBMSSUPPLIERCATEGORYNAME("CBMSSUPPLIERCATEGORYNAME", "商户企业类别名称");

    String _value;
    /**
     * 存放内容
     */
    String _Content;

    private CbmsEnterpriseType(String value, String content) {
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
    public static CbmsEnterpriseType getBean(String value) {
        CbmsEnterpriseType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CbmsEnterpriseType e = var1[var3];
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
