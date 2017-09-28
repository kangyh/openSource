package com.heepay.manage.modules.cbms.util;

/**
 * 描述：
 * <p>
 * 创建者: 牛俊鹏
 * 创建时间: 2017/7/27
 * 创建描述: 订单状态明细对象
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者:
 * 修改时间:
 * 修改内容:
 */
public class CustomStatus {
    //电商订单号
    private String orderFormNo;
    //状态
    private String status;
    //错误描述
    private String message;

    public String getOrderFormNo() {
        return orderFormNo;
    }

    public void setOrderFormNo(String orderFormNo) {
        this.orderFormNo = orderFormNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
