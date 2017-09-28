package com.heepay.billing.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleMonitorMapper;
import com.heepay.billing.entity.SettleMonitor;
import com.heepay.billing.entity.SettleMonitorExample;

/***
 * 
* 
* 描    述：根据id查询对象
*
* 创 建 者： wangl
* 创建时间：  Dec 10, 20166:41:05 PM
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
public class SettleMonitorMapperImpl extends BaseDaoImpl<SettleMonitor, SettleMonitorExample> implements SettleMonitorMapper {

	// 默认构造方法设置命名空间
	public SettleMonitorMapperImpl() {
		super.setNs("com.heepay.billing.dao.SettleMonitorMapper");
	}
	
	/**
	 * 
	 * @方法说明：服务状态监控表根据id查询对象
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Override
	public SettleMonitor getValueById(int id) {

		return super.getSqlSession().selectOne(this.getNs() + ".getValueById",id);
	
	}
}
