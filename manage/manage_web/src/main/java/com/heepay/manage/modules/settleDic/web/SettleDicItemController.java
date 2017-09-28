package com.heepay.manage.modules.settleDic.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.settle.entity.SettleDicType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.settle.entity.SettleDicItem;
import com.heepay.manage.modules.settle.service.SettleDicItemService;

/**
 *
 * 描 述： 清结算字典明细管理Controller
 *
 * 创 建 者： @author wangdong 
 * 创建时间： 2016年10月12日17:08:44 
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
@RequestMapping(value = "${adminPath}/settleDic/settleDicItemQuery")
public class SettleDicItemController extends BaseController {

	private static final Logger logger = LogManager.getLogger();
	@Autowired
	private SettleDicItemService settleDicItemService;

	/**
	 * @方法说明：清结算字典明细管理List 
	 * @时间：2016年10月12日17:08:19
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settleDic:settleDicItem:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleDicItem settleDicItem, HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception{
		try {
			//使用cookie保存查询条件
			settleDicItem = (SettleDicItem) SaveConditions.result(settleDicItem, "settleDicItem", request, response);
			model = settleDicItemService.findSettleDicItemPage(new Page<SettleDicItem>(request, response),
					settleDicItem,model);

			model.addAttribute("settleDicItem",settleDicItem);
			return "modules/settleDic/settleDicItemList";
		} catch (Exception e) {
			logger.error("SettleDicItemController list has a error:{清结算字典明细管理List出错  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * @方法说明：添加字典明细页面跳转
	 * @时间：2016年10月13日 
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settleDic:settleDicItem:view")
	@RequestMapping(value = "add")
	public String addSettleDicItem(SettleDicItem settleDicItem, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try {
			model = settleDicItemService.goToSettleDicItemAddJsp(settleDicItem,model);
			return "modules/settleDic/settleDicItemAdd";
		} catch (Exception e) {
			logger.error("SettleDicTypeController addSettleDicItem has a error:{添加字典明细页面跳转出错  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * @方法说明：保存/修改字典明细
	 * @时间：2016年10月13日 
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settleDic:settleDicItem:edit")
	@RequestMapping(value = "save")
	public String saveSettleDicItem(SettleDicItem settleDicItem, Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			/*
			 * 因为前端页面有两个text的标签，所以传参数的时候会
			 * 把隐藏值和填写的值一起拼成字符串用“，”分隔传递过来，
			 * 其中隐藏值为空值，
			 */
			String[] textArr = settleDicItem.getText().split(",");
			for(String text : textArr){
				settleDicItem.setText(text);
			}
			//查询是否存在相同类型下相同编码和名称的类型明细
			Integer count = settleDicItemService.findBySettleDicItemExist(settleDicItem);
			if (count > 0) {
				//存在相同的类型明细同一个类型下
				model = settleDicItemService.settleDicItemExist(settleDicItem,model);
				return "modules/settleDic/settleDicItemAdd";
			} else {
				//不存在相同类型下的类型明细信息
				String message = "";//用于拼接错误信息
				if(null != settleDicItem.getTypeId() && StringUtils.isBlank(settleDicItem.getTypeId().toString())){
					message += " 明细所属类型为空";
				}
				if(StringUtils.isBlank(settleDicItem.getText())){
					message += " 明细名称为空";				
				}
				if(StringUtils.isBlank(settleDicItem.getValue())){
					message += " 明细编码为空";
				}
				if(StringUtils.isBlank(settleDicItem.getStatus())){
					message += " 状态为空";
				}
				//判断是否存在为空的字段  用于提示错误信息
				if(StringUtils.isBlank(message)){
					settleDicItemService.saveSettleDicItem(settleDicItem,request);
					addMessage(redirectAttributes, "保存字典类型'" + settleDicItem.getText() + "'成功");
					return "redirect:" + adminPath + "/settleDic/settleDicItemQuery?cache=1";
				}else{
					//存在错误信息
					model = settleDicItemService.goToSettleDicItemAddJsp(settleDicItem, model);
					model.addAttribute("message","保存的字典明细:"+message);
					return "modules/settleDic/settleDicItemAdd";
				}
			}
		} catch (Exception e) {
			logger.error("SettleDicTypeController saveSettleDicItem has a error:{添加或者修改字典明细错误  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * @方法说明：查询结算区间是否存在明细
	 * @时间：2016年10月13日 
	 * @创建人：wangdong
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkType")
	public String checkType(SettleDicItem settleDicItem) throws Exception{
		try {
			return settleDicItemService.chechTypeSettleTle(settleDicItem);
		} catch (Exception e) {
			logger.error("SettleDicTypeController checkCode has a error:{查询结算区间是否存在明细错误  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

}
