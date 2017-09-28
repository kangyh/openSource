/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.entity;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：通关申报订单查询Entity
 *
 * 创 建 者： @author guozx
 * 创建时间： 2016年12月30日 10:30:25
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
public class CbmsCustomorderSum extends DataEntity<CbmsCustomorderSum> {
	
	private static final long serialVersionUID = 1L;
	private Long orderInputId;		// 订单导入批次汇总ID
	private String merchantNo;		// 商户ID
	private String cbmsCompanyName;		// 商户公司名称
	private String importBatchNumber;		// 导入批次号
	private Date importTime;		// 导入日期
	private String declarationBatchHumber;		// 申报批次号
	private Date declarationTime;		// 申报时间
	private String customCode;		// 关区代码
	private String customName;		// 关区名称
	private String declarationNumber;		// 总申报条数
	private String declarationMoney;		// 总申报金额
	private String reLoadNumber;		// 重提条数
	private String newNumber;		// 新增条数
	private String recAmount;		// 应收金额
	private String fee;		// 手续费
	private String status;		// 状态            (1未报送2报送处理中3报送成功4报送失败5取消6审核拒绝)
	private String comments;		// 备注
	private String declareType;		// 备注
	private String merchantBatchNo;		// 备注
	private String notifyUrl;		//异步通知地址
	private String loginName;		//商户的登录名
	private Date beginDeclarationTime;   // 开始申报时间
	private Date endDeclarationTime;   // 结束申报时间

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getMerchantBatchNo() {
		return merchantBatchNo;
	}

	public void setMerchantBatchNo(String merchantBatchNo) {
		this.merchantBatchNo = merchantBatchNo;
	}

	public String getDeclareType() {
		return declareType;
	}

	public void setDeclareType(String declareType) {
		this.declareType = declareType;
	}

	public Date getBeginDeclarationTime() {
		return beginDeclarationTime;
	}

	public void setBeginDeclarationTime(Date beginDeclarationTime) {
		this.beginDeclarationTime = beginDeclarationTime;
	}

	public Date getEndDeclarationTime() {
		return endDeclarationTime;
	}

	public void setEndDeclarationTime(Date endDeclarationTime) {
		this.endDeclarationTime = endDeclarationTime;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public CbmsCustomorderSum() {
		super();
	}

	public CbmsCustomorderSum(String id){
		super(id);
	}

	@NotNull(message="订单导入批次汇总ID不能为空")
	public Long getOrderInputId() {
		return orderInputId;
	}

	public void setOrderInputId(Long orderInputId) {
		this.orderInputId = orderInputId;
	}
	
	@Length(min=0, max=64, message="商户ID长度必须介于 0 和 64 之间")
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	
	@Length(min=0, max=200, message="商户公司名称长度必须介于 0 和 200 之间")
	public String getCbmsCompanyName() {
		return cbmsCompanyName;
	}

	public void setCbmsCompanyName(String cbmsCompanyName) {
		this.cbmsCompanyName = cbmsCompanyName;
	}
	
	@Length(min=0, max=64, message="导入批次号长度必须介于 0 和 64 之间")
	public String getImportBatchNumber() {
		return importBatchNumber;
	}

	public void setImportBatchNumber(String importBatchNumber) {
		this.importBatchNumber = importBatchNumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}
	
	@Length(min=0, max=64, message="申报批次号长度必须介于 0 和 64 之间")
	public String getDeclarationBatchHumber() {
		return declarationBatchHumber;
	}

	public void setDeclarationBatchHumber(String declarationBatchHumber) {
		this.declarationBatchHumber = declarationBatchHumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDeclarationTime() {
		return declarationTime;
	}

	public void setDeclarationTime(Date declarationTime) {
		this.declarationTime = declarationTime;
	}
	
	@Length(min=1, max=10, message="关区代码长度必须介于 1 和 10 之间")
	public String getCustomCode() {
		return customCode;
	}

	public void setCustomCode(String customCode) {
		this.customCode = customCode;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}
	
	@Length(min=0, max=11, message="总申报条数长度必须介于 0 和 11 之间")
	public String getDeclarationNumber() {
		return declarationNumber;
	}

	public void setDeclarationNumber(String declarationNumber) {
		this.declarationNumber = declarationNumber;
	}
	
	public String getDeclarationMoney() {
		return declarationMoney;
	}

	public void setDeclarationMoney(String declarationMoney) {
		this.declarationMoney = declarationMoney;
	}
	
	@Length(min=0, max=11, message="重提条数长度必须介于 0 和 11 之间")
	public String getReLoadNumber() {
		return reLoadNumber;
	}

	public void setReLoadNumber(String reLoadNumber) {
		this.reLoadNumber = reLoadNumber;
	}
	
	@Length(min=0, max=11, message="新增条数长度必须介于 0 和 11 之间")
	public String getNewNumber() {
		return newNumber;
	}

	public void setNewNumber(String newNumber) {
		this.newNumber = newNumber;
	}
	
	public String getRecAmount() {
		return recAmount;
	}

	public void setRecAmount(String recAmount) {
		this.recAmount = recAmount;
	}
	
	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}
	
	@Length(min=0, max=4, message="状态            (1未报送2报送处理中3报送成功4报送失败5退单)长度必须介于 0 和 4 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=1000, message="备注长度必须介于 0 和 1000 之间")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

    @Override
    public String toString() {
        return "CbmsCustomorderSum{" +
                "orderInputId=" + orderInputId +
                "| merchantNo='" + merchantNo + '\'' +
                "| cbmsCompanyName='" + cbmsCompanyName + '\'' +
                "| importBatchNumber='" + importBatchNumber + '\'' +
                "| importTime=" + importTime +
                "| declarationBatchHumber='" + declarationBatchHumber + '\'' +
                "| declarationTime=" + declarationTime +
                "| customCode='" + customCode + '\'' +
                "| declarationNumber='" + declarationNumber + '\'' +
                "| declarationMoney='" + declarationMoney + '\'' +
                "| reLoadNumber='" + reLoadNumber + '\'' +
                "| newNumber='" + newNumber + '\'' +
                "| recAmount='" + recAmount + '\'' +
                "| fee='" + fee + '\'' +
                "| status='" + status + '\'' +
                "| comments='" + comments + '\'' +
                "| declareType='" + declareType + '\'' +
                "| loginName='" + loginName + '\'' +
                "| beginDeclarationTime=" + beginDeclarationTime +
                "| endDeclarationTime=" + endDeclarationTime +
                "| merchantBatchNo=" + merchantBatchNo +
                "| notifyUrl=" + notifyUrl +
                '}';
    }
}