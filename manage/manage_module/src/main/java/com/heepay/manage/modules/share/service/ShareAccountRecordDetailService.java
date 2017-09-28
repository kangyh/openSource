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
import com.heepay.manage.modules.payment.dao.GatewayRecordDao;
import com.heepay.manage.modules.share.dao.ShareAccountRecordDetailDao;
import com.heepay.manage.modules.share.entity.ShareAccountRecordDetail;

/**
 *
 * 描    述：shareService
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
public class ShareAccountRecordDetailService extends CrudService<ShareAccountRecordDetailDao, ShareAccountRecordDetail> {

  @Autowired
  ShareAccountRecordDetailDao shareAccountRecordDetailDao;
  
	public ShareAccountRecordDetail get(String id) {
		return super.get(id);
	}
	
	public List<ShareAccountRecordDetail> findList(ShareAccountRecordDetail shareAccountRecordDetail) {
		return super.findList(shareAccountRecordDetail);
	}
	
	public Page<ShareAccountRecordDetail> findPage(Page<ShareAccountRecordDetail> page, ShareAccountRecordDetail shareAccountRecordDetail) {
		return super.findPage(page, shareAccountRecordDetail);
	}
	 public List<ShareAccountRecordDetail> getShareAccountRecordDetailListByShareId(String shareId) {
	    return shareAccountRecordDetailDao.getShareAccountRecordDetailListByShareId(shareId);
	  }
	@Transactional(readOnly = false)
	public void save(ShareAccountRecordDetail shareAccountRecordDetail) {
		super.save(shareAccountRecordDetail,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShareAccountRecordDetail shareAccountRecordDetail) {
		super.delete(shareAccountRecordDetail);
	}
	
}