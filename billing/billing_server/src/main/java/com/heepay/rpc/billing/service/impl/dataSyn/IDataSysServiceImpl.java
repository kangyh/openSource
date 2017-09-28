/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年3月29日下午2:23:25
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
package com.heepay.rpc.billing.service.impl.dataSyn;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.route.FetchRouteMessage;
import com.heepay.date.DateUtils;
import com.heepay.rpc.billing.service.IDataSysService;
import com.heepay.rpc.client.BillingClearAPIClient;
import com.heepay.rpc.client.ManagerServerClient;
import com.heepay.rpc.payment.model.ClearMerchantQueryRecordModel;
import com.heepay.vo.ProductVO;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年3月29日下午2:23:25
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
public class IDataSysServiceImpl implements IDataSysService {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	BillingClearAPIClient billingClearAPIClient;
	
	@Autowired
	ClearMerchantRecordMapper  clearMerchantRecordDaoImpl;
	
	@Autowired
	ClearChannelRecordMapper clearChannelRecordDaoImpl;
	
	@Autowired
	ManagerServerClient managerServerClient;

	@Transactional()
	@Override
	public void dataSynchronize(String transNo, String paymentId) {
		
		
		
		try{
			
			logger.info("清结算历史数据同步开始， 交易订单号：{}， 支付单号：{}", transNo, paymentId);
			
			ClearMerchantQueryRecordModel cvo = billingClearAPIClient.merchantQueryTransByPaymentId(paymentId);
			
			if(cvo == null){
				logger.info("清结算历史数据同步,查询交易系统数据为null交易订单号：{}， 支付单号：{}", transNo, paymentId);
				throw new Exception("清结算历史数据同步异常！");				
			}
			
			ClearMerchantRecord cm = clearMerchantRecordDaoImpl.findClearMerchantRecordByTansNo(transNo);
			
			String productName = "";
			
			if(cm != null){
				String merchantVo = managerServerClient.getProductVo(cm.getProductCode());
				
				//获取路由商户信息
				ProductVO merVo = FetchRouteMessage.getProductVO(merchantVo);
				
				if(merVo != null)
				productName = merVo.getProductName();
			}				
			
		     String payTime = cvo.getPayTime();
		     String createTime = cvo.getCreateTime();
		     
		     Map<String, Object> paraMap = new HashMap<String, Object>();
		     
		     paraMap.put("payTime", DateUtils.getStrDate(payTime, DateUtils.DATE_FORMAT_DATE_TIME));  //支付发起时间
		     paraMap.put("bankcardType",  cvo.getBankcardType()); //银行卡类型
		     paraMap.put("createTime", DateUtils.getStrDate(createTime, DateUtils.DATE_FORMAT_DATE_TIME));     //订单创建时间    
		     paraMap.put("bankSerialNo", cvo.getBankSerialNo()); //银行流水  
		     paraMap.put("bankId", cvo.getBankId());             //银行编码
		     paraMap.put("bankName", cvo.getBankName());         //银行名称
		     paraMap.put("payType", cvo.getPayType());           //支付类型 
		     paraMap.put("transNo", transNo);
		     paraMap.put("paymentId", paymentId);
		     paraMap.put("merchantName", cvo.getMerchantCompany());
		     paraMap.put("productName", productName);  //产品名称
		     
		     
		     clearChannelRecordDaoImpl.updateOldRecord(paraMap);
		     clearMerchantRecordDaoImpl.updateOldClearingmerchant(paraMap);
		}catch(Exception e){
			logger.error("历史数据修复异常！交易订单号：{}， 支付单号：{}", transNo, paymentId, e);
			throw new RuntimeException();
		}
        
	}

}
