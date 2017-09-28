/**
 *  
 */
package com.heepay.manage.modules.gen.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.gen.entity.GenTable;

/**
 * 业务表DAO接口
 *  
 * @version 2016-06-23
 */
@MyBatisDao
public interface GenTableDao extends CrudDao<GenTable> {
	
}
