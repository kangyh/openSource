package com.heepay.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;

/**
* @author 王英雷  E-mail:wangyl@9186.com
* @version 创建时间：2016年11月23日 上午10:50:11
* 类说明
* 获取 商户信息类接口
*/
@Component
public class MerchantRedisServiceClient  extends BaseClientDistribute{


	private static final String SERVICENAME = "merchantRedisServiceImpl"; //注意 服务名 首字母的大小写
	private static final String NODENAME = "manager_server";
	private static final Logger log = LogManager.getLogger();
	@Resource(name = "managerClient")
  private ThriftClientProxy clientProxy;
	
	
	@Override
	public void setServiceName() {
		// TODO Auto-generated method stub
		ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
	}

	@Override
	public void setNodename() {
		// TODO Auto-generated method stub
		ClientThreadLocal.getInstance().setNodename(NODENAME);
	}

	@Override
	public ThriftClientProxy getClientProxy() {
		// TODO Auto-generated method stub
		return clientProxy;
	}
	/* public MerchantRedisService.Client getClient(){
		    this.setServiceName();
		    this.setNodename();
		    this.setTMultiplexedProtocol();
		    return new MerchantRedisService.Client(ClientThreadLocal.getInstance().getProtocol());
	}*/
   /**
    * 根据商户编号 获取商户的基本信息
    * @param merchantId
    * @return
    */
	 
	public String  getMerchantVO(String merchantId)
	{
		/*try{
		      return getClient().getMerchantVO(merchantId).toString();
		    } catch (TException e){
		      log.error(e);
		    } finally {
		      this.close();
		    }*/
		    return null;
	}
	/**
	 * 根据交易类型码，得到相应的产品列表
	 * @param trxType   为枚举类型  TransType
	 * @return
	 */
	public String getProductList(String trxType)
	{
		/*try{
		      return getClient().getProductList(trxType);
		    } catch (TException e){
		      log.error(e);
		    } finally {
		      this.close();
		    }*/
		    return null;
	}
	
}
