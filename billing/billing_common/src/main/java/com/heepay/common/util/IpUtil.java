/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年3月10日下午5:19:28
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
package com.heepay.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年3月10日下午5:19:28
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
