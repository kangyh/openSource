/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.enums.ChannelPartner;
import com.heepay.enums.PayType;
import com.heepay.enums.ProductType;
import com.heepay.enums.TransType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.DateUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.dao.StatisticsRecordDao;
import com.heepay.manage.modules.payment.entity.StatisticsRecord;
import com.heepay.manage.modules.payment.service.StatisticsRecordService;
import com.heepay.manage.modules.util.ExcelUtil2007;
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
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;


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
@RequestMapping(value = "${adminPath}/payment/statisticsRecordDate")
public class StatisticsRecordDateController extends BaseController {

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
		String groupTime ="";

		//对于时段报表,默认商户按天
		if(StringUtils.isBlank(groupType)){
			groupType = "day";
		}
		if(StringUtils.isBlank(statisticsRecord.getCountType())){
			statisticsRecord.setCountType("1");
		}

		if("day".equals(groupType)||"month".equals(groupType)||"year".equals(groupType)||"quarter".equals(groupType)){
			groupTime = groupType;
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
			List<String> heads = getHeads(statisticsRecord.getCountType(), groupTime, statisticsRecord);
			List<StatisticsRecord> records = null;
			Page<StatisticsRecord> page = new Page<StatisticsRecord>();
			if("day".equals(groupTime)){
				firstTitle = "按天\\按支付类型";
				//按商户
				if("1".equals(statisticsRecord.getCountType())){
					page = this.statisticsRecordService.findDayMerchantList(page, statisticsRecord);
				//按支付类型
				}else if("2".equals(statisticsRecord.getCountType())){
					page = this.statisticsRecordService.selectByDayAndPayTypePage(page, statisticsRecord);
				}else if("3".equals(statisticsRecord.getCountType())){
					page = this.statisticsRecordService.selectByDayAndProductCodePage(page, statisticsRecord);
				}else{
					page = this.statisticsRecordService.selectByDayPage(page, statisticsRecord);
				}
				records = page.getList();
			}else if("month".equals(groupTime)){
				firstTitle = "按月\\按支付类型";
				//按商户
				if("1".equals(statisticsRecord.getCountType())){
					page = this.statisticsRecordService.findMonthMerchantList(page, statisticsRecord);
				//按支付类型
				}else if("2".equals(statisticsRecord.getCountType())){
					page = this.statisticsRecordService.selectByMonthAndPayTypePage(page, statisticsRecord);
				}else if("3".equals(statisticsRecord.getCountType())){
					page = this.statisticsRecordService.selectByMonthAndProductCodePage(page, statisticsRecord);
				}else{
					page = this.statisticsRecordService.selectByMonthPage(page, statisticsRecord);
				}
				records = page.getList();
			}else if("quarter".equals(groupTime)){
				firstTitle = "按季度\\按支付类型";
				//按商户
				if("1".equals(statisticsRecord.getCountType())){
					page = this.statisticsRecordService.findMonthMerchantList(page, statisticsRecord);
				//按支付类型
				}else if("2".equals(statisticsRecord.getCountType())){
					page = this.statisticsRecordService.selectByMonthAndPayTypePage(page, statisticsRecord);
				}else if("3".equals(statisticsRecord.getCountType())){
					page = this.statisticsRecordService.selectByMonthAndProductCodePage(page, statisticsRecord);
				}else{
					page = this.statisticsRecordService.selectByMonthPage(page, statisticsRecord);
				}
				records = page.getList();
				
			}else{
				firstTitle = "按年\\按支付类型";
				//按商户
				if("1".equals(statisticsRecord.getCountType())){
					page = this.statisticsRecordService.findYearMerchantList(page, statisticsRecord);
				//按支付类型
				}else if("2".equals(statisticsRecord.getCountType())){
					page = this.statisticsRecordService.selectByYearAndPayTypePage(page, statisticsRecord);
				}else if("3".equals(statisticsRecord.getCountType())){
					page = this.statisticsRecordService.selectByYearAndProductCodePage(page, statisticsRecord);
				}else{
					page = this.statisticsRecordService.selectByYearPage(page, statisticsRecord);
				}
				records = page.getList();
			}
			List<Map<String,String>> finaceLists = null;
			if("1".equals(statisticsRecord.getCountType())){
				finaceLists =generateMerchantFinaces(records,groupTime);
			}else if("2".equals(statisticsRecord.getCountType())){
				finaceLists = generateFinaces(records,groupTime,"payType");
			}else if("3".equals(statisticsRecord.getCountType())){
				finaceLists = generateFinaces(records,groupTime,"productCode");
			}else{
				finaceLists = generateFinaces(records,groupTime,"transType");
			}
			page.setPageSize(30);
			model.addAttribute("page", page);
			if(finaceLists.size()>0){
				int pageNo=request.getParameter("pageNo")==null?1:Integer.valueOf(request.getParameter("pageNo"));
				int pageSize=request.getParameter("pageSize")==null?30:Integer.valueOf(request.getParameter("pageSize"));
				//对组装的数据进行分页
				Page resultPage=new Page<Map<String,String>>(pageNo,pageSize,finaceLists.size());
				resultPage.setList(finaceLists);
				finaceLists = generatePageFinaces(finaceLists, resultPage);
				model.addAttribute("page", resultPage);
			}

			model.addAttribute("firstTitle", firstTitle);
			model.addAttribute("head", heads);
			model.addAttribute("groupType", groupType);
			model.addAttribute("countType", statisticsRecord.getCountType());
			model.addAttribute("finaceLists", finaceLists);
		}

		return "modules/payment/statisticsRecordDateList";
	}
	

	//组合展示数据
	private List<Map<String, String>> generateFinaces(List<StatisticsRecord> records, String groupTime, String type) {
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		Map<String,Map<String,String>> maps = new HashMap<String,Map<String,String>>();
		String sortType = "";

		if("quarter".equals(groupTime)){
			for(StatisticsRecord record:records){
				if("transType".equals(type)){
					sortType = record.getTransType();
				}else if("payType".equals(type)){
					sortType = record.getPayType();
				}else if("productCode".equals(type)){
					sortType = record.getProductCode();
				}
				if(StringUtils.isBlank(record.getTime())||StringUtils.isBlank(sortType)){
					continue;
				}
	
				String quarter = getQuarter(record.getTime());
				if(maps.containsKey(quarter)){
					Map<String,String> map = maps.get(quarter);
					map.put(sortType, record.getSucessAmount());
					
					String totalAmount = map.get("totalAmount").toString();
					if(StringUtils.isNotBlank(record.getSucessAmount())){
						BigDecimal amount = (new BigDecimal(totalAmount)).add(new BigDecimal(record.getSucessAmount()));
						map.put("totalAmount", amount.toString());
					}
					
				}else{
					Map<String,String> map = new HashMap<String,String>();
					map.put(sortType, record.getSucessAmount());
					map.put("totalAmount", record.getSucessAmount()==null?"0.00":record.getSucessAmount());
					if(!map.containsKey("time")){
						map.put("time", quarter);
					}
					maps.put(quarter, map);
				}
			}
			
		}else{
			for(StatisticsRecord record:records){
				if("transType".equals(type)){
					sortType = record.getTransType();
				}else if("payType".equals(type)){
					sortType = record.getPayType();
				}else if("productCode".equals(type)){
					sortType = record.getProductCode();
				}
				if(StringUtils.isBlank(record.getTime())||StringUtils.isBlank(sortType)){
					continue;
				}
				if(maps.containsKey(record.getTime())){
					Map<String,String> map = maps.get(record.getTime());
					map.put(sortType, record.getSucessAmount());
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
					map.put(sortType, record.getSucessAmount());
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

	//组合展示数据（按商户选择日期）
	private List<Map<String, String>> generateMerchantFinaces(List<StatisticsRecord> records, String groupTime) {
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		Map<String,Map<String,String>> maps = new HashMap<String,Map<String,String>>();

		if("quarter".equals(groupTime)){
			for(StatisticsRecord record:records){
				if(StringUtils.isBlank(record.getTime())){
					continue;
				}

				String quarter = getQuarter(record.getTime());
				if(maps.containsKey(record.getMerchantId().toString())){
					Map<String,String> map = maps.get(record.getMerchantId().toString());
					if(map.containsKey(quarter)){
						BigDecimal amount = (new BigDecimal(map.get(quarter))).add(new BigDecimal(record.getSucessAmount()));
						map.put(quarter, amount.toString());
					}else{
						map.put(quarter, record.getSucessAmount());
					}
					String totalAmount = map.get("totalAmount").toString();
					if(StringUtils.isNotBlank(record.getSucessAmount())){
						BigDecimal amount = (new BigDecimal(totalAmount)).add(new BigDecimal(record.getSucessAmount()));
						map.put("totalAmount", amount.toString());
					}
				}else{
					Map<String,String> map = new HashMap<String,String>();
					map.put(quarter, record.getSucessAmount());
					map.put("merchantLoginName", record.getMerchantLoginName());
					map.put("merchantCompany", record.getMerchantCompany());
					map.put("totalAmount", record.getSucessAmount()==null?"0.00":record.getSucessAmount());
					if(!map.containsKey("merchantId")){
						map.put("merchantId", record.getMerchantId().toString());
					}
					maps.put(record.getMerchantId().toString(), map);
				}
			}
		}else{
			for(StatisticsRecord record:records){
				if(StringUtils.isBlank(record.getTime())){
					continue;
				}
					if(maps.containsKey(record.getMerchantId().toString())){
						Map<String,String> map = maps.get(record.getMerchantId().toString());
						if(map.containsKey(record.getTime())){
							BigDecimal amount = (new BigDecimal(map.get(record.getTime()))).add(new BigDecimal(record.getSucessAmount()));
							map.put(record.getTime(), amount.toString());
						}else{
							map.put(record.getTime(), record.getSucessAmount());
						}
						String totalAmount = map.get("totalAmount").toString();
						if(StringUtils.isNotBlank(record.getSucessAmount())){
							BigDecimal amount = (new BigDecimal(totalAmount)).add(new BigDecimal(record.getSucessAmount()));
							map.put("totalAmount", amount.toString());
						}
					}else{
						Map<String,String> map = new HashMap<String,String>();
						map.put(record.getTime(), record.getSucessAmount());
						map.put("merchantLoginName", record.getMerchantLoginName());
						map.put("merchantCompany", record.getMerchantCompany());
						map.put("totalAmount", record.getSucessAmount()==null?"0.00":record.getSucessAmount());
						if(!map.containsKey("merchantId")){
							map.put("merchantId", record.getMerchantId().toString());
						}
						maps.put(record.getMerchantId().toString(), map);
					}
			}
		}

		for(Map<String,String> map:maps.values()){
			results.add(map);
		}
		return results;
	}

	//组合展示数据（按商户选择日期）
	private List<Map<String, String>> generatePageFinaces(List<Map<String, String>> records, Page<StatisticsRecord> page) {
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		if(records!=null&&records.size()>0){
			int start=0;int end=0;
			start=(page.getPageNo()-1)*page.getPageSize();
			//判断是不是最后一页
			int lastPage=records.size()%page.getPageSize()==0?records.size()/page.getPageSize():records.size()/page.getPageSize()+1;
			if(page.getPageNo()==lastPage){
				end=records.size()-1;
			}else{
				end=start+page.getPageSize()-1;
			}
			for(int i=start;i<=end;i++){
				results.add(records.get(i));
			}
		}
		return results;
	}

	//获取表头
	private List<String> getHeads(String countType, String groupType,StatisticsRecord statisticsRecord){
		List<String> heads = new ArrayList<String>();
		//按商户,把时间加到表头去
		if("1".equals(countType)){
			heads.add("merchantId");
			heads.add("merchantLoginName");
			heads.add("merchantCompany");
			if("day".equals(groupType)){
				for(String str:statisticsRecordDao.findTableHeadByDay(statisticsRecord)){
					heads.add(str);
				}
			}else if("month".equals(groupType)){
				for(String str:statisticsRecordDao.findTableHeadByMonth(statisticsRecord)){
					heads.add(str);
				}
			}else if("quarter".equals(groupType)){
				for(String str:statisticsRecordDao.findTableHeadByMonth(statisticsRecord)){
					//转化成季度
					String quarter = getQuarter(str);
					if(!heads.contains(quarter))
					heads.add(quarter);
				}
			}else{
				for(String str:statisticsRecordDao.findTableHeadByYear(statisticsRecord)){
					heads.add(str);
				}
			}
			//按支付类型
		}else if("2".equals(countType)){
			heads = statisticsRecordDao.findTableHeadByPayType(statisticsRecord);
			//按产品类型
		}else if("3".equals(countType)){
			heads = statisticsRecordDao.findTableHeadByProductCode(statisticsRecord);
		}else{
			heads = statisticsRecordDao.findTableHead(statisticsRecord);
		}
		return heads;
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

	@RequestMapping(value = "exportExcel")
	public void exportExcel(StatisticsRecord statisticsRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String groupType = request.getParameter("groupType");

			//对于时段报表,默认商户按天
			if(StringUtils.isBlank(groupType)){
				groupType = "day";
			}
			if(StringUtils.isBlank(statisticsRecord.getCountType())){
				statisticsRecord.setCountType("1");
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

			
			
				List<String> heads = getHeads(statisticsRecord.getCountType(), groupType, statisticsRecord);
				if("day".equals(groupType)){
					if(!"1".equals(statisticsRecord.getCountType())){
						headers = new String[heads.size()+2];
						headers[0] = "按天";
					}else{
						headers = new String[heads.size()+1];
					}
					//按商户
					if("1".equals(statisticsRecord.getCountType())){
						title = "财务数据统计：按天-商户-";
						statisticsRecords = statisticsRecordDao.findDayMerchantList(statisticsRecord);
					//按支付类型
					}else if("2".equals(statisticsRecord.getCountType())){
						title = "财务数据统计：按天-支付类型-";
						statisticsRecords = statisticsRecordDao.selectByDayAndPayType(statisticsRecord);
					//按产品类型
					}else if("3".equals(statisticsRecord.getCountType())){
						title = "财务数据统计：按天-产品类型-";
						statisticsRecords = statisticsRecordDao.selectByDayAndProductCode(statisticsRecord);
					}else{
						title = "财务数据统计：按天-";
						statisticsRecords = statisticsRecordDao.selectByDay(statisticsRecord);
					}
				}else if("month".equals(groupType)){
					if(!"1".equals(statisticsRecord.getCountType())){
						headers = new String[heads.size()+2];
						headers[0] = "按月";
					}else{
						headers = new String[heads.size()+1];
					}
					//按商户
					if("1".equals(statisticsRecord.getCountType())){
						title = "财务数据统计：按月-商户-";
						statisticsRecords = statisticsRecordDao.findMonthMerchantList(statisticsRecord);
					//按支付类型
					}else if("2".equals(statisticsRecord.getCountType())){
						title = "财务数据统计：按月-支付类型-";
						statisticsRecords = statisticsRecordDao.selectByMonthAndPayType(statisticsRecord);
					//按产品类型
					}else if("3".equals(statisticsRecord.getCountType())){
						title = "财务数据统计：按月-产品类型-";
						statisticsRecords = statisticsRecordDao.selectByMonthAndProductCode(statisticsRecord);
					}else{
						title = "财务数据统计：按月-";
						statisticsRecords = statisticsRecordDao.selectByMonth(statisticsRecord);
					}
				}else if("quarter".equals(groupType)){
					if(!"1".equals(statisticsRecord.getCountType())){
						headers = new String[heads.size()+2];
						headers[0] = "按季度";
					}else{
						headers = new String[heads.size()+1];
					}
					//按商户
					if("1".equals(statisticsRecord.getCountType())){
						title = "财务数据统计：按季度-商户-";
						statisticsRecords = statisticsRecordDao.findMonthMerchantList(statisticsRecord);
					//按支付类型
					}else if("2".equals(statisticsRecord.getCountType())){
						title = "财务数据统计：按季度-支付类型-";
						statisticsRecords = statisticsRecordDao.selectByMonthAndPayType(statisticsRecord);
					//按产品类型
					}else if("3".equals(statisticsRecord.getCountType())){
						title = "财务数据统计：按季度-产品类型-";
						statisticsRecords = statisticsRecordDao.selectByMonthAndProductCode(statisticsRecord);
					}else{
						title = "财务数据统计：按季度-";
						statisticsRecords = statisticsRecordDao.selectByMonth(statisticsRecord);
					}
				}else if("year".equals(groupType)){
					if(!"1".equals(statisticsRecord.getCountType())){
						headers = new String[heads.size()+2];
						headers[0] = "按年";
					}else{
						headers = new String[heads.size()+1];
					}
					//按商户
					if("1".equals(statisticsRecord.getCountType())){
						title = "财务数据统计：按年-商户-";
						statisticsRecords = statisticsRecordDao.findYearMerchantList(statisticsRecord);
					//按支付类型
					}else if("2".equals(statisticsRecord.getCountType())){
						title = "财务数据统计：按年-支付类型-";
						statisticsRecords = statisticsRecordDao.selectByYearAndPayType(statisticsRecord);
					//按产品类型
					}else if("3".equals(statisticsRecord.getCountType())){
						title = "财务数据统计：按年-产品类型-";
						statisticsRecords = statisticsRecordDao.selectByYearAndProductCode(statisticsRecord);
					}else{
						title = "财务数据统计：按年-";
						statisticsRecords = statisticsRecordDao.selectByYear(statisticsRecord);
					}
				}
				List<Map<String,String>> finaceLists = null;
				if("1".equals(statisticsRecord.getCountType())){
					finaceLists = generateMerchantFinaces(statisticsRecords,groupType);
				}else if("2".equals(statisticsRecord.getCountType())){
					finaceLists = generateFinaces(statisticsRecords,groupType,"payType");
				}else if("3".equals(statisticsRecord.getCountType())){
					finaceLists = generateFinaces(statisticsRecords,groupType,"productCode");
				}else{
					finaceLists = generateFinaces(statisticsRecords,groupType,"transType");
				}
				int i = "1".equals(statisticsRecord.getCountType())?0:1;
				for(String head:heads){
					if("1".equals(statisticsRecord.getCountType())){
						headers[0]="商户编码";headers[1]="商户账号";headers[2]="商户公司名称";
						if(i>2){
							headers[i] = head;
						}
					}else if("2".equals(statisticsRecord.getCountType())){
						headers[i] = PayType.getContentByValue(head);
					}else if("3".equals(statisticsRecord.getCountType())){
						headers[i] = ProductType.getContentByValue(head);
					}else{
						headers[i] = TransType.getContentByValue(head);
					}
					i++;
				}
				if(!"1".equals(statisticsRecord.getCountType())){
					headers[heads.size()+1]="总金额";
				}else{
					headers[heads.size()]="总金额";
				}
				for(Map<String,String> map:finaceLists){
					String[] content = new String[headers.length];
					if(!"1".equals(statisticsRecord.getCountType())){
						content[0] = map.get("time").toString();
					}
					for(String key:map.keySet()){
						if("time".equals(key)||"totalAmount".equals(key)){
							continue;
						}
						if("1".equals(statisticsRecord.getCountType())){
							int m = heads.indexOf(key);
							if(m>2){
								content[m] = StringUtil.moneyFmt(map.get(key));
							}else if(m!=-1){
								content[m] = map.get(key);
							}
						}else{
							int m = heads.indexOf(key);
							content[m+1] = StringUtil.moneyFmt(map.get(key));
						}
					}
					
					content[headers.length-1]=StringUtil.moneyFmt(map.get("totalAmount").toString());
					contents.add(content); 
				}

		
			title = title+DateUtils.getDateStr(statisticsRecord.getBeginStatisticsTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(statisticsRecord.getEndStatisticsTime(), DateUtils.DATE_FORMAT_DATE);

			String fileName = "财务数据统计".concat(DateUtil.dateToString(new Date(), DateUtils.DATE_FORMAT_DATE));
			ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}