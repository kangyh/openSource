package com.heepay.manage.modules.riskManage.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.rpc.boss.service.BossRuleRpcService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;

@Component
public class BossRpcCLient extends BaseClientDistribute {

	private static final String SERVICENAME = "BossRuleRpcServiceImpl";
	private static final String NODENAME = "boss_rpc";

	private static final Logger log = LogManager.getLogger();
	@Resource(name = "bossRuleClient")
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

	public BossRuleRpcService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new BossRuleRpcService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	
	//插入
	public String insertRule(String arg) {

		log.info("RPC服务   插入报表配置开始");
		try {
			return getClient().addBossRule(arg);
		} catch (TException e) {
			log.error("RPC服务  插入报表配置出错！{FIND_ERROR}", e.getMessage());
		} finally {
			this.close();
		}
		return null;
	}
	
	//插入
	public String updateRule(String arg) {

		log.info("RPC服务   修改报表配置开始");
		try {
			return getClient().editBossRule(arg);
		} catch (TException e) {
			log.error("RPC服务  修改报表配置出错！{FIND_ERROR}", e.getMessage());
		} finally {
			this.close();
		}
		return null;
	}
	
	//查询
	public String selectRule(String arg) {

		log.info("RPC服务   查询报表配置开始");
		try {
			return getClient().getBossRule(arg);
		} catch (TException e) {
			log.error("RPC服务  查询报表配置出错！{FIND_ERROR}", e.getMessage());
		} finally {
			this.close();
		}
		return null;
	}
}
