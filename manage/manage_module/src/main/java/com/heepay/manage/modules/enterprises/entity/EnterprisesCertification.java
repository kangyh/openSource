/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.enterprises.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：企业认证对外服务Entity
 *
 * 创 建 者： @author yangcl
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
public class EnterprisesCertification extends DataEntity<EnterprisesCertification> {
	
	private static final long serialVersionUID = 1L;
	private Long merchantId;		// 商户ID
	private String entName;		// 公司名称
	private String entRegNo;		// 工商注册号
	private String repName;		// 法人代表名称
	private String repIdNo;		// 法人代表身份证号
	private String result;		// 认证结果
	private String message;		// 返回信息
	private String channelNo;		// 通道流水号
	private String requestAmount;		// 交易金额
	private String feeAmount;		// 手续费
	private String entNameMatch;		// 公司名称比对结果
	private String entRegNoMatch;		// 工商注册号比对结果
	private String repNameMatch;		// 法定代表人比对结果
	private String repIdNoMatch;		// 法定代表人身份证号码比对结果
	private String channelCode;		// 通道代码
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间
	private String statisticsDate;
	private String groupType;
	
	public EnterprisesCertification() {
		super();
	}

	public EnterprisesCertification(String id){
		super(id);
	}

	@NotNull(message="商户ID不能为空")
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=0, max=256, message="公司名称长度必须介于 0 和 256 之间")
	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}
	
	@Length(min=0, max=128, message="工商注册号长度必须介于 0 和 128 之间")
	public String getEntRegNo() {
		return entRegNo;
	}

	public void setEntRegNo(String entRegNo) {
		this.entRegNo = entRegNo;
	}
	
	@Length(min=0, max=32, message="法人代表名称长度必须介于 0 和 32 之间")
	public String getRepName() {
		return repName;
	}

	public void setRepName(String repName) {
		this.repName = repName;
	}
	
	@Length(min=0, max=32, message="法人代表身份证号长度必须介于 0 和 32 之间")
	public String getRepIdNo() {
		return repIdNo;
	}

	public void setRepIdNo(String repIdNo) {
		this.repIdNo = repIdNo;
	}
	
	@Length(min=0, max=16, message="认证结果长度必须介于 0 和 16 之间")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	@Length(min=0, max=16, message="返回信息长度必须介于 0 和 16 之间")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Length(min=0, max=64, message="通道流水号长度必须介于 0 和 64 之间")
	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	
	public String getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(String requestAmount) {
		this.requestAmount = requestAmount;
	}
	
	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	@Length(min=0, max=64, message="公司名称比对结果长度必须介于 0 和 64 之间")
	public String getEntNameMatch() {
		return entNameMatch;
	}

	public void setEntNameMatch(String entNameMatch) {
		this.entNameMatch = entNameMatch;
	}
	
	@Length(min=0, max=64, message="工商注册号比对结果长度必须介于 0 和 64 之间")
	public String getEntRegNoMatch() {
		return entRegNoMatch;
	}

	public void setEntRegNoMatch(String entRegNoMatch) {
		this.entRegNoMatch = entRegNoMatch;
	}
	
	@Length(min=0, max=64, message="法定代表人比对结果长度必须介于 0 和 64 之间")
	public String getRepNameMatch() {
		return repNameMatch;
	}

	public void setRepNameMatch(String repNameMatch) {
		this.repNameMatch = repNameMatch;
	}
	
	@Length(min=0, max=64, message="法定代表人身份证号码比对结果长度必须介于 0 和 64 之间")
	public String getRepIdNoMatch() {
		return repIdNoMatch;
	}

	public void setRepIdNoMatch(String repIdNoMatch) {
		this.repIdNoMatch = repIdNoMatch;
	}
	
	@Length(min=0, max=64, message="通道代码长度必须介于 0 和 64 之间")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
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
	@NotNull(message="更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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