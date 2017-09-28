/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.ChartRecord;
import com.heepay.manage.modules.payment.entity.StatisticsChannelRecord;
import com.heepay.manage.modules.payment.entity.StatisticsRecord;
import com.heepay.manage.modules.payment.service.StatisticsChannelRecordService;
import com.heepay.manage.modules.payment.service.StatisticsRecordService;


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
@RequestMapping(value = "${adminPath}/payment/statisticsRecordPerHour")
public class StatisticsRecordPerHourController extends BaseController {

	@Autowired
	private StatisticsRecordService statisticsRecordService;
	@Autowired
	private StatisticsChannelRecordService statisticsChannelRecordService;
	

	
	@RequiresPermissions("payment:statisticsChannelRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(StatisticsRecord statisticsRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		statisticsRecord.setChannelPartners(null);
		statisticsRecord.setBankCode(null);
		statisticsRecord.setChannelType(null);
		Date today = new Date();
		statisticsRecord.setBeginStatisticsTime(today);
		statisticsRecord.setEndStatisticsTime(today);
		
		/*statisticsRecord.setPage(null);
		List<StatisticsRecord> noPageTotal = statisticsRecordService.findList(statisticsRecord);	*/	
		//setStatisticsInTime(model,noPageTotal);
		
		return "modules/payment/statisticsPerHour";
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
	public List<ChartRecord> getStatisticsInfo(StatisticsRecord statisticsRecord, HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			
/*			statisticsRecord.setChannelPartners(null);
			statisticsRecord.setBankCode(null);
			statisticsRecord.setChannelType(null);*/
			String begin = request.getParameter("beginStatisticsTime");
			String end = request.getParameter("endStatisticsTime");
			String merchantId = request.getParameter("merchantId");
			if(StringUtils.isNotBlank(merchantId)){
				statisticsRecord.setMerchantId(Long.parseLong(merchantId));
			}
			
			String channelPartners = request.getParameter("channelPartners");
			if("R1".equals(channelPartners)){
				channelPartners = null;
			}
			statisticsRecord.setChannelPartners(channelPartners);
			
			if(StringUtils.isNotBlank(begin)){
				statisticsRecord.setBeginStatisticsTime(DateUtils.getStr2Date(begin));
			}
			if(StringUtils.isNotBlank(end)){
				statisticsRecord.setEndStatisticsTime(DateUtils.getStr2Date(end));
			}
			
			String checkDate = request.getParameter("checkDate");
			
			statisticsRecord.setPage(null);
			List<StatisticsRecord> statisticsRecords = new ArrayList<StatisticsRecord>();
			List<ChartRecord> chartRecords = null;
			if("day".equals(checkDate)){
				statisticsRecord.setEndStatisticsTime(statisticsRecord.getBeginStatisticsTime());
				statisticsRecords = statisticsRecordService.findInTimeList(statisticsRecord);
				if(statisticsRecords!=null && !statisticsRecords.isEmpty()){
					chartRecords = generateRecords(statisticsRecords,"day");
				}
			}else if("week".equals(checkDate)){
				statisticsRecords = statisticsRecordService.findListByDay(statisticsRecord);
				if(statisticsRecords!=null && !statisticsRecords.isEmpty()){
					chartRecords = generateRecords(statisticsRecords,"week");
				}
			}else if("year".equals(checkDate)){
				statisticsRecords = statisticsRecordService.findListByMonth(statisticsRecord);
				if(statisticsRecords!=null && !statisticsRecords.isEmpty()){
					chartRecords = generateRecords(statisticsRecords,"year");
				}
			}else{
				statisticsRecords = statisticsRecordService.findListByDay(statisticsRecord);
				if(statisticsRecords!=null && !statisticsRecords.isEmpty()){
					chartRecords = generateRecords(statisticsRecords,"month");
				}
			}
			
			if(chartRecords!=null&&!chartRecords.isEmpty()){
				Collections.sort(chartRecords,new TimeCompare());
			}
			return chartRecords;
		   
		} catch (Exception e) {
			logger.error("获取数据出错，错误原因{}",e.toString());
			
			return null;
		}
		
	}
	
	 @SuppressWarnings("rawtypes")
	class TimeCompare implements Comparator {
		 private boolean flag=false;
		 public TimeCompare(){

		 }
		 
		@Override
		public int compare(Object o1, Object o2) {
			if(o1==null||o2==null){
				return 0;
			}
			ChartRecord record1 = (ChartRecord)o1;
			ChartRecord record2 = (ChartRecord)o2;
			Integer time1 = Integer.parseInt(record1.getTime().replaceAll("[\u4E00-\u9FA5]", ""));
			Integer time2 = Integer.parseInt(record2.getTime().replaceAll("[\u4E00-\u9FA5]", ""));

			return time1.compareTo(time2);
		}
		 
	 }
	 
	 public static void main(String[] args) {
		String str = "2016年03月";
		System.out.println(str.replaceAll("[\u4E00-\u9FA5]", ""));
	}
	 
	
/**
 * 
 * @description生成返回对象
 * @author TianYanqing      
 * @created 2017年7月12日 下午6:34:44     
 * @param statisticsRecords
 * @param records
 */
   private List<ChartRecord> generateRecords(List<StatisticsRecord> statisticsRecords,String type) {
	
	    Map<String, List<StatisticsRecord>> map = new HashMap<String, List<StatisticsRecord>>();
	    List<StatisticsRecord> list = null;
	    List<ChartRecord> charts = new ArrayList<ChartRecord>();
	    for(StatisticsRecord record:statisticsRecords){
	    	   if("day".equals(type)){
	    		   if(map.containsKey(record.getStatisticHour())){
	    			  list = map.get(record.getStatisticHour());
	    			  list.add(record);
	    		   }else{
	    			  list = new ArrayList<StatisticsRecord>();
	    			  list.add(record);
	    			  map.put(record.getStatisticHour(), list);
	    		   }
			   }else if("year".equals(type)){
				   if(map.containsKey(record.getMonth())){
		    			  list = map.get(record.getMonth());
		    			  list.add(record);
		    		  }else{
		    			  list = new ArrayList<StatisticsRecord>();
		    			  list.add(record);
		    			  map.put(record.getMonth(), list);
		    		  }
			   }else {
				   if(map.containsKey(record.getDay())){
					   list = map.get(record.getDay());
					   list.add(record);
				   }else{
					   list = new ArrayList<StatisticsRecord>();
					   list.add(record);
					   map.put(record.getDay(), list);
				   }
			   }
	    	
		}
	    
	    for(String key:map.keySet()){
	    	ChartRecord record = new ChartRecord();
	    	record.setTime(key);
	    	record.setRecords(map.get(key));
	    	charts.add(record);
	    }
	    
	    
	    return charts;
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