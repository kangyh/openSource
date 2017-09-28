/**
 *  
 */
package com.heepay.manage.modules.merchant.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * userEntity
 * @author ly
 * @version V1.0
 */
public class MerchantUser extends DataEntity<MerchantUser> {
	
	private static final long serialVersionUID = 1L;
	private String loginName;		// 用户登录名
	private String loginNickname;		// 用户昵称
	private String userType;		// 用户类型
	private String loginPassword;		// 登录密码
	private String payPassword;		// 支付密码
	private String secretQuestion;		// 安全保护问题
	private String answerSecretQuestion;		// 安全保护答案
	private String loginIpsAllowed;		// 允许登录IP地址
	private Date lastLoginDate;		// 上次登录时间
	private String lastLoginIp;		// 上次登录IP
	private String phone;		// 联系手机
	private String mobile;		// 手机号码
	private String qq;		// qq
	private String linkAddress;		// 联系地址
	private String macInfo;		// mac信息
	private String diskInfo;		// 计算机硬盘信息
	private String cpuInfo;		// 计算机cpu信息
	private String passGuardCtrlVersion;		// 密码控件版本号
	private String clientType;		// 客户端请求类型
	private String status;		// 登录账号状态
	private String companyName;  //商户公司名称
	private String name;         //商户名称
	private Date createTime;   //注册时间
	private String legalIdcard;  //法人身份证号
	private String legalRepresentative; //法人代表
	private String infoAuthStatus;    //信息认证状态
	private String legalAuditStatus;  //法务审核状态
	private String rcAuditStatus;     //风控审核状态
	private String merchantStatus; //商户状态
	private String payStatus;      //打款状态
	private String authenticationStatus;      //打款认证状态
	private String source;      //在哪个系统注册的
	private String allowSystem;      //可以登陆哪些系统
	private String merchantFlag;  //商户标识
	private String inchargerId;  //当前负责人

	public MerchantUser() {
		super();
	}

	public MerchantUser(String id){
		super(id);
	}

	@Length(min=0, max=11, message="用户登录名长度必须介于 0 和 50 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Length(min=0, max=50, message="用户昵称长度必须介于 0 和 50 之间")
	public String getLoginNickname() {
		return loginNickname;
	}

	public void setLoginNickname(String loginNickname) {
		this.loginNickname = loginNickname;
	}
	
	@Length(min=0, max=6, message="用户类型长度必须介于 0 和 6 之间")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@Length(min=0, max=256, message="登录密码长度必须介于 0 和 256 之间")
	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	
	@Length(min=0, max=256, message="支付密码长度必须介于 0 和 256 之间")
	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
	
	@Length(min=0, max=256, message="安全保护问题长度必须介于 0 和 256 之间")
	public String getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}
	
	@Length(min=0, max=256, message="安全保护答案长度必须介于 0 和 256 之间")
	public String getAnswerSecretQuestion() {
		return answerSecretQuestion;
	}

	public void setAnswerSecretQuestion(String answerSecretQuestion) {
		this.answerSecretQuestion = answerSecretQuestion;
	}
	
	@Length(min=0, max=15, message="允许登录IP地址长度必须介于 0 和 15 之间")
	public String getLoginIpsAllowed() {
		return loginIpsAllowed;
	}

	public void setLoginIpsAllowed(String loginIpsAllowed) {
		this.loginIpsAllowed = loginIpsAllowed;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	@Length(min=0, max=15, message="上次登录IP长度必须介于 0 和 15 之间")
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
	@Length(min=0, max=50, message="联系手机长度必须介于 0 和 50 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=50, message="手机号码长度必须介于 0 和 50 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=50, message="qq长度必须介于 0 和 50 之间")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@Length(min=0, max=50, message="联系地址长度必须介于 0 和 50 之间")
	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}
	
	@Length(min=0, max=50, message="mac信息长度必须介于 0 和 50 之间")
	public String getMacInfo() {
		return macInfo;
	}

	public void setMacInfo(String macInfo) {
		this.macInfo = macInfo;
	}
	
	@Length(min=0, max=128, message="计算机硬盘信息长度必须介于 0 和 128 之间")
	public String getDiskInfo() {
		return diskInfo;
	}

	public void setDiskInfo(String diskInfo) {
		this.diskInfo = diskInfo;
	}
	
	@Length(min=0, max=128, message="计算机cpu信息长度必须介于 0 和 128 之间")
	public String getCpuInfo() {
		return cpuInfo;
	}

	public void setCpuInfo(String cpuInfo) {
		this.cpuInfo = cpuInfo;
	}
	
	@Length(min=0, max=50, message="密码控件版本号长度必须介于 0 和 50 之间")
	public String getPassGuardCtrlVersion() {
		return passGuardCtrlVersion;
	}

	public void setPassGuardCtrlVersion(String passGuardCtrlVersion) {
		this.passGuardCtrlVersion = passGuardCtrlVersion;
	}
	
	@Length(min=0, max=6, message="客户端请求类型长度必须介于 0 和 6 之间")
	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	
	@Length(min=0, max=6, message="登录账号状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLegalIdcard() {
		return legalIdcard;
	}

	public void setLegalIdcard(String legalIdcard) {
		this.legalIdcard = legalIdcard;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getInfoAuthStatus() {
		return infoAuthStatus;
	}

	public void setInfoAuthStatus(String infoAuthStatus) {
		this.infoAuthStatus = infoAuthStatus;
	}

	public String getLegalAuditStatus() {
		return legalAuditStatus;
	}

	public void setLegalAuditStatus(String legalAuditStatus) {
		this.legalAuditStatus = legalAuditStatus;
	}

	public String getRcAuditStatus() {
		return rcAuditStatus;
	}

	public void setRcAuditStatus(String rcAuditStatus) {
		this.rcAuditStatus = rcAuditStatus;
	}

	public String getMerchantStatus() {
		return merchantStatus;
	}

	public void setMerchantStatus(String merchantStatus) {
		this.merchantStatus = merchantStatus;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getAuthenticationStatus() {
		return authenticationStatus;
	}

	public void setAuthenticationStatus(String authenticationStatus) {
		this.authenticationStatus = authenticationStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAllowSystem() {
		return allowSystem;
	}

	public void setAllowSystem(String allowSystem) {
		this.allowSystem = allowSystem;
	}

	public String getMerchantFlag() {
		return merchantFlag;
	}

	public void setMerchantFlag(String merchantFlag) {
		this.merchantFlag = merchantFlag;
	}

	public String getInchargerId() {
		return inchargerId;
	}

	public void setInchargerId(String inchargerId) {
		this.inchargerId = inchargerId;
	}
}