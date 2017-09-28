/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：跨境通知表数据查询Entity
 *
 * 创 建 者： @author 牛俊鹏
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
public class CbmsNofityRecord extends DataEntity<CbmsNofityRecord> {
	
	private static final long serialVersionUID = 1L;
	private Long notifyId;		// 通知ID
	private String merchantId;		// 商户订单号
	private String merchantBatchNo;		// 商户批次号
	private String importBatchNo;		// 导入批次号
	private String notifyUrl;		// 通知URL地址
	private String status;		// 通知状态,
	private String notifyRequestParams;		// 请求参数
	private String notifyResponse;		// 返回结果
	private String notifyNumber;		// 通知次数  5分钟一次，通知5次
	private String notifyType;		// 通知类型       1=手动通知2=自动通知
	private Date notifyTime;		// 通知时间
	private Date updateTime;		// 更新时间
	private Date createTime;		// 创建时间
	
	public CbmsNofityRecord() {
		super();
	}

	public CbmsNofityRecord(String id){
		super(id);
	}

	@NotNull(message="通知ID不能为空")
	public Long getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(Long notifyId) {
		this.notifyId = notifyId;
	}
	
	@Length(min=0, max=20, message="商户订单号长度必须介于 0 和 20 之间")
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=32, message="商户批次号长度必须介于 0 和 32 之间")
	public String getMerchantBatchNo() {
		return merchantBatchNo;
	}

	public void setMerchantBatchNo(String merchantBatchNo) {
		this.merchantBatchNo = merchantBatchNo;
	}
	
	@Length(min=0, max=32, message="导入批次号长度必须介于 0 和 32 之间")
	public String getImportBatchNo() {
		return importBatchNo;
	}

	public void setImportBatchNo(String importBatchNo) {
		this.importBatchNo = importBatchNo;
	}
	
	@Length(min=0, max=512, message="通知URL地址长度必须介于 0 和 512 之间")
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	@Length(min=0, max=6, message="通知状态,长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=5000, message="请求参数长度必须介于 0 和 5000 之间")
	public String getNotifyRequestParams() {
		return notifyRequestParams;
	}

	public void setNotifyRequestParams(String notifyRequestParams) {
		this.notifyRequestParams = notifyRequestParams;
	}
	
	@Length(min=0, max=5000, message="返回结果长度必须介于 0 和 5000 之间")
	public String getNotifyResponse() {
		return notifyResponse;
	}

	public void setNotifyResponse(String notifyResponse) {
		this.notifyResponse = notifyResponse;
	}
	
	@Length(min=0, max=2, message="通知次数  5分钟一次，通知5次长度必须介于 0 和 2 之间")
	public String getNotifyNumber() {
		return notifyNumber;
	}

	public void setNotifyNumber(String notifyNumber) {
		this.notifyNumber = notifyNumber;
	}
	
	@Length(min=0, max=2, message="通知类型       1=手动通知2=自动通知长度必须介于 0 和 2 之间")
	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}