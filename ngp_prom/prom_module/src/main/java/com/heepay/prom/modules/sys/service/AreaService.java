/**
 *  
 */
package com.heepay.prom.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.prom.common.service.TreeService;
import com.heepay.prom.modules.sys.dao.AreaDao;
import com.heepay.prom.modules.sys.entity.Area;
import com.heepay.prom.modules.sys.utils.UserUtils;

/**
 * 区域Service
 *  
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area,false);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
}
