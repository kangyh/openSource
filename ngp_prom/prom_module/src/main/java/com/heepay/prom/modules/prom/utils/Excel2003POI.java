package com.heepay.prom.modules.prom.utils;

import com.heepay.prom.common.utils.Constants;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 
* 
* 描    述：
*
* 创 建 者： wangl
* 创建时间：  Dec 15, 20165:23:20 PM
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
public class Excel2003POI {

	public List<List<String>> readXls(String path) throws Exception{
		
		InputStream inputStream=null;
		HSSFWorkbook hSSFWorkbook=null;//标识整个excel
		List<List<String>> result=new ArrayList<>();
		
		try {
			inputStream=new  FileInputStream(path);
			hSSFWorkbook=new HSSFWorkbook(inputStream);//标识整个excel
			//循环每一页，并处理当前循环页
			for (int i = 0,length=hSSFWorkbook.getNumberOfSheets(); i < length; i++) {
				//HSSFSheet表示某一行
				HSSFSheet hSSFSheet= hSSFWorkbook.getSheetAt(i);
				if(hSSFSheet==null){
					continue;
				}
				//处理当前页，循环读取每一行
				for (int j = 0,lengt=hSSFSheet.getLastRowNum(); j < lengt; j++) {
					HSSFRow hSSFRow=hSSFSheet.getRow(j);//hssrow表示行
					int minCilIx=hSSFRow.getFirstCellNum();
					int maxCilIx=hSSFRow.getLastCellNum();
					
					List<String> list=new ArrayList<>();
					//循环遍历该行
					for(int colIx=minCilIx;colIx<maxCilIx;colIx++){
						HSSFCell cell=hSSFRow.getCell(colIx);
						if(cell==null){
							continue;
						}
						result.add(list);
					}
				}
			}
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			inputStream.close();
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2003(MultipartFile file) throws IOException{
		//返回结果集
		List<Map> valueList=new ArrayList<Map>();
		
        InputStream fis=null;
		try {
            fis=file.getInputStream();
			HSSFWorkbook wookbook = new HSSFWorkbook(fis);	// 创建对Excel工作簿文件的引用
			HSSFSheet sheet = wookbook.getSheetAt(0);	// 在Excel文档中，第一张工作表的缺省索引是0
			int rows = sheet.getPhysicalNumberOfRows();	// 获取到Excel文件中的所有行数­
			Map<Integer,String> keys=new HashMap<Integer, String>();
			int cells=0;
			// 遍历行­（第1行  表头） 准备Map里的key
			HSSFRow firstRow = sheet.getRow(0);
			if (firstRow != null) {
				// 获取到Excel文件中的所有的列
				cells = firstRow.getPhysicalNumberOfCells();
				// 遍历列
				for (int j = 0; j < cells; j++) {
					// 获取到列的值­
					try {
						HSSFCell cell = firstRow.getCell(j);
						String cellValue = getCellValue(cell);
						keys.put(j,cellValue);						
					} catch (Exception e) {
						e.printStackTrace();	
					}
				}
			}
			// 遍历行­（从第二行开始）
			for (int i = 1; i < rows; i++) {
				// 读取左上端单元格(从第二行开始)
				HSSFRow row = sheet.getRow(i);
				// 行不为空
				if (row != null) {
					//准备当前行 所储存值的map
					Map<String, Object> val=new HashMap<String, Object>();
					
					boolean isValidRow = false;
					
					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 获取到列的值­
						try {
							HSSFCell cell = row.getCell(j);
							String cellValue = getCellValue(cell);
							val.put(keys.get(j),cellValue);	
							if(!isValidRow && cellValue!=null && cellValue.trim().length()>0){
								isValidRow = true;
							}
						} catch (Exception e) {
							e.printStackTrace();		
						}
					}
					//第I行所有的列数据读取完毕，放入valuelist
					if(isValidRow){
						valueList.add(val);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            fis.close();
        }
        return valueList;
	}
	/**
	 * 读取个人/企业风险信息的Excel
	 * @方法说明：
	 * @时间：2017年3月15日
	 * @创建人：李震
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2003ForRiskInfo(MultipartFile file, int cusNature) throws IOException{
		//返回结果集
		List<Map> valueList=new ArrayList<Map>();
		
        InputStream fis=null;
		try {
            fis=file.getInputStream();
			HSSFWorkbook wookbook = new HSSFWorkbook(fis);	// 创建对Excel工作簿文件的引用
			HSSFSheet sheet = wookbook.getSheetAt(0);	// 在Excel文档中，第一张工作表的缺省索引是0
			int rows = sheet.getPhysicalNumberOfRows();	// 获取到Excel文件中的所有行数­
			Map<Integer,String> keys=new HashMap<Integer, String>();
			int cells=0;
			// 遍历行­（第1行  表头） 准备Map里的key
			HSSFRow firstRow = sheet.getRow(0);
			if (firstRow != null) {
				// 获取到Excel文件中的所有的列
				cells = firstRow.getPhysicalNumberOfCells();
				// 遍历列
				for (int j = 0; j < cells; j++) {
					// 获取到列的值­
					try {
						//HSSFCell cell = firstRow.getCell(j);
						//String cellValue = getCellValue(cell);
						if( cusNature == 1){//个人
							keys.put(j,Constants.PERSON_EXPORT[j]);
						}else{
							keys.put(j,Constants.MERCHANT_EXPORT[j]);
						}
					} catch (Exception e) {
						e.printStackTrace();	
					}
				}
			}
			// 遍历行­（从第二行开始）
			for (int i = 1; i < rows; i++) {
				// 读取左上端单元格(从第二行开始)
				HSSFRow row = sheet.getRow(i);
				// 行不为空
				if (row != null) {
					//准备当前行 所储存值的map
					Map<String, Object> val=new HashMap<String, Object>();
					
					boolean isValidRow = false;
					
					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 获取到列的值­
						try {
							HSSFCell cell = row.getCell(j);
							String cellValue = getCellValue(cell);
							val.put(keys.get(j),cellValue);	
							if(!isValidRow && cellValue!=null && cellValue.trim().length()>0){
								isValidRow = true;
							}
						} catch (Exception e) {
							e.printStackTrace();		
						}
					}
					//第I行所有的列数据读取完毕，放入valuelist
					if(isValidRow){
						valueList.add(val);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            fis.close();
        }
        return valueList;
	}
	
	/**
	 * 读取黑白名单明细信息的Excel
	 * @方法说明：
	 * @时间：2017年4月21日
	 * @创建人：李震
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2003ForBw(MultipartFile file) throws IOException{
		//返回结果集
		List<Map> valueList=new ArrayList<Map>();
		
        InputStream fis=null;
		try {
            fis=file.getInputStream();
			HSSFWorkbook wookbook = new HSSFWorkbook(fis);	// 创建对Excel工作簿文件的引用
			HSSFSheet sheet = wookbook.getSheetAt(0);	// 在Excel文档中，第一张工作表的缺省索引是0
			int rows = sheet.getPhysicalNumberOfRows();	// 获取到Excel文件中的所有行数­
			Map<Integer,String> keys=new HashMap<Integer, String>();
			int cells=0;
			// 遍历行­（第1行  表头） 准备Map里的key
			HSSFRow firstRow = sheet.getRow(0);
			if (firstRow != null) {
				// 获取到Excel文件中的所有的列
				cells = firstRow.getPhysicalNumberOfCells();
				// 遍历列
				for (int j = 0; j < cells; j++) {
					// 获取到列的值­
					try {
						//HSSFCell cell = firstRow.getCell(j);
						//String cellValue = getCellValue(cell);
							keys.put(j,Constants.BLACK_ITEM_VALUE[j]);

					} catch (Exception e) {
						e.printStackTrace();	
					}
				}
			}
			// 遍历行­（从第二行开始）
			for (int i = 1; i < rows; i++) {
				// 读取左上端单元格(从第二行开始)
				HSSFRow row = sheet.getRow(i);
				// 行不为空
				if (row != null) {
					//准备当前行 所储存值的map
					Map<String, Object> val=new HashMap<String, Object>();
					
					boolean isValidRow = false;
					
					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 获取到列的值­
						try {
							HSSFCell cell = row.getCell(j);
							String cellValue = getCellValue(cell);
							val.put(keys.get(j),cellValue);	
							if(!isValidRow && cellValue!=null && cellValue.trim().length()>0){
								isValidRow = true;
							}
						} catch (Exception e) {
							e.printStackTrace();		
						}
					}
					//第I行所有的列数据读取完毕，放入valuelist
					if(isValidRow){
						valueList.add(val);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            fis.close();
        }
        return valueList;
	}
	
	/**
	 * 
	 * @方法说明：众安推广导入订单
	 * @author wangjie
	 * @param file
	 * @param cusNature
	 * @return
	 * @throws IOException
	 * @时间：2017年9月14日下午2:57:17
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2003ForPromOrder(MultipartFile file) throws IOException{
		//返回结果集
		List<Map> valueList=new ArrayList<Map>();
		
        InputStream fis=null;
		try {
            fis=file.getInputStream();
			HSSFWorkbook wookbook = new HSSFWorkbook(fis);	// 创建对Excel工作簿文件的引用
			HSSFSheet sheet = wookbook.getSheetAt(0);	// 在Excel文档中，第一张工作表的缺省索引是0
			int rows = sheet.getPhysicalNumberOfRows();	// 获取到Excel文件中的所有行数­
			Map<Integer,String> keys=new HashMap<Integer, String>();
			int cells=0;
			// 遍历行­（第1行  表头） 准备Map里的key
			HSSFRow firstRow = sheet.getRow(0);
			if (firstRow != null) {
				// 获取到Excel文件中的所有的列
				cells = firstRow.getPhysicalNumberOfCells();
				// 遍历列
				for (int j = 0; j < cells; j++) {
					// 获取到列的值­
					try {
						//HSSFCell cell = firstRow.getCell(j);
						//String cellValue = getCellValue(cell);
							keys.put(j,PromConstants.PROM_ORDER[j]);

					} catch (Exception e) {
						e.printStackTrace();	
					}
				}
			}
			// 遍历行­（从第二行开始）
			for (int i = 1; i < rows; i++) {
				// 读取左上端单元格(从第二行开始)
				HSSFRow row = sheet.getRow(i);
				// 行不为空
				if (row != null) {
					//准备当前行 所储存值的map
					Map<String, Object> val=new HashMap<String, Object>();
					
					boolean isValidRow = false;
					
					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 获取到列的值­
						try {
							HSSFCell cell = row.getCell(j);
							String cellValue = getCellValue(cell);
							val.put(keys.get(j),cellValue);	
							if(!isValidRow && cellValue!=null && cellValue.trim().length()>0){
								isValidRow = true;
							}
						} catch (Exception e) {
							e.printStackTrace();		
						}
					}
					//第I行所有的列数据读取完毕，放入valuelist
					if(isValidRow){
						valueList.add(val);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            fis.close();
        }
        return valueList;
	}
	
	private static String getCellValue(HSSFCell cell) {
		DecimalFormat df = new DecimalFormat("#");
		String cellValue=null;
		if (cell == null)
			return null;
		switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				if(HSSFDateUtil.isCellDateFormatted(cell)){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					cellValue=sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
					break;
				}
				cellValue=df.format(cell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_STRING:
				cellValue=String.valueOf(cell.getStringCellValue());
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				cellValue=String.valueOf(cell.getCellFormula());
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				cellValue=null;
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				cellValue=String.valueOf(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				cellValue=String.valueOf(cell.getErrorCellValue());
				break;
		}
		if(cellValue!=null&&cellValue.trim().length()<=0){
			cellValue=null;
		}
		return cellValue;
	}
}
