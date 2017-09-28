/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.ordersum.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.heepay.manage.modules.cbms.entity.CbmsOrderSum;
import com.heepay.manage.modules.cbms.service.CbmsOrderSumService;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * 描    述：导入文件查询Controller
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
@RequestMapping(value = "${adminPath}/cbms/cbmsOrderSum")
public class CbmsOrderSumController extends BaseController {

	@Autowired
	private CbmsOrderSumService cbmsOrderSumService;
	/**
	 * @param id
	 * @return
	 * @discription 根据id查询数据库对象（页面发送请求第一时间执行此方法）
	 * @author 牛俊鹏
	 * @created 2017年2月6日
	 */
	@ModelAttribute
	public CbmsOrderSum get(@RequestParam(required=false) String id) {
		logger.info("页面传入的id为"+id);
		CbmsOrderSum entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cbmsOrderSumService.get(id);
		}
		if (entity == null){
			entity = new CbmsOrderSum();
		}
		return entity;
	}
	/**
	 * @return
	 * @discription 列表展示
	 * @author 牛俊鹏
	 * @created 2017年1月
	 */
	@RequiresPermissions("cbms:cbmsOrderSum:view")
	@RequestMapping(value = {"list", ""})
	public String list(CbmsOrderSum cbmsOrderSum, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("条件查询对象为"+cbmsOrderSum.toString());
		//分页查询
		Page<CbmsOrderSum> page = cbmsOrderSumService.findPageimport(new Page<CbmsOrderSum>(request, response), cbmsOrderSum);
		logger.info("分页page对象为"+page.toString());
		List<CbmsOrderSum> newlist = new ArrayList<>();
		for(CbmsOrderSum currency : page.getList() ){
			if (null != currency) {
				if (StringUtils.isNotBlank(currency.getStatus())) {
					//判断状态 1为成功 2为失败
					currency.setStatus(currency.getStatus().equals("2")? "失败":"成功");
				}
			}
			newlist.add(currency);
		}
		page.setList(newlist);
		model.addAttribute("page", page);
		return "modules/cbms/ordersum/cbmsOrderSumList";
	}
	/**
	 * @return
	 * @discription 跳转添加修改页面参数为cbmsOrderSum
	 * @author 牛俊鹏
	 * @created 2017年1月
	 */
	@RequiresPermissions("cbms:cbmsOrderSum:view")
	@RequestMapping(value = "form")
	public String form(CbmsOrderSum cbmsOrderSum, Model model) {
		//验证数据是否有效（无效）然后走这个方法跳回修改添加页面提示错误
		model.addAttribute("cbmsOrderSum", cbmsOrderSum);
		return "modules/cbms/ordersum/cbmsOrderSumForm";
	}
	/**
	 * @return
	 * @discription 修改操作  参数为cbmsOrderSum
	 * @author 牛俊鹏
	 * @created 2017年1月
	 */
	@RequiresPermissions("cbms:cbmsOrderSum:edit")
	@RequestMapping(value = "save")
	public String save(CbmsOrderSum cbmsOrderSum, Model model, RedirectAttributes redirectAttributes) {
		logger.info("添加修改操作对象为"+cbmsOrderSum.toString());
		//验证数据是否有效
		if (!beanValidator(model, cbmsOrderSum)){
			return form(cbmsOrderSum, model);
		}//根据id（null或者存在）判断是添加还是修改操作
		cbmsOrderSumService.save(cbmsOrderSum);
		addMessage(redirectAttributes, "保存导入文件查询成功");
		return "redirect:"+Global.getAdminPath()+"/cbms/cbmsOrderSum/?repage";
	}
	

}