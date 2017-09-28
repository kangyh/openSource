namespace java com.heepay.manage.rpc.service

/**外部商户用户信息对象*/
struct ExternalMerchantUserThrift {
        /**用户id，保存时不用*/
        1:string id
        /**登录名，邮箱，必填*/
        2:string loginName,
        /**昵称，以后用*/
        3:string loginNickname,
        /**用户类型，必填*/
        4:string userType,
        /**登录密码，必填*/
        5:string loginPassword,
        /**支付密码，必填*/
        6:string payPassword,
        /**安全问题，必填*/
        7:string secretQuestion,
        /**安全问题答案，必填*/
        8:string answerSecretQuestion,
        /**允许登录ips，以后用*/
        9:string loginIpsAllowed,
         /**最后一次登录ip*/
        10:string lastLoginIp,
         /**联系手机*/
        11:string phone,
         /**手机号，如有手机绑定请设置到mobile中*/
        12:string mobile,
         /**QQ号*/
        13:string qq,
         /**联系地址*/
        14:string linkAddress,
         /**电脑mac信息*/
        15:string macInfo,
         /**电脑disk信息*/
        16:string diskInfo,
         /**电脑cpu信息*/
        17:string cpuInfo,
         /**安全控件版本*/
        18:string passGuardCtrlVersion,
         /**客户端请求类型*/
        19:string clientType,
         /**登录账号状态，必填*/
        20:string status,
         /**上次登录时间*/
        21:string lastLoginDate,
         /**备注*/
        22:string remarks,
         /**来源，必填*/
        23:string source,
         /**允许登陆的系统，必填*/
        24:string allowSystem
}

/**外部商户信息对象*/
struct ExternalMerchantThrift {
        /**用户的id，不用传*/
        1:string userId,
        /**登录email，必填*/
        2:string email,
        /**公司名称，必填*/
        3:string companyName,
        /**法人代表，必填*/
        4:string legalRepresentative,
        /**公司联系电话*/
        5:string companyPhone,
        /**法人手机号码，必填*/
        6:string legalMobile,
        /**公司网址，必填*/
        8:string website,
        /**公司注册地址，必填*/
        9:string businessAddress,
        /**国家代码*/
        10:string contryCode,
        /**国家名称*/
        11:string contryName,
        /**省、自治区、州代码，必填*/
        12:string provinceCode,
        /**省、自治区、州名称，必填*/
        13:string provinceName,
        /**城市代码，必填*/
        14:string cityCode,
        /**城市名称，必填*/
        15:string cityName,
        /**县代码，必填*/
        16:string countyCode,
        /**县名称，必填*/
        17:string countyName,
        /**联系地址*/
        18:string contactAddress,
        /**联系人，必填*/
        19:string contactor,
        /**商户类型，必填*/
        20:string type,
        /**商户等级*/
        21:string level,
        /**手续费收取类型*/
        22:string chargeType,
        /**商户的请求方式*/
        23:string requestWays,
        /**商户请求的版本号*/
        24:string requestVersion,
        /**允许商户请求的IP地址列表通过|分隔*/
        25:string allowedIps,
        /**签约类型*/
        26:string signedType,
        /**商户状态*/
        27:string status,
        /**运营修改时间*/
        28:string opAuditTime,
        /**运营修改人*/
        29:string opAuditor,
        /**风控审核状态*/
        30:string rcAuditStatus,
        /**风控审核时间*/
        31:string rcAuditTime,
        /**风控审核人*/
        32:string rcAuditor,
        /**法务审核状态*/
        33:string legalAuditStatus,
        /**法务审核时间*/
        34:string legalAuditTime,
        /**风控审核人*/
        35:string legalAuditor,
        /**销售人员*/
        36:string salesId,
        /**当前负责人*/
        37:string inchargerId,
        /**备注*/
        38:string remark,
        /**创建时间，必填*/
        39:string createTime,
        /**商户欢迎信息*/
        40:string welcomeMessage,
        /**证件类型，必填*/
        41:string certificateType,
        /**营业执照结束时间，长期请传0000-00-00，必填*/
        42:string businessLicenseEndTime,
        /**组织机构代码*/
        43:string organizationCode,
        /**税务登记证号码*/
        44:string taxRegistrationCertificateNo,
        /**经营范围，必填*/
        45:string businessScope,
        /**法人代表身份证号，必填*/
        46:string legalIdcard,
        /**法人代表证件有效期结束，必填*/
        47:string legalCertificateEndTime,
        /**联系人身份证号，必填*/
        48:string contactorIdcardNo,
        /**联系人证件有效期结束，必填*/
        49:string contactorCertificateEndTime,
        /**联系人手机号，必填*/
        50:string contactorPhone,
        /**IPC备案号，必填*/
        51:string ipcNo,
        /**开户许可证，必填*/
        52:string permitsAccounts,
        /**法人代表证件照(正)，必填*/
        53:string legalCertificateFront,
        /**法人代表证件照(反)，必填*/
        54:string legalCertificateBack,
        /**税务登记证*/
        55:string taxRegistrationCertificate,
        /**组织机构代码证*/
        56:string organizationCodeCertificate,
        /**营业执照文件本地存储路径及文件名*/
        57:string businessLicenseFile,
        /**营业执照号码*/
        58:string businessLicenseNo,
        /**拒绝理由*/
        59:string objection,
        /**留存金额*/
        60:string retainedAmount,
        /**银联区域编码省代码*/
        61:string unionPayProvinceCode,
        /**银联区域编码省名称*/
        62:string unionPayProvinceName,
        /**银联区域编码市代码*/
        63:string unionPayCityCode,
        /**银联区域编码市名称*/
        64:string unionPayCityName,
        /**商户行业基本类型*/
        65:string mccType,
        /**商户行业基本类型*/
        66:string mccServer,
        /**商户行业基本类型*/
        67:string mccDetail,
        /**商户MCC码*/
        68:string industryCategory,
        /**上级商户Id，必填*/
        69:string superior_id
}

service ExternalMerchantService {
    string saveExternalMerchant(1:ExternalMerchantUserThrift externalMerchantUserThrift,2:ExternalMerchantThrift externalMerchantThrift)
}