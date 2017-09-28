/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.gatewayaccount.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.manage.modules.gatewayaccount.entity.DayGatewayPayStatForInput;
import com.heepay.manage.modules.gatewayaccount.service.DayGatewayPayStatForInputService;
import com.heepay.manage.modules.payment.entity.StatisticsRecord;
import com.heepay.manage.modules.payment.service.StatisticsRecordService;
import com.heepay.manage.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


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
@RequestMapping(value = "${adminPath}/gatewayAccount/dayGatewayPayStatForInput")
public class DayGatewayPayStatForInputController extends BaseController {

	@Autowired
	private DayGatewayPayStatForInputService dayGatewayPayStatForInputService;
	
	
	private static final String INPUT_FLAG = "2";
	
	private static final String AMOUNT_INIT = "0";
	
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
	
    @RequiresPermissions("gatewayAccount:dayGatewayPayStatForInput:view")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(DayGatewayPayStatForInput dayGatewayPayStatForInput, HttpServletRequest request, HttpServletResponse response, Model model) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date statisticsTime = DateUtil.stringToDate(DateUtil.dateToString(cal.getTime()),
                DateUtil.DATE_FORMAT_YYYYMMDD);
        List<Map<String, Object>> newMapList = new ArrayList<Map<String, Object>>();
        //直连的渠道
        List<Map<String, Object>> dirconList = dayGatewayPayStatForInputService.getDirconList();
        //三方的渠道
        List<Map<String, Object>> thirdPartyList = dayGatewayPayStatForInputService.getThirdPartyList();
        newMapList.addAll(thirdPartyList);
        newMapList.addAll(dirconList);
        dayGatewayPayStatForInput.setInputFlag("1");//未保存的
        dayGatewayPayStatForInput.setBeginTime(DateUtil.dateToString(statisticsTime, DateUtil.DATE_FORMAT_YYYYMMDD) + " 00:00:00");
        dayGatewayPayStatForInput.setEndTime(DateUtil.dateToString(statisticsTime, DateUtil.DATE_FORMAT_YYYYMMDD) + " 23:59:59");
        Page<DayGatewayPayStatForInput> page = dayGatewayPayStatForInputService.findPage(new Page<DayGatewayPayStatForInput>(request, response), dayGatewayPayStatForInput);
        model.addAttribute("page", page);
        model.addAttribute("channelCodeList", newMapList);
        model.addAttribute("statisticsTime", statisticsTime);
        return "modules/gatewayaccount/dayGatewayPayStatForInputList";
    }
	
	
	@RequiresPermissions("gatewayAccount:dayGatewayPayStatForInput:view")
	@RequestMapping(value = "getListByStatisticTime", method=RequestMethod.POST)
	public String getListByStatisticTime(String statisticsTime, String channelCode, HttpServletRequest request, HttpServletResponse response, Model model) {
	  DayGatewayPayStatForInput dayGatewayPayStatForInput = new DayGatewayPayStatForInput();
	  dayGatewayPayStatForInput.setBeginTime(statisticsTime + " 00:00:00");
	  dayGatewayPayStatForInput.setEndTime(statisticsTime + " 23:59:59");
	  dayGatewayPayStatForInput.setInputFlag("1");
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
	  
	  Page<DayGatewayPayStatForInput> page = dayGatewayPayStatForInputService.findPage(new Page<DayGatewayPayStatForInput>(request, response), dayGatewayPayStatForInput);
	  
	  List<Map<String, Object>> newMapList = new ArrayList<Map<String, Object>>();
      //直连的渠道
      List<Map<String, Object>> dirconList = dayGatewayPayStatForInputService.getDirconList();
      //三方的渠道
      List<Map<String, Object>> thirdPartyList = dayGatewayPayStatForInputService.getThirdPartyList();
      newMapList.addAll(thirdPartyList);
      newMapList.addAll(dirconList);
      
	  model.addAttribute("page", page);
	  model.addAttribute("channelCodeList", newMapList);
	  model.addAttribute("channelCode", channelCode);
	  model.addAttribute("statisticsTime", DateUtil.stringToDate(statisticsTime, DateUtil.DATE_FORMAT_YYYYMMDD));
	  return "modules/gatewayaccount/dayGatewayPayStatForInputList";
	}

	
	@RequiresPermissions("gatewayAccount:dayGatewayPayStatForInput:view")
	@RequestMapping(value = "form")
	public String form(DayGatewayPayStatForInput dayGatewayPayStatForInput, Model model) {
		model.addAttribute("dayGatewayPayStatForInput", dayGatewayPayStatForInput);
		return "modules/gatewayaccount/dayGatewayPayStatForInputForm";
	}

	@SuppressWarnings("unchecked")
  @RequiresPermissions("gatewayAccount:dayGatewayPayStatForInput:edit")
	@RequestMapping(value = "save")
	public void save(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) throws Exception {
	  Principal principal = UserUtils.getPrincipal();
	  Map<String, Object> resultMap = new HashMap<String, Object>();
	  String reqData = request.getParameter("reqData");
	  JSONArray jsonArray = JSONArray.fromObject(reqData);
	  Iterator<Object> it = jsonArray.iterator();
    while (it.hasNext()) {
      JSONObject obj = (JSONObject) it.next();
      DayGatewayPayStatForInput dayGatewayPayStatForInput = dayGatewayPayStatForInputService.get(obj.getString("recordId"));
      dayGatewayPayStatForInput.setInputCheckSuccessCount(obj.getString("checkSuccessCount"));
      dayGatewayPayStatForInput.setInputCheckSuccessAmt(obj.getString("checkSuccessAmount"));
      dayGatewayPayStatForInput.setInputNote(obj.getString("remark"));
      dayGatewayPayStatForInput.setInputSettleAmt(obj.getString("settleAmount"));
      dayGatewayPayStatForInput.setCheckNote(obj.getString("remarkOther"));
      dayGatewayPayStatForInput.setInputFlag(INPUT_FLAG);
      dayGatewayPayStatForInput.setInputUser(principal.getLoginName());
      try { 
        dayGatewayPayStatForInputService.updateDayGateWayPayStatForInput(dayGatewayPayStatForInput);
      } catch (Exception e) {
        logger.error("系统异常{}",e.getMessage());
        resultMap.put("success", false);
        resultMap.put("message", "系统异常");
        WebUtil.outputJson(resultMap, response);
        return;
      }
    }
    resultMap.put("success", true);
    WebUtil.outputJson(resultMap, response);
    
	}
	
	@RequiresPermissions("gatewayAccount:dayGatewayPayStatForInput:edit")
	@RequestMapping(value = "delete")
	public String delete(DayGatewayPayStatForInput dayGatewayPayStatForInput, RedirectAttributes redirectAttributes) {
		dayGatewayPayStatForInputService.delete(dayGatewayPayStatForInput);
		addMessage(redirectAttributes, "删除网关支付核账成功");
		return "redirect:"+Global.getAdminPath()+"/gatewayaccount/dayGatewayPayStatForInput/?repage";
	}

	 /**
   * 
  * @description 构造StatisticsChannelRecord集合
  * @author 王亚洪       
  * @created 2017年4月25日 上午10:24:12     
  * @param list
  * @return
   */
  private List<StatisticsRecord> getStatList(List<StatisticsRecord> list){
    logger.info("getStatList={}",JsonMapperUtil.nonDefaultMapper().toJson(list));
    String dircon = DictUtils.getDictValue("直连", "ChannelPartner", "");
    List<StatisticsRecord> newList = new ArrayList<StatisticsRecord>();
    Map<String, StatisticsRecord> statMap = new HashMap<String, StatisticsRecord>();
    if(list !=null && !list.isEmpty()){
      //先分类汇总，第三方的只按照通道来汇总，直连的要再统计银行
      for(StatisticsRecord record : list){
        String channelPartners = record.getChannelPartners();
        String key = null;
        if(StringUtils.equals(channelPartners, dircon)){
          key = record.getBankCode();
        }else{
          key = channelPartners;
        }
        if(statMap.containsKey(key)){
          statMap.get(key).setSucessAmount(String.valueOf(new BigDecimal(statMap.get(key).getSucessAmount()).add(new BigDecimal(record.getSucessAmount()))));
          statMap.get(key).setSucessCount(String.valueOf(Integer.parseInt(statMap.get(key).getSucessCount()) + Integer.parseInt(record.getSucessCount())));
        }else{
          if(!StringUtils.equals(channelPartners, dircon)){
            record.setBankName("--");
          }
          statMap.put(key, record);
        }
      }
    }
    
    //遍历Map 将记录扔到newList中
    for (Map.Entry<String, StatisticsRecord> entry : statMap.entrySet()) {  
      newList.add(entry.getValue());
    }  
    return newList;
  }
  
  
  
	
}