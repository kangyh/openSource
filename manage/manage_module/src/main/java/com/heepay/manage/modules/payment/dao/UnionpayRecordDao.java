/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.dao;

import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.UnionpayRecord;

/**
 *
 * 描    述：银联扫码支付DAO接口
 *
 * 创 建 者： @author ld
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
public interface UnionpayRecordDao extends CrudDao<UnionpayRecord> {
	
  /**
   * 获取总记录数
  * @description
  * @author 王亚洪       
  * @created 2017年7月10日 下午4:04:54     
  * @param paramsMap
  * @return
   */
  public int getCountByParams(UnionpayRecord unionpayRecord);
  
}