/**
 *  
 */
package com.heepay.prom.modules.gen.dao;

import com.heepay.prom.common.persistence.CrudDao;
import com.heepay.prom.common.persistence.annotation.MyBatisDao;
import com.heepay.prom.modules.gen.entity.GenTableColumn;

/**
 * 业务表字段DAO接口
 *  
 * @version 2016-06-23
 */
@MyBatisDao
public interface GenTableColumnDao extends CrudDao<GenTableColumn> {
	
	public void deleteByGenTableId(String genTableId);
}
