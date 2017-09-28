/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.service;

import java.util.List;

import com.heepay.manage.modules.cbms.entity.CusdeclarationResult;
import com.heepay.manage.modules.cbms.entity.CustdeclarationQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.cbms.entity.CbmsCustomorderSum;
import com.heepay.manage.modules.cbms.dao.CbmsCustomorderSumDao;

/**
 *
 * 描    述：通关申报订单查询Service
 *
 * 创 建 者： @author guozx
 * 创建时间： 2016/12/30 10:30 2016年12月30日 10:30:25
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
public class CbmsCustomorderSumService extends CrudService<CbmsCustomorderSumDao, CbmsCustomorderSum> {

	@Autowired
	private CbmsCustomorderSumDao cbmsCustomorderSumDao;

	public CbmsCustomorderSum get(String id) {
		return super.get(id);
	}
	
	public List<CbmsCustomorderSum> findList(CbmsCustomorderSum cbmsCustomorderSum) {
		return super.findList(cbmsCustomorderSum);
	}
	
	public Page<CbmsCustomorderSum> findPage(Page<CbmsCustomorderSum> page, CbmsCustomorderSum cbmsCustomorderSum) {
		return super.findPage(page, cbmsCustomorderSum);
	}
	
	@Transactional(readOnly = false)
	public void save(CbmsCustomorderSum cbmsCustomorderSum) {
		super.save(cbmsCustomorderSum,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(CbmsCustomorderSum cbmsCustomorderSum) {
		super.delete(cbmsCustomorderSum);
	}

	@Transactional(readOnly = false)
	public CusdeclarationResult getCusdeclarationResult(CustdeclarationQuery query){
		return cbmsCustomorderSumDao.getCusdeclarationResult(query);
	}

	/**
	 *根据merchantNo获取CbmsCustomorderSum
	 * @param merchantNo
	 * @return
	 */
	public List<CbmsCustomorderSum> selectList(String merchantNo) {
		return cbmsCustomorderSumDao.selectList(merchantNo);
	}
}