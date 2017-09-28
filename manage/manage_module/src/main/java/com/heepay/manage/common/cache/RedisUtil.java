package com.heepay.manage.common.cache;

import java.math.BigDecimal;
import java.util.List;

import com.heepay.codec.Md5;
import com.heepay.enums.*;
import com.heepay.manage.modules.merchant.vo.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.heepay.common.util.Constants;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.utils.DateUtil;
import com.heepay.manage.common.utils.SpringContextHolder;
import com.heepay.manage.modules.merchant.dao.MerchantDao;
import com.heepay.manage.modules.merchant.dao.MerchantProductRateDao;
import com.heepay.manage.modules.merchant.dao.MerchantProductRedisDao;
import com.heepay.manage.modules.merchant.dao.MerchantRateNewDao;
import com.heepay.manage.modules.merchant.dao.ProductDao;
import com.heepay.manage.modules.route.dao.ProductDetailDao;
import com.heepay.manage.modules.route.entity.ProductDetail;
import com.heepay.redis.JedisClusterUtil;
import com.heepay.vo.MerchantProductVO;
import com.heepay.vo.MerchantVO;
import com.heepay.vo.ProductFeeVO;
import com.heepay.vo.ProductVO;

import redis.clients.jedis.JedisCluster;

/**
 * 
 * 描 述：存redis工具类
 *
 * 创 建 者： ly 创建时间： 2016年9月20日 下午3:28:20 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */

public class RedisUtil {
    private static RedisUtil redisUtil = null;

    public static synchronized RedisUtil getRedisUtil() {
        if (redisUtil == null) {
            redisUtil = new RedisUtil();
        }
        return redisUtil;
    }

    private RedisUtil() {
    }

    // 缓存有效时间分钟数
    private Integer exp = 30;

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();

    private JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();

    private JsonMapperUtil mapper = new JsonMapperUtil();

    private static MerchantRateNewDao merchantRateNewDao = SpringContextHolder.getBean(MerchantRateNewDao.class);
    private static MerchantProductRateDao merchantProductRateDao = SpringContextHolder
            .getBean(MerchantProductRateDao.class);
    private static MerchantProductRedisDao merchantProductRedisDao = SpringContextHolder
            .getBean(MerchantProductRedisDao.class);
    private static MerchantDao merchantDao = SpringContextHolder.getBean(MerchantDao.class);
    private static ProductDao productDao = SpringContextHolder.getBean(ProductDao.class);
    private static ProductDetailDao productDetailDao = SpringContextHolder.getBean(ProductDetailDao.class);
    /**
     * @discription 为获取商户的redis信息的接口提供的方法
     * @author ly
     * @created 2016年10月18日 下午5:58:07
     * @param merchantId
     *            商户id
     * @param productCode
     *            产品code
     * @param bankCardType
     *            银行卡类型
     * @param bankNo
     *            银行NO.
     * @param which
     *            选择的方法
     * @return
     */
    public String merchantRedis(String merchantId, String productCode, String bankCardType, String bankNo,
            String money, String businessType, String which) {
        String json = "";
        logger.info("获取{}信息,商户ID{} 产品编码{} 卡类型{} 银行ID{} 金额{} 业务类型{}",
                which,merchantId, productCode, bankCardType, bankNo, money, businessType);
        if ("merchant".equals(which)) {
            // 获取商户缓存
            json = jedisCluster.get(Constants.RedisKey.MERCHANTVO_REDIS_KEY + merchantId);
            if (StringUtils.isNotBlank(json)) {
                return json;
            }
            json = changeMerchantVO(merchantId);
            if (StringUtils.isNotBlank(json)) {
                redisSaveMerchant(merchantId, json);
            }
        } else if ("merchantProduct".equals(which)) {
            // 获取商户签约产品缓存
            json = jedisCluster.get(Constants.RedisKey.MERCHANTPRODUCTVO_REDIS_KEY + merchantId + productCode + businessType);
            if (StringUtils.isNotBlank(json)) {
                logger.info("获取{}缓存信息,商户ID{} 产品编码{} 卡类型{} 银行ID{} 金额{} 业务类型{},返回{}",
                        which,merchantId, productCode, bankCardType, bankNo, money,businessType,json);
                return json;
            }
            json = changeMerchantProductVO(merchantId, productCode, businessType);
            if (StringUtils.isNotBlank(json)) {
                redisSaveMerchantProduct(merchantId, productCode, json ,businessType);
            }
        } else if ("fee".equals(which)) {
            // 获取费率缓存
            json = getFeeRedis(merchantId, productCode, bankCardType, bankNo, money, businessType);
            if (StringUtils.isNotBlank(json)) {
                return json;
            }
            json = getRate(merchantId, productCode, bankCardType, bankNo, money, businessType);
        } else if ("product".equals(which)) {
            // 获取产品信息缓存
            json = jedisCluster.get(Constants.RedisKey.PRODUCT_REDIS_KEY + productCode);
            if (StringUtils.isNotBlank(json)) {
                return json;
            }
            json = getProductVO(productCode);
            if (StringUtils.isNotBlank(json)) {
                redisSaveProduct(productCode, json);
            }
        }
        if (StringUtils.isBlank(json)) {
            json = "";
        }
        logger.info("获取{}信息,商户ID{} 产品编码{} 卡类型{} 银行ID{} 金额{} 业务类型{},返回{}",
                which,merchantId, productCode, bankCardType, bankNo, money,businessType,json);
        return json;
    }

    /**
     * @discription 从缓存获取商户费率
     * @author ly
     * @created 2016年11月1日 下午1:19:29
     * @param merchantId
     * @param productCode
     * @param bankCardType
     * @param bankNo
     * @return
     */
    private String getFeeRedis(String merchantId, String productCode, String bankCardType, String bankNo,
                  String money, String businessType) {
        String json = "";
        // 获取费率缓存field
        String field = getField(productCode, bankNo, bankCardType);
        if (StringUtils.isNotBlank(field)) {
            // 获取商户费率缓存
            json = jedisCluster.hget(Constants.RedisKey.PRODUCT_FEE_PRIFIX + merchantId + productCode + businessType, field);
            if (StringUtils.isNotBlank(json)) {
                String returnJson = getRateJson(json,money);
                logger.info("获取商户缓存费率{}",returnJson);
                return returnJson;
            }
        }
        return json;
    }


    /**
     * @discription 从缓存获取默认费率
     * @author ly
     * @created 2016年11月1日 下午1:20:36
     * @param productCode
     * @param bankCardType
     * @return
     */
    private String getFeeDefaultRedis(String productCode, String bankCardType,String money) {
        String json;
        String merchantId = com.heepay.manage.common.utils.Constants.MERCHANT_DEFAULT_ID;
        // 获取默认费率缓存
        if (StringUtils.isNotBlank(bankCardType)) {
            json = jedisCluster.hget(Constants.RedisKey.PRODUCT_FEE_PRIFIX + merchantId + productCode,
                    Constants.RedisKey.PRODUCT_FEE_NOBANK + bankCardType);
        } else {
            json = jedisCluster.hget(Constants.RedisKey.PRODUCT_FEE_PRIFIX + merchantId + productCode,
                    Constants.RedisKey.PRODUCT_FEE_NOBANK);
        }
        if(StringUtils.isNotBlank(json)){
            String returnJson = getRateJson(json,money);
            return returnJson;
        }
        return json;
    }

    /**
     * @discription 存产品redis
     * @author ly
     * @created 2016年10月19日 下午4:35:22
     * @param productCode
     * @param json
     */
    private void redisSaveProduct(String productCode, String json) {
        jedisCluster.set(Constants.RedisKey.PRODUCT_REDIS_KEY + productCode, json);
    }

    /**
     * @discription 存商户redis
     * @author ly
     * @created 2016年10月19日 下午4:35:46
     * @param merchantId
     * @param json
     */
    private void redisSaveMerchant(String merchantId, String json) {
        jedisCluster.setex(Constants.RedisKey.MERCHANTVO_REDIS_KEY + merchantId,
                exp * com.heepay.manage.common.utils.Constants.ONE_MINUTE_SECOND, json);
    }

    /**
     * @discription 存商户签约产品redis
     * @author ly
     * @created 2016年10月19日 下午4:35:57
     * @param merchantId
     * @param productCode
     * @param json
     */
    private void redisSaveMerchantProduct(String merchantId, String productCode, String json, String businessType) {
        jedisCluster.setex(Constants.RedisKey.MERCHANTPRODUCTVO_REDIS_KEY + merchantId + productCode + businessType,
                exp * com.heepay.manage.common.utils.Constants.ONE_MINUTE_SECOND, json);
    }

    /**
     * @discription 存商户费率redis
     * @author ly
     * @created 2016年10月19日 下午4:36:09
     * @param merchantId
     * @param productCode
     * @param bankNo
     * @param bankCardType
     * @param json
     */
    private void redisSaveRate(String merchantId, String productCode, String bankNo, String bankCardType,
                String json, String businessType) {
        String field = getField(productCode, bankNo, bankCardType);
        if (StringUtils.isNotBlank(field)) {
            jedisCluster.hset(Constants.RedisKey.PRODUCT_FEE_PRIFIX + merchantId + productCode + businessType, field, json);
            jedisCluster.expire(Constants.RedisKey.PRODUCT_FEE_PRIFIX + merchantId + productCode + businessType,
                    exp * com.heepay.manage.common.utils.Constants.ONE_MINUTE_SECOND);
        }
    }

    /**
     * @discription 存默认费率redis
     * @author ly
     * @created 2016年10月19日 下午4:36:23
     * @param merchantId
     * @param productCode
     * @param json
     * @param bankCardType
     */
    private void redisSaveDefaultRate(String merchantId, String productCode, String json,
                String bankCardType, String businessType) {
        String field = getDefaultField(productCode, bankCardType);
        if (StringUtils.isNotBlank(field)) {
            jedisCluster.hset(Constants.RedisKey.PRODUCT_FEE_PRIFIX + merchantId + productCode + businessType, field, json);
            jedisCluster.expire(Constants.RedisKey.PRODUCT_FEE_PRIFIX + merchantId + productCode + businessType,
                    exp * com.heepay.manage.common.utils.Constants.ONE_MINUTE_SECOND);
        }
    }

    /**
     * @discription 保存产品信息redis
     * @author ly
     * @created 2016年10月18日 下午6:03:35
     * @param productCode
     *            产品code
     * @return
     */
    public boolean saveProductVoRedis(String productCode) {
        String json = getProductVO(productCode);
        redisSaveProduct(productCode, json);
        return true;
    }

    /**
     * @discription redis缓存(商户信息)
     * @author ly
     * @created 2016年10月12日 下午3:13:20
     * @param merchantId
     * @return
     */
    public boolean saveMerchantVoRedis(String merchantId) {
        String json = changeMerchantVO(merchantId);
        redisSaveMerchant(merchantId, json);
        return true;
    }

    /**
     * @discription redis缓存(商家费率)
     * @author ly
     * @created 2016年10月12日 下午3:20:01
     * @param merchantId
     * @param productCode
     * @return
     */
    public boolean saveProductFeeVORedis(String merchantId, String productCode, String businessType) {
        return saveRate(merchantId, productCode, businessType);
    }

    /**
     * @discription redis缓存(默认费率)
     * @author ly
     * @created 2016年10月12日 下午3:20:01
     * @param productCode
     * @return
     */
    public boolean saveDefaultFeeVORedis(String productCode, String businessType) {
        saveDefaultRate(productCode, businessType);
        return true;
    }

    /**
     * @discription 开通默认产品(充值，提现，退款)
     * @author ly
     * @created 2016年10月12日 下午3:23:07
     * @param merchantId
     * @return
     */
    public boolean saveDefaultProductVORedis(String merchantId) {
        String charge = "CP01";// 充值
        String withdraw = "CP05";// 提现
        String refund = "CP04";// 退款
        saveMerchantProductRedis(merchantId, charge, false, false, RateBusinessType.INTERNETPAY.getValue());
        saveMerchantProductRedis(merchantId, withdraw, false, false, RateBusinessType.INTERNETPAY.getValue());
        saveMerchantProductRedis(merchantId, refund, false, false, RateBusinessType.INTERNETPAY.getValue());
        return true;
    }

    /**
     * @discription redis缓存(产品，费率，默认费率)
     * @author ly
     * @created 2016年10月11日 下午9:24:20
     * @param merchantId
     *            商户ID
     * @param productCode
     *            产品
     * @param isChangeRate
     *            是否更新费率
     * @param isChangeDefaultRate
     *            是否更新默认费率
     * @return
     */
    public boolean saveMerchantProductRedis(String merchantId, String productCode, boolean isChangeRate,
            boolean isChangeDefaultRate, String businessType) {
        String json = changeMerchantProductVO(merchantId, productCode, businessType);
        if (StringUtils.isNotBlank(json)) {
            redisSaveMerchantProduct(merchantId, productCode, json, businessType);
        }
        if (isChangeRate) {
            // 修改费率的redis
            boolean flag = saveRate(merchantId, productCode, businessType);
            boolean flagDefault = true;
            if (isChangeDefaultRate) {
                flagDefault = saveDefaultRate(productCode, businessType);
            }
            return flag && flagDefault;
        }
        return true;
    }

    /**
     * @discription 获取产品信息
     * @author ly
     * @created 2016年10月18日 下午5:59:46
     * @param productCode
     * @return
     */
    private String getProductVO(String productCode) {
        Product product = productDao.selectByCode(productCode);
        if (null == product) {
            product = new Product();
        }
        ProductVO productVO = new ProductVO();
        productVO.setProductCode(product.getCode());
        productVO.setProductName(product.getName());
        if (CommonStatus.ENABLE.getValue().equals(product.getStatus())) {
            productVO.setProductStatus(ProductStatus.NORMAL.getValue());
        } else if (CommonStatus.DISABLE.getValue().equals(product.getStatus())) {
            productVO.setProductStatus(ProductStatus.CLOSE.getValue());
        } else {
            productVO.setProductStatus("");
        }
        return mapper.toJson(productVO);
    }

    /**
     * @discription 获取费率VO
     * @author ly
     * @created 2016年10月14日 下午3:36:16
     * @param merchantId
     *            商户Id
     * @param productCode
     *            产品code
     * @param bankCardType
     *            卡类型
     * @return
     */
    private String getRate(String merchantId, String productCode, String bankCardType, String bankNo,
                 String money, String businessType) {
        String json = "";
        //获取商户产品费率
        MerchantRateNew merchantRateFind = new MerchantRateNew();
        merchantRateFind.setMerchantId(merchantId);
        merchantRateFind.setProductCode(productCode);
        merchantRateFind.setBusinessType(businessType);
        //判断是否区分卡类型
        if(isDistinguishBankCardType(productCode)){
            merchantRateFind.setBankCardType(bankCardType);
        }
        merchantRateFind.setBankNo(bankNo);
        if("CP02".equals(productCode)){
            merchantRateFind.setBankNo("105");//转账只有建行
        }
        List<MerchantRateNew> merchantRateNews = merchantRateNewDao.findList(merchantRateFind);
        if (!merchantRateNews.isEmpty()) {
            ProductFeeVO productFeeVO = changeFeeVo(merchantRateNews.get(0),money);
            json = mapper.toJson(productFeeVO);
            logger.info("获取商户配置费率,商户ID{} 产品编码{} 卡类型{} 银行ID{} 业务类型{},返回{}",
                    merchantId, productCode, merchantRateFind.getBankCardType(), bankNo,businessType,json);
            saveRate(merchantId, productCode, businessType);
        } else {
            //根据产品code获取产品
            Product product = productDao.selectByCodeEnable(productCode);
            //获取商户默认费率
            switch (product.getTrxType()) {
            case "CHARGE":// 充值
            case "WZDRAW":// 提现
            case "RENAME":// 实名认证
            case "EPRISE"://企业认证
            case "AUTHEN"://鉴权认证
            case "SHARES":// 平级分润
            case "CACNCE":// 通关申报
            case "TRAFER"://内转
            case "WALLET"://钱包服务
            case "DPTWZD":// 存管提现
                json = getDefaultRate(productCode, bankCardType, merchantRateFind, money);
                break;
            case "BATPAY":// 转账
                merchantRateFind.setBankNo("105");//转账只有建行
                json = getBankDefaultRate(merchantRateFind,money);
                break;
            case "BATCOL":// 代收
                json = getBankDefaultRate(merchantRateFind,money);
                break;
            case "DPTPAY":// 存管支付
                merchantRateFind.setBankCardType("");
                json = getBankDefaultRate(merchantRateFind,money);
                break;
            case "PAYMNT":// 支付
                if(product.getName().indexOf(com.heepay.manage.common.utils.Constants.WECHAT) >= 0||
                        product.getName().indexOf(com.heepay.manage.common.utils.Constants.ALIPAY) >= 0 ||
                        product.getName().indexOf(com.heepay.manage.common.utils.Constants.UNOPAY) >= 0 ||
                        product.getName().indexOf(com.heepay.manage.common.utils.Constants.QUAGRP) >= 0){//微信支付宝银联量化派
                    json = getDefaultRate(productCode, bankCardType, merchantRateFind,money);
                }else if(product.getName().indexOf(com.heepay.manage.common.utils.Constants.QUICKPAY2) >= 0 ||
                product.getName().indexOf(com.heepay.manage.common.utils.Constants.B2B) >= 0){//快捷支付2
                    merchantRateFind.setBankCardType("");
                    json = getBankDefaultRate(merchantRateFind,money);
                }else{//其他 //B2B
                    json = getBankDefaultRate(merchantRateFind,money);
                }
                break;
            default:
                break;
            }
            logger.info("获取商户配置默认费率,商户ID{} 产品编码{} 卡类型{} 银行ID{} 业务类型{},返回{}",
                    merchantId, productCode, merchantRateFind.getBankCardType(), bankNo,businessType,json);
        }
        return json;
    }

    /**
     * 是否区分卡类型
     * ly 2017年4月6日11:38:38
     */
    public boolean isDistinguishBankCardType(String productCode) {
        //根据产品code获取产品
        Product product = productDao.selectByCodeEnable(productCode);
        if("DPTPAY".equals(product.getTrxType())){
            return false;
        }else if("PAYMNT".equals(product.getTrxType())){
            if(product.getName().indexOf(com.heepay.manage.common.utils.Constants.QUICKPAY2) >= 0 ||
                    product.getName().indexOf(com.heepay.manage.common.utils.Constants.B2B) >= 0){
                return false;
            }
        }
        return true;
    }


    /**     
    * @discription 获取没有银行默认费率
    * @author ly     
    * @created 2016年12月7日 上午11:09:59     
    * @param productCode
    * @param bankCardType
    * @param merchantRateFind
    * @return     
    */
    private String getDefaultRate(String productCode, String bankCardType, MerchantRateNew merchantRateFind,String money) {
        String json = "";
        ProductDetail productDetailFind = new ProductDetail();
        productDetailFind.setProductCode(merchantRateFind.getProductCode());
        productDetailFind.setCardTypeCode(merchantRateFind.getBankCardType());
        productDetailFind.setBankNo(merchantRateFind.getBankNo());
        //获取通道支持银行(包含银行卡类型,银行id)是否存在
        List<ProductDetail> productDetails = productDetailDao.getProductDetailBank(productDetailFind);
        Product product = productDao.selectByCode(productCode);
        boolean isProductDetails = isJudgeChannel(productDetails, product);
        if(isProductDetails){
            json = getFeeDefaultRedis(productCode, bankCardType, money);
            if (StringUtils.isBlank(json)) {
                merchantRateFind.setMerchantId(com.heepay.manage.common.utils.Constants.MERCHANT_DEFAULT_ID);
                merchantRateFind.setBankNo("");
                List<MerchantRateNew> merchantRateNewDefaults = merchantRateNewDao.findList(merchantRateFind);
                if (!merchantRateNewDefaults.isEmpty()) {
                    ProductFeeVO productFeeVO = changeFeeVo(merchantRateNewDefaults.get(0),money);
                    json = mapper.toJson(productFeeVO);
                    saveDefaultRate(productCode,merchantRateFind.getBusinessType());
                }
            }
        }
        return json;
    }

      
    /**     
    * @discription 是否判断通道支持银行
    * @author ly     
    * @created 2016年12月28日 下午7:02:11     
    * @param productDetails
    * @param product
    * @return     
    */
    public boolean isJudgeChannel(List<ProductDetail> productDetails, Product product) {
        boolean isProductDetails;
        //实名认证，平级分润，退款,通关申报,企业认证,鉴权认证不判断产品通道
        if("RENAME".equals(product.getTrxType()) || "SHARES".equals(product.getTrxType()) || "REFUND".equals(product.getTrxType())
                || "COSTOM".equals(product.getTrxType()) || "EPRISE".equals(product.getTrxType())|| "AUTHEN".equals(product.getTrxType())
                || "WALLET".equals(product.getTrxType())){
            isProductDetails = true;
        }else{
            isProductDetails = null != productDetails && !productDetails.isEmpty();
        }
        return isProductDetails;
    }

    /**     
     * @discription 获取有银行默认费率
     * @author ly     
     * @created 2016年12月7日 上午11:09:59
     * @param merchantRateFind
     * @return     
     */
    private String getBankDefaultRate(MerchantRateNew merchantRateFind,String money) {
        String json = "";
        ProductDetail productDetailFind = new ProductDetail();
        productDetailFind.setProductCode(merchantRateFind.getProductCode());
        productDetailFind.setCardTypeCode(merchantRateFind.getBankCardType());
        productDetailFind.setBankNo(merchantRateFind.getBankNo());
        //获取通道支持银行(包含银行卡类型,银行id)是否存在
        List<ProductDetail> productDetails = productDetailDao.getProductDetailBank(productDetailFind);
        if(null != productDetails && !productDetails.isEmpty()){
            //获取商户开通默认费率
            merchantRateFind.setBankNo("ALL");
            List<MerchantRateNew> merchantRateNewDefaults = merchantRateNewDao.findList(merchantRateFind);
            if (!merchantRateNewDefaults.isEmpty()) {
                MerchantRateNew merchantRateNewDefault = merchantRateNewDefaults.get(0);
                MerchantProductFeeVo merchantProductFeeVo = changeMerchangeProductFeeVo(merchantRateNewDefault);
                //保存商户默认缓存费率
                redisSaveRate(merchantRateFind.getMerchantId(), merchantRateFind.getProductCode(),
                        productDetailFind.getBankNo(), merchantRateFind.getBankCardType(),
                        mapper.toJson(merchantProductFeeVo),merchantRateFind.getBusinessType());
                ProductFeeVO productFeeVO = changeFeeVo(merchantRateNewDefault,money);
                json = mapper.toJson(productFeeVO);
            }
        }
        return json;
    }
    
    /**
     * @discription 转换MerchantVO
     * @author ly
     * @created 2016年10月14日 下午3:07:45
     * @param merchantId
     * @return
     */
    private String changeMerchantVO(String merchantId) {
        Merchant merchant = merchantDao.get(merchantId);
        if(null != merchant && RouteStatus.AUDIT_SUCCESS.getValue().equals(merchant.getStatus())){
            MerchantVO merchantVO = merchantDao.getMerchantVo(merchantId);
            return mapper.toJson(merchantVO);
        }
        return "";
    }

    /**
     * @discription 转换merchantProductVO
     * @author ly
     * @created 2016年10月14日 下午2:14:50
     * @param merchantId
     * @param productCode
     */
    private String changeMerchantProductVO(String merchantId, String productCode, String businessType) {
        String json = "";
        if (StringUtils.isNotBlank(productCode)) {
            Product product = productDao.selectByCode(productCode);
            if (null != product) {
                MerchantProductVO merchantProductVO = new MerchantProductVO();
                merchantProductVO.setMerchantId(merchantId);
                merchantProductVO.setProductCode(productCode);
                String productKey;
                switch (product.getTrxType()) {
                case "CHARGE":// 充值
                    productKey = Constants.RedisKey.CHARGE_PRODUCT_KEY + merchantId;
                    merchantProductVO.setProductKey(Md5.encode(productKey));
                    json = merchantProductDefaultRedis(merchantProductVO,product.getStatus(),businessType);
                    break;
                case "WZDRAW":// 提现
                    productKey = Constants.RedisKey.WITHDRAW_PRODUCT_KEY + merchantId;
                    merchantProductVO.setProductKey(Md5.encode(productKey));
                    json = merchantProductDefaultRedis(merchantProductVO,product.getStatus(),businessType);
                    break;
                case "REFUND":// 退款
                    productKey = Constants.RedisKey.REFUND_PRODUCT_KEY + merchantId;
                    merchantProductVO.setProductKey(Md5.encode(productKey));
                    json = merchantProductDefaultRedis(merchantProductVO,product.getStatus(),businessType);
                    break;
                case "PMONEY"://打款认证
                    productKey = "";
                    merchantProductVO.setProductKey(productKey);
                    json = merchantProductDefaultRedis(merchantProductVO,product.getStatus(),businessType);
                    break;
                case "BATPAY":// 转账
                case "SHARES":// 实名认证
                case "EPRISE"://企业认证
                case "AUTHEN"://鉴权认证
                case "RENAME":// 平级分润
				case "COSTOM":// 通关申报
                    json = merchantProductOtherRedis(merchantProductVO,product.getStatus(),businessType);
                    break;
                case "BATCOL":// 代收
                default:// 消费(9)
                    json = merchantProductConsumptionRedis(merchantProductVO,product.getStatus(),businessType);
                    break;
                }
            }
        }
        return json;
    }

    /**
     * @discription 修改商户费率redis
     * @author ly
     * @created 2016年10月11日 下午6:39:24
     * @param merchantId
     * @param productCode
     * @return
     */
    private boolean saveRate(String merchantId, String productCode, String businessType) {
        MerchantRateNew merchantRateFind = new MerchantRateNew();
        merchantRateFind.setMerchantId(merchantId);
        merchantRateFind.setProductCode(productCode);
        merchantRateFind.setBusinessType(businessType);
        //获取商户费率
        List<MerchantRateNew> merchantRateNews = merchantRateNewDao.findList(merchantRateFind);
        if (null != merchantRateNews && !merchantRateNews.isEmpty()) {
            for (MerchantRateNew merchantRateNew : merchantRateNews) {
                MerchantProductFeeVo merchantProductFeeVo = changeMerchangeProductFeeVo(merchantRateNew);
                String json = mapper.toJson(merchantProductFeeVo);
                redisSaveRate(merchantId, productCode, merchantRateNew.getBankNo(), merchantRateNew.getBankCardType(),
                        json,businessType);
            }
        }
        return true;
    }

    /**
     * @discription 修改默认费率redis
     * @author ly
     * @created 2016年10月11日 下午6:39:02
     * @return
     */
    private boolean saveDefaultRate(String productCode, String businessType) {
        MerchantRateNew merchantRateFind = new MerchantRateNew();
        String merchantId = com.heepay.manage.common.utils.Constants.MERCHANT_DEFAULT_ID;
        merchantRateFind.setMerchantId(merchantId);
        merchantRateFind.setProductCode(productCode);
        merchantRateFind.setBusinessType(businessType);
        List<MerchantRateNew> merchantRateNews = merchantRateNewDao.findList(merchantRateFind);
        if (!merchantRateNews.isEmpty()) {
            for (MerchantRateNew merchantRateNew : merchantRateNews) {
                if (!"CP04".equals(productCode)) {
                    MerchantProductFeeVo merchantProductFeeVo = changeMerchangeProductFeeVo(merchantRateNew);
                    String json = mapper.toJson(merchantProductFeeVo);
                    String bankCardType = merchantRateNew.getBankCardType();
                    redisSaveDefaultRate(merchantId, productCode, json, bankCardType, businessType);
                }
            }
        }
        return true;
    }

    /**
     * 费率redisJson转换回交易费率VO
     * ly 2017年5月27日15:34:01
     * @return
     */
    private String getRateJson(String json,String money) {
        MerchantProductFeeVo merchantProductFeeVo = mapper.fromJson(json, MerchantProductFeeVo.class);
        ProductFeeVO productFeeVO = getFeeVo(merchantProductFeeVo,money);
        return mapper.toJson(productFeeVO);
    }

    /**
     * @discription 转换返回交易费率VO
     * @author ly
     * @created 2016年10月11日 下午6:43:55
     */
    private ProductFeeVO changeFeeVo(MerchantRateNew merchantRate,String money) {
        MerchantProductFeeVo merchantProductFeeVo = changeMerchangeProductFeeVo(merchantRate);
        ProductFeeVO productFeeVO = getFeeVo(merchantProductFeeVo,money);
        return productFeeVO;
    }

    /**
     * 根据money获取阶梯费率
     * ly 2017年5月27日15:24:45
     * @return
     */
    private ProductFeeVO getFeeVo(MerchantProductFeeVo merchantProductFeeVo, String money) {
        ProductFeeVO productFeeVO = new ProductFeeVO();
        BigDecimal moneyFee = new BigDecimal(money);
        productFeeVO.setFeeCollectType(merchantProductFeeVo.getFeeCollectType());
        productFeeVO.setFeeType(merchantProductFeeVo.getFeeType());
        productFeeVO.setMaxFee(merchantProductFeeVo.getMaxFee());
        productFeeVO.setMinFee(merchantProductFeeVo.getMinFee());
        if(StringUtils.isBlank(merchantProductFeeVo.getChargeMax())&&StringUtils.isBlank(merchantProductFeeVo.getChargeMin())){
            //无阶梯
            productFeeVO.setFeeRatio(merchantProductFeeVo.getFeeRatio());
            return productFeeVO;
        }else{
            //阶梯1  阶梯最小值不大于money 并且 money小于阶梯最大值
            if(new BigDecimal(merchantProductFeeVo.getChargeMin()).compareTo(moneyFee) != 1 &&
                    moneyFee.compareTo(new BigDecimal(merchantProductFeeVo.getChargeMax())) == -1){
                productFeeVO.setFeeRatio(merchantProductFeeVo.getFeeRatio());
                return productFeeVO;
            }
            //阶梯2  阶梯最小值不大于money 并且 money小于阶梯最大值
            if(new BigDecimal(merchantProductFeeVo.getChargeMin2()).compareTo(moneyFee) != 1 &&
                    moneyFee.compareTo(new BigDecimal(merchantProductFeeVo.getChargeMax2())) == -1){
                productFeeVO.setFeeRatio(merchantProductFeeVo.getFeeRatio2());
                return productFeeVO;
            }
            //阶梯3  阶梯最小值不大于money 并且 money小于阶梯最大值
            if(new BigDecimal(merchantProductFeeVo.getChargeMin3()).compareTo(moneyFee) != 1 &&
                    moneyFee.compareTo(new BigDecimal(merchantProductFeeVo.getChargeMax3())) == -1){
                productFeeVO.setFeeRatio(merchantProductFeeVo.getFeeRatio3());
                return productFeeVO;
            }
        }
        return productFeeVO;
    }

    /**
     * 获取缓存费率VO
     * ly 2017年5月27日15:24:23
     * @param merchantRate
     * @return
     */
    private MerchantProductFeeVo changeMerchangeProductFeeVo(MerchantRateNew merchantRate) {
        MerchantProductFeeVo merchantProductFeeVo = new MerchantProductFeeVo();
        merchantProductFeeVo.setFeeType(merchantRate.getChargeType());
        merchantProductFeeVo.setFeeCollectType(merchantRate.getChargeDeductType());
        merchantProductFeeVo.setChargeMax(null == merchantRate.getChargeMax()?null:merchantRate.getChargeMax().toString());
        merchantProductFeeVo.setChargeMin(null == merchantRate.getChargeMin()?null:merchantRate.getChargeMin().toString());
        merchantProductFeeVo.setChargeMax2(null == merchantRate.getChargeMax2()?null:merchantRate.getChargeMax2().toString());
        merchantProductFeeVo.setChargeMin2(null == merchantRate.getChargeMin2()?null:merchantRate.getChargeMin2().toString());
        merchantProductFeeVo.setChargeMax3(null == merchantRate.getChargeMax3()?null:merchantRate.getChargeMax3().toString());
        merchantProductFeeVo.setChargeMin3(null == merchantRate.getChargeMin3()?null:merchantRate.getChargeMin3().toString());
        if (CostType.COUNT.getValue().equals(merchantRate.getChargeType())) {
            merchantProductFeeVo.setFeeRatio(merchantRate.getChargeFee().toString());
            merchantProductFeeVo.setFeeRatio2(null == merchantRate.getChargeFee2()?null:merchantRate.getChargeFee2().toString());
            merchantProductFeeVo.setFeeRatio3(null == merchantRate.getChargeFee3()?null:merchantRate.getChargeFee3().toString());
            merchantProductFeeVo.setMaxFee("0");
            merchantProductFeeVo.setMinFee("0");
        } else if (CostType.RATE.getValue().equals(merchantRate.getChargeType())) {
            merchantProductFeeVo.setFeeRatio(merchantRate.getChargeRatio().toString());
            merchantProductFeeVo.setFeeRatio2(null == merchantRate.getChargeRatio2()?null:merchantRate.getChargeRatio2().toString());
            merchantProductFeeVo.setFeeRatio3(null == merchantRate.getChargeRatio3()?null:merchantRate.getChargeRatio3().toString());
            merchantProductFeeVo.setMaxFee(merchantRate.getMaxFee().toString());
            merchantProductFeeVo.setMinFee(merchantRate.getMinFee().toString());
        }
        return merchantProductFeeVo;
    }

    /**
     * @discription 获取费率redis field
     * @author ly
     * @created 2016年10月19日 下午4:31:08
     * @param productCode
     *            产品code
     * @param bankNo
     *            银行NO
     * @param bankCardType
     *            银行卡类型
     * @return
     */
    private String getField(String productCode, String bankNo, String bankCardType) {
        String field = null;
        Product product = productDao.selectByCode(productCode);
        switch (product.getTrxType()) {
        case "CHARGE":// 充值
        case "WZDRAW":// 提现
        case "RENAME":// 实名认证
        case "EPRISE"://企业认证
        case "AUTHEN"://鉴权认证
        case "SHARES":// 平级分润
        case "CACNCE":// 通关申报
        case "TRAFER"://内转
        case "WALLET"://钱包服务
        case "DPTWZD":// 存管提现
            field = Constants.RedisKey.PRODUCT_FEE_NOBANK;
            break;
        case "BATPAY":// 转账
        case "BATCOL":// 代收
        case "DPTPAY":// 存管支付
            field = bankNo + BankcardType.SAVINGCARD.getValue();
            break;
        case "PAYMNT":// 支付
            if(product.getName().indexOf(com.heepay.manage.common.utils.Constants.B2B) >= 0
                    || product.getName().indexOf(com.heepay.manage.common.utils.Constants.QUICKPAY2) >= 0){//B2B 快捷支付2                
			    field = bankNo + BankcardType.SAVINGCARD.getValue();
            }else if(product.getName().indexOf(com.heepay.manage.common.utils.Constants.WECHAT) >= 0||
                    product.getName().indexOf(com.heepay.manage.common.utils.Constants.ALIPAY) >= 0 ||
                    product.getName().indexOf(com.heepay.manage.common.utils.Constants.UNOPAY) >= 0 ||
                    product.getName().indexOf(com.heepay.manage.common.utils.Constants.QUAGRP) >= 0){//微信支付宝银联量化派
                field = Constants.RedisKey.PRODUCT_FEE_NOBANK;
            }else{//其他
                field = bankNo + bankCardType;
            }
            break;
        default:
            break;
        }
        return field;
    }

    /**
     * @discription 获取默认费率redis field
     * @author ly
     * @created 2016年10月19日 下午4:31:08
     * @param productCode
     *            产品code
     * @param bankCardType
     *            银行卡类型
     * @return
     */
    private String getDefaultField(String productCode, String bankCardType) {
        String field = null;
        Product product = productDao.selectByCode(productCode);
        switch (product.getTrxType()) {
        case "CHARGE":// 充值
        case "WZDRAW":// 提现
        case "RENAME":// 实名认证
        case "EPRISE"://企业认证
        case "AUTHEN"://鉴权认证
        case "SHARES":// 平级分润
        case "CACNCE":// 通关申报
        case "TRAFER"://内转
        case "WALLET"://钱包服务
        case "DPTWZD":// 存管提现
            field = Constants.RedisKey.PRODUCT_FEE_NOBANK;
            break;
        case "BATPAY":// 转账
        case "BATCOL":// 代收
        case "DPTPAY":// 存管支付
            field = Constants.RedisKey.PRODUCT_FEE_NOBANK + BankcardType.SAVINGCARD.getValue();
            break;
        case "PAYMNT":// 支付
            if(product.getName().indexOf(com.heepay.manage.common.utils.Constants.B2B) >= 0
                    || product.getName().indexOf(com.heepay.manage.common.utils.Constants.QUICKPAY2) >= 0){//B2B 快捷支付2
                field = Constants.RedisKey.PRODUCT_FEE_NOBANK + BankcardType.SAVINGCARD.getValue();
            }else if(product.getName().indexOf(com.heepay.manage.common.utils.Constants.WECHAT) >= 0 ||
                    product.getName().indexOf(com.heepay.manage.common.utils.Constants.ALIPAY) >= 0 ||
                    product.getName().indexOf(com.heepay.manage.common.utils.Constants.UNOPAY) >= 0 ||
                    product.getName().indexOf(com.heepay.manage.common.utils.Constants.QUAGRP) >= 0){//微信支付宝银联量化派
                field = Constants.RedisKey.PRODUCT_FEE_NOBANK;
            }else{
                field = Constants.RedisKey.PRODUCT_FEE_NOBANK + bankCardType;
            }
            break;
        default:
            break;
        }
        return field;
    }

    /**
     * @discription 转账实名认证平级分润redis(商户签约产品)
     * @author ly
     * @created 2016年10月10日 下午6:11:38
     * @param merchantProductVO
     */
    private String merchantProductOtherRedis(MerchantProductVO merchantProductVO, String status, String businessType) {
        MerchantProductRedis merchantProductRedis = new MerchantProductRedis();
        merchantProductRedis.setMerchantId(merchantProductVO.getMerchantId());
        merchantProductRedis.setProductCode(merchantProductVO.getProductCode());
        merchantProductRedis.setBusinessType(businessType);
        List<MerchantProductRedis> list = merchantProductRedisDao.findList(merchantProductRedis);
        String json = null;
        if (!list.isEmpty()) {
            MerchantProductRedis productRedis = list.get(0);
            MerchantProductVO vo = changeMerchantProductVoRedis(productRedis);
            vo.setSettleCyc("0");// 没有结算周期 给默认值0(实时)
            if (null != vo.getValidityDateBegin()) {
                vo.setValidityDateBegin(vo.getValidityDateBegin());
            }
            if (null != vo.getValidityDateEnd()) {
                vo.setValidityDateEnd(DateUtil.MillisecondNextDay(vo.getValidityDateEnd()));
            }
            //产品状态为禁用 将商户产品状态至为禁用
            if(CommonStatus.DISABLE.getValue().equals(status)){
                merchantProductVO.setStatus(status);
            }
            json = mapper.toJson(vo);
        }
        return json;
    }

    /**
     * @discription 代收消费redis(商户签约产品)
     * @author ly
     * @created 2016年10月10日 下午6:11:38
     * @param merchantProductVO
     * @param status 
     */
    private String merchantProductConsumptionRedis(MerchantProductVO merchantProductVO, String status, String businessType) {
        MerchantProductRedis merchantProductRedis = new MerchantProductRedis();
        merchantProductRedis.setMerchantId(merchantProductVO.getMerchantId());
        merchantProductRedis.setProductCode(merchantProductVO.getProductCode());
        merchantProductRedis.setBusinessType(businessType);
        List<MerchantProductRedis> list = merchantProductRedisDao.findList(merchantProductRedis);
        String json = null;
        if (!list.isEmpty()) {
            MerchantProductRedis productRedis = list.get(0);
            MerchantProductVO vo = changeMerchantProductVoRedis(productRedis);
            if (null != vo.getValidityDateBegin()) {
                vo.setValidityDateBegin(vo.getValidityDateBegin());
            }
            if (null != vo.getValidityDateEnd()) {
                vo.setValidityDateEnd(DateUtil.MillisecondNextDay(vo.getValidityDateEnd()));
            }
            //产品状态为禁用 将商户产品状态至为禁用
            if(CommonStatus.DISABLE.getValue().equals(status)){
                merchantProductVO.setStatus(status);
            }
            //获取配置的银行卡类型
            MerchantRateNew merchantRateNewFind = new MerchantRateNew();
            merchantRateNewFind.setMerchantId(merchantProductVO.getMerchantId());
            merchantRateNewFind.setProductCode(merchantProductVO.getProductCode());
            merchantRateNewFind.setBankNo(com.heepay.manage.common.utils.Constants.MERCHANT_RATE_ALL_BANK);
            List<MerchantRateNew> merchantRateNews = merchantRateNewDao.findList(merchantRateNewFind);
            if(null != merchantRateNews && !merchantRateNews.isEmpty()){
                List<String> strings = Lists.newArrayList();
                for(MerchantRateNew merchantRateNew : merchantRateNews){
                    strings.add(merchantRateNew.getBankCardType());
                }
                vo.setBankCardTypes(strings);
            }
            json = mapper.toJson(vo);
        }
        return json;
    }

    /**
     * @discription 转换merchantProductVo
     * @author ly
     * @created 2016年11月2日 下午8:01:01
     * @param productRedis
     * @return
     */
    private MerchantProductVO changeMerchantProductVoRedis(MerchantProductRedis productRedis) {
        MerchantProductVO merchantProductVO = new MerchantProductVO();
        merchantProductVO.setBackUrl(productRedis.getBackUrl());
        merchantProductVO.setFeeSettleCyc(productRedis.getFeeSettleCyc());
        merchantProductVO.setFeeWay(productRedis.getFeeWay());
        merchantProductVO.setIsRefundable(productRedis.getIsRefundable());
        merchantProductVO.setIpDomain(productRedis.getIpDomain());
        merchantProductVO.setMerchantId(productRedis.getMerchantId());
        merchantProductVO.setNotifyUrl(productRedis.getNotifyUrl());
        merchantProductVO.setProductCode(productRedis.getProductCode());
        merchantProductVO.setProductKey(productRedis.getProductKey());
        merchantProductVO.setProductName(productRedis.getProductName());
        merchantProductVO.setReferer(productRedis.getReferer());
        merchantProductVO.setSettleCyc(productRedis.getSettleCyc());
        merchantProductVO.setToBalanceOrBankcard(productRedis.getToBalanceOrBankcard());
        Long begin = null == productRedis.getValidityDateBegin() ? DateUtil.getNowTime():productRedis.getValidityDateBegin().getTime();
        Long end = null == productRedis.getValidityDateEnd() ? DateUtil.getNowTimeAfterYear(1):productRedis.getValidityDateEnd().getTime();
        merchantProductVO.setValidityDateBegin(begin);
        merchantProductVO.setValidityDateEnd(end);
        if(!"Y".equals(productRedis.getRateAudit())){
            merchantProductVO.setStatus(CommonStatus.DISABLE.getValue());
        }else{
            merchantProductVO.setStatus(productRedis.getStatus());
        }
        return merchantProductVO;
    }

    /**
     * @discription 充值提现退款redis(商户签约产品)
     * @author ly
     * @created 2016年10月10日 下午6:11:50
     * @param merchantProductVO 商户签约产品VO
     */
    private String merchantProductDefaultRedis(MerchantProductVO merchantProductVO, String status, String businessType) {
        MerchantProductRate merchantProductRate = new MerchantProductRate();
        merchantProductRate.setMerchantId(merchantProductVO.getMerchantId());
        merchantProductRate.setProductCode(merchantProductVO.getProductCode());
        merchantProductRate.setBusinessType(businessType);
        //根据merchantId productCode 取商户产品是否存在
        merchantProductRate = merchantProductRateDao.exist(merchantProductRate);
        if (null == merchantProductRate) {
            merchantProductRate = new MerchantProductRate();
            merchantProductRate.setMerchantId(com.heepay.manage.common.utils.Constants.MERCHANT_DEFAULT_ID);
            merchantProductRate.setProductCode(merchantProductVO.getProductCode());
            merchantProductRate.setBusinessType(businessType);
          //根据merchantId productCode 取默认产品是否存在
            merchantProductRate = merchantProductRateDao.exist(merchantProductRate);
            if (null == merchantProductRate) {
                //如果不存在拼一个默认的产品信息
                merchantProductRate = new MerchantProductRate();
                Product product = productDao.selectByCode(merchantProductVO.getProductCode());
                merchantProductRate.setProductName(product.getName());
            }
        }
        if (StringUtils.isNotBlank(merchantProductRate.getChargeDeductType())) {
            merchantProductVO.setIsRefundable("false");
            merchantProductVO.setFeeWay(merchantProductRate.getChargeDeductType());
        } else {
            merchantProductVO.setIsRefundable(merchantProductRate.getIsRefundable());
            if (!"CP04".equals(merchantProductVO.getProductCode())) {// 退款没有手续费扣除方式
                merchantProductVO.setFeeWay(ChargeDeductType.INTERNAL_DEDUCT.getValue());
            }
        }
        merchantProductVO.setSettleCyc("0");// 默认开通的产品没有结算周期 给默认值0(实时)
        merchantProductVO.setProductName(merchantProductRate.getProductName());
        merchantProductVO.setValidityDateBegin(DateUtil.getNowTime());
        merchantProductVO.setValidityDateEnd(DateUtil.getNowTimeAfterYear(1));
        if(CommonStatus.DISABLE.getValue().equals(status)){
            merchantProductVO.setStatus(status);
        }else{
            //审核状态为空或审核通过
            if(StringUtils.isBlank(merchantProductRate.getRateAudit()) || "Y".equals(merchantProductRate.getRateAudit())){
                //状态为空设置默认为启用
                merchantProductVO.setStatus(null == merchantProductRate.getStatus() ?
                        CommonStatus.ENABLE.getValue() : merchantProductRate.getStatus() );
            }else{
                merchantProductVO.setStatus(CommonStatus.DISABLE.getValue());
            }
        }
        // merchantProductVO.setFeeSettleCyc("1");//手续费结算周期暂时定死为1
        return mapper.toJson(merchantProductVO);
    }

      
    /**     
    * @discription 根据交易类型查询产品list
    * @author ly     
    * @created 2016年11月24日 上午11:42:26     
    * @param trxType
    * @return     
    */
    public String getProductList(String trxType) {
        logger.info("根据交易类型{}查询产品list",trxType);
        List<Product> products= productDao.selectByType(trxType);
        return mapper.toJson(products);
    }

      
    /**
     * @discription 商户禁用(前台用)
     * @author ly
     * @created 2016年12月10日 下午4:59:22
     * @param id
     */
    public void saveRedisDisableMerchant(String id) {
        jedisCluster.setex(id + ":" + "DISABLE",
                exp * com.heepay.manage.common.utils.Constants.ONE_MINUTE_SECOND, "DISABLE");
    }

      
    /**     
    * @discription 商户启用(前台用)
    * @author ly     
    * @created 2016年12月10日 下午5:03:24     
    * @param id     
    */
    public void delRedisDisableMerchant(String id) {
        jedisCluster.del(id + ":" + "DISABLE");
    }

}
