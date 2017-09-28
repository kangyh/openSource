package com.heepay.manage.modules.settle.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecord;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import org.apache.ibatis.annotations.Param;

/**
*
* 描    述：用户清算记录DAO接口
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
public interface ClearingMerchantRecordDao extends CrudDao<ClearingMerchantRecord> {

	/**
	 * @方法说明：用户清算记录信息导出
	 * @时间：2016年9月19日
	 * @创建人：wangdong
	 */
	List<Map<String, Object>> findListToExcel(ClearingMerchantRecord clearingMerchantRecord);

	/**
	 * 
	 * @方法说明：用于商户结算单据查询明细
	 * @时间：2016年10月19日 下午3:12:24
	 * @创建人：wangdong
	 */
	List<ClearingMerchantRecord> findPageDetail(ClearingMerchantRecord clearingMerchantRecord);

	/**
	 * 
	 * @方法说明：获取实体类
	 * @时间：2016年12月5日 下午3:12:54
	 * @创建人：wangdong
	 */
	ClearingMerchantRecord get(Long id);
	
	/**
	 * 
	 * @方法说明：清结算财务报表
	 * @时间：2017年3月28日 下午5:44:35
	 * @创建人：wangdong
	 */
	List<ClearingMerchantRecord> findReportList(ClearingMerchantRecord clearingMerchantRecord);

	/**
	 * 
	 * @方法说明：导出财务报表
	 * @时间：2017年3月29日 上午11:56:57
	 * @创建人：wangdong
	 */
	List<Map<String, Object>> findListToReportExcel(ClearingMerchantRecord clearingMerchantRecord);

	//运营报表
	List<ClearingMerchantRecord> findDetailPage(ClearingMerchantRecord clearingMerchantRecord);
	//运营报表
	List<Map<String, Object>> findListDetailReportExcel(ClearingMerchantRecord clearingMerchantRecord);

	/**
	 * @方法说明：更具ID查询对象
	 * @时间： 2017-04-10 03:37 PM
	 * @创建人：wangl
	 */
    ClearingMerchantRecord getEntityById(Long clearingId);

	/**
	 * @方法说明：查询当前对象
	 * @时间： 2017-05-19 11:13 AM
	 * @创建人：wangl
	 */
    ClearingMerchantRecord getEntity(@Param(value = "transType") String transType,@Param(value = "transNo") String transNo);

	/**
	 * @方法说明：实际支付金额总和,订单应结算金额总和,订单手续费总和
	 * @时间： 2017-08-31 13:49
	 * @创建人：wangl
	 */
	ClearingMerchantRecord sumNum(ClearingMerchantRecord clearingMerchantRecord);
}
