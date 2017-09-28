package com.heepay.manage.modules.tpds.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.tpds.dao.TpdsMerchantAccountDao;
import com.heepay.manage.modules.tpds.entity.TpdsMerchant;
import com.heepay.manage.modules.tpds.entity.TpdsMerchantAccount;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年2月17日下午5:26:49
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
public class TpdsMerchantAccountService extends CrudService<TpdsMerchantAccountDao, TpdsMerchantAccount>{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private TpdsMerchantAccountDao tpdsMerchantAccountDao;
	/**
	 * @方法说明：根据id查询对象
	 * @时间：9 Feb 201712:57:46
	 * @创建人：wangjie
	 */
	public TpdsMerchantAccount getEntityById(Integer merchantId) {
		
		return tpdsMerchantAccountDao.getEntityById(merchantId);
	}
	
	/**
	 * @方法说明：保存入库的方法
	 * @时间：9 Feb 201714:42:33
	 * @创建人：wangjie
	 */
    @Transactional(readOnly = false)
	public int saveEntity(TpdsMerchantAccount record) {
		
		int num=0;
		try {
			num = tpdsMerchantAccountDao.saveEntity(record);
		} catch (Exception e) {
			logger.error("商户信息表保存入库的方法--->{异常}"+e.getMessage());
		}
		return num;
	}
    
    /**
	 * @方法说明：更新操作
	 * @时间：9 Feb 201716:19:07
	 * @创建人：wangjie
	 */
    @Transactional(readOnly = false)
	public int updateEntity(TpdsMerchantAccount record) {
		
		return tpdsMerchantAccountDao.updateEntity(record);
	}
    
    /**
	 * @方法说明：
	 * @时间：9 Feb 201716:19:07
	 * @创建人：wangjie
	 */
    @Transactional(readOnly = false)
	public TpdsMerchantAccount selectByMerchantNo(String merchantNo) {
		
		return tpdsMerchantAccountDao.selectByMerchantNo(merchantNo);
	}
    
    /**
   	 * @方法说明：
   	 * @时间：9 Feb 201716:19:07
   	 * @创建人：wangjie
   	 */
     @Transactional(readOnly = false)
   	public TpdsMerchantAccount selectBySystemNo(String systemNo) {
   		
   		return tpdsMerchantAccountDao.selectBySystemNo(systemNo);
   	}

}
