/**
 *  
 */
package com.heepay.manage.modules.route.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.route.dao.UbankcodeDao;
import com.heepay.manage.modules.route.entity.Ubankcode;

/**          
* 
* 描    述：支行代码Service
*
* 创 建 者： 牛文
* 创建时间： 2016年9月29日 下午1:47:23 
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
public class UbankcodeService extends CrudService<UbankcodeDao, Ubankcode> {

	@Autowired
	private UbankcodeDao ubankcodeDao;

	  
	/**     
	* @discription 由银行、市县（石家庄市)查找开户银行-表ubankcode
	* @author 牛文
	* @created 2016年9月29日 下午1:47:27     
	* @param id
	* @param bankNo
	* @return     
	*/
	public List<Ubankcode> findList(String id,String bankNo) {
		return ubankcodeDao.findList(id, bankNo);
	}
	
	
}