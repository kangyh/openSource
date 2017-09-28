package com.heepay.manage.modules.settle.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecord;

/**
*
* 描    述：通道清算记录DAO接口
*
* 创 建 者： @author wangdong
* 创建时间：
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
public interface ClearingChannelRecordDao extends CrudDao<ClearingChannelRecord> {

	/**
	 * @方法说明：通道清算记录信息导出
	 * @时间：2016年9月19日
	 * @创建人：wangdong
	 */
	List<Map<String, Object>> findListExcel(ClearingChannelRecord clearingChannelRecord);

	/**
	 * @方法说明：通道清算记录表中的通道名称
	 * @时间：2016年9月26日
	 * @创建人：wangdong
	 */
	List<ClearingChannelRecord> findChannelRecordName();

	/**
	 * 
	 * @方法说明：获取实体类
	 * @时间：2016年12月5日 下午3:13:28
	 * @创建人：wangdong
	 */
	ClearingChannelRecord get(Long id);


	/**
	 * @方法说明：根据ID查询对象
	 * @时间： 2017-04-10 06:10 PM
	 * @创建人：wangl
	 */
    ClearingChannelRecord getEntityById(Long clearingId);

    /**
     * @方法说明：实际支付金额和交易成本
     * @时间： 2017-08-31 13:49
     * @创建人：wangl
     */
	ClearingChannelRecord sumNum(ClearingChannelRecord clearingChannelRecord);
}
