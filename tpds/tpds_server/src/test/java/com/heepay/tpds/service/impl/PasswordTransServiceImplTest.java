/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年2月22日上午9:54:28
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
package com.heepay.tpds.service.impl;

import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年2月22日上午9:54:28
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring.xml")
public class PasswordTransServiceImplTest {
	
	@Autowired
	PasswordTransServiceImpl passwordTransServiceImpl;
	
	/**
	 * 
	 * @方法说明：
	 * @author xuangang
	 * @时间：2017年2月22日上午9:58:14
	 */
	@Test
	public void testpasswordVerify(){
		
		try {
			passwordTransServiceImpl.passwordVerify("","systemCode=xxx1&userId=xxxxxx2&businessSeqNo=xxxxx3&backURL=xxxxx4&signTime=xxxxx&signature=xxxxx");
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testpasswordVerifyBack(){
		
		try {
			passwordTransServiceImpl.passwordVerifyBack("","backURL?userId=xxxxxx&businessSeqNo=xxxxx&flag=1&signTime=xxxxx&signature=xxxxx");
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
