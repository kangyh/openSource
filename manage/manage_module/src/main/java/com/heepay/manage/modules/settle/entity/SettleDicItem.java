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
public class SettleDicItem extends DataEntity<SettleDicItem> {
	
    /**
	 * 2016年10月12日
	 */
	private static final long serialVersionUID = -3367055358640195147L;

	private Long itemId;//类型明细ID

    private Integer sort;//序列号

    private String text;//明细名称

    private String value;//明细编码

    private String status;//明细状态

    private Integer typeId;//类型ID
    
    private String createOperator;//创建人
    
    private Date createTime;//创建时间
    
    private String updateOperator;//更新人
    
    private Date updateTime;//更新时间
    
    /**
     * 该字段为非映射字段只是用于查询显示
     */
    private String typeText;//类型名称
    private String flg;//用于区分是列表查询还是添加查询
    
    public SettleDicItem() {
		super();
	}

	public SettleDicItem(String id){
		super(id);
	}

    @NotNull(message="类型明细id不能为空")
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

	public String getTypeText() {
		return typeText;
	}

	public void setTypeText(String typeText) {
		this.typeText = typeText;
	}

	public String getFlg() {
		return flg;
	}

	public void setFlg(String flg) {
		this.flg = flg;
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