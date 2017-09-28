/**
 *  
 */
package com.heepay.manage.modules.route.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.route.entity.Areacode;

/**
 * 市级DAO接口
 * @author 牛文
 * @version V1.0
 */
@MyBatisDao
public interface AreacodeDao extends CrudDao<Areacode> {
	
	public List<Areacode> findList(String id);

	public List<Areacode> findAllList();
}