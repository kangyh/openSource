package com.heepay.manage.modules.cbms.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.heepay.manage.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * Created by Administrator on 2017/1/4.
 */
public class CbmsCustomCodeSuper extends DataEntity<CbmsCustomCodeSuper> {
    private static final long serialVersionUID = 1L;
    private String pcustomCode;		// 父级编码
    private String pchinaName;		// 父级名称
    private String customCode;		// 子级编码
    private String chinaName;		// 子级名称
    private String status;		// 状态            0.正常            1. 停用
    private Date createdTime;		// 创建时间
    public CbmsCustomCodeSuper() {
        super();
    }

    public CbmsCustomCodeSuper(String id){
        super(id);
    }

    @Length(min=1, max=10, message="Customs长度必须介于 1 和 10 之间")
    public String getPcustomCode() {
        return pcustomCode;
    }

    public void setPcustomCode(String pcustomCode) {
        this.pcustomCode = pcustomCode;
    }

    @Length(min=0, max=11, message="上级ID长度必须介于 0 和 11 之间")
    public String getPchinaName() {
        return pchinaName;
    }

    public void setPchinaName(String pchinaName) {
        this.pchinaName = pchinaName;
    }

    @Length(min=1, max=10, message="关区代码长度必须介于 1 和 10 之间")
    public String getCustomCode() {
        return customCode;
    }

    public void setCustomCode(String customCode) {
        this.customCode = customCode;
    }

    @Length(min=1, max=100, message="中文名称长度必须介于 1 和 100 之间")
    public String getChinaName() {
        return chinaName;
    }

    public void setChinaName(String chinaName) {
        this.chinaName = chinaName;
    }



    @ Length(min=0, max=10, message="停用长度必须介于 0 和 4 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "CbmsCustomCodeSuper{" +
                "pcustomCode='" + pcustomCode + '\'' +
                "| pchinaName='" + pchinaName + '\'' +
                "| customCode='" + customCode + '\'' +
                "| chinaName='" + chinaName + '\'' +
                "| status='" + status + '\'' +
                "| createdTime=" + createdTime +
                '}';
    }
}
