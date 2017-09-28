package com.heepay.tpds.interceptor;

import java.lang.annotation.Annotation;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.heepay.common.util.StringUtil;
import com.heepay.tpds.service.SysNo;
import com.heepay.tpds.service.impl.ConfigServiceImpl;
import com.heepay.tpds.vo.CommonResponseHeaderMessage;
import com.heepay.tpds.vo.ResponseMessage;
/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年2月14日 下午1:26:04
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
public class ServiceInterceptor implements MethodInterceptor {
	private static final Logger log = LogManager.getLogger();
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		StopWatch clock = new StopWatch();
		boolean isExist = false;
		Object result =null;
        //invocation.getClass()
        // 方法参数类型，转换成简单类型  
        Class[] params = invocation.getMethod().getParameterTypes();  
        String[] simpleParams = new String[params.length];  
        Annotation[] tation  = invocation.getMethod().getAnnotations();
        SysNo sysNo= invocation.getMethod().getAnnotation(SysNo.class);
        clock.start(); // 计时开始  
        if (sysNo!=null)
        {
        	Object[] args = invocation.getArguments(); //默认第一个参数是存放reqHeader
        	JSONObject jsonObject = new JSONObject(args[0].toString());
        	if (!(StringUtil.isBlank((ConfigServiceImpl.GetBySysNo(jsonObject.get("merchantCode").toString())))))
        	{
        		isExist = true;
        	}
        	if (isExist){//存在校验
            	
            	result = invocation.proceed(); 
            	 
            }else{//不存在校验
            	//返回报错信息
            	ResponseMessage responseMessage = new ResponseMessage();
            	CommonResponseHeaderMessage commonResponseHeaderMessage =new CommonResponseHeaderMessage();
            	JSONObject outBody = new JSONObject();
            	responseMessage.setRespHeader(commonResponseHeaderMessage);
            	responseMessage.setOutBody(outBody);
            	commonResponseHeaderMessage.setRespCode("10254");
            	commonResponseHeaderMessage.setRespMsg("没有查询到接入编号");
            	result = (new JSONObject(responseMessage)).toString();
            }
        }
        else   //不需要验证
        {
        	result = invocation.proceed();
        }
        clock.stop(); // 计时结束  
        //Object[] args = invocation.getArguments(); 
        String loginfo = "Takes:" + clock.getTime() + " ms ["  
        + invocation.getThis().getClass().getName() + "."  
        + invocation.getMethod().getName() + "] ";
        log.info("method:ServiceInterceptor,info:{}",loginfo);
		return result;
	}
}
