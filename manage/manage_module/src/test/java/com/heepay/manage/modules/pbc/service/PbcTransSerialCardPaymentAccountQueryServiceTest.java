package com.heepay.manage.modules.pbc.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.manage.modules.pbc.entity.PbcTransSerialCardPaymentAccountQuery;

import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * 
 *
 * 描    述：测试按交易流水查询银行卡或支付账号Service
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月22日11:45:53
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
public class PbcTransSerialCardPaymentAccountQueryServiceTest extends TestCase{

	@Autowired
	private PbcTransSerialCardPaymentAccountQueryService pbcTransSerialCardPaymentAccountQueryService;
	
	//构造方法
	public PbcTransSerialCardPaymentAccountQueryServiceTest(String name) {
		super(name);
	}
	
	//测试套件
	public static void main(String[] args) {
		junit.textui.TestRunner.run(new TestSuite(PbcTransSerialCardPaymentAccountQueryServiceTest.class));
	}

	/**
	 * 
	 * @方法说明：获取实体信息
	 * @时间：2016年12月26日 下午2:20:47
	 * @创建人：wangdong
	 */
	@Test
	public void testGetEntityById() {
		try {
			int pbcId = 1;
			PbcTransSerialCardPaymentAccountQuery r = pbcTransSerialCardPaymentAccountQueryService.getEntityById(pbcId);
			if(null != r){
				Assert.assertEquals("获取实体信息", "成功");
			}else{
				Assert.assertEquals("获取实体信息", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：更新实体信息
	 * @时间：2016年12月26日 下午2:21:06
	 * @创建人：wangdong
	 */
	@Test
	public void testUpdateEntity() {
		try {
			PbcTransSerialCardPaymentAccountQuery pbcTransSerialCardPaymentAccountQuery = new PbcTransSerialCardPaymentAccountQuery();
			pbcTransSerialCardPaymentAccountQueryService.updateEntity(pbcTransSerialCardPaymentAccountQuery);
			Assert.assertEquals("更新实体信息", "成功");
		} catch (Exception e) {
			Assert.assertEquals("更新实体信息", "失败");
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：保存实体信息
	 * @时间：2016年12月26日 下午2:21:20
	 * @创建人：wangdong
	 */
	@Test
	public void testSaveEntity() {
		try {
			PbcTransSerialCardPaymentAccountQuery pbcTransSerialCardPaymentAccountQuery = new PbcTransSerialCardPaymentAccountQuery();
			pbcTransSerialCardPaymentAccountQueryService.saveEntity(pbcTransSerialCardPaymentAccountQuery);
			Assert.assertEquals("保存实体信息", "成功");
		} catch (Exception e) {
			Assert.assertEquals("保存实体信息", "失败");
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

	/**
	 * 
	 * @方法说明：获取实体信息
	 * @时间：2016年12月26日 下午2:21:37
	 * @创建人：wangdong
	 */
	@Test
	public void testGetQueryInfoId() {
		try {
			int pbcId = 1;
			PbcTransSerialCardPaymentAccountQuery r = pbcTransSerialCardPaymentAccountQueryService.getEntityById(pbcId);
			if(null != r){
				Assert.assertEquals("获取实体信息", "成功");
			}else{
				Assert.assertEquals("获取实体信息", "失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
		}
	}

}
