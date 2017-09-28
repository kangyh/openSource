package com.heepay.manage.common.importexcel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.heepay.billingutils.CreateDir;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.modules.pbc.entity.PbcAccountDetail;
import com.heepay.manage.modules.pbc.entity.PbcAccountInfo;
import com.heepay.manage.modules.pbc.entity.PbcBankInfo;
import com.heepay.manage.modules.pbc.entity.PbcMeasureInfo;
import com.heepay.manage.modules.pbc.entity.PbcPaymentAccountBack;
import com.heepay.manage.modules.pbc.entity.PbcReleaseFeedback;
import com.heepay.manage.modules.pbc.entity.PbcTransCardPaymentAccount;
import com.heepay.manage.modules.pbc.entity.PbcTransInfo;

/**
 * 
 *
 * 描    述：Excel表格解析
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年12月26日 下午3:24:21
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
public class ReadExcel {
	//2003
	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	//2007
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	public static final String EMPTY = "";
	public static final String POINT = ".";
	public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
	public static final String PROCESSING = "Processing...";

	protected static final Logger logger = LogManager.getLogger();
	
	public static String getPostfix(String path) {
		if (path == null || EMPTY.equals(path.trim())) {
			return EMPTY;
		}
		if (path.contains(POINT)) {
			return path.substring(path.lastIndexOf(POINT) + 1, path.length());
		}
		return EMPTY;
	}

	/**
	 * 读取Excel表格
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String,Object>> readExcel(MultipartFile file,PropertiesLoader loader, PbcTransInfo model) throws IOException {
		try {
			//把上传的文件保存在服务器中
			boolean flg = upLoadFile(file, loader);
			if(!flg){
				//上传文件失败
				return null;
			}
			//开始解析Excel入库
			String postfix = getPostfix(file.getOriginalFilename());
			if (!EMPTY.equals(postfix)) {
				if (OFFICE_EXCEL_2003_POSTFIX.equals(postfix)) {
					//return readPbcTransInfoXls(file, model);
				} else if (OFFICE_EXCEL_2010_POSTFIX.equals(postfix)) {
					return readPbcTransInfoXlsx(file, model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @方法说明：读取交易流水信息
	 * @时间：2016年12月26日 下午3:26:01
	 * @创建人：wangdong
	 */
	@SuppressWarnings("static-access")
	public static List<Map<String,Object>> readPbcTransInfoXlsx(MultipartFile file, PbcTransInfo model) throws IOException {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		try {
			InputStream is = file.getInputStream();
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			// Read the Sheet
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// Read the Row
				for (int rowNum = 3; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						Map<String,Object> map = new HashMap<String,Object>();
						for (int cellNum = 0; cellNum < xssfRow.getLastCellNum(); cellNum++) {
							if(null != xssfRow.getCell(cellNum)){
								Object values = null;
								if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_BOOLEAN) {
									values = String.valueOf(xssfRow.getCell(cellNum).getBooleanCellValue());
						        } else if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_NUMERIC) {
						        	if(HSSFDateUtil.isCellDateFormatted(xssfRow.getCell(cellNum))){
						        		 Date d = xssfRow.getCell(cellNum).getDateCellValue();
						        		 DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						        		 values = formater.format(d);
						        		 values = formater.parse((String) values);
						        	}else{
						        		values = String.valueOf(xssfRow.getCell(cellNum).getNumericCellValue());
						        	}
						        } else {
						        	values = String.valueOf(xssfRow.getCell(cellNum).getStringCellValue());
						        }
								setObject(map,model,cellNum,values);
							}
						}
						list.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Read the Excel 2003-2007
	 * @param <T>
	 * 
	 * @param path
	 *            the path of the Excel
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public static <T> List<T> readXls(MultipartFile file, T model) throws IOException {
		List<T> list = new ArrayList<T>();
		try {
			InputStream is = file.getInputStream();
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				// Read the Row
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow != null) {
						for(int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++){
							String values = null;
							if(null != hssfRow.getCell(cellNum)){
								if (hssfRow.getCell(cellNum).getCellType() == hssfRow.getCell(cellNum).CELL_TYPE_BOOLEAN) {
									values = String.valueOf(hssfRow.getCell(cellNum).getBooleanCellValue());
						        } else if (hssfRow.getCell(cellNum).getCellType() == hssfRow.getCell(cellNum).CELL_TYPE_NUMERIC) {
									values = String.valueOf(hssfRow.getCell(cellNum).getNumericCellValue());
								} else {
									values = String.valueOf(hssfRow.getCell(cellNum).getStringCellValue());
								}
								//model = setObject(model,cellNum,values);
							}
						}
						list.add(model);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 
	 * @方法说明：（交易流水）根据实体赋值
	 * @时间：2016年12月24日 下午4:29:22
	 * @创建人：wangdong
	 */
	public static void setObject(Map<String,Object> map,PbcTransInfo model,int i,Object values)throws Exception{
		Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
        	int num = i+2;//赋值从主键下一个属性开始
            String name = field[num].getName(); // 获取属性的名字
            map.put(name, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 
	 * @方法说明：上传文件
	 * @时间：2016年12月23日 下午3:16:53
	 * @创建人：wangdong
	 */
	public static boolean upLoadFile(MultipartFile file,PropertiesLoader loader) throws Exception{
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			if (!file.isEmpty()) {
				is=file.getInputStream();
				String lastIndex="";
				String filename="";
				String path = loader.getProperty("localPath");
				String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				//创建上传文件的路径    
				path=path+"/"+dateFormat+"/";
				boolean flag = CreateDir.createLiunxDir(path);
				if(!flag){
	        		logger.error("路径创建失败-------->{}",!flag);
					return false;
	        	}
				filename = file.getOriginalFilename();
	        	long size = file.getSize();
	        	//根据截取的值是否是-1判断是否是组合命名
	        	int of = filename.lastIndexOf(".");
	        	if(of != -1){
					lastIndex = filename.substring(of);
					//判断这个是不是标准的Excel表格
					filename ="电信反诈骗  "+file.getOriginalFilename()+dateFormat+System.currentTimeMillis()+lastIndex;
					logger.error("路径创建名称-------->{}",filename);
				}else {
					logger.error("文件名称错误-------->{}",filename);
					return false;
				}
	        	//最终生成的上传地址
	            path=path+filename;
	        	logger.info("文件地址："+path);
	        	 //使用Apache文件上传组件处理文件上传步骤：
	            //1、创建一个DiskFileItemFactory工厂
	            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
	            //2、创建一个文件上传解析器
	            ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
	            //解决上传文件名的中文乱码
	            fileUpload.setHeaderEncoding("UTF-8");
	            fos = new FileOutputStream(path);
					byte[] buffer=new byte[(int) size];   
					int length = 0;
					while((length = is.read(buffer))>0){
					    //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
					    fos.write(buffer, 0, length);
					}
			}
		} catch (Exception e) {
			logger.info("自动创建文件最大值失败的流失败-->手动创建",e);
			byte[] buffer=new byte[1024];   
			int length = 0;
			while((length = is.read(buffer))>0){
				//使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
				fos.write(buffer, 0, length);
			}
		}finally{
			is.close();
			fos.close();
		}
		logger.info("文件上传成功");
		return true;
	}

	/**
	 * 
	 * @方法说明：读取银行卡Excel
	 * @时间：2016年12月26日 下午3:26:47
	 * @创建人：wangdong
	 */
	@SuppressWarnings("static-access")
	public static List<Map<String, Object>> readExcel(MultipartFile file, PropertiesLoader loader,
			PbcBankInfo pbcBankInfo) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			InputStream is = file.getInputStream();
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			// Read the Sheet
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// Read the Row
				for (int rowNum = 3; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						Map<String,Object> map = new HashMap<String,Object>();
						for (int cellNum = 0; cellNum < xssfRow.getLastCellNum(); cellNum++) {
							if(null != xssfRow.getCell(cellNum)){
								Object values = null;
								if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_BOOLEAN) {
									values = String.valueOf(xssfRow.getCell(cellNum).getBooleanCellValue());
						        } else if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_NUMERIC) {
						        	if(HSSFDateUtil.isCellDateFormatted(xssfRow.getCell(cellNum))){
						        		 Date d = xssfRow.getCell(cellNum).getDateCellValue();
						        		 DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						        		 values = formater.format(d);
						        		 values = formater.parse((String) values);
						        	}else{
						        		values = String.valueOf(xssfRow.getCell(cellNum).getNumericCellValue());
						        	}
						        } else {
						        	values = String.valueOf(xssfRow.getCell(cellNum).getStringCellValue());
						        }
								setObject(map,pbcBankInfo,cellNum,values);
							}
						}
						list.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * @方法说明：（银行信息）根据下标属性赋值
	 * @时间：2016年12月26日 下午3:27:11
	 * @创建人：wangdong
	 */
	public static void setObject(Map<String,Object> map,PbcBankInfo model,int i,Object values)throws Exception{
		Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
        	int num = i+2;//赋值从主键下一个属性开始
            String name = field[num].getName(); // 获取属性的名字
            map.put(name, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
	 * 
	 * @方法说明：读取措施信息Excel
	 * @时间：2016年12月26日 下午3:27:49
	 * @创建人：wangdong
	 */
	@SuppressWarnings("static-access")
	public static List<Map<String, Object>> readExcel(MultipartFile file, PropertiesLoader loader,
			PbcMeasureInfo pbcMeasureInfo) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			InputStream is = file.getInputStream();
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			// Read the Sheet
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// Read the Row
				for (int rowNum = 3; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						Map<String,Object> map = new HashMap<String,Object>();
						for (int cellNum = 0; cellNum < xssfRow.getLastCellNum(); cellNum++) {
							if(null != xssfRow.getCell(cellNum)){
								Object values = null;
								if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_BOOLEAN) {
									values = String.valueOf(xssfRow.getCell(cellNum).getBooleanCellValue());
						        } else if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_NUMERIC) {
						        	if(HSSFDateUtil.isCellDateFormatted(xssfRow.getCell(cellNum))){
						        		 Date d = xssfRow.getCell(cellNum).getDateCellValue();
						        		 DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						        		 values = formater.format(d);
						        		 values = formater.parse((String) values);
						        	}else{
						        		values = String.valueOf(xssfRow.getCell(cellNum).getNumericCellValue());
						        	}
						        } else {
						        	values = String.valueOf(xssfRow.getCell(cellNum).getStringCellValue());
						        }
								setObject(map,pbcMeasureInfo,cellNum,values);
							}
						}
						list.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * @方法说明：根据措施实体的属性赋值
	 * @时间：2016年12月26日 下午3:28:08
	 * @创建人：wangdong
	 */
	public static void setObject(Map<String,Object> map,PbcMeasureInfo model,int i,Object values)throws Exception{
		Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
        	int num = i+2;//赋值从主键下一个属性开始
            String name = field[num].getName(); // 获取属性的名字
            map.put(name, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
	 * 
	 * @方法说明：读取账户信息Excel
	 * @时间：2016年12月26日 下午3:29:03
	 * @创建人：wangdong
	 */
	@SuppressWarnings("static-access")
	public static List<Map<String, Object>> readExcel(MultipartFile file, PropertiesLoader loader,
			PbcAccountInfo pbcAccountInfo) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			InputStream is = file.getInputStream();
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			// Read the Sheet
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// Read the Row
				for (int rowNum = 3; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						Map<String,Object> map = new HashMap<String,Object>();
						for (int cellNum = 0; cellNum < xssfRow.getLastCellNum(); cellNum++) {
							if(null != xssfRow.getCell(cellNum)){
								Object values = null;
								if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_BOOLEAN) {
									values = String.valueOf(xssfRow.getCell(cellNum).getBooleanCellValue());
						        } else if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_NUMERIC) {
						        	if(HSSFDateUtil.isCellDateFormatted(xssfRow.getCell(cellNum))){
						        		 Date d = xssfRow.getCell(cellNum).getDateCellValue();
						        		 DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						        		 values = formater.format(d);
						        		 values = formater.parse((String) values);
						        	}else{
						        		values = String.valueOf(xssfRow.getCell(cellNum).getNumericCellValue());
						        	}
						        } else {
						        	values = String.valueOf(xssfRow.getCell(cellNum).getStringCellValue());
						        }
								setObject(map,pbcAccountInfo,cellNum,values);
							}
						}
						list.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * @方法说明：根据账户信息实体属性赋值
	 * @时间：2016年12月26日 下午3:29:27
	 * @创建人：wangdong
	 */
	public static void setObject(Map<String,Object> map,PbcAccountInfo model,int i,Object values)throws Exception{
		Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
        	int num = i+2;//赋值从主键下一个属性开始
            String name = field[num].getName(); // 获取属性的名字
            map.put(name, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
	 * 
	 * @方法说明：读取账户交易明细Excel
	 * @时间：2016年12月26日 下午3:29:50
	 * @创建人：wangdong
	 */
	public static List<Map<String, Object>> readExcel(MultipartFile file, PropertiesLoader loader,
			PbcAccountDetail pbcAccountDetail) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			InputStream is = file.getInputStream();
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			// Read the Sheet
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// Read the Row
				for (int rowNum = 3; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						Map<String,Object> map = new HashMap<String,Object>();
						for (int cellNum = 0; cellNum < xssfRow.getLastCellNum(); cellNum++) {
							if(null != xssfRow.getCell(cellNum)){
								Object values = null;
								if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_BOOLEAN) {
									values = String.valueOf(xssfRow.getCell(cellNum).getBooleanCellValue());
						        } else if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_NUMERIC) {
						        	if(HSSFDateUtil.isCellDateFormatted(xssfRow.getCell(cellNum))){
						        		 Date d = xssfRow.getCell(cellNum).getDateCellValue();
						        		 DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						        		 values = formater.format(d);
						        		 values = formater.parse((String) values);
						        	}else{
						        		values = String.valueOf(xssfRow.getCell(cellNum).getNumericCellValue());
						        	}
						        } else {
						        	values = String.valueOf(xssfRow.getCell(cellNum).getStringCellValue());
						        }
								setObject(map,pbcAccountDetail,cellNum,values);
							}
						}
						list.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * @方法说明：根据账户交易明细属性赋值
	 * @时间：2016年12月26日 下午3:30:27
	 * @创建人：wangdong
	 */
	public static void setObject(Map<String,Object> map,PbcAccountDetail model,int i,Object values)throws Exception{
		Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
        	int num = i+2;//赋值从主键下一个属性开始
            String name = field[num].getName(); // 获取属性的名字
            if(StringUtils.equals(name, "telNumber")){
            	double parseDouble = Double.parseDouble(values.toString());
				DecimalFormat df = new DecimalFormat("#");
				String format = df.format(parseDouble);
				map.put(name, format);
            }else{
            	map.put(name, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
	 * 
	 * @方法说明：读取关联全支付账号信息Excel
	 * @时间：2016年12月26日 下午3:30:49
	 * @创建人：wangdong
	 */
	public static List<Map<String, Object>> readExcel(MultipartFile file, PropertiesLoader loader,
			PbcPaymentAccountBack pbcPaymentAccountBack) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			InputStream is = file.getInputStream();
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			// Read the Sheet
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// Read the Row
				for (int rowNum = 3; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						Map<String,Object> map = new HashMap<String,Object>();
						for (int cellNum = 0; cellNum < xssfRow.getLastCellNum(); cellNum++) {
							if(null != xssfRow.getCell(cellNum)){
								Object values = null;
								if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_BOOLEAN) {
									values = String.valueOf(xssfRow.getCell(cellNum).getBooleanCellValue());
						        } else if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_NUMERIC) {
						        	if(HSSFDateUtil.isCellDateFormatted(xssfRow.getCell(cellNum))){
						        		 Date d = xssfRow.getCell(cellNum).getDateCellValue();
						        		 DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						        		 values = formater.format(d);
						        		 values = formater.parse((String) values);
						        	}else{
						        		values = String.valueOf(xssfRow.getCell(cellNum).getNumericCellValue());
						        	}
						        } else {
						        	values = String.valueOf(xssfRow.getCell(cellNum).getStringCellValue());
						        }
								setObject(map,pbcPaymentAccountBack,cellNum,values);
							}
						}
						list.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * @方法说明：根据关联全支付账号实体属性赋值
	 * @时间：2016年12月26日 下午3:31:11
	 * @创建人：wangdong
	 */
	public static void setObject(Map<String,Object> map,PbcPaymentAccountBack model,int i,Object values)throws Exception{
		Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
        	int num = i+2;//赋值从主键下一个属性开始
            String name = field[num].getName(); // 获取属性的名字
            if(StringUtils.equals(name, "telNumber")){
            	double parseDouble = Double.parseDouble(values.toString());
				DecimalFormat df = new DecimalFormat("#");
				String format = df.format(parseDouble);
				map.put(name, format);
            }else{
            	map.put(name, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
	 * 
	 * @方法说明：根据按交易流水查询银行卡或支付账号
	 * @时间：2016年12月26日 下午3:31:54
	 * @创建人：wangdong
	 */
	public static List<Map<String, Object>> readExcel(MultipartFile file, PropertiesLoader loader,
			PbcTransCardPaymentAccount pbcTransCardPaymentAccount) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			InputStream is = file.getInputStream();
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			// Read the Sheet
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// Read the Row
				for (int rowNum = 3; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						Map<String,Object> map = new HashMap<String,Object>();
						for (int cellNum = 0; cellNum < xssfRow.getLastCellNum(); cellNum++) {
							if(null != xssfRow.getCell(cellNum)){
								Object values = null;
								if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_BOOLEAN) {
									values = String.valueOf(xssfRow.getCell(cellNum).getBooleanCellValue());
						        } else if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_NUMERIC) {
						        	if(HSSFDateUtil.isCellDateFormatted(xssfRow.getCell(cellNum))){
						        		 Date d = xssfRow.getCell(cellNum).getDateCellValue();
						        		 DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						        		 values = formater.format(d);
						        		 values = formater.parse((String) values);
						        	}else{
						        		values = String.valueOf(xssfRow.getCell(cellNum).getNumericCellValue());
						        	}
						        } else {
						        	values = String.valueOf(xssfRow.getCell(cellNum).getStringCellValue());
						        }
								setObject(map,pbcTransCardPaymentAccount,cellNum,values);
							}
						}
						list.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * @方法说明：按交易流水查询银行卡或支付账号实体属性赋值
	 * @时间：2016年12月26日 下午3:34:52
	 * @创建人：wangdong
	 */
	public static void setObject(Map<String,Object> map,PbcTransCardPaymentAccount model,int i,Object values)throws Exception{
		Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
        	int num = i+2;//赋值从主键下一个属性开始
            String name = field[num].getName(); // 获取属性的名字
            map.put(name, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	/**
	 * 
	 * @方法说明：根据账户动态查询解除Excel
	 * @时间：2016年12月26日 下午3:35:05
	 * @创建人：wangdong
	 */
	public static List<Map<String, Object>> readExcel(MultipartFile file, PropertiesLoader loader,
			PbcReleaseFeedback pbcReleaseFeedback) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			InputStream is = file.getInputStream();
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			// Read the Sheet
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// Read the Row
				for (int rowNum = 3; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						Map<String,Object> map = new HashMap<String,Object>();
						for (int cellNum = 0; cellNum < xssfRow.getLastCellNum(); cellNum++) {
							if(null != xssfRow.getCell(cellNum)){
								Object values = null;
								if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_BOOLEAN) {
									values = String.valueOf(xssfRow.getCell(cellNum).getBooleanCellValue());
						        } else if (xssfRow.getCell(cellNum).getCellType() == xssfRow.getCell(cellNum).CELL_TYPE_NUMERIC) {
						        	if(HSSFDateUtil.isCellDateFormatted(xssfRow.getCell(cellNum))){
						        		 Date d = xssfRow.getCell(cellNum).getDateCellValue();
						        		 DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						        		 values = formater.format(d);
						        		 values = formater.parse((String) values);
						        	}else{
						        		values = String.valueOf(xssfRow.getCell(cellNum).getNumericCellValue());
						        	}
						        } else {
						        	values = String.valueOf(xssfRow.getCell(cellNum).getStringCellValue());
						        }
								setObject(map,pbcReleaseFeedback,cellNum,values);
							}
						}
						list.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * @方法说明：账户动态查询解除属性赋值
	 * @时间：2016年12月26日 下午3:35:34
	 * @创建人：wangdong
	 */
	public static void setObject(Map<String,Object> map,PbcReleaseFeedback model,int i,Object values)throws Exception{
		Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
        	int num = i+2;//赋值从主键下一个属性开始
            String name = field[num].getName(); // 获取属性的名字
            map.put(name, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
