package com.heepay.manage.modules.tpds.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.tpds.entity.TpdsBankCer;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年2月17日下午5:27:04
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
@MyBatisDao
public interface TpdsBankCerDao  extends CrudDao<TpdsBankCer>{
  
	/**
	 * 
	 * @方法说明：保存入库
	 * @时间：9 Feb 201710:31:55
	 * @创建人：wangl
	 */
    int saveEntity(TpdsBankCer record);
    
    
    /**
	 * 
	 * @方法说明：更新对象
	 * @时间：9 Feb 201710:31:55
	 * @创建人：wangl
	 */
    int updateEntity(TpdsBankCer record);
    
    
    /**
	 * @方法说明：根据id查询对象
	 * @时间：9 Feb 201713:06:20
	 * @创建人：wangl
	 */
    TpdsBankCer getEntityById(Integer tpdsBankId);


	/**
	 * @方法说明：
	 * @时间：18 Feb 201715:29:15
	 * @创建人：wangl
	 */
	int changeValue(String bankNo);


	/**
	 * @方法说明：
	 * @时间：18 Feb 201715:55:21
	 * @创建人：wangl
	 */
	int deleteEntity(Integer tpdsBankId);
}