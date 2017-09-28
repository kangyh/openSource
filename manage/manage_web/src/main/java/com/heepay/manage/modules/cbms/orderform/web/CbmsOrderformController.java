/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.orderform.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.enums.CbmsOrderFormCStatus;
import com.heepay.manage.common.enums.orderStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.cbms.entity.CbmsCustomCode;
import com.heepay.manage.modules.cbms.entity.CbmsCustomsetting;
import com.heepay.manage.modules.cbms.entity.CbmsOrderform;
import com.heepay.manage.modules.cbms.service.CbmsCustomCodeService;
import com.heepay.manage.modules.cbms.service.CbmsCustomsettingService;
import com.heepay.manage.modules.cbms.service.CbmsOrderformService;
import com.heepay.manage.modules.cbms.utils.OrderFormUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * 描    述：导入订单表（货物类）详情查看和新增Controller
 *
 * 创 建 者： guozx
 * 创建时间： 2017年1月2日 下午4:48:24
 * 创建描述：
 *
 * 修 改 者：牛俊鹏
 * 修改时间：2017年8月23日
 * 修改描述：给审核明细页面及订单明细页面添加订单列表字段
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/cbms/cbmsOrderform")
public class CbmsOrderformController extends BaseController {

	@Autowired
	private CbmsOrderformService cbmsOrderformService;
	@Autowired
	private CbmsCustomsettingService cbmsCustomsettingService;

    @Autowired
    private CbmsCustomCodeService cbmsCustomCodeService;

	/**
	 * @discription 根据id申报明细查看获取信息
	 * @author guozx
	 * @created 2017年1月2日 下午4:48:34
	 * @param id
	 * @return
	 */
	@ModelAttribute
	public CbmsOrderform get(@RequestParam(required=false) String id) {
		CbmsOrderform entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cbmsOrderformService.get(id);
		}
		if (entity == null){
			entity = new CbmsOrderform();
		}
		return entity;
	}
//
	/**
	 * @discription 获取申报明细查看列表
	 * @author guozx
	 * @created 2017年1月2日 下午4:49:26
	 * @param cbmsOrderform
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("cbms:cbmsOrderform:view")
	@RequestMapping(value = "detail")
	public String listDetail(CbmsOrderform cbmsOrderform, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CbmsOrderform> page = cbmsOrderformService.findPage(new Page<CbmsOrderform>(request, response), cbmsOrderform);
		List <CbmsCustomsetting> customsettinglist = cbmsCustomsettingService.findcustomsettinglist();
		List<CbmsOrderform> list = page.getList();
		//申报明细订单添加对应的申报批次号
		for (CbmsOrderform orderform : list) {
            String merchantNo = orderform.getMerchantNo();
            if(merchantNo != null) {
				for(CbmsCustomsetting cbmsCustomsetting:customsettinglist){
					if(orderform.getCustomCode().equals(cbmsCustomsetting.getCustomNo())){
						orderform.setCustomCode(cbmsCustomsetting.getChinaName());
						break;
					}
				}
            }
            //获取通关申报订单实体类的状态（custom_status）
			//先判断
            if(orderform.getCustomStatus() != null) {
				if("6".equals(orderform.getCustomStatus())) {
					orderform.setCausesFailure(orderform.getAuditFailReason());
				}
					CbmsOrderFormCStatus status = CbmsOrderFormCStatus.getBean(orderform.getCustomStatus());
					orderform.setCustomStatus(status==null?"":status.getContent());
			}
			//转换申报类型为中文格式
			if("2".equals(orderform.getDeclareType())){
				orderform.setDeclareType("API接口");
			}else{
				orderform.setDeclareType("文件上传");
			}
			orderform.setTransStatus(OrderFormUtils.changeTransStatus(orderform.getTransStatus()));
			orderform.setModeCode(OrderFormUtils.changeModeCode(orderform.getModeCode()));
			orderform.setCurrencyId(OrderFormUtils.changeCurrencyType(orderform.getCurrencyId()));
			orderform.setPayErcertificateType(OrderFormUtils.changePayErcertificateType(orderform.getPayErcertificateType()));
			orderform.setPayPurpose(OrderFormUtils.changePayPurpose(orderform.getPayPurpose()));
			orderform.setPayStatus(OrderFormUtils.changePayStatus(orderform.getPayStatus()));
			orderform.setTransCode(OrderFormUtils.changeTransCode(orderform.getTransCode()));
		}
		page.setList(list);
		logger.info("获取申报明细查看列表-----申报的总条数" + page.getCount());
		model.addAttribute("page", page);
		model.addAttribute("customsettinglist",customsettinglist);
		return "modules/cbms/cbmsOrderform/cbmsOrderformDetail";
	}

	/**
	 * @discription 获取同一申报批次号的申报订单列表
	 * @author guozx
	 * @created 2017年1月2日 下午4:49:26
	 * @param cbmsOrderform
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("cbms:cbmsOrderform:view")
	@RequestMapping(value = {"list", ""})
	public String list(CbmsOrderform cbmsOrderform, HttpServletRequest request, HttpServletResponse response, Model model) {
		//获取申报批次号
	    String declarationBatchHumber = request.getParameter("declarationBatchHumber");
	    String customCode = request.getParameter("customCode");
		cbmsOrderform.setSentCustomsNumber(declarationBatchHumber!=null?declarationBatchHumber:"");
		cbmsOrderform.setCustomCode(customCode!=null?customCode:"");
        Page<CbmsOrderform> page = cbmsOrderformService.findPage(new Page<CbmsOrderform>(request, response), cbmsOrderform);
		//同申报批次号实体类中添加申报状态、
        List<CbmsOrderform> cbmsOrderforms = page.getList();
        for (CbmsOrderform cbmsOrderformtemp : cbmsOrderforms) {
			//同申报批次号实体类中封装申报状态
            String customStatus = cbmsOrderformtemp.getCustomStatus();
            String content = CbmsOrderFormCStatus.getBean(customStatus).getContent();
            cbmsOrderformtemp.setCustomStatus(content);
			//同申报批次号实体类中封装标识
            cbmsOrderformtemp.setIdentification("0".equals(cbmsOrderformtemp.getIdentification()) ? "新增申报" : "修改申报");
			cbmsOrderformtemp.setTransStatus(OrderFormUtils.changeTransStatus(cbmsOrderformtemp.getTransStatus()));
        }
        page.setList(cbmsOrderforms);
		logger.info("获取同一申报批次号的申报订单列表-----总条数" + page.getCount());
        List<CbmsCustomCode> customCodesList = cbmsCustomCodeService.findAllList();
        model.addAttribute("customCodesList", customCodesList);
        model.addAttribute("page", page);
        return "modules/cbms/cbmsOrderform/cbmsOrderformList";
	}
	/**
	 * @return
	 * @discription 列表展示 参数是对象
	 * @author 牛俊鹏
	 * @created 2017年1月
	 */
	@RequiresPermissions("cbms:cbmsOrderform:view")
	@RequestMapping(value = "orderformlist")
	public String orderformlist(CbmsOrderform cbmsOrderform,String importBatchNumber,String status ,HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		String message =(String)model.asMap().get("message");
		if(message != null){
			cbmsOrderform.setImportBatchNumber(message.split("&")[0]);
			model.asMap().put("message",message.split("&")[1]);
		}else{
			cbmsOrderform.setImportBatchNumber(importBatchNumber);
		}
		Page<CbmsOrderform> page = cbmsOrderformService.findPage(new Page<CbmsOrderform>(request, response), cbmsOrderform);
		List<CbmsOrderform> newlist = new ArrayList<>();
		for(CbmsOrderform currency : page.getList() ){
			if (null != currency) {
						currency.setIdentification("1".equals(currency.getIdentification())? "修改申报":"新增申报");
					currency.setOrderStatus(orderStatus.labelOf(currency.getOrderStatus()));
					currency.setCustomStatus(CbmsOrderFormCStatus.labelOf(currency.getCustomStatus()));
				currency.setTransStatus(OrderFormUtils.changeTransStatus(currency.getTransStatus()));
				currency.setModeCode(OrderFormUtils.changeModeCode(currency.getModeCode()));
				currency.setCurrencyId(OrderFormUtils.changeCurrencyType(currency.getCurrencyId()));
				currency.setPayErcertificateType(OrderFormUtils.changePayErcertificateType(currency.getPayErcertificateType()));
				currency.setPayPurpose(OrderFormUtils.changePayPurpose(currency.getPayPurpose()));
				currency.setPayStatus(OrderFormUtils.changePayStatus(currency.getPayStatus()));
				currency.setTransCode(OrderFormUtils.changeTransCode(currency.getTransCode()));
				//转换申报类型为中文格式
				if("2".equals(currency.getDeclareType())){
					currency.setDeclareType("API接口");
				}else{
					currency.setDeclareType("文件上传");
				}
			}
			newlist.add(currency);
		}
		page.setList(newlist);
		model.addAttribute("page", page);
		if(status!=null){
			model.addAttribute("status", status);
		}
		return "modules/cbms/orderexamine/cbmsOrderformexamineList";
	}

	/**
	 * @return
	 * @discription 同一申报批次号的添加和修改
	 * @param cbmsOrderform
	 * @param model
	 * @return
	 */
	@RequiresPermissions("cbms:cbmsOrderform:view")
	@RequestMapping(value = "form")
	public String form(CbmsOrderform cbmsOrderform, Model model) {
		model.addAttribute("cbmsOrderform", cbmsOrderform);
		return "modules/cbms/cbmsOrderform/cbmsOrderformForm";
	}

	/**
	 * @return
	 * @discription 同一申报批次号的保存
	 * @param cbmsOrderform
	 * @param model
	 * @return
	 */
	@RequiresPermissions("cbms:cbmsOrderform:edit")
	@RequestMapping(value = "save")
	public String save(CbmsOrderform cbmsOrderform, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cbmsOrderform)){
			return form(cbmsOrderform, model);
		}
		cbmsOrderformService.save(cbmsOrderform);
		addMessage(redirectAttributes, "保存同申报批次号详情成功");
		return "redirect:"+Global.getAdminPath()+"/cbms/cbmsOrderform/?repage";
	}

	/**
	 * @return
	 * @discription 同一申报批次号的删除
	 * @param cbmsOrderform
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("cbms:cbmsOrderform:edit")
	@RequestMapping(value = "delete")
	public String delete(CbmsOrderform cbmsOrderform, RedirectAttributes redirectAttributes) {
		cbmsOrderformService.delete(cbmsOrderform);
		addMessage(redirectAttributes, "删除同申报批次号详情成功");
		return "redirect:"+Global.getAdminPath()+"/cbms/cbmsOrderform/?repage";
	}

}