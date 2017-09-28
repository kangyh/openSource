/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.currency.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.utils.DateUtil;
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
import com.heepay.manage.modules.cbms.entity.CbmsCurrencysetting;
import com.heepay.manage.modules.cbms.service.CbmsCurrencysettingService;

import java.util.Date;
import java.util.List;


/**
 *
 * 描    述：币种信息管理Controller
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
 * 审核描述：ceshi
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cbms/cbmsCurrencysetting")
public class CbmsCurrencysettingController extends BaseController {

	@Autowired
	private CbmsCurrencysettingService cbmsCurrencysettingService;
	/**
	 * @param id
	 * @return
	 * @discription 根据id查询数据库对象（页面发送请求第一时间执行此方法）
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@ModelAttribute
	public CbmsCurrencysetting get(@RequestParam(required=false) String id) {
		logger.info("查询的ID号为:"+id);
		CbmsCurrencysetting entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cbmsCurrencysettingService.get(id);
		}
		if (entity == null){
			entity = new CbmsCurrencysetting();
		}
		return entity;
	}
	/**
	 * @param cbmsCurrencysetting
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @discription 列表展示
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@RequiresPermissions("cbms:cbmsCurrencysetting:view")
	@RequestMapping(value = {"list", ""})
	public String list(CbmsCurrencysetting cbmsCurrencysetting, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("条件查询为:"+cbmsCurrencysetting.toString());
		//分页查询
		Page<CbmsCurrencysetting> page = cbmsCurrencysettingService.findPage(new Page<CbmsCurrencysetting>(request, response), cbmsCurrencysetting);
		logger.info("分页列表为:"+cbmsCurrencysetting.toString());
		List<CbmsCurrencysetting> list = Lists.newArrayList();
		for(CbmsCurrencysetting currency : page.getList() ){
			if (null != currency) {
				if (StringUtils.isNotBlank(currency.getStatus())) {
					//状态（1有效，0无效）
					currency.setStatus(currency.getStatus().equals("1")? "启用":"禁用");
				}
			}
			if(null != currency){

				//判断修改人id是否为null，根据id获取用户的name
				if(currency.getUpdateUserId() != null) {
					//根据UserId，查询用户名称
					String userName = UserUtils.get(currency.getUpdateUserId()).getName();
					currency.setUpdateUserId(userName);
				}
			}
			list.add(currency);
		}
		page.setList(list);
		model.addAttribute("page", page);
		return "modules/cbms/currency/cbmsCurrencysettingList";
	}
	/**
	 * @param cbmsCurrencysetting
	 * @param model
	 * @return
	 * @discription 跳转修改添加页面
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@RequiresPermissions("cbms:cbmsCurrencysetting:view")
	@RequestMapping(value = "form")
	public String form(CbmsCurrencysetting cbmsCurrencysetting, Model model) {
		//验证数据是否有效（无效）然后走这个方法跳回修改添加页面提示错误
		model.addAttribute("cbmsCurrencysetting", cbmsCurrencysetting);
		return "modules/cbms/currency/cbmsCurrencysettingForm";
	}
	/**
	 * @param cbmsCurrencysetting
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @discription 隐藏的添加修改操作
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@RequiresPermissions("cbms:cbmsCurrencysetting:edit")
	@RequestMapping(value = "save")
	public String save(CbmsCurrencysetting cbmsCurrencysetting, Model model, RedirectAttributes redirectAttributes) {
		logger.info("修改添加操作的对象为:"+cbmsCurrencysetting.toString());
		//设置录入人和录入时间
		//如果ID号为空，则表示新增币种
		if(cbmsCurrencysetting.getCurrencyId() == null){
		/*	cbmsCurrencysetting.setCreateBy(UserUtils.getUser());*/
			cbmsCurrencysetting.setInputuserId(UserUtils.getUser().getId());//录入人
			cbmsCurrencysetting.setCreatedTime(new Date());
			//设置状态/状态（1有效，0无效）
			cbmsCurrencysetting.setStatus("1");
		}else{
			//则表示修改币种
			cbmsCurrencysetting.setUpdateTime(new Date());
			cbmsCurrencysetting.setUpdateUserId(UserUtils.getUser().getId());//修改人
			cbmsCurrencysetting.setStatus("1");
		}
		//数据校验是否有效
		if (!beanValidator(model, cbmsCurrencysetting)){
			return form(cbmsCurrencysetting, model);
		}//根据id（null或者存在）判断是添加还是修改操作
		cbmsCurrencysettingService.save(cbmsCurrencysetting);
		addMessage(redirectAttributes, "保存币种信息成功");
		return "redirect:"+Global.getAdminPath()+"/cbms/cbmsCurrencysetting/?repage";
	}
}