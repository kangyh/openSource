/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.prom.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 *
 * 描    述：上下级关系管理Entity
 *
 * 创 建 者： @author wangdong
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
public class PromTeacherManage extends DataEntity<PromTeacherManage> {
	
	private static final long serialVersionUID = 1L;
	private Long teId;		// 主键
	private String merchantCode;		// 商户编码
	private String merchantName;		// 商户名称
	private String superMerchantCode;		// 上级（商户编码）
	private String superMerchantName;		// 上级名称（商户名称）
	private String templetId;		// 分润比例模板号
	private Date effectTime;		// 生效时间
	private Date failureTime;		// 失效时间
	private Date createTime;		// 创建时间
	private String createAuthor;		// 创建人
	private Date updateTime;		// 修改时间
	private String updateAuthor;		// 修改人
    private String status;              //等级

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public PromTeacherManage() {
		super();
	}

	public PromTeacherManage(String id){
		super(id);
	}

	@NotNull(message="主键不能为空")
	public Long getTeId() {
		return teId;
	}

	public void setTeId(Long teId) {
		this.teId = teId;
	}
	
	@Length(min=0, max=10, message="商户编码长度必须介于 0 和 10 之间")
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	@Length(min=0, max=10, message="商户名称长度必须介于 0 和 20 之间")
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	@Length(min=0, max=10, message="上级（商户编码）长度必须介于 0 和 10 之间")
	public String getSuperMerchantCode() {
		return superMerchantCode;
	}

	public void setSuperMerchantCode(String superMerchantCode) {
		this.superMerchantCode = superMerchantCode;
	}

	@Length(min=0, max=10, message="上级（商户编码）长度必须介于 0 和 20 之间")
	public String getSuperMerchantName() {
		return superMerchantName;
	}

	public void setSuperMerchantName(String superMerchantName) {
		this.superMerchantName = superMerchantName;
	}

	public String getTempletId() {
		return templetId;
	}

	public void setTempletId(String templetId) {
		this.templetId = templetId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFailureTime() {
		return failureTime;
	}

	public void setFailureTime(Date failureTime) {
		this.failureTime = failureTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=20, message="创建人长度必须介于 0 和 20 之间")
	public String getCreateAuthor() {
		return createAuthor;
	}

	public void setCreateAuthor(String createAuthor) {
		this.createAuthor = createAuthor;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	@Length(min=0, max=20, message="修改人长度必须介于 0 和 20 之间")
	public String getUpdateAuthor() {
		return updateAuthor;
	}

	public void setUpdateAuthor(String updateAuthor) {
		this.updateAuthor = updateAuthor;
	}
	
}