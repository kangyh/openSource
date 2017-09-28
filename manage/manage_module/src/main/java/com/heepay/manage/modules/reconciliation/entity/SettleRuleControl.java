package com.heepay.manage.modules.reconciliation.entity;

import com.heepay.manage.common.persistence.DataEntity;

import java.util.Date;


/***
 *
 *
 * 描    述：对账规则表
 *
 * 创 建 者： wangl
 * 创建时间：  2017年1月17日下午4:00:26
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
public class SettleRuleControl extends DataEntity<SettleRuleControl> {

	private static final long serialVersionUID = 1L;

	private Long ruleControlId;

	private String channelCode;

	private String channelType;

	private String channelTypeRule;//用于页面显示 非映射字段

	private String channelName;

	private String billType;

	private Integer paymentId;

	private Integer channelNo;

	private Integer costAmount;

	private Integer successAmount;

	private String splitFlg;

	private Integer beginRowNum;

	private String endFlg;

	private String status;

	private String settleWay;//对账方式

	private Integer promotionAmount;// 优惠金额的位置

	private Integer transStatus; //交易状态的位置

	//保存业务类型编码
	private String loadChannelType;

	private Date createTime;

	private String createAuthor;

	private Date updateTime;

	private String updateAuthor;

	//添加规则非映射字段
	//支付单号
	private String paymentIdName;
	//规则
	private String paymentIdRule;

	//银行流水号
	private String channelNoName;
	//规则
	private String channelNoRule;


	//交易成本
	private String costAmountName;
	//规则
	private String costAmountRule;


	//通道金额
	private String successAmountName;
	//规则
	private String successAmountRule;


	//优惠金额
	private String promotionAmountName;
	//规则
	private String promotionAmountRule;


	//交易状态
	private String transStatusName;
	//规则
	private String transStatusRule;


	public String getChannelTypeRule() {
		return channelTypeRule;
	}

	public void setChannelTypeRule(String channelTypeRule) {
		this.channelTypeRule = channelTypeRule;
	}

	public Long getRuleControlId() {
		return ruleControlId;
	}

	public void setRuleControlId(Long ruleControlId) {
		this.ruleControlId = ruleControlId;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Integer getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(Integer channelNo) {
		this.channelNo = channelNo;
	}

	public Integer getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(Integer costAmount) {
		this.costAmount = costAmount;
	}

	public Integer getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(Integer successAmount) {
		this.successAmount = successAmount;
	}

	public String getSplitFlg() {
		return splitFlg;
	}

	public void setSplitFlg(String splitFlg) {
		this.splitFlg = splitFlg;
	}

	public Integer getBeginRowNum() {
		return beginRowNum;
	}

	public void setBeginRowNum(Integer beginRowNum) {
		this.beginRowNum = beginRowNum;
	}

	public String getEndFlg() {
		return endFlg;
	}

	public void setEndFlg(String endFlg) {
		this.endFlg = endFlg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSettleWay() {
		return settleWay;
	}

	public void setSettleWay(String settleWay) {
		this.settleWay = settleWay;
	}

	public Integer getPromotionAmount() {
		return promotionAmount;
	}

	public void setPromotionAmount(Integer promotionAmount) {
		this.promotionAmount = promotionAmount;
	}

	public Integer getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(Integer transStatus) {
		this.transStatus = transStatus;
	}

	public String getLoadChannelType() {
		return loadChannelType;
	}

	public void setLoadChannelType(String loadChannelType) {
		this.loadChannelType = loadChannelType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateAuthor() {
		return createAuthor;
	}

	public void setCreateAuthor(String createAuthor) {
		this.createAuthor = createAuthor;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}

	public String getPaymentIdName() {
		return paymentIdName;
	}

	public void setPaymentIdName(String paymentIdName) {
		this.paymentIdName = paymentIdName;
	}

	public String getPaymentIdRule() {
		return paymentIdRule;
	}

	public void setPaymentIdRule(String paymentIdRule) {
		this.paymentIdRule = paymentIdRule;
	}

	public String getChannelNoName() {
		return channelNoName;
	}

	public void setChannelNoName(String channelNoName) {
		this.channelNoName = channelNoName;
	}

	public String getChannelNoRule() {
		return channelNoRule;
	}

	public void setChannelNoRule(String channelNoRule) {
		this.channelNoRule = channelNoRule;
	}

	public String getCostAmountName() {
		return costAmountName;
	}

	public void setCostAmountName(String costAmountName) {
		this.costAmountName = costAmountName;
	}

	public String getCostAmountRule() {
		return costAmountRule;
	}

	public void setCostAmountRule(String costAmountRule) {
		this.costAmountRule = costAmountRule;
	}

	public String getSuccessAmountName() {
		return successAmountName;
	}

	public void setSuccessAmountName(String successAmountName) {
		this.successAmountName = successAmountName;
	}

	public String getSuccessAmountRule() {
		return successAmountRule;
	}

	public void setSuccessAmountRule(String successAmountRule) {
		this.successAmountRule = successAmountRule;
	}

	public String getPromotionAmountName() {
		return promotionAmountName;
	}

	public void setPromotionAmountName(String promotionAmountName) {
		this.promotionAmountName = promotionAmountName;
	}

	public String getPromotionAmountRule() {
		return promotionAmountRule;
	}

	public void setPromotionAmountRule(String promotionAmountRule) {
		this.promotionAmountRule = promotionAmountRule;
	}

	public String getTransStatusName() {
		return transStatusName;
	}

	public void setTransStatusName(String transStatusName) {
		this.transStatusName = transStatusName;
	}

	public String getTransStatusRule() {
		return transStatusRule;
	}

	public void setTransStatusRule(String transStatusRule) {
		this.transStatusRule = transStatusRule;
	}
}