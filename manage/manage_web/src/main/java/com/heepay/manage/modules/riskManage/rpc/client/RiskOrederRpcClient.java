package com.heepay.manage.modules.riskManage.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.heepay.rpc.account.model.MerchantFrozenRes;
import com.heepay.rpc.account.service.MerchantFrozenService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;

/**
 * 
 *
 * 描 述：调用风控系统的获取商户信息和产品信息的接口
 *
 * 创 建 者： wangdong 
 * 创建时间：2016年11月23日 上午9:24:57 
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
public class RiskOrederRpcClient extends BaseClientDistribute {

	private static final String SERVICENAME = "MerchantFrozenServiceImpl";
	private static final String NODENAME = "payment_rpc";

	private static final Logger log = LogManager.getLogger();
	
	//配置文件中的id名称
	@Resource(name = "heepayClient")
	private ThriftClientProxy clientProxy;
	
	@Override
	public ThriftClientProxy getClientProxy() {
		return clientProxy;
	}

	private MerchantFrozenService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new MerchantFrozenService.Client(ClientThreadLocal.getInstance().getProtocol());
	}

	@Override
	public void setServiceName() {
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setNodename() {
		ClientThreadLocal.getInstance().setNodename(NODENAME);
	}

	
	/**
	 * 
	 * @方法说明：冻结方法
	 * @时间：Nov 23, 2016
	 * @创建人：wangl
	 */
	public MerchantFrozenRes getFrozenMerchantAmount(String merchantId,String riskOrder,String balanceChange,String applyNotes,String remark) {
		
		MerchantFrozenService.Client client = this.getClient();
		
		MerchantFrozenRes merchantFrozen =null;
		try {
			log.info("RPC服务   解冻和冻结调取方法{getMerchant}");
			
			merchantFrozen = client.frozenMerchantAmount(merchantId, riskOrder, balanceChange, applyNotes, remark);
			 
			log.info("RPC服务   解冻和冻结调取方法{getMerchant}",merchantFrozen);
			
		} catch (Exception e) {
			log.error("RPC服务   解冻和冻结调取方法出错！{解冻和冻结调取方法}", e.getMessage());
		} finally {
			this.close();
		}
		return merchantFrozen;
	}
	
	/**
	 * 
	 * @方法说明：解冻方法   商户id 解冻单号   解冻的金额  解冻理由
	 * @时间：Nov 23, 2016
	 * @创建人：wangl
	 */
	public MerchantFrozenRes thawMerchantAmount(String merchantId,String riskOrder,String thawOrder,String remark) {
		MerchantFrozenService.Client client = this.getClient();
		
		MerchantFrozenRes thawMerchantAmount=null;
		try {
			log.info("RPC服务   商户账户解冻调取方法服务{getMerchant}");
			 
			thawMerchantAmount = client.thawMerchantAmount(merchantId, riskOrder, thawOrder, remark);
			log.info("RPC服务   商户账户解冻调取方法服务{getMerchant}",thawMerchantAmount);
			
		} catch (Exception e) {
			log.error("RPC服务    商户账户解冻调取方法服务出错！{解冻方法}", e.getMessage());
		} finally {
			this.close();
		}
		
		return thawMerchantAmount;
	}
	
	
}
