/**
 *  
 */
package com.heepay.manage.modules.sys.web;

import java.util.Map;

import com.google.common.collect.Maps;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.utils.CacheUtils;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.common.utils.SpringContextHolder;
import com.heepay.manage.modules.sys.dao.UserDao;
import com.heepay.manage.modules.sys.entity.Dict;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
 * 登录Controller
 *  
 * @version 2013-5-31
 */
public class LoginStatic {

	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
			if(StringUtils.isNotBlank(DictUtils.getSysCommonDictValue(Constants.LOGIN_FLAG_NUM))){
				Integer errorNum = Integer.valueOf(DictUtils.getSysCommonDictValue(Constants.LOGIN_FLAG_NUM));
				//判断登录错误超出指定次数 禁用该用户
				if(loginFailNum >= errorNum){
					if(!(null != UserUtils.getByLoginName(useruame) && UserUtils.getByLoginName(useruame).isAdmin())){
						userDao.updateUserLoginFlag(useruame, Global.NO);
						CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LOGIN_NAME_ + useruame);
					}
				}
			}
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}	
}
