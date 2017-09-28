package com.heepay.manage.modules.pcac.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pcac.entity.PcacMerchantRepVo;
import com.heepay.manage.modules.pcac.entity.PcacMerchantReport;

import java.util.List;
import java.util.Map;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月3日
* 创建描述：
* 
* 修 改 者： 李震
* 修改时间： 2017-3-21
* 修改描述： 
* 
* 审 核 者：
* 审核时间：
* 审核描述：
*
 */
@MyBatisDao
public interface PcacMerchantReportDao extends CrudDao<PcacMerchantReport>{
		/**
		 * @方法说明：根据主键获取企业商户信息
		 * @时间：2017年3月21日
		 * @创建人：李震
		 */
		public PcacMerchantReport getPcacMerchantReportById(Long merchantReportId) ;
		
		/**
		 * @方法说明：导出Excel,获取全部数据
		 * @时间：2017年3月21日
		 * @创建人：李震
		 */
		List<Map<String, Object>> findMerchantListExcel(PcacMerchantReport record);
		/**
		 * @方法说明：导出Excel,获取选中数据
		 * @时间：2017年3月21日
		 * @创建人：李震
		 */
		List<Map<String, Object>> findMerchantCheckedListExcel(PcacMerchantReport record);
		/**
		 * @方法说明：本地删除企业商户信息
		 * @时间：2017年3月21日
		 * @创建人：李震
		 */
		int deletePcacMerchantReport(Long merchantReportId);
		/**
		 * @方法说明：本地批量删除企业商户信息
		 * @时间：2017年3月21日
		 * @创建人：李震
		 */
		int deleteManyPcacMerchantReport(PcacMerchantReport record);
		/**
		 * @方法说明：查询准备上报的数据
		 * @时间：2017年3月21日
		 * @创建人：李震
		 */
		List<PcacMerchantRepVo> findMerchantRepList(PcacMerchantReport record);
		/**
		 * @方法说明：查询准备上报删除的数据
		 * @时间：2017年3月21日
		 * @创建人：李震
		 */
		List<PcacMerchantRepVo> findMerchantDelRepList(PcacMerchantReport record);
		/**
		 * @方法说明：插入企业商户信息
		 * @时间：2017年3月21日
		 * @创建人：李震
		 */
		int insertMerchantMapReport(@SuppressWarnings("rawtypes") Map map);
		/**
		 * @方法说明：分组查询企业商户信息
		 * @时间：2017年3月21日
		 * @创建人：李震
		 */
		List<PcacMerchantReport> findMerchantListGroupByBatch(PcacMerchantReport record);
		/**
		 * @方法说明：分组查询企业商户上报删除的信息
		 * @时间：2017年3月21日
		 * @创建人：李震
		 */
		List<PcacMerchantReport> findDeleteListGroupByBatch(PcacMerchantReport record);
		/**
		 * @方法说明：更新企业商户信息
		 * @时间：2017年3月21日
		 * @创建人：李震
		 */
		int updatePcacMerchantReport(PcacMerchantReport record);
		/**
		 * @方法说明：根据上报删除表的批次号 查询企业商户表主键
		 * @时间：2017年3月21日
		 * @创建人：李震
		 */
		List<String> getReportIdsByDelBatchNo(String delBatchNo);
		List<String> getReportIdsByRepBatchNo(String batchNo);
}