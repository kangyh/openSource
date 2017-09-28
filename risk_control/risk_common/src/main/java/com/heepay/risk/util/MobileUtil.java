package com.heepay.risk.util;

import com.heepay.common.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MobileUtil {


    public  static String GetMobileLocation(String mobileNumber) throws Exception {
        HttpClient client=null;
        GetMethod method=null;
        NameValuePair mobileParameter=null;
        NameValuePair actionParameter=null;
        int httpStatusCode;

        String htmlSource=null;
        String result=null;


        try {
            client=new HttpClient();
            client.getHostConfiguration().setHost("tcc.taobao.com");
            method=new GetMethod("/cc/json/mobile_tel_segment.htm");
            mobileParameter=new NameValuePair("tel",mobileNumber);
            method.setQueryString(new NameValuePair[] { mobileParameter });
            //设置编码
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GB2312");

            client.executeMethod(method);
            httpStatusCode=method.getStatusLine().getStatusCode();
            if(httpStatusCode!=200){
                throw new Exception("网页内容获取异常！Http Status Code:"+httpStatusCode);
            }

            htmlSource=method.getResponseBodyAsString();
            if(!StringUtil.isBlank(htmlSource))
            {
                htmlSource=htmlSource.replace("__GetZoneResult_ = ","");
                JSONObject json=JSONObject.fromObject(htmlSource);
                return json.get("province").toString();
            }
        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            method.releaseConnection();
        }

        return htmlSource;


    }

    public static void main(String[] args) throws Exception {
        String str=GetMobileLocation("13120254844");
    }
}
