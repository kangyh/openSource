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
import com.heepay.enums.billing.BillingYCheckStatus;
import com.heepay.enums.billing.ClearingCheckStatus;
import com.heepay.enums.billing.SettleDifferIsProfit;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.settle.dao.ClearingChannelRecordHisDao;
import com.heepay.manage.modules.settle.dao.ClearingMerchantRecordHisDao;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecordHis;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecordHis;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.settle.service.util.ExcelService;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月10日下午2:08:56
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
public class ClearingMerchantRecordHisService extends CrudService<ClearingMerchantRecordHisDao, ClearingMerchantRecordHis>{
	
	 @Autowired
     private ClearingMerchantRecordHisDao clearingMerchantRecordHisDao;
	 
	 @Autowired
     private ExcelService excelService;
	 
	 /**
		 * 
		 * @throws Exception 
		 * @方法说明：用于商户结算单号查询明细
		 * @时间：2016年10月19日 下午3:11:45
		 * @创建人：wangdong
		 */
	public Page<ClearingMerchantRecordHis> findPageDetail(Page<ClearingMerchantRecordHis> page,
			ClearingMerchantRecordHis clearingMerchantRecordHis) throws Exception {
		try {
			clearingMerchantRecordHis.setPage(page);
			page.setList(clearingMerchantRecordHisDao.findPageDetail(clearingMerchantRecordHis));
			return page;
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordHisService findPageDetail has a error:{用于商户结算单号查询明细出错 FIND_ERROR} ", e);
			throw new Exception(e);
		}
	}
		
 	/**
	 * 
	 * @throws Exception 
	 * @方法说明：查询商户侧清算记录信息List
	 * @时间：2016年11月16日 下午2:31:35
	 * @创建人：wangdong
	 */
	public Model findClearingMerchantRecordHisPage(Page<ClearingMerchantRecordHis> page,
			ClearingMerchantRecordHis clearingMerchantRecordHis, Model model) throws Exception {
		try {
			Page<ClearingMerchantRecordHis> pageClearingMerchantRecordHis = findPage(page, clearingMerchantRecordHis);

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
				/*//订单结算周期
				if(StringUtils.isNotBlank(clearingMR.getSettleCyc())){
					if(StringUtils.equals(clearingMR.getSettleCyc(), "0")){
						clearingMR.setSettleCyc("实时");
					}else if(StringUtils.equals(clearingMR.getSettleCyc(), "1")){
						clearingMR.setSettleCyc("周期");
					}
				}*/
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
				//是否分润
				if(StringUtils.isNotBlank(clearingMR.getIsProfit())){
					clearingMR.setIsProfit(SettleDifferIsProfit.labelOf(clearingMR.getIsProfit()));
				}
				//对账状态
				if(StringUtils.isNotBlank(clearingMR.getCheckStatus())){
					clearingMR.setCheckStatus(ClearingCheckStatus.labelOf(clearingMR.getCheckStatus()));
				}
				//已对账状态
				if(StringUtils.isNotBlank(clearingMR.getCheckFlg())){
					if(StringUtils.equals(clearingMR.getCheckFlg(), BillingYCheckStatus.BCFQSTS.getValue())){
						clearingMR.setCheckFlg("平账");
					}else{
						//除了平账都是差异账（产品需求）
						clearingMR.setCheckFlg("差异账");
					}
				}
				//结算状态
				if(StringUtils.isNotBlank(clearingMR.getSettleStatus())){
					clearingMR.setSettleStatus(BillingSettleStatus.labelOf(clearingMR.getSettleStatus()));
				}
				clearingMRList.add(clearingMR);
			}
			pageClearingMerchantRecordHis.setList(clearingMRList);
			
			//前端页面  类型条件显示
			CommonUtil.enumsShow(model,Constants.Clear.SETTLE);
			
			model.addAttribute("page", pageClearingMerchantRecordHis);
			model.addAttribute("clearingMerchantRecordHis", clearingMerchantRecordHis);
			
			return model;
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordHisService findClearingMerchantRecordHisPage has a error:{查询商户侧清算历史记录信息List出错 FIND_ERROR} ", e);
			throw new Exception(e);
		}
	}
	
	//导出
	public void exportClearingMerchantRecordHisExcel(ClearingMerchantRecordHis clearingMerchantRecordHis,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		//数据库查询结果
		List<Map<String,Object>> clearingCR = clearingMerchantRecordHisDao.findListToExcel(clearingMerchantRecordHis);
		//导出Excel表格标题行
		String[] headerArray = {"商户编码","交易类型","币种","交易订单号","实际支付金额","清算日期","清算流水号","订单应结算日期","订单应结算金额","结算单号","手续费扣除方式","订单手续费","是否分润","对账状态","状态描述","结算状态"};
		//导出表格对应的字段名称
		String[] showField = {"merchantId","transType","currency","transNo","requestAmount","settleTime","settleNo","settleTimePlan","settleAmountPlan","settleBath","feeWay","fee","isProfit","checkStatus","checkFlg","settleStatus"};
		try {
			excelService.exportExcel("商户清算历史记录", headerArray,clearingCR,showField,response, request,false);
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordHisService exportClearingMerchantRecordExcel has a error:{商户清算历史记录信息导出出错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
}
