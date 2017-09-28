/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.web;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.AllowSystemType;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.rpc.client.ElectronicSignatureClient;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.service.OnlineContractInfoCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.OnlineContractInfo;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferRecord;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.rpc.service.OnlineContractInfoThrift;
import com.heepay.rpc.payment.model.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;


/**
 *
 * 描    述：线上签约Controller
 *
 * 创 建 者： @author ly
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
@RequestMapping(value = "${adminPath}/merchant/onlineContractInfoRc")
public class OnlineContractInfoRcController extends BaseController {

	@Autowired
	private OnlineContractInfoCService onlineContractInfoService;

	@Autowired
	private ElectronicSignatureClient electronicSignatureClient;

	@Autowired
	private MerchantCService merchantCService;

	@ModelAttribute
	public OnlineContractInfo get(@RequestParam(required=false) String id) {
		OnlineContractInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = onlineContractInfoService.get(id);
		}
		if (entity == null){
			entity = new OnlineContractInfo();
		}
		return entity;
	}

	@RequiresPermissions("merchant:onlineContractInfoRc:view")
	@RequestMapping(value = {"list", ""})
	public String list(OnlineContractInfo onlineContractInfo, HttpServletRequest request, HttpServletResponse response, Model model) {

		//使用cookie保存查询条件
		onlineContractInfo = (OnlineContractInfo) SaveConditions.result(onlineContractInfo, "onlineContractInfoa", request, response);

		onlineContractInfo.setLegalAuditStatus(RouteStatus.AUDIT_SUCCESS.getValue());
		Page<OnlineContractInfo> page = onlineContractInfoService.findPage(new Page<OnlineContractInfo>(request, response), onlineContractInfo);
		page.setList(EnumView.onlineContractInfo(page.getList()));

		model.addAttribute("page", page);
		model.addAttribute("onlineContractInfo", onlineContractInfo);
		return "modules/merchant/onlineContractInfoRcList";
	}

	@RequiresPermissions("merchant:onlineContractInfoRc:view")
	@RequestMapping(value = "detail")
	public String detail(OnlineContractInfo onlineContractInfo, Model model) {
		List<OnlineContractInfo> list = onlineContractInfoService.findProductList(onlineContractInfo);
		model.addAttribute("list",list);
		onlineContractInfo = onlineContractInfoService.queryProductsBybatchNoAndUserId(
				onlineContractInfo.getBatchNo(),onlineContractInfo.getUserId());
		model.addAttribute("onlineContractInfo", onlineContractInfo);
		return "modules/merchant/onlineContractInfoRcDetail";
	}

	@RequiresPermissions("merchant:onlineContractInfoRc:edit")
	@RequestMapping(value = "save")
	public String save(OnlineContractInfo onlineContractInfo, Model model, RedirectAttributes redirectAttributes) throws TException {
		if (!beanValidator(model, onlineContractInfo)){
			return audit(onlineContractInfo, model);
		}
		if(RouteStatus.AUDIT_SUCCESS.getValue().equals(onlineContractInfo.getRcAuditStatus())){
			//调用签章服务
			if(StringUtils.isNotBlank(onlineContractInfo.getMiddleFilePath())){
				Merchant merchant = merchantCService.get(onlineContractInfo.getUserId());
				String filePath = onlineContractInfo.getMiddleFilePath();
				ElectronicsSealResponse electronicsSealResponse = seal(filePath,merchant);
				if(null != electronicsSealResponse){
					onlineContractInfo.setFinalFilePath(electronicsSealResponse.getMsg());
				}else{
					model.addAttribute("msg","签章失败");
					return audit(onlineContractInfo, model);
				}
			}
			else{
				model.addAttribute("msg","合同为空");
				return audit(onlineContractInfo, model);
			}
		}
		onlineContractInfoService.rcAudit(onlineContractInfo);
		addMessage(redirectAttributes, "保存线上签约成功");
		return "redirect:"+Global.getAdminPath()+"/merchant/onlineContractInfoRc?cache=1&repage";
	}

	@RequiresPermissions("merchant:onlineContractInfoRc:view")
	@RequestMapping(value = "audit")
	public String audit(OnlineContractInfo onlineContractInfo, Model model) {
		List<OnlineContractInfo> list = onlineContractInfoService.findProductList(onlineContractInfo);
		model.addAttribute("list",list);
		onlineContractInfo = onlineContractInfoService.queryProductsBybatchNoAndUserId(
				onlineContractInfo.getBatchNo(),onlineContractInfo.getUserId());
		model.addAttribute("onlineContractInfo", onlineContractInfo);
		return "modules/merchant/onlineContractInfoRcAuditForm";
	}

	private ElectronicsSealResponse seal(String url,Merchant merchant) throws TException {
		ElectronicsSealPosModel electronicsSealPosModel = new ElectronicsSealPosModel();
		electronicsSealPosModel.setPosx("400");//x坐标
		electronicsSealPosModel.setPosy("380");//y坐标  windows下是550
		electronicsSealPosModel.setPosPage("7");//页码
		electronicsSealPosModel.setType("0");//坐标签章    还有关键字签章
		//签章信息
		SealStyleReqModel sealStyleReqModel = new SealStyleReqModel();
		sealStyleReqModel.setColor("RED");
		sealStyleReqModel.setHText("");
		sealStyleReqModel.setQText("");
		sealStyleReqModel.setSealStyle("STAR");
		//合同信息
		SealContractReqModel sealContractReqModel = new SealContractReqModel();
		sealContractReqModel.setContractId("HYYT-YTWG2017-001");
		sealContractReqModel.setContractName("汇付宝支付服务合同");
		sealContractReqModel.setContractUrl(url);
		//商户信息
		SealMerchantReqModel sealMerchantReqModel = new SealMerchantReqModel();
//		sealMerchantReqModel.setMerchantId(merchant.getUserId());     //商户id
		sealMerchantReqModel.setCompanyName("汇元银通（北京）在线支付技术有限公司");               //公司名称
//		sealMerchantReqModel.setContactor(merchant.getContactor());                   //联系人
//		sealMerchantReqModel.setContactorPhone(merchant.getContactorPhone());         //联系人手机号
//		sealMerchantReqModel.setEmail(merchant.getEmail());                           //邮箱
//		sealMerchantReqModel.setCompanyAdress(merchant.getBusinessAddress());         //公司地址
//		sealMerchantReqModel.setUserType("2");                                              //注册类型
//		sealMerchantReqModel.setOrganType("0");                                             //单位类型
//		sealMerchantReqModel.setRegType("");                                                //企业注册类型
//		sealMerchantReqModel.setRegCode("");                                                //工商注册号
//		sealMerchantReqModel.setLegalName(merchant.getLegalRepresentative());         //法人姓名
//		sealMerchantReqModel.setLegalIdNo(merchant.getLegalIdcard());                 //法人身份证号
//		sealMerchantReqModel.setLegalArea("");                                              //法人归属地
//		sealMerchantReqModel.setOrganCode(merchant.getOrganizationCode());            //组织机构代码
//		sealMerchantReqModel.setAgentName("");                                              //代理人姓名
//		sealMerchantReqModel.setAgentIdNo("");                                              //代理人身份证号
//		sealMerchantReqModel.setScope(merchant.getBusinessScope());                   //经营范围
//		sealMerchantReqModel.setBusinessLicenseNo(merchant.getBusinessLicenseNo());   //社会信用代码
//		sealMerchantReqModel.setCertificateType(merchant.getCertificateType());       //证件类型
		return electronicSignatureClient.executeSeal(sealMerchantReqModel, sealContractReqModel, electronicsSealPosModel,
				sealStyleReqModel, AllowSystemType.MANAGE_WEB.getValue(), "0", true);
	}


}