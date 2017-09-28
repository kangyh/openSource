package com.heepay.manage.modules.risk.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;


/***
 * 
* 
* 描    述：订单冻结/解冻表
*
* 创 建 者：wangl
* 创建时间：  Nov 18, 20167:03:30 PM
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
public class RiskOrderFreeze extends DataEntity<RiskOrderFreeze>{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer freezeId;

    private String freezeNo;

    private String freezeType;

    private String defreezeNo;

    private String orderNo;

    private String transNo;
    private String transType;

    private BigDecimal transAmount;

    private String freezeRemark;

    private String failReason;

    private String status;

    private Date createTime;

    private String createAuthor;

    private Date updateTime;

    private String updateAuthor;

    private String merchantCode;
    private String merchantName;
    private String transStatus;
    private String checkMessage;//解冻理由
    
    //创建自定义属性
    private Date beginOperEndTime;
    private Date endOperEndTime;
    
    
    
    public BigDecimal getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(BigDecimal transAmount) {
		this.transAmount = transAmount;
	}

	public String getCheckMessage() {
		return checkMessage;
	}

	public void setCheckMessage(String checkMessage) {
		this.checkMessage = checkMessage;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Date getBeginOperEndTime() {
		return beginOperEndTime;
	}

	public void setBeginOperEndTime(Date beginOperEndTime) {
		this.beginOperEndTime = beginOperEndTime;
	}

	public Date getEndOperEndTime() {
		return endOperEndTime;
	}

	public void setEndOperEndTime(Date endOperEndTime) {
		this.endOperEndTime = endOperEndTime;
	}

	public Integer getFreezeId() {
        return freezeId;
    }

    public void setFreezeId(Integer freezeId) {
        this.freezeId = freezeId;
    }

    public String getFreezeNo() {
        return freezeNo;
    }

    public void setFreezeNo(String freezeNo) {
        this.freezeNo = freezeNo == null ? null : freezeNo.trim();
    }

   

    public String getFreezeType() {
		return freezeType;
	}

	public void setFreezeType(String freezeType) {
		this.freezeType = freezeType;
	}

	public String getDefreezeNo() {
        return defreezeNo;
    }

    public void setDefreezeNo(String defreezeNo) {
        this.defreezeNo = defreezeNo == null ? null : defreezeNo.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }

  
    public String getFreezeRemark() {
        return freezeRemark;
    }

    public void setFreezeRemark(String freezeRemark) {
        this.freezeRemark = freezeRemark == null ? null : freezeRemark.trim();
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

   
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateAuthor() {
        return createAuthor;
    }

    public void setCreateAuthor(String createAuthor) {
        this.createAuthor = createAuthor == null ? null : createAuthor.trim();
    }

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
        this.updateAuthor = updateAuthor == null ? null : updateAuthor.trim();
    }

	@Override
	public String toString() {
		return "RiskOrderFreeze [freezeId=" + freezeId + ", freezeNo=" + freezeNo + ", freezeType=" + freezeType
				+ ", defreezeNo=" + defreezeNo + ", orderNo=" + orderNo + ", transNo=" + transNo + ", transAmount="
				+ transAmount + ", freezeRemark=" + freezeRemark + ", failReason=" + failReason + ", status=" + status
				+ ", createTime=" + createTime + ", createAuthor=" + createAuthor + ", updateTime=" + updateTime
				+ ", updateAuthor=" + updateAuthor + ", beginOperEndTime=" + beginOperEndTime + ", endOperEndTime="
				+ endOperEndTime + "]";
	}
    
}