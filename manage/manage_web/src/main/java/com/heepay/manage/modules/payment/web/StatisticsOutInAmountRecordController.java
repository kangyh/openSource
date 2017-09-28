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
import com.heepay.manage.modules.payment.service.StatisticsRecordService;
import com.heepay.manage.modules.payment.service.StatisticsTransRecordService;
import com.heepay.manage.modules.util.ExcelUtil2007;
import com.heepay.rpc.payment.model.StatisticsQueryWhere;


/**
 * 
* 
* 描    述：出入金统计
*
* 创 建 者： TianYanqing  
* 创建时间： 2017年5月17日 下午2:41:57 
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
@RequestMapping(value = "${adminPath}/payment/statisticsOutInAmountRecord")
public class StatisticsOutInAmountRecordController extends BaseController {

	@Autowired
	private StatisticsTransRecordService statisticsTransRecordService;
/*	@Autowired
	private StatisticsRecordService statisticsRecordService;*/
	
	@RequiresPermissions("payment:statisticsTransRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(StatisticsTransRecord statisticsTransRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		
/*		if("R1".equals(statisticsTransRecord.getTransType())){
			statisticsTransRecord.setTransType(null);
		}
		if("R1".equals(statisticsTransRecord.getProductCode())){
			statisticsTransRecord.setProductCode(null);
		}
		if("R1".equals(statisticsTransRecord.getPayType())){
			statisticsTransRecord.setPayType(null);
		}*/
		if(statisticsTransRecord.getBeginStatisticsTime()==null&&statisticsTransRecord.getEndStatisticsTime()==null){
			statisticsTransRecord.setBeginStatisticsTime(DateUtils.getYesterdayDate());
			statisticsTransRecord.setEndStatisticsTime(DateUtils.getYesterdayDate());
		}

		List<StatisticsTransRecord> productList = statisticsTransRecordService.findProductList(statisticsTransRecord);
		List<StatisticsTransRecord> outList = new ArrayList<StatisticsTransRecord>();
		List<StatisticsTransRecord> inList = new ArrayList<StatisticsTransRecord>();
		getOutInList(productList,outList,inList);
		
		model.addAttribute("outList", outList);
		model.addAttribute("inList", inList);
		setOutTotalPropertie(model,outList);
		setInTotalPropertie(model, inList);
		return "modules/payment/statisticsOutInAmountRecordList";
	}

	/**
	 * 
	 * @description出金和入金分开
	 * @author TianYanqing      
	 * @created 2017年5月17日 下午4:06:49     
	 * @param productList
	 * @param outList
	 * @param inList
	 */
	private void getOutInList(List<StatisticsTransRecord> productList, List<StatisticsTransRecord> outList,
			List<StatisticsTransRecord> inList) {
		for(StatisticsTransRecord record:productList){
			if((ProductType.BATCHPAY.getValue()).equals(record.getProductCode())
					||(ProductType.REFUND.getValue()).equals(record.getProductCode())
					||(ProductType.WITHDRAW.getValue()).equals(record.getProductCode())
					||(ProductType.DEPOSIT_WITHDRAW.getValue()).equals(record.getProductCode())
					||(ProductType.PERSONAL_WITHDRAW.getValue()).equals(record.getProductCode())){
				outList.add(record);
			}else{
				inList.add(record);
			}
		}
		
	}
	
	/**
	 * 
	 * @description设置合计值
	 * @author TianYanqing      
	 * @created 2017年1月16日 下午4:34:32     
	 * @param model
	 * @param page
	 */
	private void setOutTotalPropertie(Model model, List<StatisticsTransRecord> records) {
		if(records==null||records.isEmpty()){
			return ;
		}
		int totalCount = 0;
		BigDecimal totalAmount = BigDecimal.ZERO;
		int sucessCount = 0;
		BigDecimal sucessAmount=BigDecimal.ZERO;
		int failCount = 0;
		BigDecimal failAmount=BigDecimal.ZERO;
		
		for(StatisticsTransRecord record :records){
			totalCount+=Integer.parseInt(record.getTotalCount());
			totalAmount = totalAmount.add(new BigDecimal(record.getTotalAmount()));
			sucessCount+=Integer.parseInt(record.getSucessCount());
			sucessAmount = sucessAmount.add(new BigDecimal(record.getSucessAmount()));
			failCount+=Integer.parseInt(record.getFailCount());
			failAmount = failAmount.add(new BigDecimal(record.getFailAmount()));
		}
		model.addAttribute("outTotalCount",totalCount );
		model.addAttribute("outTotalAmount",totalAmount );
		model.addAttribute("outSucessCount",sucessCount );
		model.addAttribute("outSucessAmount",sucessAmount );
		model.addAttribute("outFailCount",failCount );
		model.addAttribute("outFailAmount",failAmount );
	}
	
	/**
	 * 
	 * @description设置合计值
	 * @author TianYanqing      
	 * @created 2017年1月16日 下午4:34:32     
	 * @param model
	 * @param page
	 */
	private void setInTotalPropertie(Model model, List<StatisticsTransRecord> records) {
		if(records==null||records.isEmpty()){
			return ;
		}
		int totalCount = 0;
		BigDecimal totalAmount = BigDecimal.ZERO;
		int sucessCount = 0;
		BigDecimal sucessAmount=BigDecimal.ZERO;
		int failCount = 0;
		BigDecimal failAmount=BigDecimal.ZERO;
		
		for(StatisticsTransRecord record :records){
			totalCount+=Integer.parseInt(record.getTotalCount());
			totalAmount = totalAmount.add(new BigDecimal(record.getTotalAmount()));
			sucessCount+=Integer.parseInt(record.getSucessCount());
			sucessAmount = sucessAmount.add(new BigDecimal(record.getSucessAmount()));
			failCount+=Integer.parseInt(record.getFailCount());
			failAmount = failAmount.add(new BigDecimal(record.getFailAmount()));
		}
		model.addAttribute("inTotalCount",totalCount );
		model.addAttribute("inTotalAmount",totalAmount );
		model.addAttribute("inSucessCount",sucessCount );
		model.addAttribute("inSucessAmount",sucessAmount );
		model.addAttribute("inFailCount",failCount );
		model.addAttribute("inFailAmount",failAmount );
	}
	
	
}