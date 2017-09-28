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
 * 描    述：推广位管理Entity
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
public class PromManage extends DataEntity<PromManage> {
	
	private static final long serialVersionUID = 1L;
	private Long proId;		// 主键
    private String promotionId;    //推广位ID
	private String proName;		// 推广位名称
	private String productName;		// 产品名称
	private String proType;		// 推广方式
	private String createAuthor;		// 创建人
	private Date createTime;		// 创建时间
	private String status;		// 状态
    private String proUrl;     //推广链接
    private Date updateTime;   //修改时间
    private String updateAuthor; //修改人

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(String updateAuthor) {
        this.updateAuthor = updateAuthor;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getProUrl() {
        return proUrl;
    }

    public void setProUrl(String proUrl) {
        this.proUrl = proUrl;
    }

	public PromManage() {
		super();
	}

	public PromManage(String id){
		super(id);
	}

	@NotNull(message="主键（推广位ID）不能为空")
	public Long getProId() {
		return proId;
	}

	public void setProId(Long proId) {
		this.proId = proId;
	}
	
	@Length(min=0, max=50, message="推广位名称长度必须介于 0 和 50 之间")
	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}
	
	@Length(min=0, max=200, message="产品名称长度必须介于 0 和 200 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=0, max=6, message="推广方式长度必须介于 0 和 6 之间")
	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}
	
	@Length(min=0, max=20, message="创建人长度必须介于 0 和 20 之间")
	public String getCreateAuthor() {
		return createAuthor;
	}

	public void setCreateAuthor(String createAuthor) {
		this.createAuthor = createAuthor;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Length(min=0, max=6, message="状态长度必须介于 0 和 6 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}