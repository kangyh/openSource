package com.heepay.manage.modules.monitor.java;

import java.util.Date;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.monitor.java.entity.MonitorGroup;
import com.heepay.manage.modules.monitor.java.service.MonitorGroupService;
import com.heepay.manage.modules.monitor.java.service.MonitorMachineService;
import com.heepay.manage.modules.sys.utils.UserUtils;

/***
 * 
 * 
 * 描    述：通道管理表查询操作
 *
 * 创 建 者： wangl
 * 创建时间：  2016年9月23日下午1:38:03
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
@RequestMapping(value = "${adminPath}/modules/monitorGroup")
public class MonitorGroupController extends BaseController {
	
	//日志打印
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private MonitorGroupService monitorGroupService;

	@Autowired
	private MonitorMachineService monitorMachineService;
	
	//组信息查询开始显示
	@RequiresPermissions("modules:monitorGroup:view")
	@RequestMapping(value = { "list", "" })
	public String list(MonitorGroup monitorGroup, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		log.info("组信息查询开始--->{条件}"+monitorGroup);
		//分页查询
		Page<MonitorGroup> page = monitorGroupService.findPage(new Page<MonitorGroup>(request, response), monitorGroup);
		
		model.addAttribute("monitorGroup",monitorGroup);
		model.addAttribute("page",page);
		
		log.info("组信息查询开始--->{结束}"+page.getList());
		return "modules/monitor_java/monitorGroup";
		
	}
	
	/**
	 * 
	 * @方法说明：删除操作
	 * @时间：2017年1月20日下午3:24:01
	 * @创建人：wangl
	 */
	@RequiresPermissions("modules:monitorGroup:edit")
	@RequestMapping(value = "/delete/{groupId}")
	public String deleteEntity(@PathVariable(value = "groupId") Integer groupId,RedirectAttributes redirectAttributes) {
		
		log.info("组信息删除操作开始--->{条件}"+groupId);
		//查询是否关联了子数据，若有就不能删除，没有才能删除
		int countNum=monitorMachineService.getEntityByMachineGroupId(groupId);
		if(countNum>0){
			addMessage(redirectAttributes, "组信息删除失败,请先删除主机信息管理子数据");
		}else{
			int num=monitorGroupService.deleteEntity(groupId);
			if(num>0){
				addMessage(redirectAttributes, "组信息删除成功");
			}else{
				addMessage(redirectAttributes, "组信息删除失败");
			}
		}
		return "redirect:" + Global.getAdminPath() + "/modules/monitorGroup";
	}
	
	
	/**
	 * 
	 * @方法说明：添加或修改跳转页面
	 * @时间：2017年1月20日下午3:30:49
	 * @创建人：wangl
	 */
	@RequiresPermissions("modules:monitorGroup:edit")
	@RequestMapping(value = "/update")
	public String toAddAndUpdate(@RequestParam(value = "groupId", required = false) Integer groupId, Model model) {
		
		MonitorGroup monitorGroup=null;
		log.info("组信息添加或修改跳转页面--->{开始}"+groupId);
		if(groupId !=null){
			log.info("组信息添加或修改跳转页面--->{修改操作}"+groupId);
			 monitorGroup=monitorGroupService.getEntityById(groupId);
		}else{
			log.info("组信息添加或修改跳转页面--->{添加操作}");
			monitorGroup=new MonitorGroup();
		}
		
		model.addAttribute("monitorGroup", monitorGroup);
		return "modules/monitor_java/monitorGroupAddUpdate";
	}
	/**
	 * 
	 * @方法说明：添加或修入库方法
	 * @时间：2017年1月20日下午3:30:49
	 * @创建人：wangl
	 */
	@RequiresPermissions("modules:monitorGroup:edit")
	@RequestMapping(value = "/saveEntity")
	public String saveEntity(MonitorGroup monitorGroup,RedirectAttributes redirectAttributes){
		
		log.info("组信息添加或修入库方法--->{开始}"+monitorGroup);
		if(monitorGroup.getGroupId() !=null){
			log.info("组信息修改入库方法--->{修改操作}"+monitorGroup);
			try {
				//修改人
				monitorGroup.setUpdateAuthor(UserUtils.getUser().getName());
				//修改时间
				monitorGroup.setUpdateTime(new Date());
				monitorGroupService.updateEntity(monitorGroup);
				addMessage(redirectAttributes, "组信息修改入库成功");
			} catch (Exception e) {
				addMessage(redirectAttributes, "组信息修改入库异常  "+e.getMessage());
			}
		}else{
			log.info("组信息新增入库方法--->{新增操作}"+monitorGroup);
			try {
				//创建人
				monitorGroup.setCreateAuthor(UserUtils.getUser().getName());
				//创建时间
				monitorGroup.setCreateTime(new Date());;
				monitorGroupService.saveEntity(monitorGroup);
				addMessage(redirectAttributes, "组信息新增入库成功");
			} catch (Exception e) {
				addMessage(redirectAttributes, "组信息新增入库异常  "+e.getMessage());
			}
		}
		return "redirect:" + Global.getAdminPath() + "/modules/monitorGroup";
	}
	
}
	
