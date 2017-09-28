/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.gatewayaccount.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.gatewayaccount.entity.DayGatewayPayStatForInput;
import com.heepay.manage.modules.gatewayaccount.dao.DayGatewayPayStatForInputDao;

/**
 *
 * 描    述：网关支付核账Service
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
@Service
@Transactional(readOnly = true)
public class DayGatewayPayStatForInputService extends CrudService<DayGatewayPayStatForInputDao, DayGatewayPayStatForInput> {

  @Autowired
  private DayGatewayPayStatForInputDao dayGatewayPayStatForInputDao;
  
	public DayGatewayPayStatForInput get(String id) {
		return super.get(id);
	}
	
	public List<DayGatewayPayStatForInput> findList(DayGatewayPayStatForInput dayGatewayPayStatForInput) {
		return super.findList(dayGatewayPayStatForInput);
	}
	
	public Page<DayGatewayPayStatForInput> findPage(Page<DayGatewayPayStatForInput> page, DayGatewayPayStatForInput dayGatewayPayStatForInput) {
		return super.findPage(page, dayGatewayPayStatForInput);
	}
	
	@Transactional(readOnly = false)
	public void save(DayGatewayPayStatForInput dayGatewayPayStatForInput) {
		super.save(dayGatewayPayStatForInput,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(DayGatewayPayStatForInput dayGatewayPayStatForInput) {
		super.delete(dayGatewayPayStatForInput);
	}
	
	public int getByChannelCodeAndGroupTimeCount(String groupTime, String channelCode){
	  Map<String, Object> paramsMap = new HashMap<String, Object>();
	  paramsMap.put("groupTime", groupTime);
	  paramsMap.put("channelCode", channelCode);
    return dayGatewayPayStatForInputDao.getByChannelCodeAndGroupTimeCount(paramsMap);
  }
	
	public int getBySuccessAmount(String successCount, String successAmount){
    Map<String, Object> paramsMap = new HashMap<String, Object>();
    paramsMap.put("successBillCount", successCount);
    paramsMap.put("successTradeAmt", successAmount);
    return dayGatewayPayStatForInputDao.getBySuccessAmount(paramsMap);
  }
	
	
	public List<Map<String, Object>> getGatewaySelect(){
	  return dayGatewayPayStatForInputDao.getGatewaySelect();
	}
	
	public int getByChannelPartnerAndGroupTimeCount(String groupTime, String channelPartners){
    Map<String, Object> paramsMap = new HashMap<String, Object>();
    paramsMap.put("groupTime", groupTime);
    paramsMap.put("channelPartners", channelPartners);
    return dayGatewayPayStatForInputDao.getByChannelPartnerAndGroupTimeCount(paramsMap);
  }
	
	@Transactional(readOnly = false)
	public int updateDayGateWayPayStatForInput(DayGatewayPayStatForInput dayGatewayPayStatForInput){
	  return dayGatewayPayStatForInputDao.updateDayGateWayPayStatForInput(dayGatewayPayStatForInput);
	}
	
	
	public List<Map<String, Object>> getDirconList(){
	    return dayGatewayPayStatForInputDao.getDirconList();
	}
	
	
	public List<Map<String, Object>> getThirdPartyList(){
	    return dayGatewayPayStatForInputDao.getThirdPartyList();
	}
	
	
}