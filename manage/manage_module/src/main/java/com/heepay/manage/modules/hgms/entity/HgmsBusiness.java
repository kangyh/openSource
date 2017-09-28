/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 描    述：业务管理Entity
 * <p>
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-10 15:55:12
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
public class HgmsBusiness extends DataEntity<HgmsBusiness> {

    private static final long serialVersionUID = 1L;
    private Long businessId;        // 业务ID
    private String businessName;        // 业务名称
    private String inputuserId;        // 录入人ID
    private String inputuserName;        // 录入人名称
    private Date createdTime;        // 录入时间
    private Date beginCreateTime;   // 开始 创建时间
    private Date endCreateTime;   // 结束 创建时间
    private String status;        // 状态
    private String extend2;        // extend2
    private String extend3;        // extend3

    public HgmsBusiness() {
        super();
    }

    public HgmsBusiness(String id) {
        super(id);
    }

    @NotNull(message = "业务ID不能为空")
    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    @Length(min = 0, max = 64, message = "业务名称长度必须介于 0 和 64 之间")
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Length(min = 0, max = 6, message = "录入人长度必须介于 0 和 6 之间")
    public String getInputuserId() {
        return inputuserId;
    }

    public void setInputuserId(String inputuserId) {
        this.inputuserId = inputuserId;
    }

    public String getInputuserName() {
        return inputuserName;
    }

    public void setInputuserName(String inputuserName) {
        this.inputuserName = inputuserName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getBeginCreateTime() {
        return beginCreateTime;
    }

    public void setBeginCreateTime(Date beginCreateTime) {
        this.beginCreateTime = beginCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    @Length(min = 0, max = 6, message = "状态长度必须介于 0 和 6 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

    @Override
    public String toString() {
        return "HgmsBusiness{" +
                "businessId=" + businessId +
                ", businessName='" + businessName + '\'' +
                ", inputuserId='" + inputuserId + '\'' +
                ", inputuserName='" + inputuserName + '\'' +
                ", createdTime=" + createdTime +
                ", beginCreateTime=" + beginCreateTime +
                ", endCreateTime=" + endCreateTime +
                ", status='" + status + '\'' +
                ", extend2='" + extend2 + '\'' +
                ", extend3='" + extend3 + '\'' +
                '}';
    }
}