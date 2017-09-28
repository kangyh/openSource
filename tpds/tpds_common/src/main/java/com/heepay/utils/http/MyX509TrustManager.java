package com.heepay.utils.http;


import java.security.cert.CertificateException; 
import java.security.cert.X509Certificate; 

import javax.net.ssl.X509TrustManager; 

/**
* <p>Title: MyX509TrustManager.java</p>
* <p>Description: 实例X509TrustManager，管理使用哪一个 X509 证书来验证远端的安全套接字。决定是根据信任的证书授权、证书撤消列表、在线状态检查或其他方式做出的</p>
* <p>Company: hy</p> 
* @author W.X
* @date 2016年6月21日
*/
public class MyX509TrustManager implements X509TrustManager { 



    public MyX509TrustManager() throws Exception { 
	
    } 

    /* 
     * Delegate to the default trust manager. 
     */ 
    public void checkClientTrusted(X509Certificate[] chain, String authType) 
                throws CertificateException { 

    } 

    /* 
     * Delegate to the default trust manager. 
     */ 
    public void checkServerTrusted(X509Certificate[] chain, String authType) 
                throws CertificateException { 

    } 

    /* 
     * Merely pass this through. 
     */ 
    public X509Certificate[] getAcceptedIssuers() { 	
        return new java.security.cert.X509Certificate[0];	
    } 
}
