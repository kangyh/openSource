/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.MerchantLogDao;
import com.heepay.manage.modules.payment.entity.MerchantLog;
import com.heepay.manage.modules.payment.entity.MerchantLogSum;

/**
 *
 * 描    述：账户明细查询Service
 *
 * 创 建 者： @author yq
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
public class MerchantLogService extends CrudService<MerchantLogDao, MerchantLog> {

	@Autowired
  private MerchantLogDao merchantLogDao;

	public MerchantLog get(String id) {
		return super.get(id);
	}
	
	public List<MerchantLog> findList(MerchantLog merchantLog) {
		return super.findList(merchantLog);
	}
	
	public Page<MerchantLog> findPage(Page<MerchantLog> page, MerchantLog merchantLog) {
		return super.findPage(page, merchantLog);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantLog merchantLog) {
		super.save(merchantLog,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantLog merchantLog) {
		super.delete(merchantLog);
	}

	public List<MerchantLog> checkMerchantLog(String transNo, String transType, String accountMark){
		Map<String, Object> params = new HashMap<>();
		params.put("transNo", transNo);
		params.put("transType", transType);
		params.put("accountMark", accountMark);
		return merchantLogDao.checkMerchantLog(params);
	}
	
	public List<MerchantLog> getListByLogIds(Map<String, Object> paramsMap){
	  return merchantLogDao.getListByLogIds(paramsMap);
	}

	public MerchantLog getLogId() {
		return merchantLogDao.getLogId();
	}
	public List<MerchantLogSum> getSumList(Map<String, Object> paramsMap){
		return merchantLogDao.getSumList(paramsMap);	
	}
}