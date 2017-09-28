package com.heepay.tpds.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年5月2日下午3:47:13
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
public class IpUtil {
	
	 public static InetAddress getInetAddress(){  
	   	  
	        try{  
	            return InetAddress.getLocalHost();  
	        }catch(UnknownHostException e){  
	            System.out.println("unknown host!");  
	        }  
	        return null;	  
	    } 
	    
	    public static String getHostIp(InetAddress netAddress){  
	        if(null == netAddress){  
	            return "";  
	        }  
	        String ip = netAddress.getHostAddress(); //get the ip address  
	        return ip;  
	    } 
	    
	    public static String getHostIp(){
	    	InetAddress netAddress = getInetAddress();  
	       return getHostIp(netAddress);
	    }
}
