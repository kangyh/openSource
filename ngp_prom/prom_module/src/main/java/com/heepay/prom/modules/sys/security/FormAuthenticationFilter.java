/**
 *  
 */
package com.heepay.prom.modules.sys.security;

import com.heepay.common.util.StringUtils;
import com.heepay.cookie.CookieUtils;
import com.heepay.prom.common.utils.Constants;
import com.heepay.prom.common.utils.SpringContextHolder;
import com.heepay.prom.modules.sys.entity.User;
import com.heepay.prom.modules.sys.service.SystemService;
import com.heepay.prom.modules.sys.utils.DictUtils;
import com.heepay.prom.modules.sys.utils.UserUtils;
import com.heepay.redis.JedisClusterUtil;
import ocx.AESWithJCE;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 表单验证（包含验证码）过滤类
 *  
 * @version 2014-5-19
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {
    
    private SystemService systemService;
    
    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();
    
	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
	private String messageParam = DEFAULT_MESSAGE_PARAM;

	/**
     * 获取系统业务对象
     */
    public SystemService getSystemService() {
        if (systemService == null){
            systemService = SpringContextHolder.getBean(SystemService.class);
        }
        return systemService;
    }
	
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
	    //转换
	    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //取参
        String pwd = httpServletRequest.getParameter("pwd");
        String key = CookieUtils.getCookie(httpServletRequest, "prom.session.id");
        String sKey = JedisClusterUtil.getJedisCluster().get(key);
        String pwdMsg = AESWithJCE.getResult(sKey,pwd);
        request.setAttribute("password", pwdMsg);
		String username = getUsername(request);
		String password = pwdMsg;
	    if (StringUtils.isBlank(password)){
	          password = "";
	    }
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
		String captcha = getCaptcha(request);
		String manageCode = httpServletRequest.getParameter("manageCode");
		boolean mobile = isMobileLogin(request);
		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile, manageCode);
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	public String getMobileLoginParam() {
		return mobileLoginParam;
	}
	
	protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }
	
	public String getMessageParam() {
		return messageParam;
	}
	
	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}
	
	
	@Override
	protected void issueSuccessRedirect(ServletRequest request,
			ServletResponse response) throws Exception {
	    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
	    //获取sessionid
	    String key = CookieUtils.getCookie(httpServletRequest, "prom.session.id");
	    //获取控件key
        String sKey = JedisClusterUtil.getJedisCluster().get(key);
        //解密
        String network = request.getParameter("network");
        String disk = request.getParameter("disk");
        String cpu = request.getParameter("cpu");
        String networkMsg = AESWithJCE.getResult(sKey,network);
        String diskMsg = AESWithJCE.getResult(sKey,disk);
        String cpuMsg = AESWithJCE.getResult(sKey,cpu);
        String username = getUsername(request);
        User user = UserUtils.getByLoginName(username);
        //判断是否是admin,是否为后台字典配置不校验用户
		String result = DictUtils.getDictValue(UserUtils.getUser().getLoginName(),Constants.USER_NO_CHECK, null);
        if(UserUtils.getUser().isAdmin()||StringUtils.isNotBlank(result)){
            WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
        }else{
            logger.info("操作员{}登录时的登录信息:cpu-{},硬盘-{},网卡-{}",username,cpuMsg,diskMsg,networkMsg);
            if(isVerifyInfoExist(user)){
                saveVerifyInfo(networkMsg, diskMsg, cpuMsg, user);
                WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
            }else{
                if(!cpuMsg.equals(user.getCpuInfo())){
                    WebUtils.issueRedirect(request, response, "/a/loginError", null, true);
                }else{
                    saveVerifyInfo(networkMsg, diskMsg, cpuMsg, user);
                    WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
                }
            }
        }
        
//		Principal p = UserUtils.getPrincipal();
//		if (p != null && !p.isMobileLogin()){
//		}else{
//			super.issueSuccessRedirect(request, response);
//		}
	}

      
    /**     
    * @discription 保存验证信息
    * @author ly     
    * @created 2016年12月30日 下午5:57:20     
    */
    private void saveVerifyInfo(String networkMsg, String diskMsg, String cpuMsg, User user) {
        user.setCpuInfo(cpuMsg);
        user.setDiskInfo(diskMsg);
        user.setMacInfo(networkMsg);
        getSystemService().updateVerifyInfo(user);
    }
	
	/**     
    * @discription 判断验证信息是否为空
    * @author ly     
    * @created 2016年12月16日 下午5:03:30     
    * @param user
    * @return     
    */
    public boolean isVerifyInfoExist(User user){
        if(StringUtils.isBlank(user.getCpuInfo()) && StringUtils.isBlank(user.getDiskInfo()) 
                && StringUtils.isBlank(user.getMacInfo())){
            return true;
        }
        return false;
    }

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request, ServletResponse response) {
		String className = e.getClass().getName(), message = "";
		if (IncorrectCredentialsException.class.getName().equals(className)
				|| UnknownAccountException.class.getName().equals(className)){
			message = "用户或密码错误, 请重试.";
		}
		else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
			message = StringUtils.replace(e.getMessage(), "msg:", "");
		}
		else{
			message = "系统出现点问题，请稍后再试！";
			e.printStackTrace(); // 输出到控制台
		}
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);
        return true;
	}
	
}