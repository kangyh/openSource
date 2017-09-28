/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：支付宝扫码支付Entity
 *
 * 创 建 者： @author tyq
 * 创建时间：
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
public class AlipayRecord extends DataEntity<AlipayRecord> {
	
	private static final long serialVersionUID = 1L;
	private String alipayId;		// 交易订单号
	private Long merchantId;		// 商户编码
	private String outTradeNo;		// 商户订单号
	private String version;		// 版本号
	private String scene;		// (扫码支付不用，扩展字段)支付场景 条码支付，取值：bar_code 声波支付，取值：wave_code,扫码支付，取值：scan_code
	private String authCode;		// 支付授权码，条码和声波支付需要，扫码支付不需要
	private String productCode;		// 产品编码
	private String type;		// 交易类型
	private String status;		// 交易状态
	private String subject;		// 订单标题
	private String body;		// 对交易或商品描述
	private String goodsDetail;		// 订单包含的商品列表信息.Json格式. 其它说明详见：&ldquo;商品明细说明&rdquo;
	private String buyerId;		// （扫码支付不用，扩展预留字段）买家的支付宝用户id，如果为空，会从传入了码值信息中获取买家ID
	private String buyerLogonId;		// 买家支付宝账号
	private String sellerId;		// 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
	private String totalAmount;		// 总金额
	private String discountableAmount;		// 参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。 如果该值未传入，但传入了【订单总金额】和【不可打折金额】，则该值默认为【订单总金额】-【不可打折金额】
	private String undiscountableAmount;		// 不参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]。如果该值未传入，但传入了【订单总金额】和【可打折金额】，则该值默认为【订单总金额】-【可打折金额】
	private String successAmount;		// 成功金额
	private String feeAmount;		// 手续费金额
	private String feeType;		// 手续费扣除方式
	private String operatorId;		// 商户操作员编号
	private String storeId;		// 商户门店编号
	private String terminalId;		// 商户机具终端编号
	private String alipayStoreId;		// 支付宝的店铺编号
	private String extendParams;		// 业务扩展参数,json格式
	private String timeoutExpress;		// 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m
	private String royaltyInfo;		// 描述分账信息，Json格式，其它说明详见分账说明
	private String subMerchant;		// 二级商户信息,当前只对特殊银行机构特定场景下使用此字段
	private String authNo;		// 预授权号，预授权转交易请求中传入
	private String extUserInfo;		// (扫码支付不用，预留字段)外部指定买家
	private Date createTime;		// 交易时间
	private Date requestTime;		// 交易请求时间
	private Date updateTime;		// 更新时间
	private Date sucessTime;		// 成功时间
	private String currency;		// 货币类型
	private String settleCyc;		// 结算方式  0=T+0结算,1=T+1结算,x=T+x结算
	private String notifyUrl;		// 支付宝服务器主动通知商户服务器里指定的页面http/https路径。
	private String returnUrl;		// return url
	private String userIp;		// 商户ip
	private Date beginCreateTime;		// 开始 交易时间
	private Date endCreateTime;		// 结束 交易时间
	
	public AlipayRecord() {
		super();
	}

	public AlipayRecord(String id){
		super(id);
	}

	@Length(min=1, max=20, message="交易订单号长度必须介于 1 和 20 之间")
	public String getAlipayId() {
		return alipayId;
	}

	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}
	
	@NotNull(message="商户编码不能为空")
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=1, max=64, message="商户订单号长度必须介于 1 和 64 之间")
	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	
	@Length(min=0, max=6, message="版本号长度必须介于 0 和 6 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Length(min=0, max=32, message="(扫码支付不用，扩展字段)支付场景 条码支付，取值：bar_code 声波支付，取值：wave_code,扫码支付，取值：scan_code长度必须介于 0 和 32 之间")
	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}
	
	@Length(min=0, max=32, message="支付授权码，条码和声波支付需要，扫码支付不需要长度必须介于 0 和 32 之间")
	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	
	@Length(min=0, max=32, message="产品编码长度必须介于 0 和 32 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=0, max=6, message="交易类型长度必须介于 0 和 6 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=6, message="交易状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=256, message="订单标题长度必须介于 0 和 256 之间")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Length(min=0, max=128, message="对交易或商品描述长度必须介于 0 和 128 之间")
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public String getGoodsDetail() {
		return goodsDetail;
	}

	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}
	
	@Length(min=0, max=28, message="（扫码支付不用，扩展预留字段）买家的支付宝用户id，如果为空，会从传入了码值信息中获取买家ID长度必须介于 0 和 28 之间")
	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	
	@Length(min=0, max=100, message="买家支付宝账号长度必须介于 0 和 100 之间")
	public String getBuyerLogonId() {
		return buyerLogonId;
	}

	public void setBuyerLogonId(String buyerLogonId) {
		this.buyerLogonId = buyerLogonId;
	}
	
	@Length(min=0, max=28, message="如果该值为空，则默认为商户签约账号对应的支付宝用户ID长度必须介于 0 和 28 之间")
	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	
	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public String getDiscountableAmount() {
		return discountableAmount;
	}

	public void setDiscountableAmount(String discountableAmount) {
		this.discountableAmount = discountableAmount;
	}
	
	public String getUndiscountableAmount() {
		return undiscountableAmount;
	}

	public void setUndiscountableAmount(String undiscountableAmount) {
		this.undiscountableAmount = undiscountableAmount;
	}
	
	public String getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}
	
	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	@Length(min=0, max=6, message="手续费扣除方式长度必须介于 0 和 6 之间")
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	@Length(min=0, max=28, message="商户操作员编号长度必须介于 0 和 28 之间")
	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	
	@Length(min=0, max=32, message="商户门店编号长度必须介于 0 和 32 之间")
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	@Length(min=0, max=32, message="商户机具终端编号长度必须介于 0 和 32 之间")
	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	
	@Length(min=0, max=32, message="支付宝的店铺编号长度必须介于 0 和 32 之间")
	public String getAlipayStoreId() {
		return alipayStoreId;
	}

	public void setAlipayStoreId(String alipayStoreId) {
		this.alipayStoreId = alipayStoreId;
	}
	
	@Length(min=0, max=1000, message="业务扩展参数,json格式长度必须介于 0 和 1000 之间")
	public String getExtendParams() {
		return extendParams;
	}

	public void setExtendParams(String extendParams) {
		this.extendParams = extendParams;
	}
	
	public String getTimeoutExpress() {
		return timeoutExpress;
	}

	public void setTimeoutExpress(String timeoutExpress) {
		this.timeoutExpress = timeoutExpress;
	}
	
	@Length(min=0, max=1000, message="描述分账信息，Json格式，其它说明详见分账说明长度必须介于 0 和 1000 之间")
	public String getRoyaltyInfo() {
		return royaltyInfo;
	}

	public void setRoyaltyInfo(String royaltyInfo) {
		this.royaltyInfo = royaltyInfo;
	}
	
	@Length(min=0, max=1000, message="二级商户信息,当前只对特殊银行机构特定场景下使用此字段长度必须介于 0 和 1000 之间")
	public String getSubMerchant() {
		return subMerchant;
	}

	public void setSubMerchant(String subMerchant) {
		this.subMerchant = subMerchant;
	}
	
	@Length(min=0, max=64, message="预授权号，预授权转交易请求中传入长度必须介于 0 和 64 之间")
	public String getAuthNo() {
		return authNo;
	}

	public void setAuthNo(String authNo) {
		this.authNo = authNo;
	}
	
	@Length(min=0, max=1000, message="(扫码支付不用，预留字段)外部指定买家长度必须介于 0 和 1000 之间")
	public String getExtUserInfo() {
		return extUserInfo;
	}

	public void setExtUserInfo(String extUserInfo) {
		this.extUserInfo = extUserInfo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSucessTime() {
		return sucessTime;
	}

	public void setSucessTime(Date sucessTime) {
		this.sucessTime = sucessTime;
	}
	
	@Length(min=0, max=16, message="货币类型长度必须介于 0 和 16 之间")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Length(min=0, max=1, message="结算方式  0=T+0结算,1=T+1结算,x=T+x结算长度必须介于 0 和 1 之间")
	public String getSettleCyc() {
		return settleCyc;
	}

	public void setSettleCyc(String settleCyc) {
		this.settleCyc = settleCyc;
	}
	
	@Length(min=0, max=256, message="支付宝服务器主动通知商户服务器里指定的页面http/https路径。长度必须介于 0 和 256 之间")
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	@Length(min=0, max=256, message="return url长度必须介于 0 和 256 之间")
	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	@Length(min=0, max=16, message="商户ip长度必须介于 0 和 16 之间")
	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	
	public Date getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	
	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
		
}