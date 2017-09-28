package com.heepay.manage.modules.reconciliation.web.adjustMoney;

import com.google.common.collect.Lists;
import com.heepay.common.util.FastDFSUtils;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.CurrencyType;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.*;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferRecord;
import com.heepay.manage.modules.reconciliation.service.SettleDifferRecordService;
import com.heepay.manage.modules.reconciliation.util.ChannelTypeClear;
import com.heepay.manage.modules.reconciliation.web.rpc.client.AdjustMoneyClient;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import com.heepay.manage.modules.settle.service.ClearingMerchantRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/adjustMoney/settleDifferRrecord")
public class SettleDifferRrecordAdjustMoney extends BaseController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SettleDifferRecordService settleDifferRrecordService;

	@Autowired
	private AdjustMoneyClient adjustMoneyClient;

	//商户清算表
	@Autowired
	private ClearingMerchantRecordService clearingMerchantRecordService;

	@RequiresPermissions("adjustMoney:settleDifferRrecord:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleDifferRecord settleDifferRecord,
					   HttpServletRequest request,
					   HttpServletResponse response,
					   Model model) {

		//使用cookie保存查询条件
		settleDifferRecord = (SettleDifferRecord)SaveConditions.result(settleDifferRecord, "settleDifferRecordAdjustMoney", request, response);

		String adjust = request.getParameter("adjust");
		if(StringUtils.isNoneBlank(adjust)){
			model.addAttribute("adjust", adjust);
		}
		//格式化查询时间条件
		Date endCheckTime = settleDifferRecord.getEndCheckTime();
		if(endCheckTime!=null){
			String format = new SimpleDateFormat("yyyy-MM-dd").format(endCheckTime);
			format=format+" 23:59:59";
			try {
				Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
				settleDifferRecord.setEndCheckTime(time);
			} catch (ParseException e) {
				logger.error("调账管理--->{时间转换异常}"+e.getMessage());
			}
		}

		//保存查询条件
		settleDifferRecord.setDifferType(BillingDifferType.BDTYPEO.getValue());//QT
		settleDifferRecord.setHandleResult(BillingBillStatus.BBSTATUSD.getValue());//D
		settleDifferRecord.setSettleBath("GZ%");
		Page<SettleDifferRecord> page = getListByPage(new Page<>(request, response), settleDifferRecord);

		logger.info("调账管理--->{查询}--->{结果}" + page.getList());

		try {
			//前台页面搜索条件的名称汇总
			List<SettleChannelManager> list = settleDifferRrecordService.getBatchName();
			// 根据getBatchName方法去通道管理表查询通道名称有没有数据，如果没有就不让其显示可添加的页面
			// 通道合作方
			if (null != list) {
				List<SettleDifferRecord> clearingList = Lists.newArrayList();
				for (SettleChannelManager settleChannelManager : list) {
					SettleDifferRecord settleRule = new SettleDifferRecord();
					settleRule.setChannelCode(settleChannelManager.getChannelCode());
					settleRule.setChannelName(settleChannelManager.getChannelName());
					clearingList.add(settleRule);
					logger.info("调账管理--->{查询}--->{结果}"+settleChannelManager);
				}
				model.addAttribute("checklist", clearingList);
			}
		} catch (Exception e) {
			logger.error("调账管理--->{没有获取到名称}"+e.getMessage());
		}

		for (SettleDifferRecord clearingCR : page.getList()) {
			//找出符合条件的 交易类型，并且差异类型 为其他差异的数据
			// 充值																 // 消费															   // 提现，转账,退款																							//转账															//退款
			if (clearingCR.getTransType().equals(TransType.CHARGE.getValue()) || clearingCR.getTransType().equals(TransType.PAY.getValue()) || clearingCR.getTransType().equals(TransType.WITHDRAW.getValue()) || clearingCR.getTransType().equals(TransType.BATCHPAY.getValue()) || clearingCR.getTransType().equals(TransType.REFUND.getValue())) {
				if (BillingDifferType.BDTYPEO.getValue().equals(clearingCR.getDifferType())) {
					clearingCR.setQT("QT");
					//翻译字段名称
					resultName(clearingCR);
				}
			}
		}

		// 通道业务类型
		List<EnumBean> dataEntityChannelType = ChannelTypeClear.getList();
		model.addAttribute("checkTypeList", dataEntityChannelType);
		
		// 交易类型
		List<EnumBean> transType = Lists.newArrayList();
		for (TransType checkFlg : TransType.values()) {
			if (checkFlg.getValue().equals(TransType.CHARGE.getValue())
				|| checkFlg.getValue().equals(TransType.PAY.getValue())
				|| checkFlg.getValue().equals(TransType.WITHDRAW.getValue())
				|| checkFlg.getValue().equals(TransType.BATCHPAY.getValue())
				|| checkFlg.getValue().equals(TransType.REFUND.getValue())) {

				EnumBean ct = new EnumBean();
				ct.setValue(checkFlg.getValue());
				ct.setName(checkFlg.getContent());
				transType.add(ct);
			}

		}
		model.addAttribute("transType", transType);

		model.addAttribute("page", page);
		model.addAttribute("settleDifferRecord", settleDifferRecord);
		
		logger.info("操作结束------->",settleDifferRecord);
		return "modules/reconciliation/adjustMoney/settleDifferRecordAdjustMoney";
	}


	// 其他差异跳转的处理方法
	@RequiresPermissions("adjustMoney:settleDifferRrecord:edit")
	@RequestMapping(value = "/qt/{differId}")
	public String differencesQtAdd(@PathVariable(value = "differId") int differId,
								   RedirectAttributes redirectAttributes,
								   HttpServletRequest request,
								   Model model) {

		logger.info("调账管理--->{差异单处理}--->{条件}"+redirectAttributes);

		//根据id获取到对象，然后翻译成中文
		SettleDifferRecord settleDifferRecord = settleDifferRrecordService.getEntityById(differId);
		resultName(settleDifferRecord);

		// 其他差异备注时的选项
		List<EnumBean> errorNote = Lists.newArrayList();
		for (ErrorNoteType checkFlg : ErrorNoteType.values()) {
			if(ErrorNoteType.CHANNEL_Y.getValue().equals(checkFlg.getValue()) || ErrorNoteType.CHANNEL_N.getValue().equals(checkFlg.getValue())){
				EnumBean ct = new EnumBean();
				ct.setValue(checkFlg.getValue());
				ct.setName(checkFlg.getContent());
				errorNote.add(ct);
			}
		}
		logger.info("调账管理--->{差异单处理结束}");
		model.addAttribute("errorNote", errorNote);
		model.addAttribute("settleDifferRecord", settleDifferRecord);

		return "modules/reconciliation/adjustMoney/dealResultAdjustMoney";
	}


	@RequiresPermissions("adjustMoney:settleDifferRrecord:edit")
	@RequestMapping(value = "/save/{differId}")
	public String save(@PathVariable(value = "differId") int differId,
					   @RequestParam(value = "receiptsPathHand", required = false) MultipartFile receiptsPathHand,
					   RedirectAttributes redirectAttributes,
					   SettleDifferRecord settleDiffer) {


		String handleMessage = settleDiffer.getHandleMessage();
		//通道无记录的选择
		if(handleMessage.equals(ErrorNoteType.CHANNEL_Y.getValue())){
			//通道有记录的选择
			try {
				String pathImg = upLoadIMG(receiptsPathHand);
				//保存图片差错
				settleDiffer.setReceiptsPath(pathImg);
			} catch (Exception e) {
				logger.error("调账管理--->{图片上传异常}"+e.getMessage());
				addMessage(redirectAttributes, "调账处理异常");
			}
		}

		//设置操作时间
		settleDiffer.setOperationDate(new Date());
		//生成组合名称 “QT,Y”
		settleDiffer.setHandleMessage(BillingDifferType.BDTYPEO.getValue() + "," + handleMessage);
		settleDifferRrecordService.updateTime(settleDiffer);

		SettleDifferRecord entityById = settleDifferRrecordService.getEntityById(differId);
		//消费
		if (entityById.getTransType().equals(TransType.PAY.getValue())) {
			ClearingMerchantRecord clearingMerchantRecord = clearingMerchantRecordService.getEntity(entityById.getTransType(),entityById.getTransNo());
			if(null != clearingMerchantRecord){
				entityById.setSettleCyc(clearingMerchantRecord.getSettleCyc());//订单结算周期
			}
		}
		entityById.setHandleMessage(handleMessage);
		entityById.setCurrency(CurrencyType.RMB.getValue());//默认使用人民币
		JsonMapperUtil json = new JsonMapperUtil();
		String value = json.toJson(entityById);

		logger.info("调账管理--->{其他差异调账处理}--->{调取清结算}"+adjustMoneyClient);
		String result = adjustMoneyClient.adjustMoney(value);

		logger.info("调账管理--->{其他差异调账处理}--->{调取清返回结果}"+result);
		Map map = json.fromJson(result, Map.class);
		if(null != map && !map.isEmpty()){
			String flag = (String)map.get("flag");
			if(StringUtils.isNoneBlank(flag)){
				//1000,"成功"											//1006,"已记账"
				if(flag.equals(InterfaceStatus.SUCCESS.getValue()+"") || flag.equals(InterfaceStatus.ALREADY.getValue()+"")){
					SettleDifferRecord differRecord = new SettleDifferRecord();

					differRecord.setDifferId(entityById.getDifferId());
					differRecord.setHandleResult("Y");
					differRecord.setRemark((String)map.get("remark"));
					settleDifferRrecordService.updateTime(differRecord);
					logger.info("调账管理--->{调账处理成功}--->{返回结果}",map.get("remark"));
					addMessage(redirectAttributes, "调账处理成功!");
				}else {
					addMessage(redirectAttributes, "调账处理失败!");
				}

			}else {
				addMessage(redirectAttributes, "调取清结算异常!");
			}
		}else{
			addMessage(redirectAttributes, "调取清结算异常!");
		}

		return "redirect:"+ Global.getAdminPath()+"/adjustMoney/settleDifferRrecord?cache=1";
	}



	public Page<SettleDifferRecord> getListByPage(Page<SettleDifferRecord> page, SettleDifferRecord entity) {
		entity.setPage(page);
		page.setList(settleDifferRrecordService.getListByPage(entity));
		return page;
	}

	public String upLoadIMG(MultipartFile file) throws Exception {
		if(!file.isEmpty()) {
			return FastDFSUtils.uploadPic(file.getBytes(), file.getOriginalFilename(), file.getSize());
		}
		return "";
	}

	// 根据组合handleMessage组合拆分
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
				String handleMessagePath = ErrorStatusTranslation.labelOf(stringarray[0]) + "-" + ErrorNoteType.labelOf(stringarray[1]);
				settleDifferRecord.setHandleMessage(handleMessagePath);
			} else {
				// 获取出单个命名的数据
				settleDifferRecord.setHandleMessage(ErrorStatusTranslation.labelOf(settleDifferRecord.getHandleMessage()));
			}
		}
	}

	private void resultName(SettleDifferRecord clearingCR) {

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
		handleMessage(clearingCR);
	}

	public void fileIO(final HttpServletResponse response, BufferedImage image, String suffix){
		try {
			response.setContentType("image/jpeg");
			response.setDateHeader("expries", -1);
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			ImageIO.write(image,suffix, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
