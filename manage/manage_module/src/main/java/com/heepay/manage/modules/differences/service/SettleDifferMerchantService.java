package com.heepay.manage.modules.differences.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.differences.dao.SettleDifferMerchantDao;
import com.heepay.manage.modules.differences.entity.SettleDifferMerchant;


/***
 * 
* 
* 描    述：商户差错账汇总service
*
* 创 建 者：wangl
* 创建时间：  Nov 3, 20169:41:28 AM
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
public class SettleDifferMerchantService extends CrudService<SettleDifferMerchantDao, SettleDifferMerchant> {

	@Autowired
	SettleDifferMerchantDao settleDifferMerchantDao;

	/**
	 * 
	 * @方法说明：//查询所有记录list
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<SettleDifferMerchant> findList(SettleDifferMerchant settleDifferMerchant) {
		return super.findList(settleDifferMerchant);
	}
	
	/**
	 * 
	 * @方法说明：//导出为Excel表格
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<Map<String, Object>> findListExcel(SettleDifferMerchant settleDifferMerchant) {
		return settleDifferMerchantDao.findListExcel(settleDifferMerchant);
	}

	/**
	 * 
	 * @方法说明：根据id查询整个对象
	 * @时间：Nov 15, 2016
	 * @创建人：wangl
	 */
	public SettleDifferMerchant getEntityById(int differMerbillId) {
		
		return settleDifferMerchantDao.getEntityById(differMerbillId);
	}

	/**
	 * 
	 * @方法说明：更新审核状态 和 添加备注
	 * @时间：Nov 16, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(SettleDifferMerchant settleDifferMerchant) {
		
		settleDifferMerchantDao.updateEntity(settleDifferMerchant);
	}
	
	@Transactional(readOnly = false)
	public void updateErrorStatusById(Long differMerbillId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("differMerbillId", differMerbillId);
		map.put("errorStatus", "Y");
		settleDifferMerchantDao.updateErrorStatusById(map);
	}
	
}
