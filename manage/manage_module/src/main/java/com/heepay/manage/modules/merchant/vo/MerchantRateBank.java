/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.vo;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描 述：商户费率银行Entity
 *
 * 创 建 者： @author ly 创建时间： 创建描述：
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者： 审核时间： 审核描述：
 *
 */
public class MerchantRateBank extends DataEntity<MerchantRateBank> {

    private static final long serialVersionUID = 1L;
    private String rateId; // 费率id
    private String bankNo; // 银行id
    private String bankName; // 银行名称
    private String bankCardType; // 银行卡类型
    private String chargeType; // 计费类型
    private BigDecimal chargeRatio; // 手续费费用(%)比例
    private BigDecimal chargeFee; // 手续费费用(元)固定
    private BigDecimal maxFee; // 手续费比例最大金额
    private BigDecimal minFee; // 手续费比例最小金额
    private BigDecimal chargeMax; //收费的上限额度，为空或0表示无上限(阶梯1)
    private BigDecimal chargeMin; //收费的下限额度，为空或0表示无下限(阶梯1)
    private BigDecimal chargeRatio2; // 手续费费用(%)比例(阶梯2)
    private BigDecimal chargeFee2; // 手续费费用(元)固定(阶梯2)
    private BigDecimal chargeMax2; //收费的上限额度，为空或0表示无上限(阶梯2)
    private BigDecimal chargeMin2; //收费的下限额度，为空或0表示无下限(阶梯2)
    private BigDecimal chargeRatio3; // 手续费费用(%)比例(阶梯3)
    private BigDecimal chargeFee3; // 手续费费用(元)固定(阶梯3)
    private BigDecimal chargeMax3; //收费的上限额度，为空或0表示无上限(阶梯3)
    private BigDecimal chargeMin3; //收费的下限额度，为空或0表示无下限(阶梯3)


    public MerchantRateBank() {
        super();
    }

    public MerchantRateBank(String id) {
        super(id);
    }

    @Length(min = 0, max = 11, message = "费率id长度必须介于 0 和 11 之间")
    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    @Length(min = 0, max = 3, message = "银行id长度必须介于 0 和 3 之间")
    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    @Length(min = 0, max = 100, message = "银行名称长度必须介于 0 和 100 之间")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Length(min = 0, max = 6, message = "银行卡类型长度必须介于 0 和 6 之间")
    public String getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(String bankCardType) {
        this.bankCardType = bankCardType;
    }

    @Length(min = 0, max = 6, message = "计费类型长度必须介于 0 和 6 之间")
    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public BigDecimal getChargeRatio() {
        return chargeRatio;
    }

    public void setChargeRatio(BigDecimal chargeRatio) {
        this.chargeRatio = chargeRatio;
    }

    public BigDecimal getChargeFee() {
        return chargeFee;
    }

    public void setChargeFee(BigDecimal chargeFee) {
        this.chargeFee = chargeFee;
    }

    public BigDecimal getMaxFee() {
        return maxFee;
    }

    public void setMaxFee(BigDecimal maxFee) {
        this.maxFee = maxFee;
    }

    public BigDecimal getMinFee() {
        return minFee;
    }

    public void setMinFee(BigDecimal minFee) {
        this.minFee = minFee;
    }

    public BigDecimal getChargeMax() {
        return chargeMax;
    }

    public void setChargeMax(BigDecimal chargeMax) {
        this.chargeMax = chargeMax;
    }

    public BigDecimal getChargeMin() {
        return chargeMin;
    }

    public void setChargeMin(BigDecimal chargeMin) {
        this.chargeMin = chargeMin;
    }

    public BigDecimal getChargeRatio2() {
        return chargeRatio2;
    }

    public void setChargeRatio2(BigDecimal chargeRatio2) {
        this.chargeRatio2 = chargeRatio2;
    }

    public BigDecimal getChargeFee2() {
        return chargeFee2;
    }

    public void setChargeFee2(BigDecimal chargeFee2) {
        this.chargeFee2 = chargeFee2;
    }

    public BigDecimal getChargeMax2() {
        return chargeMax2;
    }

    public void setChargeMax2(BigDecimal chargeMax2) {
        this.chargeMax2 = chargeMax2;
    }

    public BigDecimal getChargeMin2() {
        return chargeMin2;
    }

    public void setChargeMin2(BigDecimal chargeMin2) {
        this.chargeMin2 = chargeMin2;
    }

    public BigDecimal getChargeRatio3() {
        return chargeRatio3;
    }

    public void setChargeRatio3(BigDecimal chargeRatio3) {
        this.chargeRatio3 = chargeRatio3;
    }

    public BigDecimal getChargeFee3() {
        return chargeFee3;
    }

    public void setChargeFee3(BigDecimal chargeFee3) {
        this.chargeFee3 = chargeFee3;
    }

    public BigDecimal getChargeMax3() {
        return chargeMax3;
    }

    public void setChargeMax3(BigDecimal chargeMax3) {
        this.chargeMax3 = chargeMax3;
    }

    public BigDecimal getChargeMin3() {
        return chargeMin3;
    }

    public void setChargeMin3(BigDecimal chargeMin3) {
        this.chargeMin3 = chargeMin3;
    }
}