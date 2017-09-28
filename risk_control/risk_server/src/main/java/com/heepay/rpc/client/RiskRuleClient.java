/**
 * 
 */
package com.heepay.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.risk.service.RiskRuleService;

/**
 * @author Administrator
 *
 */
@Service
public class RiskRuleClient  extends BaseClientDistribute {
	
	private static final String SERVICENAME = "RiskRuleServiceImpl";
    private static final String NODENAME = "risk_rpc";
    private static final Logger log = LogManager.getLogger();
    @Resource(name = "riskClient")
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

    public RiskRuleService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new RiskRuleService.Client(ClientThreadLocal.getInstance().getProtocol());
    }
    /**
     * 添加风控规则
     * @方法说明：
     * @时间：2017年8月7日
     * @创建人：李震
     */
    public String addRiskRule(String riskRulelistStr) {
    	try {
    		log.info("添加风控规则信息------>{}",riskRulelistStr);
    		String returnStr = getClient().addRiskRule(riskRulelistStr);
    		log.info("添加风控规则信息---返回信息--->{}",returnStr);
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }
    /**
     * 更新风控规则
     * @方法说明：
     * @时间：2017年8月7日
     * @创建人：李震
     */
    public String editRiskRule(String riskRulelistStr) {
    	try {
    		log.info("更新风控规则信息------>{}",riskRulelistStr);
    		String returnStr = getClient().editRiskRule(riskRulelistStr);
    		log.info("更新风控规则信息---返回信息--->{}",returnStr);
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }
}
