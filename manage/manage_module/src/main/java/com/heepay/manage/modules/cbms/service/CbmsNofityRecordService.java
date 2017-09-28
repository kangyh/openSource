/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.service;

import java.util.List;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.cbms.dao.CbmsNofityRecordDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.cbms.entity.CbmsNofityRecord;


/**
 *
 * 描    述：跨境通知表数据查询Service
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
public class CbmsNofityRecordService extends CrudService<CbmsNofityRecordDao, CbmsNofityRecord> {

	public CbmsNofityRecord get(String id) {
		return super.get(id);
	}
	
	public List<CbmsNofityRecord> findList(CbmsNofityRecord cbmsNofityRecord) {
		return super.findList(cbmsNofityRecord);
	}
	
	public Page<CbmsNofityRecord> findPage(Page<CbmsNofityRecord> page, CbmsNofityRecord cbmsNofityRecord) {
		page.setOrderBy("a.notify_time desc");
		return super.findPage(page, cbmsNofityRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(CbmsNofityRecord cbmsNofityRecord) {
		super.save(cbmsNofityRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(CbmsNofityRecord cbmsNofityRecord) {
		super.delete(cbmsNofityRecord);
	}
	
}