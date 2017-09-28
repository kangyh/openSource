/**
 *  
 */
package com.heepay.manage.modules.route.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.route.entity.HatProvince;

/**
 * 省DAO接口
 * @author 牛文
 * @version V1.0
 */
@MyBatisDao
public interface HatProvinceDao extends CrudDao<HatProvince> {
	
	public List<HatProvince> findList();
	
}