/**
 *  
 */
package com.heepay.manage.modules.payment.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * 委托收款管理Entity
 * @author ld
 * @version V1.0
 */
public class BatchCollectionRecord extends DataEntity<BatchCollectionRecord> {
	
	private static final long serialVersionUID = 1L;
	private String batchId;		// 批次号
	private Long accountId;		// 账户ID
	private String accountName;		// 账户名称
	private Long merchantId;		// 商户ID
	private String merchantLoginName;		// 商户登录账号
	private String merchantCompany;		// 商户公司
	private String totalAmount;		// 总金额
	private String totalNum;		// 总笔数
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String status;		// 状态
	private String successTotalAmount;		// 总成功金额
	private String successTotalNum;		// 总成功笔数
	private String bankOrderId;		// 批付银行单号
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间
	
	public BatchCollectionRecord() {
		super();
	}

	public BatchCollectionRecord(String id){
		super(id);
	}

	@Length(min=1, max=32, message="批次号长度必须介于 1 和 32 之间")
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
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
	
	@Length(min=0, max=11, message="总笔数长度必须介于 0 和 11 之间")
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
	
	@Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
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
	
	@Length(min=0, max=11, message="总成功笔数长度必须介于 0 和 11 之间")
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
		
}