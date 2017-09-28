/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：批量代收Entity
 *
 * 创 建 者： @author 杨春龙
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
public class BatchCollectionRecordDetail extends DataEntity<BatchCollectionRecordDetail> {
	
	private static final long serialVersionUID = 1L;
	private String collectId;		// 代收明细ID
	private Long merchantId;		// 商户ID
	private String merchantUserId;		// 用户id
	private Date createTime;		// 申请时间
	private Date updateTime;		// 修改时间
	private String collectAmount;		// 收款金额
	private String bankcardNo;		// 银行卡号
	private String bankcardOwnerMobile;		// 银行预留手机号
	private String bankcardOwnerName;		// 持卡人姓名
	private String bankcardOwnerIdcard;		// 持卡人身份证号
	private String bankName;		// 银行名称
	private String bankId;		// 银行id
	private String merchantBatchNo;		// 商户批次号
	private String merchantOrderNo;		// 商户订单号
	private String status;		// 状态
	private Date successTime;		// 成功时间
	private String successAmount;		// 成功金额
	private String feeAmount;		// 手续费
	private Date beginCreateTime;		// 开始 申请时间
	private Date endCreateTime;		// 结束 申请时间
	private String statisticsDate;
	private String groupType;
	
	public BatchCollectionRecordDetail() {
		super();
	}

	public BatchCollectionRecordDetail(String id){
		super(id);
	}

	@Length(min=1, max=20, message="代收明细ID长度必须介于 1 和 20 之间")
	public String getCollectId() {
		return collectId;
	}

	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}
	
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=64, message="用户id长度必须介于 0 和 64 之间")
	public String getMerchantUserId() {
		return merchantUserId;
	}

	public void setMerchantUserId(String merchantUserId) {
		this.merchantUserId = merchantUserId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="申请时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="修改时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getCollectAmount() {
		return collectAmount;
	}

	public void setCollectAmount(String collectAmount) {
		this.collectAmount = collectAmount;
	}
	
	@Length(min=0, max=256, message="银行卡号长度必须介于 0 和 256 之间")
	public String getBankcardNo() {
		String cardNo = "";
		if(StringUtils.isNotBlank(bankcardNo)){
			cardNo= Aes.decryptStr(bankcardNo, Constants.QuickPay.SYSTEM_KEY);
		}else{
			return "";
		}
		cardNo=cardNo.substring(0, 6)+"******"+cardNo.substring(cardNo.length()-4);
		return cardNo;
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}
	
	@Length(min=0, max=256, message="银行预留手机号长度必须介于 0 和 256 之间")
	public String getBankcardOwnerMobile() {
		String mobile = "";
		if(StringUtils.isNotBlank(bankcardOwnerMobile)){
			mobile= Aes.decryptStr(bankcardOwnerMobile, Constants.QuickPay.SYSTEM_KEY);
		}else{
			return "";
		}
		mobile=mobile.substring(0, 3)+"******"+mobile.substring(mobile.length()-4);
		return mobile;
	}

	public void setBankcardOwnerMobile(String bankcardOwnerMobile) {
		this.bankcardOwnerMobile = bankcardOwnerMobile;
	}
	
	@Length(min=0, max=64, message="持卡人姓名长度必须介于 0 和 64 之间")
	public String getBankcardOwnerName() {
		return bankcardOwnerName;
	}

	public void setBankcardOwnerName(String bankcardOwnerName) {
		this.bankcardOwnerName = bankcardOwnerName;
	}
	
	@Length(min=0, max=256, message="持卡人身份证号长度必须介于 0 和 256 之间")
	public String getBankcardOwnerIdcard() {
		String idcard = "";
		if(StringUtils.isNotBlank(bankcardOwnerIdcard)){
			idcard= Aes.decryptStr(bankcardOwnerIdcard, Constants.QuickPay.SYSTEM_KEY);
		}else{
			return "";
		}
		idcard=idcard.substring(0, 3)+"******"+idcard.substring(idcard.length()-4);
		return idcard;
	}

	public void setBankcardOwnerIdcard(String bankcardOwnerIdcard) {
		this.bankcardOwnerIdcard = bankcardOwnerIdcard;
	}
	
	@Length(min=0, max=128, message="银行名称长度必须介于 0 和 128 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=20, message="银行id长度必须介于 0 和 20 之间")
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	@Length(min=0, max=64, message="商户批次号长度必须介于 0 和 64 之间")
	public String getMerchantBatchNo() {
		return merchantBatchNo;
	}

	public void setMerchantBatchNo(String merchantBatchNo) {
		this.merchantBatchNo = merchantBatchNo;
	}
	
	@Length(min=0, max=64, message="商户订单号长度必须介于 0 和 64 之间")
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	@Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	
	public String getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
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
}