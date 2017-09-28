package com.sohu.test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.dao.DaoMethod;
/**
 * <P>
 * Description:
 * </p>
 * @author kangyh
 * @version 1.0
 * @Date 2015年12月25日下午6:24:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-aop-aspectj.xml"})
public class TestStatis {
	@Autowired
    DaoMethod daoMethod;
	
    @Test
    public void Method() throws InterruptedException{
        while(true){
            daoMethod.method1();
            daoMethod.method2();
            daoMethod.method3();
            daoMethod.method4();
            daoMethod.method5();

            daoMethod.method2();
            daoMethod.method3();
            daoMethod.method4();
            Thread.currentThread().sleep(3000);
        }
    }

}