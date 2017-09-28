package com.heepay.manage.modules.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.ctc.wstx.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


/**
 * 
* 
* 描    述：导出excel2007
*
* 创 建 者： 王亚洪  
* 创建时间： 2017年1月3日 下午4:15:54 
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
public class ExcelUtil2007 {


  /**
   * 
  * @description 创建excel
  * @author 王亚洪       
  * @created 2017年1月3日 下午4:16:59     
  * @param title
  * @param sheetName
  * @param excelHeader
  * @param list
  * @return
   */
  private static Workbook createWorkBoot(String title, String sheetName, String[] excelHeader, List<String[]> list) {
    Workbook workbook = new SXSSFWorkbook(1000);
    // 设置sheet的名字
    SXSSFSheet sheet = (SXSSFSheet) workbook.createSheet(sheetName);

    // excel中当前行索引
    int index = 0;
    /* 合并标题的单元格设置标题信息及样式 */
    if(StringUtils.isNotBlank(title)){
      sheet.addMergedRegion(new CellRangeAddress(index, index, index, excelHeader.length - 1));
      Row titleRow = sheet.createRow(index++);
      Cell titleCell = titleRow.createCell(0);
      titleCell.setCellValue(title);
    }

    /* 设置表格头信息及样式 */
    int len = excelHeader.length;
    Row headerRow = sheet.createRow(index++);
    for (int i = 0; i < len; i++) {
      Cell headerCell = headerRow.createCell(i);
      headerCell.setCellValue(excelHeader[i]);
    }
    
    /*
     * 设置每行每 列的值及样式 Row为行,cell为方格 创建i*j个方格并设置对应的属性值
     */
    for (int i = 0; i < list.size(); i++) {
      Row bodyRow = sheet.createRow(index++);
      for (int j = 0; j < len; j++) {
        Cell bodyCell = bodyRow.createCell(j);
        bodyCell.setCellValue(list.get(i)[j]);
      }
    }
    return workbook;
  }

  
  /**
   * 
  * @description 导出excel
  * @author 王亚洪       
  * @created 2017年1月3日 下午4:16:40     
  * @param title
  * @param fileName
  * @param sheetName
  * @param headers
  * @param response
  * @param list
  * @return
  * @throws IOException
   */
  public static boolean exportExcel(String title, String fileName, String sheetName, String[] headers, HttpServletResponse response, List<String[]> list) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      createWorkBoot(title, sheetName, headers, list).write(baos);
    } catch (IOException e) {
      e.printStackTrace();
    }
    byte[] content = baos.toByteArray();
    InputStream is = new ByteArrayInputStream(content);

    response.reset();
    response.setCharacterEncoding("utf-8");  
    response.setContentType("multipart/form-data"); 
    BufferedInputStream bis = null;
    BufferedOutputStream bos = null;
    try {
      response.setHeader("Content-Disposition",
          "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
      ServletOutputStream sos = response.getOutputStream();
      bis = new BufferedInputStream(is);
      bos = new BufferedOutputStream(sos);
      byte[] buff = new byte[2048];
      int byteRead = 0;
      while (-1 != (byteRead = bis.read(buff, 0, buff.length))) {
        bos.write(buff, 0, byteRead);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (bos != null)
        bos.close();
      if (bis != null)
        bis.close();
      if (is != null)
        is.close();
      if (baos != null)
        baos.close();
    }
    return true;
  }

}
