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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.StatisticsChannelRecord;
import com.heepay.manage.modules.payment.service.StatisticsChannelRecordService;
import com.heepay.manage.modules.util.ExcelUtil2007;


/**
 *
 * 描    述：通道数据统计Controller
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
@RequestMapping(value = "${adminPath}/payment/statisticsChannelRecord")
public class StatisticsChannelRecordController extends BaseController {

	@Autowired
	private StatisticsChannelRecordService statisticsChannelRecordService;
	
	@ModelAttribute
	public StatisticsChannelRecord get(@RequestParam(required=false) String id) {
		StatisticsChannelRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = statisticsChannelRecordService.get(id);
		}
		if (entity == null){
			entity = new StatisticsChannelRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:statisticsChannelRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(StatisticsChannelRecord statisticsChannelRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		if("R1".equals(statisticsChannelRecord.getChannelPartners())){
			statisticsChannelRecord.setChannelPartners(null);
		}
		if("R1".equals(statisticsChannelRecord.getBankCode())){
			statisticsChannelRecord.setBankCode(null);
		}
		if("R1".equals(statisticsChannelRecord.getChannelType())){
			statisticsChannelRecord.setChannelType(null);
		}
		if("R1".equals(statisticsChannelRecord.getBankType())){
			statisticsChannelRecord.setBankType(null);
		}
		if(statisticsChannelRecord.getBeginStatisticTime()==null&&statisticsChannelRecord.getEndStatisticTime()==null){
			statisticsChannelRecord.setBeginStatisticTime(DateUtils.getYesterdayDate());
			statisticsChannelRecord.setEndStatisticTime(DateUtils.getYesterdayDate());
		}
		
		Page<StatisticsChannelRecord> page = statisticsChannelRecordService.findPage(new Page<StatisticsChannelRecord>(request, response), statisticsChannelRecord); 
		statisticsChannelRecord.setPage(null);
		List<StatisticsChannelRecord> noPageTotal = statisticsChannelRecordService.findList(statisticsChannelRecord);
		model.addAttribute("page", page);
		
		setTotalProperty(model,page);
		setNoPageTotal(model,noPageTotal);
		return "modules/payment/statisticsChannelRecordList";
	}
	@RequestMapping(value = "exportExcel")
	public void exportExcel(StatisticsChannelRecord statisticsChannelRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			if("R1".equals(statisticsChannelRecord.getChannelPartners())){
				statisticsChannelRecord.setChannelPartners(null);
			}
			if("R1".equals(statisticsChannelRecord.getBankCode())){
				statisticsChannelRecord.setBankCode(null);
			}
			if("R1".equals(statisticsChannelRecord.getChannelType())){
				statisticsChannelRecord.setChannelType(null);
			}
			if(statisticsChannelRecord.getBeginStatisticTime()==null && statisticsChannelRecord.getEndStatisticTime()==null){
				statisticsChannelRecord.setBeginStatisticTime(DateUtils.getYesterdayDate());
				statisticsChannelRecord.setEndStatisticTime(new Date());
			}
			
			statisticsChannelRecord.setPage(null);
			List<StatisticsChannelRecord> records = statisticsChannelRecordService.findList(statisticsChannelRecord);
			
			List<String[]> contents = new ArrayList<String[]>();
			String title = "渠道数据统计:";
			if(statisticsChannelRecord.getBeginStatisticTime()!=null && statisticsChannelRecord.getEndStatisticTime()!=null){
				title = "渠道数据统计:"+DateUtils.getDateStr(statisticsChannelRecord.getBeginStatisticTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(statisticsChannelRecord.getEndStatisticTime(), DateUtils.DATE_FORMAT_DATE);
			}
			String[] headers = new String[] { "通道合作方","银行名称", "支付通道类型", "总笔数", "总金额（元）", 
					"成功总笔数", "成功总金额（元）", "成功率", "失败总笔数",
					"失败总金额（元）","失败率", };
			
			for(StatisticsChannelRecord record:records){
				String[] content = new String[headers.length];
				content[0] = record.getChannelPartnersName();
				content[1] = record.getBankName();
				content[2] = record.getChannelTypeName();
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
	 * @description设置不分页总计值
	 * @author TianYanqing      
	 * @created 2017年1月16日 下午4:53:43     
	 * @param model
	 * @param page
	 */
	private void setNoPageTotal(Model model, List<StatisticsChannelRecord> records) {
		if(records==null||records.isEmpty()){
			return ;
		}
		int totalCount = 0;
		BigDecimal totalAmount = BigDecimal.ZERO;
		int sucessCount = 0;
		BigDecimal sucessAmount=BigDecimal.ZERO;
		int failCount = 0;
		BigDecimal failAmount=BigDecimal.ZERO;
		
		BigDecimal feeAmount=BigDecimal.ZERO;
		
		for(StatisticsChannelRecord record :records){
			totalCount+=Integer.parseInt(record.getTotalCount());
			totalAmount = totalAmount.add(new BigDecimal(record.getTotalAmount()));
			sucessCount+=Integer.parseInt(record.getSucessCount());
			sucessAmount = sucessAmount.add(new BigDecimal(record.getSucessAmount()));
			failCount+=Integer.parseInt(record.getFailCount());
			failAmount = failAmount.add(new BigDecimal(record.getFailAmount()));
			if(StringUtils.isNotBlank(record.getFeeAmount())){
				feeAmount = feeAmount.add(new BigDecimal(record.getFeeAmount()));
			}
		}
		model.addAttribute("pretotalCount",totalCount );
		model.addAttribute("pretotalAmount",totalAmount );
		model.addAttribute("presucessCount",sucessCount );
		model.addAttribute("presucessAmount",sucessAmount );
		model.addAttribute("prefailCount",failCount );
		model.addAttribute("prefailAmount",failAmount );
		model.addAttribute("prefeeAmount",feeAmount );
	}
	/**
	 * 
	 * @description设置总计值
	 * @author TianYanqing      
	 * @created 2017年1月16日 下午4:53:43     
	 * @param model
	 * @param page
	 */
	private void setTotalProperty(Model model, Page<StatisticsChannelRecord> page) {
		List<StatisticsChannelRecord> records = page.getList();
		if(records==null||records.isEmpty()){
			return ;
		}
		int totalCount = 0;
		BigDecimal totalAmount = BigDecimal.ZERO;
		int sucessCount = 0;
		BigDecimal sucessAmount=BigDecimal.ZERO;
		int failCount = 0;
		BigDecimal failAmount=BigDecimal.ZERO;
		BigDecimal feeAmount=BigDecimal.ZERO;
		
		for(StatisticsChannelRecord record :records){
			totalCount+=Integer.parseInt(record.getTotalCount());
			totalAmount = totalAmount.add(new BigDecimal(record.getTotalAmount()));
			sucessCount+=Integer.parseInt(record.getSucessCount());
			sucessAmount = sucessAmount.add(new BigDecimal(record.getSucessAmount()));
			failCount+=Integer.parseInt(record.getFailCount());
			failAmount = failAmount.add(new BigDecimal(record.getFailAmount()));
			String fee ="0.00" ;
			if(StringUtils.isNotBlank(record.getFeeAmount())){
				fee = record.getFeeAmount();
			}
			feeAmount = feeAmount.add(new BigDecimal(fee));
		}
		model.addAttribute("totalCount",totalCount );
		model.addAttribute("totalAmount",totalAmount );
		model.addAttribute("sucessCount",sucessCount );
		model.addAttribute("sucessAmount",sucessAmount );
		model.addAttribute("failCount",failCount );
		model.addAttribute("failAmount",failAmount );
		model.addAttribute("feeAmount",feeAmount );
	}

	@RequiresPermissions("payment:statisticsChannelRecord:view")
	@RequestMapping(value = "form")
	public String form(StatisticsChannelRecord statisticsChannelRecord, Model model) {
		model.addAttribute("statisticsChannelRecord", statisticsChannelRecord);
		return "modules/payment/statisticsChannelRecordForm";
	}

	@RequiresPermissions("payment:statisticsChannelRecord:edit")
	@RequestMapping(value = "save")
	public String save(StatisticsChannelRecord statisticsChannelRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, statisticsChannelRecord)){
			return form(statisticsChannelRecord, model);
		}
		statisticsChannelRecordService.save(statisticsChannelRecord);
		addMessage(redirectAttributes, "保存通道数据统计成功");
		return "redirect:"+Global.getAdminPath()+"/payment/statisticsChannelRecord/?repage";
	}
	
	@RequiresPermissions("payment:statisticsChannelRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(StatisticsChannelRecord statisticsChannelRecord, RedirectAttributes redirectAttributes) {
		statisticsChannelRecordService.delete(statisticsChannelRecord);
		addMessage(redirectAttributes, "删除通道数据统计成功");
		return "redirect:"+Global.getAdminPath()+"/payment/statisticsChannelRecord/?repage";
	}
	
	@RequiresPermissions("payment:statisticsChannelRecord:view")
	@RequestMapping(value = "inTimeStatistics")
	public String getStatisticsPerHour(StatisticsChannelRecord statisticsChannelRecord, HttpServletRequest request, HttpServletResponse response, Model model){
		
		/*		if("R1".equals(statisticsChannelRecord.getChannelPartners())){
		}
		if("R1".equals(statisticsChannelRecord.getBankCode())){
		}
		if("R1".equals(statisticsChannelRecord.getChannelType())){
		}*/
		statisticsChannelRecord.setChannelPartners(null);
		statisticsChannelRecord.setBankCode(null);
		statisticsChannelRecord.setChannelType(null);
		Date today = new Date();
		statisticsChannelRecord.setBeginStatisticTime(today);
		statisticsChannelRecord.setEndStatisticTime(today);
		
		statisticsChannelRecord.setPage(null);
		List<StatisticsChannelRecord> noPageTotal = statisticsChannelRecordService.findList(statisticsChannelRecord);		
		setStatisticsInTime(model,noPageTotal);
		
		return "modules/payment/statisticsPerHour";
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "getStatisticsInfo")
	public List<StatisticsChannelRecord> getStatisticsInfo(StatisticsChannelRecord statisticsChannelRecord, HttpServletRequest request, HttpServletResponse response, Model model){
		
		/*		if("R1".equals(statisticsChannelRecord.getChannelPartners())){
		}
		if("R1".equals(statisticsChannelRecord.getBankCode())){
		}
		if("R1".equals(statisticsChannelRecord.getChannelType())){
		}*/
		try {
			
			statisticsChannelRecord.setChannelPartners(null);
			statisticsChannelRecord.setBankCode(null);
			statisticsChannelRecord.setChannelType(null);
			
			String checkDate = request.getParameter("checkDate");
			//最近一天
			Date today = new Date();
			//最近一周
			Date week = DateUtils.getInternalDateByDay(today, -7);
			//最近一月
		//	Date month = DateUtils.getInternalTimeByMonth1(-1);
			
			Date formatToday = DateUtils.getStrDate(today,DateUtils.DATE_FORMAT_DATE);
			Date formatWeek = DateUtils.getStrDate(week, DateUtils.DATE_FORMAT_DATE);
		//	Date formatMonth = DateUtils.getStrDate(month, DateUtils.DATE_FORMAT_DATE);
			//结束日期为当日
			
			statisticsChannelRecord.setPage(null);
			List<StatisticsChannelRecord> statisticsRecords = new ArrayList<StatisticsChannelRecord>();
			if("day".equals(checkDate)){
				statisticsChannelRecord.setBeginStatisticTime(formatToday);
				statisticsChannelRecord.setEndStatisticTime(formatToday);
				statisticsRecords = statisticsChannelRecordService.findInTimeList(statisticsChannelRecord);		
				return statisticsRecords;
			}else if("week".equals(checkDate)){
				statisticsChannelRecord.setBeginStatisticTime(formatWeek);
				statisticsChannelRecord.setEndStatisticTime(formatToday);
				statisticsRecords = statisticsChannelRecordService.findTotalList(statisticsChannelRecord);
				return statisticsRecords;
			}else if("month".equals(checkDate)){
				for(int i=4 ; i>0 ;i--){
					Date begin = DateUtils.getInternalDateByDay(today, -7*i);
					Date end = DateUtils.getInternalDateByDay(today, -7*(i-1));
					statisticsChannelRecord.setBeginStatisticTime(begin);
					statisticsChannelRecord.setEndStatisticTime(end);
					List<StatisticsChannelRecord> records = statisticsChannelRecordService.findweekList(statisticsChannelRecord);
					if(records==null || records.isEmpty()){
						continue;
					}
					records.get(0).setWeek("第"+i+"周");
					statisticsRecords.add(records.get(0));
				}
						
				return statisticsRecords;
			}else{
				logger.error("统计图表参数传递错误");
				return null;
			}
			
		   
		} catch (Exception e) {
			logger.error("获取数据出错，错误原因{}",e.toString());
			
			return null;
		}
		
	}
	

/*	public static void main(String[] args)throws Exception {
		Date today = new Date();
		Date week = DateUtils.getInternalDateByDay(today, -7);
		//最近一月
		Date month = DateUtils.getInternalTimeByMonth1(-1);
		Date formatToday = DateUtils.getStrDate(today,DateUtils.DATE_FORMAT_DATE);
		
		System.out.println(DateUtils.getDateStr(today, DateUtils.DATE_FORMAT_DATE_TIME));
		System.out.println(DateUtils.getDateStr(month, DateUtils.DATE_FORMAT_DATE_TIME));
		System.out.println(DateUtils.getDateStr(week, DateUtils.DATE_FORMAT_DATE_TIME));
	}*/

	/**
	 * 
	 * @description图表数据
	 * @author TianYanqing      
	 * @created 2017年4月25日 上午11:41:15     
	 * @param model
	 * @param noPageTotal
	 */
	private void setStatisticsInTime(Model model, List<StatisticsChannelRecord> records) {
		if(records==null||records.isEmpty()){
			return ;
		}
		int totalCount = 0;
		BigDecimal totalAmount = BigDecimal.ZERO;
		int sucessCount = 0;
		BigDecimal sucessAmount=BigDecimal.ZERO;
		int failCount = 0;
		BigDecimal failAmount=BigDecimal.ZERO;
		
		for(StatisticsChannelRecord record :records){
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
		model.addAttribute("records",records );
		
	}
	

}