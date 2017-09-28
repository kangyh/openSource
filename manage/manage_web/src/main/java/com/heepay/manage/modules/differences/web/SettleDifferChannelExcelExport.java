package com.heepay.manage.modules.differences.web;


import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.enums.TransType;
import com.heepay.manage.common.utils.DictList;
import com.heepay.manage.common.utils.EnumBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.ChannelType;
import com.heepay.enums.billing.BillingCurrency;
import com.heepay.enums.billing.DifferErrorStatus;
import com.heepay.enums.billing.DifferencesBillType;
import com.heepay.enums.billing.SettleDifferCheckStatus;

/***
 * 
* 
* 描    述：通道差错账导出
*
* 创 建 者：wangl
* 创建时间：  Nov 15, 20162:47:27 PM
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
public class SettleDifferChannelExcelExport {

private static final Logger logger = LogManager.getLogger();
	
	/**
	 * 
	 * @方法说明：道差错账导出
	 * @时间：Nov 15, 2016
	 * @创建人：wangl
	 */
	@SuppressWarnings({"deprecation" })
	public void exportExcel(String fileName, String[] headers, List<Map<String, Object>> dataset,
			String[] showField,HttpServletRequest request, HttpServletResponse response){
		OutputStream out = null;
		logger.info("通道差错账下载开始------->",fileName, headers, dataset, showField);
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

			//支付通道类型枚举
			List<EnumBean> dataEntityChannelType = DictList.channelType();

	        int index = 0;
	        for(Map<String,Object> valMap: dataset) {
	            index++;
	            row = sheet.createRow(index);

	            for (short i = 0; i < showField.length; i++) {
	                HSSFCell cell = row.createCell(i);
	                //通道业务类型
					if(null != valMap.get(showField[i])){
						if(StringUtils.equals(showField[i], "errorStatus")){
							cell.setCellValue(DifferErrorStatus.labelOf(valMap.get(showField[i]).toString()));
						
						}else if(StringUtils.equals(showField[i].toString(), "billType")){
							cell.setCellValue(DifferencesBillType.labelOf(valMap.get(showField[i]).toString()));
						
						}else if(StringUtils.equals(showField[i].toString(), "currency")){
							cell.setCellValue(BillingCurrency.labelOf(valMap.get(showField[i]).toString()));
						
						}else if(StringUtils.equals(showField[i].toString(), "channelType")){
							/**
							 * RENAME
							 *
							 * 需要特殊翻译  成 “实名认证”
							 */
							String channelType = valMap.get(showField[i]).toString();
							if(channelType.equals("RENAME")){
								cell.setCellValue(TransType.labelOf(channelType));
							}else{
								//cell.setCellValue(ChannelType.labelOf(valMap.get(showField[i]).toString()));
								dataEntityChannelType.forEach(p -> {
									if(p.getValue().equals(channelType)){
										cell.setCellValue(p.getName());
									}

								});
							}
						
						}else if(StringUtils.equals(showField[i].toString(), "checkStatus")){
							cell.setCellValue(SettleDifferCheckStatus.labelOf(valMap.get(showField[i]).toString()));
						
						}else if(StringUtils.equals(showField[i].toString(), "errorDate")){
							cell.setCellValue(valMap.get(showField[i]).toString().replace("00:00:00.0", "").replace(".0", ""));
						
						}else if(StringUtils.equals(showField[i].toString(), "dealTime")){
							cell.setCellValue(valMap.get(showField[i]).toString().replace("00:00:00.0", "").replace(".0", ""));
						
						}else{
							cell.setCellValue(valMap.get(showField[i]).toString());
						}
					}else{
						cell.setCellValue(" ");
					}
	            }
	        }
	        workbook.write(out);
	        logger.info("通道差错账文件下载结束");
		}catch (IOException e) {
			logger.error("通道差错账文件下载错误",e);
        } catch (Exception e) {
        	logger.error("通道差错账文件下载错误",e);
		}finally {
        	try {
				out.flush();
				out.close();
			} catch (IOException e) {
				logger.error("通道差错账文件下载错误",e);
			}
		}
	}
}
