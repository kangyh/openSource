/**
 *
 */
package com.heepay.manage.modules.merchant.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;

/**
 * merchantEntity
 *
 * @author ly
 * @version V1.0
 */
public class MerchantRateNewVo extends DataEntity<MerchantRateNewVo> {

    private static final long serialVersionUID = 1L;
    private String rateId;// 费率产品id
    private String merchantId; // 商户ID
    private String merchantCompanyName; // 商户公司名称
    private String merchantLoginName; // 商户账号
    private String businessType; // 业务类型
    private String productCode; // 产品代码
    private String productName; // 产品名称
    private List<String> bankCardType; // 银行卡类型
    private Date ruleBeginTime; // 规则开始时间
    private Date ruleEndTime; // 规则结束时间
    private String chargeSource; // 手续费来源
    private String chargeDeductType; // 手续费扣除方式
    private String chargeCollectionType; // 手续费收取方式
    private String isRefundable; // 退款时是否退还手续费
    private String status; // 规则状态
    private Date beginCreateTime; // 开始 create_time
    private Date endCreateTime; // 结束 create_time
    private String bankNo; // 银行编码
    private String bankName; // 银行名称
    private String returnUrl;// 返回的地址(用于区分商户添加还是详情修改)
    private String rateAudit;// 费率审核(Y:审核通过 N:审核失败: S:未审核)

    // 技术签约
    private String notifyUrl; // 异步通知地址
    private String backUrl; // 同步返回地址
    private String ipDomain;//ip/域名
    private String autographType; // 签名方式
    private String autographKey; // 签名key

    //结算周期
    private String settlementTo; //结算至
    private String settleType;   // 结算类型

    private String chargeTypeSaving; // 计费类型借记卡
    private BigDecimal chargeRatioSaving; // 手续费费用(%)借记卡
    private BigDecimal chargeFeeSaving; // 手续费费用(元)借记卡
    private BigDecimal maxFeeSaving; // 手续费最大值借记卡
    private BigDecimal minFeeSaving; // 手续费最小值借记卡
    private String chargeTypeCredit; // 计费类型贷记卡
    private BigDecimal chargeRatioCredit; // 手续费费用(%)贷记卡
    private BigDecimal chargeFeeCredit; // 手续费费用(元)贷记卡
    private BigDecimal maxFeeCredit; // 手续费最大值贷记卡
    private BigDecimal minFeeCredit; // 手续费最小值贷记卡


    //阶梯费率
    private BigDecimal chargeMaxSavingRatiod; //收费的上限额度，为空或0表示无上限(阶梯1)借记卡
    private BigDecimal chargeMinSavingRatiod;
    ; //收费的下限额度，为空或0表示无下限(阶梯1)借记卡
    private BigDecimal chargeMaxCreditRatiod;
    ; //收费的上限额度，为空或0表示无上限(阶梯1)贷记卡
    private BigDecimal chargeMinCreditRatiod;
    ; //收费的下限额度，为空或0表示无下限(阶梯1)贷记卡
    private BigDecimal chargeMaxSavingCountd; //收费的上限额度，为空或0表示无上限(阶梯1)借记卡
    private BigDecimal chargeMinSavingCountd; //收费的下限额度，为空或0表示无下限(阶梯1)借记卡
    private BigDecimal chargeMaxCreditCountd; //收费的上限额度，为空或0表示无上限(阶梯1)贷记卡
    private BigDecimal chargeMinCreditCountd; //收费的下限额度，为空或0表示无下限(阶梯1)贷记卡

    private BigDecimal chargeRatioSaving2; // 手续费费用(%)比例(阶梯2)借记卡
    private BigDecimal chargeFeeSaving2; // 手续费费用(元)固定(阶梯2)借记卡
    private BigDecimal chargeMaxSavingRatiod2; //收费的上限额度，为空或0表示无上限(阶梯2)借记卡
    private BigDecimal chargeMinSavingRatiod2; //收费的下限额度，为空或0表示无下限(阶梯2)借记卡
    private BigDecimal chargeMaxSavingCountd2; //收费的上限额度，为空或0表示无上限(阶梯2)借记卡
    private BigDecimal chargeMinSavingCountd2; //收费的下限额度，为空或0表示无下限(阶梯2)借记卡
    private BigDecimal chargeRatioSaving3; // 手续费费用(%)比例(阶梯3)借记卡
    private BigDecimal chargeFeeSaving3; // 手续费费用(元)固定(阶梯3)借记卡
    private BigDecimal chargeMaxSavingRatiod3; //收费的上限额度，为空或0表示无上限(阶梯3)借记卡
    private BigDecimal chargeMinSavingRatiod3; //收费的下限额度，为空或0表示无下限(阶梯3)借记卡
    private BigDecimal chargeMaxSavingCountd3; //收费的上限额度，为空或0表示无上限(阶梯3)借记卡
    private BigDecimal chargeMinSavingCountd3; //收费的下限额度，为空或0表示无下限(阶梯3)借记卡

    private BigDecimal chargeRatioCredit2; // 手续费费用(%)比例(阶梯2)贷记卡
    private BigDecimal chargeFeeCredit2; // 手续费费用(元)固定(阶梯2)贷记卡
    private BigDecimal chargeMaxCreditRatiod2; //收费的上限额度，为空或0表示无上限(阶梯2)贷记卡
    private BigDecimal chargeMinCreditRatiod2; //收费的下限额度，为空或0表示无下限(阶梯2)贷记卡
    private BigDecimal chargeMaxCreditCountd2; //收费的上限额度，为空或0表示无上限(阶梯2)贷记卡
    private BigDecimal chargeMinCreditCountd2; //收费的下限额度，为空或0表示无下限(阶梯2)贷记卡
    private BigDecimal chargeRatioCredit3; // 手续费费用(%)比例(阶梯3)贷记卡
    private BigDecimal chargeFeeCredit3; // 手续费费用(元)固定(阶梯3)贷记卡
    private BigDecimal chargeMaxCreditRatiod3; //收费的上限额度，为空或0表示无上限(阶梯3)贷记卡
    private BigDecimal chargeMinCreditRatiod3; //收费的下限额度，为空或0表示无下限(阶梯3)贷记卡
    private BigDecimal chargeMaxCreditCountd3; //收费的上限额度，为空或0表示无上限(阶梯3)贷记卡
    private BigDecimal chargeMinCreditCountd3; //收费的下限额度，为空或0表示无下限(阶梯3)贷记卡


    public MerchantRateNewVo() {
        super();
    }

    public MerchantRateNewVo(String id) {
        super(id);
    }

    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    @Length(min = 0, max = 10, message = "商户ID长度必须介于 0 和 10 之间")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantCompanyName() {
        return merchantCompanyName;
    }

    public void setMerchantCompanyName(String merchantCompanyName) {
        this.merchantCompanyName = merchantCompanyName;
    }

    public String getMerchantLoginName() {
        return merchantLoginName;
    }

    public void setMerchantLoginName(String merchantLoginName) {
        this.merchantLoginName = merchantLoginName;
    }

    @Length(min = 0, max = 6, message = "业务类型长度必须介于 0 和 6 之间")
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @NotNull(message = "产品不能为空")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Length(min = 0, max = 100, message = "产品名称长度必须介于 0 和 100 之间")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @NotNull(message = "卡类型不能为空")
    public List<String> getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(List<String> bankCardType) {
        this.bankCardType = bankCardType;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getRuleBeginTime() {
        return ruleBeginTime;
    }

    public void setRuleBeginTime(Date ruleBeginTime) {
        this.ruleBeginTime = ruleBeginTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getRuleEndTime() {
        return ruleEndTime;
    }

    public void setRuleEndTime(Date ruleEndTime) {
        this.ruleEndTime = ruleEndTime;
    }


    @Length(min = 0, max = 6, message = "手续费来源长度必须介于 0 和 6 之间")
    public String getChargeSource() {
        return chargeSource;
    }

    public void setChargeSource(String chargeSource) {
        this.chargeSource = chargeSource;
    }

    @Length(min = 0, max = 6, message = "手续费扣除方式长度必须介于 0 和 6 之间")
    public String getChargeDeductType() {
        return chargeDeductType;
    }

    public void setChargeDeductType(String chargeDeductType) {
        this.chargeDeductType = chargeDeductType;
    }

    @Length(min = 0, max = 6, message = "手续费收取方式长度必须介于 0 和 6 之间")
    public String getChargeCollectionType() {
        return chargeCollectionType;
    }

    public void setChargeCollectionType(String chargeCollectionType) {
        this.chargeCollectionType = chargeCollectionType;
    }

    @Length(min = 0, max = 6, message = "退款时是否退还手续费长度必须介于 0 和 6 之间")
    public String getIsRefundable() {
        return isRefundable;
    }

    public void setIsRefundable(String isRefundable) {
        this.isRefundable = isRefundable;
    }

    @Length(min = 0, max = 6, message = "规则状态长度必须介于 0 和 6 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(Date beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    @Length(min = 0, max = 3, message = "银行代码长度必须介于 0 和 6 之间")
    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Length(min = 0, max = 512, message = "异步通知地址长度必须介于 0 和 512 之间")
    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    @Length(min = 0, max = 512, message = "同步返回地址长度必须介于 0 和 512 之间")
    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    @Length(min = 0, max = 3, message = "签名方式长度必须介于 0 和 3 之间")
    public String getAutographType() {
        return autographType;
    }

    public void setAutographType(String autographType) {
        this.autographType = autographType;
    }

    @Length(min = 0, max = 512, message = "签名key长度必须介于 0 和 512 之间")
    public String getAutographKey() {
        return autographKey;
    }

    public void setAutographKey(String autographKey) {
        this.autographKey = autographKey;
    }

    @Length(min = 0, max = 6, message = "结算至长度必须介于 0 和 6 之间")
    public String getSettlementTo() {
        return settlementTo;
    }

    public void setSettlementTo(String settlementTo) {
        this.settlementTo = settlementTo;
    }

    @Length(min = 0, max = 1, message = "结算类型长度必须介于 0 和 1 之间")
    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public String getChargeTypeSaving() {
        return chargeTypeSaving;
    }

    public void setChargeTypeSaving(String chargeTypeSaving) {
        this.chargeTypeSaving = chargeTypeSaving;
    }

    public BigDecimal getChargeRatioSaving() {
        return chargeRatioSaving;
    }

    public void setChargeRatioSaving(BigDecimal chargeRatioSaving) {
        this.chargeRatioSaving = chargeRatioSaving;
    }

    public BigDecimal getChargeFeeSaving() {
        return chargeFeeSaving;
    }

    public void setChargeFeeSaving(BigDecimal chargeFeeSaving) {
        this.chargeFeeSaving = chargeFeeSaving;
    }

    public BigDecimal getMaxFeeSaving() {
        return maxFeeSaving;
    }

    public void setMaxFeeSaving(BigDecimal maxFeeSaving) {
        this.maxFeeSaving = maxFeeSaving;
    }

    public BigDecimal getMinFeeSaving() {
        return minFeeSaving;
    }

    public void setMinFeeSaving(BigDecimal minFeeSaving) {
        this.minFeeSaving = minFeeSaving;
    }

    public String getChargeTypeCredit() {
        return chargeTypeCredit;
    }

    public void setChargeTypeCredit(String chargeTypeCredit) {
        this.chargeTypeCredit = chargeTypeCredit;
    }

    public BigDecimal getChargeRatioCredit() {
        return chargeRatioCredit;
    }

    public void setChargeRatioCredit(BigDecimal chargeRatioCredit) {
        this.chargeRatioCredit = chargeRatioCredit;
    }

    public BigDecimal getChargeFeeCredit() {
        return chargeFeeCredit;
    }

    public void setChargeFeeCredit(BigDecimal chargeFeeCredit) {
        this.chargeFeeCredit = chargeFeeCredit;
    }

    public BigDecimal getMaxFeeCredit() {
        return maxFeeCredit;
    }

    public void setMaxFeeCredit(BigDecimal maxFeeCredit) {
        this.maxFeeCredit = maxFeeCredit;
    }

    public BigDecimal getMinFeeCredit() {
        return minFeeCredit;
    }

    public void setMinFeeCredit(BigDecimal minFeeCredit) {
        this.minFeeCredit = minFeeCredit;
    }

    @Length(min = 0, max = 512, message = "ip/域名长度必须介于 0 和 512 之间")
    public String getIpDomain() {
        return ipDomain;
    }

    public void setIpDomain(String ipDomain) {
        this.ipDomain = ipDomain;
    }


    public BigDecimal getChargeMaxSavingRatiod() {
        return chargeMaxSavingRatiod;
    }

    public void setChargeMaxSavingRatiod(BigDecimal chargeMaxSavingRatiod) {
        this.chargeMaxSavingRatiod = chargeMaxSavingRatiod;
    }

    public BigDecimal getChargeMinSavingRatiod() {
        return chargeMinSavingRatiod;
    }

    public void setChargeMinSavingRatiod(BigDecimal chargeMinSavingRatiod) {
        this.chargeMinSavingRatiod = chargeMinSavingRatiod;
    }

    public BigDecimal getChargeMaxCreditRatiod() {
        return chargeMaxCreditRatiod;
    }

    public void setChargeMaxCreditRatiod(BigDecimal chargeMaxCreditRatiod) {
        this.chargeMaxCreditRatiod = chargeMaxCreditRatiod;
    }

    public BigDecimal getChargeMinCreditRatiod() {
        return chargeMinCreditRatiod;
    }

    public void setChargeMinCreditRatiod(BigDecimal chargeMinCreditRatiod) {
        this.chargeMinCreditRatiod = chargeMinCreditRatiod;
    }

    public BigDecimal getChargeMaxSavingCountd() {
        return chargeMaxSavingCountd;
    }

    public void setChargeMaxSavingCountd(BigDecimal chargeMaxSavingCountd) {
        this.chargeMaxSavingCountd = chargeMaxSavingCountd;
    }

    public BigDecimal getChargeMinSavingCountd() {
        return chargeMinSavingCountd;
    }

    public void setChargeMinSavingCountd(BigDecimal chargeMinSavingCountd) {
        this.chargeMinSavingCountd = chargeMinSavingCountd;
    }

    public BigDecimal getChargeMaxCreditCountd() {
        return chargeMaxCreditCountd;
    }

    public void setChargeMaxCreditCountd(BigDecimal chargeMaxCreditCountd) {
        this.chargeMaxCreditCountd = chargeMaxCreditCountd;
    }

    public BigDecimal getChargeMinCreditCountd() {
        return chargeMinCreditCountd;
    }

    public void setChargeMinCreditCountd(BigDecimal chargeMinCreditCountd) {
        this.chargeMinCreditCountd = chargeMinCreditCountd;
    }

    public BigDecimal getChargeRatioSaving2() {
        return chargeRatioSaving2;
    }

    public void setChargeRatioSaving2(BigDecimal chargeRatioSaving2) {
        this.chargeRatioSaving2 = chargeRatioSaving2;
    }

    public BigDecimal getChargeFeeSaving2() {
        return chargeFeeSaving2;
    }

    public void setChargeFeeSaving2(BigDecimal chargeFeeSaving2) {
        this.chargeFeeSaving2 = chargeFeeSaving2;
    }

    public BigDecimal getChargeMaxSavingRatiod2() {
        return chargeMaxSavingRatiod2;
    }

    public void setChargeMaxSavingRatiod2(BigDecimal chargeMaxSavingRatiod2) {
        this.chargeMaxSavingRatiod2 = chargeMaxSavingRatiod2;
    }

    public BigDecimal getChargeMinSavingRatiod2() {
        return chargeMinSavingRatiod2;
    }

    public void setChargeMinSavingRatiod2(BigDecimal chargeMinSavingRatiod2) {
        this.chargeMinSavingRatiod2 = chargeMinSavingRatiod2;
    }

    public BigDecimal getChargeMaxSavingCountd2() {
        return chargeMaxSavingCountd2;
    }

    public void setChargeMaxSavingCountd2(BigDecimal chargeMaxSavingCountd2) {
        this.chargeMaxSavingCountd2 = chargeMaxSavingCountd2;
    }

    public BigDecimal getChargeMinSavingCountd2() {
        return chargeMinSavingCountd2;
    }

    public void setChargeMinSavingCountd2(BigDecimal chargeMinSavingCountd2) {
        this.chargeMinSavingCountd2 = chargeMinSavingCountd2;
    }

    public BigDecimal getChargeRatioSaving3() {
        return chargeRatioSaving3;
    }

    public void setChargeRatioSaving3(BigDecimal chargeRatioSaving3) {
        this.chargeRatioSaving3 = chargeRatioSaving3;
    }

    public BigDecimal getChargeFeeSaving3() {
        return chargeFeeSaving3;
    }

    public void setChargeFeeSaving3(BigDecimal chargeFeeSaving3) {
        this.chargeFeeSaving3 = chargeFeeSaving3;
    }

    public BigDecimal getChargeMaxSavingRatiod3() {
        return chargeMaxSavingRatiod3;
    }

    public void setChargeMaxSavingRatiod3(BigDecimal chargeMaxSavingRatiod3) {
        this.chargeMaxSavingRatiod3 = chargeMaxSavingRatiod3;
    }

    public BigDecimal getChargeMinSavingRatiod3() {
        return chargeMinSavingRatiod3;
    }

    public void setChargeMinSavingRatiod3(BigDecimal chargeMinSavingRatiod3) {
        this.chargeMinSavingRatiod3 = chargeMinSavingRatiod3;
    }

    public BigDecimal getChargeMaxSavingCountd3() {
        return chargeMaxSavingCountd3;
    }

    public void setChargeMaxSavingCountd3(BigDecimal chargeMaxSavingCountd3) {
        this.chargeMaxSavingCountd3 = chargeMaxSavingCountd3;
    }

    public BigDecimal getChargeMinSavingCountd3() {
        return chargeMinSavingCountd3;
    }

    public void setChargeMinSavingCountd3(BigDecimal chargeMinSavingCountd3) {
        this.chargeMinSavingCountd3 = chargeMinSavingCountd3;
    }

    public BigDecimal getChargeRatioCredit2() {
        return chargeRatioCredit2;
    }

    public void setChargeRatioCredit2(BigDecimal chargeRatioCredit2) {
        this.chargeRatioCredit2 = chargeRatioCredit2;
    }

    public BigDecimal getChargeFeeCredit2() {
        return chargeFeeCredit2;
    }

    public void setChargeFeeCredit2(BigDecimal chargeFeeCredit2) {
        this.chargeFeeCredit2 = chargeFeeCredit2;
    }

    public BigDecimal getChargeMaxCreditRatiod2() {
        return chargeMaxCreditRatiod2;
    }

    public void setChargeMaxCreditRatiod2(BigDecimal chargeMaxCreditRatiod2) {
        this.chargeMaxCreditRatiod2 = chargeMaxCreditRatiod2;
    }

    public BigDecimal getChargeMinCreditRatiod2() {
        return chargeMinCreditRatiod2;
    }

    public void setChargeMinCreditRatiod2(BigDecimal chargeMinCreditRatiod2) {
        this.chargeMinCreditRatiod2 = chargeMinCreditRatiod2;
    }

    public BigDecimal getChargeMaxCreditCountd2() {
        return chargeMaxCreditCountd2;
    }

    public void setChargeMaxCreditCountd2(BigDecimal chargeMaxCreditCountd2) {
        this.chargeMaxCreditCountd2 = chargeMaxCreditCountd2;
    }

    public BigDecimal getChargeMinCreditCountd2() {
        return chargeMinCreditCountd2;
    }

    public void setChargeMinCreditCountd2(BigDecimal chargeMinCreditCountd2) {
        this.chargeMinCreditCountd2 = chargeMinCreditCountd2;
    }

    public BigDecimal getChargeRatioCredit3() {
        return chargeRatioCredit3;
    }

    public void setChargeRatioCredit3(BigDecimal chargeRatioCredit3) {
        this.chargeRatioCredit3 = chargeRatioCredit3;
    }

    public BigDecimal getChargeFeeCredit3() {
        return chargeFeeCredit3;
    }

    public void setChargeFeeCredit3(BigDecimal chargeFeeCredit3) {
        this.chargeFeeCredit3 = chargeFeeCredit3;
    }

    public BigDecimal getChargeMaxCreditRatiod3() {
        return chargeMaxCreditRatiod3;
    }

    public void setChargeMaxCreditRatiod3(BigDecimal chargeMaxCreditRatiod3) {
        this.chargeMaxCreditRatiod3 = chargeMaxCreditRatiod3;
    }

    public BigDecimal getChargeMinCreditRatiod3() {
        return chargeMinCreditRatiod3;
    }

    public void setChargeMinCreditRatiod3(BigDecimal chargeMinCreditRatiod3) {
        this.chargeMinCreditRatiod3 = chargeMinCreditRatiod3;
    }

    public BigDecimal getChargeMaxCreditCountd3() {
        return chargeMaxCreditCountd3;
    }

    public void setChargeMaxCreditCountd3(BigDecimal chargeMaxCreditCountd3) {
        this.chargeMaxCreditCountd3 = chargeMaxCreditCountd3;
    }

    public BigDecimal getChargeMinCreditCountd3() {
        return chargeMinCreditCountd3;
    }

    public void setChargeMinCreditCountd3(BigDecimal chargeMinCreditCountd3) {
        this.chargeMinCreditCountd3 = chargeMinCreditCountd3;
    }

    public String getRateAudit() {
        return rateAudit;
    }

    public void setRateAudit(String rateAudit) {
        this.rateAudit = rateAudit;
    }

}
