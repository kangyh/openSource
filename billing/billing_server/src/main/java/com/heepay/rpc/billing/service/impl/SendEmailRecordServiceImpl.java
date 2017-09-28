package com.heepay.rpc.billing.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.dao.SettleDifferRecordMapper;
import com.heepay.billing.dao.SettleWarnRecordMapper;
import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.SettleDifferRecord;
import com.heepay.billing.entity.SettleWarnRecord;
import com.heepay.billing.route.FetchRouteMessage;
import com.heepay.billingutils.IdBatch;
import com.heepay.billingutils.util.SendMail;
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
import com.heepay.rpc.client.ManagerServerClient;
import com.heepay.rpc.client.PayChannelCacheServiceClient;
import com.heepay.rpc.payment.model.ClearChannelQueryModel;
import com.heepay.rpc.payment.model.ClearMerchantQueryRecordModel;
import com.heepay.vo.ChannelRouteMessage;
import com.heepay.vo.MerchantProductVO;

/**
 * *
 * 
 * 
 * 描 述：告警记录插入告警记录表，更改差异状态实现类
 *
 * 创 建 者： wangjie 创建时间： 2016年11月21日下午4:59:47 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Component
public class SendEmailRecordServiceImpl implements ISendMailService {

	@Autowired
	ClearChannelRecordMapper clearChannelRecordDaoImpl;

	@Autowired
	SettleDifferRecordMapper settleDifferRecordDaoImpl;

	@Autowired
	ClearMerchantRecordMapper clearMerchantRecordDaoImpl;

	@Autowired
	SettleWarnRecordMapper settleWarnRecordDaoImpl;

	@Autowired
	BillingClearAPIClient billingClearAPIClient;

	@Autowired
	ManagerServerClient managerServerClient;
	
	@Autowired
	PayChannelCacheServiceClient payChannelCacheServiceClient;
	
	@Autowired
	DoneSettleDifferRecordServiceImpl doneSettleDifferRecordServiceImpl;

	@Resource(name = "billingCommonClient")
	BillingCommonClient billingCommonClient;

	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * 商户侧没有数据，通道侧有数据时处理其他差异数据
	 */
	@Transactional
	@Override
	public boolean settleDifferRecordChannel(String tranNo, ClearChannelRecord clearChannelRecord) {

		try {
			// 插入告警记录表
			insertSettleWarnRecord(tranNo);

			// 把通道侧数据改为已对账
			clearChannelRecordDaoImpl.updateClearChannelRecordByTransNo(tranNo);
			logger.info("告警记录更改通道侧状态为已对账:" + tranNo);

			// 从交易系统查询merchantId
			ClearMerchantQueryRecordModel clearMerchantQueryRecordVO = billingClearAPIClient
					.merchantQueryTransByPaymentId(clearChannelRecord.getPaymentId());
			logger.info("商户侧没有数据，通道侧有数据时处理其他差异调交易系统返回商户侧数据{}",clearMerchantQueryRecordVO);
			SettleDifferRecord settleDifferRecord = new SettleDifferRecord();
			if (null != clearMerchantQueryRecordVO) {
				settleDifferRecord.setMerchantId(clearMerchantQueryRecordVO.getMerchantId() == null ? null
						: Integer.valueOf(clearMerchantQueryRecordVO.getMerchantId())); // 商品编码
				settleDifferRecord.setProductCode(clearMerchantQueryRecordVO.getProductCode()); // 产品编码
				
				// 非空值替换
				clearMerchantQueryRecordVO.setFeeAmount(clearMerchantQueryRecordVO.getFeeAmount() == null ? "0.0000"
						: clearMerchantQueryRecordVO.getFeeAmount());
				clearMerchantQueryRecordVO.setRequestAmount(clearMerchantQueryRecordVO.getRequestAmount() == null
						? "0.0000" : clearMerchantQueryRecordVO.getRequestAmount());
				
				//应结算金额
				if(StringUtil.isBlank(clearMerchantQueryRecordVO.getRequestAmount()) &&
						StringUtil.isBlank(clearMerchantQueryRecordVO.getFeeAmount())){
					settleDifferRecord = doneSettleDifferRecordServiceImpl.getAmountPlanOnCostWay(clearMerchantQueryRecordVO, settleDifferRecord);
					settleDifferRecord.setFeeAmount(new BigDecimal(clearMerchantQueryRecordVO.getFeeAmount())); // 手续费
				}
				logger.info("告警记录，获取交易系统支付金额为：{}", clearMerchantQueryRecordVO.getRequestAmount());
				logger.info("告警记录，获取交易系统手续费金额为：{}", clearMerchantQueryRecordVO.getFeeAmount());
				
				//商户交易订单号
				if(StringUtil.isBlank(clearMerchantQueryRecordVO.getMerchantOrderNo())){
					logger.info("告警记录，获取交易系统商户交易订单号[MerchantOrderNo]为null：{}", clearMerchantQueryRecordVO.getMerchantOrderNo());
					settleDifferRecord.setMerchantOrderNo(null); // 商户交易订单号
				}else{
					settleDifferRecord.setMerchantOrderNo(clearMerchantQueryRecordVO.getMerchantOrderNo()); // 商户交易订单号
				}
				//成功支付时间
				if(StringUtil.isBlank(clearMerchantQueryRecordVO.getSuccessTime())){
					logger.info("告警记录，获取交易系统成功支付时间[SuccessTime]为null：{}", clearMerchantQueryRecordVO.getSuccessTime());
					settleDifferRecord.setSuccessTime(null); // 成功支付时间
				}else{
					settleDifferRecord.setSuccessTime(DateUtils.getStrDate(clearMerchantQueryRecordVO.getSuccessTime(),"yyyy-MM-dd HH:mm:ss")); // 成功支付时间
				}
				//支付发起时间
				if(StringUtil.isBlank(clearMerchantQueryRecordVO.getPayTime())){
					logger.info("告警记录，获取交易系统支付发起时间[PayTime]为null：{}", clearMerchantQueryRecordVO.getPayTime());
					settleDifferRecord.setPayTime(null); // 支付发起时间
				}else{
					settleDifferRecord.setPayTime(DateUtils.getStrDate(clearMerchantQueryRecordVO.getPayTime(),"yyyy-MM-dd HH:mm:ss")); // 支付发起时间
				}
				//商户名称
				if(StringUtil.isBlank(clearMerchantQueryRecordVO.getMerchantCompany())){
					logger.info("告警记录，获取交易系统商户名称[MerchantCompany]为null：{}", clearMerchantQueryRecordVO.getMerchantCompany());
					settleDifferRecord.setMerchantName(null); //商户名称
				}else{
					settleDifferRecord.setMerchantName(clearMerchantQueryRecordVO.getMerchantCompany()); //商户名称
				}
				//手续费扣除方式
				if(StringUtil.isBlank(clearMerchantQueryRecordVO.getFeeWay())){
					logger.info("告警记录，获取交易系统手续费扣除方式[FeeWay]为null：{}", clearMerchantQueryRecordVO.getFeeWay());
					settleDifferRecord.setFeeWay(null); //手续费扣除方式
				}else{
					settleDifferRecord.setFeeWay(clearMerchantQueryRecordVO.getFeeWay()); //手续费扣除方式
				}
				//银行卡类型
				if(StringUtil.isBlank(clearMerchantQueryRecordVO.getBankcardType())){
					logger.info("告警记录，获取交易系统银行卡类型[BankcardType]为null：{}", clearMerchantQueryRecordVO.getBankcardType());
					settleDifferRecord.setBankcardType(null); //银行卡类型
				}else{
					settleDifferRecord.setBankcardType(clearMerchantQueryRecordVO.getBankcardType()); //银行卡类型
				}
				//交易发起时间
				if(StringUtil.isBlank(clearMerchantQueryRecordVO.getCreateTime())){
					logger.info("告警记录，获取交易系统交易发起时间[CreateTime]为null：{}", clearMerchantQueryRecordVO.getCreateTime());
					settleDifferRecord.setBusiTime(null); //交易发起时间
				}else{
					settleDifferRecord.setBusiTime(DateUtils.getStrDate(clearMerchantQueryRecordVO.getCreateTime(),"yyyy-MM-dd HH:mm:ss")); //交易发起时间
				}
//				//账户编码
//				if(StringUtil.isBlank(clearMerchantQueryRecordVO.getAccountNo())){
//					logger.info("告警记录，获取交易系统账户编码[AccountNo]为null：{}", clearMerchantQueryRecordVO.getAccountNo());
//					settleDifferRecord.setAccountNo(null); //账户编码
//				}else{
//					settleDifferRecord.setAccountNo(clearMerchantQueryRecordVO.getAccountNo()); //账户编码
//				}
				//支付类型
				if(StringUtil.isBlank(clearMerchantQueryRecordVO.getPayType())){
					logger.info("告警记录，获取交易系统支付类型[PayType]为null：{}", clearMerchantQueryRecordVO.getPayType());
					settleDifferRecord.setPayType(null); //支付类型
				}else{
					settleDifferRecord.setPayType(clearMerchantQueryRecordVO.getPayType()); //支付类型
				}
//				//通道提供者 
//				if(StringUtil.isBlank(clearMerchantQueryRecordVO.getChannelProvider())){
//					logger.info("告警记录，获取交易系统通道提供者[ChannelProvider]为null：{}", clearMerchantQueryRecordVO.getChannelProvider());
//					settleDifferRecord.setChannelProvider(null); //通道提供者 
//				}else{
//					settleDifferRecord.setChannelProvider(clearMerchantQueryRecordVO.get; //通道提供者 
//				}
				//银行流水号
				if(StringUtil.isBlank(clearMerchantQueryRecordVO.getBankSerialNo())){
					logger.info("告警记录，获取交易系统银行流水号[ChannelProvider]为null：{}", clearMerchantQueryRecordVO.getBankSerialNo());
					settleDifferRecord.setBankSeq(null); //银行流水号
				}else{
					settleDifferRecord.setBankSeq(clearMerchantQueryRecordVO.getBankSerialNo()); //银行流水号 
				}
				//银行编码
				if(StringUtil.isBlank(clearMerchantQueryRecordVO.getBankId())){
					logger.info("告警记录，获取交易系统银行编码 [ChannelProvider]为null：{}", clearMerchantQueryRecordVO.getBankId());
					settleDifferRecord.setBankCode(null); //银行编码 
				}else{
					settleDifferRecord.setBankCode(clearMerchantQueryRecordVO.getBankId()); //银行编码
				}
				//银行名称
				if(StringUtil.isBlank(clearMerchantQueryRecordVO.getBankName())){
					logger.info("告警记录，获取交易系统银行名称[ChannelProvider]为null：{}", clearMerchantQueryRecordVO.getBankName());
					settleDifferRecord.setBankName(null); //银行名称 
				}else{
					settleDifferRecord.setBankName(clearMerchantQueryRecordVO.getBankName()); //银行名称
				}
				
				settleDifferRecord.setIsZip("unzip");
				settleDifferRecord.setPayType(clearMerchantQueryRecordVO.getCreateTime());
				
			}
			// 插入差异表
			insertChannelSettleDifferRecord(clearChannelRecord, settleDifferRecord);

			return true;
		} catch (Exception e) {
			logger.error("商户侧没有数据时,告警记录处理失败:",e);
			throw new RuntimeException();
		}

	}
	
	/**
	 * 商户侧有数据时处理其他差异数据
	 */
	@Transactional
	@Override
	public boolean settleDifferRecordMerchant(String tranNo, ClearMerchantRecord clearMerchantRecord) {

		// 根据商户侧tranNo查询通道侧数据
		ClearChannelRecord clearChannelRecord = clearChannelRecordDaoImpl.queryClearChannelRecordByTranNo(tranNo);

		if (null == clearChannelRecord) {
			try {
				// 插入告警记录表
				insertSettleWarnRecord(tranNo);
				// 把商户侧数据改为已对账
				clearMerchantRecordDaoImpl.updateClearMerchantRecordByTransNo(tranNo);
				logger.info("通道侧没有数据,告警记录更改商户侧状态为已对账:" + tranNo);

				// 插入差异表
				insertSettleDifferRecordMerchantRecord(clearMerchantRecord);

				return true;
			} catch (Exception e) {
				logger.error("通道侧没有数据时,告警记录插入差异表失败" , e);
				throw new RuntimeException();
			}

		} else {
			try {
				// 插入告警记录表
				insertSettleWarnRecord(tranNo);

				// 把通道侧数据改为已对账
				clearChannelRecordDaoImpl.updateClearChannelRecordByTransNo(tranNo);
				logger.info("告警记录更改通道侧状态为已对账:" + tranNo);
				// 把商户侧数据改为已对账
				clearMerchantRecordDaoImpl.updateClearMerchantRecordByTransNo(tranNo);
				logger.info("告警记录更改商户侧状态为已对账:" + tranNo);

				// 更新差异表
				insertMerchantRecordSettleDifferRecord(clearMerchantRecord);

				return true;
			} catch (Exception e) {
				logger.error("商户侧，通道侧都有数据时,告警记录插入差异表失败", e);
				throw new RuntimeException();
			}
		}
	}

	public void insertSettleWarnRecord(String transNo) {
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
	}

	// 商户侧没有数据，由通道侧组装差异数据
	public void insertChannelSettleDifferRecord(ClearChannelRecord clearChannelRecord,
			SettleDifferRecord settleDifferRecord) throws Exception {
	
		if (null != clearChannelRecord) {
			settleDifferRecord.setPaymentId(clearChannelRecord.getPaymentId()); // 支付单号
			settleDifferRecord.setChannelType(clearChannelRecord.getChannelType()); // 通道类型
			settleDifferRecord.setChannelCode(clearChannelRecord.getChannelCode()); // 通道编码
			settleDifferRecord.setCostAmount(clearChannelRecord.getCostAmount()); // 成本
			settleDifferRecord.setTransNo(clearChannelRecord.getTransNo()); // 交易单号
			settleDifferRecord.setChannelName(clearChannelRecord.getChannelName()); // 通道名称
		}

		// 告警记录插入到差异表
		insertOtherSettleDifferRecordMethod(null,settleDifferRecord,clearChannelRecord);
		logger.info("通道侧有数据告警记录插入差异表{}", clearChannelRecord);

	}

	// 商户侧有数据,通道侧有数据
	public void insertMerchantRecordSettleDifferRecord(ClearMerchantRecord clearMerchantRecord) throws Exception {

		// 为告警记录插入到差异表准备数据
		SettleDifferRecord settleDifferRecord = new SettleDifferRecord();

		ClearChannelRecord ccr = clearChannelRecordDaoImpl
				.queryClearChannelRecordByTranNo(clearMerchantRecord.getTransNo());
		if (null != ccr) {
			settleDifferRecord.setPaymentId(ccr.getPaymentId()); // 支付单号
			settleDifferRecord.setChannelType(ccr.getChannelType()); // 通道类型
			settleDifferRecord.setChannelCode(ccr.getChannelCode()); // 通道编码
			settleDifferRecord.setCostAmount(ccr.getCostAmount()); // 成本
			settleDifferRecord.setChannelName(ccr.getChannelName()); // 通道名称
		}

		// 告警记录插入到差异表
		insertOtherSettleDifferRecordMethod(clearMerchantRecord,settleDifferRecord,ccr);
		logger.info("商户侧有数据告警记录插入差异表{}", clearMerchantRecord);

	}

	// 商户侧有数据，通道侧没有数据
	public void insertSettleDifferRecordMerchantRecord(ClearMerchantRecord clearMerchantRecord) throws Exception {

		SettleDifferRecord settleDifferRecord = new SettleDifferRecord();

		// 从交易系统查询通道侧数据，参数transNo
		ClearChannelQueryModel clearChannel = billingClearAPIClient
				.channelQueryTransByTransNo(clearMerchantRecord.getTransNo());
		logger.info("其他差异处理时,通道侧没有数据时从交易系统获取的通道信息是{}",clearChannel);
		if (clearChannel == null) {
			logger.info("根据交易单号从交易系统获取数据为空,自定义异常回滚数据状态{}", "交易单号为" + clearMerchantRecord.getTransNo());
			throw new Exception();
		} else {
			// 根据channelCode查询路由信息
			String channelByChannelCode = payChannelCacheServiceClient
					.getChannelByChannelCode(clearChannel.getChannelCode());
			logger.info("其他差异处理时,通道侧没有数据时根据channelCode获取路由信息{}",channelByChannelCode);
			// 解析路由信息
			ChannelRouteMessage routeChannelMesage = FetchRouteMessage.getRouteChannelMesage(channelByChannelCode);
			if (routeChannelMesage == null) {
				logger.info("通道侧从路由获取到的信息为空,自定义异常回滚数据状态:{}", clearChannel);
				throw new Exception();
			} else {
				if (StringUtils.isNotBlank(routeChannelMesage.getChannelPartnerCode())) {
					// 如果是直连银行，channelCode格式为
					// DIRCON-银行号，名称为银行名称；如果是第三方支付通道，channelCode为合作方编码，名称是合作方名称
					if (ChannelPartner.DIRECTCONNECTION.getValue().equals(routeChannelMesage.getChannelPartnerCode())) {
						settleDifferRecord.setChannelCode(
								ChannelPartner.DIRECTCONNECTION.getValue() + "-" + routeChannelMesage.getBankNo());
						settleDifferRecord.setChannelName(routeChannelMesage.getBankName());
					} else {
						settleDifferRecord.setChannelCode(routeChannelMesage.getChannelPartnerCode());
						settleDifferRecord.setChannelName(routeChannelMesage.getChannelPartnerName());
					}
					
					//settleDifferRecord.setBankSeq(); // 流水暂时流水
					settleDifferRecord.setBankName(routeChannelMesage.getBankName()); // 银行名称
					settleDifferRecord.setBankCode(routeChannelMesage.getBankNo()); // 银行编码
					settleDifferRecord.setChannelProvider(routeChannelMesage.getChannelPartnerName()); // 提供者
						
				} else {
					logger.info("通道侧从路由获取到的信息ChannelPartnerCode有误:{}", routeChannelMesage.getChannelPartnerCode());
				}
			}
			// 为告警记录插入到差异表准备数据
			settleDifferRecord
					.setChannelType(routeChannelMesage == null ? null : routeChannelMesage.getChannelTypeCode()); // 通道类型
			settleDifferRecord.setPaymentId(clearChannel.getPaymentId()); // 支付单号
			settleDifferRecord.setTransType(clearChannel.getTransType()); // 交易类型
			settleDifferRecord.setRequestAmount(new BigDecimal(clearChannel.getPayAmount()));  //支付金额
			// 告警记录插入到差异表
			insertOtherSettleDifferRecordMethod(clearMerchantRecord,settleDifferRecord, null);
			logger.info("商户侧有数据,通道侧没有数据时告警记录插入差异表{}", clearMerchantRecord);
		}
	}

	// 插入差异类型为其他差异的数据
	public void insertOtherSettleDifferRecordMethod(ClearMerchantRecord mRecord, SettleDifferRecord settleDifferRecord,
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
				//modified by xuangang 2017-03-10  添加商户字段 end	

//				// 通过thrift 获取商户侧路由信息
//				String merchantVo = managerServerClient.getMerchantProductVO(String.valueOf(mRecord.getMerchantId()),
//						mRecord.getProductCode());
//
//				// 获取路由商户信息
//				MerchantProductVO merrouteMsg = FetchRouteMessage.getMerchantMessage(merchantVo);
//				if (null == merrouteMsg) {
//					logger.info("入差异表获取路由信息:" + merrouteMsg);
//				} else {
//					settleDifferRecord.setProductName(merrouteMsg.getProductName()); // 产品名称
//				}
//				logger.info("【其他差异】插入差异记录表  查询路由信息产品名称成功！用户编码："
//						+ settleDifferRecord.getMerchantId());
			}
			settleDifferRecord.setDifferType(BillingDifferType.BDTYPEO.getValue()); // 差异类型
			settleDifferRecord.setErrorDate(new Date()); // 差错日期
			settleDifferRecord.setHandleResult(BillingBillStatus.BBSTATUSN.getValue()); // 处理状态
			settleDifferRecord.setIsBill(ClearingCheckStatus.CHECKSTATUSN.getValue());// 记账标记
			settleDifferRecord.setIsProfit(SettleDifferIsProfit.ACCOUNTFLGN.getValue());// 是否分润
																						// 默认N（否）
			settleDifferRecord.setSettleBath(IdBatch.getSettleBathString(0));// 结算批次(为其他差异和人工审核未通过的数据调用挂账接口做准备)
			// 存储差异表
			settleDifferRecordDaoImpl.insertOtherSettleDifferRecord(settleDifferRecord);
		} catch (Exception e) {
			logger.error("其他差异插入差异表异常,差异记录ID为"+settleDifferRecord.getDifferId().toString(), e);
			throw new Exception();
		}
	}

	/* (non-Javadoc)
	 * @see com.heepay.rpc.billing.service.ISendMailService#settleDifferRecord(com.heepay.billing.entity.ClearChannelRecord, com.heepay.billing.entity.ClearMerchantRecord)
	 */
	@Override
	public boolean settleDifferRecord(ClearChannelRecord clearChannelRecord,
			ClearMerchantRecord clearMerchantRecord) {
		// TODO Auto-generated method stub
		return false;
	}

}
