package com.heepay.manage.modules.cbms.entity;

/**
 * 描    述：申报订单查询结果 CusdeclarationResult类
 * <p>
 * 创 建 者： @author guozx
 * 创建时间： 2017年1月20日 12:40:25
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
public class CusdeclarationResult {
    private String declarationNumberCount;   // 申报总笔数
    private String declarationNumberSuccess;   // 成功总笔数
    private String declarationNumberFailed;   // 失败总笔数
    private String declarationMoneyCount;   // 申报总金额
    private String declarationMoneySuccess;   // 成功总金额
    private String declarationMoneyFailed;   // 失败总金额
    private String feeCount;   // 手续费

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

    public String getFeeCount() {
        return feeCount;
    }

    public void setFeeCount(String feeCount) {
        this.feeCount = feeCount;
    }

    public CusdeclarationResult() {

    }
}
