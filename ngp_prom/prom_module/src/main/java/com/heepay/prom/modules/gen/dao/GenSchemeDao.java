/**
 *  
 */
package com.heepay.prom.modules.gen.dao;

import com.heepay.prom.common.persistence.CrudDao;
import com.heepay.prom.common.persistence.annotation.MyBatisDao;
import com.heepay.prom.modules.gen.entity.GenScheme;

/**
 * 生成方案DAO接口
 *  
 * @version 2016-06-23
 */
@MyBatisDao
public interface GenSchemeDao extends CrudDao<GenScheme> {
	
}
