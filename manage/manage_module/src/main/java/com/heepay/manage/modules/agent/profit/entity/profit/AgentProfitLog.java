/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.profit.entity.profit;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：分润申请记录Entity
 *
 * 创 建 者： @author shixp
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
public class AgentProfitLog extends DataEntity<AgentProfitLog> {
	
	private static final long serialVersionUID = 1L;
	private Long agentProfitId;		// 分润结算ID（agent_profit表的id）
	private String settleBeginDate;		// 结算开始日期
	private String settleEndDate;		// 结算结束日期
	private String applyTime;		// 申请时间
	private String agentUserId;		// 申请人
	private String invoices;		// 发票信息
	private String express;		// 快递信息
	private String applyRemark;		// 申请备注
	private String status;		// 状态（INICHK初审 RECHEK复审 REJECT审核拒绝 PROFIT分润中 FINISH已完成）
	private String remark;		// 备注（可填拒绝理由）
	private String agentId;		// 代理商编号
	private String profitAmount;		// 分润金额
	private String profitBeginAmount;		//申请开始金额
	private String profitEndAmount;	//申请结束金额
	private String agentCode;		//代理商编号
	private String agentName;		//代理商名称
	private String inichkUserId;	//初审审核人
	private String rechekUserId;	//复审审核人

	public AgentProfitLog() {
		super();
	}

	public AgentProfitLog(String id){
		super(id);
	}

	//@NotNull(message="分润结算ID（agent_profit表的id）不能为空")
	public Long getAgentProfitId() {
		return agentProfitId;
	}

	public void setAgentProfitId(Long agentProfitId) {
		this.agentProfitId = agentProfitId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	//@NotNull(message="申请时间不能为空")
	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	
	@Length(min=1, max=10, message="申请人长度必须介于 1 和 10 之间")
	public String getAgentUserId() {
		return agentUserId;
	}

	public void setAgentUserId(String agentUserId) {
		this.agentUserId = agentUserId;
	}
	
	@Length(min=0, max=50, message="发票信息长度必须介于 0 和 50 之间")
	public String getInvoices() {
		return invoices;
	}

	public void setInvoices(String invoices) {
		this.invoices = invoices;
	}
	
	@Length(min=0, max=50, message="快递信息长度必须介于 0 和 50 之间")
	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}
	
	@Length(min=0, max=50, message="申请备注长度必须介于 0 和 50 之间")
	public String getApplyRemark() {
		return applyRemark;
	}

	public void setApplyRemark(String applyRemark) {
		this.applyRemark = applyRemark;
	}
	
	@Length(min=1, max=6, message="状态（INICHK初审 RECHEK复审 REJECT审核拒绝 PROFIT分润中 FINISH已完成）长度必须介于 1 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=100, message="备注（可填拒绝理由）长度必须介于 0 和 100 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Length(min=0, max=11, message="代理商编号长度必须介于 0 和 11 之间")
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	public String getProfitAmount() {
		return profitAmount;
	}

	public void setProfitAmount(String profitAmount) {
		this.profitAmount = profitAmount;
	}

	public String getSettleBeginDate() {
		return settleBeginDate;
	}

	public void setSettleBeginDate(String settleBeginDate) {
		this.settleBeginDate = settleBeginDate;
	}

	public String getSettleEndDate() {
		return settleEndDate;
	}

	public void setSettleEndDate(String settleEndDate) {
		this.settleEndDate = settleEndDate;
	}

	public String getProfitBeginAmount() {
		return profitBeginAmount;
	}

	public void setProfitBeginAmount(String profitBeginAmount) {
		this.profitBeginAmount = profitBeginAmount;
	}

	public String getProfitEndAmount() {
		return profitEndAmount;
	}

	public void setProfitEndAmount(String profitEndAmount) {
		this.profitEndAmount = profitEndAmount;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getInichkUserId() {
		return inichkUserId;
	}

	public void setInichkUserId(String inichkUserId) {
		this.inichkUserId = inichkUserId;
	}

	public String getRechekUserId() {
		return rechekUserId;
	}

	public void setRechekUserId(String rechekUserId) {
		this.rechekUserId = rechekUserId;
	}
}