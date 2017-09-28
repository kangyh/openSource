/**
 *  
 */
package com.heepay.manage.modules.gen.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.gen.entity.GenTableColumn;

/**
 * 业务表字段DAO接口
 *  
 * @version 2016-06-23
 */
@MyBatisDao
public interface GenTableColumnDao extends CrudDao<GenTableColumn> {
	
	public void deleteByGenTableId(String genTableId);
}
