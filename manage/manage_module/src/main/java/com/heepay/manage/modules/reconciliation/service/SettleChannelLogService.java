package com.heepay.manage.modules.reconciliation.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.reconciliation.dao.SettleChannelLogDao;
import com.heepay.manage.modules.reconciliation.dao.SettleChannelManagerDao;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelLog;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;

/***
 * 
 * 
 * 描    述：对账日志service
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
public class SettleChannelLogService extends CrudService<SettleChannelLogDao, SettleChannelLog> {

	@Autowired
	SettleChannelLogDao settleChannelLogDao;
	
	@Autowired
	SettleChannelManagerDao settleChannelManagerDao;
	
	/**
	 * @方法说明：获取用户清算记录List
	 * @author wangl
	 * @时间： 2016年9月14日11:07:48
	 */
	public List<SettleChannelLog> findList(SettleChannelLog settleChannelLog) {
		return super.findList(settleChannelLog);
	}

	/**
	 * 
	 * @方法说明：数据导出
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<Map<String, Object>> findListExcel(SettleChannelLog settleChannelLog) {
		return settleChannelLogDao.findListExcel(settleChannelLog);
	}

	/**
	 * 
	 * @方法说明：根据参数查询路径
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public String getLocalPath(SettleChannelManager settleChannelManager) {
		
		return settleChannelManagerDao.getLocalPath(settleChannelManager);
	}
	
	/**
	 * 
	 * @方法说明：获取统计数据
	 * @时间：2017年1月17日 上午9:58:54
	 * @创建人：wangdong
	 */
	public List<SettleChannelLog> findCheckDateRemarkSettleChannelLog(SettleChannelLog settleChannelLog) {
		return settleChannelLogDao.findCheckDateRemarkSettleChannelLog(settleChannelLog);
	}
	
	/**
	 * 
	 * @方法说明：
	 * @时间：2017年1月18日 下午1:30:14
	 * @创建人：wangdong
	 */
	public List<SettleChannelLog> findChannelCodeAndName(){
		return settleChannelLogDao.findChannelCodeAndName();
	}

	/**
	 * @方法说明：根据 对账批次号 查询文件地址
	 * @时间：2017年1月22日下午1:14:17
	 * @创建人：wangl
	 */
	public SettleChannelLog getEntityByNo(String checkBathNo) {
		
		return settleChannelLogDao.getEntityByNo(checkBathNo);
	}

	/**
	 * @方法说明：根据ID查询对象
	 * @时间：28 Mar 201710:47:40
	 * @创建人：wangl
	 */
	public SettleChannelLog getEntityById(long logId) {
		
		return settleChannelLogDao.getEntityById(logId);
	}
	
}
