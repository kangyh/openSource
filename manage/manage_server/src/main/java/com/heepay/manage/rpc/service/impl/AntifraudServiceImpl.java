/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.rpc.service.impl;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.DateUtil;
import com.heepay.enums.RateBankcardType;
import com.heepay.manage.modules.merchant.dao.MerchantBankCardDao;
import com.heepay.manage.modules.merchant.dao.MerchantDao;
import com.heepay.manage.modules.merchant.dao.MerchantUserDao;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantBankCard;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.rpc.service.AntifraudThrift;
import com.heepay.rpc.service.RpcService;

/**          
* 
* 描    述：获取反欺诈信息
*
* 创 建 者： ly
* 创建时间： 2016年12月20日 下午3:27:19 
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
@RpcService(name = "antifraudServiceImpl" , processor = com.heepay.manage.rpc.service.AntifraudService.Processor.class)
public class AntifraudServiceImpl implements com.heepay.manage.rpc.service.AntifraudService.Iface{

    @Autowired
    private MerchantBankCardDao merchantBankCardDao;
    
    @Autowired
    private MerchantDao merchantDao;
    
    @Autowired
    private MerchantUserDao merchantUserDao;
    
    private static final String LONGTIME = "Thu Nov 30 00:00:00 CST 2";//长期date
    
    private static final String LONGTIMESTRING = "长期";//长期string
    
    @Override
    public List<AntifraudThrift> getAntifraudInfoByMerchantId(List<String> merchantIds) throws TException {
        if(null != merchantIds && !merchantIds.isEmpty()){
            List<AntifraudThrift> list = Lists.newArrayList();
            for(String merchantId : merchantIds){
                MerchantUser merchantUser = merchantUserDao.get(merchantId);
                if(null != merchantUser){
                    Merchant merchantFind = new Merchant();
                    merchantFind.setUserId(Integer.valueOf(merchantId));
                    Merchant merchant = merchantDao.get(merchantFind);
                    MerchantBankCard merchantBankCard = merchantBankCardDao.getMerchant(merchantId);
                    AntifraudThrift antifraudThrift = getAntifraudThrift(merchant,merchantUser,merchantBankCard);
                    list.add(antifraudThrift);
                }else{
                    AntifraudThrift antifraudThrift = new AntifraudThrift();
                    antifraudThrift.setMerchantId(merchantId);
                    list.add(antifraudThrift);
                }
            }
            return list;
        }
        return null;
    }

      
    /**     
    * @discription 转换反欺诈thrift
    * @author ly     
    * @created 2016年12月20日 下午5:36:51     
    * @param merchant
    * @param merchantUser
    * @param merchantBankCard
    * @return     
    */
    private AntifraudThrift getAntifraudThrift(Merchant merchant, MerchantUser merchantUser,
            MerchantBankCard merchantBankCard) {
        AntifraudThrift antifraudThrift = new AntifraudThrift();
        antifraudThrift.setAssociateLineNumber(merchantBankCard.getAssociateLineNumber());
        antifraudThrift.setBankCardType(RateBankcardType.DEBITCARD.getValue());
        antifraudThrift.setBankId(merchantBankCard.getBankId());
        antifraudThrift.setBankName(merchantBankCard.getBankName());
        String bankNo = Aes.decryptStr(merchantBankCard.getBankNo(), Constants.QuickPay.SYSTEM_KEY);
        antifraudThrift.setBankNo(bankNo);
        antifraudThrift.setBankStatus(merchantBankCard.getStatus());
        //转换营业执照结束时间
        if(null != merchant.getBusinessLicenseEndTime()){
            if(merchant.getBusinessLicenseEndTime().toString().equals(LONGTIME)){
                antifraudThrift.setBusinessLicenseEndTime(LONGTIMESTRING);
            }else{
                antifraudThrift.setBusinessLicenseEndTime(DateUtil.dateToString(merchant.getBusinessLicenseEndTime(), DateUtil.DATE_FORMAT_YYYYMMDD));
            }
        }
        antifraudThrift.setBusinessLicenseEndTime(DateUtil.dateToString(merchant.getBusinessLicenseEndTime(), DateUtil.DATE_FORMAT_YYYYMMDD));
        antifraudThrift.setBusinessLicenseNo(merchant.getBusinessLicenseNo());
        antifraudThrift.setBusinessScope(merchant.getBusinessScope());
        antifraudThrift.setCertificateType(merchant.getCertificateType());
        antifraudThrift.setCompanyName(merchantUser.getCompanyName());
        //转换联系人证件有效期结束
        if(null != merchant.getContactorCertificateEndTime()){
            if(merchant.getContactorCertificateEndTime().toString().equals(LONGTIME)){
                antifraudThrift.setContactorCertificateEndTime(LONGTIMESTRING);
            }else{
                antifraudThrift.setContactorCertificateEndTime(DateUtil.dateToString(merchant.getContactorCertificateEndTime(), DateUtil.DATE_FORMAT_YYYYMMDD));
            }
        }
        antifraudThrift.setContactorIdcardNo(merchant.getContactorIdcardNo());
        antifraudThrift.setContactorPhone(merchant.getContactorPhone());
        antifraudThrift.setCpuInfo(merchantUser.getCpuInfo());
        antifraudThrift.setDiskInfo(merchantUser.getDiskInfo());
        antifraudThrift.setIpcNo(merchant.getIpcNo());
        //转换最后登录时间
        if(null != merchantUser.getLastLoginDate()){
            antifraudThrift.setLastLoginDate(DateUtil.dateToString(merchantUser.getLastLoginDate(), DateUtil.DATE_FORMAT_YYYYMMDD));
        }
        antifraudThrift.setLastLoginIp(merchantUser.getLastLoginIp());
        //转换法人代表证件有效期结束
        if(null != merchant.getLegalCertificateEndTime()){
            if(merchant.getLegalCertificateEndTime().toString().equals(LONGTIME)){
                antifraudThrift.setLegalCertificateEndTime(LONGTIMESTRING);
            }else{
                antifraudThrift.setLegalCertificateEndTime(DateUtil.dateToString(merchant.getLegalCertificateEndTime(), DateUtil.DATE_FORMAT_YYYYMMDD));
            }
        }
        antifraudThrift.setLegalIdcard(merchant.getLegalIdcard());
        antifraudThrift.setLegalRepresentative(merchant.getLegalRepresentative());
        antifraudThrift.setLoginName(merchantUser.getLoginName());
        antifraudThrift.setMacInfo(merchantUser.getMacInfo());
        antifraudThrift.setMerchantId(merchantUser.getId());
        antifraudThrift.setMobile(merchantUser.getMobile());
        antifraudThrift.setOrganizationCode(merchant.getOrganizationCode());
        antifraudThrift.setStatus(merchantUser.getStatus());
        antifraudThrift.setTaxRegistrationCertificateNo(merchant.getTaxRegistrationCertificateNo());
        antifraudThrift.setUserType(merchantUser.getUserType());
        return antifraudThrift;
    }


    @Override
    public List<AntifraudThrift> getAntifraudInfoByLoginName(List<String> loginNames) throws TException {
        if(null != loginNames && !loginNames.isEmpty()){
            List<AntifraudThrift> list = Lists.newArrayList();
            for(String loginName : loginNames){
                MerchantUser merchantUser = merchantUserDao.queryEmailExist(loginName);
                if(null != merchantUser){
                    Merchant merchantFind = new Merchant();
                    merchantFind.setUserId(Integer.valueOf(merchantUser.getId()));
                    Merchant merchant = merchantDao.get(merchantFind);
                    MerchantBankCard merchantBankCard = merchantBankCardDao.getMerchant(merchantUser.getId());
                    AntifraudThrift antifraudThrift = getAntifraudThrift(merchant,merchantUser,merchantBankCard);
                    list.add(antifraudThrift);
                }else{
                    AntifraudThrift antifraudThrift = new AntifraudThrift();
                    antifraudThrift.setLoginName(loginName);
                    list.add(antifraudThrift);
                }
            }
            return list;
        }
        return null;
    }
}
