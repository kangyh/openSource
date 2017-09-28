package com.heepay.manage.modules.reconciliation.web.rule;

import com.google.common.collect.Lists;
import com.heepay.billingutils.Base64Utils;
import com.heepay.billingutils.FileType;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingBecomeEffect;
import com.heepay.enums.billing.CheckWay;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.DictList;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;
import com.heepay.manage.modules.reconciliation.entity.SettleRegexControl;
import com.heepay.manage.modules.reconciliation.entity.SettleRuleControl;
import com.heepay.manage.modules.reconciliation.entity.SettleRuleSecond;
import com.heepay.manage.modules.reconciliation.service.SettleRegexControlService;
import com.heepay.manage.modules.reconciliation.service.SettleRuleControlService;
import com.heepay.manage.modules.reconciliation.service.SettleRuleSecondService;
import com.heepay.manage.modules.reconciliation.web.util.RuleType;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.sys.utils.UserUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 *
 *
 * 描    述：规则控制查询
 *
 * 创 建 者： wangl
 * 创建时间：  2016年9月23日下午1:38:03
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
@RequestMapping(value = "${adminPath}/reconciliation/settleRuleControl")
public class SettleRuleControlUpdateController extends BaseController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SettleRuleControlService settleRuleControlService;

	//正则表达式
	@Autowired
	private SettleRegexControlService settleRegexControlService;

	//更新保存
	List<SettleRegexControl> saveList=null;

	//二代规则
	@Autowired
	private SettleRuleSecondService settleRuleSecondService;

	/**
	 * @方法说明：规则控制查询显示操作
	 * @时间： 2017-05-15 09:40 AM
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleRuleControl:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleRuleControl settleRuleControl,
					   HttpServletRequest request,
					   HttpServletResponse response,
					   Model model) {
		//使用cookie保存查询条件
		settleRuleControl = (SettleRuleControl) SaveConditions.result(settleRuleControl, "settleRuleControl", request, response);

		//查询通道合作方的内容
		List<EnumBean> dataEntityChannelType = DictList.channelType();
		EnumBean de = new EnumBean();
		de.setName(TransType.REAL_NAME.getContent());//"实名认证"
		de.setValue(TransType.REAL_NAME.getValue());//RENAME
		dataEntityChannelType.add(de);
		model.addAttribute("checkTypeList", dataEntityChannelType);

		Page<SettleRuleControl> page = settleRuleControlService.findPage(new Page<SettleRuleControl>(request, response),settleRuleControl);
		List<SettleChannelManager> list = settleRuleControlService.getBatchName();
		// 获取到通道管理表的通道名称和通道类型
		logger.info("规则控制处理开始------>", settleRuleControl);
		int size = list.size();
		if (size == 0) {
			model.addAttribute("batchList", null);
		} else {

			List<SettleRuleControl> clearingList = Lists.newArrayList();

			for (SettleChannelManager settleChannelManager : list) {
				SettleRuleControl settleRule = new SettleRuleControl();

				settleRule.setChannelCode(settleChannelManager.getChannelCode());
				settleRule.setChannelName(settleChannelManager.getChannelName());
				clearingList.add(settleRule);
			}
			model.addAttribute("checklist", clearingList);
			// 用来判断能不能添加
			model.addAttribute("batchList", clearingList);
		}

		List<SettleRuleControl> clearingCRList = Lists.newArrayList();
		for (SettleRuleControl clearingCR : page.getList()) {

			String channelCode = clearingCR.getChannelCode();
			String channelName = settleRuleControlService.getChannelName(channelCode);
			clearingCR.setChannelName(channelName);
			// 通道业务类型
			if (StringUtils.isNotBlank(clearingCR.getChannelType())) {
				//clearingCR.setChannelType(ChannelType.labelOf(clearingCR.getChannelType()));
				dataEntityChannelType.forEach(p -> {
					if(p.getValue().equals(clearingCR.getChannelType())){
						clearingCR.setChannelType(p.getName());
					}

				});
			}

			// status
			if (StringUtils.isNotBlank(clearingCR.getStatus())) {
				clearingCR.setStatus(BillingBecomeEffect.labelOf(clearingCR.getStatus()));
			}

			//对账方式
			if(StringUtils.isNotBlank(clearingCR.getSettleWay())){
				clearingCR.setSettleWay(CheckWay.labelOf(clearingCR.getSettleWay()));
			}

			clearingCRList.add(clearingCR);
		}
		page.setList(clearingCRList);

		// 生效标识
		List<EnumBean> becomngeEffect = Lists.newArrayList();
		for (BillingBecomeEffect checkFlg : BillingBecomeEffect.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			becomngeEffect.add(ct);
		}
		model.addAttribute("becomngeEffect", becomngeEffect);

		model.addAttribute("page", page);
		model.addAttribute("settleRuleControl", settleRuleControl);

		logger.info("规则控制处理结束------>", settleRuleControl);
		return "modules/reconciliation/CheckRules";
	}

	// 添加和更新的跳转的方法
	@RequiresPermissions("settle:settleRuleControl:edit")
	@RequestMapping(value = "/updateAndAdd")
	public String updateAndAdd(@RequestParam(value = "ruleControlId", required = false) Long ruleControlId,
                               SettleRuleControl rule,
							   Model model) {

		//查询通道合作方的内容
		List<EnumBean> dataEntityChannelType = DictList.channelType();
		EnumBean de = new EnumBean();
		de.setName(TransType.REAL_NAME.getContent());//"实名认证"
		de.setValue(TransType.REAL_NAME.getValue());//RENAME
		dataEntityChannelType.add(de);
		model.addAttribute("channelType", dataEntityChannelType);

		if(null == ruleControlId){ //添加方法
			rule = new SettleRuleControl();
            logger.info("执行了添加方法");
            // 获取下拉列表中的通道名称
            List<SettleChannelManager> channelName = settleRuleControlService.getBatchName();
            List<SettleRuleControl> List_channelName = Lists.newArrayList();
            for (SettleChannelManager settleChannelManager : channelName) {
                SettleRuleControl settleRule = new SettleRuleControl();
                settleRule.setChannelCode(settleChannelManager.getChannelCode());
                settleRule.setChannelName(settleChannelManager.getChannelName());
                List_channelName.add(settleRule);
            }
            model.addAttribute("List_channelName", List_channelName);

		}else{ //修改方法
			logger.info("执行了修改方法--->{条件}"+ruleControlId);
			long ruleId = ruleControlId;
			rule = settleRuleControlService.getEntity((int)ruleId);

			//支付单号
			if(rule.getPaymentId() !=null){
				long paymentId = rule.getPaymentId();
				//保存查询条件
				SettleRegexControl regex_paymentId=new SettleRegexControl();
				regex_paymentId.setRuleId(ruleControlId);//规则主键
				regex_paymentId.setRuleKey(paymentId);//规则key
				regex_paymentId.setRuleType(RuleType.COMM.getValue());//规则类型 COMM：一代规则
				List<SettleRegexControl> list_paymentId=settleRegexControlService.getEntityByList(regex_paymentId);
				model.addAttribute("list_paymentId", list_paymentId);
			}


			//银行流水号
			if(rule.getChannelNo() !=null){
				//银行流水号
				long channelNo = rule.getChannelNo();
				//保存查询条件
				SettleRegexControl regex_channelNo=new SettleRegexControl();
				regex_channelNo.setRuleId(ruleControlId); //规则主键
				regex_channelNo.setRuleKey(channelNo);//规则key
				regex_channelNo.setRuleType(RuleType.COMM.getValue());//规则类型 COMM：一代规则
				List<SettleRegexControl> list_channelNo=settleRegexControlService.getEntityByList(regex_channelNo);
				model.addAttribute("list_channelNo", list_channelNo);
			}

			//交易成本
			if(rule.getCostAmount() != null){
				long costAmount = rule.getCostAmount();
				//保存查询条件
				SettleRegexControl regex_costAmount=new SettleRegexControl();
				regex_costAmount.setRuleId(ruleControlId);//规则主键
				regex_costAmount.setRuleKey(costAmount);//规则key
				regex_costAmount.setRuleType(RuleType.COMM.getValue());//规则类型 COMM：一代规则
				List<SettleRegexControl> list_costAmount=settleRegexControlService.getEntityByList(regex_costAmount);
				model.addAttribute("list_costAmount", list_costAmount);
			}

			//通道金额
			if(rule.getSuccessAmount() != null){
				//通道金额
				long successAmount = rule.getSuccessAmount();
				//保存查询条件
				SettleRegexControl regex_successAmount=new SettleRegexControl();
				regex_successAmount.setRuleId(ruleControlId);//规则主键
				regex_successAmount.setRuleKey(successAmount);//规则key
				regex_successAmount.setRuleType(RuleType.COMM.getValue());//规则类型 COMM：一代规则
				List<SettleRegexControl> list_successAmount=settleRegexControlService.getEntityByList(regex_successAmount);
				model.addAttribute("list_successAmount", list_successAmount);
			}

			//优惠金额
			if(rule.getPromotionAmount()!=null) {
				long promotionAmount = rule.getPromotionAmount();
				//保存查询条件
				SettleRegexControl regex_promotionAmount = new SettleRegexControl();
				regex_promotionAmount.setRuleId(ruleControlId);//规则主键
				regex_promotionAmount.setRuleKey(promotionAmount);//规则key
				regex_promotionAmount.setRuleType(RuleType.COMM.getValue());//规则类型 COMM：一代规则
				List<SettleRegexControl> list_promotionAmount = settleRegexControlService.getEntityByList(regex_promotionAmount);
				model.addAttribute("list_promotionAmount", list_promotionAmount);
			}

			//交易状态
			if(rule.getTransStatus() !=null){
				long transStatus = rule.getTransStatus();
				//保存查询条件
				SettleRegexControl regex_transStatus = new SettleRegexControl();
				regex_transStatus.setRuleId(ruleControlId);//规则主键
				regex_transStatus.setRuleKey(transStatus);//规则key
				regex_transStatus.setRuleType(RuleType.COMM.getValue());//规则类型 COMM：一代规则
				List<SettleRegexControl> list_transStatus=settleRegexControlService.getEntityByList(regex_transStatus);
				model.addAttribute("list_transStatus", list_transStatus);
			}


			// 通道合作方
			if (StringUtils.isNotBlank(rule.getChannelCode())) {
				String channelCode = rule.getChannelCode();
				String channelName = settleRuleControlService.getChannelName(channelCode);
				rule.setChannelName(channelName);
			}
			// 通道业务类型
			if (StringUtils.isNotBlank(rule.getChannelType())) {
				//rule.setChannelTypeRule(ChannelType.labelOf(rule.getChannelType()));
				for (int i = 0,length = dataEntityChannelType.size(); i < length; i++) {
					EnumBean enumBean = dataEntityChannelType.get(i);
					if(enumBean.getValue().equals(rule.getChannelType())){
						rule.setChannelTypeRule(enumBean.getName());
						break;
					}
				}
			}

		}

		List<EnumBean> billType = Lists.newArrayList();
		// 账单类型
		for (FileType checkFlg : FileType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			billType.add(ct);
		}
		model.addAttribute("billType", billType);

		List<EnumBean> settleWay = Lists.newArrayList();
		// 对账方式
		for (CheckWay checkFlg : CheckWay.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			settleWay.add(ct);
		}
		model.addAttribute("settleWay", settleWay);

		// 状态
		List<EnumBean> statusRegx = Lists.newArrayList();
		for (BillingBecomeEffect checkFlg : BillingBecomeEffect.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			statusRegx.add(ct);
		}
		model.addAttribute("statusRegx", statusRegx);

		model.addAttribute("settleRuleControl", rule);

		return "modules/reconciliation/rule/checkRulesAddUpdate";
	}

	/**
	 * @方法说明：添加和更新
	 * @时间： 2017-05-15 10:55 AM
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleRuleControl:edit")
	@RequestMapping(value = "/save")
	public String save(@RequestParam(value = "ruleControlId", required = false) Long ruleControlId,
							   SettleRuleControl settleRuleControl,
					           RedirectAttributes redirectAttributes) {


		//判断是否和二代规则冲突
		SettleRuleSecond ruleSecond =  new SettleRuleSecond();
		ruleSecond.setChannelCode(settleRuleControl.getChannelCode());
		ruleSecond.setChannelType(settleRuleControl.getChannelType());
		ruleSecond.setBillType(settleRuleControl.getBillType());
		ruleSecond.setSettleWay(settleRuleControl.getSettleWay());

		SettleRuleSecond entityByConditon = settleRuleSecondService.getEntityByConditon(ruleSecond);
		if(null != entityByConditon){
			addMessage(redirectAttributes, "二代规则已存在请删除后再试!");
			return "redirect:" + Global.getAdminPath() + "/reconciliation/settleRuleControl?cache=1";
		}

		Long ruleId = settleRuleControl.getRuleControlId();
		if(null != ruleId){
			logger.info("规则控制执行了修改方法------->{}"+ruleControlId);
				settleRegexControlService.deleteByRule(ruleId,RuleType.COMM.getValue());
				settleRuleControl.setUpdateTime(new Date());
				settleRuleControl.setUpdateAuthor(UserUtils.getUser().getName());
				settleRuleControlService.updateEntity(settleRuleControl);
		}else{
			logger.info("规则控制执行了添加方法");
				try {
					settleRuleControl.setCreateTime(new Date());
					settleRuleControl.setCreateAuthor(UserUtils.getUser().getName());
					//保存入库
					settleRuleControlService.saveEntity(settleRuleControl);
					ruleId = settleRuleControl.getRuleControlId();
				} catch (Exception e) {
					logger.error("规则控制执行了添加/修改--->{异常}"+e.getMessage());
				}
			}

		//批量插入
		int num = breachAddRegex(settleRuleControl, ruleId);
		if(num == 1){
			addMessage(redirectAttributes, "操作成功");
		}else{
			addMessage(redirectAttributes, "操作失败");
		}

		return "redirect:" + Global.getAdminPath() + "/reconciliation/settleRuleControl?cache=1";
	}


	/**
	 * @方法说明:批量保存插入的条件
	 * @时间： 2017-05-15 10:43 AM
	 * @创建人：wangl
	 */
	private int breachAddRegex(SettleRuleControl settleRuleControl, Long ruleId) {
		if(null == saveList){
            saveList = new ArrayList<>();
        }

		//批量插入正则控制表
		//支付单号
		if(settleRuleControl.getPaymentId() !=null){
			//规则key
			long paymentId=settleRuleControl.getPaymentId();
			//交易成本
			String paymentIdName = settleRuleControl.getPaymentIdName();
			String paymentIdRule = settleRuleControl.getPaymentIdRule();
			if(StringUtils.isNoneBlank(paymentIdName) && StringUtils.isNoneBlank(paymentIdRule)){
				saveRegex(ruleId, paymentIdName, paymentIdRule,paymentId);
			}
		}

		//银行流水号
		if(settleRuleControl.getChannelNo() !=null){
			//规则key
			long channelNo=settleRuleControl.getChannelNo();
			//交易成本
			String channelNoName = settleRuleControl.getChannelNoName();
			String channelNoRule = settleRuleControl.getChannelNoRule();
			if(StringUtils.isNoneBlank(channelNoName) && StringUtils.isNoneBlank(channelNoRule)){
				saveRegex(ruleId, channelNoName, channelNoRule,channelNo);
			}
		}

		//交易成本
		if(settleRuleControl.getCostAmount() !=null){
            //规则key
            long costAmount=settleRuleControl.getCostAmount();
            //交易成本
            String costAmountName = settleRuleControl.getCostAmountName();
            String costAmountRule = settleRuleControl.getCostAmountRule();
            if(StringUtils.isNoneBlank(costAmountName) && StringUtils.isNoneBlank(costAmountRule)){
				saveRegex(ruleId, costAmountName, costAmountRule,costAmount);
            }
        }

		//通道金额
		if(settleRuleControl.getSuccessAmount() !=null){
			//规则key
			long successAmount=settleRuleControl.getSuccessAmount();

			//通道金额
			String successAmountName = settleRuleControl.getSuccessAmountName();
			String successAmountRule = settleRuleControl.getSuccessAmountRule();
			if(StringUtils.isNoneBlank(successAmountName) && StringUtils.isNoneBlank(successAmountRule)){
				saveRegex(ruleId, successAmountName, successAmountRule,successAmount);
			}
		}


		//优惠金额
		if(settleRuleControl.getPromotionAmount() !=null){
			//规则key
			long promotionAmount=settleRuleControl.getPromotionAmount();
			//优惠金额
			String promotionAmountName = settleRuleControl.getPromotionAmountName();
			String promotionAmountRule = settleRuleControl.getPromotionAmountRule();
			if(StringUtils.isNoneBlank(promotionAmountName) && StringUtils.isNoneBlank(promotionAmountRule)){
				saveRegex(ruleId, promotionAmountName, promotionAmountRule,promotionAmount);
			}
		}


		//交易状态
		if(settleRuleControl.getTransStatus() !=null){
			//规则key
			long transStatus=settleRuleControl.getTransStatus();
			//交易状态
			String transStatusName = settleRuleControl.getTransStatusName();
			String transStatusRule = settleRuleControl.getTransStatusRule();
			if(StringUtils.isNoneBlank(transStatusName) && StringUtils.isNoneBlank(transStatusRule)){
				saveRegex(ruleId, transStatusName, transStatusRule,transStatus);
			}
		}


		//批量插入
		if(saveList != null && !saveList.isEmpty()){
            try {
                settleRegexControlService.insetList(saveList);
                logger.info("规则控表批量插入-----{成功}");
				saveList = null;
				return 1;
			} catch (Exception e) {
                logger.error("规则控表批量插入失败------->{}", e.getMessage());
				saveList = null;
				return 0;
            }
        }else{
			return 1;
		}

	}


	/**
	 * @方法说明：删除正则表达式方法
	 * @时间： 2017-05-15 11:04 AM
	 * @创建人：wangl
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/delete")
	public boolean deleteEntityValue(@RequestParam(value = "deleteId", required = false) Long deleteId) {
		if(deleteId != null){
			try {
				int num=settleRegexControlService.deleteEntity(deleteId);
				logger.info("删除规则方法执行了------->{成功}",num);
				return true;
			} catch (Exception e) {
				logger.error("删除规则方法执行了------->{失败}", e.getMessage());
				return false;
			}
		}
		return false;
	}

	/**
	 * @方法说明：删除规则方法
	 * @时间： 2017-05-15 10:24 AM
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleRuleControl:edit")
	@RequestMapping(value = "/delete/{ruleControlId}")
	public String deleteEntity(@PathVariable(value = "ruleControlId") Long ruleControlId, RedirectAttributes redirectAttributes) {

		try {
			//级联删除子数据
			settleRegexControlService.deleteByRule(ruleControlId,RuleType.COMM.getValue());
			int num=settleRuleControlService.deleteEntity(ruleControlId);
			logger.info("删除规则方法执行了------->{成功}",num);
			addMessage(redirectAttributes, "删除成功");
		} catch (Exception e) {
			logger.error("删除规则方法执行了------->{失败}", e.getMessage());
			addMessage(redirectAttributes, "删除成功");
		}

		return "redirect:" + Global.getAdminPath() + "/reconciliation/settleRuleControl?cache=1";
	}

	/**
	 * @方法说明：正则表达式入库操作
	 * @throws
	 * @时间：2017年1月18日上午10:01:00
	 * @创建人：wangl
	 */
	private void saveRegex(Long ruleId, String breachName,String breachRule,Long id) {

		if (StringUtils.isNotBlank(breachName) && StringUtils.isNotBlank(breachRule)) {

			String[] splitEntityName = breachName.split(",");
			String[] splitEntityRule = breachRule.split(",");
			for (int i = 0, length = splitEntityName.length; i < length; i++) {
				//获取规则名称
				String regexName = splitEntityName[i];
				//获取规则表达式
				String regexShow = splitEntityRule[i];

				if(StringUtils.isNoneBlank(regexName) && StringUtils.isNoneBlank(regexShow)){
					//对base64加密的正则表达式进行解密
					String decodeData = Base64Utils.decodeData(regexShow);
					//保存入库
					SettleRegexControl settleRegexControl = new SettleRegexControl();
					settleRegexControl.setRegexName(regexName);//规则名称
					settleRegexControl.setRegexShow(decodeData);//正则表达式
					settleRegexControl.setRuleId(ruleId);//规则主键
					settleRegexControl.setCreateTime(new Date());//创建时间
					settleRegexControl.setCreateAuthor(UserUtils.getUser().getName());//创建时间
					settleRegexControl.setRuleKey(id);//规则key
					settleRegexControl.setRuleType(RuleType.COMM.getValue());
					//将正则表达式入库
					try {
						saveList.add(settleRegexControl);
					} catch (Exception e) {
						logger.info("正则控制表入库异常------{}", e.getMessage());
					}
				}
			}
		}
	}
}
