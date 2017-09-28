package com.heepay.manage.modules.cbms.util;

/**
 * 描述：
 * <p>
 * 创建者: 牛俊鹏
 * 创建时间: 2017/7/27
 * 创建描述: 异步通知订单传输对象
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者:
 * 修改时间:
 * 修改内容:
 */
public class CustomRequestNotifyVO {
    //汇付宝商户ID
    private String merchantId;
    //商户批次号
    private String merchantBatchNo;
    //申报详情
    private String customDetails;
    //导入批次号
    private String importBatchNo;
    //签名字符串
    private String signString;
    //异步通知地址
    private String notifyUrl;


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantBatchNo() {
        return merchantBatchNo;
    }

    public void setMerchantBatchNo(String merchantBatchNo) {
        this.merchantBatchNo = merchantBatchNo;
    }

    public String getSignString() {
        return signString;
    }

    public void setSignString(String signString) {
        this.signString = signString;
    }

    public String getCustomDetails() {
        return customDetails;
    }

    public void setCustomDetails(String customDetails) {
        this.customDetails = customDetails;
    }

    public String getImportBatchNo() {
        return importBatchNo;
    }

    public void setImportBatchNo(String importBatchNo) {
        this.importBatchNo = importBatchNo;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
