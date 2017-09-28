/**
 *
 */
package com.heepay.manage.modules.hgms.entity;


import com.heepay.manage.common.persistence.DataEntity;

/**
 * 描    述： 商户员工功能
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-31 15:12:02
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
public class HgmsMerchantEmployeeFunctions extends DataEntity<HgmsMerchantEmployeeFunctions> {

    private static final long serialVersionUID = 1L;
    private Long merchantId;        // 商户ID
    private Long employeeId;        // 员工ID
    private Long functionId;        // 功能ID

    public HgmsMerchantEmployeeFunctions() {
        super();
    }

    public HgmsMerchantEmployeeFunctions(String id) {
        super(id);
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    @Override
    public String toString() {
        return "HgmsMerchantEmployeeFunctions{" +
                "merchantId=" + merchantId +
                ", employeeId=" + employeeId +
                ", functionId=" + functionId +
                '}';
    }
}