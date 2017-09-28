package com.heepay.manage.modules.tpds.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.tpds.dao.TpdsProductKeyDao;
import com.heepay.manage.modules.tpds.entity.TpdsBindInterface;
import com.heepay.manage.modules.tpds.entity.TpdsProductKey;

/**
 * *
 * 
* 
* 描    述：商户产品密钥配置
*
* 创 建 者： wangjie
* 创建时间：  2017年3月2日下午5:34:28
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
public class TpdsProductKeyService extends CrudService<TpdsProductKeyDao, TpdsProductKey> {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private TpdsProductKeyDao tpdsProductKeyDao;
	
	/**
	 * @方法说明：根据id查询对象
	 * @时间：9 Feb 201712:57:46
	 * @创建人：wangjie
	 */
	public TpdsProductKey getEntityById(Integer id) {
		
		return tpdsProductKeyDao.getEntityById(id);
	}
	
	/**
	 * @方法说明：保存入库的方法
	 * @时间：9 Feb 201714:42:33
	 * @创建人：wangjie
	 */
    @Transactional(readOnly = false)
	public int saveEntity(TpdsProductKey record) {
		
		int num=0;
		try {
			num = tpdsProductKeyDao.saveEntity(record);
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
	public int updateEntity(TpdsProductKey record) {
		
		return tpdsProductKeyDao.updateEntity(record);
	}
    
}
