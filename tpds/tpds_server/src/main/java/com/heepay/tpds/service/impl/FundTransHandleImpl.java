/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年2月16日下午1:41:46
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
package com.heepay.tpds.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heepay.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.tpds.dao.TpdsMerchantH5Mapper;
import com.heepay.tpds.dao.TpdsObjectTransDetailMapper;
import com.heepay.tpds.dao.TpdsObjectTransMapper;
import com.heepay.tpds.entity.TpdsObjectTrans;
import com.heepay.tpds.entity.TpdsObjectTransDetail;
import com.heepay.tpds.service.IFundTransHandle;


/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年2月16日下午1:41:46
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
public class FundTransHandleImpl  implements IFundTransHandle{

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	TpdsObjectTransMapper tpdsObjectTransDaoImpl;
	@Autowired
	TpdsObjectTransDetailMapper TpdsObjectTransDetailDaoImpl;
	
	@Autowired
	TpdsMerchantH5Mapper tpdsMerchantH5DaoImpl;
	
	@Transactional()
	@SuppressWarnings("unchecked")
	@Override
	public String fundTransSave(String body) {
				
			if(StringUtil.notBlank(body)){
				
				 Map<String, Object> bodymap = JsonMapperUtil.nonEmptyMapper().fromJson(body, Map.class);
				 
				 logger.info("资金交易入库开始，{}", bodymap);
				 try{ 
				 String businessSeqNo = (String)bodymap.get("businessSeqNo");
				 
				 Map<String, String> paraMap = new HashMap<String, String>();
				 
				 paraMap.put("businessSeqNo", businessSeqNo);   //业务流水号
				 paraMap.put("flag", "1");                      //结果码
				 
				 //查询校验交易密码是否成功
				 Integer num = tpdsMerchantH5DaoImpl.queryMerchantH5BybusinessSeqNo(paraMap);
				 
				 //若交易密码交易成功，则资金数据入库
				 if(num>0){
					 List<Map<String, Object>> list = (List<Map<String, Object>>)bodymap.get("accountList");			 
					 
					 TpdsObjectTrans tpdsObjectTrans = new TpdsObjectTrans();
					 
					 tpdsObjectTrans.setBusinessSeqNo((String)bodymap.get("businessSeqNo"));     //业务流水号
					 tpdsObjectTrans.setBusiTradeType((String)bodymap.get("busiTradeType"));     //业务操作类型
					 tpdsObjectTrans.setEntrustflag((String)bodymap.get("entrustflag"));         //委托标识
					 tpdsObjectTrans.setObjId(String.valueOf(bodymap.get("objectId")));         //标的编号   modify by wyl 20170614 description: 用 string.valueof 强转字符串
					 tpdsObjectTrans.setNote((String)bodymap.get("note"));
					 
					 tpdsObjectTransDaoImpl.insertSelective(tpdsObjectTrans);  
					 
					 if(list == null){
						 return "";
					 }
					 //资金交易明细入库
					 for(Map<String, Object> accountMap:list){
						 
						 TpdsObjectTransDetail detail = new TpdsObjectTransDetail();
						 detail.setOderNo((String)accountMap.get("oderNo"));   
						 detail.setDebitAccountNo((String)accountMap.get("debitAccountNo"));  
						 detail.setCebitAccountNo((String)accountMap.get("cebitAccountNo"));  
						 detail.setCurrency((String)accountMap.get("currency"));
						 if(accountMap.get("amount") !=null) //增加为空判断 wyl 20170615
						 {
							 detail.setAmount(new BigDecimal((accountMap.get("amount")).toString()));   
						 }
						 if(accountMap.get("otherAmounttype") != null) {
							 detail.setOtherAmounttype((String) accountMap.get("otherAmounttype"));
						 }
						 if(accountMap.get("otherAmount") != null && StringUtils.isNotBlank(accountMap.get("otherAmount").toString())){
							 detail.setOtherAmount(new BigDecimal(accountMap.get("otherAmount").toString()));
						 }

						 TpdsObjectTransDetailDaoImpl.insertSelective(detail);
					 }
					 logger.info("资金交易入库完毕,标的编号:{}", bodymap.get("objectId")); 
				 }else {
					 logger.info("资金交易,交易密码校验失败：业务流水：{}", businessSeqNo); 
				 }			
			
		}catch(Exception e){			
			logger.info("资金交易入库异常:{}", bodymap, e);
			throw new RuntimeException();
		}
	  }
		return null;
	}

}
