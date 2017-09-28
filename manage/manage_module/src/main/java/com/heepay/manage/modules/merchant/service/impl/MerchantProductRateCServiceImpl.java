/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.service.impl;

import java.util.List;

import com.heepay.common.util.SendMailUtil;
import com.heepay.enums.RateBusinessType;
import com.heepay.manage.modules.merchant.dao.check.MerchantProductRateCheckDao;
import com.heepay.manage.modules.merchant.dao.check.MerchantRateBankCheckDao;
import com.heepay.manage.modules.notification.dao.NotificationEmailDao;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.cache.RedisUtil;
import com.heepay.manage.common.enums.OperationType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.modules.merchant.dao.MerchantProductRateDao;
import com.heepay.manage.modules.merchant.dao.MerchantRateBankDao;
import com.heepay.manage.modules.merchant.dao.MerchantRateLogDao;
import com.heepay.manage.modules.merchant.dao.ProductDao;
import com.heepay.manage.modules.merchant.service.MerchantProductRateCService;
import com.heepay.manage.modules.merchant.vo.MerchantProductRate;
import com.heepay.manage.modules.merchant.vo.MerchantRateBank;
import com.heepay.manage.modules.merchant.vo.MerchantRateLog;
import com.heepay.manage.modules.merchant.vo.MerchantRateNew;
import com.heepay.manage.modules.merchant.vo.Product;

/**
 *
 * 描 述：商户产品费率Service
 *
 * 创 建 者： @author ly
 * 创建时间：
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
@Service
@Transactional(readOnly = true)
public class MerchantProductRateCServiceImpl extends CrudService<MerchantProductRateDao, MerchantProductRate> implements MerchantProductRateCService {

    @Autowired
    private MerchantProductRateDao merchantProductRateDao;

    @Autowired
    private MerchantProductRateCheckDao merchantProductRateCheckDao;

    @Value("#{task['update.rate.email']}")
    private String environment;
    @Autowired
    private MerchantRateBankDao merchantRateBankDao;

    @Autowired
    private MerchantRateBankCheckDao merchantRateBankCheckDao;

    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private MerchantRateLogDao merchantRateLogDao;

    @Autowired
    private NotificationEmailDao notificationEmailDao;

    public Page<MerchantRateNew> findPageNotDefaul(Page<MerchantRateNew> page, MerchantRateNew merchantRateNew) {
        merchantRateNew.setPage(page);
        page.setList(merchantProductRateDao.findPageNotDefaul(merchantRateNew));
        return page;
    }


    /**
     * @方法说明：费率审核
     * @时间： 2017-06-05 02:05 PM
     * @创建人：wangl
     */
    @Override
    public List<MerchantProductRate> getListByPage(MerchantProductRate merchantProductRate) {
        return merchantProductRateDao.getListByPage(merchantProductRate);
    }

    @Transactional(readOnly = false)
    public int updateEntity(MerchantProductRate merchantProductRate) {
        int update = merchantProductRateDao.update(merchantProductRate);
        MerchantProductRate productRate = merchantProductRateDao.get(merchantProductRate.getId());
        String businessType = productRate.getBusinessType();
        if(!Constants.IS_CHECK_YES.equals(DictUtils.getDictValue(Constants.IS_BUSINESSTYPE,Constants.SYS_COMMON,""))){
            businessType = RateBusinessType.INTERNETPAY.getValue();
        }
        RedisUtil.getRedisUtil().saveMerchantProductRedis(productRate.getMerchantId(),productRate.getProductCode(), false, false, businessType);
        return update;
    }

    public Page<MerchantRateNew> findPageDefaul(Page<MerchantRateNew> page, MerchantRateNew merchantRateNew) {
        merchantRateNew.setPage(page);
        page.setList(merchantProductRateDao.findPageDefaul(merchantRateNew));
        return page;
    }

    public MerchantProductRate exist(MerchantProductRate merchantProductRate) {
        return merchantProductRateDao.exist(merchantProductRate);
    }

    @Transactional(readOnly = false)
    public void status(MerchantProductRate merchantProductRate) {
        merchantProductRateDao.status(merchantProductRate);
    }
    
      
    /**     
    * @discription 获取商户签约产品
    * @author ly     
    * @created 2016年12月8日 下午7:41:44     
    * @param merchantId
    * @return     
    */
    public List<Product> getMerchantProduct(String merchantId){
        List<MerchantProductRate> findMerchantProductAndRate = merchantProductRateDao.findMerchantProduct(merchantId);
        List<String> merchantDefaultProduct = Lists.newArrayList();
        merchantDefaultProduct.add("CP01");// 充值
        merchantDefaultProduct.add("CP04");// 提现
        merchantDefaultProduct.add("CP05");// 退款
        List<Product> products = Lists.newArrayList();
        if(null != findMerchantProductAndRate && !findMerchantProductAndRate.isEmpty()){
            for(MerchantProductRate merchantProductRate : findMerchantProductAndRate){
                Product product = productDao.selectByCode(merchantProductRate.getProductCode());
                merchantDefaultProduct.remove(merchantProductRate.getProductCode());
                products.add(product);
            }
        }
        if(!merchantDefaultProduct.isEmpty()){
            for(String code : merchantDefaultProduct){
                Product product = productDao.selectByCode(code);
                products.add(product);
            }
        }
        return products;
    }


    @Transactional(readOnly = false)
    public void delete(MerchantProductRate merchantProductRate){
        MerchantRateBank merchantRateBank = new MerchantRateBank();
        merchantRateBank.setRateId(merchantProductRate.getId());
        //删除merchant_rate_bank表
        merchantRateBankDao.deleteByRateId(merchantRateBank);
        //删除merchant_rate_bank_check表
        merchantRateBankCheckDao.deleteByRateId(merchantRateBank);
        //删除merchant_product_rate_check表
        merchantProductRateCheckDao.delete(merchantProductRate);
        //删除merchant_product_rate表
        super.delete(merchantProductRate);
    }
    
    @Transactional(readOnly = false)
    public void save(MerchantProductRate entity, boolean flag) {
        if (entity.getIsNewRecord()){
            entity.setUserOrDate();
            if(StringUtils.isBlank(entity.getId())){
                entity.setId(null);
            }
            merchantProductRateCheckDao.insert(entity);
            toSaveMerchantRateLog(entity,OperationType.SAVEPRO.getValue());
        }else{
            entity.preUpdate();
            // 根据id查询当前数据库的这条记录
            MerchantProductRate merchantProductRate = merchantProductRateDao.get(entity.getId());
            // 对比结算周期是否变化，如果变化则发邮件通知
            if(!merchantProductRate.getSettleType().equals(entity.getSettleType())){
                List<String> emails = notificationEmailDao.queryList();
                String toEmail = listToString(emails, ',');
                String content = "用户(id:name)"+UserUtils.getUser().getId() +":"+ UserUtils.getUser().getName()+ "在时间："
                        + com.heepay.common.util.DateUtil.getTodayYYYYMMDD_HHMMSS() + "修改了" +"产品名为《"+merchantProductRate.getProductName()
                        +"》产品id为"+merchantProductRate.getId()+"的结算周期"+"修改前为："+ merchantProductRate.getSettleType() + "修改后为" + entity.getSettleType();
                boolean isSuccess = SendMailUtil.getInstance().sendMail(toEmail,environment + "结算周期修改通知",content,null);
                logger.info("结算周期修改发送邮件通知结果{}。发送内容为{}",isSuccess,content);
            }
            merchantProductRateCheckDao.update(entity);
            toSaveMerchantRateLog(entity,OperationType.UPDATEPRO.getValue());
        }
        String businessType = entity.getBusinessType();
        if(!Constants.IS_CHECK_YES.equals(DictUtils.getDictValue(Constants.IS_BUSINESSTYPE,
                Constants.SYS_COMMON,""))){
            businessType = RateBusinessType.INTERNETPAY.getValue();
        }
        if(!entity.getMerchantId().equals(Constants.MERCHANT_DEFAULT_ID)){
            RedisUtil.getRedisUtil().saveMerchantProductRedis(entity.getMerchantId(), entity.getProductCode(),
                    true, false, businessType);
        }
    }
    
      
    /**     
    * @discription 保存MerchantRateLog
    * @author ly     
    * @created 2016年12月23日 下午5:06:14     
    * @param merchantProductRate
    * @param
    * @param operationType     
    */
    private void toSaveMerchantRateLog(MerchantProductRate merchantProductRate,String operationType) {
        MerchantRateLog merchantRateLog = toChangeMerchantRateLog(merchantProductRate,operationType);
        merchantRateLog.setId(null);
        merchantRateLogDao.insert(merchantRateLog);
    }

      
    /**     
    * @discription 转换MerchantRateLog
    * @author ly     
    * @created 2016年12月23日 下午5:09:28     
    * @param merchantProductRate
    * @param operationType
    * @return     
    */
    private MerchantRateLog toChangeMerchantRateLog(MerchantProductRate merchantProductRate, String operationType) {
        MerchantRateLog merchantRateLog = new MerchantRateLog();
        merchantRateLog.setAutographKey(merchantProductRate.getAutographKey());
        merchantRateLog.setAutographType(merchantProductRate.getAutographType());
        merchantRateLog.setBackUrl(merchantProductRate.getBackUrl());
        merchantRateLog.setBusinessType(merchantProductRate.getBusinessType());
        merchantRateLog.setChargeCollectionType(merchantProductRate.getChargeCollectionType());
        merchantRateLog.setChargeDeductType(merchantProductRate.getChargeDeductType());
        merchantRateLog.setChargeSource(merchantProductRate.getChargeSource());
        merchantRateLog.setIpDomain(merchantProductRate.getIpDomain());
        merchantRateLog.setIsRefundable(merchantProductRate.getIsRefundable());
        merchantRateLog.setMerchantId(merchantProductRate.getMerchantId());
        merchantRateLog.setNotifyUrl(merchantProductRate.getNotifyUrl());
        merchantRateLog.setProductCode(merchantProductRate.getProductCode());
        merchantRateLog.setProductName(merchantProductRate.getProductName());
        merchantRateLog.setRateId(merchantProductRate.getId());//merchant_product_rate表Id
        merchantRateLog.setRuleBeginTime(merchantProductRate.getRuleBeginTime());
        merchantRateLog.setRuleEndTime(merchantProductRate.getRuleEndTime());
        merchantRateLog.setSettlementTo(merchantProductRate.getSettlementTo());
        merchantRateLog.setSettleType(merchantProductRate.getSettleType());
        merchantRateLog.setStatus(merchantProductRate.getStatus());
        merchantRateLog.setOperationType(operationType);
        merchantRateLog.setUserOrDate();
        return merchantRateLog;
    }

    /**
     * 把list变成用 ,链接的字符串
     * @param list          list
     * @param separator     分隔符
     * @return
     */
    private String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        // 没配置发给ngp@9186.com
        if(list.isEmpty()){
            return "ngp@9186.com";
        }else{
            return sb.toString().substring(0, sb.toString().length() - 1);
        }
    }
}