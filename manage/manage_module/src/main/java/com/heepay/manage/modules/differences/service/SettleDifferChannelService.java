package com.heepay.manage.modules.differences.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.differences.dao.SettleDifferChannelDao;
import com.heepay.manage.modules.differences.entity.SettleDifferChannel;

/***
 * 
 * 
 * 描 述：通道差错账汇总service
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
public class SettleDifferChannelService extends CrudService<SettleDifferChannelDao, SettleDifferChannel> {

	@Autowired
	SettleDifferChannelDao settleDifferChannelDao;
	
	/**
	 * 
	 * @方法说明：//查询所有记录list
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<SettleDifferChannel> findList(SettleDifferChannel settleDifferChannel) {
		return super.findList(settleDifferChannel);
	}
	
	/**
	 * 
	 * @方法说明：//导出为Excel表格
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<Map<String, Object>> findListExcel(SettleDifferChannel settleDifferChannel) {
		return settleDifferChannelDao.findListExcel(settleDifferChannel);
	}

	/**
	 * 
	 * @方法说明：根据id查找整个对象
	 * @时间：Nov 22, 2016
	 * @创建人：wangl
	 */
	public SettleDifferChannel getEntityById(int differMerbillId) {
		
		return settleDifferChannelDao.getEntityById(differMerbillId);
	}

	/**
	 * 
	 * @方法说明：更新
	 * @时间：Nov 22, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(SettleDifferChannel settleDifferChannel) {
		
		settleDifferChannelDao.updateEntity(settleDifferChannel);
	}
	
	/**
	 * 
	 * @方法说明：更新记账状态
	 * @author chenyanming
	 * @param differMerbillId
	 * @时间：2017年8月14日上午10:51:36
	 */
	@Transactional(readOnly = false)
	public void updateErrorStatusById(Long differChanbillId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("differChanbillId", differChanbillId);
		map.put("errorStatus", "Y");
		settleDifferChannelDao.updateErrorStatusById(map);
	}
}
