/**
\ *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.gatewayaccount.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：网关支付核账Entity
 *
 * 创 建 者： @author 王亚洪
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
public class DayGatewayPayStatForInput extends DataEntity<DayGatewayPayStatForInput> {
	
	private static final long serialVersionUID = 1L;
	private Long recordId;		// 记录Id
	private Date groupTime;		// 汇总当天
	private String payType;		// 支付类型
	private String channelCode;		// 通道代码
	private String channelPartners; //通道合作方
	private String channelPartnersName; //通道合作方名称
	private String bankCode;		// 银行编码
	private String bankName;		// 银行名称
	private String totalBillCount;		// 总单据数
	private String totalTradeAmt;		// 总交易金额
	private String totalRealAmt;		// 总实付金额
	private String successBillCount;		// 总成功单据数
	private String successTradeAmt;		// 总成功交易金额
	private String successRealAmt;		// 总成功实付金额
	private String successSettleAmt;		// 总成功结算金额
	private String refundBillCount;		// 退款单据数
	private String refundRealAmt;		// 退款金额
	private String autoCheckFlag;		// 自动核对标记(0-未知  1-未核对  2-已核对)
	private String autoCheckSuccessAmt;		// 自动核帐的成功金额
	private String autoCheckSuccessCount;		// 自动核帐的成功单据数
	private String autoCheckFailAmt;		// 自动核帐的失败金额
	private String autoCheckFailCount;		// 自动核帐的失败单据数
	private String inputFlag;		// 录入标记(0-未知  1-未录入  2-已录入)
	private String inputCheckSuccessAmt;		// 输入的核帐成功金额
	private String inputCheckSuccessCount;		// 输入的核帐成功单据数
	private String inputCheckFailAmt;		// 输入的核帐失败金额
	private String inputCheckFailCount;		// 输入的核帐失败单据数
	private String isBalance;		// 是否平衡(Y-是   N-否)
	private String inputNote;		// 说明
	private String inputUser;		// 录入人
	private Date inputTime;		// 录入时间
	private String inputSettleAmt;		// 输入的实际结算金额
	private String checkNote;		// 说明
	private String checkUser;		// 审核人
	private Date checkTime;		// 审核时间
	private String status;		// 状态(0=录入中,1=提交录入,2=已复核)
	private String dealMergeFlag;		// 处理合并标记
	private Date createTime;		// 创建时间
	
	private String failCount; //差异笔数
	private String failAmount; //差异金额
	private String realFailAmount; //实际差异金额 
	
	private String beginTime; //统计开始时间
	private String endTime;  //统计结束时间
	
	private String sortOrder;
	
	public DayGatewayPayStatForInput() {
		super();
	}

	public DayGatewayPayStatForInput(String id){
		super(id);
	}

	@NotNull(message="记录Id不能为空")
	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="汇总当天不能为空")
	public Date getGroupTime() {
		return groupTime;
	}

	public void setGroupTime(Date groupTime) {
		this.groupTime = groupTime;
	}
	
	@Length(min=1, max=6, message="支付类型长度必须介于 1 和 6 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=1, max=200, message="通道代码长度必须介于 1 和 200 之间")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	public String getChannelPartners() {
    return channelPartners;
  }

  public void setChannelPartners(String channelPartners) {
    this.channelPartners = channelPartners;
  }

  public String getChannelPartnersName() {
    return channelPartnersName;
  }

  public void setChannelPartnersName(String channelPartnersName) {
    this.channelPartnersName = channelPartnersName;
  }

  @Length(min=1, max=20, message="银行id长度必须介于 1 和 10 之间")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Length(min=1, max=50, message="银行名称长度必须介于 1 和 50 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=1, max=255, message="总单据数长度必须介于 1 和 255 之间")
	public String getTotalBillCount() {
		return totalBillCount;
	}

	public void setTotalBillCount(String totalBillCount) {
		this.totalBillCount = totalBillCount;
	}
	
	public String getTotalTradeAmt() {
		return totalTradeAmt;
	}

	public void setTotalTradeAmt(String totalTradeAmt) {
		this.totalTradeAmt = totalTradeAmt;
	}
	
	public String getTotalRealAmt() {
		return totalRealAmt;
	}

	public void setTotalRealAmt(String totalRealAmt) {
		this.totalRealAmt = totalRealAmt;
	}
	
	@Length(min=1, max=11, message="总成功单据数长度必须介于 1 和 11 之间")
	public String getSuccessBillCount() {
		return successBillCount;
	}

	public void setSuccessBillCount(String successBillCount) {
		this.successBillCount = successBillCount;
	}
	
	public String getSuccessTradeAmt() {
		return successTradeAmt;
	}

	public void setSuccessTradeAmt(String successTradeAmt) {
		this.successTradeAmt = successTradeAmt;
	}
	
	public String getSuccessRealAmt() {
		return successRealAmt;
	}

	public void setSuccessRealAmt(String successRealAmt) {
		this.successRealAmt = successRealAmt;
	}
	
	public String getSuccessSettleAmt() {
		return successSettleAmt;
	}

	public void setSuccessSettleAmt(String successSettleAmt) {
		this.successSettleAmt = successSettleAmt;
	}
	
	@Length(min=1, max=11, message="退款单据数长度必须介于 1 和 11 之间")
	public String getRefundBillCount() {
		return refundBillCount;
	}

	public void setRefundBillCount(String refundBillCount) {
		this.refundBillCount = refundBillCount;
	}
	
	public String getRefundRealAmt() {
		return refundRealAmt;
	}

	public void setRefundRealAmt(String refundRealAmt) {
		this.refundRealAmt = refundRealAmt;
	}
	
	@Length(min=1, max=4, message="自动核对标记(0-未知  1-未核对  2-已核对)长度必须介于 1 和 4 之间")
	public String getAutoCheckFlag() {
		return autoCheckFlag;
	}

	public void setAutoCheckFlag(String autoCheckFlag) {
		this.autoCheckFlag = autoCheckFlag;
	}
	
	public String getAutoCheckSuccessAmt() {
		return autoCheckSuccessAmt;
	}

	public void setAutoCheckSuccessAmt(String autoCheckSuccessAmt) {
		this.autoCheckSuccessAmt = autoCheckSuccessAmt;
	}
	
	@Length(min=1, max=11, message="自动核帐的成功单据数长度必须介于 1 和 11 之间")
	public String getAutoCheckSuccessCount() {
		return autoCheckSuccessCount;
	}

	public void setAutoCheckSuccessCount(String autoCheckSuccessCount) {
		this.autoCheckSuccessCount = autoCheckSuccessCount;
	}
	
	public String getAutoCheckFailAmt() {
		return autoCheckFailAmt;
	}

	public void setAutoCheckFailAmt(String autoCheckFailAmt) {
		this.autoCheckFailAmt = autoCheckFailAmt;
	}
	
	@Length(min=1, max=11, message="自动核帐的失败单据数长度必须介于 1 和 11 之间")
	public String getAutoCheckFailCount() {
		return autoCheckFailCount;
	}

	public void setAutoCheckFailCount(String autoCheckFailCount) {
		this.autoCheckFailCount = autoCheckFailCount;
	}
	
	@Length(min=1, max=4, message="录入标记(0-未知  1-未录入  2-已录入)长度必须介于 1 和 4 之间")
	public String getInputFlag() {
		return inputFlag;
	}

	public void setInputFlag(String inputFlag) {
		this.inputFlag = inputFlag;
	}
	
	public String getInputCheckSuccessAmt() {
		return inputCheckSuccessAmt;
	}

	public void setInputCheckSuccessAmt(String inputCheckSuccessAmt) {
		this.inputCheckSuccessAmt = inputCheckSuccessAmt;
	}
	
	@Length(min=1, max=11, message="输入的核帐成功单据数长度必须介于 1 和 11 之间")
	public String getInputCheckSuccessCount() {
		return inputCheckSuccessCount;
	}

	public void setInputCheckSuccessCount(String inputCheckSuccessCount) {
		this.inputCheckSuccessCount = inputCheckSuccessCount;
	}
	
	public String getInputCheckFailAmt() {
		return inputCheckFailAmt;
	}

	public void setInputCheckFailAmt(String inputCheckFailAmt) {
		this.inputCheckFailAmt = inputCheckFailAmt;
	}
	
	@Length(min=1, max=11, message="输入的核帐失败单据数长度必须介于 1 和 11 之间")
	public String getInputCheckFailCount() {
		return inputCheckFailCount;
	}

	public void setInputCheckFailCount(String inputCheckFailCount) {
		this.inputCheckFailCount = inputCheckFailCount;
	}
	
	@Length(min=0, max=1, message="是否平衡(Y-是   N-否)长度必须介于 0 和 1 之间")
	public String getIsBalance() {
		return isBalance;
	}

	public void setIsBalance(String isBalance) {
		this.isBalance = isBalance;
	}
	
	@Length(min=0, max=255, message="说明长度必须介于 0 和 255 之间")
	public String getInputNote() {
		return inputNote;
	}

	public void setInputNote(String inputNote) {
		this.inputNote = inputNote;
	}
	
	@Length(min=0, max=20, message="录入人长度必须介于 0 和 20 之间")
	public String getInputUser() {
		return inputUser;
	}

	public void setInputUser(String inputUser) {
		this.inputUser = inputUser;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	
	public String getInputSettleAmt() {
		return inputSettleAmt;
	}

	public void setInputSettleAmt(String inputSettleAmt) {
		this.inputSettleAmt = inputSettleAmt;
	}
	
	@Length(min=0, max=255, message="说明长度必须介于 0 和 255 之间")
	public String getCheckNote() {
		return checkNote;
	}

	public void setCheckNote(String checkNote) {
		this.checkNote = checkNote;
	}
	
	@Length(min=0, max=20, message="审核人长度必须介于 0 和 20 之间")
	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
	@Length(min=0, max=4, message="状态(0=录入中,1=提交录入,2=已复核)长度必须介于 0 和 4 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=4, message="处理合并标记长度必须介于 0 和 4 之间")
	public String getDealMergeFlag() {
		return dealMergeFlag;
	}

	public void setDealMergeFlag(String dealMergeFlag) {
		this.dealMergeFlag = dealMergeFlag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

  public String getFailCount() {
    return failCount;
  }

  public void setFailCount(String failCount) {
    this.failCount = failCount;
  }

  public String getFailAmount() {
    return failAmount;
  }

  public void setFailAmount(String failAmount) {
    this.failAmount = failAmount;
  }

  public String getRealFailAmount() {
    return realFailAmount;
  }

  public void setRealFailAmount(String realFailAmount) {
    this.realFailAmount = realFailAmount;
  }

  public String getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(String beginTime) {
    this.beginTime = beginTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }
	
}