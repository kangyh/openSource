/**
 *  
 */
package com.heepay.manage.modules.payment.entity;

import com.heepay.common.util.StringUtil;

/**
 *
 * 描    述：转账汇总实体
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

public class BatchPayRecordSummary extends BatchPayRecord {

	private String totalNum;		// 转账总笔数
	private String successTotalNum;		// 成功总笔数
	private String failTotalNum;		//失败总笔数

	private String totalAmount;			// 转账总金额
	private String successTotalAmount;  // 成功转账总金额
	private String failTotalAmount;		// 失败转账总金额

	private String totalFeeAmount;		//转账总手续费

	@Override
	public String getTotalNum() {
		return StringUtil.isBlank(totalNum)?"0":totalNum;
	}

	@Override
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	@Override
	public String getSuccessTotalNum() {
		return StringUtil.isBlank(successTotalNum)?"0":successTotalNum;
	}

	@Override
	public void setSuccessTotalNum(String successTotalNum) {
		this.successTotalNum = successTotalNum;
	}

	public String getFailTotalNum() {
		return StringUtil.isBlank(failTotalNum)?"0":failTotalNum;
	}

	public void setFailTotalNum(String failTotalNum) {
		this.failTotalNum = failTotalNum;
	}

	@Override
	public String getTotalAmount() {
		return StringUtil.isBlank(totalAmount)?"0.0000":totalAmount;
	}

	@Override
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String getSuccessTotalAmount() {
		return StringUtil.isBlank(successTotalAmount)?"0.0000":successTotalAmount;
	}

	@Override
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