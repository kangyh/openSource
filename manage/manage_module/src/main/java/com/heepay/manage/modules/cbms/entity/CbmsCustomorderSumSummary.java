package com.heepay.manage.modules.cbms.entity;

/**
 * 描    述：CbmsCustomorderSumSummary类(cbms/cbmsCustomorderSum/中的计算)
 * <p>
 * 创 建 者： @author gzx 郭正新
 * 创建时间： 2017/1/17 17:00
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
public class CbmsCustomorderSumSummary {
    private String declarationNumberCount;   // 申报总笔数
    private String declarationNumberSuccess;   // 成功总笔数
    private String declarationNumberFailed;   // 失败总笔数
    private String declarationMoneyCount;   // 申报总金额
    private String declarationMoneySuccess;   // 成功总金额
    private String declarationMoneyFailed;   // 失败总金额
    private String feeCount;   // 手续费

    public String getDeclarationMoneyCount() {
        return declarationMoneyCount;
    }

    public void setDeclarationMoneyCount(String declarationMoneyCount) {
        this.declarationMoneyCount = declarationMoneyCount;
    }

    public String getDeclarationMoneySuccess() {
        return declarationMoneySuccess;
    }

    public void setDeclarationMoneySuccess(String declarationMoneySuccess) {
        this.declarationMoneySuccess = declarationMoneySuccess;
    }

    public String getDeclarationMoneyFailed() {
        return declarationMoneyFailed;
    }

    public void setDeclarationMoneyFailed(String declarationMoneyFailed) {
        this.declarationMoneyFailed = declarationMoneyFailed;
    }

    public String getDeclarationNumberCount() {
        return declarationNumberCount;
    }

    public void setDeclarationNumberCount(String declarationNumberCount) {
        this.declarationNumberCount = declarationNumberCount;
    }

    public String getDeclarationNumberSuccess() {
        return declarationNumberSuccess;
    }

    public void setDeclarationNumberSuccess(String declarationNumberSuccess) {
        this.declarationNumberSuccess = declarationNumberSuccess;
    }

    public String getDeclarationNumberFailed() {
        return declarationNumberFailed;
    }

    public void setDeclarationNumberFailed(String declarationNumberFailed) {
        this.declarationNumberFailed = declarationNumberFailed;
    }

    public String getFeeCount() {
        return feeCount;
    }

    public void setFeeCount(String feeCount) {
        this.feeCount = feeCount;
    }
}
