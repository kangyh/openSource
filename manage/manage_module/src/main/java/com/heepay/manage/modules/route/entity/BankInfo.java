/**
 *  
 */
package com.heepay.manage.modules.route.entity;

import org.hibernate.validator.constraints.Length;
import com.heepay.manage.common.persistence.DataEntity;

/**
 * 银行Entity
 * @author ljh
 * @version V1.0
 */
public class BankInfo extends DataEntity<BankInfo> {
	
	private static final long serialVersionUID = 1L;
	private String bankNo;		// 银行代码
	private String bankName;		// 银行名称
	private String bankCode;		// 银行简称
	private String status;		// 状态(启停)
	private String remark;		// 备注
	private String updateName;//更新人
	
	private String selectStatus; //页面显示的查询状态

	public BankInfo() {
		super();
	}

	public BankInfo(String id){
		super(id);
	}

	@Length(min=1, max=3, message="银行代码长度必须介于 1 和 3之间")
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	
	@Length(min=1, max=30, message="银行名称长度必须介于 1 和 30 之间")
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Length(min=0, max=10, message="银行简称长度必须介于 0 和 10 之间")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Length(min=1, max=6, message="状态(启停)长度必须介于 1 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	@Length(min=0, max=200, message="备注长度必须介于 0 和 200 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSelectStatus() {
		return selectStatus;
	}

	public void setSelectStatus(String selectStatus) {
		this.selectStatus = selectStatus;
	}


	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
}