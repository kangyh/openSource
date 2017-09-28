/**
 *  
 */
package com.heepay.manage.modules.merchant.vo;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;

/**
 * merchantEntity
 * @author ly
 * @version V1.0
 */
public class MerchantEmployee extends DataEntity<MerchantEmployee> {
	
	private static final long serialVersionUID = 1L;
	private String employeeId;		// 员工ID
	private String email; // 邮箱
	private String mobile; //手机
	private String userId;		// 用户ID
	private String merchantId; //商户ID
	private String name;		// 员工姓名
	private String level;		// 等级
	private String status;		// 商户状态
	private String type;		// 类型
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	private String remarks; //备注
	
	public MerchantEmployee() {
		super();
	}

	public MerchantEmployee(String id){
		super(id);
	}

	@Length(min=1, max=10, message="员工ID长度必须介于 1 和 10 之间")
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	@Length(min=0, max=50, message="员工姓名长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=4, message="等级长度必须介于 0 和 4 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Length(min=0, max=6, message="商户状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=4, message="类型长度必须介于 0 和 4 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="更新时间不能为空")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@NotNull(message="用户ID不能为空")
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @NotNull(message="商户ID不能为空")
  public String getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }

  @Length(min=0, max=100, message="备注必须介于 0 和 100 之间")
  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
  
}