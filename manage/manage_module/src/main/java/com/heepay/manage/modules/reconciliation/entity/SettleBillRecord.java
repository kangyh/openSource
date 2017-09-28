package com.heepay.manage.modules.reconciliation.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;


/***
 * 
* 
* 描    述：账单明细
*
* 创 建 者： wangl
* 创建时间：  8 May 201710:52:04
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
public class SettleBillRecord extends DataEntity<SettleBillRecord>{
    /**
	 * @方法说明：
	 * @时间： 8 May 201710:43:11
	 * @创建人：wangl
	 */
	private static final long serialVersionUID = 1L;

	private Long billDetailId;

    private String channelCode;

    private String channelType;

    private Date saveTime;

    private String paymentId;

    private String channleNo;

    private BigDecimal successAmount;

    private BigDecimal fee;

    private String billStatus;

    private String checkBathNo;

    private BigDecimal promotionAmount;

    private String remark;

    private String isZip;

    private BigDecimal feeService;

    private String field1;

    private String field2;

    private String field3;

    //非映射字段
    private Date beginOperEndTime;

    private Date endOperEndTime;

    private String resultPass;

    public String getResultPass() {
        return resultPass;
    }

    public void setResultPass(String resultPass) {
        this.resultPass = resultPass;
    }

    public Date getBeginOperEndTime() {
        return beginOperEndTime;
    }

    public void setBeginOperEndTime(Date beginOperEndTime) {
        this.beginOperEndTime = beginOperEndTime;
    }

    public Date getEndOperEndTime() {
        return endOperEndTime;
    }

    public void setEndOperEndTime(Date endOperEndTime) {
        this.endOperEndTime = endOperEndTime;
    }

    public Long getBillDetailId() {
        return billDetailId;
    }

    public void setBillDetailId(Long billDetailId) {
        this.billDetailId = billDetailId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType == null ? null : channelType.trim();
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
    }

    public String getChannleNo() {
        return channleNo;
    }

    public void setChannleNo(String channleNo) {
        this.channleNo = channleNo == null ? null : channleNo.trim();
    }

    public BigDecimal getSuccessAmount() {
        return successAmount;
    }

    public void setSuccessAmount(BigDecimal successAmount) {
        this.successAmount = successAmount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus == null ? null : billStatus.trim();
    }

    public String getCheckBathNo() {
        return checkBathNo;
    }

    public void setCheckBathNo(String checkBathNo) {
        this.checkBathNo = checkBathNo == null ? null : checkBathNo.trim();
    }

    public BigDecimal getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(BigDecimal promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsZip() {
        return isZip;
    }

    public void setIsZip(String isZip) {
        this.isZip = isZip == null ? null : isZip.trim();
    }

    public BigDecimal getFeeService() {
        return feeService;
    }

    public void setFeeService(BigDecimal feeService) {
        this.feeService = feeService;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3 == null ? null : field3.trim();
    }
}