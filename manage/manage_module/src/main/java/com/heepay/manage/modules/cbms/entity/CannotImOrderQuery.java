package com.heepay.manage.modules.cbms.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/23.
 */
public class CannotImOrderQuery {
    private static final long serialVersionUID = 1L;
    private String merchantNo;		// 商户号
    private List idsList;		// 订单集合

    public CannotImOrderQuery() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public List getIdsList() {
        return idsList;
    }

    public void setIdsList(List idsList) {
        this.idsList = idsList;
    }
}

