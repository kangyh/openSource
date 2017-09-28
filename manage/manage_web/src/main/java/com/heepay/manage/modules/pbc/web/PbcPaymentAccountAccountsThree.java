package com.heepay.manage.modules.pbc.web;

import java.io.IOException;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.pbc.PbcEventType;
import com.heepay.enums.pbc.PbcPaymentAccountEnums;
import com.heepay.enums.pbc.PbcTransCode;
import com.heepay.enums.pbc.RiskAuditStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.pbc.entity.PbcPaymentAccount;
import com.heepay.manage.modules.pbc.service.PbcFeedBackService;
import com.heepay.manage.modules.pbc.service.PbcPaymentAccountService;
import com.heepay.manage.modules.pbc.web.utils.CommonUntil;
import com.heepay.manage.modules.pbc.web.utils.PbcCodeUntil;
import com.heepay.manage.modules.sys.utils.UserUtils;


/***
 * 
* 
* 描    述：可疑名单上报-涉案账户历史明细上报-关联全支付账号
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
@RequestMapping(value = "${adminPath}/pbc/pbcPaymentAccountThree/view/three")
public class PbcPaymentAccountAccountsThree extends BaseController {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private PbcPaymentAccountService pbcPaymentAccountService;//关联全支付账号表服务层
	
	@Autowired
	private PbcFeedBackService pbcFeedBackService;//反馈表服务层
	
	
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
		}
		pbcPaymentAccount.setNo(no);//查看页面中的查看操作
		
		if(StringUtils.isNotBlank(feedBackId)){
			//查询调取我的服务
			long feedBackIds=Long.parseLong(feedBackId);
			pbcPaymentAccount.setFeedBackId(feedBackIds);
			//
			model = pbcPaymentAccountService.pbcPaymentAccountPage(new Page<PbcPaymentAccount>(request, response), pbcPaymentAccount,model,pageNo);
			logger.info("可疑名单上报-涉案账户历史明细  查看页面过来的操作----->{查询结束}:");
			
		}else{
			//主动添加时，需要的数据
			try {
				String applicationId = request.getParameter("applicationId");
				request.setAttribute("applicationIdLoad", applicationId);
				
				pbcPaymentAccount.setToId(PbcPaymentAccountEnums.PBC_TOID.getValue());//接收机构ID
				pbcPaymentAccount.setTxCode(PbcTransCode.PBC_TRANS_CODE_405.getValue());//交易类型编码
				pbcPaymentAccount.setReportCodes(PbcPaymentAccountEnums.PBC_REPORTCODES.getValue());//上报机构编码
				pbcPaymentAccount.setOperatorPhoneNumber(PbcPaymentAccountEnums.PBC_NUMBER.getValue());//我方经办人电话
				pbcPaymentAccount.setFeedBackOrgName(PbcPaymentAccountEnums.PBC_FEED_NAME.getValue()); //反馈机构名称
				pbcPaymentAccount.setOperatorName(UserUtils.getUser().getName());//我经办人姓名  当前操作人  
				
				CommonUntil commonUntil=new CommonUntil();
				pbcPaymentAccount.setTransSerialNumber(commonUntil.getReqHeader());//传输报文流水号 
				PbcCodeUntil PbcCodeUntil=new PbcCodeUntil();
				String reqHeader = PbcCodeUntil.getReqHeader("0405");
				pbcPaymentAccount.setApplicationId(reqHeader);// 业务申请编号//业务申请编号   0404-可疑名单上报-涉案账户
				
				//判断是否是已产生 业务申请编号 并且上传过文件
				if(StringUtils.isNotBlank(applicationId)){
					pbcPaymentAccount.setApplicationId(applicationId);	
					pbcPaymentAccount.setApplicationCode(applicationId);//用于查询和这个记录关联的商户
				}else{
					pbcPaymentAccount.setApplicationId(reqHeader);
					pbcPaymentAccount.setApplicationCode(reqHeader);//用于查询和这个记录关联的商户
				}
				
				
				model = pbcPaymentAccountService.findRiskProductQuotaPage(new Page<PbcPaymentAccount>(request, response), pbcPaymentAccount,model,pageNo);
				logger.info("可疑名单上报-涉案账户历史明细----->{查询结束}:");
			} catch (Exception e) {
				logger.info("可疑名单上报-涉案账户历史明细----->{查询出错}:"+e);
				throw new Exception(e);
			}
		}

		return "modules/pbc/pbcPaymentAccountThree";
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
		String checkFlg = request.getParameter("checkFlg");//页面是否是同意上传
		String feedBackId = request.getParameter("feedBackId");
		
		//用来判断是否是 是审核成功的操作
		String redioValue = request.getParameter("redioValue");   
		if(StringUtils.isNotBlank(redioValue)){
			pbcPaymentAccount.setRiskStatus(RiskAuditStatus.TQS_Y.getValue());//N 待审核 Y 审核通过
			int msg=pbcFeedBackService.updateEntityByLoad(pbcPaymentAccount);
			if(msg!=0){
				addMessage(redirectAttributes, "审核成功");
				logger.info("关联全支付账号----->{审核完成}:");
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
				logger.info("可疑名单上报-涉案账户历史明细----->{更新完成}:");
			}else{
				addMessage(redirectAttributes, "插入成功失败");
				logger.error("可疑名单上报-涉案账户历史明细----->{插入成功失败}:");
			}
			
			//跳回查看的总列表
			return "redirect:"+Global.getAdminPath()+"/pbc/verification";
			
		}else{
			if(StringUtils.isNotBlank(feedBackId)){ //查看页面过来的保存
				long feedBackIds=Long.parseLong(feedBackId);
				pbcPaymentAccount.setFeedBackId(feedBackIds);
				int msg=pbcFeedBackService.saveEntityByPaymentAccount(pbcPaymentAccount);
				if(msg!=0){
					addMessage(redirectAttributes, "更新成功");
					logger.info("可疑名单上报-涉案账户历史明细----->{更新完成}:");
				}else{
					addMessage(redirectAttributes, "插入成功失败");
					logger.error("可疑名单上报-涉案账户历史明细----->{插入成功失败}:");
				}
			}else{ //添加页面过来的保存
				pbcPaymentAccount.setFeedType(PbcEventType.EXCEPTION_EVENTS.getValue());//反馈类型   
				pbcPaymentAccount.setRiskStatus(RiskAuditStatus.TQS_N.getValue()); //风控审核状态(Y:审核成功 N:待审核)
				int msg=pbcFeedBackService.saveEntityByPaymentAccount(pbcPaymentAccount);
				if(msg!=0){
					addMessage(redirectAttributes, "插入成功");
					logger.info("可疑名单上报-涉案账户历史明细----->{入库完成}:");
				}else{
					addMessage(redirectAttributes, "插入成功失败");
					logger.error("可疑名单上报-涉案账户历史明细----->{插入成功失败}:");
				}
			}
			if(StringUtils.isNotBlank(feedBackId)){
				//查询过来的数据
				return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccountThree/view/three?feedBackId="+feedBackId;
			}else{
				//添加页面过来的数据
				return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccountThree/view/three"; 
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
		
		logger.info("关联全支付账号----->{上传文件入库}:");
		String applicationId = request.getParameter("applicationId");
		try {
			String msg=pbcPaymentAccountService.loadModel(file,applicationId);
			addMessage(redirectAttributes, msg);
			logger.info("关联全支付账号----->{上传文件入库成功}:");
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
				return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccountThree/view/three?applicationId="+applicationId; 	
			}else{
				//举报及可疑查询页面跳转
				return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccountThree/view/three?feedBackId="+feedBackId; 
			}
			 
		}else{
			//普通添加操作
			return "redirect:"+Global.getAdminPath()+"/pbc/pbcPaymentAccountThree/view/three?applicationId="+applicationId; 
		}
		
	}
	
}
