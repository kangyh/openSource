/**
 *  
 */
package com.heepay.manage.modules.sys.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.sys.entity.HatCities;

/**
 * 市级DAO接口
 * @author 牛文
 * @version V1.0
 */
@MyBatisDao
public interface HatCitiesDao extends CrudDao<HatCities> {
	
	List<HatCities> selectByFather(String father);
	
}