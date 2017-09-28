package com.heepay.manage.modules.merchant.service.check;

import com.heepay.enums.CommonStatus;
import com.heepay.enums.RateBusinessType;
import com.heepay.manage.common.cache.RedisUtil;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.modules.merchant.dao.MerchantProductRateDao;
import com.heepay.manage.modules.merchant.dao.MerchantRateBankDao;
import com.heepay.manage.modules.merchant.dao.check.MerchantProductRateCheckDao;
import com.heepay.manage.modules.merchant.dao.check.MerchantRateBankCheckDao;
import com.heepay.manage.modules.merchant.service.MerchantProductRateCService;
import com.heepay.manage.modules.merchant.service.MerchantRateBankCService;
import com.heepay.manage.modules.merchant.vo.MerchantProductRate;
import com.heepay.manage.modules.merchant.vo.MerchantRateBank;
import com.heepay.manage.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 *
 *
 * 描    述：商家产品费率记录Service
 *
 * 创 建 者： wangl
 * 创建时间： 2017-08-02 18:20
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
@Transactional
public class MerchantProductRateCheckService extends CrudService<MerchantProductRateCheckDao, MerchantProductRate> {

    @Autowired
    private MerchantProductRateCheckDao merchantProductRateCheckDao;

    @Autowired
    private MerchantRateBankCheckDao merchantRateBankCheckDao;

    @Autowired
    private MerchantProductRateDao merchantProductRateDao;

    @Autowired
    private MerchantRateBankDao merchantRateBankDao;


    /**
     * @方法说明：费率审核
     * @时间： 2017-08-08 09:56
     * @创建人：wangl
     */
    public List<MerchantProductRate> getListByPage(MerchantProductRate entity) {

        return merchantProductRateCheckDao.getListByPage(entity);
    }

    /**
     * @方法说明：更新操作
     * @时间： 2017-08-08 13:19
     * @创建人：wangl
     */
    public int updateEntity(MerchantProductRate merchantProductRate) {
       return merchantProductRateCheckDao.update(merchantProductRate);
    }

    /**
     * @方法说明：费率审核操作
     * @时间： 2017-08-08 13:44
     * @创建人：wangl
     */
    public void beachAndInsert(MerchantProductRate merchantProductRate) {

        String id = merchantProductRate.getId();
        logger.info("费率审核管理--->{费率审核}--->{条件}"+id);
        int num = merchantProductRateDao.countNum(id);

        if(merchantProductRate.getRateAudit().equals("Y")){ //审核通过
            MerchantProductRate  productRate = merchantProductRateCheckDao.get(id);
            productRate.setRateAudit("Y");
            List<MerchantRateBank> bankCardType = merchantRateBankCheckDao.getEntityByRateId(id);
            if(num > 0){ //更新操作
                merchantProductRateDao.update(productRate);
                if(bankCardType.size() > 0){
                    merchantRateBankDao.beachInsertAndUpdate(bankCardType);
                }
            }else{ //新增操作
                merchantProductRateDao.insert(productRate);
                if(bankCardType.size() > 0){
                    merchantRateBankDao.beachInsert(bankCardType);
                }
            }
            
            String businessType = productRate.getBusinessType();
            if(!Constants.IS_CHECK_YES.equals(DictUtils.getDictValue(Constants.IS_BUSINESSTYPE,Constants.SYS_COMMON,""))){
                businessType = RateBusinessType.INTERNETPAY.getValue();
            }
            // 修改商家费率缓存
            RedisUtil.getRedisUtil().saveMerchantProductRedis(productRate.getMerchantId(),productRate.getProductCode(), true, false, businessType);
        }
        merchantProductRateCheckDao.update(merchantProductRate);
        if(merchantProductRate.getRateAudit().equals("N")){
            //审核不通过 如果是新增的删除新增的数据
            if(num < 1){
                MerchantRateBank merchantRateBank = new MerchantRateBank();
                merchantRateBank.setRateId(merchantProductRate.getId());
                merchantRateBankCheckDao.deleteByRateId(merchantRateBank);
                merchantProductRateCheckDao.delete(merchantProductRate);
            }
        }
    }
}