package com.heepay.manage.modules.differences.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.differences.dao.SettleDifferMerchantHisDao;
import com.heepay.manage.modules.differences.entity.SettleDifferMerchantHis;


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
public class SettleDifferMerchantHisService extends CrudService<SettleDifferMerchantHisDao, SettleDifferMerchantHis> {

	@Autowired
	SettleDifferMerchantHisDao settleDifferMerchantHisDao;

	/**
	 * 
	 * @方法说明：//查询所有记录list
	 * @时间：2017年3月13日17:23:53
	 * @创建人：wangdong
	 */
	public List<SettleDifferMerchantHis> findList(SettleDifferMerchantHis settleDifferMerchantHis) {
		return super.findList(settleDifferMerchantHis);
	}
	
	/**
	 * 
	 * @方法说明：//导出为Excel表格
	 * @时间：2017年3月13日17:23:59
	 * @创建人：wangdong
	 */
	public List<Map<String, Object>> findListExcel(SettleDifferMerchantHis settleDifferMerchantHis) {
		return settleDifferMerchantHisDao.findListExcel(settleDifferMerchantHis);
	}
}
