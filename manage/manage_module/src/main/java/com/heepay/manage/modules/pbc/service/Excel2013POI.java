package com.heepay.manage.modules.pbc.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.heepay.manage.common.utils.Constants;


/***
 * 
* 
* 描    述：
*
* 创 建 者： wangl
* 创建时间：  Dec 15, 20165:21:25 PM
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
public class Excel2013POI {

	public List<List<String>> readXlsx(MultipartFile file) throws Exception{
		
		List<List<String>> result=null;
		InputStream	inputStream=null;
		XSSFWorkbook xSSFWorkbook = null;
		try {
			//inputStream=new  FileInputStream(path);//"
				inputStream=file.getInputStream();
			 xSSFWorkbook = new XSSFWorkbook(inputStream);//标识整个excel
			result = new ArrayList<>();
			//循环每一页，并处理当前循环页
			for (XSSFSheet xssfSheet : xSSFWorkbook) {
				if(xssfSheet==null){
					continue;
				}
				//循环每一页，并处理当前循环页
				for (int rowNum = 1,length=xssfSheet.getLastRowNum(); rowNum <= length; rowNum++) {
					//HSSFSheet表示某一行
					XSSFRow xssfRow=xssfSheet.getRow(rowNum);
					
					int minCilIx=xssfRow.getFirstCellNum();
					int maxCilIx=xssfRow.getLastCellNum();
					List<String> list=new ArrayList<>();
					
					//循环遍历该行
					for(int colIx=minCilIx;colIx<maxCilIx;colIx++){
						XSSFCell cell=xssfRow.getCell(colIx);
						if(cell==null){
							continue;
						}
						result.add(list);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			inputStream.close();
		}
		
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2007(MultipartFile file) throws Exception{
		List<Map> valueList=new ArrayList<Map>();
		
        InputStream fis =null;
        try {
            fis =file.getInputStream();
            XSSFWorkbook xwb = new XSSFWorkbook(fis);	// 构造 XSSFWorkbook 对象，strPath 传入文件路径
            XSSFSheet sheet = xwb.getSheetAt(0);			// 读取第一章表格内容
            // 定义 row、cell
            XSSFRow row;
            // 循环输出表格中的第一行内容   表头
            Map<Integer, String> keys=new HashMap<Integer, String>();
            row = sheet.getRow(0);
            if(row !=null){
                //System.out.println("j = row.getFirstCellNum()::"+row.getFirstCellNum());
                //System.out.println("row.getPhysicalNumberOfCells()::"+row.getPhysicalNumberOfCells());
                for (int j = row.getFirstCellNum(); j <=row.getPhysicalNumberOfCells(); j++) {
                    // 通过 row.getCell(j).toString() 获取单元格内容，
                    if(row.getCell(j)!=null){
                        if(!row.getCell(j).toString().isEmpty()){
                            keys.put(j, row.getCell(j).toString());
                        }
                    }else{
                        keys.put(j, "K-R1C"+j+"E");
                    }
                }
            }
            // 循环输出表格中的从第二行开始内容
            for (int i = sheet.getFirstRowNum() + 1,length=sheet.getPhysicalNumberOfRows(); i <=length ; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    boolean isValidRow = false;
                    Map<String, Object> val = new HashMap<String, Object>();
                    for (int j = row.getFirstCellNum(),lengths=row.getPhysicalNumberOfCells(); j <= lengths; j++) {
                        XSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            String cellValue = null;
                            if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
                                if(DateUtil.isCellDateFormatted(cell)){
                                    cellValue = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-MM-dd HH:mm:ss");
                                }
                                else{
                                    cellValue = String.valueOf(cell.getNumericCellValue());
                                }
                            }
                            else{
                                cellValue = cell.toString();
                            }
                            if(cellValue!=null&&cellValue.trim().length()<=0){
                                cellValue=null;
                            }
                            val.put(keys.get(j), cellValue);
                            if(!isValidRow && cellValue!= null && cellValue.trim().length()>0){
                                isValidRow = true;
                            }
                        }
                    }

                    // 第I行所有的列数据读取完毕，放入valuelist
                    if (isValidRow) {
                        valueList.add(val);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            fis.close();
        }

        return valueList;
	}
	/**
	 * @方法说明：读取个人/企业风险信息的Excel
	 * @时间：2017年3月15日
	 * @创建人：李震
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2007ForRiskInfo(MultipartFile file,int cusNature) throws Exception{
		List<Map> valueList=new ArrayList<Map>();
		
        InputStream fis =null;
        try {
            fis =file.getInputStream();
            XSSFWorkbook xwb = new XSSFWorkbook(fis);	// 构造 XSSFWorkbook 对象，strPath 传入文件路径
            XSSFSheet sheet = xwb.getSheetAt(0);			// 读取第一章表格内容
            // 定义 row、cell
            XSSFRow row;
            // 循环输出表格中的第一行内容   表头
            Map<Integer, String> keys=new HashMap<Integer, String>();
            row = sheet.getRow(0);
            if(row !=null){
                for (int j = row.getFirstCellNum(); j <=row.getPhysicalNumberOfCells(); j++) {
                    // 通过 row.getCell(j).toString() 获取单元格内容，
                    if(row.getCell(j)!=null){
                        if(!row.getCell(j).toString().isEmpty()){
                            if( cusNature == 1){//个人
    							keys.put(j,Constants.PERSON_EXPORT[j]);
    						}else{
    							keys.put(j,Constants.MERCHANT_EXPORT[j]);
    						}
                        }
                    }else{
                        keys.put(j, "K-R1C"+j+"E");
                    }
                }
            }
            // 循环输出表格中的从第二行开始内容
            for (int i = sheet.getFirstRowNum() + 1,length=sheet.getPhysicalNumberOfRows(); i <=length ; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    boolean isValidRow = false;
                    Map<String, Object> val = new HashMap<String, Object>();
                    for (int j = row.getFirstCellNum(),lengths=row.getPhysicalNumberOfCells(); j <= lengths; j++) {
                        XSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            String cellValue = null;
                            if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
                                if(DateUtil.isCellDateFormatted(cell)){
                                    cellValue = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-MM-dd HH:mm:ss");
                                }
                                else{
                                    cellValue = String.valueOf(cell.getNumericCellValue());
                                }
                            }
                            else{
                                cellValue = cell.toString();
                            }
                            if(cellValue!=null&&cellValue.trim().length()<=0){
                                cellValue=null;
                            }
                            val.put(keys.get(j), cellValue);
                            if(!isValidRow && cellValue!= null && cellValue.trim().length()>0){
                                isValidRow = true;
                            }
                        }
                    }

                    // 第I行所有的列数据读取完毕，放入valuelist
                    if (isValidRow) {
                        valueList.add(val);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            fis.close();
        }

        return valueList;
	}
	/**
	 * @方法说明：读取黑白名单信息的Excel
	 * @时间：2017年4月21日
	 * @创建人：李震
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2007ForBw(MultipartFile file ) throws Exception{
		List<Map> valueList=new ArrayList<Map>();
		
        InputStream fis =null;
        try {
            fis =file.getInputStream();
            XSSFWorkbook xwb = new XSSFWorkbook(fis);	// 构造 XSSFWorkbook 对象，strPath 传入文件路径
            XSSFSheet sheet = xwb.getSheetAt(0);			// 读取第一章表格内容
            // 定义 row、cell
            XSSFRow row;
            // 循环输出表格中的第一行内容   表头
            Map<Integer, String> keys=new HashMap<Integer, String>();
            row = sheet.getRow(0);
            if(row !=null){
                for (int j = row.getFirstCellNum(); j <=row.getPhysicalNumberOfCells(); j++) {
                    // 通过 row.getCell(j).toString() 获取单元格内容，
                    if(row.getCell(j)!=null){
                        if(!row.getCell(j).toString().isEmpty()){

    							keys.put(j,Constants.BLACK_ITEM_VALUE[j]);

                        }
                    }else{
                        keys.put(j, "K-R1C"+j+"E");
                    }
                }
            }
            // 循环输出表格中的从第二行开始内容
            for (int i = sheet.getFirstRowNum() + 1,length=sheet.getPhysicalNumberOfRows(); i <=length ; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    boolean isValidRow = false;
                    Map<String, Object> val = new HashMap<String, Object>();
                    for (int j = row.getFirstCellNum(),lengths=row.getPhysicalNumberOfCells(); j <= lengths; j++) {
                        XSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            String cellValue = null;
                            if(cell.getCellType()==XSSFCell.CELL_TYPE_NUMERIC){
                                if(DateUtil.isCellDateFormatted(cell)){
                                    cellValue = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-MM-dd HH:mm:ss");
                                }
                                else{
                                    cellValue = String.valueOf(cell.getNumericCellValue());
                                }
                            }
                            else{
                                cellValue = cell.toString();
                            }
                            if(cellValue!=null&&cellValue.trim().length()<=0){
                                cellValue=null;
                            }
                            val.put(keys.get(j), cellValue);
                            if(!isValidRow && cellValue!= null && cellValue.trim().length()>0){
                                isValidRow = true;
                            }
                        }
                    }

                    // 第I行所有的列数据读取完毕，放入valuelist
                    if (isValidRow) {
                        valueList.add(val);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            fis.close();
        }

        return valueList;
	}
}
