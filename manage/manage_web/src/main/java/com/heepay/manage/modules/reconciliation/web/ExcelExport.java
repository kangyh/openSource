package com.heepay.manage.modules.reconciliation.web;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.billing.BillingBecomeEffect;
import com.heepay.enums.billing.BillingChannelManageCheckFlg;
import com.heepay.enums.billing.BillingCheckStatus;
import com.heepay.manage.modules.reconciliation.util.ChannelTypeClear;
import com.heepay.manage.modules.reconciliation.web.util.RuleType;
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


/***
 * 
* 
* 描    述：通道管理批量查询导出
*
* 创 建 者：wangl
* 创建时间：  Nov 15, 201610:03:25 AM
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
public class ExcelExport {

	private static final Logger logger = LogManager.getLogger();
	/**
	 * 
	 * @方法说明：通道管理批量查询导出
	 * @时间：Nov 15, 2016
	 * @创建人：wangl
	 */
	@SuppressWarnings({"deprecation" })
	public void exportExcel(String fileName, String[] headers, List<Map<String, Object>> dataset,
			String[] showField,HttpServletRequest request, HttpServletResponse response){
		
		logger.info("通道管理下载开始------>",fileName, headers, dataset, showField);
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
	        for (short i = 0; i < headers.length; i++) {
	            HSSFCell cell = row.createCell(i);
	            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
	            cell.setCellValue(text);
	        }
	        int index = 0;
	        for(Map<String,Object> valMap: dataset) {
	            index++;
	            row = sheet.createRow(index);

	            for (short i = 0; i < showField.length; i++) {
	                HSSFCell cell = row.createCell(i);
	                //通道业务类型
					if(null != valMap.get(showField[i])){
						if(StringUtils.equals(showField[i], "channelType")){
							cell.setCellValue(ChannelTypeClear.labelOf(valMap.get(showField[i]).toString()));
						//规则类型
						}else if(StringUtils.equals(showField[i].toString(), "ruleType")){
							cell.setCellValue(RuleType.labelOf(valMap.get(showField[i]).toString()));
						//
						}else if(StringUtils.equals(showField[i].toString(), "effectFlg")){
						cell.setCellValue(BillingBecomeEffect.labelOf(valMap.get(showField[i]).toString()));
						//checkFlg
						}else if(StringUtils.equals(showField[i].toString(), "checkFlg")){
							cell.setCellValue(BillingChannelManageCheckFlg.labelOf(valMap.get(showField[i]).toString()));
						//checkStatus
						}else if(StringUtils.equals(showField[i].toString(), "checkStatus")){
							cell.setCellValue(BillingCheckStatus.labelOf(valMap.get(showField[i]).toString()));
						//创建时间
						}else if(StringUtils.equals(showField[i].toString(), "createTime")){
							cell.setCellValue(valMap.get(showField[i]).toString().replace("00:00:00.0", "").replace(".0", ""));
							
						//跟新时间
						}else if(StringUtils.equals(showField[i].toString(), "updateTime")){
							cell.setCellValue(valMap.get(showField[i]).toString().replace("00:00:00.0", "").replace(".0", ""));
							
						//checkStatus
						}else{
							cell.setCellValue(valMap.get(showField[i]).toString());
						}
					}else{
						cell.setCellValue(" ");
					}
	            }
	        }
	        workbook.write(out);
	        logger.info("通道管理导出下载错误----->{}");
		}catch (IOException e) {
			logger.error("通道管理导出下载错误----->{}",e);
        } catch (Exception e) {
        	logger.error("通道管理导出下载错误----->{}",e);
		}finally {
        	try {
				out.flush();
				out.close();
			} catch (IOException e) {
				logger.error("通道管理导出下载错误----->{}",e);
			}
		}
	}
}
