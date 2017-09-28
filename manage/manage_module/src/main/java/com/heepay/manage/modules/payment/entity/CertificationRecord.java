/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：实名认证Entity
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
public class CertificationRecord extends DataEntity<CertificationRecord> {
	
	private static final long serialVersionUID = 1L;
	private String certificationId;		// 交易订单号

	//private String paymentId;		// 支付ID
	private Long merchantId;		// 商户编码
	private String merchantLoginName;		// 商户登录账号
	private String merchantCompany;		// 商户公司名称
	private String certificationName;		// 认证人姓名
	private String certificationIdcard;		// 认证人身份证号
	private Long accountId;		// 账户ID
	private String accountName;		// 账户名称
	private String feeAmount;		// 手续费金额(实名认证服务费)
	private String feeType;		// 手续费收取方式
	private String status;		// 认证状态
	private Date createTime;		// 创建时间
	private Date returnTime;		// 结果返回时间
	private String remark;		// 备注
	private String sortOrder;
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间
	private String statisticsDate;

	public CertificationRecord() {
		super();
	}

	public CertificationRecord(String id){
		super(id);
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

	@Length(min=1, max=18, message="交易订单号长度必须介于 1 和 18 之间")
	public String getCertificationId() {
		return certificationId;
	}

	public void setCertificationId(String certificationId) {
		this.certificationId = certificationId;
	}
		@NotNull(message="商户编码不能为空")
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
	
	@Length(min=0, max=100, message="商户公司名称长度必须介于 0 和 100 之间")
	public String getMerchantCompany() {
		return merchantCompany;
	}

	public void setMerchantCompany(String merchantCompany) {
		this.merchantCompany = merchantCompany;
	}
	
	@Length(min=1, max=64, message="认证人姓名长度必须介于 1 和 64 之间")
	public String getCertificationName() {
		return certificationName;
	}

	public void setCertificationName(String certificationName) {
		this.certificationName = certificationName;
	}
	
	@Length(min=1, max=64, message="认证人身份证号长度必须介于 1 和 64 之间")
	public String getCertificationIdcard() {
		if(certificationIdcard==null){
			return "";
		}else{
			String idcard = Aes.decryptStr(certificationIdcard, Constants.QuickPay.SYSTEM_KEY);
			
			String stars = idcard.substring(7, 13).replaceAll(".", "*");
			return idcard.substring(0, 6) + stars + idcard.substring(14, idcard.length());
		}
	}

	public void setCertificationIdcard(String certificationIdcard) {
		this.certificationIdcard = certificationIdcard;
	}
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	
	@Length(min=0, max=64, message="账户名称长度必须介于 0 和 64 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	@Length(min=0, max=6, message="手续费收取方式长度必须介于 0 和 6 之间")
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	@Length(min=0, max=6, message="认证状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结果返回时间不能为空")
	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	
	@Length(min=0, max=200, message="备注长度必须介于 0 和 200 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(String statisticsDate) {
		this.statisticsDate = statisticsDate;
	}
}