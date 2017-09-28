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
public class MerchantsUser extends DataEntity<MerchantsUser> {

	private static final long serialVersionUID = 1L;
	private String loginName;		// 用户登录名
	private String loginNickname;		// 用户昵称
	private String userType;		// 用户类型
	private String loginPassword;		// 登录密码
	private String payPassword;		// 支付密码
	private String secretQuestion;		// 安全保护问题
	private String answerSecretQuestion;		// 安全保护答案
	private String phone;		// 联系手机
	private String mobile;		// 手机号码
	private String qq;		// qq
	private String linkAddress;		// 联系地址
	private String status;		// 登录账号状态
	private String source;      //在哪个系统注册的
	private String merchantId;      //商户id
	private String remarks;   //备注
	private Date createTime;   //注册时间
	private String idNo; //身份证号
	private String realnameAuthSign; //实名认证状态
	private String bankcardAuthSign; //银行卡鉴权状态
	private String faceAuth; //人脸识别认证
	private String lifeAuth; //活体认证状态
	private String userLevel; //用户级别
	private String realName; //真实姓名

	public MerchantsUser() {
		super();
	}

	public MerchantsUser(String id){
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
	
	@Length(min=0, max=6, message="登录账号状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getRealnameAuthSign() {
		return realnameAuthSign;
	}

	public void setRealnameAuthSign(String realnameAuthSign) {
		this.realnameAuthSign = realnameAuthSign;
	}

	public String getBankcardAuthSign() {
		return bankcardAuthSign;
	}

	public void setBankcardAuthSign(String bankcardAuthSign) {
		this.bankcardAuthSign = bankcardAuthSign;
	}

	public String getFaceAuth() {
		return faceAuth;
	}

	public void setFaceAuth(String faceAuth) {
		this.faceAuth = faceAuth;
	}

	public String getLifeAuth() {
		return lifeAuth;
	}

	public void setLifeAuth(String lifeAuth) {
		this.lifeAuth = lifeAuth;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}