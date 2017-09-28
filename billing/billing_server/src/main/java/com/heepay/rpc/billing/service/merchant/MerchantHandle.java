/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年5月22日下午4:46:51
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
package com.heepay.rpc.billing.service.merchant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;

import com.heepay.billing.dao.ClearingExceptionMapper;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.ClearingException;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.FeeDeductType;
import com.heepay.enums.billing.SettleDifferIsProfit;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年5月22日下午4:46:51
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
public abstract class MerchantHandle {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	ClearingExceptionMapper ClearingExceptionImpl;

	public ClearMerchantRecord getClearMerchantRecord(Map<String, String> map) {

		try {
			ClearMerchantRecord clearParam = new ClearMerchantRecord();

			clearParam.setMerchantId(new Integer(map.get("merchantId")));    	//用户编码
			clearParam.setMerchantType(map.get("merchantType"));				//用户类型
			clearParam.setProductCode(map.get("productCode"));					//业务类型（产品编码）
			clearParam.setCurrency(map.get("currency"));    					//币种(156:人民币)
			clearParam.setTransNo(map.get("transNo"));      					//交易订单号（支付单号）
			clearParam.setTransNoOld(map.get("transNoOld")); 					//原交易订单号（支付单号）
			
			//modified by xuangang 2016-11-03 使用成功支付金额替换原来的交易总金额
			clearParam.setRequestAmount(new BigDecimal(map.get("successAmount")));     //交易金额()
			clearParam.setSettleTime(DateUtils.getStr2Date(map.get("successTime")));    //清算日期，使用交易日期作为清算日期
			clearParam.setTransType(map.get("transType"));		         			   //交易类型（后来新增） 	
			clearParam.setSuccessTime(DateUtils.getStrDate(map.get("successTime"), DateUtils.DATE_FORMAT_DATE_TIME));	//交易支付成功时间yyyy-MM-dd HH:mm:ss	
			clearParam.setIsProfit(SettleDifferIsProfit.ACCOUNTFLGN.getValue());     //默认为不分润
			
			//modified by xuangang 2017-03-09 begin 新增字段
			clearParam.setBusiTime(DateUtils.getStrDate(map.get("busiTime"), DateUtils.DATE_FORMAT_DATE_TIME));//交易发起时间
			clearParam.setAgentName(map.get("agentName"));													   //代理名称
			clearParam.setAgentCode(map.get("agentCode")==null?null:new Long(map.get("agentCode")));           //代理商编码
			clearParam.setBankcardType(map.get("bankcardType"));    //银行卡类型
			clearParam.setMerchantName(map.get("merchantCompany"));    //商户名称
			
			clearParam.setAccountNo(map.get("accountNo"));            //账户编码
			clearParam.setPayTime(DateUtils.getStrDate(map.get("payTime"), DateUtils.DATE_FORMAT_DATE_TIME));  //支付发起时间
			clearParam.setCreateTime(DateUtils.getStrDate(map.get("createTime"), DateUtils.DATE_FORMAT_DATE_TIME)); //订单创建时间	
			clearParam.setPayType(map.get("payType"));                                                          ////支付类型			
						
			BigDecimal successAmount = new BigDecimal(map.get("successAmount"));     //交易金额（银行返回的）
			BigDecimal feeAmount = new BigDecimal(map.get("feeAmount"));             //手续费
			
			//modified by xuangang 2017-03-09  
            String feeWay = map.get("feeWay");		                                //手续费扣除方式
			
			if(StringUtil.isBlank(feeWay)){
				logger.error("商户侧手续费扣除方式为空:{}", map);
				return null;
			}else if(FeeDeductType.INNER.getValue().equals(feeWay)){  //坐扣
				//订单应结算金额 = 交易金额（成功支付金额） - 手续费;					
				BigDecimal successAmountPlan = successAmount.subtract(feeAmount);
				
				clearParam.setFee(feeAmount); 						 //手续费
				clearParam.setSettleAmountPlan(successAmountPlan);  //订单应结算金额
			}else{                                                   //外扣
				clearParam.setFee(feeAmount); 						 //手续费
				clearParam.setSettleAmountPlan(successAmount);      //订单应结算金额
			}
			
			//modified by xuangang 2017-05 begin
			clearParam.setSettleCyc(map.get("settleCyc"));   		//订单结算周期 (交易系统提供)
			clearParam.setFeeWay(map.get("feeWay"));         		//手续费扣除方式
			clearParam.setMerchantOrderNo(map.get("merchantOrderNo"));  //商户交易订单号
			
			return clearParam;
			
		} catch (Exception e) {
            logger.error("商户侧清算数据处理异常：{}", map, e);           
		}

		return null;
	}
	
	/**
	 * 
	 * @方法说明：
	 * @author xuangang
	 * @param msg
	 * @throws TException
	 * @时间：2017年8月10日下午2:22:11
	 */
	public void saveClearExceptionData(Map<String, String> map) {
		
		try{
			logger.info("清结算异常数据处理开始:{}", map);
			
			ClearingException cdata = new ClearingException();
			
			cdata.setMerchantId(map.get("merchantId"));     //商户编码
			cdata.setBankcardType(map.get("bankcardType"));    //银行卡类型
			cdata.setBankCode(map.get("bankId"));       //银行编码     
			cdata.setBankName(map.get("bankName"));       //银行名称
			cdata.setBankSeq(map.get("bankSerialNo"));        //银行流水号
			cdata.setChannelCode(map.get("channelCode"));    //通道编码 
			cdata.setCostWay(map.get("costWay"));        //手续费扣除方式  
			cdata.setCreateTime(map.get("createTime"));     //订单创建时间   
			
			cdata.setCurrency(map.get("currency"));       //币种
			cdata.setFee(map.get("feeAmount"));            //手续费
			cdata.setFeeWay(map.get("feeWay"));         //手续费扣除方式 
			cdata.setMerchantName(map.get("merchantCompany"));   //商户名称
			cdata.setMerchantOrderNo(map.get("merchantOrderNo")); //用户订单号  
			cdata.setMerchantType(map.get("merchantType"));    //用户类型
			cdata.setPayAmount(map.get("payAmount"));       //订单金额 
			
			cdata.setPaymentid(map.get("paymentID"));	  //支付单号   		
			cdata.setPaymentId(map.get("paymentId"));      //支付单号
			cdata.setPaymentIdOld(map.get("paymentIdOld"));   //原支付单号
			cdata.setPayTime(map.get("payTime"));        //支付发起时间
			cdata.setPayType(map.get("payType"));        //支付类型
			cdata.setProductCode(map.get("productCode"));    //产品编码
			cdata.setRequestAmount(map.get("successAmount"));  //交易金额
			
			cdata.setSettleCyc(map.get("settleCyc"));      //结算周期
			cdata.setStatus(map.get("status"));                  //交易数据异常
			cdata.setSuccessTime(map.get("successTime"));      //成功支付时间(yyyy-MM-dd hh:mi:ss)  
			cdata.setTransNo(map.get("transNo"));          //交易订单号
			cdata.setTransNoOld(map.get("transNoOld"));       //原交易订单号
			cdata.setTransType(map.get("transType"));        //交易类型
			
			cdata.setField1(DateUtils.getFormatDateDir(new Date(), DateUtils.DATE_FORMAT_DATE_TIME));
			
			ClearingExceptionImpl.insertSelective(cdata);		
			
		}catch(Exception e){
			logger.error("清结算异常数据保存失败:{}", map, e);
		}		
	}
}
