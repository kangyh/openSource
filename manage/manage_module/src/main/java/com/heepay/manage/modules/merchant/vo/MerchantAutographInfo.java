/**
 *  
 */
package com.heepay.manage.modules.merchant.vo;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.heepay.manage.common.persistence.DataEntity;

/**
 * 技术签约Entity
 * 
 * @author ly
 * @version V1.0
 */
public class MerchantAutographInfo extends DataEntity<MerchantAutographInfo> {

    private static final long serialVersionUID = 1L;
    private String merchantId; // 商户id
    private String merchantCompanyName; // 商户公司名称
    private String merchantLoginName; // 商户账号
    private String merchantSignNo; // 商户签约号
    private String productCode; // 产品编码
    private String productName; // 产品名称
    private String notifyUrl; // 异步通知地址
    private String backUrl; // 同步返回地址
    private String autographType; // 签名方式
    private String autographKey; // 签名key
    private String allowInterfaceType; // 允许的接口类型
    private Date startTime; // 合同开始时间
    private Date endTime; // 合同结束时间
    private String remark; // 备注
    private String status; // 状态
    private List<String> allowInterfaceTypes;

    public MerchantAutographInfo() {
        super();
    }

    public MerchantAutographInfo(String id) {
        super(id);
    }

    @Length(min = 0, max = 11, message = "商户id长度必须介于 0 和 11 之间")
    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantLoginName() {
        return merchantLoginName;
    }

    public void setMerchantLoginName(String merchantLoginName) {
        this.merchantLoginName = merchantLoginName;
    }

    @Length(min = 0, max = 20, message = "商户签约号长度必须介于 0 和 20 之间")
    public String getMerchantSignNo() {
        return merchantSignNo;
    }

    public void setMerchantSignNo(String merchantSignNo) {
        this.merchantSignNo = merchantSignNo;
    }

    @Length(min = 0, max = 16, message = "产品代码长度必须介于 0 和 16 之间")
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    @Length(min = 0, max = 100, message = "产品名称长度必须介于 0 和 100 之间")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Length(min = 0, max = 512, message = "异步通知地址长度必须介于 0 和 512 之间")
    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    @Length(min = 0, max = 512, message = "同步返回地址长度必须介于 0 和 512 之间")
    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    @Length(min = 0, max = 3, message = "签名方式长度必须介于 0 和 3 之间")
    public String getAutographType() {
        return autographType;
    }

    public void setAutographType(String autographType) {
        this.autographType = autographType;
    }

    @Length(min = 0, max = 512, message = "签名key长度必须介于 0 和 512 之间")
    public String getAutographKey() {
        return autographKey;
    }

    public void setAutographKey(String autographKey) {
        this.autographKey = autographKey;
    }

    public String getAllowInterfaceType() {
        return allowInterfaceType;
    }

    public void setAllowInterfaceType(String allowInterfaceType) {
        this.allowInterfaceType = allowInterfaceType;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Length(min = 0, max = 255, message = "签名key长度必须介于 0 和 255 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Length(min = 0, max = 6, message = "状态长度必须介于 0 和 6 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMerchantCompanyName() {
        return merchantCompanyName;
    }

    public void setMerchantCompanyName(String merchantCompanyName) {
        this.merchantCompanyName = merchantCompanyName;
    }

    public List<String> getAllowInterfaceTypes() {
        return allowInterfaceTypes;
    }

    public void setAllowInterfaceTypes(List<String> allowInterfaceTypes) {
        this.allowInterfaceTypes = allowInterfaceTypes;
    }

}