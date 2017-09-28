package com.heepay.manage.common.enums;

/**
 *
 * 描    述：合同的启用和禁用
 *
 * 创 建 者： guozx
 * 创建时间： 2017年3月21日 10:44:17
 * 创建描述：     合同的启用和禁用 NORMAL("NORMAL", "正常"), FREEZED("FREZED", "冻结"), CLOSED("CLOSED", "关闭"), UNKNOWNED("UNKNOW", "未知");
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
public enum HgmsContractStatus {

    /**
     *枚举常量
     */
    NORMAL("NORMAL", "正常"),
    FREEZED("FREZED", "冻结"),
    EXPIRE("EXPIRE", "过期"),
    UNKNOWNED("UNKNOW", "未知");

    String _value;
    /**
     * 存放内容
     */
    String _Content;

    private HgmsContractStatus(String value, String content) {
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
    public static HgmsContractStatus getBean(String value) {
        HgmsContractStatus[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            HgmsContractStatus e = var1[var3];
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
