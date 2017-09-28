/**
 *  
 */
package com.heepay.manage.modules.sys.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heepay.enums.MerchantAccountType;
import com.heepay.manage.common.utils.CacheUtils;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.common.utils.DictList;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.utils.ListEnum;
import com.heepay.manage.common.utils.SpringContextHolder;
import com.heepay.manage.modules.account.dao.MerchantAccountDao;
import com.heepay.manage.modules.account.entity.MerchantAccount;
import com.heepay.manage.modules.merchant.dao.MerchantDao;
import com.heepay.manage.modules.merchant.dao.MerchantIndustryBaseDao;
import com.heepay.manage.modules.merchant.dao.ProductDao;
import com.heepay.manage.modules.merchant.dao.ProductTrxTypeDao;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantIndustryBase;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.merchant.vo.ProductTrxType;
import com.heepay.manage.modules.route.dao.BankInfoDao;
import com.heepay.manage.modules.route.dao.PayChannelDao;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.entity.PayChannel;
import com.heepay.manage.modules.sys.dao.UserDao;
import com.heepay.manage.modules.sys.entity.Dict;
import com.heepay.manage.modules.sys.entity.Office;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.redis.JedisClusterUtil;

/**
 * 业务信息工具类
 * 
 * 审核者： 刘宇
 * 审核时间：2016-08-29
 * 审核描述：审核不通过,没有注释,代码比较乱
 * 
 * @version 2013-5-29
 */
public class BusinessInfoUtils {

    private static BankInfoDao bankInfoDao = SpringContextHolder.getBean(BankInfoDao.class);
    private static ProductDao productDao = SpringContextHolder.getBean(ProductDao.class);
    private static MerchantDao merchantDao = SpringContextHolder.getBean(MerchantDao.class);
    private static PayChannelDao payChannelDao = SpringContextHolder.getBean(PayChannelDao.class);
    private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
    private static ProductTrxTypeDao productTrxTypeDao = SpringContextHolder.getBean(ProductTrxTypeDao.class);
    private static MerchantAccountDao merchantAccountDao = SpringContextHolder.getBean(MerchantAccountDao.class);
    private static MerchantIndustryBaseDao merchantIndustryBaseDao = SpringContextHolder.getBean(MerchantIndustryBaseDao.class);

    public static final String CACHE_PAYCHANNEL_MAP = "payChannelMap";
    public static final String CACHE_MERCHANT_MAP = "merchantMap";
    
    public static final String CACHE_CHANNELPARTNER_MAP = "channelPartnerMap";
    
    public static final String CACHE_PRODUCT_MAP = "productMap";
    public static final String CACHE_BUSINESS_MAP = "businesMap";
    public static final String CACHE_PRODUCT_SETTLE_MAP = "productSettleMap";
    
    public static final String CACHE_MERCHANT_ACCOUNT_MAP = "merchantAccountMap";

    public static final String CACHE_INDUSTRY_CHILD = "industryChildMap";

    public static final String CACHE_INCHARGER = "incharger";

    /**
     * 根据银行ID获取银行名称
     */
    public static String getBankNameByBankId(String bankId) {
        Map<String, BankInfo> bankInfoMap = (Map<String, BankInfo>) CacheUtils.get(CACHE_BUSINESS_MAP);
        BankInfo bankInfo = bankInfoMap.get(bankId);
        if(bankInfo != null){
            return bankInfo.getBankName();
        }
        return "";
    }

    /**
     * 获取银行信息List
     * @return
     */
    public static List<BankInfo> getBankInfoList() {
        @SuppressWarnings("unchecked")
        Map<String, BankInfo> bankInfoMap = (Map<String, BankInfo>) CacheUtils.get(CACHE_BUSINESS_MAP);
        if (bankInfoMap == null) {
            bankInfoMap = Maps.newHashMap();
            for (BankInfo bankInfo : bankInfoDao.selectList()) {
              bankInfoMap.put(bankInfo.getBankNo(), bankInfo);
            }
            CacheUtils.put(CACHE_BUSINESS_MAP, bankInfoMap);
        }

        List<BankInfo> bankInfoList = Lists.newArrayList();
        for (Entry<String, BankInfo> entry : bankInfoMap.entrySet()) {
            bankInfoList.add(entry.getValue());
        }
        Collections.sort(bankInfoList,new Comparator<Object>() {
          public int compare(Object a, Object b) {
            String bankNoFirst = ((BankInfo) a).getBankNo();
            String bankNoSecond = ((BankInfo) b).getBankNo();
            return bankNoFirst.compareTo(bankNoSecond);
          }
        });
        return bankInfoList;
    }

      
    /**     
    * @discription 移除银行信息List
    * @author ly     
    * @created 2016年11月16日 下午5:23:04          
    */
    public static void delBankList(){
      CacheUtils.remove(CACHE_BUSINESS_MAP);
      JedisClusterUtil.getJedisValue(del -> del.del(Constants.BANK_INFO_KEY));
    }
    
    /**
     * 获取产品信息List
     * @return
     */
    public static List<Product> getProductList() {
        @SuppressWarnings("unchecked")
        Map<String, Product> productMap = getProductMap();
        List<Product> productList = Lists.newArrayList();
        for (Entry<String, Product> entry : productMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
            productList.add(entry.getValue());
        }
      return productList;
    }
    
   public static Map<String, Product> getProductMap(){
       Map<String, Product> productMap = (Map<String, Product>) CacheUtils.get(CACHE_PRODUCT_MAP);
       if (productMap == null) {
           productMap = Maps.newHashMap();
           for (Product productVo : productDao.selectList()) {
               productMap.put(productVo.getCode(), productVo);
           }
           CacheUtils.put(CACHE_PRODUCT_MAP, productMap);
       }
       return  productMap;
   }
    
    
    /**
     * 
    * @description 获取内部账户详细类型
    * @author 王亚洪       
    * @created 2017年1月18日 下午2:31:00     
    * @return
     */
    public static List<BankInfo> getInteralAccountDetailsType(){
      List<BankInfo> bankInfoList = getBankInfoList();
      List<EnumBean> enumBeanList = ListEnum.internalAccountTypeNoBank();
      
      for(EnumBean enumBean : enumBeanList){
        BankInfo bInfo = new BankInfo();
        bInfo.setBankNo(enumBean.getValue());
        bInfo.setBankName(enumBean.getName());
        bankInfoList.add(bInfo);
      }
      return bankInfoList;
      
    }
      
    /**     
    * @discription 移除产品信息List
    * @author ly     
    * @created 2016年11月1日 上午11:31:23          
    */
    public static void delProductList(){
      CacheUtils.remove(CACHE_PRODUCT_MAP);
    }
    
    /**
     * 获取产品信息List(结算周期)
     * @return
     */
    public static List<Product> getProductSettleList() {
      CacheUtils.put(CACHE_PRODUCT_SETTLE_MAP, null);
        String settleP = "CP01CP02CP04CP05CP15";
        @SuppressWarnings("unchecked")
        Map<String, Product> productMap = (Map<String, Product>) CacheUtils.get(CACHE_PRODUCT_SETTLE_MAP);
        if (productMap == null) {
            productMap = Maps.newHashMap();
            for (Product productVo : productDao.selectList()) {
              if(-1 == settleP.indexOf(productVo.getCode())){
                productMap.put(productVo.getCode(), productVo);
              }
            }
            CacheUtils.put(CACHE_PRODUCT_SETTLE_MAP, productMap);
        }
        List<Product> productList = Lists.newArrayList();
        for (Entry<String, Product> entry : productMap.entrySet()) {
            productList.add(entry.getValue());
        }
      return productList;
    }

    /**
     * 获取行业信息
     * @return
     */
    public static List<MerchantIndustryBase> getIndustry() {
        @SuppressWarnings("unchecked")
        Map<String, MerchantIndustryBase> merchantIndustryBaseMap = (Map<String, MerchantIndustryBase>) CacheUtils.get(CACHE_INDUSTRY_CHILD);
        if (merchantIndustryBaseMap == null) {
            merchantIndustryBaseMap = Maps.newHashMap();
            for (MerchantIndustryBase merchantIndustryBase : merchantIndustryBaseDao.industryChild("1")) {
                merchantIndustryBaseMap.put(merchantIndustryBase.getIndustryChildId(), merchantIndustryBase);
            }
            CacheUtils.put(CACHE_INDUSTRY_CHILD, merchantIndustryBaseMap);
        }
        List<MerchantIndustryBase> merchantIndustryBaseList = Lists.newArrayList();
        for (Entry<String, MerchantIndustryBase> entry : merchantIndustryBaseMap.entrySet()) {
            merchantIndustryBaseList.add(entry.getValue());
        }
        return merchantIndustryBaseList;
    }


    /**
     * 获取商家信息List
     * @return
     */
    public static List<Merchant> getMerchantList() {
        @SuppressWarnings("unchecked")
        Map<Integer, Merchant> merchantMap = (Map<Integer, Merchant>) CacheUtils.get(CACHE_MERCHANT_MAP);
        if (merchantMap == null) {
            merchantMap = Maps.newHashMap();
            for (Merchant merchantVo : merchantDao.selectList()) {
              merchantMap.put(merchantVo.getUserId(), merchantVo);
            }
            CacheUtils.put(CACHE_MERCHANT_MAP, merchantMap);
        }
        List<Merchant> merchantList = Lists.newArrayList();
        for (Entry<Integer, Merchant> entry : merchantMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
            merchantList.add(entry.getValue());
        }
        return merchantList;
    }
    
    /**
     * 获取注册的商户信息List
     * @return
     */
    public static List<MerchantAccount> getMerchantAccountList() {
        @SuppressWarnings("unchecked")
        Map<Long, MerchantAccount> merchantAccountMap = (Map<Long, MerchantAccount>) CacheUtils.get(CACHE_MERCHANT_ACCOUNT_MAP);
        if (merchantAccountMap == null) {
          merchantAccountMap = Maps.newHashMap();
          MerchantAccount mAccount = new MerchantAccount();
          mAccount.setType(MerchantAccountType.MERCHANT_ACCOUNT.getValue());
          List<MerchantAccount> list = merchantAccountDao.findList(mAccount);
            for (MerchantAccount merchantAccountVo : list) {
              merchantAccountMap.put(merchantAccountVo.getMerchantId(), merchantAccountVo);
            }
            CacheUtils.put(CACHE_MERCHANT_ACCOUNT_MAP, merchantAccountMap);
        }
        List<MerchantAccount> merchantAccountList = Lists.newArrayList();
        for (Entry<Long, MerchantAccount> entry : merchantAccountMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
            merchantAccountList.add(entry.getValue());
        }
        return merchantAccountList;
    }
    
    
    /**
     * 获取支付通道信息List
     * @return
     */
    public static List<PayChannel> getPayChannelList() {
        @SuppressWarnings("unchecked")
        Map<String, PayChannel> payChannelMap = (Map<String, PayChannel>) CacheUtils.get(CACHE_PAYCHANNEL_MAP);
        if (payChannelMap == null) {
            payChannelMap = Maps.newHashMap();
            for (PayChannel payChannel : payChannelDao.selectList()) {
              payChannelMap.put(payChannel.getChannelCode(), payChannel);
            }
            CacheUtils.put(CACHE_PAYCHANNEL_MAP, payChannelMap);
        }
        List<PayChannel> payChannelList = Lists.newArrayList();
        for (Entry<String, PayChannel> entry : payChannelMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
            payChannelList.add(entry.getValue());
        }
        return payChannelList;
    }
    /**
     * 获取通道合作方的List w
     * @return
     */
    public static List<PayChannel> getChannelPartnerList() {
    	@SuppressWarnings("unchecked")
    	Map<String, PayChannel> payChannelMap = (Map<String, PayChannel>) CacheUtils.get(CACHE_CHANNELPARTNER_MAP);
    	if (payChannelMap == null) {
    		payChannelMap = Maps.newHashMap();
    		for (PayChannel payChannel : payChannelDao.selectList()) {
    			payChannelMap.put(payChannel.getChannelPartnerCode(), payChannel);
    		}
    		CacheUtils.put(CACHE_PAYCHANNEL_MAP, payChannelMap);
    	}
    	List<PayChannel> payChannelList = Lists.newArrayList();
    	for (Entry<String, PayChannel> entry : payChannelMap.entrySet()) {
    		System.out.println(entry.getKey() + " : " + entry.getValue());
    		payChannelList.add(entry.getValue());
    	}
    	return payChannelList;
    }
    
    /**
    * @discription 获取该部门id下的用户list
    * @author ly
    * @created 2016年9月6日 下午6:08:54
    * @param id
    * @return
    */
    public static List<User> getUserList(String id){
        User user = new User();
        Office office = new Office();
        office.setId(id);
        user.setOffice(office);
        List<User> users = userDao.findUserByOfficeId(user);
        return users;
    }

    /**
     * @discription 获取维系员工用户list
     * @author ly
     * @created 2016年9月6日 下午6:08:54
     * @return
     */
    public static List<User> getInchargerUser(){
        List<User> users = (List<User>) CacheUtils.get(CACHE_INCHARGER);
        if(users == null){
            users = Lists.newArrayList();
            List<Dict> dictList = DictUtils.getDictList(Constants.INCHARGER);
            for(Dict dict : dictList){
                User user = new User();
                Office office = new Office();
                office.setId(dict.getValue());
                user.setOffice(office);
                List<User> officeUserList = userDao.findUserByOfficeId(user);
                users.addAll(officeUserList);
            }
            CacheUtils.put(CACHE_INCHARGER, users);
        }
        return users;
    }


    /**     
    * @discription 获取交易类型
    * @author ly     
    * @created 2016年11月22日 下午1:59:49     
    * @return     
    */
    public static List<ProductTrxType> getProductTrxType(){
        return productTrxTypeDao.selectList();
    }
    
  
  
    /**
    * @methods getEnumList
    * @description <p>获取枚举类list</p>
    * @author ly
    * @date 2016年8月29日 下午8:11:01
    * @param enumClass
    * @return 参数说明
    * @return List<EnumBean> 返回结果的说明
    */
    public static List<EnumBean> getEnumList(String enumClass){
      List<EnumBean> enumList = Lists.newArrayList();
      switch (enumClass) {
          case "autographType":
            enumList = ListEnum.autographType();
            break;
          case "settleType":
            enumList = ListEnum.settleType();
            break;
          case "commonStatus":
            enumList = ListEnum.commonStatus();
            break;
          case "costType":
            enumList = ListEnum.costType();
            break;
          case "channelPartner":
            enumList = DictList.channelPartner();
            break;
          case "channelType":
            enumList = DictList.channelType();
            break;
          case "merchantFlag":
            enumList = DictList.merchantFlag();
            break;
          case "bankcardType":
            enumList = ListEnum.bankcardType();
            break;
          case "accountType":
            enumList = ListEnum.accountType();
            break;
          case "businessType":
            enumList = ListEnum.businessType();
            break;
          case "settlePeriod":
            enumList = ListEnum.settlePeriod();
            break;
          case "certifyChannelPartner":
            enumList = ListEnum.certifyChannelPartner();
            break;
          case "routeStatus":
            enumList = ListEnum.routeStatus();
            break;
          case "authenticationStatus":
            enumList = ListEnum.authenticationStatus();
            break;
          case "remitStatus":
            enumList = ListEnum.remitStatus();
            break;
          case "rateBusinessType":
            enumList = ListEnum.rateBusinessType();
            break;
          case "chargeCollectionType":
            enumList = ListEnum.chargeCollectionType();
            break;
          case "chargeDeductType":
            enumList = ListEnum.chargeDeductType();
            break;
          case "chargeSource":
            enumList = ListEnum.chargeSource();
            break;
          case "rateBankcardType":
            enumList = ListEnum.rateBankcardType();
            break;
          case "settlementTo":
            enumList = ListEnum.settlementTo();
            break;
          case "merchantAutographType":
            enumList = ListEnum.merchantAutographType();
            break;
          case "yesOrNo":
            enumList = ListEnum.yesOrNo();
            break;
          case "accountStatus":
              enumList = ListEnum.accountStatus();
              break;
          case "subjectStatus":
              enumList = ListEnum.subjectStatus();
              break;
          case "subjectType":
            enumList = ListEnum.subjectType();
            break;
          case "subjectLevel":
            enumList = ListEnum.subjectLevel();
            break;
          case "merchantAccountDirection":
            enumList = ListEnum.merchantAccountDirection();
            break;
          case "internalAccountType":
            enumList = ListEnum.internalAccountType();
            break;
          case "orderFormStatus":
              enumList = ListEnum.orderFormStatus();
              break;
          case "allowSystemType":
              enumList = ListEnum.allowSystemType();
              break;
          case "certificateType":
              enumList = ListEnum.certificateType();
              break;
          case "merchantType":
              enumList = ListEnum.merchantType();
              break;
          case "cbmsEnterpriseType":
              enumList = ListEnum.cbmsEnterpriseType();
              break;
          case "cbmsSupplierCategory":
              enumList = ListEnum.cbmsSupplierCategory();
              break;
          case "cbmsTradeType":
              enumList = ListEnum.cbmsTradeType();
              break;
          case "byProject":
              enumList = DictList.byProject();
              break;
          case "byCompany":
              enumList = DictList.byCompany();
              break;
          case "onlineProduct":
              enumList = DictList.onlineProduct();
              break;
          case "integraChannelPayType":
              enumList = ListEnum.integraChannelPayType();
              break;
          case "merchantStatus":
              enumList = ListEnum.merchantStatus();
              break;
          case "channelReqResult":
              enumList = ListEnum.channelReqResult();
              break;
          case "contractType":
              enumList = ListEnum.contractType();
              break;
          case "installmentType":
              enumList = ListEnum.installmentType();
              break;
          default:
            break;
          }
      return enumList;
    }
}
