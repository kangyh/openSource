package com.heepay.manage.modules.pbc.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.pbc.PbcEventType;
import com.heepay.enums.pbc.PbcInterfaceStatus;
import com.heepay.enums.pbc.PbcPaymentAccountEnums;
import com.heepay.enums.pbc.PbcTransCode;
import com.heepay.enums.pbc.ReportStatus;
import com.heepay.enums.pbc.RiskAuditStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.pbc.entity.PbcAccountDetailSubject;
import com.heepay.manage.modules.pbc.entity.PbcFeedBack;
import com.heepay.manage.modules.pbc.entity.PbcPaymentAccount;
import com.heepay.manage.modules.pbc.service.PbcAccountDetailSubjectService;
import com.heepay.manage.modules.pbc.service.PbcFeedBackService;
import com.heepay.manage.modules.pbc.service.PbcPaymentAccountService;
import com.heepay.manage.modules.pbc.web.rpc.client.PbcPaymentAccountRpcClient;
import com.heepay.manage.modules.pbc.web.utils.CommonUntil;
import com.heepay.manage.modules.pbc.web.utils.PbcCodeUntil;
import com.heepay.manage.modules.riskLogs.service.RiskLogsService;
import com.heepay.manage.modules.sys.utils.UserUtils;


/***
 * 
* 
* 描    述：可疑名单上报-异常消费支付账户-关联全支付账号
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
@RequestMapping(value = "${adminPath}/pbc/pbcPaymentAccount/view")
public class PbcPaymentAccountController extends BaseController {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private RiskLogsService riskLogsService;//记录日志
	
	@Autowired
	private PbcPaymentAccountService pbcPaymentAccountService;//关联全支付账号表服务层
	
	@Autowired
	private PbcFeedBackService pbcFeedBackService;//反馈表服务层
	
	@Autowired
	private PbcAccountDetailSubjectService pbcAccountDetailSubjectService;//反馈表服务层
	
	@Autowired
	private PbcPaymentAccountRpcClient pbcPaymentAccountRpcClient;//网关上传客户端
	
	
	/**
	 * 
	 * @方法说明：添加页面跳转
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("pbc:pbcPaymentAccount:view")
	@RequestMapping(value = { "list", "" })
	public String list(PbcPaymentAccount pbcPaymentAccount, HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
		
		String pageNo = request.getParameter("pageNo");
		logger.info("关联全支付账号----->{查询开始}:");
		
		String feedBackId = request.getParameter("feedBackId");
		
		String no = request.getParameter("no");
		if(StringUtils.isNoneBlank(no) ){
			//用于页面判断是否是查看链接需要隐藏
			request.setAttribute("ngp", "Y");
			logger.info("关联全支付账号----->{查询操作}--->{条件}:"+no);
		}
		pbcPaymentAccount.setNo(no);//查看页面中的查看操作
		
		if(StringUtils.isNotBlank(feedBackId)){
			//查询调取我的服务
			long feedBackIds=Long.parseLong(feedBackId);
			pbcPaymentAccount.setFeedBackId(feedBackIds);
			logger.info("关联全支付账号----->{审核操作}--->{条件}:  "+feedBackId);
			model = pbcPaymentAccountService.pbcPaymentAccountPage(new Page<PbcPaymentAccount>(request, response), pbcPaymentAccount,model,pageNo);
			
		}else{
			//主动添加时，需要的数据
			try {
				String applicationId = request.getParameter("applicationId");
				request.setAttribute("applicationIdLoad", applicationId);
				
				pbcPaymentAccount.setToId(PbcPaymentAccountEnums.PBC_TOID.getValue());//接收机构ID
				pbcPaymentAccount.setTxCode(PbcTransCode.PBC_TRANS_CODE_3.getValue());//交易类型编码
				pbcPaymentAccount.setReportCodes(PbcPaymentAccountEnums.PBC_REPORTCODES.getValue());//上报机构编码
				pbcPaymentAccount.setOperatorPhoneNumber(PbcPaymentAccountEnums.PBC_NUMBER.getValue());//我方经办人电话
				pbcPaymentAccount.setFeedBackOrgName(PbcPaymentAccountEnums.PBC_FEED_NAME.getValue()); //反馈机构名称
				pbcPaymentAccount.setOperatorName(UserUtils.getUser().getName());//我经办人姓名  当前操作人  
				
				CommonUntil commonUntil=new CommonUntil();
				pbcPaymentAccount.setTransSerialNumber(commonUntil.getReqHeader());//传输报文流水号 
				PbcCodeUntil PbcCodeUntil=new PbcCodeUntil();
				String reqHeader = PbcCodeUntil.getReqHeader("0403");// 业务申请编号 //业务申请编号  0403-可疑名单上报-异常开卡
				
				//判断是否是已产生 业务申请编号 并且上传过文件
				if(StringUtils.isNotBlank(applicationId)){
					pbcPaymentAccount.setApplicationId(applicationId);	
					pbcPaymentAccount.setApplicationCode(applicationId);//用于查询和这个记录关联的商户
				}else{
					pbcPaymentAccount.setApplicationId(reqHeader);
					pbcPaymentAccount.setApplicationCode(reqHeader);//用于查询和这个记录关联的商户
				}
				//application_code
				model = pbcPaymentAccountService.findRiskProductQuotaPage(new Page<PbcPaymentAccount>(request, response), pbcPaymentAccount,model,pageNo);
				
			} catch (Exception e) {
				logger.error("关联全支付账号----->{查询数据异常}:", e);
				throw new Exception(e);
			}
		}
		return "modules/pbc/pbcPaymentAccount";
	}
	
	/**
	 * 
	 * @方法说明：删除数据操作
	 * @时间：Jan 12, 20174:07:04 PM
	 * @创建人：wangl
	 */
	@RequiresPermissions("pbc:pbcPaymentAccount:edit")
	@RequestMapping(value ="delete/{feedBackId}")
	public String deleteData(@PathVariable("feedBackId") long feedBackId,
							 HttpServletRequest request,
						     RedirectAttributes redirectAttributes,
						     HttpServletResponse response){
		logger.info("单笔交易------>{删除}----->{开始}");
		int num=pbcPaymentAccountService.deleteData(feedBackId);
		if(num>0){
			addMessage(redirectAttributes, "删除成功!");
		}else{
			addMessage(redirectAttributes, "删除失败!");
		}
		logger.info("单笔交易------>{删除}----->{开始}");
		//跳回查看的总列表
		return "redirect:"+Global.getAdminPath()+"/pbc/verification";
	}
	
	
	/**
	 * 
	 * @方法说明：数据入库
	 * @时间：Dec 19, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("pbc:pbcPaymentAccount:edit")
	@RequestMapping(value ="save")
	public String save(PbcPaymentAccount pbcPaymentAccount,
						 HttpServletRequest request,
						 RedirectAttributes redirectAttributes, 
						 Model model) throws IOException{
		
		
		logger.info("关联全支付账号----->{入库开始}:");
		String feedBackId = request.getParameter("feedBackId");
		String checkFlg = request.getParameter("checkFlg");//页面是否是同意上传
		
		//页面上审核通过但是不保存
		String redioValue = request.getParameter("redioValue");   
		if(StringUtils.isNotBlank(redioValue)){
			pbcPaymentAccount.setRiskStatus(RiskAuditStatus.TQS_Y.getValue());//N 待审核 Y 审核通过
			 //说明审核失败，直接保存
			int msg=pbcFeedBackService.updateEntityByLoad(pbcPaymentAccount);
			if(msg!=0){
				addMessage(redirectAttributes, "审核成功");
				logger.info("关联全支付账号----->{审核完成}:"+msg);
			}else{
				addMessage(redirectAttributes, "审核失败");
				logger.error("关联全支付账号----->{审核失败}:");
			}
			
			//跳回查看的总列表
			return "redirect:"+Global.getAdminPath()+"/pbc/verification";
		}
		if(StringUtil.isBlank(checkFlg)){
			checkFlg="Y";
		}
		if(checkFlg.equals("N")){ //说明审核失败，直接保存
			pbcPaymentAccount.setRiskStatus(RiskAuditStatus.TQS_N.getValue());//N 待审核 Y 审核通过
			int msg=pbcFeedBackService.updateEntityByLoad(pbcPaymentAccount);
			if(msg!=0){
				addMessage(redirectAttributes, "更新成功");
				logger.info("关联全支付账号----->{更新完成}:"+msg);
			}else{
				addMessage(redirectAttributes, "插入成功失败");
				logger.error("关联全支付账号----->{更新失败}:");
			}
			
			//跳回查看的总列表
			return "redirect:"+Global.getAdminPath()+"/pbc/verification";
			
		}else{

			//根据feedBackId判断这个是新添加的操作还是更行操作
			if(StringUtils.isNotBlank(feedBackId)){ //查看页面过来的保存
				long feedBackIds=Long.parseLong(feedBackId);
				pbcPaymentAccount.setFeedBackId(feedBackIds);
				int msg=pbcFeedBackService.saveEntityByPaymentAccount(pbcPaymentAccount);
				if(msg!=0){
					addMessage(redirectAttributes, "更新成功");
					logger.info("关联全支付账号----->{更新完成}:"+msg);
				}else{
					addMessage(redirectAttributes, "插入成功失败");
					logger.error("关联全支付账号----->{更新失败}:");
				}
				
			}else{ //添加页面过来的保存
				pbcPaymentAccount.setFeedType(PbcEventType.ABNORMAL_ACCOUNTS.getValue());//反馈类型   
				pbcPaymentAccount.setRiskStatus(RiskAuditStatus.TQS_N.getValue()); //风控审核状态(Y:审核成功 N:待审核)
				int msg=pbcFeedBackService.saveEntityByPaymentAccount(pbcPaymentAccount);
				if(msg!=0){
					addMessage(redirectAttributes, "插入成功");
					logger.info("关联全支付账号----->{入库完成}:"+msg);
				}else{
					addMessage(redirectAttributes, "插入失败");
					logger.error("关联全支付账号----->{插入失败}:");
				}
			}
			if(StringUtils.isNotBlank(feedBackId)){
				//查询过来的数据
				return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccount/view?feedBackId="+feedBackId;
			}else{
				//添加页面过来的数据
				return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccount/view"; 
			}

		}
		
		
	}
	
	/**
	 * 
	 * @方法说明：上传文件入库
	 * @时间：Dec 19, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("pbc:pbcPaymentAccount:edit")
	@RequestMapping(value ="loadModel")
	public String loadModel(RedirectAttributes redirectAttributes,@RequestParam("file") MultipartFile file,HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		
 		String applicationId = request.getParameter("applicationId");
		
		logger.info("关联全支付账号----->{上传文件入库}--->{applicationId}="+applicationId);
		try {
			String msg=pbcPaymentAccountService.loadModel(file,applicationId);
			addMessage(redirectAttributes, msg);
			logger.info("关联全支付账号----->{上传文件入库成功}:"+msg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "插入成功失败");
			logger.error("关联全支付账号----->{插入成功失败}:"+e.getMessage());
		}
		
		String feedBackId = request.getParameter("feedBackId");
		
		//判断是否是举报及可疑查询页面跳转
		if(StringUtils.isNotBlank(feedBackId)){
			// 举报及可疑查询页面跳转
			logger.info("查询账户关联银行卡----->{ 举报及可疑查询页面跳转}");
			if(feedBackId.equals("0")){
				// 添加页面操作
				return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccount/view?applicationId="+applicationId; 	
			}else{
				// 查看页面过来的添加
				return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccount/view?feedBackId="+feedBackId; 		
			}
		}else{
			//添加页面操作
			return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccount/view?applicationId="+applicationId; 
		}
	}
	
	/**
	 * 
	 * @方法说明：查询账户关联银行卡页面跳转
	 * @时间：Dec 19, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("pbc:pbcPaymentAccount:edit")
	@RequestMapping(value ="add")
	public String toAdd(PbcAccountDetailSubject pbcAccountDetailSubject, 
						HttpServletRequest request,
						HttpServletResponse response, Model model){
		
		logger.info("查询账户关联银行卡----->{页面跳转}:");
		try {
			//讲银行卡和当前用户关联
			String paymentAccountId = request.getParameter("paymentAccountId");
			
			//用于保存页面地址跳转
			String local = request.getParameter("local");
			String feedBackId = request.getParameter("feedBackId");
			request.setAttribute("feedBackId", feedBackId);
			
			
			String applicationId = request.getParameter("applicationId");
			request.setAttribute("applicationIdLoad", applicationId);
			//用于跳回查询页的数据
			if(StringUtils.isNotBlank(feedBackId)){
				long feedBackIds=Long.parseLong(feedBackId);
				pbcAccountDetailSubject.setFeedBackId(feedBackIds);
			}
			pbcAccountDetailSubject.setLocal(local);
			if(StringUtils.isNotBlank(paymentAccountId)){
				pbcAccountDetailSubject.setMerchantNumber(paymentAccountId);
			}
			model=pbcAccountDetailSubjectService.findRiskProductQuotaPage(new Page<PbcAccountDetailSubject>(request, response), pbcAccountDetailSubject,model,paymentAccountId);
			
		} catch (Exception e) {
			logger.error("查询账户关联银行卡----->{ 举报及可疑查询页面跳转 异常}"+e.getMessage());
		}
		return "modules/pbc/pbcAccountSetailSubject";
		
	}
	
	
	/**
	 * 
	 * @方法说明：账户主体详情表上传文件入库
	 * @时间：Dec 19, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("pbc:pbcPaymentAccount:edit")
	@RequestMapping(value ="loadModel/two")
	public String loadModelTwo(RedirectAttributes redirectAttributes,
							   @RequestParam("file") MultipartFile file,
							   HttpServletRequest request,
							   HttpServletResponse response) throws IOException{
		
		logger.info("查询账户关联银行卡----->{上传文件入库开始}");
		String local = request.getParameter("local");//页面地址判断
		String feedBackId = request.getParameter("feedBackId");//页面地址判断
		request.setAttribute("feedBackId", feedBackId);
		//获取当前反馈信息的id 和 传输报文流水号
		String applicationId = request.getParameter("applicationId");
		String paymentAccountId = request.getParameter("paymentAccountId");
		
		try {
			
			String msg=pbcAccountDetailSubjectService.loadModel(file,paymentAccountId);
			addMessage(redirectAttributes, msg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "插入成功失败");
			logger.info("账户主体详情表上传文件入库----->{上传文件入库失败}:"+e.getMessage());
		}
		
			if("two".equals(local)){
				//可疑名单上报-涉案账户页面跳转
				logger.info("查询账户关联银行卡----->{可疑名单上报-涉案账户页面跳转}");
				if(feedBackId.equals("0")){
					return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccountTwo/view/two?applicationId="+applicationId; 					
				}else{
					return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccountTwo/view/two?feedBackId="+feedBackId; 		
				}
			}else if("three".equals(local)){
				//可疑名单上报-涉案账户历史明细上报页面跳转
				logger.info("查询账户关联银行卡----->{可疑名单上报-涉案账户历史明细上报页面跳转}");
				if(feedBackId.equals("0")){
					return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccountThree/view/three?applicationId="+applicationId;
					
				}else{
					return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccountThree/view/three?feedBackId="+feedBackId; 
				}
			}else{
				//可疑名单上报-异常消费支付账户页面跳转
				logger.info("查询账户关联银行卡----->{可疑名单上报-异常消费支付账户页面跳转}");
				if(feedBackId.equals("0")){
					return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccount/view?applicationId"+paymentAccountId+"&applicationId="+applicationId;
				}else{
					return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccount/view?feedBackId="+feedBackId; 
				}
		}
	}
	
	/**
	 * 
	 * @方法说明：上传数据到网关
	 * @时间：Dec 19, 2016
	 * @创建人：wangl
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	@RequiresPermissions("pbc:pbcPaymentAccount:edit")
	@RequestMapping(value ="save/load")
	public String load(PbcPaymentAccount pbcPaymentAccount,
					   HttpServletRequest request,
					   RedirectAttributes redirectAttributes,
					   HttpServletResponse response) throws IOException{
		
		logger.info("上传数据到网关----->{查询开始}");
		//String feedBackId = request.getParameter("feedBackId");//页面地址判断
		String checkFlg = request.getParameter("checkFlg");//页面是否是同意上传
		if(checkFlg.equals("Y")){
			PbcFeedBack pbcFeedBack=new PbcFeedBack();
			pbcFeedBack.setToId(pbcPaymentAccount.getToId());//接收机构ID
			pbcFeedBack.setTxCode(pbcPaymentAccount.getTxCode());//交易类型编码
			pbcFeedBack.setTransSerialNumber(pbcPaymentAccount.getTransSerialNumber());//传输报文流水号
			pbcFeedBack.setApplicationId(pbcPaymentAccount.getApplicationId());//业务申请编号
			pbcFeedBack.setFeatureCodes(pbcPaymentAccount.getFeatureCodes());//事件特征码
			pbcFeedBack.setAbnormalDescribe(pbcPaymentAccount.getAbnormalDescribe());//异常事件描述(事件特征码为9999时必填)
			pbcFeedBack.setReportCodes(pbcPaymentAccount.getReportCodes());//上报机构编码
			pbcFeedBack.setOperatorName(pbcPaymentAccount.getOperatorName());//我经办人姓名
			pbcFeedBack.setOperatorPhoneNumber(pbcPaymentAccount.getOperatorPhoneNumber());//我方经办人电话
			pbcFeedBack.setStatus(pbcPaymentAccount.getStatus());//上报状态
			pbcFeedBack.setRiskStatus(pbcPaymentAccount.getRiskStatus());//风控审核状态
			pbcFeedBack.setFeedType(pbcPaymentAccount.getFeedType());//反馈类型   
			pbcFeedBack.setDealTime(pbcPaymentAccount.getDealTime()); //风控专员处理时间
			pbcFeedBack.setRiskOperator(pbcPaymentAccount.getRiskOperator()); //审核人
			pbcFeedBack.setRiskTime(pbcPaymentAccount.getRiskTime());//审核时间
			pbcFeedBack.setReportTime(pbcPaymentAccount.getReportTime());//上报时间
			pbcFeedBack.setFeedBackRemark(pbcPaymentAccount.getFeedBackRemark());  //查询反馈说明
			pbcFeedBack.setFailRemark(pbcPaymentAccount.getFailRemark());//上报失败原因
			pbcFeedBack.setRemark(pbcPaymentAccount.getRemark()); //风控审核备注
			
			//String featureCode = pbcPaymentAccount.getFeatureCode();
			String applicationCode = pbcPaymentAccount.getApplicationId();
			
			logger.info("可疑名单上报-异常消费支付账户----->{查询中}----->{featureCode}="+applicationCode);
			
			try {
				List<PbcPaymentAccount> list = pbcPaymentAccountService.getListByfeatureCode(applicationCode);
				
				//将数据组装取来发送给网关
				JsonMapperUtil jsonUtils=new JsonMapperUtil();
				LoadEntityTwo.getLoad load=new LoadEntityTwo().new getLoad<>();
				
				List listPbcPaymentAccount=new ArrayList<PbcPaymentAccount>();
				
				for (PbcPaymentAccount pbcPayments : list) {
				
					//LoadEntityTwo.getLoad loadTwo=new LoadEntityTwo().new getLoad<>();
					//List listPbcPaymentAccount=new ArrayList<PbcPaymentAccount>();
					//用户名和用户的  银行卡信息关联
					List<PbcAccountDetailSubject> listByAccount =pbcAccountDetailSubjectService.getListByMerchantNumber(pbcPayments.getAccountNumber());
					
					if(listByAccount.isEmpty()){//判断是否为空,如果为空则传空数据
						listByAccount.add(new PbcAccountDetailSubject());
					}
					//组装报文体
					pbcPayments.setBankCard(listByAccount); 
					listPbcPaymentAccount.add(pbcPayments);
					
				}
				load.setBody(pbcFeedBack);
				
				pbcFeedBack.setAccountsList(listPbcPaymentAccount);
				
				//pbcFeedBack.setAccounts(pbcPayments);
				//load.setAccountsList(listPbcPaymentAccount);
				//pbcFeedBack.setAccountList(list);
				
				String json = jsonUtils.toJson(load);
				//String joson= JsonMapperUtil.nonEmptyMapper().toJson(load);
				String replace = json.replace("null", "\"-\"")
									 .replace("applicationId", "applicationID")
									 .replace("abnormalDescribe", "featureDescription")
									 .replace("feedBackOrgName", "feedbackOrgName")
									 .replace("featureCodes", "featureCode")
									 .replace("reportCodes", "reportOrgID")
									 .replace("loginId", "loginID")
									 .replace("wechatId", "wechatID")
									 .replace("bankId", "bankID")
									 .replace("regularIpAddress", "regularIPAddress")
									 .replace("accountOwnerIdType", "accountOwnerIDType")
									 .replace("feedBackRemark", "feedbackRemark")
									 .replace("orderId", "orderID")
									 .replace("accountOwnerId", "accountOwnerID")
									 .replace("accountOpenIpAddress", "accountOpenIPAddress")
									 .replace("transInBankId", "transInBankID")
									 .replace("posIdentity", "posIdentity")
									 .replace("transactionIp", "transactionIP")
									 .replace("transactionMac", "transactionMAC")
									 .replace("transList", "transactions");

				logger.info("上传数据到网关----->{可疑名单上报-异常消费支付账户}----->{上报的数据}"+replace);
				String msg = pbcPaymentAccountRpcClient.pbcPaymentAccount(replace);
				
				Map map = jsonUtils.fromJson(msg, Map.class);
				String value = (String) map.get("code");
				String description = (String) map.get("description");
				
				PbcFeedBack feedBack=new PbcFeedBack();
				
				if(value.equals(PbcInterfaceStatus.SUCCESS.getValue())){//上报成功
					feedBack.setCode(value);//返回码
					feedBack.setDescription(description); //返回消息
					feedBack.setStatus(ReportStatus.SUCCESE_S.getValue());//上报成功
					//feedBack.setRiskStatus(RiskAuditStatus.TQS_Y.getValue());//风控审核状态(Y:审核成功 N:审核失败)
					
					addMessage(redirectAttributes, "上传成功");
				}else{
					feedBack.setCode(value);//返回码
					feedBack.setDescription(description); //返回消息
					feedBack.setStatus(ReportStatus.FREPORTED.getValue());//上报失败
					
					addMessage(redirectAttributes, "未上传成功原因: "+description);
				}
				
				feedBack.setRiskStatus(RiskAuditStatus.TQS_Y.getValue());//风控审核状态(Y:审核成功 N:审核失败)
				feedBack.setReportTime(new Date());//申请时间
				feedBack.setDealTime(new Date());//风控专员处理时间
				feedBack.setRiskOperator(UserUtils.getUser().getName());//风控审核人
				feedBack.setFeedBackId(pbcPaymentAccount.getFeedBackId());
				feedBack.setNum(1);//上报次数
				feedBack.setRiskRemark(pbcPaymentAccount.getRiskRemark());//审核备注
				
				
				pbcFeedBackService.updateEntity(feedBack);
				
				riskLogsService.savaEntity("上报", "可疑名单上报-异常消费支付账户主动上报上报", request);
				logger.info("上传数据到网关----->{可疑名单上报-异常消费支付账户}----->{操作结束}");
				
			} catch (Exception e) {
				//上传失败返回原来的主页面
				addMessage(redirectAttributes, "上传失败 原因： "+e.getMessage());
			}
		}
		//跳回查看的总列表
		return "redirect:"+Global.getAdminPath()+"/pbc/verification";
	}
}
