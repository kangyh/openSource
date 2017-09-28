package com.heepay.risk.vo;

public class BankFraudAttachment {
    private String fileName;

    /**
     * 文件内容,Base64编码 (不大于1MB)
     */
    private String content;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
