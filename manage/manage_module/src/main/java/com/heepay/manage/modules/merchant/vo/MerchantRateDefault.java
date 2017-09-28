package com.heepay.manage.modules.merchant.vo;

import java.math.BigDecimal;

/**          
* 
* 描    述：费率默认vo
*
* 创 建 者： ly
* 创建时间： 2016年9月13日 下午3:14:23 
* 创建描述：
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
    
public class MerchantRateDefault {
  private String merchantId;    // 商户ID
  private String productCode;   // 产品代码
  private String bankCardType;    // 银行卡类型
  private String chargeSource;    // 手续费来源
  private String chargeDeductType;    // 手续费扣除方式
  private String chargeCollectionType;    // 手续费收取方式
  private String isRefundable;    // 退款时是否退还手续费
  private String chargeType;    // 计费类型
  private BigDecimal chargeRatio;   // 手续费费用(%)
  private BigDecimal chargeFee;   // 手续费费用(元)
  private BigDecimal maxFee; //手续费最大值
  private BigDecimal minFee; //手续费最小值
  
  public String getMerchantId() {
    return merchantId;
  }
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }
  public String getProductCode() {
    return productCode;
  }
  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }
  public String getBankCardType() {
    return bankCardType;
  }
  public void setBankCardType(String bankCardType) {
    this.bankCardType = bankCardType;
  }
  public String getChargeSource() {
    return chargeSource;
  }
  public void setChargeSource(String chargeSource) {
    this.chargeSource = chargeSource;
  }
  public String getChargeDeductType() {
    return chargeDeductType;
  }
  public void setChargeDeductType(String chargeDeductType) {
    this.chargeDeductType = chargeDeductType;
  }
  public String getChargeCollectionType() {
    return chargeCollectionType;
  }
  public void setChargeCollectionType(String chargeCollectionType) {
    this.chargeCollectionType = chargeCollectionType;
  }
  public String getIsRefundable() {
    return isRefundable;
  }
  public void setIsRefundable(String isRefundable) {
    this.isRefundable = isRefundable;
  }
  public BigDecimal getChargeFee() {
    return chargeFee;
  }
  public void setChargeFee(BigDecimal chargeFee) {
    this.chargeFee = chargeFee;
  }
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
  
}
