/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.util;

import java.io.IOException;
import java.util.List;

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

import com.heepay.common.util.IpUtil;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.prom.common.utils.ListEnum;


/**          
* 
* 描    述： ip拦截
*
* 创 建 者： ly
* 创建时间： 2016年12月17日 上午10:42:52 
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
    
public class IpFilter extends HttpServlet implements Filter {
    /**
     *
     */
    private static final long serialVersionUID = 5286703103846683570L;
    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();
    private String failPage = "/prom_web/a/loginError";//ip不对重定向地址
    
    private static final String LOCALLONGIP = "0:0:0:0:0:0:0:1";//本地长ip
    
    private static final String LOCALSHORTIP = "127.0.0.1";//本地短ip
    
    private static PropertiesLoader loader = new PropertiesLoader("fastDFS.properties");
    
    private List<String> ips;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain filterchain) throws IOException, ServletException {
        //转换
        HttpServletRequest req = (HttpServletRequest) request;
        //判断是否是错误页面
        if(req.getRequestURI().indexOf("loginError")<0){
            boolean flag = ipIsTrue(req);
            if (!flag) {
                logger.warn("请求的ip为{},请求的URI为{}",IpUtil.getIpAddr(req),req.getRequestURI());
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect(failPage);
                return;
            } 
        }
        // 传递控制到下一个过滤器
        filterchain.doFilter(request, response);
    }

    /**
     * 判断ip是否正确
     *
     * @param request
     * @return
     */
    public boolean ipIsTrue(HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        if(ip.equals(LOCALLONGIP) || ip.equals(LOCALSHORTIP)){
            return true;
        }
        if(null != ips && !ips.isEmpty()){
            boolean flag = false;
            for(String allowIp : ips){
                flag = flag || ip.startsWith(allowIp);
            }
            return flag;
        }
        logger.warn("操作员登录不正确ip地址:{}",ip);
        return false;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String ip = loader.getProperty("ip");
        ips = ListEnum.stringToList(ip);
    }
}
