/**
 *  
 */
package com.heepay.manage.modules.sys.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.sys.entity.HatProvinces;

/**
 * 省DAO接口
 * @author 牛文
 * @version V1.0
 */
@MyBatisDao
public interface HatProvincesDao extends CrudDao<HatProvinces> {
	
	List<HatProvinces> selectList();
	
}