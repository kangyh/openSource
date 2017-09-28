/**
 *  
 */
package com.heepay.manage.modules.route.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.route.dao.HatProvinceDao;
import com.heepay.manage.modules.route.entity.HatProvince;


/**          
* 
* 描    述：市级Service
*
* 创 建 者： 牛文
* 创建时间： 2016年9月29日 下午1:46:49 
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
public class HatProvinceService extends CrudService<HatProvinceDao, HatProvince> {

	@Autowired
	private HatProvinceDao hatProvincedao;


	  
	/**     
	* @discription 查找省份-表hat_province
	* @author 牛文
	* @created 2016年9月29日 下午1:46:54     
	* @return     
	*/
	public List<HatProvince> findList() {
		return hatProvincedao.findList();
		
	}
	
}