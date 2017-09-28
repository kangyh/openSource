package com.heepay.boss.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.heepay.boss.client.BossCommonClient;
import com.heepay.boss.entity.B4ChargeRecord;
import com.heepay.boss.entity.ClearChannelRecord;
import com.heepay.boss.entity.ClearMerchantRecord;
import com.heepay.boss.entity.SettleDifferRecord;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.RiskBossLogUtil;
import com.heepay.common.vo.B4ChargeRecordVo;

/**
 * *
 * 
 * 
 * 描 述： 数据入库操作
 *
 * 创 建 者： wangjie 创建时间： 2017年5月31日下午5:23:12 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Component
public class DataStoreServer {

	private static final Logger logger = LogManager.getLogger();

	@Resource(name = "bossCommonClient")
	private BossCommonClient bossCommonClient;

	// 封装数据，用于抓取
	public void convertObject(ClearMerchantRecord clearMerchantRecord, ClearChannelRecord clearChannelRecord) {
		if ("B4".equals(bossCommonClient.getChargeRecordType())) {
			this.convertObjectB4(clearMerchantRecord, clearChannelRecord);
		}
	}

	// 封装数据，用于抓取
	public void convertObject(ClearMerchantRecord clearMerchantRecord, SettleDifferRecord settleDifferRecord) {
		if ("B4".equals(bossCommonClient.getChargeRecordType())) {
			this.convertObjectB4(clearMerchantRecord, settleDifferRecord);
		}
	}

	// 转为B4对象
	public void convertObjectB4(ClearMerchantRecord clearMerchantRecord, ClearChannelRecord clearChannelRecord) {
		try {
			B4ChargeRecordVo b4Record = new B4ChargeRecordVo();

			b4Record.setBankcardType((clearMerchantRecord.getBankcardType()==null || "".equals(clearMerchantRecord.getBankcardType())) ? "null":clearMerchantRecord.getBankcardType()); // 银行卡类型
			b4Record.setBankCode(clearChannelRecord == null ? null : clearChannelRecord.getBankCode()); // 银行编码
			b4Record.setBankId(null); // 银行Id
			b4Record.setBankShortName(clearChannelRecord==null? null :clearChannelRecord.getBankName()); // 银行简称
			b4Record.setCardholderGender(null); // 持卡人类别
			b4Record.setChannelCode(clearChannelRecord == null ? null : clearChannelRecord.getChannelCode()); // 通道编码
			b4Record.setChannelTransType(clearChannelRecord == null ? null : clearChannelRecord.getChannelType()); // 通道交易类型
			b4Record.setChannleNo(clearChannelRecord == null ? null : clearChannelRecord.getBankSeq()); // 上游流水
			b4Record.setCreatePerson(null); // 创建人
			b4Record.setCreateTime(clearMerchantRecord.getCreateTime()); // 创建时间
			b4Record.setMerchantId(clearMerchantRecord.getMerchantId()==null? null:clearMerchantRecord.getMerchantId().toString()); // 商户号
			b4Record.setFeeAmount(clearMerchantRecord.getFee()); // 手续费
			b4Record.setPayAmount(clearMerchantRecord.getRequestAmount()); // 支付金额
			b4Record.setPayFinishTime(clearMerchantRecord.getSuccessTime()); // 支付完成时间
			b4Record.setProductCode(clearMerchantRecord.getProductCode()); // 产品编码
			b4Record.setProductDescription(null); // 产品描述
			b4Record.setProductNumber(null); // 产品数量
			b4Record.setProvinceId(null); // IP来源省Id
			b4Record.setRequestAmount(clearMerchantRecord.getRequestAmount()); // 订单金额
			b4Record.setRequestIp(null); // Ip
			b4Record.setRequestStatus(null); // 订单状态
			b4Record.setRequestTime(clearMerchantRecord.getCreateTime()); // 订单创建时间
			b4Record.setSettleAmount(clearMerchantRecord.getSettleAmountPlan()); // 结算金额
			b4Record.setSettlePart(null); // 结算主题
			b4Record.setSettleStatus(clearMerchantRecord.getSettleStatus()); // 结算状态
			b4Record.setTransNo(clearMerchantRecord.getTransNo()); // 交易单号
			b4Record.setUpdatePerson(null); // 更新人
			b4Record.setUpdateTime(new Date()); // 更新时间
			b4Record.setMerchantName(clearMerchantRecord.getMerchantName()); //商户名称
			b4Record.setMerchantOrderNo(clearMerchantRecord.getMerchantOrderNo()); // 商户订单号
			b4Record.setTransType(clearMerchantRecord.getTransType()); //交易类型
			b4Record.setYear(DateUtil.dateToString(clearMerchantRecord.getSuccessTime(), "yyyy")); 
			b4Record.setMonth(DateUtil.dateToString(clearMerchantRecord.getSuccessTime(), "yyyyMM"));
			b4Record.setDay(DateUtil.dateToString(clearMerchantRecord.getSuccessTime(), "yyyyMMdd"));
			//写日记
			RiskBossLogUtil.logB4Risk(logger, b4Record);
		} catch (Exception e) {
			logger.error("B4单转换出错{},{}", e,clearMerchantRecord.getTransNo());
		}
	}

	public void convertObjectB4(ClearMerchantRecord clearMerchantRecord, SettleDifferRecord settleDifferRecord) {
		try {
			B4ChargeRecordVo b4Record = new B4ChargeRecordVo();

			b4Record.setBankcardType((clearMerchantRecord.getBankcardType()==null || "".equals(clearMerchantRecord.getBankcardType())) ? "null":clearMerchantRecord.getBankcardType()); // 银行卡类型
			b4Record.setBankCode(settleDifferRecord == null ? null : settleDifferRecord.getBankCode()); // 银行编码
			b4Record.setBankId(null); // 银行Id
			b4Record.setBankShortName(settleDifferRecord==null? null:settleDifferRecord.getBankName()); // 银行简称
			b4Record.setCardholderGender(null); // 持卡人类别
			b4Record.setChannelCode(settleDifferRecord == null ? null : settleDifferRecord.getChannelCode()); // 通道编码
			b4Record.setChannelTransType(settleDifferRecord == null ? null : settleDifferRecord.getChannelType()); // 通道交易类型
			b4Record.setChannleNo(settleDifferRecord == null ? null : settleDifferRecord.getBankSeq()); // 上游流水
			b4Record.setCreatePerson(null); // 创建人
			b4Record.setCreateTime(clearMerchantRecord.getCreateTime()); // 创建时间
			b4Record.setMerchantId(clearMerchantRecord.getMerchantId()==null? null:clearMerchantRecord.getMerchantId().toString()); // 商户号
			b4Record.setFeeAmount(clearMerchantRecord.getFee()); // 手续费
			b4Record.setPayAmount(clearMerchantRecord.getRequestAmount()); // 支付金额
			b4Record.setPayFinishTime(clearMerchantRecord.getSuccessTime()); // 支付完成时间
			b4Record.setProductCode(clearMerchantRecord.getProductCode()); // 产品编码
			b4Record.setProductDescription(null); // 产品描述
			b4Record.setProductNumber(null); // 产品数量
			b4Record.setProvinceId(null); // IP来源省Id
			b4Record.setRequestAmount(clearMerchantRecord.getRequestAmount()); // 订单金额
			b4Record.setRequestIp(null); // Ip
			b4Record.setRequestStatus(null); // 订单状态
			b4Record.setRequestTime(clearMerchantRecord.getCreateTime()); // 订单创建时间
			b4Record.setSettleAmount(clearMerchantRecord.getSettleAmountPlan()); // 结算金额
			b4Record.setSettlePart(null); // 结算主题
			b4Record.setSettleStatus(clearMerchantRecord.getSettleStatus()); // 结算状态
			b4Record.setTransNo(clearMerchantRecord.getTransNo()); // 交易单号
			b4Record.setUpdatePerson(null); // 更新人
			b4Record.setUpdateTime(new Date()); // 更新时间
			b4Record.setMerchantName(clearMerchantRecord.getMerchantName()); //商户名称
			b4Record.setMerchantOrderNo(clearMerchantRecord.getMerchantOrderNo()); // 商户订单号
			b4Record.setTransType(clearMerchantRecord.getTransType()); //交易类型
			b4Record.setYear(DateUtil.dateToString(clearMerchantRecord.getSuccessTime(), "yyyy")); 
			b4Record.setMonth(DateUtil.dateToString(clearMerchantRecord.getSuccessTime(), "yyyyMM"));
			b4Record.setDay(DateUtil.dateToString(clearMerchantRecord.getSuccessTime(), "yyyyMMdd"));
			//写日记
			RiskBossLogUtil.logB4Risk(logger, b4Record);
		} catch (Exception e) {
			logger.error("B4单转换出错{},{}", e,clearMerchantRecord.getTransNo());
		}
	}

}
