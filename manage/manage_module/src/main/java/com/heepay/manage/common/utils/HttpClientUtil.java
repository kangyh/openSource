/**
 * 
 */
package com.heepay.manage.common.utils;

import java.io.IOException;  
import java.net.URL;  
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;  
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.config.RequestConfig;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.CloseableHttpResponse;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.conn.ssl.DefaultHostnameVerifier;  
import org.apache.http.conn.util.PublicSuffixMatcher;  
import org.apache.http.conn.util.PublicSuffixMatcherLoader;  
import org.apache.http.entity.StringEntity;  
import org.apache.http.impl.client.CloseableHttpClient;  
import org.apache.http.impl.client.HttpClients;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;  

public class HttpClientUtil {  
	private static final Logger logger = LogManager.getLogger("HttpClientUtil");
    private RequestConfig requestConfig = RequestConfig.custom()  
            .setSocketTimeout(30000)  
            .setConnectTimeout(30000)  
            .setConnectionRequestTimeout(30000)  
            .build();  
      
    private static HttpClientUtil instance = null;  
    private HttpClientUtil(){}  
    public static HttpClientUtil getInstance(){  
        if (instance == null) {  
            instance = new HttpClientUtil();  
        }  
        return instance;  
    }  
     public static void main(String[] args) throws Exception {
    	 String y = "[{\"cusType\":\"01\",\"cusNature\":\"01\",\"regName\":\"简称\",\"cusName\":\"简称\",\"cusNameEn\":\"JC\","
    	 		+ "\"docType\":\"01\",\"docCode\":\"130625199603650365\",\"cusCode\":\"123456\",\"induType\":\"01,02\","
    	 		+ "\"bankNo\":\"6225758333100088\",\"openBank\":\"1411\",\"regAddrProv\":\"111111\",\"regAddrDetail\":\"北京\","
    	 		+ "\"addrProv\":\"111111\",\"addrDetail\":\"北京市海淀区\",\"url\":\"URL\",\"serverIp\":\"1.1.1.1\",\"icp\":\"ICP\","
    	 		+ "\"contName\":\"商户联系人\",\"contPhone\":\"15034097562\",\"cusEmail\":\"123456@QQ.COM\",\"occurarea\":\"北京地区\","
    	 		+ "\"networkType\":\"01\",\"status\":\"01\",\"endTime\":\"2017-12-03\",\"riskStatus\":\"01\",\"openType\":\"01\","
    	 		+ "\"chargeType\":\"01\",\"accountType\":\"01\",\"expandType\":\"01\",\"outServiceName\":\"外包服务机构名称\","
    	 		+ "\"outServiceCardType\":\"01\",\"outServiceCardCode\":\"130625199603650365\",\"outServiceLegCardType\":\"01\","
    	 		+ "\"outServiceLegCardCode\":\"130625199603650365\",\"orgId\":\"Z2011611000017\",\"repDate\":\"2016-07-15 10:15:44\","
    	 		+ "\"repType\":\"03\",\"repPerson\":\"admin\",\"startTime\":\"2016-12-03\"},{\"cusType\":\"01\",\"cusNature\":\"01\","
    	 		+ "\"regName\":\"简称\",\"cusName\":\"简称\",\"cusNameEn\":\"JC\",\"docType\":\"01\",\"docCode\":\"130625199603650365\","
    	 		+ "\"cusCode\":\"123456\",\"induType\":\"01,02\",\"bankNo\":\"6225758333100088\",\"openBank\":\"1411\",\"regAddrProv\":\"111111\","
    	 		+ "\"regAddrDetail\":\"北京\",\"addrProv\":\"111111\",\"addrDetail\":\"北京市海淀区\",\"url\":\"URL\",\"serverIp\":\"1.1.1.1\",\"icp\":\"ICP\","
    	 		+ "\"contName\":\"商户联系人\",\"contPhone\":\"15034097562\",\"cusEmail\":\"123456@QQ.COM\",\"occurarea\":\"北京地区\","
    	 		+ "\"networkType\":\"01\",\"status\":\"01\",\"endTime\":\"2017-12-03\",\"riskStatus\":\"01\",\"openType\":\"01\",\"chargeType\":\"01\","
    	 		+ "\"accountType\":\"01\",\"expandType\":\"01\",\"outServiceName\":\"外包服务机构名称\",\"outServiceCardType\":\"01\","
    	 		+ "\"outServiceCardCode\":\"130625199603650365\",\"outServiceLegCardType\":\"01\",\"outServiceLegCardCode\":\"130625199603650365\","
    	 		+ "\"orgId\":\"Z2011611000017\",\"repDate\":\"2016-07-15 10:15:44\",\"repType\":\"03\",\"repPerson\":\"admin\",\"startTime\":\"2016-12-03\"}]";
    	 System.out.println(java.net.URLEncoder.encode(y, "UTF-8"));
    	 
    	 //regAddrProv 
    	 //addrProv  
    	 
    	 
    	 String z = "[{\"accountType\":\"01\",\"addrDetail\":\"海淀\",\"addrProv\":\"111111\",\"bankNo\":\"1323123123\",\"chargeType\":\"01\",\"contName\":\"风行\","
    	 		+ "\"contPhone\":\"13731762889\",\"cusCode\":\"PERSON022\",\"cusEmail\":\"yaho@qq.com\",\"cusName\":\"张三\",\"cusNameEn\":\"zhangsan\","
    	 		+ "\"cusNature\":\"01\",\"cusType\":\"01\",\"docCode\":\"130684198408157014\",\"docType\":\"01\",\"endTime\":\"2017-03-06\",\"expandType\":\"01\","
    	 		+ "\"icp\":\"234234\",\"induType\":\"1\",\"networkType\":\"01\",\"occurarea\":\"东北\",\"openBank\":\"建行\",\"openType\":\"01\",\"orgId\":\"010101\","
    	 		+ "\"outServiceCardCode\":\"01\",\"outServiceCardType\":\"01\",\"outServiceLegCardCode\":\"01\",\"outServiceLegCardType\":\"01\","
    	 		+ "\"outServiceName\":\"测试名称1\",\"regAddrDetail\":\"海淀\",\"regAddrProv\":\"111111\",\"regName\":\"张三\",\"repDate\":\"2017-03-10 12:12:12\","
    	 		+ "\"repPerson\":\"ngp\",\"repType\":\"03\",\"riskStatus\":\"01\",\"serverIp\":\"192.168.0.0\",\"startTime\":\"2017-03-06\",\"status\":\"01\","
    	 		+ "\"url\":\"www.baidu.com\"},{\"accountType\":\"01\",\"addrDetail\":\"海淀\",\"addrProv\":\"111111\",\"bankNo\":\"5675675675\","
    	 		+ "\"chargeType\":\"01\",\"contName\":\"武士\",\"contPhone\":\"13731762889\",\"cusCode\":\"PERSON033\",\"cusEmail\":\"yaho@qq.com\","
    	 		+ "\"cusName\":\"李四\",\"cusNameEn\":\"lisi\",\"cusNature\":\"01\",\"cusType\":\"01\",\"docCode\":\"13567898567\",\"docType\":\"01\","
    	 		+ "\"endTime\":\"2017-03-06\",\"expandType\":\"01\",\"icp\":\"234234\",\"induType\":\"1\",\"networkType\":\"01\",\"occurarea\":\"河北\","
    	 		+ "\"openBank\":\"招行\",\"openType\":\"01\",\"orgId\":\"010101\",\"outServiceCardCode\":\"01\",\"outServiceCardType\":\"01\",\"outServiceLegCardCode\":\"01\","
    	 		+ "\"outServiceLegCardType\":\"01\",\"outServiceName\":\"测试名称2\",\"regAddrDetail\":\"海淀\",\"regAddrProv\":\"111111\",\"regName\":\"李四\","
    	 		+ "\"repDate\":\"2017-03-10 12:12:12\",\"repPerson\":\"ngp\",\"repType\":\"03\",\"riskStatus\":\"01\",\"serverIp\":\"192.168.0.0\",\"startTime\":\"2017-03-06\",\"status\":\"01\",\"url\":\"www.baidu.com\"}]";
    	 HttpClientUtil.getInstance().sendJsonHttpPost("http://192.168.4.113:8080/contributing/merReport",z);
     }
    /** 
     * 发送 post请求 
     * @param httpUrl 地址 
     */  
    public String sendHttpPost(String httpUrl) {  
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost    
        return sendHttpPost(httpPost);  
    }  
    /** 
     * 发送 post请求 
     * @param httpUrl 地址 
     * @param params 参数(Json格式) 
     */  
    public String sendJsonHttpPost(String httpUrl, String params) {  
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost    
        try {  
            //设置参数  
            StringEntity stringEntity = new StringEntity(params, "UTF-8");  
            stringEntity.setContentType("text/json");  
            httpPost.setEntity(stringEntity);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return sendHttpPost(httpPost);  
    }  
    /** 
     * 发送 post请求 
     * @param httpUrl 地址 
     * @param params 参数(格式:key1=value1&key2=value2) 
     */  
    public String sendHttpPost(String httpUrl, String params) {  
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost    
        try {  
            //设置参数  
            StringEntity stringEntity = new StringEntity(params, "UTF-8");  
            stringEntity.setContentType("application/x-www-form-urlencoded");  
            httpPost.setEntity(stringEntity);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return sendHttpPost(httpPost);  
    }  
      
    /** 
     * 发送 post请求 
     * @param httpUrl 地址 
     * @param maps 参数 
     */  
    public String sendHttpPost(String httpUrl, Map<String, String> maps) {  
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost    
        // 创建参数队列    
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
        for (String key : maps.keySet()) {  
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));  
        }  
        try {  
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return sendHttpPost(httpPost);  
    }  
      
    /** 
     * 发送Post请求 
     * @param httpPost 
     * @return 
     */  
    private String sendHttpPost(HttpPost httpPost) {  
        CloseableHttpClient httpClient = null;  
        CloseableHttpResponse response = null;  
        HttpEntity entity = null;  
        String responseContent = null;  
        try {  
            // 创建默认的httpClient实例.  
            httpClient = HttpClients.createDefault();  
            httpPost.setConfig(requestConfig);  
            // 执行请求  
            response = httpClient.execute(httpPost);  
            entity = response.getEntity();  
            responseContent = EntityUtils.toString(entity, "UTF-8");  
            logger.info("请求结果："+responseContent);
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                // 关闭连接,释放资源  
                if (response != null) {  
                    response.close();  
                }  
                if (httpClient != null) {  
                    httpClient.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return responseContent;  
    }  
    /** 
     * 发送Post请求 并且获取响应头信息
     * @param httpPost 
     * @return 
     */  
    public Map<String ,Object> sendJsonHttpPostWithHeader(String httpUrl, String params) {  
   	 	HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost    
        StringEntity stringEntity = new StringEntity(params, "UTF-8");  
        stringEntity.setContentType("text/json");  
        httpPost.setEntity(stringEntity);  
        CloseableHttpClient httpClient = null;  
        CloseableHttpResponse response = null;  
        HttpEntity entity = null;  
        String responseContent = null;
        Header[] responseHeaders = null;
        Map<String ,Object> map = null;
        map = new HashMap<String,Object>();
        try {  
            // 创建默认的httpClient实例.  
            httpClient = HttpClients.createDefault();  
            httpPost.setConfig(requestConfig);  
            // 执行请求  
            response = httpClient.execute(httpPost);  
            entity = response.getEntity();  
            responseContent = EntityUtils.toString(entity, "UTF-8");  
            responseHeaders = response.getHeaders("identification");
            map.put("responseContent", responseContent);
            map.put("responseHeaders", responseHeaders);
            logger.info("请求结果："+responseContent);
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                // 关闭连接,释放资源  
                if (response != null) {  
                    response.close();  
                }  
                if (httpClient != null) {  
                    httpClient.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return map;  
    }  
    /** 
     * 发送 get请求 
     * @param httpUrl 
     */  
    public String sendHttpGet(String httpUrl) {  
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求  
        return sendHttpGet(httpGet);  
    }  
      
    /** 
     * 发送 get请求Https 
     * @param httpUrl 
     */  
    public String sendHttpsGet(String httpUrl) {  
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求  
        return sendHttpsGet(httpGet);  
    }  
      
    /** 
     * 发送Get请求 
     * @param httpPost 
     * @return 
     */  
    private String sendHttpGet(HttpGet httpGet) {  
        CloseableHttpClient httpClient = null;  
        CloseableHttpResponse response = null;  
        HttpEntity entity = null;  
        String responseContent = null;  
        try {  
            // 创建默认的httpClient实例.  
            httpClient = HttpClients.createDefault();  
            httpGet.setConfig(requestConfig);  
            // 执行请求  
            response = httpClient.execute(httpGet);  
            entity = response.getEntity();  
            responseContent = EntityUtils.toString(entity, "UTF-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                // 关闭连接,释放资源  
                if (response != null) {  
                    response.close();  
                }  
                if (httpClient != null) {  
                    httpClient.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return responseContent;  
    }  
      
    /** 
     * 发送Get请求Https 
     * @param httpPost 
     * @return 
     */  
    private String sendHttpsGet(HttpGet httpGet) {  
        CloseableHttpClient httpClient = null;  
        CloseableHttpResponse response = null;  
        HttpEntity entity = null;  
        String responseContent = null;  
        try {  
            // 创建默认的httpClient实例.  
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(httpGet.getURI().toString()));  
            DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);  
            httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();  
            httpGet.setConfig(requestConfig);  
            // 执行请求  
            response = httpClient.execute(httpGet);  
            entity = response.getEntity();  
            responseContent = EntityUtils.toString(entity, "UTF-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                // 关闭连接,释放资源  
                if (response != null) {  
                    response.close();  
                }  
                if (httpClient != null) {  
                    httpClient.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return responseContent;  
    }  
}
