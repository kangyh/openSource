/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 *
 * 描    述：账户明细查询Entity
 *
 * 创 建 者： @author yq
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
public class MerchantSettleBean extends DataEntity<MerchantSettleBean> {

	private String merchantId;
	private String transNo;
	private String merchantSettleStatus;
	private String channelSettleStatus;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getMerchantSettleStatus() {
		return merchantSettleStatus;
	}

	public void setMerchantSettleStatus(String merchantSettleStatus) {
		this.merchantSettleStatus = merchantSettleStatus;
	}

	public String getChannelSettleStatus() {
		return channelSettleStatus;
	}

	public void setChannelSettleStatus(String channelSettleStatus) {
		this.channelSettleStatus = channelSettleStatus;
	}
}