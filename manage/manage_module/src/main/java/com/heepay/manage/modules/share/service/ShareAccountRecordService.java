/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.share.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.share.dao.ShareAccountRecordDao;
import com.heepay.manage.modules.share.entity.ShareAccountRecord;

/**
 *
 * 描    述：分润查看Service
 *
 * 创 建 者： @author zc
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
public class ShareAccountRecordService extends CrudService<ShareAccountRecordDao, ShareAccountRecord> {

	@Autowired
	ShareAccountRecordDao shareAccountRecordDao;
	
	public ShareAccountRecord get(String id) {
		return super.get(id);
	}
	
	public List<ShareAccountRecord> findList(ShareAccountRecord shareAccountRecord) {
		return super.findList(shareAccountRecord);
	}
	
	public Page<ShareAccountRecord> findPage(Page<ShareAccountRecord> page, ShareAccountRecord shareAccountRecord) {
		return super.findPage(page, shareAccountRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(ShareAccountRecord shareAccountRecord) {
		super.save(shareAccountRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShareAccountRecord shareAccountRecord) {
		super.delete(shareAccountRecord);
	}

	public ShareAccountRecord getShareId() {
		return shareAccountRecordDao.getShareId();
	}
	
}