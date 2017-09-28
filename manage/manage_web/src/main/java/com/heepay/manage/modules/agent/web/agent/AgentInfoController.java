/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.web.agent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.heepay.agent.common.enums.AgentType;
import com.heepay.agent.common.utils.RandomSaltUtils;
import com.heepay.agent.rpc.thrift.*;
import com.heepay.common.util.SmsUtils;
import com.heepay.manage.common.agent.AgentInfoUtil;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.modules.agent.service.agent.AgentInfoPreService;
import com.heepay.manage.modules.merchant.rpc.client.AddAgentFundClient;
import com.heepay.manage.modules.merchant.rpc.client.AddAgentUserClient;
import com.heepay.manage.modules.merchant.rpc.client.GetAgentCodeClient;
import com.heepay.manage.modules.merchant.rpc.client.RecordAgentCheckClient;
import com.heepay.manage.modules.sys.security.SystemAuthorizingRealm;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.thoughtworks.xstream.mapper.Mapper;
import org.apache.commons.lang3.EnumUtils;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.modules.agent.entity.agent.AgentInfo;
import com.heepay.manage.modules.agent.service.agent.AgentInfoService;
import com.heepay.manage.modules.agent.service.agent.AgentInfoOtherService;
import com.heepay.agent.common.enums.AgentStatus;
import com.heepay.agent.common.enums.BusiType;
import com.heepay.agent.common.enums.BankAccountType;


/**
 *
 * 描    述：代理商信息维护Controller
 *
 * 创 建 者： @author xch
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
@RequestMapping(value = "${adminPath}/agent/agent/agentInfo")
public class AgentInfoController extends BaseController {

	@Autowired
	private AgentInfoService agentInfoService;
	@Autowired
	private AgentInfoOtherService agentInfoOtherService;
	@Autowired
	private AgentInfoPreService agentInfoPreService;
	@Autowired
	private AddAgentUserClient   addAgentUserClient;
	@Autowired
	private GetAgentCodeClient getAgentCodeClient;
	@Autowired
	private RecordAgentCheckClient recordAgentCheckClient;

	@Autowired
	private AddAgentFundClient  addAgentFundClient;

	@ModelAttribute
	public AgentInfo get(@RequestParam(required=false) String id) {
		AgentInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = agentInfoService.get(id);
		}
		if (entity == null){
			entity = new AgentInfo();
		}
		return entity;
	}
	
	@RequiresPermissions("agent:agent:agentInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(AgentInfo agentInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AgentInfo> page = new Page<AgentInfo>(request, response);
		//page.setOrderBy("update_time desc");
		page = agentInfoService.findPage(page, agentInfo);
		//获取状态列表信息
		List<AgentStatus> agentStatusList = EnumUtils.getEnumList(AgentStatus.class);
		//修改记录中状态为对应的中文描述信息
		List<AgentInfo> list= Lists.newArrayList();
		for(AgentInfo info:page.getList()){
			//考虑有可能是空指针
			AgentStatus agentStatus=EnumUtils.getEnum(AgentStatus.class,info.getAgentStatus());
			AgentType agentType=EnumUtils.getEnum(AgentType.class,info.getAgentType());
			if(agentStatus != null){
				info.setAgentStatus(agentStatus.getValue());
			}
			if(agentType != null){
				info.setAgentType(agentType.getValue());
			}
			list.add(info);
		}
		//把修改内容重新赋值给页面
		page.setList(list);
		model.addAttribute("page", page);
		model.addAttribute("agentStatusList", agentStatusList);
		return "modules/agent/agent/agentInfoList";
	}

	@RequiresPermissions("agent:agent:agentInfo:view")
	@RequestMapping(value = {"checkList"})
	public String checkList(AgentInfo agentInfo, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<AgentInfo> page = agentInfoService.findCheckPage(new Page<AgentInfo>(request, response), agentInfo);
		//page.setOrderBy("update_time desc ");
		//修改记录中状态为对应的中文描述信息
		List<AgentInfo> list= Lists.newArrayList();
		for(AgentInfo info:page.getList()){
			//考虑有可能是空指针
			AgentType agentType=EnumUtils.getEnum(AgentType.class,info.getAgentType());
			if(agentType != null){
				info.setAgentType(agentType.getValue());
			}
			list.add(info);
		}
		//把修改内容重新赋值给页面
		page.setList(list);
		model.addAttribute("page", page);
		return "modules/agent/agent/agentInfoCheckList";
	}

	@RequiresPermissions("agent:agent:agentInfo:view")
	@RequestMapping(value = "form")
	public String form(AgentInfo agentInfo, Model model) {
		//获取营业证照类型列表信息
		List<BusiType> busiTypeList = EnumUtils.getEnumList(BusiType.class);
		//获取账户类型列表信息
		List<BankAccountType> bankAccountTypeList = EnumUtils.getEnumList(BankAccountType.class);
		//给图像加前缀
		AgentInfoUtil.changeAgentInfo(agentInfo);

		model.addAttribute("agentInfo", agentInfo);
		model.addAttribute("busiTypeList", busiTypeList);
		model.addAttribute("bankAccountTypeList", bankAccountTypeList);
		return "modules/agent/agent/agentInfoForm";
	}

	@RequiresPermissions("agent:agent:agentInfo:view")
	@RequestMapping(value = "detail")
	public String detail(AgentInfo agentInfo, Model model) {
		AgentInfoUtil.agentInfoEnumValue(agentInfo);
		AgentInfoUtil.changeAgentInfo(agentInfo);

		AgentStatus agentStatus=EnumUtils.getEnum(AgentStatus.class,agentInfo.getAgentStatus());
		if(agentStatus != null){
			agentInfo.setAgentStatus(agentStatus.getValue());
		}
		model.addAttribute("agentInfo", agentInfo);
		return "modules/agent/agent/agentInfoDetail";
	}


	 //详情-提交审核
	@RequestMapping(value = "verify")
	public String verify(AgentInfo agentInfo) {
		//详情-提交审核-状态为：系统待审
		agentInfo.setAgentStatus("SYSCHK");
		agentInfoOtherService.updateStatus(agentInfo);
		//保存审核信息
		AgentCheckObject agentCheckObject = new AgentCheckObject();
		agentCheckObject.setAgentId(agentInfo.getId());
		agentCheckObject.setAgentUserId(agentInfo.getId());
		agentCheckObject.setCheckStatus(agentInfo.getAgentStatus());
		agentCheckObject.setCheckTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		try {
			recordAgentCheckClient.insert(agentCheckObject);
		} catch (TException e) {
			e.printStackTrace();
		}
		return "redirect:"+Global.getAdminPath()+"/agent/agent/agentInfo?repage";
	}

	@RequiresPermissions("agent:agent:agentInfo:view")
	@RequestMapping(value = "checkDetail")
	public String checkDetail(AgentInfo agentInfo, Model model) {
		AgentInfoUtil.agentInfoEnumValue(agentInfo);
		AgentInfoUtil.changeAgentInfo(agentInfo);
		AgentStatus agentStatus=EnumUtils.getEnum(AgentStatus.class,agentInfo.getAgentStatus());
		if(agentStatus != null){
			agentInfo.setAgentStatus(agentStatus.getValue());
		}
		model.addAttribute("agentInfo", agentInfo);
		return "modules/agent/agent/agentInfoCheckDetail";
	}

	@RequiresPermissions("agent:agent:agentInfo:edit")
	@RequestMapping(value = "doCheck")
	public String doCheck(AgentInfo agentInfo, Model model,RedirectAttributes redirectAttributes) throws TException {
       	//拿到一个未使用的agentcode；
		//更新 agent_info的agentcode
		//新增代理商用户

		if("NORMAL".equals(agentInfo.getAgentStatus())) {    //选择审核通过时
		AgentCodeObject  agentCodeObject=null;
			  agentCodeObject=getAgentCodeClient.selectOne();
         agentInfo.setAgentCode(agentCodeObject.getCode());
			agentCodeObject.setStatus("HASUSE");
			getAgentCodeClient.update(agentCodeObject);
			//add  account
			AgentFundObject agentFundObject =new AgentFundObject();
			agentFundObject.setId(agentInfo.getId());
			addAgentFundClient.insert(agentFundObject);

		//增加用户  success

			AgentUserObject agentUserObject = new AgentUserObject();
			agentUserObject.setAgentId(agentInfo.getId());
			agentUserObject.setUserName(agentInfo.getHeadName());
			agentUserObject.setPasswordSalt(RandomSaltUtils.genSalt(20));
			agentUserObject.setPayPasswordSalt(RandomSaltUtils.genSalt(20));
			agentUserObject.setUserCode(agentInfo.getHeadPhone());
			agentUserObject.setUserType("ADMINI");   //管理员
			agentUserObject.setStatus("ENABLE");
			SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();   //当前登录用户
			agentUserObject.setUserId(principal.getId());      //获得登录用户的id
				int result = addAgentUserClient.insert(agentUserObject);
			//发送短信通知
			SmsUtils.sendSms(agentInfo.getHeadPhone(), "亲爱的"+agentInfo.getHeadName()+"，您已通过汇付宝审核，请前往代理商系统网站，通过找回密码设置您的登陆密码。加入汇元，共创辉煌", "42");
		}
		//保存审核信息
		AgentCheckObject agentCheckObject = new AgentCheckObject();
		agentCheckObject.setAgentId(agentInfo.getId());
		agentCheckObject.setAgentUserId(agentInfo.getId());
		agentCheckObject.setCheckStatus(agentInfo.getAgentStatus());
		agentCheckObject.setCheckTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			recordAgentCheckClient.insert(agentCheckObject);

		agentInfoService.save(agentInfo);

		return "redirect:"+Global.getAdminPath()+"/agent/agent/agentInfo/checkList?repage";
	}

	/**
	 *  校验负责人手机号是否存在
	 *
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "checkHeadPhone")
	@ResponseBody
	public String checkHeadPhone(AgentInfo agentInfo) {
		String result = "success";
		//校验负责人手机号是否存在
		AgentInfo headPhoneAgentInfo = new AgentInfo();
		headPhoneAgentInfo.setHeadPhone(agentInfo.getHeadPhone());
		List<AgentInfo> agentInfos = agentInfoService.findList(headPhoneAgentInfo);
		if(StringUtils.isBlank(agentInfo.getId())){
			//新增的时候光判断手机号就可以,如果存在就提示
			if(agentInfos!=null&&agentInfos.size()>=1){
				result = "error";
			}
		}else{
			//修改的时候判断手机号，查到的数据不是当前代理商的话就提示
			if(agentInfos!=null&&((agentInfos.size()==1&&!agentInfos.get(0).getId().equals(agentInfo.getId()))||agentInfos.size()>1)){
				result = "error";
			}
		}
			return result;
	}

	@RequiresPermissions("agent:agent:agentInfo:edit")
	@RequestMapping(value = "save")
	public String save(AgentInfo agentInfo, Model model, RedirectAttributes redirectAttributes) {
		//校验前预处理
		agentInfoPreService.preAgentInfoRequest(agentInfo);

		if (!beanValidator(model, agentInfo)){
			return form(agentInfo, model);
		}
		/*agentInfoService.save(agentInfo);*/
		agentInfo.setAgentPath("..."); //一级代理商默认 '...'
		agentInfoOtherService.saveCondition(agentInfo);
		addMessage(redirectAttributes, "保存代理商成功");
		return "redirect:"+Global.getAdminPath()+"/agent/agent/agentInfo/form?repage";
	}
	
	@RequiresPermissions("agent:agent:agentInfo:edit")
	@RequestMapping(value = "delete")
	public String delete(AgentInfo agentInfo, RedirectAttributes redirectAttributes) {
		agentInfoService.delete(agentInfo);
		addMessage(redirectAttributes, "删除代理商信息管理成功");
		return "redirect:"+Global.getAdminPath()+"/agent/agent/agentInfo/?repage";
	}

	@RequiresPermissions("agent:agent:agentInfo:edit")
	@RequestMapping(value = "lock")
	public String lock(AgentInfo agentInfo, RedirectAttributes redirectAttributes) {
		agentInfo.setAgentStatus("LOCKIN");
		agentInfoOtherService.updateStatus(agentInfo);
		addMessage(redirectAttributes, "锁定代理商成功");
		return "redirect:"+Global.getAdminPath()+"/agent/agent/agentInfo/?repage";
	}

	@RequiresPermissions("agent:agent:agentInfo:edit")
	@RequestMapping(value = "unlock")
	public String unlock(AgentInfo agentInfo, RedirectAttributes redirectAttributes) {
		agentInfo.setAgentStatus("NORMAL");
		agentInfoOtherService.updateStatus(agentInfo);
		addMessage(redirectAttributes, "解锁代理商成功");
		return "redirect:"+Global.getAdminPath()+"/agent/agent/agentInfo/?repage";
	}
}