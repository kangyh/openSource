/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.MerchantRateBank;

/**
 *
 * 描    述：商户费率银行DAO接口
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
@MyBatisDao
public interface MerchantRateBankDao extends CrudDao<MerchantRateBank> {

    List<MerchantRateBank> getBankCardType(@Param("rateId")String id);
    
      
    /**     
    * @discription 根据rateId删除
    * @author ly     
    * @created 2016年12月19日 下午5:23:17     
    * @param merchantRateBank
    * @return     
    */
    public int deleteByRateId(MerchantRateBank merchantRateBank);

    /**
     * 获取MerchantRateBank
     */
    MerchantRateBank getRateBank(MerchantRateBank merchantRateBank);

    int updateRateFee(MerchantRateBank merchantRateBank);

    /**
     * @方法说明：查询整个对象
     * @时间： 2017-06-05 03:39 PM
     * @创建人：wangl
     */
    List<MerchantRateBank> getEntityByRateId(String rateId);

    List<MerchantRateBank> selectInfoByBankCardType(MerchantRateBank merchantRateBank);

    void beachInsert(List<MerchantRateBank> list);

    void beachInsertAndUpdate(List<MerchantRateBank> bankCardType);
}
