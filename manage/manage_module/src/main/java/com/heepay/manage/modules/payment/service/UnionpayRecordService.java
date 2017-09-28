/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.entity.UnionpayRecord;
import com.heepay.manage.modules.payment.dao.UnionpayRecordDao;

/**
 *
 * 描    述：银联扫码支付Service
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
@Service
@Transactional(readOnly = true)
public class UnionpayRecordService extends CrudService<UnionpayRecordDao, UnionpayRecord> {

  @Autowired
  private UnionpayRecordDao unionpayRecordDao;
  
	public UnionpayRecord get(String id) {
		return super.get(id);
	}
	
	public List<UnionpayRecord> findList(UnionpayRecord unionpayRecord) {
		return super.findList(unionpayRecord);
	}
	
	public Page<UnionpayRecord> findPage(Page<UnionpayRecord> page, UnionpayRecord unionpayRecord) {
		return super.findPage(page, unionpayRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(UnionpayRecord unionpayRecord) {
		super.save(unionpayRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(UnionpayRecord unionpayRecord) {
		super.delete(unionpayRecord);
	}
	
	public int getCountByParams(UnionpayRecord unionpayRecord){
	  return unionpayRecordDao.getCountByParams(unionpayRecord);
	}
}