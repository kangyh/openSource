package com.heepay.billing.route;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.date.DateUtils;
import com.heepay.vo.ChannelRouteMessage;
import com.heepay.vo.MerchantProductVO;
import com.heepay.vo.ProductVO;

/***
 * 
 * 
 * 描 述：获取路由系统信息
 *
 * 创 建 者： xuangang 创建时间： 2016年9月14日下午2:06:39 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
public class FetchRouteMessage {

	private static final Logger logger = LogManager.getLogger();

	/**
	 * 获取路由端通道信息
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ChannelRouteMessage getRouteChannelMesage(String info) {

		if (StringUtil.isBlank(info)) {
			return null;
		}
		try {
			ChannelRouteMessage channleRouteMsg = new ChannelRouteMessage();

			Map<String, String> map = JsonMapperUtil.nonEmptyMapper().fromJson(
					info, Map.class);

			channleRouteMsg.setChannelCode(map.get("channelCode")); // 通道编码
			channleRouteMsg.setChannelName(map.get("channelName")); // 支付通道名称
			channleRouteMsg.setBankName(map.get("bankName")); // 银行名称
			channleRouteMsg.setBankNo(map.get("bankNo")); // 银行代码
			channleRouteMsg
					.setChannelPartnerName(map.get("channelPartnerName")); // 通道合作方
			channleRouteMsg
					.setChannelPartnerCode(map.get("channelPartnerCode")); // 通道合作方代码
			channleRouteMsg.setChannelTypeName(map.get("channelTypeName")); // 支付通道类型
			channleRouteMsg.setChannelTypeCode(map.get("channelTypeCode")); // 支付通道类型代码
			channleRouteMsg.setCardTypeName(map.get("cardTypeName")); // 银行卡类型
			channleRouteMsg.setCardTypeCode(map.get("cardTypeCode")); // 银行卡类型代码
			channleRouteMsg.setAccountType(map.get("accountType")); // 账户类型代码
			channleRouteMsg.setBusinessType(map.get("businessType")); // 业务类型代码
			channleRouteMsg.setEffectEndDate(DateUtils.getStr2Date(map
					.get("effectEndDate"))); // 有效开始时间
			channleRouteMsg.setEffectStartDate(DateUtils.getStr2Date(map
					.get("effectStartDate"))); // 有效开始时间
			channleRouteMsg.setCostType(map.get("costType")); // 成本类型

			channleRouteMsg.setCostCount(map.get("costCount"));// 成本按笔数
			channleRouteMsg.setStatus(map.get("status")); // 状态
			channleRouteMsg.setContractDate(DateUtils.getStr2Date(map
					.get("contractDate"))); // 合约时间
			channleRouteMsg.setSettleType(map.get("settleType")); // 本金结算类型
			channleRouteMsg.setSettlePeriod(map.get("settlePeriod")); // 本金结算周期

			channleRouteMsg.setPerlimitAmount(map.get("perlimitAmount")); // 单笔限额
			channleRouteMsg.setDaylimitAmount(map.get("daylimitAmount")); // 单日限额
			channleRouteMsg.setMonlimitAmount(map.get("monlimitAmount")); // 单月限额

			channleRouteMsg
					.setChannelPartnerCode(map.get("channelPartnerCode")); // 通道合作方代码

			channleRouteMsg.setOrderSettlePeriod(map.get("orderSettlePeriod")); // 通道结算周期
			channleRouteMsg.setChargeDeductType(map.get("chargeDeductType")); // 手续费扣除方式

			return channleRouteMsg;
		} catch (Exception e) {
			logger.info("获取通道信息异常{}", info, e);
			return null;
		}

	}

	/**
	 * 获取路由系统商户信息
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static MerchantProductVO getMerchantMessage(String info) {

		if (StringUtil.isBlank(info)) {
			return null;
		}
		try {
			MerchantProductVO msg = new MerchantProductVO();

			Map<String, String> map = JsonMapperUtil.nonEmptyMapper().fromJson(
					info, Map.class);

			msg.setMerchantId(map.get("merchantId")); // 商户编码
			msg.setProductName(map.get("productName")); // 产品名称
			msg.setProductCode(map.get("productCode")); // 产品编码
			msg.setFeeWay(map.get("feeWay")); // 手续费扣除方式
			msg.setFeeSettleCyc(map.get("feeSettleCyc")); // 手续费结算周期
			msg.setToBalanceOrBankcard(map.get("toBalanceOrBankcard"));
			msg.setReferer(map.get("referer"));

			msg.setBackUrl(map.get("backUrl")); // 对接产品的backurl
			msg.setProductKey(map.get("productKey")); // 对接产品的秘钥

			msg.setSettleCyc(map.get("settleCyc")); // 订单结算周期
			msg.setIsRefundable(map.get("isRefundable")); // 是否退还手续费

			return msg;
		} catch (Exception e) {
			logger.info("获取商户侧路由信息异常{}", info, e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static ProductVO getProductVO(String info) {
		if (StringUtil.isBlank(info)) {
			return null;
		}
		try {
			ProductVO msg = new ProductVO();			
			Map<String, String> map = JsonMapperUtil.nonEmptyMapper().fromJson(info, Map.class);

			msg.setProductCode(map.get("productCode"));  //产品编码
			msg.setProductName(map.get("productName")); // 产品名称
			msg.setProductStatus(map.get("productStatus")); //产品状态

			return msg;
		} catch (Exception e) {
			logger.info("获取产品路由路由信息异常{}", info, e);
			return null;
		}
	}

}
