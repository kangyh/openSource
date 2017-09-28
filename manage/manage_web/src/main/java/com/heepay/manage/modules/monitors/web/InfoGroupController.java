package com.heepay.manage.modules.monitors.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.billingutils.Base64Utils;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.tpds.Status;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.monitors.entity.InfoGroup;
import com.heepay.manage.modules.monitors.service.InfoGroupService;
import com.heepay.manage.modules.monitors.web.util.SendType;
import com.heepay.manage.modules.sys.utils.UserUtils;


/***
 * 
* 
* 描    述：组管理
*
* 创 建 者： wangl
* 创建时间：  20 Mar 201715:39:50
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
@RequestMapping(value = "${adminPath}/monitors/infoGroup")
public class InfoGroupController extends BaseController {
	
    private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private InfoGroupService infoGroupService;
	
	/**
	 * 
	 * @方法说明：分页查询
	 * @时间：20 Mar 201715:44:40
	 * @创建人：wangl
	 */
	@RequiresPermissions("monitors:infoGroup:view")
	@RequestMapping(value = { "list", "" })
	public String list(InfoGroup infoGroup, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		Page<InfoGroup> page = infoGroupService.findPage(new Page<InfoGroup>(request, response), infoGroup);
		
		for (InfoGroup Info : page.getList()) {
			//状态  ENABLE(启用) DISABL(禁用)
			if(StringUtils.isNotBlank(Info.getStatus())){
				Info.setStatus(Status.labelOf(Info.getStatus()));
			}
			//发送类型
			if(StringUtils.isNotBlank(Info.getRemark())){
				Info.setRemark(SendType.labelOf(Info.getRemark()));
			}
		}
		logger.info("组管理------>{查询记录}"+ page.getList());
		model.addAttribute("page", page);
		model.addAttribute("infoGroup", infoGroup);
	
		return "modules/monitors/infoGroup";
	}
	
	
	/**
	 * 
	 * @方法说明：组管理添加和更新页面跳转
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("monitors:infoGroup:edit")
	@RequestMapping(value = "/addAndUpdate")
	public String addUpdate(@RequestParam(value="groupId",required = false) Integer groupId, 
							InfoGroup infoGroup, 
							Model model) {
		
				
		if(null != groupId){
			logger.info("银行证书密钥管理页面跳转------>{修改操作}"+groupId);
			infoGroup=infoGroupService.getEntity(groupId);
            
		}else{
			logger.info("银行证书密钥管理页面跳转------>{添加操作}");
			infoGroup=new InfoGroup();
		}
		
		//状态  ENABLE(启用) DISABL(禁用)
		List<EnumBean> status = Lists.newArrayList();
		for (Status checkFlg : Status.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			status.add(ct);
		}
		model.addAttribute("status", status);
		

		//发送类型
		List<EnumBean> sendType = Lists.newArrayList();
		for (SendType checkFlg : SendType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			sendType.add(ct);
		}
		model.addAttribute("sendType", sendType);
				
		model.addAttribute("infoGroup", infoGroup);
		return "modules/monitors/infoGroupAddAndUpdate";
	}
	
	
	/**
	 * 
	 * @方法说明：组管理页面
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("monitors:infoGroup:edit")
	@RequestMapping(value = "/saveEntity")
	public String save(@RequestParam(value="groupId",required = false) Integer groupId, 
			   		   InfoGroup infoGroup, 
					   RedirectAttributes redirectAttributes,
					   Model model){
		//解码
		String decodeData = Base64Utils.decodeData(infoGroup.getGroupCode());
		infoGroup.setGroupCode(decodeData);
		String message="";
		if(null != groupId){
			logger.info("组管理页面------>{修改操作}"+groupId);
			
			infoGroup.setUpdateTime(new Date());
			infoGroup.setUpdateUser(UserUtils.getUser().getName());
			int num=infoGroupService.updateEntity(infoGroup);
			if(num>0){
				message="修改操作成功";
			}else{
				message="修改操作失败";
			}
		}else{
			logger.info("组管理页面------>{添加操作}");
			try {
				infoGroup.setCreateTime(new Date());
				infoGroup.setCreateUser(UserUtils.getUser().getName());
				int	num=infoGroupService.saveEntity(infoGroup);
				if(num>0){
					message="保存操作成功";
				}else{
					message="保存操作失败";
				}
			} catch (Exception e) {
				logger.info("组管理页面------>{保存失败}"+e.getMessage());
			}
		}
		
		addMessage(redirectAttributes, message);
		return "redirect:"+Global.getAdminPath()+"/monitors/infoGroup";
		
	}

	/**
	 * 
	 * @方法说明：组管理删除
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("monitors:infoGroup:edit")
	@RequestMapping(value = "/delete/{groupId}")
	public String delete(@PathVariable("groupId") Integer groupId,RedirectAttributes redirectAttributes) {
		
		try {
			int num=infoGroupService.delete(groupId);
			if(num>0){
				addMessage(redirectAttributes, "删除成功");
			}else{
				addMessage(redirectAttributes, "删除失败");
			}
		} catch (Exception e) {
			addMessage(redirectAttributes, "删除失败");
		}
		return "redirect:"+Global.getAdminPath()+"/monitors/infoGroup";
	}
	
	/**
	 * 
	 * @方法说明：验重
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/changValue")
	public int  changValue(@RequestParam(value="groupCode") String groupCode) {
		
		//解码
		String decodeData = Base64Utils.decodeData(groupCode);
		int num = infoGroupService.countNum(decodeData);
		if(num > 0){
			//已存在
			return 200;
		}else{
			//不存在
			return 404;
		}
	}
	
	
}