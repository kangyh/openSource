package com.heepay.manage.modules.settle.service;

import com.google.common.collect.Lists;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingCurrency;
import com.heepay.enums.billing.BillingSettleStatus;
import com.heepay.enums.billing.BillingYCheckStatus;
import com.heepay.enums.billing.ClearingCheckStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.reconciliation.util.ChannelTypeClear;
import com.heepay.manage.modules.settle.dao.ClearingChannelRecordDao;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecord;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.settle.service.util.ExcelService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
*
* 描    述：通道清算记录Service
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
public class ClearingChannelRecordService extends CrudService<ClearingChannelRecordDao, ClearingChannelRecord>{
	
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private ClearingChannelRecordDao clearingChannelRecordDao;
	
	@Autowired
	private ExcelService excelService;
	
	/**
	 * @方法说明：根据id获取通道清算记录
	 * @author wangdong
	 * @param id
	 * @时间： 2016年9月14日10:59:57
	 */
	public ClearingChannelRecord get(Long id) {
		return clearingChannelRecordDao.get(id);
	}
	
	/**
	 * @方法说明：获取通道清算记录List
	 * @author wangdong
	 * @param clearingChannelRecord
	 * @时间：2016年9月14日11:00:57
	 */
	public List<ClearingChannelRecord> findList(ClearingChannelRecord clearingChannelRecord) {
		return super.findList(clearingChannelRecord);
	}
	
	/**
	 * @方法说明：通道清算记录表中的通道名称
	 * @时间：2016年9月26日
	 * @创建人：wangdong
	 */
	public List<ClearingChannelRecord> findChannelRecordName() {
		return clearingChannelRecordDao.findChannelRecordName();
	}
	
	/**
	 * 
	 * @param model 
	 * @方法说明：查询通道侧清算记录List
	 * @时间：2016年11月16日 下午1:45:28
	 * @创建人：wangdong
	 */
	public Model findClearingChannelRecordPage(Page<ClearingChannelRecord> page,ClearingChannelRecord clearingChannelRecord, Model model) throws Exception{
		try {

			ClearingChannelRecord clearingSumNum = clearingChannelRecordDao.sumNum(clearingChannelRecord);
			model.addAttribute("clearingSumNum", clearingSumNum);

			Page<ClearingChannelRecord> pageClearingChannelRecord = findPage(page, clearingChannelRecord);
			List<ClearingChannelRecord> clearingCRList = Lists.newArrayList();


			//循环翻译部分字段
			for (ClearingChannelRecord clearingCR : pageClearingChannelRecord.getList()) {

				//通道业务类型
				if(StringUtils.isNotBlank(clearingCR.getChannelType())){
					clearingCR.setChannelType(ChannelTypeClear.labelOf(clearingCR.getChannelType()));
				}
				//币种
				if(StringUtils.isNotBlank(clearingCR.getCurrency())){
					clearingCR.setCurrency(BillingCurrency.labelOf(clearingCR.getCurrency()));
				}else{
					clearingCR.setCurrency(BillingCurrency.CURRENCY.getContent());
				}
				//交易类型
				if(StringUtils.isNotBlank(clearingCR.getTransType())){
					clearingCR.setTransType(TransType.labelOf(clearingCR.getTransType()));
				}
				//对账状态
				if(StringUtils.isNotBlank(clearingCR.getCheckStatus())){
					clearingCR.setCheckStatus(ClearingCheckStatus.labelOf(clearingCR.getCheckStatus()));
				}
				//已对账状态
				if(StringUtils.isNotBlank(clearingCR.getCheckFlg())){
					if(BillingYCheckStatus.BCFQSTS.getValue().toString().equals(clearingCR.getCheckFlg())){
						clearingCR.setCheckFlg("平账");
					}else{
						//除了平账都是差异账（产品需求）
						clearingCR.setCheckFlg("差异账");
					}
				}
				//结算状态
				if(StringUtils.isNotBlank(clearingCR.getSettleStatus())){
					clearingCR.setSettleStatus(BillingSettleStatus.labelOf(clearingCR.getSettleStatus()));
				}
				
				clearingCRList.add(clearingCR);
			}
			pageClearingChannelRecord.setList(clearingCRList);

			//页面显示通道合作方的查询条件下拉选
//			List<EnumBean> cList = DictList.channelPartner();
//			model.addAttribute("cList", cList);
			List<ClearingChannelRecord> cList = clearingChannelRecordDao.findChannelRecordName();
			model.addAttribute("cList", cList);
			
			//前端页面  类型条件显示
			CommonUtil.enumsShow(model,Constants.Clear.SETTLE);
			
			model.addAttribute("page", pageClearingChannelRecord);
			model.addAttribute("clearingChannelRecord",clearingChannelRecord);
			return model;
		} catch (Exception e) {
			logger.error("ClearingChannelRecordService findClearingChannelRecordPage has a error:{查询通道清算记录List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @方法说明：通道侧清算记录导出
	 * @时间：2016年11月16日 下午2:00:58
	 * @创建人：wangdong
	 */
	public void exportClearingChannelRecordExcel(ClearingChannelRecord clearingChannelRecord,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		//数据库查询结果
		List<Map<String,Object>> clearingCR = clearingChannelRecordDao.findListExcel(clearingChannelRecord);
		//导出Excel表格标题行
		String[] headerArray = {"通道合作方","支付通道类型","交易类型","币种","交易日期","支付单号","交易订单号","实际支付金额","通道对账日期","清算日期","清算流水号","银行流水","应结算日期","结算单号","交易成本","对账状态","状态描述","结算状态"};
		//导出表格对应的字段名称
		String[] showField = {"channelName","channelType","transType","currency","payTime","paymentId","transNo","successAmount","channelTime","settleTime","settleNo","bankSeq","settleTimePlan","settleBath","costAmount","checkStatus","checkFlg","settleStatus"};
		try {
			excelService.exportExcel("通道清算记录", headerArray,clearingCR,showField,response,request,false);
		} catch (Exception e) {
			logger.error("ClearingChannelRecordService exportClearingChannelRecordExcel has a error:{通道清算记录信息导出出错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * @方法说明：根据ID查询对象
	 * @时间： 2017-04-10 06:10 PM
	 * @创建人：wangl
	 */
	public ClearingChannelRecord getEntityById(Long clearingId) {

		return clearingChannelRecordDao.getEntityById(clearingId);
	}
}
