package com.heepay.risk.vo;

import java.util.List;

/**
 * 
 * 
 * 描 述：推送黑名单数据请求体
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月3日 上午10:00:13
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
public class SettleBlackListRequestBody {
	private List<SettleBlackListVo> pcacList;

	public List<SettleBlackListVo> getPcacList() {
		return pcacList;
	}

	public void setPcacList(List<SettleBlackListVo> pcacList) {
		this.pcacList = pcacList;
	}

}

 