package com.heepay.manage.modules.differences.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.differences.dao.SettleProfitBathDao;
import com.heepay.manage.modules.differences.entity.SettleProfitBath;

/***
 * 
 * 
 * 描    述：分润汇总service
 *
 * 创 建 者： wangl
 * 创建时间：  2016年9月23日下午4:09:20
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
public class SettleProfitBathService extends CrudService<SettleProfitBathDao, SettleProfitBath> {

	@Autowired
	SettleProfitBathDao settleProfitBathDao;
	
	/**
	 * 
	 * @方法说明：//查询所有记录list
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<SettleProfitBath> findList(SettleProfitBath settleProfitBath) {
		return super.findList(settleProfitBath);
	}
	
	/**
	 * 
	 * @方法说明：//导出为Excel表格
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<Map<String, Object>> findListExcel(SettleProfitBath settleProfitBath) {
		return settleProfitBathDao.findListExcel(settleProfitBath);
	}

	
}
