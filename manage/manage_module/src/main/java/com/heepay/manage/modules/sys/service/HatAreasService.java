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
import com.heepay.manage.modules.sys.dao.HatAreasDao;
import com.heepay.manage.modules.sys.entity.HatAreas;

/**
 * 县Service
 * @author 牛文
 * @version V1.0
 */
@Service
@Transactional(readOnly = true)
public class HatAreasService extends CrudService<HatAreasDao, HatAreas> {
	

   @Autowired
   private HatAreasDao hatAreasDao;
   
   public List<HatAreas> selectByFather(String father){
	     return hatAreasDao.selectByFather(father);
   }

	public HatAreas get(String id) {
		return super.get(id);
	}
	
	public List<HatAreas> findList(HatAreas hatAreas) {
		return super.findList(hatAreas);
	}
	
	public Page<HatAreas> findPage(Page<HatAreas> page, HatAreas hatAreas) {
		return super.findPage(page, hatAreas);
	}
	
	@Transactional(readOnly = false)
	public void save(HatAreas hatAreas) {
		super.save(hatAreas,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(HatAreas hatAreas) {
		super.delete(hatAreas);
	}
	
}