package com.heepay.boss.server;

import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.ext.json.JsonRepresentation;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年2月13日 上午10:14:29
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
public class SubmitForm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*ClientResource client = new ClientResource("http://localhost:7999/customer");  
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", "wangyinglei");
		jsonObject.put("sex", "M");
		JsonRepresentation jr = new JsonRepresentation(jsonObject);  
		Representation result =  client.post(jr) ;		//调用post方法
		//System.out.println(result.getText());  
		 * 
		 * 
*/	
		
		
		String jsonStr="{\"reqHeader\":{"+
					   	"\"version\":\"1.0\","+
					   	"\"merchantCode\":\"xxx\","+
					   	"\"txnType\":\"U00001\","+
						"\"clientSn\":\"nnnnnnnnnnnnnnnn\","+
						"\"clientDate\":\"20161012\","+
						"\"clientTime\":\"135500\","+
						"\"fileName\":\"/home/filedir/\","+
						"\"signTime\":\"1478094942382\","+
						"\"signature\":\"xxxxxx\""+
						"},"+
						"inBody:"+
						"{"+
						"\"busiTradeType\":\"\","+
						"\"businessSeqNo\":\"\","+
						"\"custPassword\":\"\","+
						"\"AccountList\":"+
						"["+
						"{"+
						"\"debitAccountNo\":\"\","+
						"\"cebitAccountNo\":\"\","+
						"\"currency\":\"\","+
						"\"amount\":\"\","+
						"\"otherAmounttype\":\"\","+
						"\"otherAmount\":\"\""+
						"},"+
						"{"+
						"\"debitAccountNo\":\"\","+
						"\"cebitAccountNo\":\"\","+
						"\"currency\":\"\","+
						"\"amount\":\"\","+
						"\"otherAmounttype\":\"\","+
						"\"otherAmount\":\"\""+
						"},"+
						"{"+
						"\"debitAccountNo\":\"\","+
						"\"cebitAccountNo\":\"\","+
						"\"currency\":\"\","+
						"\"amount\":\"\","+
						"\"otherAmounttype\":\"\","+
						"\"otherAmount\":\"\""+
						"}"+
						"],"+
						"\"objectId\":\"\","+
						"\"returnType\":\"\","+
						"\"note\":\"\""+
						"}"+
						"}";
		
		ClientResource client = new ClientResource("http://localhost:7999/customer/CustomerInfoSync");  
		client.post(new JsonRepresentation(jsonStr)) ;	
	
	
	
	
	
	
	
	}

}
