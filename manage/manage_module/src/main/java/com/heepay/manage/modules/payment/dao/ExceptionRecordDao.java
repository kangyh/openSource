/**
 *  
 */
package com.heepay.manage.modules.payment.dao;

import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.ExceptionRecord;

/**
 * 
* 
* 描    述：投诉处理
*
* 创 建 者： yanxb  
* 创建时间： 2016年11月29日 下午6:27:21 
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
public interface ExceptionRecordDao extends CrudDao<ExceptionRecord> {

	ExceptionRecord getRecordByTransNo(String transNo);
	int reset(Map<String, Object> map);

}