/**
 *  
 */
package com.heepay.manage.modules.merchant.service.impl;

import com.heepay.enums.*;
import com.heepay.manage.modules.merchant.utils.MerhchantExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.cache.RedisUtil;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.modules.account.service.MerchantAccountService;
import com.heepay.manage.modules.merchant.dao.MerchantBankCardDao;
import com.heepay.manage.modules.merchant.dao.MerchantDao;
import com.heepay.manage.modules.merchant.dao.MerchantUserDao;
import com.heepay.manage.modules.merchant.service.MerchantBankCardAuthenticationCService;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantBankCard;
import com.heepay.manage.modules.merchant.vo.MerchantBankCardAuthentication;
import com.heepay.manage.modules.merchant.vo.MerchantUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * 描 述：商户CService
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
public class MerchantCServiceImpl extends CrudService<MerchantDao, Merchant> implements MerchantCService {

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private MerchantBankCardDao merchantBankCardCService;

    @Autowired
    private MerchantBankCardAuthenticationCService merchantBankCardAuthenticationCService;

    @Autowired
    private MerchantUserDao merchantUserCService;

    @Autowired
    private MerchantAccountService merchantAccountService;

    @Autowired
    private MerhchantExcel merhchantExcel;

    @Transactional(readOnly = false)
    public void save(Merchant merchant, boolean flag) {
        super.save(merchant, flag);
        // 写缓存(商户信息)
        RedisUtil.getRedisUtil().saveMerchantVoRedis(merchant.getUserId().toString());
    }

    @Transactional(readOnly = false)
    public void status(Merchant merchant) {
        merchantDao.status(merchant);
    }

    @Transactional(readOnly = false)
    public void legalAuditStatus(Merchant merchant) {
        merchant.preUpdate();
        merchantDao.legalAuditStatus(merchant);
    }

    @Transactional(readOnly = false)
    public String rcAuditStatus(Merchant merchant) {
        merchant.preUpdate();
        if ("PAY".equals(merchant.getRcAuditStatus())) {
            // 保存打款信息
            // 获取银行卡信息
            MerchantBankCard merchantBankCard = merchantBankCardCService.getMerchant(merchant.getUserId().toString());
            // 构造打款信息
            MerchantBankCardAuthentication merchantBankCardAuthentication = new MerchantBankCardAuthentication();
            merchantBankCardAuthentication.setMerchantId(merchant.getUserId().toString());
            MerchantBankCardAuthentication merchantBankCardAuthenticationExist = merchantBankCardAuthenticationCService
                    .getByMerchantId(merchantBankCardAuthentication);
            if(null != merchantBankCardAuthenticationExist){
                merchantBankCardAuthentication.setId(merchantBankCardAuthentication.getId()); 
            }
            String bankNo = Aes.decryptStr(merchantBankCard.getBankNo(), Constants.QuickPay.SYSTEM_KEY);
            merchantBankCardAuthentication.setBankNo(bankNo);
            merchantBankCardAuthentication.setOpenBankName(merchantBankCard.getOpenBankName());
            merchantBankCardAuthentication.setMerchantCompanyName(merchant.getCompanyName());
            merchantBankCardAuthentication.setLegalRepresentative(merchant.getLegalRepresentative());
            merchantBankCardAuthentication.setPayStatus(RemitStatus.REMIT.getValue());
            merchantBankCardAuthenticationCService.save(merchantBankCardAuthentication, false);
            // 修改风控状态为成功
            merchant.setRcAuditStatus(RouteStatus.AUDIT_SUCCESS.getValue());
            merchantDao.rcAuditStatus(merchant);
            return "redirect:" + Global.getAdminPath() + "/merchant/merchantBankCardAuthentication/list?merchantId="
                    + merchant.getUserId();
        } else if ("SUCCES".equals(merchant.getRcAuditStatus())) {
            merchant.setStatus(AuthenticationStatus.SUCCESS.getValue());
            // 修改商户类型
            MerchantUser user = new MerchantUser();
            user.setId(merchant.getUserId().toString());
            user.setUserType(UserType.MERCHANT.getValue());
            merchantUserCService.update(user);
            // 修改银行卡状态
            MerchantBankCard merchantBankCard = new MerchantBankCard();
            merchantBankCard.setMerchantId(merchant.getUserId().toString());
            merchantBankCard.setStatus(BankcardAuthStatus.SUCCES.getValue());
            merchantBankCardCService.statusCard(merchantBankCard);
            // 开通账号
            Merchant merchantEmail = merchantDao.get(merchant.getUserId().toString());
            merchantAccountService.createMerchanAccount(Long.valueOf(merchant.getUserId()), merchantEmail.getEmail(),
                    merchantEmail.getCompanyName(), MerchantAccountType.MERCHANT_ACCOUNT.getValue(), merchantEmail.getSource());
            // 写缓存(商户信息，默认开通充值CP01提现CP05退款CP04 费率)
            RedisUtil.getRedisUtil().saveDefaultProductVORedis(merchant.getUserId().toString());// 开通默认产品
            RedisUtil.getRedisUtil().saveMerchantVoRedis(merchant.getUserId().toString());// 商户信息
        }
        merchantDao.rcAuditStatus(merchant);
        return "redirect:" + Global.getAdminPath() + "/merchant/merchantRc?cache=1&repage";
    }

    public String getMerchantPosCode(String userId, String mcc) {
        String merchantPosCode = "";
        Merchant merchant = new Merchant();
        merchant.setUserId(Integer.valueOf(userId));
        merchant = super.get(merchant);
        String bankId = com.heepay.manage.common.utils.Constants.MERCHANT_POS_CODE_ACQUIRING_BANK;
        String cityCode = merchant.getCityCode();
        if (StringUtils.isNotBlank(cityCode)) {
            merchantPosCode = bankId + cityCode.substring(0, 4) + mcc;
            Integer count = merchantDao.count(merchantPosCode);
            String code = RandomUtil.getFourNumber(count);
            merchantPosCode = merchantPosCode + code;
        }
        return merchantPosCode;
    }

    @Override
    public void exportExcel(Merchant merchant, HttpServletRequest request, HttpServletResponse response) {
        //数据库查询结果
        List<Map<String,Object>> excelMap = merchantDao.findExcel(merchant);

        //导出表格对应的字段名称
        String[] showField = {"index","userId","companyName","merchantStatus","website","mccServer","mccDetail","contactor","contactorPhone","inchargerId",
                "agencyCompanyName","settleType","businessType","productName","chargeType","fee","minFee","maxFee","retainedAmount","protocolNumber",
                "ruleBeginTime","ruleEndTime","remark"
        };
        merhchantExcel.exportExcel("商户信息",excelMap,showField,response,request);
    }

    @Override
    public Merchant selectMerchant(Merchant merchant){
        return dao.selectMerchant(merchant);
    }

    @Override
    public List<Merchant> queryInternalMerchantsList(List<String> ids){
        return dao.queryInternalMerchantsList(ids);
    }

    @Override
    public List<Merchant> queryInternalMerchantsByFlag() {
        return dao.queryInternalMerchantsByFlag();
    }

    @Transactional(readOnly = false)
    public void updateAuth(String merchantId, String status, String s) {
        merchantDao.updateAuth(merchantId,status,s);
    }

    /**
     * @方法说明：手机实名认证
     * @时间： 24/07/2017 14:00
     * @创建人：wangl
     */
    @Transactional(readOnly = false)
    public void updatePhoneAuth(String merchantId, String status, String s) {
        merchantDao.updatePhoneAuth(merchantId,status,s);
    }
}