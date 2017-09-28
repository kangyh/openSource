
package com.heepay.manage.rpc.service.impl;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.heepay.manage.modules.merchant.dao.MerchantUserDao;
import com.heepay.manage.modules.merchant.vo.MerchantUser;

import com.heepay.manage.rpc.service.MerchantUserService;
import com.heepay.manage.rpc.service.MerchantUserThrift;
import com.heepay.rpc.service.RpcService;


/**
 * 名称：商户修改登录密码
 * <p/>
 * 创建者 L.Gk
 * 创建时间 2016-08-24 
 */


@Component
@RpcService(name = "MerchantModifyServiceImpl", processor = MerchantUserService.Processor.class)
public class MerchantModifyServiceImpl implements MerchantUserService.Iface{
  
  
  
/**
   * 定义全局日志
   */

  private static final Logger logger = LogManager.getLogger();
  
  @Autowired
  private MerchantUserDao merchantUserDao;

  @Override
  public MerchantUserThrift save(MerchantUserThrift merchantUserThrift) throws TException {
    
      MerchantUser merchantUser = new MerchantUser();
      
      merchantUser.setLoginName(merchantUserThrift.getLoginName());
      merchantUser.setId(merchantUserThrift.getId());
      merchantUser.setLoginPassword(merchantUserThrift.getLoginPassword());
      merchantUser.setPayPassword(merchantUserThrift.getPayPassword());
      merchantUser.setMobile(merchantUserThrift.getMobile());
      merchantUser.setSecretQuestion(merchantUserThrift.getSecretQuestion());
      merchantUser.setAnswerSecretQuestion(merchantUserThrift.getAnswerSecretQuestion());
      
      if(null==merchantUserThrift.getId()){
         merchantUserDao.insert(merchantUser);
      }else{
         try {
          merchantUserDao.update(merchantUser);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      return merchantUserThrift;
  }

    @Override
    public MerchantUserThrift update(MerchantUserThrift arg0) throws TException {
        return null;
    }

    @Override
    public MerchantUserThrift employeeRegist(MerchantUserThrift arg0) throws TException {
        return null;
    }

    @Override
  public List<MerchantUserThrift> findList(MerchantUserThrift merchantUserThrift) throws TException {
      return null;
  }

  @Override
  public MerchantUserThrift queryEmailExist(String email) throws TException {
    
      MerchantUser merchantUser = merchantUserDao.queryEmailExist(email);
      MerchantUserThrift user = new MerchantUserThrift();
      if(null!= merchantUser){
        user.setLoginName(merchantUser.getLoginName());
        user.setId(merchantUser.getId());
        user.setLoginPassword(merchantUser.getLoginPassword());
        user.setPayPassword(merchantUser.getPayPassword());
        user.setMobile(merchantUser.getMobile());
        user.setSecretQuestion(merchantUser.getSecretQuestion());
        user.setAnswerSecretQuestion(merchantUser.getAnswerSecretQuestion());
        
      }
      
      return user;
      
      
  }

  @Override
  public MerchantUserThrift get(String id) throws TException {
    MerchantUser merchantUser = merchantUserDao.get(id);
    MerchantUserThrift user = new MerchantUserThrift();
    if(null!= merchantUser){
      user.setLoginName(merchantUser.getLoginName());
      user.setId(merchantUser.getId());
      user.setLoginPassword(merchantUser.getLoginPassword());
      user.setPayPassword(merchantUser.getPayPassword());
      user.setMobile(merchantUser.getMobile());
      user.setSecretQuestion(merchantUser.getSecretQuestion());
      user.setAnswerSecretQuestion(merchantUser.getAnswerSecretQuestion());
    }
    return user;
  }

  
  @Override
  public MerchantUserThrift queryPhoneNo(String phone) throws TException {
    MerchantUser merchantUser = merchantUserDao.queryPhoneNo(phone);
    MerchantUserThrift user = new MerchantUserThrift();
    if(null!= merchantUser){
      user.setLoginName(merchantUser.getLoginName());
      user.setId(merchantUser.getId());
      user.setLoginPassword(merchantUser.getLoginPassword());
      user.setPayPassword(merchantUser.getPayPassword());
      user.setMobile(merchantUser.getMobile());
      user.setSecretQuestion(merchantUser.getSecretQuestion());
      user.setAnswerSecretQuestion(merchantUser.getAnswerSecretQuestion());
      
    }
    return user;
  }

    @Override
    public MerchantUserThrift changIpMsg(MerchantUserThrift merchantUserThrift) throws TException {
        return null;
    }


    @Override
  public MerchantUserThrift resetLoginPassword(MerchantUserThrift arg0) throws TException {
    return null;
  }

    @Override
    public MerchantUserThrift getCertificationStatus(MerchantUserThrift arg0) throws TException {
        return null;
    }

    @Override
  public MerchantUserThrift resetPayPassword(MerchantUserThrift arg0) throws TException {
    return null;
  }
 
   
  }

 



