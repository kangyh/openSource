package com.heepay.manage.modules.riskManage.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.risk.service.RiskService;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年7月27日下午5:25:54
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
public class RiskLoginClient extends BaseClientDistribute {

	private static final String SERVICENAME = "RiskServiceImpl";
	private static final String NODENAME = "risk_rpc";

	private static final Logger log = LogManager.getLogger();
	@Resource(name = "riskMerchantInfoClient")
	private ThriftClientProxy clientProxy;

	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);
	}

	@Override
	public ThriftClientProxy getClientProxy() {
		return clientProxy;
	}

	public RiskService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new RiskService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	/**
	 * 风控黑名单商户
	 * 
	 * @param chargeId
	 * @return
	 * @return
	 */
	public String addRiskLoginBlacklist(String param) {

		try {
			return getClient().addRiskLoginBlacklistInfo(param);
		} catch (TException e) {
			log.error("RPC服务   调取风控系统服务添加风控黑名单商户出错！{FIND_ERROR}", e.getMessage());
		} finally {
			this.close();
		}
		return null;
	}
	
	/**
	 * 风控黑名单商户
	 * 
	 * @param chargeId
	 * @return
	 * @return
	 */
	public String editRiskLoginBlacklist(String param) {

		try {
			return getClient().editRiskLoginBlacklistInfo(param);
		} catch (TException e) {
			log.error("RPC服务   调取风控系统服务修改风控黑名单商户出错！{FIND_ERROR}", e.getMessage());
		} finally {
			this.close();
		}
		return null;
	}
	
	/**
	 * 风控黑名单商户
	 * 
	 * @param chargeId
	 * @return
	 * @return
	 */
	public String deleteRiskLoginBlacklist(String param) {

		try {
			return getClient().delRiskLoginBlacklistInfo(param);
		} catch (TException e) {
			log.error("RPC服务   调取风控系统服务删除风控黑名单商户出错！{FIND_ERROR}", e.getMessage());
		} finally {
			this.close();
		}
		return null;
	}
}
