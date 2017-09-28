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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.monitors.entity.InfoGroup;
import com.heepay.manage.modules.monitors.entity.InfoMsg;
import com.heepay.manage.modules.monitors.service.InfoGroupService;
import com.heepay.manage.modules.monitors.service.InfoMsgService;
import com.heepay.manage.modules.monitors.web.rpc.WarningClient;
import com.heepay.manage.modules.monitors.web.util.IsAdd;
import com.heepay.manage.modules.monitors.web.util.SendStatus;
import com.heepay.manage.modules.monitors.web.util.SendType;
import com.heepay.manage.modules.sys.utils.UserUtils;


/***
 * 
* 
* 描    述：消息管理
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
@RequestMapping(value = "${adminPath}/monitors/infoMsg")
public class InfoMsgController extends BaseController {
	
    private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private InfoMsgService iInfoMsgService;
	
	
	@Autowired
	private InfoGroupService infoGroupService;
	
	
	@Autowired
	private WarningClient warningClient;
	
	
	/**
	 * 
	 * @方法说明：分页查询
	 * @时间：20 Mar 201715:44:40
	 * @创建人：wangl
	 */
	@RequiresPermissions("monitors:InfoMember:view")
	@RequestMapping(value = { "list", "" })
	public String list(InfoMsg infoMsg, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		Page<InfoMsg> page = iInfoMsgService.findPage(new Page<InfoMsg>(request, response), infoMsg);
		
		for (InfoMsg Info : page.getList()) {
			//是否有附件  Y:存在  N:不存在
			if(StringUtils.isNotBlank(Info.getIsAdd())){
				Info.setIsAdd(IsAdd.labelOf(Info.getIsAdd()));
			}
			//发送状态：  INIT：未发送  ING：发送中  FAIL：发送失败  OK：发送成功
			if(StringUtils.isNotBlank(Info.getSendStatus())){
				Info.setSendStatus(SendStatus.labelOf(Info.getSendStatus()));
			}
			
			//发送类型
			if(StringUtils.isNotBlank(Info.getType())){
				Info.setType(SendType.labelOf(Info.getType()));
			}
			
			//翻译groupId在组管理中的名字
			InfoGroup entity = infoGroupService.getEntity(Info.getGroupId());
			Info.setGroupName(entity.getGroupName());
		}
		logger.info("成员管理------>{查询记录}");
		
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
		
		
		//发送状态：  INIT：未发送  ING：发送中  FAIL：发送失败  OK：发送成功
		List<EnumBean> sendStatus = Lists.newArrayList();
		for (SendStatus checkFlg : SendStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			sendStatus.add(ct);
		}
		model.addAttribute("sendStatus", sendStatus);
		
		//是否有附件  Y:存在  N:不存在
		List<EnumBean> isAdds = Lists.newArrayList();
		for (IsAdd checkFlg : IsAdd.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			isAdds.add(ct);
		}
		model.addAttribute("isAdds", isAdds);
		
		
		model.addAttribute("page", page);
		model.addAttribute("infoMsg", infoMsg);
	
		return "modules/monitors/infoMsg";
	}
	
	
	/**
	 * 
	 * @方法说明：重发邮件
	 * @时间：21 Mar 201710:05:34
	 * @创建人：wangl
	 */
	@RequiresPermissions("monitors:InfoMember:edit")
	@RequestMapping(value = "/sendMsg/{msgId}")
	public String sendMsg(@PathVariable(value="msgId") Integer msgId, RedirectAttributes redirectAttributes){
		
		InfoMsg infoMsg = iInfoMsgService.selectEntity(msgId);
		
		JsonMapperUtil jsonUtils=new JsonMapperUtil();
		String json = jsonUtils.toJson(infoMsg);
		
		String num = warningClient.saveTpdsTxBankCard(json);
		
		int status=0;
		if(StringUtils.isNotBlank(num)){
			if(num.equals("1")){
				InfoMsg info = new InfoMsg();
				info.setMsgId(msgId);
				info.setSenderUser(UserUtils.getUser().getName());
				info.setSendStatus(SendStatus.SENDSTATUS_OK.getValue());
				info.setSendTime(new Date());
				status = iInfoMsgService.update(info);
			}
		}
		
		if(status > 0){
			addMessage(redirectAttributes,"发送成功");
		}else{
			addMessage(redirectAttributes,"发送失败");
		}
		return "redirect:"+Global.getAdminPath()+"/monitors/infoMsg";
	}
	
	/**
	 * 
	 * @方法说明：查询异常信息
	 * @时间：24 Mar 201711:38:36
	 * @创建人：wangl
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/displayBody/{msgId}")
	public String display(@PathVariable(value="msgId") Integer msgId){
		
		InfoMsg infoMsg = iInfoMsgService.selectEntity(msgId);
		
		//返回消息体
		if(StringUtils.isNoneBlank(infoMsg.getMsgBody())){
			return infoMsg.getMsgBody();
		}else{
			return "";
		}
		
	}
}