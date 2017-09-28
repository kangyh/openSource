/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.entity;

import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 *
 * 描    述： 汇聚财web端功能模块
 *
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-31 15:11:56
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
public class HgmsMerchantFuctions extends DataEntity<HgmsMerchantFuctions> {

    private static final long serialVersionUID = 1L;
    private String functionCode;        // 功能CODE
    private String functionName;        // 功能名称
    private String functionUrl;        // 功能URL
    private String functionStatus;        // 功能状态
    private String parentFunctionCode;        // 父功能CODE

    public HgmsMerchantFuctions() {
        super();
    }

    public HgmsMerchantFuctions(String id) {
        super(id);
    }

    @Length(min = 0, max = 50, message = "功能CODE长度必须介于 0 和 50 之间")
    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    @Length(min = 0, max = 100, message = "功能名称长度必须介于 0 和 100 之间")
    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @Length(min = 0, max = 100, message = "功能URL长度必须介于 0 和 100 之间")
    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }

    @Length(min = 0, max = 1, message = "功能状态长度必须介于 0 和 1 之间")
    public String getFunctionStatus() {
        return functionStatus;
    }

    public void setFunctionStatus(String functionStatus) {
        this.functionStatus = functionStatus;
    }

    @Length(min = 0, max = 200, message = "父功能CODE长度必须介于 0 和 200 之间")
    public String getParentFunctionCode() {
        return parentFunctionCode;
    }

    public void setParentFunctionCode(String parentFunctionCode) {
        this.parentFunctionCode = parentFunctionCode;
    }

    @Override
    public String toString() {
        return "HgmsMerchantFuctions{" +
                "functionCode='" + functionCode + '\'' +
                ", functionName='" + functionName + '\'' +
                ", functionUrl='" + functionUrl + '\'' +
                ", functionStatus='" + functionStatus + '\'' +
                ", parentFunctionCode='" + parentFunctionCode + '\'' +
                '}';
    }
}