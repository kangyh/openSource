package com.heepay.manage.modules.tpds.web;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.tpds.Status;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.manage.modules.tpds.entity.TpdsMerchantAccount;
import com.heepay.manage.modules.tpds.service.TpdsMerchantAccountService;
import com.heepay.manage.modules.tpds.web.rpc.ConfigServiceClient;

/**
 * *
 * 
 * 
 * 描 述：
 *
 * 创 建 者： wangjie 创建时间： 2017年2月17日下午5:31:43 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/tpds/merchantAccount")
public class TpdsMerchantAccountController extends BaseController{

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private TpdsMerchantAccountService tpdsMerchantAccountService;
	
	@Autowired
	private ConfigServiceClient configServiceClient;

	@RequiresPermissions("tpds:merchantAccount:view")
	@RequestMapping(value = { "list", "" })
	public String list(TpdsMerchantAccount tpdsMerchantAccount, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		Page<TpdsMerchantAccount> page = tpdsMerchantAccountService
				.findPage(new Page<TpdsMerchantAccount>(request, response), tpdsMerchantAccount);
		logger.info("操作日志表------>{查询记录}" + page.getList());
		
		for (TpdsMerchantAccount tpdsAccount : page.getList()) {
			//状态  ENABLE(启用) DISABL(禁用)
			if(StringUtils.isNotBlank(tpdsAccount.getStatus())){
				tpdsAccount.setStatus(Status.labelOf(tpdsAccount.getStatus()));
			}
			
			// 字段解密
			if (StringUtils.isNoneBlank(tpdsAccount.getCertNo())) {

				String certNo = Aes.decryptStr(tpdsAccount.getCertNo(), Constants.QuickPay.SYSTEM_KEY);
				tpdsAccount.setCertNo(StringUtil.getEncryptedIdcard(certNo));
			}
			if (StringUtils.isNoneBlank(tpdsAccount.getBankCard())) {
				String bankCard = Aes.decryptStr(tpdsAccount.getBankCard(),
						Constants.QuickPay.SYSTEM_KEY);
				tpdsAccount.setBankCard(StringUtil.getEncryptedCardNo(bankCard));
			}
			if (StringUtils.isNoneBlank(tpdsAccount.getBankAccount())) {
				String bankAccount = Aes.decryptStr(tpdsAccount.getBankAccount(), Constants.QuickPay.SYSTEM_KEY);
				tpdsAccount.setBankAccount(StringUtil.getEncryptedCardNo(bankAccount));
			}
			
		}
		model.addAttribute("page", page);
		model.addAttribute("tpdsMerchantAccount", tpdsMerchantAccount);

		logger.info("操作日志表------>{}" + request);
		return "modules/tpds/merchantAccountList";
	}

	@RequiresPermissions("tpds:merchantAccount:edit")
	@RequestMapping(value = "/updateEntity")
	public String updateEntity(@RequestParam(value = "tpdsMerchantId", required = false) Integer tpdsMerchantId, 
			@RequestParam(value = "read", required = false) String read,
			Model model) {

		TpdsMerchantAccount tpdsMerchantAccount = tpdsMerchantAccountService.getEntityById(tpdsMerchantId);
		//状态  ENABLE(启用) DISABL(禁用)
		List<EnumBean> status = Lists.newArrayList();
		for (Status checkFlg : Status.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			status.add(ct);
		}
		model.addAttribute("status", status);
	    model.addAttribute("tpdsMerchantAccount", tpdsMerchantAccount);
	    return "modules/tpds/merchantAccountUpdate";

		

	}
	
	@RequiresPermissions("tpds:merchantAccount:edit")
	@RequestMapping(value = "/addEntity")
	public String addEntity(@RequestParam(value = "tpdsMerchantId", required = false) Integer tpdsMerchantId, 
			@RequestParam(value = "read", required = false) String read,
			Model model) {
		TpdsMerchantAccount tpdsMerchantAccount = new TpdsMerchantAccount();
		//状态  ENABLE(启用) DISABL(禁用)
		List<EnumBean> status = Lists.newArrayList();
		for (Status checkFlg : Status.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			status.add(ct);
		}
		model.addAttribute("status", status);
	    model.addAttribute("tpdsMerchantAccount", tpdsMerchantAccount);
	    return "modules/tpds/merchantAccountAdd";
	}
	
	
	//保存商户台账信息
	@RequiresPermissions("tpds:merchantAccount:edit")
	@RequestMapping(value = "/save")
	public String save(TpdsMerchantAccount tpdsMerchantAccount, Model model, RedirectAttributes redirectAttributes,
					   @RequestParam(value="tpdsMerchantId",required = false) Integer tpdsMerchantId
                        ) throws Exception {
		
		JsonMapperUtil jsonUtil = new JsonMapperUtil();
		if(tpdsMerchantId==null){
			tpdsMerchantAccount.setCreateDate(new Date());
			tpdsMerchantAccount.setStatus(Status.ENABLE.getValue()); // 状态
			//int num = tpdsMerchantAccountService.saveEntity(tpdsMerchantAccount);
			//写入缓存
		   String msg = configServiceClient.addMerchantAccount(jsonUtil.toJson(tpdsMerchantAccount));
            if("1".equals(msg)){
            	addMessage(redirectAttributes, "添加成功");
            }else{
            	addMessage(redirectAttributes, "添加失败");
            }
        	
		}else{
			tpdsMerchantAccount.setUpdateTime(new Date());
			tpdsMerchantAccount.setUpdateUser(UserUtils.getUser().getName());
			//int num = tpdsMerchantAccountService.updateEntity(tpdsMerchantAccount);
			//写入缓存
		    String msg = configServiceClient.editMerchantAccount(jsonUtil.toJson(tpdsMerchantAccount));
			if("1".equals(msg)){
	        	addMessage(redirectAttributes, "更新成功");
	        }else{
	        	addMessage(redirectAttributes, "更新失败");
	        }
			
		} 
		return "redirect:"+Global.getAdminPath()+"/tpds/merchantAccount";
	}
	
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/changeValue")
	public String chanageValue(@RequestParam("merchantNo") String merchantNo) {
		TpdsMerchantAccount tpdsMerchantAccount = tpdsMerchantAccountService.selectByMerchantNo(merchantNo);
		if(tpdsMerchantAccount == null){
			return 1+"";
		}else{
			return 0+"";
		}
		
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/changeValue1")
	public String chanageValue1(@RequestParam("systemNo") String systemNo) {
		
		TpdsMerchantAccount tpdsMerchantAccount = tpdsMerchantAccountService.selectBySystemNo(systemNo);
		if(tpdsMerchantAccount == null){
			return 1+"";
		}else{
			return 0+"";
		}
		
	}
	
}
