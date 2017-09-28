package com.heepay.risk.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年1月16日 下午7:45:09
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
public class RiskProductQuotaVO {

 private Long proId;
 private String productCode;
 private String productName;
 private BigDecimal perItem;
 private BigDecimal perDay;
 private String status;
 private BigDecimal perMonth;
 private String accountType;
 private Date createTime;
 private String createAuthor;
 private Date updateTime;
 private String updateAuthor;
 private String bankcardType;
 
 public RiskProductQuotaVO() {}
 
 public String getProductCode()
 {
   return this.productCode;
 }
 
 public void setProductCode(String productCode) {
   this.productCode = (productCode == null ? null : productCode.trim());
 }
 
 public String getProductName() {
   return this.productName;
 }
 
 public void setProductName(String productName) {
   this.productName = (productName == null ? null : productName.trim());
 }
 
 public BigDecimal getPerItem() {
   return this.perItem;
 }
 
 public void setPerItem(BigDecimal perItem) {
   this.perItem = perItem;
 }
 
 public BigDecimal getPerDay() {
   return this.perDay;
 }
 
 public void setPerDay(BigDecimal perDay) {
   this.perDay = perDay;
 }
 
 public String getStatus() {
   return this.status;
 }
 
 public void setStatus(String status) {
   this.status = (status == null ? null : status.trim());
 }
 
 public BigDecimal getPerMonth() {
   return this.perMonth;
 }
 
 public void setPerMonth(BigDecimal perMonth) {
   this.perMonth = perMonth;
 }
 
 public Date getCreateTime()
 {
   return this.createTime;
 }
 
 public void setCreateTime(Date createTime) {
   this.createTime = createTime;
 }
 
 public String getCreateAuthor() {
   return this.createAuthor;
 }
 
 public void setCreateAuthor(String createAuthor) {
   this.createAuthor = (createAuthor == null ? null : createAuthor.trim());
 }
 
 public Date getUpdateTime() {
   return this.updateTime;
 }
 
 public void setUpdateTime(Date updateTime) {
   this.updateTime = updateTime;
 }
 
 public String getUpdateAuthor() {
   return this.updateAuthor;
 }
 
 public void setUpdateAuthor(String updateAuthor) {
   this.updateAuthor = (updateAuthor == null ? null : updateAuthor.trim());
 }
 
 public Long getProId() {
   return this.proId;
 }
 
 public void setProId(Long proId) {
   this.proId = proId;
 }
 
 public String getBankcardType() {
   return this.bankcardType;
 }
 
 public void setBankcardType(String bankcardType) {
   this.bankcardType = bankcardType;
 }
 
 public String getAccountType() {
   return this.accountType;
 }
 
 public void setAccountType(String accountType) {
   this.accountType = accountType;
 }

}


