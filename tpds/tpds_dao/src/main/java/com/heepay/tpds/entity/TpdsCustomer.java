package com.heepay.tpds.entity;

public class TpdsCustomer {
    private Integer customerId;

    /**
     * 会员编号
     */
    private String customerNo;

    /**
     * 会员类型
            00:个人
            01：企业
     */
    private String type;

    /**
     * 会员角色
            00：投资方
            01：融资方
            02：担保方
            09：全部
     */
    private String role;

    /**
     * 证件类型
            00：身份证
            01：居住证
            02：签证
            03：护照
            04：户口本
            05：军人证
            06：港澳通行证
     */
    private String certType;

    /**
     * 绑卡标识
            00:绑卡
            01：不绑卡
            99：全部
     */
    private String bindFlag;

    /**
     * 用户名
     */
    private String useName;

    /**
     * 证件号码
     */
    private String certNo;

    /**
     * 身份证正面影像
     */
    private String certFimage;

    /**
     * 身份证反面影像
     */
    private String certBimage;

    /**
     * 身份证详情
            不同项之间”|”分隔，顺序如下：
            身份证号码|姓名|性别(M-女，F-男)|生日|民族|住址|发证机关|有效期起始日期|有效期截止日期
     */
    private String certInfo;

    /**
     * 身份证有效起始日期
     */
    private String valiDate;

    /**
     * 有效期截止日期
     */
    private String expDate;

    /**
     * 职业类型
            0政府机关
            1邮政
            3电信
            4 IT
            5商业/贸易
            6金融
            7旅游/饭店
            8医疗卫生
            9房地产
            10交通运输
            11法律司法
            12文化娱乐
            13媒体广告
            14科研/教育
            15学生
            16农林牧渔
            17矿业/制造
            18自由职业
            X其他
     */
    private String jobType;

    /**
     * 职业描述
            当jobType为X其他时该字段必输
     */
    private String job;

    /**
     * 邮政编码
     */
    private String postCode;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 名族
            10	汉族,20	少数民族,2002	蒙古族       ,2003	回族         ,2004	维吾尔族     ,2005	藏族         ,2006	苗族         ,2007	彝族         ,2008	壮族         ,2009	布依族       ,2010	朝鲜族      ,2011	满族        ,2012	侗族        ,2013	瑶族        ,2014	白族        ,2015	土家族      ,2016	哈尼族      ,2017	哈萨克族    ,2018	傣族        ,2019	黎族        ,2020	僳僳族      ,2021	佤族        ,2022	畲族        ,2023	高山族      ,2024	拉祜族      ,2025	水族        ,2026	东乡族      ,2027	纳西族      ,2028	景颇族      ,2029	柯尔克孜族  ,2030	土族        ,2031	达斡尔族    ,2032	仫佬族      ,2033	羌族        ,2034	布朗族      ,2035	撒拉族      ,2036	毛难族      ,2037	仡佬族      ,2038	锡伯族      ,2039	阿昌族      ,2040	普米族      ,2041	塔吉克斯坦族,2042	怒族        ,2043	乌孜别克族  ,2044	俄罗斯族    ,2045	鄂温克族    ,2046	崩龙族      ,2047	保安族      ,2048	裕固族      ,2049	京族        ,2050	塔塔尔族    ,2051	独龙族      ,2052	鄂伦春族    ,2053	赫哲族      ,2054	门巴族      ,2055	珞巴族      ,2056	基诺族,97	其他,98	外国血统
     */
    private String national;

    /**
     * 手机号
     */
    private String phoneNo;

    /**
     * 营业执照号
     */
    private String busiliceNo;

    /**
     * 营业执照存放地址
     */
    private String busiliceDir;

    /**
     * 组织机构代码
     */
    private String orgcodeNo;

    /**
     * 组织机构存放地址
     */
    private String orgcodeDir;

    /**
     * 税务登记号
     */
    private String taxregisNo;

    /**
     * 税务登记号地址
     */
    private String taxregisDir;

    /**
     * 统一社会信用代码
     */
    private String unisoccreCode;

    /**
     * 统一社会信用地址
     */
    private String unisoccreDir;

    /**
     * 绑卡类型
            00:充值
            01：提现
     */
    private String bindType;

    /**
     * 卡账标识
            00：银行卡
     */
    private String accType;

    /**
     * 原绑定银行卡号
     */
    private String oldbankaccountNo;

    /**
     * 银行卡号
     */
    private String bankaccountNo;

    /**
     * 银行账户名称
     */
    private String bankaccountName;

    /**
     * 银行手机号
     */
    private String bankaccountTelno;

    /**
     * 台账账户（资金存管系统）
     */
    private String accNo;

    /**
     * 备注
     */
    private String note;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType == null ? null : certType.trim();
    }

    public String getBindFlag() {
        return bindFlag;
    }

    public void setBindFlag(String bindFlag) {
        this.bindFlag = bindFlag == null ? null : bindFlag.trim();
    }

    public String getUseName() {
        return useName;
    }

    public void setUseName(String useName) {
        this.useName = useName == null ? null : useName.trim();
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo == null ? null : certNo.trim();
    }

    public String getCertFimage() {
        return certFimage;
    }

    public void setCertFimage(String certFimage) {
        this.certFimage = certFimage == null ? null : certFimage.trim();
    }

    public String getCertBimage() {
        return certBimage;
    }

    public void setCertBimage(String certBimage) {
        this.certBimage = certBimage == null ? null : certBimage.trim();
    }

    public String getCertInfo() {
        return certInfo;
    }

    public void setCertInfo(String certInfo) {
        this.certInfo = certInfo == null ? null : certInfo.trim();
    }

    public String getValiDate() {
        return valiDate;
    }

    public void setValiDate(String valiDate) {
        this.valiDate = valiDate == null ? null : valiDate.trim();
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate == null ? null : expDate.trim();
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType == null ? null : jobType.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national == null ? null : national.trim();
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

    public String getBusiliceNo() {
        return busiliceNo;
    }

    public void setBusiliceNo(String busiliceNo) {
        this.busiliceNo = busiliceNo == null ? null : busiliceNo.trim();
    }

    public String getBusiliceDir() {
        return busiliceDir;
    }

    public void setBusiliceDir(String busiliceDir) {
        this.busiliceDir = busiliceDir == null ? null : busiliceDir.trim();
    }

    public String getOrgcodeNo() {
        return orgcodeNo;
    }

    public void setOrgcodeNo(String orgcodeNo) {
        this.orgcodeNo = orgcodeNo == null ? null : orgcodeNo.trim();
    }

    public String getOrgcodeDir() {
        return orgcodeDir;
    }

    public void setOrgcodeDir(String orgcodeDir) {
        this.orgcodeDir = orgcodeDir == null ? null : orgcodeDir.trim();
    }

    public String getTaxregisNo() {
        return taxregisNo;
    }

    public void setTaxregisNo(String taxregisNo) {
        this.taxregisNo = taxregisNo == null ? null : taxregisNo.trim();
    }

    public String getTaxregisDir() {
        return taxregisDir;
    }

    public void setTaxregisDir(String taxregisDir) {
        this.taxregisDir = taxregisDir == null ? null : taxregisDir.trim();
    }

    public String getUnisoccreCode() {
        return unisoccreCode;
    }

    public void setUnisoccreCode(String unisoccreCode) {
        this.unisoccreCode = unisoccreCode == null ? null : unisoccreCode.trim();
    }

    public String getUnisoccreDir() {
        return unisoccreDir;
    }

    public void setUnisoccreDir(String unisoccreDir) {
        this.unisoccreDir = unisoccreDir == null ? null : unisoccreDir.trim();
    }

    public String getBindType() {
        return bindType;
    }

    public void setBindType(String bindType) {
        this.bindType = bindType == null ? null : bindType.trim();
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType == null ? null : accType.trim();
    }

    public String getOldbankaccountNo() {
        return oldbankaccountNo;
    }

    public void setOldbankaccountNo(String oldbankaccountNo) {
        this.oldbankaccountNo = oldbankaccountNo == null ? null : oldbankaccountNo.trim();
    }

    public String getBankaccountNo() {
        return bankaccountNo;
    }

    public void setBankaccountNo(String bankaccountNo) {
        this.bankaccountNo = bankaccountNo == null ? null : bankaccountNo.trim();
    }

    public String getBankaccountName() {
        return bankaccountName;
    }

    public void setBankaccountName(String bankaccountName) {
        this.bankaccountName = bankaccountName == null ? null : bankaccountName.trim();
    }

    public String getBankaccountTelno() {
        return bankaccountTelno;
    }

    public void setBankaccountTelno(String bankaccountTelno) {
        this.bankaccountTelno = bankaccountTelno == null ? null : bankaccountTelno.trim();
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo == null ? null : accNo.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}