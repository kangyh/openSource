/**
 *  
 */
package com.heepay.prom.modules.sys.dao;

import java.util.List;

import com.heepay.prom.common.persistence.CrudDao;
import com.heepay.prom.common.persistence.annotation.MyBatisDao;
import com.heepay.prom.modules.sys.entity.Menu;

/**
 * 菜单DAO接口
 *  
 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
}
