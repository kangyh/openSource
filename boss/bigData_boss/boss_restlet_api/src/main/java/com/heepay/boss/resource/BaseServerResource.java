package com.heepay.boss.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.ServerResource;

/**
 * 描    述：风控系统
 * <p>
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年2月10日 上午10:55:29
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */

public class BaseServerResource extends ServerResource{
	
	private static final Logger log = LogManager.getLogger();
	
	@Override
	 public void init(Context context, Request request, Response response){
		 super.init(context, request, response);
		 log.info("--------------------开始打印浏览器请求参数---------------------");
		 log.info("context---->"+context.getCurrent().toString());
		 log.info("request---->"+request.getHeaders().toString());
		 String referer = request.getHeaders().getValues("Referer");
		 
		 if(referer==null){
			 log.info("request.referer---->"+"is null");
		 }else{
			 if(request.getHeaders().removeFirst("Referer")){
				 log.info("request.referer---->"+"is delete");
				 log.info("request---->"+request.getHeaders().toString());
			 }else{
				 log.info("request.referer---->"+referer);
			 }
		 }
		 log.info("request---->"+request.getHostRef().toString());
		 log.info("request---->"+request.getMethod().toString());
		 log.info("request---->"+request.getCurrent().toString());	 
		 log.info("request body---->"+request.getEntity().toString());
		 log.info("--------------------结束打印浏览器请求参数---------------------");
	}
}
