package com.heepay.manage.modules.route.web;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.entity.PayChannel;
import com.heepay.manage.modules.route.service.BankInfoService;
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
import java.util.Arrays;
import java.util.List;

/**
 * 名称：支付通道信息操作类
 * 创建者：刘萌
 * 创建日期：2016-8-15
 * 创建描述：支付通道信息的查看、添加、修改、查询功能
 *
 * 审核者：于亮
 * 审核时间：2016年9月1日
 * 审核描述：代码缩进部分不标准；save方法重复返回可以最后写一次；加方法注释；
 * 
 * 修 改 者：
 * 修改时间：
 * 修改描述： 
 */
@Controller
@RequestMapping(value = "${adminPath}/route/payChannel")
public class PayChannelController extends BaseController {
	
    @Autowired
    private PayChannelService payChannelService;
  
    @Autowired
    private BankInfoService bankInfoService;

  	/**
  	* @discription 根据id获取支付通道信息
  	* @author l.m
  	* @created 2016年9月7日 下午5:36:57     
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
  	* @discription 获取支付通道信息列表
  	* @author L.M
  	* @created 2016年9月7日 下午5:37:56     
  	* @param payChannel
  	* @param request
  	* @param response
  	* @param model
  	* @return     
  	*/
  	@RequiresPermissions("route:payChannel:view")
  	@RequestMapping(value = {"list", ""})
  	public String list(PayChannel payChannel,@RequestParam (defaultValue = "1")String pageNo,HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PayChannel>  page  = new Page<PayChannel>(request, response);
		//根据创建日期倒序排列
		page.setOrderBy("updateDate desc");
		if(StringUtils.isNotBlank(pageNo)){
			page.setPageNo(Integer.valueOf(pageNo));
		}
		page = payChannelService.findPage(page, payChannel);
		//判断是否为空
		if(null!=page.getList() && !page.getList().isEmpty()) {
			//支付通道的状态由值取内容
			page.setList(EnumView.PayChannel(page.getList()));
		}
		model.addAttribute("payChannelFind", payChannel);
		model.addAttribute("page", page);
		return "modules/route/payChannelList";
  	}
    
  	  
  	/**     
  	* @discription 添加支付通道信息
  	* @author L.M
    * @created 2016年9月7日 下午5:40:28
  	* @param payChannel
  	* @param model
  	* @return     
  	*/
  	@RequiresPermissions("route:payChannel:view")
  	@RequestMapping(value = "form")
  	public String form(PayChannel payChannel, Model model) {
		model.addAttribute("payChannel", payChannel);
		return "modules/route/payChannelForm";
  	}
  	
  	  
  	/**     
  	* @discription 修改支付通道信息
  	* @author L.M
    * @created 2016年9月7日 下午5:40:51
  	* @param payChannel
  	* @param model
  	* @return     
  	*/
  	@RequiresPermissions("route:payChannel:edit")
    @RequestMapping(value = "update")
    public String update(PayChannel payChannel,String bankNoFind,String channelPartnerCodeFind,String channelTypeCodeFind,String cardTypeCodeFind,String selectStatusFind,String pageNo, Model model) {
		payChannelService.changeValue(payChannel);
		model.addAttribute("payChannel", payChannel);
		model.addAttribute("bankNoFind", bankNoFind);
		model.addAttribute("channelPartnerCodeFind", channelPartnerCodeFind);
		model.addAttribute("channelTypeCodeFind", channelTypeCodeFind);
		model.addAttribute("cardTypeCodeFind", cardTypeCodeFind);
		model.addAttribute("selectStatusFind", selectStatusFind);
		model.addAttribute("pageNo", pageNo);
		return "modules/route/payChannelUpdate";
    }
  	
  	  
  	/**     
  	* @discription 添加修改保存支付通道信息
  	* @author L.M
    * @created 2016年9月7日 下午5:41:14
  	* @param payChannel
  	* @param model
  	* @param redirectAttributes
  	* @return     
  	*/
  	@RequiresPermissions("route:payChannel:edit")
  	@RequestMapping(value = "save")
  	public String save(PayChannel payChannel,String bankNoFind,String channelPartnerCodeFind,String channelTypeCodeFind,String cardTypeCodeFind,String selectStatusFind,String pageNo, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, payChannel)){
			return form(payChannel, model);
		}
		payChannel.setChannelCode("");

		//分割bankNo然后批量查找bankName
		String[] split = payChannel.getBankNo().split(",");
		List<String> list = Arrays.asList(split);
		List<BankInfo> listChannel = bankInfoService.getBankNameForList(list);

		String name = "";
		String resultName = "";
		//将 payChannel 赋值给overPayChannel 循环将bankNo和bankName插入List然后去重插入和
		for (int i = 0 ,length = listChannel.size(); i < length; i++) {

			PayChannel overPayChannel  = payChannel.clone();
			overPayChannel.setBankNo(listChannel.get(i).getBankNo());
			String bankName = listChannel.get(i).getBankName();
			overPayChannel.setBankName(bankName);
			//查询是否存在这个通道，存在跳出不存在继续

			int num = payChannelService.sumNum(overPayChannel);
			if(num == 0){
				//只有一条通道的优先级为默认
				overPayChannel = payChannelService.setChannelSort(overPayChannel);
				payChannelService.savePayChannel(overPayChannel);
				resultName += bankName + ",";
			}else {
				name += bankName + ",";
			}
		}

		String result = "";
		if(name.equals("")){
			result = resultName + "支付通道保存成功!";
		}else{
			if(resultName.equals("")){
				result = "<span style='color: red'>"+name+"</span>" + "已存在不保存";
			}else {
				result = "<span style='color: red'>"+name+"</span>" + "已存在不保存,其余支付通道 <span style='color: #00ffff;'>"+resultName+"</span>保存成功!";
			}
		}
		addMessage(redirectAttributes, result);

		return "redirect:"+Global.getAdminPath()+"/route/payChannel/?bankNo="+bankNoFind+"&channelPartnerCode="+channelPartnerCodeFind+"&channelTypeCode="+channelTypeCodeFind+"&cardTypeCode="+cardTypeCodeFind+"&status="+selectStatusFind+"&pageNo="+pageNo;
  	}
  	 
  	
  	/**     
  	* @discription 获取支付通道详情页面
  	* @author L.M
    * @created 2016年9月7日 下午5:41:42
  	* @param payChannel
  	* @param model
  	* @return     
  	*/
  	@RequiresPermissions("route:payChannel:view")
  	@RequestMapping(value = "details")
  	public String details(PayChannel payChannel, Model model) {
        //支付通道的成本类型、本金类型、本金周期、付款类型、对公对私标识、状态由值置内容
		payChannel=EnumView.payChannelShow(payChannel);
        model.addAttribute("payChannel", payChannel);
        return "modules/route/payChannelDetails";
  	}
  	 
  	   
  	/**     
  	* @discription 支付通道启用、禁用状态修改
  	* @author L.M
    * @created 2016年9月7日 下午5:42:14
  	* @param payChannel
  	* @param redirectAttributes
  	* @return     
  	*/
  	@RequiresPermissions("route:payChannel:edit")
    @RequestMapping(value = "status")
    public String status(PayChannel payChannel,String pageNo, RedirectAttributes redirectAttributes) {
		payChannelService.status(payChannel);
		addMessage(redirectAttributes, "成功");
		System.out.println("redirect:"+Global.getAdminPath()+"/route/payChannel/?bankNo="+payChannel.getBankNo()+"&channelPartnerCode="+payChannel.getChannelTypeCode()+"&channelTypeCode="+payChannel.getChannelTypeCode()+"&cardTypeCode="+payChannel.getCardTypeCode()+"&status"+payChannel.getSelectStatus()+"&pageNo="+pageNo);
		return "redirect:"+Global.getAdminPath()+"/route/payChannel/?bankNo="+payChannel.getBankNo()+"&channelPartnerCode="+payChannel.getChannelPartnerCode()+"&channelTypeCode="+payChannel.getChannelTypeCode()+"&cardTypeCode="+payChannel.getCardTypeCode()+"&status="+payChannel.getSelectStatus()+"&pageNo="+pageNo;
    }

	/**
	 * 修改通道合作方页面
	 * @param payChannel
	 * @param model
	 * @return
	 */
	@RequiresPermissions("route:payChannel:view")
	@RequestMapping(value = "batchList")
	public String batchList(PayChannel payChannel, Model model) {
		model.addAttribute("payChannel", payChannel);
		return "modules/route/payChannelBatchList";
	}

	/**
	 * @discription 通道合作方批量启用、禁用状态修改
	 * @author L.M
	 * @created 2017年5月15日 下午4:11:14
	 * @param payChannel
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("route:payChannel:edit")
	@RequestMapping(value = "batchStatus")
	public String batchStatus(PayChannel payChannel, RedirectAttributes redirectAttributes) {
		payChannelService.batchStatus(payChannel);
		addMessage(redirectAttributes, "修改状态成功");
		return "redirect:"+Global.getAdminPath()+"/route/payChannel/queryList?channelPartnerCode="+payChannel.getChannelPartnerCode()+"&channelTypeCode="+payChannel.getChannelTypeCode()+"&cardTypeCode="+payChannel.getCardTypeCode()+"&status="+payChannel.getSelectStatus();
	}

	/**
	 * @discription 通道合作方批量修改页面
	 * @author L.M
	 * @created 2017年5月15日 下午4:11:14
	 * @param payChannel
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("route:payChannel:view")
	@RequestMapping(value = "batchUpdate")
	public String batchUpdate(PayChannel payChannel,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, HttpServletResponse response) {
		payChannel = payChannelService.groupPayChannel(payChannel);
		payChannelService.changeValue(payChannel);
//		model.addAttribute("payChannel", payChannel);
		//涉及修改的支付通道展示Page
		Page<PayChannel>  page  = new Page<PayChannel>(request, response);
		//根据创建日期倒序排列
		page.setOrderBy("createDate desc");
		page = payChannelService.findInvolvedChannelPage(page, payChannel);
		//判断是否为空
		if(null!=page.getList() && !page.getList().isEmpty()) {
			//支付通道的状态由值取内容
			page.setList(EnumView.PayChannel(page.getList()));
		}
		model.addAttribute("page", page);
		model.addAttribute("payChannel", payChannel);
		return "modules/route/payChannelBatchUpdate";
	}

	/**
	 * @discription 通道合作方批量修改方法
	 * @author L.M
	 * @created 2017年5月15日 下午4:11:14
	 * @param payChannel
	 * @param redirectAttributes
	 * @return
	 *
	 */
	@RequiresPermissions("route:payChannel:edit")
	@RequestMapping(value = "batchUpdatePayChannel")
	public String batchUpdatePayChannel(PayChannel payChannel, RedirectAttributes redirectAttributes) {
		payChannelService.batchUpdatePayChannel(payChannel);
		addMessage(redirectAttributes, "修改通道成功");
		return "redirect:"+Global.getAdminPath()+"/route/payChannel/queryList?channelPartnerCode="+payChannel.getChannelPartnerCode()+"&channelTypeCode="+payChannel.getChannelTypeCode()+"&cardTypeCode="+payChannel.getCardTypeCode()+"&status="+payChannel.getStatus();
	}

	/**
	 * @discription 获取通道合作方通道信息列表
	 * @author L.M
	 * @created 2017年5月15日 下午5:37:56
	 * @param payChannel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("route:payChannel:view")
	@RequestMapping(value = "queryList")
	public String queryList(PayChannel payChannel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PayChannel>  page  = new Page<PayChannel>(request, response);
		//根据创建日期倒序排列
//		page.setOrderBy("createDate desc");
		page = payChannelService.findChannelGroupPage(page, payChannel);
		//判断是否为空
		if(null!=page.getList() && !page.getList().isEmpty()) {
			//支付通道的状态由值取内容
			page.setList(EnumView.PayChannel(page.getList()));
		}
		model.addAttribute("payChannelFind", payChannel);
		model.addAttribute("page", page);
		return "modules/route/payChannelBatchList";
	}
}