/**
 *  
 */
package com.heepay.manage.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.service.TreeService;
import com.heepay.manage.modules.sys.dao.OfficeDao;
import com.heepay.manage.modules.sys.entity.Office;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
 * 机构Service
 *  
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

    @Autowired
    private OfficeDao officeDao;
    
	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null){
		    if(StringUtils.isNotBlank(office.getParentIds())){
		        office.setParentIds(office.getParentIds());
		    }
			return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office,false);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	  
	/**     
	* @discription 根据机构名称获取机构
	* @author ly     
	* @created 2016年12月1日 下午5:44:10     
	* @param office
	* @return     
	*/
	public Office getOfficeByName(Office office){
        return officeDao.getOfficeByName(office);
    }
	
}
