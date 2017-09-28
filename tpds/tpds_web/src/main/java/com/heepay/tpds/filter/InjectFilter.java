package com.heepay.tpds.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
/**
 *
 *
 * 描 述：拦截器
 *
 * 创 建 者： TianYanqing
 * 创建时间： 2017年3月6日 上午11:40:03
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
public class InjectFilter extends HttpServlet implements Filter {
    /**  描述   (@author: TianYanqing) */      
	    
	private static final long serialVersionUID = -7604933013663426098L;

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();
   // private String failPage = "/injectError.jsp";//发生注入时，跳转页面

    @SuppressWarnings("unchecked")
	@Override
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain filterchain) throws IOException, ServletException {
        //判断是否有注入攻击字符
        HttpServletRequest req = (HttpServletRequest) request;
        Map<String,String[]> params = new HashMap<String, String[]>(request.getParameterMap());
        //替换成自定义的request
        request = new ParameterNewRequest(req, params);
        
        String inj = injectInput(request);
        if (!inj.equals("")) {
            logger.warn("请求的URI为{}",req.getRequestURI());
        }
        filterchain.doFilter(request, response);
    }

    /**
     * 判断request中是否含有注入攻击字符
     *
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
	public String injectInput(ServletRequest request) {
        Enumeration e = request.getParameterNames();
        ParameterNewRequest req = (ParameterNewRequest)request;
        String attributeName;
        String attributeValues[];
        String inj = "";
        while (e.hasMoreElements()) {
            attributeName = (String) e.nextElement();
            //不对密码信息进行过滤，一般密码中可以包含特殊字符
            if (attributeName.equals("pwd") || attributeName.equals("network") || attributeName.equals("disk")
                    || attributeName.equals("cpu") || attributeName.equals("passWordOne") || attributeName.equals("passWordTwo")
                    || attributeName.equals("payWordOne") || attributeName.equals("payWordTwo") || attributeName.equals("passwordNew")
                    || attributeName.equals("passwordNew2")|| attributeName.equals("passwordNew1")|| attributeName.equals("password")
                    || attributeName.indexOf("password")>0 || attributeName.indexOf("pwd")>0) {
                continue;
            }
            attributeValues = request.getParameterValues(attributeName);
            for (int i = 0; i < attributeValues.length; i++) {
                if (attributeValues[i] == null || attributeValues[i].equals(""))
                    continue;
                inj =  injectChar(attributeValues[i]);
                if (!inj.equals("")) {
                    logger.warn("请求name为{}，数据为{}被拦截，过滤后的内容为{}",attributeName,attributeValues,inj);
                    attributeValues[i] = inj ;
                }
            }
            req.setParameterValueByName(attributeName, attributeValues);
                        
        }
        return inj;
    }

    /**
     * 判断字符串中是否含有注入攻击字符
     *
     * @param str
     * @return
     */
    public String injectChar(String str) {
        String inj_str = "\' % < > script cookie expression exec insert select delete update drop master truncate declare";
        String inj_stra[] = inj_str.split(" ");
        boolean flag =false ;
        for (int i = 0; i < inj_stra.length; i++) {
            if (str.indexOf(inj_stra[i]) >= 0) {
                str = str.replace(inj_stra[i], "*");
                flag = true ;
            }
        }
        if(flag){
        	return str;
        }
        return "";
    }
    

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        // System.out.println("----注入过滤器初始化----");
    }
}
