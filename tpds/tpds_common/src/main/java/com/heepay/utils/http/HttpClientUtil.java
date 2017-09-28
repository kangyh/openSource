package com.heepay.utils.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.heepay.common.util.JsonMapperUtil;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年2月15日 下午5:25:14
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
public class HttpClientUtil {
	
	private static final Logger logger = LogManager.getLogger();
    @SuppressWarnings("deprecation")
	public static String RequestPost(String url,Map<String,String> map)
    {
    	 
        HttpClient client = new HttpClient();
      
        PostMethod myPost = new PostMethod(url);
        String responseString = null;
        try {
          
        	String jsonStr=JsonMapperUtil.buildNonDefaultMapper().toJson(map);
          myPost.setRequestHeader("Content-Type", "application/json");
          myPost.setRequestHeader("charset", "utf-8");        
          myPost.setRequestBody(jsonStr);
          myPost.setRequestEntity(new StringRequestEntity(jsonStr, "application/json",
              "utf-8"));
          logger.info("request parameters:{}",jsonStr);
          int statusCode = client.executeMethod(myPost);
          if (statusCode == HttpStatus.SC_OK) {
            BufferedInputStream bis = new BufferedInputStream(
                myPost.getResponseBodyAsStream());
            byte[] bytes = new byte[1024];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int count = 0;
            while ((count = bis.read(bytes)) != -1) {
              bos.write(bytes, 0, count);
            }
            byte[] strByte = bos.toByteArray();
            responseString = new String(strByte, 0, strByte.length, "utf-8");
            bos.close();
            bis.close();
          }
        } catch (Exception e) {
        	 logger.error("exception:{}",e.getMessage());
        }
        myPost.releaseConnection();
        logger.info("response content:{}",responseString);
        return responseString;
    }
}

 