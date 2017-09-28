/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：微信支付订单Entity
 *
 * 创 建 者： @author 张晨
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
public class WechatRecord extends DataEntity<WechatRecord> {
	
	private static final long serialVersionUID = 1L;
	private String wechatId;		// 交易号
	private Long merchantId;		// 商户ID
	private String appid;		// 应用ID
	private String wxAppid;		// 微信公众号ID
	private String deviceInfo;		// 设备号
	private String nonceStr;		// 随机字符串
	private String version;		// 版本号
	private String prepayId;		// 微信生成的预付ID
	private String codeUrl;		// 二维码
	private String jsapiAppid;		// JSAPI支付公众号
	private String jsapiTimestamp;		// JSAPI支付时间
	private String jsapiNoncestr;		// JSAPI支付随机字符串
	private String jsapiPackage;		// JSAPI订单详情
	private String jsapiSigntype;		// jsapi签名方式
	private String jsapiPaysign;		// jsapi签名
	private String spbillCreateIp;		// 商户请求IP
	private String notifyUrl;		// 商户通知地址
	private Date createTime;		// 交易时间
	private Date updateTime;		// 更新时间
	private Date successTime;		// 成功时间
	private String body;		// 商品描述
	private String detail;		// 商品详情
	private String attach;		// 附加数据
	private String outTradeNo;		// 商户订单号
	private String currency;		// 货币类型
	private String totalFee;		// 总金额
	private String timeStart;		// 交易起始时间
	private String timeExpire;		// 交易结束时间
	private String goodsTag;		// 商品标签
	private String tradeType;		// 交易类型 JSAPI、NATIVE、APP
	private String openid;		// 用户标识
	private String productId;		// 商品id
	private String limitPay;		// 指定支付方式
	private String successAmount;		// 成功交易金额
	private String productCode;		// 产品编码
	private String type;		// 交易类型
	private String status;		// 交易状态
	private String refundTimes;		// 发起退款次数
	private String canRefundAmount;		// 可退余额
	private String feeType;		// 交易手续费扣除方式
	private String feeRatio;		// 交易手续费率
	private String feeAmount;		// 手续费金额
	private String operator;		// 操作人
	private String processDesc;		// 处理备注
	private String couponFee;		// 优惠总额
	private String transactionId;		// 微信支付订单号
	private String passTradeNo;		// 通道订单号
	private String channelCode;		// 通道代码
	private Date beginCreateTime;   // 开始 创建时间
	private Date endCreateTime;   // 结束 创建时间
	  
	
	private String settleCyc;   // 结算类型
	
	
	private String statisticsDate;
	private String groupType; //交易时间/创建时间
	
	 
	public String getSettleCyc() {
    return settleCyc;
  }

  public void setSettleCyc(String settleCyc) {
    this.settleCyc = settleCyc;
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

  public WechatRecord() {
		super();
	}

	public WechatRecord(String id){
		super(id);
	}

	@Length(min=1, max=20, message="交易号长度必须介于 1 和 20 之间")
	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
	
	@NotNull(message="商户ID不能为空")
	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	@Length(min=1, max=18, message="应用ID长度必须介于 1 和 18 之间")
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	@Length(min=1, max=18, message="微信公众号ID长度必须介于 1 和 18 之间")
	public String getWxAppid() {
		return wxAppid;
	}

	public void setWxAppid(String wxAppid) {
		this.wxAppid = wxAppid;
	}
	
	@Length(min=0, max=64, message="设备号长度必须介于 0 和 64 之间")
	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	
	@Length(min=1, max=32, message="随机字符串长度必须介于 1 和 32 之间")
	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	
	@Length(min=0, max=6, message="版本号长度必须介于 0 和 6 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Length(min=1, max=64, message="微信生成的预付ID长度必须介于 1 和 64 之间")
	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}
	
	@Length(min=0, max=128, message="二维码长度必须介于 0 和 128 之间")
	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	
	@Length(min=0, max=32, message="JSAPI支付公众号长度必须介于 0 和 32 之间")
	public String getJsapiAppid() {
		return jsapiAppid;
	}

	public void setJsapiAppid(String jsapiAppid) {
		this.jsapiAppid = jsapiAppid;
	}
	
	@Length(min=0, max=32, message="JSAPI支付时间长度必须介于 0 和 32 之间")
	public String getJsapiTimestamp() {
		return jsapiTimestamp;
	}

	public void setJsapiTimestamp(String jsapiTimestamp) {
		this.jsapiTimestamp = jsapiTimestamp;
	}
	
	@Length(min=0, max=32, message="JSAPI支付随机字符串长度必须介于 0 和 32 之间")
	public String getJsapiNoncestr() {
		return jsapiNoncestr;
	}

	public void setJsapiNoncestr(String jsapiNoncestr) {
		this.jsapiNoncestr = jsapiNoncestr;
	}
	
	@Length(min=0, max=128, message="JSAPI订单详情长度必须介于 0 和 128 之间")
	public String getJsapiPackage() {
		return jsapiPackage;
	}

	public void setJsapiPackage(String jsapiPackage) {
		this.jsapiPackage = jsapiPackage;
	}
	
	@Length(min=0, max=32, message="jsapi签名方式长度必须介于 0 和 32 之间")
	public String getJsapiSigntype() {
		return jsapiSigntype;
	}

	public void setJsapiSigntype(String jsapiSigntype) {
		this.jsapiSigntype = jsapiSigntype;
	}
	
	@Length(min=0, max=64, message="jsapi签名长度必须介于 0 和 64 之间")
	public String getJsapiPaysign() {
		return jsapiPaysign;
	}

	public void setJsapiPaysign(String jsapiPaysign) {
		this.jsapiPaysign = jsapiPaysign;
	}
	
	@Length(min=1, max=15, message="商户请求IP长度必须介于 1 和 15 之间")
	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}
	
	@Length(min=0, max=512, message="商户通知地址长度必须介于 0 和 512 之间")
	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="交易时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSuccessTime() {
		return successTime;
	}

	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	
	@Length(min=1, max=32, message="商品描述长度必须介于 1 和 32 之间")
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	@Length(min=0, max=500, message="商品详情长度必须介于 0 和 500 之间")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	@Length(min=1, max=128, message="附加数据长度必须介于 1 和 128 之间")
	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	@Length(min=1, max=32, message="商户订单号长度必须介于 1 和 32 之间")
	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	
	@Length(min=0, max=16, message="货币类型长度必须介于 0 和 16 之间")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	
	@Length(min=0, max=16, message="交易起始时间长度必须介于 0 和 16 之间")
	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}
	
	@Length(min=0, max=16, message="交易结束时间长度必须介于 0 和 16 之间")
	public String getTimeExpire() {
		return timeExpire;
	}

	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}
	
	@Length(min=1, max=32, message="商品标签长度必须介于 1 和 32 之间")
	public String getGoodsTag() {
		return goodsTag;
	}

	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}
	
	@Length(min=1, max=16, message="交易类型 JSAPI、NATIVE、APP长度必须介于 1 和 16 之间")
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	
	@Length(min=0, max=128, message="用户标识长度必须介于 0 和 128 之间")
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	@Length(min=0, max=32, message="商品id长度必须介于 0 和 32 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=32, message="指定支付方式长度必须介于 0 和 32 之间")
	public String getLimitPay() {
		return limitPay;
	}

	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}
	
	public String getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}
	
	@Length(min=0, max=4, message="产品编码长度必须介于 0 和 4 之间")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	@Length(min=1, max=6, message="交易类型长度必须介于 1 和 6 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=1, max=6, message="交易状态长度必须介于 1 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=1, max=1, message="发起退款次数长度必须介于 1 和 1 之间")
	public String getRefundTimes() {
		return refundTimes;
	}

	public void setRefundTimes(String refundTimes) {
		this.refundTimes = refundTimes;
	}
	
	public String getCanRefundAmount() {
		return canRefundAmount;
	}

	public void setCanRefundAmount(String canRefundAmount) {
		this.canRefundAmount = canRefundAmount;
	}
	
	@Length(min=0, max=6, message="交易手续费扣除方式长度必须介于 0 和 6 之间")
	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	public String getFeeRatio() {
		return feeRatio;
	}

	public void setFeeRatio(String feeRatio) {
		this.feeRatio = feeRatio;
	}
	
	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	@Length(min=0, max=6, message="操作人长度必须介于 0 和 6 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Length(min=0, max=256, message="处理备注长度必须介于 0 和 256 之间")
	public String getProcessDesc() {
		return processDesc;
	}

	public void setProcessDesc(String processDesc) {
		this.processDesc = processDesc;
	}
	
	public String getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(String couponFee) {
		this.couponFee = couponFee;
	}
	
	@Length(min=0, max=256, message="微信支付订单号长度必须介于 0 和 256 之间")
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	@Length(min=0, max=256, message="通道订单号长度必须介于 0 和 256 之间")
	public String getPassTradeNo() {
		return passTradeNo;
	}

	public void setPassTradeNo(String passTradeNo) {
		this.passTradeNo = passTradeNo;
	}
	
	@Length(min=0, max=256, message="通道代码长度必须介于 0 和 256 之间")
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

  public String getGroupType() {
    return groupType;
  }

  public void setGroupType(String groupType) {
    this.groupType = groupType;
  }

  public String getStatisticsDate() {
    return statisticsDate;
  }

  public void setStatisticsDate(String statisticsDate) {
    this.statisticsDate = statisticsDate;
  }
	
	
	
}