package com.heepay.manage.modules.tpds.service;

/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.tpds.dao.TpdsFileLogDao;
import com.heepay.manage.modules.tpds.entity.TpdsFileLog;

/**
 *
 * 描    述：存管日记Service
 *
 * 创 建 者： @author wj
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
public class TpdsFileLogService extends CrudService<TpdsFileLogDao, TpdsFileLog> {
	
	@Autowired
	private TpdsFileLogDao tpdsFileLogDao;
	
	public TpdsFileLog get(String id) {
		return super.get(id);
	}
	
	public List<TpdsFileLog> findList(TpdsFileLog tpdsFileLog) {
		return super.findList(tpdsFileLog);
	}
	
	public Page<TpdsFileLog> findPage(Page<TpdsFileLog> page, TpdsFileLog tpdsFileLog) {
		return super.findPage(page, tpdsFileLog);
	}
	
	@Transactional(readOnly = false)
	public void save(TpdsFileLog tpdsFileLog) {
		super.save(tpdsFileLog,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(TpdsFileLog tpdsFileLog) {
		super.delete(tpdsFileLog);
	}
	
	@Transactional(readOnly = false)
	public int updateEntity(TpdsFileLog tpdsFileLog){
		return tpdsFileLogDao.updateEntity(tpdsFileLog);
	}
	
}