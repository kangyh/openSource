/**
 *  
 */
package com.heepay.manage.modules.payment.entity;

import java.util.Date;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;

/**
 * 批量付款管理Entity
 * @author zjx
 * @version V1.0
 */
public class BatchPayRecord extends DataEntity<BatchPayRecord> {
	
	private static final long serialVersionUID = 1L;
	private String batchId;		// 批次号
	private String merchantBatchNo;     // 商户批次号
	private Long accountId;		// 账户ID
	private String accountName;		// 账户名称
	private Long merchantId;		// 商户ID
	private String merchantLoginName;		// 商户登录账号
	private String merchantCompany;		// 商户公司
	private String totalAmount;		// 批付总金额
	private String totalNum;		// 批付总笔数
	private Date createTime;		// 创建时间
	private Date updateTime;		// update_time
	private String status;		// 状态     0=批付申请中 1=批付审核通过付款中 2=批付全部处理成功 3=付款失败 4=批复审核不通过
	private String successTotalAmount;		// 批付成功总金额
	private String successTotalNum;		// 批付成功总笔数
	private String transferFrom;	 // 转账来源
	private String bankOrderId;		// 批付银行单号
	private String feeType;		// 手续费方式
	private String feeRatio;		// 手续费费率
	private String feeAmount;		// 手续费金额
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间

	private String sortOrder; //升降排序
	private Date intoaccountTime; //银行服务类型
	private String statisticsDate;

	public BatchPayRecord() {
		super();
	}

	public BatchPayRecord(String id){
		super(id);
	}

	@Length(min=1, max=32, message="批次号长度必须介于 1 和 32 之间")
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	@NotNull(message="账户ID不能为空")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=256, message="账户名称长度必须介于 0 和 256 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@NotNull(message="商户ID不能为空")
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="商户登录账号长度必须介于 0 和 64 之间")
	public String getMerchantLoginName() {
		return merchantLoginName;
	}

	public void setMerchantLoginName(String merchantLoginName) {
		this.merchantLoginName = merchantLoginName;
	}
	
	@Length(min=0, max=64, message="商户公司长度必须介于 0 和 64 之间")
	public String getMerchantCompany() {
		return merchantCompany;
	}

	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	@Length(min=0, max=11, message="批付总笔数长度必须介于 0 和 11 之间")
	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=6, message="状态     0=批付申请中 1=批付审核通过付款中 2=批付全部处理成功 3=付款失败 4=批复审核不通过长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getSuccessTotalAmount() {
		return successTotalAmount;
	}

	public void setSuccessTotalAmount(String successTotalAmount) {
		this.successTotalAmount = successTotalAmount;
	}
	
	@Length(min=0, max=11, message="批付成功总笔数长度必须介于 0 和 11 之间")
	public String getSuccessTotalNum() {
		return successTotalNum;
	}

	public void setSuccessTotalNum(String successTotalNum) {
		this.successTotalNum = successTotalNum;
	}
	
	@Length(min=0, max=11, message="批付银行单号长度必须介于 0 和 11 之间")
	public String getBankOrderId() {
		return bankOrderId;
	}

	public void setBankOrderId(String bankOrderId) {
		this.bankOrderId = bankOrderId;
	}
	
	@Length(min=0, max=10, message="手续费方式长度必须介于 0 和 10 之间")
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	public String getFeeRatio() {
		return feeRatio;
	}

	public void setFeeRatio(String feeRatio) {
		this.feeRatio = feeRatio;
	}
	
	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
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

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Date getIntoaccountTime() {
		return intoaccountTime;
	}

	public void setIntoaccountTime(Date intoaccountTime) {
		this.intoaccountTime = intoaccountTime;
	}

	public String getMerchantBatchNo() {
		return merchantBatchNo;
	}

	public void setMerchantBatchNo(String merchantBatchNo) {
		this.merchantBatchNo = merchantBatchNo;
	}

	public String getTransferFrom() {
		return transferFrom;
	}

	public void setTransferFrom(String transferFrom) {
		this.transferFrom = transferFrom;
	}

	public String getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(String statisticsDate) {
		this.statisticsDate = statisticsDate;
	}
}