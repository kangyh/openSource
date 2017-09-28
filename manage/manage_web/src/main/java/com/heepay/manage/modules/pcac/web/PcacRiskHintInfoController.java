package com.heepay.manage.modules.pcac.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.pcac.DocType;
import com.heepay.enums.pcac.LegDocType;
import com.heepay.enums.pcac.MerchantRiskType;
import com.heepay.enums.pcac.RiskLevel;
import com.heepay.enums.pcac.ValidStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.pcac.entity.PcacBlacklist;
import com.heepay.manage.modules.pcac.entity.PcacRiskHintInfo;
import com.heepay.manage.modules.pcac.service.PcacBlacklistService;
import com.heepay.manage.modules.pcac.service.PcacRiskHintInfoService;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月6日下午3:20:21
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
@RequestMapping(value = "${adminPath}/pcac/pcacRiskHintInfo")
public class PcacRiskHintInfoController extends BaseController{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private PcacRiskHintInfoService pcacRiskHintInfoService;
	
	@RequiresPermissions("pcac:pcacRiskHintInfo:view")
	@RequestMapping(value = { "list", "" })
	public String list(PcacRiskHintInfo pcacRiskHintInfo, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		//风险信息等级
		List<EnumBean> levelList = Lists.newArrayList();
		for (RiskLevel checkFlg : RiskLevel.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			levelList.add(ct);
		}
		model.addAttribute("levelList", levelList);
		
		//风险类型
		List<EnumBean> riskTypeList = Lists.newArrayList();
		for (MerchantRiskType checkFlg : MerchantRiskType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			riskTypeList.add(ct);
		}
		model.addAttribute("riskTypeList", riskTypeList);
		
		List<EnumBean> validStatusList = Lists.newArrayList();
		//有效性
		for (ValidStatus checkFlg : ValidStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			validStatusList.add(ct);
		}
		model.addAttribute("validStatusList", validStatusList);
		
		//根据条件查询出符合的数据，显示到页面
		Page<PcacRiskHintInfo> page = pcacRiskHintInfoService.findPage(new Page<PcacRiskHintInfo>(request, response), pcacRiskHintInfo);
		
		for (PcacRiskHintInfo pcacRiskHint : page.getList()) {
			//法人证件类型
			if(StringUtils.isNotBlank(pcacRiskHint.getDocType())){
				pcacRiskHint.setDocType(DocType.labelOf(pcacRiskHint.getDocType()));
			}
			
			//法定代表人证件类型
			if(StringUtils.isNotBlank(pcacRiskHint.getLegDocType())){
				pcacRiskHint.setLegDocType(LegDocType.labelOf(pcacRiskHint.getLegDocType()));
			}
			
			//风险信息等级风险信息等级
			if(StringUtils.isNotBlank(pcacRiskHint.getLevel())){
				pcacRiskHint.setLevel(RiskLevel.labelOf(pcacRiskHint.getLevel()));
			}
			//个人风险类型
			if(StringUtils.isNotBlank(pcacRiskHint.getRiskType())){
				//判断个人还是商户
				pcacRiskHint.setRiskType(MerchantRiskType.labelOf(pcacRiskHint.getRiskType()));
			}
			
			//有效性
			if(StringUtils.isNotBlank(pcacRiskHint.getValidStatus())){
				pcacRiskHint.setValidStatus(ValidStatus.labelOf(pcacRiskHint.getValidStatus()));
			}
		}
		
		model.addAttribute("page", page);
		model.addAttribute("pcacRiskHintInfo", pcacRiskHintInfo);
		
		return "modules/pcac/pcacRiskHintInfoList";
	}
	
	/**
	 * @方法说明：清算协会风险提示信息导出
	 * @时间：2016年9月19日10:50:33
	 * @创建人：wangdong
	 */
	@RequiresPermissions("pcac:pcacBlacklist:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes,PcacRiskHintInfo pcacRiskHintInfo, HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			pcacRiskHintInfoService.exportPcacBlacklistExcel(pcacRiskHintInfo,request,response);
		} catch (Exception e) {
			logger.error("ClearingChannelRecordController export has a error:{清算协会风险提示信息导出出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
		
	}
}
