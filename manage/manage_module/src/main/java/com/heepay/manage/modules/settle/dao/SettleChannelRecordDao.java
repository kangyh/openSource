package com.heepay.manage.modules.settle.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecord;
import com.heepay.manage.modules.settle.entity.SettleChannelRecord;

/**
*
* 描    述：通道结算DAO接口
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
public interface SettleChannelRecordDao extends CrudDao<SettleChannelRecord> {

	/**
	 * @方法说明：通道结算记录信息导出
	 * @时间：2016年9月19日
	 * @创建人：wangdong
	 */
	List<Map<String, Object>> findListToExcel(SettleChannelRecord settleChannelRecord);

	/**
	 * 
	 * @方法说明：获取通道侧结算记录信息中的通道侧合作方名称不重复的
	 * @时间：2016年11月16日 下午5:47:54
	 * @创建人：wangdong
	 */
	List<SettleChannelRecord> findChannelRecordName();

	/**
	 * 
	 * @方法说明：获取实体类
	 * @时间：2016年12月5日 下午3:12:25
	 * @创建人：wangdong
	 */
	SettleChannelRecord get(Long id);

	/**
	 * @方法说明：更新为不结算
	 * @时间： 7/6/2017 3:56 PM
	 * @创建人：wangl
	 */
    int updateEntity(SettleChannelRecord sttleChannelRecord);

	/**
	 * @方法说明：查询当前数据
	 * @时间： 7/6/2017 3:56 PM
	 * @创建人：wangl
	 */
    SettleChannelRecord getEntity(String settleBath);

	/**
	 * @方法说明：交易总笔数,交易总金额,交易总成本
	 * @时间： 2017-08-31 13:49
	 * @创建人：wangl
	 */
	SettleChannelRecord sumNum(SettleChannelRecord settleChannelRecord);
}
