package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描    述：客户充值返回参数Vo
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月18日下午6:32:32 
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
public class ChargeMsgVo {
	private int retCode;
	private String  retMsg;
	public int getRetCode() {
		return retCode;
	}
	public void setRetCode(int retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	
}
