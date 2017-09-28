/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.route.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.cache.IntegrationBankIdSync;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.route.entity.ChannelBankid;
import com.heepay.manage.modules.route.entity.IntegrationChannel;
import com.heepay.manage.modules.route.entity.PayChannel;
import com.heepay.manage.modules.route.service.ChannelBankidService;
import com.heepay.manage.modules.route.service.IntegrationChannelService;
import com.heepay.manage.modules.route.service.PayChannelService;
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
 * 描    述：java通道与.net的bankId关联
 *
 * 创 建 者： M.Z
 * 创建时间： 2017/4/19 16:54 
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
@RequestMapping(value = "${adminPath}/route/bankIdMapping")
public class BankIdMappingController extends BaseController {

	@Autowired
	private PayChannelService payChannelService;

	@Autowired
	private IntegrationChannelService integrationChannelService;

	@Autowired
	private ChannelBankidService channelBankidService;


	/**
	 * @discription 获取通道信息
	 * @param id
	 * @return
	 */
	@ModelAttribute
	public PayChannel get(@RequestParam(required=false) String id) {
		PayChannel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = payChannelService.get(id);
		}
		if (entity == null){
			entity = new PayChannel();
		}
		return entity;
	}


	/**
	 * @discription 获取产品信息列表
	 * @param payChannel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("route:bankIdMapping:view")
	@RequestMapping(value = {"list", ""})
	public String list(PayChannel payChannel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PayChannel>  page  = new Page<>(request, response);
		//根据创建日期倒序排列
		page.setOrderBy("createDate desc");
		page = payChannelService.findPage(page, payChannel);
		//判断是否为空
		if(null!=page.getList() && !page.getList().isEmpty()) {
			//支付通道的状态由值取内容
			page.setList(EnumView.PayChannel(page.getList()));
		}
		model.addAttribute("page", page);
		return "modules/route/bankIdMappingList";
	}

	/**
	 * @discription  获取为产品添加支付通道的页面
	 * @param channelCode
	 * @param channelId
	 * @param channelName
	 * @param integrationChannel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("route:bankIdMapping:view")
	@RequestMapping(value = "add")
	public String add(String channelCode, String channelId, String channelName, IntegrationChannel integrationChannel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String,Object> map = new HashMap<>();
		map.put("channelCode", channelCode);
		//支付通道查询-add方法
		map.put("channelKey", integrationChannel.getChannelKey());
		map.put("providerCode", integrationChannel.getProviderCode());
		map.put("payType", integrationChannel.getPayType());
		map.put("sceneKey", integrationChannel.getSceneKey());
		Page<IntegrationChannel>  page  = integrationChannelService.findIntegraChannelPage(new Page<>(request, response),map);
		model.addAttribute("channelKey", integrationChannel.getChannelKey());
		model.addAttribute("providerCode", integrationChannel.getProviderCode());
		model.addAttribute("payType", integrationChannel.getPayType());
		model.addAttribute("sceneKey", integrationChannel.getSceneKey());
		model.addAttribute("channelCode", channelCode);
		model.addAttribute("channelId", channelId);
		model.addAttribute("channelName", channelName);
		model.addAttribute("page", page);
		return "modules/route/bankIdMappingAdd";
	}


	/**
	 * @discription 银通通道添加.net聚合通道的方法
	 * @param id
	 * @param channelId
	 * @param channelCode
	 * @param channelName
	 * @param pageNo
	 * @param integrationChannel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("route:bankIdMapping:view")
	@RequestMapping(value = "addBankId")
	public void addBankId(String id,String channelId ,String channelCode,String channelName,String pageNo,IntegrationChannel integrationChannel,HttpServletRequest request,HttpServletResponse response,Model model){
		//生成产品明细
		ChannelBankid channelBankid = channelBankidService.combineDetail(id,channelId);
		channelBankidService.save(channelBankid,false);
		Map<String,Object> map = new HashMap<>();
		map.put("channelCode",channelCode);
		map.put("channelKey", integrationChannel.getChannelKey());
		map.put("providerCode", integrationChannel.getProviderCode());
		map.put("payType", integrationChannel.getPayType());
		map.put("sceneKey", integrationChannel.getSceneKey());
		Page<IntegrationChannel>  page  = integrationChannelService.findIntegraChannelPage(new Page<>(request, response),map);
		model.addAttribute("page", page);
		model.addAttribute("channelKey", integrationChannel.getChannelKey());
		model.addAttribute("providerCode", integrationChannel.getProviderCode());
		model.addAttribute("payType", integrationChannel.getPayType());
		model.addAttribute("sceneKey", integrationChannel.getSceneKey());
		model.addAttribute("channelCode", channelCode);
		model.addAttribute("channelId", channelId);
		model.addAttribute("channelName", channelName);
		model.addAttribute("pageNo", pageNo);
		//return "modules/route/productChannelAdd";
	}

	/**
	 * 为银通通道批量添加.net聚合通道（当前页面批量操作）
	 * @param checkedstr
	 * @param channelId
	 * @param channelCode
	 * @param channelName
	 * @param pageNo
	 * @param integrationChannel
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequiresPermissions("route:bankIdMapping:view")
	@RequestMapping(value = "addBatchBankId")
	public void addBatchBankId(String checkedstr,String channelId ,String channelCode,String channelName,String pageNo,IntegrationChannel integrationChannel,HttpServletRequest request,HttpServletResponse response,Model model){
		//批量生成通道明细
		if(StringUtils.isNotBlank(checkedstr)){
			channelBankidService.saveBatchBankId(checkedstr,channelId);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("channelCode",channelCode);
		map.put("channelKey", integrationChannel.getChannelKey());
		map.put("providerCode", integrationChannel.getProviderCode());
		map.put("payType", integrationChannel.getPayType());
		map.put("sceneKey", integrationChannel.getSceneKey());
		Page<IntegrationChannel>  page  = integrationChannelService.findIntegraChannelPage(new Page<>(request, response),map);
		model.addAttribute("page", page);
		model.addAttribute("channelKey", integrationChannel.getChannelKey());
		model.addAttribute("providerCode", integrationChannel.getProviderCode());
		model.addAttribute("payType", integrationChannel.getPayType());
		model.addAttribute("sceneKey", integrationChannel.getSceneKey());
		model.addAttribute("channelCode", channelCode);
		model.addAttribute("channelId", channelId);
		model.addAttribute("channelName", channelName);
		model.addAttribute("pageNo", pageNo);
	}


	/**
	 * @discription 获取产品通道详情页面
	 * @param channelBankid
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("route:bankIdMapping:view")
	@RequestMapping(value = "details")
	public String details(PayChannel payChannel, ChannelBankid channelBankid, HttpServletRequest request, HttpServletResponse response, Model model) {
		//设置查询条件
		channelBankid.setChannelCode(payChannel.getChannelCode());
		Page<ChannelBankid>  page  = new Page<>(request, response);
		//加入分页
		channelBankid.setPage(page);
		List<ChannelBankid> channelBankids = channelBankidService.findList(channelBankid);
		EnumView.channelBankId(channelBankids);
		page.setList(channelBankids);
		model.addAttribute("page", page);
		model.addAttribute("payChannel", payChannel);
		return "modules/route/bankIdMappingDetails";
	}


	/**
	 * @discription 同步bankId
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("route:bankIdMapping:edit")
	@RequestMapping(value = "bankIdSync")
	public String bankIdSync(RedirectAttributes redirectAttributes) {
		IntegrationBankIdSync.bankIdSync();
		addMessage(redirectAttributes, "同步成功");
		return "redirect:"+ Global.getAdminPath()+"/route/bankIdMapping/?repage";
	}

	/**
	 * @discription 删除通道的bankiD信息
	 * @param channelBankid
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("route:bankIdMapping:edit")
	@RequestMapping(value = "delete")
	public String delete(ChannelBankid channelBankid,String channelId,RedirectAttributes redirectAttributes) {
		channelBankidService.delete(channelBankid);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+ Global.getAdminPath()+"/route/bankIdMapping/details?id="+ channelId;
	}

	/**
	 * @discription 手动添加bankId
	 * @return
	 */
	@RequiresPermissions("route:bankIdMapping:view")
	@RequestMapping(value = "addBankIdByHands")
	public void addBankIdByHands(String id,String channelCode,String channelName,String pageNo,String bankId,String subMerchantNo,HttpServletRequest request,HttpServletResponse response,Model model){
		//生成通道明细
		ChannelBankid channelBankid = new ChannelBankid();
		channelBankid.setChannelCode(channelCode);
		channelBankid.setChannelName(channelName);
		channelBankid.setBankId(bankId);
		channelBankid.setSubMerchantNo(subMerchantNo);
		//保存
		if (StringUtils.isNotBlank(bankId)){
			channelBankidService.save(channelBankid,false);
		}
	}




}
