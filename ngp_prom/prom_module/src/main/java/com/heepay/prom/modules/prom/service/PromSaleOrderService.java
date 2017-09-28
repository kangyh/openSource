/**
 * 
 */
package com.heepay.prom.modules.prom.service;

import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtils;
import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.service.CrudService;
import com.heepay.prom.modules.prom.dao.PromProfitOrderDao;
import com.heepay.prom.modules.prom.dao.PromSaleOrderDao;
import com.heepay.prom.modules.prom.entity.PromProfitOrder;
import com.heepay.prom.modules.prom.entity.PromSaleOrder;
import com.heepay.prom.modules.prom.entity.PromSaleOrderBuild;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
@Service
@Transactional(readOnly = true)
public class PromSaleOrderService   extends CrudService<PromSaleOrderDao,PromSaleOrder>{

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private PromSaleOrderDao promSaleOrderDao;
	@Autowired
	private PromProfitOrderDao promProfitOrderDao;
	
	private static PropertiesLoader loader = new PropertiesLoader("prom.properties");
	
	public PromSaleOrder selectByPrimaryKey(Long id) {
		return promSaleOrderDao.selectByPrimaryKey(id);
	}
	public List<PromSaleOrder> findList(PromSaleOrder promSaleOrder) {
		return super.findList(promSaleOrder);
	}
	
	public Page<PromSaleOrder> findPage(Page<PromSaleOrder> page, PromSaleOrder promSaleOrder) {
		return super.findPage(page, promSaleOrder);
	}
	public Model findPageList(Page<PromSaleOrder> page, 
			PromSaleOrder promSaleOrder,
										  Model model,String pageNo) {
		if(StringUtils.isNotBlank(pageNo)){
			int pageno=Integer.parseInt(pageNo);
			page.setPageNo(pageno);
		}
		page.setPageSize(10);
		
		Page<PromSaleOrder> ppReport = findPage(page,promSaleOrder);
		ppReport.setPageSize(10);
		model.addAttribute("promSaleOrder", promSaleOrder);
		model.addAttribute("page", ppReport);
		return model;
	}
	@Transactional(readOnly = false)
	public void insert(PromSaleOrder promSaleOrder) {
		promSaleOrderDao.insert(promSaleOrder) ;
	}
	
	@Transactional(readOnly = false)
	public int deleteByPrimaryKey(Long saleId) {
		return promSaleOrderDao.deleteByPrimaryKey(saleId);
	}
	@Transactional(readOnly = false)
	public int deletePromSaleByBatch(String orderBatch ) {
		return promSaleOrderDao.deletePromSaleByBatch(orderBatch);
	}
	@Transactional(readOnly = false)
	public int deletePromSaleList(PromSaleOrder promSaleOrder){
		return promSaleOrderDao.deletePromSaleList(promSaleOrder);
	}
	@Transactional(readOnly = false)
	public void updateByPrimaryKey(PromSaleOrder promSaleOrder) {
		promSaleOrderDao.updateByPrimaryKey(promSaleOrder);
	}
	@Transactional(readOnly = false)
	public String  generateSaleAndProfitOrder(PromSaleOrderBuild promSaleOrderBuild){
		List<PromSaleOrderBuild> buildOrders = promSaleOrderDao.selectUnProfitOrder(promSaleOrderBuild);
		if( buildOrders==null || buildOrders.size()==0 ) {
			logger.info("生成销售单分润单失败，找不到订单");
			return "生成销售单分润单失败，找不到订单";
		}
		List<PromSaleOrder> saleOrderList = new ArrayList<PromSaleOrder>();
		List<PromProfitOrder> profitOrderList = new ArrayList<PromProfitOrder>();
		PromSaleOrder saleOrder = null; 
		int saleOrderCount = 0;
		PromProfitOrder profitOrder = null ; 
		int profitOrderCount = 0;
		PromProfitOrder refereeOrder = null ;
//		PromProfitOrder higherOrder = null ;
//		PromProfitOrder highestOrder = null ;
		for(PromSaleOrderBuild build:buildOrders ){
			saleOrder = new PromSaleOrder();
			BeanUtils.copyProperties(build, saleOrder);
			saleOrder.setProfitMoney(build.getGearProfit());
			saleOrder.setOperator(promSaleOrderBuild.getOperator());
			saleOrder.setProfitTime(promSaleOrderBuild.getProfitTime());
			saleOrder.setRemark(promSaleOrderBuild.getRemark());
			saleOrderList.add(saleOrder);
			
			if( build.getHuiyuanProfit().compareTo(new BigDecimal(0)) ==1 ) {//如果存在汇元分润
				profitOrder =  new PromProfitOrder();
				BeanUtils.copyProperties(build,  profitOrder);
				profitOrder.setMerchantId(loader.getProperty("merchantId"));
				profitOrder.setMerchantName(loader.getProperty("merchantName"));
				profitOrder.setProfitMoney(build.getHuiyuanProfit());
				profitOrder.setOperator(promSaleOrderBuild.getOperator());
				profitOrder.setProfitTime(promSaleOrderBuild.getProfitTime());
				profitOrder.setRemark(promSaleOrderBuild.getRemark());
				profitOrderList.add(profitOrder);
			}
			if( build.getRefereeProfit().compareTo(new BigDecimal(0)) ==1 ) {//如果存在推荐者(默认汇元)分润
				refereeOrder =  new PromProfitOrder();
				BeanUtils.copyProperties(build,  refereeOrder);
				refereeOrder.setMerchantId(loader.getProperty("merchantId"));
				refereeOrder.setMerchantName(loader.getProperty("merchantName"));
				refereeOrder.setProfitMoney(build.getRefereeProfit());
				refereeOrder.setOperator(promSaleOrderBuild.getOperator());
				refereeOrder.setProfitTime(promSaleOrderBuild.getProfitTime());
				refereeOrder.setRemark(promSaleOrderBuild.getRemark());
				profitOrderList.add(refereeOrder);
			}
			/*if( build.getHigherMasterProfit().compareTo(new BigDecimal(0)) ==1 ) {//如果生成销售单成功 且存在上级分润
				higherOrder =  new PromProfitOrder();
				BeanUtils.copyProperties(build,  higherOrder);
				higherOrder.setMerchantId( build.getHigherMerId() );
				higherOrder.setMerchantName(build.getHigherMerName());
				higherOrder.setProfitMoney(build.getHigherMasterProfit());
				higherOrder.setOperator(promSaleOrderBuild.getOperator());
				higherOrder.setProfitTime(promSaleOrderBuild.getProfitTime());
				higherOrder.setRemark(promSaleOrderBuild.getRemark());
				profitOrderList.add(higherOrder);
			}
			if( build.getHighestMasterProfit().compareTo(new BigDecimal(0)) ==1 ) {//如果生成销售单成功 且存在上上级分润
				highestOrder =  new PromProfitOrder();
				BeanUtils.copyProperties(build,  highestOrder);
				highestOrder.setMerchantId( build.getHighestMerId() );
				highestOrder.setMerchantName( build.getHighestMerName() );
				highestOrder.setProfitMoney( build.getHighestMasterProfit() );
				highestOrder.setOperator(promSaleOrderBuild.getOperator());
				highestOrder.setProfitTime(promSaleOrderBuild.getProfitTime());
				highestOrder.setRemark(promSaleOrderBuild.getRemark());
				profitOrderList.add(highestOrder);
			}*/
		}
		saleOrderCount = promSaleOrderDao.insertSaleOrderList(saleOrderList);
		if( saleOrderCount>0  ) {//如果生成销售单成功 则插入分润单
			profitOrderCount = promProfitOrderDao.insertProfitOrderList(profitOrderList);
			for(PromSaleOrderBuild build:buildOrders ){
				build.setIsProfit("Y");
				build.setProfitTime(promSaleOrderBuild.getProfitTime());
			}
			int upcount = promSaleOrderDao.updatePromOrder(buildOrders);
			logger.info("生成成功！销售单"+saleOrderCount+"条，分润单"+profitOrderCount+"条"+"，更新订单数量："+upcount);
			return "生成成功！销售单"+saleOrderCount+"条，分润单"+profitOrderCount+"条";
		}
		logger.info("生成销售单分润单失败,订单批次号："+promSaleOrderBuild.getOrderBatch());
		return "生成销售单分润单失败";
	}
}
