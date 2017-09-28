package com.heepay.manage.modules.settle.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.modules.merchant.dao.MerchantDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.PayType;
import com.heepay.enums.TransType;
import com.heepay.enums.UserType;
import com.heepay.enums.billing.BillingCurrency;
import com.heepay.enums.billing.BillingSettleStatus;
import com.heepay.enums.billing.BillingYCheckStatus;
import com.heepay.enums.billing.ClearingCheckStatus;
import com.heepay.enums.billing.SettleDifferIsProfit;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.settle.dao.ClearingChannelRecordDao;
import com.heepay.manage.modules.settle.dao.ClearingMerchantRecordDao;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecord;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.settle.service.util.ExcelService;

/**
*
* 描    述：用户清算记录Service
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
public class ClearingMerchantRecordService extends CrudService<ClearingMerchantRecordDao, ClearingMerchantRecord>{

	private static final Logger logger=LogManager.getLogger();
	
	@Autowired
	private ClearingMerchantRecordDao clearingMerchantRecordDao;
	
	@Autowired
	private ClearingChannelRecordDao clearingChannelRecordDao;
	
	@Autowired
	private MerchantDao merchantDao;

	@Autowired
	private ExcelService excelService;
	
	/**
	 * @方法说明：根据id用户清算记录
	 * @author wangdong
	 * @param id
	 * @时间：2016年9月14日11:07:43
	 */
	public ClearingMerchantRecord get(Long id) {
		return clearingMerchantRecordDao.get(id);
	}
	
	/**
	 * @方法说明：获取用户清算记录List
	 * @author wangdong
	 * @param clearingMerchantRecord
	 * @时间： 2016年9月14日11:07:48
	 */
	public List<ClearingMerchantRecord> findList(ClearingMerchantRecord clearingMerchantRecord) {
		return super.findList(clearingMerchantRecord);
	}
	
	/**
	 * 
	 * @throws Exception 
	 * @方法说明：用于商户结算单号查询明细
	 * @时间：2016年10月19日 下午3:11:45
	 * @创建人：wangdong
	 */
	public Page<ClearingMerchantRecord> findPageDetail(Page<ClearingMerchantRecord> page,
			ClearingMerchantRecord clearingMerchantRecord) throws Exception {
		try {
			clearingMerchantRecord.setPage(page);
			page.setList(clearingMerchantRecordDao.findPageDetail(clearingMerchantRecord));
			return page;
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordService findPageDetail has a error:{用于商户结算单号查询明细出错 FIND_ERROR} ", e);
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
	public Model findClearingMerchantRecordPage(Page<ClearingMerchantRecord> page,ClearingMerchantRecord clearingMerchantRecord, Model model) throws Exception {

		try {
			List<Integer> list = merchantDao.getUserId(clearingMerchantRecord.getMerchantFlag());
			clearingMerchantRecord.setMerchantFlagList(list);

			ClearingMerchantRecord clearingMerSumNum = clearingMerchantRecordDao.sumNum(clearingMerchantRecord);
			model.addAttribute("clearingMerSumNum", clearingMerSumNum);

			Page<ClearingMerchantRecord> pageClearingMerchantRecord = findPage(page, clearingMerchantRecord);

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
			pageClearingMerchantRecord.setList(clearingMRList);

			//前端页面  类型条件显示
			CommonUtil.enumsShow(model,Constants.Clear.SETTLE);
			
			model.addAttribute("page", pageClearingMerchantRecord);
			return model;
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordService findClearingMerchantRecordPage has a error:{查询商户侧清算记录信息List出错 FIND_ERROR} ", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：商户侧清算记录导出
	 * @时间：2016年11月16日 下午2:53:23
	 * @创建人：wangdong
	 */
	public void exportClearingMerchantRecordExcel(ClearingMerchantRecord clearingMerchantRecord,HttpServletResponse response, HttpServletRequest request) throws Exception {

		List<Integer> list = merchantDao.getUserId(clearingMerchantRecord.getMerchantFlag());
		clearingMerchantRecord.setMerchantFlagList(list);

		//数据库查询结果
		List<Map<String,Object>> clearingCR = clearingMerchantRecordDao.findListToExcel(clearingMerchantRecord);
		//导出Excel表格标题行
		String[] headerArray = {"商户编码","交易类型","币种","交易订单号","实际支付金额","清算流水号","清算日期","订单应结算日期","成功支付时间","订单应结算金额","结算单号","手续费扣除方式","订单手续费","是否分润","对账状态","状态描述","结算状态"};
		//导出表格对应的字段名称
		String[] showField = {"merchantId","transType","currency","transNo","requestAmount","settleNo","settleTime","settleTimePlan","successTime","settleAmountPlan","settleBath","feeWay","fee","isProfit","checkStatus","checkFlg","settleStatus"};
		try {
			excelService.exportExcel("商户清算记录", headerArray,clearingCR,showField,response, request,false);
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordService exportClearingMerchantRecordExcel has a error:{商户清算记录信息导出出错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @方法说明：清结算财务报表List
	 * @时间：2017年3月28日 下午5:37:03
	 * @创建人：wangdong
	 */
	public Model findClearingMerchantRecordReportPage(Page<ClearingMerchantRecord> page,
			ClearingMerchantRecord clearingMerchantRecord, Model model) throws Exception {
		try {
			clearingMerchantRecord.setPage(page);
			Page<ClearingMerchantRecord> pageClearingMerchantRecord = page.setList(clearingMerchantRecordDao.findReportList(clearingMerchantRecord));
			
			List<ClearingMerchantRecord> clearingMRList = Lists.newArrayList();
			for (ClearingMerchantRecord clearingMR : pageClearingMerchantRecord.getList()) {
				clearingMR.setGroupby(clearingMerchantRecord.getGroupby());
				clearingMR.setPayType(PayType.getContentByValue(clearingMR.getPayType()));
				clearingMRList.add(clearingMR);
			}
			pageClearingMerchantRecord.setList(clearingMRList);
			model.addAttribute("page", pageClearingMerchantRecord);
			model.addAttribute("clearingMerchantRecord", clearingMerchantRecord);
			List<ClearingChannelRecord> cList = clearingChannelRecordDao.findChannelRecordName();
			model.addAttribute("cList", cList);
			List<EnumBean> pList = Lists.newArrayList();  //支付类型
			for (PayType payType : PayType.values()) {
				EnumBean ct = new EnumBean();
				ct.setValue(payType.getValue());
				ct.setName(payType.getContent());
				pList.add(ct);
			}
			model.addAttribute("pList", pList);
			return model;
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordService findClearingMerchantRecordReportPage has a error:{清结算财务报表List出错 FIND_ERROR} ", e);
			throw new Exception(e);
		}
	}
	
	
	//运营报表查询
	public Model findClearingMerchantRecordReportDetailPage(Page<ClearingMerchantRecord> page,
			ClearingMerchantRecord clearingMerchantRecord, Model model) throws Exception {
		try {
			//查询全部
			Integer totalPayNumList1=0;
			BigDecimal totalAmountList1 = new BigDecimal("0.0000");
			BigDecimal totalSettleAmountList1 = new BigDecimal("0.0000");
			BigDecimal totalFreeAmountList1 = new BigDecimal("0.0000");
			
			List<ClearingMerchantRecord> totalClearingMerchantRecord = clearingMerchantRecordDao.findDetailPage(clearingMerchantRecord);
			for (ClearingMerchantRecord clearingMerchantRecord2 : totalClearingMerchantRecord) {
				
				//非空替换
				if(clearingMerchantRecord2.getSuccessAmount() == null){
					clearingMerchantRecord2.setSuccessAmount(new BigDecimal("0.0000"));
				}
				if(clearingMerchantRecord2.getSettleAmount() == null){
					clearingMerchantRecord2.setSettleAmount(new BigDecimal("0.0000"));
				}
				if(clearingMerchantRecord2.getFeeAmount() == null){
					clearingMerchantRecord2.setFeeAmount(new BigDecimal("0.0000"));
				}
				
				if(StringUtils.isNotBlank(clearingMerchantRecord2.getCountNum())){
					totalPayNumList1=totalPayNumList1+Integer.parseInt(clearingMerchantRecord2.getCountNum());
				}
				if(StringUtils.isNotBlank(clearingMerchantRecord2.getSuccessAmount().toString())){
					totalAmountList1=totalAmountList1.add(clearingMerchantRecord2.getSuccessAmount());		
								}
				if(StringUtils.isNotBlank(clearingMerchantRecord2.getSettleAmount().toString())){
					totalSettleAmountList1=totalSettleAmountList1.add(clearingMerchantRecord2.getSettleAmount());
				}
				if(StringUtils.isNotBlank(clearingMerchantRecord2.getFeeAmount().toString())){
					totalFreeAmountList1=totalFreeAmountList1.add(clearingMerchantRecord2.getFeeAmount());
				}
				
			}
			model.addAttribute("totalPayNumList1", totalPayNumList1);
			model.addAttribute("totalAmountList1", totalAmountList1);
			model.addAttribute("totalSettleAmountList1", totalSettleAmountList1);
			model.addAttribute("totalFreeAmountList1", totalFreeAmountList1);
			
			
			clearingMerchantRecord.setPage(page);
			List<ClearingMerchantRecord> pageClearingMerchantRecord = clearingMerchantRecordDao.findDetailPage(clearingMerchantRecord);
			Page<ClearingMerchantRecord> pageClearingMerchant =page.setList(pageClearingMerchantRecord);
			List<ClearingMerchantRecord> clearingMRList = Lists.newArrayList();
			Integer totalPayNumList=0;
			BigDecimal totalAmountList = new BigDecimal("0.0000");
			BigDecimal totalSettleAmountList = new BigDecimal("0.0000");
			BigDecimal totalFreeAmountList = new BigDecimal("0.0000");
			//循环翻译部分字段
			for (ClearingMerchantRecord clearingMR : pageClearingMerchant.getList()) {
				
				//非空替换
				if(clearingMR.getSuccessAmount() == null){
					clearingMR.setSuccessAmount(new BigDecimal("0.0000"));
				}
				if(clearingMR.getSettleAmount() == null){
					clearingMR.setSettleAmount(new BigDecimal("0.0000"));
				}
				if(clearingMR.getFeeAmount() == null){
					clearingMR.setFeeAmount(new BigDecimal("0.0000"));
				}
				
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
				if(StringUtils.isNotBlank(clearingMR.getCountNum())){
					totalPayNumList=totalPayNumList+Integer.parseInt(clearingMR.getCountNum());
				}
				if(StringUtils.isNotBlank(clearingMR.getSuccessAmount().toString())){
					totalAmountList=totalAmountList.add(clearingMR.getSuccessAmount());		
								}
				if(StringUtils.isNotBlank(clearingMR.getSettleAmount().toString())){
					totalSettleAmountList=totalSettleAmountList.add(clearingMR.getSettleAmount());
				}
				if(StringUtils.isNotBlank(clearingMR.getFeeAmount().toString())){
					totalFreeAmountList=totalFreeAmountList.add(clearingMR.getFeeAmount());
				}
				clearingMRList.add(clearingMR);
			}
			pageClearingMerchant.setList(clearingMRList);
			
			//前端页面  类型条件显示
			CommonUtil.enumsShow(model,Constants.Clear.SETTLE);
			
			model.addAttribute("page", pageClearingMerchant);
			model.addAttribute("totalPayNumList", totalPayNumList);
			model.addAttribute("totalAmountList", totalAmountList);
			model.addAttribute("totalSettleAmountList", totalSettleAmountList);
			model.addAttribute("totalFreeAmountList", totalFreeAmountList);
			return model;
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordService findClearingMerchantRecordReportPage has a error:{清结算财务报表List出错 FIND_ERROR} ", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @throws Exception 
	 * @方法说明：商户侧清算记录导出
	 * @时间：2016年11月16日 下午2:53:23
	 * @创建人：wangdong
	 */
	public void exportClearingMerchantRecordReportExcel(ClearingMerchantRecord clearingMerchantRecord,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		//数据库查询结果
		List<Map<String,Object>> clearingCR = clearingMerchantRecordDao.findListToReportExcel(clearingMerchantRecord);
		//导出Excel表格标题行
		String[] headerArray = {};
		//导出表格对应的字段名称
		String[] showField = {};
		
		if(null != clearingMerchantRecord){
			if(StringUtils.isNotBlank(clearingMerchantRecord.getGroupby())){
				if(StringUtils.equals(clearingMerchantRecord.getGroupby(), "merchant")){
					String[] merchantheaderArray = {"商户编码","商户名称","交易笔数（笔）","成功交易金额（元）","手续费（元）","结算到商户金额（元）"};
					String[] merchantshowField = {"merchantId","merchantName","countNum","successAmount","feeAmount","settleAmount"};
					headerArray = merchantheaderArray;
					showField = merchantshowField;
				}else if(StringUtils.equals(clearingMerchantRecord.getGroupby(), "paytype")){
					String[] payTypeheaderArray = {"支付类型","交易笔数（笔）","成功交易金额（元）","手续费（元）","结算到商户金额（元）"};
					String[] payTypeshowField = {"payType","countNum","successAmount","feeAmount","settleAmount"};
					headerArray = payTypeheaderArray;
					showField = payTypeshowField;
				}else if(StringUtils.equals(clearingMerchantRecord.getGroupby(), "merchantAndPayType")){
					String[] merchantAndPayTypeheaderArray = {"商户编码","商户名称","支付类型","交易笔数（笔）","成功交易金额（元）","手续费（元）","结算到商户金额（元）"};
					String[] merchantAndPayTypeshowField = {"merchantId","merchantName","payType","countNum","successAmount","feeAmount","settleAmount"};
					headerArray = merchantAndPayTypeheaderArray;
					showField = merchantAndPayTypeshowField;
				}else if(StringUtils.equals(clearingMerchantRecord.getGroupby(), "channel")){
					String[] channelheaderArray = {"支付类型","通道名称","银行名称","交易笔数（笔）","成功交易金额（元）","手续费（元）","结算到商户金额（元）"};
					String[] channelshowField = {"payType","channelName","bankName","countNum","successAmount","feeAmount","settleAmount"};
					headerArray = channelheaderArray;
					showField = channelshowField;
				}
			}else{
				String[] baseheaderArray = {"交易笔数（笔）","成功交易金额（元）","手续费（元）","结算到商户金额（元）"};
				String[] baseshowField = {"countNum","successAmount","feeAmount","settleAmount"};
				headerArray = baseheaderArray;
				showField = baseshowField;
			}
		}
		
		try {
			excelService.exportExcel("财务报表", headerArray,clearingCR,showField,response, request,false);
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordService exportClearingMerchantRecordExcel has a error:{商户清算记录信息导出出错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	
	//运营报表导出
	public void exportSettleMerchantReportRecordExcel(ClearingMerchantRecord clearingMerchantRecord, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		//数据库查询结果
		List<Map<String,Object>> clearingCR = clearingMerchantRecordDao.findListDetailReportExcel(clearingMerchantRecord);
		//导出Excel表格标题行
		String[] headerArray = {"商户编码","商户公司名称","交易类型","产品名称","总笔数","总金额","结算总金额","手续费总金额"};
		//导出表格对应的字段名称
		String[] showField = {"merchantId","merchantName","transType","productName","countNum","successAmount","settleAmount","feeAmount"};
		
		//统计数据
		Integer totalPayNumList1=0;
		BigDecimal totalAmountList1 = new BigDecimal("0.0000");
		BigDecimal totalSettleAmountList1 = new BigDecimal("0.0000");
		BigDecimal totalFreeAmountList1 = new BigDecimal("0.0000");
		Date date=null;
		Date date1=null;
		String dataTotal=null;
		
		List<ClearingMerchantRecord> totalClearingMerchantRecord = clearingMerchantRecordDao.findDetailPage(clearingMerchantRecord);
		for (ClearingMerchantRecord clearingMerchantRecord2 : totalClearingMerchantRecord) {
			
			//非空替换
			if(clearingMerchantRecord2.getSuccessAmount() == null){
				clearingMerchantRecord2.setSuccessAmount(new BigDecimal("0.0000"));
			}
			if(clearingMerchantRecord2.getSettleAmount() == null){
				clearingMerchantRecord2.setSettleAmount(new BigDecimal("0.0000"));
			}
			if(clearingMerchantRecord2.getFeeAmount() == null){
				clearingMerchantRecord2.setFeeAmount(new BigDecimal("0.0000"));
			}
			
			if(StringUtils.isNotBlank(clearingMerchantRecord2.getCountNum())){
				totalPayNumList1=totalPayNumList1+Integer.parseInt(clearingMerchantRecord2.getCountNum());
			}
			if(StringUtils.isNotBlank(clearingMerchantRecord2.getSuccessAmount().toString())){
				totalAmountList1=totalAmountList1.add(clearingMerchantRecord2.getSuccessAmount());		
							}
			if(StringUtils.isNotBlank(clearingMerchantRecord2.getSettleAmount().toString())){
				totalSettleAmountList1=totalSettleAmountList1.add(clearingMerchantRecord2.getSettleAmount());
			}
			if(StringUtils.isNotBlank(clearingMerchantRecord2.getFeeAmount().toString())){
				totalFreeAmountList1=totalFreeAmountList1.add(clearingMerchantRecord2.getFeeAmount());
			}
			
			date = clearingMerchantRecord.getBeginSettleTime();
			date1 = clearingMerchantRecord.getEndSettleTime();
			dataTotal = DateUtils.getDateStr(date, "yyyy-MM-dd")+"-"+DateUtils.getDateStr(date1, "yyyy-MM-dd");
			
		}
		String[] total = {dataTotal,totalPayNumList1.toString(),totalAmountList1.toString(),totalSettleAmountList1.toString(),totalFreeAmountList1.toString()};
		try {
			excelService.exportToExcel("运营报表记录", headerArray,clearingCR,showField,response, request,false,total);
		} catch (Exception e) {
			logger.error("SettleMerchantRecordService exportSettleMerchantRecordExcel has a error:{商户清算记录信息导出错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * 更具ID查询对象
	 * @param clearingId
	 * @return
	 */
    public ClearingMerchantRecord getEntityById(Long clearingId) {

		return clearingMerchantRecordDao.getEntityById(clearingId);
    }

    /**
     * @方法说明：查询当前对象
     * @时间： 2017-05-19 11:13 AM
     * @创建人：wangl
     */
	public ClearingMerchantRecord getEntity(String transType, String transNo) {

		return clearingMerchantRecordDao.getEntity(transType,transNo);
	}
}
