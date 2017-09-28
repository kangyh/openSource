package com.heepay.manage.modules.tpds.web;

import java.util.Map;

/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.manage.modules.tpds.entity.TpdsFileLog;
import com.heepay.manage.modules.tpds.service.TpdsFileLogService;
import com.heepay.manage.modules.tpds.web.rpc.TpdsAdviceClient;


/**
 *
 * 描    述：存管日记Controller
 *
 * 创 建 者： @author wj
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
@RequestMapping(value = "${adminPath}/tpds/tpdsFileLog")
public class TpdsFileLogController extends BaseController {
	
	private static PropertiesLoader loader = new PropertiesLoader("billing.properties");
	
	@Autowired
	private TpdsFileLogService tpdsFileLogService;
	
	@Autowired
	private TpdsAdviceClient tpdsAdviceClient;
	
	
	@RequiresPermissions("tpds:tpdsFileLog:view")
	@RequestMapping(value = {"list", ""})
	public String list(TpdsFileLog tpdsFileLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TpdsFileLog> page = tpdsFileLogService.findPage(new Page<TpdsFileLog>(request, response), tpdsFileLog); 
		model.addAttribute("page", page);
		return "modules/tpds/tpdsFileLogForm";
	}
	
	
	@RequiresPermissions("tpds:tpdsFileLog:view")
	@RequestMapping(value ="/advice")
	public String advice(@RequestParam(value = "tpdsFileLogId", required = false) String tpdsFileLogId, Model model,
			RedirectAttributes redirectAttributes) {
		TpdsFileLog tpdsFileLog = tpdsFileLogService.get(tpdsFileLogId);
		String bankHostInterfaceIp = loader.getProperty("bankHostInterfaceIp");
		String result =  tpdsAdviceClient.httpAdvice(bankHostInterfaceIp, "JCT", tpdsFileLog.getFileName());
		if(StringUtils.isNotBlank(result)){
			tpdsFileLog.setOperPerson(UserUtils.getUser().getName());
			tpdsFileLogService.updateEntity(tpdsFileLog);
			Map<String, Object> adviceMap = JsonMapperUtil.nonEmptyMapper().fromJson(result, Map.class);
			String headMap  = adviceMap.get("respHeader").toString();
			if(StringUtils.isNotBlank(headMap)){
				String [] arrayMsg = headMap.split(",");
				String  msg = arrayMsg[5].split("=")[1];
				addMessage(redirectAttributes, msg);
			}
		}
		return "redirect:"+Global.getAdminPath()+"/tpds/tpdsFileLog";
	}

	
}