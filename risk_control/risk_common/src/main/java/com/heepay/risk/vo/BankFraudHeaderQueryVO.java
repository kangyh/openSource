package com.heepay.risk.vo;

public class BankFraudHeaderQueryVO {
	/**
     *交易类型编码
     */
    private String txCode;

    /**
     *发送机构编号
     */
    private String messageFrom;
    /**
     *传输报文流水号（参见附录H）
     */
    private String transSerialNumber;

    public String getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }

    public String getTxCode() {
        return txCode;
    }

    public void setTxCode(String txCode) {
        this.txCode = txCode;
    }

    public String getTransSerialNumber() {
        return transSerialNumber;
    }

    public void setTransSerialNumber(String transSerialNumber) {
        this.transSerialNumber = transSerialNumber;
    }
}
