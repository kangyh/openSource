/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.adjust.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.adjust.entity.AdjustAccount;
import com.heepay.manage.modules.allocate.entity.AllocateRecord;
import com.heepay.manage.modules.adjust.dao.AdjustAccountDao;

/**
 *
 * 描    述：调账Service
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
public class AdjustAccountService extends CrudService<AdjustAccountDao, AdjustAccount> {

  @Autowired
  private AdjustAccountDao adjustAccountDao;
  
	public AdjustAccount get(String id) {
		return super.get(id);
	}
	
	public List<AdjustAccount> findList(AdjustAccount adjustAccount) {
		return super.findList(adjustAccount);
	}
	
	public Page<AdjustAccount> findPage(Page<AdjustAccount> page, AdjustAccount adjustAccount) {
		return super.findPage(page, adjustAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(AdjustAccount adjustAccount) {
		super.save(adjustAccount,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(AdjustAccount adjustAccount) {
		super.delete(adjustAccount);
	}
	
	public String getMaxSerialNo(Map<String, Object> paramsMap){
	  Map<String, Object> resultMap = Maps.newHashMap();
	  resultMap = adjustAccountDao.getMaxSerialNo(paramsMap);
	  if(resultMap == null || resultMap.isEmpty()){
	    return "";
	  }
	  return (String) resultMap.get("serialNo");
	}
	
	@Transactional(readOnly = false)
	public int updateAdjustAccount(AdjustAccount adjustAccount){
	  return adjustAccountDao.updateAdjustAccount(adjustAccount);
	}
	
	
}