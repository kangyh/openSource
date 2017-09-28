/**     
*   Copyright © since 2008. All Rights Reserved 
*   汇元银通（北京）在线支付技术有限公司   www.heepay.com    
*/
    
package com.heepay.prom.modules.util;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**          
* 
* 描    述：excel工具类
*
* 创 建 者： 刘栋  
* 创建时间： 2016年11月14日 下午4:20:39 
* 创建描述：excel工具类
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

public class ExcelUtil {
	
	public static Workbook getExportWorkbook(String title, String[] colNames, List<String[]> tableList){
		
		Workbook workbook = new HSSFWorkbook();
		
		Sheet sheet = workbook.createSheet(title);

		Font titleFont = workbook.createFont();
		titleFont.setFontName("Courier New");
		titleFont.setFontHeightInPoints((short)18);
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setFont(titleFont);
		
		Row titleRow = sheet.createRow(0);
		Cell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(title);
		titleCell.setCellStyle(titleStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, colNames.length-1));
		
		Row headRow = sheet.createRow(2);
		for(int i=0; i<colNames.length; i++){
			headRow.createCell(i).setCellValue(colNames[i]);
		}
		
		for(int i=0; i<tableList.size(); i++){
			Row dataRow = sheet.createRow(i+3);
			for(int j=0; j<colNames.length; j++){
				dataRow.createCell(j).setCellValue(tableList.get(i)[j]);
			}
		}
		
		for(int i=0; i<colNames.length; i++){
			sheet.autoSizeColumn((short)i);
		}
		
		return workbook;
		
	}
	
}
