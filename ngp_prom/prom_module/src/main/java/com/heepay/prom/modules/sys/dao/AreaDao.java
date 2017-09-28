/**
 *  
 */
package com.heepay.prom.modules.sys.dao;

import com.heepay.prom.common.persistence.TreeDao;
import com.heepay.prom.common.persistence.annotation.MyBatisDao;
import com.heepay.prom.modules.sys.entity.Area;

/**
 * 区域DAO接口
 *  
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}
