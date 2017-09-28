package com.heepay.rpc.client;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.heepay.manage.rpc.service.MerchantRedisService;


/***
 * 
* 
* 描    述：获取商户路由信息
*
* 创 建 者： xuangang
* 创建时间：  2016年10月24日下午4:17:16
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
@Service
public class ManagerServerClient extends BaseClientDistribute{

	
	private static final String SERVICENAME = "merchantRedisServiceImpl";
	
	private static final String NODENAME = "manager_server";
	
	private static final Logger log = LogManager.getLogger();
	@Resource(name = "manageServerClient")
	private ThriftClientProxy clientProxy;
  
	
	 @Override
	  public void setServiceName(){
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
	  
	  public MerchantRedisService.Client getClient(){
		    this.setServiceName();
		    this.setNodename();
		    this.setTMultiplexedProtocol();
		    return new MerchantRedisService.Client(ClientThreadLocal.getInstance().getProtocol());
	 }
	  
      /**
       * @描述 获取商户路由信息
       * @param merchantId 商户Id
       * @param productCode 产品编码
       * @return
       */
	  public String getMerchantProductVO(String merchantId, String productCode){
		  MerchantRedisService.Client client = this.getClient();
		  try {
			     String merchantVo = null;
				 merchantVo = client.getMerchantProductVO(merchantId, productCode);
				 log.info("获取商户侧路由信息成功！{}", merchantVo);
				 return merchantVo;
			} catch (TException e) {				
				log.error("获取商户侧路由信息异常！{}，异常{}", merchantId + productCode, e);
			} finally {
				this.close();
			}
			return null;
	  }
	  
      /**
       * 
       * @方法说明：
       * @author xuangang
       * @param productCode
       * @return
       * @时间：2017年4月7日下午2:07:20
       */
	  public String getProductVo(String productCode){
		  MerchantRedisService.Client client = this.getClient();
		  try {
			     String productVO = null;
			     productVO = client.getProductVo(productCode);
				 log.info("获取商户侧路由信息成功！{}", productVO);
				 return productVO;
			} catch (TException e) {				
				log.error("获取商户侧路由信息异常！{}，异常{}", productCode, e);
			} finally {
				this.close();
			}
			return null;
	  } 

}
