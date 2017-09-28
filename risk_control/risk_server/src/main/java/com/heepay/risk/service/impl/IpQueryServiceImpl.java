/**
 * 
 */
package com.heepay.risk.service.impl;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.heepay.risk.dao.RiskIpbaseMapper;
import com.heepay.risk.entity.RiskIpbase;
import com.heepay.risk.service.IpQueryService;
import com.heepay.risk.util.IpUtil;

/**
 * @author Administrator
 *
 */
@Component
public class IpQueryServiceImpl implements IpQueryService{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	RiskIpbaseMapper riskIpbaseMapper;
	
	public boolean checkIfChineseIp(String ip) {
		RiskIpbase riskIpbase =  new RiskIpbase();
		Configuration configs = null;
		riskIpbase = riskIpbaseMapper.selectByPrimaryKey( IpUtil.ip2long(ip) );
		if( riskIpbase!=null  ) {
			riskIpbase.setIp(ip);//查询回来的是ip段，所以需要回写
			try {
				configs = new PropertiesConfiguration("ipbase.properties");
			} catch (ConfigurationException e) {
				logger.info("获取ipbase配置失败！");
				e.printStackTrace();
			}
			
			if( "false".equals( configs.getProperty("excludeLocalAreaNet") ) ){
				if( riskIpbase.getRemark().indexOf("中国") > -1 || riskIpbase.getRemark().indexOf("局域网") >-1  || riskIpbase.getRemark().indexOf("保留地址") >-1   || riskIpbase.getRemark().indexOf("本机地址") >-1 )  {
					logger.info("ip属于中国【"+riskIpbase.getRemark() + "】");
					return true;
				}else{
					logger.info("ip不属于中国【"+riskIpbase.getRemark() +  "】");
					return false;
				}
			}else{
				if( riskIpbase.getRemark().indexOf("中国") > -1  )  {
					logger.info("ip属于中国【"+riskIpbase.getRemark() + "】");
					return true;
				}else{
					logger.info("ip不属于中国【"+riskIpbase.getRemark() +  "】");
					return false;
				}
			}
		}
		return true;
	}
	public RiskIpbase getIpInfo(String ip) {
		RiskIpbase riskIpbase =  new RiskIpbase();
		riskIpbase = riskIpbaseMapper.selectByPrimaryKey(IpUtil.ip2long(ip));
		if(riskIpbase!=null) {
			riskIpbase.setIp(ip);//查询回来的是ip段，所以需要回写
		}
		return riskIpbase;
	}

}
