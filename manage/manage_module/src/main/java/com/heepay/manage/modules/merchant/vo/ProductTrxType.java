/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.vo;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 *
 * 描    述：产品交易类型Entity
 *
 * 创 建 者： @author ly
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
public class ProductTrxType extends DataEntity<ProductTrxType> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 产品交易类型code
	private String name;		// 产品交易类型名称
	private String businessType; // 业务类型
	
	public ProductTrxType() {
		super();
	}

	public ProductTrxType(String id){
		super(id);
	}

	@Length(min=0, max=4, message="产品交易类型code长度必须介于 0 和 4 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=0, max=100, message="产品交易类型名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
}