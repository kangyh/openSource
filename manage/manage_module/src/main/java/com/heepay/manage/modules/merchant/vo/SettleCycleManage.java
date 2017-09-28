/**
 *  
 */
package com.heepay.manage.modules.merchant.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;

/**
 * 结算周期Entity
 * 
 * @author ly
 * @version V1.0
 */
public class SettleCycleManage extends DataEntity<SettleCycleManage> {

    private static final long serialVersionUID = 1L;
    private String merchantId; // 商户id
    private String productCode; // 产品编码
    private String productName; // 产品名称
    private String merchantCompanyName; // 商户公司名称
    private String merchantLoginName; // 商户账号
    private String settleType; // 结算类型
    private BigDecimal minSettlementAmount; // 最小结算金额
    private Date effectiveStartTime; // 有效开始时间
    private Date effectiveEndTime; // 有效截止时间
    private String status; // 配置状态
    private String settlementTo; // 结算至

    public SettleCycleManage() {
        super();
    }

    public SettleCycleManage(String id) {
        super(id);
    }

    @Length(min = 0, max = 11, message = "商户id长度必须介于 0 和 11 之间")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantLoginName() {
        return merchantLoginName;
    }

    public void setMerchantLoginName(String merchantLoginName) {
        this.merchantLoginName = merchantLoginName;
    }

    @Length(min = 0, max = 4, message = "结算类型长度必须介于 0 和 4 之间")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Length(min = 0, max = 4, message = "结算类型长度必须介于 0 和 4 之间")
    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public BigDecimal getMinSettlementAmount() {
        return minSettlementAmount;
    }

    public void setMinSettlementAmount(BigDecimal minSettlementAmount) {
        this.minSettlementAmount = minSettlementAmount;
    }

    @Length(min = 0, max = 6, message = "配置状态长度必须介于 0 和 6 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEffectiveStartTime() {
        return effectiveStartTime;
    }

    public void setEffectiveStartTime(Date effectiveStartTime) {
        this.effectiveStartTime = effectiveStartTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEffectiveEndTime() {
        return effectiveEndTime;
    }

    public void setEffectiveEndTime(Date effectiveEndTime) {
        this.effectiveEndTime = effectiveEndTime;
    }

    public String getMerchantCompanyName() {
        return merchantCompanyName;
    }

    public void setMerchantCompanyName(String merchantCompanyName) {
        this.merchantCompanyName = merchantCompanyName;
    }

    @Length(min = 0, max = 6, message = "配置状态长度必须介于 0 和 6 之间")
    public String getSettlementTo() {
        return settlementTo;
    }

    public void setSettlementTo(String settlementTo) {
        this.settlementTo = settlementTo;
    }

}