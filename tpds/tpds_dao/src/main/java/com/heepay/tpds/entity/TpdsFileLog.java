package com.heepay.tpds.entity;

import java.util.Date;

public class TpdsFileLog {
    /**
     * 日记ID
     */
    private Integer logId;

    /**
     * 商户编码
     */
    private String merchantNo;

    /**
     * 源文件IP
     */
    private String checkFileFrom;

    /**
     * 上传文件IP
     */
    private String checkFileWhere;

    /**
     * 对账文件名
     */
    private String fileName;

    /**
     * 存放目录
     */
    private String fileDir;

    /**
     * 操作人
     */
    private String operPerson;

    /**
     * 操作时间
     */
    private Date operTime;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getCheckFileFrom() {
        return checkFileFrom;
    }

    public void setCheckFileFrom(String checkFileFrom) {
        this.checkFileFrom = checkFileFrom == null ? null : checkFileFrom.trim();
    }

    public String getCheckFileWhere() {
        return checkFileWhere;
    }

    public void setCheckFileWhere(String checkFileWhere) {
        this.checkFileWhere = checkFileWhere == null ? null : checkFileWhere.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileDir() {
        return fileDir;
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir == null ? null : fileDir.trim();
    }

    public String getOperPerson() {
        return operPerson;
    }

    public void setOperPerson(String operPerson) {
        this.operPerson = operPerson == null ? null : operPerson.trim();
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }
}