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
import com.heepay.manage.modules.cbms.entity.CbmsCountrysetting;
import com.heepay.manage.modules.cbms.dao.CbmsCountrysettingDao;

/**
 *
 * 描    述：国家代码和编号Service
 *
 * 创 建 者： @author guozx
 * 创建时间： 2017年1月10日 17:00:25
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
public class CbmsCountrysettingService extends CrudService<CbmsCountrysettingDao, CbmsCountrysetting> {

    @Autowired
	private CbmsCountrysettingDao cbmsCountrysettingDao;

	public CbmsCountrysetting get(String id) {
		return super.get(id);
	}
	
	public List<CbmsCountrysetting> findList(CbmsCountrysetting cbmsCountrysetting) {
		return super.findList(cbmsCountrysetting);
	}
	
	public Page<CbmsCountrysetting> findPage(Page<CbmsCountrysetting> page, CbmsCountrysetting cbmsCountrysetting) {
		return super.findPage(page, cbmsCountrysetting);
	}
	
	@Transactional(readOnly = false)
	public void save(CbmsCountrysetting cbmsCountrysetting) {
		super.save(cbmsCountrysetting,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(CbmsCountrysetting cbmsCountrysetting) {
		super.delete(cbmsCountrysetting);
	}

	/**
	 *根据CountryName获取CbmsCountrysetting对象
	 * @return CountryNo
	 */
	public CbmsCountrysetting getCbmsCountrysettingByCountryName(String countryName) {
		return cbmsCountrysettingDao.getCbmsCountrysettingByCountryName(countryName);
	}
}