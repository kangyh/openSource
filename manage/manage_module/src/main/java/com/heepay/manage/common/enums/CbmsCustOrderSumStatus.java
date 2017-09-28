package com.heepay.manage.common.enums;

/**
 *
 * 描    述：通关申报订单汇总表--申报状态
 *
 * 创 建 者： guozx
 * 创建时间： 2017年1月3日 下午7:41:53
 * 创建描述：报送海关状态：     1,待报送 2. 报送处理中  3. 已报送
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
public enum CbmsCustOrderSumStatus {

    /**
     *枚举常量
     */
    SUCCESS("1", "待报送"),
    SUBMITTEDPROCESSING("2", "报送处理中"),
    SUCCESSSUBMITTED("3", "已报送"),
    FAILEDSUBMITTED("4", "已报送");

    String _value;
    /**
     * 存放内容
     */
    String _Content;

    private CbmsCustOrderSumStatus(String value, String content) {
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
    public static CbmsCustOrderSumStatus getBean(String value) {
        CbmsCustOrderSumStatus[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            CbmsCustOrderSumStatus e = var1[var3];
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
