/**
 * 
 */
package com.heepay.prom.modules.prom.service;

import com.heepay.common.util.StringUtils;
import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.service.CrudService;
import com.heepay.prom.modules.prom.dao.PromProfitOrderDao;
import com.heepay.prom.modules.prom.entity.PromProfitOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

/**
 * @author Administrator
 *
 */
@Service
@Transactional(readOnly = true)
public class PromProfitOrderService  extends CrudService<PromProfitOrderDao,PromProfitOrder>{
	@Autowired
	private PromProfitOrderDao promProfitOrderDao;
	public PromProfitOrder selectByPrimaryKey(Long id) {
		return promProfitOrderDao.selectByPrimaryKey(id);
	}
	public List<PromProfitOrder> findList(PromProfitOrder promProfitOrder) {
		return super.findList(promProfitOrder);
	}
	
	public Page<PromProfitOrder> findPage(Page<PromProfitOrder> page, PromProfitOrder promProfitOrder) {
		return super.findPage(page, promProfitOrder);
	}
	public Model findPageList(Page<PromProfitOrder> page, 
			PromProfitOrder promProfitOrder,
										  Model model,String pageNo) {
		if(StringUtils.isNotBlank(pageNo)){
			int pageno=Integer.parseInt(pageNo);
			page.setPageNo(pageno);
		}
		page.setPageSize(10);
		
		Page<PromProfitOrder> ppReport = findPage(page,promProfitOrder);
		ppReport.setPageSize(10);
		model.addAttribute("promProfitOrder", promProfitOrder);
		model.addAttribute("page", ppReport);
		return model;
	}
	@Transactional(readOnly = false)
	public void insert(PromProfitOrder promProfitOrder) {
		promProfitOrderDao.insert(promProfitOrder) ;
	}
	
	@Transactional(readOnly = false)
	public int deleteByPrimaryKey(Long saleId) {
		return promProfitOrderDao.deleteByPrimaryKey(saleId);
	}
	@Transactional(readOnly = false)
	public int deletePromProfitByBatch(String orderBatch ) {
		return promProfitOrderDao.deletePromProfitByBatch(orderBatch);
	}
	@Transactional(readOnly = false)
	public int deletePromProfitList(PromProfitOrder promProfitOrder){
		return promProfitOrderDao.deletePromProfitList(promProfitOrder);
	}
	@Transactional(readOnly = false)
	public void updateByPrimaryKey(PromProfitOrder promProfitOrder) {
		promProfitOrderDao.updateByPrimaryKey(promProfitOrder);
	}

}
