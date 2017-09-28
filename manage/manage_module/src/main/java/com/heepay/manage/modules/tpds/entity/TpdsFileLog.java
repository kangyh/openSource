package com.heepay.manage.modules.tpds.entity;

/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：存管日记Entity
 *
 * 创 建 者： @author wj
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
public class TpdsFileLog extends DataEntity<TpdsFileLog> {
	
	private static final long serialVersionUID = 1L;
	private String logId;		// 日记ID
	private String merchantNo;		// 商户编码
	private String checkFileFrom;		// 源文件IP
	private String checkFileWhere;		// 上传文件IP
	private String fileName;		// 对账文件名
	private String fileDir;		// 存放目录
	private String operPerson;		// 操作人
	private Date operTime;		// 操作时间
	
	public TpdsFileLog() {
		super();
	}

	public TpdsFileLog(String id){
		super(id);
	}

	@Length(min=1, max=11, message="日记ID长度必须介于 1 和 11 之间")
	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}
	
	@Length(min=1, max=15, message="商户编码长度必须介于 1 和 15 之间")
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	
	@Length(min=0, max=20, message="源文件IP长度必须介于 0 和 20 之间")
	public String getCheckFileFrom() {
		return checkFileFrom;
	}

	public void setCheckFileFrom(String checkFileFrom) {
		this.checkFileFrom = checkFileFrom;
	}
	
	@Length(min=0, max=20, message="上传文件IP长度必须介于 0 和 20 之间")
	public String getCheckFileWhere() {
		return checkFileWhere;
	}

	public void setCheckFileWhere(String checkFileWhere) {
		this.checkFileWhere = checkFileWhere;
	}
	
	@Length(min=0, max=50, message="对账文件名长度必须介于 0 和 50 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=0, max=50, message="存放目录长度必须介于 0 和 50 之间")
	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	
	@Length(min=0, max=20, message="操作人长度必须介于 0 和 20 之间")
	public String getOperPerson() {
		return operPerson;
	}

	public void setOperPerson(String operPerson) {
		this.operPerson = operPerson;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	
}