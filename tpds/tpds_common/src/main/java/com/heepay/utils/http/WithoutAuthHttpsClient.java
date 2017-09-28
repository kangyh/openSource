package com.heepay.utils.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
/*import com.heepay.gateway.server.util.ProcessProperties;*/
import org.apache.http.NameValuePair;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.heepay.tpds.vo.SignkeyCommon;


/**
* 
* 描    述：绕过证书认证的https请求
*
* 创 建 者： W.X  
* 创建时间： 2016年9月28日 下午7:50:28 
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
public class WithoutAuthHttpsClient {
	
	/**
	 * 定义全局日志
	 */
	private final Logger logger = LogManager
			.getLogger(WithoutAuthHttpsClient.class);
	
	//将最大连接数增加到200
	private  int maxTotalConnect=200;
		
	//将每个路由基础的连接数增加到20
	private  int defaultMaxPerRoute=20;
		
	//请求时间更改为30秒（连接一个url的连接等待时间）
	private  int requestTimeout=60000; //应银行要求，30秒更改为 60秒
		
	//连接超时时间为10秒（获取response的返回等待时间）
	private  int timeout = 60000; //应银行要求，30秒更改为 60秒
	
	private  SSLConnectionSocketFactory socketFactory = null;
	
	/**
	 * 请求配置
	 */
	private  RequestConfig requestConfig = null;
	
	private  CloseableHttpClient client = null;
	@Resource(name = "signkeyCommon")
	private SignkeyCommon signkeyCommon;
	
	private  PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
	
	private WithoutAuthHttpsClient(){
		
	}
	
	  
	/**     
	* @discription 初始化
	* @author W.X       
	* @created 2016年9月29日 上午8:48:59          
	*/
	public void init(){
		final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
			@Override
			public boolean verify(String s, SSLSession sslSession) {
				return true;
			}
		};
		System.setProperty("jsse.enableSNIExtension", "false");
		socketFactory= new SSLConnectionSocketFactory(createIgnoreVerifySSL(), hostnameVerifier);
		//初始化RequestConfig 创建可用的scheme
		requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).
				setExpectContinueEnabled(true).setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM,AuthSchemes.DIGEST)).
				setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC)).setConnectionRequestTimeout(requestTimeout)
				.setConnectTimeout(timeout).build();
		//创建和连接套接字的连接工厂
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().
				register("http", PlainConnectionSocketFactory.INSTANCE).register("https", socketFactory).build();
		//初始化连接管理
		cm =  new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		cm.setDefaultMaxPerRoute(defaultMaxPerRoute);
		cm.setMaxTotal(maxTotalConnect);
		//创建httpclient
		client =  HttpClients.custom().setConnectionManager(cm)
				.setDefaultRequestConfig(requestConfig).build();
		
	}
	
	
	
	/** 
	 * 绕过验证 
	 *   
	 * @return 
	 * @throws Exception 
	 */  
	public SSLContext createIgnoreVerifySSL(){
		final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};
	    SSLContext sslContext = null;
		try {
			sslContext=SSLContext.getInstance("TLS");
		    // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
			X509TrustManager tm = new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return _AcceptedIssuers;
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain,
											   String authType) throws CertificateException {
				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain,
											   String authType) throws CertificateException {
				}
			};
			
			sslContext.init(null,  new TrustManager[] { tm }, new SecureRandom());
		} catch (Exception e) {
			logger.error("https绕过验证失败!",e);
		} 
		return sslContext;
	}  
	
	  
	
	  
	/**     
	* @discription
	* @author W.X       
	* @created 2016年9月29日 上午8:53:31     
	* @param url
	* @param params
	* @param charset
	* @return
	* @throws Exception     
	*/
	public String send(String url, Map<String,String> params, String charset) throws Exception{
		String realreqCharset = (charset == null) ? "UTF-8" : charset;
		logger.info("请求参数:" + params);
		HttpPost post = new HttpPost(url);
		// url格式编码
		UrlEncodedFormEntity uefEntity = null;
		// 创建参数列表
		List<NameValuePair> list = new ArrayList<NameValuePair>();

		if (null != params) {
			for (Map.Entry<String, String> entity : params.entrySet()) {
				list.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
			}
			uefEntity = new UrlEncodedFormEntity(list, realreqCharset);
			post.setEntity(uefEntity);
		}

		System.out.println("POST 请求...." + post.getURI());
		CloseableHttpResponse response=null;
		String content="";
		try {
			response = client.execute(post);
			content= EntityUtils.toString(response.getEntity(),realreqCharset);
			logger.info("返回参数:" + content);	
		} catch (Exception e) {
			logger.error("执行post请求异常！",e);
			throw e;
		}finally{
			post.abort();
		}	
		return content;	
	}

	/**
	 * @discription
	 * @author W.X
	 * @created 2016年9月29日 上午8:53:31
	 * @param url
	 * @param params
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public String send(String url,String params,String charset) throws Exception{
		String realreqCharset = (charset == null) ? "UTF-8" : charset;

		//String keystr = signkeyCommon.getBank_Key();  //统一进行 AES 加密
		//String encryptParams = KKAES2.encrypt(params, signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV());
		logger.info("请求参数:" + params);
		HttpPost post = new HttpPost(url);

		StringEntity entity = new StringEntity(params,"application/json",
				realreqCharset);
		post.setEntity(entity);
		CloseableHttpResponse response=null;
		String content="";
		try {
			response = client.execute(post);
			content= EntityUtils.toString(response.getEntity(),realreqCharset);
			logger.info("返回参数:" + content);
		} catch (Exception e) {
			logger.error("执行post请求异常！",e);
			throw e;
		}
		return content;
	}
	/**
	 * 不带AES参数加密的传输
	 * @param url
	 * @param params
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public String sendWithoutAES(String url,String params,String charset) throws Exception{
		String realreqCharset = (charset == null) ? "UTF-8" : charset;

		/*String keystr = signkeyCommon.getBank_Key();  //统一进行 AES 加密
		String encryptParams = KKAES2.encrypt(params, signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV());
		logger.info("请求参数:" + encryptParams);*/
		HttpPost post = new HttpPost(url);

		StringEntity entity = new StringEntity(params,"application/json",
				realreqCharset);
		post.setEntity(entity);
		CloseableHttpResponse response=null;
		String content="";
		try {
			response = client.execute(post);
			content= EntityUtils.toString(response.getEntity(),realreqCharset);
			logger.info("返回参数:" + content);
		} catch (Exception e) {
			logger.error("执行post请求异常！",e);
			throw e;
		}
		return content;
	}
	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String sendGet(String url, String params)throws Exception {
		//String realreqCharset = (charset == null) ? "UTF-8" : charset;

		//String keystr = signkeyCommon.getBank_Key();  //统一进行 AES 加密
		//String encryptParams = KKAES2.encrypt(params, signkeyCommon.getBank_Key(), signkeyCommon.getBank_IV());
		logger.info("请求参数:" + params);
		HttpGet get = new HttpGet(url);

		/*StringEntity entity = new StringEntity(params,"application/json",
				realreqCharset);*/
		//get.setEntity(entity);
		CloseableHttpResponse response=null;
		String content="";
		try {
			response = client.execute(get);
			content= EntityUtils.toString(response.getEntity(),"UTF-8");
			logger.info("返回参数:" + content);
		} catch (Exception e) {
			logger.error("执行post请求异常！",e);
			throw e;
		}
		return content;
	}
	
	@SuppressWarnings("finally")
	public String executeGet(String url) throws Exception {  
        BufferedReader in = null;  
  
        String content = null;  
        try {  
            // 定义HttpClient  
//            HttpClient client = new DefaultHttpClient();  
            // 实例化HTTP方法  
            HttpGet request = new HttpGet();  
            request.setURI(new URI(url));  
            HttpResponse response = client.execute(request);  
  
            in = new BufferedReader(new InputStreamReader(response.getEntity()  
                    .getContent()));  
            StringBuffer sb = new StringBuffer("");  
            String line = "";  
            String NL = System.getProperty("line.separator");  
            while ((line = in.readLine()) != null) {  
                sb.append(line + NL);  
            }  
            in.close();  
            content = sb.toString();  
        }  catch (Exception e) {
        	logger.error("执行get请求异常！{}", url, e);
			throw e;
        }
        finally {  
            if (in != null) {  
                try {  
                    in.close();// 最后要关闭BufferedReader  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
            
        }
        return content;  
    }  
	
	
	public int getMaxTotalConnect() {
		return maxTotalConnect;
	}


	public void setMaxTotalConnect(int maxTotalConnect) {
		this.maxTotalConnect = maxTotalConnect;
	}


	public int getDefaultMaxPerRoute() {
		return defaultMaxPerRoute;
	}


	public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}


	public int getRequestTimeout() {
		return requestTimeout;
	}


	public void setRequestTimeout(int requestTimeout) {
		this.requestTimeout = requestTimeout;
	}


	public int getTimeout() {
		return timeout;
	}


	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}


	public RequestConfig getRequestConfig() {
		return requestConfig;
	}


	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}
	
	

}
