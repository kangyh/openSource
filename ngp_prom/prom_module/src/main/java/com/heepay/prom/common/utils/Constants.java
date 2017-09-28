package com.heepay.prom.common.utils;

/**
 * 
 * 描 述：常量类
 *
 * 创 建 者： ly 创建时间： 2016年11月1日 下午4:13:14 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */

public class Constants {
	//默认排序
    public static final String DEFAULT_SORT = "1";

    //是否退还手续费
    public static final String DEFAULT_RETURN_TAG = "1";

    //通道结算周期
    public static final String DEFAULT_CHANNEL_SETTLE = "1";

    //银行信息唯一性
    public static final Integer LENGTH = 1;


    // 银行信息缓存的key
    public static final String BANK_INFO_KEY = "bankInfoCatch";

    // 商户通道key缓存
    public static final String MERCHANT_CHANNEL_KEY = "merchantChannelKeyCollection";

    // 最优通道key缓存
    public static final String BEST_PAY_CHANNEL_KEY = "bestPayChannelKeyCollection";

    // 所有通道信息key缓存
    public static final String PAY_CHANNEL_KEY = "allPayChannelKeyCollection";

    // 通道对应的银行信息Key收集
    public static final String CHANNEL_TYPE_KEY = "bankInfoOfeachChannelTypeKeyCollection";

    // 通道对应的路由key缓存
    public static final String PAY_CHANNEL_ROUTE = "payChannelRoute";

    //产品支持银行及银行卡缓存key
    public static final String BANK_OF_PRODUCT = "bankInfoOfProduct";

    //产品支持银行及银行卡缓存key的收集缓存key
    public static final String BANK_OF_PRODUCT_KEY = "bankInfoOfProductKeyCollection";

    // 处理成功
    public static final String SUCCESS = "success";

    // 处理失败
    public static final String FAIL = "fail";

    // 重置登录密码标识
    public static final String RESET_LOGIN_PW = "重置登录密码";

    // 重置支付密码标识
    public static final String RESET_PAY_PW = "重置支付密码";

    // utf-8格式
    public static final String ENCODE_TYPE_UTF = "UTF-8";

    // 手机号未绑定
    public static final String NON_BOUNDLING = "未绑定";

    // 验证密码错误
    public static final String ERROR_PW = "errorPassword";

    // 默认商户id
    public static final String MERCHANT_DEFAULT_ID = "1";

    // 一分钟的秒数
    public static final Integer ONE_MINUTE_SECOND = 60;

    // 中国银联商户号 收单行代码（3位）
    public static final String MERCHANT_POS_CODE_ACQUIRING_BANK = "900";
    
    // 费率全部银行默认标示
    public static final String MERCHANT_RATE_ALL_BANK = "ALL";
    
    //B2B
    public static final String B2B = "B2B";
    
    //微信
    public static final String WECHAT = "微信";

    //支付宝
    public static final String ALIPAY = "支付宝";

    //银联
    public static final String UNOPAY = "银联";

    //量化派
    public static final String QUAGRP = "量化派";

    //默认开通的产品
    public static final String MERCHANT_DEFAULT_PRODUCT = "CP01CP04CP05";

    //动态口令标识
    public static final String DYNAMIC_IDENTIFICATION  = "_B0C18D9D26B945B39E2C9084";

    //验证动态口令后的返回码    1=非法来源ip，2=无效的参数，3=无效签名，-1=验证口令失败 0=验证口令成功
    public static final String DYNAMIC_STATUS = "0";

    //T+X产品类型
    public static final String SETTLEX = "SettleX";

    //联行号城市银行转换类型
    public static final String LINEBANK = "LineBank";

    //快捷支付2
    public static final String QUICKPAY2 = "快捷支付2";

    //通道路由
    public static final String ROUTEMAP = "RouteMap";

    //通道合作方
    public static final String CHANNEL_PARTNER = "ChannelPartner";

    //支付通道类型
    public static final String CHANNEL_TYPE = "ChannelType";

    //所属平台类型
    public static final String BY_PROJECT = "ByProject";

    //所属公司类型
    public static final String BY_COMPANY = "ByCompany";

    //所属公司类型
    public static final String MERCHANT_FLAG = "MerchantFlag";

    //不校验用户
    public static final String USER_NO_CHECK = "UserNoCheck";

	//综合通道产品类型
    public static final String COMPREHENSIVE_CODE = "ComprehensiveCode";

    //系统通用配置字典type
    public static final String SYS_COMMON = "SysCommon";

    //是否验证动态口令
    public static final String IS_CHECK = "IsCheck";

    //产品是否根据业务类型选择
    public static final String IS_BUSINESSTYPE = "IsBusinessType";

    //线上签约产品类型
    public static final String ONLINE_PRODUCT = "OnlineProduct";

    //维系员工类型
    public static final String INCHARGER = "InCharger";

    //密码错误多少次禁止登录
    public static final String LOGIN_FLAG_NUM = "LoginFlagNum";

    //验证动态口令标示
    public static final String IS_CHECK_YES = "yes";

    //综合通道考拉partnerCode(除实名认证)
    public static final String KOALA = "KOALA";
    //综合通道岂安partnerCode(除实名认证)
    public static final String QARED = "QARED";

    //商户类型
    public static final String CUS_TYPE= "{'自然人':'01','企业商户':'02','个体工商户':'03'}";
    //商户属性
    public static final String CUS_NATURE= "{'实体特约商户': '01','网络特约商户': '02','实体兼网络特约商户':'03'}";
    //个人证件类型
    public static final String  LEG_DOC_TYPE= "{'身份证':'01','军官证':'02','护照':'03','户口簿':'04','士兵证':'05','港澳来往内地通行证':'06','台湾同胞来往内地通行证':'07',"
    		+ "'临时身份证':'08','外国人居留证':'09','警官证':'10','其他':'99'}";
    //企业证件类型
    public static final String  DOC_TYPE= "{'营业执照编码':'01','统一社会信息代码':'02','组织机构代码证':'03','经营许可证':'04','税务登记证':'05','其他':'99'}";
    //商户状态
    public static final String STATUS= "{'启用':'01','关闭（暂停）':'02','注销':'03'}";
    //风险状况
    public static final String RISK_STATUS= "{'合规':'01','风险':'02'}";
    //开通业务种类
    public static final String OPEN_TYPE= "{'POS':'01','条码':'02','其他':'99'}";
    //账户类型
    public static final String ACCOUNT_TYPE= "{'借记卡':'01','贷记卡':'02','支付账户':'03'}";
    //计费类型
    public static final String CHAGE_TYPE= "{'标准':'01','优惠':'02','减免':'03'}";
    //拓展方式
    public static final String EXPAND_TYPE= "{'自主拓展':'01','外包服务机构推荐':'02'}";
    //清算网络类型
    public static final String NETWORK_TYPE= "{'中国银联':'01','网络支付清算平台':'02','清算总中心':'03','农信银':'04','城商行':'05','同城结算中心':'06','其他':'99'}";
    
    public static final String PROVINCE = 
    		"{'湖南省':'430000','河南省':'410000','江苏省':'320000','广西壮族自治区':'450000',"
    		+ "'云南省':'530000','西藏自治区':'540000',"
    		+ "'北京市':'110000','宁夏回族自治区':'640000','浙江省':'330000',"
    		+ "'福建省':'350000','四川省':'510000','香港特别行政区':'810000',"
    		+ "'山西省':'140000',"
    		+ "'陕西省':'610000','河北省':'130000','安徽省':'340000','上海市':'310000',"
    		+ "'澳门特别行政区':'820000','青海省':'630000','广东省':'440000','辽宁省':'210000',"
    		+ "'江西省':'360000','甘肃省':'620000','湖北省':'420000','贵州省':'520000',"
    		+ "'山东省':'370000','台湾省':'710000','内蒙古自治区':'150000',"
    		+ "'海南省':'460000','新疆维吾尔自治区':'650000','天津市':'120000',"
    		+ "'黑龙江省':'230000','重庆市':'500000','吉林省':'220000'}";

    public static final String[] PERSON_EXPORT= {"cusType","cusNature","regName","cusName","cusNameEn","docType","docCode","cusCode","induType","bankNo","openBank","regAddrProv","regAddrDetail",
			"addrProv","addrDetail","url","serverIp","icp","contName","contPhone","occurarea","cusEmail","networkType","status","startTime","endTime","riskStatus",
			"openType","chageType","accountType","expandType","outServiceName","outServiceCardType","outServiceCardCode","outServicelegCardType","outServicelegCardCode","orgId","fillerTime","filler"};
    
    public static final String[] BLACK_ITEM_VALUE= {"blackItemValue"};
    public static final String[]  MERCHANT_EXPORT = {"cusType","cusNature","regName","cusName","cusNameEn","docType","docCode","legDocName","legDocType","legDocCode",
			"cusCode","induType","bankNo","openBank","regAddrProv","regAddrDetail",
			"addrProv","addrDetail","url","serverIp","icp","contName","contPhone","occurarea","cusEmail","networkType","status","startTime","endTime","riskStatus","shareHolder",
			"openType","chageType","accountType","expandType","outServiceName","outServiceCardType","outServiceCardCode","outServicelegCardType","outServicelegCardCode","orgId","fillerTime","filler"};
    
    
    public static final String[] HFTPDS_EXPORT= {"merchantId","merchantName","flowNo","transNo","bankSuccessTime","transAmt","transState","settleTime","checkStatus"};
}
