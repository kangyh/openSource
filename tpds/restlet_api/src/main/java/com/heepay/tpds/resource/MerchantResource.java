package com.heepay.tpds.resource;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年2月9日 下午3:01:36
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
public class MerchantResource  extends BaseServerResource{

	@Get
	public Representation getMerchantInfo_get(Representation entity) {		
		Form form = this.getRequest().getResourceRef().getQueryAsForm() ;	//获取查询参数
		String username = form.getFirstValue("username");		//获取key=username的参数值
		System.out.println(username);
		return new StringRepresentation("测试");
	}
	
	@Post
	public Representation getMerchantInfo_post(String username) {
		System.out.println(username);
		return new StringRepresentation("测试");
	}
}