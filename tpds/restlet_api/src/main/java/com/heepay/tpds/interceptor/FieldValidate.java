package com.heepay.tpds.interceptor;

import java.io.*; 
import java.util.*; 
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年2月18日 下午4:35:30
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
public class FieldValidate {

	private static final Logger log = LogManager.getLogger();
	/**
	 * 
	 * @param xmlFormatTemplatePath
	 * @param jsonObject
	 * @return
	 */
	public static JSONObject validateData(String xmlFormatTemplatePath,JSONObject jsonObject)
	{
		if(jsonObject==null)
		{
			return new JSONObject().put("errorcode", "999")
								   .put("errorinfo", "传入的对像不能为null值");		
		}
			
		//log.info("xmlFormatTemplatePath:"+xmlFormatTemplatePath);
		// 解析文件，生成document对象
		DocumentBuilder builder;
		Document document = null;
		try {
			
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = builder.parse(new File(xmlFormatTemplatePath));
			//document = builder.parse(new File("format/customersync.xml"));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 // 生成XPath对象
		 XPath xpath = XPathFactory.newInstance().newXPath();
		 JSONObject error =  valideFormatXMLFormat(jsonObject,document.getFirstChild(),xpath,document,""); //从根节点 root 开始
		 if(error ==null)
		 {
			 // 获取节点值
			 error = validateXMLFormat(xpath,jsonObject,document,"/root");
		 }
		 return error;
		
	}
	/**
	 * 以校验模板为基础，校验数据是否有缺失
	 * @return
	 */
	private static JSONObject valideFormatXMLFormat(JSONObject jsonObject,Node element,XPath xpath,Document document,String relativePath){
		if (!(element instanceof Element)){
			return null;
		}
		JSONObject error = null;
		NodeList elements = element.getChildNodes();
		String fullPath = relativePath+"/"+element.getNodeName();
		System.out.println(fullPath);
		if (!(fullPath.equals("/root"))) // /root 不做验证
		{
			if (element.getNodeName().equals("note")){
				System.out.print("note");
			}
			if (jsonObject.has(element.getNodeName()))
			{
				 try {
					String obj = (String)xpath.evaluate(relativePath+"/"+element.getNodeName()+"/@obj", document,XPathConstants.STRING);
					if (obj.equals("json")){ //只检查 json 字段
						jsonObject = jsonObject.getJSONObject(element.getNodeName());
					}else
					{
						return null;
					}
				 } catch (XPathExpressionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				error = new JSONObject();
				error.put("errorcode", "888");
				error.put("errorinfo", element.getNodeName()+"字段缺失，请检查");
				return error;
			}
		}	
		
        for(int i=0;elements != null &&i< elements.getLength();i++) {
        	error = valideFormatXMLFormat(jsonObject, elements.item(i),xpath,document,relativePath+"/"+element.getNodeName());  
        	if (error != null){
        		break;
        	}
        }  
		return error;
	}
	/**
	 * 以上传数据为主验证数据的有效性
	 * @param xpath
	 * @param jsonObject
	 * @param document
	 * @param relativePath
	 * @return
	 */
	private static JSONObject validateXMLFormat(XPath xpath,JSONObject jsonObject,Document document,String relativePath)
	{
		
		JSONObject result = new JSONObject();
		JSONObject error = new JSONObject();
		Set jsonSet = jsonObject.keySet();
		Iterator iterator  = jsonSet.iterator();
		String errorcode = "";
		String errorinfo="";
		while (iterator.hasNext()) {
			 try {
				 String key = iterator.next().toString();
				 String obj = (String)xpath.evaluate(relativePath+"/"+key+"/@obj", document,XPathConstants.STRING);
				 if (obj.equals("json"))
				 {
					  error = validateXMLFormat(xpath,jsonObject.getJSONObject(key),document,relativePath+"/"+key);
					 if (!(error.has("errorinfo"))) {
						 continue;
					 }else{
						 break;
					 }
				 }
				 result = jsonObject;
				 String type = (String) xpath.evaluate(relativePath+"/"+key+"/@type", document,XPathConstants.STRING);
				 String req =  (String) xpath.evaluate(relativePath+"/"+key+"/@req", document,XPathConstants.STRING);
				 if (type.toUpperCase().equals("VARCHAR") && req !=null && req.toUpperCase().equals("M")){
					int value = Integer.valueOf(xpath.evaluate(relativePath+"/"+key+"/@value", document,XPathConstants.STRING).toString());
					if (result.getString(key).length()>value)
					{
						errorinfo = (String) xpath.evaluate(relativePath+"/"+key+"/text()", document,XPathConstants.STRING);
						errorcode = (String) xpath.evaluate(relativePath+"/"+key+"/@code", document,XPathConstants.STRING);
						break;
					}
					//System.out.println("key:"+key+",type:"+type+",value:"+value);
				}else if(type.toUpperCase().equals("CHAR") && req !=null && req.toUpperCase().equals("M"))
				{
					int value = Integer.valueOf(xpath.evaluate(relativePath+"/"+key+"/@value", document,XPathConstants.STRING).toString());
					if (result.getString(key).length()!=value)
					{
						errorinfo = (String) xpath.evaluate(relativePath+"/"+key+"/text()", document,XPathConstants.STRING);
						errorcode = (String) xpath.evaluate(relativePath+"/"+key+"/@code", document,XPathConstants.STRING);
						break;
					}
				}
				else if(type.toUpperCase().equals("INT") && req !=null && req.toUpperCase().equals("M")){
					try
					{
						Integer.valueOf(result.getString(key));
					}catch(Exception ex){
						errorinfo = (String) xpath.evaluate(relativePath+"/"+key+"/text()", document,XPathConstants.STRING);
						errorcode = (String) xpath.evaluate(relativePath+"/"+key+"/@code", document,XPathConstants.STRING);
						break;
					}
				}else if(type.toUpperCase().equals("DOUBLE") && req !=null && req.toUpperCase().equals("M")){
					try
					{
						Double.valueOf(result.getString(key));
					}catch(Exception ex){
						errorinfo = (String) xpath.evaluate(relativePath+"/"+key+"/text()", document,XPathConstants.STRING);
						errorcode = (String) xpath.evaluate(relativePath+"/"+key+"/@code", document,XPathConstants.STRING);
						break;
					}
				}else if(type.toUpperCase().equals("LIST") && req !=null && req.toUpperCase().equals("M")){ 
				
					JSONArray jsonArray= (JSONArray)result.get(key);
					for (int index=0;index<jsonArray.length();index++)
					{
						error = validateXMLFormat(xpath,jsonArray.getJSONObject(index),document,relativePath+"/"+key);
						if (error.has("errorinfo")){
							break; //退出第一层for循环
						}
					}
					if (error.has("errorinfo")){
						break; //退出第二层while 循环
					}
				}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!(errorinfo.equals(""))){
			error.put("errorcode", errorcode);
			error.put("errorinfo", errorinfo);
		}
		return error;
	}
}
