/**
 *  
 */
package com.heepay.manage.modules.route.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.route.dao.AreacodeDao;
import com.heepay.manage.modules.route.entity.Areacode;

/**          
* 
* 描    述：市级Service
*
* 创 建 者： 牛文
* 创建时间： 2016年9月29日 上午10:56:56 
* 创建描述：
* 
* 修 改 者：  
* 修改时间： 
* 修改描述： 
* 
* 审 核 者：
* 审核时间：
* 审核描述：
*
*/      
    
@Service
@Transactional(readOnly = true)
public class AreacodeService extends CrudService<AreacodeDao, Areacode> {

	@Autowired
	private AreacodeDao areacodeDao;

	  
	/**     
	* @discription 查找省对应的市县-表areacode
	* @author 牛文
	* @created 2016年9月29日 上午11:17:11     
	* @param id
	* @return     
	*/
	public List<Areacode> findList(String id) {
		return areacodeDao.findList(id);
	}

}