package com.heepay.tpds.vo;
/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年2月10日 上午9:44:25
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
public  class CommonRequestHeaderMessage {
		private String version ; //版本号ID     1.0
		private String merchantCode ;//接入系统号   接入系统代码
		private String txnType ;//交易代码
		private String clientSn ;//客户端请求流水号  客户端请求流水号
		private String clientDate ;//客户端日期   客户端日期（YYYYMMDD）
		private String clientTime ;//客户端时间戳 客户端时间戳（HHmmss）
		private String fileName ;//文件名称
		private String signTime ;//加签时间戳   Unix系统当前时间，单位毫秒，如：1478094942382
		private String signature ;//签名 签名方法：
		//签名KEY|加签时间戳|value1|value2，对整个应用报文体（inBody）内容块加密，包含上送的字段值为空串，
		//使用JAVA的treeMap的排序机制，根据所有参数key按照字母升序对value排序，然后使用SHA-256算法加签生成加签串

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
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
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

}
