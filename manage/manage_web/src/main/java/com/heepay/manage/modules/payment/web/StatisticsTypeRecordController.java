/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.PayType;
import com.heepay.enums.ProductType;
import com.heepay.enums.TransType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.StatisticsTransRecord;
import com.heepay.manage.modules.payment.rpc.client.StatiServiceClient;
import com.heepay.manage.modules.payment.service.StatisticsTransRecordService;
import com.heepay.manage.modules.util.ExcelUtil2007;
import com.heepay.rpc.payment.model.StatisticsQueryWhere;


/**
 * 
* 
* 描    述：按类型统计
*
* 创 建 者： TianYanqing  
* 创建时间： 2017年5月3日 下午2:27:23 
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
@RequestMapping(value = "${adminPath}/payment/statisticsTypeRecord")
public class StatisticsTypeRecordController extends BaseController {

	@Autowired
	private StatisticsTransRecordService statisticsTransRecordService;
	
	@Autowired
	private StatiServiceClient statisitcs;
	
	@ModelAttribute
	public StatisticsTransRecord get(@RequestParam(required=false) String id) {
		StatisticsTransRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = statisticsTransRecordService.get(id);
		}
		if (entity == null){
			entity = new StatisticsTransRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:statisticsTransRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(StatisticsTransRecord statisticsTransRecord, HttpServletRequest request, HttpServletResponse response, Model model) {

		if(statisticsTransRecord.getBeginStatisticsTime()==null&&statisticsTransRecord.getEndStatisticsTime()==null){
			statisticsTransRecord.setBeginStatisticsTime(DateUtils.getDate());
			statisticsTransRecord.setEndStatisticsTime(DateUtils.getDate());
			statisticsTransRecord.setIsNow("now");
		}
		List<StatisticsTransRecord> payTypeList = statisticsTransRecordService.findPayTypeList(statisticsTransRecord);
		List<StatisticsTransRecord> productList = statisticsTransRecordService.findProductList(statisticsTransRecord);
		List<StatisticsTransRecord> transTypeList = statisticsTransRecordService.findTransTypeList(statisticsTransRecord);
		
		model.addAttribute("payTypeList", payTypeList);
		model.addAttribute("productList", productList);
		model.addAttribute("transTypeList", transTypeList);
		
		return "modules/payment/statisticsTypeRecordList";
	}
	
	

}