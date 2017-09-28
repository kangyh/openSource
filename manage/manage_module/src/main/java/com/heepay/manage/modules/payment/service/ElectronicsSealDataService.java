/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.ElectronicsSealDataDao;
import com.heepay.manage.modules.payment.entity.ElectronicsSealData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：签宝账户印章模板Service
 *
 * 创 建 者： @author zjx
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
public class ElectronicsSealDataService extends CrudService<ElectronicsSealDataDao, ElectronicsSealData> {

	public ElectronicsSealData get(String id) {
		return super.get(id);
	}
	
	public List<ElectronicsSealData> findList(ElectronicsSealData electronicsSealData) {
		return super.findList(electronicsSealData);
	}
	
	public Page<ElectronicsSealData> findPage(Page<ElectronicsSealData> page, ElectronicsSealData electronicsSealData) {
		return super.findPage(page, electronicsSealData);
	}
	
	@Transactional(readOnly = false)
	public void save(ElectronicsSealData electronicsSealData) {
		super.save(electronicsSealData,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(ElectronicsSealData electronicsSealData) {
		super.delete(electronicsSealData);
	}
	
}