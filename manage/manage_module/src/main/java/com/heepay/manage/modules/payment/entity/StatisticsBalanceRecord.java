/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：余额统计Entity
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
public class StatisticsBalanceRecord extends DataEntity<StatisticsBalanceRecord> {
	
	private static final long serialVersionUID = 1L;
	private Date transDate;		// 交易日期
	private String beginInAmount;		// 期初入金
	private String beginOutAmount;		// 期初出金
	private String beginBalanceAmount;		// 期初余额
	private String beginFeeAmount;		// 期初手续费
	private String transInAmount;		// 交易入金
	private String transOutAmount;		// 交易出金
	private String transFeeAmount;		// 交易手续费
	private String endInAmount;		// 期末入金
	private String endOutAmount;		// 期末出金
	private String endBalanceAmount;		// 期末余额
	private String endFeeAmount;		// 期末手续费
	private Date beginTransDate;		// 开始 交易日期
	private Date endTransDate;		// 结束 交易日期
	
	public StatisticsBalanceRecord() {
		super();
	}

	public StatisticsBalanceRecord(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	
	public String getBeginInAmount() {
		return beginInAmount;
	}

	public void setBeginInAmount(String beginInAmount) {
		this.beginInAmount = beginInAmount;
	}
	
	public String getBeginOutAmount() {
		return beginOutAmount;
	}

	public void setBeginOutAmount(String beginOutAmount) {
		this.beginOutAmount = beginOutAmount;
	}
	
	public String getBeginBalanceAmount() {
		return beginBalanceAmount;
	}

	public void setBeginBalanceAmount(String beginBalanceAmount) {
		this.beginBalanceAmount = beginBalanceAmount;
	}
	
	public String getBeginFeeAmount() {
		return beginFeeAmount;
	}

	public void setBeginFeeAmount(String beginFeeAmount) {
		this.beginFeeAmount = beginFeeAmount;
	}
	
	public String getTransInAmount() {
		return transInAmount;
	}

	public void setTransInAmount(String transInAmount) {
		this.transInAmount = transInAmount;
	}
	
	public String getTransOutAmount() {
		return transOutAmount;
	}

	public void setTransOutAmount(String transOutAmount) {
		this.transOutAmount = transOutAmount;
	}
	
	public String getTransFeeAmount() {
		return transFeeAmount;
	}

	public void setTransFeeAmount(String transFeeAmount) {
		this.transFeeAmount = transFeeAmount;
	}
	
	public String getEndInAmount() {
		return endInAmount;
	}

	public void setEndInAmount(String endInAmount) {
		this.endInAmount = endInAmount;
	}
	
	public String getEndOutAmount() {
		return endOutAmount;
	}

	public void setEndOutAmount(String endOutAmount) {
		this.endOutAmount = endOutAmount;
	}
	
	public String getEndBalanceAmount() {
		return endBalanceAmount;
	}

	public void setEndBalanceAmount(String endBalanceAmount) {
		this.endBalanceAmount = endBalanceAmount;
	}
	
	public String getEndFeeAmount() {
		return endFeeAmount;
	}

	public void setEndFeeAmount(String endFeeAmount) {
		this.endFeeAmount = endFeeAmount;
	}
	
	public Date getBeginTransDate() {
		return beginTransDate;
	}

	public void setBeginTransDate(Date beginTransDate) {
		this.beginTransDate = beginTransDate;
	}
	
	public Date getEndTransDate() {
		return endTransDate;
	}

	public void setEndTransDate(Date endTransDate) {
		this.endTransDate = endTransDate;
	}
		
}