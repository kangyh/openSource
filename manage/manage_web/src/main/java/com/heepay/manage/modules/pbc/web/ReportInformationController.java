package com.heepay.manage.modules.pbc.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.enums.pbc.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.pbc.entity.PbcFeedBack;
import com.heepay.manage.modules.pbc.service.PbcFeedBackService;

/**
 * *
 * 
 * 
 * 描 述： 上报信息controller
 *
 * 创 建 者： wangjie 创建时间： 2016年12月17日下午5:30:12 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/pbc/reportInformation")
public class ReportInformationController extends BaseController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private PbcFeedBackService pbcFeedBackService;
	

	@RequiresPermissions("pbc:PbcTransQquery:view")
	@RequestMapping(value = { "list", "" })
	public String list(PbcFeedBack pbcFeedBack, HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		try {
			
			String status = pbcFeedBack.getStatus();
			if(StringUtil.isBlank(status)){
				pbcFeedBack.setQueryXml(ReportStatus.SUCCESE_S.getValue());//上报成功
				logger.info("查询上报查看页面的操作----->{查询出未上报成功的数据}"+pbcFeedBack.getQueryXml());
			}else if(ReportStatus.SUCCESE_S.getValue().equals(status)){
				pbcFeedBack.setStatus("");//去除重复的查询条件
				pbcFeedBack.setQueryYes(ReportStatus.SUCCESE_S.getValue());
				model.addAttribute("showStatu", "hide");
				logger.info("查询上报查看页面的操作----->{查询上报成功的数据}"+pbcFeedBack.getQueryYes());
			}else if(ReportStatus.NOREPORTED.getValue().equals(status)){   //未上报
				pbcFeedBack.setStatus("");//去除重复的查询条件
				pbcFeedBack.setQueryYes(ReportStatus.NOREPORTED.getValue());
			}else if(ReportStatus.FREPORTED.getValue().equals(status)){    //上报失败
				pbcFeedBack.setStatus("");//去除重复的查询条件
				pbcFeedBack.setQueryYes(ReportStatus.FREPORTED.getValue());
			}
			//主动上报，查询出符合的上报类型，并且没有上报成功或者上报失败的审核失败的
			Page<PbcFeedBack> page = pbcFeedBackService.findPage(new Page<PbcFeedBack>(request, response), pbcFeedBack);
					
			logger.info("查询上报查看页面的操作----->{数据}"+page.getList());
			
			List<PbcFeedBack> tempList = new ArrayList<PbcFeedBack>();
			for (PbcFeedBack feedBack : page.getList()) {
				
				//状态（S：上报成功 F：上报失败 N：未上报）
				if(StringUtils.isNotBlank(feedBack.getStatus())){
					feedBack.setStatus(ReportStatus.labelOf(feedBack.getStatus()));
				}
				
				//参数类型
				if(StringUtils.isNotBlank(feedBack.getParamType())){
					if (StringUtils.equals(feedBack.getRequestEventType(), RequestEventType.CASE_REPORT.getValue())) {
						// 支付账户交易明细查询
						feedBack.setParamType(PbcStaticParamType.labelOf(feedBack.getParamType()));
					}else if (StringUtils.equals(feedBack.getRequestEventType(),RequestEventType.ABNORMAL_ACCOUNTS.getValue())) {
						// 账户主体详情查询
						feedBack.setParamType(PbcStaticParamType.labelOf(feedBack.getParamType()));
					}else if (StringUtils.equals(feedBack.getRequestEventType(),RequestEventType.ACCOUNTS_INVOLVED.getValue())) {
						// 账户动态查询
						feedBack.setParamType(PbcDynamicParamType.labelOf(feedBack.getParamType()));
					}else if (StringUtils.equals(feedBack.getRequestEventType(),RequestEventType.EXCEPTION_EVENTS.getValue())) {
						// 账户动态查询解除
					}else if (StringUtils.equals(feedBack.getRequestEventType(),RequestEventType.TRANSNO_ACCOUNT.getValue())) {
						// 按交易流水号查询银行卡/支付帐号
						feedBack.setParamType(PbcTransCardParamType.labelOf(feedBack.getParamType()));
					}else if (StringUtils.equals(feedBack.getRequestEventType(),RequestEventType.PAYMENY_ACCOUNT.getValue())) {
						// 关联全支付账号查询
						feedBack.setParamType(PbcAccountParamType.labelOf(feedBack.getParamType()));
					}
				}
				
				//反馈类型
				if(StringUtils.isNotBlank(feedBack.getRequestEventType())){
					feedBack.setQueryXml(feedBack.getRequestEventType());//设置页面链接判断的条件
					feedBack.setRequestEventType(RequestEventType.labelOf(feedBack.getRequestEventType()));
				}
				
				
				
				//状态（S：上报成功 F：上报失败 N：未上报）
				if(StringUtils.isNotBlank(feedBack.getRiskStatus())){
					feedBack.setRiskStatus(RiskAuditStatus.labelOf(feedBack.getRiskStatus()));
				}
				tempList.add(feedBack);
			}
			page.setList(tempList);
			
			// 上报事件类型
			List<EnumBean> pETList = Lists.newArrayList();
			for (RequestEventType pbcEventType : RequestEventType.values()) {
				EnumBean ct = new EnumBean();
				ct.setValue(pbcEventType.getValue());
				ct.setName(pbcEventType.getContent());
				pETList.add(ct);
			}
			model.addAttribute("pETList", pETList);

			// 处理状态
			List<EnumBean> pHSTList = Lists.newArrayList();
			for (ReportStatus pbcHandleStatus : ReportStatus.values()) {
				EnumBean ct = new EnumBean();
				ct.setValue(pbcHandleStatus.getValue());
				ct.setName(pbcHandleStatus.getContent());
				pHSTList.add(ct);
			}
			model.addAttribute("pHSTList", pHSTList);

			// 风控审核状态
			List<EnumBean> riskList = Lists.newArrayList();
			for (RiskAuditStatus pbcHandleStatus : RiskAuditStatus.values()) {
				EnumBean ct = new EnumBean();
				ct.setValue(pbcHandleStatus.getValue());
				ct.setName(pbcHandleStatus.getContent());
				riskList.add(ct);
			}
			model.addAttribute("riskList", riskList);
			// 根据条件查询出符合的数据，显示到页面
					
					
			pbcFeedBack.setStatus(pbcFeedBack.getQueryYes());//将查询条件返回页面显示
			model.addAttribute("page",page);
			model.addAttribute("pbcFeedBack", pbcFeedBack);
			
			
			return "modules/pbc/reportInformation";
		} catch (Exception e) {
			logger.error("ReportInformation list has a error:{查询上报交易信息记录List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
		
	}

}
