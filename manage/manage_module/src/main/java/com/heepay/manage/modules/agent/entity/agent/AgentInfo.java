/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.entity.agent;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：代理商信息维护Entity
 *
 * 创 建 者： @author xch
 * 创建时间：
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
public class AgentInfo extends DataEntity<AgentInfo> {
	
	private static final long serialVersionUID = 1L;
	private String agentLevel;		// 代理商等级（共1/2/3四个等级）
	private String superAgent;		// 上级代理商（id字段）
	private String agentCode;		// 代理商编号
	private String agentName;		// 代理商名称
	private String shortName;		// 代理商名称简称
	private String headName;		// 负责人姓名
	private String headPhone;		// 负责人手机号
	private String headIdcard;		// 负责人身份证号
	private String headIdcardExpiry;		// 身份证有效期
	private String email;		// 电子邮箱
	private String agentExpiry;		// 代理商有效期
	private String agentType;		// 代理商类型（枚举类AgentType PERSON 个人  COMPAN企业）
	private String legalName;		// 法人姓名
	private String legalPhone;		// 法人手机号
	private String legalIdcard;		// 法人身份证号
	private String legalIdcardExpiry;		// 法人身份证有效期
	private String busiType;		// 营业证照类型（枚举类BusiType GENERA 普通 MULONE多证合一）
	private String busiCode;		// 营业证照编号
	private String busiExpiry;		// 营业证照有效期
	private String regAmount;		// 注册资金
	private String orgInstCode;		// 组织机构代码证
	private String taxRegCode;		// 税务登记证
	private String addrProvinceCode;		// 所在地省份编码
	private String addrProvinceName;		// 所在地省份名称
	private String addrCityCode;		// 所在地城市编号
	private String addrCityName;		// 所在地城市名称
	private String addrCountryCode;		// 所在地地市编号
	private String addrDetail;		// 所在地详细地址
	private String addrCountryName;		// 所在地地市名称
	private String agentAreaAddr;		// 代理区域地址
	private String depositAmount;		// 保证金
	private String joinAmount;		// 接入费
	private String updatedStatus;		// 修改状态
	private String idcardFaceImage;		// 身份证（个人信息页）
	private String idcardBackImage;		// 身份证（国徽页）
	private String agreementFile;		// 合作协议
	private String busiImage;		// 营业执照
	private String taxRegImage;		// 税务登记证
	private String orgInstImage;		// 组织机构代码
	private String otherGenaImage;		// 其他辅助材料
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String agentStatus;		// 使用状态（枚举类AgentStatus  INITIA初始 ONECHK一级待审 SYSCHK系统待审 NORMAL 工作 WAITIN 修改待审 LOCKIN 锁定  DISABL 无效）
	private String beginCreateTime;		// 开始 创建时间
	private String endCreateTime;		// 结束 创建时间
    private String agentPath;
	public AgentInfo() {
		super();
	}

	public AgentInfo(String id){
		super(id);
	}

	@Length(min=0, max=4, message="代理商等级（共1/2/3四个等级）长度必须介于 0 和 4 之间")
	public String getAgentLevel() {
		return agentLevel;
	}

	public void setAgentLevel(String agentLevel) {
		this.agentLevel = agentLevel;
	}
	
	@Length(min=0, max=11, message="上级代理商（id字段）长度必须介于 0 和 11 之间")
	public String getSuperAgent() {
		return superAgent;
	}

	public void setSuperAgent(String superAgent) {
		this.superAgent = superAgent;
	}
	
	@Length(min=0, max=6, message="代理商编号长度必须介于 0 和 6 之间")
	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	
	@Length(min=1, max=50, message="代理商名称长度必须介于 1 和 50 之间")
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	@Length(min=0, max=50, message="代理商名称简称长度必须介于 0 和 50 之间")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	@Length(min=1, max=50, message="负责人姓名长度必须介于 1 和 50 之间")
	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}
	
	@Length(min=1, max=11, message="负责人手机号长度必须介于 1 和 11 之间")
	public String getHeadPhone() {
		return headPhone;
	}

	public void setHeadPhone(String headPhone) {
		this.headPhone = headPhone;
	}
	
	@Length(min=1, max=18, message="负责人身份证号长度必须介于 1 和 18 之间")
	public String getHeadIdcard() {
		return headIdcard;
	}

	public void setHeadIdcard(String headIdcard) {
		this.headIdcard = headIdcard;
	}
	
	@Length(min=1, max=10, message="身份证有效期长度必须介于 1 和 10 之间")
	public String getHeadIdcardExpiry() {
		return headIdcardExpiry;
	}

	public void setHeadIdcardExpiry(String headIdcardExpiry) {
		this.headIdcardExpiry = headIdcardExpiry;
	}
	
	@Length(min=1, max=50, message="电子邮箱长度必须介于 1 和 50 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=10, message="代理商有效期长度必须介于 0 和 10 之间")
	public String getAgentExpiry() {
		return agentExpiry;
	}

	public void setAgentExpiry(String agentExpiry) {
		this.agentExpiry = agentExpiry;
	}
	
	@Length(min=0, max=6, message="代理商类型（枚举类AgentType PERSON 个人  COMPAN企业）长度必须介于 0 和 6 之间")
	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}
	
	@Length(min=1, max=50, message="法人姓名长度必须介于 1 和 50 之间")
	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	
	@Length(min=1, max=11, message="法人手机号长度必须介于 1 和 11 之间")
	public String getLegalPhone() {
		return legalPhone;
	}

	public void setLegalPhone(String legalPhone) {
		this.legalPhone = legalPhone;
	}
	
	@Length(min=1, max=18, message="法人身份证号长度必须介于 1 和 18 之间")
	public String getLegalIdcard() {
		return legalIdcard;
	}

	public void setLegalIdcard(String legalIdcard) {
		this.legalIdcard = legalIdcard;
	}
	
	@Length(min=1, max=10, message="法人身份证有效期长度必须介于 1 和 10 之间")
	public String getLegalIdcardExpiry() {
		return legalIdcardExpiry;
	}

	public void setLegalIdcardExpiry(String legalIdcardExpiry) {
		this.legalIdcardExpiry = legalIdcardExpiry;
	}
	
	@Length(min=1, max=6, message="营业证照类型（枚举类BusiType GENERA 普通 MULONE多证合一）长度必须介于 1 和 6 之间")
	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	@Length(min=1, max=50, message="营业证照编号长度必须介于 1 和 50 之间")
	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}
	
	@Length(min=1, max=10, message="营业证照有效期长度必须介于 1 和 10 之间")
	public String getBusiExpiry() {
		return busiExpiry;
	}

	public void setBusiExpiry(String busiExpiry) {
		this.busiExpiry = busiExpiry;
	}
	
	public String getRegAmount() {
		return regAmount;
	}

	public void setRegAmount(String regAmount) {
		this.regAmount = regAmount;
	}
	
	//@Length(min=1, max=30, message="组织机构代码证长度必须介于 1 和 30 之间")
	public String getOrgInstCode() {
		return orgInstCode;
	}

	public void setOrgInstCode(String orgInstCode) {
		this.orgInstCode = orgInstCode;
	}
	
	//@Length(min=1, max=30, message="税务登记证长度必须介于 1 和 30 之间")
	public String getTaxRegCode() {
		return taxRegCode;
	}

	public void setTaxRegCode(String taxRegCode) {
		this.taxRegCode = taxRegCode;
	}
	
	@Length(min=0, max=6, message="所在地省份编码长度必须介于 0 和 6 之间")
	public String getAddrProvinceCode() {
		return addrProvinceCode;
	}

	public void setAddrProvinceCode(String addrProvinceCode) {
		this.addrProvinceCode = addrProvinceCode;
	}
	
	@Length(min=1, max=100, message="所在地省份名称长度必须介于 1 和 100 之间")
	public String getAddrProvinceName() {
		return addrProvinceName;
	}

	public void setAddrProvinceName(String addrProvinceName) {
		this.addrProvinceName = addrProvinceName;
	}
	
	@Length(min=0, max=6, message="所在地城市编号长度必须介于 0 和 6 之间")
	public String getAddrCityCode() {
		return addrCityCode;
	}

	public void setAddrCityCode(String addrCityCode) {
		this.addrCityCode = addrCityCode;
	}
	
	@Length(min=1, max=100, message="所在地城市名称长度必须介于 1 和 100 之间")
	public String getAddrCityName() {
		return addrCityName;
	}

	public void setAddrCityName(String addrCityName) {
		this.addrCityName = addrCityName;
	}
	
	@Length(min=0, max=6, message="所在地地市编号长度必须介于 0 和 6 之间")
	public String getAddrCountryCode() {
		return addrCountryCode;
	}

	public void setAddrCountryCode(String addrCountryCode) {
		this.addrCountryCode = addrCountryCode;
	}
	
	@Length(min=1, max=100, message="所在地详细地址长度必须介于 1 和 100 之间")
	public String getAddrDetail() {
		return addrDetail;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}
	
	@Length(min=0, max=100, message="所在地地市名称长度必须介于 0 和 100 之间")
	public String getAddrCountryName() {
		return addrCountryName;
	}

	public void setAddrCountryName(String addrCountryName) {
		this.addrCountryName = addrCountryName;
	}
	
	@Length(min=0, max=100, message="代理区域地址长度必须介于 0 和 100 之间")
	public String getAgentAreaAddr() {
		return agentAreaAddr;
	}

	public void setAgentAreaAddr(String agentAreaAddr) {
		this.agentAreaAddr = agentAreaAddr;
	}
	
	public String getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(String depositAmount) {
		this.depositAmount = depositAmount;
	}
	
	public String getJoinAmount() {
		return joinAmount;
	}

	public void setJoinAmount(String joinAmount) {
		this.joinAmount = joinAmount;
	}
	
	@Length(min=0, max=6, message="修改状态长度必须介于 0 和 6 之间")
	public String getUpdatedStatus() {
		return updatedStatus;
	}

	public void setUpdatedStatus(String updatedStatus) {
		this.updatedStatus = updatedStatus;
	}
	
	@Length(min=1, max=256, message="身份证（个人信息页）长度必须介于 1 和 256 之间")
	public String getIdcardFaceImage() {
		return idcardFaceImage;
	}

	public void setIdcardFaceImage(String idcardFaceImage) {
		this.idcardFaceImage = idcardFaceImage;
	}
	
	@Length(min=1, max=256, message="身份证（国徽页）长度必须介于 1 和 256 之间")
	public String getIdcardBackImage() {
		return idcardBackImage;
	}

	public void setIdcardBackImage(String idcardBackImage) {
		this.idcardBackImage = idcardBackImage;
	}
	
	@Length(min=1, max=256, message="合作协议长度必须介于 1 和 256 之间")
	public String getAgreementFile() {
		return agreementFile;
	}

	public void setAgreementFile(String agreementFile) {
		this.agreementFile = agreementFile;
	}
	
	@Length(min=1, max=256, message="营业执照长度必须介于 1 和 256 之间")
	public String getBusiImage() {
		return busiImage;
	}

	public void setBusiImage(String busiImage) {
		this.busiImage = busiImage;
	}
	
	@Length(min=1, max=256, message="税务登记证长度必须介于 1 和 256 之间")
	public String getTaxRegImage() {
		return taxRegImage;
	}

	public void setTaxRegImage(String taxRegImage) {
		this.taxRegImage = taxRegImage;
	}
	
	@Length(min=1, max=256, message="组织机构代码长度必须介于 1 和 256 之间")
	public String getOrgInstImage() {
		return orgInstImage;
	}

	public void setOrgInstImage(String orgInstImage) {
		this.orgInstImage = orgInstImage;
	}
	
	@Length(min=0, max=256, message="其他辅助材料长度必须介于 0 和 256 之间")
	public String getOtherGenaImage() {
		return otherGenaImage;
	}

	public void setOtherGenaImage(String otherGenaImage) {
		this.otherGenaImage = otherGenaImage;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=6, message="使用状态（枚举类AgentStatus  INITIA初始 ONECHK一级待审 SYSCHK系统待审 NORMAL 工作 WAITIN 修改待审 LOCKIN 锁定  DISABL 无效）长度必须介于 0 和 6 之间")
	public String getAgentStatus() {
		return agentStatus;
	}

	public void setAgentStatus(String agentStatus) {
		this.agentStatus = agentStatus;
	}

	public String getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(String beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}

	public String getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
    public String getAgentPath() {
        return agentPath;
    }

    public void setAgentPath(String agentPath) {
        this.agentPath = agentPath;
    }
}