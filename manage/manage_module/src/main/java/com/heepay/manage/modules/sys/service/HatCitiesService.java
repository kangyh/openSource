/**
 *  
 */
package com.heepay.manage.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.sys.dao.HatCitiesDao;
import com.heepay.manage.modules.sys.entity.HatCities;

/**
 * 市级Service
 * @author 牛文
 * @version V1.0
 */
@Service
@Transactional(readOnly = true)
public class HatCitiesService extends CrudService<HatCitiesDao, HatCities> {
	
	  @Autowired
	  private HatCitiesDao hatCitiesDao;
	  
	  public List<HatCities> selectByFather(String father){
	    return hatCitiesDao.selectByFather(father);
	  }

	public HatCities get(String id) {
		return super.get(id);
	}
	
	public List<HatCities> findList(HatCities hatCities) {
		return super.findList(hatCities);
	}
	
	public Page<HatCities> findPage(Page<HatCities> page, HatCities hatCities) {
		return super.findPage(page, hatCities);
	}
	
	@Transactional(readOnly = false)
	public void save(HatCities hatCities) {
		super.save(hatCities,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(HatCities hatCities) {
		super.delete(hatCities);
	}
	
}