package com.heepay.manage.modules.riskLogs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.riskLogs.entity.RiskLogs;
import com.heepay.manage.modules.riskLogs.service.RiskLogsService;

/***
 * 
 * 
 * 描    述：分润明细查询
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
@RequestMapping(value = "${adminPath}/risk/logs")
public class RiskLogsController extends BaseController {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private RiskLogsService riskLogsService;
	
	
	//分润明细显示的查询方法
	@RequiresPermissions("riskLogs:RiskLogs:view")
	@RequestMapping(value = { "list", "" })
	public String list(RiskLogs riskLogs, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		Page<RiskLogs> page = riskLogsService.findPage(new Page<RiskLogs>(request, response), riskLogs);
		logger.info("操作日志表------>{查询记录}"+ page.getList());
		model.addAttribute("page", page);
		model.addAttribute("riskLogs", riskLogs);
	
		logger.info("操作日志表------>{}"+request);
		return "modules/riskLogs/riskLogs";
	}
}
