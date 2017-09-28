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
import com.heepay.common.util.StringUtils;
import com.heepay.enums.tpds.Status;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.service.BankInfoService;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.manage.modules.tpds.entity.TpdsBindInterface;
import com.heepay.manage.modules.tpds.service.TpdsBindInterfaceService;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年2月17日下午5:31:43
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
@RequestMapping(value = "${adminPath}/tpds/tpdsBindInterface")
public class TpdsBindInterfaceController extends BaseController{
	
    private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private TpdsBindInterfaceService tpdsBindInterfaceService;
	
	@Autowired
	private BankInfoService bankInfoService;
	
	@RequiresPermissions("tpds:tpdsBindInterface:view")
	@RequestMapping(value = { "list", "" })
	public String list(TpdsBindInterface tpdsBindInterface, HttpServletRequest request,HttpServletResponse response, Model model) {
		
		Page<TpdsBindInterface> page = tpdsBindInterfaceService.findPage(new Page<TpdsBindInterface>(request, response), tpdsBindInterface);
		logger.info("操作日志表------>{查询记录}"+ page.getList());
		for (TpdsBindInterface tpdsInterface : page.getList()) {
			//状态  ENABLE(启用) DISABL(禁用)
			if(StringUtils.isNotBlank(tpdsInterface.getStatus())){
				tpdsInterface.setStatus(Status.labelOf(tpdsInterface.getStatus()));
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("tpdsBindInterface", tpdsBindInterface);
	
		logger.info("操作日志表------>{}"+request);
		return "modules/tpds/tpdsBindInterfaceList";
	}
	
	@RequiresPermissions("tpds:tpdsBindInterface:edit")
	@RequestMapping(value = "/updateEntity")
	public String updateEntity(@RequestParam(value = "tpdsBindId", required = false) Integer tpdsBindId, 
			@RequestParam(value = "read", required = false) String read,
			Model model) {

		TpdsBindInterface tpdsBindInterface = tpdsBindInterfaceService.getEntityById(tpdsBindId);
		
		//状态  ENABLE(启用) DISABL(禁用)
		List<EnumBean> status = Lists.newArrayList();
		for (Status checkFlg : Status.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			status.add(ct);
		}
		model.addAttribute("status", status);
	    model.addAttribute("tpdsBindInterface", tpdsBindInterface);
	    return "modules/tpds/tpdsBindInterfaceUpdate";

	}
	
	
	@RequiresPermissions("tpds:tpdsBindInterface:edit")
	@RequestMapping(value = "/addEntity")
	public String addEntity(@RequestParam(value = "tpdsBindId", required = false) Integer tpdsBindId, 
			@RequestParam(value = "read", required = false) String read,
			Model model) {
		TpdsBindInterface tpdsBindInterface = new TpdsBindInterface();
		//状态  ENABLE(启用) DISABL(禁用)
		List<EnumBean> status = Lists.newArrayList();
		for (Status checkFlg : Status.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			status.add(ct);
		}
		
		model.addAttribute("status", status);
	    model.addAttribute("tpdsBindInterface", tpdsBindInterface);
	    return "modules/tpds/tpdsBindInterfaceAdd";
	}
	
	@RequiresPermissions("tpds:tpdsBindInterface:edit")
	@RequestMapping(value = "/save")
	public String save(TpdsBindInterface tpdsBindInterface, Model model, RedirectAttributes redirectAttributes,
					   @RequestParam(value="tpdsBindId",required = false) Integer tpdsBindId
                        ) throws Exception {
		
		if(tpdsBindId==null){
			//根据bankName查找银行信息
			BankInfo bankInfo = bankInfoService.getBankNameByBankNo(tpdsBindInterface.getBankName());
			tpdsBindInterface.setBankNo(bankInfo.getBankNo());
			tpdsBindInterface.setBankName(bankInfo.getBankName());
			tpdsBindInterface.setBankCode(bankInfo.getBankCode());
			tpdsBindInterface.setCreateDate(new Date());
			int num = tpdsBindInterfaceService.saveEntity(tpdsBindInterface);
			
            if(num>0){
            	addMessage(redirectAttributes, "添加成功");
            }else{
            	addMessage(redirectAttributes, "添加失败");
            }
        	
		}else{
			//根据bankName查找银行信息
			BankInfo bankInfo = bankInfoService.getBankNameByBankNo(tpdsBindInterface.getBankName());
			tpdsBindInterface.setBankNo(bankInfo.getBankNo());
			tpdsBindInterface.setBankName(bankInfo.getBankName());
			tpdsBindInterface.setBankCode(bankInfo.getBankCode());
			tpdsBindInterface.setUpdateTime(new Date());
			tpdsBindInterface.setUpdateUser(UserUtils.getUser().getName());
			int num = tpdsBindInterfaceService.updateEntity(tpdsBindInterface);
			if(num>0){
	        	addMessage(redirectAttributes, "更新成功");
	        }else{
	        	addMessage(redirectAttributes, "更新失败");
	        }
		} 
		return "redirect:"+Global.getAdminPath()+"/tpds/tpdsBindInterface";
	}
	
	
	//验证商户号
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/changeValue")
	public String chanageValue(@RequestParam("merchantNo") String merchantNo) {
		TpdsBindInterface tpdsBindInterface = tpdsBindInterfaceService.selectByMerchantNo(merchantNo);
		if(tpdsBindInterface == null){
			return 1+"";
		}else{
			return 0+"";
		}
		
	}
	
}
