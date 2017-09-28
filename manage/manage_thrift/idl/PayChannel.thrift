namespace java com.heepay.manage.rpc.service


struct PayChannelThrift {
    1:string channelCode;		// 支付通道代码
    2:string channelName;		// 支付通道名称
    3:string bankName;		// 银行名称
    4:string bankNo;   // 银行代码
    5:string channelPartnerName;		// 通道合作方
    6:string channelPartnerCode;   // 通道合作方代码
    7:string channelTypeName;		// 支付通道类型
    8:string channelTypeCode;    // 支付通道类型代码
    9:string cardTypeName;		// 银行卡类型
    10:string cardTypeCode;   // 银行卡类型代码
    11:string accountType;   // 对公对私标识代码
    12:string businessType;   // 付款类型代码
    13:string chargeDeductType; //手续费扣除方式
    14:string chargeReturnTag; // 是否退还手续费
    15:string effectStartDate;		// 有效开始时间
    16:string effectEndDate;		// 有效结束时间
    17:string costType;		// 成本类型
    18:string costRate;		// 成本按比例
    19:string costCount;    // 成本按笔数
    20:string status;		// 状态
    21:string contractDate;		// 合约时间
    22:string settleType;		// 本金结算类型
    23:string settlePeriod;		// 本金结算周期
    24:string sort;		// 优先级别
    25:string perlimitAmount;   // 单笔限额
    26:string daylimitAmount;   // 单日限额
    27:string monlimitAmount;   // 单月限额
    28:string orderSettlePeriod; //通道结算周期

}

struct CertifyThrift{
	1:string cost;//成本
	2:string channelCode;//支付通道代码
}

service PayChannelCacheService {
    void queryMerchantChannel(),
    string queryMerchantChannelBy(1:string bankNo,2:string channelTypeCode,3:string cardTypeCode,4:string accountType,5:string businessType,6:string merchantId),
    void queryPayChannel(),
    string queryBestChannel(1:string bankNo,2:string channelTypeCode,3:string cardTypeCode,4:string accountType,5:string businessType,6:string costType),
    void queryChannelType(),
    string queryBankInfoByChannelType(1:string channelTypeCode),
    string queryBankInfoByProductCode(1:string productCode),
    void queryPayChannelAll(),
    string queryChannelByChannelCode(1:string channelCode),
    list<PayChannelThrift> selectPayChannelAllList(),
    list<PayChannelThrift> selectPayChannelByType(1:string bankNo,2:string channelTypeCode,3:string bankCardType,4:string productCode),
    list<PayChannelThrift> queryChannelTypeList(),
    CertifyThrift queryCertifyCost(),
    string queryCertifyChannelByChannelCode(1:string channelCode),
    string getRouteByChannelCode(1:string channelCode),
    list<PayChannelThrift> queryPayChannelByProductCode(1:string productCode),
    void bankIdSync(),
    string queryBankId(1:string channelCode),
    string selectPayChannelByMerchantId(1:string bankNo,2:string channelTypeCode,3:string bankCardType,4:string merchantId,5:string productCode),
    string queryCertifyChannelByChannelType(1:string channelType),

}