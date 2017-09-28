/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.allocate.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.modules.allocate.entity.AllocateMonitor;
import com.heepay.manage.modules.allocate.service.AllocateMonitorService;
import com.heepay.manage.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.heepay.manage.modules.sys.utils.UserUtils;


/**
 *
 * 描    述：调拨监听Controller
 *
 * 创 建 者： @author 王亚洪
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
@RequestMapping(value = "${adminPath}/allocate/allocateMonitor")
public class AllocateMonitorController extends BaseController {

	@Autowired
	private AllocateMonitorService allocateMonitorService;
	
	@ModelAttribute
	public AllocateMonitor get(@RequestParam(required=false) String id) {
	  AllocateMonitor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = allocateMonitorService.get(id);
		}
		if (entity == null){
			entity = new AllocateMonitor();
		}
		return entity;
	}
	
	@RequiresPermissions("allocate:allocateMonitor:view")
	@RequestMapping(value = {"list", ""})
	public String list(AllocateMonitor allocateMonitor, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AllocateMonitor> page = allocateMonitorService.findPage(new Page<AllocateMonitor>(request, response), allocateMonitor); 
		model.addAttribute("page", page);
		//判断是否需要添加阀值
		List<AllocateMonitor> list = allocateMonitorService.findList(null);
		model.addAttribute("nums", list.size());
		return "modules/allocate/allocateMonitorList";
	}

	@RequiresPermissions("allocate:allocateMonitor:view")
	@RequestMapping(value = "form")
	public String form(AllocateMonitor allocateMonitor, Model model) {
		model.addAttribute("allocateMonitor", allocateMonitor);
		return "modules/allocate/allocateMonitorForm";
	}

	@RequiresPermissions("allocate:allocateMonitor:edit")
	@RequestMapping(value = "save")
	public String save(AllocateMonitor allocateMonitor, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, allocateMonitor)){
			return form(allocateMonitor, model);
		}
		Principal principal = UserUtils.getPrincipal();
		allocateMonitor.setCreator(principal.getLoginName());
		allocateMonitor.setCreateTime(new Date());
		allocateMonitor.setUpdateTime(allocateMonitor.getCreateTime());
		allocateMonitorService.save(allocateMonitor);
		addMessage(redirectAttributes, "保存监听阀值成功");
		return "redirect:"+Global.getAdminPath()+"/allocate/allocateMonitor/?repage";
	}
	
	
	@RequiresPermissions("allocate:allocateMonitor:view")
  @RequestMapping(value = "details")
  public String details(String monitorId, HttpServletRequest request, HttpServletResponse response,Model model) {
	    AllocateMonitor allocateMonitor = allocateMonitorService.get(monitorId);
      model.addAttribute("allocateMonitor", allocateMonitor);
      return "modules/allocate/allocateMonitorDetails";
  }
	
	
	@RequiresPermissions("allocate:allocateMonitor:view")
  @RequestMapping(value = "toUpdatePage")
  public String toUpdatePage(String monitorId, HttpServletRequest request, HttpServletResponse response,Model model) {
	  AllocateMonitor allocateMonitor = allocateMonitorService.get(monitorId);
      model.addAttribute("allocateMonitor", allocateMonitor);
      return "modules/allocate/allocateMonitorUpdate";
  }
	
	
	@RequiresPermissions("allocate:allocateRecordReview:edit")
  @RequestMapping(value = "updateAllocateMonitor")
  public String updateAllocateMonitor(AllocateMonitor allocateMonitor, String remark, String flag, Model model, RedirectAttributes redirectAttributes) {
    Principal principal = UserUtils.getPrincipal();
    allocateMonitor.setModifier(principal.getLoginName());
    allocateMonitor.setUpdateTime(new Date());
    addMessage(redirectAttributes, "更新监听阀值成功");
    allocateMonitorService.updateAllocateMonitor(allocateMonitor);
    return "redirect:"+Global.getAdminPath()+"/allocate/allocateMonitor/?repage";
  }
	
	@RequestMapping(value="isAdminUser")
  public @ResponseBody boolean isAdminUser(){
    return UserUtils.getUser().isAdmin();
  }

}