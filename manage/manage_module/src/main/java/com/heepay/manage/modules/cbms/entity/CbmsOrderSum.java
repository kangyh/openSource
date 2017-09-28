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
 * 描    述：导入文件查询Entity
 *
 * 创 建 者： @author 牛俊鹏
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
public class CbmsOrderSum extends DataEntity<CbmsOrderSum> {
	
	private static final long serialVersionUID = 1L;
	private Long orderInputId;		// 订单导入批次汇总ID
	private String merchantNo;		// 商户ID
	private String cbmsCompanyName;		// 商户公司名称
	private String fileName;		// 文件名称
	private String filePath;		// 文件路径
	private String fileSize;		// 文件大小（M）
	private String importBatchNumber;		// 导入批次号
	private Date importTime;		// 导入日期
	private String declarationNumber;		// 总申报条数
	private String declarationMoney;		// 总申报金额
	private String reLoadNumber;		// 重提条数
	private String newNumber;		// 新增条数
	private String recAmount;		// 应收金额
	private String fee;		// 手续费
	private String businessType;		// 业务类型
	private String status;		// 状态            SUCCES. 成功            FAILS. 失败
	private String comments;		// 备注
	private String merchantBatchNo;		// 商户批次号
	private String declareType;		// 申报方式
	private String reasonfailure;		// 审核失败原因
	private Date beginImportTime;		// 开始 导入日期
	private Date endImportTime;		// 结束 导入日期
	private String notifyUrl;//异步通知地址
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

	public CbmsOrderSum() {
		super();
	}

	public CbmsOrderSum(String id){
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
	
	@Length(min=1, max=1000, message="文件名称长度必须介于 1 和 1000 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=1, max=1000, message="文件路径长度必须介于 1 和 1000 之间")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Length(min=0, max=11, message="文件大小（M）长度必须介于 0 和 11 之间")
	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
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
	
	@Length(min=0, max=10, message="业务类型长度必须介于 0 和 10 之间")
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	@ Length(min=0, max=1000, message="失败长度必须介于 0 和 4 之间")
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
	@Length(min=0, max=1000, message="备注长度必须介于 0 和 1000 之间")
	public String getReasonfailure() {
		return reasonfailure;
	}

	public void setReasonfailure(String reasonfailure) {
		this.reasonfailure = reasonfailure;
	}
	public Date getBeginImportTime() {
		return beginImportTime;
	}

	public void setBeginImportTime(Date beginImportTime) {
		this.beginImportTime = beginImportTime;
	}
	
	public Date getEndImportTime() {
		return endImportTime;
	}

	public void setEndImportTime(Date endImportTime) {
		this.endImportTime = endImportTime;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	@Override
	public String toString() {
		return "CbmsOrderSum{" +
				"orderInputId=" + orderInputId +
				"| merchantNo='" + merchantNo + '\'' +
				"| cbmsCompanyName='" + cbmsCompanyName + '\'' +
				"| fileName='" + fileName + '\'' +
				"| filePath='" + filePath + '\'' +
				"| fileSize='" + fileSize + '\'' +
				"| importBatchNumber='" + importBatchNumber + '\'' +
				"| importTime=" + importTime +
				"| declarationNumber='" + declarationNumber + '\'' +
				"| declarationMoney='" + declarationMoney + '\'' +
				"| reLoadNumber='" + reLoadNumber + '\'' +
				"| newNumber='" + newNumber + '\'' +
				"| recAmount='" + recAmount + '\'' +
				"| fee='" + fee + '\'' +
				"| businessType='" + businessType + '\'' +
				"| status='" + status + '\'' +
				"| comments='" + comments + '\'' +
				"| merchantBatchNo='" + merchantBatchNo + '\'' +
				"| declareType='" + declareType + '\'' +
				"| reasonfailure='" + reasonfailure + '\'' +
				"| beginImportTime=" + beginImportTime +
				"| endImportTime=" + endImportTime +
				"| notifyUrl=" + notifyUrl +
				'}';
	}
}