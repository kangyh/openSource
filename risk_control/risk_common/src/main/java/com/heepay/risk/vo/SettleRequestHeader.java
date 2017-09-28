package com.heepay.risk.vo;
/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年3月3日 上午9:57:22
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
public class SettleRequestHeader {
	 private static final long serialVersionUID = 712851714926397865L;

	    /**
	     * 版本号，默认为V1.0.1
	     */
	    private String version;

	    /**
	     * 报文唯一标识（8位日期+10顺序号）
	     */
	    private String identification;

	    /**
	     * 会员单位机构号（字母、数字、下划线）
	     */
	    private String origSender;

	    /**
	     * 会员单位发送系统号（字母、数字、下划线）
	     */
	    private String origSenderSID;

	    /**
	     * 协会系统编号,见数据字典（R0001）
	     */
	    private String recSystemId;

	    /**
	     * 交易码
	     */
	    private String trnxCode;

	    /**
	     * 交易时间（格式：yyyyMMDDHHmmss）
	     */
	    private String trnxTime;

	    /**
	     * 用户登录信息凭证（用户登录和登出时无需该字段）
	     */
	    private String userToken;

	    /**
	     * 密钥
	     */
	    private String secretKey;


	    public String getVersion() {
	        return version;
	    }

	    public void setVersion(String version) {
	        this.version = version;
	    }

	    public String getIdentification() {
	        return identification;
	    }

	    public void setIdentification(String identification) {
	        this.identification = identification;
	    }

	    public String getOrigSender() {
	        return origSender;
	    }

	    public void setOrigSender(String origSender) {
	        this.origSender = origSender;
	    }

	    public String getOrigSenderSID() {
	        return origSenderSID;
	    }

	    public void setOrigSenderSID(String origSenderSID) {
	        this.origSenderSID = origSenderSID;
	    }

	    public String getRecSystemId() {
	        return recSystemId;
	    }

	    public void setRecSystemId(String recSystemId) {
	        this.recSystemId = recSystemId;
	    }

	    public String getTrnxCode() {
	        return trnxCode;
	    }

	    public void setTrnxCode(String trnxCode) {
	        this.trnxCode = trnxCode;
	    }

	    public String getTrnxTime() {
	        return trnxTime;
	    }

	    public void setTrnxTime(String trnxTime) {
	        this.trnxTime = trnxTime;
	    }

	    public String getUserToken() {
	        return userToken;
	    }

	    public void setUserToken(String userToken) {
	        this.userToken = userToken;
	    }

	    public String getSecretKey() {
	        return secretKey;
	    }

	    public void setSecretKey(String secretKey) {
	        this.secretKey = secretKey;
	    }

}

 