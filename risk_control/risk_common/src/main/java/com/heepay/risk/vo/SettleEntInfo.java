package com.heepay.risk.vo;

import java.util.List;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月3日 上午10:58:53
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
public class SettleEntInfo {
	/**
	 * 商户代码
	 */
	private String cusCode;
	/**
	 * 商户代码
	 */
	private String regName;
	/**
	 * 法定代表人（负责人）姓名
	 */
	private String legDocName;
	private List<SettleMerchantInfoVo> differList ;
	
	public List<SettleMerchantInfoVo> getDifferList() {
		return differList;
	}
	public void setDifferList(List<SettleMerchantInfoVo> differList) {
		this.differList = differList;
	}
	public String getCusCode() {
		return cusCode;
	}
	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
	public String getLegDocName() {
		return legDocName;
	}
	public void setLegDocName(String legDocName) {
		this.legDocName = legDocName;
	}
	

}

 