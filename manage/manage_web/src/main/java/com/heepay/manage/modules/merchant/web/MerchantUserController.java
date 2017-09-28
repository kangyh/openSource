/**
 *  
 */
package com.heepay.manage.modules.merchant.web;

import com.heepay.codec.CodecException;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.PrintWriterUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantUserCService;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * MerchantUserController
 * @author M.Z
 * @version V1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/merchant/merchantUser")
public class MerchantUserController extends BaseController {

	@Autowired
	private MerchantUserCService merchantUserService;
	
	private static JsonMapperUtil jsonMapper = JsonMapperUtil.buildNonDefaultMapper();	
	
	@ModelAttribute
	public MerchantUser get(@RequestParam(required=false) String id) {
		MerchantUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantUserService.get(id);
		}
		if (entity == null){
			entity = new MerchantUser();
		}
		return entity;
	}
	
	@RequiresPermissions("merchant:merchantUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantUser merchantUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MerchantUser> page = new Page<>(request, response);
		merchantUser.setPage(page);
		page.setOrderBy("createTime desc");
		List<MerchantUser> merchantUsers = merchantUserService.findList(merchantUser);
		//查询员工的公司
		List<MerchantUser> users = merchantUserService.selectCompanyForEmployee();
		List<MerchantUser> list = new ArrayList<>();
		for(MerchantUser merchantUserIn:merchantUsers){
			//判断信息认证状态
			MerchantUser merchantUserNew = merchantUserService.setInfoAuthStatus(merchantUserIn);
			//给员工赋值公司名
			merchantUserService.setCompanyForEmployee(users,merchantUserNew);
			list.add(merchantUserNew);
		}
		page.setList(EnumView.merchantUserShow(list));
		model.addAttribute("page", page);
		return "modules/merchant/merchantUserList";
	}

	@RequiresPermissions("merchant:merchantUser:view")
	@RequestMapping(value = "form")
	public String form(MerchantUser merchantUser, Model model) {
		model.addAttribute("merchantUser", merchantUser);
		return "modules/merchant/merchantUserForm";
	}

	@RequiresPermissions("merchant:merchantUser:view")
	@RequestMapping(value = "detail")
	public String detail(MerchantUser merchantUser, Model model) {
		//查询员工的公司
		List<MerchantUser> Users = merchantUserService.selectCompanyForEmployee();
		//判断信息认证状态
		merchantUser = merchantUserService.setInfoAuthStatus(merchantUser);
		//给员工赋值公司名
		merchantUserService.setCompanyForEmployee(Users,merchantUser);
		merchantUser = EnumView.merchantUserShow(merchantUser);
		model.addAttribute("merchantUser", merchantUser);
		return "modules/merchant/merchantUserDetail";
	}
	
	@RequiresPermissions("merchant:merchantUser:edit")
	@RequestMapping(value = "save")
	public String save(MerchantUser merchantUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantUser)){
			return form(merchantUser, model);
		}
		merchantUserService.save(merchantUser, false);
		addMessage(redirectAttributes, "保存merchantUser成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/merchantUser/?repage";
	}
	
	@RequiresPermissions("merchant:merchantUser:edit")
	@RequestMapping(value = "delete")
	public String delete(MerchantUser merchantUser, RedirectAttributes redirectAttributes) {
		merchantUserService.delete(merchantUser);
		addMessage(redirectAttributes, "删除merchantUser成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/merchantUser/?repage";
	}
	@RequiresPermissions("merchant:merchantUser:rc")
	@RequestMapping(value = "status")
	public void status(MerchantUser merchantUser,String password,HttpServletResponse response) {
		if(merchantUserService.checkPassword(password)){
			 String result =  merchantUserService.status(merchantUser);
			 String jsonString = jsonMapper.toJson(result);
			 PrintWriterUtil.reder(response, jsonString);
		}else{
			 String result = Constants.ERROR_PW;
			 String jsonString = jsonMapper.toJson(result);
			 PrintWriterUtil.reder(response, jsonString);
		}
	}
	@RequiresPermissions("merchant:merchantUser:rc")
	@RequestMapping(value = "sendEmailAddress")
	public void sendEmailAddress(String emailAddress,String loginName,String password,String resetWhat, HttpServletResponse response) throws UnsupportedEncodingException, CodecException {
		if(merchantUserService.checkPassword(password)){
			String result = merchantUserService.sendMail(emailAddress,loginName,resetWhat);
			String jsonString = jsonMapper.toJson(result);
			PrintWriterUtil.reder(response, jsonString);
		}else{
			String result = Constants.ERROR_PW;
			String jsonString = jsonMapper.toJson(result);
			PrintWriterUtil.reder(response, jsonString);
		}
	}
	@RequiresPermissions("merchant:merchantUser:rc")
	@RequestMapping(value = "unbundling")
	public void unbundling(MerchantUser merchantUser,String password,HttpServletResponse response) throws UnsupportedEncodingException, CodecException {
		if(merchantUserService.checkPassword(password)){
			String result = merchantUserService.unbundling(merchantUser);
			String jsonString = jsonMapper.toJson(result);
		    PrintWriterUtil.reder(response, jsonString);
		}else{
			String result = Constants.ERROR_PW;
			String jsonString = jsonMapper.toJson(result);
			PrintWriterUtil.reder(response, jsonString);
		}
	}

}