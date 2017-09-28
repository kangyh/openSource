/**
 *  
 */
package com.heepay.manage.modules.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.enums.GatewayRecordStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.GatewayRecordDao;
import com.heepay.manage.modules.payment.entity.GatewayRecord;

/**
 * 交易管理Service
 * @author ld
 * @version V1.0
 */
@Service
@Transactional(readOnly = true)
public class GatewayRecordService extends CrudService<GatewayRecordDao, GatewayRecord> {
	
	@Autowired
	GatewayRecordDao gatewayRecordDao;

	public GatewayRecord get(String id) {
		return super.get(id);
	}
	
	public GatewayRecord getOne(String merchantOrderNo){
		return gatewayRecordDao.getOne(merchantOrderNo);
	}
	
	public List<GatewayRecord> findList(GatewayRecord gatewayRecord) {
		return super.findList(gatewayRecord);
	}
	
	public Page<GatewayRecord> findPage(Page<GatewayRecord> page, GatewayRecord gatewayRecord) {
		return super.findPage(page, gatewayRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(GatewayRecord gatewayRecord) {
		super.save(gatewayRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(GatewayRecord gatewayRecord) {
		super.delete(gatewayRecord);
	}
	
	@Transactional(readOnly = false)
	public void freeze(GatewayRecord gatewayRecord) {
		gatewayRecord.setStatus(GatewayRecordStatus.FREEZED.getValue());
		gatewayRecordDao.freezeAndThaw(gatewayRecord);
	}
	
	@Transactional(readOnly = false)
	public void thaw(GatewayRecord gatewayRecord) {
		gatewayRecord.setStatus(GatewayRecordStatus.PAYING.getValue());
		gatewayRecordDao.freezeAndThaw(gatewayRecord);
	}

	public GatewayRecord getGatewayId() {
		return gatewayRecordDao.getGatewayId();
	}
	@Transactional(readOnly = false)
	public int updateGatewayRecordPAYING(String id) {
    return gatewayRecordDao.updateGatewayRecordPAYING(id);
  }
	
}