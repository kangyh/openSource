/**
 *  
 */
package com.heepay.manage.modules.merchant.vo;

import org.hibernate.validator.constraints.Length;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * 产品Entity
 * 
 * @author ly
 * @version V1.0
 */
public class Product extends DataEntity<Product> {

    private static final long serialVersionUID = 1L;
    private String code; // 产品编码
    private String name; // 产品名称
    private String status; // 产品状态，0：正常，1：停用
    private String remark; // 备注
    private String auditStatus;// 审核状态
    private String objection; // 拒绝理由
    private String businessType; // 业务类型
    private String trxType; // 交易类型
    private String createName;// 创建人名称

    public Product() {
        super();
    }

    public Product(String id) {
        super(id);
    }

    @Length(min = 1, max = 16, message = "产品编码长度必须介于 1 和 16 之间")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min = 1, max = 100, message = "产品名称长度必须介于 1 和 100 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 255, message = "备注长度必须介于 0 和 255 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Length(min = 0, max = 6, message = "审核状态必须介于 0 和 255 之间")
    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    @Length(min = 0, max = 6, message = "状态必须介于 0 和 255 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Length(min = 0, max = 100, message = "拒绝理由长度必须介于 0 和 255 之间")
    public String getObjection() {
        return objection;
    }

    public void setObjection(String objection) {
        this.objection = objection;
    }

    @Length(min = 0, max = 6, message = "业务类型长度必须介于 0 和 6 之间")
    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    @Length(min = 0, max = 10, message = "交易类型长度必须介于 0 和 10 之间")
    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

}