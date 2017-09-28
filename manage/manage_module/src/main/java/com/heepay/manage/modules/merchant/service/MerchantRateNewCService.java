/**
 *  
 */
package com.heepay.manage.modules.merchant.service;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.MerchantRateDefault;
import com.heepay.manage.modules.merchant.vo.MerchantRateNew;
import com.heepay.manage.modules.merchant.vo.MerchantRateNewVo;

/**
 * 商家费率Service
 * 
 * @author ly
 * @version V1.0
 */
public interface MerchantRateNewCService {

    public MerchantRateNew get(String id);

    public List<MerchantRateNew> findList(MerchantRateNew merchantRate);

    public Page<MerchantRateNew> findPage(Page<MerchantRateNew> page, MerchantRateNew merchantRate);

    public String saveRate(MerchantRateNew merchantRateNew, boolean flag);

    public void delete(MerchantRateNew merchantRate);

    public void status(MerchantRateNew merchantRate);

    public Page<MerchantRateNew> findPageDefaul(Page<MerchantRateNew> page, MerchantRateNew merchantRate);

    public MerchantRateDefault getDefault(MerchantRateNew merchantRate);

    public MerchantRateNew edit(MerchantRateNew merchantRateNew);

    public Page<MerchantRateNew> findPageMerchant(Page<MerchantRateNew> page, MerchantRateNew merchantRate);

    public MerchantRateNew existDefault(MerchantRateNew merchantRateNew);

    public List<MerchantRateNew> existNoBank(MerchantRateNewVo merchantRateNewVo);

    public String saveVo(MerchantRateNewVo merchantRateNewVo, boolean b);

    public Map<String,String> isOverCost(MerchantRateNew merchantRateNew);

}