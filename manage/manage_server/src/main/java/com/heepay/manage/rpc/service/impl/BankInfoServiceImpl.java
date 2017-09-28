/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.rpc.service.impl;

import com.google.common.collect.Maps;
import com.heepay.common.util.StringUtil;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.common.utils.JsonMapper;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.service.BankInfoService;
import com.heepay.manage.rpc.service.BankInfoThrift;
import com.heepay.redis.JedisClusterUtil;
import com.heepay.rpc.service.RpcService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * 描    述：银行信息查询接口
 *
 * 创 建 者： M.Z
 * 创建时间： 2016/10/11 14:56 
 * 创建描述：银行信息缓存出现异常时，手动查询接口
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
@RpcService(name = "bankInfoServiceImpl" , processor = com.heepay.manage.rpc.service.BankInfoService.Processor.class)
public class BankInfoServiceImpl implements com.heepay.manage.rpc.service.BankInfoService.Iface{

	@Autowired
	private BankInfoService bankInfoService;
	/**
	 * 查询出银行信息(目前只传bankNo,bankName)
	 *
	 */
	@Override
	public List<BankInfoThrift> queryBankInfoList() throws TException {
		List<BankInfo> bankInfos = bankInfoService.selectList();
		List<BankInfoThrift> bankInfoThrifts = new ArrayList<>();
		for(BankInfo bankInfo:bankInfos){
			BankInfoThrift bankInfoThrift = new BankInfoThrift();
			bankInfoThrift.setBankNo(bankInfo.getBankNo());
			bankInfoThrift.setBankName(bankInfo.getBankName());
			bankInfoThrifts.add(bankInfoThrift);
		}
		return bankInfoThrifts;
	}

	/**
	 * 查询出银行信息
	 */
	@Override
	public String queryBankInfo() throws TException {
		JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
        //查询Redis
		String value = jedisCluster.get(Constants.BANK_INFO_KEY);
        //如果缓存中没有则查询数据库并将这条数据放入缓存
		if (StringUtil.isBlank(value)){
			//查询数据
			List<BankInfo> bankInfos = bankInfoService.selectList();
			if(null!=bankInfos && !bankInfos.isEmpty()) {
				Map<String, String> bankMap = Maps.newHashMap();
				for (BankInfo bankInfo : bankInfos) {
					bankMap.put(bankInfo.getBankNo(), bankInfo.getBankName());
				}
				value = JsonMapper.toJsonString(bankMap);
				String finalValue = value;
				jedisCluster.set(Constants.BANK_INFO_KEY, finalValue);
			}
		}
		return value;
	}
}
