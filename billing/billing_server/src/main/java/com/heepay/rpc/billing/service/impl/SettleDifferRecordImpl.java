/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年6月26日下午1:43:54
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.dao.SettleDifferBillRecordMapper;
import com.heepay.billing.dao.SettleDifferRecordMapper;
import com.heepay.billing.dao.SettleWarnRecordMapper;
import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.SettleBillRecord;
import com.heepay.billing.entity.SettleDifferBillRecord;
import com.heepay.billing.entity.SettleDifferRecord;
import com.heepay.billing.entity.SettleWarnRecord;
import com.heepay.billing.route.FetchRouteMessage;
import com.heepay.billingutils.IdBatch;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.enums.ChannelPartner;
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.billing.BillingBillStatus;
import com.heepay.enums.billing.BillingDifferType;
import com.heepay.enums.billing.BillingWaringStatus;
import com.heepay.enums.billing.ClearingCheckStatus;
import com.heepay.enums.billing.SettleDifferIsProfit;
import com.heepay.enums.billing.WarnType;
import com.heepay.enums.risk.LevelOfRiskRule;
import com.heepay.rpc.billing.service.ISendMailService;
import com.heepay.rpc.client.BillingClearAPIClient;
import com.heepay.rpc.client.BillingCommonClient;
import com.heepay.rpc.client.PayChannelCacheServiceClient;
import com.heepay.rpc.payment.model.ClearChannelQueryModel;
import com.heepay.rpc.payment.model.ClearMerchantQueryRecordModel;
import com.heepay.vo.ChannelRouteMessage;

/***
 * 
 * 
 * 描    述：其他差异处理
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年6月26日下午1:43:54
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
public class SettleDifferRecordImpl implements ISendMailService{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	BillingClearAPIClient billingClearAPIClient;

	@Resource(name = "billingCommonClient")
	BillingCommonClient billingCommonClient;
	
	@Autowired
	SettleWarnRecordMapper settleWarnRecordDaoImpl;
	
	@Autowired
	SettleDifferRecordMapper settleDifferRecordDaoImpl;
	
	@Autowired
	DoneSettleDifferRecordServiceImpl doneSettleDifferRecordServiceImpl;
	
	@Autowired
	ClearChannelRecordMapper clearChannelRecordDaoImpl;
	
	@Autowired
	ClearMerchantRecordMapper clearMerchantRecordDaoImpl;
	
	@Autowired
	PayChannelCacheServiceClient payChannelCacheServiceClient;
	
	@Autowired
	SettleDifferBillRecordMapper SettleDifferBillRecordDao;
	
	@Transactional()
	@Override
	public boolean settleDifferRecordChannel(String tranNo,	ClearChannelRecord cRecord) {		
		if(cRecord == null){
			return false;
		}
		try {
			// 插入告警记录表
			insertSettleWarnRecord(tranNo);
			// 把通道侧数据改为已对账
			clearChannelRecordDaoImpl.updateClearChannelRecordByTransNo(tranNo);
			
			logger.info("告警记录更改通道侧状态为已对账:" + tranNo);

			// 从交易系统查询merchantId
			ClearMerchantQueryRecordModel clearVO = billingClearAPIClient.merchantQueryTransByPaymentId(cRecord.getPaymentId());
			logger.info("商户侧没有数据，通道侧有数据时处理其他差异调交易系统返回商户侧数据{}",clearVO);
			SettleDifferRecord settleDifferRecord = new SettleDifferRecord();
			if (null != clearVO) {
				settleDifferRecord.setMerchantId(clearVO.getMerchantId() == null? null: Integer.valueOf(clearVO.getMerchantId())); // 商品编码
				settleDifferRecord.setProductCode(clearVO.getProductCode()); // 产品编码
				
				// 非空值替换
				clearVO.setFeeAmount(clearVO.getFeeAmount()==null?"0.0000":clearVO.getFeeAmount());
				clearVO.setRequestAmount(clearVO.getRequestAmount()==null?"0.0000":clearVO.getRequestAmount());				

			    //应结算金额
				settleDifferRecord.setSettleAmountPlan(calculateRequestAmoutPlan(clearVO.getRequestAmount(), clearVO.getFeeAmount(), clearVO.getFeeWay()));
				settleDifferRecord.setFeeAmount(new BigDecimal(clearVO.getFeeAmount())); // 手续费
				
				settleDifferRecord.setMerchantOrderNo(clearVO.getMerchantOrderNo()); // 商户交易订单号			
				settleDifferRecord.setSuccessTime(DateUtils.getStrDate(clearVO.getSuccessTime(),"yyyy-MM-dd HH:mm:ss")); // 成功支付时间								
				settleDifferRecord.setPayTime(DateUtils.getStrDate(clearVO.getPayTime(),"yyyy-MM-dd HH:mm:ss")); // 支付发起时间					
							
				//商户名称			
				settleDifferRecord.setMerchantName(clearVO.getMerchantCompany()); //商户名称						
				settleDifferRecord.setFeeWay(clearVO.getFeeWay()); //手续费扣除方式				
				settleDifferRecord.setBankcardType(clearVO.getBankcardType()); //银行卡类型			
				settleDifferRecord.setBusiTime(DateUtils.getStrDate(clearVO.getCreateTime(),"yyyy-MM-dd HH:mm:ss")); //交易发起时间
					
				settleDifferRecord.setPayType(clearVO.getPayType()); //支付类型						
				settleDifferRecord.setBankSeq(clearVO.getBankSerialNo()); //银行流水号 
				settleDifferRecord.setBankCode(clearVO.getBankId()); //银行编码
				settleDifferRecord.setBankName(clearVO.getBankName()); //银行名称				
				settleDifferRecord.setIsZip("unzip");
				settleDifferRecord.setPayType(clearVO.getCreateTime());				
			}
			
			settleDifferRecord.setPaymentId(cRecord.getPaymentId()); // 支付单号
			settleDifferRecord.setChannelType(cRecord.getChannelType()); // 通道类型
			settleDifferRecord.setChannelCode(cRecord.getChannelCode()); // 通道编码
			settleDifferRecord.setCostAmount(cRecord.getCostAmount()); // 成本
			settleDifferRecord.setTransNo(cRecord.getTransNo()); // 交易单号
			settleDifferRecord.setChannelName(cRecord.getChannelName()); // 通道名称		
			
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("paymentId", settleDifferRecord.getPaymentId());
			
			int num = settleDifferRecordDaoImpl.countDifferRecordNumByPaymentId(paraMap);
			if(num > 0){
				logger.info("该交易记录差异表已存在，交易单号：{}", cRecord.getTransNo());
				
				saveSettleDifferBillRecord(cRecord);  //差异表已存在，入对账异常表
				
				return false;
			}			
			// 告警记录插入到差异表
			saveSettleDifferRecord(null,settleDifferRecord, cRecord);
			return true;
		} catch (Exception e) {
			logger.error("商户侧没有数据时,告警记录处理失败:",e);
			throw new RuntimeException();
		}		
	}

	@Override
	public boolean settleDifferRecordMerchant(String tranNo, ClearMerchantRecord mRecord) {		
		try{
			insertSettleWarnRecord(tranNo);
			// 把商户侧数据改为已对账
			clearMerchantRecordDaoImpl.updateClearMerchantRecordByTransNo(mRecord.getTransNo());
			SettleDifferRecord settleDifferRecord = new SettleDifferRecord();
			//从交易系统查询通道侧数据，参数transNo
			ClearChannelQueryModel channelModel = billingClearAPIClient.channelQueryTransByTransNo(mRecord.getTransNo());
			logger.info("其他差异,通道侧无清算数据，从交易系统获取的通道信息：{}",channelModel);
			if (channelModel == null) {
				logger.info("通过交易单号查询交易系统数据为空,交易单号：{}", mRecord.getTransNo());
				return false;				
			} else {
				//根据channelCode查询路由信息
				String channelByChannelCode = payChannelCacheServiceClient.getChannelByChannelCode(channelModel.getChannelCode());
				logger.info("其他差异处理时,通道侧没有数据时根据channelCode获取路由信息{}",channelByChannelCode);
				// 解析路由信息
				ChannelRouteMessage routeChannelMesage = FetchRouteMessage.getRouteChannelMesage(channelByChannelCode);
				if (routeChannelMesage == null) {
					logger.info("通道侧从路由获取到的信息为空:{}", channelModel);
					return false;
					
				} else {
					if (StringUtil.notBlank(routeChannelMesage.getChannelPartnerCode())) {
						//如果是直连银行，channelCode格式为
						//DIRCON-银行号，名称为银行名称；如果是第三方支付通道，channelCode为合作方编码，名称是合作方名称
						if (ChannelPartner.DIRECTCONNECTION.getValue().equals(routeChannelMesage.getChannelPartnerCode())) {
							settleDifferRecord.setChannelCode(
									ChannelPartner.DIRECTCONNECTION.getValue() + "-" + routeChannelMesage.getBankNo());
							settleDifferRecord.setChannelName(routeChannelMesage.getBankName());
						} else {
							settleDifferRecord.setChannelCode(routeChannelMesage.getChannelPartnerCode());
							settleDifferRecord.setChannelName(routeChannelMesage.getChannelPartnerName());
						}
						
						settleDifferRecord.setBankName(routeChannelMesage.getBankName()); // 银行名称
						settleDifferRecord.setBankCode(routeChannelMesage.getBankNo()); // 银行编码
						settleDifferRecord.setChannelProvider(routeChannelMesage.getChannelPartnerName()); // 提供者
							
					} else {
						logger.info("通道侧从路由获取到的信息ChannelPartnerCode有误:{}", routeChannelMesage.getChannelPartnerCode());
					}
				}
				// 为告警记录插入到差异表准备数据
				settleDifferRecord.setChannelType(routeChannelMesage.getChannelTypeCode()); // 通道类型
				settleDifferRecord.setPaymentId(channelModel.getPaymentId()); // 支付单号
				settleDifferRecord.setTransType(channelModel.getTransType()); // 交易类型
				settleDifferRecord.setRequestAmount(new BigDecimal(channelModel.getPayAmount()));  //支付金额
				// 告警记录插入到差异表
				saveSettleDifferRecord(mRecord,settleDifferRecord, null);
				logger.info("商户侧有数据,通道侧没有数据时告警记录插入差异表{}", mRecord);
			}
			return true;
		}catch(Exception e){
			logger.error("通道侧没有数据时,告警记录插入差异表失败" , e);
			throw new RuntimeException();
		}
	}
	 /**
     * @描述 商户侧、通道侧都存在的其他差异
     * @author xuangang
     * @date 2017-06-26
     */
	@Transactional
	public boolean settleDifferRecord(ClearChannelRecord cRecord, ClearMerchantRecord mRecord) {
		
		if(cRecord == null || mRecord == null){
			logger.info("其他差异，商户侧或通道侧清算数据为空！");
			return false;
		}
		try{
			// 插入告警记录表
			insertSettleWarnRecord(mRecord.getTransNo());
			
			// 把通道侧数据改为已对账
			clearChannelRecordDaoImpl.updateClearChannelRecordByTransNo(cRecord.getTransNo());			
			// 把商户侧数据改为已对账
			clearMerchantRecordDaoImpl.updateClearMerchantRecordByTransNo(mRecord.getTransNo());
			
			logger.info("其他差异修改通道侧、商户侧清算数据为已对账，交易单号：{}", cRecord.getTransNo());
			
			Map<String, String> paraMap = new HashMap<String, String>();
			paraMap.put("paymentId", cRecord.getPaymentId());
			
			int num = settleDifferRecordDaoImpl.countDifferRecordNumByPaymentId(paraMap);
			if(num > 0){
				logger.info("该交易记录差异表已存在，支付单号：{}", cRecord.getPaymentId());
				
				saveSettleDifferBillRecord(cRecord);   //差异表已存在，入对账异常表
				
				return false;
			}
			
			SettleDifferRecord sDiffer = new SettleDifferRecord();			
			saveSettleDifferRecord(mRecord, sDiffer, cRecord);    //保存差异记录
			
			logger.info("清算记录保存差异表成功，支付单号：{}", cRecord.getPaymentId());
			return true;
		}catch (Exception e) {
			logger.error("商户侧没有数据时,告警记录处理失败，支付单号：{}", cRecord.getPaymentId(), e);
			throw new RuntimeException();
		}	
	}
	/**
	 * 
	 * @方法说明：保存其他差异数据
	 * @author xuangang
	 * @param settleDifferRecord
	 * @时间：2017年6月26日下午2:13:44
	 */
	private void saveDifferRecord(SettleDifferRecord settleDifferRecord){
		settleDifferRecord.setDifferType(BillingDifferType.BDTYPEO.getValue()); // 差异类型
		settleDifferRecord.setErrorDate(new Date()); // 差错日期
		settleDifferRecord.setHandleResult(BillingBillStatus.BBSTATUSN.getValue()); // 处理状态
		settleDifferRecord.setIsBill(ClearingCheckStatus.CHECKSTATUSN.getValue());// 记账标记
		settleDifferRecord.setIsProfit(SettleDifferIsProfit.ACCOUNTFLGN.getValue());// 是否分润
																					// 默认N（否）
		settleDifferRecord.setSettleBath(IdBatch.getSettleBathString(0));// 结算批次(为其他差异和人工审核未通过的数据调用挂账接口做准备)
		// 存储差异表
		settleDifferRecordDaoImpl.insertOtherSettleDifferRecord(settleDifferRecord);
	}	
	
	/**
	 * 
	 * @方法说明：存储告警记录
	 * @author xuangang
	 * @param transNo
	 * @时间：2017年6月26日下午2:25:38
	 */
	private void insertSettleWarnRecord(String transNo) {
		// 插入告警记录表
		SettleWarnRecord settleWarnRecord = new SettleWarnRecord();
		settleWarnRecord.setContactPerson(billingCommonClient.getContactPerson());
		settleWarnRecord.setHandleType(WarnType.SENDEMAIL.getValue());
		settleWarnRecord.setStatus(BillingWaringStatus.STATUSN.getValue());
		settleWarnRecord.setWarnContext("交易订单号为" + transNo + "的数据发生差异");
		settleWarnRecord.setWarnLevel(LevelOfRiskRule.LEVEL_OF_RISK_RELE_3.getValue());
		settleWarnRecord.setWarnBlock(billingCommonClient.getWarnBlock());
		settleWarnRecord.setOperTime(new Date());
		settleWarnRecordDaoImpl.insert(settleWarnRecord);
		logger.info("清算记录插入告警表成功，交易订单号：{}", transNo);
	}
	
   
	/**
	 * 
	 * @方法说明：通道侧、商户侧都存在的其他差异
	 * @author xuangang
	 * @param mRecord
	 * @param settleDifferRecord
	 * @param cRecord
	 * @throws Exception
	 * @时间：2017年6月26日下午3:18:59
	 */
	public void saveSettleDifferRecord(ClearMerchantRecord mRecord, SettleDifferRecord settleDifferRecord,
			ClearChannelRecord cRecord) throws Exception {
		try {
			// 通道侧清算记录
			if (null != cRecord) {
				settleDifferRecord.setChannelName(cRecord.getChannelName()); // 通道名称
				settleDifferRecord.setRequestAmount(cRecord.getSuccessAmount());// 支付金额（订单金额）
				settleDifferRecord.setTransType(cRecord.getTransType()); // 交易类型
				settleDifferRecord.setBankSeq(cRecord.getBankSeq()); // 流水
				settleDifferRecord.setBankName(cRecord.getBankName()); // 银行名称
				settleDifferRecord.setBankCode(cRecord.getBankCode()); // 银行编码
				settleDifferRecord.setChannelProvider(cRecord.getChannelProvider()); // 提供者
				
				settleDifferRecord.setPaymentId(cRecord.getPaymentId()); // 支付单号
				settleDifferRecord.setChannelType(cRecord.getChannelType()); // 通道类型
				settleDifferRecord.setChannelCode(cRecord.getChannelCode()); // 通道编码
				settleDifferRecord.setCostAmount(cRecord.getCostAmount()); // 成本			
			}
			// 用户侧清算记录
			if (null != mRecord) {
				settleDifferRecord.setMerchantId(mRecord.getMerchantId()); // 用户编码
				settleDifferRecord.setTransNo(mRecord.getTransNo()); // 交易单号
				settleDifferRecord.setProductCode(mRecord.getProductCode()); // 产品编码
				settleDifferRecord.setSettleAmountPlan(mRecord.getSettleAmountPlan()); // 订单应结算金额
				settleDifferRecord.setFeeAmount(mRecord.getFee()); // 手续费				
				
				//modified by xuangang 2017-03-10  添加商户字段 begin 
				settleDifferRecord.setMerchantName(mRecord.getMerchantName());   //商户名称
				settleDifferRecord.setBankcardType(mRecord.getBankcardType());   //银行卡类型
				settleDifferRecord.setFeeWay(mRecord.getFeeWay());               //手续费扣除方式
				settleDifferRecord.setBusiTime(mRecord.getBusiTime());           //交易发起时间
				settleDifferRecord.setPayTime(mRecord.getSettleTime());          //支付发起时间
				settleDifferRecord.setSuccessTime(mRecord.getSuccessTime());     //成功支付时间
				settleDifferRecord.setAgentCode(mRecord.getAgentCode());         //代理商编码
				settleDifferRecord.setAgentName(mRecord.getAgentName());         //代理商名称
				settleDifferRecord.setPayType(mRecord.getPayType());             //订单创建时间
				settleDifferRecord.setAccountNo(mRecord.getAccountNo());            //账户编码
				settleDifferRecord.setProductName(mRecord.getProductName());     //产品名称
			}
			 saveDifferRecord(settleDifferRecord);

		} catch (Exception e) {
			logger.error("其他差异插入差异表异常,交易单号：{}", settleDifferRecord.getTransNo(), e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @方法说明：计算应结算金额
	 * @author xuangang
	 * @param requestAmount
	 * @param feeAmount
	 * @param feeWay
	 * @return
	 * @时间：2017年6月26日下午5:47:08
	 */
	private BigDecimal calculateRequestAmoutPlan(String requestAmount, String feeAmount, String feeWay){
		
		if (ChargeDeductType.INTERNAL_DEDUCT.getValue().equals(feeWay)) {// 坐扣
			if (StringUtil.isBlank(requestAmount)) {
				return null;
			} else {
				if (StringUtil.isBlank(feeAmount)) {
					return new BigDecimal(requestAmount);
				} else {
					return new BigDecimal(requestAmount).subtract(new BigDecimal(feeAmount));
				}
			}
		} else {// 外扣
			if (StringUtil.notBlank(requestAmount)) 
				return new BigDecimal(requestAmount);
		}
		return null;
	}
	
	/**
	 * 
	 * @方法说明：对账异常表入库
	 * @author xuangang
	 * @param settleBillRecord
	 * @时间：2017年7月28日上午10:58:38
	 */
	private void saveSettleDifferBillRecord(ClearChannelRecord cRecord){
		try{
			SettleDifferBillRecord settleDifferBillRecord = new SettleDifferBillRecord();
			settleDifferBillRecord.setChannelCode(cRecord.getChannelCode());             //通道编码
			settleDifferBillRecord.setChannelType(cRecord.getChannelType());             //通道类型
			settleDifferBillRecord.setCheckBathNo(cRecord.getCheckBath());
			settleDifferBillRecord.setPaymentId(cRecord.getPaymentId());                 //支付单号
			settleDifferBillRecord.setSaveTime(DateUtils.getDate());
			settleDifferBillRecord.setSuccessAmount(cRecord.getSuccessAmount());         //成功支付金额
			settleDifferBillRecord.setFee(cRecord.getCostAmount());                      //书续费
			settleDifferBillRecord.setChannleNo(cRecord.getBankSeq());                   //上游流水
			settleDifferBillRecord.setDifferType("CF");                           //未知差异重复入库
//			settleDifferBillRecord.setFeeService(cRecord.getFeeService());
//			settleDifferBillRecord.setPromotionAmount(cRecord.getPromotionAmount());
			settleDifferBillRecord.setRemark(cRecord.getRemark());

			SettleDifferBillRecordDao.insert(settleDifferBillRecord);
		}catch(Exception e){
			logger.error("其他差异异常，其他差异入对账异常表失败：{}", cRecord.getPaymentId(), e);
		}		
	}
}
