package com.heepay.manage.modules.differences.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.differences.dao.SettleDifferChannelHisDao;
import com.heepay.manage.modules.differences.entity.SettleDifferChannelHis;

/***
 * 
 * 
 * 描 述：通道差错账汇总历史表service
 *
 * 创 建 者： wangl 
 * 创建时间： 2016年9月23日下午4:09:20 创建描述：
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
public class SettleDifferChannelHisService extends CrudService<SettleDifferChannelHisDao, SettleDifferChannelHis> {

	@Autowired
	SettleDifferChannelHisDao settleDifferChannelHisDao;
	
	/**
	 * 
	 * @方法说明：//查询所有记录list
	 * @时间：2017年3月13日17:22:49
	 * @创建人：wangdong
	 */
	public List<SettleDifferChannelHis> findList(SettleDifferChannelHis settleDifferChannelHis) {
		return super.findList(settleDifferChannelHis);
	}
	
	/**
	 * 
	 * @方法说明：//导出为Excel表格
	 * @时间：2017年3月13日17:22:52
	 * @创建人：wangdong
	 */
	public List<Map<String, Object>> findListExcel(SettleDifferChannelHis settleDifferChannelHis) {
		return settleDifferChannelHisDao.findListExcel(settleDifferChannelHis);
	}
}
