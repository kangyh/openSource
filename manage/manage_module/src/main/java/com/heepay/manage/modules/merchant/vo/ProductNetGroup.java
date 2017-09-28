package com.heepay.manage.modules.merchant.vo;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * 描述：
 * <p>
 * 创建者  ly
 * 创建时间 2017-04-10-20:22
 * 创建描述：
 * <p>
 * 审核者：
 * 审核时间：
 * 审核描述：
 * <p>
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
public class ProductNetGroup extends DataEntity<ProductNetGroup> {

    String productCode; //产品代码

    String productName; //产品名称

    String netGroup;//.net组

    String createName;//创建人

    String updateName;//更新人

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

    public String getNetGroup() {
        return netGroup;
    }

    public void setNetGroup(String netGroup) {
        this.netGroup = netGroup;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }
}
