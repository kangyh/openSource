package com.heepay.manage.modules.reconciliation.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.reconciliation.entity.SettleRegexControl;

import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 *
 * 描    述：规则验证的测试类
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年1月20日 上午9:21:24
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
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" }) // 用于指定配置文件所在的位置
public class SettleRegexControlServiceTest extends TestCase{
	
	@Autowired
	private SettleRegexControlService settleRegexControlService;
	
	//构造方法
	public SettleRegexControlServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(SettleRegexControlServiceTest.class));
	}

	@Test
	public void testSaveEntity() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetEntityByList() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateEntity() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteEntity() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateList() {
		fail("Not yet implemented");
	}

}
