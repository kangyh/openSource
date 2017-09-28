package com.heepay.manage.common.enums;

/**
 *
 * 描    述：商户企业类型名称  cbms_enterprise_type_name
 *
 * 创 建 者： guozx
 * 创建时间： 2017年1月7日 下午7:41:53
 * 创建描述： 商户企业类型名称
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
public enum CbmsSupplierCategory {

    /**
     *枚举常量
     */
    GENERALTRADE("GENERALTRADE", " 一般贸易区"),
    FREETRADEAREANOREGULATION("FREETRADEAREANOREGULATION", "自由贸易试验区（非特殊监管）"),
    FREETRADEAREAREGULATION("FREETRADEAREAREGULATION", "自由贸易试验区（特殊监管）"),
    BONDEDAREA("BONDEDAREA", "保税区"),
    EXPORTPROCESSINGZONE("EXPORTPROCESSINGZONE", "出口加工区"),
    BOUNDEDWAREHOUSEA("BOUNDEDWAREHOUSEA", "保税物流中心A型"),
    BOUNDEDWAREHOUSEB("BOUNDEDWAREHOUSEB", "保税物流中心B型"),
    BONDEDLOGISTICSPARK("BONDEDLOGISTICSPARK", "保税物流园区"),
    DIAMONDBOURSE("DIAMONDBOURSE", "钻石交易所"),
    BONDEDPORTAREA("BONDEDPORTAREA", "保税港区"),
    COMPREHENSIVEBONDEDZONE("COMPREHENSIVEBONDEDZONE", "综合保税区"),
    CROSSBORDERINDUSTRIALPARK("CROSSBORDERINDUSTRIALPARK", "跨境工业园区"),
    EXPORTSUPERVISEDWAREHOUSE("EXPORTSUPERVISEDWAREHOUSE", "出口监管仓库"),
    IMPORTSUPERVISEDWAREHOUSE("IMPORTSUPERVISEDWAREHOUSE", "进口保税仓库");

    String _value;
    /**
     * 存放内容
     */
    String _Content;

    private CbmsSupplierCategory(String value, String content) {
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
    public static CbmsSupplierCategory getBean(String value) {
        CbmsSupplierCategory[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CbmsSupplierCategory e = var1[var3];
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
