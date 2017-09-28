/**
 * 
 */
package com.heepay.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.risk.service.RiskMoniConfService;

/**
 * @author Administrator
 *
 */
@Service
public class RiskMoniConfClient extends BaseClientDistribute {

	private static final String SERVICENAME = "RiskMoniConfServiceImpl";
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
	
	public RiskMoniConfService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new RiskMoniConfService.Client(ClientThreadLocal.getInstance().getProtocol());
    }
	
	/**
     * 添加通道监控配置
     * @方法说明：
     * @时间：2017年8月10日
     * @创建人：李震
     */
	public String addChannelMonitorConfig(String configStr) throws TException {
		try {
    		log.info("添加通道监控配置------>{}",configStr);
    		String returnStr = getClient().addChannelMonitorConfig(configStr);
    		log.info("添加通道监控配置---返回信息--->{}",returnStr);
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
	}
	/**
     * 修改通道监控配置
     * @方法说明：
     * @时间：2017年8月10日
     * @创建人：李震
     */
	public String editChannelMonitorConfig(String configStr) throws TException {
		try {
    		log.info("修改通道监控配置------>{}",configStr);
    		String returnStr = getClient().editChannelMonitorConfig(configStr);
    		log.info("修改通道监控配置---返回信息--->{}",returnStr);
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
	}
	/**
     * 删除通道监控配置
     * @方法说明：
     * @时间：2017年8月10日
     * @创建人：李震
     */
	public String delChannelMonitorConfig(String configStr) throws TException {
		try {
    		log.info("删除通道监控配置------>{}",configStr);
    		String returnStr = getClient().delChannelMonitorConfig(configStr);
    		log.info("删除通道监控配置---返回信息--->{}",returnStr);
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
	}
	/**
     * 添加商户监控配置
     * @方法说明：
     * @时间：2017年8月10日
     * @创建人：李震
     */
	public String addMerchantMonitorConfig(String configStr) throws TException {
		try {
    		log.info("添加商户监控配置------>{}",configStr);
    		String returnStr = getClient().addMerchantMonitorConfig(configStr);
    		log.info("添加商户监控配置---返回信息--->{}",returnStr);
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
	}
	/**
     * 修改商户监控配置
     * @方法说明：
     * @时间：2017年8月10日
     * @创建人：李震
     */
	public String editMerchantMonitorConfig(String configStr) throws TException {
		try {
    		log.info("修改商户监控配置------>{}",configStr);
    		String returnStr = getClient().editMerchantMonitorConfig(configStr);
    		log.info("修改商户监控配置---返回信息--->{}",returnStr);
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
	}
	/**
     * 删除商户监控配置
     * @方法说明：
     * @时间：2017年8月10日
     * @创建人：李震
     */
	public String delMerchantMonitorConfig(String configStr) throws TException {
		try {
    		log.info("删除商户监控配置------>{}",configStr);
    		String returnStr = getClient().delMerchantMonitorConfig(configStr);
    		log.info("删除商户监控配置---返回信息--->{}",returnStr);
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
	}

}
