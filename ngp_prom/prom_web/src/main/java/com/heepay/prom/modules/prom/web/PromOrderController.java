/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.web;

import com.heepay.common.util.StringUtils;
import com.heepay.prom.common.config.Global;
import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.web.BaseController;
import com.heepay.prom.modules.prom.entity.PromOrder;
import com.heepay.prom.modules.prom.service.PromOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 *
 * 描    述：订单管理Controller
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
@RequestMapping(value = "${adminPath}/prom/promOrder")
public class PromOrderController extends BaseController {

	@Autowired
	private PromOrderService promOrderService;
	private static final Logger logger = LogManager.getLogger();
	@ModelAttribute
	public PromOrder get(@RequestParam(required=false) String id) {
		PromOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = promOrderService.get(id);
		}
		if (entity == null){
			entity = new PromOrder();
		}
		return entity;
	}
	
	@RequiresPermissions("prom:promOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(PromOrder promOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PromOrder> page = promOrderService.findPage(new Page<PromOrder>(request, response), promOrder); 
		model.addAttribute("page", page);
		return "modules/prom/promOrderList";
	}

	@RequiresPermissions("prom:promOrder:view")
	@RequestMapping(value = "form")
	public String form(PromOrder promOrder, Model model) {
		model.addAttribute("promOrder", promOrder);
		return "modules/prom/promOrderForm";
	}

	@RequiresPermissions("prom:promOrder:edit")
	@RequestMapping(value = "save")
	public String save(PromOrder promOrder, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, promOrder)){
			return form(promOrder, model);
		}
		promOrderService.save(promOrder);
		addMessage(redirectAttributes, "保存订单管理成功");
		return "redirect:"+Global.getAdminPath()+"/prom/promOrder/?repage";
	}
	
	@RequiresPermissions("prom:promOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(PromOrder promOrder, RedirectAttributes redirectAttributes) {
		promOrderService.delete(promOrder);
		addMessage(redirectAttributes, "删除订单管理成功");
		return "redirect:"+Global.getAdminPath()+"/prom/promOrder/?repage";
	}
	
	
	@RequiresPermissions("prom:promOrder:edit")
	@RequestMapping(value ="onImport")
	public String onImport(RedirectAttributes redirectAttributes, HttpServletRequest request,
                           HttpServletResponse response){
		return "modules/prom/promOrderUpload";
	}
	
	@RequiresPermissions("prom:promOrder:edit")
	@RequestMapping(value ="upload")
	public String loadModel(RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile file, HttpServletRequest request,
                            HttpServletResponse response) throws IOException{
		    logger.info("订单数据----->{上传文件入库}--->{applicationId}=");
			Map<String,Object> msg=promOrderService.loadModel(file);
			addMessage(redirectAttributes, "订单数据成功上传"+msg.get("msg").toString());
			logger.info("订单数据上传结果:"+msg);
		//添加页面操作
		return "redirect:"+Global.getAdminPath()+"/prom/promOrder";
		
	}
	
	@RequiresPermissions("prom:promOrder:view")
	@RequestMapping(value ="detail")
	public String detail(RedirectAttributes redirectAttributes, HttpServletRequest request,
                         HttpServletResponse response, Model model){
		 String id = request.getParameter("id");
		 PromOrder promOrder = promOrderService.get(id);
		 model.addAttribute("promOrder", promOrder);
		return "modules/prom/promOrderDetail";
	}

}