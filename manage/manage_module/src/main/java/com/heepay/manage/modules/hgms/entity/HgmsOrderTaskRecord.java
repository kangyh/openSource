/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.hgms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 *
 * 描    述：汇聚财定时任务Entity
 *
 * 创 建 者： @author guozx@9186.com
 * 创建时间： 2017-07-31 09:55:24
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
public class HgmsOrderTaskRecord extends DataEntity<HgmsOrderTaskRecord> {

    private static final long serialVersionUID = 1L;
    private String taskName;        // 任务名称
    private Date createTime;        // 创建时间
    private Date updateTime;        // 更新时间
    private Date successTime;        // 成功时间
    private Date checkBeginDate;        // 理论执行时间范围的开始时间
    private Date checkEndDate;        // 理论执行时间范围的结束时间
    private String taskType;        // 任务类型-&ldquo;CREATE&rdquo; = 生成订单 &ldquo;EXCUTE&rdquo; = 执行订单
    private String orderAmount;        // 订单数量
    private String status;        // 状态
    private String operator;        // 操作人
    private String errorRecord;        // 错误记录
    private String extend1;        // 扩展字段1
    private String extend2;        // extend2
    private String extend3;        // extend3
    private String extend4;        // extend4
    private String extend5;        // extend5

    public HgmsOrderTaskRecord() {
        super();
    }

    public HgmsOrderTaskRecord(String id) {
        super(id);
    }

    @Length(min = 0, max = 256, message = "任务名称长度必须介于 0 和 256 之间")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCheckBeginDate() {
        return checkBeginDate;
    }

    public void setCheckBeginDate(Date checkBeginDate) {
        this.checkBeginDate = checkBeginDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCheckEndDate() {
        return checkEndDate;
    }

    public void setCheckEndDate(Date checkEndDate) {
        this.checkEndDate = checkEndDate;
    }

    @Length(min = 0, max = 6, message = "任务类型-&ldquo;CREATE&rdquo; = 生成订单 &ldquo;EXCUTE&rdquo; = 执行订单长度必须介于 0 和 6 之间")
    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    @Length(min = 0, max = 11, message = "订单数量长度必须介于 0 和 11 之间")
    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    @Length(min = 0, max = 6, message = "状态长度必须介于 0 和 6 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Length(min = 0, max = 64, message = "操作人长度必须介于 0 和 64 之间")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Length(min = 0, max = 1024, message = "错误记录长度必须介于 0 和 1024 之间")
    public String getErrorRecord() {
        return errorRecord;
    }

    public void setErrorRecord(String errorRecord) {
        this.errorRecord = errorRecord;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
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

    public String getExtend4() {
        return extend4;
    }

    public void setExtend4(String extend4) {
        this.extend4 = extend4;
    }

    public String getExtend5() {
        return extend5;
    }

    public void setExtend5(String extend5) {
        this.extend5 = extend5;
    }

    @Override
    public String toString() {
        return "HgmsOrderTaskRecord{" +
                "taskName='" + taskName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", successTime=" + successTime +
                ", checkBeginDate=" + checkBeginDate +
                ", checkEndDate=" + checkEndDate +
                ", taskType='" + taskType + '\'' +
                ", orderAmount='" + orderAmount + '\'' +
                ", status='" + status + '\'' +
                ", operator='" + operator + '\'' +
                ", errorRecord='" + errorRecord + '\'' +
                ", extend1='" + extend1 + '\'' +
                ", extend2='" + extend2 + '\'' +
                ", extend3='" + extend3 + '\'' +
                ", extend4='" + extend4 + '\'' +
                ", extend5='" + extend5 + '\'' +
                '}';
    }
}