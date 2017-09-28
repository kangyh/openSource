package com.heepay.manage.modules.settle.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.modules.merchant.dao.MerchantDao;
import com.heepay.manage.modules.settle.entity.rabbit.SettleMerchantRecordRabbit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.heepay.manage.modules.settle.dao.SettleMerchantRecordDao;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import com.heepay.manage.modules.settle.entity.SettleMerchantRecord;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.settle.service.util.ExcelService;

/**
*
* 描    述：用户结算Service
*
* 创 建 者： @author wangdong
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
public class SettleMerchantRecordService extends CrudService<SettleMerchantRecordDao, SettleMerchantRecord>{
	
	private static final Logger logger=LogManager.getLogger();

	@Autowired
	private SettleMerchantRecordDao settleMerchantRecordDao;
	
	@Autowired
	private ClearingMerchantRecordService clearingMerchantRecordService;

	@Autowired
	private MerchantDao merchantDao;

	@Autowired
	private ExcelService excelService;
	
	/**
	 * @方法说明：根据id用户结算记录
	 * @author wangdong
	 * @param id
	 * @时间：2016年9月14日11:15:13
	 */
	public SettleMerchantRecord get(Long id) {
		return settleMerchantRecordDao.get(id);
	}
	
	/**
	 * @方法说明：获取用户结算记录List
	 * @author wangdong
	 * @param settleMerchantRecord
	 * @时间： 2016年9月14日11:15:17
	 */
	public List<SettleMerchantRecord> findList(SettleMerchantRecord settleMerchantRecord) {
		return super.findList(settleMerchantRecord);
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：查询商户侧结算记录List
	 * @时间：2016年11月16日 下午3:30:34
	 * @创建人：wangdong
	 */
	public Model findSettleMerchantRecordPage(Page<SettleMerchantRecord> page,SettleMerchantRecord settleMerchantRecord, Model model) throws Exception {
		try {
			List<Integer> list = merchantDao.getUserId(settleMerchantRecord.getMerchantFlag());
			settleMerchantRecord.setMerchantFlagList(list);

			SettleMerchantRecord settleMerchantSumNum = settleMerchantRecordDao.sumNum(settleMerchantRecord);
			model.addAttribute("settleMerchantSumNum", settleMerchantSumNum);

			Page<SettleMerchantRecord> pageSettleMerchantRecord = findPage(page, settleMerchantRecord);
			List<SettleMerchantRecord> settleMRList = Lists.newArrayList();



			//循环翻译部分字段
			for (SettleMerchantRecord settleMR : pageSettleMerchantRecord.getList()) {

				// 判断状态是否是结算中
				if(settleMR.getSettleStatus().equals(BillingSettleStatus.SETTLESTATUSD.getValue()) || settleMR.getSettleStatus().equals(BillingSettleStatus.SETTLE_STATUS_C.getValue())){
					settleMR.setFlag("Y");
				}

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
			pageSettleMerchantRecord.setList(settleMRList);

			//前端页面  类型条件显示
			CommonUtil.enumsShow(model,Constants.Clear.SETTLE);
			model.addAttribute("page", pageSettleMerchantRecord);
			return model;
		} catch (Exception e) {
			logger.error("SettleMerchantRecordService findSettleMerchantRecordPage has a error:{查询商户结算记录List错误 FIND_ERROR}", e);
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
	public Model findSettleMerchantRecordDetailPage(Page<ClearingMerchantRecord> page,ClearingMerchantRecord clearingMerchantRecord, Model model) throws Exception {
		try {
			Page<ClearingMerchantRecord> pageClearingMerchantRecord = clearingMerchantRecordService.findPageDetail(page, clearingMerchantRecord);

			List<ClearingMerchantRecord> clearingMRList = Lists.newArrayList();
			//循环翻译部分字段
			for (ClearingMerchantRecord clearingMR : pageClearingMerchantRecord.getList()) {
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
			pageClearingMerchantRecord.setList(clearingMRList);
			model.addAttribute("page", pageClearingMerchantRecord);
			return model;
		} catch (Exception e) {
			logger.error("SettleMerchantRecordService findSettleMerchantRecordDetailPage has a error:{查询商户侧结算记录详细信息错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：商户侧结算记录信息导出
	 * @时间：2016年11月16日 下午3:43:56
	 * @创建人：wangdong
	 */
	public void exportSettleMerchantRecordExcel(SettleMerchantRecord settleMerchantRecord, HttpServletResponse response,HttpServletRequest request) throws Exception {

		List<Integer> list = merchantDao.getUserId(settleMerchantRecord.getMerchantFlag());
		settleMerchantRecord.setMerchantFlagList(list);

		//数据库查询结果
		List<Map<String,Object>> clearingCR = settleMerchantRecordDao.findListToExcel(settleMerchantRecord);
		//导出Excel表格标题行
		String[] headerArray = {"商户编码","交易类型","币种","交易总笔数","交易总金额","会计日期","结算日期","结算单号","订单应结算总金额","订单结算周期","交易总手续费","结算状态"};
		//导出表格对应的字段名称
		String[] showField = {"merchantId","transType","currency","payNum","totalAmount","checkTime","settleTime","settleBath","settleAmount","settleCyc","totalFee","settleStatus"};
		try {
			excelService.exportExcel("商户结算记录", headerArray,clearingCR,showField,response, request,false);
		} catch (Exception e) {
			logger.error("SettleMerchantRecordService exportSettleMerchantRecordExcel has a error:{商户清算记录信息导出错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}


	/**
	 * @方法说明：更新方法
	 * @时间： 2017/7/10 10:22
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int updateEntity(SettleMerchantRecord settleMerchantRecord) {

		return settleMerchantRecordDao.updateEntity(settleMerchantRecord);
	}

	/**
	 * @方法说明：查询
	 * @时间： 2017/7/10 10:41
	 * @创建人：wangl
	 */
	public SettleMerchantRecordRabbit getEntityBySettleBath(String settleBath) {
		return settleMerchantRecordDao.getEntityBySettleBath(settleBath);
	}
}
