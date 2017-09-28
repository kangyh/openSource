/**
 *  
 */
package com.heepay.prom.modules.gen.dao;

import com.heepay.prom.common.persistence.CrudDao;
import com.heepay.prom.common.persistence.annotation.MyBatisDao;
import com.heepay.prom.modules.gen.entity.GenTemplate;

/**
 * 代码模板DAO接口
 *  
 * @version 2016-06-23
 */
@MyBatisDao
public interface GenTemplateDao extends CrudDao<GenTemplate> {
	
}
