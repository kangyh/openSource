package com.heepay.manage.modules.pbc.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;

public class PbcMeasureInfo extends DataEntity<PbcMeasureInfo>{
    /**
	 * 2016年12月24日
	 */
	private static final long serialVersionUID = -2629569831581942295L;

	private Long pbcId;

    private String freezeSerial;

    private String paymentAccountNumber;

    private String measureType;

    private Date measureStartTime;

    private Date measureEndTime;

    private String measureDeptName;

    private String currency;

    private BigDecimal measureBalance;

    private String remark;

    private String applicationCode;

    public Long getPbcId() {
        return pbcId;
    }

    public void setPbcId(Long pbcId) {
        this.pbcId = pbcId;
    }

    public String getFreezeSerial() {
        return freezeSerial;
    }

    public void setFreezeSerial(String freezeSerial) {
        this.freezeSerial = freezeSerial == null ? null : freezeSerial.trim();
    }

    public String getPaymentAccountNumber() {
        return paymentAccountNumber;
    }

    public void setPaymentAccountNumber(String paymentAccountNumber) {
        this.paymentAccountNumber = paymentAccountNumber == null ? null : paymentAccountNumber.trim();
    }

    public String getMeasureType() {
        return measureType;
    }

    public void setMeasureType(String measureType) {
        this.measureType = measureType == null ? null : measureType.trim();
    }

    public Date getMeasureStartTime() {
        return measureStartTime;
    }

    public void setMeasureStartTime(Date measureStartTime) {
        this.measureStartTime = measureStartTime;
    }

    public Date getMeasureEndTime() {
        return measureEndTime;
    }

    public void setMeasureEndTime(Date measureEndTime) {
        this.measureEndTime = measureEndTime;
    }

    public String getMeasureDeptName() {
        return measureDeptName;
    }

    public void setMeasureDeptName(String measureDeptName) {
        this.measureDeptName = measureDeptName == null ? null : measureDeptName.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public BigDecimal getMeasureBalance() {
        return measureBalance;
    }

    public void setMeasureBalance(BigDecimal measureBalance) {
        this.measureBalance = measureBalance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode == null ? null : applicationCode.trim();
    }
}