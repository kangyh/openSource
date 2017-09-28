package com.heepay.manage.modules.pbc.entity;

import com.heepay.manage.common.persistence.DataEntity;

public class PbcBankInfo extends DataEntity<PbcBankInfo>{
    /**
	 * 2016年12月24日
	 */
	private static final long serialVersionUID = -360823397066684808L;

	private Long pbcId;

    private String bankId;

    private String bankName;

    private String bankAccount;

    private String cardType;

    private String cardValidation;

    private String cardExpiryDate;

    private String cardInfo;

    private String applicationCode;

    public Long getPbcId() {
        return pbcId;
    }

    public void setPbcId(Long pbcId) {
        this.pbcId = pbcId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }

    public String getCardValidation() {
        return cardValidation;
    }

    public void setCardValidation(String cardValidation) {
        this.cardValidation = cardValidation == null ? null : cardValidation.trim();
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate == null ? null : cardExpiryDate.trim();
    }

    public String getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(String cardInfo) {
        this.cardInfo = cardInfo == null ? null : cardInfo.trim();
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode == null ? null : applicationCode.trim();
    }
}