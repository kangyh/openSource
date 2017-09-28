/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.service;

import java.util.List;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.vo.MerchantProductRate;
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
public interface MerchantProductRateCService {

    public MerchantProductRate get(String id);

    public List<MerchantProductRate> findList(MerchantProductRate merchantProductRate);

    public Page<MerchantProductRate> findPage(Page<MerchantProductRate> page, MerchantProductRate merchantProductRate);

    public void save(MerchantProductRate merchantProductRate, boolean flag);

    public void delete(MerchantProductRate merchantProductRate);

    public Page<MerchantRateNew> findPageNotDefaul(Page<MerchantRateNew> page, MerchantRateNew merchantRateNew);

    public Page<MerchantRateNew> findPageDefaul(Page<MerchantRateNew> page, MerchantRateNew merchantRateNew);

    public MerchantProductRate exist(MerchantProductRate merchantProductRateExist);

    public void status(MerchantProductRate merchantProductRate);

    public List<Product> getMerchantProduct(String string);

    /**
     * @方法说明：费率审核分页
     * @时间： 2017-06-05 02:02 PM
     * @创建人：wangl
     */
    List<MerchantProductRate> getListByPage(MerchantProductRate entity);

    /**
     * @方法说明：保存入库
     * @时间： 2017-06-05 02:02 PM
     * @创建人：wangl
     */
    public int updateEntity(MerchantProductRate merchantProductRate);
}