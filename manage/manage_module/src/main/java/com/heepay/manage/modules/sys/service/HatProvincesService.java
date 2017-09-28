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
import com.heepay.manage.modules.sys.dao.HatProvincesDao;
import com.heepay.manage.modules.sys.entity.HatProvinces;

/**
 * 省Service
 * @author 牛文
 * @version V1.0
 */
@Service
@Transactional(readOnly = true)
public class HatProvincesService extends CrudService<HatProvincesDao, HatProvinces> {
	
	  @Autowired
	  private HatProvincesDao hatProvincesDao ;
	  
	  public List<HatProvinces> selectList(){
	    return hatProvincesDao.selectList();
	  }

	public HatProvinces get(String id) {
		return super.get(id);
	}
	
	public List<HatProvinces> findList(HatProvinces hatProvinces) {
		return super.findList(hatProvinces);
	}
	
	public Page<HatProvinces> findPage(Page<HatProvinces> page, HatProvinces hatProvinces) {
		return super.findPage(page, hatProvinces);
	}
	
	@Transactional(readOnly = false)
	public void save(HatProvinces hatProvinces) {
		super.save(hatProvinces,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(HatProvinces hatProvinces) {
		super.delete(hatProvinces);
	}
	
}