package com.heepay.manage.modules.reconciliation.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelLog;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： wangl
 * 创建时间：  2016年9月23日下午3:29:16
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
@MyBatisDao
public interface SettleChannelLogDao extends CrudDao<SettleChannelLog> {

	List<Map<String, Object>> findListExcel(SettleChannelLog settleChannelLog);

	void addLog(SettleChannelLog settleChannelLog);
	
	//查询对账文件是否路径存在是否是重复上传
	boolean getPath(String path);
	/**
	 * 
	 * @方法说明：获取统计数据
	 * @时间：2017年1月17日 上午10:00:25
	 * @创建人：wangdong
	 */
	List<SettleChannelLog> findCheckDateRemarkSettleChannelLog(SettleChannelLog settleChannelLog);
	
	/**
	 * 
	 * @方法说明：获取code和name
	 * @时间：2017年1月18日 下午4:28:36
	 * @创建人：wangdong
	 */
	List<SettleChannelLog> findChannelCodeAndName();

	/**
	 * @方法说明：
	 * @时间：2017年1月22日下午1:16:11
	 * @创建人：wangl
	 */
	SettleChannelLog getEntityByNo(String checkBathNo);

	/**
	 * @方法说明：根据ID查询对象
	 * @时间：28 Mar 201710:48:19
	 * @创建人：wangl
	 */
	SettleChannelLog getEntityById(long logId);
}
