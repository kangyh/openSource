package com.heepay.manage.modules.reconciliation.web.adjustMoney;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.*;
import com.heepay.enums.billing.*;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferRecord;
import com.heepay.manage.modules.reconciliation.service.SettleDifferRecordService;
import com.heepay.manage.modules.reconciliation.util.ChannelTypeClear;
import com.heepay.manage.modules.reconciliation.web.ErrorExcelExport;
import com.heepay.manage.modules.reconciliation.web.rpc.client.BillingClearAPIClient;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.riskLogs.service.RiskLogsService;
import com.heepay.manage.modules.settle.web.util.PayTypeSettle;
import com.heepay.rpc.payment.model.AsyncMsgModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping(value = "${adminPath}/reconciliation/SettleDifferRrecord")
public class SettleDifferRrecordRefactorController extends BaseController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SettleDifferRecordService settleDifferRrecordService;

	@Autowired
	private RiskLogsService riskLogsService;//日志操作
	
	@Autowired
	private BillingClearAPIClient billingClearAPIClient;

	@Autowired
	private ErrorExcelExport excelController;// 下载的方法

	/**
	 * @方法说明：差异记录查询页面显示
	 * @时间： 2017-05-22 06:15 PM
	 * @创建人：wangl
	 */
	@RequiresPermissions("reconciliation:settleDifferRrecord:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleDifferRecord settleDifferRecord, HttpServletRequest request, HttpServletResponse response,
			Model model) {

		//使用cookie保存查询条件
		settleDifferRecord = (SettleDifferRecord) SaveConditions.result(settleDifferRecord, "settleDifferRecord", request, response);

		logger.info("差异记录查询数据--->{差异记录查询页面显示}",settleDifferRecord);
		//格式化查询时间条件
		Date endCheckTime = settleDifferRecord.getEndCheckTime();
		if(endCheckTime!=null){
			String format = new SimpleDateFormat("yyyy-MM-dd").format(endCheckTime);
			format=format+" 23:59:59";
			try {
				Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
				settleDifferRecord.setEndCheckTime(time);
			} catch (ParseException e) {
				logger.error("差异记录查询数据--->{时间转换异常}",e.getMessage());
			}
		}


		//重构分页方法
		Page<SettleDifferRecord> page = differRecordPage(new Page<SettleDifferRecord>(request, response), settleDifferRecord);
		try {
			//通道合作方
			List<SettleChannelManager> list = settleDifferRrecordService.getBatchName();
			if (null != list && !list.isEmpty()) {
				List<SettleDifferRecord> channelCode = Lists.newArrayList();
				list.forEach(p ->{
					SettleDifferRecord settleRule = new SettleDifferRecord();
					settleRule.setChannelCode(p.getChannelCode());
					settleRule.setChannelName(p.getChannelName());
					channelCode.add(settleRule);
				});
				model.addAttribute("channelCode", channelCode);

				/*for (SettleChannelManager settleChannelManager : list) {
					SettleDifferRecord settleRule = new SettleDifferRecord();
					settleRule.setChannelCode(settleChannelManager.getChannelCode());
					settleRule.setChannelName(settleChannelManager.getChannelName());
					channelCode.add(settleRule);
					logger.info("查询通道合作方数据--->{结果}",settleChannelManager);
				}*/
			}
		} catch (Exception e) {
			logger.error("查询通道合作方数据--->{没有获取到名称}", e.getMessage());
		}

		/**
		 * CD : 撤单
		 * BD: 补单
		 * QT: 其他
		 * XXCL: 线下处理
		 * QXCY: 取消差异
		 */
		// 遍历根据条件查询出来的page,动态翻译，将数据库的字段翻译成中文
		for (SettleDifferRecord clearingCR : page.getList()) {

			// 充值
			if (StringUtils.equals(clearingCR.getTransType(), TransType.CHARGE.getValue()) || StringUtils.equals(clearingCR.getTransType(), TransType.BATCHCOLLECTION.getValue())) {
				if (BillingDifferType.BDTYPEC.getValue().equals(clearingCR.getDifferType())) {
					clearingCR.setBD("BD");// 补单。只是定义的一个前台页面比较的值
					if (BillingBillStatus.BBSTATUSY.getValue().equals(clearingCR.getHandleResult())) {
						clearingCR.setBD("YBD");
					}
				}
			}
			/*if (StringUtils.equals(clearingCR.getTransType(), TransType.PAY.getValue())) {
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
			}*/
            // 支付
            if (StringUtils.equals(clearingCR.getTransType(), TransType.PAY.getValue())) {
                if (BillingDifferType.BDTYPEC.getValue().equals(clearingCR.getDifferType())) {
                    clearingCR.setBD("BD");
                    if (BillingBillStatus.BBSTATUSY.getValue().equals(clearingCR.getHandleResult())) {
						clearingCR.setBD("YBD");
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

			//翻译字段
			resultName(clearingCR);
		}

		resultModel(model);
		model.addAttribute("page", page);
		model.addAttribute("settleDifferRecord", settleDifferRecord);
		return "modules/reconciliation/settleDifferRrecord";
	}




	/**
	 * @方法说明：记录信息导出
	 * @时间： 2017-05-22 06:15 PM
	 * @创建人：wangl
	 */
	@RequiresPermissions("reconciliation:settleDifferRrecord:edit")
	@RequestMapping(value = "/export")
	public void export(RedirectAttributes redirectAttributes, 
					   SettleDifferRecord settleDifferRecord,
					   HttpServletRequest request, 
					   HttpServletResponse response) {


		logger.info("记录信息导出--->{差异记录下载开始}",settleDifferRecord);
		//格式化查询时间条件
		Date endCheckTime = settleDifferRecord.getEndCheckTime();
		if(endCheckTime!=null){
			String format = new SimpleDateFormat("yyyy-MM-dd").format(endCheckTime);
			format=format+" 23:59:59";
			try {
				Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
				settleDifferRecord.setEndCheckTime(time);
			} catch (ParseException e) {
				logger.error("时间转换异常",e.getMessage());
			}
		}
				
		//写入日志
		riskLogsService.savaEntity("记录信息导出", "差异记录下载", request);
		// 根据条件查询出数据并导出
		List<Map<String, Object>> clearingCR = settleDifferRrecordService.findListExcel(settleDifferRecord);

		// 表格生产的标题行
		String[] headerArray = {"通道合作方","支付通道类型","交易类型","对账批次号","差错日期","差错类型","支付单号","交易订单号","我方金额","对方金额","操作人","操作日期","分润标识","差错状态"};
		// 表格插入对应的字段值
		String[] showField = {"channelName","channelType","transType","checkBathno","errorDate","differType","paymentId","transNo","requestAmount","successAmount","updateAuthor","operationDate","isProfit","handleMessage"};

		//下载的标题行和表格插入对应的字段值

		/**
		 * 导出为excel表格
		 */
		try {
			excelController.exportExcel("对账差错管理记录", headerArray, clearingCR, showField, request, response);
		} catch (Exception e) {
			logger.error("ClearingChannelRecordController list has a error:", e);
		}
	}


	/**
	 * @方法说明：补单跳转
	 * @时间： 2017-05-22 06:14 PM
	 * @创建人：wangl
	 */
	@RequiresPermissions("reconciliation:settleDifferRrecord:edit")
	@RequestMapping(value = "/differences/addbd/{differId}")
	public String differencesAddBd(@PathVariable("differId") int differId, Model model) {

		logger.info("差异单处理开始--->{补单跳转}",differId);
		//根据id获取到对象，然后翻译成中文
		SettleDifferRecord settleDifferRecord = settleDifferRrecordService.getEntityById(differId);
		//翻译字段
		resultName(settleDifferRecord);
		model.addAttribute("settleDifferRecord",settleDifferRecord);
		logger.info("差异单处理结束--->{补单跳转}",settleDifferRecord);

		return "modules/reconciliation/addBd";
	}


	/**
	 * @方法说明：补单处理
	 * @时间： 2017-05-22 06:16 PM
	 * @创建人：wangl
	 */
	@RequiresPermissions("reconciliation:settleDifferRrecord:edit")
	@RequestMapping(value = "differences/bd/{differId}")
	public String differences(@PathVariable("differId") int differId, 
			                  HttpServletRequest request,
			                  RedirectAttributes redirectAttributes) {


		SettleDifferRecord settleDifferRecord = settleDifferRrecordService.getEntityById(differId);
		logger.info("差异单处理开始--->{补单处理}",settleDifferRecord);
		try {
			// 用户补单
			AsyncMsgModel suppleTrans = billingClearAPIClient.suppleMerchantTrans(
					settleDifferRecord.getPaymentId().toString(), settleDifferRecord.getFeeAmount(),
					settleDifferRecord.getSettleAmountPlan());

			logger.info("差异单处理--->{调取账务返回结果}",suppleTrans);
			//交易返回的状态码
			int merchantStatus = suppleTrans.getStatus();
			/**
			 * 根据和交易系统的返回值处理业务逻辑
			 */
			if (merchantStatus == InterfaceStatus.SUCCESS.getValue()) { // 1000

				//处理返回结果
				transResult(differId,ErrorStatusTranslation.SETTLESTATUSY.getValue());
				logger.info("补单处理结束--->{补单处理 交易返回的状态码}",merchantStatus);
				riskLogsService.savaEntity("补单", "补单处理 交易返回的状态码"+merchantStatus, request);
			}

			if (merchantStatus == InterfaceStatus.BILLNOTPAYMENTERROR.getValue()) { /// 6001
				logger.info("补单处理结束--->{补单处理交易单不存在}");
				addMessage(redirectAttributes, "交易单不存在");
			}
			if (merchantStatus == InterfaceStatus.BILLINSERTREFUNDERROR.getValue()) { // 6002

				//处理返回结果
				transResult(differId,ErrorStatusTranslation.CHANNEL_S.getValue());
				logger.info("补单处理结束--->{生成退款单失败}",merchantStatus);
				addMessage(redirectAttributes, "生成退款单失败");
			} else {
				addMessage(redirectAttributes, suppleTrans.getMsg());
				logger.info("补单处理结束", suppleTrans.getMsg());
			}
		} catch (Exception e) {
			logger.error("补单处理结束，服务调取失败请修复!", e.getMessage());
			addMessage(redirectAttributes, "补单处理结束，服务调取失败请修复!");
		}
		 return "redirect:"+Global.getAdminPath()+"/reconciliation/SettleDifferRrecord?cache=1";
	}

	/**
	 * @方法说明：撤单跳转
	 * @时间： 2017-05-22 06:29 PM
	 * @创建人：wangl
	 */
	@RequiresPermissions("reconciliation:settleDifferRrecord:edit")
	@RequestMapping(value = "differences/addcd/{differId}")
	public String differencesAddCd(@PathVariable("differId") int differId,
								   Model model) {


		//根据id获取到对象，然后翻译成中文
		SettleDifferRecord settleDifferRecord = settleDifferRrecordService.getEntityById(differId);
		//翻译字段
		resultName(settleDifferRecord);
		model.addAttribute("settleDifferRecord", settleDifferRecord);
		logger.info("差异单处理--->{撤单跳转}",settleDifferRecord);
		return "modules/reconciliation/addCd";
	}

	/**
	 * @方法说明：撤单处理
	 * @时间： 2017-05-22 06:16 PM
	 * @创建人：wangl
	 */
	@RequiresPermissions("reconciliation:settleDifferRrecord:edit")
	@RequestMapping(value = "differences/cd/{differId}")
	public String differencesCd(@PathVariable("differId") int differId, 
								HttpServletRequest request,
								RedirectAttributes redirectAttributes) {



		SettleDifferRecord settleDifferRecord = settleDifferRrecordService.getEntityById(differId);
		logger.info("差异单处理--->{撤单处理}",settleDifferRecord);
		try {
			// 用户撤单
			AsyncMsgModel suppleTrans = billingClearAPIClient.cancelMerchantTrans(
					settleDifferRecord.getPaymentId().toString(), settleDifferRecord.getFeeAmount(),
					settleDifferRecord.getSettleAmountPlan());

			logger.info("差异单处理--->{撤单处理}--->{调取账务返回的结果}",suppleTrans);
			int merchantStatus = suppleTrans.getStatus();
			/**
			 * 根据和交易系统的返回值处理业务逻辑
			 */
			if (merchantStatus == InterfaceStatus.SUCCESS.getValue()) { // 1000
				//处理返回结果
				transResult(differId,ErrorStatusTranslation.SETTLESTATUSD.getValue());
				addMessage(redirectAttributes, "撤单处理结束成功");

				logger.info("差异单处理--->{撤单处理结束成功}--->{交易返回的状态码}",merchantStatus);
				riskLogsService.savaEntity("撤单", "撤单处理 交易返回的状态码"+merchantStatus, request);
			}
			if (merchantStatus == InterfaceStatus.BILLNOTPAYMENTERROR.getValue()) { /// 6001
				logger.info("差异单处理--->{撤单处理交易单不存在}");
				addMessage(redirectAttributes, "撤单处理交易单不存在");
			}
			if (merchantStatus == InterfaceStatus.BILLINSERTREFUNDERROR.getValue()) { // 6002
				//处理返回结果
				transResult(differId,ErrorStatusTranslation.CHANNEL_C.getValue());
			} else {
				addMessage(redirectAttributes, suppleTrans.getMsg());
				logger.info("撤单处理结束");
			}
		} catch (Exception e) {
			addMessage(redirectAttributes, "撤单处理结束，服务调取失败请修复!");
			logger.error("撤单处理结束，服务调取失败请修复",e.getMessage());
		}

		 return "redirect:"+Global.getAdminPath()+"/reconciliation/SettleDifferRrecord?cache=1";
	}


	/**
	 * @方法说明：金额不一致跳转
	 * @时间： 2017-05-23 09:32 AM
	 * @创建人：wangl
	 */
	@RequiresPermissions("reconciliation:settleDifferRrecord:edit")
	@RequestMapping(value = "differences/agree/{differId}")
	public String differencesAgree(@PathVariable("differId") int differId,
								   RedirectAttributes redirectAttributes,
								   Model model) {

		//根据id获取到对象，然后翻译成中文
		SettleDifferRecord settleDifferRecord = settleDifferRrecordService.getEntityById(differId);
		resultName(settleDifferRecord);
		model.addAttribute("settleDifferRecord", settleDifferRecord);

		logger.info("差异单处理--->{金额不一致跳转}", settleDifferRecord);
		return "modules/reconciliation/agree";
	}

	/**
	 * @方法说明：处理线下处理(金额不一致处理)
	 * @时间： 2017-05-22 06:30 PM
	 * @创建人：wangl
	 */
	@RequiresPermissions("reconciliation:settleDifferRrecord:edit")
	@RequestMapping(value = "differences/xxcl/{differId}")
	public String differencesQxcy(@PathVariable("differId") int differId, 
								  HttpServletRequest request,
								  RedirectAttributes redirectAttributes) {

		//处理返回结果
		transResult(differId,BillingDifferType.BDTYPEJ.getValue());
		addMessage(redirectAttributes, "差异单处理结束成功");
		riskLogsService.savaEntity("处理线下处理(金额不一致处理)", "差异单处理 ", request);

		logger.info("差异单处理--->{处理线下处理(金额不一致处理)}");
		 return "redirect:"+Global.getAdminPath()+"/reconciliation/SettleDifferRrecord?cache=1";
	}

	/**
	 * @方法说明：其他跳转
	 * @时间： 2017-05-23 09:28 AM
	 * @创建人：wangl
	 */
	@RequiresPermissions("reconciliation:settleDifferRrecord:edit")
	@RequestMapping(value = "differences/qtAdd/{differId}")
	public String differencesQtAdd(@PathVariable("differId") int differId,
								   Model model) {


		//根据id获取到对象，然后翻译成中文
		SettleDifferRecord settleDifferRecord = settleDifferRrecordService.getEntityById(differId);
		//翻译字段
		resultName(settleDifferRecord);

		logger.info("差异单处理--->{其他跳转}", settleDifferRecord);
		// 其他差异备注时的选项
		List<EnumBean> errorNote = Lists.newArrayList();
		for (ErrorNoteType checkFlg : ErrorNoteType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			errorNote.add(ct);
		}
		model.addAttribute("errorNote", errorNote);
		model.addAttribute("settleDifferRecord", settleDifferRecord);
		return "modules/reconciliation/dealResult";
	}

	/**
	 * @方法说明：其他处理
	 * @时间： 2017-05-22 06:30 PM
	 * @创建人：wangl
	 */
	@RequiresPermissions("reconciliation:settleDifferRrecord:edit")
	@RequestMapping(value = "differences/qt/{differId}")
	public String differencesQt(@PathVariable("differId") int differId, 
								HttpServletRequest request,
								RedirectAttributes redirectAttributes,
								SettleDifferRecord settleDifferRecord) {


		SettleDifferRecord settleDiffer = new SettleDifferRecord();
		settleDiffer.setHandleResult(BillingBillStatus.BBSTATUSY.getValue());
		settleDiffer.setOperationDate(new Date());
		settleDiffer.setDifferId((long) differId);
		String handleMessage = settleDifferRecord.getHandleMessage();

		//生成组合名称 “QT,Y”
		settleDiffer.setHandleMessage(BillingDifferType.BDTYPEO.getValue() + "," + handleMessage);
		settleDifferRrecordService.updateTime(settleDiffer);
		logger.info("差异单处理--->{其他处理}", settleDiffer);
		addMessage(redirectAttributes, "差异单处理结束成功");
		riskLogsService.savaEntity("撤单", "差异单处理 ", request);

		 return "redirect:"+Global.getAdminPath()+"/reconciliation/SettleDifferRrecord?cache=1";
	}



	/**
	 * @方法说明：差异单其他类型处理
	 * @时间： 2017-05-23 09:31 AM
	 * @创建人：wangl
	 */
	@RequiresPermissions("reconciliation:settleDifferRrecord:edit")
	@RequestMapping(value = "differences/qt")
	public String differencesQtCss(@RequestParam("differId") String differId,
								   @RequestParam("handleMessage") String handleMessage,
								   RedirectAttributes redirectAttributes) {


		SettleDifferRecord settleDiffer = new SettleDifferRecord();
		settleDiffer.setHandleResult(BillingBillStatus.BBSTATUSY.getValue());
		settleDiffer.setOperationDate(new Date());
		int parseInt = Integer.parseInt(differId);
		settleDiffer.setDifferId((long) parseInt);

		//生成组合名称 “QT,Y”
		settleDiffer.setHandleMessage(BillingDifferType.BDTYPEO.getValue() + "," + handleMessage);
		settleDifferRrecordService.updateTime(settleDiffer);

		logger.info("差异单处理--->{差异单处理结束成功}", settleDiffer);
		addMessage(redirectAttributes, "差异单处理结束成功");

		 return "redirect:"+Global.getAdminPath()+"/reconciliation/SettleDifferRrecord?cache=1";
	}


	/**
	 * @方法说明：查看详情
	 * @时间： 2017-04-11 01:56 PM
	 * @创建人：wangl
	 */
	@RequiresPermissions("reconciliation:settleDifferRrecord:view")
	@RequestMapping(value = "/more/{differId}")
	public String more(@PathVariable(value = "differId") Integer differId,
					   Model model) throws Exception{

	 	SettleDifferRecord settleDifferRecord = settleDifferRrecordService.getEntityById(differId);
		logger.info("差异记录查询查看详情操作--->{查询结果}"+settleDifferRecord);
		resultName(settleDifferRecord);
		model.addAttribute("settleDifferRecord",settleDifferRecord);
		return "modules/reconciliation/settledifferrrecordlog";
	}

	/**
	 * @方法说明：处理支付老数据
	 * @时间： 2017-08-14 15:39
	 * @创建人：wangl
	 */
	@RequiresPermissions("reconciliation:settleDifferRrecord:edit")
	@RequestMapping(value = "differences/updateOldDate/{differId}")
	public String updateOldDate(@PathVariable("differId") long differId,
								SettleDifferRecord settleDifferRecord,
								RedirectAttributes redirectAttributes) {

		settleDifferRecord.setDifferId(differId);
		settleDifferRecord.setHandleResult(BillingBillStatus.BBSTATUSY.getValue());
		settleDifferRecord.setOperationDate(new Date());
		settleDifferRecord.setHandleMessage("BD");
		//将老数据修改状态
		try {
			settleDifferRrecordService.updateTime(settleDifferRecord);
			addMessage(redirectAttributes, "老数据处理成功");
			logger.info("处理支付老数据--->{}",settleDifferRecord);
		} catch (Exception e) {
			addMessage(redirectAttributes, "老数据处理异常");
			logger.info("处理支付老数据--->{异常}--->{}",e);
		}

		return "redirect:"+Global.getAdminPath()+"/reconciliation/SettleDifferRrecord?cache=1";
	}


	/**
	 * @方法说明：处理交易返回结果
	 * @时间： 2017-05-22 06:21 PM
	 * @创建人：wangl
	 */
	private void transResult(long differId,String handleMessage) {
		SettleDifferRecord settleDiffer = new SettleDifferRecord();
		settleDiffer.setHandleResult(BillingBillStatus.BBSTATUSY.getValue());
		settleDiffer.setOperationDate(new Date());
		settleDiffer.setDifferId(differId);
		settleDiffer.setHandleMessage(handleMessage);
		settleDifferRrecordService.updateTime(settleDiffer);
	}


	/**
	 * @方法说明：调账处理查看回执单图片
	 * @时间： 2017-05-23 10:42 AM
	 * @创建人：wangl
	 */
	@ResponseBody
	@RequiresPermissions("user")
	@RequestMapping(value = "/showImg")
	public String checkWebsite(@RequestParam(value = "differId") Integer differId) {

		SettleDifferRecord entityById = settleDifferRrecordService.getEntityById(differId);
		if(StringUtils.isNoneBlank(entityById.getReceiptsPath())){
			String path = entityById.getReceiptsPath();
			// 页面图片加域，将地址设置进参数中方便页面显示
			path = RandomUtil.getFastDfs(path);
			return path;
		}else{
			return "404";
		}
	}


	/**
	 * @方法说明：返回查询条件
	 * @时间： 2017-05-22 05:43 PM
	 * @创建人：wangl
	 */
	private void resultModel(Model model) {
		// 通道业务类型
		List<EnumBean> dataEntityChannelType = ChannelTypeClear.getList();
		model.addAttribute("channelType", dataEntityChannelType);

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
		List<EnumBean> differType = Lists.newArrayList();
		for (BillingDifferType checkFlg : BillingDifferType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			differType.add(ct);
		}
		model.addAttribute("differType", differType);

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
		List<EnumBean> handleMessage = Lists.newArrayList();
		for (ErrorStatus checkFlg : ErrorStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			handleMessage.add(ct);
		}
		model.addAttribute("handleMessage", handleMessage);
	}


	/**
	 * @方法说明：翻译显示的字段
	 * @时间： 2017-05-22 05:07 PM
	 * @创建人：wangl
	 */
	private void resultName(SettleDifferRecord settleDifferRecord) {


		// TransType
		if (StringUtils.isNotBlank(settleDifferRecord.getTransType())) {
			settleDifferRecord.setTransType(TransType.labelOf(settleDifferRecord.getTransType()));
		}
		//手续费扣除方式
		if(StringUtils.isNotBlank(settleDifferRecord.getFeeWay())){
			settleDifferRecord.setFeeWay(ChargeDeductType.labelOf(settleDifferRecord.getFeeWay()));
		}

		//银行卡类型 bankcard_type
		if(StringUtils.isNotBlank(settleDifferRecord.getBankcardType())){
			settleDifferRecord.setBankcardType(BankcardType.labelOf(settleDifferRecord.getBankcardType()));
		}

		//支付类型 pay_type
		if(StringUtils.isNotBlank(settleDifferRecord.getPayType())){
			settleDifferRecord.setPayType(PayTypeSettle.labelOf(settleDifferRecord.getPayType()));
		}

		//业务类型（交易类型）(product表code字段)
		if(StringUtils.isNotBlank(settleDifferRecord.getProductCode())){
			settleDifferRecord.setProductCode(ProductType.labelOf(settleDifferRecord.getProductCode()));
		}

		// ChannelType
		if (StringUtils.isNotBlank(settleDifferRecord.getChannelType())) {
			settleDifferRecord.setChannelType(ChannelTypeClear.labelOf(settleDifferRecord.getChannelType()));
		}

		// DifferType
		if (StringUtils.isNotBlank(settleDifferRecord.getDifferType())) {
			settleDifferRecord.setDifferType(BillingDifferType.labelOf(settleDifferRecord.getDifferType()));
		}
		// HandleResult
		if (StringUtils.isNotBlank(settleDifferRecord.getHandleResult())) {
			settleDifferRecord.setHandleResult(BillingBillStatus.labelOf(settleDifferRecord.getHandleResult()));
		}

		// trans_status
		if (StringUtils.isNotBlank(settleDifferRecord.getTransStatus())) {
			settleDifferRecord.setTransStatus(SettleDifferTransStatus.labelOf(settleDifferRecord.getTransStatus()));
		}
		// is_bill
		if (StringUtils.isNotBlank(settleDifferRecord.getIsBill())) {
			settleDifferRecord.setIsBill(ClearingCheckStatus.labelOf(settleDifferRecord.getIsBill()));
		}
		// is_profit
		if (StringUtils.isNotBlank(settleDifferRecord.getIsProfit())) {
			settleDifferRecord.setIsProfit(SettleDifferIsProfit.labelOf(settleDifferRecord.getIsProfit()));
		}

		// HandleMessage
		if (StringUtils.isNotBlank(settleDifferRecord.getHandleMessage())) {
			handleMessage(settleDifferRecord);
		}

	}


	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<SettleDifferRecord> differRecordPage(Page<SettleDifferRecord> page, SettleDifferRecord entity) {
		entity.setPage(page);
		page.setList(settleDifferRrecordService.differRecordPage(entity));
		return page;
	}


	/**
	 * @方法说明：根据组合handleMessage组合拆分
	 * @时间： 2017-05-23 10:42 AM
	 * @创建人：wangl
	 */
	private void handleMessage(SettleDifferRecord settleDifferRecord) {
		// HandleMessage
		if (StringUtils.isNotBlank(settleDifferRecord.getHandleMessage())) {

			String checkFlg = settleDifferRecord.getHandleMessage();
			String stringarray[] = checkFlg.split(",");
			int length = stringarray.length;
			/**
			 * 因为HandleMessage是一个组合命名，QT,Y 所以要拆分翻译
			 */
			if (length == 2) {
				// 获取出组合命名的数据
				String handleMessagePath = ErrorStatusTranslation.labelOf(stringarray[0]) + "-"
						+ ErrorNoteType.labelOf(stringarray[1]);
				settleDifferRecord.setHandleMessage(handleMessagePath);
			} else {
				// 获取出单个命名的数据
				settleDifferRecord.setHandleMessage(ErrorStatusTranslation.labelOf(settleDifferRecord.getHandleMessage()));
			}
		}
	}
}
