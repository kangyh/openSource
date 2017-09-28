package com.heepay.manage.modules.monitors.web.rpc;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.warning.service.WarningService;

@Service
public class WarningClient extends BaseClientDistribute  {
    private static final String NODENAME = "warning_rpc";
    private static final String SERVICENAME = "WarningServiceImpl";
	private static final Logger log = LogManager.getLogger();
	
	
  @Resource(name = "warnClient")
  private ThriftClientProxy clientProxy;
  
  @Override
  public void setServiceName(){
    ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
  }
  
  @Override
  public void setNodename() {
    ClientThreadLocal.getInstance().setNodename(NODENAME);;
  }
  
  @Override
  public ThriftClientProxy getClientProxy() {
    return clientProxy;
  }
  
  
  public WarningService.Client getClient(){
    this.setServiceName();
    this.setNodename();
    this.setTMultiplexedProtocol();
    return new WarningService.Client(ClientThreadLocal.getInstance().getProtocol());
  }

  /**
	 * 
	 * @方法说明：发送消息
	 * @时间：13 Feb 201714:20:16
	 * @创建人：wangl
	 */
	public String saveTpdsTxBankCard(String msg) {
		
		WarningService.Client client = this.getClient();
		
		try {
			String sendWaringMsg = client.sendWaringMsg(msg);
			return sendWaringMsg;
		} catch (TException e) {
			log.error("告警记录--->{异常}"+e.getMessage());
			return "";
		} finally {
			this.close();
		}
	}
}
