package com.heepay.manage.modules.settle.service;

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
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.TransType;
import com.heepay.enums.UserType;
import com.heepay.enums.billing.BillingCurrency;
import com.heepay.enums.billing.BillingSettleStatus;
import com.heepay.enums.billing.ClearingCheckStatus;
import com.heepay.enums.billing.SettleCyc;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.settle.dao.SettleMerchantRecordHisDao;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecordHis;
import com.heepay.manage.modules.settle.entity.SettleMerchantRecordHis;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.settle.service.util.ExcelService;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月10日下午2:10:03
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
public class SettleMerchantRecordHisService extends CrudService<SettleMerchantRecordHisDao, SettleMerchantRecordHis>{
	
	@Autowired
	private SettleMerchantRecordHisDao settleMerchantRecordHisDao;
	
	@Autowired
	private ExcelService excelService;
	
	@Autowired
	private ClearingMerchantRecordHisService clearingMerchantRecordHisService;
	
	public Model findSettleMerchantRecordHisPage(Page<SettleMerchantRecordHis> page,
			SettleMerchantRecordHis settleMerchantRecordHis, Model model) throws Exception {
		try {
			Page<SettleMerchantRecordHis> SettleMerchantRecordHis = findPage(page, settleMerchantRecordHis);
			List<SettleMerchantRecordHis> settleMRList = Lists.newArrayList();
			//循环翻译部分字段
			for (SettleMerchantRecordHis settleMR : SettleMerchantRecordHis.getList()) {
				//商户类型
				if(StringUtils.isNotBlank(settleMR.getMerchantType())){
					settleMR.setMerchantType(UserType.labelOf(settleMR.getMerchantType()));
				}
				//业务类型
				if(StringUtils.isNotBlank(settleMR.getTransType())){
					settleMR.setTransType(TransType.labelOf(settleMR.getTransType()));
				}
				//币种
				if(StringUtils.isNotBlank(settleMR.getCurrency())){
					settleMR.setCurrency(BillingCurrency.labelOf(settleMR.getCurrency()));
				}else{
					settleMR.setCurrency(BillingCurrency.CURRENCY.getContent());
				}
				//订单结算周期
				if(StringUtils.isNotBlank(settleMR.getSettleCyc())){
					if(StringUtils.equals(settleMR.getSettleCyc(), SettleCyc.SETTLECYC_T0.getValue()) || 
							StringUtils.equals(settleMR.getSettleCyc(), SettleCyc.SETTLECYC_T1.getValue())){
						settleMR.setSettleCyc(SettleCyc.labelOf(settleMR.getSettleCyc()));
					}else{
						settleMR.setSettleCyc(SettleCyc.SETTLECYC_T1.getContent());
					}
				}
				//手续费扣除方式
				if(StringUtils.isNotBlank(settleMR.getFeeWay())){
					settleMR.setFeeWay(ChargeDeductType.labelOf(settleMR.getFeeWay()));
				}
				//结算状态
				if(StringUtils.isNotBlank(settleMR.getSettleStatus())){
					settleMR.setSettleStatus(BillingSettleStatus.labelOf(settleMR.getSettleStatus()));
				}
				settleMRList.add(settleMR);
			}
			SettleMerchantRecordHis.setList(settleMRList);
			//前端页面  类型条件显示
			CommonUtil.enumsShow(model,Constants.Clear.SETTLE);
			model.addAttribute("page", SettleMerchantRecordHis);
			return model;
		} catch (Exception e) {
			logger.error("SettleMerchantRecordHisService findSettleMerchantRecordPage has a error:{查询商户结算历史记录List错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	//导出
	public void exportSettleMerchantRecordHisExcel(SettleMerchantRecordHis settleMerchantRecordHis, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		//数据库查询结果
		List<Map<String,Object>> clearingCR = settleMerchantRecordHisDao.findListToExcel(settleMerchantRecordHis);
		//导出Excel表格标题行
		String[] headerArray = {"商户编码","交易类型","币种","交易总笔数","交易总金额","会计日期","结算日期","结算单号","订单应结算总金额","订单结算周期","交易总手续费","结算状态"};
		//导出表格对应的字段名称
		String[] showField = {"merchantId","transType","currency","payNum","totalAmount","checkTime","settleTime","settleBath","settleAmount","settleCyc","totalFee","settleStatus"};
		try {
			excelService.exportExcel("商户结算历史记录", headerArray,clearingCR,showField,response, request,false);
		} catch (Exception e) {
			logger.error("SettleMerchantRecordHisService exportSettleMerchantRecordExcel has a error:{商户清算历史记录信息导出错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @throws Exception 
	 * @方法说明：查询商户侧结算记录详细信息
	 * @时间：2016年11月16日 下午3:37:26
	 * @创建人：wangdong
	 */
	public Model findSettleMerchantRecordHisDetailPage(Page<ClearingMerchantRecordHis> page,
			ClearingMerchantRecordHis clearingMerchantRecordHis, Model model) throws Exception {
		try {
			Page<ClearingMerchantRecordHis> pageClearingMerchantRecordHis = clearingMerchantRecordHisService.findPageDetail(page, clearingMerchantRecordHis);

			List<ClearingMerchantRecordHis> clearingMRList = Lists.newArrayList();
			//循环翻译部分字段
			for (ClearingMerchantRecordHis clearingMR : pageClearingMerchantRecordHis.getList()) {
				//商户类型
				if(StringUtils.isNotBlank(clearingMR.getMerchantType())){
					clearingMR.setMerchantType(UserType.labelOf(clearingMR.getMerchantType()));
				}
				//业务类型
				if(StringUtils.isNotBlank(clearingMR.getTransType())){
					clearingMR.setTransType(TransType.labelOf(clearingMR.getTransType()));
				}
				//币种
				if(StringUtils.isNotBlank(clearingMR.getCurrency())){
					clearingMR.setCurrency(BillingCurrency.labelOf(clearingMR.getCurrency()));
				}else{
					clearingMR.setCurrency(BillingCurrency.CURRENCY.getContent());
				}
				//手续费扣除方式 
				if(StringUtils.isNotBlank(clearingMR.getFeeWay())){
					clearingMR.setFeeWay(ChargeDeductType.labelOf(clearingMR.getFeeWay()));
				}
				//对账状态
				if(StringUtils.isNotBlank(clearingMR.getCheckStatus())){
					clearingMR.setCheckStatus(ClearingCheckStatus.labelOf(clearingMR.getCheckStatus()));
				}
				//结算状态
				if(StringUtils.isNotBlank(clearingMR.getSettleStatus())){
					clearingMR.setSettleStatus(BillingSettleStatus.labelOf(clearingMR.getSettleStatus()));
				}
				clearingMRList.add(clearingMR);
			}
			pageClearingMerchantRecordHis.setList(clearingMRList);
			model.addAttribute("page", pageClearingMerchantRecordHis);
			return model;
		} catch (Exception e) {
			logger.error("SettleMerchantRecordHisService findSettleMerchantRecordDetailPage has a error:{查询商户侧结算历史记录详细信息错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
}
