/**
 *  
 */
package com.heepay.prom.modules.gen.dao;

import com.heepay.prom.common.persistence.CrudDao;
import com.heepay.prom.common.persistence.annotation.MyBatisDao;
import com.heepay.prom.modules.gen.entity.GenTable;

/**
 * 业务表DAO接口
 *  
 * @version 2016-06-23
 */
@MyBatisDao
public interface GenTableDao extends CrudDao<GenTable> {
	
}
