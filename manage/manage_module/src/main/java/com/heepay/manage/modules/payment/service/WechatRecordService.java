/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.payment.dao.WechatRecordDao;
import com.heepay.manage.modules.payment.entity.WechatRecord;

/**
 *
 * 描    述：微信支付订单Service
 *
 * 创 建 者： @author 张晨
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
public class WechatRecordService extends CrudService<WechatRecordDao, WechatRecord> {

	@Autowired
	WechatRecordDao wechatRecordDao;
	
	public WechatRecord get(String id) {
		return super.get(id);
	}
	
	public List<WechatRecord> findList(WechatRecord wechatRecord) {
		return super.findList(wechatRecord);
	}
	
	public Page<WechatRecord> findPage(Page<WechatRecord> page, WechatRecord wechatRecord) {
		return super.findPage(page, wechatRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(WechatRecord wechatRecord) {
		super.save(wechatRecord,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(WechatRecord wechatRecord) {
		super.delete(wechatRecord);
	}

	public WechatRecord getWechatId() {
		return wechatRecordDao.getWechatId();
	}
	 public int updatewechatRecordPAYING(String id) {
	    return wechatRecordDao.updatewechatRecordPAYING(id);
	  }
	
}