package com.heepay.manage.modules.settle.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * 
 *
 * 描    述：
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年10月12日
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
public class SettleDicType extends DataEntity<SettleDicType> {
	
    /**
	 * 2016年10月12日
	 */
	private static final long serialVersionUID = 290821285876003020L;

	private Integer typeId;//类型ID

    private String code;//类型编码

    private String text;//类型名称

    private String status;//类型状态
    
    private String createOperator;//创建人
    
    private Date createTime;//创建时间
    
    private String updateOperator;//更新人
    
    private Date updateTime;//更新时间
    
    public SettleDicType() {
		super();
	}

	public SettleDicType(String id){
		super(id);
	}

    @NotNull(message="类型ID不能为空")
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

	public String getCreateOperator() {
		return createOperator;
	}

	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateOperator() {
		return updateOperator;
	}

	public void setUpdateOperator(String updateOperator) {
		this.updateOperator = updateOperator;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}