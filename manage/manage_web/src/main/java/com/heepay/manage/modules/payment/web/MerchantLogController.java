/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.common.util.DateUtil;
import com.heepay.enums.AccountMark;
import com.heepay.enums.BalanceDirection;
import com.heepay.enums.MerchantAccountType;
import com.heepay.manage.common.cache.PrimaryKeyCreator;
import com.heepay.manage.modules.payment.entity.MerchantLogDetail;
import com.heepay.manage.modules.payment.rpc.client.SettleBatchMsgClient;
import com.heepay.manage.modules.payment.service.MerchantLogDetailService;
import com.heepay.rpc.billing.model.SettleBatchMsgModel;
import com.heepay.rpc.billing.model.SettleChannelModel;
import com.heepay.rpc.billing.model.SettleMerchantModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.modules.account.service.MerchantAccountService;
import com.heepay.manage.modules.payment.entity.MerchantLog;
import com.heepay.manage.modules.payment.service.MerchantLogService;


/**
 *
 * 描    述：账户明细查询Controller
 *
 * 创 建 者： @author yq
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
@Controller
@RequestMapping(value = "${adminPath}/payment/merchantLog")
public class MerchantLogController extends BaseController {

	@Autowired
	private MerchantLogService merchantLogService;

	@Autowired
	private MerchantLogDetailService merchantLogDetailService;

	@Autowired
	private MerchantAccountService merchantAccountService;

	@Autowired
	private SettleBatchMsgClient settleBatchMsgClient;

	private static Logger logger = LogManager.getLogger();

	@ModelAttribute
	public MerchantLog get(@RequestParam(required=false) String id) {
		MerchantLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantLogService.get(id);
		}
		if (entity == null){
			entity = new MerchantLog();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:merchantLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantLog merchantLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if(merchantLog.getSortOrder() == null){
			//默认按照创建时间降序
			merchantLog.setSortOrder(SortOrderType.DESC.getValue());
		}
		merchantLog.setSourceSearch(0);
		
		String accountCodesHidden = merchantLog.getAccountCodesHidden();
    List<String> accCodesList = Lists.newArrayList();
    if(StringUtil.notBlank(accountCodesHidden)){
      String[] accountCodes = accountCodesHidden.split(",");
      accCodesList = Arrays.asList(accountCodes);
      Map<String, Object> paramsMap = new HashMap<String, Object>();
      paramsMap.put("accCodes", accCodesList);
      List<Long> merchantIds = merchantAccountService.getSelectMerchantIds(paramsMap);
      merchantLog.setMerchantIds(merchantIds);
    }
		if(null == merchantLog.getBeginCreateTime() && null == merchantLog.getEndCreateTime()){
			merchantLog.setBeginCreateTime(new Date());
			merchantLog.setEndCreateTime(new Date());
		}
		Page<MerchantLog> page = merchantLogService.findPage(new Page<MerchantLog>(request, response), merchantLog); 
		model.addAttribute("page", page);
		return "modules/payment/merchantLogList";
	}

	@RequiresPermissions("payment:merchantLog:view")
	@RequestMapping(value = "form")
	public String form(MerchantLog merchantLog, Model model) {
		model.addAttribute("merchantLog", merchantLog);
		return "modules/payment/merchantLogForm";
	}

	@RequiresPermissions("payment:merchantLog:edit")
	@RequestMapping(value = "save")
	public String save(MerchantLog merchantLog, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantLog)){
			return form(merchantLog, model);
		}
		merchantLogService.save(merchantLog);
		addMessage(redirectAttributes, "保存账户明细查询成功");
		return "redirect:"+Global.getAdminPath()+"/payment/merchantLog/?repage";
	}
	
	@RequiresPermissions("payment:merchantLog:edit")
	@RequestMapping(value = "delete")
	public String delete(MerchantLog merchantLog, RedirectAttributes redirectAttributes) {
		merchantLogService.delete(merchantLog);
		addMessage(redirectAttributes, "删除账户明细查询成功");
		return "redirect:"+Global.getAdminPath()+"/payment/merchantLog/?repage";
	}


	@RequiresPermissions("payment:splMerchantLogDetail:edit")
	@RequestMapping(value = "splMerchantLogDetailList")
	public String splMerchantLogDetailList(MerchantLog merchantLog,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes,Model model) {
		MerchantLog reMerchantLog = null;
		if(StringUtil.notBlank(String.valueOf(merchantLog.getMerchantId())) || StringUtil.notBlank(merchantLog.getSettleId())){
			List<MerchantLog> merchantLogs = merchantLogService.findList(merchantLog);
			if(merchantLogs !=null){
				for(MerchantLog merchantLog1 : merchantLogs){
					String cAccountMark = AccountMark.getSettleCodeByTransType(merchantLog1.getType());
					String mAccountMark = AccountMark.getSettleCodeMerchantByTransType(merchantLog1.getType());
					if((MerchantAccountType.PERSONAL_ACCOUNT.getValue().equals(merchantLog1.getMerchantType()) ||
							MerchantAccountType.MERCHANT_ACCOUNT.getValue().equals(merchantLog1.getMerchantType()))  ||
							String.valueOf(merchantLog1.getAccountId()).startsWith("6803110") &&
							(merchantLog1.getAccountMark().equals(cAccountMark) || merchantLog1.getAccountMark().equals(mAccountMark))){
						reMerchantLog = merchantLog1;
						break;
					}
				}
			}
			model.addAttribute("splmerchantLogDetail", reMerchantLog);
		}
		if(reMerchantLog !=null){
			MerchantLogDetail merchantLogDetail  = new MerchantLogDetail();
			merchantLogDetail.setSettleId(reMerchantLog.getSettleId());
			List<MerchantLogDetail> mD = merchantLogDetailService.findList(merchantLogDetail);
			if(mD == null || mD.size()==0){
				model.addAttribute("settleStatus", Boolean.FALSE);
			}else{
				model.addAttribute("settleStatus", Boolean.TRUE);
			}
		}
		return "modules/payment/splMerchantLogDetailList";
	}

	@RequiresPermissions("payment:executeSplMerchantLogDetail:edit")
	@RequestMapping(value = "executeSplMerchantLogDetail")
	public String executeSplMerchantLogDetail(MerchantLog merchantLog,HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		List<MerchantLog> merchantLogs = new ArrayList<MerchantLog>();
		String iSettleId = request.getParameter("iSettleId");
		//String iPayNum = request.getParameter("iPayNum");
		logger.info("补账务明细,结算单号:{}", iSettleId);
		MerchantLogDetail merchantLogDetail  = new MerchantLogDetail();
		merchantLogDetail.setSettleId(iSettleId);
		List<MerchantLogDetail> mD = merchantLogDetailService.findList(merchantLogDetail);
		if(mD == null || mD.size()==0){
			SettleBatchMsgModel settleBatchMsgModel = null;
			if(iSettleId.endsWith("0")){
				settleBatchMsgModel = settleBatchMsgClient.queryChannelSettleBatch(iSettleId, -1, 30);
				if(settleBatchMsgModel !=null){
					logger.info("补账务明细,结算单号:{},通道侧返回:{}", iSettleId,settleBatchMsgModel.toString());
					List<SettleChannelModel> settleChannelList = settleBatchMsgModel.getSettleChannelList();
					int remainder = settleChannelList.size()/500;
					int mod = settleChannelList.size()%500;
					int pageNumber = 0;
					if(remainder == 0){
						pageNumber=1;
					}
					if(remainder != 0){
						pageNumber=remainder;
						if(mod !=0){
							pageNumber+=1;
						}
					}
					for(int i=0; i<pageNumber; i++){
						List<SettleChannelModel> list = null;
						if(i + 1 == pageNumber){
							list = settleChannelList.subList(i*500,settleChannelList.size());
						}else{
							list = settleChannelList.subList(i*500,(i+1)*500);
						}
						List<MerchantLogDetail> merchantLogDetailList = new ArrayList<MerchantLogDetail>();
						for(SettleChannelModel settleChannelModel : list){
							MerchantLogDetail dtail = new MerchantLogDetail();
							dtail.setMerchantId(0L);
							dtail.setLogId(0L);
							dtail.setDetailId(PrimaryKeyCreator.getMerchantLogDetailId());
							dtail.setType(settleChannelModel.getTransType());
							dtail.setTransNo(settleChannelModel.getTransNo());
							dtail.setSettleId(iSettleId);
							dtail.setSettlementAmount(settleChannelModel.getSuccessAmount());
							dtail.setCreateTime(new Date());
							dtail.setUpdateTime(new Date());
							dtail.setAccountDate(settleChannelModel.getSettleTime());
							dtail.setBalanceAmount(String.valueOf(new BigDecimal(settleChannelModel.getSuccessAmount()).add(new BigDecimal(settleChannelModel.getCostAmount()))));
							dtail.setFeeAmount(String.valueOf(new BigDecimal(settleChannelModel.getCostAmount())));
							dtail.setBalanceDirection(BalanceDirection.DEBIT.getValue());
							merchantLogDetailList.add(dtail);
						}
						if(merchantLogDetailList.size()>0){
							merchantLogDetailService.batchInsertMerchantLogDetails(merchantLogDetailList);
						}
					}
				}
			}
			if(iSettleId.endsWith("1")){
				settleBatchMsgModel = settleBatchMsgClient.queryMerchantSettleBatch(iSettleId, -1, 30);
				if(settleBatchMsgModel !=null){
					logger.info("补账务明细,结算单号:{},商户侧返回:{}", iSettleId,settleBatchMsgModel.toString());
					List<SettleMerchantModel> settleMerchantList = settleBatchMsgModel.getSettleMerchantList();
					int remainder = settleMerchantList.size()/500;
					int mod = settleMerchantList.size()%500;
					int pageNumber = 0;
					if(remainder == 0){
						pageNumber=1;
					}
					if(remainder != 0){
						pageNumber=remainder;
						if(mod !=0){
							pageNumber+=1;
						}
					}
					for(int i=0; i<pageNumber; i++){
						List<SettleMerchantModel> list = null;
						if(i + 1 == pageNumber){
							list = settleMerchantList.subList(i*500,settleMerchantList.size());
						}else{
							list = settleMerchantList.subList(i*500,(i+1)*500);
						}
						List<MerchantLogDetail> merchantLogDetailList = new ArrayList<MerchantLogDetail>();
						for(SettleMerchantModel settleMerchantModel : list){
							MerchantLogDetail dtail = new MerchantLogDetail();
							dtail.setDetailId(PrimaryKeyCreator.getMerchantLogDetailId());
							dtail.setMerchantId(Long.parseLong(settleMerchantModel.getMerchantId() + ""));
							dtail.setLogId(0L);
							dtail.setType(settleMerchantModel.getTransType());
							dtail.setTransNo(settleMerchantModel.getTransNo());
							dtail.setSettleId(iSettleId);
							dtail.setSettlementAmount(settleMerchantModel.getSuccessAmount());
							dtail.setCreateTime(DateUtil.stringToDate(DateUtil.getTodayYYYYMMDD_HHMMSS()));
							dtail.setUpdateTime(DateUtil.stringToDate(DateUtil.getTodayYYYYMMDD_HHMMSS()));
							dtail.setAccountDate(settleMerchantModel.getSuccessTime());
							dtail.setFeeAmount(String.valueOf(new BigDecimal(settleMerchantModel.getFeeAmount())));
							dtail.setBalanceAmount(settleMerchantModel.getRequestAmount());
							dtail.setBalanceDirection(BalanceDirection.CREBIT.getValue());
							merchantLogDetailList.add(dtail);
						}
						if(merchantLogDetailList.size()>0){
							merchantLogDetailService.batchInsertMerchantLogDetails(merchantLogDetailList);
						}
					}
				}
			}
			addMessage(redirectAttributes, iSettleId+"的账务明细已经补全");
		}else{
			addMessage(redirectAttributes, iSettleId+"的账务明细已经存在");
		}
		return "modules/payment/splMerchantLogDetailList";
	}

}
