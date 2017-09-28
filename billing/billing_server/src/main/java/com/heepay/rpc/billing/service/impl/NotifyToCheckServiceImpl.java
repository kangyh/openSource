package com.heepay.rpc.billing.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.SettleChannelManagerMapper;
import com.heepay.billing.entity.SettleChannelManager;
import com.heepay.common.util.AESCode;
import com.heepay.rpc.service.RpcService;

/**
 * 
 * 
 * 描    述：网关通知对账实现类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年8月17日下午2:49:54 
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
@RpcService(name = "NotifyToCheckServiceServiceImpl", processor = com.heepay.rpc.billing.service.NotifyToCheckService.Processor.class)
public class NotifyToCheckServiceImpl implements com.heepay.rpc.billing.service.NotifyToCheckService.Iface{
	
	@Autowired
	private SettleChannelManagerServiceImpl settleChannelManagerServiceImpl;
	
	@Autowired
	SettleChannelManagerMapper settleChannelManagerDaoImpl;
	
	private static final Logger logger = LogManager.getLogger();
	
	@Override
	public void notifyToCheck(String channelCode, String channelType, String remoteAdress, String format) {
		logger.info("网关通知对账开始，参数为{},{},{},{}", channelCode, channelType, remoteAdress , format);
		try {
			remoteAdress = "sftp" + remoteAdress;
			SettleChannelManager settleChannelManager = settleChannelManagerDaoImpl.getSettleChannelManager(channelCode, channelType, remoteAdress);
			if(settleChannelManager == null) {
				logger.info("网关通知对账，没有相关的通道配置，参数为{},{},{},{}", channelCode, channelType, remoteAdress , format);
				return;
			}
			String remoteUserName = AESCode.AESDncode(settleChannelManager.getRemoteUserName());
			String remotePassword = AESCode.AESDncode(settleChannelManager.getRemotePassword());
			String localFilePath = settleChannelManager.getLocalFilePath();
			String settleWay =settleChannelManager.getSettleWay();
			String ruleType =settleChannelManager.getRuleType();
			String port = settleChannelManager.getPort();
			settleChannelManagerServiceImpl.downloadFileSftp(channelCode, channelType, 
					remoteAdress , remoteUserName, remotePassword, localFilePath, settleWay, ruleType, port, format);
			logger.info("网关通知对账结束，参数为{},{},{},{}", channelCode, channelType, remoteAdress , format);
		} catch (Exception e) {
			logger.error("网关通知对账出错，参数为{},{},{},{}", channelCode, channelType, remoteAdress , format);
		}
	}


}
