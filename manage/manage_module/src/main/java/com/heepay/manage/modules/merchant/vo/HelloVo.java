package com.heepay.manage.modules.merchant.vo;

import com.heepay.manage.common.persistence.DataEntity;

public class HelloVo extends DataEntity<HelloVo>{
	private static final long serialVersionUID = 1L;
	
	private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}