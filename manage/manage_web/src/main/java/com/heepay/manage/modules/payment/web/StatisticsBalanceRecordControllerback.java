/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.heepay.date.DateUtils;
import com.heepay.enums.ProductType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.StatisticsBalanceRecord;
import com.heepay.manage.modules.payment.entity.StatisticsTransRecord;
import com.heepay.manage.modules.payment.service.StatisticsTransRecordService;


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
@RequestMapping(value = "${adminPath}/payment/statisticsBalance")
public class StatisticsBalanceRecordControllerback extends BaseController {

	@Autowired
	private StatisticsTransRecordService statisticsTransRecordService;
	
	@RequiresPermissions("payment:statisticsTransRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(StatisticsBalanceRecord statisticsBalanceRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		
/*		if("R1".equals(statisticsTransRecord.getTransType())){
			statisticsTransRecord.setTransType(null);
		}
		if("R1".equals(statisticsTransRecord.getProductCode())){
			statisticsTransRecord.setProductCode(null);
		}
		if("R1".equals(statisticsTransRecord.getPayType())){
			statisticsTransRecord.setPayType(null);
		}*/
/*		if(statisticsBalanceRecord.getBeginStatisticsTime()==null&&statisticsBalanceRecord.getEndStatisticsTime()==null){
			statisticsBalanceRecord.setBeginStatisticsTime(DateUtils.getYesterdayDate());
			statisticsBalanceRecord.setEndStatisticsTime(DateUtils.getYesterdayDate());
		}
*/

		Page<StatisticsBalanceRecord> page = statisticsTransRecordService.findBalancePage(new Page<StatisticsBalanceRecord> (), statisticsBalanceRecord);	
		model.addAttribute("page", page);

		return "modules/payment/statisticsBalance";
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