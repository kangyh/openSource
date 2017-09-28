package com.heepay.manage.modules.differences.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.differences.entity.SettleDifferChannel;

/***
 * 
* 
* 描    述：
*
* 创 建 者：wangl
* 创建时间：  Nov 2, 20162:17:08 PM
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
public interface SettleDifferChannelDao extends CrudDao<SettleDifferChannel> {

	List<Map<String, Object>> findListExcel(SettleDifferChannel settleDifferChannel);

	SettleDifferChannel getEntityById(int differMerbillId);

	void updateEntity(SettleDifferChannel settleDifferChannel);
	
	void updateErrorStatusById(Map<String, Object> map);
   
}