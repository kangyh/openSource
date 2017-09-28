/**
 *  
 */
package com.heepay.manage.modules.merchant.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.MerchantBankCard;

/**
 * merchantDAO接口
 * @author ly
 * @version V1.0
 */
@MyBatisDao
public interface MerchantBankCardDao extends CrudDao<MerchantBankCard> {
  void status(MerchantBankCard merchantBankCard);
  
  MerchantBankCard getMerchant(String merchantId);
  
  void statusCard(MerchantBankCard merchantBankCard);
  
  void updateBankNo(MerchantBankCard merchantBankCard);

	/**
	 * @方法说明：查询银行卡
	 * @时间：14 Feb 201709:04:23
	 * @创建人：wangl
	 */
  List<MerchantBankCard> findPageList(MerchantBankCard merchantBankCard);

    /**
     * @方法说明：资金归集商户同步银行卡信息
     * @时间：2017年3月29日22:11:09
     * @创建人：guozx@9186.com
     * @param merchantBankCard
     */
    void synchronizeBankCard(MerchantBankCard merchantBankCard);
}