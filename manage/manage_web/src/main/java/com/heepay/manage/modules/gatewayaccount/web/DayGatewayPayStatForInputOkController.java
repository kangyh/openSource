/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.gatewayaccount.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.modules.gatewayaccount.entity.DayGatewayPayStatForInput;
import com.heepay.manage.modules.gatewayaccount.service.DayGatewayPayStatForInputService;
import com.heepay.manage.modules.sys.utils.DictUtils;



/**
 *
 * 描    述：网关支付核账Controller
 *
 * 创 建 者： @author 王亚洪
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
@RequestMapping(value = "${adminPath}/gatewayAccount/dayGatewayPayStatForInputOk")
public class DayGatewayPayStatForInputOkController extends BaseController {

	@Autowired
	private DayGatewayPayStatForInputService dayGatewayPayStatForInputService;
	
	
	@ModelAttribute
	public DayGatewayPayStatForInput get(@RequestParam(required=false) String id) {
		DayGatewayPayStatForInput entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dayGatewayPayStatForInputService.get(id);
		}
		if (entity == null){
			entity = new DayGatewayPayStatForInput();
		}
		return entity;
	}
	
	@RequiresPermissions("gatewayAccount:dayGatewayPayStatForInputOk:view")
	@RequestMapping(value = {"list", ""})
	public String list(String beginTime, String endTime, String hz, String channelCode, DayGatewayPayStatForInput dayGatewayPayStatForInput, HttpServletRequest request, HttpServletResponse response, Model model) {
	  if(dayGatewayPayStatForInput.getSortOrder() == null){
      //默认按照创建时间降序
	    dayGatewayPayStatForInput.setSortOrder(SortOrderType.DESC.getValue());
    }
	  String dircon = DictUtils.getDictValue("直连", "ChannelPartner", "");
	  if(StringUtils.isNotEmpty(channelCode)){
        //需要判断channelCode 是不是直连
        if(StringUtils.indexOf(channelCode, "_") != -1){
            String[] str = StringUtils.split(channelCode, "_");
            dayGatewayPayStatForInput.setChannelPartners(str[0]);
            dayGatewayPayStatForInput.setBankCode(str[1]);
        }else{
            dayGatewayPayStatForInput.setChannelPartners(channelCode);
        }
      }
	  dayGatewayPayStatForInput.setChannelCode(null);
	  dayGatewayPayStatForInput.setInputFlag("2");
	  Page<DayGatewayPayStatForInput> page = dayGatewayPayStatForInputService.findPage(new Page<DayGatewayPayStatForInput>(request, response), dayGatewayPayStatForInput); 
	  List<DayGatewayPayStatForInput> list = page.getList();
	  if(list != null && !list.isEmpty()){
	    for(DayGatewayPayStatForInput inputRecord : list){
	      int successCount = Integer.parseInt(inputRecord.getSuccessBillCount());
	      BigDecimal successAmount = new BigDecimal(inputRecord.getSuccessTradeAmt());
	      int inputCount = Integer.parseInt(inputRecord.getInputCheckSuccessCount());
	      BigDecimal inputAmount = new BigDecimal(inputRecord.getInputCheckSuccessAmt());
	      inputRecord.setFailCount(String.valueOf(successCount - inputCount));
	      inputRecord.setFailAmount(String.valueOf(successAmount.subtract(inputAmount)));
	      BigDecimal inputSettleAmt = new BigDecimal(inputRecord.getInputSettleAmt());
	      inputRecord.setRealFailAmount(String.valueOf(inputAmount.subtract(inputSettleAmt)));
	    }
	  }
	  
	  //如果勾选了汇总
	  List<DayGatewayPayStatForInput> newList = Lists.newArrayList();
	  Map<String, DayGatewayPayStatForInput> map = Maps.newHashMap();
	  if(StringUtils.isNotEmpty(hz)){
	    for(DayGatewayPayStatForInput inputRecord : list){
	      String channelPartners = inputRecord.getChannelPartners();
	      String key = null;
	      if(StringUtils.equals(channelPartners, dircon)){
	        key = inputRecord.getBankCode();
	      }else{
	        key = channelPartners;
	      }
	      if(map.containsKey(key)){
	        map.get(key).setSuccessTradeAmt(String.valueOf(new BigDecimal(map.get(key).getSuccessTradeAmt()).add(new BigDecimal(inputRecord.getSuccessTradeAmt()))));
	        map.get(key).setSuccessBillCount(String.valueOf(Integer.parseInt(map.get(key).getSuccessBillCount()) + Integer.parseInt(inputRecord.getSuccessBillCount())));
	      }else{
	        inputRecord.setGroupTime(null);
	        map.put(key, inputRecord);
	      }
	    }
	  }
	  
	  //判断是不是map
	  if(!map.isEmpty()){
	  //遍历Map 将记录扔到newList中
	    for (Map.Entry<String, DayGatewayPayStatForInput> entry : map.entrySet()) {  
	      newList.add(entry.getValue());
	    }  
	  }
	  
	  if(!newList.isEmpty()){
	    page.setList(newList);
	  }
	  List<Map<String, Object>> newMapList = new ArrayList<Map<String, Object>>();
      //直连的渠道
      List<Map<String, Object>> dirconList = dayGatewayPayStatForInputService.getDirconList();
      //三方的渠道
      List<Map<String, Object>> thirdPartyList = dayGatewayPayStatForInputService.getThirdPartyList();
      newMapList.addAll(thirdPartyList);
      newMapList.addAll(dirconList);
      
	  model.addAttribute("page", page);
	  model.addAttribute("hz", hz);
	  model.addAttribute("channelCodeList", newMapList);
	  model.addAttribute("channelCode", channelCode);
	  model.addAttribute("channelCodeHidden", channelCode);
	  model.addAttribute("beginTime", DateUtil.stringToDate(beginTime, DateUtil.DATE_FORMAT_YYYYMMDD));
	  model.addAttribute("endTime", DateUtil.stringToDate(endTime, DateUtil.DATE_FORMAT_YYYYMMDD));
		return "modules/gatewayaccount/dayGatewayPayStatForInputListOk";
	}
	
	@RequiresPermissions("gatewayAccount:dayGatewayPayStatForInputOk:view")
	@RequestMapping(value = "form")
	public String form(DayGatewayPayStatForInput dayGatewayPayStatForInput, Model model) {
		model.addAttribute("dayGatewayPayStatForInput", dayGatewayPayStatForInput);
		return "modules/gatewayaccount/dayGatewayPayStatForInputForm";
	}

	
	@RequiresPermissions("gatewayAccount:dayGatewayPayStatForInputOk:edit")
	@RequestMapping(value = "delete")
	public String delete(DayGatewayPayStatForInput dayGatewayPayStatForInput, RedirectAttributes redirectAttributes) {
		dayGatewayPayStatForInputService.delete(dayGatewayPayStatForInput);
		addMessage(redirectAttributes, "删除网关支付核账成功");
		return "redirect:"+Global.getAdminPath()+"/gatewayaccount/dayGatewayPayStatForInput/?repage";
	}

	@RequiresPermissions("gatewayAccount:dayGatewayPayStatForInputOk:edit")
  @RequestMapping(value = "toUpdate")
  public String toUpdate(String recordId, Model model){
	  DayGatewayPayStatForInput dayGatewayPayStatForInput = dayGatewayPayStatForInputService.get(recordId);
	  int successCount = Integer.parseInt(dayGatewayPayStatForInput.getSuccessBillCount());
    BigDecimal successAmount = new BigDecimal(dayGatewayPayStatForInput.getSuccessTradeAmt());
    int inputCount = Integer.parseInt(dayGatewayPayStatForInput.getInputCheckSuccessCount());
    BigDecimal inputAmount = new BigDecimal(dayGatewayPayStatForInput.getInputCheckSuccessAmt());
    dayGatewayPayStatForInput.setFailCount(String.valueOf(successCount - inputCount));
    dayGatewayPayStatForInput.setFailAmount(String.valueOf(successAmount.subtract(inputAmount)));
    BigDecimal inputSettleAmt = new BigDecimal(dayGatewayPayStatForInput.getInputSettleAmt());
    dayGatewayPayStatForInput.setRealFailAmount(String.valueOf(inputAmount.subtract(inputSettleAmt)));
    model.addAttribute("dayGatewayPayStatForInput", dayGatewayPayStatForInput);
	  return "modules/gatewayaccount/dayGatewayPayStatForInputForm";
  }


	@RequiresPermissions("gatewayAccount:dayGatewayPayStatForInputOk:edit")
  @RequestMapping(value = "update")
	public String update(String recordId,String inputCheckSuccessCount, String inputCheckSuccessAmt, String inputNote, String inputSettleAmt, String checkNote, RedirectAttributes redirectAttributes){
	  DayGatewayPayStatForInput dayGatewayPayStatForInput = dayGatewayPayStatForInputService.get(recordId);
	  dayGatewayPayStatForInput.setInputCheckSuccessAmt(inputCheckSuccessAmt);
	  dayGatewayPayStatForInput.setInputCheckSuccessCount(inputCheckSuccessCount);
	  dayGatewayPayStatForInput.setInputNote(inputNote);
	  dayGatewayPayStatForInput.setInputSettleAmt(inputSettleAmt);
	  dayGatewayPayStatForInput.setCheckNote(checkNote);
	  dayGatewayPayStatForInputService.updateDayGateWayPayStatForInput(dayGatewayPayStatForInput);
	  addMessage(redirectAttributes, "更新成功");
	  return "redirect:"+Global.getAdminPath()+"/gatewayAccount/dayGatewayPayStatForInputOk/list";
	}
	
	
  @RequiresPermissions("gatewayAccount:dayGatewayPayStatForInputOk:view")
  @RequestMapping(value="getStatisticsList")
  @ResponseBody
  public void getStatisticsList(String channelCode, String beginTime, String endTime, HttpServletResponse response) throws ParseException{
    DayGatewayPayStatForInput dayGatewayPayStatForInput = new DayGatewayPayStatForInput();
    if (StringUtils.isNotEmpty(channelCode)) {
        // 需要判断channelCode 是不是直连
        if (StringUtils.indexOf(channelCode, "_") != -1) {
            String[] str = StringUtils.split(channelCode, "_");
            dayGatewayPayStatForInput.setChannelPartners(str[0]);
            dayGatewayPayStatForInput.setBankCode(str[1]);
        } else {
            dayGatewayPayStatForInput.setChannelPartners(channelCode);
        }
    }
    if (StringUtils.isNotEmpty(beginTime)) {
        dayGatewayPayStatForInput.setBeginTime(beginTime);
    }
    if (StringUtils.isNotEmpty(endTime)) {
        dayGatewayPayStatForInput.setEndTime(endTime);
    }
    dayGatewayPayStatForInput.setInputFlag("2");

    //汇总金额
    int successTotalSum = 0;
    BigDecimal successTotalAmount = BigDecimal.ZERO;
    int inputCheckSuccessTotalSum = 0;
    BigDecimal inputCheckSuccessTotalAmount = BigDecimal.ZERO;
    int failTotalSum = 0;
    BigDecimal failTotalAmount = BigDecimal.ZERO;
    BigDecimal inputSettleTotalAmount = BigDecimal.ZERO;
    BigDecimal realFailTotalAmount = BigDecimal.ZERO;
    List<DayGatewayPayStatForInput> dayGatewayPayStatForInputList = dayGatewayPayStatForInputService.findList(dayGatewayPayStatForInput);
    if(dayGatewayPayStatForInputList != null && !dayGatewayPayStatForInputList.isEmpty()){
      for(DayGatewayPayStatForInput record : dayGatewayPayStatForInputList){
        int successCount = Integer.parseInt(record.getSuccessBillCount());
        successTotalSum = successTotalSum + successCount;
        BigDecimal successAmount = new BigDecimal(StringUtils.isEmpty(record.getSuccessTradeAmt())?"0.00":record.getSuccessTradeAmt());
        successTotalAmount = successTotalAmount.add(successAmount);
        int inputCount = Integer.parseInt(record.getInputCheckSuccessCount()); 
        inputCheckSuccessTotalSum = inputCheckSuccessTotalSum + inputCount;
        BigDecimal inputCheckSuccessAmount = new BigDecimal(StringUtils.isEmpty(record.getInputCheckSuccessAmt())?"0.00":record.getInputCheckSuccessAmt());
        inputCheckSuccessTotalAmount = inputCheckSuccessTotalAmount.add(inputCheckSuccessAmount);
        failTotalSum = failTotalSum + (successCount - inputCount);
        BigDecimal failAmount = successAmount.subtract(inputCheckSuccessAmount);
        failTotalAmount = failTotalAmount.add(failAmount);
        BigDecimal inputSettleAmount = new BigDecimal(StringUtils.isEmpty(record.getInputSettleAmt())?"0.00":record.getInputSettleAmt());
        inputSettleTotalAmount = inputSettleTotalAmount.add(inputSettleAmount);
        BigDecimal realFailAmount = inputCheckSuccessAmount.subtract(inputSettleAmount);
        realFailTotalAmount = realFailTotalAmount.add(realFailAmount);
        
      }
    }
    
    DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(); 
    df.applyPattern("￥#,##0.00"); 
    Map<String, String> map = new HashMap<String, String>();
    map.put("successTotalSum", String.valueOf(successTotalSum));
    map.put("successTotalAmount", df.format(successTotalAmount));
    map.put("inputCheckSuccessTotalSum", String.valueOf(inputCheckSuccessTotalSum));
    map.put("inputCheckSuccessTotalAmount", df.format(inputCheckSuccessTotalAmount));
    map.put("failTotalSum", String.valueOf(failTotalSum));
    map.put("failTotalAmount", df.format(failTotalAmount));
    map.put("inputSettleTotalAmount", df.format(inputSettleTotalAmount));
    map.put("realFailTotalAmount", df.format(realFailTotalAmount));
    
    WebUtil.outputJson(map, response);
    
    
  }
  

	
	
}