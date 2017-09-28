package com.heepay.manage.modules.pbc.web.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.report.antifraud.thrift.TQueryPrecCashFlowService;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;

/**
 * 
 *
 * 描 述：账户资金流动查询反馈
 *
 * 创 建 者： wangl
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
public class PrecCashFlowServiceRpcClient extends BaseClientDistribute {

	private static final String SERVICENAME = "PrecCashFlowServiceImpl";
	private static final String NODENAME = "antifraud_rpc";

	private static final Logger log = LogManager.getLogger();
	
	//配置文件中的id名称
	@Resource(name = "PbcPaymentAccountClient")
	private ThriftClientProxy clientProxy;
	
	@Override
	public ThriftClientProxy getClientProxy() {
		return clientProxy;
	}

	private TQueryPrecCashFlowService.Client getClient() {
		this.setServiceName();
		this.setNodename();
		this.setTMultiplexedProtocol();
		return new TQueryPrecCashFlowService.Client(ClientThreadLocal.getInstance().getProtocol());
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
	 * @方法说明：账户主体查询反馈
	 * @时间：Dec 23, 2016
	 * @创建人：wangl
	 */
	public String pbcPaymentAccount(String param) {

		TQueryPrecCashFlowService.Client client = this.getClient();
		log.info("账户资金流动查询反馈上报------>{开始}----->{参数}"+param);
		
		String report="";
		try {
			 report = client.report(param);
			log.info("账户资金流动查询反馈上报----->{结束}----->{返回值}"+report);
		} catch (TException e) {
			log.info("账户资金流动查询反馈上报----->{出现异常}   "+e.getMessage());
		}finally {
			this.close();
		}
		return report;
	}
}
