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
import com.heepay.manage.modules.cbms.entity.CbmsOrderSum;
import com.heepay.manage.modules.cbms.dao.CbmsOrderSumDao;

/**
 *
 * 描    述：导入文件查询Service
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
public class CbmsOrderSumService extends CrudService<CbmsOrderSumDao, CbmsOrderSum> {
@Autowired
private CbmsOrderSumDao cbmsOrderSumDao;
	public CbmsOrderSum get(String id) {
		return super.get(id);
	}
	
	public List<CbmsOrderSum> findList(CbmsOrderSum cbmsOrderSum) {
		return super.findList(cbmsOrderSum);
	}
	/**
	 * 按照导入批次号查询 ordersum表中的唯一一条数据
	 * @param
	 * @return
	 */
	public Page<CbmsOrderSum> findPage(Page<CbmsOrderSum> page, CbmsOrderSum cbmsOrderSum) {
		//列表展示按照时间倒序展示
		page.setOrderBy("a.import_time desc");//按时间倒序排序
		return super.findPage(page, cbmsOrderSum);
	}
	/**
	 * 分页查询，要求按照时间倒序排序
	 * @param
	 * @return
	 */
	public Page<CbmsOrderSum> findPageimport(Page<CbmsOrderSum> page, CbmsOrderSum cbmsOrderSum) {
		page.setOrderBy("a.import_time desc");//按时间倒序排序
		cbmsOrderSum.setPage(page);
		page.setList(cbmsOrderSumDao.findPageimport(cbmsOrderSum));
		return page;
	}
	/**
	 * 按照导入批次号查询 ordersum表中的唯一一条数据
	 * @param
	 * @return
	 */
	public CbmsOrderSum ordersumselect(String ImportBatchNumber) {
		return cbmsOrderSumDao.ordersumselect(ImportBatchNumber);
	}
	/**
	 * 修改操作 不含添加
	 * @param
	 * @return
	 */
	@Transactional(readOnly = false)
	public void update(CbmsOrderSum cbmsOrderSum) {
		super.update(cbmsOrderSum,false);
	}
	@Transactional(readOnly = false)
	public void save(CbmsOrderSum cbmsOrderSum) {
		super.save(cbmsOrderSum,false);
	}
}