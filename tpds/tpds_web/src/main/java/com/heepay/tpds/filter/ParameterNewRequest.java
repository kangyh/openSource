package com.heepay.tpds.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

/**
 * 
* 
* 描    述：自定义request，方便修改parameter值
*
* 创 建 者： TianYanqing  
* 创建时间： 2017年3月6日 上午11:40:03 
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

public class ParameterNewRequest extends HttpServletRequestWrapper {

	private Map<String,String[]> params ;
	
	public ParameterNewRequest(HttpServletRequest request) {
		super(request);
	}
	
	public ParameterNewRequest(HttpServletRequest request,Map<String,String[]> newParams){
		super(request);
		this.params=newParams;
		
		renewParameterMap(request);  
	}
	
	@Override
	public String getParameter(String name) {
		String result = "";
		Object v = params.get(name);
		if(v==null){
			result=null;
		}else if(v instanceof String[]){
			String[] str = (String[])v;
			if(str.length>0){
				result = str[0];
			}else{
				result = null;
			}
		}else if(v instanceof String){
			result = (String)v;
		}else{
			result = v.toString();
		}
		return result;
	}
	
	@Override
	public Map<String,String[]> getParameterMap() {
		return params;
	}
	
	@Override
	public Enumeration<String> getParameterNames() {
		return new Vector<String>(params.keySet()).elements();
	}
	
	@Override
	public String[] getParameterValues(String name) {
		if(name==null){
			return null;
		}
		String[] result = null;
		Object v = params.get(name);
		if(v==null){
			return null;
		}else if (v instanceof String[]){
			result = (String[])v ;
		}else if (v instanceof String){
			result = new String[]{(String) v};
		}else {
			result = new String[]{v.toString()};
		}
		
		return result ;
	}
	
     private void renewParameterMap(HttpServletRequest req) {  
		  
	        String queryString = req.getQueryString();  
	  
	        if (queryString != null && queryString.trim().length() > 0) {  
	            String[] queryParams = queryString.split("&");  
	  
	            for (int i = 0; i < queryParams.length; i++) {  
	                int splitIndex = queryParams[i].indexOf("=");  
	                if (splitIndex == -1) {  
	                    continue;  
	                }  
	                  
	                String key = queryParams[i].substring(0, splitIndex);  
	  
	                if (!this.params.containsKey(key)) {  
	                    if (splitIndex < queryParams[i].length()) {  
	                        String value = queryParams[i].substring(splitIndex + 1);  
	                        this.params.put(key, new String[] { value });  
	                    }  
	                }  
	            }  
	        }  
	        
	 }
     
     /**
      * 
      * @description 重新设置parameter值
      * @author TianYanqing      
      * @created 2017年3月6日 下午3:28:29     
      * @param parameter
      * @param parameterValues
      */
     public void setParameterValueByName(String parameter,String[] parameterValues){
    	 if(!this.params.containsKey(parameter)){
    		 return ;
    	 }
    	 this.params.put(parameter, parameterValues);
     }
	

}
