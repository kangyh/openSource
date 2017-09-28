package com.heepay.tpds.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.rpc.service.RpcService;
import com.heepay.rpc.tpds.service.QuerySignKeyService;
import com.heepay.tpds.dao.TpdsProductKeyMapper;
import com.heepay.tpds.entity.TpdsProductKey;

/**
 * 
 * 
 * 描    述：查询商户与汇付宝签约key
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年3月3日上午10:46:56 
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
@RpcService(name="QuerySignKeyServiceImpl",processor=QuerySignKeyService.Processor.class)
public class QuerySignKeyServiceImpl implements com.heepay.rpc.tpds.service.QuerySignKeyService.Iface {
	
	/*@Autowired
	private TpdsProductKeyMapper tpdsProductKeyDaoImpl;*/
	@Autowired
	ConfigServiceImpl configServiceImpl;
	private static final Logger logger = LogManager.getLogger();

	@Override
	public String querySignKey(String merchantNo, String productCode) throws TException {
		//TpdsProductKey tpdsProductKey = tpdsProductKeyDaoImpl.selectTpdsProductKeyByMerchantNo(merchantNo, productCode);
		String productKey = configServiceImpl.getProductKey(merchantNo, productCode);
		if(StringUtils.isBlank(productKey)) {
			return null;
		}
		TpdsProductKey tpdsProductKey = JsonMapperUtil.nonEmptyMapper().fromJson(productKey, TpdsProductKey.class);
		logger.info("查询商户签约key值,{},{},{}", merchantNo, productCode, tpdsProductKey.getSignKey());
		return tpdsProductKey.getSignKey();
	}

}
