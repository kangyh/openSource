package com.heepay.manage.modules.settle.service;

import com.google.common.collect.Lists;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingCurrency;
import com.heepay.enums.billing.BillingSettleStatus;
import com.heepay.enums.billing.ClearingCheckStatus;
import com.heepay.enums.billing.SettleCyc;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.DictList;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.reconciliation.util.ChannelTypeClear;
import com.heepay.manage.modules.settle.dao.SettleChannelRecordDao;
import com.heepay.manage.modules.settle.dao.SettleChannelRecordHisDao;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecordHis;
import com.heepay.manage.modules.settle.entity.SettleChannelRecordHis;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.settle.service.util.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月10日下午2:09:09
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
public class SettleChannelRecordHisService extends CrudService<SettleChannelRecordHisDao, SettleChannelRecordHis>{
	
	@Autowired
	private SettleChannelRecordDao settleChannelRecordDao;
	
	@Autowired
	private SettleChannelRecordHisDao settleChannelRecordHisDao;
	
	@Autowired
	private ExcelService excelService;
	
	@Autowired
	private ClearingChannelRecordHisService clearingChannelRecordHisService;
	
	public Model findSettleChannelRecordHisPage(Page<SettleChannelRecordHis> page, SettleChannelRecordHis sttleChannelRecordHis,
			Model model) throws Exception {
		try {
			Page<SettleChannelRecordHis> pageSettleChannelRecordHis = findPage(page, sttleChannelRecordHis);

			List<SettleChannelRecordHis> settleCRList = Lists.newArrayList();
			//循环翻译部分字段
			for (SettleChannelRecordHis settleCR : pageSettleChannelRecordHis.getList()) {
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
			pageSettleChannelRecordHis.setList(settleCRList);
			//页面显示通道合作方的查询条件下拉选
			List<EnumBean> cList = DictList.channelPartner();
			model.addAttribute("cList", cList);
			/*List<SettleChannelRecord> cList = settleChannelRecordDao.findChannelRecordName();
			model.addAttribute("cList", cList);*/
			//前端页面  类型条件显示
			CommonUtil.enumsShow(model,Constants.Clear.SETTLE);
			model.addAttribute("page", pageSettleChannelRecordHis);
			return model;
		} catch (Exception e) {
			logger.error("SettleChannelRecordHisService findSettleChannelRecordPage has a error:{查询通道结算历史记录List错误FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	//导出
	public void exportSettleChannelRecordHisExcel(SettleChannelRecordHis settleChannelRecordHis, HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		//数据库查询结果
		List<Map<String,Object>> clearingCR = settleChannelRecordHisDao.findListToExcel(settleChannelRecordHis);
		//导出Excel表格标题行
		String[] headerArray = {"通道合作方","支付通道类型","交易总笔数","交易总金额","结算日期","结算单号","交易总成本","结算状态"};
		//导出表格对应的字段名称
		String[] showField = {"channelName","channelType","payNum","totalAmount","settleTime","settleBath","costAmount","settleStatus"};
		try {
			excelService.exportExcel("通道结算历史记录", headerArray,clearingCR,showField,response, request,false);
		} catch (Exception e) {
			logger.error("SettleChannelRecordHisService exportSettleChannelRecordExcel has a error:{通道结算历史记录信息导出错误FIND_ERROR}", e);
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
	public Model findSettleChannelRecordHisDetailPage(ClearingChannelRecordHis clearingChannelRecordHis,
			Page<ClearingChannelRecordHis> page, Model model) throws Exception {
		try {
			Page<ClearingChannelRecordHis> pageClearingChannelRecordHis = clearingChannelRecordHisService.findPage(page, clearingChannelRecordHis);

			List<ClearingChannelRecordHis> clearingCRList = Lists.newArrayList();
			//循环翻译部分字段
			for (ClearingChannelRecordHis clearingCR : pageClearingChannelRecordHis.getList()) {
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
			pageClearingChannelRecordHis.setList(clearingCRList);
			model.addAttribute("page", pageClearingChannelRecordHis);
			return model;
		} catch (Exception e) {
			logger.error("SettleChannelRecordHisService findSettleChannelRecordDetailPage has a error:{获取通道侧结算历史记录详细信息错误FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
}
