/**
 *  
 */
package com.heepay.manage.modules.route.entity;

import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 银行Entity
 * @author ljh
 * @version V1.0
 */
public class DownloadAccFile extends DataEntity<DownloadAccFile> {

	private static final long serialVersionUID = 1L;
	private String bankNo;	// 银行代码
	private Date downDate;	// 开始时间

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public Date getDownDate() {
		return downDate;
	}

	public void setDownDate(Date downDate) {
		this.downDate = downDate;
	}
}