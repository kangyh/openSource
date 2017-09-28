/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.cbms.dao.CbmsCurrencysettingDao;
import com.heepay.manage.modules.cbms.entity.CbmsCurrencysetting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：币种信息管理Service
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
public class CbmsCurrencysettingService extends CrudService<CbmsCurrencysettingDao, CbmsCurrencysetting> {

	public CbmsCurrencysetting get(String id) {
		return super.get(id);
	}
	
	public List<CbmsCurrencysetting> findList(CbmsCurrencysetting cbmsCurrencysetting) {
		return super.findList(cbmsCurrencysetting);
	}
	
	public Page<CbmsCurrencysetting> findPage(Page<CbmsCurrencysetting> page, CbmsCurrencysetting cbmsCurrencysetting) {
		return super.findPage(page, cbmsCurrencysetting);
	}
	
	@Transactional(readOnly = false)
	public void save(CbmsCurrencysetting cbmsCurrencysetting) {
		super.save(cbmsCurrencysetting,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(CbmsCurrencysetting cbmsCurrencysetting) {
		super.delete(cbmsCurrencysetting);
	}
	
}