package com.heepay.manage.modules.pbc.web;

import java.io.IOException;
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
import com.heepay.manage.modules.pbc.entity.PbcAccountDynamic;
import com.heepay.manage.modules.pbc.entity.PbcFeedBack;
import com.heepay.manage.modules.pbc.service.PbcAccountDynamicService;
import com.heepay.manage.modules.pbc.service.PbcFeedBackService;
import com.heepay.manage.modules.pbc.web.rpc.client.PbcPaymentAccountRpcClient;
import com.heepay.manage.modules.pbc.web.utils.CommonUntil;
import com.heepay.manage.modules.pbc.web.utils.PbcCodeUntil;
import com.heepay.manage.modules.riskLogs.service.RiskLogsService;
import com.heepay.manage.modules.sys.utils.UserUtils;


/***
 * 
* 
* 描    述：可疑交易上报-单笔交易上报-关联全支付账号
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
@RequestMapping(value = "${adminPath}/pbc/pbcPaymentAccountFour/view/four")
public class PbcAccountTransDetailFour extends BaseController {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private PbcFeedBackService pbcFeedBackService;//反馈表服务层
	
	@Autowired
	private PbcAccountDynamicService pbcAccountDynamicService;//反馈表服务层
	
	
	@Autowired
	private PbcPaymentAccountRpcClient pbcPaymentAccountRpcClient;//网关上传客户端
	
	@Autowired
	private RiskLogsService riskLogsService;//记录日志
	
	/**
	 * 
	 * @方法说明：添加页面跳转
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@RequiresPermissions("pbc:pbcPaymentAccount:view")
	@RequestMapping(value = { "list", "" })
	public String list(PbcAccountDynamic pbcAccountDynamic, HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
		
		String pageNo = request.getParameter("pageNo");
		logger.info("可疑名单上报-单笔交易------>{关联全支付账号}----->{查询开始}:");
		
		String feedBackId = request.getParameter("feedBackId");
				
		String no = request.getParameter("no");
			pbcAccountDynamic.setNo(no);//查看页面中的查看操作
		
		if(StringUtils.isNotBlank(feedBackId)){
			//查询调取我的服务
			long feedBackIds=Long.parseLong(feedBackId);
			pbcAccountDynamic.setFeedBackId(feedBackIds);
			model = pbcAccountDynamicService.pbcPaymentAccountPage(new Page<PbcAccountDynamic>(request, response), pbcAccountDynamic,model,pageNo);
			logger.info("可疑名单上报-单笔交易------>{查看页面中的查看操作}----->{查询结束}:");
		}else{
			//主动添加时，需要的数据
			try {
				String applicationId = request.getParameter("applicationId");
				
				pbcAccountDynamic.setToId(PbcPaymentAccountEnums.PBC_TOID.getValue());//接收机构ID
				pbcAccountDynamic.setTxCode(PbcTransCode.PBC_TRANS_CODE_406.getValue());//交易类型编码
				pbcAccountDynamic.setReportCodes(PbcPaymentAccountEnums.PBC_REPORTCODES.getValue());//上报机构编码
				pbcAccountDynamic.setOperatorPhoneNumber(PbcPaymentAccountEnums.PBC_NUMBER.getValue());//我方经办人电话
				pbcAccountDynamic.setFeedBackOrgName(PbcPaymentAccountEnums.PBC_FEED_NAME.getValue()); //反馈机构名称
				pbcAccountDynamic.setOperatorName(UserUtils.getUser().getName());//我经办人姓名  当前操作人  
				
				CommonUntil commonUntil=new CommonUntil();
				pbcAccountDynamic.setTransSerialNumber(commonUntil.getReqHeader());//传输报文流水号 
				PbcCodeUntil PbcCodeUntil=new PbcCodeUntil();
				String reqHeader = PbcCodeUntil.getReqHeader("0406");
				pbcAccountDynamic.setApplicationId(reqHeader);// 0406-可疑交易上报-单笔交易上报
				
				//判断是否是已产生 业务申请编号 并且上传过文件
				if(StringUtils.isNotBlank(applicationId)){
					pbcAccountDynamic.setApplicationId(applicationId);	
					pbcAccountDynamic.setApplicationCode(applicationId);//用于查询和这个记录关联的商户
				}else{
					pbcAccountDynamic.setApplicationId(reqHeader);
					pbcAccountDynamic.setApplicationCode(reqHeader);//用于查询和这个记录关联的商户
				}
				model = pbcAccountDynamicService.findRiskProductQuotaPage(new Page<PbcAccountDynamic>(request, response), pbcAccountDynamic,model,pageNo);
				logger.info("可疑名单上报-单笔交易------>{查看页面中的查看操作}----->{查询结束}:");
			} catch (Exception e) {
				logger.error("可疑名单上报-单笔交易----->{查询数据异常}:"+e);
				throw new Exception(e);
			}
		}
		return "modules/pbc/pbcPaymentAccountFour";
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
		logger.info("可疑名单上报-单笔交易------>{删除}----->{开始}");
		int num=pbcAccountDynamicService.deleteData(feedBackId);
		if(num>0){
			addMessage(redirectAttributes, "删除成功!");
		}else{
			addMessage(redirectAttributes, "删除失败!");
		}
		logger.info("可疑名单上报-单笔交易------>{删除}----->{结束}");
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
	public String save(PbcAccountDynamic pbcAccountDynamic,
						 HttpServletRequest request,
						 RedirectAttributes redirectAttributes, 
						 Model model) throws IOException{
		
		
		logger.info("可疑名单上报-单笔交易----->{关联全支付账号}----->{入库开始}:");
		
		pbcAccountDynamic.setFeedType(PbcEventType.SINGLE_TRANSATION.getValue());//反馈类型   
		String checkFlg = request.getParameter("checkFlg");//页面是否是同意上传
		String feedBackId = request.getParameter("feedBackId");
		
		//页面上审核通过但是不保存
		String redioValue = request.getParameter("redioValue");   
		if(StringUtils.isNotBlank(redioValue)){
			pbcAccountDynamic.setRiskStatus(RiskAuditStatus.TQS_Y.getValue());//N 待审核 Y 审核通过
			 //说明审核失败，直接保存
			int msg=pbcFeedBackService.updateEntityByLoadTwo(pbcAccountDynamic);
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
			pbcAccountDynamic.setRiskStatus(RiskAuditStatus.TQS_N.getValue());//N 待审核 Y 审核通过
			int msg=pbcFeedBackService.updateEntityByLoadTwo(pbcAccountDynamic);
			if(msg!=0){
				addMessage(redirectAttributes, "更新成功");
				logger.info("关联全支付账号----->{更新完成}:");
			}else{
				addMessage(redirectAttributes, "插入成功失败");
				logger.info("关联全支付账号----->{更新失败}:");
			}
			
			//跳回查看的总列表
			return "redirect:"+Global.getAdminPath()+"/pbc/verification";
			
		}else{
			
			if(StringUtils.isNotBlank(feedBackId)){ //查看页面过来的保存
				long feedBackIds=Long.parseLong(feedBackId);
				pbcAccountDynamic.setFeedBackId(feedBackIds);
				int msg=pbcFeedBackService.saveEntityByFeedBank(pbcAccountDynamic);
				if(msg!=0){
					addMessage(redirectAttributes, "更新成功");
					logger.info("可疑名单上报-单笔交易----->{关联全支付账号}----->{更新完成}:");
				}else{
					addMessage(redirectAttributes, "插入成功失败");
					logger.info("可疑名单上报-单笔交易----->{关联全支付账号}----->{插入成功失败}:");
				}
				
			}else{ //添加页面过来的保存
				pbcAccountDynamic.setRiskStatus(RiskAuditStatus.TQS_N.getValue());//N 待审核 Y 审核通过
				int msg=pbcFeedBackService.saveEntityByFeedBank(pbcAccountDynamic);
				if(msg!=0){
					addMessage(redirectAttributes, "插入成功");
					logger.info("疑名单上报-单笔交易----->{关联全支付账号}----->{入库完成}:");
				}else{
					addMessage(redirectAttributes, "插入成功失败");
					logger.info("疑名单上报-单笔交易----->{关联全支付账号}----->{插入成功失败}:");
				}
			}
			if(StringUtils.isNotBlank(feedBackId)){
				//查询过来的数据
				return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccountFour/view/four?feedBackId="+feedBackId;
			}else{
				//添加页面过来的数据
				return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccountFour/view/four"; 
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
	public String loadModel(RedirectAttributes redirectAttributes,HttpServletRequest request,@RequestParam("file") MultipartFile file) throws IOException{
		
		String applicationCode = request.getParameter("applicationId");
		
		logger.info("疑名单上报-单笔交易----->{关联全支付账号}----->{上传文件入库}:");
		try {
			String yes="true";
			String msg=pbcAccountDynamicService.loadModel(file,applicationCode,yes);
			addMessage(redirectAttributes, msg);
			logger.info("疑名单上报-单笔交易----->{关联全支付账号}----->{上传文件入库成功}:");
		} catch (Exception e) {
			addMessage(redirectAttributes, "插入成功失败");
			logger.error("疑名单上报-单笔交易----->{关联全支付账号}----->{插入成功失败}:"+e);
		}
		
		String feedBackId = request.getParameter("feedBackId");
		//判断是否是举报及可疑查询页面跳转
		if(StringUtils.isNotBlank(feedBackId)){
			// 举报及可疑查询页面跳转
			logger.info("查询账户关联银行卡----->{ 举报及可疑查询页面跳转}");
			return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccountFour/view/four?feedBackId="+feedBackId; 		
		}else{
			logger.info("查询账户关联银行卡----->{ 举报及可疑添加页面跳转}");
			return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccountFour/view/four?applicationId="+applicationCode; 
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
	public String load(PbcAccountDynamic pbcAccountDynamic,
					   HttpServletRequest request,
					   RedirectAttributes redirectAttributes,
					   HttpServletResponse response) throws IOException{
		
		logger.info("上传数据到网关----->{查询开始}");
		//String feedBackId = request.getParameter("feedBackId");//页面地址判断
		String checkFlg = request.getParameter("checkFlg");//页面是否是同意上传
		
		if(checkFlg.equals("Y")){
			//String featureCode = pbcPaymentAccount.getFeatureCode();
			String applicationCode = pbcAccountDynamic.getApplicationId();
			
			logger.info("上传数据到网关----->{查询中}----->{featureCode}="+applicationCode);
			
			try {
				List<PbcAccountDynamic> list = pbcAccountDynamicService.getListByfeatureCode(applicationCode);
				
				for (PbcAccountDynamic pbcAccounts : list) {
					//PbcFeedBack pbcFeedBack=new PbcFeedBack();
					pbcAccounts.setToId(pbcAccountDynamic.getToId());//接收机构ID
					pbcAccounts.setTxCode(pbcAccountDynamic.getTxCode());//交易类型编码
					pbcAccounts.setTransSerialNumber(pbcAccountDynamic.getTransSerialNumber());//传输报文流水号
					pbcAccounts.setApplicationId(pbcAccountDynamic.getApplicationId());//业务申请编号
					pbcAccounts.setFeatureCodes(pbcAccountDynamic.getFeatureCodes());//事件特征码
					pbcAccounts.setAbnormalDescribe(pbcAccountDynamic.getAbnormalDescribe());//异常事件描述(事件特征码为9999时必填)
					pbcAccounts.setReportCodes(pbcAccountDynamic.getReportCodes());//上报机构编码
					pbcAccounts.setOperatorName(pbcAccountDynamic.getOperatorName());//我经办人姓名
					pbcAccounts.setOperatorPhoneNumber(pbcAccountDynamic.getOperatorPhoneNumber());//我方经办人电话
					pbcAccounts.setStatus(pbcAccountDynamic.getStatus());//上报状态
					pbcAccounts.setRiskStatus(pbcAccountDynamic.getRiskStatus());//风控审核状态
					pbcAccounts.setFeedType(pbcAccountDynamic.getFeedType());//反馈类型   
					pbcAccounts.setDealTime(pbcAccountDynamic.getDealTime()); //风控专员处理时间
					pbcAccounts.setRiskOperator(pbcAccountDynamic.getRiskOperator()); //审核人
					pbcAccounts.setRiskTime(pbcAccountDynamic.getRiskTime());//审核时间
					pbcAccounts.setReportTime(pbcAccountDynamic.getReportTime());//上报时间
					pbcAccounts.setFeedBackRemark(pbcAccountDynamic.getFeedBackRemark());  //查询反馈说明
					pbcAccounts.setFailRemark(pbcAccountDynamic.getFailRemark());//上报失败原因
					pbcAccounts.setRemark(pbcAccountDynamic.getRemark()); //风控审核备注
					
					//将数据组装取来发送给网关
					JsonMapperUtil jsonUtils=new JsonMapperUtil();
					LoadEntityTwo.getLoad load=new LoadEntityTwo().new getLoad<>();
					
					load.setBody(pbcAccounts);
					
					//单个商户发送
					//pbcFeedBack.setAccountsList(list);
					
					String json = jsonUtils.toJson(load);
					
					String replace = json.replace("null", "\"-\"")
										 .replace("applicationId", "applicationID")               
										 .replace("transInBankId", "transInBankID")               
										 .replace("transOutBankId", "transOutBankID")               
										 .replace("featureDescription", "abnormalDescribe")       
										 .replace("feedBackOrgName", "feedbackOrgName")           
										 .replace("accountNumber", "merchantNumber")  //商户号         
										 .replace("featureCodes", "featureCode")                  
										 .replace("reportCodes", "repoprtOrgID")                  
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
										 .replace("accountNumber", "merchantNumber")              
										 .replace("transList", "transactions");                   
					
					String msg = pbcPaymentAccountRpcClient.reportSingleTransQuery(replace);
					Map map = jsonUtils.fromJson(msg, Map.class);
					String value = (String) map.get("code");
					String description = (String) map.get("description");
					
					PbcFeedBack feedBack=new PbcFeedBack();
					if(value.equals(PbcInterfaceStatus.SUCCESS.getValue())){// 上传成功
						feedBack.setCode(value);//返回码
						feedBack.setDescription(description); //返回消息
						feedBack.setStatus(ReportStatus.SUCCESE_S.getValue());//上报成功
						//feedBack.setRiskStatus(RiskAuditStatus.TQS_Y.getValue());//风控审核状态(Y:审核成功 N:审核失败)
						
						addMessage(redirectAttributes, "上传成功");
					}else{// 上传失败
						feedBack.setCode(value);//返回码
						feedBack.setDescription(description); //返回消息
						feedBack.setStatus(ReportStatus.FREPORTED.getValue());//上报失败
						
						addMessage(redirectAttributes, "未上传成功原因: "+description);
					}
					
					feedBack.setRiskStatus(RiskAuditStatus.TQS_Y.getValue());//风控审核状态(Y:审核成功 N:审核失败)
					feedBack.setReportTime(new Date());//申请时间
					feedBack.setDealTime(new Date());//风控专员处理时间
					feedBack.setRiskOperator(UserUtils.getUser().getName());//风控审核人
					feedBack.setFeedBackId(pbcAccountDynamic.getFeedBackId());
					feedBack.setNum(1);//上报次数
					feedBack.setRiskRemark(pbcAccountDynamic.getRiskRemark());//审核备注
					
					pbcFeedBackService.updateEntity(feedBack);
					riskLogsService.savaEntity("上报", "可疑名单上报-单笔交易{返回结果}", request);
				}
				
				
				logger.info("上传数据到网关----->{可疑名单上报-单笔交易}----->{操作结束}");
			} catch (Exception e) {
				//上传失败返回原来的主页面
				addMessage(redirectAttributes, "上传失败 原因："+e.getMessage());
			}
		}
		//跳回查询的地址
		return "redirect:"+Global.getAdminPath()+"/pbc/verification";
	}
	
}
