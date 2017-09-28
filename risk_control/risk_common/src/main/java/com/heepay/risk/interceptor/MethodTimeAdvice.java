
package com.heepay.risk.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.heepay.risk.util.OtherRiskUtil;


/**
* @author 王英雷  E-mail:wangyl@9186.com
* @version 创建时间：2016年12月16日 上午11:32:51
* 类说明  通用记录日志说明
*/
public class MethodTimeAdvice implements MethodInterceptor {

	private static final Logger log = LogManager.getLogger();
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		 
        // 用 commons-lang 提供的 StopWatch 计时  
        StopWatch clock = new StopWatch();  
        clock.start(); // 计时开始  
        Object result = invocation.proceed();  
        clock.stop(); // 计时结束  
  
        // 方法参数类型，转换成简单类型  
        Class[] params = invocation.getMethod().getParameterTypes();  
        String[] simpleParams = new String[params.length];  
        Object[] args = invocation.getArguments(); 
        //由于反欺诈系统 中传递的参数有 base64图片，导致记录的日志过大，现将图片参数过滤掉
        int excludeIndex = -1;
        for (int i = 0; i < params.length; i++) {  
            simpleParams[i] = params[i].getSimpleName();
            //if (invocation.getMethod().getName().contains("GetPaymentAccountList"))  //方法名 GetPaymentAccountList 参数中有attachments 
            if(args[i].toString().contains("\"content\":"))
            {
            	args[i] = args[i].toString().replaceAll("\"content\":\".*\"", "\"content\":\"\"");  //实参与数数名一一对应。
            }
        } 
        String loginfo = "Takes:" + clock.getTime() + " ms ["  
        + invocation.getThis().getClass().getName() + "."  
        + invocation.getMethod().getName() + "("  
        + StringUtils.join(simpleParams, ",") + ")("  
        + StringUtils.join(args, ",") + ")] ";
        OtherRiskUtil.riskLoggerInfo(log,loginfo);
        return result;  
	} 
}

