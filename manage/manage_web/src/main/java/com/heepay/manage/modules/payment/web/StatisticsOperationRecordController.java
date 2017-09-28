/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.enums.ChannelPartner;
import com.heepay.enums.ChannelType;
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


/**
 * 
* 
* 描    述：运营报表
*
* 创 建 者： TianYanqing  
* 创建时间： 2017年8月8日 下午3:50:51 
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
@RequestMapping(value = "${adminPath}/payment/statisticsOperationRecord")
public class StatisticsOperationRecordController extends BaseController {

	@Autowired
	private StatisticsRecordService statisticsRecordService;
	@Autowired
	private StatisticsRecordDao statisticsRecordDao;
	
	private static Map<String,String> mheaders = new HashMap<String,String>();
	private static Map<String,String> mgroupBy = new HashMap<String,String>();
	private static Map<String,String> searchs = new HashMap<String,String>();

	static{
		mheaders.put("merchant","merchantId,merchantLoginName,merchantCompany");
		mheaders.put("channel", "channelPartners,channelType,bankName");
		mheaders.put("bank", "bankName");
		mheaders.put("bankCardType", "bankType");
		mheaders.put("transType", "transType");
		mheaders.put("product", "productCode");
		mheaders.put("payType", "payType");
		mheaders.put("operator", "operator");
		mheaders.put("all", "sucessCount,sucessAmount,feeAmount,settleAmount");
		/******************统计维度***************************/
		mgroupBy.put("merchant","merchant_id,merchant_login_name,merchant_company");
		mgroupBy.put("channel", "channel_partners,channel_type");
		mgroupBy.put("bank", "bank_name");
		mgroupBy.put("bankCardType", "bank_type");
		mgroupBy.put("transType", "trans_type");
		mgroupBy.put("product", "product_code");
		mgroupBy.put("payType", "pay_type");
		mgroupBy.put("operator", "operator");
		/******************查询字段***************************/
		searchs.put("merchant","merchant_id AS \"merchantId\",merchant_login_name AS \"merchantLoginName\",merchant_company AS \"merchantCompany\" ,");
		searchs.put("channel", "channel_partners AS \"channelPartners\",channel_type AS \"channelType\" ,");
		searchs.put("bank", "bank_name AS \"bankName\" ,");
		searchs.put("bankCardType", "bank_type AS \"bankType\" ,");
		searchs.put("transType", "trans_type AS \"transType\" ,");
		searchs.put("product", "product_code AS \"productCode\" ,");
		searchs.put("payType", "pay_type AS \"payType\" ,");
		searchs.put("operator", "operator ,");
	}
	
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
	
	
	
	
	@RequiresPermissions("payment:statisticsOperationRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(StatisticsRecord statisticsRecord, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {		
		
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
		
		//多选框，展示方式
		String[] groupType = request.getParameterValues("groupType");
		
		//渠道出现银行没出现标识
		boolean channelWithoutBank = isChannelOnly(groupType);
		//展示表头
		List<String> headers = new ArrayList<String>();
		//group by 方式
		StringBuffer groupBy=new StringBuffer();
		
		//要查的字段
		StringBuffer searchFields=new StringBuffer();
		//直连通道查询字段（如果按通道单独出现会用到）
		StringBuffer searchDirecoFields=new StringBuffer();		
		StringBuffer direcoGroupBy=new StringBuffer();
		
		//检测按通道是否单独出现
		StringBuffer channel = new StringBuffer();
		
		
		
		if(groupType==null||groupType.length==0){
			groupType = new String[]{"merchant"};
			StringBuffer sb = new StringBuffer();
			sb.append(mheaders.get("merchant")).append(",").append(mheaders.get("all"));
			String[] strHead = sb.toString().split(",");
			for(String str:strHead){
				headers.add(str);
			}
			groupBy.append(mgroupBy.get("merchant"));	
			searchFields.append(searchs.get("merchant"));
		}else{
			StringBuffer sb = new StringBuffer();
			for(String group:groupType){
				channel.append(group);
				channel.append(",");
				//设置表头
				sb.append(mheaders.get(group));
				sb.append(",");
				
				//设置分组方式
				if(StringUtils.isNotBlank(groupBy.toString())){
					groupBy.append(",");
				}
				groupBy.append(mgroupBy.get(group));
				searchFields.append(searchs.get(group));
			}
			//按通道合作方式与按银行没有同时出现时的设置
			if(channelWithoutBank){
				//按通道单独存在时，不显示通道编码
				searchDirecoFields = new StringBuffer(searchFields);
				direcoGroupBy = new StringBuffer(groupBy);
				
				/*searchFields = removeChannelCode(searchFields,true);
				groupBy = removeChannelCode(groupBy,false);*/
				searchDirecoFields.append(searchs.get("bank"));
				direcoGroupBy.append(",").append(mgroupBy.get("bank"));
				searchDirecoFields.append("sum(sucess_count) AS \"sucessCount\" ,");
				searchDirecoFields.append("sum(sucess_amount) AS \"sucessAmount\" ,");
				searchDirecoFields.append("sum(fee_amount) AS \"feeAmount\" ,");
				searchDirecoFields.append("sum(settle_amount) AS \"settleAmount\" ");
			}
			sb.append(mheaders.get("all"));
			for(String str:sb.toString().split(",")){
				if(headers.contains(str)){
					continue;
				}
				headers.add(str);
			}
			
		}
		//查询字段必含字段
		searchFields.append("sum(sucess_count) AS \"sucessCount\" ,");
		searchFields.append("sum(sucess_amount) AS \"sucessAmount\" ,");
		searchFields.append("sum(fee_amount) AS \"feeAmount\" ,");
		searchFields.append("sum(settle_amount) AS \"settleAmount\" ");

		
		//排序(如果是通道则按通道合作方，否则默认按成功金额排序)
		String orderBy = "sucessAmount";
		if(mheaders.containsKey("channel")){
			orderBy = "channel_partners";
		}
		String sortDirection="asc";
		
		
		if(StringUtils.isNotBlank(statisticsRecord.getSortDirection()) && !"R1".equals(statisticsRecord.getSortDirection())){
			sortDirection = statisticsRecord.getSortDirection();
		}
		if(StringUtils.isNotBlank(statisticsRecord.getSortType()) && !"R1".equals(statisticsRecord.getSortType())){
			orderBy = statisticsRecord.getSortType();
		}
		
		Page<StatisticsRecord> p = new Page<StatisticsRecord>(request, response);
		p.setOrderBy(orderBy+"  "+sortDirection);
		
		//设置查询字段和groupby 字段
		statisticsRecord.setSearchField(searchFields.toString());
		statisticsRecord.setGroupBy(groupBy.toString());
		
		Page<StatisticsRecord> page = null;
		
		//判断按通道出现的同时按银行没有出现
		if(channelWithoutBank){
			String channelPartners = statisticsRecord.getChannelPartners();

			if(StringUtils.isNotBlank(channelPartners)){
				if("DIRCON".equals(channelPartners)){
					statisticsRecord.setNotDirecoChannelPartners(null);
					statisticsRecord.setChannelPartners("DIRCON");
					statisticsRecord.setSearchField(searchDirecoFields.toString());
					statisticsRecord.setGroupBy(direcoGroupBy.toString());
					page =  statisticsRecordService.findOperationList(p,statisticsRecord);
				}else{
					//其他链接方式(除去直连)
					statisticsRecord.setNotDirecoChannelPartners("DIRCON");
					page =  statisticsRecordService.findOperationList(p, statisticsRecord);
					List<StatisticsRecord> otherList = page.getList();
					for(StatisticsRecord record:otherList){
						record.setBankName("--");
						//record.setChannelCode("--");
					}
				}	
				
			}else{
				
				
				//其他链接方式(除去直连)
				statisticsRecord.setNotDirecoChannelPartners("DIRCON");
				page =  statisticsRecordService.findOperationList(p, statisticsRecord);
				List<StatisticsRecord> otherList = page.getList();
				for(StatisticsRecord record:otherList){
					record.setBankName("--");
					//record.setChannelCode("--");
				}
				//设置查询条件为直连
				statisticsRecord.setNotDirecoChannelPartners(null);
				statisticsRecord.setChannelPartners("DIRCON");
				statisticsRecord.setSearchField(searchDirecoFields.toString());
				statisticsRecord.setGroupBy(direcoGroupBy.toString());
				List<StatisticsRecord> direcoList = statisticsRecordDao.findOperationList(statisticsRecord);
				otherList.addAll(direcoList);
				//设置前台查询条件为全部
				statisticsRecord.setChannelPartners(channelPartners);
			}
			
		}else{
			
			page =  statisticsRecordService.findOperationList(p, statisticsRecord);
		}
		
		
		StringBuffer group = new StringBuffer();
		for (int i = 0; i < groupType.length; i++) {
			if(StringUtils.isNotBlank(group.toString())){
				group.append(",");
			}
			group.append(groupType[i]);
		}
		
		model.addAttribute("headers", headers);
		model.addAttribute("groupType",group.toString());
		model.addAttribute("size", headers.size()-5);
		setTotalModel(model,page);
		model.addAttribute("page", page);
		
		return "modules/payment/statisticsOperationRecordList";
		
	}
	
	/**
	 * 
	 * @description去除channelcode
	 * @author TianYanqing      
	 * @created 2017年8月8日 下午4:34:15     
	 * @param searchFields
	 * @param searchFields2
	 */
	private StringBuffer removeChannelCode(StringBuffer searchFields,boolean isSearchFileds) {
		String[] temp = searchFields.toString().split(",");
		StringBuffer sb = new StringBuffer();
		for(String str:temp){
			if(str.indexOf("channel_code")!=-1){
				continue;
			}
			if(StringUtils.isNotBlank(sb.toString())){
				sb.append(",");
			}
			sb.append(str);
			
		}
		if(isSearchFileds){
			sb.append(",");
		}
		
		return sb;
	}




	/**
	 * 
	 * @description判断按通道是否出现并且按银行没有出现
	 * @author TianYanqing      
	 * @created 2017年8月8日 下午2:59:50     
	 * @param groupType
	 * @return
	 */
	private boolean isChannelOnly(String[] groupType) {
		boolean flag = false;
		if(groupType==null||groupType.length==0){
			return flag;
		}
		List<String> list= Arrays.asList(groupType);
		if(list.contains("channel") && !list.contains("bank")){
			flag = true;
		}
		return flag;
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
		if(page==null||page.getList()==null){
			return ;
		}
		List<StatisticsRecord> records = page.getList();
		int sucessCount = 0;
		BigDecimal sucessAmount = BigDecimal.ZERO;
		BigDecimal feeAmount = BigDecimal.ZERO;
		BigDecimal settleAmount = BigDecimal.ZERO;
		
		for(StatisticsRecord record:records){
			if(StringUtils.isNotBlank(record.getSucessCount())){
				sucessCount = sucessCount+Integer.parseInt(record.getSucessCount())
				;
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
			Map<String,String> mapHeader = getHeaderMap();
			
			
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
			
			//多选框，展示方式
			String groupParam = request.getParameter("groupType");
			String[] groupType = null;
			if(StringUtils.isNotBlank(groupParam)){
				groupType = groupParam.split(",");
			}
			
			//渠道出现银行没出现标识
			boolean channelWithoutBank = isChannelOnly(groupType);
			//展示表头
			List<String> headers = new ArrayList<String>();
			//group by 方式
			StringBuffer groupBy=new StringBuffer();
			
			//要查的字段
			StringBuffer searchFields=new StringBuffer();
			//直连通道查询字段（如果按通道单独出现会用到）
			StringBuffer searchDirecoFields=new StringBuffer();		
			StringBuffer direcoGroupBy=new StringBuffer();
			
			//检测按通道是否单独出现
			StringBuffer channel = new StringBuffer();
			
			
			
			if(groupType==null||groupType.length==0){
				groupType = new String[]{"merchant"};
				StringBuffer sb = new StringBuffer();
				sb.append(mheaders.get("merchant")).append(",").append(mheaders.get("all"));
				String[] strHead = sb.toString().split(",");
				for(String str:strHead){
					headers.add(str);
				}
				groupBy.append(mgroupBy.get("merchant"));	
				searchFields.append(searchs.get("merchant"));
			}else{
				StringBuffer sb = new StringBuffer();
				for(String group:groupType){
					channel.append(group);
					channel.append(",");
					//设置表头
					sb.append(mheaders.get(group));
					sb.append(",");
					
					//设置分组方式
					if(StringUtils.isNotBlank(groupBy.toString())){
						groupBy.append(",");
					}
					groupBy.append(mgroupBy.get(group));
					searchFields.append(searchs.get(group));
				}
				//按通道合作方式与按银行没有同时出现时的设置
				if(channelWithoutBank){
					//按通道单独存在时，不显示通道编码
					searchDirecoFields = new StringBuffer(searchFields);
					direcoGroupBy = new StringBuffer(groupBy);
					
					searchFields = removeChannelCode(searchFields,true);
					groupBy = removeChannelCode(groupBy,false);
					searchDirecoFields.append(searchs.get("bank"));
					direcoGroupBy.append(",").append(mgroupBy.get("bank"));
					searchDirecoFields.append("sum(sucess_count) AS \"sucessCount\" ,");
					searchDirecoFields.append("sum(sucess_amount) AS \"sucessAmount\" ,");
					searchDirecoFields.append("sum(fee_amount) AS \"feeAmount\" ,");
					searchDirecoFields.append("sum(settle_amount) AS \"settleAmount\" ");
				}
				sb.append(mheaders.get("all"));
				for(String str:sb.toString().split(",")){
					if(headers.contains(str)){
						continue;
					}
					headers.add(str);
				}
			}
			//查询字段必含字段
			searchFields.append("sum(sucess_count) AS \"sucessCount\" ,");
			searchFields.append("sum(sucess_amount) AS \"sucessAmount\" ,");
			searchFields.append("sum(fee_amount) AS \"feeAmount\" ,");
			searchFields.append("sum(settle_amount) AS \"settleAmount\" ");

			
			//排序(如果是通道则按通道合作方，否则默认按成功金额排序)
			String orderBy = "sucessAmount";
			if(mheaders.containsKey("channel")){
				orderBy = "channel_partners";
			}
			String sortDirection="asc";
			
			
			if(StringUtils.isNotBlank(statisticsRecord.getSortDirection()) && !"R1".equals(statisticsRecord.getSortDirection())){
				sortDirection = statisticsRecord.getSortDirection();
			}
			if(StringUtils.isNotBlank(statisticsRecord.getSortType()) && !"R1".equals(statisticsRecord.getSortType())){
				orderBy = statisticsRecord.getSortType();
			}
			
			Page<StatisticsRecord> p = new Page<StatisticsRecord>(request, response);
			p.setOrderBy(orderBy+"  "+sortDirection);
			statisticsRecord.setPage(null);
			
			//设置查询字段和groupby 字段
			statisticsRecord.setSearchField(searchFields.toString());
			statisticsRecord.setGroupBy(groupBy.toString());
			
			Page<StatisticsRecord> page = null;
			
			//判断按通道出现的同时按银行没有出现
			if(channelWithoutBank){
				String channelPartners = statisticsRecord.getChannelPartners();

				if(StringUtils.isNotBlank(channelPartners)){
					if("DIRCON".equals(channelPartners)){
						statisticsRecord.setNotDirecoChannelPartners(null);
						statisticsRecord.setChannelPartners("DIRCON");
						statisticsRecord.setSearchField(searchDirecoFields.toString());
						statisticsRecord.setGroupBy(direcoGroupBy.toString());
						page =  statisticsRecordService.findOperationList(p,statisticsRecord);
					}else{
						//其他链接方式(除去直连)
						statisticsRecord.setNotDirecoChannelPartners("DIRCON");
						page =  statisticsRecordService.findOperationList(p, statisticsRecord);
						List<StatisticsRecord> otherList = page.getList();
						for(StatisticsRecord record:otherList){
							record.setBankName("--");
							record.setChannelCode("--");
						}
					}	
					
				}else{
					
					
					//其他链接方式(除去直连)
					statisticsRecord.setNotDirecoChannelPartners("DIRCON");
					page =  statisticsRecordService.findOperationList(p, statisticsRecord);
					List<StatisticsRecord> otherList = page.getList();
					for(StatisticsRecord record:otherList){
						record.setBankName("--");
						record.setChannelCode("--");
					}
					//设置查询条件为直连
					statisticsRecord.setNotDirecoChannelPartners(null);
					statisticsRecord.setChannelPartners("DIRCON");
					statisticsRecord.setSearchField(searchDirecoFields.toString());
					statisticsRecord.setGroupBy(direcoGroupBy.toString());
					List<StatisticsRecord> direcoList = statisticsRecordDao.findOperationList(statisticsRecord);
					otherList.addAll(direcoList);
					//设置前台查询条件为全部
					statisticsRecord.setChannelPartners(channelPartners);
				}
			}else{
				
				page =  statisticsRecordService.findOperationList(p, statisticsRecord);
			}
		

			List<StatisticsRecord> statisticsRecords = null;
		
			List<String[]> contents = new ArrayList<String[]>();
			String title = "运营统计报表:";

			statisticsRecords = page.getList(); 			

			String[] excelHeaders = new String[headers.size()];
			
			for(int i=0;i<excelHeaders.length;i++){
				excelHeaders[i] = mapHeader.get(headers.get(i));
			}

			for(StatisticsRecord record:statisticsRecords){
				String[] content = new String[excelHeaders.length];
				for(int i=0;i<content.length;i++){
					content[i] =getValueOfObject(record,headers.get(i)) ;
				}
				contents.add(content);
			}

			title = title+DateUtils.getDateStr(statisticsRecord.getBeginStatisticsTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(statisticsRecord.getBeginStatisticsTime(), DateUtils.DATE_FORMAT_DATE);

			String fileName = "运营数据统计".concat(DateUtil.dateToString(new Date(), DateUtils.DATE_FORMAT_DATE));
			ExcelUtil2007.exportExcel(title, fileName, "sheet1", excelHeaders, response, contents);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}



	/**
	 * 
	 * @description反射获取值
	 * @author TianYanqing      
	 * @created 2017年8月9日 下午1:35:10     
	 * @param record
	 * @param string
	 * @return
	 */
	private String getValueOfObject(StatisticsRecord record, String field) {
		String value = "";
		try{
			Class clazz = (Class)record.getClass();
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			
			String valueOfField =String.valueOf(f.get(record));
			
			if(StringUtils.isBlank(valueOfField)){
				valueOfField = "";
			}
			
			if("channelPartners".equals(field)){
				value = ChannelPartner.labelOf(valueOfField);
				return value;
			}
			if("channelType".equals(field)){
				value = ChannelType.labelOf(valueOfField);
				return value;
			}
			if("transType".equals(field)){
				value = TransType.getContentByValue(valueOfField);
				return value;
			}
			if("productCode".equals(field)){
				value = ProductType.getContentByValue(valueOfField);
				return value;
			}
			if("payType".equals(field)){
				value = PayType.getContentByValue(valueOfField);
				return value;
			}
	
			if("sucessAmount".equals(field)||"feeAmount".equals(field)||"settleAmount".equals(field)){
				value = StringUtil.moneyFmt(valueOfField);
				return value;
			}
			value = valueOfField;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static void main(String[] args) throws Exception {
		
		StatisticsRecord record = new StatisticsRecord();
		record.setMerchantId(111111L);
		record.setSucessAmount("21.0000");
		
		Class clazz = (Class)record.getClass();
		Field f = clazz.getDeclaredField("merchantId");

		f.setAccessible(true);
		System.out.println(f.getName());
	}



	/**
	 * 
	 * @description获取下载的表头信息
	 * @author TianYanqing      
	 * @created 2017年8月9日 上午11:24:06     
	 * @return
	 */
	private Map<String,String> getHeaderMap() {
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("merchantId", "商户编码");
		map.put("merchantLoginName", "商户账号");
		map.put("merchantCompany", "商户公司名称");
		map.put("channelPartners", "通道合作方");
		//map.put("channelCode", "通道编码");
		map.put("channelType", "通道类型");
		map.put("bankName", "银行名称");
		map.put("bankType", "银行卡类型");
		map.put("productCode", "产品名称");
		map.put("payType", "支付类型");
		map.put("transType", "交易类型");
		map.put("operator", "维系员工");
		map.put("sucessCount", "交易笔数");
		map.put("sucessAmount", "成功交易金额");
		map.put("feeAmount", "手续费金额");
		map.put("settleAmount", "结算金额");
		return map;
	}
	
	

}