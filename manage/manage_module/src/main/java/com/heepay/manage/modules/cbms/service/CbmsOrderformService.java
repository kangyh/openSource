/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.service;

import java.util.ArrayList;
import java.util.List;

import com.heepay.manage.modules.cbms.entity.CannotImOrderQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.cbms.entity.CbmsOrderform;
import com.heepay.manage.modules.cbms.dao.CbmsOrderformDao;

/**
 *
 * 描    述：同申报批次号详情查看和新增Service
 *
 * 创 建 者： @author guozx
 * 创建时间： 2017年1月2日 17:00:25
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
public class CbmsOrderformService extends CrudService<CbmsOrderformDao, CbmsOrderform> {

	@Autowired
	private CbmsOrderformDao cbmsOrderformDao;

	public CbmsOrderform get(String id) {
		return super.get(id);
	}

	public List<CbmsOrderform> findList(CbmsOrderform cbmsOrderform) {
		return super.findList(cbmsOrderform);
	}
	
	public Page<CbmsOrderform> findPage(Page<CbmsOrderform> page, CbmsOrderform cbmsOrderform) {
		page.setOrderBy("a.created_time desc ");
		return super.findPage(page, cbmsOrderform);
	}
	
	@Transactional(readOnly = false)
	public void save(CbmsOrderform cbmsOrderform) {
		super.save(cbmsOrderform,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(CbmsOrderform cbmsOrderform) {
		super.delete(cbmsOrderform);
	}

	@Transactional(readOnly = false)
	public void update(CbmsOrderform cbmsOrderform) {
		super.update(cbmsOrderform,false);
	}

    /**
     *查询同一申报批次号的CbmsOrderform对象
     * @param sentCustomsNumber
     * @return
     */
	public List<CbmsOrderform> select(String sentCustomsNumber) {
		return cbmsOrderformDao.select(sentCustomsNumber);
	}

	/**
	 *查询同导入批次号的CbmsOrderform对象
	 * @param importBatchNumber
	 * @return
	 */
	public List<CbmsOrderform> selectimport(String importBatchNumber) {
		return cbmsOrderformDao.selectimport(importBatchNumber);
	}
	/**
	 *查询重复订单
	 * @param cannotImOrderQuery
	 * @return
	 */
	public List<String> queryRepeatOrder(CannotImOrderQuery cannotImOrderQuery) {
		return cbmsOrderformDao.queryRepeatOrder(cannotImOrderQuery);
	}

    /**
     *查询同导入批次号的CbmsOrderform对象
     * @param merchantNo
     * @return
     */
	public CbmsOrderform findOrderform(String merchantNo) {
		return cbmsOrderformDao.findOrderform(merchantNo);
	}
	/**
	 *查询同list中的所有主见id的集合
	 * @param strList
	 * @return
	 */
	public List<CbmsOrderform> selectIdList(List<CbmsOrderform> strList) {
		return cbmsOrderformDao.selectIdList(strList);
	}
	/**
	 *批量更新订单表的申报批次号并且将notea赋值为1
	 * @param arrayList
	 * @return
	 */
	@Transactional(readOnly = false)
	public void updateList(List<CbmsOrderform> arrayList ){
		cbmsOrderformDao.updateList(arrayList);
	}

	public int notAuditedNum (String importBatchNumber ){
		return cbmsOrderformDao.notAuditedNum(importBatchNumber);
	}

	@Transactional(readOnly = false)
	public void updateAuditFailList(List<CbmsOrderform> list) {
		 cbmsOrderformDao.updateAuditFailList(list);
	}
}