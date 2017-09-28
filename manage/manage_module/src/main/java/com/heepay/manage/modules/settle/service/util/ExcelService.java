package com.heepay.manage.modules.settle.service.util;

import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.PayType;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.*;
import com.heepay.enums.pbc.*;
import com.heepay.enums.pcac.*;
import com.heepay.enums.risk.RiskOrderDealType;
import com.heepay.enums.risk.RiskOrderStatus;
import com.heepay.manage.modules.reconciliation.util.ChannelTypeClear;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
/**
 * 
 *
 * 描    述：清结算系统文件导出公共方法
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月15日 上午9:28:19
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
@Component
public class ExcelService {
	
	private static final Logger logger=LogManager.getLogger();

	/**
	 * 
	 * @param flg 
	 * @方法说明：清结算系统文件导出方法
	 * @时间：2016年11月15日 上午9:29:07
	 * @创建人：wangdong
	 */
	@SuppressWarnings({"deprecation" })
	public void exportExcel(String fileName, String[] headers, List<Map<String, Object>> dataset,
			String[] showField,HttpServletResponse response, HttpServletRequest request, boolean flg) throws Exception{
		OutputStream out = null;
		try {
			out = response.getOutputStream();  
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			String sheetName = fileName;//sheet名称
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
			HSSFSheet sheet = workbook.createSheet(sheetName);
			
			//产生表格标题行
			HSSFRow row = sheet.createRow(0);
			for (short i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}
			
			int index = 0;
			String requestEventType = "";//存储请求事件类型
			//循环数据库中的数据信息
			for(Map<String,Object> valMap: dataset) {
				index++;
				row = sheet.createRow(index);
				//根据标题行循环数据库中的数据信息
				for (short i = 0; i < showField.length; i++) {
					/**
					 * 数据库的数据存在Map中，Map中的key就是字段，
					 * 下面中翻译字段就是对应key翻译该值，之后再根据
					 * 标题行所对应的字段作为key取value值。
					 */
					HSSFCell cell = row.createCell(i);
					//循环翻译部分字段
					if(null != valMap.get(showField[i])){
						//翻译部分字段
						if(flg){
							//反馈表
							requestEventType = translationField(showField, valMap, i, cell,requestEventType);
						}else{
							//其他
							translationField(showField, valMap, i, cell);
						}
						
					}else{
						//数据为空的显示
						cell.setCellValue("--");
					}
				}
			}
			workbook.write(out);
		}catch (IOException e) {
        	e.printStackTrace();
        	logger.error("ExcelController exportExcel has a error:{IO出错 FIND_ERROR}", e);
        	throw new Exception(e);
        } catch (Exception e) {
			e.printStackTrace();
			logger.error("ExcelController exportExcel has a error:{清结算系统文件导出公共方法出错 FIND_ERROR}", e);
			throw new Exception(e);
		}finally {
        	try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
	}
	
	
	
	/**
	 * 
	 * @param flg 
	 * @方法说明：清结算系统文件导出方法
	 * @时间：2016年11月15日 上午9:29:07
	 * @创建人：wangdong
	 */
	@SuppressWarnings({"deprecation" })
	public void exportToExcel(String fileName, String[] headers, List<Map<String, Object>> dataset,
			String[] showField,HttpServletResponse response, HttpServletRequest request, boolean flg,String[] total) throws Exception{
		OutputStream out = null;
		try {
			out = response.getOutputStream();  
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			String sheetName = fileName;//sheet名称
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
			HSSFSheet sheet = workbook.createSheet(sheetName);
			
			//产生表格标题行
			HSSFRow row = sheet.createRow(0);
			for (short i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}
			
			//设置其他值
			for(int j= headers.length;j<headers.length+5;j++){
				if(j==headers.length){
					HSSFCell cell = row.createCell(j);
					cell.setCellValue("统计时间");
				}
				if(j==headers.length+1){
					HSSFCell cell = row.createCell(j);
					cell.setCellValue("成功总笔数");
				}
				if(j==headers.length+2){
					HSSFCell cell = row.createCell(j);
					cell.setCellValue("成功总金额");
				}
				if(j==headers.length+3){
					HSSFCell cell = row.createCell(j);
					cell.setCellValue("已结算总金额");
				}
				if(j==headers.length+4){
					HSSFCell cell = row.createCell(j);
					cell.setCellValue("手续费总金额");
				}
			}
			
			int index = 0;
			String requestEventType = "";//存储请求事件类型
			//循环数据库中的数据信息
			for(Map<String,Object> valMap: dataset) {
				index++;
				row = sheet.createRow(index);
				//根据标题行循环数据库中的数据信息
				for (short i = 0; i < showField.length; i++) {
					/**
					 * 数据库的数据存在Map中，Map中的key就是字段，
					 * 下面中翻译字段就是对应key翻译该值，之后再根据
					 * 标题行所对应的字段作为key取value值。
					 */
					HSSFCell cell = row.createCell(i);
					
					//循环翻译部分字段
					if(null != valMap.get(showField[i])){
						//翻译部分字段
						if(flg){
							//反馈表
							requestEventType = translationField(showField, valMap, i, cell,requestEventType);
						}else{
							//其他
							translationField(showField, valMap, i, cell);
						}
						
					}else{
						//数据为空的显示
						cell.setCellValue("--");
					}
				}
				
			}
			
			if(dataset.size()>0){
				for(int j= showField.length;j<showField.length+5;j++){
					if(j==showField.length){
						HSSFCell cell = row.createCell(j);
						cell.setCellValue(total[0]);
					}
					if(j==showField.length+1){
						HSSFCell cell = row.createCell(j);
						cell.setCellValue(total[1]);
					}
					if(j==showField.length+2){
						HSSFCell cell = row.createCell(j);
						cell.setCellValue(total[2]);
					}
					if(j==showField.length+3){
						HSSFCell cell = row.createCell(j);
						cell.setCellValue(total[3]);
					}
					if(j==showField.length+4){
						HSSFCell cell = row.createCell(j);
						cell.setCellValue(total[4]);
					}
				}
			}
			workbook.write(out);
		}catch (IOException e) {
        	e.printStackTrace();
        	logger.error("ExcelController exportExcel has a error:{IO出错 FIND_ERROR}", e);
        	throw new Exception(e);
        } catch (Exception e) {
			e.printStackTrace();
			logger.error("ExcelController exportExcel has a error:{清结算系统文件导出公共方法出错 FIND_ERROR}", e);
			throw new Exception(e);
		}finally {
        	try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
	}
	/**
	 *  
	 * @方法说明：支付清算服务平台 ，个人/企业商户信息文件导出方法
	 * @时间：2017-3-15
	 * @创建人：李震
	 */
	public void exportCusExcel(String fileName, String[] headers, List<Map<String, Object>> dataset,
			String[] showField,HttpServletResponse response, HttpServletRequest request, boolean ifPerson) throws Exception{
		OutputStream out = null;
		try {
			out = response.getOutputStream();  
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			String sheetName = fileName;//sheet名称
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
			HSSFSheet sheet = workbook.createSheet(sheetName);
			
			//产生表格标题行
			HSSFRow row = sheet.createRow(0);
			HSSFCellStyle style =  workbook.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			for (int i = 0; i < headers.length; i++) {
				HSSFCell cell = row.createCell(i);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
				cell.setCellStyle(style);
				sheet.setColumnWidth(i, text.length()*512);
			}
			
			int index = 0;
			
			//循环数据库中的数据信息
			for(Map<String,Object> valMap: dataset) {
				index++;
				row = sheet.createRow(index);
				//根据标题行循环数据库中的数据信息
				for (int i = 0; i < showField.length; i++) {
					/**
					 * 数据库的数据存在Map中，Map中的key就是字段，
					 * 下面中翻译字段就是对应key翻译该值，之后再根据
					 * 标题行所对应的字段作为key取value值。
					 */
					
					HSSFCell cell = row.createCell(i);
					//循环翻译部分字段
					if(null != valMap.get(showField[i])){
						translationOtherField(showField, valMap, i, cell, ifPerson);
						//cell.setCellValue(valMap.get(showField[i]).toString());
					}else{
						//数据为空的显示
						cell.setCellValue("--");
					}
					if(index==1){
						if("docCode".equals(showField[i]) ||   "cusCode".equals(showField[i])  || "bankNo".equals(showField[i]) || "url".equals(showField[i])
								||"serverIp".equals(showField[i]) || "icp".equals(showField[i]) || "fillerTime".equals(showField[i]) ){
							sheet.setColumnWidth(i, cell.getStringCellValue().length()*256);
						}
					}
					cell.setCellStyle(style);
				}
			}
			workbook.write(out);
		}catch (IOException e) {
        	e.printStackTrace();
        	logger.error("ExcelController exportCusExcel has a error:{IO出错 FIND_ERROR}", e);
        	throw new Exception(e);
        } catch (Exception e) {
			e.printStackTrace();
			logger.error("ExcelController exportCusExcel has a error:{支付清算服务平台商户信息导出方法出错 FIND_ERROR}", e);
			throw new Exception(e);
		}finally {
        	try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
	}
	
	
	/**
	 * 
	 * @方法说明：翻译导出文件中需要翻译的字段
	 * @时间：2017-3-20
	 * @创建人：lizhen
	 */
	public void translationOtherField(String[] showField,  Map<String, Object> valMap, int i,
			HSSFCell cell,boolean ifPerson) throws Exception{
		try {
			if(StringUtils.equals(showField[i], "cusType")){
				cell.setCellValue(CusType.labelOf(valMap.get(showField[i]).toString()) );
			}else if(StringUtils.equals(showField[i], "cusNature")){
				cell.setCellValue(  CusNature.labelOf(valMap.get(showField[i]).toString()) );
			}else if(StringUtils.equals(showField[i].toString(), "docType")){
				if(ifPerson){
					cell.setCellValue(LegDocType.labelOf(valMap.get(showField[i]).toString()));
				}else{
					cell.setCellValue(DocType.labelOf(valMap.get(showField[i]).toString()));
				}
			}else if(StringUtils.equals(showField[i].toString(), "legDocType")){
				cell.setCellValue(LegDocType.labelOf(valMap.get(showField[i]).toString()));
			}else if(StringUtils.equals(showField[i].toString(), "status")){
				cell.setCellValue(PcacCusStatus.labelOf(valMap.get(showField[i]).toString()) );
			}else if(StringUtils.equals(showField[i].toString(), "riskStatus")){
				cell.setCellValue(RiskStatus.labelOf(valMap.get(showField[i]).toString()) );
			}else if(StringUtils.equals(showField[i].toString(), "openType")){
				cell.setCellValue(OpenType.labelOf(valMap.get(showField[i]).toString()) );
			}else if(StringUtils.equals(showField[i].toString(), "accountType")){
				cell.setCellValue(AccountType.labelOf(valMap.get(showField[i]).toString()) );
			}else if(StringUtils.equals(showField[i].toString(), "chageType")){
				cell.setCellValue(ChageType.labelOf(valMap.get(showField[i]).toString()) );
			}else if(StringUtils.equals(showField[i].toString(), "expandType")){
				cell.setCellValue(ExpandType.labelOf(valMap.get(showField[i]).toString()) );
			}else if(StringUtils.equals(showField[i].toString(), "networkType")){
				cell.setCellValue(NetworkType.labelOf(valMap.get(showField[i]).toString()) );
			}else if(StringUtils.equals(showField[i].toString(), "outServicelegCardType")){
				cell.setCellValue(LegDocType.labelOf(valMap.get(showField[i]).toString()) );
			}else if(StringUtils.equals(showField[i].toString(), "outServiceCardType")){
				cell.setCellValue(DocType.labelOf(valMap.get(showField[i]).toString()) );
			}else if(StringUtils.equals(showField[i].toString(), "regAddrProv")){
				cell.setCellValue(PcacProvince.labelOf(valMap.get(showField[i]).toString()) );
			}else if(StringUtils.equals(showField[i].toString(), "addrProv")){
				cell.setCellValue(PcacProvince.labelOf(valMap.get(showField[i]).toString()) );
			}else if(StringUtils.equals(showField[i].toString(), "level")){
				cell.setCellValue(RiskLevel.labelOf(valMap.get(showField[i]).toString()) );
			}else if(StringUtils.equals(showField[i].toString(), "riskType")){
				cell.setCellValue(MerchantRiskType.labelOf(valMap.get(showField[i]).toString()) );
			}else if(StringUtils.equals(showField[i].toString(), "validStatus")){
				cell.setCellValue(ValidStatus.labelOf(valMap.get(showField[i]).toString()) );
			}else{
				cell.setCellValue(valMap.get(showField[i]).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ExcelController exportExcel has a error:{清结算系统文件导出公共方法翻译字段报错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	
	/**
	 * 
	 * @方法说明：翻译导出文件中需要翻译的字段
	 * @时间：2016年11月22日 下午3:02:12
	 * @创建人：wangdong
	 */
	public void translationField(String[] showField, Map<String, Object> valMap, short i,
			HSSFCell cell) throws Exception{

		try {
			//通道业务类型
			if(StringUtils.equals(showField[i], "channelType")){
				cell.setCellValue(ChannelTypeClear.labelOf(valMap.get(showField[i]).toString()));
				//交易类型
			}else if(StringUtils.equals(showField[i], "transType")){
				cell.setCellValue(TransType.labelOf(valMap.get(showField[i]).toString()));
				//手续费扣除方式
			}else if(StringUtils.equals(showField[i].toString(), "feeWay")){
				cell.setCellValue(ChargeDeductType.labelOf(valMap.get(showField[i]).toString()));
				//币种
			}else if(StringUtils.equals(showField[i].toString(), "currency")){
				if(null != valMap.get(showField[i]) && StringUtils.isNotBlank(valMap.get(showField[i]).toString())){
					cell.setCellValue(BillingCurrency.labelOf(valMap.get(showField[i]).toString()));
				}else{
					//默认为人民币
					cell.setCellValue(BillingCurrency.CURRENCR.getContent());
				}
				//对账状态
			}else if(StringUtils.equals(showField[i].toString(), "checkStatus")){
				cell.setCellValue(ClearingCheckStatus.labelOf(valMap.get(showField[i]).toString()));
				//已对账状态
			}else if(StringUtils.equals(showField[i].toString(), "checkFlg")){
				if(StringUtils.equals(valMap.get(showField[i]).toString(), BillingYCheckStatus.BCFQSTS.getValue())){
					cell.setCellValue("平账");
				}else{
					//除了平账都是差异账（产品需求）
					cell.setCellValue("差异账");
				}
				//结算状态
			}else if(StringUtils.equals(showField[i].toString(), "settleStatus")){
				cell.setCellValue(BillingSettleStatus.labelOf(valMap.get(showField[i]).toString()));
				//结算周期
			}else if(StringUtils.equals(showField[i].toString(), "settleCyc")){
				if(StringUtils.equals(valMap.get(showField[i]).toString(), SettleCyc.SETTLECYC_T0.getValue()) || 
						StringUtils.equals(valMap.get(showField[i]).toString(), SettleCyc.SETTLECYC_T1.getValue())){
					cell.setCellValue(SettleCyc.labelOf(valMap.get(showField[i]).toString()));
				}else{
					cell.setCellValue(SettleCyc.SETTLECYC_T1.getContent());
				}
				//成本结算周期
			}else if(StringUtils.equals(showField[i].toString(), "costSettleCyc")){
				if(StringUtils.equals(valMap.get(showField[i]).toString(), Constants.Clear.T0)){
					cell.setCellValue(SettleCyc.SETTLECYC_T0.getValue());
				}else if(StringUtils.equals(valMap.get(showField[i]).toString(), Constants.Clear.T1)){
					cell.setCellValue(SettleCyc.SETTLECYC_T1.getValue());
				}
				//是否分润
			}else if(StringUtils.equals(showField[i].toString(), "isProfit")){
				cell.setCellValue(SettleDifferIsProfit.labelOf(valMap.get(showField[i]).toString()));
				//格式化 时间格式
			}else if(showField[i].toString().indexOf("Time")>-1){
				cell.setCellValue(valMap.get(showField[i]).toString().replace("00:00:00.0", "").replace(".0", ""));
				//手续费
			}else if(StringUtils.equals(showField[i].toString(), "fee")){
				//如果手续费扣除方式是外扣
				cell.setCellValue(valMap.get(showField[i]).toString());
				//风险订单处理方式
			}else if(StringUtils.equals(showField[i].toString(), "orderDealwith")){
				cell.setCellValue(RiskOrderDealType.labelOf(valMap.get(showField[i]).toString()));
				//风险订单状态
			}else if(StringUtils.equals(showField[i].toString(), "orderStatus")){
				cell.setCellValue(RiskOrderStatus.labelOf(valMap.get(showField[i]).toString()));
				//支付类型
			}else if(StringUtils.equals(showField[i].toString(), "payType")){
				cell.setCellValue(PayType.getContentByValue(valMap.get(showField[i]).toString()));
			}else{
				cell.setCellValue(valMap.get(showField[i]).toString());
			}
		} catch (Exception e) {
			logger.error("ExcelController exportExcel has a error:{清结算系统文件导出公共方法翻译字段报错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @方法说明：翻译导出文件中需要翻译的字段（反馈表）
	 * @时间：2016年11月22日 下午3:02:12
	 * @创建人：wangdong
	 */
	public String translationField(String[] showField,Map<String, Object> valMap, short i,
			HSSFCell cell, String requestEventType) throws Exception{
		try {
				//电信反欺诈系统   请求事件类型
			if(StringUtils.equals(showField[i].toString(), "request_event_type")){
				requestEventType = valMap.get(showField[i]).toString();
				cell.setCellValue(RequestEventType.labelOf(valMap.get(showField[i]).toString()));
				//电信反欺诈系统  参数类型
			}else if(StringUtils.equals(showField[i].toString(), "param_type")){
				if(StringUtils.isNotBlank(requestEventType)){
					if (StringUtils.equals(requestEventType, RequestEventType.CASE_REPORT.getValue())) {
						// 支付账户交易明细查询
						cell.setCellValue(PbcStaticParamType.labelOf(valMap.get(showField[i]).toString()));
					}else if (StringUtils.equals(requestEventType,RequestEventType.ABNORMAL_ACCOUNTS.getValue())) {
						// 账户主体详情查询
						cell.setCellValue(PbcStaticParamType.labelOf(valMap.get(showField[i]).toString()));
					}else if (StringUtils.equals(requestEventType,RequestEventType.ACCOUNTS_INVOLVED.getValue())) {
						// 账户动态查询
						cell.setCellValue(PbcDynamicParamType.labelOf(valMap.get(showField[i]).toString()));
					}else if (StringUtils.equals(requestEventType,RequestEventType.EXCEPTION_EVENTS.getValue())) {
						// 账户动态查询解除
					}else if (StringUtils.equals(requestEventType,RequestEventType.TRANSNO_ACCOUNT.getValue())) {
						// 按交易流水号查询银行卡/支付帐号
						cell.setCellValue(PbcTransCardParamType.labelOf(valMap.get(showField[i]).toString()));
					}else if (StringUtils.equals(requestEventType,RequestEventType.PAYMENY_ACCOUNT.getValue())) {
						// 关联全支付账号查询
						cell.setCellValue(PbcAccountParamType.labelOf(valMap.get(showField[i]).toString()));
					}
				}
				//电信反欺诈系统  状态
			}else if(StringUtils.equals(showField[i].toString(), "status")){
				cell.setCellValue(ReportStatus.labelOf(valMap.get(showField[i]).toString()));
				//电信反欺诈系统  风控状态
			}else if(StringUtils.equals(showField[i].toString(), "risk_status")){
				cell.setCellValue(RiskAuditStatus.labelOf(valMap.get(showField[i]).toString()));
				//电信反欺诈系统
			}else{
				cell.setCellValue(valMap.get(showField[i]).toString());
			}
		} catch (Exception e) {
			logger.error("ExcelController exportExcel has a error:{清结算系统文件导出公共方法翻译字段报错 FIND_ERROR}", e);
			throw new Exception(e);
		}
		return requestEventType;
	}
}
