package com.heepay.manage.modules.route.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.heepay.codec.Md5;
import com.heepay.common.util.Constants;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.MerchantAutographType;
import com.heepay.enums.ProductType;
import com.heepay.enums.SettlementTo;
import com.heepay.manage.common.cache.RedisUtil;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.modules.merchant.dao.MerchantDao;
import com.heepay.manage.modules.merchant.dao.MerchantProductRateDao;
import com.heepay.manage.modules.merchant.dao.MerchantProductRedisDao;
import com.heepay.manage.modules.merchant.dao.ProductDao;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantProductRedis;
import com.heepay.manage.modules.route.dao.LineBankNumberDao;
import com.heepay.manage.modules.route.entity.LineBankNumber;
import com.heepay.manage.modules.sys.entity.Dict;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.redis.JedisClusterUtil;
import com.heepay.vo.MerchantProductVO;
import com.heepay.vo.MerchantVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) //用于配置spring中测试的环境 
@ContextConfiguration(locations = {"classpath*:/spring-context.xml"}) //用于指定配置文件所在的位置 
public class RedisTest {
  
  @Autowired
  private MerchantProductRedisDao merchantProductVODao;
  
  @Autowired
  private MerchantProductRateDao merchantProductRateDao;
  
  @Autowired
  private PayChannelService payChannelService;
  
  @Autowired
  private MerchantDao merchantDao;
  
  @Autowired
  private ProductDao productDao;
  
  private JedisCluster jedisCluster1 = JedisClusterUtil.getJedisCluster();
  
  private JsonMapperUtil mapper = new JsonMapperUtil ();

  @Test
  public void testRedisUtil() throws JsonProcessingException{
    String merchantId = "100001";
    String product = "CP01";
//    jedisCluster1.del(Constants.RedisKey.MERCHANTPRODUCTVO_REDIS_KEY+merchantId+product);
//    SettleCycleManage settleCycleManageFind = new SettleCycleManage();
//    settleCycleManageFind.setMerchantId(merchantId);
//    settleCycleManageFind = settleCycleManageDao.getByMerchantId(settleCycleManageFind);
//    System.out.println(mapper.writeValueAsString(settleCycleManageFind));
//    boolean flag = RedisUtil.getRedisUtil().merchantProductRedis("100013", true, false);
    String merchantProduct = jedisCluster1.get(Constants.RedisKey.MERCHANTPRODUCTVO_REDIS_KEY+merchantId+product);
//    jedisCluster1.del(Constants.RedisKey.MERCHANTPRODUCTVO_REDIS_KEY+merchantId+product);
    String fee = jedisCluster1.hget(Constants.RedisKey.PRODUCT_FEE_PRIFIX+merchantId+product,"308SAVING");
//    String merchant = jedisCluster1.get(Constants.RedisKey.MERCHANTVO_REDIS_KEY+merchantId);
//    String defaultfee = jedisCluster1.hget(Constants.RedisKey.PRODUCT_FEE_PRIFIX+"1"+"CP02",Constants.RedisKey.PRODUCT_FEE_NOBANK);
    System.out.println("  merchantProduct   "+merchantProduct);
    System.out.println("  fee   "+fee);
//    System.out.println("  merchant   "+merchant);
//    System.out.println("  defaultfee   "+defaultfee);
//    String filed = "310"+BankcardType.SAVINGCARD.getValue();
//    String productFee = jedisCluster1.hget(Constants.RedisKey.PRODUCT_FEE_PRIFIX+merchantId+product,filed);
//    System.out.println("productFee: "+productFee);
  }
  
  @Test
  public void testGetVo() throws JsonProcessingException{
    String merchantId = "100085";
    String product = "CP08";
    MerchantProductRedis merchantProductVO = new MerchantProductRedis();
    merchantProductVO.setMerchantId(merchantId);
    merchantProductVO.setProductCode(product);
    List<MerchantProductRedis> list = merchantProductVODao.findList(merchantProductVO);
    System.out.println("listdate:"+list.get(0).getValidityDateEnd()+"  list:"+mapper.toJson(list.get(0)));
  }
  
  @Test
  public void testGetRedist() {
    String merchantId = "100467";
    String product = "CP02";
    long merchantProduct = jedisCluster1.del(Constants.RedisKey.MERCHANTPRODUCTVO_REDIS_KEY+merchantId+product);
    System.out.println(merchantProduct);
  }
  
  @Test
  public void testdelRedist() {
    String merchantId = "100013";
    String product = "CP01";
    String merchantProduct = jedisCluster1.get(Constants.RedisKey.MERCHANTPRODUCTVO_REDIS_KEY+merchantId+product);
    System.out.println(merchantProduct);
  }
  
  @Test
  public void testSaveProduct(){
    String merchantId = "100013";
    String productCode = "CP05";
    String productName = "提现";
    MerchantProductVO merchantProductVO = new MerchantProductVO();
    merchantProductVO.setMerchantId(merchantId);
    merchantProductVO.setBackUrl("1");
    merchantProductVO.setNotifyUrl("1");
    merchantProductVO.setProductCode(productCode);
    merchantProductVO.setProductKey("20160930");
    merchantProductVO.setProductName(productName);
    merchantProductVO.setToBalanceOrBankcard(SettlementTo.ACCOUNTBALANCE.getValue());
  //  merchantProductVO.setValidityDateEnd(DateUtil.dateToString(new Date(),"yyyy/MM/dd HH:mm:ss"));//对接产品的有效期截止日 YYYY/MM/DD HH:MM:SS
    merchantProductVO.setSettleCyc("0");
    merchantProductVO.setIsRefundable("false");
    merchantProductVO.setFeeWay(ChargeDeductType.INTERNAL_DEDUCT.getValue());
    String json = mapper.toJson(merchantProductVO);
    jedisCluster1.set(Constants.RedisKey.MERCHANTPRODUCTVO_REDIS_KEY+merchantId+productCode, json);
  }
  
  @Test
  public void testMerchantProductRedis(){
    String merchantId = "100425";
    String buss = "";
    for (ProductType e : ProductType.values()) {
      String productCode = e.getValue();
      RedisUtil.getRedisUtil().saveMerchantProductRedis(merchantId,productCode,true,true,buss);
      String merchantProduct = jedisCluster1.get(Constants.RedisKey.MERCHANTPRODUCTVO_REDIS_KEY+merchantId+productCode);
      System.out.println(merchantProduct);
    }
  }
  
  @Test
  public void testMerchantProductRedis2(){
//    String merchantId = "100015";
//    String productCode = "CP02";
//    MerchantProductRate merchantProductRate = new MerchantProductRate();
//    List<MerchantProductRate> findList = merchantProductRateDao.findList(merchantProductRate);
    Merchant entity = new Merchant();
    List<Merchant> findList = merchantDao.findList(entity);
    for(Merchant merchant : findList){
//      String merchantId = merchantProductRate2.getMerchantId();
//      String productCode = merchantProductRate2.getProductCode();
      String merchantId = merchant.getUserId().toString();
      String productCode = "CP04";
      String buss = "";
      RedisUtil.getRedisUtil().saveMerchantProductRedis(merchantId,productCode,false,false,buss);
    }
  }
  
  @Test
  public void testDefaultMerchantProduct(){
    Merchant entity = new Merchant();
    entity.setStatus("SUCCES");
    List<Merchant> findList = merchantDao.findList(entity);
    for(Merchant merchant : findList){
      String merchantId = merchant.getUserId().toString();
      RedisUtil.getRedisUtil().saveDefaultProductVORedis(merchantId);
    }
  }
  
  @Test
  public void getMerchantVo(){
    MerchantVO merchantVO = merchantDao.getMerchantVo("100425");
    System.out.println(mapper.toJson(merchantVO));
  }
  
  @Test
  public void testMerchantRedis(){
    String merchantId = "100005";
    String productCode = "CP08";
    String bankCardType = "SAVING";
    String bankNo = "102";
    String which = "merchantProduct";
    String money = "1000";
    String buss = "";
    String json = RedisUtil.getRedisUtil().merchantRedis(merchantId, productCode, bankCardType, bankNo, money, buss, which);
    System.out.println(json);
  }
  
  @Test
  public void A(){
    jedisCluster1.del("liuyu@9186.com:CHECK_AMOUNT_FAIL_TIME");
  }
  
  
  @Test
  public void testCount(){
    System.out.println(productDao.count());
  }

  @Test
  public void sata(){
    String merchantId = "100085";
    String productCode = "CP04";
    String au = MerchantAutographType.MD5.getValue();
    String key = RandomUtil.getKey(merchantId, productCode, au);
    System.out.println(key);
  }

  

  @Test
  public void dsf(){
    jedisCluster1.del(Constants.RedisKey.MERCHANTPRODUCTVO_REDIS_KEY + "100005" + "CP08" + "");
  }
}
