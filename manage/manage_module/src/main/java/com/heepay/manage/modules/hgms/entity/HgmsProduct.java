/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.entity;

/**
 *
 * 描    述：产品临时类（防止产品其他信息泄露）
 *
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-10 15:57:36
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
public class HgmsProduct {

    private String productCode;        // 产品编码
    private String productName;        // 产品名称

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "HgmsProduct{" +
                "productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}