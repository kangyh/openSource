/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.gatewayaccount.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.gatewayaccount.entity.DayGatewayPayStatForInput;

/**
 *
 * 描    述：网关支付核账DAO接口
 *
 * 创 建 者： @author 王亚洪
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
public interface DayGatewayPayStatForInputDao extends CrudDao<DayGatewayPayStatForInput> {
	  
  public int getByChannelCodeAndGroupTimeCount(Map<String, Object> paramsMap);
  
  public int getByChannelPartnerAndGroupTimeCount(Map<String, Object> paramsMap);
  
  public int getBySuccessAmount(Map<String, Object> paramsMap);
  
  public List<Map<String, Object>> getGatewaySelect();
  
  public int updateDayGateWayPayStatForInput(DayGatewayPayStatForInput dayGatewayPayStatForInput);
  
  public List<Map<String, Object>> getDirconList();
  
  public List<Map<String, Object>> getThirdPartyList();
  
}