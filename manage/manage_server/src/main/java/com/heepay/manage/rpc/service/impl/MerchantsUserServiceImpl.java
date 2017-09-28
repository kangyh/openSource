package com.heepay.manage.rpc.service.impl;

import com.heepay.codec.Md5;
import com.heepay.common.util.Constants;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.CommonStatus;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.MerchantStatus;
import com.heepay.enums.UserType;
import com.heepay.manage.modules.merchant.dao.MerchantUserDao;
import com.heepay.manage.modules.merchant.dao.MerchantsUserDao;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.modules.merchant.vo.MerchantsUser;
import com.heepay.manage.rpc.service.*;
import com.heepay.rpc.service.RpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RpcService(name = "merchantsUserServiceImpl" , processor = MerchantsUserService.Processor.class)
public class MerchantsUserServiceImpl implements MerchantsUserService.Iface{
	private static final String loginPasswdMd5Key=Constants.WalletKey.LOGIN_PASSWD_KEY;
	private static final String payPasswdMd5Key=Constants.WalletKey.PAY_PASSWD_KEY;
    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private MerchantsUserDao merchantsUserDao;

    @Autowired
    private MerchantUserDao merchantUserDao;
    @Override
	public MerchantsUserThrift queryByLoginNameAndMerchantId(String loginName, String merchantId) throws TException {
	        MerchantsUser merchantsUser = new MerchantsUser();
	        merchantsUser.setMerchantId(merchantId);
	        merchantsUser.setLoginName(loginName);	      
	         merchantsUser = merchantsUserDao.queryByLoginNameAndMerchantId(merchantsUser);
	        if(null == merchantsUser){
	            return new MerchantsUserThrift();
	        }else{
	            MerchantsUserThrift merchantsUserThrift = changeThrift(merchantsUser);
	            return merchantsUserThrift;
	        }
	}
    
    @Override
    public MerchantsUserThrift getMerchantsUserThriftByUserId(String userId) throws TException {
        MerchantsUser merchantsUser = merchantsUserDao.get(userId);
        if(null == merchantsUser){
            return new MerchantsUserThrift();
        }else{
            MerchantsUserThrift merchantsUserThrift = changeThrift(merchantsUser);
            return merchantsUserThrift;
        }
    }

    @Override
    public MerchantsUserThrift getMerchantsUserThrift(String merchantId, String userId) throws TException {
        MerchantsUser merchantsUser = merchantsUserDao.select(userId,merchantId);
        if(null == merchantsUser){
            return new MerchantsUserThrift();
        }else{
            MerchantsUserThrift merchantsUserThrift = changeThrift(merchantsUser);
            return merchantsUserThrift;
        }
    }
    
    
    
    @Override
    public MerchantsUserReturnThrift saveMerchantsUser2(String merchantId,String loginName,String loginPassword,String source) throws TException {
        MerchantsUserReturnThrift merchantsUserReturnThrift = new MerchantsUserReturnThrift();
        MerchantsUser merchantsUser = new MerchantsUser();
        merchantsUser.setMerchantId(merchantId);
        merchantsUser.setLoginName(loginName);
        //验证商户用户是否存在
        MerchantsUser merchantsUserReturn = merchantsUserDao.queryByLoginNameAndMerchantId(merchantsUser);
        if(null == merchantsUserReturn){
            //不存在  将商户用户基本信息存入
            merchantsUser.setPhone(loginName);
            merchantsUser.setLoginPassword(Md5.encode(loginPassword+loginPasswdMd5Key));
            merchantsUser.setCreateTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
            merchantsUser.setStatus(MerchantStatus.NORMAL.getValue());
            merchantsUser.setUserType(UserType.USER.getValue());
            merchantsUser.setUserLevel("0");
            merchantsUser.setSource(source);
            merchantsUserDao.insert(merchantsUser);
            if(StringUtils.isNotBlank(merchantsUser.getId())){
                //插入成功
                merchantsUserReturnThrift.setUserId(Long.valueOf(merchantsUser.getId()));
                merchantsUserReturnThrift.setCode(InterfaceStatus.SUCCESS.getValue());
                merchantsUserReturnThrift.setMsg(InterfaceStatus.SUCCESS.getContent());
            }else{
                //插入失败
                merchantsUserReturnThrift.setUserId(0);
                merchantsUserReturnThrift.setCode(InterfaceStatus.INTERNALERROR.getValue());
                merchantsUserReturnThrift.setMsg(InterfaceStatus.INTERNALERROR.getContent());
            }
        }else{
            //存在
            merchantsUserReturnThrift.setUserId(0);
            merchantsUserReturnThrift.setCode(InterfaceStatus.MERCHANTSUSEREXIST.getValue());
            merchantsUserReturnThrift.setMsg(InterfaceStatus.MERCHANTSUSEREXIST.getContent());
        }
        return merchantsUserReturnThrift;
    }
    
    @Override
    public MerchantsUserReturnThrift saveMerchantsUser(String merchantId,String loginName,String source) throws TException {
        MerchantsUserReturnThrift merchantsUserReturnThrift = new MerchantsUserReturnThrift();
        MerchantsUser merchantsUser = new MerchantsUser();
        merchantsUser.setMerchantId(merchantId);
        merchantsUser.setLoginName(loginName);
        //验证商户用户是否存在
        MerchantsUser merchantsUserReturn = merchantsUserDao.queryByLoginNameAndMerchantId(merchantsUser);
        if(null == merchantsUserReturn){
            //不存在  将商户用户基本信息存入
            merchantsUser.setPhone(loginName);
            merchantsUser.setCreateTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
            merchantsUser.setStatus(MerchantStatus.NORMAL.getValue());
            merchantsUser.setUserType(UserType.USER.getValue());
            merchantsUser.setUserLevel("0");
            merchantsUser.setSource(source);
            merchantsUserDao.insert(merchantsUser);
            if(StringUtils.isNotBlank(merchantsUser.getId())){
                //插入成功
                merchantsUserReturnThrift.setUserId(Long.valueOf(merchantsUser.getId()));
                merchantsUserReturnThrift.setCode(InterfaceStatus.SUCCESS.getValue());
                merchantsUserReturnThrift.setMsg(InterfaceStatus.SUCCESS.getContent());
            }else{
                //插入失败
                merchantsUserReturnThrift.setUserId(0);
                merchantsUserReturnThrift.setCode(InterfaceStatus.INTERNALERROR.getValue());
                merchantsUserReturnThrift.setMsg(InterfaceStatus.INTERNALERROR.getContent());
            }
        }else{
            //存在
            merchantsUserReturnThrift.setUserId(0);
            merchantsUserReturnThrift.setCode(InterfaceStatus.MERCHANTSUSEREXIST.getValue());
            merchantsUserReturnThrift.setMsg(InterfaceStatus.MERCHANTSUSEREXIST.getContent());
        }
        return merchantsUserReturnThrift;
    }
    
    
    @Override
    public MerchantsUserReturnThrift saveMerchantsUserLeve1(String merchantId,String loginName,String idNo,String realName,String loginPassword) throws TException {
    	
    	MerchantsUserReturnThrift merchantsUserReturnThrift = new MerchantsUserReturnThrift();
        MerchantsUser merchantsUser = new MerchantsUser();
        merchantsUser.setMerchantId(merchantId);
        merchantsUser.setLoginName(loginName);
        //验证商户用户是否存在
        MerchantsUser merchantsUserReturn = merchantsUserDao.queryByLoginNameAndMerchantId(merchantsUser);
        if(null == merchantsUserReturn){
            //不存在  将商户用户基本信息存入
            merchantsUser.setPhone(loginName);
            merchantsUser.setIdNo(idNo);
            merchantsUser.setRealName(realName);
            merchantsUser.setRealnameAuthSign(CommonStatus.YES.getValue());
            merchantsUser.setCreateTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
            merchantsUser.setStatus(MerchantStatus.NORMAL.getValue());
            merchantsUser.setUserType(UserType.USER.getValue());
            merchantsUser.setUserLevel("1");
            merchantsUserDao.insert(merchantsUser);
            if(StringUtils.isNotBlank(merchantsUser.getId())){
                //插入成功
                merchantsUserReturnThrift.setUserId(Long.valueOf(merchantsUser.getId()));
                merchantsUserReturnThrift.setCode(InterfaceStatus.SUCCESS.getValue());
                merchantsUserReturnThrift.setMsg(InterfaceStatus.SUCCESS.getContent());
            }else{
                //插入失败
                merchantsUserReturnThrift.setUserId(0);
                merchantsUserReturnThrift.setCode(InterfaceStatus.INTERNALERROR.getValue());
                merchantsUserReturnThrift.setMsg(InterfaceStatus.INTERNALERROR.getContent());
            }
        }else{
            //存在
            merchantsUserReturnThrift.setUserId(0);
            merchantsUserReturnThrift.setCode(InterfaceStatus.MERCHANTSUSEREXIST.getValue());
            merchantsUserReturnThrift.setMsg(InterfaceStatus.MERCHANTSUSEREXIST.getContent());
        }
        return merchantsUserReturnThrift;
    }
    @Override
    public MerchantsUserReturnThrift setPayPassword(String merchantId,String userId,String payPassword) throws TException {
        MerchantsUserReturnThrift merchantsUserReturnThrift;
        MerchantsUserThrift merchantsUserThrift = new MerchantsUserThrift();
        merchantsUserThrift.setId(userId);
        merchantsUserThrift.setMerchantId(merchantId);
        merchantsUserThrift.setPayPassword(payPassword);
        //验证用户
        merchantsUserReturnThrift = verifyMerchantUser(merchantsUserThrift);
        if(null == merchantsUserReturnThrift){
            MerchantsUser merchantsUser = new MerchantsUser();
            merchantsUser.setMerchantId(merchantsUserThrift.getMerchantId());
            merchantsUser.setId(merchantsUserThrift.getId());
            merchantsUser.setPayPassword(Md5.encode(payPassword+payPasswdMd5Key));
            //保存支付密码
            int num = merchantsUserDao.resetPayPassword(merchantsUser);
            merchantsUserReturnThrift = judgeUpdate(num);
        }
        return merchantsUserReturnThrift;
    }
    @Override
	public MerchantsUserReturnThrift setLoginPassword(String merchantId, String userId, String loginPassword)
			throws TException {
    	 MerchantsUserReturnThrift merchantsUserReturnThrift;
         MerchantsUserThrift merchantsUserThrift = new MerchantsUserThrift();
         merchantsUserThrift.setId(userId);
         merchantsUserThrift.setMerchantId(merchantId);
         
         
         //验证用户
         merchantsUserReturnThrift = verifyMerchantUser(merchantsUserThrift);
         if(null == merchantsUserReturnThrift){
             MerchantsUser merchantsUser = new MerchantsUser();
             merchantsUser.setMerchantId(merchantsUserThrift.getMerchantId());
             merchantsUser.setId(merchantsUserThrift.getId());
             merchantsUser.setLoginPassword(Md5.encode(loginPassword+loginPasswdMd5Key));
             //保存登录密码
             int num = merchantsUserDao.resetLoginPassword(merchantsUser);
             merchantsUserReturnThrift = judgeUpdate(num);
         }
         return merchantsUserReturnThrift;
	}
    @Override
 	public MerchantsUserReturnThrift resetLoginNameAndPhone(String merchantId, String userId, String phone)
 			throws TException {
     	 MerchantsUserReturnThrift merchantsUserReturnThrift;
          MerchantsUserThrift merchantsUserThrift = new MerchantsUserThrift();
          merchantsUserThrift.setId(userId);
          merchantsUserThrift.setMerchantId(merchantId);
          
          
          //验证用户
          merchantsUserReturnThrift = verifyMerchantUser(merchantsUserThrift);
          if(null == merchantsUserReturnThrift){
              MerchantsUser merchantsUser = new MerchantsUser();
              merchantsUser.setMerchantId(merchantsUserThrift.getMerchantId());
              merchantsUser.setId(merchantsUserThrift.getId());
              merchantsUser.setLoginName(phone);
              merchantsUser.setPhone(phone);
              //保存
              int num = merchantsUserDao.resetLoginNameAndPhone(merchantsUser);
              merchantsUserReturnThrift = judgeUpdate(num);
          }
          return merchantsUserReturnThrift;
 	}
    
    
    private MerchantsUserReturnThrift judgeUpdate(int num) {
        MerchantsUserReturnThrift merchantsUserReturnThrift = new MerchantsUserReturnThrift();
        if(num > 0 ){
            //插入成功
            merchantsUserReturnThrift.setUserId(0);
            merchantsUserReturnThrift.setCode(InterfaceStatus.SUCCESS.getValue());
            merchantsUserReturnThrift.setMsg(InterfaceStatus.SUCCESS.getContent());
        }else{
            //插入失败
            merchantsUserReturnThrift.setUserId(0);
            merchantsUserReturnThrift.setCode(InterfaceStatus.INTERNALERROR.getValue());
            merchantsUserReturnThrift.setMsg(InterfaceStatus.INTERNALERROR.getContent());
        }
        return merchantsUserReturnThrift;
    }

    @Override
    public MerchantsUserReturnThrift verifyPayPassword(String merchantId,String userId,String payPassword) throws TException {
        MerchantsUserReturnThrift merchantsUserReturnThrift;
        MerchantsUserThrift merchantsUserThrift = new MerchantsUserThrift();
        merchantsUserThrift.setId(userId);
        merchantsUserThrift.setMerchantId(merchantId);
        merchantsUserThrift.setPayPassword(payPassword);
        merchantsUserReturnThrift = verifyMerchantUser(merchantsUserThrift);
        if(null == merchantsUserReturnThrift){
            merchantsUserReturnThrift = new MerchantsUserReturnThrift();
            //获取商户用户信息
            MerchantsUser merchantsUserReturn = merchantsUserDao.get(merchantsUserThrift.getId());
            //验证支付密码
            if(merchantsUserThrift.getPayPassword().equals(merchantsUserReturn.getPayPassword())){
                merchantsUserReturnThrift.setUserId(0);
                merchantsUserReturnThrift.setCode(InterfaceStatus.SUCCESS.getValue());
                merchantsUserReturnThrift.setMsg(InterfaceStatus.SUCCESS.getContent());
                return merchantsUserReturnThrift;
            }else{
                merchantsUserReturnThrift.setUserId(0);
                merchantsUserReturnThrift.setCode(InterfaceStatus.PASSWORDERROR.getValue());
                merchantsUserReturnThrift.setMsg(InterfaceStatus.PASSWORDERROR.getContent());
                return merchantsUserReturnThrift;
            }
        }
        return merchantsUserReturnThrift;
    }

    @Override
    public MerchantsUserReturnThrift setUserInfoLeve(String merchantId, String userId, String idNo, String realName,int leve) throws TException {
        MerchantsUserReturnThrift merchantsUserReturnThrift;
        MerchantsUserThrift merchantsUserThrift = new MerchantsUserThrift();
        merchantsUserThrift.setId(userId);
        merchantsUserThrift.setMerchantId(merchantId);
        merchantsUserReturnThrift = verifyMerchantUser(merchantsUserThrift);
        if(null == merchantsUserReturnThrift){
            MerchantsUser merchantsUser = new MerchantsUser();
            merchantsUser.setId(userId);
            merchantsUser.setIdNo(idNo);
            merchantsUser.setRealName(realName);
            merchantsUser.setRealnameAuthSign(CommonStatus.YES.getValue());
            if(leve>=2){
            	merchantsUser.setBankcardAuthSign(CommonStatus.YES.getValue());
            }
            merchantsUser.setUserLevel(String.valueOf(leve));
            int num = merchantsUserDao.update(merchantsUser);
            merchantsUserReturnThrift = judgeUpdate(num);
        }
        return merchantsUserReturnThrift;
    }
  
  


    /**
     * 验证商户用户
     */
    private MerchantsUserReturnThrift verifyMerchantUser(MerchantsUserThrift merchantsUserThrift){
        MerchantsUserReturnThrift merchantsUserReturnThrift = new MerchantsUserReturnThrift();
        //验证商户是否合法
        MerchantUser merchantUser = merchantUserDao.get(merchantsUserThrift.getMerchantId());
        if(null == merchantUser){
            //商户不存在
            merchantsUserReturnThrift.setUserId(0);
            merchantsUserReturnThrift.setCode(InterfaceStatus.NOMERCHANTERROR.getValue());
            merchantsUserReturnThrift.setMsg(InterfaceStatus.NOMERCHANTERROR.getContent());
            return merchantsUserReturnThrift;
        }else{
            if(!UserType.MERCHANT.getValue().equals(merchantUser.getUserType()) ||
                    !MerchantStatus.NORMAL.getValue().equals(merchantUser.getStatus())){
                //商户不合法
                merchantsUserReturnThrift.setUserId(0);
                merchantsUserReturnThrift.setCode(InterfaceStatus.MERCHANTERROR.getValue());
                merchantsUserReturnThrift.setMsg(InterfaceStatus.MERCHANTERROR.getContent());
                return merchantsUserReturnThrift;
            }
        }
        //验证商户用户是否存在
        MerchantsUser merchantsUserReturn ;
        if(StringUtils.isNotBlank(merchantsUserThrift.getId())){
            merchantsUserReturn = merchantsUserDao.get(merchantsUserThrift.getId());
            //验证用户是否属于商户
            if(!merchantsUserReturn.getMerchantId().equals(merchantsUserThrift.getMerchantId())){
            	merchantsUserReturnThrift.setUserId(0);
                merchantsUserReturnThrift.setCode(InterfaceStatus.MERCHANTSUSERNOTEXIST.getValue());
                merchantsUserReturnThrift.setMsg(InterfaceStatus.MERCHANTSUSERNOTEXIST.getContent());
                return merchantsUserReturnThrift;
            }
        }else{
            MerchantsUser merchantsUser = new MerchantsUser();
            merchantsUser.setMerchantId(merchantsUserThrift.getMerchantId());
            merchantsUser.setLoginName(merchantsUserThrift.getLoginName());
            merchantsUserReturn = merchantsUserDao.queryByLoginNameAndMerchantId(merchantsUser);
        }
        if(null == merchantsUserReturn){
            merchantsUserReturnThrift.setUserId(0);
            merchantsUserReturnThrift.setCode(InterfaceStatus.MERCHANTSUSERNOTEXIST.getValue());
            merchantsUserReturnThrift.setMsg(InterfaceStatus.MERCHANTSUSERNOTEXIST.getContent());
            return merchantsUserReturnThrift;
        }else{
            return null;
        }
    }

    /**
     * 转换thrift
     * ly 2017年5月2日14:37:01
     */
    private MerchantsUserThrift changeThrift(MerchantsUser merchantsUser) {
        MerchantsUserThrift merchantsUserThrift = new MerchantsUserThrift();
        merchantsUserThrift.setId(merchantsUser.getId());
        merchantsUserThrift.setMerchantId(merchantsUser.getMerchantId());
        merchantsUserThrift.setPhone(merchantsUser.getPhone());
        merchantsUserThrift.setIdNo(merchantsUser.getIdNo());
        merchantsUserThrift.setRealName(merchantsUser.getRealName());
        merchantsUserThrift.setRealnameAuthSign(merchantsUser.getRealnameAuthSign());
        merchantsUserThrift.setBankcardAuthSign(merchantsUser.getBankcardAuthSign());
        merchantsUserThrift.setUserLevel(merchantsUser.getUserLevel());
        merchantsUserThrift.setLoginName(merchantsUser.getLoginName());
        merchantsUserThrift.setLoginPassword(merchantsUser.getLoginPassword());
        merchantsUserThrift.setPayPassword(merchantsUser.getPayPassword());
        merchantsUserThrift.setStatus(merchantsUser.getStatus());
        merchantsUserThrift.setCreateTime(DateUtil.dateToString(merchantsUser.getCreateTime()));
        return merchantsUserThrift;
    }

    /**
     * 商家站点钱包查询商户用户
     * @param walletUsersWhere
     * @return
     * @throws TException
     */
    @Override
    public WalletQueryUsersResult queryMerchantsUser(WalletUsersWhere walletUsersWhere) throws TException {
    	logger.info("商家站点钱包查询商户用户请求：{}", walletUsersWhere);
    	
        List<MerchantsUserThrift> resultList = new ArrayList<>();
        int count = 0 ;
        try{
	        Map<String, Object> whereMap = fundRecordQueryWhereToMap(walletUsersWhere);
	
	        List<MerchantsUser> userList = merchantsUserDao.queryMerchantsUser(whereMap);
	    	logger.info("商家站点钱包查询商户用户请求查询结果：{}", userList);
	        for (MerchantsUser merchantsUser : userList) {
	            resultList.add(changeThrift(merchantsUser));
	        }
	        count = merchantsUserDao.queryMerchantsUserCount(whereMap);
	
	        WalletQueryUsersResult result = new WalletQueryUsersResult();
	        result.setCounts(count);
	        result.setQueryList(resultList);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        WalletQueryUsersResult result = new WalletQueryUsersResult();
        result.setCounts(count);
        result.setQueryList(resultList);
        return result;
    }

    /**
     * 商家站点钱包查询商户用户个数
     * @param walletUsersWhere
     * @return
     * @throws TException
     */
    @Override
    public int queryMerchantsUserCounts(WalletUsersWhere walletUsersWhere) throws TException {

        Map<String, Object> whereMap = fundRecordQueryWhereToMap(walletUsersWhere);
        int result = merchantsUserDao.queryMerchantsUserCount(whereMap);
        return result;
    }


    /**
     * where条件转成Map
     * @param where
     * @return
     */
    private Map<String, Object> fundRecordQueryWhereToMap(WalletUsersWhere where){
        Map<String, Object> whereMap = new HashMap<String, Object>();
        whereMap.put("timeFrom", where.getTimeFrom());
        whereMap.put("timeTo", where.getTimeTo());
        whereMap.put("userId", where.getUserId());
        whereMap.put("phone", where.getPhone());
        whereMap.put("realName", where.getRealName());
        whereMap.put("pageSize", where.getPageSize());
        whereMap.put("start", (where.getCurPage() - 1) * where.getPageSize());
        whereMap.put("merchantId", where.getMerchantId());
        return whereMap;
    }

}
