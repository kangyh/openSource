/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.rpc.service.impl;

import com.heepay.common.util.DateUtil;
import com.heepay.enums.MerchantStatus;
import com.heepay.enums.UserType;
import com.heepay.manage.modules.merchant.dao.MerchantUserDao;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.modules.merchant.dao.MerchantUserDao;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.rpc.service.PersonalUserService;
import com.heepay.manage.rpc.service.PersonalUserThrift;
import com.heepay.rpc.service.RpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * 描    述：个人用户中心User接口
 *
 * 创 建 者： M.Z
 * 创建时间： 2017/3/14 11:32 
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
@Component
@RpcService(name = "personalUserServiceImpl", processor = PersonalUserService.Processor.class)
public class PersonalUserServiceImpl implements PersonalUserService.Iface {

	/**
	 * 定义全局日志
	 */
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private MerchantUserDao merchantUserDao;

	@Override
	public String save(PersonalUserThrift arg0) throws TException {
		MerchantUser user = new MerchantUser();
		user.setLoginName(arg0.getLoginName());
		user.setUserType(UserType.USER.getValue());
		user.setStatus(MerchantStatus.NORMAL.getValue());
		merchantUserDao.insert(user);
		return String.valueOf(user.getId());
	}

	@Override
	public void update(PersonalUserThrift arg0) throws TException {
	}

	/**
	 * 根据登录密获取用户信息
	 * @param loginName
	 * @return
	 * @throws TException
	 */
	@Override
	public PersonalUserThrift queryByLoginName(String loginName) throws TException {
		MerchantUser merchantUser = merchantUserDao.queryEmailExist(loginName);
		PersonalUserThrift user = new PersonalUserThrift();
		if(null == merchantUser){
			return user;
		}
		user.setLoginName(merchantUser.getLoginName());
		user.setId(merchantUser.getId());
		user.setLoginPassword(merchantUser.getLoginPassword());
		user.setPayPassword(merchantUser.getPayPassword());
		user.setMobile(merchantUser.getMobile());
		user.setUserType(merchantUser.getUserType());
		user.setStatus(merchantUser.getStatus());
		user.setLastLoginIp(merchantUser.getLastLoginIp());
		//上一次登录的cpu,disk,mac,version信息
		user.setCpuInfo(merchantUser.getCpuInfo());
		user.setDiskInfo(merchantUser.getDiskInfo());
		user.setMacInfo(merchantUser.getMacInfo());
		//注册的系统，允许登陆的系统
		user.setSource(merchantUser.getSource());
		user.setAllowSystem(merchantUser.getAllowSystem());
		user.setLastLoginDate(DateUtil.dateToString(merchantUser.getLastLoginDate()));
		return user;
	}

	@Override
	public PersonalUserThrift resetLoginPassword(PersonalUserThrift personalUserThrift) throws TException {
		MerchantUser merchantUser = new MerchantUser();
		merchantUser.setLoginPassword(personalUserThrift.getLoginPassword());
		merchantUser.setLoginName(personalUserThrift.getLoginName());
		merchantUserDao.resetLoginPassword(merchantUser);
		return personalUserThrift;
	}

	@Override
	public PersonalUserThrift getCertificationStatus(PersonalUserThrift arg0) throws TException {
		return null;
	}

	@Override
	public PersonalUserThrift resetPayPassword(PersonalUserThrift personalUserThrift) throws TException {
		MerchantUser merchantUser = new MerchantUser();
		merchantUser.setPayPassword(personalUserThrift.getPayPassword());
		merchantUser.setLoginName(personalUserThrift.getLoginName());
		merchantUserDao.resetPayPassword(merchantUser);
		return personalUserThrift;
	}

	@Override
	public PersonalUserThrift changeLoginName(String oldLoginName,String newLoginName) throws TException {
		MerchantUser merchantUser = merchantUserDao.queryEmailExist(newLoginName);
		PersonalUserThrift personalUserThrift = new PersonalUserThrift();
		if(null != merchantUser){
			personalUserThrift.setLoginName("");
		}else{
			merchantUser = merchantUserDao.queryEmailExist(oldLoginName);
			merchantUser.setLoginName(newLoginName);
			merchantUserDao.update(merchantUser);
			personalUserThrift.setLoginName(newLoginName);
		}
		return personalUserThrift;
	}
}
