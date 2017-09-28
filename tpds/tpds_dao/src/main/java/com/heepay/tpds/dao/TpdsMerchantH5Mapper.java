package com.heepay.tpds.dao;

import java.util.Map;

import com.heepay.tpds.entity.TpdsMerchantH5;


public interface TpdsMerchantH5Mapper {
   
    int insert(TpdsMerchantH5 record);
    int update(TpdsMerchantH5 record);
    
	/**
	 * 
	 * @方法说明：查询交易密码交易成功的记录
	 * @author xuangang
	 * @param map
	 * @return
	 * @时间：2017年2月18日下午4:48:23
	 */
	Integer queryMerchantH5BybusinessSeqNo(Map<String, String> map);
	
	/**
	 * 
	 * @方法说明：交易密码校验，银行回调更新结果
	 * @author xuangang
	 * @param record
	 * @时间：2017年2月21日下午4:05:18
	 */
	void updateBybusinessSeqNo(TpdsMerchantH5 record);

	/**
	 * 
	 * @方法说明：业务流水号是否存在
	 * @author xuangang
	 * @param map
	 * @return
	 * @时间：2017年4月17日下午4:55:14
	 */
	Integer countMerchantH5BybusinessSeqNo(Map<String, String> map);
	
	/**
	 * 
	 * @方法说明：
	 * @author xuangang
	 * @param map
	 * @return
	 * @时间：2017年4月18日下午4:10:07
	 */
	TpdsMerchantH5 queryMerchantBybusinessSeqNo(Map<String, String> map);
	
	int insertMerchantH5(TpdsMerchantH5 record);
}