/**
 *  
 */
package com.heepay.manage.modules.merchant.vo;


import com.heepay.manage.common.persistence.DataEntity;

/**
 * merchantEntity
 * @author ly
 * @version V1.0
 */
public class MerchantEmployeeFunctions extends DataEntity<MerchantEmployeeFunctions> {
	
	private static final long serialVersionUID = 1L;
	private Long merchantId;		// 商户ID
	private Long employeeId;		// 员工ID
	private Long functionId;		// 功能ID
	
	public MerchantEmployeeFunctions() {
		super();
	}

	public MerchantEmployeeFunctions(String id){
		super(id);
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	
	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	
	public Long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}
	
}