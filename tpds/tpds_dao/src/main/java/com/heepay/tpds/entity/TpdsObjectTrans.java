package com.heepay.tpds.entity;

public class TpdsObjectTrans {
    /**
     * 主键
     */
    private Long id;

    /**
     * 标的编码
     */
    private String objId;

    private String businessSeqNo;

    /**
     * 业务操作类型(
            M01：修改银行密码
            M02：重置银行密码
            U01：客户注册
            U02：客户信息修改
            B01：客户绑卡
            B02：客户解绑
            B03：客户绑卡修改
            P01：标的发布
            P03：标的撤标
            P04：标的修改
            T01：投标
            T02: 取消投标
            T03：放款
            T04：还款
            T05：出款
            T07：受让
            T10：营销)
     */
    private String busiTradeType;

    /**
     * 委托标识
     */
    private String entrustflag;

    /**
     * 备注
     */
    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId == null ? null : objId.trim();
    }

    public String getBusinessSeqNo() {
        return businessSeqNo;
    }

    public void setBusinessSeqNo(String businessSeqNo) {
        this.businessSeqNo = businessSeqNo == null ? null : businessSeqNo.trim();
    }

    public String getBusiTradeType() {
        return busiTradeType;
    }

    public void setBusiTradeType(String busiTradeType) {
        this.busiTradeType = busiTradeType == null ? null : busiTradeType.trim();
    }

    public String getEntrustflag() {
        return entrustflag;
    }

    public void setEntrustflag(String entrustflag) {
        this.entrustflag = entrustflag == null ? null : entrustflag.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}