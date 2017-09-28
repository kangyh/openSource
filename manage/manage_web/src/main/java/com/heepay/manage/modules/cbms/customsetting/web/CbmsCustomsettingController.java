/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.customsetting.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.heepay.manage.modules.cbms.service.CbmsCustomCodeService;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.modules.cbms.entity.CbmsCustomsetting;
import com.heepay.manage.modules.cbms.service.CbmsCustomsettingService;


/**
 *
 * 描    述：海关信息设置Controller
 *
 * 创 建 者： @author 牛俊鹏
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
@RequestMapping(value = "${adminPath}/cbms/cbmsCustomsetting")
public class CbmsCustomsettingController extends BaseController {

	@Autowired
	private CbmsCustomsettingService cbmsCustomsettingService;
	@Autowired
	private CbmsCustomCodeService cbmsCustomCodeService;
	/**
	 * @param id
	 * @return
	 * @discription 根据id查询数据库对象（页面发送请求第一时间执行此方法）
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@ModelAttribute
	public CbmsCustomsetting get(@RequestParam(required=false) String id) {
		logger.info("页面传入的id值为:"+id);
		CbmsCustomsetting entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cbmsCustomsettingService.get(id);
		}
		if (entity == null){
			entity = new CbmsCustomsetting();
		}
		return entity;
	}
	/**
	 * @param cbmsCustomsetting
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @discription 列表展示
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@RequiresPermissions("cbms:cbmsCustomsetting:view")
	@RequestMapping(value = {"list", ""})
	public String list(CbmsCustomsetting cbmsCustomsetting, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("条件查询参数为:"+cbmsCustomsetting.toString());
		Page<CbmsCustomsetting> page = cbmsCustomsettingService.findPage(new Page<CbmsCustomsetting>(request, response), cbmsCustomsetting);
		logger.info("分页对象为:"+page.toString());
		for(CbmsCustomsetting currency : page.getList() ){
			if (null != currency) {
					currency.setStatus("1".equals(currency.getStatus())? "启用":"禁用");
			}
		}
		model.addAttribute("page", page);
		return "modules/cbms/customsetting/cbmsCustomsettingList";
	}
	/**
	 * @param cbmsCustomsetting
	 * @param model
	 * @return
	 * @discription 跳转添加修改页面
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@RequiresPermissions("cbms:cbmsCustomsetting:view")
	@RequestMapping(value = "form")
	public String form(CbmsCustomsetting cbmsCustomsetting, Model model) {
		logger.info("进入添加修改页面");
		model.addAttribute("cbmsCustomsetting", cbmsCustomsetting);

		return "modules/cbms/customsetting/cbmsCustomsettingForm";
	}
	/**
	 * @param cbmsCustomsetting
	 * @param model
	 * @return
	 * @discription 添加修改操作
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@RequiresPermissions("cbms:cbmsCustomsetting:edit")
	@RequestMapping(value = "save")
	public String save(CbmsCustomsetting cbmsCustomsetting, Model model, RedirectAttributes redirectAttributes) {
		logger.info("修改添加操作对象为:"+cbmsCustomsetting.toString());
		Date date=new Date();
		//判断id 是否有效且为空则是添加操作
		if(StringUtils.isBlank(cbmsCustomsetting.getId()) || cbmsCustomsetting.getId().equals("")){
			cbmsCustomsetting.setCreatedTime(date);
			cbmsCustomsetting.setUpdateTime(date);
			cbmsCustomsetting.setInputUserId(UserUtils.getUser().getId());
			cbmsCustomsetting.setStatus("2");
		}
		else{ //反之是修改操作
			cbmsCustomsetting.setUpdateTime(date);
			cbmsCustomsetting.setUpdateUserId(UserUtils.getUser().getId());
		}
		if (!beanValidator(model, cbmsCustomsetting)  ){
			return form(cbmsCustomsetting, model);
		}
		/**
		 * 保存支付公司在海关配置设置
		 */
		cbmsCustomsettingService.save(cbmsCustomsetting);
		/**
		 *如果为修改
		 * 注意，在对象CbmsCustomsetting中保持的“海关编码为对象”CbmsCustomCode中的custemid
		 */
		addMessage(redirectAttributes, "保存海关信息设置成功");//向页面显示提示信息
		return "redirect:"+Global.getAdminPath()+"/cbms/cbmsCustomsetting/?repage";
	}
	/**
	 * @param cbmsCustomsetting
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @discription 操作：禁用
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@RequiresPermissions("cbms:cbmsCustomsetting:edit")
	@RequestMapping(value = "disable")
	public String  disable (CbmsCustomsetting cbmsCustomsetting, Model model, RedirectAttributes redirectAttributes) {
		logger.info("禁用操作对象为:"+cbmsCustomsetting.toString());
		//数据验证是否有效
		if (!beanValidator(model, cbmsCustomsetting)){
			return "redirect:"+ Global.getAdminPath()+"/cbms/cbmsCustomsetting/?repage";
		}
		cbmsCustomsetting.setUpdateTime(new Date());
		cbmsCustomsetting.setStatus("2");//2为禁用1为启用

		cbmsCustomsettingService.save(cbmsCustomsetting);
		return "redirect:"+Global.getAdminPath()+"/cbms/cbmsCustomsetting/?repage";
	}
	/**
	 * @param cbmsCustomsetting
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @discription 操作：启用
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@RequiresPermissions("cbms:cbmsCustomsetting:edit")
	@RequestMapping(value = "enable")
	public String  enable (CbmsCustomsetting cbmsCustomsetting, Model model, RedirectAttributes redirectAttributes) {
		logger.info("启用操作对象为:"+cbmsCustomsetting.toString());
		//数据验证是否有效
		if (!beanValidator(model, cbmsCustomsetting)){
			return "redirect:"+ Global.getAdminPath()+"/cbms/cbmsCustomsetting/?repage";
		}
		cbmsCustomsetting.setUpdateTime(new Date());
		cbmsCustomsetting.setStatus("1");//2为禁用1为启用

		cbmsCustomsettingService.save(cbmsCustomsetting);
		return "redirect:"+Global.getAdminPath()+"/cbms/cbmsCustomsetting/?repage";
	}

	/**
	 * @param cbmsCustomsetting
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @discription 查询customsetting的数据  ajax请求
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@RequestMapping(value = "Customsettinglist")
	public void  Customsettinglist (CbmsCustomsetting cbmsCustomsetting, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		StringBuffer sf=new StringBuffer();
		String customNostr="";
		//查询海关信息设置表的所有数据
		List <CbmsCustomsetting> customsettinglist = cbmsCustomsettingService.findcustomsettinglist();
		logger.info("查询海关设置表所有数据为:"+customsettinglist.toString());
		List<String> str= new ArrayList<String>(); ;
		for(CbmsCustomsetting currency : customsettinglist ){
			if (null != currency) {
				//将海关编码取出append拼接
				sf.append(currency.getCustomNo()+",");
			}

		}
		customNostr=sf.toString();
		//将数据抛回页面（ajax回调函数获取）
		response.getWriter().write(customNostr);
	}
}