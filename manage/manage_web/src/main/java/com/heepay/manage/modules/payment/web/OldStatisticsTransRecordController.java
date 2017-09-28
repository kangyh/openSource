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
 * 描    述：交易数据统计Controller
 *
 * 创 建 者： @author id
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
@RequestMapping(value = "${adminPath}/payment/oldStatisticsTransRecord")
public class OldStatisticsTransRecordController extends BaseController {

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
		
		if("R1".equals(statisticsTransRecord.getTransType())){
			statisticsTransRecord.setTransType(null);
		}
		if("R1".equals(statisticsTransRecord.getProductCode())){
			statisticsTransRecord.setProductCode(null);
		}
		if("R1".equals(statisticsTransRecord.getPayType())){
			statisticsTransRecord.setPayType(null);
		}
		if(statisticsTransRecord.getBeginStatisticsTime()==null&&statisticsTransRecord.getEndStatisticsTime()==null){
			statisticsTransRecord.setBeginStatisticsTime(DateUtils.getYesterdayDate());
			statisticsTransRecord.setEndStatisticsTime(DateUtils.getYesterdayDate());
		}

		Page<StatisticsTransRecord> page = statisticsTransRecordService.findOldPage(new Page<StatisticsTransRecord>(request, response), statisticsTransRecord);
		model.addAttribute("page", page);

		return "modules/payment/oldStatisticsTransRecordList";
	}
	
	/**
	 * 
	 * @description统计功能
	 * @author TianYanqing      
	 * @created 2017年1月20日 上午11:03:41     
	 * @param statisticsTransRecord
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "statistics")
	public void statistics(StatisticsTransRecord statisticsTransRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		try {
			if(statisticsTransRecord.getBeginStatisticsTime()==null || statisticsTransRecord.getEndStatisticsTime()==null){
				return ;
			}
			StatisticsQueryWhere where = new StatisticsQueryWhere();
			where.setStartTime(DateUtils.getDateStr(statisticsTransRecord.getBeginStatisticsTime(), DateUtils.DATE_FORMAT_DATE));
			where.setEndTime(DateUtils.getDateStr(DateUtils.getInternalDateByDay(statisticsTransRecord.getEndStatisticsTime(), 1), DateUtils.DATE_FORMAT_DATE));
			this.statisitcs.statistics(where);
			WebUtil.outputJson("{msg:\"统计成功\"}", response);
		} catch (Exception e) {
			logger.error("统计功能异常");
			WebUtil.outputJson("{msg:\"统计失败\"}", response);
		}
	}
	
	/**
	 * 
	 * @description导出excel
	 * @author TianYanqing      
	 * @created 2017年1月20日 上午11:03:29     
	 * @param statisticsTransRecord
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "exportExcel")
	public void exportExcel(StatisticsTransRecord statisticsTransRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		 try {
			 
			 if("R1".equals(statisticsTransRecord.getTransType())){
				 statisticsTransRecord.setTransType(null);
			 }
			 if("R1".equals(statisticsTransRecord.getProductCode())){
				 statisticsTransRecord.setProductCode(null);
			 }
			 if("R1".equals(statisticsTransRecord.getPayType())){
				 statisticsTransRecord.setPayType(null);
			 }
			if(statisticsTransRecord.getBeginStatisticsTime()==null && statisticsTransRecord.getEndStatisticsTime()==null){
				statisticsTransRecord.setBeginStatisticsTime(DateUtils.getYesterdayDate());
				statisticsTransRecord.setEndStatisticsTime(new Date());
			}
			//设置不分页 
			statisticsTransRecord.setPage(null);
			List<StatisticsTransRecord> records =  statisticsTransRecordService.findList(statisticsTransRecord);;
			 
			 List<String[]> contents = new ArrayList<String[]>();
			 String title = "交易数据统计:";
			 if(statisticsTransRecord.getBeginStatisticsTime()!=null && statisticsTransRecord.getEndStatisticsTime()!=null){
				  title = "交易数据统计:"+DateUtils.getDateStr(statisticsTransRecord.getBeginStatisticsTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(statisticsTransRecord.getEndStatisticsTime(), DateUtils.DATE_FORMAT_DATE);
			 }
			 String[] headers = new String[] { "交易类型", "产品名称", "支付类型", "总笔数", "总金额（元）", 
					 "成功总笔数", "成功总金额（元）", "成功率", "失败总笔数",
					 "失败总金额（元）","失败率", };
			 
			 for(StatisticsTransRecord record:records){
				 String[] content = new String[headers.length];
				 content[0] = TransType.getContentByValue(record.getTransType());
				 content[1] = ProductType.getContentByValue(record.getProductCode());
				 content[2] = PayType.getContentByValue(record.getPayType());
				 content[3] = record.getTotalCount();
				 content[4] = StringUtil.moneyFmt(record.getTotalAmount());
				 content[5] = record.getSucessCount();
				 content[6]  = StringUtil.moneyFmt(record.getSucessAmount());
				 if(!StringUtil.isBlank(record.getSucessCount())){
					 content[7] = getPercent((Double.valueOf(record.getSucessCount())/Double.valueOf(record.getTotalCount())));
				 }else{
					 content[7] = "0.00%";
				 }
				 content[8] = record.getFailCount();
				 content[9]  = StringUtil.moneyFmt(record.getFailAmount());
				 if(!StringUtil.isBlank(record.getFailCount())){
					 content[10] = getPercent((Double.valueOf(record.getFailCount())/Double.valueOf(record.getTotalCount())));
				 }else{
					 content[10] = "0.00%";
				 }
				 contents.add(content);
			 }
			String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
			ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	private  String getPercent(double d) {
		NumberFormat f = NumberFormat.getPercentInstance();
		f.setMinimumFractionDigits(2);
		return f.format(d);

	}
	
	
	/**
	 * 
	 * @description设置总合计值
	 * @author TianYanqing      
	 * @created 2017年1月16日 下午4:34:32     
	 * @param model
	 * @param page
	 */
	private void setTotal(Model model, List<StatisticsTransRecord> records) {
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
		model.addAttribute("pretotalCount",totalCount );
		model.addAttribute("pretotalAmount",totalAmount );
		model.addAttribute("presucessCount",sucessCount );
		model.addAttribute("presucessAmount",sucessAmount );
		model.addAttribute("prefailCount",failCount );
		model.addAttribute("prefailAmount",failAmount );
	}

	
	
	

	/**
	 * 
	 * @description设置合计值
	 * @author TianYanqing      
	 * @created 2017年1月16日 下午4:34:32     
	 * @param model
	 * @param page
	 */
	private void setTotalPropertie(Model model, Page<StatisticsTransRecord> page) {
		List<StatisticsTransRecord> records = page.getList();
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
		model.addAttribute("totalCount",totalCount );
		model.addAttribute("totalAmount",totalAmount );
		model.addAttribute("sucessCount",sucessCount );
		model.addAttribute("sucessAmount",sucessAmount );
		model.addAttribute("failCount",failCount );
		model.addAttribute("failAmount",failAmount );
	}

	@RequiresPermissions("payment:statisticsTransRecord:view")
	@RequestMapping(value = "form")
	public String form(StatisticsTransRecord statisticsTransRecord, Model model) {
		model.addAttribute("statisticsTransRecord", statisticsTransRecord);
		return "modules/payment/statisticsTransRecordForm";
	}


	@RequiresPermissions("payment:statisticsTransRecord:edit")
	@RequestMapping(value = "save")
	public String save(StatisticsTransRecord statisticsTransRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, statisticsTransRecord)){
			return form(statisticsTransRecord, model);
		}
		statisticsTransRecordService.save(statisticsTransRecord);
		addMessage(redirectAttributes, "保存交易数据统计成功");
		return "redirect:"+Global.getAdminPath()+"/payment/statisticsTransRecord/?repage";
	}
	
	@RequiresPermissions("payment:statisticsTransRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(StatisticsTransRecord statisticsTransRecord, RedirectAttributes redirectAttributes) {
		statisticsTransRecordService.delete(statisticsTransRecord);
		addMessage(redirectAttributes, "删除交易数据统计成功");
		return "redirect:"+Global.getAdminPath()+"/payment/statisticsTransRecord/?repage";
	}

}