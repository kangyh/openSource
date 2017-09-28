/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.customcode.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.modules.cbms.entity.CbmsCustomCodeSuper;
import com.heepay.manage.modules.cbms.service.CbmsCustomCodeSuperService;
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
import com.heepay.manage.modules.cbms.entity.CbmsCustomCode;
import com.heepay.manage.modules.cbms.service.CbmsCustomCodeService;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 描    述：海关代码信息Controller
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
@RequestMapping(value = "${adminPath}/cbms/cbmsCustomCode")
public class CbmsCustomCodeController extends BaseController {

	@Autowired
	private CbmsCustomCodeService cbmsCustomCodeService;
	@Autowired
	private CbmsCustomCodeSuperService cbmsCustomCodeSuperService;
	/**
	 * @param id
	 * @return
	 * @discription 根据id查询数据库对象（页面发送请求第一时间执行此方法）
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@ModelAttribute
	public CbmsCustomCode get(@RequestParam(required=false) String id) {
		logger.info("查询的ID号为:"+id);
		CbmsCustomCode entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cbmsCustomCodeService.get(id);
		}
		if (entity == null){
			entity = new CbmsCustomCode();
		}
		return entity;
	}
	/**
	 * @param cbmsCustomCodeSuper
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @discription 列表展示
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@RequiresPermissions("cbms:cbmsCustomCode:view")
	@RequestMapping(value = {"list", ""})
	//分页查询
	public String list(CbmsCustomCodeSuper cbmsCustomCodeSuper, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("条件查询为:"+cbmsCustomCodeSuper.toString());
		Page<CbmsCustomCodeSuper> page = cbmsCustomCodeSuperService.findPage(new Page<CbmsCustomCodeSuper>(request, response), cbmsCustomCodeSuper);
		List<CbmsCustomCode> customCodesList = cbmsCustomCodeService.customCodeList();
		logger.info("分页page对象为:"+cbmsCustomCodeSuper.toString());
		List<CbmsCustomCodeSuper> newlist = new ArrayList<>();
		for(CbmsCustomCodeSuper currency : page.getList() ){
			if (null != currency) {
				if (StringUtils.isNotBlank(currency.getStatus())) {
						currency.setStatus(currency.getStatus().equals("0")? "启用":"禁用");

					}
			}
			newlist.add(currency);
		}
		page.setList(newlist);
		model.addAttribute("page", page);
		model.addAttribute("customCodesList", customCodesList);
		return "modules/cbms/customsetting/cbmsCustomCodeList";
	}

	/**
	 * @param cbmsCustomCode
	 * @param model
	 * @return
	 * @discription 跳转共同的添加修改页面
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@RequiresPermissions("cbms:cbmsCustomCode:view")
	@RequestMapping(value = "form")
	public String form(CbmsCustomCode cbmsCustomCode, Model model) {
		//验证数据是否有效（无效）然后走这个方法跳回修改添加页面提示错误
		model.addAttribute("cbmsCustomCode", cbmsCustomCode);
		return "modules/cbms/customsetting/cbmsCustomCodeForm";
	}

	@RequiresPermissions("cbms:cbmsCustomCode:edit")
	@RequestMapping(value = "save")
	public String save(CbmsCustomCode cbmsCustomCode, Model model, RedirectAttributes redirectAttributes) {
		logger.info("修改添加操作对象为:"+cbmsCustomCode.toString());
		//校验数据有效
		if (!beanValidator(model, cbmsCustomCode)){
			return form(cbmsCustomCode, model);
		}
		//根据id（null或者存在）判断是添加还是修改操作
		cbmsCustomCodeService.save(cbmsCustomCode);
		addMessage(redirectAttributes, "保存海关代码信息成功");
		return "redirect:"+Global.getAdminPath()+"/cbms/cbmsCustomCode/?repage";
	}
	/**
	 * @param cbmsCustomCode
	 * @param redirectAttributes
	 * @return
	 * @discription 删除操作暂时隐藏
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@RequiresPermissions("cbms:cbmsCustomCode:edit")
	@RequestMapping(value = "delete")
	public String delete(CbmsCustomCode cbmsCustomCode, RedirectAttributes redirectAttributes) {
		logger.info("删除操作对象为:"+cbmsCustomCode.toString());
		cbmsCustomCodeService.delete(cbmsCustomCode);
		addMessage(redirectAttributes, "删除海关代码信息成功");
		return "redirect:"+Global.getAdminPath()+"/cbms/cbmsCustomCode/?repage";
	}
}