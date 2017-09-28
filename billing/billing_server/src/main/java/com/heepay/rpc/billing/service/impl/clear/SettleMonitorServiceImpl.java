package com.heepay.rpc.billing.service.impl.clear;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.SettleMonitorMapper;
import com.heepay.billing.entity.SettleMonitor;
import com.heepay.rpc.service.RpcService;


/***
 * 
 * 
 * 描    述：服务状态监控表根据id查询对象
 *
 * 创 建 者： wangl
 * 创建时间：  2016年9月12日下午6:11:19
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

@Component
@RpcService(name="SettleMonitorServiceWL",processor=com.heepay.rpc.billing.service.SettleMonitorService.Processor.class)
public class SettleMonitorServiceImpl implements com.heepay.rpc.billing.service.SettleMonitorService.Iface {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SettleMonitorMapper settleMonitorMapper ;

	@Override
	public String getValueById(int id) throws TException {
		
		logger.info("服务状态监控表根据id查询对象----查询开始--->{}"+id);
		
		SettleMonitor settleMonitor=null;
		try {
			settleMonitor = settleMonitorMapper.getValueById(id);
		} catch (Exception e) {
			logger.error("服务状态监控表根据id查询对象------->{异常}"+e.getMessage());
		}
			
		if(settleMonitor !=null){
			logger.info("服务状态监控表根据id查询对象----查询结束--->{}"+settleMonitor.getValue());
			return settleMonitor.getValue();
		}else{
			logger.info("服务状态监控表根据id查询对象----查询结束--->{没有记录}");
			
			return "";
		}
	}
	
	
}
