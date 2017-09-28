/**
 *  
 */
package com.heepay.manage.modules.sys.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.sys.entity.Office;
import com.heepay.manage.modules.sys.entity.Role;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.service.SystemService;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 *  
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/userBuss")
public class UserBussController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	/**
	 * 客服
	 */
	@RequiresPermissions("sys:userBuss:view")
	@RequestMapping(value = "formBuss")
	public String formBuss(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		return "modules/sys/userBussForm";
	}

	@RequiresPermissions("sys:userBuss:view")
	@RequestMapping(value = {"listBuss",""})
	public String listBuss(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		model.addAttribute("page", page);
		return "modules/sys/userBussList";
	}

	@RequiresPermissions("sys:userBuss:edit")
	@RequestMapping(value = "saveBuss")
	public String saveBuss(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		// 保存用户信息
		systemService.saveUser(user);
		return "redirect:" + adminPath + "/sys/userBuss/listBuss?repage";
	}
}
