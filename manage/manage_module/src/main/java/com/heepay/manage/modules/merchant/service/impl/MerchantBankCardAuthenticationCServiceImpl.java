/**
 *  
 */
package com.heepay.manage.modules.merchant.service.impl;

import com.heepay.enums.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.manage.common.cache.RedisUtil;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.account.service.MerchantAccountService;
import com.heepay.manage.modules.merchant.dao.MerchantBankCardAuthenticationDao;
import com.heepay.manage.modules.merchant.dao.MerchantBankCardDao;
import com.heepay.manage.modules.merchant.dao.MerchantDao;
import com.heepay.manage.modules.merchant.dao.MerchantUserDao;
import com.heepay.manage.modules.merchant.service.MerchantBankCardAuthenticationCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantBankCard;
import com.heepay.manage.modules.merchant.vo.MerchantBankCardAuthentication;
import com.heepay.manage.modules.merchant.vo.MerchantUser;

/**
 *
 * 描 述：商户打款认证Service
 *
 * 创 建 者：ly 创建时间：2016-08-23 创建描述：
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者：ljh 审核时间：2016-09-01
 * 审核描述：如果直接使用父类方法，子类中可不用重写，直接调用父类。本类中所有调用super的方法可不用重写
 *
 */
@Service
@Transactional(readOnly = true)
public class MerchantBankCardAuthenticationCServiceImpl
        extends CrudService<MerchantBankCardAuthenticationDao, MerchantBankCardAuthentication>
        implements MerchantBankCardAuthenticationCService {

    @Autowired
    private MerchantUserDao merchantUserDao;

    @Autowired
    private MerchantBankCardDao merchantBankCardDao;

    @Autowired
    private MerchantAccountService merchantAccountService;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private MerchantBankCardAuthenticationDao merchantBankCardAuthenticationDao;

    @Transactional(readOnly = false)
    public void status(MerchantBankCardAuthentication merchantBankCardAuthentication) {
        merchantBankCardAuthenticationDao.status(merchantBankCardAuthentication);
    }

    @Transactional(readOnly = false)
    public void save(MerchantBankCardAuthentication merchantBankCardAuthentication, boolean flag) {
        if (AuthenticationStatus.SUCCESS.getValue().equals(merchantBankCardAuthentication.getAuthenticationStatus())) {
            successMerchnat(merchantBankCardAuthentication);
        }
        if (StringUtils.isNotBlank(merchantBankCardAuthentication.getBankNo())) {
            String bankNo = Aes.encryptStr(merchantBankCardAuthentication.getBankNo(), Constants.QuickPay.SYSTEM_KEY);
            // 获取银行卡信息
            MerchantBankCard merchantBankCardFind = merchantBankCardDao
                    .getMerchant(merchantBankCardAuthentication.getMerchantId());
            if (!bankNo.equals(merchantBankCardFind.getBankNo())) {
                MerchantBankCard merchantBankCard = new MerchantBankCard();
                merchantBankCard.setMerchantId(merchantBankCardAuthentication.getMerchantId());
                merchantBankCard.setBankNo(bankNo);
                merchantBankCardDao.updateBankNo(merchantBankCard);
            }
        }
        super.save(merchantBankCardAuthentication, flag);
    }

    /**
     * @discription 商户认证成功
     * @author ly
     * @created 2016年10月11日 下午6:36:30
     * @param merchantBankCardAuthentication
     */
    @Transactional(readOnly = false)
    public void successMerchnat(MerchantBankCardAuthentication merchantBankCardAuthentication) {
        // 修改商户类型
        MerchantUser user = new MerchantUser();
        user.setId(merchantBankCardAuthentication.getMerchantId());
        user.setUserType(UserType.MERCHANT.getValue());
        merchantUserDao.update(user);
        // 修改银行卡状态
        MerchantBankCard merchantBankCard = new MerchantBankCard();
        merchantBankCard.setMerchantId(merchantBankCardAuthentication.getMerchantId());
        merchantBankCard.setStatus(BankcardAuthStatus.SUCCES.getValue());
        merchantBankCardDao.statusCard(merchantBankCard);
        // 开通账号
        Merchant merchant = merchantDao.get(merchantBankCardAuthentication.getMerchantId());
        merchantAccountService.createMerchanAccount(Long.valueOf(merchant.getUserId()), merchant.getEmail(),
                merchant.getCompanyName(), MerchantAccountType.MERCHANT_ACCOUNT.getValue(), merchant.getSource());
        // 修改商户最终状态
        merchant.setStatus(AuthenticationStatus.SUCCESS.getValue());
        merchantDao.rcAuditStatus(merchant);
        // 写缓存(商户信息，默认开通充值CP01提现CP05退款CP04 费率)
        RedisUtil.getRedisUtil().saveDefaultProductVORedis(merchant.getUserId().toString());// 开通默认产品
        RedisUtil.getRedisUtil().saveMerchantVoRedis(merchant.getUserId().toString());// 商户信息
    }

    @Override
    public MerchantBankCardAuthentication getByMerchantId(
            MerchantBankCardAuthentication merchantBankCardAuthentication) {
        return merchantBankCardAuthenticationDao.getByMerchantId(merchantBankCardAuthentication);
    }

    @Transactional(readOnly = false)
    public void statusAut(MerchantBankCardAuthentication merchantBankCardAuthentication) {
        if (AuthenticationStatus.SUCCESS.getValue().equals(merchantBankCardAuthentication.getAuthenticationStatus())) {
            successMerchnat(merchantBankCardAuthentication);
        }
        merchantBankCardAuthenticationDao.statusAut(merchantBankCardAuthentication);
    }

}