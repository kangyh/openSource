package com.heepay.tpds.entity;

import java.util.Date;

public class TpdsObjectDetail {
    /**
     * 主键
     */
    private Long retId;

    /**
     * 序号
     */
    private String oderNo;

    /**
     * 还款期数
     */
    private String returnNo;

    /**
     * 还款日期
     */
    private Date returnDate;

    /**
     * 标的编号
     */
    private String objectId;

    public Long getRetId() {
        return retId;
    }

    public void setRetId(Long retId) {
        this.retId = retId;
    }

    public String getOderNo() {
        return oderNo;
    }

    public void setOderNo(String oderNo) {
        this.oderNo = oderNo == null ? null : oderNo.trim();
    }

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo == null ? null : returnNo.trim();
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId == null ? null : objectId.trim();
    }
}