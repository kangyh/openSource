/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：支付宝扫码支付Entity
 *
 * 创 建 者： @author tyq
 * 创建时间：
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
public class RefundReport extends DataEntity<RefundReport> {

	private static final long serialVersionUID = 1L;

	//select by merchant
	private Long merchantId;
	private String merchantLoginName;
	private String merchantCompany;
    private String merchantSource;

	//select by channel
	private String payType;
	private String channelPartner;
    private String bankId;
	private String channelCode;

	//select common
	private Integer refundCounts;
	private String totalOriPayAmount;
	private String totalRefundAmount;
	private String totalOriFeeAmount;
	private String totalFeeBackAmount;

	//where
	private String beginCreateTime;
    private String endCreateTime;
    private String sortBy;
    private String orderBy;
    private String groupType;
    private String timePicker;

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantLoginName() {
        return merchantLoginName;
    }

    public void setMerchantLoginName(String merchantLoginName) {
        this.merchantLoginName = merchantLoginName;
    }

    public String getMerchantCompany() {
        return merchantCompany;
    }

    public void setMerchantCompany(String merchantCompany) {
        this.merchantCompany = merchantCompany;
    }

    public String getMerchantSource() {
        return merchantSource;
    }

    public void setMerchantSource(String merchantSource) {
        this.merchantSource = merchantSource;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getChannelPartner() {
        return channelPartner;
    }

    public void setChannelPartner(String channelPartner) {
        this.channelPartner = channelPartner;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Integer getRefundCounts() {
        return refundCounts;
    }

    public void setRefundCounts(Integer refundCounts) {
        this.refundCounts = refundCounts;
    }

    public String getTotalOriPayAmount() {
        return totalOriPayAmount;
    }

    public void setTotalOriPayAmount(String totalOriPayAmount) {
        this.totalOriPayAmount = totalOriPayAmount;
    }

    public String getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(String totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public String getTotalOriFeeAmount() {
        return totalOriFeeAmount;
    }

    public void setTotalOriFeeAmount(String totalOriFeeAmount) {
        this.totalOriFeeAmount = totalOriFeeAmount;
    }

    public String getTotalFeeBackAmount() {
        return totalFeeBackAmount;
    }

    public void setTotalFeeBackAmount(String totalFeeBackAmount) {
        this.totalFeeBackAmount = totalFeeBackAmount;
    }

    public String getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(String beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getTimePicker() {
        return timePicker;
    }

    public void setTimePicker(String timePicker) {
        this.timePicker = timePicker;
    }
}