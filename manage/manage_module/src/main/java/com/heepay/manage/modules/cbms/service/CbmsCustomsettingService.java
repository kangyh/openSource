/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.cbms.entity.CbmsCustomsetting;
import com.heepay.manage.modules.cbms.dao.CbmsCustomsettingDao;

/**
 *
 * 描    述：海关信息设置Service
 *
 * 创 建 者： @author 牛俊鹏
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
public class CbmsCustomsettingService extends CrudService<CbmsCustomsettingDao, CbmsCustomsetting> {
	@Autowired
	private CbmsCustomsettingDao cbmsCustomsettingDao;

	public CbmsCustomsetting get(String id) {
		return super.get(id);
	}
	/**
	 *根据获取CbmsCustomsetting表中的所有数据
	 * @param
	 * @return
	 * @author 牛俊鹏
	 */
	public List<CbmsCustomsetting> findcustomsettinglist() {
		return cbmsCustomsettingDao.findcustomsettinglist();
	}
	public List<CbmsCustomsetting> findList(CbmsCustomsetting cbmsCustomsetting) {
		return super.findList(cbmsCustomsetting);
	}
	
	public Page<CbmsCustomsetting> findPage(Page<CbmsCustomsetting> page, CbmsCustomsetting cbmsCustomsetting) {
		return super.findPage(page, cbmsCustomsetting);
	}
	
	@Transactional(readOnly = false)
	public void save(CbmsCustomsetting cbmsCustomsetting) {
		super.save(cbmsCustomsetting,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(CbmsCustomsetting cbmsCustomsetting) {
		super.delete(cbmsCustomsetting);
	}
	
}