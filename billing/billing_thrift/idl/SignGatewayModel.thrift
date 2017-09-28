namespace java com.heepay.rpc.gateway.model

//这个结构体定义了签约的请求信息
struct SignRequestVO{
	/**商户号*/
	1: required string merchantId;
	/**客户号*/
	2: required string custId;
	/**客户姓名/持卡人姓名*/
	3: required string custName;
	/**证件类型*/
	4: string idType;
	/**证件号/持卡人身份证号*/
	5: required string cardId;
	/**手机号*/
	6: required string phoneNO;
	/**外部跟踪编号/支付订单号*/
	7: required string paymentId;
	/**交易金额*/
	8: required string amount;

	/**
	 * 快钱特殊字段
	 */
	/**加密持卡人证件号*/
	9: string cardHolderIdEn;
	/**卡号*/
	10: required string pan;
	/**加密卡号*/
	11: string panEn;
	/**缩略卡号*/
	12: required string storablePan;
	/**银行代码*/
    13: string bankId;
	/**卡效期*/
    14: string expiredDate;
	/**卡校验码*/
    15: string cvv2;
	/**加密卡校验码*/
    16: string cvv2En;

	/**
	 * 建行特殊字段
	 */
	/**交易标志*/
	17: string transFlag;
	/**柜台号*/
	18: string cuntNo;
	/**分期期数*/
    19: string instlNum;
	/**账户借贷标志*/
    20: string cardType;
    /**交易日期*/
    21: required string entryDate;
    /**交易时间*/
    22: required string entryTime;
    /**请求方序列号*/
    23: required string requestSn;
  	/**签名*/
    24: string signature;
	/**终端信息*/
    25: string terminalMsg;
    /**交易码*/
    26:  string transCode;

	/**
     * 路由字段
     */
    /**通道合作方代码*/
    27: string channelPartnerCode;
    /**通道类型代码*/
    28: string channelTypeCode;
    /**银行卡类型代码*/
    29:required string cardTypeCode;
}

//这个结构体定义了签约的返回信息
struct SignResponseVO{
	/**支付订单号*/
	1: required string paymengtId;
	/**交易日期*/
	2: required string entryDate;
	/**交易时间*/
	3: string entryTime;
	/**令牌信息*/
	4: required string token;
	/**应答码*/
	5: required string responseCode;
	/**应答码文本消息*/
	6: required string responseMsg;
	/**通道代码*/
    7: required string payChannelCode;
	/**
	 * 快钱特殊字段
	 */
	/**商户号*/
	8: required string merchantId;
	/**客户号*/
	9: string custId;
	/**缩略卡号*/
	10: string storablePan;

	/**
	 * 建行特殊字段
	 */
	/**附加验证标志 0-不验，1-银行验证短信*/
	11: string validFlag;
	/**交易状态*/
	12: required string status;
	/**请求方序列号*/
    13: required string requestSn;
    /**交易码*/
    14: required string transCode;
    /**签名*/
    15: string signature;
	/**终端信息*/
    16: string terminalMsg;
    
}
