/**
 *  
 */
package com.heepay.manage.modules.payment.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtil;
import com.heepay.manage.common.persistence.DataEntity;

/**
 * 
* 
* 描    述：打款认证实体
*
* 创 建 者： yanxb  
* 创建时间： 2016年10月28日 下午1:54:48 
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
public class QualificationRecord extends DataEntity<QualificationRecord> {
	
	private static final long serialVersionUID = 1L;
	private String qualifyId;		// 打款认证ID
	private String paymentId;		// 支付ID
	private Long merchantId;		// 商户ID
	private String merchantLoginName;		// 商户登录账号
	private String merchantName;		// 商户公司
	private String payAmount;		// 批付总金额
	private Date createTime;		// 创建时间
	private Date updateTime;		// update_time
	private String status;		// 状态     0=批付申请中 1=批付审核通过付款中 2=批付全部处理成功 3=付款失败 4=批复审核不通过
	private String successAmount;		// 批付成功总金额
	private String bankcardNo;		// 银行卡号
	private String bankId;		// 银行ID
	private String bankName;		// 银行名称
	private String bankcardOwnerType;		// 银行卡持卡人类型
	private String openbankName;		// 开户行名称
	private String associateLineNumber;		// 联行号
	private String bankcardType;		// 银行卡类型
	private String exceptionStatus;		// 异常状态
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间
	private String sortOrder; //升降排序
	private String statisticsDate;
	
	public String getQualifyId() {
		return qualifyId;
	}
	public void setQualifyId(String qualifyId) {
		this.qualifyId = qualifyId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
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
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSuccessAmount() {
		return successAmount;
	}
	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}
	
	@Length(min=0, max=256, message="银行卡号长度必须介于 0 和 256 之间")
	public String getBankcardNo() {
		if(bankcardNo==null){
			return null;
		}
		String card = Aes.decryptStr(bankcardNo, Constants.QuickPay.SYSTEM_KEY);
		return StringUtil.getEncryptedCardNo(card);
	}
	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankcardOwnerType() {
		return bankcardOwnerType;
	}
	public void setBankcardOwnerType(String bankcardOwnerType) {
		this.bankcardOwnerType = bankcardOwnerType;
	}
	public String getOpenbankName() {
		return openbankName;
	}
	public void setOpenbankName(String openbankName) {
		this.openbankName = openbankName;
	}
	public String getAssociateLineNumber() {
		return associateLineNumber;
	}
	public void setAssociateLineNumber(String associateLineNumber) {
		this.associateLineNumber = associateLineNumber;
	}
	public String getBankcardType() {
		return bankcardType;
	}
	public void setBankcardType(String bankcardType) {
		this.bankcardType = bankcardType;
	}
	public String getExceptionStatus() {
		return exceptionStatus;
	}
	public void setExceptionStatus(String exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
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
	public String getStatisticsDate() {
		return statisticsDate;
	}
	public void setStatisticsDate(String statisticsDate) {
		this.statisticsDate = statisticsDate;
	}
}