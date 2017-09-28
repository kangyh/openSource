/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.route.web;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.MerchantStatus;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.service.MerchantUserCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.route.entity.MerchantPayChannel;
import com.heepay.manage.modules.route.entity.PayChannel;
import com.heepay.manage.modules.route.service.MerchantPayChannelService;
import com.heepay.manage.modules.route.service.PayChannelService;
import com.heepay.manage.modules.sys.utils.UserUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * 描    述：商户通道配置Controller
 *
 * 创 建 者： @author 牛文
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
@RequestMapping(value = "${adminPath}/route/merchantPayChannel")
public class MerchantPayChannelController extends BaseController {

	@Autowired
	private MerchantPayChannelService merchantPayChannelService;

	@Autowired
	private MerchantUserCService merchantUserCService;

	@Autowired
	private MerchantCService merchantCService;

	@Autowired
	private PayChannelService payChannelService;
	
	/**
	 * @discription 根据id获取商户信息
	 * @author ly
	 * @created 2016年9月2日 下午4:20:03
	 * @param id
	 * @return
	 */
	@ModelAttribute
	public Merchant get(@RequestParam(required = false) String id) {
		Merchant entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = merchantCService.get(id);
		}
		if (entity == null) {
			entity = new Merchant();
		}
		return entity;
	}

	/**
	 * 获取商户信息列表
	 * @param merchant
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("route:merchantPayChannel:view")
	@RequestMapping(value = { "list", "" })
	public String list(Merchant merchant, HttpServletRequest request, HttpServletResponse response, Model model) {

		//使用cookie保存查询条件
		merchant = (Merchant) SaveConditions.result(merchant, "merchants", request, response);

		merchant.setStatus(RouteStatus.AUDIT_SUCCESS.getValue());
		Page<Merchant> page = new Page<>(request, response);
		page.setOrderBy("a.create_time desc");
		page = merchantCService.findPage(page, merchant);
		List<Merchant> list = Lists.newArrayList();
		for (Merchant merchantFor : page.getList()) {
			MerchantUser merchantUser = merchantUserCService.get(merchantFor.getUserId().toString());
			if (null != merchantUser) {
				merchantFor.setLoginName(merchantUser.getLoginName());
				if (StringUtils.isNotBlank(merchantUser.getStatus())) {
					merchantFor.setUserStatus(MerchantStatus.labelOf(merchantUser.getStatus()));
				}
			}
			if (StringUtils.isNotBlank(merchantFor.getInchargerId())) {
				merchantFor.setInchargerId(UserUtils.get(merchantFor.getInchargerId()).getName());
			}
			list.add(merchantFor);
		}
		page.setList(list);
		model.addAttribute("page", page);
		model.addAttribute("merchant", merchant);
		return "modules/route/merchantPayChannelList";
	}

	/**
	 * @discription 获取商户通道详情页面
	 * @author N.W
	 * @created 2017年4月25日 下午3:51:05
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("route:merchantPayChannel:view")
	@RequestMapping(value = "details")
	public String details(Merchant merchant,MerchantPayChannel merchantPayChannel,HttpServletRequest request, HttpServletResponse response, Model model) {
		//根据userId查询商户通道明细
		merchantPayChannel.setMerchantId(String.valueOf(merchant.getUserId()));
		Page<MerchantPayChannel>  page  = new Page<>(request, response);
		//根据创建日期倒序排列
		page.setOrderBy("createBeginTime desc");
		page = merchantPayChannelService.findPage(page, merchantPayChannel);
		model.addAttribute("page", page);
        Merchant merchant1 = merchantCService.selectMerchant(merchant);
		Merchant merchantReturn = EnumView.merchantShow(merchant1);
		model.addAttribute("merchant", merchantReturn);
		return "modules/route/merchantPayChannelDetails";
	}

	/**
	 * @discription  获取为产品添加支付通道的页面
	 * @author N.W
	 * @created 2017年4月25日 下午3:49:58
	 * @param merchantId
	 * @param companyName
	 * @param payChannel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("route:merchantPayChannel:view")
	@RequestMapping(value = "add")
	public String add(String merchantId, String companyName,String productCode,String productName, PayChannel payChannel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String,Object> map = new HashMap<>();
		map.put("merchantId", merchantId);
		//支付通道查询-add方法
		map.put("bankNo", payChannel.getBankNo());
		map.put("channelPartnerCode", payChannel.getChannelPartnerCode());
		map.put("channelTypeCode", payChannel.getChannelTypeCode());
		map.put("cardTypeCode", payChannel.getCardTypeCode());
		map.put("productCode", productCode);
		Page<PayChannel>  page  = payChannelService.findMerchantChannelPage(new Page<>(request, response),map);
		model.addAttribute("bankNo", payChannel.getBankNo());
		model.addAttribute("channelPartnerCode", payChannel.getChannelPartnerCode());
		model.addAttribute("channelTypeCode", payChannel.getChannelTypeCode());
		model.addAttribute("cardTypeCode", payChannel.getCardTypeCode());
		model.addAttribute("companyName", companyName);
		model.addAttribute("merchantId", merchantId);
		model.addAttribute("productCode", productCode);
		model.addAttribute("productName", productName);
		model.addAttribute("page", page);
		return "modules/route/merchantPayChannelAdd";
	}

	/**
	 * @discription 商户添加通道的方法
	 * @author N.W
	 * @created 2017年4月25日 下午5:51:45
	 * @param id
	 * @param merchantId
	 * @param companyName
	 * @param pageNo
	 * @param payChannel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("route:merchantPayChannel:view")
	@RequestMapping(value = "addChannel")
	public void addChannel(String id,String productCode,String productName,String merchantId ,String companyName,String pageNo,PayChannel payChannel,HttpServletRequest request,HttpServletResponse response,Model model){
		//生成商户通道明细
		MerchantPayChannel merchantPayChannel = merchantPayChannelService.combineDetail(id,merchantId);
		merchantPayChannel.setProductCode(productCode);
		merchantPayChannel.setProductName(productName);
		merchantPayChannelService.save(merchantPayChannel,false);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("merchantId", merchantId);
		map.put("productCode", productCode);
		map.put("bankNo", payChannel.getBankNo());
		map.put("channelPartnerCode", payChannel.getChannelPartnerCode());
		map.put("channelTypeCode", payChannel.getChannelTypeCode());
		map.put("cardTypeCode", payChannel.getCardTypeCode());
		Page<PayChannel>  page  = payChannelService.findMerchantChannelPage(new Page<>(request, response),map);
		model.addAttribute("page", page);
		model.addAttribute("bankNo", payChannel.getBankNo());
		model.addAttribute("channelPartnerCode", payChannel.getChannelPartnerCode());
		model.addAttribute("channelTypeCode", payChannel.getChannelTypeCode());
		model.addAttribute("cardTypeCode", payChannel.getCardTypeCode());
		model.addAttribute("merchantId", merchantId);
		model.addAttribute("companyName", companyName);
		model.addAttribute("productCode", productCode);
		model.addAttribute("productName", productName);
		model.addAttribute("pageNo", pageNo);
	}

	/**
	 * 为商户批量添加支付通道（当前页面批量操作）
	 * @param checkedstr
	 * @param merchantId
	 * @param companyName
	 * @param pageNo
	 * @param payChannel
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequiresPermissions("route:merchantPayChannel:view")
	@RequestMapping(value = "addBatchChannel")
	public void addBatchChannel(String checkedstr,String productCode,String productName,String merchantId ,String companyName,String pageNo,PayChannel payChannel,HttpServletRequest request,HttpServletResponse response,Model model){
		//批量生成商户通道明细
		if(StringUtils.isNotBlank(checkedstr)){
			merchantPayChannelService.saveBatchChannel(checkedstr,merchantId,productCode,productName);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("merchantId", merchantId);
		map.put("bankNo", payChannel.getBankNo());
		map.put("channelPartnerCode", payChannel.getChannelPartnerCode());
		map.put("channelTypeCode", payChannel.getChannelTypeCode());
		map.put("cardTypeCode", payChannel.getCardTypeCode());
		map.put("productCode", productCode);
		Page<PayChannel>  page  = payChannelService.findMerchantChannelPage(new Page<>(request, response),map);
		model.addAttribute("page", page);
		model.addAttribute("bankNo", payChannel.getBankNo());
		model.addAttribute("channelPartnerCode", payChannel.getChannelPartnerCode());
		model.addAttribute("channelTypeCode", payChannel.getChannelTypeCode());
		model.addAttribute("cardTypeCode", payChannel.getCardTypeCode());
		model.addAttribute("MerchantId", merchantId);
		model.addAttribute("companyName", companyName);
		model.addAttribute("productCode", productCode);
		model.addAttribute("productName", productName);
		model.addAttribute("pageNo", pageNo);
	}


	@RequiresPermissions("route:merchantPayChannel:view")
	@RequestMapping(value = "form")
	public String form(MerchantPayChannel merchantPayChannel, Model model) {
		model.addAttribute("merchantPayChannel", merchantPayChannel);
		return "modules/route/merchantPayChannelForm";
	}

	@RequiresPermissions("route:merchantPayChannel:edit")
	@RequestMapping(value = "save")
	public String save(MerchantPayChannel merchantPayChannel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, merchantPayChannel)){
			return form(merchantPayChannel, model);
		}
		merchantPayChannelService.save(merchantPayChannel);
		addMessage(redirectAttributes, "保存商户通道配置成功");
		return "redirect:"+Global.getAdminPath()+"/route/merchantPayChannel/?repage";
	}
	
	@RequiresPermissions("route:merchantPayChannel:edit")
	@RequestMapping(value = "delete")
	public String delete(MerchantPayChannel merchantPayChannel,String merchantId, RedirectAttributes redirectAttributes) {
		merchantPayChannelService.delete(merchantPayChannel);
		addMessage(redirectAttributes, "删除商户通道配置成功");
		return "redirect:"+Global.getAdminPath()+"/route/merchantPayChannel/details?id="+ merchantId;
	}


}