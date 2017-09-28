/**
 *  
 */
package com.heepay.manage.modules.merchant.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.codec.Base64;
import com.heepay.codec.CodecException;
import com.heepay.codec.Des;
import com.heepay.common.util.SendMailUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.AuthenticationStatus;
import com.heepay.enums.CertificationStatus;
import com.heepay.enums.MerchantStatus;
import com.heepay.enums.RemitStatus;
import com.heepay.enums.RouteStatus;
import com.heepay.enums.UserType;
import com.heepay.manage.common.cache.RedisUtil;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.modules.merchant.dao.MerchantEmployeeDao;
import com.heepay.manage.modules.merchant.dao.MerchantUserDao;
import com.heepay.manage.modules.merchant.service.MerchantUserCService;
import com.heepay.manage.modules.merchant.vo.MerchantEmployee;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.service.SystemService;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.redis.JedisClusterUtil;

import ocxrz.GetRandom;

/**
 *
 * 描    述：商户员工功能
 *
 * 创 建 者：ly
 * 创建时间：2016-08-23
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：ljh
 * 审核时间：2016-09-01
 * 审核描述：139行IP不能写死；156空方法；174方法中连续同样字符串可不用连续append。合并为一个字符串；
 * 			 如果直接使用父类方法，子类中可不用重写，直接调用父类。本类中所有调用super的方法可不用重写；
 *			 58行调用方法返回结果要做健壮性判定；91行方法中字符串使用==有问题，同时方法需优化
 *
 */
@Service
@Transactional(readOnly = true)
public class MerchantUserCServiceImpl extends CrudService<MerchantUserDao, MerchantUser> implements MerchantUserCService {

	 // 邮箱验证的30分钟的加密串
    private static final String EMAIL_CODE = "EMAIL_CODE";
    
	@Autowired
	private MerchantUserDao merchantUserDao;
	
	@Autowired
    private MerchantEmployeeDao merchantEmployeeDao;

	public MerchantUser get(String id) {
		return super.get(id);
	}
	
	/** 
	* @discription 判断设置信息认证状态方法
	* @author M.Z       
	* @created 2016年8月31日 下午2:34:55      
	* @param merchantUser
	* @return     
	* @see com.heepay.manage.modules.merchant.service.MerchantUserCService#setInfoAuthStatus(com.heepay.manage.modules.merchant.vo.MerchantUser)     
	*/

	@Override
	public MerchantUser setInfoAuthStatus(MerchantUser merchantUser) {
		if(null != merchantUser) {
			if (StringUtil.notBlank(merchantUser.getUserType())) {
				if (merchantUser.getUserType().equals(UserType.EMPLOYEE.getValue())) {
					merchantUser.setInfoAuthStatus(CertificationStatus.AUTHENTICATIONSUCCESS.getContent());//认证成功
				} else if (merchantUser.getUserType().equals(UserType.MERCHANT.getValue())||merchantUser.getUserType().equals(UserType.INDIVIDUAL.getValue())||merchantUser.getUserType().equals(UserType.USER.getValue())) {
					if (StringUtil.isBlank(merchantUser.getLegalAuditStatus())) {
						merchantUser.setInfoAuthStatus(CertificationStatus.UNAUTHORIZED.getContent());//未认证
					} else if (merchantUser.getLegalAuditStatus().equals(RouteStatus.AUDREJ.getValue())) {
						merchantUser.setInfoAuthStatus(CertificationStatus.AUDITFAILURE.getContent());//审核失败
					} else if (merchantUser.getLegalAuditStatus().equals(RouteStatus.AUDING.getValue())) {
						merchantUser.setInfoAuthStatus(CertificationStatus.LEGALAUDITSTATUS.getContent());//法务审核中
					} else if (merchantUser.getLegalAuditStatus().equals(RouteStatus.AUDIT_SUCCESS.getValue())) {
						if (merchantUser.getRcAuditStatus().equals(RouteStatus.AUDREJ.getValue())) {
							merchantUser.setInfoAuthStatus(CertificationStatus.AUDITFAILURE.getContent());//审核失败
						} else if (merchantUser.getRcAuditStatus().equals(RouteStatus.AUDING.getValue())) {
							merchantUser.setInfoAuthStatus(CertificationStatus.RCAUDITSTATUS.getContent());//风控审核中
						} else if (merchantUser.getRcAuditStatus().equals(RouteStatus.AUDIT_SUCCESS.getValue())) {
							if (StringUtil.isBlank(merchantUser.getMerchantStatus())) {
								if (merchantUser.getPayStatus().equals(RemitStatus.FAIL.getValue())) {
									merchantUser.setInfoAuthStatus(CertificationStatus.AUTHENTICATIONFAILURE.getContent());//认证失败
								} else if (merchantUser.getPayStatus().equals(RemitStatus.REMIT.getValue())) {
									merchantUser.setInfoAuthStatus(CertificationStatus.REVIEW.getContent());//审核中
								} else if (merchantUser.getPayStatus().equals(RemitStatus.SUCCESS.getValue())) {
								    if (StringUtil.isBlank(merchantUser.getAuthenticationStatus())) {
									  	merchantUser.setInfoAuthStatus(CertificationStatus.AUTHENTICATION.getContent());//认证中
									}else if (merchantUser.getAuthenticationStatus().equals(AuthenticationStatus.FAIL.getValue())) {
										merchantUser.setInfoAuthStatus(CertificationStatus.AUTHENTICATIONFAILURE.getContent());//认证失败
									} else if (merchantUser.getAuthenticationStatus().equals(AuthenticationStatus.SUCCESS.getValue())) {
										merchantUser.setInfoAuthStatus(CertificationStatus.AUTHENTICATIONSUCCESS.getContent());//认证成功
									}
								}
							} else if (merchantUser.getMerchantStatus().equals(AuthenticationStatus.SUCCESS.getValue())) {
								merchantUser.setInfoAuthStatus(CertificationStatus.AUTHENTICATIONSUCCESS.getContent());//认证成功
							}
						}
					}
				}

			}
		}
		return merchantUser;
	}

	/**
	 * @discription 为员工查询公司名称
	 * @author M.Z
	 * @created 2016年10月11日
	 * @param
	 * @return List<MerchantUser>
	 */
	@Override
	public List<MerchantUser> selectCompanyForEmployee() {
		List<MerchantUser> merchantUsers = merchantUserDao.selectCompanyForEmployee();
		return merchantUsers;
	}

	/**
	 * @discription 为员工赋值公司名称
	 * @author M.Z
	 * @created 2016年10月11日
	 * @param  merchantUsers,user
	 * @return MerchantUser
	 */
	@Override
	public void setCompanyForEmployee(List<MerchantUser> merchantUsers, MerchantUser user) {
		for(MerchantUser merchantUser:merchantUsers){
			if (StringUtil.notBlank(merchantUser.getId())&&StringUtil.notBlank(merchantUser.getCompanyName())){
				if (merchantUser.getId().equals(user.getId())){
					user.setCompanyName(merchantUser.getCompanyName());
				}
			}
		}
	}


	@Transactional(readOnly = false)
	public String status(MerchantUser merchantUser) {
		merchantUserDao.status(merchantUser);
		List<MerchantEmployee> merchantEmployees = merchantEmployeeDao.getEmployeesByMerchantId(Integer.valueOf(merchantUser.getId()));
		if(null != merchantEmployees && !merchantEmployees.isEmpty()){
		    for(MerchantEmployee merchantEmployee : merchantEmployees){
		        MerchantUser merchantUserEmployee = new MerchantUser();
		        merchantUserEmployee.setId(merchantEmployee.getUserId().toString());
		        merchantUserEmployee.setStatus(merchantUser.getStatus());
		        merchantUserDao.status(merchantUserEmployee);
		    }
		}
		RedisUtil.getRedisUtil().saveMerchantVoRedis(merchantUser.getId());
		if(MerchantStatus.FREEZED.getValue().equals(merchantUser.getStatus())){
		    RedisUtil.getRedisUtil().saveRedisDisableMerchant(merchantUser.getId());
		}else if(MerchantStatus.NORMAL.getValue().equals(merchantUser.getStatus())){
		    RedisUtil.getRedisUtil().delRedisDisableMerchant(merchantUser.getId());
		}
		return Constants.SUCCESS;
	}


	/** 
	* @discription 发送登录密码重置邮件
	* @author M.Z       
	* @created 2016年8月31日 下午6:50:59      
	* @param emailAddress
	 * @throws CodecException
	 * @throws UnsupportedEncodingException 
	*
	*/  
	    
	@Override
	public String sendMail(String emailAddress,String loginName,String resetWhat) throws CodecException, UnsupportedEncodingException {
		 if (StringUtil.notBlank(emailAddress)) {
	          String key = GetRandom.generateString(32);
	          //将加密串放入缓存。30分钟有效期
	          setAttibuteForEmailCode(emailAddress, key);
	          String encode = encode(emailAddress + ":" + key, key);
	          String encode1 = URLEncoder.encode(encode, Constants.ENCODE_TYPE_UTF);
			  String url;
			  String mailTitle;
			  if (resetWhat.equals(Constants.RESET_LOGIN_PW)){
				  url = Global.getConfig("reset.loginPassword.url") + "?ac=" + encode1 + "&email=" + emailAddress+"&loginName=" + loginName;
				  mailTitle = "重置登录密码---请勿回复此邮件";
			  }else if (resetWhat.equals(Constants.RESET_PAY_PW)){
				  url = Global.getConfig("reset.payPassword.url") + "?ac=" + encode1 + "&email=" + emailAddress+"&loginName=" + loginName;
				  mailTitle = "重置支付密码---请勿回复此邮件";
			  }else{
				  return Constants.FAIL;
			  }
			 SendMailUtil.getInstance().sendMail(emailAddress, mailTitle, getEmailContent(emailAddress, url), null);
			 return Constants.SUCCESS;
	      }else{
	    	  return Constants.FAIL;
	      }

	}

	  
	/** 
	* @discription  验证操作员密码是否正确
	* @author M.Z       
	* @created 2016年8月31日 下午8:35:11      
	* @param password
	* @return     
	* @see com.heepay.manage.modules.merchant.service.MerchantUserCService#checkPassword(java.lang.String)     
	*/  
	    
	@Override
	public boolean checkPassword(String password) {
		User user = UserUtils.getUser();
		return SystemService.validatePassword(password, user.getPassword());
	}

	  
	/** 
	* @discription
	* @author M.Z       
	* @created 2016年8月31日 下午8:43:35      
	* @param emailAddress
	* @param
	* @param url
	* @return     
	*
	*/  
	    
	@Override
	public String getEmailContent(String emailAddress,String url) {
		StringBuilder e = new StringBuilder();
        e.append("</br>您申请重置密码。");
        e.append("如果点击无效，请复制下方网页地址到浏览器地址中打开").append("</br>").append("</br>");
        e.append(url).append("</br>").append("</br>").append("</br>");
        e.append("此为系统邮件，请勿回复  Copyright 汇付宝 2004-2016 All Right Reserved");
        return e.toString();
	}

	  
	/** 
	* @discription
	* @author M.Z       
	* @created 2016年8月31日 下午8:57:28      
	* @param email
	* @param value     
	* @see com.heepay.manage.modules.merchant.service.MerchantUserCService#setAttibuteForEmailCode(java.lang.String, java.lang.String)     
	*/  
	    
	@Override
	public void setAttibuteForEmailCode(String email, String value) {
		 // 设置新生成的加密串
        JedisClusterUtil.setJedisValue(setter -> setter.set(email + ":" + EMAIL_CODE, value));
        //设置存活时间
        JedisClusterUtil.setJedisValue(setter -> setter.expire(email + ":" + EMAIL_CODE, 60 * 30));
		
	}

	  
	/** 
	* @discription
	* @author M.Z       
	* @created 2016年8月31日 下午9:00:26      
	* @param src
	* @param key
	* @return     
	 * @throws CodecException 
	* @see com.heepay.manage.modules.merchant.service.MerchantUserCService#encode(java.lang.String, java.lang.String)     
	*/  
	    
	@Override
	public String encode(String src, String key) throws CodecException {
		 byte[] encode = Des.encode(src.getBytes(), key);
	     return Base64.encode(encode);
	}

	  
	/** 
	* @discription
	* @author M.Z        
	* @created 2016年9月1日 下午2:36:53      
	* @param merchantUser     
	* @see com.heepay.manage.modules.merchant.service.MerchantUserCService#unbundling(com.heepay.manage.modules.merchant.vo.MerchantUser)     
	*/  
	    
	@Transactional(readOnly = false)
	public String unbundling(MerchantUser merchantUser) {
		merchantUserDao.unbundling(merchantUser);
		return Constants.SUCCESS;
	}


	/**
	 * @discription
	 * @author 郭正新
	 * @created 2017年1月10日 下午20:40:58
	 * @see com.heepay.manage.modules.merchant.service.MerchantUserCService#unbundling(com.heepay.manage.modules.merchant.vo.MerchantUser)
	 * @param merchantUser
	 */

    @Transactional(readOnly = false)
	public void updateMerchantPASS(MerchantUser merchantUser) {
        merchantUserDao.updateMerchantPASS(merchantUser);
	}

}
