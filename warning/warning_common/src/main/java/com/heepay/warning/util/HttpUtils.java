package com.heepay.warning.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.heepay.common.util.Constants;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年5月19日 下午3:53:06
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
public class HttpUtils {
	private static final Logger logger = LogManager.getLogger();
	private static CloseableHttpClient getHttpClient() {
		return HttpClients.createDefault();
	}
	/**
	 * 
	* @description POST方式发起http请求   编码格式GB2312
	* @author 王亚洪       
	* @created 2016年12月27日 上午11:46:10     
	* @param url
	* @param paramMap
	* @return
	 */
	 public static int requestByPostMethodForWeiXin(String url, Map<String, String> paramMap) {
	    CloseableHttpClient httpClient = getHttpClient();
	    int result = Constants.Http.RESULT_SUCCESS;
	    try {
	      HttpPost post = new HttpPost(url);

	      // url格式编码
	      UrlEncodedFormEntity uefEntity = null;

	      // 创建参数列表
	      List<NameValuePair> list = new ArrayList<NameValuePair>();

	      if (null != paramMap) {
	        for (Map.Entry<String, String> entity : paramMap.entrySet()) {
	          list.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
	        }
	        uefEntity = new UrlEncodedFormEntity(list, "GB2312");
	        post.setEntity(uefEntity);
	      }

	      logger.info("POST 请求...." + post.getURI());

	      // 执行请求
	      CloseableHttpResponse httpResponse = httpClient.execute(post);
	      StatusLine statusLine = httpResponse.getStatusLine();
	      System.out.println(statusLine);

	      try {
	        HttpEntity entity = httpResponse.getEntity();
	        if (entity != null) {
	        	logger.info("--------------------------------------");
	        	logger.info("Response content: " + EntityUtils.toString(entity, "UTF-8"));
	        	logger.info("--------------------------------------");
	        }
	      } finally {
	        httpResponse.close();
	      }
	      
	      result = Constants.Http.RESULT_SUCCESS;
	    } catch (Exception e) {
	    	logger.info(e.getMessage());
	      result = Constants.Http.RESULT_ERROR;
	    } finally {
	      try {
	        closeHttpClient(httpClient);
	      } catch (Exception e) {
	        e.printStackTrace();
	      }
	    }
	    
	    return result;
	  }
	 public static int requestByGetMethod(String url) {
			
			// 创建默认的httpClient实例
			CloseableHttpClient httpClient = getHttpClient();
			try {
				// 用get方法发送http请求
				HttpGet get = new HttpGet(url);
				logger.info("执行get请求:....{}" ,get.getURI());
				CloseableHttpResponse httpResponse = null;
				// 发送get请求
				httpResponse = httpClient.execute(get);
				try {
					// response实体
					HttpEntity entity = httpResponse.getEntity();
					
					if (null != entity) {
						logger.info("--------------------------------------");
			        	logger.info("Response content: " + EntityUtils.toString(entity, "UTF-8"));
			        	logger.info("--------------------------------------");
						return  Constants.Http.RESULT_SUCCESS;
					}
				} finally {
					httpResponse.close();
				}
				
			} catch (Exception e) {
				logger.error("执行get请求 异常:....{}" ,e.getMessage());
				return  Constants.Http.RESULT_ERROR;
			} finally {
				try {
					closeHttpClient(httpClient);
				} catch (IOException e) {
					logger.error("执行get请求 异常:....{}" ,e.getMessage());
					e.printStackTrace();
				}
			}		
			return  Constants.Http.RESULT_ERROR;

		}

	 private static void closeHttpClient(CloseableHttpClient client) throws IOException {
			if (client != null) {
				client.close();
			}
		}
}

 