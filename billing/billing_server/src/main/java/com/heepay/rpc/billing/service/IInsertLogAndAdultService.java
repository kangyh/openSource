package com.heepay.rpc.billing.service;


public interface IInsertLogAndAdultService {
	
	public boolean insertAndAdult(String checkFileWhere, String checkFileFrom,String channelCode, String channelType, String fileName, String localFilePath, String  settleWay,String ruleType, String format);
}
