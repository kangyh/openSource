package com.heepay.manage.modules.hgms.entity;

import com.heepay.manage.common.persistence.DataEntity;

import java.util.Date;

/**
 * 描    述：交易汇总结果 HgmsSummaryResult类
 * <p>
 * 创 建 者： @author 牛俊鹏
 * 创建时间： 2017年3月21日
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
public class HgmsMerchantFeeManagementObj extends DataEntity<HgmsMerchantFeeManagementObj> {
    private String sourcemerchantid;   // 商户编号
    private String sourceMerchantName;   // 商户名称
    private String feeAount;   // 手续费
    private Date beginCreateTime;        // 开始区间 createTime
    private Date endCreateTime;        // 结束区间  createTime
    private String flagstr;        // 标识为收款

    public String getFlagstr() {
        return flagstr;
    }

    public void setFlagstr(String flagstr) {
        this.flagstr = flagstr;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private Date createTime;        // 结束区间  createTime


    public String getSourcemerchantid() {
        return sourcemerchantid;
    }

    public void setSourcemerchantid(String sourcemerchantid) {
        this.sourcemerchantid = sourcemerchantid;
    }

    public String getSourceMerchantName() {
        return sourceMerchantName;
    }

    public void setSourceMerchantName(String sourceMerchantName) {
        this.sourceMerchantName = sourceMerchantName;
    }

    public String getFeeAount() {
        return feeAount;
    }

    public void setFeeAount(String feeAount) {
        this.feeAount = feeAount;
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

    public HgmsMerchantFeeManagementObj() {

    }

    @Override
    public String toString() {
        return "HgmsMerchantFeeManagementObj{" +
                "sourcemerchantid='" + sourcemerchantid + '\'' +
                ", sourceMerchantName='" + sourceMerchantName + '\'' +
                ", feeAount='" + feeAount + '\'' +
                ", beginCreateTime=" + beginCreateTime +
                ", endCreateTime=" + endCreateTime +
                ", flagstr='" + flagstr + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
