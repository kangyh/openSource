package com.heepay.manage.modules.pbc.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.heepay.enums.pbc.*;
import com.heepay.manage.common.pbc.ResultType;
import com.heepay.manage.common.utils.EnumBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.Constants;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.pbc.entity.PbcAccountDetail;
import com.heepay.manage.modules.pbc.entity.PbcAccountDynamicQuery;
import com.heepay.manage.modules.pbc.entity.PbcAccountInfo;
import com.heepay.manage.modules.pbc.entity.PbcAttachmentQuery;
import com.heepay.manage.modules.pbc.entity.PbcBankInfo;
import com.heepay.manage.modules.pbc.entity.PbcFeedBack;
import com.heepay.manage.modules.pbc.entity.PbcMeasureInfo;
import com.heepay.manage.modules.pbc.entity.PbcPaymentAccountBack;
import com.heepay.manage.modules.pbc.entity.PbcPaymentAccountQuery;
import com.heepay.manage.modules.pbc.entity.PbcQueryInfo;
import com.heepay.manage.modules.pbc.entity.PbcReleaseFeedback;
import com.heepay.manage.modules.pbc.entity.PbcTransCardPaymentAccount;
import com.heepay.manage.modules.pbc.entity.PbcTransDetailQuery;
import com.heepay.manage.modules.pbc.entity.PbcTransInfo;
import com.heepay.manage.modules.pbc.entity.PbcTransSerialCardPaymentAccountQuery;
import com.heepay.manage.modules.pbc.service.PbcAccountDetailService;
import com.heepay.manage.modules.pbc.service.PbcAccountDynamicQueryService;
import com.heepay.manage.modules.pbc.service.PbcAccountInfoService;
import com.heepay.manage.modules.pbc.service.PbcAttachmentQueryService;
import com.heepay.manage.modules.pbc.service.PbcBankInfoService;
import com.heepay.manage.modules.pbc.service.PbcFeedBackService;
import com.heepay.manage.modules.pbc.service.PbcMeasureInfoService;
import com.heepay.manage.modules.pbc.service.PbcPaymentAccountBackService;
import com.heepay.manage.modules.pbc.service.PbcPaymentAccountQueryService;
import com.heepay.manage.modules.pbc.service.PbcQueryInfoService;
import com.heepay.manage.modules.pbc.service.PbcReleaseFeedbackService;
import com.heepay.manage.modules.pbc.service.PbcTransCardPaymentAccountService;
import com.heepay.manage.modules.pbc.service.PbcTransDetailQueryService;
import com.heepay.manage.modules.pbc.service.PbcTransInfoService;
import com.heepay.manage.modules.pbc.service.PbcTransSerialCardPaymentAccountQueryService;
import com.heepay.manage.modules.pbc.web.LoadEntity.getLoad;
import com.heepay.manage.modules.pbc.web.rpc.client.DynamicServiceRpcClient;
import com.heepay.manage.modules.pbc.web.rpc.client.PrecCashFlowServiceRpcClient;
import com.heepay.manage.modules.pbc.web.rpc.client.SubjectDetailServiceRpcClient;
import com.heepay.manage.modules.pbc.web.rpc.client.TranDetailServiceRpcClient;
import com.heepay.manage.modules.pbc.web.rpc.client.TransNumberServiceRpcClient;
import com.heepay.manage.modules.riskLogs.service.RiskLogsService;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
 * 
 *
 * 描 述：电信反诈骗交易处理（反馈表）
 *
 * 创 建 者： wangdong 创建时间：2016年12月16日 下午7:50:19 创建描述：
 *
 * 修 改 者： 修改时间： 修改描述：
 *
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/pbc/pbcFeedBackQueryDealQuery")
public class PbcFeedbackQueryDealController extends BaseController {

	private static final Logger log = LogManager.getLogger();

	@Autowired
	private PbcFeedBackService pbcFeedBackService;

	@Autowired
	private PbcQueryInfoService pbcQueryInfoService;// 查询信息主表信息
	@Autowired
	private PbcTransDetailQueryService pbcTransDetailQueryService;// 支付账户交易明细查询
	@Autowired
	private PbcAccountDynamicQueryService pbcAccountDynamicQueryService;// 账户动态查询
	@Autowired
	private PbcPaymentAccountQueryService pbcPaymentAccountQueryService;// 关联全支付账号查询
	@Autowired
	private PbcTransSerialCardPaymentAccountQueryService pbcTransSerialCardPaymentAccountQueryService;// 按交易流水号查询银行卡/支付帐号查询

	@Autowired
	private PbcReleaseFeedbackService pbcReleaseFeedbackService;// 账户动态解除
	@Autowired
	private PbcTransCardPaymentAccountService pbcTransCardPaymentAccountService;// 按交易流水号查询银行卡/支付账号

	@Autowired
	private PbcAttachmentQueryService pbcAttachmentQueryService;

	@Autowired
	DynamicServiceRpcClient dynamicServiceRpcClient; // 动态查询反馈 和 动态查询解除反馈

	@Autowired
	PrecCashFlowServiceRpcClient precCashFlowServiceRpcClient; // 账户资金流动查询反馈

	@Autowired
	SubjectDetailServiceRpcClient subjectDetailServiceRpcClient; // 账户主体查询反馈

	@Autowired
	TranDetailServiceRpcClient tranDetailServiceRpcClient; // 账户交易明细查询反馈

	@Autowired
	TransNumberServiceRpcClient transNumberServiceRpcClient; // 按交易流水号查询银行卡/支付帐号反馈

	@Autowired
	private PbcAccountInfoService pbcAccountInfoService;// 账户信息
	@Autowired
	private PbcTransInfoService pbcTransInfoService;// 交易信息
	@Autowired
	private PbcAccountDetailService pbcAccountDetailService;// 账户主体详情
	@Autowired
	private PbcPaymentAccountBackService pbcPaymentAccountBackService;// 关联全支付账号
	@Autowired
	private PbcMeasureInfoService pbcMeasureInfoService;// 措施信息
	@Autowired
	private PbcBankInfoService pbcBankInfoService;// 银行信息
	
	@Autowired
	private RiskLogsService riskLogsService;

	/**
	 * @方法说明：查询反馈表都是未处理的或者上报失败的List
	 * @时间：2016年9月19日 @创建人：wangdong
	 */
	@RequiresPermissions("pbc:pbcFeedBackQueryDeal:view")
	@RequestMapping(value = { "list", "" })
	public String list(PbcFeedBack pbcFeedBack, HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		try {
			// 电信诈骗交易处理页面查询的数据都是未处理的或者上报失败的
			pbcFeedBack.setQueryFlg(Constants.Clear.CHECK_STATUS_N);
			model = pbcFeedBackService.findPbcFeedBackPage(new Page<PbcFeedBack>(request, response), pbcFeedBack,
					model);
			return "modules/pbc/pbcFeedBackQueryDealList";
		} catch (Exception e) {
			log.error("PbcFeedbackQueryDealController list has a error:{查询反馈表都是未处理的或者上报失败的List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @throws Exception
	 * @方法说明：反馈表信息查看和处理信息的查询
	 * @时间：2016年12月17日 下午5:21:51
	 * @创建人：wangdong
	 */
	public void showPbcQueryInfo(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		try {
			String feedBackId = request.getParameter("feedBackId");
			String param = request.getParameter("param");
			PbcFeedBack pbcFeedBack = new PbcFeedBack();
			PbcQueryInfo pbcQueryInfo = new PbcQueryInfo();
			if (StringUtils.isNotBlank(feedBackId)) {
				// 获取反馈信息
				pbcFeedBack = pbcFeedBackService.getEntityById(Long.valueOf(feedBackId));
				if (StringUtils.isNotBlank(pbcFeedBack.getApplicationId())) {
					// 获取查询主表信息根据业务申请编码
					pbcQueryInfo = pbcQueryInfoService.getEntityByApplicationId(pbcFeedBack.getApplicationId());
					//案件类型
					if(StringUtils.isNotBlank(pbcQueryInfo.getCaseType())){
						pbcQueryInfo.setCaseType(PbcCaseType.labelOf(pbcQueryInfo.getCaseType()));
					}
					if(StringUtils.isNotBlank(pbcQueryInfo.getDataType())){
						pbcQueryInfo.setDataType(PbcStaticParamType.labelOf(pbcQueryInfo.getDataType()));
					}
					//经办人证件类型
					if(StringUtils.isNotBlank(pbcQueryInfo.getOperatorIdType())){
						pbcQueryInfo.setOperatorIdType(PbcCertificateType.labelOf(pbcQueryInfo.getOperatorIdType()));
					}
					//协查人证件类型
					if(StringUtils.isNotBlank(pbcQueryInfo.getInvestigatorIdType())){
						pbcQueryInfo.setInvestigatorIdType(PbcCertificateType.labelOf(pbcQueryInfo.getInvestigatorIdType()));
					}
					model.addAttribute("pbcQueryInfo", pbcQueryInfo);
				}
			}
			// 判断哪个请求事件的信息查看
			if (StringUtils.equals(pbcFeedBack.getRequestEventType(), RequestEventType.CASE_REPORT.getValue())) {
				// 支付账户交易明细查询
				PbcTransDetailQuery pbcTransDetailQuery = pbcTransDetailQueryService
						.getQueryInfoId(pbcQueryInfo.getApplicationId());
				if(null != pbcTransDetailQuery){
					if(StringUtils.isNotBlank(pbcTransDetailQuery.getDataType())){
						pbcTransDetailQuery.setDataType(PbcStaticParamType.labelOf(pbcTransDetailQuery.getDataType()));
					}
					if(StringUtils.isNotBlank(pbcTransDetailQuery.getSubjectType())){
						pbcTransDetailQuery.setSubjectType(PbcQueryDetailType.labelOf(pbcTransDetailQuery.getSubjectType()));
					}
				}
				model.addAttribute("pbcTransDetailQuery", pbcTransDetailQuery);
				request.setAttribute("title", "支付账户交易明细查询");
				request.setAttribute("feedback", "账户交易明细查询反馈");
				request.setAttribute("feedBackId", feedBackId);
				model.addAttribute("flg", "transAccount");
				model.addAttribute("hidden", param);
			} else if (StringUtils.equals(pbcFeedBack.getRequestEventType(),
					RequestEventType.ABNORMAL_ACCOUNTS.getValue())) {
				// 账户主体详情查询
				// 信息在主表中
				request.setAttribute("title", "账户主体详情查询");
				request.setAttribute("feedback", "账户主体详情查询反馈");
				request.setAttribute("feedBackId", feedBackId);
				model.addAttribute("flg", "accountDetail");
				model.addAttribute("hidden", param);
			} else if (StringUtils.equals(pbcFeedBack.getRequestEventType(),
					RequestEventType.ACCOUNTS_INVOLVED.getValue())) {
				// 账户动态查询
				PbcAccountDynamicQuery pbcAccountDynamicQuery = pbcAccountDynamicQueryService
						.getQueryInfoId(pbcQueryInfo.getApplicationId());
				if(null != pbcAccountDynamicQuery){
					if(StringUtils.isNotBlank(pbcAccountDynamicQuery.getDataType())){
						pbcAccountDynamicQuery.setDataType(PbcDynamicParamType.labelOf(pbcAccountDynamicQuery.getDataType()));
					}
					if(StringUtils.isNotBlank(pbcAccountDynamicQuery.getSubjectType())){
						pbcAccountDynamicQuery.setSubjectType(PbcQueryDetailType.labelOf(pbcAccountDynamicQuery.getSubjectType()));
					}
					if(StringUtils.isNotBlank(pbcAccountDynamicQuery.getIdType())){
						pbcAccountDynamicQuery.setIdType(PbcCertificateType.labelOf(pbcAccountDynamicQuery.getIdType()));
					}
				}
				model.addAttribute("pbcAccountDynamicQuery", pbcAccountDynamicQuery);
				request.setAttribute("title", "账户动态查询");
				request.setAttribute("feedback", "账户动态查询反馈");
				model.addAttribute("flg", "transAccount");
				request.setAttribute("feedBackId", feedBackId);
				model.addAttribute("hidden", param);
			} else if (StringUtils.equals(pbcFeedBack.getRequestEventType(),
					RequestEventType.EXCEPTION_EVENTS.getValue())) {
				// 账户动态查询解除
				// 信息在主表中
				request.setAttribute("title", "账户动态查询解除");
				request.setAttribute("feedback", "账户动态查询解除反馈");
				request.setAttribute("feedBackId", feedBackId);
				model.addAttribute("flg", "remove");
				model.addAttribute("hidden", param);
			} else if (StringUtils.equals(pbcFeedBack.getRequestEventType(),
					RequestEventType.TRANSNO_ACCOUNT.getValue())) {
				// 按交易流水号查询银行卡/支付帐号
				PbcTransSerialCardPaymentAccountQuery pbcTransSerialCardPaymentAccountQuery = pbcTransSerialCardPaymentAccountQueryService
						.getQueryInfoId(pbcQueryInfo.getApplicationId());
				if(null != pbcTransSerialCardPaymentAccountQuery){
					if(StringUtils.isNotBlank(pbcTransSerialCardPaymentAccountQuery.getDataType())){
						pbcTransSerialCardPaymentAccountQuery.setDataType(PbcTransCardParamType.labelOf(pbcTransSerialCardPaymentAccountQuery.getDataType()));
					}
				}
				model.addAttribute("pbcTransSerialCardPaymentAccountQuery", pbcTransSerialCardPaymentAccountQuery);
				request.setAttribute("title", "按交易流水号查询银行卡/支付帐号");
				request.setAttribute("feedback", "按交易流水号查询银行卡/支付帐号反馈");
				model.addAttribute("flg", "bankCark");
				request.setAttribute("feedBackId", feedBackId);
				model.addAttribute("hidden", param);
			} else if (StringUtils.equals(pbcFeedBack.getRequestEventType(),
					RequestEventType.PAYMENY_ACCOUNT.getValue())) {
				// 关联全支付账号查询
				PbcPaymentAccountQuery pbcPaymentAccountQuery = pbcPaymentAccountQueryService
						.getQueryInfoId(pbcQueryInfo.getApplicationId());
				if(null != pbcPaymentAccountQuery){
					if(StringUtils.isNotBlank(pbcPaymentAccountQuery.getDataType())){
						pbcPaymentAccountQuery.setDataType(PbcAccountParamType.labelOf(pbcPaymentAccountQuery.getDataType()));
					}
					if(StringUtils.isNotBlank(pbcPaymentAccountQuery.getSubjectType())){
						pbcPaymentAccountQuery.setSubjectType(PbcQueryDetailType.labelOf(pbcPaymentAccountQuery.getSubjectType()));
					}
					if(StringUtils.isNotBlank(pbcPaymentAccountQuery.getAccountOwnerIdType())){
						pbcPaymentAccountQuery.setAccountOwnerIdType(PbcCertificateType.labelOf(pbcPaymentAccountQuery.getAccountOwnerIdType()));
					}
				}
				model.addAttribute("pbcPaymentAccountQuery", pbcPaymentAccountQuery);
				request.setAttribute("title", "关联全支付账号查询");
				request.setAttribute("feedback", "关联全支付账号查询反馈");
				model.addAttribute("flg", "paymenyId");
				request.setAttribute("feedBackId", feedBackId);
				model.addAttribute("hidden", param);
			}
			
			//上报状态
			if(StringUtils.isNotBlank(pbcFeedBack.getStatus())){
				pbcFeedBack.setStatus(ReportStatus.labelOf(pbcFeedBack.getStatus()));
			}
			//上报失败原因
			//参数类型
			//风控审核状态
			if(StringUtils.isNotBlank(pbcFeedBack.getRiskStatus())){
				pbcFeedBack.setRiskStatus(RiskAuditStatus.labelOf(pbcFeedBack.getRiskStatus()));
			}
			model.addAttribute("pbcFeedBack", pbcFeedBack);
		} catch (Exception e) {
			log.error("PbcFeedbackQueryDealController showPbcQueryInfo has a error:{反馈表信息查看和处理信息的查询错误 FIND_ERROR}",
					e);
			throw new Exception(e);
		}
	}

	/**
	 * @方法说明：反馈表信息处理页面跳转 
	 * @时间：2016年11月18日18:55:48
	 * @创建人：wangdong
	 */
	@RequiresPermissions("pbc:pbcFeedBackQueryDeal:edit")
	@RequestMapping(value = "deal")
	public String deal(RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		try {
			// 显示要查询的信息
			showPbcQueryInfo(request, response, model);
			List<EnumBean> yList = Lists.newArrayList();
			//业务应答码
			for (ResultType type : ResultType.values()) {
				EnumBean ct = new EnumBean();
				ct.setValue(type.getValue());
				ct.setName(type.getContent());
				yList.add(ct);
			}
			model.addAttribute("yList", yList);
			return "modules/pbc/pbcFeedBackQueryDeal";
		} catch (Exception e) {
			log.error("PbcFeedbackQueryDealController deal has a error:{反馈表信息处理错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @方法说明：获取消息体的信息
	 * @时间：2016年12月19日 下午6:01:27
	 * @创建人：wangdong
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "getBodyInfo")
	public String getPbcAccountTransDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String applicationId = request.getParameter("applicationId");
			String requestEventType = request.getParameter("requestEventType");
			JsonMapperUtil jsonType = new JsonMapperUtil();
			if (StringUtils.isNotBlank(applicationId) && StringUtils.isNotBlank(requestEventType)) {
				// 判断哪个请求事件的信息查看
				if (StringUtils.equals(requestEventType, RequestEventType.CASE_REPORT.getValue())) {
					// 支付账户交易明细查询
					PbcAccountInfo pbcAccountInfo = new PbcAccountInfo();
					pbcAccountInfo.setApplicationCode(applicationId);
					List<PbcAccountInfo> pbcAccountInfoList = pbcAccountInfoService.findList(pbcAccountInfo);
					//翻译字段
					for(PbcAccountInfo pbc : pbcAccountInfoList){
						//支付账号类型
						if(StringUtils.isNotBlank(pbc.getSubjectType())){
							pbc.setSubjectType(PbcAccountType.labelOf(pbc.getSubjectType()));
						}
						//账户类型
						if(StringUtils.isNotBlank(pbc.getAccountType())){
							pbc.setAccountType(PbcTransDetailAccountType.labelOf(pbc.getAccountType()));
						}
						//账户状态
						if(StringUtils.isNotBlank(pbc.getAccountStatus())){
							pbc.setAccountStatus(PbcAccountStatus.labelOf(pbc.getAccountStatus()));
						}
						//币种
						if(StringUtils.isNotBlank(pbc.getCurrency())){
							pbc.setCurrency(PbcCurrencyType.labelOf(pbc.getCurrency()));
						}
					}
					return jsonType.toJson(pbcAccountInfoList);
				} else if (StringUtils.equals(requestEventType, RequestEventType.ABNORMAL_ACCOUNTS.getValue())) {
					// 账户主体详情查询
					PbcAccountDetail pbcAccountDetail = new PbcAccountDetail();
					pbcAccountDetail.setApplicationCode(applicationId);
					List<PbcAccountDetail> pbcAccountDetailList = pbcAccountDetailService.findList(pbcAccountDetail);
					for(PbcAccountDetail pbc : pbcAccountDetailList){
						//支付账号类别
						if(StringUtils.isNotBlank(pbc.getSubjectType())){
							pbc.setSubjectType(PbcQueryDetailType.labelOf(pbc.getSubjectType()));
						}
						//数据类型
						if(StringUtils.isNotBlank(pbc.getBindingDataType())){
							pbc.setBindingDataType(PbcBindingDataType.labelOf(pbc.getBindingDataType()));
						}
					}
					return jsonType.toJson(pbcAccountDetailList);
				} else if (StringUtils.equals(requestEventType, RequestEventType.ACCOUNTS_INVOLVED.getValue())) {
					// 账户动态查询
					PbcAccountInfo pbcAccountInfo = new PbcAccountInfo();
					pbcAccountInfo.setApplicationCode(applicationId);
					List<PbcAccountInfo> pbcAccountInfoList = pbcAccountInfoService.findList(pbcAccountInfo);
					for(PbcAccountInfo pbc : pbcAccountInfoList){
						//支付账号类型
						if(StringUtils.isNotBlank(pbc.getSubjectType())){
							pbc.setSubjectType(PbcAccountType.labelOf(pbc.getSubjectType()));
						}
						//账户类型
						if(StringUtils.isNotBlank(pbc.getAccountType())){
							pbc.setAccountType(PbcTransDetailAccountType.labelOf(pbc.getAccountType()));
						}
						//账户状态
						if(StringUtils.isNotBlank(pbc.getAccountStatus())){
							pbc.setAccountStatus(PbcAccountStatus.labelOf(pbc.getAccountStatus()));
						}
						//币种
						if(StringUtils.isNotBlank(pbc.getCurrency())){
							pbc.setCurrency(PbcCurrencyType.labelOf(pbc.getCurrency()));
						}
					}
					return jsonType.toJson(pbcAccountInfoList);
				} else if (StringUtils.equals(requestEventType, RequestEventType.EXCEPTION_EVENTS.getValue())) {
					// 账户动态查询解除
					PbcReleaseFeedback pbcReleaseFeedback = new PbcReleaseFeedback();
					pbcReleaseFeedback.setApplicationCode(applicationId);
					List<PbcReleaseFeedback> pbcReleaseFeedbackList = pbcReleaseFeedbackService.findList(pbcReleaseFeedback);
					return jsonType.toJson(pbcReleaseFeedbackList);
				} else if (StringUtils.equals(requestEventType, RequestEventType.TRANSNO_ACCOUNT.getValue())) {
					// 按交易流水号查询银行卡/支付帐号
					PbcTransCardPaymentAccount pbcTransCardPaymentAccount = new PbcTransCardPaymentAccount();
					pbcTransCardPaymentAccount.setApplicationCode(applicationId);
					List<PbcTransCardPaymentAccount> pbcTransCardPaymentAccountList = pbcTransCardPaymentAccountService.findList(pbcTransCardPaymentAccount);
					for(PbcTransCardPaymentAccount pbc : pbcTransCardPaymentAccountList){
						//支付机构类型
						if(StringUtils.isNotBlank(pbc.getOnlinePayCompanyType())){
							pbc.setOnlinePayCompanyType(PbcOnlinePayCompanyType.labelOf(pbc.getOnlinePayCompanyType()));
						}
						//交易类型
						if(StringUtils.isNotBlank(pbc.getTransactionType())){
							pbc.setTransactionType(PbcTransType.labelOf(pbc.getTransactionType()));
						}
						//支付类型
						if(StringUtils.isNotBlank(pbc.getPaymentType())){
							pbc.setPaymentType(PbcPaymentType.labelOf(pbc.getPaymentType()));
						}
						//币种
						if(StringUtils.isNotBlank(pbc.getCurrency())){
							pbc.setCurrency(PbcCurrencyType.labelOf(pbc.getCurrency()));
						}
						//交易设备类型  
						if(StringUtils.isNotBlank(pbc.getTransactionDeviceType())){
							pbc.setTransactionDeviceType(PbcDeviceType.labelOf(pbc.getTransactionDeviceType()));
						}
					}
					return jsonType.toJson(pbcTransCardPaymentAccountList);
				} else if (StringUtils.equals(requestEventType, RequestEventType.PAYMENY_ACCOUNT.getValue())) {
					// 关联全支付账号查询
					PbcPaymentAccountBack pbcPaymentAccountBack = new PbcPaymentAccountBack();
					pbcPaymentAccountBack.setApplicationCode(applicationId);
					List<PbcPaymentAccountBack> pbcPaymentAccountBackList = pbcPaymentAccountBackService.findList(pbcPaymentAccountBack);
					for(PbcPaymentAccountBack pbc : pbcPaymentAccountBackList){
						//支付账号类型
						if(StringUtils.isNotBlank(pbc.getSubjectType())){
							pbc.setSubjectType(PbcQueryDetailType.labelOf(pbc.getSubjectType()));
						}
					}
					return jsonType.toJson(pbcPaymentAccountBackList);
				}
			}
			return null;
		} catch (Exception e) {
			log.error("PbcFeedbackQueryDealController getPbcAccountTransDetail has a error:{获取消息体的信息错误  FIND_ERROR}", e);
			return null;
		}
	}

	/**
	 * @方法说明：获取账户交易明细List
	 * @时间：2016年9月19日 
	 * @创建人：wangdong
	 */
	@RequiresPermissions("pbc:pbcFeedBackQueryDeal:view")
	@RequestMapping(value = "show")
	public String transList(RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		try {
			String applicationId = request.getParameter("applicationId");
			String flg = request.getParameter("flg");
			if (StringUtils.isNotBlank(flg) && StringUtils.isNotBlank(applicationId)) {
				if (StringUtils.equals(flg, "bank")) {
					PbcBankInfo pbcBankInfo = new PbcBankInfo();
					pbcBankInfo.setApplicationCode(applicationId);
					model = pbcBankInfoService.findPbcBankInfoPage(new Page<PbcBankInfo>(request, response),
							pbcBankInfo, model);
					model.addAttribute("flg", "bank");
				}
				if (StringUtils.equals(flg, "trans") && StringUtils.isNotBlank(applicationId)) {
					PbcTransInfo pbcTransInfo = new PbcTransInfo();
					pbcTransInfo.setApplicationCode(applicationId);
					model = pbcTransInfoService.findPbcTransInfoPage(new Page<PbcTransInfo>(request, response),
							pbcTransInfo, model);
					model.addAttribute("flg", "trans");
				}
				if (StringUtils.equals(flg, "measure") && StringUtils.isNotBlank(applicationId)) {
					PbcMeasureInfo pbcMeasureInfo = new PbcMeasureInfo();
					pbcMeasureInfo.setApplicationCode(applicationId);
					model = pbcMeasureInfoService.findPbcMeasureInfoPage(new Page<PbcMeasureInfo>(request, response),
							pbcMeasureInfo, model);
					model.addAttribute("flg", "measure");
				}
				model.addAttribute("pbcFeedBack", pbcFeedBackService.getEntityByApplicationCode(applicationId));
			}
			return "modules/pbc/pbcAccountTransDetailList";
		} catch (Exception e) {
			log.error("PbcFeedbackQueryDealController transList has a error:{获取账户交易明细List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * @方法说明：删除数据信息
	 * @时间：2016年9月19日 
	 * @创建人：wangdong
	 */
	@RequiresPermissions("pbc:pbcFeedBackQueryDeal:edit")
	@RequestMapping(value = "del")
	public String delInfo(RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		try {
			String applicationId = request.getParameter("applicationId");
			String requestEventType = request.getParameter("type");
			String pbcId = request.getParameter("pbcId");
			String flg = request.getParameter("flg");
			//查看该反馈信息是否审核完成，审核完成不允许删除
			if(StringUtils.isNotBlank(applicationId)){
				PbcFeedBack pbcFeedBack = pbcFeedBackService.getEntityByApplicationCode(applicationId);
				if(StringUtils.equals(pbcFeedBack.getRiskStatus(), RiskAuditStatus.TQS_Y.getValue())){
					model.addAttribute("message", "审核完成不允许删除!");
					if(StringUtils.isNotBlank(flg)){
						return transList(redirectAttributes, request, response, model);
					}else{
						return deal(redirectAttributes, request, response, model);
					}
				}
			}
			if (StringUtils.equals(requestEventType, RequestEventType.CASE_REPORT.getValue())) {
				// 支付账户交易明细查询
				PbcAccountInfo pbcAccountInfo = new PbcAccountInfo();
				pbcAccountInfo.setPbcId(Long.valueOf(pbcId));
				pbcAccountInfoService.delete(pbcAccountInfo);
				riskLogsService.savaEntity("删除", "账户交易明细查询反馈信息删除:业务申请编号:"+applicationId+",数据ID:"+pbcId, request);
				//删除账户交易明细查询的子集数据  交易流水数据  根据业务申请编号
				PbcTransInfo pbcTransInfo = new PbcTransInfo();
				pbcTransInfo.setApplicationCode(applicationId);
				pbcTransInfoService.delete(pbcTransInfo);
				riskLogsService.savaEntity("删除", "账户交易明细查询反馈信息关联子数据交易流水信息删除:业务申请编号:"+applicationId+",数据ID:"+pbcId, request);
			} else if (StringUtils.equals(requestEventType, RequestEventType.ABNORMAL_ACCOUNTS.getValue())) {
				// 账户主体详情查询
				PbcAccountDetail pbcAccountDetail = new PbcAccountDetail();
				pbcAccountDetail.setPbcId(Long.valueOf(pbcId));
				pbcAccountDetailService.delete(pbcAccountDetail);
				riskLogsService.savaEntity("删除", "账户主体详情查询反馈信息删除:业务申请编号:"+applicationId+",数据ID:"+pbcId, request);
				//删除账户主体详情查询的子集数据  银行卡信息  根据业务申请编号
				PbcBankInfo pbcBankInfo = new PbcBankInfo();
				pbcBankInfo.setApplicationCode(applicationId);
				pbcBankInfoService.delete(pbcBankInfo);
				riskLogsService.savaEntity("删除", "账户主体详情查询关联子数据银行卡信息删除:业务申请编号:"+applicationId+",数据ID:"+pbcId, request);
			} else if (StringUtils.equals(requestEventType, RequestEventType.ACCOUNTS_INVOLVED.getValue())) {
				// 账户动态查询
				PbcAccountInfo pbcAccountInfo = new PbcAccountInfo();
				pbcAccountInfo.setPbcId(Long.valueOf(pbcId));
				pbcAccountInfoService.delete(pbcAccountInfo);
				riskLogsService.savaEntity("删除", "账户动态查询反馈信息删除:业务申请编号:"+applicationId+",数据ID:"+pbcId, request);
				//删除账户动态查询的子集数据  交易流水数据  根据业务申请编号
				PbcTransInfo pbcTransInfo = new PbcTransInfo();
				pbcTransInfo.setApplicationCode(applicationId);
				pbcTransInfoService.delete(pbcTransInfo);
				riskLogsService.savaEntity("删除", "账户动态查询反馈关联子数据交易流水信息删除:业务申请编号:"+applicationId+",数据ID:"+pbcId, request);
			} else if (StringUtils.equals(requestEventType, RequestEventType.EXCEPTION_EVENTS.getValue())) {
				// 账户动态查询解除
				
			} else if (StringUtils.equals(requestEventType, RequestEventType.TRANSNO_ACCOUNT.getValue())) {
				// 按交易流水号查询银行卡/支付帐号
				PbcTransCardPaymentAccount pbcTransCardPaymentAccount = new PbcTransCardPaymentAccount();
				pbcTransCardPaymentAccount.setTransCardPaymentId(Long.valueOf(pbcId));
				pbcTransCardPaymentAccountService.delete(pbcTransCardPaymentAccount);
				riskLogsService.savaEntity("删除", "按交易流水号查询银行卡/支付帐号查询反馈信息删除:业务申请编号:"+applicationId+",数据ID:"+pbcId, request);
			} else if (StringUtils.equals(requestEventType, RequestEventType.PAYMENY_ACCOUNT.getValue())) {
				// 关联全支付账号查询
				PbcPaymentAccountBack pbcPaymentAccountBack = new PbcPaymentAccountBack();
				pbcPaymentAccountBack.setPaymentAccountId(Long.valueOf(pbcId));
				pbcPaymentAccountBackService.delete(pbcPaymentAccountBack);
				riskLogsService.savaEntity("删除", "关联全支付账号查询反馈信息删除:业务申请编号:"+applicationId+",数据ID:"+pbcId, request);
				//删除关联全支付账号查询的子集数据  银行卡信息  根据业务申请编号
				PbcBankInfo pbcBankInfo = new PbcBankInfo();
				pbcBankInfo.setApplicationCode(applicationId);
				pbcBankInfoService.delete(pbcBankInfo);
				riskLogsService.savaEntity("删除", "关联全支付账号查询关联子数据银行卡信息删除:业务申请编号:"+applicationId+",数据ID:"+pbcId, request);
				//删除关联全支付账号查询的子集数据  交易流水数据  根据业务申请编号
				PbcMeasureInfo pbcMeasureInfo = new PbcMeasureInfo();
				pbcMeasureInfo.setApplicationCode(applicationId);
				pbcMeasureInfoService.delete(pbcMeasureInfo);
				riskLogsService.savaEntity("删除", "关联全支付账号查询关联子数据措施信息删除:业务申请编号:"+applicationId+",数据ID:"+pbcId, request);
			}
			
			model.addAttribute("message", "删除数据成功!");
			
			if(StringUtils.isNotBlank(flg)){
				//删除银行卡信息
				if (StringUtils.equals(flg, "bank")) {
					PbcBankInfo pbcBankInfo = new PbcBankInfo();
					pbcBankInfo.setPbcId(Long.valueOf(pbcId));
					pbcBankInfoService.delete(pbcBankInfo);
					riskLogsService.savaEntity("删除", "银行卡信息删除:业务申请编号:"+applicationId+",数据ID:"+pbcId, request);
				}
				//删除交易流水信息
				if (StringUtils.equals(flg, "trans")) {
					PbcTransInfo pbcTransInfo = new PbcTransInfo();
					pbcTransInfo.setPbcId(Long.valueOf(pbcId));
					pbcTransInfoService.delete(pbcTransInfo);
					riskLogsService.savaEntity("删除", "交易流水信息删除:业务申请编号:"+applicationId+",数据ID:"+pbcId, request);
				}
				//删除措施信息
				if (StringUtils.equals(flg, "measure")) {
					PbcMeasureInfo pbcMeasureInfo = new PbcMeasureInfo();
					pbcMeasureInfo.setPbcId(Long.valueOf(pbcId));
					pbcMeasureInfoService.delete(pbcMeasureInfo);
					riskLogsService.savaEntity("删除", "措施信息删除:业务申请编号:"+applicationId+",数据ID:"+pbcId, request);
				}
				model.addAttribute("pbcFeedBack", pbcFeedBackService.getEntityByApplicationCode(applicationId));
					
				return transList(redirectAttributes, request, response, model);
			}else{
				return deal(redirectAttributes, request, response, model);
			}
		} catch (Exception e) {
			log.error("PbcFeedbackQueryDealController delInfo has a error:{删除数据信息出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}
	

	/**
	 * @方法说明：上传文件 @时间：2016年9月19日 ]
	 * @创建人：wangdong
	 */
	@RequiresPermissions("pbc:pbcFeedBackQueryDeal:edit")
	@RequestMapping(value = "upload")
	public String upload(RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		try {
			String deal = request.getParameter("deal");
			// 调用上传的方法
			pbcFeedBackService.upLoadFileAndInsert(file, request, response);
			addMessage(redirectAttributes, "上传成功");
			if (StringUtils.isNotBlank(deal)) {
				return deal(redirectAttributes, request, response, model);
			} else {
				return transList(redirectAttributes, request, response, model);
			}
		} catch (Exception e) {
			log.error("PbcFeedbackQueryDealController upload has a error:{上传文件出错 FIND_ERROR }", e);
			addMessage(redirectAttributes, "上传失败");
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @方法说明：保存查询结果
	 * @时间：2017年1月22日11:10:52
	 * @创建人：wangdong
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "saveReault")
	public String saveReault(RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		try {
			String feedBackId = request.getParameter("feedBackId");
			String result = request.getParameter("result");
			if(StringUtils.isNotBlank(feedBackId)){
				PbcFeedBack pbcFeedBack = new PbcFeedBack();
				pbcFeedBack.setFeedBackId(Long.valueOf(feedBackId));
				pbcFeedBack.setResult(result);
				pbcFeedBack.setRiskStatus(RiskAuditStatus.TQS_D.getValue());
				pbcFeedBackService.updateEntity(pbcFeedBack);
				addMessage(redirectAttributes, "保存数据成功！");
				riskLogsService.savaEntity("更新", "保存查询结果:"+result+",数据ID:"+feedBackId, request);
				return deal(redirectAttributes, request, response, model);
			}
			return null;
		} catch (Exception e) {
			log.error("PbcFeedbackQueryDealController getPbcAccountTransDetail has a error:{保存查询结果错误  FIND_ERROR}", e);
			return null;
		}
	}


	/**********************************************************************************************************************/

	/**
	 * wangjie
	 * 
	 * @param feedBackId
	 * @param remark
	 * @throws Exception
	 */
	@RequiresPermissions("pbc:pbcFeedBackQueryDeal:view")
	@RequestMapping(value = "saveRemark")
	public String save(PbcFeedBack pbcFeedBack, HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		String remark = request.getParameter("remark");
		String feedBackId = request.getParameter("feedBackId");
		String riskStatus = request.getParameter("riskStatus");
		String status = request.getParameter("status");
		//替换值，保存数据库
		if(ReportStatus.SUCCESE_S.getContent().equals(status)){
			status=ReportStatus.SUCCESE_S.getValue();
		}else if(ReportStatus.NOREPORTED.getContent().equals(status)){
			status=ReportStatus.NOREPORTED.getValue();
		}else{
			status=ReportStatus.FREPORTED.getValue();
		}
		
		try {
			pbcFeedBack.setFeedBackId((long) Integer.valueOf(feedBackId));
			pbcFeedBack.setRiskStatus(riskStatus);  //风控审核
			pbcFeedBack.setStatus(status);  //上报状态
			pbcFeedBack.setRemark(remark);   //备注
			pbcFeedBack.setRiskTime(new Date());   //风控审核时间
			pbcFeedBack.setDealTime(new Date());//风控专员处理时间
			pbcFeedBack.setOperatorName(UserUtils.getUser().getName());  //操作人
			pbcFeedBack.setRiskOperator(UserUtils.getUser().getName());   //审核人   
			int total = pbcFeedBackService.updateEntity(pbcFeedBack);
			model.addAttribute("total", total);
			return "modules/pbc/pbcFeedBackQueryDeal";
		} catch (Exception e) {
			log.error("PbcFeedbackQueryDealController transList has a error:{保存审核备注出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}

	/**
	 * wangjie 上报
	 * 
	 * @param feedBackId
	 * @param remark
	 * @throws Exception
	 */
	@RequiresPermissions("pbc:pbcFeedBackQueryDeal:view")
	@ResponseBody
	@RequestMapping(value = "report")
	public String reportRecord(PbcFeedBack pbcFeedBack, HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {

		String msg1 = "";
		String feedBackId = request.getParameter("feedBackId");
		try {

			// 获取反馈信息
			pbcFeedBack = pbcFeedBackService.getEntityById(Long.valueOf(feedBackId));
			String requestEventType = pbcFeedBack.getRequestEventType();
			String applicationId = pbcFeedBack.getApplicationId();
			// 从下行接口查询head信息
			PbcQueryInfo pbcQueryInfo = new PbcQueryInfo();
			pbcQueryInfo = pbcQueryInfoService.getEntityByApplicationId(applicationId);
			if (StringUtils.isNotBlank(applicationId) && StringUtils.isNotBlank(requestEventType)) {
				// 判断哪个请求事件的信息查看
				if (StringUtils.equals(requestEventType, RequestEventType.CASE_REPORT.getValue())) {
					// 支付账户交易明细查询
					PbcAccountInfo pbcAccountInfo = new PbcAccountInfo();
					pbcAccountInfo.setApplicationCode(applicationId);
					List<PbcAccountInfo> pbcAccountInfoList = pbcAccountInfoService.findList(pbcAccountInfo);
					//数据为空时，传个空对象进行上报
					if(pbcAccountInfoList.isEmpty()){
						pbcAccountInfoList = new ArrayList<PbcAccountInfo>();
						pbcAccountInfoList.add(new PbcAccountInfo());
					}
					pbcFeedBack.setAccountsList(pbcAccountInfoList);
					// 查出每个账户对应的交易明细
					for (PbcAccountInfo pbc : pbcAccountInfoList) {
						PbcTransInfo pbcTransInfo = new PbcTransInfo();
						pbcTransInfo.setApplicationCode(pbc.getApplicationCode());
						if("".equals(pbcTransInfo.getApplicationCode()) || pbcTransInfo.getApplicationCode()==null){
							pbcTransInfo.setApplicationCode("applicationCode");
						}
						List<PbcTransInfo> pbcTransInfoList = pbcTransInfoService.findList(pbcTransInfo);
						if(pbcTransInfoList.isEmpty()){
							pbcTransInfoList=new ArrayList<PbcTransInfo>();
							pbcTransInfoList.add(new PbcTransInfo());
						}
						pbc.setTransList(pbcTransInfoList);
					}
					JsonMapperUtil jsonUtils = new JsonMapperUtil();
					getLoad<Object, Object> load = new LoadEntity().new getLoad<>();
						pbcFeedBack.setSubjectType(pbcAccountInfoList.get(0).getSubjectType());
						pbcFeedBack.setAccountNumber(pbcAccountInfoList.get(0).getAccountNumber());
					load.setHead(pbcQueryInfo); // 判断
					load.setBody(pbcFeedBack);
					
					//替换报文字段名称
					String json = jsonUtils.toJson(load);
					String replace = json.replace("null", "\"-\"")
							.replace("applicationId", "applicationID")
							.replace("feedBackOrgName", "feedbackOrgName")
							.replace("featureCodes", "featureCode")
							.replace("reportCodes", "repoprtOrgID")
							.replace("feedBackRemark", "feedbackRemark")
							.replace("orderId", "orderID")
							.replace("accountOpenIpAddress", "accountOpenIPAddress")
							.replace("transInBankId", "transInBankID")
							.replace("transOutBankId", "transOutBankID")
							.replace("posIdentity", "posIdentity")
							.replace("transactionIp", "transactionIP")
							.replace("transactionMac", "transactionMAC")
							.replace("accountsList", "accounts")
							.replace("messageFrom", "to")
							.replace("transList", "transactions");

					// 调用上报接口 0000
					String msg = tranDetailServiceRpcClient.report(replace);
					if (StringUtil.notBlank(msg)) {
						Map map = jsonUtils.fromJson(msg, Map.class);
						String code = (String) map.get("code");
						String description = (String) map.get("description");
						if (PbcInterfaceStatus.SUCCESS.getValue().equals(code)) {
							pbcFeedBack.setStatus(ReportStatus.SUCCESE_S.getValue());
							pbcFeedBack.setReportTime(new Date());
							pbcFeedBack.setCode(code);
							pbcFeedBack.setNum(1);
							pbcFeedBack.setDealTime(new Date());//风控专员处理时间
							pbcFeedBack.setDescription(description);
							pbcFeedBackService.updateEntity(pbcFeedBack);
							msg1 = "1000";
							log.info("支付账户交易明细查询上报成功");
						} else {
							pbcFeedBack.setStatus(ReportStatus.FREPORTED.getValue());
							pbcFeedBack.setFailRemark(description);
							pbcFeedBack.setCode(code);
							pbcFeedBack.setDealTime(new Date());//风控专员处理时间
							pbcFeedBack.setDescription(description);
							pbcFeedBackService.updateEntity(pbcFeedBack);
							msg1 = "2000";
							log.info("支付账户交易明细查询上报失败");
						}
						//记录日记
						riskLogsService.savaEntity("上报", "支付账户交易明细查询上报--->{返回结果}"+description, request);

					}
					
				} else if (StringUtils.equals(requestEventType, RequestEventType.ABNORMAL_ACCOUNTS.getValue())) {
					// 账户主体详情查询 关联银行卡
					PbcAccountDetail pbcAccountDetail = new PbcAccountDetail();
					pbcAccountDetail.setApplicationCode(applicationId);
					List<PbcAccountDetail> pbcAccountDetailList = pbcAccountDetailService.findList(pbcAccountDetail);
					if(pbcAccountDetailList.isEmpty()){
						pbcAccountDetailList = new ArrayList<PbcAccountDetail>();
						pbcAccountDetailList.add(new PbcAccountDetail());
					}
						pbcFeedBack.setAccountOwnerName(pbcAccountDetailList.get(0).getAccountOwnerName());
						pbcFeedBack.setAccountOwnerIDType(pbcAccountDetailList.get(0).getAccountOwnerIdType() == null?null:pbcAccountDetailList.get(0).getAccountOwnerIdType().toString());
						pbcFeedBack.setAccountOwnerID(pbcAccountDetailList.get(0).getAccountOwnerId());
						pbcFeedBack.setCredentialValidity(pbcAccountDetailList.get(0).getCredentialValidity());
						pbcFeedBack.setTelNumber(pbcAccountDetailList.get(0).getTelNumber());
						pbcFeedBack.setOnlineShopName(pbcAccountDetailList.get(0).getOnlineShopName());
						pbcFeedBack.setDataType(pbcAccountDetailList.get(0).getDataType());
						pbcFeedBack.setData(pbcAccountDetailList.get(0).getData());
					    
					for (PbcAccountDetail pbc : pbcAccountDetailList) {
						PbcBankInfo pbcBankInfo = new PbcBankInfo();
						pbcBankInfo.setApplicationCode(applicationId);
						if("".equals(pbcBankInfo.getApplicationCode()) || pbcBankInfo.getApplicationCode()==null){
							pbcBankInfo.setApplicationCode("applicationCode");
						}
						List<PbcBankInfo> pbcBankInfoList = pbcBankInfoService.findList(pbcBankInfo);
						if(pbcBankInfoList.isEmpty()){
							pbcBankInfoList=new ArrayList<PbcBankInfo>();
							pbcBankInfoList.add(new PbcBankInfo());
						}
						pbcFeedBack.setPbcBankInfo(pbcBankInfoList);
						pbcFeedBack.setList(pbcAccountDetailList);
					}
					JsonMapperUtil jsonUtils = new JsonMapperUtil();
					getLoad<Object, Object> load = new LoadEntity().new getLoad<>();
					load.setHead(pbcQueryInfo); // 判断
					load.setBody(pbcFeedBack);
					String json = jsonUtils.toJson(load);
					String replace = json.replace("null", "\"-\"")
							.replace("feedBackOrgName", "feedbackOrgName")
							.replace("applicationId", "applicationID")
							.replace("featureCodes", "featureCode")
							.replace("reportCodes", "repoprtOrgID")
							.replace("feedBackRemark", "feedbackRemark")
							.replace("orderId", "orderID")
							.replace("accountOpenIpAddress", "accountOpenIPAddress")
							.replace("transInBankId", "transInBankID")
							.replace("posIdentity", "posIdentity")
							.replace("transactionIp", "transactionIP")
							.replace("transactionMac", "transactionMAC")
							.replace("accountList", "accounts")
							.replace("transList", "transactions")
							.replace("bankId", "bankID")
							.replace("messageFrom", "to")
							.replace("pbcBankInfo", "bankCard");

					String msg = subjectDetailServiceRpcClient.pbcPaymentAccount(replace);
					if (StringUtil.notBlank(msg)) {
						Map map = jsonUtils.fromJson(msg, Map.class);
						String code = (String) map.get("code");
						String description = (String) map.get("description");
						if (PbcInterfaceStatus.SUCCESS.getValue().equals(code)) {
							pbcFeedBack.setStatus(ReportStatus.SUCCESE_S.getValue());
							pbcFeedBack.setReportTime(new Date());
							pbcFeedBack.setCode(code);
							pbcFeedBack.setDealTime(new Date());//风控专员处理时间
							pbcFeedBack.setDescription(description);
							pbcFeedBack.setNum(1);
							pbcFeedBackService.updateEntity(pbcFeedBack);
							msg1 = "1000";
							log.info("账户主体详情查询上报成功");
						} else {
							pbcFeedBack.setStatus(ReportStatus.FREPORTED.getValue());
							pbcFeedBack.setFailRemark(description);
							pbcFeedBack.setCode(code);
							pbcFeedBack.setDealTime(new Date());//风控专员处理时间
							pbcFeedBack.setDescription(description);
							pbcFeedBackService.updateEntity(pbcFeedBack);
							msg1 = "2000";
							log.info("账户主体详情查询上报失败");
						}
						riskLogsService.savaEntity("上报", "账户主体详情查询上报--->{返回结果}"+description, request);
					}
				} else if (StringUtils.equals(requestEventType, RequestEventType.ACCOUNTS_INVOLVED.getValue())) {
					// 账户动态查询
					PbcAccountInfo pbcAccountInfo = new PbcAccountInfo();
					pbcAccountInfo.setApplicationCode(applicationId);
					List<PbcAccountInfo> pbcAccountInfoList = pbcAccountInfoService.findList(pbcAccountInfo);
					if(pbcAccountInfoList.isEmpty()){
						pbcAccountInfoList = new ArrayList<PbcAccountInfo>();
						pbcAccountInfoList.add(new PbcAccountInfo());
					}
						pbcFeedBack.setSubjectType(pbcAccountInfoList.get(0).getSubjectType());
						pbcFeedBack.setAccountNumber(pbcAccountInfoList.get(0).getAccountNumber());
					
					pbcFeedBack.setAccountsList(pbcAccountInfoList);
					// 查出每个账户对应的交易明细
					for (PbcAccountInfo pbc : pbcAccountInfoList) {
						PbcTransInfo pbcTransInfo = new PbcTransInfo();
						pbcTransInfo.setApplicationCode(applicationId);
						
						if("".equals(pbcTransInfo.getApplicationCode()) || pbcTransInfo.getApplicationCode()==null){
							pbcTransInfo.setApplicationCode("applicationCode");
						}
						List<PbcTransInfo> transList = pbcTransInfoService.findList(pbcTransInfo);
						if(transList.isEmpty()){
							transList = new ArrayList<PbcTransInfo>();
							transList.add(new PbcTransInfo());
						}
						pbc.setTransList(transList);
					}
					JsonMapperUtil jsonUtils = new JsonMapperUtil();
					getLoad<Object, Object> load = new LoadEntity().new getLoad<>();
					load.setHead(pbcQueryInfo); // 判断
					load.setBody(pbcFeedBack);
					String json = jsonUtils.toJson(load);
					String replace = json.replace("null", "\"-\"")
							.replace("feedBackOrgName", "feedbackOrgName")
							.replace("featureCodes", "featureCode")
							.replace("reportCodes", "repoprtOrgID")
							.replace("feedBackRemark", "feedbackRemark")
							.replace("orderId", "orderID")
							.replace("accountOpenIpAddress", "accountOpenIPAddress")
							.replace("transInBankId", "transInBankID")
							.replace("posIdentity", "posIdentity")
							.replace("transactionIp", "transactionIP")
							.replace("transactionMac", "transactionMAC")
							.replace("accountsList", "accounts")
							.replace("transOutBankId", "transOutBankID")
							.replace("applicationId", "applicationID")
							.replace("messageFrom", "to")
							.replace("transList", "transactions");

					String msg = dynamicServiceRpcClient.reportResponseData(replace);
					if (StringUtil.notBlank(msg)) {
						Map map = jsonUtils.fromJson(msg, Map.class);
						String code = (String) map.get("code");
						String description = (String) map.get("description");
						if (PbcInterfaceStatus.SUCCESS.getValue().equals(code)) {
							pbcFeedBack.setStatus(ReportStatus.SUCCESE_S.getValue());
							pbcFeedBack.setReportTime(new Date());
							pbcFeedBack.setCode(code);
							pbcFeedBack.setDealTime(new Date());//风控专员处理时间
							pbcFeedBack.setNum(1);
							pbcFeedBack.setDescription(description);
							pbcFeedBackService.updateEntity(pbcFeedBack);
							msg1 = "1000";
							log.info("账户动态查询上报成功");
						} else {
							pbcFeedBack.setStatus(ReportStatus.FREPORTED.getValue());
							pbcFeedBack.setFailRemark(description);
							pbcFeedBack.setCode(code);
							pbcFeedBack.setDescription(description);
							pbcFeedBack.setDealTime(new Date());//风控专员处理时间
							pbcFeedBackService.updateEntity(pbcFeedBack);
							msg1 = "2000";
							log.info("账户动态查询上报失败");
						}
						riskLogsService.savaEntity("上报", "账户动态查询上报--->{返回结果}"+description, request);
					}
					
				} else if (StringUtils.equals(requestEventType, RequestEventType.EXCEPTION_EVENTS.getValue())) {
					// 账户动态查询解除
					PbcReleaseFeedback pbcReleaseFeedback = new PbcReleaseFeedback();
					pbcReleaseFeedback.setApplicationCode(applicationId);

					// 查询机构,编码
					List<PbcReleaseFeedback> pbcReleaseFeedbackList = pbcReleaseFeedbackService
							.findList(pbcReleaseFeedback);
					if(pbcReleaseFeedbackList.isEmpty()){
						pbcReleaseFeedbackList = new ArrayList<PbcReleaseFeedback>();
						pbcReleaseFeedbackList.add(new PbcReleaseFeedback());
					}
						pbcFeedBack.setCaseNumber(pbcReleaseFeedbackList.get(0).getCaseNumber());
						pbcFeedBack.setApplicationCode(pbcReleaseFeedbackList.get(0).getApplicationCode());
					
					pbcFeedBack.setOnlinePayCompanyID(pbcQueryInfo.getOnlinePayCompanyId());
					pbcFeedBack.setOnlinePayCompanyName(pbcQueryInfo.getOnlinePayCompanyName());

					JsonMapperUtil jsonUtils = new JsonMapperUtil();
					getLoad<Object, Object> load = new LoadEntity().new getLoad<>();
					load.setHead(pbcQueryInfo); // 判断
					load.setBody(pbcFeedBack);
					String json = jsonUtils.toJson(load);
					String replace = json.replace("null", "\"-\"")
							.replace("feedBackOrgName", "feedbackOrgName")
							.replace("featureCodes", "featureCode")
							.replace("reportCodes", "repoprtOrgID")
							.replace("feedBackRemark", "feedbackRemark")
							.replace("orderId", "orderID")
							.replace("accountOpenIpAddress", "accountOpenIPAddress")
							.replace("transInBankId", "transInBankID")
							.replace("posIdentity", "posIdentity")
							.replace("transactionIp", "transactionIP")
							.replace("transactionMac", "transactionMAC")
							.replace("accountList", "accounts")
							.replace("messageFrom", "to")
							.replace("transList", "transactions")
							.replace("applicationOrgCode", "applicationOrgID")
							.replace("applicationId", "applicationID");

					String msg = dynamicServiceRpcClient.reportRelieveResponseData(replace);
					if (StringUtil.notBlank(msg)) {
						Map map = jsonUtils.fromJson(msg, Map.class);
						String code = (String) map.get("code");
						String description = (String) map.get("description");
						if (PbcInterfaceStatus.SUCCESS.getValue().equals(code)) {
							pbcFeedBack.setStatus(ReportStatus.SUCCESE_S.getValue());
							pbcFeedBack.setReportTime(new Date());
							pbcFeedBack.setDealTime(new Date());//风控专员处理时间
							pbcFeedBack.setCode(code);
							pbcFeedBack.setNum(1);
							pbcFeedBack.setDescription(description);
							pbcFeedBackService.updateEntity(pbcFeedBack);
							msg1 = "1000";
							log.info("账户动态查询解除上报成功");
						} else {
							pbcFeedBack.setStatus(ReportStatus.FREPORTED.getValue());
							pbcFeedBack.setFailRemark(description);
							pbcFeedBack.setCode(code);
							pbcFeedBack.setDealTime(new Date());//风控专员处理时间
							pbcFeedBack.setDescription(description);
							pbcFeedBackService.updateEntity(pbcFeedBack);
							msg1 = "2000";
							log.info("账户动态查询解除上报失败");
						}
						riskLogsService.savaEntity("上报", "账户动态查询解除上报--->{返回结果}"+description, request);
					}
				} else if (StringUtils.equals(requestEventType, RequestEventType.TRANSNO_ACCOUNT.getValue())) {
					// 按交易流水号查询银行卡/支付帐号
					PbcTransCardPaymentAccount pbcTransCardPaymentAccount = new PbcTransCardPaymentAccount();
					pbcTransCardPaymentAccount.setApplicationCode(applicationId);
					List<PbcTransCardPaymentAccount> pbcTransCardPaymentAccountList = pbcTransCardPaymentAccountService
							.findList(pbcTransCardPaymentAccount);
					if(pbcTransCardPaymentAccountList.isEmpty()){
						pbcTransCardPaymentAccountList = new ArrayList<PbcTransCardPaymentAccount>();
						pbcTransCardPaymentAccountList.add(new PbcTransCardPaymentAccount());
					}
					pbcFeedBack.setOnlinePayCompanyType(pbcTransCardPaymentAccountList.get(0).getOnlinePayCompanyType());
					pbcFeedBack.setTransactionType(pbcTransCardPaymentAccountList.get(0).getTransactionType());
					pbcFeedBack.setPaymentType(pbcTransCardPaymentAccountList.get(0).getPaymentType());
					pbcFeedBack.setCurrency(pbcTransCardPaymentAccountList.get(0).getCurrency());
					pbcFeedBack.setTransferAmount(pbcTransCardPaymentAccountList.get(0).getTransferAmount() == null
							? null : pbcTransCardPaymentAccountList.get(0).getTransferAmount().toString());
					pbcFeedBack.setTransInBankID(pbcTransCardPaymentAccountList.get(0).getTransInBankId());
					pbcFeedBack.setTransInBankName(pbcTransCardPaymentAccountList.get(0).getTransInBankName());
					pbcFeedBack.setTransInCardNumber(pbcTransCardPaymentAccountList.get(0).getTransInCardNumber());
					pbcFeedBack.setTransInAccountNumber(pbcTransCardPaymentAccountList.get(0).getTransInAccountNumber());
					pbcFeedBack.setPosIdentity(pbcTransCardPaymentAccountList.get(0).getPosIdentity());
					pbcFeedBack.setMerchantNumber(pbcTransCardPaymentAccountList.get(0).getMerchantNumber());
					pbcFeedBack.setTransOutBankID(pbcTransCardPaymentAccountList.get(0).getTransOutBankId());
					pbcFeedBack.setTransOutBankName(pbcTransCardPaymentAccountList.get(0).getTransOutBankName());
					pbcFeedBack.setTransOutCardNumber(pbcTransCardPaymentAccountList.get(0).getTransOutCardNumber());
					pbcFeedBack.setTransOutAccountNumber(pbcTransCardPaymentAccountList.get(0).getTransOutAccountNumber());
					pbcFeedBack.setTransactionDeviceType(pbcTransCardPaymentAccountList.get(0).getTransactionDeviceType());
					pbcFeedBack.setTransactionIP(pbcTransCardPaymentAccountList.get(0).getTransactionIp());
					pbcFeedBack.setTransactionMAC(pbcTransCardPaymentAccountList.get(0).getTransactionMac());
					pbcFeedBack.setTransactionDeviceCode(pbcTransCardPaymentAccountList.get(0).getTransactionDeviceCode());
					pbcFeedBack.setTransactionLongitude(
							pbcTransCardPaymentAccountList.get(0).getTransactionLongitude() == null ? null
									: pbcTransCardPaymentAccountList.get(0).getTransactionLongitude().toString());
					pbcFeedBack.setTransactionLatitude(
							pbcTransCardPaymentAccountList.get(0).getTransactionLatitude() == null ? null
									: pbcTransCardPaymentAccountList.get(0).getTransactionLatitude().toString());
					pbcFeedBack
							.setBankTransactionSerial(pbcTransCardPaymentAccountList.get(0).getBankTransactionSerial());
					
					JsonMapperUtil jsonUtils = new JsonMapperUtil();
					getLoad<Object, Object> load = new LoadEntity().new getLoad<>();
					load.setHead(pbcQueryInfo); // 判断
					load.setBody(pbcFeedBack);
					String json = jsonUtils.toJson(load);
					String replace = json.replace("null", "\"-\"")
							.replace("feedBackOrgName", "feedbackOrgName")
							.replace("featureCodes", "featureCode")
							.replace("reportCodes", "repoprtOrgID")
							.replace("feedBackRemark", "feedbackRemark")
							.replace("orderId", "orderID")
							.replace("accountOpenIpAddress", "accountOpenIPAddress")
							.replace("transInBankId", "transInBankID")
							.replace("posIdentity", "posIdentity")
							.replace("transactionIp", "transactionIP")
							.replace("transactionMac", "transactionMAC")
							.replace("accountList", "accounts")
							.replace("applicationId", "applicationID")
							.replace("messageFrom", "to")
							.replace("transList", "transactions");

					String msg = transNumberServiceRpcClient.report(replace);
				    if (StringUtil.notBlank(msg)) {
						Map map = jsonUtils.fromJson(msg, Map.class);
						String code = (String) map.get("code");
						String description = (String) map.get("description");
						if (PbcInterfaceStatus.SUCCESS.getValue().equals(code)) {
							pbcFeedBack.setStatus(ReportStatus.SUCCESE_S.getValue());
							pbcFeedBack.setReportTime(new Date());
							pbcFeedBack.setCode(code);
							pbcFeedBack.setDealTime(new Date());//风控专员处理时间
							pbcFeedBack.setDescription(description);
							pbcFeedBack.setNum(1);
							pbcFeedBackService.updateEntity(pbcFeedBack);
							msg1 = "1000";
							log.info("按交易流水号查询银行卡/支付帐号上报成功");
						} else {
							pbcFeedBack.setStatus(ReportStatus.FREPORTED.getValue());
							pbcFeedBack.setFailRemark(description);
							pbcFeedBack.setCode(code);
							pbcFeedBack.setDescription(description);
							pbcFeedBack.setDealTime(new Date());//风控专员处理时间
							pbcFeedBackService.updateEntity(pbcFeedBack);
							msg1 = "2000";
							log.info("按交易流水号查询银行卡/支付帐号上报失败");
						}
						riskLogsService.savaEntity("上报", "按交易流水号查询银行卡/支付帐号上报--->{返回结果}"+description, request);
					}
					
				} else if (StringUtils.equals(requestEventType, RequestEventType.PAYMENY_ACCOUNT.getValue())) {
					// 关联全支付账号查询
					PbcPaymentAccountBack pbcPaymentAccountBack = new PbcPaymentAccountBack();
					pbcPaymentAccountBack.setApplicationCode(applicationId);
					List<PbcPaymentAccountBack> pbcPaymentAccountList = pbcPaymentAccountBackService
							.findList(pbcPaymentAccountBack);
					if(pbcPaymentAccountList.isEmpty()){
						pbcPaymentAccountList = new ArrayList<PbcPaymentAccountBack>();
						pbcPaymentAccountList.add(new PbcPaymentAccountBack());
					}
					pbcFeedBack.setAccountsList(pbcPaymentAccountList);
					// 查询多个银行卡
					for (PbcPaymentAccountBack pbc : pbcPaymentAccountList) {
						PbcBankInfo pbcBankInfo = new PbcBankInfo();
						pbcBankInfo.setApplicationCode(applicationId);
						List<PbcBankInfo> pbcBankInfoList = pbcBankInfoService.findList(pbcBankInfo);
						if(pbcBankInfoList.isEmpty()){
							pbcBankInfoList = new ArrayList<PbcBankInfo>();
							pbcBankInfoList.add(new PbcBankInfo());
						}
						pbc.setBackCard(pbcBankInfoList);
						PbcMeasureInfo pbcMeasureInfo = new PbcMeasureInfo();
						pbcMeasureInfo.setApplicationCode(applicationId);
						
						if("".equals(pbcMeasureInfo.getApplicationCode()) || pbcMeasureInfo.getApplicationCode()==null){
							pbcMeasureInfo.setApplicationCode("applicationCode");
						}
						
						List<PbcMeasureInfo> pbcMeasureInfoList = pbcMeasureInfoService.findList(pbcMeasureInfo);
						if(pbcMeasureInfoList.isEmpty()){
							pbcMeasureInfoList = new ArrayList<PbcMeasureInfo>();
							pbcMeasureInfoList.add(new PbcMeasureInfo());
						}
						pbc.setMeasures(pbcMeasureInfoList);
					}
					JsonMapperUtil jsonUtils = new JsonMapperUtil();
					getLoad<Object, Object> load = new LoadEntity().new getLoad<>();
					load.setHead(pbcQueryInfo); // 判断
					load.setBody(pbcFeedBack);
					String json = jsonUtils.toJson(load);
					String replace = json.replace("null", "\"-\"")
							.replace("feedBackOrgName", "feedbackOrgName")
							.replace("featureCodes", "featureCode")
							.replace("reportCodes", "repoprtOrgID")
							.replace("feedBackRemark", "feedbackRemark")
							.replace("orderId", "orderID")
							.replace("accountOpenIpAddress", "accountOpenIPAddress")
							.replace("transInBankId", "transInBankID")
							.replace("posIdentity", "posIdentity")
							.replace("transactionIp", "transactionIP")
							.replace("transactionMac", "transactionMAC")
							.replace("accountList", "accountsList")
							.replace("messageFrom", "to")
							.replace("backCard", "bankCard")
							.replace("loginId", "loginID")
							.replace("wechatId", "wechatID")
							.replace("regularIpAddress", "regularIPAddress")
							.replace("bankId", "bankID")
							.replace("applicationId", "applicationID")
					        .replace("transList", "transactions");

					String msg = precCashFlowServiceRpcClient.pbcPaymentAccount(replace);
					if (StringUtil.notBlank(msg)) {
						Map map = jsonUtils.fromJson(msg, Map.class);
						String code = (String) map.get("code");
						String description = (String) map.get("description");
						if (PbcInterfaceStatus.SUCCESS.getValue().equals(code)) {
							pbcFeedBack.setStatus(ReportStatus.SUCCESE_S.getValue());
							pbcFeedBack.setReportTime(new Date());
							pbcFeedBack.setCode(code);
							pbcFeedBack.setDealTime(new Date());//风控专员处理时间
							pbcFeedBack.setDescription(description);
							pbcFeedBack.setNum(1);
							pbcFeedBackService.updateEntity(pbcFeedBack);
							msg1 = "1000";
							log.info("关联全支付账号查询上报成功");
						} else {
							pbcFeedBack.setStatus(ReportStatus.FREPORTED.getValue());
							pbcFeedBack.setFailRemark(description);
							pbcFeedBack.setCode(code);
							pbcFeedBack.setDealTime(new Date());//风控专员处理时间
							pbcFeedBack.setDescription(description);
							pbcFeedBackService.updateEntity(pbcFeedBack);
							msg1 = "2000";
							log.info("关联全支付账号查询上报失败");
						}
						riskLogsService.savaEntity("上报", "关联全支付账号查询上报--->{返回结果}"+description, request);
					}
					
				}

			}
			return msg1;
		} catch (Exception e) {
			log.error("PbcFeedbackQueryDealController transList has a error:{保存审核备注出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	
	}


	/**
	 * 查看附件    wangjie
	 * 
	 * @param pbcFeedBack
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "onExport")
	public Map<String,Object> onExport(PbcFeedBack pbcFeedBack, HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		String queryInfoId = request.getParameter("queryInfoId");
		
		try {
			// 获取反馈信息
			PbcAttachmentQuery pbcAttachmentQuery = new PbcAttachmentQuery();
			pbcAttachmentQuery.setApplicationCode(queryInfoId);
				List<PbcAttachmentQuery> pbcAttachmentQueryList = pbcAttachmentQueryService
						.findList(pbcAttachmentQuery);
				Map<String,Object> map = new HashMap<String, Object>();
				
				map.put("filePath",pbcAttachmentQueryList.get(0).getFilePath() );
				map.put("fileContent", pbcAttachmentQueryList.get(0).getFileContent());
				map.put("success", true);
				
			return map;
		} catch (Exception e) {
			log.error("PbcFeedbackQueryDealController transList has a error:{查看附件出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}

	/**
	 * @方法说明：反馈表信息到处 @时间：2016年11月18日18:55:48
	 * @创建人：wangdong
	 */
	@RequiresPermissions("pbc:pbcFeedBackQueryDeal:edit")
	@RequestMapping(value = "export")
	public void export(HttpServletRequest request, HttpServletResponse response, PbcFeedBack pbcFeedBack)
			throws Exception {
		try {
			// 显示要查询的信息
			pbcFeedBackService.export(pbcFeedBack, request, response);
		} catch (Exception e) {
			log.error("PbcFeedbackQueryDealController export has a error:{反馈表信息处理错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	

}