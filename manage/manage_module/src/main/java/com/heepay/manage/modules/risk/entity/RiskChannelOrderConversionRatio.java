/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：风控通道转化率Entity
 *
 * 创 建 者： @author xch
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
public class RiskChannelOrderConversionRatio extends DataEntity<RiskChannelOrderConversionRatio> {
	
	private static final long serialVersionUID = 1L;
	private String channelPartnerCode;		// 通道合作方编码
	private String channelPartnerName;		// 通道合作方名称
	private String channelSuccessOrder;		// 成功订单数
	private String channelTotalOrder;		// 通道总订单
	private Date beginTime;		// 开始时间
	private Date endTime;		// 结束时间
	private Date createTime;		// 创建时间
	private String sucessRatio;		// 成功率
	private String channelTypeCode;		// 支付类型编码
	private String channelTypeName;		// 支付类型名称
	private String host;		// 主机名
	private String beginStatisticTime;
	private String endStatisticTime;
	
	public RiskChannelOrderConversionRatio() {
		super();
	}

	public RiskChannelOrderConversionRatio(String id){
		super(id);
	}

	@Length(min=0, max=30, message="通道合作方编码长度必须介于 0 和 30 之间")
	public String getChannelPartnerCode() {
		return channelPartnerCode;
	}

	public void setChannelPartnerCode(String channelPartnerCode) {
		this.channelPartnerCode = channelPartnerCode;
	}
	
	@Length(min=0, max=300, message="通道合作方名称长度必须介于 0 和 300 之间")
	public String getChannelPartnerName() {
		return channelPartnerName;
	}

	public void setChannelPartnerName(String channelPartnerName) {
		this.channelPartnerName = channelPartnerName;
	}
	
	@Length(min=0, max=11, message="成功订单数长度必须介于 0 和 11 之间")
	public String getChannelSuccessOrder() {
		return channelSuccessOrder;
	}

	public void setChannelSuccessOrder(String channelSuccessOrder) {
		this.channelSuccessOrder = channelSuccessOrder;
	}
	
	@Length(min=0, max=11, message="通道总订单长度必须介于 0 和 11 之间")
	public String getChannelTotalOrder() {
		return channelTotalOrder;
	}

	public void setChannelTotalOrder(String channelTotalOrder) {
		this.channelTotalOrder = channelTotalOrder;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=11, message="成功率长度必须介于 0 和 11 之间")
	public String getSucessRatio() {
		return sucessRatio;
	}

	public void setSucessRatio(String sucessRatio) {
		this.sucessRatio = sucessRatio;
	}
	
	@Length(min=0, max=30, message="支付类型编码长度必须介于 0 和 30 之间")
	public String getChannelTypeCode() {
		return channelTypeCode;
	}

	public void setChannelTypeCode(String channelTypeCode) {
		this.channelTypeCode = channelTypeCode;
	}
	
	@Length(min=0, max=300, message="支付类型名称长度必须介于 0 和 300 之间")
	public String getChannelTypeName() {
		return channelTypeName;
	}

	public void setChannelTypeName(String channelTypeName) {
		this.channelTypeName = channelTypeName;
	}
	
	@Length(min=0, max=200, message="主机名长度必须介于 0 和 200 之间")
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getBeginStatisticTime() {
		return beginStatisticTime;
	}

	public void setBeginStatisticTime(String beginStatisticTime) {
		this.beginStatisticTime = beginStatisticTime;
	}

	public String getEndStatisticTime() {
		return endStatisticTime;
	}

	public void setEndStatisticTime(String endStatisticTime) {
		this.endStatisticTime = endStatisticTime;
	}
}