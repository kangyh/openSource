/**
 * 
 */
package com.heepay.manage.modules.risk.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Character.UnicodeBlock;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.heepay.billingutils.CreateDir;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.RegExpression;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.enums.RiskBwCategory;
import com.heepay.manage.common.enums.RiskBwStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.cbms.validate.Validator;
import com.heepay.manage.modules.pbc.service.Excel2003POI;
import com.heepay.manage.modules.pbc.service.Excel2013POI;
import com.heepay.manage.modules.risk.dao.RiskBlackorwhiteItemListDao;
import com.heepay.manage.modules.risk.dao.RiskBlackorwhiteListDao;
import com.heepay.manage.modules.risk.entity.RiskBlackorwhiteItemList;
import com.heepay.manage.modules.risk.entity.RiskBlackorwhiteItemVo;
import com.heepay.manage.modules.risk.entity.RiskBlackorwhiteList;
import com.heepay.manage.modules.settle.service.util.ExcelService;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
 * @author Administrator
 *
 */
@Service
public class RiskBlackorwhiteItemService extends CrudService<RiskBlackorwhiteItemListDao,RiskBlackorwhiteItemList>{
	private static final Logger logger = LogManager.getLogger("RiskBlackorwhiteItemService");
	@Autowired
	private RiskBlackorwhiteItemListDao riskBlackorwhiteItemListDao;
	@Autowired
	private Excel2013POI excel2013POI;//解析文件
	@Autowired
	private Excel2003POI excel2003POI;//解析文件
	private static PropertiesLoader loader = new PropertiesLoader("riskPbc.properties");

	/**
	 * @方法说明：获取黑白名单元素值
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    public RiskBlackorwhiteItemList getRiskBlackorwhiteItemById(Integer blackId) {
    	return riskBlackorwhiteItemListDao.getRiskBlackorwhiteItemById(blackId);
    }
    /**
	 * @方法说明：插入黑白名单元素信息
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    @Transactional(readOnly = false)
    public int insertRiskBlackorwhiteItem(RiskBlackorwhiteItemList record){
    	return riskBlackorwhiteItemListDao.insertRiskBlackorwhiteItem(record);
    }
    
    /**
	 * @方法说明：删除黑白名单元素信息
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    @Transactional(readOnly = false)
    public int deleteRiskBlackorwhiteItem(Integer blackItemId){
    	return riskBlackorwhiteItemListDao.deleteRiskBlackorwhiteItem(blackItemId);
    }
    /**
	 * @方法说明：更新黑白名单元素信息
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    @Transactional(readOnly = false)
    public int updateRiskBlackorwhiteItem(RiskBlackorwhiteItemList record){
    	return riskBlackorwhiteItemListDao.updateRiskBlackorwhiteItem(record);
    }
    
    /**
	 * @方法说明：分页查询黑名单元素信息
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
	public Model findBwItemPageList(Page<RiskBlackorwhiteItemList> page, 
			RiskBlackorwhiteItemList riskBlackorwhiteItem,
										  Model model,String pageNo) {
		if(StringUtils.isNotBlank(pageNo)){
			int pageno=Integer.parseInt(pageNo);
			page.setPageNo(pageno);
		}
		page.setPageSize(10);
		
		Page<RiskBlackorwhiteItemList> rbwInfoPage = findPage(page,riskBlackorwhiteItem);
		rbwInfoPage.setPageSize(10);
		model.addAttribute("riskBlackorwhiteItem", riskBlackorwhiteItem);
		model.addAttribute("page", rbwInfoPage);
		return model;
	}
	
	/**
	 * 
	 * @方法说明：上传黑白名单元素信息入库
	 * @时间：2017年4月21日
	 * @创建人：李震
	 
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	@Transactional(readOnly = false)
	public Map<String,Object> loadModel(MultipartFile file,RiskBlackorwhiteList rbwInfo) {
		String lastIndex="";
    	String  filename="";
    	Map<String,Object> resultMap = null;resultMap = new HashMap<String,Object>() ;
		//上传文件
		if (!file.isEmpty()) {
			String path = loader.getProperty("localPath");
			String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			path=path+"/"+format+"/";
			boolean flag = CreateDir.createLiunxDir(path);
			if(!flag){
        		logger.error("路径创建失败-------->{}"+path);
        		resultMap.put("msg", "路径创建失败");
				return resultMap; 
        	}
			 filename = file.getOriginalFilename();
        	long size = file.getSize();
        	
        	
        	//根据截取的值是否是-1判断是否是组合命名
        	int of = filename.lastIndexOf(".");
        	if(of != -1){
				lastIndex = filename.substring(of);
				
				//判断这个是不是标准的Excel表格
				filename ="黑白名单元素"+format+lastIndex;
				logger.error("路径创建名称-------->{}",filename);
			}else {
				resultMap.put("msg", "上传文件格式错误");
				return resultMap; 
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
            
            InputStream is = null ;
            FileOutputStream fos = null;
            
			try {
				is=file.getInputStream();
				fos = new FileOutputStream(path);
				byte[] buffer=new byte[(int) size];   
				int length = 0;
				while((length = is.read(buffer))>0){
				    //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
				    fos.write(buffer, 0, length);
				}
			} catch (Exception e) {
				logger.info("自动创建文件最大值失败的流失败-->手动创建",e);
				byte[] buffer=new byte[1024];   
				int length = 0;
				try {
					while((length = is.read(buffer))>0){
					    //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
					    fos.write(buffer, 0, length);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}finally{
				try {
					is.close();
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
			}
			logger.info("文件上传成功");
			int index = filename.lastIndexOf(".");
			String lastIndexfilename = filename.substring(index);
			int errorIndex = 0;
			int insertValue=0;
				try {
					List<Map> readExcel = null;
					if(lastIndexfilename.equalsIgnoreCase(".xls")){
						readExcel =  excel2003POI.readExcel2003ForBw(file);
					}else if(lastIndexfilename.equalsIgnoreCase(".xlsx")){
						readExcel =  excel2013POI.readExcel2007ForBw(file);
					}
					Date currentDate = DateUtil.stringToDate( DateUtil.getTodayYYYYMMDD_HHMMSS(), "yyyy-MM-dd HH:mm:ss");
					StringBuffer blackItemIds = new StringBuffer();
					RiskBlackorwhiteItemList temp = null;
					int count = 0;
					for (int i=0;i<(readExcel==null?0:readExcel.size());i++) {
						errorIndex = i;
						if(readExcel.get(i)!=null){
							readExcel.get(i).put("status", RiskBwStatus.ENABLE.getValue());
							readExcel.get(i).put("blackId", rbwInfo.getBlackId() );
							readExcel.get(i).put("createAuthor", UserUtils.getUser().getName());
							readExcel.get(i).put("updateAuthor", UserUtils.getUser().getName());
							readExcel.get(i).put("createTime", currentDate);
							readExcel.get(i).put("updateTime", currentDate);
							Map checkMap = this.checkUploadData( readExcel.get(i) , (i+1) ,rbwInfo );
							if( !"校验通过".equals(checkMap.get("message")) ){
								resultMap.put("msg","文件上传入库失败,"+checkMap.get("message"));
								return resultMap; 
							}
							temp = new RiskBlackorwhiteItemList();
							temp.setBlackId( rbwInfo.getBlackId() );
							temp.setBlackItemValue(readExcel.get(i).get("blackItemValue").toString());
							count = riskBlackorwhiteItemListDao.getCountByItemValue(temp);
							if( count==0  ){//如果没有记录则新增，否则不操作
								insertValue = riskBlackorwhiteItemListDao.insertMapItems( readExcel.get(i) );
							}
						}
						if( insertValue == 0 && count==0 ){
							resultMap.put("msg","文件上传入库失败,请查看第"+errorIndex+"行数据");
							return resultMap; 
						}
						blackItemIds.append(readExcel.get(i).get("blackItemId")+",");
					}
					resultMap.put("msg","文件上传成功"+readExcel==null?"0":readExcel.size()+"行数据！");
					resultMap.put("blackItemIds", blackItemIds);
					resultMap.put("list", readExcel);
					return resultMap; 
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("文件上传失败------>{文件上传入库失败}"+e.getMessage());
					resultMap.put("msg","文件上传入库失败,请查看第"+errorIndex+"行数据");
					return resultMap;
				}

			}
		resultMap.put("msg", "文件为空");
		return resultMap; 

	}
	*/
	/**
	 * @方法说明：根据元素值和类别获取数量
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    public int getCountByItemValue(RiskBlackorwhiteItemList record) {
    	return riskBlackorwhiteItemListDao.getCountByItemValue(record);
    }
	
	
	/**
	 * 
	 * @方法说明：读取上传的文件
	 * @时间：2017年4月21日
	 * @创建人：李震
	 */
	@SuppressWarnings({ "static-access", "rawtypes" })
	@Transactional(readOnly = false)
	public List<Map> readExcel( MultipartFile file ) {
		String lastIndex="";
    	String  filename="";
		//上传文件
		if (!file.isEmpty()) {
			String path = loader.getProperty("localPath");
			String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			path=path+"/"+format+"/";
			boolean flag = CreateDir.createLiunxDir(path);
			if(!flag){
        		logger.error("路径创建失败-------->{}"+path);
				return null; 
        	}
			 filename = file.getOriginalFilename();
        	long size = file.getSize();
        	
        	
        	//根据截取的值是否是-1判断是否是组合命名
        	int of = filename.lastIndexOf(".");
        	if(of != -1){
				lastIndex = filename.substring(of);
				
				//判断这个是不是标准的Excel表格
				filename ="黑白名单元素"+format+lastIndex;
				logger.error("路径创建名称-------->{}",filename);
			}else {
				logger.error("上传文件格式错误-------->{}",filename);
				return null;
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
            
            InputStream is = null ;
            FileOutputStream fos = null;
            
			try {
				is=file.getInputStream();
				fos = new FileOutputStream(path);
				byte[] buffer=new byte[(int) size];   
				int length = 0;
				while((length = is.read(buffer))>0){
				    //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
				    fos.write(buffer, 0, length);
				}
			} catch (Exception e) {
				logger.info("自动创建文件最大值失败的流失败-->手动创建",e);
				byte[] buffer=new byte[1024];   
				int length = 0;
				try {
					while((length = is.read(buffer))>0){
					    //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
					    fos.write(buffer, 0, length);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}finally{
				try {
					is.close();
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
			}
			logger.info("文件上传成功");
			int index = filename.lastIndexOf(".");
			String lastIndexfilename = filename.substring(index);
				try {
					List<Map> readExcel = null;
					if(lastIndexfilename.equalsIgnoreCase(".xls")){
						readExcel =  excel2003POI.readExcel2003ForBw(file);
					}else if(lastIndexfilename.equalsIgnoreCase(".xlsx")){
						readExcel =  excel2013POI.readExcel2007ForBw(file);
					}
					
					return readExcel; 
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("文件上传失败------>{文件读取失败}"+e.getMessage());
					return null;
				}

			}
		return null; 

	}
	/**
	 * 
	 * @方法说明：检测上传的信息
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,String> checkUploadData(RiskBlackorwhiteItemVo vo,int j ,RiskBlackorwhiteList rbwInfo) {
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("message", "校验通过");
	     if(vo!=null ){
		    	
		    	if(vo.getBlackItemValue()!=null){
		    		String itemValue = vo.getBlackItemValue();
		    		if(itemValue.length()>50){
		    			resultMap.put("message", "第"+j+"行数据，黑名单字段值过长!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(  itemValue ) ){
				    	resultMap.put("message", "第"+j+"行数据，黑名单字段值包含非法字符!");
				    	return resultMap;
				    }else if (  rbwInfo.getCategory().equals(RiskBwCategory.MERCHANT_ID.getValue())  &&  !RegExpression.regNumber(  itemValue  ) ) {
				    	resultMap.put("message", "第"+j+"行数据，商户编号必须是数字!");
				    	return resultMap;
				    }else if (  rbwInfo.getCategory().equals(RiskBwCategory.BANKCARD.getValue())  &&  !RegExpression.regBankCard(itemValue) ) {
				    	resultMap.put("message", "第"+j+"行数据，银行卡号格式不正确!");
				    	return resultMap;
				    }else if (  rbwInfo.getCategory().equals(RiskBwCategory.MOBILE.getValue())  &&  !Validator.isMobile(itemValue)  ) {
				    	resultMap.put("message", "第"+j+"行数据，手机号格式不正确!");
				    	return resultMap;
				    }/*else if (  rbwInfo.getCategory().equals(RiskBwCategory.IP.getValue())  &&  !Validator.isIP(itemValue) ) {
				    	resultMap.put("message", "第"+j+"行数据，IP格式不正确!");
				    	return resultMap;
				    }*/else if (  rbwInfo.getCategory().equals(RiskBwCategory.IDCARD.getValue())  && !Validator.isIDCard2(  itemValue  )  ) {
				    	resultMap.put("message", "第"+j+"行数据，身份证格式不正确!");
				    	return resultMap;
				    }
		    	}else{
		    		resultMap.put("message", "第"+j+"行数据，黑名单字段值不能为空!");
	    			return resultMap;
		    	}
	     }
		
		return resultMap;
	}
	private boolean ifHasInjectChar(String str) {
        String inj_str = "\' * % < > & ; script cookie expression insert update delete select #";
        String inj_stra[] = inj_str.split(" ");
        for (int i = 0; i < inj_stra.length; i++) {
            if (str.indexOf(inj_stra[i]) >= 0) {
                return true;
            }
        }
        return false;
    }
	 public String getItemIdByValueAndBlackId(RiskBlackorwhiteItemList record) {
		 return riskBlackorwhiteItemListDao.getItemIdByValueAndBlackId(record);
	 }
	 public  boolean checkStringContainChinese(String checkStr){  
	        if(!StringUtil.isBlank(checkStr)){  
	            char[] checkChars = checkStr.toCharArray();  
	            for(int i = 0; i < checkChars.length; i++){  
	                char checkChar = checkChars[i];  
	                if(checkCharContainChinese(checkChar)){  
	                    return true;  
	                }  
	            }  
	        }  
	        return false;  
	    }
	    private  boolean checkCharContainChinese(char checkChar){  
	        Character.UnicodeBlock ub = Character.UnicodeBlock.of(checkChar);  
	        if( UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS == ub || UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS == ub || UnicodeBlock.CJK_COMPATIBILITY_FORMS == ub ||  
	        		UnicodeBlock.CJK_RADICALS_SUPPLEMENT == ub || UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A == ub || UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B == ub){  
	            return true;  
	        }  
	        return false;  
	    }
}
