package com.heepay.test;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.boss.client.BossRpcCLient;
import com.heepay.boss.service.impl.ProvideDataServiceImpl;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.vo.BossRule;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring.xml")
public class ClientTest {
	
	@Autowired
	private ProvideDataServiceImpl provideDataServiceImpl;
	
	@Autowired
	private BossRpcCLient bossRpcCLient;
	
	@Test
	public void test() {
		try {
			provideDataServiceImpl.queryDate();
		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}
	
	
	
	
	@Test
	public void test1() {
		try {
			JsonMapperUtil jsonUtil = JsonMapperUtil.buildNonDefaultMapper();
			BossRule bossRule = new BossRule();
			bossRule.setRemark("aaa");
			System.out.println(jsonUtil.toJson(bossRule));
			String msg = bossRpcCLient.insertRule(jsonUtil.toJson(bossRule));
			System.out.println(msg);
		} catch (Exception e) {
			fail("Not yet implemented");
		}
	}
	
	

}
