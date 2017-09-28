package com.heepay.tpds.service.impl;

import com.heepay.common.util.StringUtils;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 描 述：
 * <p>
 * 创 建 者：wangdong
 * 创建时间：2017/8/16 14:14
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring.xml")
public class B2CAsynNoticeServiceImplTest  extends TestCase {

    @Autowired
    private B2CAsynNoticeServiceImpl b2CAsynNoticeServiceImpl;

    //构造方法
    public B2CAsynNoticeServiceImplTest(String name) {
        super(name);
    }

    //测试套件
    public static void main(String[] args) {
        junit.textui.TestRunner.run(new TestSuite(B2CAsynNoticeServiceImplTest.class));
    }

    /**
     * @方法说明：B2C网银充值异步通知
     * @时间： 2017/8/16 14:19
     * @创建人：wangdong
     */
    @Test
    public void testCustomerChargeAsynNotice(String requestHeader, String requestBody) throws Exception {
        try {
            String ret = b2CAsynNoticeServiceImpl.customerChargeAsynNotice(requestHeader, requestBody);
            if(StringUtils.isNotBlank(ret)){
                Assert.assertEquals("B2C网银充值异步通知","成功");
            }else{
                Assert.assertEquals("B2C网银充值异步通知","失败");
            }
        } catch (Exception e) {
            fail("Not yet implemented");
        }
    }
}