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
import com.heepay.manage.modules.cbms.entity.CbmsSuppliersetting;
import com.heepay.manage.modules.cbms.dao.CbmsSuppliersettingDao;

/**
 *
 * 描    述：商户信息显示Service
 *
 * 创 建 者： @author guozx
 * 创建时间： 2017年1月9日 09:51:25
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
public class CbmsSuppliersettingService extends CrudService<CbmsSuppliersettingDao, CbmsSuppliersetting> {

	@Autowired
	private CbmsSuppliersettingDao cbmsSuppliersettingDao;

	public CbmsSuppliersetting get(String id) {
		return super.get(id);
	}
	
	public List<CbmsSuppliersetting> findList(CbmsSuppliersetting cbmsSuppliersetting) {
		return super.findList(cbmsSuppliersetting);
	}
	
	public Page<CbmsSuppliersetting> findPage(Page<CbmsSuppliersetting> page, CbmsSuppliersetting cbmsSuppliersetting) {
		return super.findPage(page, cbmsSuppliersetting);
	}
	
	@Transactional(readOnly = false)
	public void save(CbmsSuppliersetting cbmsSuppliersetting) {
		super.save(cbmsSuppliersetting,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(CbmsSuppliersetting cbmsSuppliersetting) {
		super.delete(cbmsSuppliersetting);
	}

	/**
	 *根据merchantNo获取CbmsCbmsSuppliersetting对象
	 * @param merchantNo
	 * @return
	 */
    public CbmsSuppliersetting getMerchantByNo(Integer merchantNo) {
    	return cbmsSuppliersettingDao.getMerchantByNo(merchantNo);
    }

	/**
	 * 跨境商户列表显示
	 * @param cbmsSuppliersetting
	 * @return
	 */
	public Page<CbmsSuppliersetting> findCbmsSuppliersettingList(Page<CbmsSuppliersetting> page,CbmsSuppliersetting cbmsSuppliersetting) {
		cbmsSuppliersetting.setPage(page);
		page.setList(cbmsSuppliersettingDao.findCbmsSuppliersettingList(cbmsSuppliersetting));
		return page;
	}
	/**
	 * 跨境商户列表显
	 * @param cbmsSuppliersetting
	 * @return
	 */
	@Transactional(readOnly = false)
	public void updatecbmsSuppliersetting(CbmsSuppliersetting cbmsSuppliersetting) {
		 cbmsSuppliersettingDao.updatecbmsSuppliersetting(cbmsSuppliersetting);
	}
}