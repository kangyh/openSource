package com.heepay.risk.entity;

import java.util.Date;

public class RiskChannelOrderConversionRatio {
    private Integer id;

    private String channelPartnerCode;

    private String channelPartnerName;

    private Integer channelSuccessOrder;

    private Integer channelTotalOrder;

    private Date beginTime;

    private Date endTime;

    private Date createTime;

    private Integer sucessRatio;

    private String channelTypeCode;

    private String channelTypeName;

    private String host;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelPartnerCode() {
        return channelPartnerCode;
    }

    public void setChannelPartnerCode(String channelPartnerCode) {
        this.channelPartnerCode = channelPartnerCode == null ? null : channelPartnerCode.trim();
    }

    public String getChannelPartnerName() {
        return channelPartnerName;
    }

    public void setChannelPartnerName(String channelPartnerName) {
        this.channelPartnerName = channelPartnerName;
    }

    public Integer getChannelSuccessOrder() {
        return channelSuccessOrder;
    }

    public void setChannelSuccessOrder(Integer channelSuccessOrder) {
        this.channelSuccessOrder = channelSuccessOrder;
    }

    public Integer getChannelTotalOrder() {
        return channelTotalOrder;
    }

    public void setChannelTotalOrder(Integer channelTotalOrder) {
        this.channelTotalOrder = channelTotalOrder;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSucessRatio() {
        return sucessRatio;
    }

    public void setSucessRatio(Integer sucessRatio) {
        this.sucessRatio = sucessRatio;
    }

    public String getChannelTypeCode() {
        return channelTypeCode;
    }

    public void setChannelTypeCode(String channelTypeCode) {
        this.channelTypeCode = channelTypeCode == null ? null : channelTypeCode.trim();
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName == null ? null : channelTypeName.trim();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }
}