package com.heepay.manage.modules.reconciliation.web;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.*;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferRecordHis;
import com.heepay.manage.modules.reconciliation.service.SettleDifferRecordHisService;
import com.heepay.manage.modules.reconciliation.util.ChannelTypeClear;
import com.heepay.manage.modules.riskLogs.service.RiskLogsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/***
 * 
 * 
 * 描 述：差异记录查询
 *
 * 创 建 者： wangl 
 * 创建时间： 2016年9月23日下午1:38:03 
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
@Controller
@RequestMapping(value = "${adminPath}/reconciliation/SettleDifferRrecordHis")
public class SettleDifferRrecordHisController extends BaseController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SettleDifferRecordHisService settleDifferRrecordHisService;

	@Autowired
	private RiskLogsService riskLogsService;//日志操作

	@Autowired
	private ErrorExcelExport excelController;// 下载的方法

	// 差异记录查询页面显示
	@RequiresPermissions("reconciliation:settleDifferRrecordHis:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleDifferRecordHis settleDifferRecordHis, HttpServletRequest request, HttpServletResponse response,
			Model model) {

		//格式化查询时间条件
		Date endCheckTime = settleDifferRecordHis.getEndErrorTime();
		if(endCheckTime!=null){
			String format = new SimpleDateFormat("yyyy-MM-dd").format(endCheckTime);
			format=format+" 23:59:59";
			try {
				Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
				settleDifferRecordHis.setEndErrorTime(time);
			} catch (ParseException e) {
				logger.error("时间转换异常",e.getMessage());
			}
		}
		
		Page<SettleDifferRecordHis> page = settleDifferRrecordHisService.findPage(new Page<SettleDifferRecordHis>(request, response), settleDifferRecordHis);

		logger.info("查询数据---------->", page.getList());
		List<SettleChannelManager> list = null;
		int size = 0;
		try {
			//前台页面搜索条件的名称汇总
			list = settleDifferRrecordHisService.getBatchName();
			size = list.size();
		} catch (Exception e) {
			logger.error("没有获取到名称", e);
		}
		// 根据getBatchName方法去通道管理表查询通道名称有没有数据，如果没有就不让其显示可添加的页面
		if (size != 0) {
			List<SettleDifferRecordHis> clearingList = Lists.newArrayList();
			for (SettleChannelManager settleChannelManager : list) {
				SettleDifferRecordHis settleRule = new SettleDifferRecordHis();

				settleRule.setChannelCode(settleChannelManager.getChannelCode());
				settleRule.setChannelName(settleChannelManager.getChannelName());
				clearingList.add(settleRule);
				logger.info("查询数据---------->",settleChannelManager);
			}
			model.addAttribute("checklist", clearingList);
		} else {
			model.addAttribute("checklist", null);
		}

		List<SettleDifferRecordHis> clearingCRList = Lists.newArrayList();
		// 遍历根据条件查询出来的page,动态翻译，将数据库的字段翻译成中文
		for (SettleDifferRecordHis clearingCR : page.getList()) {
			
			/**
			 * CD : 撤单
			 * BD: 补单
			 * QT: 其他
			 * XXCL: 线下处理
			 * QXCY: 取消差异
			 */
			// 系统补单查询
			// 充值
			if (StringUtils.equals(clearingCR.getTransType(), TransType.CHARGE.getValue())) {
				if (BillingDifferType.BDTYPEC.getValue().equals(clearingCR.getDifferType())) {
					clearingCR.setBD("BD");// 补单。只是定义的一个前台页面比较的值
					if (BillingBillStatus.BBSTATUSY.getValue().equals(clearingCR.getHandleResult())) {
						clearingCR.setBD("YBD");
					}
				}
			}
			// 支付
			if (StringUtils.equals(clearingCR.getTransType(), TransType.PAY.getValue())) {
				if (BillingDifferType.BDTYPEC.getValue().equals(clearingCR.getDifferType())) {
					if(clearingCR.getTransStatus().equals(SettleDifferTransStatus.PAYMENTSTATUS_SUC.getValue())){
						clearingCR.setBD("BD");
						if (BillingBillStatus.BBSTATUSY.getValue().equals(clearingCR.getHandleResult())) {
							clearingCR.setBD("YBD");
						}
					}else{
					clearingCR.setCD("CD");// cd。只是定义的一个前台页面比较的值
					if (BillingBillStatus.BBSTATUSY.getValue().equals(clearingCR.getHandleResult())) {
						clearingCR.setCD("YCD");
					  }
					}
				}
			}

			
			// 提现，转账,退款
			//BD
			if (BillingDifferType.BDTYPED.getValue().equals(clearingCR.getDifferType())) {
				clearingCR.setBD("BD");// 补单。只是定义的一个前台页面比较的值
				if (BillingBillStatus.BBSTATUSY.getValue().equals(clearingCR.getHandleResult())) {
					clearingCR.setBD("YBD");
				}
			}
			// QXCY
			if (BillingDifferType.BDTYPEJ.getValue().equals(clearingCR.getDifferType())) {
				clearingCR.setQXCY("QXCY");// 取消差异。只是定义的一个前台页面比较的值
				if (BillingBillStatus.BBSTATUSY.getValue().equals(clearingCR.getHandleResult())) {
					clearingCR.setQXCY("YQXCY");
				}
			}
			// QT
			if (BillingDifferType.BDTYPEO.getValue().equals(clearingCR.getDifferType())) {
				clearingCR.setQT("QT");// 其他。只是定义的一个前台页面比较的值
				if (BillingBillStatus.BBSTATUSY.getValue().equals(clearingCR.getHandleResult())) {
					clearingCR.setQT("YQT");
				}
			}

			// TransType
			if (StringUtils.isNotBlank(clearingCR.getTransType())) {
				clearingCR.setTransType(TransType.labelOf(clearingCR.getTransType()));
			}

			// ChannelType
			if (StringUtils.isNotBlank(clearingCR.getChannelType())) {
				clearingCR.setChannelType(ChannelTypeClear.labelOf(clearingCR.getChannelType()));
			}

			// DifferType
			if (StringUtils.isNotBlank(clearingCR.getDifferType())) {
				clearingCR.setDifferType(BillingDifferType.labelOf(clearingCR.getDifferType()));
			}
			// HandleResult
			if (StringUtils.isNotBlank(clearingCR.getHandleResult())) {
				clearingCR.setHandleResult(BillingBillStatus.labelOf(clearingCR.getHandleResult()));
			}

			// trans_status
			if (StringUtils.isNotBlank(clearingCR.getTransStatus())) {
				clearingCR.setTransStatus(SettleDifferTransStatus.labelOf(clearingCR.getTransStatus()));
			}
			// is_bill
			if (StringUtils.isNotBlank(clearingCR.getIsBill())) {
				clearingCR.setIsBill(ClearingCheckStatus.labelOf(clearingCR.getIsBill()));
			}
			// is_profit
			if (StringUtils.isNotBlank(clearingCR.getIsProfit())) {
				clearingCR.setIsProfit(SettleDifferIsProfit.labelOf(clearingCR.getIsProfit()));
			}

			// HandleMessage
			if (StringUtils.isNotBlank(clearingCR.getHandleMessage())) {

				String checkFlg = clearingCR.getHandleMessage();
				String stringarray[] = checkFlg.split(",");
				int length = stringarray.length;
				/**
				 * 因为HandleMessage是一个组合命名，例如：QT,Y 所以要拆分翻译
				 */
				if (length == 2) {
					// 获取出组合命名的数据
					logger.debug(length + "条数据------->" + stringarray);
					String handleMessagePath = ErrorStatusTranslation.labelOf(stringarray[0]) + "-"
							+ ErrorNoteType.labelOf(stringarray[1]);
					clearingCR.setHandleMessage(handleMessagePath);
				} else {
					// 获取出单个命名的数据
					logger.debug(length + "条数据------->" + stringarray);
					clearingCR.setHandleMessage(ErrorStatusTranslation.labelOf(clearingCR.getHandleMessage()));
				}
			}

			clearingCRList.add(clearingCR);
		}
		page.setList(clearingCRList);

		logger.info("查询到数据--->", page.getList());

		// 通道业务类型
		List<EnumBean> pTList = ChannelTypeClear.getList();

		model.addAttribute("checkTypeList", pTList);
		
		// 交易类型
		List<EnumBean> transType = Lists.newArrayList();
		for (TransType checkFlg : TransType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			transType.add(ct);
		}
		model.addAttribute("transType", transType);
				
		// 差错类型
		List<EnumBean> errorList = Lists.newArrayList();
		for (BillingDifferType checkFlg : BillingDifferType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			errorList.add(ct);
		}
		model.addAttribute("errorList", errorList);

		// 其他差异备注时的选项
		List<EnumBean> errorNote = Lists.newArrayList();
		for (ErrorNoteType checkFlg : ErrorNoteType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			errorNote.add(ct);
		}
		model.addAttribute("errorNote", errorNote);

		// 差错状态
		List<EnumBean> errorStatus = Lists.newArrayList();
		for (ErrorStatus checkFlg : ErrorStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			errorStatus.add(ct);
		}
		model.addAttribute("errorStatus", errorStatus);

		model.addAttribute("page", page);
		model.addAttribute("settleDifferRecordHis", settleDifferRecordHis);
		
		logger.info("操作结束------->",settleDifferRecordHis);
		return "modules/reconciliation/checkErrorManageHisList";
	}

	// 记录信息导出
	@RequiresPermissions("reconciliation:settleDifferRrecordHis:edit")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes, 
					   SettleDifferRecordHis settleDifferRecordHis,
					   HttpServletRequest request, 
					   HttpServletResponse response) {

		logger.info("差异记录下载开始------->",redirectAttributes);
		
		//格式化查询时间条件
		Date endCheckTime = settleDifferRecordHis.getEndErrorTime();
		if(endCheckTime!=null){
			String format = new SimpleDateFormat("yyyy-MM-dd").format(endCheckTime);
			format=format+" 23:59:59";
			try {
				Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
				settleDifferRecordHis.setEndErrorTime(time);
			} catch (ParseException e) {
				logger.error("时间转换异常",e.getMessage());
			}
		}
				
		//写入日志
		riskLogsService.savaEntity("下载", "差异记录下载", request);
		// 根据条件查询出数据并导出
		List<Map<String, Object>> clearingCR = settleDifferRrecordHisService.findListExcel(settleDifferRecordHis);

		// 表格生产的标题行
		String[] headerArray = { "通道合作方", "支付通道类型", "交易类型","对账批次号", "差错日期", "差错类型", "支付单号", "交易订单号", "我方金额", "对方金额","操作人", "操作日期","分润标识", "差错状态" };
		// 表格插入对应的字段值
		String[] showField = { "channelName", "channelType", "transType", "checkBathno","errorDate", "differType", "paymentId","transNo", "requestAmount", "successAmount","updateAuthor", "operationDate", "isProfit", "handleMessage" };

		/**
		 * 导出为excel表格
		 */
		try {
			excelController.exportExcel("对账差错历史管理记录", headerArray, clearingCR, showField, request, response);
		} catch (Exception e) {

			logger.error("ClearingChannelRecordController list has a error:", e);
		}
	}

}
