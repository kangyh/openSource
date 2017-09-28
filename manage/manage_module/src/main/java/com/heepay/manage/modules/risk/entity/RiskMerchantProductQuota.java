package com.heepay.manage.modules.risk.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.heepay.manage.common.persistence.DataEntity;
/**
 * 
 *
 * 描    述：商户产品限额表实体类
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月18日 下午4:50:24
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
public class RiskMerchantProductQuota extends DataEntity<RiskMerchantProductQuota>{
    /**
	 * 2016年11月18日
	 */
	private static final long serialVersionUID = 2722585087500580990L;

	private Long merProId;

    private String productCode;

    private String productName;

    private Integer merchantId;

    private String merchantName;

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
    
    private String merchantAccount;

    public Long getMerProId() {
        return merProId;
    }

    public void setMerProId(Long merProId) {
        this.merProId = merProId;
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

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
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
    
    public String getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(String merchantAccount) {
        this.merchantAccount = merchantAccount == null ? null : merchantAccount.trim();
    }
}