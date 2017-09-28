package com.heepay.tpds.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.redis.JedisClusterUtil;
import com.heepay.rpc.service.RpcService;
import com.heepay.tpds.dao.TpdsBankCerMapper;
import com.heepay.tpds.dao.TpdsBindInterfaceMapper;
import com.heepay.tpds.dao.TpdsCutDayMapper;
import com.heepay.tpds.dao.TpdsMerchantAccountMapper;
import com.heepay.tpds.dao.TpdsMerchantCerMapper;
import com.heepay.tpds.dao.TpdsMerchantH5Mapper;
import com.heepay.tpds.dao.TpdsProductKeyMapper;
import com.heepay.tpds.entity.TpdsBankCer;
import com.heepay.tpds.entity.TpdsBindInterface;
import com.heepay.tpds.entity.TpdsCutDay;
import com.heepay.tpds.entity.TpdsMerchantAccount;
import com.heepay.tpds.entity.TpdsMerchantCer;
import com.heepay.tpds.entity.TpdsMerchantH5;
import com.heepay.tpds.entity.TpdsProductKey;
import com.heepay.tpds.util.Constants;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * 
 * 描 述：保存配置信息
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年2月17日 下午3:20:18
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
@Component
@RpcService(name="ConfigServiceImpl",processor=com.heepay.rpc.tpds.service.ConfigService.Processor.class)
public class ConfigServiceImpl implements com.heepay.rpc.tpds.service.ConfigService.Iface  {
    
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	private TpdsBankCerMapper tpdsBankCerMapper;
	@Autowired
	private TpdsBindInterfaceMapper tpdsBindInterfaceMapper;
	@Autowired
	private TpdsMerchantCerMapper tpdsMerchantCerMapper;
	@Autowired
	private TpdsMerchantAccountMapper tpdsMerchantAccountMapper;
	@Autowired
	private TpdsCutDayMapper tpdsCutDayMapper;
	@Autowired
	private TpdsMerchantH5Mapper tpdsMerchantH5Mapper;
	@Autowired
	private TpdsProductKeyMapper tpdsProductKeyMapper;
	
	@Override
	/**
	 * 
	  * 
	  * @方法说明：添加商户台账
	  * @author dongzhengjiang
	  * @param 商户台账实体json格式
	  * @return
	  * @时间：2017年4月20日 下午1:35:01
	 */
	public String addMerchantAccount(String MerchantAccountEntity) throws TException {
		TpdsMerchantAccount entity=JsonMapperUtil.buildNonDefaultMapper().fromJson(MerchantAccountEntity, TpdsMerchantAccount.class);
		if(JedisClusterUtil.getJedisCluster().get(Constants.getMerchantAccountKey(entity.getSystemNo()))!=null)
		{
			return "-1";
		}
		this.refreshCache();
		JedisClusterUtil.getJedisCluster().set(Constants.getMerchantAccountKey(entity.getSystemNo()),JsonMapperUtil.buildNonDefaultMapper().toJson(entity));
		return String.valueOf(tpdsMerchantAccountMapper.insert(entity));
	}

	@Override
	/**
	 * 
	  * 
	  * @方法说明：编辑商户台账
	  * @author dongzhengjiang
	  * @param 商户台账实体json格式
	  * @return
	  * @时间：2017年4月20日 下午1:35:48
	 */
	public String editMerchantAccount(String MerchantAccountEntity) throws TException {
		TpdsMerchantAccount entity=JsonMapperUtil.buildNonDefaultMapper().fromJson(MerchantAccountEntity, TpdsMerchantAccount.class);
		this.refreshCache();
		JedisClusterUtil.getJedisCluster().set(Constants.getMerchantAccountKey(entity.getSystemNo()),JsonMapperUtil.buildNonDefaultMapper().toJson(entity));
		return String.valueOf(tpdsMerchantAccountMapper.update(entity));
	}

	@Override
	public String addBindInterface(String BindInterfaceEntity) throws TException {
		TpdsBindInterface entity=JsonMapperUtil.buildNonDefaultMapper().fromJson(BindInterfaceEntity, TpdsBindInterface.class);
		return String.valueOf(tpdsBindInterfaceMapper.insert(entity));
	}

	@Override
	public String editBindInterface(String BindInterfaceEntity) throws TException {
		TpdsBindInterface entity=JsonMapperUtil.buildNonDefaultMapper().fromJson(BindInterfaceEntity, TpdsBindInterface.class);
		return String.valueOf(tpdsBindInterfaceMapper.update(entity));
	}

	@Override
	/**
	 * 
	  * 
	  * @方法说明：添加银行密钥信息
	  * @author dongzhengjiang
	  * @param 银行密钥信息json格式
	  * @return
	  * @时间：2017年4月20日 下午1:36:22
	 */
	public String addBankCer(String BankCerEntity) throws TException {
		TpdsBankCer entity=JsonMapperUtil.buildNonDefaultMapper().fromJson(BankCerEntity, TpdsBankCer.class);
		JedisClusterUtil.getJedisCluster().set(Constants.getBankCertKey(entity.getBankNo()),JsonMapperUtil.buildNonDefaultMapper().toJson(entity));
		return String.valueOf(tpdsBankCerMapper.insert(entity));
	}

	@Override
	/**
	 * 
	  * 
	  * @方法说明：编辑银行密钥信息
	  * @author dongzhengjiang
	  * @param 银行密钥信息json格式
	  * @return
	  * @时间：2017年4月20日 下午1:36:53
	 */
	public String editBankCer(String BankCerEntity) throws TException {
		TpdsBankCer entity=JsonMapperUtil.buildNonDefaultMapper().fromJson(BankCerEntity, TpdsBankCer.class);
		JedisClusterUtil.getJedisCluster().set(Constants.getBankCertKey(entity.getBankNo()),JsonMapperUtil.buildNonDefaultMapper().toJson(entity));
		return String.valueOf(tpdsBankCerMapper.update(entity));
	}

	@Override
	/**
	 * 
	  * 
	  * @方法说明：添加商户密钥信息
	  * @author dongzhengjiang
	  * @param 商户密钥信息json格式
	  * @return
	  * @时间：2017年4月20日 下午1:37:18
	 */
	public String addMerchantCer(String MerchantCerEntity) throws TException {
		TpdsMerchantCer entity=JsonMapperUtil.buildNonDefaultMapper().fromJson(MerchantCerEntity, TpdsMerchantCer.class);
		JedisClusterUtil.getJedisCluster().set(Constants.getMerchantCertKey(entity.getMerchantNo()),JsonMapperUtil.buildNonDefaultMapper().toJson(entity));
		return String.valueOf(tpdsMerchantCerMapper.insert(entity));
	}

	@Override
	/**
	 * 
	  * 
	  * @方法说明：编辑商户密钥信息
	  * @author dongzhengjiang
	  * @param 商户密钥信息json格式
	  * @return
	  * @时间：2017年4月20日 下午1:37:46
	 */
	public String editMerchantCer(String MerchantCerEntity) throws TException {
		TpdsMerchantCer entity=JsonMapperUtil.buildNonDefaultMapper().fromJson(MerchantCerEntity, TpdsMerchantCer.class);
		JedisClusterUtil.getJedisCluster().set(Constants.getMerchantCertKey(entity.getMerchantNo()),JsonMapperUtil.buildNonDefaultMapper().toJson(entity));
		return String.valueOf(tpdsMerchantCerMapper.update(entity));
	}

	@Override
	public String addCutDay(String CutDayEntity) throws TException {
		TpdsCutDay entity=JsonMapperUtil.buildNonDefaultMapper().fromJson(CutDayEntity, TpdsCutDay.class);
		return String.valueOf(tpdsCutDayMapper.insert(entity));
	}

	@Override
	public String editCutDay(String CutDayEntity) throws TException {
		TpdsCutDay entity=JsonMapperUtil.buildNonDefaultMapper().fromJson(CutDayEntity, TpdsCutDay.class);
		return String.valueOf(tpdsCutDayMapper.update(entity));
	}
	 /**
	    * 初始化缓存接口
	    */
		@PostConstruct
		private void initRuleCache()
		{   
			//刷新商户台账缓存
			this.ClearMerchantAccount();
			//刷新银行密钥缓存
			this.ClearBankCert();
			//刷新商户密钥缓存
			this.ClearMerchantCert();
			//刷新产品密钥缓存
			this.ClearProductKey();
		}
		private void refreshCache()
		{
			JedisClusterUtil.getJedisCluster().del(Constants.TPDS_MERCHANTACCOUNT_LIST);
		}
		public TreeSet<String> keys(String pattern){  
	        //logger.debug("Start getting keys...");  
	        TreeSet<String> keys = new TreeSet<>();  
	        Map<String, JedisPool> clusterNodes = JedisClusterUtil.getJedisCluster().getClusterNodes();  
	        for(String k : clusterNodes.keySet()){  
	            JedisPool jp = clusterNodes.get(k);  
	            Jedis connection = jp.getResource();  
	            try {  
	                keys.addAll(connection.keys(pattern));  
	            } catch(Exception e){  
	                logger.error("Getting keys error: {}", e);  
	            } finally{  
	                //logger.debug("Connection closed.");  
	                connection.close();//用完一定要close这个链接！！！  
	            }  
	        } 
	        return keys;  
	    }
        /**
         * 
          * 
          * @方法说明：
          * @author dongzhengjiang
          * @param sysNo:系统编码
          * @return 存在则返回 TpdsMerchantAccount实体的json字符串 没有返回 ""
          * @时间：2017年2月21日 下午4:47:06
         */
		@SuppressWarnings("unchecked")
		@Override
		public String checksysNo(String sysNo) throws TException {
			
			List<TpdsMerchantAccount> list=null;
			
			String msg=JedisClusterUtil.getJedisCluster().get(Constants.TPDS_MERCHANTACCOUNT_LIST);
			if(StringUtil.isBlank(msg))
			{
				list=tpdsMerchantAccountMapper.getList();
				String result=JsonMapperUtil.buildNonDefaultMapper().toJson(list);
				JedisClusterUtil.getJedisCluster().set(Constants.TPDS_MERCHANTACCOUNT_LIST, result);
			}
			else
			{   
				
				JavaType javaType=JsonMapperUtil.buildNonDefaultMapper().createCollectionType(List.class,TpdsMerchantAccount.class);
				list=(List<TpdsMerchantAccount>)JsonMapperUtil.nonDefaultMapper().fromJson(msg,javaType);
			}
			for(TpdsMerchantAccount account:list)
			{
				if(account.getSystemNo().equals(sysNo)&&account.getStatus().equals("ENABLE"))
					return JsonMapperUtil.nonDefaultMapper().toJson(account);
			}
			return "";
		}
		  /**
         * 
          * 
          * @方法说明：
          * @author dongzhengjiang
          * @param sysNo:系统编码
          * @return 存在则返回 TpdsMerchantAccount实体的json字符串 没有返回 ""
          * @时间：2017年2月21日 下午4:47:06
         */
		public static String GetBySysNo(String sysNo)
		{   
			String str=JedisClusterUtil.getJedisCluster().get(Constants.getMerchantAccountKey(sysNo));
			return str;		
		}
		@Override
		public String addMerchantH5(String merchantH5Entity) throws TException {
			TpdsMerchantH5 h5=JsonMapperUtil.nonDefaultMapper().fromJson(merchantH5Entity,TpdsMerchantH5.class);
			String result=JedisClusterUtil.getJedisCluster().get(Constants.getMerchantH5Key(h5.getBusinessSeqNo()));
			if(StringUtil.isBlank(result))
				tpdsMerchantH5Mapper.insert(h5);
			else
				tpdsMerchantH5Mapper.update(h5);
			JedisClusterUtil.getJedisCluster().set(Constants.getMerchantH5Key(h5.getBusinessSeqNo()), merchantH5Entity);
			return "1";
		}

		@Override
		public String getMerchantBySeqNo(String businessSeqNo) throws TException {
			String result=JedisClusterUtil.getJedisCluster().get(Constants.getMerchantH5Key(businessSeqNo));
			if(StringUtil.isBlank(result))
				return "0";
			else
			{
				TpdsMerchantH5 h5=JsonMapperUtil.nonDefaultMapper().fromJson(result,TpdsMerchantH5.class);
				if(h5.getFlag().equals("1"))
					return "1";
				else
					return "0";
			}
		}

		@Override
		public String addproductKey(String productKeyEntity) throws TException {
			TpdsProductKey entity=JsonMapperUtil.buildNonDefaultMapper().fromJson(productKeyEntity, TpdsProductKey.class);
			
			//this.refreshCache();
			JedisClusterUtil.getJedisCluster().set(Constants.getProductKey(entity.getMerchantNo(),entity.getProductCode()),JsonMapperUtil.buildNonDefaultMapper().toJson(entity));
			return String.valueOf(tpdsProductKeyMapper.insert(entity));
		}

		@Override
		public String editproductKey(String productKeyEntity) throws TException {
	     TpdsProductKey entity=JsonMapperUtil.buildNonDefaultMapper().fromJson(productKeyEntity, TpdsProductKey.class);
			
			//this.refreshCache();
			JedisClusterUtil.getJedisCluster().set(Constants.getProductKey(entity.getMerchantNo(),entity.getProductCode()),JsonMapperUtil.buildNonDefaultMapper().toJson(entity));
			return String.valueOf(tpdsProductKeyMapper.updateByPrimaryKey(entity));
		}

		@Override
		/**
		 * 
		  * 
		  * @方法说明：根据银行编码获取银行密钥信息
		  * @author dongzhengjiang
		  * @param 银行编码
		  * @return
		  * @时间：2017年4月20日 下午1:40:44
		 */
		public String getBankCerByBankNo(String bankNo) throws TException {
			String result=JedisClusterUtil.getJedisCluster().get(Constants.getBankCertKey(bankNo));
			if(StringUtil.isBlank(result))
				return null;
			else
				return result;
		}

		@Override
		/**
		 * 
		  * 
		  * @方法说明：根据商户编码获取商户密钥信息
		  * @author dongzhengjiang
		  * @param 
		  * @return
		  * @时间：2017年4月20日 下午1:41:20
		 */
		public String getMerchantCerByMerchantNo(String merchantNo) throws TException {
			String result=JedisClusterUtil.getJedisCluster().get(Constants.getMerchantCertKey(merchantNo));
			if(StringUtil.isBlank(result))
				return null;
			else
				return result;
		}
		/**
		 * 
		  * 
		  * @方法说明：清除商户台账密钥
		  * @author dongzhengjiang
		  * @param 
		  * @return
		  * @时间：2017年4月20日 下午1:45:05
		 */
		private void ClearMerchantAccount()
		{
			JedisClusterUtil.getJedisCluster().del(Constants.TPDS_MERCHANTACCOUNT_LIST);
			Set<String> merchantKeys = this.keys("tpds_MERCHANTACCOUNT*");
			for (String merchantkey:merchantKeys)
			{
				logger.info("删除tpds_MERCHANTACCOUNT key:"+merchantkey);
				JedisClusterUtil.getJedisCluster().del(merchantkey);
			}
			List<TpdsMerchantAccount> list=tpdsMerchantAccountMapper.getList();
			
			if(list!=null&&!list.isEmpty())
			{    
				String msg=JsonMapperUtil.buildNonDefaultMapper().toJson(list);
				logger.info("商户台账记录缓存:{}",msg);
				JedisClusterUtil.getJedisCluster().set(Constants.TPDS_MERCHANTACCOUNT_LIST, msg);
				for(TpdsMerchantAccount account:list)
				{   
					logger.info("添加tpds_MERCHANTACCOUNT key:"+Constants.getMerchantAccountKey(account.getSystemNo()));
					JedisClusterUtil.getJedisCluster().set(Constants.getMerchantAccountKey(account.getSystemNo()),JsonMapperUtil.buildNonDefaultMapper().toJson(account));
				}
			}
		}
		/**
		 * 
		  * 
		  * @方法说明：清除商户密钥缓存
		  * @author dongzhengjiang
		  * @param 
		  * @return
		  * @时间：2017年4月20日 下午1:45:10
		 */
		private void ClearMerchantCert()
		{
			Set<String> Keys = this.keys("tpds_MERCHANTCERT*");
			for (String key:Keys)
			{
				logger.info("删除tpds_MERCHANTCERT key:"+key);
				JedisClusterUtil.getJedisCluster().del(key);
			}
			List<TpdsMerchantCer> productKeylist=tpdsMerchantCerMapper.getlist();
			if(productKeylist!=null&&!productKeylist.isEmpty())
			{    
				for(TpdsMerchantCer productKey:productKeylist)
				{   
					logger.info("添加tpds_MERCHANTCERT key:"+Constants.getMerchantCertKey(productKey.getMerchantNo()));
					JedisClusterUtil.getJedisCluster().set(Constants.getMerchantCertKey(productKey.getMerchantNo()),JsonMapperUtil.buildNonDefaultMapper().toJson(productKey));
				}
			}
		}
		/**
		 * 
		  * 
		  * @方法说明：清除银行密钥缓存
		  * @author dongzhengjiang
		  * @param 
		  * @return
		  * @时间：2017年4月20日 下午1:45:14
		 */
		private void ClearBankCert()
		{
			Set<String> productKeys = this.keys("tpds_BANKCERT*");
			for (String productkey:productKeys)
			{
				logger.info("删除tpds_BANKCERT key:"+productkey);
				JedisClusterUtil.getJedisCluster().del(productkey);
			}
			List<TpdsBankCer> productKeylist=tpdsBankCerMapper.getlist();
			if(productKeylist!=null&&!productKeylist.isEmpty())
			{    
				for(TpdsBankCer productKey:productKeylist)
				{   
					logger.info("添加tpds_BANKCERT key:"+Constants.getBankCertKey(productKey.getBankNo()));
					JedisClusterUtil.getJedisCluster().set(Constants.getBankCertKey(productKey.getBankNo()),JsonMapperUtil.buildNonDefaultMapper().toJson(productKey));
				}
			}
		}
		/**
		 * 
		  * 
		  * @方法说明：清除产品密钥缓存
		  * @author dongzhengjiang
		  * @param 
		  * @return
		  * @时间：2017年4月20日 下午1:45:20
		 */
		private void ClearProductKey()
		{
			Set<String> productKeys = this.keys("tpds_PRODUCTKEY*");
			for (String productkey:productKeys)
			{
				logger.info("删除tpds_PRODUCTKEY key:"+productkey);
				JedisClusterUtil.getJedisCluster().del(productkey);
			}
			List<TpdsProductKey> productKeylist=tpdsProductKeyMapper.getList();
			if(productKeylist!=null&&!productKeylist.isEmpty())
			{    
				for(TpdsProductKey productKey:productKeylist)
				{   
					logger.info("添加tpds_PRODUCTKEY key:"+Constants.getProductKey(productKey.getMerchantNo(),productKey.getProductCode()));
					JedisClusterUtil.getJedisCluster().set(Constants.getProductKey(productKey.getMerchantNo(),productKey.getProductCode()),JsonMapperUtil.buildNonDefaultMapper().toJson(productKey));
				}
			}
		}

		@Override
		public String getProductKey(String merchantNo, String productCode) throws TException {
			String result=JedisClusterUtil.getJedisCluster().get(Constants.getProductKey(merchantNo, productCode));
			if(StringUtil.isBlank(result))
				return null;
			else
				return result;
		}

		@Override
		public String delBankCer(String bankNo) throws TException {
			
			int i=tpdsBankCerMapper.deleteByBankNo(bankNo);
			if(i==1)
				JedisClusterUtil.getJedisCluster().del(Constants.getBankCertKey(bankNo));
			return String.valueOf(i);
		}

		@Override
		public String delMerchantCer(String merchantNo) throws TException {
			int i=tpdsMerchantCerMapper.deleteByMerchantNo(merchantNo);
			if(i==1)
				JedisClusterUtil.getJedisCluster().del(Constants.getMerchantCertKey(merchantNo));
			return String.valueOf(i);
		}

}

 