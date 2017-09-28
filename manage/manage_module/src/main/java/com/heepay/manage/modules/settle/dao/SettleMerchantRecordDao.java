package com.heepay.manage.modules.settle.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import com.heepay.manage.modules.settle.entity.SettleMerchantRecord;
import com.heepay.manage.modules.settle.entity.rabbit.SettleMerchantRecordRabbit;

/**
*
* 描    述：用户结算DAO接口
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
public interface SettleMerchantRecordDao extends CrudDao<SettleMerchantRecord> {

	/**
	 * @方法说明：用户结算记录信息导出
	 * @时间：2016年9月19日
	 * @创建人：wangdong
	 */
	List<Map<String, Object>> findListToExcel(SettleMerchantRecord settleMerchantRecord);

	/**
	 * 
	 * @方法说明：获取实体类
	 * @时间：2016年12月5日 下午3:09:59
	 * @创建人：wangdong
	 */
	SettleMerchantRecord get(Long id);

	/**
	 * @方法说明：更新方法
	 * @时间： 2017/7/10 10:22
	 * @创建人：wangl
	 */
    int updateEntity(SettleMerchantRecord settleMerchantRecord);

	/**
	 * @方法说明：查询
	 * @时间： 2017/7/10 10:41
	 * @创建人：wangl
	 */
	SettleMerchantRecordRabbit getEntityBySettleBath(String settleBath);

	/**
	 * @方法说明：交易总金额总和,交易总笔数,订单应结算总金额,交易总手续费
	 * @时间： 2017-08-31 13:49
	 * @创建人：wangl
	 */
	SettleMerchantRecord sumNum(SettleMerchantRecord settleMerchantRecord);
}
