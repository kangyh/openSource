/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.enums.ChannelPartner;
import com.heepay.enums.TransType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.DateUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.dao.StatisticsRecordDao;
import com.heepay.manage.modules.payment.entity.ChartRecord;
import com.heepay.manage.modules.payment.entity.StatisticsRecord;
import com.heepay.manage.modules.payment.service.StatisticsRecordService;
import com.heepay.manage.modules.util.ExcelUtil2007;


/**
 *
 * 描    述：财务统计报表Controller
 *
 * 创 建 者： @author tyq
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
@RequestMapping(value = "${adminPath}/payment/statisticsRecord")
public class StatisticsRecordController extends BaseController {

	@Autowired
	private StatisticsRecordService statisticsRecordService;
	@Autowired
	private StatisticsRecordDao statisticsRecordDao;
	
	@ModelAttribute
	public StatisticsRecord get(@RequestParam(required=false) String id) {
		StatisticsRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = statisticsRecordService.get(id);
		}
		if (entity == null){
			entity = new StatisticsRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:statisticsRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(StatisticsRecord statisticsRecord, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {		
		String groupType = request.getParameter("groupType");
		//String groupTime = request.getParameter("groupTime");
		String groupTime ="";

		if("day".equals(groupType)||"month".equals(groupType)||"year".equals(groupType)||"quarter".equals(groupType)){
			groupTime = groupType;
		}
		//默认按商户显示
		if(StringUtils.isBlank(groupType)){
			groupType = "3";
		}
		
		if("R1".equals(statisticsRecord.getAllowSystem())){
			statisticsRecord.setAllowSystem(null);
		}
		if("R1".equals(statisticsRecord.getTransType())){
			statisticsRecord.setTransType(null);
		}
		if("R1".equals(statisticsRecord.getProductCode())){
			statisticsRecord.setProductCode(null);
		}
		if("R1".equals(statisticsRecord.getPayType())){
			statisticsRecord.setPayType(null);
		}
		if("R1".equals(statisticsRecord.getChannelPartners())){
			statisticsRecord.setChannelPartners(null);
		}
		if("R1".equals(statisticsRecord.getBankCode())){
			statisticsRecord.setBankCode(null);
		}
		if("R1".equals(statisticsRecord.getBankType())){
			statisticsRecord.setBankType(null);
		}
		if(statisticsRecord.getBeginStatisticsTime()==null && statisticsRecord.getEndStatisticsTime()==null){
			statisticsRecord.setBeginStatisticsTime(DateUtils.getYesterdayDate());
			statisticsRecord.setEndStatisticsTime(DateUtils.getYesterdayDate());
		}
		
		//按时段统计
		if(StringUtils.isNotBlank(groupTime)){
			//表头
			String firstTitle = "";
			List<String> heads = statisticsRecordDao.findTableHead(statisticsRecord);
			List<StatisticsRecord> records = null;
			Page<StatisticsRecord> page = new Page<StatisticsRecord>(request, response);
			if("day".equals(groupTime)){
				firstTitle = "按天\\按支付类型";
				page = this.statisticsRecordService.selectByDayPage(page, statisticsRecord);
				records = page.getList();
			}else if("month".equals(groupTime)){
				firstTitle = "按月\\按支付类型";
				page = this.statisticsRecordService.selectByMonthPage(page, statisticsRecord);
				records = page.getList();
			}else if("quarter".equals(groupTime)){
				firstTitle = "按季度\\按支付类型";
				page = this.statisticsRecordService.selectByMonthPage(page, statisticsRecord);
				records = page.getList();
				
			}else{
				firstTitle = "按年\\按支付类型";
				page = this.statisticsRecordService.selectByYearPage(page, statisticsRecord);
				records = page.getList();
			}
			List<Map<String,String>> finaceLists = generateFinaces(records,groupTime);
			
			model.addAttribute("firstTitle", firstTitle);
			model.addAttribute("head", heads);
			model.addAttribute("groupType", groupType);
			model.addAttribute("finaceLists", finaceLists);
			model.addAttribute("page", page);
			
			return "modules/payment/statisticsRecordList";
		}
		
		
		
		//排序
		
		String orderBy = "sucessAmount";
		String sortDirection="asc";
		if(StringUtils.isNotBlank(statisticsRecord.getSortDirection()) && !"R1".equals(statisticsRecord.getSortDirection())){
			sortDirection = statisticsRecord.getSortDirection();
		}
		if(StringUtils.isNotBlank(statisticsRecord.getSortType()) && !"R1".equals(statisticsRecord.getSortType())){
			orderBy = statisticsRecord.getSortType();
		}
		
		Page<StatisticsRecord> p = new Page<StatisticsRecord>(request, response);
		p.setOrderBy(orderBy+"  "+sortDirection);
		
		Page<StatisticsRecord> page = null;
		if("1".equals(groupType)){
			 page = statisticsRecordService.findMerchantTransPage(p, statisticsRecord); 
			
		}else if("2".equals(groupType)){
			 page = statisticsRecordService.findTransPage(p, statisticsRecord); 
			 
		}else if("3".equals(groupType)){
			 page = statisticsRecordService.findMerchantPage(p, statisticsRecord); 
			 
		}else if("0".equals(groupType)){
			 page = statisticsRecordService.findChannelPage(p, statisticsRecord);
			 
		}
		
		//设置总计
		setTotalModel(model,page);
		model.addAttribute("page", page);
		model.addAttribute("groupType", groupType);
		return "modules/payment/statisticsRecordList";
	}
	

	//组合展示数据
	private List<Map<String, String>> generateFinaces(List<StatisticsRecord> records, String groupTime) {
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		Map<String,Map<String,String>> maps = new HashMap<String,Map<String,String>>();
		
		if("quarter".equals(groupTime)){
			for(StatisticsRecord record:records){
				if(StringUtils.isBlank(record.getTime())||StringUtils.isBlank(record.getTransType())){
					continue;
				}
	
				String quarter = getQuarter(record.getTime());
				if(maps.containsKey(quarter)){
					Map<String,String> map = maps.get(quarter);
					map.put(record.getTransType(), record.getSucessAmount());
					
					String totalAmount = map.get("totalAmount").toString();
					if(StringUtils.isNotBlank(record.getSucessAmount())){
						BigDecimal amount = (new BigDecimal(totalAmount)).add(new BigDecimal(record.getSucessAmount()));
						map.put("totalAmount", amount.toString());
					}
					
				}else{
					Map<String,String> map = new HashMap<String,String>();
					map.put(record.getTransType(), record.getSucessAmount());
					map.put("totalAmount", record.getSucessAmount()==null?"0.00":record.getSucessAmount());
					if(!map.containsKey("time")){
						map.put("time", quarter);
					}
					maps.put(quarter, map);
				}
			}
			
		}else{
			for(StatisticsRecord record:records){
				if(StringUtils.isBlank(record.getTime())||StringUtils.isBlank(record.getTransType())){
					continue;
				}
				if(maps.containsKey(record.getTime())){
					Map<String,String> map = maps.get(record.getTime());
					map.put(record.getTransType(), record.getSucessAmount());
	/*				if(!map.containsKey("time")){
						map.put("time", record.getTime());
					}*/
					String totalAmount = map.get("totalAmount").toString();
					if(StringUtils.isNotBlank(record.getSucessAmount())){
						BigDecimal amount = (new BigDecimal(totalAmount)).add(new BigDecimal(record.getSucessAmount()));
						map.put("totalAmount", amount.toString());
					}
					
				}else{
					Map<String,String> map = new HashMap<String,String>();
					map.put(record.getTransType(), record.getSucessAmount());
					map.put("totalAmount", record.getSucessAmount()==null?"0.00":record.getSucessAmount());
					if(!map.containsKey("time")){
						map.put("time", record.getTime());
					}
					maps.put(record.getTime(), map);
				}
			}
		}
		
		for(Map<String,String> map:maps.values()){
			results.add(map);
		}
		Collections.sort(results,new Comparator<Map>() {

			@Override
			public int compare(Map o1, Map o2) {
				String time = o1.get("time").toString();
				String time2 = o2.get("time").toString();
				time = getQuarterTime(time);
				time2 = getQuarterTime(time2);
				return time.compareTo(time2);
			}
			//转换
			private String getQuarterTime(String time) {
				if(time.indexOf("第一季度")!=-1){
					return "A"+time; 
				}
				if(time.indexOf("第二季度")!=-1){
					return "B"+time;
				}
				if(time.indexOf("第三季度")!=-1){
					return "C"+time;
				}
				if(time.indexOf("第四季度")!=-1){
					return "D"+time;
				}
				return time;
			}
		});
		
		return results;
	}
	
	
	 
	

	//获取第几季度
	private  String getQuarter(String time) {
	  String[] t = time.split("-");
	  int month = Integer.parseInt(t[1]);
	  String quarter  = "";
	  if(month>=1&&month<=3){
		  quarter ="年第一季度";
	  }else if(month>=4&&month<=6){
		  quarter ="年第二季度";
	  }else if(month>=7&&month<=9){
		  quarter ="年第三季度";
	  }else{
		  quarter ="年第四季度";
	  }
	  return t[0]+quarter;
	}
	
	/**
	 * 
	 * @description
	 * @author TianYanqing   
	 * @created 2017年6月28日 下午6:49:28     
	 * @param model
	 * @param page
	 */
	private void setTotalModel(Model model, Page<StatisticsRecord> page) {
		List<StatisticsRecord> records = page.getList();
		int sucessCount = 0;
		BigDecimal sucessAmount = BigDecimal.ZERO;
		BigDecimal feeAmount = BigDecimal.ZERO;
		BigDecimal settleAmount = BigDecimal.ZERO;
		
		for(StatisticsRecord record:records){
			if(StringUtils.isNotBlank(record.getSucessCount())){
				sucessCount = sucessCount+Integer.parseInt(record.getSucessCount());
			}
			if(StringUtils.isNotBlank(record.getSucessAmount())){
				sucessAmount = sucessAmount.add(new BigDecimal(record.getSucessAmount()));
			}
			if(StringUtils.isNotBlank(record.getFeeAmount())){
				feeAmount = feeAmount.add(new BigDecimal(record.getFeeAmount()));
			}
			if(StringUtils.isNotBlank(record.getSettleAmount())){
				settleAmount = settleAmount.add(new BigDecimal(record.getSettleAmount()));
			}
			
		}
		model.addAttribute("sucessCount", sucessCount);
		model.addAttribute("sucessAmount", sucessAmount);
		model.addAttribute("feeAmount", feeAmount);
		model.addAttribute("settleAmount", settleAmount);
		
	}

	
	@RequiresPermissions("payment:statisticsRecord:view")
	@RequestMapping(value = "form")
	public String form(StatisticsRecord statisticsRecord, Model model) {
		model.addAttribute("statisticsRecord", statisticsRecord);
		return "modules/payment/statisticsRecordForm";
	}

	@RequiresPermissions("payment:statisticsRecord:edit")
	@RequestMapping(value = "save")
	public String save(StatisticsRecord statisticsRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, statisticsRecord)){
			return form(statisticsRecord, model);
		}
		statisticsRecordService.save(statisticsRecord);
		addMessage(redirectAttributes, "保存财务统计报表成功");
		return "redirect:"+Global.getAdminPath()+"/payment/statisticsRecord/?repage";
	}
	
	@RequiresPermissions("payment:statisticsRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(StatisticsRecord statisticsRecord, RedirectAttributes redirectAttributes) {
		statisticsRecordService.delete(statisticsRecord);
		addMessage(redirectAttributes, "删除财务统计报表成功");
		return "redirect:"+Global.getAdminPath()+"/payment/statisticsRecord/?repage";
	}
	
	
	@RequestMapping(value = "exportExcel")
	public void exportExcel(StatisticsRecord statisticsRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String groupType = request.getParameter("groupType");
			
			//默认按商户显示
			if(StringUtils.isBlank(groupType)){
				groupType = "3";
			}
			
			if("R1".equals(statisticsRecord.getAllowSystem())){
				statisticsRecord.setAllowSystem(null);
			}
			if("R1".equals(statisticsRecord.getTransType())){
				statisticsRecord.setTransType(null);
			}
			if("R1".equals(statisticsRecord.getProductCode())){
				statisticsRecord.setProductCode(null);
			}
			if("R1".equals(statisticsRecord.getPayType())){
				statisticsRecord.setPayType(null);
			}
			if("R1".equals(statisticsRecord.getChannelPartners())){
				statisticsRecord.setChannelPartners(null);
			}
			if("R1".equals(statisticsRecord.getBankCode())){
				statisticsRecord.setBankCode(null);
			}
			if("R1".equals(statisticsRecord.getBankType())){
				statisticsRecord.setBankType(null);
			}

		
			statisticsRecord.setPage(null);
			
			List<StatisticsRecord> statisticsRecords = null;
			
			String[] headers = new String[] {};
			
			List<String[]> contents = new ArrayList<String[]>();
			String title = "财务数据统计:";

			
			
			if("1".equals(groupType)){
				 title = "财务数据统计:按商户+交易类型-";
				 statisticsRecords = statisticsRecordDao.findMerchantTransList(statisticsRecord); 			
				 headers = new String[] { "商户编码","商户账号", "商户公司","交易类型", "交易笔数(笔)", "成功交易金额(元)", 
							"手续费(元)", "结算金额(元)"};
	
					for(StatisticsRecord record:statisticsRecords){
						String[] content = new String[headers.length];
						content[0] = String.valueOf(record.getMerchantId());
						content[1] = record.getMerchantLoginName();
						content[2] = record.getMerchantCompany();
						content[3] = TransType.getContentByValue(record.getTransType());
						content[4] = record.getSucessCount();
						content[5] = StringUtil.moneyFmt(record.getSucessAmount());
						content[6]  = StringUtil.moneyFmt(record.getFeeAmount());
						content[7]  = StringUtil.moneyFmt(record.getSettleAmount());
						contents.add(content);
					}
			}else if("2".equals(groupType)){
				 title = "财务数据统计:按交易类型-";
				 statisticsRecords = statisticsRecordDao.findTransList(statisticsRecord); 
				 headers = new String[] { "交易类型", "交易笔数(笔)", "成功交易金额(元)", 
							"手续费(元)", "结算金额(元)"};
					
				for(StatisticsRecord record:statisticsRecords){
						String[] content = new String[headers.length];
						content[0] = TransType.getContentByValue(record.getTransType());
						content[1] = record.getSucessCount();
						content[2] = StringUtil.moneyFmt(record.getSucessAmount());
						content[3]  = StringUtil.moneyFmt(record.getFeeAmount());
						content[4]  = StringUtil.moneyFmt(record.getSettleAmount());
						contents.add(content);
					
				}
			}else if("3".equals(groupType)){
				title = "财务数据统计:按商户-";
				 statisticsRecords = statisticsRecordDao.findMerchantList(statisticsRecord); 
				 headers = new String[] { "商户编码","商户账号", "商户公司", "交易笔数(笔)", "成功交易金额(元)", 
							"手续费(元)", "结算金额(元)"};
				 for(StatisticsRecord record:statisticsRecords){
						String[] content = new String[headers.length];
						content[0] = String.valueOf(record.getMerchantId());
						content[1] = record.getMerchantLoginName();
						content[2] = record.getMerchantCompany();
						content[3] = record.getSucessCount();
						content[4] = StringUtil.moneyFmt(record.getSucessAmount());
						content[5]  = StringUtil.moneyFmt(record.getFeeAmount());
						content[6]  = StringUtil.moneyFmt(record.getSettleAmount());
						contents.add(content);
					}
			}else if("0".equals(groupType)){
				 title = "财务数据统计:按通道-";
				 statisticsRecords = statisticsRecordDao.findChannelList(statisticsRecord);
				 headers = new String[] {  "交易类型","通道提供者", "银行名称","通道编码", "交易笔数(笔)", "成功交易金额(元)", 
							"手续费(元)", "结算金额(元)"};	
					for(StatisticsRecord record:statisticsRecords){
						String[] content = new String[headers.length];
						content[0] = TransType.getContentByValue(record.getTransType());
						content[1] = ChannelPartner.labelOf(record.getChannelPartners());
						content[2] = record.getBankName();
						content[3] = record.getChannelCode();
						content[4] = record.getSucessCount();
						content[5] = StringUtil.moneyFmt(record.getSucessAmount());
						content[6]  = StringUtil.moneyFmt(record.getFeeAmount());
						content[7]  = StringUtil.moneyFmt(record.getSettleAmount());
						contents.add(content);
					}
			}else {
				List<String> heads = statisticsRecordDao.findTableHead(statisticsRecord);;
				if("day".equals(groupType)){
					title = "财务数据统计：按天-";
					statisticsRecords = statisticsRecordDao.selectByDay(statisticsRecord);
					headers = new String[heads.size()+2];
					headers[0] = "按天";
	
				}else if("month".equals(groupType)){
					title = "财务数据统计：按月-";
					statisticsRecords = statisticsRecordDao.selectByMonth(statisticsRecord);
					headers = new String[heads.size()+2];
					headers[0] = "按月";
				}else if("quarter".equals(groupType)){
					title = "财务数据统计：按季度-";
					statisticsRecords = statisticsRecordDao.selectByMonth(statisticsRecord);
					headers = new String[heads.size()+2];
					headers[0] = "按季度";
				}else if("year".equals(groupType)){
					title = "财务数据统计：按年-";
					statisticsRecords = statisticsRecordDao.selectByYear(statisticsRecord);
					headers = new String[heads.size()+2];
					headers[0] = "按年";
				}
				List<Map<String,String>> finaceLists = generateFinaces(statisticsRecords,groupType);
				int i = 1;
				for(String head:heads){
					headers[i] = TransType.getContentByValue(head);
					i++;
				}
				headers[heads.size()+1]="总金额";
				for(Map<String,String> map:finaceLists){
					String[] content = new String[headers.length];
					content[0] = map.get("time").toString();
					for(String key:map.keySet()){
						if("time".equals(key)||"totalAmount".equals(key)){
							continue;
						}
						int m = heads.indexOf(key);
						content[m+1] = StringUtil.moneyFmt(map.get(key));
					}
					
					content[headers.length-1]=StringUtil.moneyFmt(map.get("totalAmount").toString());
					contents.add(content); 
				}
				
				
				
			}
			
		
			title = title+DateUtils.getDateStr(statisticsRecord.getBeginStatisticsTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(statisticsRecord.getBeginStatisticsTime(), DateUtils.DATE_FORMAT_DATE);

			String fileName = "财务数据统计".concat(DateUtil.dateToString(new Date(), DateUtils.DATE_FORMAT_DATE));
			ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		List<String> heads = new ArrayList<String>();
		heads.add("CHARGE");
		heads.add("WZDRAW");
		heads.add("REFUND");
		String[] headers =new String[heads.size()];
		int i = 0;
		for(String head:heads){
			headers[i] = TransType.getContentByValue(head);
			i++;
		}
		for(String head:headers){
			System.out.println(head);
		}
	}

}