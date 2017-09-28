package com.heepay.manage.modules.settle.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.common.utils.DictList;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.reconciliation.util.ChannelTypeClear;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.ChannelType;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingCurrency;
import com.heepay.enums.billing.BillingSettleStatus;
import com.heepay.enums.billing.ClearingCheckStatus;
import com.heepay.enums.billing.SettleCyc;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.settle.dao.SettleChannelRecordDao;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecord;
import com.heepay.manage.modules.settle.entity.SettleChannelRecord;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.settle.service.util.ExcelService;

/**
*
* 描    述：通道结算Service
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
public class SettleChannelRecordService extends CrudService<SettleChannelRecordDao, SettleChannelRecord>{

	private static final Logger logger=LogManager.getLogger();
	
	@Autowired
	private SettleChannelRecordDao settleChannelRecordDao;
	
	@Autowired
	private ClearingChannelRecordService clearingChannelRecordService;
	
	@Autowired
	private ExcelService excelService;
	
	/**
	 * @方法说明：根据id通道结算记录
	 * @author wangdong
	 * @param id
	 * @时间：2016年9月14日11:11:56
	 */
	public SettleChannelRecord get(Long id) {
		return settleChannelRecordDao.get(id);
	}
	
	/**
	 * @方法说明：获取通道结算记录List
	 * @author wangdong
	 * @param settleChannelRecord
	 * @时间： 2016年9月14日11:12:00
	 */
	public List<SettleChannelRecord> findList(SettleChannelRecord settleChannelRecord) {
		return super.findList(settleChannelRecord);
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：查询通道侧结算记录List
	 * @时间：2016年11月16日 下午2:59:00
	 * @创建人：wangdong
	 */
	public Model findSettleChannelRecordPage(Page<SettleChannelRecord> page, SettleChannelRecord settleChannelRecord,Model model) throws Exception {
		try {

			SettleChannelRecord settleSumNum = settleChannelRecordDao.sumNum(settleChannelRecord);
			model.addAttribute("settleSumNum", settleSumNum);

			Page<SettleChannelRecord> pageSettleChannelRecord = findPage(page, settleChannelRecord);
			List<SettleChannelRecord> settleCRList = Lists.newArrayList();


			//循环翻译部分字段
			for (SettleChannelRecord settleCR : pageSettleChannelRecord.getList()) {

				// 判断状态是否是结算中
				if(settleCR.getSettleStatus().equals(BillingSettleStatus.SETTLESTATUSD.getValue()) || settleCR.getSettleStatus().equals(BillingSettleStatus.SETTLE_STATUS_C.getValue())){
					settleCR.setFlag("Y");
				}

				//通道业务类型
				if(StringUtils.isNotBlank(settleCR.getChannelType())){
					settleCR.setChannelType(ChannelTypeClear.labelOf(settleCR.getChannelType()));
				}

				//结算状态
				if(StringUtils.isNotBlank(settleCR.getSettleStatus())){
					settleCR.setSettleStatus(BillingSettleStatus.labelOf(settleCR.getSettleStatus()));
				}
				
				settleCRList.add(settleCR);
			}
			pageSettleChannelRecord.setList(settleCRList);

			//页面显示通道合作方的查询条件下拉选
			//  List<EnumBean> cList = DictList.channelPartner();
			//	model.addAttribute("cList", cList);
			List<SettleChannelRecord> cList = settleChannelRecordDao.findChannelRecordName();
			model.addAttribute("cList", cList);
			//前端页面  类型条件显示
			CommonUtil.enumsShow(model,Constants.Clear.SETTLE);
			model.addAttribute("page", pageSettleChannelRecord);
			return model;
		} catch (Exception e) {
			logger.error("SettleChannelRecordService findSettleChannelRecordPage has a error:{查询通道结算记录List错误FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：获取通道侧结算记录详细信息
	 * @时间：2016年11月16日 下午3:11:07
	 * @创建人：wangdong
	 */
	public Model findSettleChannelRecordDetailPage(ClearingChannelRecord clearingChannelRecord,
			Page<ClearingChannelRecord> page, Model model) throws Exception {
		try {
			Page<ClearingChannelRecord> pageClearingChannelRecord = clearingChannelRecordService.findPage(page, clearingChannelRecord);

			List<ClearingChannelRecord> clearingCRList = Lists.newArrayList();
			//循环翻译部分字段
			for (ClearingChannelRecord clearingCR : pageClearingChannelRecord.getList()) {
				//通道业务类型
				if(StringUtils.isNotBlank(clearingCR.getChannelType())){
					clearingCR.setChannelType(ChannelTypeClear.labelOf(clearingCR.getChannelType()));
				}
				//结算周期
				if(StringUtils.isNotBlank(clearingCR.getSettleCyc())){
					if(StringUtils.equals(clearingCR.getSettleCyc(), Constants.Clear.T0)){
						clearingCR.setSettleCyc(SettleCyc.SETTLECYC_T0.getValue());
					}else if(StringUtils.equals(clearingCR.getSettleCyc(), Constants.Clear.T1)){
						clearingCR.setSettleCyc(SettleCyc.SETTLECYC_T1.getValue());
					}
				}
				//币种
				if(StringUtils.isNotBlank(clearingCR.getCurrency())){
					clearingCR.setCurrency(BillingCurrency.labelOf(clearingCR.getCurrency()));
				}else{
					clearingCR.setCurrency(BillingCurrency.CURRENCY.getContent());
				}
				//成本结算周期
				if(StringUtils.isNotBlank(clearingCR.getCostSettleCyc())){
					if(StringUtils.equals(clearingCR.getCostSettleCyc(), Constants.Clear.T0)){
						clearingCR.setCostSettleCyc("实时");
					}else if(StringUtils.equals(clearingCR.getCostSettleCyc(), Constants.Clear.T1)){
						clearingCR.setCostSettleCyc("周期");
					}
				}
				//交易类型
				if(StringUtils.isNotBlank(clearingCR.getTransType())){
					clearingCR.setTransType(TransType.labelOf(clearingCR.getTransType()));
				}
				//对账状态
				if(StringUtils.isNotBlank(clearingCR.getCheckStatus())){
					clearingCR.setCheckStatus(ClearingCheckStatus.labelOf(clearingCR.getCheckStatus()));
				}
				//结算状态
				if(StringUtils.isNotBlank(clearingCR.getSettleStatus())){
					clearingCR.setSettleStatus(BillingSettleStatus.labelOf(clearingCR.getSettleStatus()));
				}
				
				clearingCRList.add(clearingCR);
			}
			pageClearingChannelRecord.setList(clearingCRList);
			model.addAttribute("page", pageClearingChannelRecord);
			return model;
		} catch (Exception e) {
			logger.error("SettleChannelRecordService findSettleChannelRecordDetailPage has a error:{获取通道侧结算记录详细信息错误FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：通道侧结算记录导出
	 * @时间：2016年11月16日 下午3:21:12
	 * @创建人：wangdong
	 */
	public void exportSettleChannelRecordExcel(SettleChannelRecord settleChannelRecord, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		//数据库查询结果
		List<Map<String,Object>> clearingCR = settleChannelRecordDao.findListToExcel(settleChannelRecord);
		//导出Excel表格标题行
		String[] headerArray = {"通道合作方","支付通道类型","交易总笔数","交易总金额","结算日期","结算单号","交易总成本","结算状态"};
		//导出表格对应的字段名称
		String[] showField = {"channelName","channelType","payNum","totalAmount","settleTime","settleBath","costAmount","settleStatus"};
		try {
			excelService.exportExcel("通道结算记录", headerArray,clearingCR,showField,response, request,false);
		} catch (Exception e) {
			logger.error("SettleChannelRecordService exportSettleChannelRecordExcel has a error:{通道结算记录信息导出错误FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * @方法说明：更新为不结算
	 * @时间： 7/6/2017 3:56 PM
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int updateEntity(SettleChannelRecord sttleChannelRecord) {

		return settleChannelRecordDao.updateEntity(sttleChannelRecord);
	}
	/**
	 * @方法说明：查询当前数据
	 * @时间： 7/6/2017 3:56 PM
	 * @创建人：wangl
	 */
	public SettleChannelRecord getEntity(String settleBath) {

		return settleChannelRecordDao.getEntity(settleBath);
	}
}
