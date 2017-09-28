package com.heepay.billing.dao;

import com.heepay.billing.entity.SettleMonitor;

public interface SettleMonitorMapper {

	/**
	 * 
	 * @方法说明：服务状态监控表根据id查询对象
	 * @时间：Dec 12, 2016
	 * @创建人：wangl
	 */
	SettleMonitor getValueById(int id);
    
}