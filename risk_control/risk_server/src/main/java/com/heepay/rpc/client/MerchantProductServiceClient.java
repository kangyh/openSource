package com.heepay.rpc.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import com.heepay.rpc.risk.model.AsyncMsgVO;
import com.heepay.rpc.risk.service.TransOrderRiskService;

/**
* @author 王英雷  E-mail:wangyl@9186.com
* @version 创建时间：2016年11月23日 上午10:36:19
* 类说明
*/
@Component
public class MerchantProductServiceClient extends BaseClientDistribute{
	private static final String SERVICENAME = "merchantProductServiceImpl";
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
	/* public MerchantProductService.Client getClient(){
		    this.setServiceName();
		    this.setNodename();
		    this.setTMultiplexedProtocol();
		    return new MerchantProductService.Client(ClientThreadLocal.getInstance().getProtocol());
	}*/
   /**
    * 根据商户编号 获取商户的产品信息 
    * @param merchantId
    * @return
    */
	  
	public String  getMerchantProduct(String merchantId)
	{
		/*
		try{
			List<MerchantProductThrift> merchantProductThriftlist = getClient().getMerchantProduct(merchantId);
			List<Map> MerchantProductThriftMap = new ArrayList<Map>();
			for (int i=0;i<merchantProductThriftlist.size();i++)
			{
				Map map = new HashMap();
				map.put("merchantId", merchantProductThriftlist.get(i).getMerchantId());
				map.put("productCode", merchantProductThriftlist.get(i).getProductCode());
				map.put("productName", merchantProductThriftlist.get(i).getProductName());
				map.put("validityDateEnd", merchantProductThriftlist.get(i).getValidityDateEnd());
				map.put("validityDateBegin", merchantProductThriftlist.get(i).getValidityDateBegin());
				map.put("fee", merchantProductThriftlist.get(i).getFee());
				map.put("remark", merchantProductThriftlist.get(i).getRemark());
				map.put("trxType", merchantProductThriftlist.get(i).getTrxType());
				MerchantProductThriftMap.add(map);
			}
			JsonMapperUtil jsonMapperUtil = new JsonMapperUtil();
			log.info("merchantProductServiceImpl.getMerchantProduct:",MerchantProductThriftMap );
			return jsonMapperUtil.toJson(MerchantProductThriftMap);
			//return MerchantProductThriftMap.toString();
		    } catch (TException e){
		      log.error(e);
		    } finally {
		      this.close();
		    }*/
		    return null;
	}
	 
}
