/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年3月13日下午4:30:24
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
package com.heepay.rpc.billing.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.dao.SettleMerchantRecordMapper;
import com.heepay.billing.entity.SettleMerchantRecord;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.rpc.billing.message.SettleMerchantMessage;
import com.heepay.rpc.billing.producer.SettleMerchatProducerSender;
import com.heepay.vo.ClearMerchantDetailMessage;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年3月13日下午4:30:24
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring.xml")
public class SettleMerchantRecordServiceImplTest {

	@Autowired
	SettleMerchantRecordServiceImpl settleMerchantRecordServiceImpl;
	
	@Autowired
	SettleMerchantRecordMapper settleMerchantRecordDaoImpl;
	
	@Autowired
	ClearMerchantRecordMapper  clearMerchantRecordDaoImpl;
	
	@Autowired
	SettleMerchatProducerSender settleMerchatProducerSender;
	
	@Test
	public void testclearMerchantSummarizing(){
		
		try {
//			settleMerchantRecordServiceImpl.querySettleMessage(null);
			
			
			SettleMerchantRecord settleDetail = settleMerchantRecordDaoImpl.querySettleMerchantByBatchNo("19201706070094281");
			//根据批次号查询清算明细
//		    List<ClearMerchantDetailMessage> clearMessageList = clearMerchantRecordDaoImpl.queryClearDetailBySettleBatch(settleBathNo);
		    				    
		    //汇总后的结算批次+清算明细
			SettleMerchantMessage settleMessage = new SettleMerchantMessage();
			settleMessage.setSettleMerchantRecord(settleDetail);         //结算批次
//			settleMessage.setClearMerchantMessage(clearMessageList);     //清算明细 （清结算系统不再给账务系统推送结算明细modified by xuangang 2017-07-07）
			
			//将结算数据发送到队列
			settleMerchatProducerSender.sendDataToQueue(settleMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void main(){
		
		Map map = new HashMap<String, Object>();
		
		map.put("settleBatch", "B1234");
		
		map.put("pageNum", "0");
		
		map.put("pageSize", "1000");
		map.put("total：", "1000000");
		
		
		
		List<ClearMerchantDetailMessage> list = new ArrayList<ClearMerchantDetailMessage>();
		
		ClearMerchantDetailMessage msg = new ClearMerchantDetailMessage();
		
		msg.setFeeAmount(new BigDecimal("100"));
		msg.setRequestAmount(new BigDecimal("200"));
		msg.setSuccessAmount(new BigDecimal("100"));
		
		msg.setSuccessTime(new Date());
		msg.setTransNo("12345678");
		
		msg.setTransType("payment");
		
		map.put("detail", msg);
		
		String json = JsonMapperUtil.nonDefaultMapper().toJson(map);
		
		System.out.println(json);
		
	}
}
