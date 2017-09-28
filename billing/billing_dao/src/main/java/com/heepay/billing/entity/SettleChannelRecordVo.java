package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * 描    述：自定义接收查询数据对象
 *
 * 创 建 者：chenyanming
 * 创建时间： 2016年9月10日下午3:48:06 
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
public class SettleChannelRecordVo {
	private String channelCode;
	private String channelName;
	private String channelType;
	private Integer payNum;
	private BigDecimal totalAmount;
	
	//private Date checkTime;
	//private Date settleTime;
	//private String transNo; // 交易订单号
	private String settleCyc; // 交易结算周期
	//private Date costTime;   // 成本结算日期
	private String costSettleCyc;  // 成本结算周期
	private BigDecimal costAmount; // 总成本
	private String transType; // 交易类型，主要用来判断是否向账务发送结算批次（出款类不发）
	
	public String toString(){
	      StringBuilder sb=new StringBuilder(128);
	      sb.append("{\"channelCode\":\"").append(channelCode).append("\",")
	        .append("\"channelName\":\"").append(channelName).append("\",")
	        .append("\"channelType\":\"").append(channelType).append("\",")
	        .append("\"payNum\":\"").append(payNum).append("\",")
	        .append("\"totalAmount\":\"").append(totalAmount).append("\",")
	        .append("\"settleCyc\":\"").append(settleCyc).append("\",")
	        .append("\"costSettleCyc\":\"").append(costSettleCyc).append("\",")
	        .append("\"costAmount\":\"").append(costAmount).append("\",")
	        .append("\"transType\":\"").append(transType).append("\",")
	        .append("\"}");
	      return sb.toString();
	    }
	
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public BigDecimal getCostAmount() {
		return costAmount;
	}
	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public Integer getPayNum() {
		return payNum;
	}
	public void setPayNum(Integer payNum) {
		this.payNum = payNum;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getSettleCyc() {
		return settleCyc;
	}
	public void setSettleCyc(String settleCyc) {
		this.settleCyc = settleCyc;
	}
	public String getCostSettleCyc() {
		return costSettleCyc;
	}
	public void setCostSettleCyc(String costSettleCyc) {
		this.costSettleCyc = costSettleCyc;
	}
	
}













