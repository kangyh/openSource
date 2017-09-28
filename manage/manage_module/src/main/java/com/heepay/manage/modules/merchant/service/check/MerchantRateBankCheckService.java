package com.heepay.manage.modules.merchant.service.check;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.dao.check.MerchantRateBankCheckDao;
import com.heepay.manage.modules.merchant.vo.MerchantRateBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/***
 *
 *
 * 描    述：商户费率详情记录表Dao
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
public class MerchantRateBankCheckService extends CrudService<MerchantRateBankCheckDao, MerchantRateBank> {

    @Autowired
    private MerchantRateBankCheckDao merchantRateBankCheckDao;


    /**
     * @方法说明：更新操作
     * @时间： 2017-08-03 10:21
     * @创建人：wangl
     */
    public int updateEntity(MerchantRateBank record) {
        return merchantRateBankCheckDao.updateRateFee(record);
    }
}
