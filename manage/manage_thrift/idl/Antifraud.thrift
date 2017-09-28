namespace java com.heepay.manage.rpc.service

struct AntifraudThrift {
    1:string merchantId, //商户id
    2:string loginName,//登录名
    3:string userType,//用户类型
    4:string legalRepresentative,//法人代表
    5:string lastLoginIp,//最后登录ip
    6:string mobile,//绑定手机号
    7:string macInfo,//mac信息
    8:string diskInfo,//计算机硬盘信息
    9:string cpuInfo,//cpu信息
    10:string status,//状态
    11:string lastLoginDate,//最后登录日期
    12:string companyName,//公司名称
    13:string certificateType,//证件类型
    14:string businessLicenseEndTime,//营业执照结束时间
    15:string organizationCode,//组织机构代码
    16:string taxRegistrationCertificateNo,//税务登记证号码
    17:string businessScope,//经营范围
    18:string legalIdcard,//法人代表身份证号
    19:string legalCertificateEndTime,// 法人代表证件有效期结束
    20:string contactorIdcardNo,// 联系人身份证号
    21:string contactorCertificateEndTime,// 联系人证件有效期结束
    22:string contactorPhone,// 联系人手机号
    23:string ipcNo,// IPC备案号
    24:string businessLicenseNo,// 营业执照号码
    25:string bankId,//银行id
    26:string bankName,//银行名称
    27:string bankNo,//银行卡号
    28:string bankCardType,//卡类型
    29:string bankStatus,//银行卡认证状态
    30:string associateLineNumber//联行号
        
}

service AntifraudService {
    list<AntifraudThrift> getAntifraudInfoByMerchantId(1:list<string> merchantIds),
    list<AntifraudThrift> getAntifraudInfoByLoginName(1:list<string> loginNames),
}