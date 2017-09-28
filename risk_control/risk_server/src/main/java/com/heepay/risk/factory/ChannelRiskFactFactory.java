package com.heepay.risk.factory;

import com.heepay.engine.AbstractRiskFact;
import com.heepay.engine.entity.ChannelRiskFact;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年1月24日 上午10:54:49
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
public class ChannelRiskFactFactory  extends RiskFactFactory {

	@Override
	public AbstractRiskFact create() {
		// TODO Auto-generated method stub
		return new ChannelRiskFact();
	}

}


