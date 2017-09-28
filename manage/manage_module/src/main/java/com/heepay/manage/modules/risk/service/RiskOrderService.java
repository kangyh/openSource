package com.heepay.manage.modules.risk.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.TransType;
import com.heepay.enums.risk.RiskOrderDealType;
import com.heepay.enums.risk.RiskOrderStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.risk.dao.RiskOrderDao;
import com.heepay.manage.modules.risk.entity.RiskOrder;
import com.heepay.manage.modules.risk.entity.RiskRule;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.settle.service.util.ExcelService;

/***
 * 
* 
* 描    述：风险订单Service
*
* 创 建 者：wangl
* 创建时间：  Nov 18, 20167:09:43 PM
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
public class RiskOrderService extends CrudService<RiskOrderDao, RiskOrder> {
	
	@Autowired
	private RiskOrderDao riskOrderDao;
	
	@Autowired
	private ExcelService excelService;
	
	@Autowired
	private RiskRuleService riskRuleService;
	
	/**
	 * @方法说明：根据id获取风险订单信息
	 * @author wangdong
	 * @时间： 2016年11月18日17:27:49
	 */
	public RiskOrder get(Long id) {
		return riskOrderDao.get(id);
	}
	
	/**
	 * @方法说明：获取风险订单信息List
	 * @author wangdong
	 * @时间：2016年11月18日17:27:53
	 */
	public List<RiskOrder> findList(RiskOrder riskOrder) {
		return super.findList(riskOrder);
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：查询风险订单page
	 * @时间：2016年11月22日 下午2:13:32
	 * @创建人：wangdong
	 */
	public Model findRiskOrderPage(Page<RiskOrder> page, RiskOrder riskOrder, Model model) throws Exception {
		try {
			Page<RiskOrder> pageRiskOrder = findPage(page, riskOrder);
			List<RiskOrder> riKOList = Lists.newArrayList();
			//翻译产品限额中的状态
			for(RiskOrder riO : pageRiskOrder.getList()){
				//交易类型
				if(StringUtils.isNotBlank(riO.getTransType())){
					riO.setTransType(TransType.labelOf(riO.getTransType()));
				}
				//风险订单处理方式
				if(StringUtils.isNotBlank(riO.getOrderDealwith())){
					riO.setOrderDealwith(RiskOrderDealType.labelOf(riO.getOrderDealwith()));
				}
				//风险订单状态
				if(StringUtils.isNotBlank(riO.getOrderStatus())){
					riO.setOrderStatus(RiskOrderStatus.labelOf(riO.getOrderStatus()));
				}
				riKOList.add(riO);
			}
			pageRiskOrder.setList(riKOList);
			
			//显示页面查询条件下拉选框
			CommonUtil.enumsShow(model,Constants.Clear.RISK);
			
			//商户公司名称查询条件
			List<RiskOrder> mNList = riskOrderDao.findMerchantName();
			model.addAttribute("mNList", mNList);
			
			//风控规则
			List<RiskRule> riskRuleList = riskRuleService.findDistructRule();
			model.addAttribute("riskRuleList", riskRuleList);
			
			model.addAttribute("page", pageRiskOrder);
			model.addAttribute("riskOrder",riskOrder);
			return model;
		} catch (Exception e) {
			logger.error("RiskOrderService findRiskOrderPage has a error:{查询风险订单page出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：风险订单信息导出
	 * @时间：2016年11月22日 下午2:42:49
	 * @创建人：wangdong
	 */
	public void exportRiskOrderExcel(RiskOrder riskOrder, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//数据库查询结果
		List<Map<String,Object>> clearingCR = riskOrderDao.findListExcel(riskOrder);
		//导出Excel表格标题行
		String[] headerArray = {"风控单号","商户编码","商户公司名称","产品名称","交易类型","订单创建时间","商户订单号","交易订单号","支付单号","交易金额(元)","处理方式","状态"};
		//导出表格对应的字段名称
		String[] showField = {"riskNo","merchantId","merchantName","productName","transType","createTime","merchantOrderNo","transNo","paymentId","transAmount","orderDealwith","orderStatus"};
		try {
			excelService.exportExcel("风险订单记录", headerArray,clearingCR,showField,response,request,false);
		} catch (Exception e) {
			logger.error("ClearingChannelRecordService exportClearingChannelRecordExcel has a error:{通道清算记录信息导出出错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
}
