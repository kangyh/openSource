package com.heepay.manage.modules.settle.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecordHis;
import java.util.List;
import java.util.Map;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月10日下午1:57:52
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
public interface ClearingMerchantRecordHisDao extends CrudDao<ClearingMerchantRecordHis>{
    int deleteByPrimaryKey(Long hisIs);

    int insert(ClearingMerchantRecordHis record);

    ClearingMerchantRecordHis selectByPrimaryKey(Long hisIs);

    List<ClearingMerchantRecordHis> selectAll();

    int updateByPrimaryKey(ClearingMerchantRecordHis record);

	List<Map<String, Object>> findListToExcel(ClearingMerchantRecordHis clearingMerchantRecordHis);
	
	/**
	 * 
	 * @方法说明：用于商户结算单据查询明细
	 * @时间：2016年10月19日 下午3:12:24
	 * @创建人：wangdong
	 */
	List<ClearingMerchantRecordHis> findPageDetail(ClearingMerchantRecordHis clearingMerchantRecordHis);
}