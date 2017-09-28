package com.heepay.tpds.resource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.springframework.beans.factory.annotation.Autowired;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.rpc.client.PasswordTransClient;
import com.heepay.tpds.vo.ServerBankInfo;

/**
 * 
 * 
 * 描    述：客户设置交易密码、客户修改交易密码、客户重置交易密码、客户密码校验
 *
 * 创 建 者：xuangang
 * 创建时间： 2017年2月9日 下午3:01:36
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
public class PasswordResource  extends BaseServerResource{

	private static final Logger logger = LogManager.getLogger();
	@Autowired
	PasswordTransClient passwordTransClient;
	
	@Resource(name = "serverBankInfo")
	ServerBankInfo serverBankInfo;
	
	@Get
	public Representation getMerchantInfo_get(Representation entity) {		
		Form form = this.getRequest().getResourceRef().getQueryAsForm() ;	//获取查询参数

		String routeName = getRequestAttributes().get("routeName").toString();
		
		try{
			Map map = form.getValuesMap();
			String json = JsonMapperUtil.nonDefaultMapper().toJson(map);
			
			form.removeFirst("backURL");    //删除原backurl
			
			String path = serverBankInfo.getBankHostPasswordVerifyUrl();	//宜宾商行地址
			
			if(routeName.toUpperCase().equals("PASSWORDSETTING")) //客户设置交易密码
			{	
				logger.info("客户设置交易密码， {}", json);				
				String reVal = passwordTransClient.passwordSetting("", json);  //
				
				if("0".equals(reVal)){					
					return new StringRepresentation("业务流水号重复");			
					
				}else if("1".equals(reVal)){
					path = serverBankInfo.getBankHostPasswordSettingUrl();
					//重定向到宜宾商行
					reRedirect(path,"", form.getQueryString()+"&backURL="+serverBankInfo.getBackURL());	
					return new StringRepresentation("");
				}				
				return null;
			}else if(routeName.toUpperCase().equals("PASSWORDMODIFY")){   //客户修改交易密码	
				logger.info("客户修改交易密码， {}",json);
				
				String reVal = passwordTransClient.passwordModify("", json);  //				
				
				if("0".equals(reVal)){					
					return new StringRepresentation("业务流水号重复");			
					
				}else if("1".equals(reVal)){
					path = serverBankInfo.getBankHostPasswordModifyUrl();
					//重定向到宜宾商行
					reRedirect(path,"", form.getQueryString()+"&backURL="+serverBankInfo.getBackURL());		
					return new StringRepresentation("成功");
				}					
				return null;
			}else if(routeName.toUpperCase().equals("PASSWORDRESETTING")){   //客户重置交易密码
				logger.info("客户重置交易密码， {}",json);				
				
				String reVal = passwordTransClient.passwordResetting("", json);  //				
				
				if("0".equals(reVal)){					
					return new StringRepresentation("业务流水号重复");			
					
				}else if("1".equals(reVal)){
					path = serverBankInfo.getBankHostPasswordResettingUrl();
					//重定向到宜宾商行
					reRedirect(path,"", form.getQueryString()+"&backURL="+serverBankInfo.getBackURL());		
					return new StringRepresentation("成功");
				}					
				return null;
			}else if(routeName.toUpperCase().equals("PASSWORDVERIFY"))	{    //客户密码校验
				logger.info("客户密码校验开始， {}", json);							
				
				String reVal = passwordTransClient.passwordVerify("", json);  //
				
				if("0".equals(reVal)){					
					return new StringRepresentation("业务流水号重复");			
					
				}else if("1".equals(reVal)){
					path = serverBankInfo.getBankHostPasswordVerifyUrl();
					//重定向到宜宾商行
					reRedirect(path,"", form.getQueryString()+"&backURL="+serverBankInfo.getBackURL());	
					return new StringRepresentation("成功");
				}
								
				return null;
				
			}else if(routeName.toUpperCase().equals("PASSWORDVERIFYBACK")){    //交易密码校验，银行回调
				logger.info("银行回调开始， {}", json);
				String p2pPath = passwordTransClient.passwordVerifyBack("", json);  				
				//重定向到p2p
				//如果玖财通backURL跟参数，
				if(p2pPath != null && !"".equals(p2pPath) && (p2pPath.indexOf("?") > 0)){
					reRedirect(p2pPath,"&",form.getQueryString());
				}else{
					reRedirect(p2pPath,"",form.getQueryString());
				}
				
				return null;
			}else if(routeName.toUpperCase().equals("AUTHCODEVERIFY")){    //校验验证码
				reRedirect("","", form.getQueryString());				
				return null;
			}
			
		}catch(Exception e){
			logger.error("密码操作失败：{}", entity, e);
		}			

		return new StringRepresentation("");
	}
	
	
	/**
	 * 密码操作重定向
	 */
	private void reRedirect(String path,String contactor,String requestBody)
	{
			URI uriBase = null;
			try {
				if(contactor != null && "&".equals(contactor)){
					uriBase = new URI(path+"&"+requestBody);
				}else{
					uriBase = new URI(path+"?"+requestBody);
				}
				
				Reference targetRef = new Reference(uriBase);
				targetRef.setBaseRef(getReference());
				getRequest().setMethod(Method.GET);
				
				getResponse().redirectTemporary(targetRef);
			} catch (URISyntaxException e) {
				logger.error("客户设置交易密码、客户修改交易密码、客户重置交易密码、客户密码校验失败：{}", requestBody, e);
				return;
			}	
	}
	
	@Post
	public Representation getMerchantInfo_post(String username) {
		System.out.println(username);
		return new StringRepresentation("测试");
	}


}
