package com.heepay.prom.modules.prom.utils; /**
*   Copyright © since 2008. All Rights Reserved 
*   汇元银通（北京）在线支付技术有限公司   www.heepay.com    
*/
    

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**          
* 
* 描    述：httpsClient工具类
*
* 创 建 者： 刘栋  
* 创建时间： 2016年11月1日 上午10:55:15 
* 创建描述：封装httpsclient常用方法
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

public class HttpsUtil {
	
	private static Logger log = LogManager.getLogger();

	private static RequestConfig requestConfig = RequestConfig.custom()  
            .setSocketTimeout(15000)
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
            .build();
	
	public static void main(String args[]) throws ConfigurationException{
		Configuration c = new PropertiesConfiguration("https-client.properties");
		
		log.info(c.getString("https.host.address") + c.getString("api.quickpay.suffix"));
	}
	
	public static String sendHttpsRequestWithParam(String url, List<NameValuePair> nvPairs) throws Exception{
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient httpClient = HttpClients.createDefault();
//		httpPost.setConfig(requestConfig);
		httpPost.setEntity(new UrlEncodedFormEntity(nvPairs, "UTF-8"));
		CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
		String retStr = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		
		httpResponse.close();
		httpClient.close();
		
		
		return retStr;
	}
	
}
