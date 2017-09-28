package com.heepay.manage.modules.differences.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.differences.dao.ClearingProfitRecordDao;
import com.heepay.manage.modules.differences.entity.ClearingProfitRecord;

/***
 * 
 * 
 * 描    述：分润明细service
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
public class ClearingProfitRecordService extends CrudService<ClearingProfitRecordDao, ClearingProfitRecord> {

	@Autowired
	ClearingProfitRecordDao clearingProfitRecordDao;
	
	/**
	 * 
	 * @方法说明：//查询所有记录list
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<ClearingProfitRecord> findList(ClearingProfitRecord clearingProfitRecord) {
		return super.findList(clearingProfitRecord);
	}
	
	/**
	 * 
	 * @方法说明：//导出为Excel表格
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<Map<String, Object>> findListExcel(ClearingProfitRecord clearingProfitRecord) {
		return clearingProfitRecordDao.findListExcel(clearingProfitRecord);
	}
}
