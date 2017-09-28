package com.heepay.manage.modules.risk.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;
/**
 * 
 *
 * 描    述：产品限额表实体类
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月18日 下午4:50:06
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
public class RiskProductQuota extends DataEntity<RiskProductQuota>{
    /**
	 * 2016年11月18日
	 */
	private static final long serialVersionUID = -6758851878583399405L;

	private Long proId;

    private String productCode;

    private String productName;

    private BigDecimal perItem;

    private BigDecimal perDay;

    private String status;

    private BigDecimal perMonth;
    
    private String accountType;

    private String bankcardType;

    private Date createTime;

    private String createAuthor;

    private Date updateTime;

    private String updateAuthor;

    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public BigDecimal getPerItem() {
        return perItem;
    }

    public void setPerItem(BigDecimal perItem) {
        this.perItem = perItem;
    }

    public BigDecimal getPerDay() {
        return perDay;
    }

    public void setPerDay(BigDecimal perDay) {
        this.perDay = perDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getPerMonth() {
        return perMonth;
    }

    public void setPerMonth(BigDecimal perMonth) {
        this.perMonth = perMonth;
    }
    
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    public String getBankcardType() {
        return bankcardType;
    }

    public void setBankcardType(String bankcardType) {
        this.bankcardType = bankcardType == null ? null : bankcardType.trim();
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
}