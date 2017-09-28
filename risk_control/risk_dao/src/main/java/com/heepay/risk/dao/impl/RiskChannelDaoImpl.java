package com.heepay.risk.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcQueryInfoMapper;
import com.heepay.risk.dao.RiskChannelMapper;
import com.heepay.risk.entity.PbcQueryInfo;
import com.heepay.risk.entity.RiskChannel;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年5月25日 下午3:46:47
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
@Repository
public class RiskChannelDaoImpl extends BaseDaoImpl<RiskChannel> implements RiskChannelMapper {

	// 默认构造方法设置命名空间
				public RiskChannelDaoImpl() {
					super.setNs("com.heepay.risk.dao.RiskChannelMapper");
				}
			
		@Override
		public int insert(RiskChannel entity) {
			// TODO Auto-generated method stub
		return super.getSqlSession().insert(this.getNs()+".insert",entity);
		}
}

 