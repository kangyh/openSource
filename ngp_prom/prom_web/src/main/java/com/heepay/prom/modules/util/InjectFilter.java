/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.util;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 描述：
 * <p>
 * 创建者  B.HJ
 * 创建时间 2016-12-09-14:34
 * 创建描述：
 * <p>
 * 审核者：
 * 审核时间：
 * 审核描述：
 * <p>
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
public class InjectFilter extends HttpServlet implements Filter {
    /**
     *
     */
    private static final long serialVersionUID = 5286703103846683570L;
    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();
    private String failPage = "/prom_web/a/logout";//发生注入时，跳转页面

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain filterchain) throws IOException, ServletException {
        //判断是否有注入攻击字符
        HttpServletRequest req = (HttpServletRequest) request;
        String inj = injectInput(req);
        if (!inj.equals("")) {
            logger.warn("请求的URI为{}",req.getRequestURI());
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect(failPage);
            return;
        } else {
            // 传递控制到下一个过滤器
            filterchain.doFilter(request, response);
        }
    }

    /**
     * 判断request中是否含有注入攻击字符
     *
     * @param request
     * @return
     */
    public String injectInput(ServletRequest request) {
        Enumeration e = request.getParameterNames();
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
                    || attributeName.startsWith("columnList") || attributeName.equals("productName") || attributeName.equals("name")
                    || attributeName.equals("macInfo") || attributeName.equals("remarks") || attributeName.equals("checkedstr")
                    || attributeName.equals("description") || attributeName.equals("label") || attributeName.equals("ruleDescription")
                    || attributeName.equals("bankCard") || attributeName.equals("bankAccount")) {
                continue;
            }
            attributeValues = request.getParameterValues(attributeName);
            for (int i = 0; i < attributeValues.length; i++) {
                if (attributeValues[i] == null || attributeValues[i].equals(""))
                    continue;
                inj = injectChar(attributeValues[i]);
                if (!inj.equals("")) {
                    logger.warn("请求name为{}，数据为{}被拦截，触发拦截的内容为{}",attributeName,attributeValues,inj);
                    return inj;
                }
            }
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
        String inj_str = ") \' * % < > & ; script cookie expression";
        String inj_stra[] = inj_str.split(" ");
        for (int i = 0; i < inj_stra.length; i++) {
            if (str.indexOf(inj_stra[i]) >= 0) {
                return inj_stra[i];
            }
        }
        return "";
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
        // System.out.println("----注入过滤器初始化----");
    }
}
