package com.heepay.manage.modules.tpds.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.modules.tpds.dao.TpdsBankCerDao;
import com.heepay.manage.modules.tpds.entity.TpdsBankCer;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年2月17日下午5:26:28
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
@Service
@Transactional(readOnly = true)
public class TpdsBankCerService extends CrudService<TpdsBankCerDao, TpdsBankCer>{

	@Autowired
	private TpdsBankCerDao tpdsBankCerDao;
	
	
		/**
		 * 
		 * @方法说明：转换商户银行卡显示枚举类的值和内容转换
		 * @时间：18 Feb 201711:20:34
		 * @创建人：wangl
		 */
	    public static List<TpdsBankCer> getKeyValue(List<TpdsBankCer> tpdsBankCer) {
	        List<TpdsBankCer> list = Lists.newArrayList();
	        
	        for (TpdsBankCer TpdsBankCers : tpdsBankCer) {
	        	
	        	//证书文件存储路径
				if(StringUtils.isNoneBlank(TpdsBankCers.getCerPath())){
					TpdsBankCers.setCerPath(RandomUtil.getFastDfs(TpdsBankCers.getCerPath()));
				}
				
	        	//公钥
	            if (StringUtils.isNotBlank(TpdsBankCers.getPubKey())) {
	                //String pubKey = Aes.decryptStr(TpdsBankCers.getPubKey(), Constants.QuickPay.SYSTEM_KEY);
	                TpdsBankCers.setPubKey(TpdsBankCerService.getKey(TpdsBankCers.getPubKey()));
	            }
	            
	            //私钥
	            if (StringUtils.isNotBlank(TpdsBankCers.getPriKey())) {
	            	//String priKey = Aes.decryptStr(TpdsBankCers.getPriKey(), Constants.QuickPay.SYSTEM_KEY);
	            	//TpdsBankCers.setPriKey(TpdsBankCerService.getKey(priKey));
	            	TpdsBankCers.setPriKey(TpdsBankCerService.getKey(TpdsBankCers.getPriKey()));
	            }
	            
	            
	            list.add(TpdsBankCers);
	        }
	        return list;
	    }
	    
	    /**
	     * 
	     * @方法说明：将私钥转换
	     * @时间：18 Feb 201711:23:45
	     * @创建人：wangl
	     */
	    public static String getKey(String key) {
	        return key.substring(0, 1) + "*****" + key.substring(key.length() - 1);
	    }
	    
	    
	    /**
	     * 
	     * @方法说明：将明文加密
	     * @时间：18 Feb 201711:23:45
	     * @创建人：wangl
	     */
	    public static String setKey(String value) {
	    	String key = Aes.encryptStr(value, Constants.QuickPay.SYSTEM_KEY);
	    	return key;
	    }

		/**
		 * @方法说明：根据id查询对象
		 * @时间：18 Feb 201711:56:26
		 * @创建人：wangl
		 */
		public TpdsBankCer getEntityById(Integer tpdsBankId) {
			
			return tpdsBankCerDao.getEntityById(tpdsBankId);
		}

		/**
		 * @方法说明：保存入库
		 * @时间：18 Feb 201713:26:09
		 * @创建人：wangl
		 */
		@Transactional(readOnly = false)
		public int saveEntity(TpdsBankCer tpdsBankCer) {
			int num=0;
			try {
				num = tpdsBankCerDao.saveEntity(tpdsBankCer);
			} catch (Exception e) {
			}
			return num;
		}

		/**
		 * @方法说明：修改操作
		 * @时间：18 Feb 201713:27:06
		 * @创建人：wangl
		 */
		@Transactional(readOnly = false)
		public int updateEntity(TpdsBankCer tpdsBankCer) {
			int num=0;
			try {
				num = tpdsBankCerDao.updateEntity(tpdsBankCer);
			} catch (Exception e) {
			}
			return num;
		}
	    
		/**
		 * @方法说明：验证是否存在商户id
		 * @时间：18 Feb 201715:26:52
		 * @创建人：wangl
		 */
		public int changeValue(String bankNo) {
			
			return tpdsBankCerDao.changeValue(bankNo);
		}

		/**
		 * @方法说明：删除操作
		 * @时间：18 Feb 201715:50:04
		 * @创建人：wangl
		 */
		@Transactional(readOnly = false)
		public int delete(Integer tpdsBankId) {
			
			return tpdsBankCerDao.deleteEntity(tpdsBankId);
		}  
	    
}
