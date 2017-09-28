/**
 *  
 */
package com.heepay.manage.modules.payment.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;

/**
 * 
* 
* 描    述：异常处理Entity
*
* 创 建 者： yanxb
* 创建时间： 2016年11月14日 上午10:59:50 
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
public class ExceptionRecord extends DataEntity<ExceptionRecord> {
	
	/**  描述   (@author: Administrator) */      
	    
	private static final long serialVersionUID = -3994540559476993857L;
	
	private String exceptionId;		// 异常ID
	private Long merchantId;		// 商户id
	private String merchantLoginName;		// 商户登录账号
	private String merchantCompany;		// 商户公司
	private Long accountId;		// 账户ID
	private String accountName;		// 账户名称
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String exceptionAmount;		// 金额
	private String status;		// 状态 
	private String paymentId;		// 支付ID
	private String bankcardNo;		// 银行卡号
	private String transType;		// 交易类型
	private String operator;		// 处理人
	private Date processTime;		// 处理时间
	private String processDesc;		// 处理备注
	public long complainProcessId; // required
    public String transNo; // 交易订单号
    public String transAmount; // 交易金额
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间
	private String statisticsDate;
	private String groupType;

    private String resetOperator;
    private Date resetTime;
    private String resetDesc;

	public ExceptionRecord() {
		super();
	}

	public ExceptionRecord(String id){
		super(id);
	}

	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}

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

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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

	public String getExceptionAmount() {
		return exceptionAmount;
	}

	public void setExceptionAmount(String exceptionAmount) {
		this.exceptionAmount = exceptionAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getBankcardNo() {
		return bankcardNo;
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public String getProcessDesc() {
		return processDesc;
	}

	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}

	public long getComplainProcessId() {
		return complainProcessId;
	}

	public void setComplainProcessId(long complainProcessId) {
		this.complainProcessId = complainProcessId;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(String transAmount) {
		this.transAmount = transAmount;
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

	public String getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(String statisticsDate) {
		this.statisticsDate = statisticsDate;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

    public String getResetOperator() {
        return resetOperator;
    }

    public void setResetOperator(String resetOperator) {
        this.resetOperator = resetOperator;
    }

    public Date getResetTime() {
        return resetTime;
    }

    public void setResetTime(Date resetTime) {
        this.resetTime = resetTime;
    }

    public String getResetDesc() {
        return resetDesc;
    }

    public void setResetDesc(String resetDesc) {
        this.resetDesc = resetDesc;
    }
}