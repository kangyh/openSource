package com.heepay.manage.modules.riskChannel.entity;

import com.heepay.risk.RiskChannelLogVo;

/**
 * 名称：风险通道限额统计对象
 * <p>
 * 创建者：yuliang
 * 创建时间：2017-06-29 17:43
 * 创建描述：风险通道限额统计对象
 * <p>
 * 审核者：
 * 审核时间：
 * 审核描述：
 * <p>
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
public class RiskChannelLogQuotaVo extends RiskChannelLogVo {

    //累计单日限额
    private String dayAmount;
    //累计单月限额
    private String monthAmount;

    public String getDayAmount() {
        return dayAmount;
    }

    public void setDayAmount(String dayAmount) {
        this.dayAmount = dayAmount;
    }

    public String getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(String monthAmount) {
        this.monthAmount = monthAmount;
    }
}
