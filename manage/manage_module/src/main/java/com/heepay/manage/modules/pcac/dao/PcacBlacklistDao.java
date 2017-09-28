package com.heepay.manage.modules.pcac.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pcac.entity.PcacBlacklist;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecord;

import java.util.List;
import java.util.Map;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月3日下午5:14:25
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
public interface PcacBlacklistDao extends CrudDao<PcacBlacklist>{
    int deleteByPrimaryKey(Long blackId);

    int insert(PcacBlacklist record);
    
    int insertList(List<PcacBlacklist> record);
    
    PcacBlacklist selectByPrimaryKey(Long blackId);

    List<PcacBlacklist> selectAll();

    int updateByPrimaryKey(PcacBlacklist record);
    
	/**
	 * @方法说明：通道清算记录信息导出
	 * @时间：2016年9月19日
	 * @创建人：wangdong
	 */
	List<Map<String, Object>> findListExcel(PcacBlacklist pcacBlacklist);
}