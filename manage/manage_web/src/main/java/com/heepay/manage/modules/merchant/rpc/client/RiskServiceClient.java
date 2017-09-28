package com.heepay.manage.modules.merchant.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.risk.service.RiskService;

/**
 * 
 * 描 述：分控service监控
 *
 * 创 建 者： ly 创建时间： 2016年12月12日 下午1:42:12 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：  
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Service
public class RiskServiceClient extends BaseClientDistribute {
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


    public String queryRiskService() {
        try {
            return getClient().getRiskRunStatus();
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }
    
    /**
     * 
     * @方法说明：添加产品限额信息
     * @时间：2017年2月8日 上午11:59:26
     * @创建人：wangdong
     */
    public String addRiskProductQuota(String info){
    	try {
    		log.info("添加产品限额信息------>{}",info);
    		String returnStr = getClient().addRiskProductQuota(info);
    		log.info("添加产品限额信息---返回信息--->{}",returnStr);
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }
    
    /**
     * 
     * @方法说明：修改产品限额信息
     * @时间：2017年2月8日 上午11:59:16
     * @创建人：wangdong
     */
    public String editRiskProductQuota(String info){
    	try {
    		log.info("修改产品限额信息------>{}",info);
    		String returnStr = getClient().editRiskProductQuota(info);
    		log.info("修改产品限额信息---返回信息--->{}",returnStr);
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }
    
    /**
     * 
     * @方法说明：添加商户限额信息
     * @时间：2017年2月8日 上午11:59:42
     * @创建人：wangdong
     */
    public String addRiskMerchantProductQuota(String info){
    	try {
    		log.info("添加商户限额信息------>{}",info);
    		String returnStr = getClient().addRiskMerchantProductQuota(info);
    		log.info("添加商户限额信息---返回信息--->{}",returnStr);
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }
    
    /**
     * 
     * @方法说明：修改商户产品限额信息
     * @时间：2017年2月8日 下午12:00:09
     * @创建人：wangdong
     */
    public String editRiskMerchantProductQuota(String info){
    	try {
    		log.info("修改商户产品限额信息------>{}",info);
            String returnStr = getClient().editRiskMerchantProductQuota(info);
            log.info("修改商户产品限额信息---返回信息--->{}",returnStr);
            return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
        return null;
    }
    
    public String addRiskBlackorwhite(String info) {
    	try {
    		log.info("添加黑白名单------>{}" +info);
    		String returnStr = getClient().addBlackorwhiteList(info);
    		log.info("添加黑白名单---返回信息--->{"+returnStr +"}");
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
    	return null;
    }
    public String updateRiskBlackorwhite(String info) {
    	try {
    		log.info("更新黑白名单------>{"  +info + "}");
    		String returnStr = getClient().editBlackorwhiteList(info);
    		log.info("更新黑白名单---返回信息--->{"+returnStr +"}");
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
    	return null;
    	
    }
    public String deleteRiskBlackorwhite(String info ) {
    	try {
    		log.info("删除黑白名单------>{"  +info + "}");
    		String returnStr = getClient().delBlackorwhiteList(info);
    		log.info("删除黑白名单---返回信息--->{"+returnStr +"}");
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
    	return null;
    	
    }
    public String addRiskBlackorwhiteItem(String info) {
    	try {
    		log.info("添加黑白名单明细------>{"  +info + "}");
    		String returnStr = getClient().addBlackorwhiteItemList(info);
    		log.info("添加黑白名单明细--返回信息--->{"+returnStr+"}");
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
    	return null;
    }
    public String updateRiskBlackorwhiteItem(String info) {
    	try {
    		log.info("更新黑白名单明细------>{"  +info + "}");
    		String returnStr = getClient().editBlackorwhiteItemList(info);
    		log.info("更新黑白名单明细--->{"+returnStr +"}");
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
    	return null;
    }
    public String deleteRiskBlackorwhiteItem(String info) {
    	try {
    		log.info("删除黑白名单明细------>{"  +info + "}");
    		String returnStr = getClient().delBlackorwhiteItemList(info);
    		log.info("删除黑白名单明细---返回信息--->{"+returnStr +"}");
    		return returnStr;
        } catch (TException e) {
            log.error(e);
        } finally {
            this.close();
        }
    	return null;
    }

    /**
     * @方法说明：添加出入金限额
     * @时间： 2017/6/2 13:54
     * @创建人：wangdong
     */
    public String addRiskIncomeQuota(String info) {
        try {
            log.info("添加出入金限额------>{}",info);
            String returnStr = getClient().addRiskIncomeQuota(info);
            log.info("添加出入金限额---返回参数--->{}",info);
            return returnStr;
        } catch (TException e) {
            log.error("添加出入金限额调用RPC服务异常--->{}",e);
        } finally {
            this.close();
        }
        return null;
    }

    /**
     * @方法说明：修改出入金限额
     * @时间： 2017/6/2 13:54
     * @创建人：wangdong
     */
    public String editRiskIncomeQuota(String info) {
        try {
            log.info("修改出入金限额------>{}",info);
            String returnStr = getClient().editRiskIncomeQuota(info);
            log.info("修改出入金限额---返回参数--->{}",info);
            return returnStr;
        } catch (TException e) {
            log.error("修改出入金限额调用RPC服务异常--->{}",e);
        } finally {
            this.close();
        }
        return null;
    }

    /**
     * @方法说明：删除出入金限额
     * @时间： 2017/6/2 13:54
     * @创建人：wangdong
     */
    public String delRiskIncomeQuota(String info) {
        try {
            log.info("删除出入金限额------>{}",info);
            String returnStr = getClient().delRiskIncomeQuota(info);
            log.info("删除出入金限额---返回参数--->{}",info);
            return returnStr;
        } catch (TException e) {
            log.error("删除出入金限额调用RPC服务异常--->{}",e);
        } finally {
            this.close();
        }
        return null;
    }

    /**
     * @方法说明：添加出入金产品配置
     * @时间： 2017/6/2 13:54
     * @创建人：wangdong
     */
    public String addSettleIncomeInfo(String info) {
        try {
            log.info("添加出入金产品配置------>{}",info);
            String returnStr = getClient().addSettleIncomeInfo(info);
            log.info("添加出入金产品配置---返回参数--->{}",info);
            return returnStr;
        } catch (TException e) {
            log.error("添加出入金产品配置调用RPC服务异常--->{}",e);
        } finally {
            this.close();
        }
        return null;
    }

    /**
     * @方法说明：修改出入金产品配置
     * @时间： 2017/6/2 13:54
     * @创建人：wangdong
     */
    public String editSettleIncomeInfo(String info) {
        try {
            log.info("修改出入金产品配置------>{}",info);
            String returnStr = getClient().editSettleIncomeInfo(info);
            log.info("修改出入金产品配置---返回参数--->{}",info);
            return returnStr;
        } catch (TException e) {
            log.error("修改出入金产品配置调用RPC服务异常--->{}",e);
        } finally {
            this.close();
        }
        return null;
    }

}
