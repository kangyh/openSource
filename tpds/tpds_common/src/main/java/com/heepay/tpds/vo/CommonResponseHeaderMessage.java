package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年2月10日 上午9:58:26
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
public class CommonResponseHeaderMessage {
	public String version		;//版本号	1.0
	public String merchantCode	;//接入系统号	10	接入系统代码
	public String txnType		;//6	交易代码
	public String fileName ;
	public String clientSn		;//32	客户端请求流水号
	public String clientDate	;//	8	客户端日期（YYYYMMDD）
	public String clientTime	;//	6	客户端时间戳（HHmmss）
	public String serviceSn		;//32	资金存管系统流水号
	public String serviceDate	;//	8	资金存管系统日期（YYYYMMDD）
	public String serviceTime	;//	6	资金存管系统处理时间戳（HHmmss）
	public String respCode	;//		系统返回“0000”时为交易成功，系统返回非“0000”时取应答信息为错误信息；
	public String respMsg	;//O		响应信息
	public String signTime	;//	30	Unix系统当前时间，单位毫秒，如：1478094942382 
	public String signature	;//		签名方法：
	//签名KEY|加签时间戳|value1|value2，对整个应用报文体（inBody）内容块加密，包含上送的字段值为空串，使用JAVA的treeMap的排序机制，根据所有参数key按照字母升序对value排序，然后使用SHA-256算法加签生成加签串 
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getClientSn() {
		return clientSn;
	}
	public void setClientSn(String clientSn) {
		this.clientSn = clientSn;
	}
	public String getClientDate() {
		return clientDate;
	}
	public void setClientDate(String clientDate) {
		this.clientDate = clientDate;
	}
	public String getClientTime() {
		return clientTime;
	}
	public void setClientTime(String clientTime) {
		this.clientTime = clientTime;
	}
	public String getServiceSn() {
		return serviceSn;
	}
	public void setServiceSn(String serviceSn) {
		this.serviceSn = serviceSn;
	}
	public String getServiceDate() {
		return serviceDate;
	}
	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}
	public String getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getSignTime() {
		return signTime;
	}
	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
}
