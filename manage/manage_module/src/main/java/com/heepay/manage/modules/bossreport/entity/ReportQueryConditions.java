/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.bossreport.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：报表条件设置Entity
 *
 * 创 建 者： @author wangdong
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
public class ReportQueryConditions extends DataEntity<ReportQueryConditions> {
	
	private static final long serialVersionUID = 1L;
	private Long reportId;		// 主键
	private String payType;		// 支付类型
	private String payTypeName;		// 支付类型名称
	private String bankProvider;		// 银行支付提供者
	private String bankProviderName;		// 银行支付提供者名称
	private String bankId;		// 银行ID
	private String bankName;		// 银行名称
	private String bankNameRe;		// 银行名称(复)
	private String payTypeJava;		// java支付类型
	private String payTypeNameJava;		// java支付类型名称
	private String bankIdJava;		// java银行ID
	private String bankNameJava;		// java银行名称
	private String channelPartnerJava;		// java通道合作方
	private String channelPartnerNameJava;		// java通道合作方名称
	private String companyId;		// 公司ID|结算主体
	private String companyName;		// 公司名称|结算主体
	private String creator;		// 创建者
	private Date createTime;		// 创建时间
	private String updateAuthor;		// 更新者
	private Date updateTime;		// 更新时间
	private String status;		// 状态
	
	public ReportQueryConditions() {
		super();
	}

	public ReportQueryConditions(String id){
		super(id);
	}

	@NotNull(message="主键不能为空")
	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
	@Length(min=0, max=10, message="支付类型长度必须介于 0 和 10 之间")
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Length(min=0, max=50, message="支付类型名称长度必须介于 0 和 50 之间")
	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}
	
	@Length(min=0, max=50, message="银行支付提供者长度必须介于 0 和 50 之间")
	public String getBankProvider() {
		return bankProvider;
	}

	public void setBankProvider(String bankProvider) {
		this.bankProvider = bankProvider;
	}
	
	@Length(min=0, max=50, message="银行支付提供者名称长度必须介于 0 和 50 之间")
	public String getBankProviderName() {
		return bankProviderName;
	}

	public void setBankProviderName(String bankProviderName) {
		this.bankProviderName = bankProviderName;
	}
	
	@Length(min=0, max=10, message="银行ID长度必须介于 0 和 10 之间")
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	@Length(min=0, max=50, message="银行名称长度必须介于 0 和 50 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=50, message="银行名称(复)长度必须介于 0 和 50 之间")
	public String getBankNameRe() {
		return bankNameRe;
	}

	public void setBankNameRe(String bankNameRe) {
		this.bankNameRe = bankNameRe;
	}
	
	@Length(min=0, max=10, message="java支付类型长度必须介于 0 和 10 之间")
	public String getPayTypeJava() {
		return payTypeJava;
	}

	public void setPayTypeJava(String payTypeJava) {
		this.payTypeJava = payTypeJava;
	}
	
	@Length(min=0, max=50, message="java支付类型名称长度必须介于 0 和 50 之间")
	public String getPayTypeNameJava() {
		return payTypeNameJava;
	}

	public void setPayTypeNameJava(String payTypeNameJava) {
		this.payTypeNameJava = payTypeNameJava;
	}
	
	@Length(min=0, max=10, message="java银行ID长度必须介于 0 和 10 之间")
	public String getBankIdJava() {
		return bankIdJava;
	}

	public void setBankIdJava(String bankIdJava) {
		this.bankIdJava = bankIdJava;
	}
	
	@Length(min=0, max=50, message="java银行名称长度必须介于 0 和 50 之间")
	public String getBankNameJava() {
		return bankNameJava;
	}

	public void setBankNameJava(String bankNameJava) {
		this.bankNameJava = bankNameJava;
	}
	
	@Length(min=0, max=10, message="java通道合作方长度必须介于 0 和 10 之间")
	public String getChannelPartnerJava() {
		return channelPartnerJava;
	}

	public void setChannelPartnerJava(String channelPartnerJava) {
		this.channelPartnerJava = channelPartnerJava;
	}
	
	@Length(min=0, max=50, message="java通道合作方名称长度必须介于 0 和 50 之间")
	public String getChannelPartnerNameJava() {
		return channelPartnerNameJava;
	}

	public void setChannelPartnerNameJava(String channelPartnerNameJava) {
		this.channelPartnerNameJava = channelPartnerNameJava;
	}
	
	@Length(min=0, max=10, message="公司ID|结算主体长度必须介于 0 和 10 之间")
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@Length(min=0, max=50, message="公司名称|结算主体长度必须介于 0 和 50 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=8, message="创建者长度必须介于 0 和 8 之间")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=8, message="更新者长度必须介于 0 和 8 之间")
	public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
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
	
}