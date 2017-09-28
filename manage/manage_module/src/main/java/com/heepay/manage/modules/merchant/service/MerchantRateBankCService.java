/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.MerchantRateBank;
import com.heepay.manage.modules.merchant.vo.MerchantRateNew;

/**
 *
 * 描    述：商户费率银行Service
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
public interface MerchantRateBankCService{

	public MerchantRateBank get(String id);
	
	public List<MerchantRateBank> findList(MerchantRateBank merchantRateBank);
	
	public Page<MerchantRateBank> findPage(Page<MerchantRateBank> page, MerchantRateBank merchantRateBank);
	
	public void save(MerchantRateBank merchantRateBank, boolean flag);
	
	public void delete(MerchantRateBank merchantRateBank);

    public List<MerchantRateBank> getBankCardType(String id);

	void batchSaveRate(String checkedstr, String somerateType, MerchantRateNew merchantRateNew);

	List<MerchantRateBank> getEntityByRateId(String rateId);
}
