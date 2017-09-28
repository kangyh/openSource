/**
 *  
 */
package com.heepay.manage.modules.sys.dao;

import com.heepay.manage.common.persistence.TreeDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.sys.entity.Area;

/**
 * 区域DAO接口
 *  
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}
