/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.*;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.service.MerchantUserCService;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.manage.modules.payment.entity.MerchantLog;
import com.heepay.manage.modules.payment.param.AccountSettleReportResult;
import com.heepay.manage.modules.payment.service.MerchantLogService;
import com.heepay.manage.modules.sys.utils.DictUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;


/**
 *
 * 描    述：账户明细查询Controller
 *
 * 创 建 者： @author yq
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
@RequestMapping(value = "${adminPath}/payment/accountSettleQuery")
public class MerchantLogReportController extends BaseController {

	@Autowired
	private MerchantLogService merchantLogService;

	@Autowired
	private MerchantUserCService merchantUserCService;

	@ModelAttribute
	public MerchantLog get(@RequestParam(required=false) String id) {
		MerchantLog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = merchantLogService.get(id);
		}
		if (entity == null){
			entity = new MerchantLog();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:accountSettleQuery:view")
	@RequestMapping(value = {"list", ""})
	public String list(MerchantLog merchantLog, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(null == merchantLog.getMerchantType()){
			merchantLog.setMerchantType(MerchantAccountType.MERCHANT_ACCOUNT.getValue());
		}
		if(null == merchantLog.getBeginCreateTime() && null == merchantLog.getEndCreateTime()){
			merchantLog.setBeginCreateTime(new Date());
			merchantLog.setEndCreateTime(new Date());
		}
		Map<String,List<AccountSettleReportResult>> listMap = query(merchantLog,request,response,model);
		String pageNo = request.getParameter("pageNo");
		if(StringUtil.isBlank(pageNo)){
			pageNo="1";
		}
		String pageSize = request.getParameter("pageSize");
		if(StringUtil.isBlank(pageSize)){
			pageSize= "30";
		}
		List<AccountSettleReportResult> takeOutList = new ArrayList<AccountSettleReportResult>();
		int start = 0;
		int end =0;
		List<AccountSettleReportResult> pageList = listMap.get("list");
		if(1 != Integer.parseInt(pageNo)){
			 start = (Integer.parseInt(pageNo)-1) * Integer.parseInt(pageSize);
		}
		end = start + Integer.parseInt(pageSize);
		if(end > pageList.size()){
			end = pageList.size();
		}
		if(start > end){
			start = 0;
			pageNo = "1";
		}
		takeOutList = pageList.subList(start,end);
		Page<AccountSettleReportResult> page = new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize), pageList.size());
		page.setList(takeOutList);
		model.addAttribute("page", page);
		Map<String,BigDecimal> cdMap = new HashMap<String,BigDecimal>();
		List<AccountSettleReportResult> accountSettleReportResultList = listMap.get("total");
		cdMap.put("D",accountSettleReportResultList.get(0).getAllAccountDeebitAmountSum());
		cdMap.put("C",accountSettleReportResultList.get(0).getAllAccountCrebitAmountSum());
		model.addAttribute("cdMap", cdMap);
		model.addAttribute("logserch",merchantLog);
		return "modules/payment/merchantLogReportList";
	}

	private Map<String,List<AccountSettleReportResult>> query(MerchantLog merchantLog, HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String,List<AccountSettleReportResult>> mainMap = new HashMap<String,List<AccountSettleReportResult>>();
		List<MerchantLog> merchantLogList = merchantLogService.findList(merchantLog);
		Map<String,AccountSettleReportResult> settleMap = new LinkedHashMap<String,AccountSettleReportResult>();
		for(MerchantLog mlog : merchantLogList){
			if(MerchantAccountType.INTERNAL_ACCOUNT.getValue().equals(mlog.getMerchantType())){
				continue;
			}
			String key = mlog.getMerchantId()+"-"+mlog.getType()+"-"+mlog.getBalanceDirection()+"-"+mlog.getAccountExplain();
			AccountSettleReportResult ar = null;
			if(settleMap.get(key)==null){
				ar = new AccountSettleReportResult();
				ar.setAccountId(String.valueOf(mlog.getAccountId()));
				ar.setAccountName(mlog.getAccountName());
				ar.setMerchantId(String.valueOf(mlog.getMerchantId()));
				ar.setMerchantName(mlog.getMerchantName());
				ar.setTransType(mlog.getType());
				ar.setMerchantType(mlog.getMerchantType());
				ar.setAccountExplain(String.valueOf(mlog.getAccountExplain()));
			}else{
				ar = settleMap.get(key);
			}
			if(BalanceDirection.CREBIT.getValue().equals(mlog.getBalanceDirection())){
				ar.setCrebitAmountSum(ar.getCrebitAmountSum().add(new BigDecimal(mlog.getBalanceAmountChanges())));
			}else{
				ar.setDeebitAmountSum(ar.getDeebitAmountSum().add(new BigDecimal(mlog.getBalanceAmountChanges())));
			}
			settleMap.put(key,ar);
		}
		Map<String,AccountSettleReportResult> tSettleMap = new LinkedHashMap<String,AccountSettleReportResult>();
		Iterator entries = settleMap.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			String key = (String)entry.getKey();
			AccountSettleReportResult vls = (AccountSettleReportResult)entry.getValue();
			String merchantId = key.split("-")[0];
			if(MerchantAccountType.MERCHANT_ACCOUNT.getValue().equals(merchantLog.getMerchantType())){
				if(StringUtil.notBlank(merchantLog.getMerchantSonType())){
					MerchantUser merchantUser = merchantUserCService.get(merchantId);
					if("2-1".equals(merchantLog.getMerchantSonType()) && "outsid".equals(merchantUser.getMerchantFlag())){
						tSettleMap.put(key,vls);
					}
					if("2-2".equals(merchantLog.getMerchantSonType()) && "inside".equals(merchantUser.getMerchantFlag())){
						tSettleMap.put(key,vls);
					}
					if(StringUtil.isBlank(merchantUser.getMerchantFlag())){
						tSettleMap.put(key,vls);
					}
				}else{
					tSettleMap.put(key,vls);
				}
			}else{
				tSettleMap.put(key,vls);
			}
		}
		List<AccountSettleReportResult> pageList = new ArrayList<AccountSettleReportResult>();
		BigDecimal dAmount = BigDecimal.ZERO;
		BigDecimal cAmount = BigDecimal.ZERO;
		for(AccountSettleReportResult accountSettleReportResult : tSettleMap.values()){
			pageList.add(accountSettleReportResult);
			cAmount = cAmount.add(accountSettleReportResult.getCrebitAmountSum());
			dAmount = dAmount.add(accountSettleReportResult.getDeebitAmountSum());
		}
		String sOrder = request.getParameter("sOrder");
		//D升序
		if(StringUtil.isBlank(sOrder) || "0".equals(sOrder)){
			model.addAttribute("sOrder", "0");
			Collections.sort(pageList, new Comparator<AccountSettleReportResult>() {
				@Override
				public int compare(AccountSettleReportResult o1, AccountSettleReportResult o2) {
					return o1.getDeebitAmountSum().compareTo(o2.getDeebitAmountSum());
				}
			});
		}
		//D降序
		if("1".equals(sOrder)){
			model.addAttribute("sOrder", "1");
			Collections.sort(pageList, new Comparator<AccountSettleReportResult>() {
				@Override
				public int compare(AccountSettleReportResult o1, AccountSettleReportResult o2) {
					return o2.getDeebitAmountSum().compareTo(o1.getDeebitAmountSum());
				}
			});
		}
		//C升序
		if("2".equals(sOrder)){
			model.addAttribute("sOrder", "2");
			Collections.sort(pageList, new Comparator<AccountSettleReportResult>() {
				@Override
				public int compare(AccountSettleReportResult o1, AccountSettleReportResult o2) {
					return o1.getCrebitAmountSum().compareTo(o2.getCrebitAmountSum());
				}
			});
		}
		//C降序
		if("3".equals(sOrder)){
			model.addAttribute("sOrder", "3");
			Collections.sort(pageList, new Comparator<AccountSettleReportResult>() {
				@Override
				public int compare(AccountSettleReportResult o1, AccountSettleReportResult o2) {
					return o2.getCrebitAmountSum().compareTo(o1.getCrebitAmountSum());
				}
			});
		}
		mainMap.put("list", pageList);
		AccountSettleReportResult accountSettleReportResult = new AccountSettleReportResult();
		accountSettleReportResult.setAllAccountDeebitAmountSum(dAmount);
		accountSettleReportResult.setAllAccountCrebitAmountSum(cAmount);
		mainMap.put("total", Collections.singletonList(accountSettleReportResult));
		return mainMap;
	}

	@RequiresPermissions("payment:accountSettleExport:view")
	@RequestMapping(value = "accountSettleExport")
	public void accountSettleExport(MerchantLog merchantLog, HttpServletRequest request, HttpServletResponse response,Model model) {
		Map<String,List<AccountSettleReportResult>> listMap = query(merchantLog,request,response,model);
		List<AccountSettleReportResult> pageList = listMap.get("list");
		List<AccountSettleReportResult> totalList = listMap.get("total");
		String[] headerArray = {"商户编码","公司名称","账号","账户名称","交易类型","借(支出)","贷(收入)","备注"};
		String[] showField = {"merchantId","accountName","accountId","merchantName","transType","deebitAmountSum","crebitAmountSum","accountExplain"};
		if(MerchantAccountType.INTERNAL_ACCOUNT.getValue().equals(merchantLog.getMerchantType())){
			headerArray[0]="";
			headerArray[1]="";
			headerArray[5]="借";
			headerArray[6]="贷";
			showField[0]="";
			showField[1]="";
		}
		try {
			exportExcel("账务结算报表", headerArray, pageList, totalList.get(0),showField, merchantLog,request, response);
		} catch (Exception e) {
			logger.error("ClearingChannelRecordController list has a error:", e);
		}
	}


	@RequiresPermissions("payment:accountSettleQuery:view")
	@RequestMapping(value = "form")
	public String form(MerchantLog merchantLog, Model model) {
		model.addAttribute("merchantLog", merchantLog);
		return "modules/payment/merchantLogForm";
	}

	public void exportExcel(String fileName, String[] headers, List<AccountSettleReportResult> list,AccountSettleReportResult totalResult,
							String[] showField,MerchantLog merchantLog,HttpServletRequest request, HttpServletResponse response){
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			String downName=fileName;//为下载的文件名和Sheet编码设置为UTF-8
			//区分IE浏览器和其他浏览器
			if (request.getHeader("User-Agent").contains("MSIE")||request.getHeader("User-Agent").contains("Trident")) {
				fileName = java.net.URLEncoder.encode((fileName + ".xls"), "UTF-8");
			} else {
				fileName = new String((fileName + ".xls").getBytes("UTF-8"),"ISO-8859-1");
			}
			response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
			// 声明一个工作薄
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 生成一个Sheet
			HSSFSheet sheet = workbook.createSheet(downName);
			//产生表格标题行
			HSSFRow row = sheet.createRow(0);
			if(MerchantAccountType.INTERNAL_ACCOUNT.getValue().equals(merchantLog.getMerchantType())){
				for (int i = 2; i < headers.length; i++) {
					if(StringUtil.notBlank(headers[i])){
						HSSFRichTextString text = new HSSFRichTextString(headers[i]);
						int j=i-2;
						HSSFCell cell = row.createCell(j);
						cell.setCellValue(text);
					}
				}
			}else{
				for (short i = 0; i < headers.length; i++) {
					if(StringUtil.notBlank(headers[i])){
						HSSFCell cell = row.createCell(i);
						HSSFRichTextString text = new HSSFRichTextString(headers[i]);
						cell.setCellValue(text);
					}
				}
			}

			int index = 0;
			for(AccountSettleReportResult accountSettleReportResult: list) {
				index++;
				row = sheet.createRow(index);
				for (short i = 0; i < showField.length; i++) {
					int j=i;
					if(MerchantAccountType.INTERNAL_ACCOUNT.getValue().equals(merchantLog.getMerchantType())){
						 j=i-2;
					}
						if(StringUtils.equals(showField[i], "merchantId")){
							HSSFCell cell = row.createCell(j);
							cell.setCellValue(accountSettleReportResult.getMerchantId());
						}else if(StringUtils.equals(showField[i].toString(), "accountName")){
							HSSFCell cell = row.createCell(j);
							cell.setCellValue(accountSettleReportResult.getAccountName());
						}else if(StringUtils.equals(showField[i].toString(), "accountId")){
							HSSFCell cell = row.createCell(j);
							cell.setCellValue(accountSettleReportResult.getAccountId());
						}else if(StringUtils.equals(showField[i].toString(), "merchantName")){
							HSSFCell cell = row.createCell(j);
							if(MerchantAccountType.INTERNAL_ACCOUNT.getValue().equals(merchantLog.getMerchantType())){
								cell.setCellValue(accountSettleReportResult.getAccountName());
							}else{
								cell.setCellValue(accountSettleReportResult.getMerchantName());
							}
						}else if(StringUtils.equals(showField[i].toString(), "transType")){
							HSSFCell cell = row.createCell(j);
							cell.setCellValue(DictUtils.getDictLabel(accountSettleReportResult.getTransType(), "TransType", accountSettleReportResult.getTransType()));
						}else if(StringUtils.equals(showField[i].toString(), "deebitAmountSum")){
							HSSFCell cell = row.createCell(j);
							cell.setCellValue(String.valueOf(accountSettleReportResult.getDeebitAmountSum()));
						}else if(StringUtils.equals(showField[i].toString(), "crebitAmountSum")){
							HSSFCell cell = row.createCell(j);
							cell.setCellValue(String.valueOf(accountSettleReportResult.getCrebitAmountSum()));
						}else if(StringUtils.equals(showField[i].toString(), "accountExplain")){
							HSSFCell cell = row.createCell(j);
							String accountExplain = DictUtils.getDictLabel(accountSettleReportResult.getAccountExplain(), "AccountExplain", accountSettleReportResult.getAccountExplain());
							if(StringUtil.isBlank(accountExplain)){
								cell.setCellValue("");

							}else{
								cell.setCellValue(accountExplain);
							}
						}
				}
			}
			index++;
			row = sheet.createRow(index);
			if(!MerchantAccountType.INTERNAL_ACCOUNT.getValue().equals(merchantLog.getMerchantType())){
				for (short i = 0; i < showField.length; i++) {
					HSSFCell cell = row.createCell(i);
					if(i==0){
						cell.setCellValue("总计:");
					}
					if(0< i && i < 5){
						cell.setCellValue("");
					}
					if(i == 5){
						cell.setCellValue(String.valueOf(totalResult.getAllAccountDeebitAmountSum()));
					}
					if(i == 6){
						cell.setCellValue(String.valueOf(totalResult.getAllAccountCrebitAmountSum()));
					}
					if(i == 7){
						cell.setCellValue("");
					}
				}
			}else{
				HSSFCell cell = row.createCell(0);
				cell.setCellValue("总计:");
				HSSFCell cell1 = row.createCell(1);
				cell1.setCellValue("");
				HSSFCell cell2 = row.createCell(2);
				cell2.setCellValue("");
				HSSFCell cell3 = row.createCell(3);
				cell3.setCellValue(String.valueOf(totalResult.getAllAccountDeebitAmountSum()));
				HSSFCell cell4 = row.createCell(4);
				cell4.setCellValue(String.valueOf(totalResult.getAllAccountCrebitAmountSum()));
				HSSFCell cell5 = row.createCell(5);
				cell5.setCellValue("");
			}
			workbook.write(out);
			logger.info("账务结算导出完成");
		}catch (IOException e) {
			logger.error("账务结算导出错误,{}",e);
		} catch (Exception e) {
			logger.error("账务结算导出错误,{}",e);
		}finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				logger.error("账务结算导出错误-,{}",e);
			}
		}
	}
}
