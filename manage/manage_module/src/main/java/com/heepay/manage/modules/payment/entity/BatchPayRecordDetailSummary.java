/**
 *  
 */
package com.heepay.manage.modules.payment.entity;

import com.heepay.common.util.StringUtil;

/**
 *
 * 描    述：某批次转账明细汇总实体
 *
 * 创 建 者： 张俊新
 * 创建时间： 2016年9月1日 上午10:59:30
 * 创建描述： 转账汇总
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

public class BatchPayRecordDetailSummary{


	private String totalAmount;			// 转账总金额
	private String successTotalAmount;  // 成功转账总金额
	private String failTotalAmount;		// 失败转账总金额

	private String totalFeeAmount;		//转账总手续费

	public String getTotalAmount() {
		return StringUtil.isBlank(totalAmount)?"0.0000":totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getSuccessTotalAmount() {
		return StringUtil.isBlank(successTotalAmount)?"0.0000":successTotalAmount;
	}

	public void setSuccessTotalAmount(String successTotalAmount) {
		this.successTotalAmount = successTotalAmount;
	}

	public String getFailTotalAmount() {
		return StringUtil.isBlank(failTotalAmount)?"0.0000":failTotalAmount;
	}

	public void setFailTotalAmount(String failTotalAmount) {
		this.failTotalAmount = failTotalAmount;
	}

	public String getTotalFeeAmount() {
		return StringUtil.isBlank(totalFeeAmount)?"0.0000":totalFeeAmount;
	}

	public void setTotalFeeAmount(String totalFeeAmount) {
		this.totalFeeAmount = totalFeeAmount;
	}
}