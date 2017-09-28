namespace java com.heepay.rpc.gateway.model

//这个结构体定义了快捷支付的请求信息
struct QuickPayRequestVO{
    /**
     * 公共参数
     */
    /**订单号/支付流水号*/
    1:required string paymentId;
    /**支付人姓名*/
    2:required string custName;
    /**卡号*/
    3:required string cardNo;
    /**短信验证码*/
    4:required string validCode;
    /**手机号*/
    5:required string phoneNo;
    /**金额*/
    6:required string amount;
    /**商户号*/
    7:required string merchantId;
    /**终端号*/
    8: string terminalId;
    /**支付时间*/
    9:required string entryTime;

    /**
     * 快钱特殊字段
     */
    /**客户号*/
    10:required string custId;
    /**缩略卡号*/
    11:required string storableCardNo;
    /**卡有效期*/
    12: string expiredDate;
    /****CVV2**/
    13: string cvv2;
    /**持卡人国籍*/
    14: string issuerCountry;
    /**交易发生地点类型*/
    15: string siteType;
    /**交易发生地点编号*/
    16: string siteID;
    /**持卡人身份证号*/
    17:required string cardHolderId;
    /**证件类型*/
    18:required string idType;
    /**特殊交易标志*/
    19: string spFlag;
    

    /**
     * 扩展字段
     */
    /**是否保存鉴权信息*/
    20: string savePciFlag;
    /**token*/
    21:required string token;
    /**支付批次*/
    22: string payBatch;

    /**
     * 建行特殊字段
     */
    /**交易标志 0-快捷支付，1-信用卡分期支付（暂不支持1）*/
    23:required string transFlag;
    /**柜台号*/
    24: string cuntNo;
    /**交易货币代码 币种，目前仅支持人民币“01”*/
    25:required string transCurrencyType;
    /**交易钞汇标志 0－钞户 1－汇户，目前取“0”*/
    26:required string transCurrencyFlag;
    /**备注*/
    27: string remark;
    /**分期期数*/
    28: string instlNum;
    /**二级商户代码*/
    29: string subMerchantId;
    /**二级商户名称*/
    30: string submerchantName;
    /**二级商户类别代码*/
    31: string subMerchantType;
    /**二级商户类别名称*/
    32: string subMerchantTypeName;
    /**商品类别代码*/
    33: string goodsType;
    /**商品类别名*/
    34: string goodsTypeName;
    /**账户借贷标记*/
    35: string cardType;
    /**交易类型*/
    36: string transType;
    /**平台名称*/
    37: string siteName;
    /**平台网址*/
    38: string siteUrl;

    /**
     * 路由字段
     */
    /**银行代码*/
    39:required string bankId;
    /**通道合作方代码*/
    40: string channelPartnerCode;
    /**通道类型代码*/
    41: string channelTypeCode;
    /**银行卡类型代码*/
    42:required string cardTypeCode;
    /**支付通道代码*/
    43:required string payChannelCode;
}

//这个结构体定义了快捷支付的返回信息
struct QuickPayResponseVO{
    /**
     * 公共参数
     */
    /**订单号/支付流水号*/
    1:required string paymentId;
    /**金额*/
    2:required string amount;
    /**交易时间*/
    3:required string transTime;
    /**商户号*/
    4:required string merchantId;


    /**
     * 快钱特殊字段
     */
    /**交易类型*/
    5:required string transType;
    /**消息状态*/
    6:required string interactiveStatus;
    /**终端号*/
    7:required string terminalId;
    /**支付时间*/
    8:required string entryTime;
    /**客户号*/
    9:required string custId;
    /**系统参考号*/
    10:required string refNumber;
    /**应答码*/
    11:required string responseCode;
    /**应答码文本消息*/
    12: required string responseMsg;
    /**通道代码*/
    13:required string payChannelCode;
    /**卡组织编号*/
    14: string cardOrg;
    /**发卡银行名称*/
    15: string issuer;
    /**缩略卡号*/
    16: string storableCardNo;
    /**授权码*/
    17: string authorizationCode;
	
    /**
     * 建行特殊字段
     */
    /**银行流水号*/
    18: string bankSid;
    /**币种*/
    19: string curCode;

}
