/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.entity.profit;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：代理商分润管理Entity
 *
 * 创 建 者： @author yangliang
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
public class AgentProfit extends DataEntity<AgentProfit> {
	
	private static final long serialVersionUID = 1L;
	private String profitDate;	//分润日期
	private String agentId;		// 代理商id
	private String agentCode;		// 代理商编号
	private String agentName;		// 代理商名称
	private String merchantNum;	//所属商户数量
	private String settleBeginDate;		// 结算开始日期
	private String settleEndDate;		// 结算结束日期
	private String transTotalCount;		// 交易总笔数
	private String transTotalAmount;		// 交易总金额
	private String profitTotalAmount;		// 分润总额
	private String feeTotalAmount;			//手续费总金额
	private String applyAmount;		// 已申请分润金额
	private String recvAmount;		// 已领分润金额
	private String avaiApplyAmount;		// 可申请分润金额
	private String payStatus;				//财务处理状态
	private String remark;		// 备注
	private String beginSettleBeginDate;		// 开始 结算开始日期
	private String endSettleBeginDate;		// 结束 结算开始日期
	private String beginSettleEndDate;		// 开始 结算结束日期
	private String endSettleEndDate;		// 结束 结算结束日期
	private String profitDateBegin;		//开始结算日期
	private String profitDateEnd;		//结束结算日期
	
	public AgentProfit() {
		super();
	}

	public AgentProfit(String id){
		super(id);
	}

	@Length(min=1, max=10, message="代理商编号长度必须介于 1 和 10 之间")
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	@Length(min=1, max=50, message="代理商名称长度必须介于 1 和 50 之间")
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	@Length(min=0, max=10, message="结算开始日期长度必须介于 0 和 10 之间")
	public String getSettleBeginDate() {
		return settleBeginDate;
	}

	public void setSettleBeginDate(String settleBeginDate) {
		this.settleBeginDate = settleBeginDate;
	}
	
	@Length(min=0, max=10, message="结算结束日期长度必须介于 0 和 10 之间")
	public String getSettleEndDate() {
		return settleEndDate;
	}

	public void setSettleEndDate(String settleEndDate) {
		this.settleEndDate = settleEndDate;
	}
	
	@Length(min=1, max=10, message="交易总笔数长度必须介于 1 和 10 之间")
	public String getTransTotalCount() {
		return transTotalCount;
	}

	public void setTransTotalCount(String transTotalCount) {
		this.transTotalCount = transTotalCount;
	}
	
	public String getTransTotalAmount() {
		return transTotalAmount;
	}

	public void setTransTotalAmount(String transTotalAmount) {
		this.transTotalAmount = transTotalAmount;
	}
	
	public String getProfitTotalAmount() {
		return profitTotalAmount;
	}

	public void setProfitTotalAmount(String profitTotalAmount) {
		this.profitTotalAmount = profitTotalAmount;
	}
	
	public String getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
	}
	
	public String getRecvAmount() {
		return recvAmount;
	}

	public void setRecvAmount(String recvAmount) {
		this.recvAmount = recvAmount;
	}
	
	public String getAvaiApplyAmount() {
		return avaiApplyAmount;
	}

	public void setAvaiApplyAmount(String avaiApplyAmount) {
		this.avaiApplyAmount = avaiApplyAmount;
	}
	
	@Length(min=1, max=50, message="备注长度必须介于 1 和 50 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getBeginSettleBeginDate() {
		return beginSettleBeginDate;
	}

	public void setBeginSettleBeginDate(String beginSettleBeginDate) {
		this.beginSettleBeginDate = beginSettleBeginDate;
	}
	
	public String getEndSettleBeginDate() {
		return endSettleBeginDate;
	}

	public void setEndSettleBeginDate(String endSettleBeginDate) {
		this.endSettleBeginDate = endSettleBeginDate;
	}
		
	public String getBeginSettleEndDate() {
		return beginSettleEndDate;
	}

	public void setBeginSettleEndDate(String beginSettleEndDate) {
		this.beginSettleEndDate = beginSettleEndDate;
	}
	
	public String getEndSettleEndDate() {
		return endSettleEndDate;
	}

	public void setEndSettleEndDate(String endSettleEndDate) {
		this.endSettleEndDate = endSettleEndDate;
	}

	public String getProfitDate() {
		return profitDate;
	}

	public void setProfitDate(String profitDate) {
		this.profitDate = profitDate;
	}

	public String getMerchantNum() {
		return merchantNum;
	}

	public void setMerchantNum(String merchantNum) {
		this.merchantNum = merchantNum;
	}

	public String getProfitDateBegin() {
		return profitDateBegin;
	}

	public void setProfitDateBegin(String profitDateBegin) {
		this.profitDateBegin = profitDateBegin;
	}

	public String getProfitDateEnd() {
		return profitDateEnd;
	}

	public void setProfitDateEnd(String profitDateEnd) {
		this.profitDateEnd = profitDateEnd;
	}

	public String getFeeTotalAmount() {
		return feeTotalAmount;
	}

	public void setFeeTotalAmount(String feeTotalAmount) {
		this.feeTotalAmount = feeTotalAmount;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
}