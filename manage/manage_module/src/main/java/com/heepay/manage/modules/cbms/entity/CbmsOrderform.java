/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：同申报批次号详情查看和新增Entity
 *
 * 创 建 者： @author guozx
 * 创建时间： 2017年1月2日 17:00:25
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
public class CbmsOrderform extends DataEntity<CbmsOrderform> {
	
	private static final long serialVersionUID = 1L;
	private Long orderId;		// 订到导入表主键
	private String importBatchNumber;		// 导入批次号
	private String sentCustomsNumber;		// 报送海关批次号
	private String customSn;		// 海关流水号
	private String merchantNo;		// 商户ID
	private String busEnterpriseNo;		// 电商企业编号
	private String busEnterpriseName;		// 电商企业名称
	private String orderFormNo;		// 电商订单号
	private String transCode;		// 企业报送类型。            1-新增 2-变更 3-删除。默认为1
	private String modeCode;		// 模式代码                        1、 一般模式             2、 保税模式            默认为：1
	private String inspectionQuarantineCode;		// 检验检疫机构代码             参考检验检疫机构代码
	private String customCode;		// 海关编码
	private String commodityName;		// 商品名称
	private String commodityCount;		// 商品数量
	private String commodityUnit;		// 商品单位
	private String goodMsg;		// 商品及物流信息
	private String isRef;		// 是否为保税区货物,0否1是
	private String rmbPrices;		// 人民币单价
	private String foreignPrices;		// 外币单价
	private String rmbAmount;		// 订单人民币金额
	private String foreignAmount;		// 订单外币金额
	private String rmbPayAmount;		// 人民币支付金额
	private String chargeAmount;		// 手续费收取金额
	private String payPurpose;		// 支付用途            A.全款            S.货款            X.运费
	private String payTaxes;		// 支付税款
	private String freight;		// 支付运费
	private String orgPayEnterpriseName;		// 原始支付企业名称
	private String orgPayTransno;		// 原始支付流水号
	private String hyPayTransno;		// 汇元银通支付支付流水号
	private String payStatus;		// 支付交易状态            D-代扣(款项由消费者账户转至 第三方支付企业账户)             S-实扣(款项由消费者账户转至 收款方账户)             C-取消(退款)
	private String payName;		// 付款人名称
	private String sex;		// 性别            B:男            G:女
	private String age;		// 年龄
	private Date payTime;		// 付款时间
	private String payErcertificateType;		// 付款人证件类型
	private String payErcertificateNo;		// 付款人证件号
	private String currencyId;		// 付款币种ID
	private String bankCardType;		// 卡种
	private String payEraccountNo;		// 付款人银行账号
	private String payerPhone;		// 付款人手机号
	private String fileName;		// 订单文件的文件名
	private Date confirmTime;		// 确认时间
	private Date tadingTime;		// 成交时间
	private Date authenticationTime;		// 实名认证时间
	private Date declarationTime;		// 申报时间
	private String commodityClassify;		// 商品分类
	private String consigneeName;		// 收货人名称
	private String consigneePhone;		// 收货人电话
	private String consigneeAddress;		// 收货人地址
	private String deliveryType;		// 运送方式
	private String wayBillno;		// 运单号
	private String logisticsCompany;		// 物流公司
	private Date createdTime;		// 录入时间
	private Date updateTime;		// 修改时间
	private String serviceStatus;		// 业务状态            业务状态:1-暂存,2-申报,默认为2。
	private String subscriptionStatus;		// 订阅状态            用户订阅单证业务状态的信息, ALL-订阅数据和回执,  DATA-只订阅数据,RET- 只订阅回执            默认：ALL
	private String subTransMode;		// 订阅方传输模式            默认为DXP；指中国电子口岸数据交换平台
	private String note;		// 备注
	private String postScript;		// 附言
	private String identification;		// 标识            0:新增申报            1:修改申报
	private String orderStatus;		// 订单状态            1：确认，待审核            2：审核通过、待报关            3：已报关
	private String auditStatus;		// 1.法务待审核            2.风控待审核            3. 已审核            4. 审核拒绝
	private String auditFailReason;		// 审核失败原因
	private String customStatus;		// (1未报送2报送处理中3报送成功4报送失败5退单)
	private String causesFailure;		// 报送失败原因
	private String elecPortReturnNumber;		// 电子口岸返回生成36位唯一序号
	private String notea;		// 备注A
	private String noteb;		// 备注B
	private String notec;		// 备注c
	private String counts;		// 备注c
	private String declarationBatchHumber;		//申报批次号
	private String transId;		//交易订单号
	private String transStatus;		//交易状态 1. 手续费扣款成功 2. 手续费扣款失败 3. 已退款 4. 已报关,不允许退款
	private String declareType;		//交易状态
	private String authMsg;		//实名认证结果
	private String mainCustomCode;		//主管海关编码
	private Date beginDeclarationTime;   // 开始申报时间
	private Date endDeclarationTime;   // 结束申报时间

	public String getDeclareType() {
		return declareType;
	}

	public String getAuthMsg() {
		return authMsg;
	}

	public void setAuthMsg(String authMsg) {
		this.authMsg = authMsg;
	}

	public String getMainCustomCode() {
		return mainCustomCode;
	}

	public void setMainCustomCode(String mainCustomCode) {
		this.mainCustomCode = mainCustomCode;
	}

	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}

	public Date getBeginDeclarationTime() {
        return beginDeclarationTime;
    }

    public void setBeginDeclarationTime(Date beginDeclarationTime) {
        this.beginDeclarationTime = beginDeclarationTime;
    }

    public Date getEndDeclarationTime() {
        return endDeclarationTime;
    }

    public void setEndDeclarationTime(Date endDeclarationTime) {
        this.endDeclarationTime = endDeclarationTime;
    }

    public String getDeclarationBatchHumber() {
		return declarationBatchHumber;
	}

	public void setDeclarationBatchHumber(String declarationBatchHumber) {
		this.declarationBatchHumber = declarationBatchHumber;
	}

	public CbmsOrderform() {
		super();
	}

	public CbmsOrderform(String id){
		super(id);
	}

	@NotNull(message="订到导入表主键不能为空")
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	@Length(min=0, max=64, message="导入批次号长度必须介于 0 和 64 之间")
	public String getImportBatchNumber() {
		return importBatchNumber;
	}

	public void setImportBatchNumber(String importBatchNumber) {
		this.importBatchNumber = importBatchNumber;
	}
	
	@Length(min=0, max=64, message="报送海关批次号长度必须介于 0 和 64 之间")
	public String getSentCustomsNumber() {
		return sentCustomsNumber;
	}

	public void setSentCustomsNumber(String sentCustomsNumber) {
		this.sentCustomsNumber = sentCustomsNumber;
	}
	
	@Length(min=0, max=64, message="海关流水号长度必须介于 0 和 64 之间")
	public String getCustomSn() {
		return customSn;
	}

	public void setCustomSn(String customSn) {
		this.customSn = customSn;
	}
	
	@Length(min=0, max=64, message="商户ID长度必须介于 0 和 64 之间")
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	
	@Length(min=0, max=64, message="电商企业编号长度必须介于 0 和 64 之间")
	public String getBusEnterpriseNo() {
		return busEnterpriseNo;
	}

	public void setBusEnterpriseNo(String busEnterpriseNo) {
		this.busEnterpriseNo = busEnterpriseNo;
	}
	
	@Length(min=0, max=200, message="电商企业名称长度必须介于 0 和 200 之间")
	public String getBusEnterpriseName() {
		return busEnterpriseName;
	}

	public void setBusEnterpriseName(String busEnterpriseName) {
		this.busEnterpriseName = busEnterpriseName;
	}
	
	@Length(min=0, max=64, message="电商订单号长度必须介于 0 和 64 之间")
	public String getOrderFormNo() {
		return orderFormNo;
	}

	public void setOrderFormNo(String orderFormNo) {
		this.orderFormNo = orderFormNo;
	}
	
	@Length(min=0, max=10, message="企业报送类型。            1-新增 2-变更 3-删除。默认为1长度必须介于 0 和 10 之间")
	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	
	@Length(min=0, max=10, message="模式代码                        1、 一般模式             2、 保税模式            默认为：1长度必须介于 0 和 10 之间")
	public String getModeCode() {
		return modeCode;
	}

	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}
	
	@Length(min=0, max=10, message="检验检疫机构代码             参考检验检疫机构代码长度必须介于 0 和 10 之间")
	public String getInspectionQuarantineCode() {
		return inspectionQuarantineCode;
	}

	public void setInspectionQuarantineCode(String inspectionQuarantineCode) {
		this.inspectionQuarantineCode = inspectionQuarantineCode;
	}
	
	@Length(min=0, max=64, message="海关编码长度必须介于 0 和 64 之间")
	public String getCustomCode() {
		return customCode;
	}

	public void setCustomCode(String customCode) {
		this.customCode = customCode;
	}
	
	@Length(min=0, max=1000, message="商品名称长度必须介于 0 和 1000 之间")
	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	
	@Length(min=0, max=11, message="商品数量长度必须介于 0 和 11 之间")
	public String getCommodityCount() {
		return commodityCount;
	}

	public void setCommodityCount(String commodityCount) {
		this.commodityCount = commodityCount;
	}
	
	@Length(min=0, max=50, message="商品单位长度必须介于 0 和 50 之间")
	public String getCommodityUnit() {
		return commodityUnit;
	}

	public void setCommodityUnit(String commodityUnit) {
		this.commodityUnit = commodityUnit;
	}
	
	@Length(min=0, max=640, message="商品及物流信息长度必须介于 0 和 640 之间")
	public String getGoodMsg() {
		return goodMsg;
	}

	public void setGoodMsg(String goodMsg) {
		this.goodMsg = goodMsg;
	}
	
	@Length(min=0, max=11, message="是否为保税区货物,0否1是长度必须介于 0 和 11 之间")
	public String getIsRef() {
		return isRef;
	}

	public void setIsRef(String isRef) {
		this.isRef = isRef;
	}
	
	public String getRmbPrices() {
		return rmbPrices;
	}

	public void setRmbPrices(String rmbPrices) {
		this.rmbPrices = rmbPrices;
	}
	
	public String getForeignPrices() {
		return foreignPrices;
	}

	public void setForeignPrices(String foreignPrices) {
		this.foreignPrices = foreignPrices;
	}
	
	public String getRmbAmount() {
		return rmbAmount;
	}

	public void setRmbAmount(String rmbAmount) {
		this.rmbAmount = rmbAmount;
	}
	
	public String getForeignAmount() {
		return foreignAmount;
	}

	public void setForeignAmount(String foreignAmount) {
		this.foreignAmount = foreignAmount;
	}
	
	public String getRmbPayAmount() {
		return rmbPayAmount;
	}

	public void setRmbPayAmount(String rmbPayAmount) {
		this.rmbPayAmount = rmbPayAmount;
	}
	
	public String getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	
	//@运费长度必须介于 0 和 5 之间")
	public String getPayPurpose() {
		return payPurpose;
	}

	public void setPayPurpose(String payPurpose) {
		this.payPurpose = payPurpose;
	}
	
	public String getPayTaxes() {
		return payTaxes;
	}

	public void setPayTaxes(String payTaxes) {
		this.payTaxes = payTaxes;
	}
	
	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}
	
	@Length(min=0, max=500, message="原始支付企业名称长度必须介于 0 和 500 之间")
	public String getOrgPayEnterpriseName() {
		return orgPayEnterpriseName;
	}

	public void setOrgPayEnterpriseName(String orgPayEnterpriseName) {
		this.orgPayEnterpriseName = orgPayEnterpriseName;
	}
	
	@Length(min=0, max=64, message="原始支付流水号长度必须介于 0 和 64 之间")
	public String getOrgPayTransno() {
		return orgPayTransno;
	}

	public void setOrgPayTransno(String orgPayTransno) {
		this.orgPayTransno = orgPayTransno;
	}
	
	@Length(min=0, max=64, message="汇元银通支付支付流水号长度必须介于 0 和 64 之间")
	public String getHyPayTransno() {
		return hyPayTransno;
	}

	public void setHyPayTransno(String hyPayTransno) {
		this.hyPayTransno = hyPayTransno;
	}
	
	@Length(min=0, max=10, message="支付交易状态            D-代扣(款项由消费者账户转至 第三方支付企业账户)             S-实扣(款项由消费者账户转至 收款方账户)             C-取消(退款)长度必须介于 0 和 10 之间")
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
	@Length(min=0, max=100, message="付款人名称长度必须介于 0 和 100 之间")
	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}
	
	@Length(min=0, max=4, message="性别            B:男            G:女长度必须介于 0 和 4 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=11, message="年龄长度必须介于 0 和 11 之间")
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	@Length(min=0, max=10, message="付款人证件类型长度必须介于 0 和 10 之间")
	public String getPayErcertificateType() {
		return payErcertificateType;
	}

	public void setPayErcertificateType(String payErcertificateType) {
		this.payErcertificateType = payErcertificateType;
	}
	
	@Length(min=0, max=64, message="付款人证件号长度必须介于 0 和 64 之间")
	public String getPayErcertificateNo() {
		return payErcertificateNo;
	}

	public void setPayErcertificateNo(String payErcertificateNo) {
		this.payErcertificateNo = payErcertificateNo;
	}
	
	@Length(min=0, max=11, message="付款币种ID长度必须介于 0 和 11 之间")
	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	
	@Length(min=0, max=10, message="卡种长度必须介于 0 和 10 之间")
	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}
	
	@Length(min=0, max=64, message="付款人银行账号长度必须介于 0 和 64 之间")
	public String getPayEraccountNo() {
		return payEraccountNo;
	}

	public void setPayEraccountNo(String payEraccountNo) {
		this.payEraccountNo = payEraccountNo;
	}
	
	@Length(min=0, max=30, message="付款人手机号长度必须介于 0 和 30 之间")
	public String getPayerPhone() {
		return payerPhone;
	}

	public void setPayerPhone(String payerPhone) {
		this.payerPhone = payerPhone;
	}
	
	@Length(min=0, max=500, message="订单文件的文件名长度必须介于 0 和 500 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTadingTime() {
		return tadingTime;
	}

	public void setTadingTime(Date tadingTime) {
		this.tadingTime = tadingTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAuthenticationTime() {
		return authenticationTime;
	}

	public void setAuthenticationTime(Date authenticationTime) {
		this.authenticationTime = authenticationTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDeclarationTime() {
		return declarationTime;
	}

	public void setDeclarationTime(Date declarationTime) {
		this.declarationTime = declarationTime;
	}
	
	@Length(min=0, max=10, message="商品分类长度必须介于 0 和 10 之间")
	public String getCommodityClassify() {
		return commodityClassify;
	}

	public void setCommodityClassify(String commodityClassify) {
		this.commodityClassify = commodityClassify;
	}
	
	@Length(min=0, max=100, message="收货人名称长度必须介于 0 和 100 之间")
	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	
	@Length(min=0, max=64, message="收货人电话长度必须介于 0 和 64 之间")
	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}
	
	@Length(min=0, max=1000, message="收货人地址长度必须介于 0 和 1000 之间")
	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	
	@Length(min=0, max=10, message="运送方式长度必须介于 0 和 10 之间")
	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	
	@Length(min=0, max=64, message="运单号长度必须介于 0 和 64 之间")
	public String getWayBillno() {
		return wayBillno;
	}

	public void setWayBillno(String wayBillno) {
		this.wayBillno = wayBillno;
	}
	
	@Length(min=0, max=500, message="物流公司长度必须介于 0 和 500 之间")
	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=4, message="业务状态            业务状态:1-暂存,2-申报,默认为2。长度必须介于 0 和 4 之间")
	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
	@Length(min=0, max=10, message="订阅状态            用户订阅单证业务状态的信息, ALL-订阅数据和回执,  DATA-只订阅数据,RET- 只订阅回执            默认：ALL长度必须介于 0 和 10 之间")
	public String getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(String subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}
	
	@Length(min=0, max=10, message="订阅方传输模式            默认为DXP；指中国电子口岸数据交换平台长度必须介于 0 和 10 之间")
	public String getSubTransMode() {
		return subTransMode;
	}

	public void setSubTransMode(String subTransMode) {
		this.subTransMode = subTransMode;
	}
	
	@Length(min=0, max=1000, message="备注长度必须介于 0 和 1000 之间")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Length(min=0, max=1000, message="附言长度必须介于 0 和 1000 之间")
	public String getPostScript() {
		return postScript;
	}

	public void setPostScript(String postScript) {
		this.postScript = postScript;
	}
	
	@Length(min=0, max=2, message="标识            0:新增申报            1:修改申报长度必须介于 0 和 2 之间")
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	@Length(min=0, max=4, message="订单状态            1：确认，待审核            2：审核通过、待报关            3：已报关长度必须介于 0 和 4 之间")
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	//@ 审核拒绝长度必须介于 0 和 5 之间")
	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	@Length(min=0, max=500, message="审核失败原因长度必须介于 0 和 500 之间")
	public String getAuditFailReason() {
		return auditFailReason;
	}

	public void setAuditFailReason(String auditFailReason) {
		this.auditFailReason = auditFailReason;
	}
	
	@Length(min=0, max=4, message="(1未报送2报送处理中3报送成功4报送失败5退单)长度必须介于 0 和 4 之间")
	public String getCustomStatus() {
		return customStatus;
	}

	public void setCustomStatus(String customStatus) {
		this.customStatus = customStatus;
	}
	
	@Length(min=0, max=1000, message="报送失败原因长度必须介于 0 和 1000 之间")
	public String getCausesFailure() {
		return causesFailure;
	}

	public void setCausesFailure(String causesFailure) {
		this.causesFailure = causesFailure;
	}
	
	@Length(min=0, max=64, message="电子口岸返回生成36位唯一序号长度必须介于 0 和 64 之间")
	public String getElecPortReturnNumber() {
		return elecPortReturnNumber;
	}

	public void setElecPortReturnNumber(String elecPortReturnNumber) {
		this.elecPortReturnNumber = elecPortReturnNumber;
	}
	
	@Length(min=0, max=100, message="备注A长度必须介于 0 和 100 之间")
	public String getNotea() {
		return notea;
	}

	public void setNotea(String notea) {
		this.notea = notea;
	}
	
	@Length(min=0, max=100, message="备注B长度必须介于 0 和 100 之间")
	public String getNoteb() {
		return noteb;
	}

	public void setNoteb(String noteb) {
		this.noteb = noteb;
	}
	
	@Length(min=0, max=200, message="备注c长度必须介于 0 和 200 之间")
	public String getNotec() {
		return notec;
	}

	public void setNotec(String notec) {
		this.notec = notec;
	}
	public String getCounts() {
		return counts;
	}

	public void setCounts(String counts) {
		this.counts = counts;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	@Override
	public String toString() {
		return "CbmsOrderform{" +
				"orderId=" + orderId +
				", importBatchNumber='" + importBatchNumber + '\'' +
				", sentCustomsNumber='" + sentCustomsNumber + '\'' +
				", customSn='" + customSn + '\'' +
				", merchantNo='" + merchantNo + '\'' +
				", busEnterpriseNo='" + busEnterpriseNo + '\'' +
				", busEnterpriseName='" + busEnterpriseName + '\'' +
				", orderFormNo='" + orderFormNo + '\'' +
				", transCode='" + transCode + '\'' +
				", modeCode='" + modeCode + '\'' +
				", inspectionQuarantineCode='" + inspectionQuarantineCode + '\'' +
				", customCode='" + customCode + '\'' +
				", commodityName='" + commodityName + '\'' +
				", commodityCount='" + commodityCount + '\'' +
				", commodityUnit='" + commodityUnit + '\'' +
				", goodMsg='" + goodMsg + '\'' +
				", isRef='" + isRef + '\'' +
				", rmbPrices='" + rmbPrices + '\'' +
				", foreignPrices='" + foreignPrices + '\'' +
				", rmbAmount='" + rmbAmount + '\'' +
				", foreignAmount='" + foreignAmount + '\'' +
				", rmbPayAmount='" + rmbPayAmount + '\'' +
				", chargeAmount='" + chargeAmount + '\'' +
				", payPurpose='" + payPurpose + '\'' +
				", payTaxes='" + payTaxes + '\'' +
				", freight='" + freight + '\'' +
				", orgPayEnterpriseName='" + orgPayEnterpriseName + '\'' +
				", orgPayTransno='" + orgPayTransno + '\'' +
				", hyPayTransno='" + hyPayTransno + '\'' +
				", payStatus='" + payStatus + '\'' +
				", payName='" + payName + '\'' +
				", sex='" + sex + '\'' +
				", age='" + age + '\'' +
				", payTime=" + payTime +
				", payErcertificateType='" + payErcertificateType + '\'' +
				", payErcertificateNo='" + payErcertificateNo + '\'' +
				", currencyId='" + currencyId + '\'' +
				", bankCardType='" + bankCardType + '\'' +
				", payEraccountNo='" + payEraccountNo + '\'' +
				", payerPhone='" + payerPhone + '\'' +
				", fileName='" + fileName + '\'' +
				", confirmTime=" + confirmTime +
				", tadingTime=" + tadingTime +
				", authenticationTime=" + authenticationTime +
				", declarationTime=" + declarationTime +
				", commodityClassify='" + commodityClassify + '\'' +
				", consigneeName='" + consigneeName + '\'' +
				", consigneePhone='" + consigneePhone + '\'' +
				", consigneeAddress='" + consigneeAddress + '\'' +
				", deliveryType='" + deliveryType + '\'' +
				", wayBillno='" + wayBillno + '\'' +
				", logisticsCompany='" + logisticsCompany + '\'' +
				", createdTime=" + createdTime +
				", updateTime=" + updateTime +
				", serviceStatus='" + serviceStatus + '\'' +
				", subscriptionStatus='" + subscriptionStatus + '\'' +
				", subTransMode='" + subTransMode + '\'' +
				", note='" + note + '\'' +
				", postScript='" + postScript + '\'' +
				", identification='" + identification + '\'' +
				", orderStatus='" + orderStatus + '\'' +
				", auditStatus='" + auditStatus + '\'' +
				", auditFailReason='" + auditFailReason + '\'' +
				", customStatus='" + customStatus + '\'' +
				", causesFailure='" + causesFailure + '\'' +
				", elecPortReturnNumber='" + elecPortReturnNumber + '\'' +
				", notea='" + notea + '\'' +
				", noteb='" + noteb + '\'' +
				", notec='" + notec + '\'' +
				", counts='" + counts + '\'' +
				", declarationBatchHumber='" + declarationBatchHumber + '\'' +
				", transId='" + transId + '\'' +
				", transStatus='" + transStatus + '\'' +
				", declareType='" + declareType + '\'' +
				", authMsg='" + authMsg + '\'' +
				", mainCustomCode='" + mainCustomCode + '\'' +
				", beginDeclarationTime=" + beginDeclarationTime +
				", endDeclarationTime=" + endDeclarationTime +
				'}';
	}
}