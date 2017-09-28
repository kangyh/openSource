package com.heepay.manage.rpc.service.impl;

import com.google.common.collect.Maps;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.BusinessType;
import com.heepay.enums.CostType;
import com.heepay.manage.common.cache.IntegrationBankIdSync;
import com.heepay.manage.common.cache.PayChannelRedisUtil;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.common.utils.JsonMapper;
import com.heepay.manage.modules.route.dao.CertifyChannelDao;
import com.heepay.manage.modules.route.dao.PayChannelDao;
import com.heepay.manage.modules.route.dao.ProductDetailDao;
import com.heepay.manage.modules.route.entity.CertifyChannel;
import com.heepay.manage.modules.route.entity.ChannelRedisVO;
import com.heepay.manage.modules.route.entity.PayChannel;
import com.heepay.manage.modules.route.entity.ProductDetail;
import com.heepay.manage.modules.route.service.ChannelBankidService;
import com.heepay.manage.modules.route.service.PayChannelService;
import com.heepay.manage.modules.sys.dao.DictDao;
import com.heepay.manage.modules.sys.entity.Dict;
import com.heepay.manage.rpc.service.CertifyThrift;
import com.heepay.manage.rpc.service.PayChannelThrift;
import com.heepay.redis.JedisClusterUtil;
import com.heepay.rpc.service.RpcService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.heepay.redis.JedisClusterUtil.getJedisValue;

/**
 * 描    述：查询通道信息，加载到redis缓存，
 * <p>
 * 创 建 者： M.Z
 * 创建时间： 2016/9/12 12:03
 * 创建描述：查询通道信息，加载到redis缓存，
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Component
@RpcService(name = "payChannelCacheServiceImpl", processor = com.heepay.manage.rpc.service.PayChannelCacheService.Processor.class)
public class PayChannelCacheServiceImpl implements com.heepay.manage.rpc.service.PayChannelCacheService.Iface {

    @Autowired
    private PayChannelService payChannelService;

    @Autowired
    private CertifyChannelDao certifyChannelDao;

    @Autowired
    private ProductDetailDao productDetailDao;

    @Autowired
    private PayChannelDao payChannelDao;

    @Autowired
    private ChannelBankidService channelBankidService;

    @Autowired
    private DictDao dictDao;

    // 缓存有效时间分钟数
    private Integer exp = 30;

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    private static final Logger logger = LogManager.getLogger();

    /**
     * 获取jedisCluster
     *
     * @return
     */
    private JedisCluster getJedisCluster() {
        return JedisClusterUtil.getJedisCluster();
    }

    private String getBestChannelValue(List<ChannelRedisVO> channelRateRedisVOs, String key, String channelKey) {
        String value = "";
        if (null != channelRateRedisVOs && !channelRateRedisVOs.isEmpty()) {
            //从缓存中取
            JedisCluster jedisCluster = getJedisCluster();
            String jsonInfo = jedisCluster.get(channelKey);
            //json字符串转map
            Map<String, String> map = (Map<String, String>) JsonMapper.fromJsonString(jsonInfo, Map.class);
            if (null == map) {
                map = Maps.newHashMap();
            }
            //将此次key放入map中
            map.put(key, key);
            String redisMapValue = JsonMapper.toJsonString(map);
            //将记录次类key的map放入缓存
            jedisCluster.set(channelKey, redisMapValue);
            //取最优
            ChannelRedisVO channelRedisVO = channelRateRedisVOs.get(0);
            value = JsonMapper.toJsonString(channelRedisVO);
            //放入缓存
            String finalValue = value;
            jedisCluster.set(key, finalValue);
            jedisCluster.expire(key, 60 * 60 * 24);
        }
        return value;
    }

    /**
     * 查询出产品明细配置中有效期内的通道信息，加载到redis缓存，
     * 使用 bankNo, channelTypeCode, cardTypeCode，accountType，businessType，merchantId六个字段拼加为key
     */
    @Override
    public void queryMerchantChannel() throws TException {
        PayChannelRedisUtil.queryMerchantChannel();
    }

    /**
     * 按条件查询出产品明细配置中有效期内的通道信息
     * 使用 bankNo, channelTypeCode, cardTypeCode，accountType，businessType，merchantId六个字段拼加为key
     */
    @Override
    public String queryMerchantChannelBy(String bankNo, String channelTypeCode, String cardTypeCode, String accountType, String businessType, String merchantId) throws TException {
        //拼key
        String key = bankNo + channelTypeCode + cardTypeCode + accountType + businessType + merchantId;
        //查询Redis
        JedisCluster jedisCluster = getJedisCluster();
        String value = jedisCluster.get(key);
        //如果缓存中没有则查询数据库并将这条数据放入缓存
        if (StringUtil.isBlank(value)) {
            //查询数据
            ChannelRedisVO channelRedisVO = payChannelService.selectChannel(bankNo, channelTypeCode, cardTypeCode, accountType, businessType, merchantId);
            value = "";
            if (null != channelRedisVO) {
                //从缓存中取
                String jsonInfo = jedisCluster.get(Constants.MERCHANT_CHANNEL_KEY);
                //json字符串转map
                Map<String, String> map = (Map<String, String>) JsonMapper.fromJsonString(jsonInfo, Map.class);
                if (null == map) {
                    map = Maps.newHashMap();
                }
                //将此次key放入map中
                map.put(key, key);
                String redisMapValue = JsonMapper.toJsonString(map);
                //将记录次类key的map放入缓存
                jedisCluster.set(Constants.MERCHANT_CHANNEL_KEY, redisMapValue);
                //将新数据放入缓存
                value = JsonMapper.toJsonString(channelRedisVO);
                String finalValue = value;
                jedisCluster.set(key, finalValue);
                jedisCluster.expire(key, 60 * 60 * 24);
            }
        }
        return value;
    }

    /**
     * 查询有效期内的优先级最高，或者最低成本的默认通道添加到缓存，
     * 本行：使用 bankNo, channelTypeCode, cardTypeCode，accountType，本行，costType六个字段拼加为key
     * 跨行：使用 channelTypeCode, cardTypeCode，accountType，跨行，costType五个字段拼加为key
     */
    @Override
    public void queryPayChannel() throws TException {
        PayChannelRedisUtil.queryPayChannel();
    }

    /**
     * 按条件查询有效期内的优先级最高，或者最低成本的默认通道
     * 本行：使用 bankNo, channelTypeCode, cardTypeCode，accountType，本行，costType六个字段拼加为key
     * 跨行：使用 channelTypeCode, cardTypeCode，accountType，跨行，costType五个字段拼加为key
     */
    @Override
    public String queryBestChannel(String bankNo, String channelTypeCode, String cardTypeCode, String accountType, String businessType, String costType) throws TException {
        String value = "";
        JedisCluster jedisCluster = getJedisCluster();
        //本行
        if (businessType.equals(BusinessType.OWNBAK.getValue())) {
            //拼key
            String key = bankNo + channelTypeCode + cardTypeCode + accountType + businessType + costType;
            //查询Redis
            value = jedisCluster.get(key);
            //如果缓存中没有则查询数据库并将这条数据放入缓存
            if (StringUtil.isBlank(value)) {
                //判断成本类型
                if (costType.equals(CostType.RATE.getValue())) {
                    //本行按比例，查数据库
                    List<ChannelRedisVO> channelRateRedisVOs = payChannelService.selectRateList(bankNo, channelTypeCode, cardTypeCode, accountType);
                    //存值并取value
                    value = getBestChannelValue(channelRateRedisVOs, key, Constants.BEST_PAY_CHANNEL_KEY);
                } else if (costType.equals(CostType.COUNT.getValue())) {
                    //本行按笔数，查数据库
                    List<ChannelRedisVO> channelRateRedisVOs = payChannelService.selectCountList(bankNo, channelTypeCode, cardTypeCode, accountType);
                    //存值并取value
                    value = getBestChannelValue(channelRateRedisVOs, key, Constants.BEST_PAY_CHANNEL_KEY);
                }
            }

            //跨行
        } else if (businessType.equals(BusinessType.SPNBAK.getValue())) {
            //拼key
            String key = channelTypeCode + cardTypeCode + accountType + businessType + costType;
            //查询Redis
            value = jedisCluster.get(key);
            if (StringUtil.isBlank(value)) {
                //判断成本类型
                if (costType.equals(CostType.RATE.getValue())) {
                    //跨行按比例，查数据库
                    List<ChannelRedisVO> channelRateRedisVOs = payChannelService.selectSpnChannel(channelTypeCode, cardTypeCode, accountType);
                    //存值并取value,并且将key收集起来以便删除缓存用
                    value = getBestChannelValue(channelRateRedisVOs, key, Constants.BEST_PAY_CHANNEL_KEY);
                } else if (costType.equals(CostType.COUNT.getValue())) {
                    //跨行按比数，查数据库
                    List<ChannelRedisVO> channelRateRedisVOs = payChannelService.selectSpnChannelCount(channelTypeCode, cardTypeCode, accountType);
                    //存值并取value
                    value = getBestChannelValue(channelRateRedisVOs, key, Constants.BEST_PAY_CHANNEL_KEY);
                }
            }
        }
        return value;
    }

    /**
     * 查询出每种通道类型对应的银行卡类型，加载到redis缓存，
     * 使用 channelType字段拼为key
     */
    @Override
    public void queryChannelType() throws TException {
        PayChannelRedisUtil.queryChannelType();
    }

    /**
     * 按照通道类型查询出对应的银行及卡类型
     */
    @Override
    public String queryBankInfoByChannelType(String channelTypeCode) throws TException {
        JedisCluster jedisCluster = getJedisCluster();
        //查询Redis
        Map map = jedisCluster.hgetAll(channelTypeCode);
        //如果缓存中没有则查询数据库并将数据放入缓存
        if (map.size() == 0) {
            //查询数据
            List<PayChannel> payChannels = payChannelService.selectChannelType(channelTypeCode);
            if (null != payChannels && !payChannels.isEmpty()) {
                //从缓存中取
                String jsonInfo = jedisCluster.get(Constants.PAY_CHANNEL_KEY);
                //json字符串转map
                Map<String, String> channelMap = (Map<String, String>) JsonMapper.fromJsonString(jsonInfo, Map.class);
                if (null == channelMap) {
                    channelMap = Maps.newHashMap();
                }
                //将此次key放入map中
                channelMap.put(channelTypeCode, channelTypeCode);
                String redisMapValue = JsonMapper.toJsonString(channelMap);
                //将记录次类key的map放入缓存
                jedisCluster.set(Constants.PAY_CHANNEL_KEY, redisMapValue);
                //新数据放入缓存
                for (PayChannel payChannel : payChannels) {
                    String finalCardTypeCode = payChannel.getCardTypeCode();
                    jedisCluster.hset(channelTypeCode, payChannel.getBankNo(), finalCardTypeCode);
                    jedisCluster.expire(channelTypeCode, 60 * 60);
                }
                //查询Redis
                map = jedisCluster.hgetAll(channelTypeCode);
            }
        }
        Map<String, String> treeMap = new TreeMap<>(map);
        if (treeMap.size() == 0) {
            return "";
        }
        return JsonMapper.toJsonString(treeMap);
    }

    /**
     * 根据产品代码查询出银行及卡类型
     *
     * @param productCode
     * @return
     * @throws TException
     */
    @Override
    public String queryBankInfoByProductCode(String productCode) throws TException {
        JedisCluster jedisCluster = getJedisCluster();
        //查询Redis
        Map map = jedisCluster.hgetAll(Constants.BANK_OF_PRODUCT + productCode);
        //如果缓存中没有则查询数据库并将数据放入缓存
        if (map.size() == 0) {
            //查询数据
            List<ProductDetail> productDetails = productDetailDao.selectBankInfoByProductCode(productCode);
            if (null != productDetails && !productDetails.isEmpty()) {
                //从缓存中取
                String jsonInfo = jedisCluster.get(Constants.BANK_OF_PRODUCT_KEY);
                //json字符串转map
                Map<String, String> productMap = (Map<String, String>) JsonMapper.fromJsonString(jsonInfo, Map.class);
                if (null == productMap) {
                    productMap = Maps.newHashMap();
                }
                //将此次key放入map中
                productMap.put(Constants.BANK_OF_PRODUCT + productCode, Constants.BANK_OF_PRODUCT + productCode);
                String redisMapValue = JsonMapper.toJsonString(productMap);
                //将记录次类key的map放入缓存
                jedisCluster.set(Constants.BANK_OF_PRODUCT_KEY, redisMapValue);
                //新数据放入缓存
                for (ProductDetail productDetail : productDetails) {
                    jedisCluster.hset(Constants.BANK_OF_PRODUCT + productCode, productDetail.getBankNo(), productDetail.getCardTypeCode());
                    jedisCluster.expire(Constants.BANK_OF_PRODUCT + productCode, 60 * 30);
                }
                //查询Redis
                map = jedisCluster.hgetAll(Constants.BANK_OF_PRODUCT + productCode);
            }
        }
        Map<String, String> treeMap = new TreeMap<>(map);
        if (treeMap.size() == 0) {
            return "";
        }
        return JsonMapper.toJsonString(treeMap);
    }


    /**
     * 查询出每条通道的所有信息，加载到redis缓存，
     * 使用 channelCode字段拼为key
     */
    @Override
    public void queryPayChannelAll() throws TException {
        PayChannelRedisUtil.queryPayChannelAll();
    }

    /**
     * 根据通道代码查询出通道的所有信息
     */
    @Override
    public String queryChannelByChannelCode(String channelCode) throws TException {
        JedisCluster jedisCluster = getJedisCluster();
        //查询Redis
        if (StringUtil.notBlank(channelCode)) {
            String value = jedisCluster.get(channelCode);
            //如果缓存中没有则查询数据库并将这条数据放入缓存
            if (StringUtil.isBlank(value)) {
                //查询数据
                PayChannel payChannel = payChannelService.selectByChannelCode(channelCode);
                value = "";
                if (null != payChannel) {
                    //从缓存中取
                    String jsonInfo = (String) getJedisValue(getter -> getter.get(Constants.PAY_CHANNEL_KEY));
                    //json字符串转map
                    Map<String, String> map = (Map<String, String>) JsonMapper.fromJsonString(jsonInfo, Map.class);
                    if (null == map) {
                        map = Maps.newHashMap();
                    }
                    //将此次key放入map中
                    map.put(channelCode, channelCode);
                    String redisMapValue = JsonMapper.toJsonString(map);
                    //将记录次类key的map放入缓存
                    jedisCluster.set(Constants.PAY_CHANNEL_KEY, redisMapValue);
                    //默认结算周期为t+1
                    //payChannel.setOrderSettlePeriod("1");
                    value = JsonMapper.toJsonString(payChannel);
                    String finalValue = value;
                    //将新数据放入缓存
                    jedisCluster.set(channelCode, finalValue);
                    jedisCluster.expire(channelCode, 60 * 60 * 24);
                }
            }
            return value;
        }
        return "";
    }

    /**
     * 手动查询出每条通道的所有信息
     */
    @Override
    public List<PayChannelThrift> selectPayChannelAllList() throws TException {
        List<PayChannel> payChannels = payChannelService.findAllList();
        List<PayChannelThrift> payChannelThrifts = new ArrayList<>();
        for (PayChannel payChannel : payChannels) {
            PayChannelThrift payChannelThrift = changeThrift(payChannel);
            //payChannelThrift.setOrderSettlePeriod("1");
            payChannelThrifts.add(payChannelThrift);
        }
        return payChannelThrifts;
    }

    /**
     * 手动查询出每种通道类型对应的银行及卡类型(channelTypeCode,bankNo,cardTypeCode)
     */
    @Override
    public List<PayChannelThrift> queryChannelTypeList() throws TException {
        List<PayChannel> payChannels = payChannelService.selectChannelType();
        List<PayChannelThrift> payChannelThrifts = new ArrayList<>();
        for (PayChannel payChannel : payChannels) {
            PayChannelThrift payChannelThrift = new PayChannelThrift();
            payChannelThrift.setChannelTypeCode(payChannel.getChannelTypeCode());
            payChannelThrift.setBankNo(payChannel.getBankNo());
            payChannelThrift.setCardTypeCode(payChannel.getCardTypeCode());
            payChannelThrifts.add(payChannelThrift);
        }
        return payChannelThrifts;
    }

    /**
     * @param bankNo
     * @param channelTypeCode
     * @author M.Z
     * @return
     * @throws TException
     * @discription 银行代码、通道类型
     * @author ly
     * @created 2016年12月9日 上午11:34:21
     */

    @Override
    public List<PayChannelThrift> selectPayChannelByType(String bankNo, String channelTypeCode, String bankCardType,String productCode) throws TException {
        PayChannel payChannelFind = new PayChannel();
        payChannelFind.setBankNo(bankNo);
        payChannelFind.setChannelTypeCode(channelTypeCode);
        payChannelFind.setCardTypeCode(bankCardType);
        List<PayChannel> payChannels = payChannelService.findListByBankNoAndChannelTypeCode(payChannelFind);
        List<ProductDetail> productDetails = productDetailDao.selectValidByProductCode(productCode);
        List<PayChannelThrift> payChannelThrifts = new ArrayList<>();
        for (ProductDetail productDetail:productDetails){
            for (PayChannel payChannel : payChannels) {
                if (productDetail.getChannelCode().equals(payChannel.getChannelCode())){
                    PayChannelThrift payChannelThrift = changeThrift(payChannel);
                    payChannelThrifts.add(payChannelThrift);
                }
            }
        }
        return payChannelThrifts;
    }


    /**
     * @param payChannel
     * @return
     * @discription 转换thrift
     * @author ly
     * @created 2016年10月27日 下午5:50:07
     */
    private PayChannelThrift changeThrift(PayChannel payChannel) {
        PayChannelThrift payChannelThrift = new PayChannelThrift();
        payChannelThrift.setChannelCode(payChannel.getChannelCode());
        payChannelThrift.setChannelName(payChannel.getChannelName());
        payChannelThrift.setBankName(payChannel.getBankName());
        payChannelThrift.setBankNo(payChannel.getBankNo());
        payChannelThrift.setChannelPartnerName(payChannel.getChannelPartnerName());
        payChannelThrift.setChannelPartnerCode(payChannel.getChannelPartnerCode());
        payChannelThrift.setCardTypeName(payChannel.getCardTypeName());
        payChannelThrift.setCardTypeCode(payChannel.getCardTypeCode());
        payChannelThrift.setAccountType(payChannel.getAccountType());
        payChannelThrift.setBusinessType(payChannel.getBusinessType());
        payChannelThrift.setChargeDeductType(payChannel.getChargeDeductType());
        payChannelThrift.setChargeReturnTag(payChannel.getChargeReturnTag());
        payChannelThrift.setEffectStartDate(String.valueOf(payChannel.getEffectStartDate()));
        payChannelThrift.setEffectEndDate(String.valueOf(payChannel.getEffectEndDate()));
        payChannelThrift.setCostType(payChannel.getCostType());
        payChannelThrift.setCostRate(payChannel.getCostRate());
        payChannelThrift.setCostCount(payChannel.getCostCount());
        payChannelThrift.setStatus(payChannel.getStatus());
        payChannelThrift.setContractDate(String.valueOf(payChannel.getContractDate()));
        payChannelThrift.setSettleType(payChannel.getSettleType());
        payChannelThrift.setSettlePeriod(payChannel.getSettlePeriod());
        payChannelThrift.setSort(payChannel.getSort());
        payChannelThrift.setPerlimitAmount(payChannel.getPerlimitAmount());
        payChannelThrift.setDaylimitAmount(payChannel.getDaylimitAmount());
        payChannelThrift.setMonlimitAmount(payChannel.getMonlimitAmount());
        payChannelThrift.setOrderSettlePeriod(payChannel.getOrderSettlePeriod());
        payChannelThrift.setChannelTypeCode(payChannel.getChannelTypeCode());
        payChannelThrift.setChannelTypeName(payChannel.getChannelTypeName());
        return payChannelThrift;
    }


    /**
     * @return
     * @throws TException
     * @discription 获取实名认证成本
     * @author ly
     * @created 2016年12月1日 上午11:59:40
     * @see com.heepay.manage.rpc.service.PayChannelCacheService.Iface#queryCertifyCost()
     */

    @Override
    public CertifyThrift queryCertifyCost() throws TException {
        List<CertifyChannel> certifyChannels = certifyChannelDao.getOrderBySort("CP15");
        CertifyThrift certifyThrift = new CertifyThrift();
        if (null != certifyChannels && !certifyChannels.isEmpty()) {
            certifyThrift.setCost(certifyChannels.get(0).getCost());
            certifyThrift.setChannelCode(certifyChannels.get(0).getChannelCode());
        }
        return certifyThrift;
    }

    /**
     * @param channelCode
     * @return
     * @throws TException
     * @discription 获取实名认证通道
     * @author ly
     * @created 2016年12月1日 下午12:00:05
     * @see com.heepay.manage.rpc.service.PayChannelCacheService.Iface#queryCertifyChannelByChannelCode(java.lang.String)
     */

    @Override
    public String queryCertifyChannelByChannelCode(String channelCode) throws TException {
        CertifyChannel certifyChannel = certifyChannelDao.selectByChannelCode(channelCode);
        PayChannel payChannel = new PayChannel();
        if(null != certifyChannel){
            payChannel.setChannelPartnerCode(certifyChannel.getChannelPartnerCode());
            payChannel.setChannelPartnerName(certifyChannel.getChannelPartnerName());
            payChannel.setSettleType(certifyChannel.getSettleType());
            payChannel.setSettlePeriod(certifyChannel.getSettlePeriod());
            payChannel.setChannelTypeCode(certifyChannel.getChannelTypeCode());
            payChannel.setOrderSettlePeriod("1");
            payChannel.setChannelCode(certifyChannel.getChannelCode());
            payChannel.setCostCount(certifyChannel.getCost());
            return new JsonMapperUtil().toJson(payChannel);
        }else{
            return "";
        }
    }

    /**
     * 通过通道代码获取路由通道
     *
     * @param channelCode
     * @author ly
     */
    @Override
    public String getRouteByChannelCode(String channelCode) throws TException {
        if (StringUtils.isNotBlank(channelCode)) {
            String json = getJedisCluster().get(Constants.PAY_CHANNEL_ROUTE + ":" + channelCode);
            if (StringUtils.isNotBlank(json)) {
                return json;
            }
            Dict dict = dictDao.getValueByLabel(Constants.ROUTEMAP, channelCode);
            if (null != dict) {
                getJedisCluster().setex(Constants.PAY_CHANNEL_ROUTE + ":" + channelCode,
                        exp * com.heepay.manage.common.utils.Constants.ONE_MINUTE_SECOND ,dict.getValue());
                return dict.getValue();
            }
        }
        return "";
    }

    /**
     * 根据产品代码查询通道
     * @param productCode
     * @return
     * @throws TException
     */
    @Override
    public List<PayChannelThrift> queryPayChannelByProductCode(String productCode) throws TException {
        List<ProductDetail> productDetails = productDetailDao.selectValidByProductCode(productCode);
        List<PayChannelThrift> payChannelThrifts = new ArrayList<>();
        if (null != productDetails && !productDetails.isEmpty()) {
            for (ProductDetail productDetail : productDetails) {
                PayChannelThrift payChannelThrift = new PayChannelThrift();
                payChannelThrift.setChannelCode(productDetail.getChannelCode());
                payChannelThrift.setChannelName(productDetail.getChannelName());
                payChannelThrift.setBankNo(productDetail.getBankNo());
                payChannelThrift.setBankName(productDetail.getBankName());
                payChannelThrift.setChannelPartnerCode(productDetail.getChannelPartnerCode());
                payChannelThrift.setChannelPartnerName(productDetail.getChannelPartnerName());
                payChannelThrift.setChannelTypeCode(productDetail.getChannelTypeCode());
                payChannelThrift.setChannelTypeName(productDetail.getChannelTypeName());
                payChannelThrift.setCardTypeCode(productDetail.getCardTypeCode());
                payChannelThrift.setCardTypeName(productDetail.getCardTypeName());
                payChannelThrifts.add(payChannelThrift);
            }
        }
      //  System.out.println("=========成功");
        return payChannelThrifts;
    }

    /**
     * 同步.net的bankId信息
     * @throws TException
     */
    @Override
    public void bankIdSync() throws TException {
        IntegrationBankIdSync.bankIdSync();
    }

    /**
     * 查询bankId信息
     * @param channelCode
     * @return
     * @throws TException
     */
    @Override
    public String queryBankId(String channelCode) throws TException {
        return channelBankidService.queryBankId(channelCode);
    }


    /**
     * @param bankNo
     * @param channelTypeCode
     * @author N.W
     * @return
     * @throws TException
     * @discription 银行代码、通道类型
     * @author
     * @created 2017年4月26日 上午11:34:21
     */

    @Override
    public String selectPayChannelByMerchantId(String bankNo, String channelTypeCode, String bankCardType,String merchantId,String productCode) throws TException {
        PayChannel payChannelFind = new PayChannel();
        payChannelFind.setBankNo(bankNo);
        payChannelFind.setChannelTypeCode(channelTypeCode);
        payChannelFind.setCardTypeCode(bankCardType);
        payChannelFind.setMerchantId(merchantId);
        payChannelFind.setProductCode(productCode);
        List<PayChannel> payChannels = payChannelDao.selectByMerchantId(payChannelFind);
        return JsonMapper.toJsonString(payChannels);
    }

    @Override
    public String queryCertifyChannelByChannelType(String channelType) throws TException {
        CertifyChannel certifyChannel = certifyChannelDao.getCertifyByType(channelType);
        if(null != certifyChannel){
            logger.info("获取认证通道channelCode{}",certifyChannel.getChannelCode());
            return certifyChannel.getChannelCode();
        }
        logger.info("获取认证通道channelCode为空");
        return "";
    }

}
