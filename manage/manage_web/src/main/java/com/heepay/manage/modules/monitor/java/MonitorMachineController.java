package com.heepay.manage.modules.monitor.java;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.monitor.java.entity.MonitorGroup;
import com.heepay.manage.modules.monitor.java.entity.MonitorMachine;
import com.heepay.manage.modules.monitor.java.enums.IsDbType;
import com.heepay.manage.modules.monitor.java.enums.StatusType;
import com.heepay.manage.modules.monitor.java.service.MonitorGroupService;
import com.heepay.manage.modules.monitor.java.service.MonitorMachineService;
import com.heepay.manage.modules.sys.utils.UserUtils;

/***
 * 
 * 
 * 描    述：主机信息管理操作
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
@RequestMapping(value = "${adminPath}/modules/monitorMachine")
public class MonitorMachineController extends BaseController {
	
	//日志打印
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private MonitorMachineService monitorMachineService;

	@Autowired
	private MonitorGroupService monitorGroupService;
	
	@Autowired
	private MonitorLogController  monitorLogController;
	
	//主机信息管理查询开始显示
	@RequiresPermissions("modules:monitorMachine:view")
	@RequestMapping(value = { "list", "" })
	public String list(MonitorMachine monitorMachine, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		log.info("主机信息管理查询开始--->{条件}"+monitorMachine);
		//分页查询
		Page<MonitorMachine> page = monitorMachineService.findPage(new Page<MonitorMachine>(request, response), monitorMachine);
		
		List<MonitorMachine> clearingCRList = Lists.newArrayList();
		for (MonitorMachine clearingCR : page.getList()) {
			//主机访问状态 0 访问异常 1 正常
			if(clearingCR.getIsDb()==0){
				clearingCR.setView(IsDbType.ISDB_TYPE_0.getContent());
			}else{
				clearingCR.setView(IsDbType.ISDB_TYPE_1.getContent());
			}
			//翻译名称
			MonitorGroup entityById = monitorGroupService.getEntityById(clearingCR.getMachineGroupId());
			clearingCR.setName(entityById.getGroupName());
			//主机访问状态 0 访问异常 1 正常
			if(clearingCR.getStatus()==0){
				clearingCR.setValue(StatusType.STATUS_TYPE_O.getContent());
			}else{
				clearingCR.setValue(StatusType.STATUS_TYPE_1.getContent());
			}
			
			clearingCRList.add(clearingCR);
		}
		page.setList(clearingCRList);
		
		//通道管理表查询操作
		List<MonitorGroup> list=monitorGroupService.getList();
		
		model.addAttribute("page",page);
		model.addAttribute("list",list);
		model.addAttribute("monitorMachine",monitorMachine);
		log.info("主机信息管理查询开始--->{结束}");
		return "modules/monitor_java/monitorMachine";
		
	}
	
	/**
	 * 
	 * @方法说明：删除操作
	 * @时间：2017年1月20日下午3:24:01
	 * @创建人：wangl
	 */
	@RequiresPermissions("modules:monitorMachine:edit")
	@RequestMapping(value = "/delete/{machineId}")
	public String deleteEntity(@PathVariable(value = "machineId") Integer machineId,RedirectAttributes redirectAttributes) {
		
		log.info("主机信息管理删除操作开始--->{条件}"+machineId);
		int num=monitorMachineService.deleteEntity(machineId);
		if(num>0){
			addMessage(redirectAttributes, "主机信息管理删除成功");
		}else{
			addMessage(redirectAttributes, "主机信息管理删除失败");
		}
		return "redirect:" + Global.getAdminPath() + "/modules/monitorMachine";
	}
	
	/**
	 * 
	 * @方法说明：添加或修改跳转页面
	 * @时间：2017年1月20日下午3:30:49
	 * @创建人：wangl
	 */
	@RequiresPermissions("modules:monitorMachine:edit")
	@RequestMapping(value = "/update")
	public String toAddAndUpdate(@RequestParam(value = "groupId", required = false) Integer groupId, Model model) {
		
		//通道管理表查询操作
		List<EnumBean> machineName = Lists.newArrayList();
		
		List<MonitorGroup> list=monitorGroupService.getList();
		for (MonitorGroup monitorGroup : list) {
			EnumBean ct = new EnumBean();
			ct.setValue(monitorGroup.getGroupId()+"");
			ct.setName(monitorGroup.getGroupName());
			machineName.add(ct);
		}
		
		MonitorMachine monitorMachine=null;
		log.info("主机信息管理添加或修改跳转页面--->{开始}"+groupId);
		if(groupId !=null){
			log.info("主机信息管理添加或修改跳转页面--->{修改操作}"+groupId);
			 monitorMachine=monitorMachineService.getEntityById(groupId);
		}else{
			log.info("主机信息管理添加或修改跳转页面--->{添加操作}");
			
			
			
			monitorMachine=new MonitorMachine();
		}
		
		model.addAttribute("machineName", machineName);
		model.addAttribute("list", list);
		model.addAttribute("monitorMachine", monitorMachine);
		
		return "modules/monitor_java/monitorMachineAddUpdate";
	}
	/**
	 * 
	 * @方法说明：添加或修入库方法
	 * @时间：2017年1月20日下午3:30:49
	 * @创建人：wangl
	 */
	@SuppressWarnings("rawtypes")
	@RequiresPermissions("modules:monitorMachine:edit")
	@RequestMapping(value = "/saveEntity")
	public String saveEntity(MonitorMachine monitorMachine,RedirectAttributes redirectAttributes){
		
		log.info("主机信息管理添加或修入库方法--->{开始}"+monitorMachine);
		
		JsonMapperUtil jsonUtils=new JsonMapperUtil();
		
			String result=monitorLogController.CheckMachineStatus(monitorMachine);
			Map map = jsonUtils.fromJson(result, Map.class);
			boolean  value=(boolean)map.get("hasError");
			
		log.info("验证规则--->{结束}--->{结果}"+value);
			if(value){
				monitorMachine.setStatus(0);
			}else{
				monitorMachine.setStatus(1);
			}
			
		if(monitorMachine.getMachineId() !=null){
			log.info("主机信息管理修改入库方法--->{修改操作}"+monitorMachine);
			try {
				//修改人
				monitorMachine.setUpdateAuthor(UserUtils.getUser().getName());
				//修改时间
				monitorMachine.setUpdateTime(new Date());
				monitorMachineService.updateEntity(monitorMachine);
				addMessage(redirectAttributes, "主机信息管理修改入库成功");
			} catch (Exception e) {
				addMessage(redirectAttributes, "主机信息管理修改入库异常  "+e.getMessage());
			}
		}else{
			log.info("主机信息管理新增入库方法--->{新增操作}"+monitorMachine);
			try {
				//创建人
				monitorMachine.setCreateAuthor(UserUtils.getUser().getName());
				//创建时间
				monitorMachine.setCreateTime(new Date());;
				monitorMachineService.saveEntity(monitorMachine);
				addMessage(redirectAttributes, "主机信息管理新增入库成功");
			} catch (Exception e) {
				addMessage(redirectAttributes, "主机信息管理新增入库异常  "+e.getMessage());
			}
		}
		return "redirect:" + Global.getAdminPath() + "/modules/monitorMachine";
	}
	
	//验证规则
	@SuppressWarnings("rawtypes")
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/load")
	public String load(MonitorMachine monitorMachine) {
		
	log.info("验证规则--->{开始}--->{参数}"+monitorMachine);
		JsonMapperUtil jsonUtils=new JsonMapperUtil();
		String result=monitorLogController.CheckMachineStatus(monitorMachine);
		Map map = jsonUtils.fromJson(result, Map.class);
		boolean  value=(boolean)map.get("hasError");
		String  message=(String)map.get("message");
		
	log.info("验证规则--->{结束}--->{结果}"+value);
		return message;
	}
}
	
