package com.heepay.manage.common.cache;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.vo.MerchantProductVO;
import com.heepay.vo.ProductFeeVO;

/**
 * 描述：
 * <p>
 * 创建者  ly
 * 创建时间 2017-04-10-11:25
 * 创建描述：
 * <p>
 * 审核者：
 * 审核时间：
 * 审核描述：
 * <p>
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
public class PersonRedis {

    private static PersonRedis personRedis = null;

    public static synchronized PersonRedis getRedisUtil() {
        if (personRedis == null) {
            personRedis = new PersonRedis();
        }
        return personRedis;
    }

    private PersonRedis() {
    }

    private JsonMapperUtil mapper = new JsonMapperUtil();

    private static PropertiesLoader loader = new PropertiesLoader("usercenter.properties");


    public String getPersonFeeVo(String merchantId, String productCode, String bankCardType, String bankNo){
        ProductFeeVO productFeeVO = new ProductFeeVO();
        productFeeVO.setFeeType(loader.getProperty("GRTX.feeType"));
        productFeeVO.setFeeRatio(loader.getProperty("GRTX.feeRatio"));
        productFeeVO.setMinFee(loader.getProperty("GRTX.minFee"));
        productFeeVO.setMaxFee(loader.getProperty("GRTX.maxFee"));
        return mapper.toJson(productFeeVO);
    }

    public String getPersonProductVo(String merchantId, String productCode) {
        MerchantProductVO merchantProductVO = new MerchantProductVO();
        merchantProductVO.setSettleCyc(loader.getProperty("GRCZ.settleCyc"));
        return mapper.toJson(merchantProductVO);
    }
}
