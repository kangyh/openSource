package com.heepay.manage.modules.pbc.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.pbc.PbcAccountType;
import com.heepay.enums.pbc.PbcEventType;
import com.heepay.enums.pbc.PbcFeatureCode;
import com.heepay.enums.pbc.PbcTransCode;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.pbc.entity.PbcFeedBack;
import com.heepay.manage.modules.pbc.service.PbcFeedBackService;
import com.heepay.manage.modules.pbc.web.utils.CommonUntil;
import com.heepay.manage.modules.pbc.web.utils.PbcCodeUntil;


/***
 * 
* 
* 描    述：反馈表
*
* 创 建 者：wangl
* 创建时间：  Dec 10, 20164:21:42 PM
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
@RequestMapping(value = "${adminPath}/pbc/feedback/save")
public class PbcFeedbackController extends BaseController {

private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private PbcFeedBackService pbcFeedBackService;//反馈表服务层
	
	//	@Autowired
	//	private PbcTransactionService pbcTransactionService;//账户交易明细表服务层
	//	
	//	@Autowired
	//	private PbcPerAccountService pbcPerAccountService;//个人账户表服务层
		
	/**
	 * 
	 * @方法说明：添加页面跳转
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("pbc:pbcFeedback:view")
	@RequestMapping(value = { "list", "" })
	public String list(PbcFeedBack pbcFeedBack, HttpServletRequest request,HttpServletResponse response, Model model){
		
		
		logger.info("反馈表------>{添加跳转}");
		
		String value = request.getParameter("value");
		//传输报文流水号编码规则
		CommonUntil commonUntil=new CommonUntil();
		
		//传输报文流水号编码规则
		PbcCodeUntil pbcCodeUntil=new PbcCodeUntil();
		
		if(StringUtils.isNotBlank(value)){
				
			if(PbcEventType.CASE_REPORT.getValue().equals(value)){//案件举报 
				logger.info("反馈表------{案件举报 }----->{跳转}");
				pbcFeedBack.setYes("aa");
				pbcFeedBack.setChangeType(PbcEventType.CASE_REPORT.getValue());//案件举报
				//pbcFeedBack.setTxCode(PbcTransCode.PBC_TRANS_CODE_1.getValue());//案件举报
				pbcFeedBack.setTransSerialNumber(commonUntil.getReqHeader());//传输报文流水号
				pbcFeedBack.setApplicationId(pbcCodeUntil.getReqHeader("0402"));//业务申请编号
				
				model.addAttribute("headMsg","添加案件信息");
				model.addAttribute("headValue",value);
			}else if(PbcEventType.ABNORMAL_ACCOUNTS.getValue().equals(value)){// 可疑名单上报-异常开户
				logger.info("反馈表------{可疑名单上报-异常开户}----->{跳转}");
				pbcFeedBack.setYes("cc");   
				
				List<EnumBean> pbcFeatureCode = Lists.newArrayList();
				//事件特征码
				for (PbcFeatureCode checkFlg : PbcFeatureCode.values()) {
					EnumBean ct = new EnumBean();
					ct.setValue(checkFlg.getValue());
					ct.setName(checkFlg.getContent());
					pbcFeatureCode.add(ct);
				}
				model.addAttribute("pbcFeatureCode", pbcFeatureCode);
				
				//pbcFeedBack.setTxCode(PbcTransCode.PBC_TRANS_CODE_2.getValue());//可疑名单上报-异常开户报文格式
				pbcFeedBack.setTransSerialNumber(commonUntil.getReqHeader());//传输报文流水号
				pbcFeedBack.setApplicationId(pbcCodeUntil.getReqHeader("0403"));//业务申请编号
				pbcFeedBack.setChangeType(PbcEventType.ABNORMAL_ACCOUNTS.getValue());//可疑名单上报-异常开户
				model.addAttribute("headMsg","添加开户信息");
			}else if(PbcEventType.ACCOUNTS_INVOLVED.getValue().equals(value)){//可疑名单上报-涉案账户
				logger.info("反馈表------{可疑名单上报-涉案账户}----->{跳转}");
				pbcFeedBack.setYes("dd");
				
				//pbcFeedBack.setTxCode(PbcTransCode.PBC_TRANS_CODE_3.getValue());//可疑名单上报-异常开户
				pbcFeedBack.setTransSerialNumber(commonUntil.getReqHeader());//传输报文流水号
				pbcFeedBack.setApplicationId(pbcCodeUntil.getReqHeader("0404"));//业务申请编号
				//pbcFeedBack.setFeatureCode(PbcFeatureCodeUtil.PBC_FEATURE_CODE_BREACH.getValue());//2001-黑名单账户
				
				List<EnumBean> pbcAccountType = Lists.newArrayList();
				//事件特征码
				for (PbcAccountType checkFlg : PbcAccountType.values()) {
					EnumBean ct = new EnumBean();
					ct.setValue(checkFlg.getValue());
					ct.setName(checkFlg.getContent());
					pbcAccountType.add(ct);
				}
				model.addAttribute("pbcAccountType", pbcAccountType);
				
				pbcFeedBack.setChangeType(PbcEventType.ACCOUNTS_INVOLVED.getValue());
				model.addAttribute("headMsg","添加账户信息");
				model.addAttribute("headValue",value);
			}else if(PbcEventType.EXCEPTION_EVENTS.getValue().equals(value)){//可疑名单上报-异常事件
				logger.info("反馈表------{可疑名单上报-异常事件}----->{跳转}");
				
				//pbcFeedBack.setTxCode(PbcTransCode.PBC_TRANS_CODE_404.getValue());//可疑名单上报-异常开户
				pbcFeedBack.setTransSerialNumber(commonUntil.getReqHeader());//传输报文流水号
				pbcFeedBack.setApplicationId(pbcCodeUntil.getReqHeader("0405"));//业务申请编号
				//pbcFeedBack.setFeatureCode(PbcFeatureCodeUtil.PBC_FEATURE_CODE_BREACH.getValue());//2001-黑名单账户
				
				pbcFeedBack.setYes("ee");
				pbcFeedBack.setChangeType(PbcEventType.EXCEPTION_EVENTS.getValue());
				model.addAttribute("headMsg","添加事件信息");
				model.addAttribute("headValue",value);
			}else if(PbcEventType.VERIFY_ACCOUNT.getValue().equals(value)){//账户涉案/可疑名单验证
				logger.info("反馈表------{账户涉案/可疑名单验证}----->{跳转}");
				pbcFeedBack.setYes("ff");
				
				//pbcFeedBack.setTxCode(PbcTransCode.PBC_TRANS_CODE_501.getValue());//可疑名单上报-异常开户
				pbcFeedBack.setTransSerialNumber(commonUntil.getReqHeader());//传输报文流水号
				pbcFeedBack.setApplicationId(pbcCodeUntil.getReqHeader("0501"));//业务申请编号
				//pbcFeedBack.setFeatureCode(PbcFeatureCodeUtil.PBC_FEATURE_CODE_BREACH.getValue());//2001-黑名单账户
				//pbcFeedBack.setOlinePayCompanyName("阿里巴巴");
				
				pbcFeedBack.setChangeType(PbcEventType.VERIFY_ACCOUNT.getValue());
				model.addAttribute("headMsg","添加验证信息");
			}else if(PbcEventType.IDENTITY_ACCOUNT.getValue().equals(value)){//身份涉案/可疑名单验证
				logger.info("反馈表------{身份涉案/可疑名单验证}----->{跳转}");
				pbcFeedBack.setYes("gg");
				
				//pbcFeedBack.setTxCode(PbcTransCode.PBC_TRANS_CODE_502.getValue());//可疑名单上报-异常开户
				pbcFeedBack.setTransSerialNumber(commonUntil.getReqHeader());//传输报文流水号
				pbcFeedBack.setApplicationId(pbcCodeUntil.getReqHeader("0501"));//业务申请编号
				
				pbcFeedBack.setChangeType(PbcEventType.IDENTITY_ACCOUNT.getValue());
				model.addAttribute("headMsg","添加验证信息");
			}else{// 可疑名单上报-异常开户
				logger.info("反馈表------{可疑名单上报-异常开户}----->{跳转}");
				pbcFeedBack.setYes("bb");
				model.addAttribute("addValue", "addValue");
				//pbcFeedBack.setChangeType(pbcFeedBack.getChangeType());
				pbcFeedBack.setApplicationId(pbcCodeUntil.getReqHeader("0402"));
				String headValue = request.getParameter("headValue"); //判断这个按钮是保存还是上行
				
				if(PbcEventType.CASE_REPORT.getValue().equals(headValue)){//案件举报 
					model.addAttribute("headMsg","添加案件信息");
					pbcFeedBack.setChangeType(headValue);
				}else if(PbcEventType.ACCOUNTS_INVOLVED.getValue().equals(headValue)){//可疑名单上报-涉案账户
					model.addAttribute("headMsg","添加账户信息");
					pbcFeedBack.setChangeType(headValue);
				}else if(PbcEventType.EXCEPTION_EVENTS.getValue().equals(headValue)){//可疑名单上报-异常事件
					model.addAttribute("headMsg","添加事件信息");
					pbcFeedBack.setChangeType(headValue);
				}else{
					model.addAttribute("headMsg","添加案件信息");
				}
			}
			
		}else{
			pbcFeedBack.setYes("aa");
			pbcFeedBack.setChangeType(PbcEventType.CASE_REPORT.getValue());//2001-黑名单账户
			//pbcFeedBack.setTxCode(PbcTransCode.PBC_TRANS_CODE_1.getValue());//案件举报
			pbcFeedBack.setTransSerialNumber(commonUntil.getReqHeader());//传输报文流水号
			pbcFeedBack.setApplicationId(pbcCodeUntil.getReqHeader("0402"));//业务申请编号
			model.addAttribute("headMsg","添加案件信息");
		}
		List<EnumBean> pbcEventType = Lists.newArrayList();
		//手续费扣除方式
		for (PbcEventType checkFlg : PbcEventType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			pbcEventType.add(ct);
		}
		model.addAttribute("pbcEventType", pbcEventType);
		
		
		model.addAttribute("pbcFeedBack", pbcFeedBack);
		return "modules/pbc/feedback";
		
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/ajax")
	public String ajaxaEntity(@RequestParam("value") String value){
		
		//传输报文流水号编码规则
		CommonUntil commonUntil=new CommonUntil();
		
		//传输报文流水号编码规则
		PbcCodeUntil pbcCodeUntil=new PbcCodeUntil();
		
		Map<Object,Object> map=new HashMap<>();
		if(PbcEventType.CASE_REPORT.getValue().equals(value)){
			map.put("txCode", PbcTransCode.PBC_TRANS_CODE_1.getValue());
			map.put("transSerialNumber", commonUntil.getReqHeader());
			map.put("applicationId", pbcCodeUntil.getReqHeader("0402"));
		}
		JsonMapperUtil json=new JsonMapperUtil();
		String aJaxValue = json.toJson(map);
		
		return aJaxValue;
		
	}
	
	/**
	 * 
	 * @throws IOException 
	 * @方法说明：举报及可疑验证入库操作
	 * @时间：Dec 13, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("pbc:pbcFeedback:edit")
	@RequestMapping(value ="insert")
	public String insert(PbcFeedBack pbcFeedBack,
						 HttpServletRequest request,
						 RedirectAttributes redirectAttributes, 
						 @RequestParam("file") MultipartFile file,
						 Model model) throws IOException{
		
		String inter = request.getParameter("urlValue"); //判断这个按钮是保存还是上行
		String addValue = request.getParameter("addValue"); //判断这操作是普通的还是添加
		
		String msg="";
		try {
			msg = pbcFeedBackService.saveEntity(pbcFeedBack, request, redirectAttributes, model,inter,addValue,file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		User user = UserUtils.getUser();
		String name = user.getLoginName();
		pbcFeedBack.setCreateUser(name);
		pbcFeedBack.setStatus(Constants.STATIC_Y);
		pbcFeedBack.setCreateTime(new Date());
		
		String inter = request.getParameter("urlValue"); //判断这个按钮是保存还是上行
		String addValue = request.getParameter("addValue"); //判断这操作是普通的还是添加
		
		int saveEntity =0;
		
		if(StringUtils.isNotBlank(inter)){//上传按钮，调取网关接口把数据传给网关，然后存到数据库
			
			logger.info("上传操作,调取网关接口把数据传给网关,然后存到数据库----->{风控系统电信反诈骗}}"+inter);
		}
		if(StringUtils.isNotBlank(addValue)){//添加页面入库
			
			logger.info("添加页面入库操作----->{风控系统电信反诈骗}"+addValue);
		
			PbcTransaction pbcTransaction=new PbcTransaction();
			pbcTransaction.setApplicationId(pbcFeedBack.getApplicationId());//请求单标识
			pbcTransaction.setTransactionType(pbcFeedBack.getTransactionType());//交易类型
			pbcTransaction.setBorrowingSigns(pbcFeedBack.getBorrowingSigns());//借贷标识
			pbcTransaction.setCurrency(pbcFeedBack.getCurrency());//币种
			pbcTransaction.setTransactionAmount(pbcFeedBack.getTransactionAmount());//交易金额
			pbcTransaction.setAccountBalance(pbcFeedBack.getAccountBalance());//交易余额
			pbcTransaction.setTransactionTime(pbcFeedBack.getTransactionTime());//交易时间
			pbcTransaction.setApplicationId(pbcFeedBack.getApplicationId()); //交易流水号
			pbcTransaction.setOpponentAccountType(pbcFeedBack.getOpponentAccountType());//交易对方账户类型
			pbcTransaction.setTransactionType(pbcFeedBack.getTransactionType()); //交易设备类型
			pbcTransaction.setOpponentAccountNumber(pbcFeedBack.getOpponentAccountNumber());//交易对方账号/银行卡号
			pbcTransaction.setOpponentAccountOwnerName(pbcFeedBack.getOpponentAccountOwnerName());//交易对方户主姓名
			pbcTransaction.setOpponentAccountOwnerId(pbcFeedBack.getOpponentAccountOwnerId());//交易对方户主证件号码
			pbcTransaction.setTransactionIp(pbcFeedBack.getTransactionIp());//交易设备IP
			pbcTransaction.setTransactionMac(pbcFeedBack.getTransactionMac());//MAC地址
			pbcTransaction.setTransactionCeviceCode(pbcFeedBack.getTransactionCeviceCode());//交易设备号
			pbcTransaction.setLogNumber(pbcFeedBack.getLogNumber());//日志号
			pbcTransaction.setVoucherType(pbcFeedBack.getVoucherType());//凭证种类
			pbcTransaction.setTransactionStatus(pbcFeedBack.getTransactionStatus());//交易是否成功
			pbcTransaction.setVoucherCode(pbcFeedBack.getVoucherCode());//凭证号
			pbcTransaction.setMerchantName(pbcFeedBack.getMerchantName());//商户名称
			pbcTransaction.setMerchantCode(pbcFeedBack.getMerchantCode());//商户注册号
			pbcTransaction.setTransactionRemark(pbcFeedBack.getTransactionRemark());//交易摘要
			pbcTransaction.setRemark(pbcFeedBack.getRemark());//备注
			
			pbcTransaction.setAccountId((long) 123);
			
			try {//添加交易信息
				
				saveEntity = pbcTransactionService.saveEntity(pbcTransaction);
				Long pbcId = pbcFeedBack.getPbcId();
				System.out.println(pbcId);
				
				if(saveEntity !=0){
					addMessage(redirectAttributes, "反馈表插入成功");
					logger.info("反馈表------>{插入成功}"+pbcFeedBack.toString());
				}
			} catch (Exception e) {
				addMessage(redirectAttributes, "反馈表插入失败");
				logger.error("反馈表------>{插入失败}"+e.getMessage());
			}
			
		}else{// 普通数据入库
			
			/*logger.info("普通添加页面入库操作----->{风控系统电信反诈骗}");
			
			if(StringUtils.isNotBlank(pbcFeedBack.getAccountType())){
				
				logger.info("可疑名单上报-涉案账户页面入库操作----->{风控系统电信反诈骗}"+pbcFeedBack.toString());
				try {
					saveEntity = pbcFeedbackService.saveEntity(pbcFeedBack);
					Long pbcId = pbcFeedBack.getPbcId();//插入时返回的主键id
					
					PbcPerAccount pbcPerAccount=new PbcPerAccount();//个人账户表保存数据
					pbcPerAccount.setFeedbackId(pbcId);//反馈ID
					pbcPerAccount.setAccountType(pbcFeedBack.getAccountType());//账户类型
					pbcPerAccount.setAccountName(pbcFeedBack.getAccountName());//查询账户名
					pbcPerAccount.setAccountOwnerName(pbcFeedBack.getAccountOwnerName());//户主姓名
					pbcPerAccount.setAccountOwnerIdType(pbcFeedBack.getAccountOwnerIdType());//户主证件类型
					pbcPerAccount.setAccountOwnerId(pbcFeedBack.getAccountOwnerId());//户主证件号
					pbcPerAccount.setPhoneNumber(pbcFeedBack.getPhoneNumber());//联系手机号
					pbcPerAccount.setAddress(pbcFeedBack.getAddress());//常用通讯地址
					pbcPerAccount.setPostCode(pbcFeedBack.getPostCode());//邮政编码
					pbcPerAccount.setStatus(Constants.STATIC_Y);
					try {//案件举报-添加案件信息
						saveEntity = pbcPerAccountService.saveEntity(pbcPerAccount);
						if(saveEntity !=0){
							addMessage(redirectAttributes, "插入成功");
							logger.info("个人账户表插入成功------>{风控系统电信反诈骗}"+pbcFeedBack.toString());
						}
					} catch (Exception e) {
						addMessage(redirectAttributes, "个人账户表插入失败");
						logger.error("个人账户表插入失败------>{风控系统电信反诈骗}"+e.getMessage());
					}
				} catch (Exception e) {
					addMessage(redirectAttributes, "反馈表插入失败");
					logger.error("反馈表插入失败------>{风控系统电信反诈骗}"+e.getMessage());
					return "redirect:"+Global.getAdminPath()+"/pbc/feedback/save"; 
				}
				
			}else{
				try {//案件举报-添加案件信息
					saveEntity = pbcFeedbackService.saveEntity(pbcFeedBack);
					Long pbcId = pbcFeedBack.getPbcId();
					System.out.println(pbcId);
					if(saveEntity !=0){
						addMessage(redirectAttributes, "反馈表插入成功");
						logger.info("反馈表------>{插入成功}"+pbcFeedBack.toString());
					}
				} catch (Exception e) {
					addMessage(redirectAttributes, "反馈表插入失败");
					logger.error("反馈表------>{插入失败}"+e.getMessage());
				}*/
			

		addMessage(redirectAttributes, msg);
		return "redirect:"+Global.getAdminPath()+"/pbc/feedback/save"; 
		
	}
}
