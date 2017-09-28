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
import com.heepay.enums.tpds.Status;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.monitors.entity.InfoGroup;
import com.heepay.manage.modules.monitors.entity.InfoMember;
import com.heepay.manage.modules.monitors.service.InfoGroupService;
import com.heepay.manage.modules.monitors.service.InfoMemberService;
import com.heepay.manage.modules.sys.utils.UserUtils;


/***
 * 
* 
* 描    述：成员管理
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
@RequestMapping(value = "${adminPath}/monitors/infoMember")
public class InfoMemberController extends BaseController {
	
    private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private InfoMemberService infoMemberService;
	
	@Autowired
	private InfoGroupService infoGroupService;
	
	/**
	 * 
	 * @方法说明：分页查询
	 * @时间：20 Mar 201715:44:40
	 * @创建人：wangl
	 */
	@RequiresPermissions("monitors:InfoMember:view")
	@RequestMapping(value = { "list", "" })
	public String list(InfoMember infoMember, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		Page<InfoMember> page = infoMemberService.findPage(new Page<InfoMember>(request, response), infoMember);
		
		for (InfoMember Info : page.getList()) {
			//翻译groupId在组管理中的名字
			InfoGroup entity = infoGroupService.getEntity(Info.getGroupId());
			Info.setGroupName(entity.getGroupName());
		}
		
		InfoGroup infoGroup=new InfoGroup();
		List<InfoGroup> list = infoGroupService.getList(infoGroup);
		//页面查询
		List<EnumBean> groupValue = Lists.newArrayList();
		for (InfoGroup info : list) {
			EnumBean ct = new EnumBean();
			ct.setValue(info.getGroupId()+"");
			ct.setName(info.getGroupName());
			groupValue.add(ct);
		}
		model.addAttribute("groupValue", groupValue);
		
		logger.info("成员管理------>{查询记录}"+ page.getList());
		model.addAttribute("page", page);
		model.addAttribute("infoMember", infoMember);
	
		return "modules/monitors/InfoMember";
	}
	
	
	/**
	 * 
	 * @方法说明：成员管理添加和更新页面跳转
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("monitors:InfoMember:edit")
	@RequestMapping(value = "/addAndUpdate")
	public String addUpdate(@RequestParam(value="memberId",required = false) Integer memberId, 
							InfoMember infoMember, 
							Model model) {
		
		InfoGroup infoGroup=new InfoGroup();
		infoGroup.setStatus(Status.ENABLE.getValue());
		List<InfoGroup> list = infoGroupService.getList(infoGroup);
		
		//状态  ENABLE(启用) DISABL(禁用)
		List<EnumBean> infoGroupList = Lists.newArrayList();
		for (InfoGroup info : list) {
			EnumBean ct = new EnumBean();
			ct.setValue(info.getGroupId()+"");
			ct.setName(info.getGroupName());
			infoGroupList.add(ct);
		}
		model.addAttribute("infoGroupList", infoGroupList);
		
		if(null != memberId){
			logger.info("银行证书密钥管理页面跳转------>{修改操作}"+memberId);
			infoMember=infoMemberService.getEntity(memberId);
            
		}else{
			logger.info("银行证书密钥管理页面跳转------>{添加操作}");
			infoMember=new InfoMember();
		}
		
		model.addAttribute("infoMember", infoMember);
		return "modules/monitors/InfoMemberAddAndUpdate";
	}
	
	
	/**
	 * 
	 * @方法说明：成员管理页面
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("monitors:InfoMember:edit")
	@RequestMapping(value = "/saveEntity")
	public String save(@RequestParam(value="memberId",required = false) Integer memberId, 
			   		   InfoMember infoMember, 
					   RedirectAttributes redirectAttributes,
					   Model model){
		String message="";
		if(null != memberId){
			logger.info("成员管理页面------>{修改操作}"+memberId);
			
			infoMember.setUpdateTime(new Date());
			infoMember.setUpdateUser(UserUtils.getUser().getName());
			int num=infoMemberService.updateEntity(infoMember);
			if(num>0){
				message="修改操作成功";
			}else{
				message="修改操作失败";
			}
		}else{
			logger.info("成员管理页面------>{添加操作}");
			try {
				infoMember.setCreateTime(new Date());
				infoMember.setCreateUser(UserUtils.getUser().getName());
				int	num=infoMemberService.saveEntity(infoMember);
				if(num>0){
					message="保存操作成功";
				}else{
					message="保存操作失败";
				}
			} catch (Exception e) {
				logger.info("成员管理页面------>{保存失败}"+e.getMessage());
			}
		}
		
		addMessage(redirectAttributes, message);
		return "redirect:"+Global.getAdminPath()+"/monitors/infoMember";
		
	}

	/**
	 * 
	 * @方法说明：成员管理删除
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("monitors:InfoMember:edit")
	@RequestMapping(value = "/delete/{memberId}")
	public String delete(@PathVariable("memberId") Integer memberId,RedirectAttributes redirectAttributes) {
		
		logger.info("成员管理删除------>{}"+memberId);
		try {
			int num=infoMemberService.delete(memberId);
			if(num>0){
				addMessage(redirectAttributes, "删除成功");
			}else{
				addMessage(redirectAttributes, "删除失败");
			}
		} catch (Exception e) {
			logger.info("成员管理删除------>{失败}"+e);
			addMessage(redirectAttributes, "删除失败");
		}
		return "redirect:"+Global.getAdminPath()+"/monitors/infoMember";
	}
	
	
	/**
	 * 
	 * @方法说明：判断选择的类型
	 * @时间：13 Feb 201710:00:48
	 * @创建人：wangl
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/groupId")
	public Object  changValue(@RequestParam(value="groupId") Integer groupId) {
		
		logger.info("成员管理------>{判断选择的类型}"+groupId);
		InfoGroup entity = infoGroupService.getEntity(groupId);
		if( null != entity){
			String remark = entity.getRemark();
			return remark;
		}else{
			logger.info("判断选择的类型------>{失败}");
			return 404;
		}
	}
	
}