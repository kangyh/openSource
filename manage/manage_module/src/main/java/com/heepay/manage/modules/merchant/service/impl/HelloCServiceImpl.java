package com.heepay.manage.modules.merchant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.dao.HelloDao;
import com.heepay.manage.modules.merchant.service.HelloCService;
import com.heepay.manage.modules.merchant.vo.HelloVo;

@Service
public class HelloCServiceImpl extends CrudService<HelloDao,HelloVo> implements HelloCService {
	@Autowired
	private HelloDao helloDao;
	
	@Override
	public int saveHello(String name){
		HelloVo vo = new HelloVo();
		vo.setName(name);
		int num = helloDao.insertSelective(vo);
		return num;
	}

	@Override
	public List<HelloVo> queryHello() {
		return helloDao.selectAll();
	}

	@Override
	public HelloVo queryInfo(String id) {
		HelloVo vo = helloDao.selectByPrimaryKey(id);
		return vo;
	}

	@Override
	public int updateHello(HelloVo hello) {
		HelloVo vo = new HelloVo();
		vo.setId(hello.getId());
		vo.setName(hello.getName());
		int num = helloDao.updateByPrimaryKeySelective(vo);
		return num;
	}


}
