package com.heepay.manage.modules.merchant.service;

import com.heepay.codec.CodecException;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.MerchantUser;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface MerchantUserCService {

  public MerchantUser get(String id);
  
  public List<MerchantUser> findList(MerchantUser user);
  
  public Page<MerchantUser> findPage(Page<MerchantUser> page, MerchantUser user);
  
  public void save(MerchantUser user, boolean flag);
  
  public void delete(MerchantUser user);
  
  public MerchantUser setInfoAuthStatus(MerchantUser merchantUser);

  public List<MerchantUser> selectCompanyForEmployee();

  public void setCompanyForEmployee(List<MerchantUser> merchantUsers,MerchantUser user);
  
  public String status(MerchantUser merchantUser);
  
  public String sendMail(String emailAddress,String loginName,String resetWhat) throws CodecException, UnsupportedEncodingException;
  
  public boolean checkPassword(String password);
  
  public String getEmailContent(String emailAddress,String url);
  
  public void setAttibuteForEmailCode(String email, String value);
  
  public String encode(String src, String key) throws CodecException;
  
  public String unbundling(MerchantUser merchantUser);

  public void updateMerchantPASS(MerchantUser merchantUser);
}
