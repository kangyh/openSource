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
import com.heepay.manage.modules.cbms.entity.CbmsCustomCode;
import com.heepay.manage.modules.cbms.dao.CbmsCustomCodeDao;

/**
 *
 * 描    述：海关代码信息Service
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
public class CbmsCustomCodeService extends CrudService<CbmsCustomCodeDao, CbmsCustomCode> {

	@Autowired
    private CbmsCustomCodeDao cbmsCustomCodeDao;

	public CbmsCustomCode get(String id) {
		return super.get(id);
	}
	/**
	 *根据customCode获取CbmsCustomCode对象
	 * @param
	 * @return
	 * @author 牛俊鹏
	 */
	public CbmsCustomCode select(String customCode) {
		return cbmsCustomCodeDao.select(customCode);
	}
	public List<CbmsCustomCode> findList(CbmsCustomCode cbmsCustomCode) {
		return super.findList(cbmsCustomCode);
	}
	
	public Page<CbmsCustomCode> findPage(Page<CbmsCustomCode> page, CbmsCustomCode cbmsCustomCode) {
		return super.findPage(page, cbmsCustomCode);
	}

	@Transactional(readOnly = false)
	public void save(CbmsCustomCode cbmsCustomCode) {
		super.save(cbmsCustomCode,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(CbmsCustomCode cbmsCustomCode) {
		super.delete(cbmsCustomCode);
	}
	@Transactional(readOnly = false)
	public void update(CbmsCustomCode cbmsCustomCode) {
		super.update(cbmsCustomCode,false);
	}
	public CbmsCustomCode getByCustomCode (String customCode){
		return super.get(customCode);
	}

	/**
	 *根据customCode获取CbmsCustomCode对象
	 * @param customCode
	 * @return
	 * @author 郭正新
	 */
	public CbmsCustomCode getNameByCode(String customCode) {
        return cbmsCustomCodeDao.getNameByCode(customCode);
	}
	public List<CbmsCustomCode> findcodenotcustomsetting() {
		return cbmsCustomCodeDao.findcodenotcustomsetting();
	}
	public List<CbmsCustomCode> customCodeList() {
		return cbmsCustomCodeDao.customCodeList();
	}
}