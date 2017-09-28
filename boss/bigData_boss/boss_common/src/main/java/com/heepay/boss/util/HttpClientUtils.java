package com.heepay.boss.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class HttpClientUtils {

	private static final Logger logger = LogManager.getLogger();
   
	public static String httpPostWithJSON(String url,String paramJSON) throws Exception {
	  CloseableHttpClient httpClient = getHttpClient();
		try {
			// 用get方法发送http请求
			HttpPost post = new HttpPost(url);
			StringEntity entity = new StringEntity(paramJSON,"utf-8");//解决中文乱码问题    
			entity.setContentEncoding("UTF-8");    
			entity.setContentType("application/json"); 
			post.setEntity(entity);
			logger.info("执行post请求:....{}" ,post.getURI());
			CloseableHttpResponse httpResponse = null;
			// 发送get请求
			httpResponse = httpClient.execute(post);
			HttpEntity entityValue = null;
			try {
				// response实体
				entityValue = httpResponse.getEntity();
			} finally {
				//httpResponse.close();
				return EntityUtils.toString(entityValue);
			}
			
		} catch (Exception e) {
			logger.error("执行1post请求 异常:....{}" ,e.getMessage());
			return null;
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (IOException e) {
				logger.error("执行2post请求 异常:....{}" ,e.getMessage());
				e.printStackTrace();
			}
		}
	}
  
	private static CloseableHttpClient getHttpClient() {
		return HttpClients.createDefault();
	}

	private static void closeHttpClient(CloseableHttpClient client) throws IOException {
		if (client != null) {
			client.close();
		}
	}

	/**
	 * 通过GET方式发起http请求
	 * @param url
	 * @return
	 */
	public static String httpGet(String url) {
		// 创建默认的httpClient实例
		CloseableHttpClient httpClient = getHttpClient();
		try {
			// 用get方法发送http请求
			HttpGet get = new HttpGet(url);
			System.out.println("执行get请求:...." + get.getURI());
			CloseableHttpResponse httpResponse = null;
			// 发送get请求
			httpResponse = httpClient.execute(get);
			try {
				// response实体
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					String result = EntityUtils.toString(entity);
					System.out.println("响应状态码:" + httpResponse.getStatusLine());
					System.out.println("-------------------------------------------------");
					System.out.println("响应内容:" + result);
					System.out.println("-------------------------------------------------");
					return result;
				}
			} finally {
				httpResponse.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				closeHttpClient(httpClient);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
