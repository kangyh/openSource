package com.heepay.manage.modules.pcac.web;

import java.util.ArrayList;
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

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.pcac.entity.PcacMerchantComparedInvestigation;
import com.heepay.manage.modules.pcac.entity.PcacMerchantComparedInvestigationDetail;
import com.heepay.manage.modules.pcac.entity.PcacRiskHintInfo;
import com.heepay.manage.modules.pcac.service.PcacMerchantComparedInvestigationDetailService;
import com.heepay.manage.modules.pcac.service.PcacMerchantComparedInvestigationService;
/**
 * *
 * 
* 
* 描    述：商户信息对比协查查看
*
* 创 建 者： wangjie
* 创建时间：  2017年3月7日上午10:03:32
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
@RequestMapping(value = "${adminPath}/pcac/merchantInvestigation")
public class PcacMerchantComparedInvestigationController extends BaseController{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private PcacMerchantComparedInvestigationService pcacMerchantComparedInvestigationService;
	
	@Autowired
	private PcacMerchantComparedInvestigationDetailService pcacMerchantComparedInvestigationDetailService;
	
	@RequiresPermissions("pcac:merchantInvestigation:view")
	@RequestMapping(value = { "list", "" })
	public String list(PcacMerchantComparedInvestigation merchantInvestigation, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		//根据条件查询出符合的数据，显示到页面
		Page<PcacMerchantComparedInvestigation> page = pcacMerchantComparedInvestigationService.findPage(new Page<PcacMerchantComparedInvestigation>(request, response), merchantInvestigation);
		List<PcacMerchantComparedInvestigation> pcacMerchantList = new ArrayList<PcacMerchantComparedInvestigation>();
		
		for (PcacMerchantComparedInvestigation pcacMerchantInvestigation : page.getList()) {
			List<PcacMerchantComparedInvestigationDetail> pcacMerchantInvestigationDetailList = pcacMerchantComparedInvestigationDetailService.getEntityByInvestigationId(pcacMerchantInvestigation.getInvestigationId());
			
			for (PcacMerchantComparedInvestigationDetail pcacMerchantComparedInvestigationDetail : pcacMerchantInvestigationDetailList) {
					pcacMerchantInvestigation.setMerchantCode(pcacMerchantComparedInvestigationDetail.getCode());
					pcacMerchantInvestigation.setMerchantName(pcacMerchantComparedInvestigationDetail.getName());
					pcacMerchantInvestigation.setMerchantLegalName(pcacMerchantComparedInvestigationDetail.getLegalName());
					pcacMerchantList.add(pcacMerchantInvestigation);
					logger.info("数据不正确,明细表中没有找到商户信息比对协查推送信息,id{}",pcacMerchantInvestigation.getInvestigationId());
				
			}
			
		}
		
		page.setList(pcacMerchantList);
		model.addAttribute("page", page);
		model.addAttribute("merchantInvestigation", merchantInvestigation);
		
		return "modules/pcac/merchantInvestigation";
	}
	
	/**
	 * @方法说明：商户信息对比协查查看
	 * @时间：2016年9月19日10:50:33
	 * @创建人：wangdong
	 */
	@RequiresPermissions("pcac:merchantInvestigation:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes,PcacMerchantComparedInvestigation pcacMerchantComparedInvestigation, HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			pcacMerchantComparedInvestigationService.exportpcacMerchantComparedInvestigationExcel(pcacMerchantComparedInvestigation,request,response);
		} catch (Exception e) {
			logger.error("PcacMerchantComparedInvestigationController export has a error:{商户信息对比协查导出出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
		
	}
}
