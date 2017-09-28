package com.heepay.manage.modules.riskManage.web;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.billingutils.IsNumeric;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.risk.RiskFreezeRemark;
import com.heepay.enums.risk.RiskFreezeType;
import com.heepay.enums.risk.RiskStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.risk.entity.RiskMerchantFreeze;
import com.heepay.manage.modules.risk.service.RiskMerchantFreezeService;
import com.heepay.manage.modules.riskManage.rpc.client.RiskOrederRpcClient;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.risk.util.DefreezeNo;
import com.heepay.rpc.account.model.MerchantFrozenRes;


/***
 * 
* 
* 描    述：商户冻结/解冻表 Controller
*
* 创 建 者：wangl
* 创建时间：  Nov 18, 20167:14:38 PM
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
@RequestMapping(value = "${adminPath}/riskManage/RiskMerchantFreezeQuery")
public class RiskMerchantFreezeControllerDJ extends BaseController {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private  RiskMerchantFreezeService  riskMerchantFreezeService;
	
	@Autowired
	private RiskOrederRpcClient riskOrederRpcClient;
	
	@Autowired
	private Explort explort;
	
//	@Autowired
//	private RiskMerchantRpcClient client;//调取风控的客户端
	
	
	/*// 添加的方法跳转
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "/add")
	public String addEntity(RiskMerchantFreeze riskMerchantFreeze, 
							RedirectAttributes redirectAttributes,
							Model model) throws ParseException {
		
		//冻结/解冻原因 freeze_remark
		List<EnumBean> riskFreezeRemark = Lists.newArrayList();
		for (RiskFreezeRemark checkFlg : RiskFreezeRemark.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			riskFreezeRemark.add(ct);
		}
		model.addAttribute("riskFreezeRemark", riskFreezeRemark);
		model.addAttribute("riskMerchantFreeze", riskMerchantFreeze);
		return "modules/riskManage/riskMerchantFreezeAdd";
	}*/
	

	
	
	
	/*
	// 账户余额冻结申请的方法
	@SuppressWarnings("unchecked")
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "/saveEntity")
	public String saveEntity(RiskMerchantFreeze riskMerchantFreeze, RedirectAttributes redirectAttributes,
							   HttpServletRequest request,Model model) throws Exception {
		
		logger.info("冻结操作-------->{}"+riskMerchantFreeze.getFreezeId());
		String remark1 = riskMerchantFreeze.getRemark1();
		String remark2 = riskMerchantFreeze.getRemark2();
		String remark3 = riskMerchantFreeze.getRemark3();
		String remark4 = riskMerchantFreeze.getRemark4();
		
		riskMerchantFreeze.setFreezeNo(DefreezeNo.getRandomString());//冻结编号 defreeze_no的随机数
		
		String merchantCode = riskMerchantFreeze.getMerchantCode();    //商户编码
		BigDecimal transAmount = riskMerchantFreeze.getTransAmount();  //冻结金额
		String freezeNo = riskMerchantFreeze.getFreezeNo();        //冻结单号
		String freezeRemark = riskMerchantFreeze.getFreezeRemark();    //冻结原因
		
		String transNo=remark1+","+remark2+","+remark3+","+remark4;    //交易单号
		
		
		User user = UserUtils.getUser();
		String name = user.getLoginName();
		logger.info("当前登录用户-------->{}"+name);

		
		String merchantJson="";
		try {
			merchantJson = client.getMerchantVO(merchantCode);
		} catch (Exception e) {
			logger.error("查询对象为空无法添加商户冻结-------->{消息为空}");
			addMessage(redirectAttributes, "查询对象为空无法添加商户冻结");
			return "redirect:"+Global.getAdminPath()+"/riskManage/RiskMerchantFreezeQuery/"; 
		}
		
		if(StringUtils.isNotBlank(merchantJson)){
			
			//冻结服务调取   没有交易单号  
			MerchantFrozenRes merchantFrozenRes = riskOrederRpcClient.getFrozenMerchantAmount(merchantCode,  freezeNo, transAmount.toString(),transNo, freezeRemark);
			int code = merchantFrozenRes.getCode();
			String msg = merchantFrozenRes.getMsg();
			
			if(InterfaceStatus.ACCOUNT_FROZEN_SUCCESS.getValue()==code){  //冻结账户成功 5600
				
				logger.info("调取方法查询对象商户冻结-------->{}"+merchantJson);
				
				Map<String, String> map = JsonMapperUtil.nonEmptyMapper().fromJson(merchantJson, Map.class);
				String merchantName = map.get("merchantLoginName");
				
				logger.info("添加数据-------->{}"+riskMerchantFreeze.toString());
				riskMerchantFreeze.setCreateAuthor(name);//添加当前操作的用户名   
				riskMerchantFreeze.setMerchantName(merchantName);//merchant_name
				
				riskMerchantFreeze.setStatus(RiskStatus.RISK_STATUS_0.getValue());//冻结/解冻状态 0:待审核 1：已冻结 2：已解冻 3：冻结失败
				riskMerchantFreeze.setCreateTime(new Date());
				int num=riskMerchantFreezeService.saveEntity(riskMerchantFreeze);
				logger.info("添加数据成功-------->{}"+num);
				
				addMessage(redirectAttributes, "添加成功");
				
			}else if(InterfaceStatus.ACCOUNT_FROZEN_FAILURE.getValue()==code){ // 5601, "冻结账户失败"
				addMessage(redirectAttributes, msg);
				logger.info("冻结操作完成-------->{}"+msg);
				
			}else if(InterfaceStatus.MERCHANTID_PARAME_ERROR.getValue()==code){ // 5801, "商户Id不存在"
				addMessage(redirectAttributes, msg);
				logger.info("冻结操作完成-------->{}"+msg);
			}
			
			return "redirect:"+Global.getAdminPath()+"/riskManage/RiskMerchantFreezeQuery/add"; 
			
		}else{
			logger.info("查询对象为空无法添加商户冻结-------->{消息为空}");
			addMessage(redirectAttributes, "查询对象为空无法添加商户冻结");
			return "redirect:"+Global.getAdminPath()+"/riskManage/RiskMerchantFreezeQuery/"; 
		}
		
	}
	*/
	
	/**
	 * 
	 * @方法说明：商户冻结信息查询 冻结审核
	 * @时间：2016年10月26日 下午3:26:17
	 * @创建人：wangl
	 */
	@RequiresPermissions("riskManage:riskMerchantFreeze:view")
	@RequestMapping(value = { "list", "" })
	public String list(RiskMerchantFreeze riskMerchantFreeze, 
					   HttpServletRequest request,
					   HttpServletResponse response, Model model) {
		try {
			logger.info("商户冻结信息查询----->{}"+riskMerchantFreeze.toString());
			  
			// 冻结待审核
			riskMerchantFreeze.setStatus(RiskStatus.RISK_STATUS_0.getValue());//冻结待审核
			
			
			model = riskMerchantFreezeService.findRiskMerchantProductQuotaPage(new Page<RiskMerchantFreeze>(request, response), riskMerchantFreeze,model);
			
			return "modules/riskManage/riskMerchantFreezeList";
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaController list has a error:", e);
		}
		return "error/500";
	}
	
	
	// 记录信息导出
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes, RiskMerchantFreeze riskMerchantFreeze,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		explort.export(redirectAttributes, riskMerchantFreeze, request, response);
	}
	
	
	
	/**
	 * 
	 * @方法说明：撤销页面跳转
	 * @时间：Nov 21, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "/todj/{freezeId}/{pageNo}")
	public String todjEntity(@PathVariable("freezeId") int freezeId,
							 @PathVariable("pageNo") int pageNo,
							 HttpServletRequest request,
							 Model model) throws ParseException {
		
		String referer = request.getHeader("referer");
		logger.info("查询操作-------->{}"+freezeId);
		RiskMerchantFreeze riskMerchantFreeze = riskMerchantFreezeService.getEntity(freezeId);
		//冻结/解冻状态 0:待审核 1：已冻结 2：已解冻 3：冻结失败 4：解冻失败
		if(StringUtils.isNotBlank(riskMerchantFreeze.getStatus())){
			riskMerchantFreeze.setStatus(RiskStatus.labelOf(riskMerchantFreeze.getStatus()));
		}
		//冻结类型 1：冻结 2：解冻
		if(StringUtils.isNotBlank(riskMerchantFreeze.getFreezeType())){
			riskMerchantFreeze.setFreezeType(RiskFreezeType.labelOf(riskMerchantFreeze.getFreezeType()));
		}
		//冻结/解冻原因 freeze_remark
		if(StringUtils.isNotBlank(riskMerchantFreeze.getFreezeRemark())){
			riskMerchantFreeze.setFreezeRemark(RiskFreezeRemark.labelOf(riskMerchantFreeze.getFreezeRemark()));
		}
		
		//冻结/解冻原因 freeze_remark
		List<EnumBean> riskFreezeRemark = Lists.newArrayList();
		for (RiskFreezeRemark checkFlg : RiskFreezeRemark.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			riskFreezeRemark.add(ct);
		}
		model.addAttribute("riskFreezeRemark", riskFreezeRemark);
		
		model.addAttribute("riskMerchantFreeze", riskMerchantFreeze);
		model.addAttribute("referer", referer);
		model.addAttribute("pageNo", pageNo);
		logger.info("查询操作完成-------->{}"+riskMerchantFreeze.toString());
		return "modules/riskManage/riskMerchantFreezeDj";
	}
	
	
	/**
	 * 
	 * @方法说明：撤销操作  就是解冻操作
	 * @时间：Nov 21, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "/dj/{freezeId}")
	public String djEntity(RedirectAttributes redirectAttributes,
						   RiskMerchantFreeze riskMerchant,
						   @PathVariable("freezeId") int freezeId,
						   @RequestParam("checkFlg") String checkFlg,
						   @RequestParam("pageNo") int pageNo,
						   @RequestParam("referer") String referer,
						   Model model) throws ParseException {
		
		logger.info("冻结操作-------->{}"+freezeId);
		
		if("N".equals(checkFlg)){
			//获取整个对象
			RiskMerchantFreeze riskMerchantFreeze=riskMerchantFreezeService.getEntity(freezeId);
			
			String freezeMessage = riskMerchant.getFreezeMessage();          //解冻申请的理由
			String merchantCode = riskMerchantFreeze.getMerchantCode();    //商户编码
			String freezeNo = riskMerchantFreeze.getFreezeNo();        //冻结单号
			
			String defreezeNoNum = DefreezeNo.getRandomString();
			riskMerchant.setDefreezeNo(defreezeNoNum);		//生成解冻单号
			
			//冻结服务调取   没有交易单号  
			MerchantFrozenRes merchantFrozenRes = riskOrederRpcClient.thawMerchantAmount(merchantCode,  freezeNo, defreezeNoNum, freezeMessage);
			int code = merchantFrozenRes.getCode();
			String msg = merchantFrozenRes.getMsg();
			
			if(InterfaceStatus.ACCOUNT_THAW_SUCCESS.getValue()==code){  //冻结账户成功 5700
				riskMerchant.setStatus(RiskStatus.RISK_STATUS_2.getValue());//更新状态  
				riskMerchant.setFreezeType(RiskFreezeType.RISK_FREEZE_TYPE_2.getValue());//freeze_type
				
				User user = UserUtils.getUser();
				String name = user.getName();
				
				logger.info("当前登录用户-------->{}"+name);
				riskMerchant.setUpdateAuthor(name);
				riskMerchant.setUpdateTime(new Date());
				int num=riskMerchantFreezeService.updateEntity(riskMerchant);
				
				addMessage(redirectAttributes, msg);
				logger.info("冻结操作完成-------->{}"+num);
			}else if(InterfaceStatus.ACCOUNT_THAW_FAILURE.getValue()==code){ // 5701, "冻结账户失败"
				addMessage(redirectAttributes, msg);
				logger.info("冻结操作完成-------->{}"+msg);
				
				
			}else if(InterfaceStatus.MERCHANTID_PARAME_ERROR.getValue()==code){ // 5801, "商户Id不存在"
				addMessage(redirectAttributes, msg);
				logger.info("冻结操作完成-------->{}"+msg);
			}else{
				addMessage(redirectAttributes, msg);
				logger.info("冻结操作完成-------->{}"+msg);
			}
			
			
		}else{
			
			riskMerchant.setStatus(RiskStatus.RISK_STATUS_1.getValue());//更新状态  已解冻
			riskMerchant.setFreezeType(RiskFreezeType.RISK_FREEZE_TYPE_1.getValue());//freeze_type
			
			User user = UserUtils.getUser();
			String name = user.getName();
			logger.info("当前登录用户-------->{}"+name);
			riskMerchant.setUpdateAuthor(name);
			riskMerchant.setUpdateTime(new Date());
			int num=riskMerchantFreezeService.updateEntity(riskMerchant);
			
			addMessage(redirectAttributes, "审核确认成功");
			logger.info("冻结操作完成-------->{}"+num);
		}
		
		// 页面跳转
		return refererMethod(pageNo, referer);
		
	}
	
	
	/**
	 * 
	 * @方法说明：撤销操作
	 * @时间：Nov 21, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "/cxdd/{freezeId}")
	public String qxEntity(RedirectAttributes redirectAttributes,
						   @PathVariable("freezeId") int freezeId,
						   Model model) throws ParseException {
		
		logger.info("撤销操作-------->{}"+freezeId);
		RiskMerchantFreeze riskMerchantFreeze=new RiskMerchantFreeze();
		
		riskMerchantFreeze.setFreezeId(freezeId);//主键id
		riskMerchantFreeze.setUpdateTime(new Date());//更新时间
		riskMerchantFreeze.setStatus(RiskStatus.RISK_STATUS_2.getValue());//操作状态
		int num=riskMerchantFreezeService.updateEntity(riskMerchantFreeze);
		
		addMessage(redirectAttributes, "撤销成功");
		logger.info("撤销操作完成-------->{}"+num);
		return "redirect:"+Global.getAdminPath()+"/riskManage/RiskMerchantFreezeQuery/"; 
	}
	
	
	
	/**
	 * 
	 * @方法说明：查询操作
	 * @时间：Nov 21, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "/toxg/{freezeId}/{pageNo}")
	public String toUpdateEntity(RedirectAttributes redirectAttributes,
			                     @PathVariable("freezeId") int freezeId,
			                     @PathVariable("pageNo") int pageNo,
			                     HttpServletRequest request,
			                     Model model) throws ParseException {
		
		String referer = request.getHeader("referer");
		logger.info("查询操作-------->{}"+freezeId);
		RiskMerchantFreeze riskMerchantFreeze = riskMerchantFreezeService.getEntity(freezeId);
		//冻结/解冻状态 0:待审核 1：已冻结 2：已解冻 3：冻结失败 4：解冻失败
		if(StringUtils.isNotBlank(riskMerchantFreeze.getStatus())){
			riskMerchantFreeze.setStatus(RiskStatus.labelOf(riskMerchantFreeze.getStatus()));
		}
		//冻结类型 1：冻结 2：解冻
		if(StringUtils.isNotBlank(riskMerchantFreeze.getFreezeType())){
			riskMerchantFreeze.setFreezeType(RiskFreezeType.labelOf(riskMerchantFreeze.getFreezeType()));
		}
		
		
		//冻结/解冻原因 freeze_remark
		List<EnumBean> riskFreezeRemark = Lists.newArrayList();
		for (RiskFreezeRemark checkFlg : RiskFreezeRemark.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			riskFreezeRemark.add(ct);
		}
		model.addAttribute("riskFreezeRemark", riskFreezeRemark);
		
		model.addAttribute("riskMerchantFreeze", riskMerchantFreeze);
		model.addAttribute("referer", referer);
		model.addAttribute("pageNo", pageNo);
		logger.info("查询操作完成-------->{}"+riskMerchantFreeze.toString());
		return "modules/riskManage/riskMerchantFreezeUpdate";
	}
	
	/**
	 * 
	 * @方法说明：更新商户账户冻结操作
	 * @时间：Nov 21, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelManager:edit")
	@RequestMapping(value = "/updateEntity")
	public String updateEntity(RiskMerchantFreeze riskMerchantFreeze, 
							   RedirectAttributes redirectAttributes,
							   @RequestParam("pageNo") int pageNo,
							   @RequestParam("referer") String referer,
							   HttpServletRequest request,
							   Model model) throws ParseException {
		
		logger.info("更新商户账户冻结操作-------->{}"+riskMerchantFreeze.toString());
		String merchantCode = riskMerchantFreeze.getMerchantCode();
		
		int num=riskMerchantFreezeService.updateEntity(riskMerchantFreeze);
		if(num !=0){
			logger.info("更新商户账户冻结操作完成-------->{}"+num);
			addMessage(redirectAttributes, "成功更新商户 "+merchantCode+" 成功");
		}else{
			logger.info("商户账户冻结操作更新失败-------->{}"+num);
			addMessage(redirectAttributes, "更新失败");
		}
		// 页面跳转
		return refererMethod(pageNo, referer);
	}
	
	// 根据消息请求头的地址返回原来的地址
	private String refererMethod(int pageNo, String referer) {
		// 消息请求头的地址
		String substring = referer.substring(referer.length() - 1, referer.length());
		String toReferer = referer.substring(0, referer.length() - 1);
		
		
		// 判断这个是不是数字
		boolean flag = IsNumeric.isNumeric(substring);
		if (flag) {
			return "redirect:" + referer;
		}else if(!substring.equals("/")){
			return "redirect:" + referer + "?pageNo=" + pageNo;
		} else {
			return "redirect:" + toReferer + "?pageNo=" + pageNo;
		}
	}
}
