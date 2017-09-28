package com.heepay.manage.modules.merchant.service;

import java.util.List;

import com.heepay.manage.modules.merchant.vo.HelloVo;

public interface HelloCService {

	int saveHello(String name);

	List<HelloVo> queryHello();

	HelloVo queryInfo(String id);

	public int updateHello(HelloVo hello);

}
