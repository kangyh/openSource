package com.heepay.manage.modules.pbc.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.pbc.PbcEventType;
import com.heepay.enums.pbc.ReportStatus;
import com.heepay.enums.pbc.RiskAuditStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.pbc.entity.PbcFeedBack;
import com.heepay.manage.modules.pbc.service.PbcFeedBackService;

/**
 * *
 * 
 * 
 * 描 述： 举报及验证
 *
 * 创 建 者： wangjie 
 * 创建时间： 2016年12月19日上午10:51:22 
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
@RequestMapping(value = "${adminPath}/pbc/verification")
public class VerificationController extends BaseController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private PbcFeedBackService pbcFeedBackService;

	@RequiresPermissions("pbc:pbcFeedBack:view")
	@RequestMapping(value = { "list", "" })
	public String list(PbcFeedBack pbcFeedBack, HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {

		String status = pbcFeedBack.getStatus();
		if(StringUtil.isBlank(status)){//未上报或者第一次查询
			pbcFeedBack.setXml(ReportStatus.SUCCESE_S.getValue());//状态 S 上报成功
			logger.info("举报及验证查看页面的操作----->{查询出未上报成功的数据}"+pbcFeedBack.getXml());
		}else if(ReportStatus.SUCCESE_S.getValue().equals(status)){ //状态 S 上报成功
			pbcFeedBack.setYes(ReportStatus.SUCCESE_S.getValue());//状态 S 上报成功
			pbcFeedBack.setStatus("");//去除重复的查询条件
			request.setAttribute("status", ReportStatus.SUCCESE_S.getValue());
			logger.info("举报及验证查看页面的操作----->{查询上报成功的数据}"+pbcFeedBack.getYes());
		}else if(ReportStatus.FREPORTED.getValue().equals(status)){//状态F上报失败
			pbcFeedBack.setYes(ReportStatus.FREPORTED.getValue());//状态F上报失败
			pbcFeedBack.setStatus("");//去除重复的查询条件
			request.setAttribute("status", ReportStatus.FREPORTED.getValue());
			logger.info("举报及验证查看页面的操作----->{查询上报成功的数据}"+pbcFeedBack.getYes());
		}else if(ReportStatus.NOREPORTED.getValue().equals(status)){
			pbcFeedBack.setYes(ReportStatus.NOREPORTED.getValue());//状态N 未上报
			pbcFeedBack.setStatus("");//去除重复的查询条件
		}
		//主动上报，查询出符合的上报类型，并且没有上报成功或者上报失败的审核失败的
		Page<PbcFeedBack> page = pbcFeedBackService.findPage(new Page<PbcFeedBack>(request, response), pbcFeedBack);
				
		logger.info("举报及验证查看页面的操作----->{数据}"+page.getList());
		
		List<PbcFeedBack> tempList = new ArrayList<PbcFeedBack>();
		for (PbcFeedBack feedBack : page.getList()) {
			
			//状态（S：上报成功 F：上报失败 N：未上报）
			if(StringUtils.isNotBlank(feedBack.getStatus())){
				feedBack.setStatus(ReportStatus.labelOf(feedBack.getStatus()));
			}
			
			//反馈类型
			if(StringUtils.isNotBlank(feedBack.getFeedType())){
				feedBack.setXml(feedBack.getFeedType());//设置页面链接判断的条件
				feedBack.setFeedType(PbcEventType.labelOf(feedBack.getFeedType()));
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
		for (PbcEventType pbcEventType : PbcEventType.values()) {
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
				
				
		pbcFeedBack.setStatus(pbcFeedBack.getYes());//将查询条件返回页面显示
		model.addAttribute("page",page);
		model.addAttribute("pbcFeedBack", pbcFeedBack);
		return "modules/pbc/verification";

	}
}
