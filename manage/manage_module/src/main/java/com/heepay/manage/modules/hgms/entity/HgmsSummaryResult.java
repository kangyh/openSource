package com.heepay.manage.modules.hgms.entity;

/**
 * 描    述：交易汇总结果 HgmsSummaryResult类
 * <p>
 * 创 建 者： @author 牛俊鹏
 * 创建时间： 2017年3月21日
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
public class HgmsSummaryResult {
    private String tranNumberCount;   // 交易总笔数
    private String tranNumberSuccess;   // 成功总笔数
    private String tranNumberMiddle;   // 处理中总笔数
    private String tranNumberFailed;   // 失败总笔数
    private String tranMoneyCount;   // 申报总金额
    private String tranMoneyMiddle;   // 失败总金额
    private String tranMoneySuccess;   // 处理中总金额
    private String tranMoneyFailed;   // 处理中总金额
    private String feeSuccess;   // 成功手续费
    private String feeFailed;   // 失败手续费
    private String feeMiddle;   // 处理中手续费
    private String feeCount;   // 总手续费

    public String getTranNumberCount() {
        return tranNumberCount;
    }

    public void setTranNumberCount(String tranNumberCount) {
        this.tranNumberCount = tranNumberCount;
    }

    public String getTranNumberMiddle() {
        return tranNumberMiddle;
    }

    public void setTranNumberMiddle(String tranNumberMiddle) {
        this.tranNumberMiddle = tranNumberMiddle;
    }

    public String getTranNumberSuccess() {
        return tranNumberSuccess;
    }

    public void setTranNumberSuccess(String tranNumberSuccess) {
        this.tranNumberSuccess = tranNumberSuccess;
    }

    public String getTranNumberFailed() {
        return tranNumberFailed;
    }

    public void setTranNumberFailed(String tranNumberFailed) {
        this.tranNumberFailed = tranNumberFailed;
    }

    public String getTranMoneyCount() {
        return tranMoneyCount;
    }

    public void setTranMoneyCount(String tranMoneyCount) {
        this.tranMoneyCount = tranMoneyCount;
    }

    public String getTranMoneyMiddle() {
        return tranMoneyMiddle;
    }

    public void setTranMoneyMiddle(String tranMoneyMiddle) {
        this.tranMoneyMiddle = tranMoneyMiddle;
    }

    public String getTranMoneySuccess() {
        return tranMoneySuccess;
    }

    public void setTranMoneySuccess(String tranMoneySuccess) {
        this.tranMoneySuccess = tranMoneySuccess;
    }

    public String getTranMoneyFailed() {
        return tranMoneyFailed;
    }

    public void setTranMoneyFailed(String tranMoneyFailed) {
        this.tranMoneyFailed = tranMoneyFailed;
    }

    public String getFeeCount() {
        return feeCount;
    }

    public void setFeeCount(String feeCount) {
        this.feeCount = feeCount;
    }

    public String getFeeMiddle() {
        return feeMiddle;
    }

    public void setFeeMiddle(String feeMiddle) {
        this.feeMiddle = feeMiddle;
    }

    public String getFeeSuccess() {
        return feeSuccess;
    }

    public void setFeeSuccess(String feeSuccess) {
        this.feeSuccess = feeSuccess;
    }

    public String getFeeFailed() {
        return feeFailed;
    }

    public void setFeeFailed(String feeFailed) {
        this.feeFailed = feeFailed;
    }

    public HgmsSummaryResult() {

    }

    @Override
    public String toString() {
        return "HgmsSummaryResult{" +
                "tranNumberCount='" + tranNumberCount + '\'' +
                ", tranNumberSuccess='" + tranNumberSuccess + '\'' +
                ", tranNumberMiddle='" + tranNumberMiddle + '\'' +
                ", tranNumberFailed='" + tranNumberFailed + '\'' +
                ", tranMoneyCount='" + tranMoneyCount + '\'' +
                ", tranMoneyMiddle='" + tranMoneyMiddle + '\'' +
                ", tranMoneySuccess='" + tranMoneySuccess + '\'' +
                ", tranMoneyFailed='" + tranMoneyFailed + '\'' +
                ", feeSuccess='" + feeSuccess + '\'' +
                ", feeFailed='" + feeFailed + '\'' +
                ", feeMiddle='" + feeMiddle + '\'' +
                ", feeCount='" + feeCount + '\'' +
                '}';
    }
}
