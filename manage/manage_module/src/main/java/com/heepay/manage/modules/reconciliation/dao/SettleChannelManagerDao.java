package com.heepay.manage.modules.reconciliation.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;

/***
 * 
 * 
 * 描    述：导出为excel
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
public interface SettleChannelManagerDao extends CrudDao<SettleChannelManager> {

	//导出为excel
	List<Map<String, Object>> findListExcel(SettleChannelManager settleChannelManager);

	//获取整个对象
	SettleChannelManager getEntity(int channelManangeId);
	//删除操作
	void deleteEntity(int channelManangeId);

	void saveEntity(SettleChannelManager settleChannelManager);

	void addEntity(SettleChannelManager settleChannelManager);

	List<SettleChannelManager> getChannel();

	//对账规则根据批次号查询出来的通道名称
	String getChannelName(String channelCode);

	List<SettleChannelManager> getBatchName();

	String getLocalPath(SettleChannelManager settleChannelManager);
	//对账业务类型
	List<SettleChannelManager> getChannelType();

	/**
	 * 
	 * @方法说明：为上传文件是选择通道名称和类型获取数据
	 * @时间：Nov 16, 2016
	 * @创建人：wangl
	 */
	List<SettleChannelManager> getBatchManagerName(SettleChannelManager settleChannelManager);

	/**
	 * 
	 * @方法说明：为上传文件是选择通道名称和类型获取数据
	 * @时间：Nov 16, 2016
	 * @创建人：wangl
	 */
	List<SettleChannelManager> getManagerChannelType(SettleChannelManager settleChannelManager);

	
	/**
	 * @方法说明：根据通道类型和通道编码查询出多个对象
	 * @时间：2017年2月6日下午1:07:35
	 * @创建人：wangl
	 */
	List<SettleChannelManager> getSettleChangeManagerEntity(SettleChannelManager changeManager);

	/**
	 *
	 * @方法说明：更新操作
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	void updateEntity(SettleChannelManager settleChannelManager);

	/**
	 *
	 * @方法说明：删除操作
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
    void deleteEntityById(Integer channelManangeId);
    
    /**
     * 将用户名和密码加密更新
     */
	void updateAll(Map<String, String> map);
}
