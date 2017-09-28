package com.heepay.manage.modules.settleDic.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.settle.entity.SettleDicType;
import com.heepay.manage.modules.settle.service.SettleDicTypeService;
import com.heepay.manage.modules.settle.service.util.CommonUtil;

/**
*
* 描 述： 清结算系统类型管理表Controller
*
* 创 建 者： @author wangdong 
* 创建时间： 2016年10月12日17:08:14
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
@RequestMapping(value = "${adminPath}/settleDic/settleDicTypeQuery")
public class SettleDicTypeController extends BaseController {
	
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	private SettleDicTypeService settleDicTypeService;
	
	
	/**
	 * @方法说明：查询清结算系统类型管理List
	 * @时间：2016年10月12日17:08:19
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settleDic:settleDicType:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleDicType settleDicType,HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception{
		try {
			//使用cookie保存查询条件
			settleDicType = (SettleDicType) SaveConditions.result(settleDicType, "settleDicType", request, response);
			model = settleDicTypeService.findSettleDicTypePage(new Page<SettleDicType>(request, response),
					settleDicType,model);

			model.addAttribute("settleDicType",settleDicType);
			return "modules/settleDic/settleDicTypeList";
		} catch (Exception e) {
			logger.error("SettleDicTypeController list has a error:{查询清结算系统类型管理List错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * @方法说明：添加数据类型页面跳转
	 * @时间：2016年10月12日
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settleDic:settleDicType:view")
	@RequestMapping(value = "add")
	public String addSettleDicType(SettleDicType settleDicType, Model model) throws Exception{
		try {
			model = settleDicTypeService.goToSettleDicTypeAddJsp(settleDicType,model);
			return "modules/settleDic/settleDicTypeAdd";
		} catch (Exception e) {
			logger.error("SettleDicTypeController addSettleDicType has a error:{添加数据类型页面跳转错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * @方法说明：保存/修改数据类型
	 * @时间：2016年10月12日
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settleDic:settleDicType:edit")
	@RequestMapping(value = "save")
	public String saveSettleDicType(SettleDicType settleDicType, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception{
		try {
			//验证编码和名称
			Integer count = settleDicTypeService.findCodeTextBySettleDicTypeExist(settleDicType);
			if(count > 0){
				//存在相同的类型
				model.addAttribute("settleDicType", settleDicType);
				model.addAttribute("message", "保存字典类型编码为"+settleDicType.getCode()+",名称为："+settleDicType.getText()+"重复");
				//前端页面  类型条件显示
				CommonUtil.enumsShow(model,Constants.Clear.SETTLE);
				return "modules/settleDic/settleDicTypeAdd";
			}else{
				String message = "";//用于拼接错误信息
				if(StringUtils.isBlank(settleDicType.getCode())){
					message += " 字典编码为空";
				}
				if(StringUtils.isBlank(settleDicType.getText())){
					message += " 字典名称为空";				
				}
				if(StringUtils.isBlank(settleDicType.getStatus())){
					message += " 状态为空";		
				}
				
				if(StringUtils.isBlank(message)){
					settleDicTypeService.saveSettleDicType(settleDicType,request);
					addMessage(redirectAttributes, "保存字典类型'" + settleDicType.getText() + "'成功");
					return "redirect:" + adminPath + "/settleDic/settleDicTypeQuery?cache=1";
				}else{
					model = settleDicTypeService.goToSettleDicTypeAddJsp(settleDicType,model);
					model.addAttribute("message", "保存字典类型:"+message);
					return "modules/settleDic/settleDicTypeAdd";
				}
			}
		} catch (Exception e) {
			logger.error("SettleDicTypeController saveSettleDicType has a error:{保存/修改数据类型错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
}
