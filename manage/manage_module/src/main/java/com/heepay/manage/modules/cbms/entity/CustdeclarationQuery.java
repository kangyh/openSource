package com.heepay.manage.modules.cbms.entity;


/**
 * 描    述：申报订单查询条件 custdeclarationQuery类
 * <p>
 * 创 建 者： @author guozx
 * 创建时间： 2017年1月20日 12:35:25
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
public class CustdeclarationQuery {
    private String status;		// 状态            (1未报送2报送处理中3报送成功4报送失败5取消6审核拒绝)
    private String merchantNo;		// 商户ID

    private String importBatchNumber;		// 导入批次号

    private String declarationBatchHumber;		// 申报批次号
    private String declarationTimeStart;		// 申报时间
    private String declarationTimeEnd;		// 申报时间
    private String customCode;		// 关区代码
    private String declareType;//申报方式
    public CustdeclarationQuery() {
    }

    public String getDeclareType() {
        return declareType;
    }

    public void setDeclareType(String declareType) {
        this.declareType = declareType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getImportBatchNumber() {
        return importBatchNumber;
    }

    public void setImportBatchNumber(String importBatchNumber) {
        this.importBatchNumber = importBatchNumber;
    }

    public String getDeclarationBatchHumber() {
        return declarationBatchHumber;
    }

    public void setDeclarationBatchHumber(String declarationBatchHumber) {
        this.declarationBatchHumber = declarationBatchHumber;
    }

    public String getDeclarationTimeStart() {
        return declarationTimeStart;
    }

    public void setDeclarationTimeStart(String declarationTimeStart) {
        this.declarationTimeStart = declarationTimeStart;
    }

    public String getDeclarationTimeEnd() {
        return declarationTimeEnd;
    }

    public void setDeclarationTimeEnd(String declarationTimeEnd) {
        this.declarationTimeEnd = declarationTimeEnd;
    }

    public String getCustomCode() {
        return customCode;
    }

    public void setCustomCode(String customCode) {
        this.customCode = customCode;
    }
}
