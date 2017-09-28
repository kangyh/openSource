package com.heepay.manage.modules.merchant.vo;

import com.heepay.manage.common.persistence.DataEntity;

public class Monitor extends DataEntity<Monitor>{

    private static final long serialVersionUID = 1L;
    
    private Integer portId;//端口
    private String value;//成功
    private String service;//服务名
    
    public Integer getPortId() {
        return portId;
    }
    public void setPortId(Integer portId) {
        this.portId = portId;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getService() {
        return service;
    }
    public void setService(String service) {
        this.service = service;
    }
    
    
}
