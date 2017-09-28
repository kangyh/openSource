package com.heepay.manage.modules.pcac.service;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heepay.billingutils.CreateDir;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.common.utils.HttpClientUtil;
import com.heepay.manage.modules.cbms.validate.Validator;
import com.heepay.manage.modules.pbc.service.Excel2003POI;
import com.heepay.manage.modules.pbc.service.Excel2013POI;
import com.heepay.manage.modules.pcac.dao.PcacMerchantReportDeleteDao;
import com.heepay.manage.modules.pcac.dao.PcacPersonReportDao;
import com.heepay.manage.modules.pcac.entity.PcacMerchantReportDelete;
import com.heepay.manage.modules.pcac.entity.PcacPersonRepVo;
import com.heepay.manage.modules.pcac.entity.PcacPersonReport;
import com.heepay.manage.modules.settle.service.util.ExcelService;
import com.heepay.manage.modules.sys.utils.UserUtils;
@Service
@Transactional(readOnly = true)
public class PcacPersonReportService  extends CrudService<PcacPersonReportDao, PcacPersonReport> {
	
	private static final Logger logger = LogManager.getLogger("PcacPersonReportService");
	@Autowired
	private PcacPersonReportDao pcacPersonReportDao;
	@Autowired
	private PcacMerchantReportDeleteDao pcacMerchantReportDeleteDao;
	@Autowired
	private Excel2013POI excel2013POI;//解析文件
	@Autowired
	private ExcelService excelService;
	@Autowired
	private Excel2003POI excel2003POI;//解析文件
	
	private static PropertiesLoader loader = new PropertiesLoader("riskPbc.properties");
	
	
	/**
	 * @方法说明：通过主键获取个人商户信息
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	public PcacPersonReport getPcacPersonReportById(Long personReportId) {
		return pcacPersonReportDao.getPcacPersonReportById(personReportId);
	}
	/**
	 * @方法说明：个人商户信息导出
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	public void export(PcacPersonReport pcacPersonReport, HttpServletRequest request,HttpServletResponse response,String[] reportIds ) throws Exception{
		//数据库查询结果
		List<Map<String,Object>> pcacPersonReportList = null;
		if(reportIds==null||reportIds.length==0) {//是导出全部数据还是部分数据
			pcacPersonReportList = pcacPersonReportDao.findListExcel(pcacPersonReport);
		}else{
			pcacPersonReport.setReportIds(reportIds);
			pcacPersonReportList = pcacPersonReportDao.findCheckedListExcel(pcacPersonReport);
		}
		
		String[] headerArray = {"商户类型","商户属性","商户名称","商户简称","商户英文名称","证件类型","证件号码",
				"商户代码","商户行业类别","收款帐/卡号","收款帐/卡号开户行","商户注册地址(省)","商户注册地址详情",
				"商户经营地址(省)","商户经营地址详情","网址","服务器IP","ICP备案编号","商户联系人","商户联系电话","商户经营地区范围","商户E-mail",
				"清算网络","商户状态","服务起始时间","服务结束时间","合规风险状况","开通业务种类",
				"计费类型","支持账户类型","拓展方式","外包服务机构名称","外包服务机构法人证件类型","外包服务机构法人证件号码",
				"外包服务机构法定代表人证件类型","外包服务机构法定代表人证件号码","上报机构","填写时间","填写人姓名"};
		//导出Excel表格标题行
//		String[] headerArray = {"cusType","cusNature","regName","cusName","cusNameEn","docType","docCode","cusCode","induType","bankNo","openBank","regAddrProv","regAddrDetail",
//				"addrProv","addrDetail","url","serverIp","icp","contName","contPhone","occurarea","cusEmail","networkType","status","startTime","endTime	","riskStatus",
//				"openType","chageType","accountType","expandType","outServiceName","outServicCardType","outServiceCardCode","outServicelegCardType	","outServicelegCardCode","orgId","fillerTime","filler"};
		//导出表格对应的字段名称
		try {
			excelService.exportCusExcel("个人商户风险信息", headerArray,pcacPersonReportList,Constants.PERSON_EXPORT,response,request,true);
		} catch (Exception e) {
			logger.error("PcacPersonReportService export has a error:{个人商户信息导出出错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	/**
	 * @方法说明：更新个人商户信息
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	@Transactional(readOnly = false)
	public int updatePcacPersonReport(PcacPersonReport pcacPersonReport) {
		return pcacPersonReportDao.updatePcacPersonReport(pcacPersonReport);
	}
	/**
	 * @方法说明：本地删除个人商户信息
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	@Transactional(readOnly = false)
	public int deletePcacPersonReport(Long personReportId){
		return pcacPersonReportDao.deletePcacPersonReport(personReportId);
	}
	/**
	 * @方法说明：本地批量个人商户信息
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	@Transactional(readOnly = false)
	public int deleteManyPcacPersonReport(PcacPersonReport record){
		return pcacPersonReportDao.deleteManyPcacPersonReport(record);
    }
	/**
	 * @方法说明：分页查询个人商户信息
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	public Model findPcacPersonReportPage(Page<PcacPersonReport> page, 
			PcacPersonReport pcacPersonReport,
										  Model model,String pageNo) {
		if(StringUtils.isNotBlank(pageNo)){
			int pageno=Integer.parseInt(pageNo);
			page.setPageNo(pageno);
		}
		page.setPageSize(10);
		
		Page<PcacPersonReport> ppReport = findPage(page,pcacPersonReport);
		ppReport.setPageSize(10);
		model.addAttribute("pcacPersonReport", pcacPersonReport);
		model.addAttribute("page", ppReport);
		return model;
	}
	/**
	 * @方法说明：分页分组查询个人商户信息
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	public Model findPcacPersonReportPageByBatch(Page<PcacPersonReport> page, 
			PcacPersonReport pcacPersonReport,
										  Model model,String pageNo) {
		
		if(StringUtils.isNotBlank(pageNo)){
			int pageno=Integer.parseInt(pageNo);
			page.setPageNo(pageno);
		}
		page.setPageSize(10);
		pcacPersonReport.setPage(page);
		page.setList(pcacPersonReportDao.findListGroupByBatch(pcacPersonReport));
		model.addAttribute("pcacPersonReport", pcacPersonReport);
		model.addAttribute("page", page);
		return model;
	}
	/**
	 * @方法说明：分页分组查询删除状态下的个人商户信息
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	public Model findPcacPersonReportDeletePageByBatch(Page<PcacPersonReport> page, 
			PcacPersonReport pcacPersonReport,
										  Model model,String pageNo) {
		
		if(StringUtils.isNotBlank(pageNo)){
			int pageno=Integer.parseInt(pageNo);
			page.setPageNo(pageno);
		}
		page.setPageSize(10);
		pcacPersonReport.setPage(page);
		page.setList(pcacPersonReportDao.findDeleteListGroupByBatch(pcacPersonReport));
		model.addAttribute("pcacPersonReport", pcacPersonReport);
		model.addAttribute("page", page);
		return model;
	}
	public List<String> getReportIdsByDelBatchNo(String delBatchNo){
		return pcacPersonReportDao.getReportIdsByDelBatchNo(delBatchNo);
	}
	public List<String> getReportIdsByRepBatchNo(String batchNo){
		return pcacPersonReportDao.getReportIdsByRepBatchNo(batchNo);
	}
	/**
	 * 
	 * @方法说明：上传个人商户信息入库
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	@Transactional(readOnly = false)
	public Map<String,Object> loadModel(MultipartFile file) {
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
				filename ="个人商户信息"+format+lastIndex;
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
						readExcel =  excel2003POI.readExcel2003ForRiskInfo(file,1);
					}else if(lastIndexfilename.equalsIgnoreCase(".xlsx")){
						readExcel =  excel2013POI.readExcel2007ForRiskInfo(file,1);
					}
					
					//insertValue = pbcPaymentAccountDao.insertValue(readExcel2003);
					StringBuffer reportIds = new StringBuffer();
					for (int i=0;i<(readExcel==null?0:readExcel.size());i++) {
						errorIndex = i;
						if(readExcel.get(i)!=null){
							readExcel.get(i).put("cusType" , JSONObject.parseObject(Constants.CUS_TYPE).get(  readExcel.get(i).get("cusType")  )   );
							readExcel.get(i).put("cusNature", JSONObject.parseObject(Constants.CUS_NATURE).get(readExcel.get(i).get("cusNature") ) );
							//人员证件类型
							readExcel.get(i).put("docType", JSONObject.parseObject(Constants.LEG_DOC_TYPE).get(readExcel.get(i).get("docType") ) );
							readExcel.get(i).put("status" , JSONObject.parseObject(Constants.STATUS).get(readExcel.get(i).get("status")) );
							readExcel.get(i).put("riskStatus" ,JSONObject.parseObject(Constants.RISK_STATUS).get(readExcel.get(i).get("riskStatus") ) );
							readExcel.get(i).put("openType", JSONObject.parseObject(Constants.OPEN_TYPE).get(readExcel.get(i).get("openType")  ) );
							readExcel.get(i).put("accountType", JSONObject.parseObject(Constants.ACCOUNT_TYPE).get(readExcel.get(i).get("accountType") ) );
							readExcel.get(i).put("chageType", JSONObject.parseObject(Constants.CHAGE_TYPE).get(readExcel.get(i).get("chageType") ) );
							readExcel.get(i).put("expandType", JSONObject.parseObject(Constants.EXPAND_TYPE).get(readExcel.get(i).get("expandType") ) );
							readExcel.get(i).put("networkType",JSONObject.parseObject(Constants.NETWORK_TYPE).get(readExcel.get(i).get("networkType") ) );
							
							readExcel.get(i).put("outServicelegCardType",JSONObject.parseObject(Constants.LEG_DOC_TYPE).get(readExcel.get(i).get("outServicelegCardType") ) );
							readExcel.get(i).put("outServiceCardType",JSONObject.parseObject(Constants.DOC_TYPE).get(readExcel.get(i).get("outServiceCardType")  ) );
							
							readExcel.get(i).put("regAddrProv",JSONObject.parseObject(Constants.PROVINCE).get(readExcel.get(i).get("regAddrProv")  ) );
							readExcel.get(i).put("addrProv",JSONObject.parseObject(Constants.PROVINCE).get(readExcel.get(i).get("addrProv") ) );
							
							readExcel.get(i).put("opStatus", "01");
							readExcel.get(i).put("regType", "03");
							readExcel.get(i).put("uploader", UserUtils.getUser().getName());
							readExcel.get(i).put("uploadTime", DateUtil.stringToDate( DateUtil.getTodayYYYYMMDD_HHMMSS(), "yyyy-MM-dd HH:mm:ss"));
							
							Map checkMap = this.checkUploadData( readExcel.get(i) , (i+1) );
							if( !"校验通过".equals(checkMap.get("message")) ){
								resultMap.put("msg","文件上传入库失败,"+checkMap.get("message"));
								return resultMap; 
							}
							
							insertValue = pcacPersonReportDao.insertMapReport( readExcel.get(i) );
						}
						
						
						if( insertValue == 0 ){
							resultMap.put("msg","文件上传入库失败,请查看第"+errorIndex+"行数据");
							return resultMap; 
						}
						reportIds.append(readExcel.get(i).get("personReportId")+",");
					}
					resultMap.put("msg","文件上传成功"+readExcel==null?"0":readExcel.size()+"行数据！");
					resultMap.put("reportIds", reportIds);
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
	/**
	 * 
	 * @方法说明：上报时，获得唯一批次号
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	public  String getBatchNo() {
		return DateUtil.getTodayYYYYMMDDHHmmss()+RandomUtils.nextInt(111111, 999999);
	}
	/**
	 * 
	 * @方法说明：上报个人商户信息
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	@Transactional(readOnly = false)
	public boolean reportData(PcacPersonReport entity) {
		List<PcacPersonRepVo> entityList = null ;
		String currentDate = DateUtil.getTodayYYYYMMDD_HHMMSS();
		String username = UserUtils.getUser().getName();
		boolean resultBool = false;
		try {
			entityList = pcacPersonReportDao.findPersonRepList(entity);
		} catch (Exception e) {
			logger.error("查询异常："+e.getMessage());
			e.printStackTrace();
		}
		if(entityList!=null&& entityList.size()>0){
			for( PcacPersonRepVo vo : entityList){
				vo.setRepDate(currentDate);
				vo.setRepPerson(username);
				vo.setRepType("03");
			}
			String params = JSONArray.toJSONString(entityList);
			logger.info("上报个人商户风险信息，传递参数:"+params);
			PropertiesLoader loader = new PropertiesLoader("pcacRpc.properties");
			String path = loader.getProperty("reportUrl");
			String reportUrlStatus = loader.getProperty("reportUrlStatus");
			String result = null;
			if( "0".equals(reportUrlStatus)){
				result = HttpClientUtil.getInstance().sendJsonHttpPost(path, params);
			}else{
				result = "{\"respInfo\":{\"resultStatus\":\"01\",\"resultCode\":\"S00000\"}}";
			}
			logger.info("上报个人商户风险信息，响应结果:"+result);
			entity.setRepDate(DateUtil.stringToDate( currentDate, "yyyy-MM-dd HH:mm:ss"));
			entity.setRepPerson(UserUtils.getUser().getName());
			entity.setRepType("03");
			entity.setOpStatus("05");//默认上报失败
			if(StringUtil.isBlank(result)){
				logger.info("上报个人商户风险信息失败，返回结果为空");
				entity.setOpStatusNote("上报失败，返回结果为空");
			}else{
				JSONObject jobj = JSONObject.parseObject(result);
				String respInfo= jobj.getString("respInfo");
				JSONObject jobj2 = JSONObject.parseObject(respInfo);
				String resultCode = jobj2.getString("resultCode");
				if("S00000".equals(resultCode)){
					logger.info("上报成功，开始更新记录");
					entity.setOpStatus("04");
					entity.setOpStatusNote("上报成功");
					resultBool = true;
				}else{
					entity.setOpStatusNote("上报失败，返回码："+resultCode);
				}
			}
			entity.setBatchNo(this.getBatchNo());
			pcacPersonReportDao.updatePcacPersonReport(entity);
		}
		return resultBool;
	}
	/**
	 * 
	 * @方法说明：上报删除个人商户信息
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	@Transactional(readOnly = false)
	public boolean reportDelData(PcacPersonReport entity) {
		boolean resultBool = false;
		List<PcacPersonRepVo> entityList = pcacPersonReportDao.findPersonDelRepList(entity);
		if(entityList!=null&& entityList.size()>0){
			Date currentDate = DateUtil.stringToDate( DateUtil.getTodayYYYYMMDD_HHMMSS(), "yyyy-MM-dd HH:mm:ss");
			String params = JSONArray.toJSONString(entityList);
			logger.info("调接口删除个人商户风险信息，传递参数:"+params);
			PropertiesLoader loader = new PropertiesLoader("pcacRpc.properties");
			String path = loader.getProperty("reportDeleteUrl");
			String reportDeleteUrlStatus = loader.getProperty("reportDeleteUrlStatus");
			String result = null;
			if(("0").equals(reportDeleteUrlStatus)){
				 result = HttpClientUtil.getInstance().sendJsonHttpPost(path, params);
			}else{
				 result = "{\"respInfo\":{\"resultStatus\":\"01\",\"resultCode\":\"S00000\"}}";
			}
			
			logger.info("接口删除个人商户风险信息，响应结果:"+result);
			entity.setRepDate(currentDate);
			entity.setRepPerson(UserUtils.getUser().getName());
			entity.setOpStatus("06");//10为删除后的状态
			String resultCode ="";
			String resultStatus = "";
			if(StringUtil.isBlank(result)){
				logger.info("接口删除个人商户信息失败，返回结果为空");
				entity.setOpStatusNote("接口删除个人商户信息失败，返回结果为空");
			}else{
				JSONObject jobj = JSONObject.parseObject(result);
				String respInfo= jobj.getString("respInfo");
				JSONObject jobj2 = JSONObject.parseObject(respInfo);
				resultCode = jobj2.getString("resultCode");
				resultStatus = jobj2.getString("resultStatus");
				if("S00000".equals(resultCode)){
					logger.info("接口删除个人商户信息成功，开始更新记录");
					
					entity.setOpStatusNote("接口删除个人商户信息成功");
					resultBool = true;
				}else{
					entity.setOpStatusNote("接口删除个人商户信息失败，返回码："+resultCode);
				}
			}
			
			pcacPersonReportDao.updatePcacPersonReport(entity);
			
			
			PcacPersonReport entity2 = new PcacPersonReport();
			entity2.setReportIds(entity.getReportIds());
			 List<PcacPersonReport> entity2List =  pcacPersonReportDao.findList(entity2);
			 String batchNo = this.getBatchNo();
			for(PcacPersonReport vo  : entity2List){
				PcacMerchantReportDelete delete = new PcacMerchantReportDelete();
				delete.setBatchNo(batchNo);
				delete.setCusType(vo.getCusType());
				delete.setCusCode(vo.getCusCode());
				delete.setDocType(vo.getDocType());
				delete.setDocCode(vo.getDocCode());
				delete.setResultCode(resultCode);
				delete.setResultStatus(resultStatus);
				delete.setCreatetime(currentDate);
				delete.setReportId(vo.getPersonReportId());
				pcacMerchantReportDeleteDao.insert(delete);
			}
		}
		return resultBool;
	}
	/**
	 * 
	 * @方法说明：检测上传的个人商户信息
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	@SuppressWarnings("rawtypes")
	private Map<String,String> checkUploadData(Map map,int j) {
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("message", "校验通过");
	     if(map!=null&&map.size()>0){
		    	 if(map.get("cusType")==null){
		    		 	resultMap.put("message", "第"+j+"行数据，商户类型不能为空!");
			    		return resultMap;
			    }
		    	if(map.get("cusNature")==null){
		    		 	resultMap.put("message", "第"+j+"行数据，商户属性不能为空!");
			    		return resultMap;
			    }
		    	if(map.get("regName")!=null){
		    		if(map.get("regName").toString().length()>64){
		    			resultMap.put("message", "第"+j+"行数据，商户名称文字过长!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("regName").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，商户名称包含非法字符!");
				    	return resultMap;
				    }
		    	}else{
		    		resultMap.put("message", "第"+j+"行数据，商户名称不能为空!");
	    			return resultMap;
		    	}
		    	if(map.get("cusName")!=null){
		    		if(map.get("cusName").toString().length()>64){
		    			resultMap.put("message", "第"+j+"行数据，商户简称文字过长!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("cusName").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，商户简称包含非法字符!");
				    	return resultMap;
				    }
		    	}
		    	if(map.get("cusNameEn")!=null){
		    		if(map.get("cusNameEn").toString().length()>64){
		    			resultMap.put("message", "第"+j+"行数据，商户英文名称文字过长!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("cusNameEn").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，商户英文名称包含非法字符!");
				    	return resultMap;
				    }
		    	}
		    	if(map.get("docType")==null){
	    		 	resultMap.put("message", "第"+j+"行数据，证件类型不能为空!");
		    		return resultMap;
		    	}
		    	
		    	if(map.get("docCode")!=null){
		    		if(map.get("docCode").toString().length()>64){
		    			resultMap.put("message", "第"+j+"行数据，证件号码文字过长!");
		    			return resultMap;
		    		}else if( "01".equals(map.get("docType")) && !Validator.isIDCard2(map.get("docCode").toString())){
		    			resultMap.put("message", "第"+j+"行数据，身份证号码格式不正确!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("docCode").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，证件号码包含非法字符!");
				    	return resultMap;
				    }
		    	}else{
		    		resultMap.put("message", "第"+j+"行数据，证件号码不能为空!");
	    			return resultMap;
		    	}
		    	
		    	
		    	if(map.get("cusCode")!=null){
		    		if( map.get("cusCode").toString().length()>64 ){
		    			resultMap.put("message", "第"+j+"行数据，商户代码文字过长!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("cusCode").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，商户代码包含非法字符!");
				    	return resultMap;
				    }
		    	}else{
		    		resultMap.put("message", "第"+j+"行数据，商户代码不能为空!");
	    			return resultMap;
		    	}
		    	
		    	if(map.get("induType")!=null){
		    		if( map.get("induType").toString().length()>50 ){
		    			resultMap.put("message", "第"+j+"行数据，商户行业类别文字过长!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("induType").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，商户行业类别包含非法字符!");
				    	return resultMap;
				    }
		    	}else{
		    		resultMap.put("message", "第"+j+"行数据，商户行业类别不能为空!");
	    			return resultMap;
		    	}
		    	if(map.get("bankNo")!=null){
		    		if( map.get("bankNo").toString().length()>32 ){
		    			resultMap.put("message", "第"+j+"行数据，收款帐/卡号文字过长!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("bankNo").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，收款帐/卡号包含非法字符!");
				    	return resultMap;
				    }
		    	}else{
		    		resultMap.put("message", "第"+j+"行数据，收款帐/卡号不能为空!");
	    			return resultMap;
		    	}
		    	if(map.get("openBank")!=null){
		    		if( map.get("openBank").toString().length()>64 ){
		    			resultMap.put("message", "第"+j+"行数据，收款帐/卡号开户行文字过长!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("openBank").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，收款帐/卡号开户行包含非法字符!");
				    	return resultMap;
				    }
		    	}else{
		    		resultMap.put("message", "第"+j+"行数据，收款帐/卡号开户行不能为空!");
	    			return resultMap;
		    	}
		    	
		    	if(  ("01".equals(map.get("cusNature"))  ||  "03".equals(map.get("cusNature")) )  &&map.get("regAddrProv")==null){
		    			resultMap.put("message", "第"+j+"行数据，（实体或实体兼网络商户）商户注册地址（省）不能为空!");
		    			return resultMap;
		    	}
		    	
		    	if(map.get("regAddrDetail")!=null){
		    		if( map.get("regAddrDetail").toString().length()>128 ){
		    			resultMap.put("message", "第"+j+"行数据，商户注册地址详情文字过长!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("regAddrDetail").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，商户注册地址详情包含非法字符!");
				    	return resultMap;
				    }
		    	}else if(   ("01".equals(map.get("cusNature"))  ||  "03".equals(map.get("cusNature")) )  && map.get("regAddrDetail")==null   ){
		    		resultMap.put("message", "第"+j+"行数据，（实体或实体兼网络商户）商户注册地址详情不能为空!");
	    			return resultMap;
		    	}
		    	
		    	if(  "01".equals(map.get("cusNature"))   && map.get("addrProv")==null){
	    			resultMap.put("message", "第"+j+"行数据，（实体商户）商户经营地址（省）不能为空!");
	    			return resultMap;
		    	}
		    	if(map.get("addrDetail")!=null){
		    		if( map.get("addrDetail").toString().length()>128 ){
		    			resultMap.put("message", "第"+j+"行数据，商户经营地址详情文字过长!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("addrDetail").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，商户经营地址详情包含非法字符!");
				    	return resultMap;
				    }
		    	}else if(   "01".equals(map.get("cusNature"))   && map.get("addrDetail")==null   ){
		    		resultMap.put("message", "第"+j+"行数据，（实体商户）商户经营地址详情不能为空!");
	    			return resultMap;
		    	}
		    	
		    	
		    	if(map.get("url")!=null){
		    		if( !Validator.isUrl2(map.get("url").toString()) ){
		    			resultMap.put("message", "第"+j+"行数据，网址格式不正确!");
		    			return resultMap;
		    		}
		    	}else if(   !"01".equals(map.get("cusNature"))   && map.get("url")==null   ){
		    		resultMap.put("message", "第"+j+"行数据，（网络商户或实体兼网络）网址不能为空!");
	    			return resultMap;
		    	}
		    	
		    	if(map.get("serverIp")!=null){
		    		if( !Validator.isIP(map.get("serverIp").toString()) ){
		    			resultMap.put("message", "第"+j+"行数据，IP地址格式不正确!");
		    			return resultMap;
		    		}
		    	}else if(   !"01".equals(map.get("cusNature"))   && map.get("serverIp")==null   ){
		    		resultMap.put("message", "第"+j+"行数据，（网络商户或实体兼网络）IP地址不能为空!");
	    			return resultMap;
		    	}
		    	
		    	if(map.get("icp")!=null){
		    		if( map.get("icp").toString().length()>20 ){
		    			resultMap.put("message", "第"+j+"行数据，ICP备案编号文字过长!");
		    			return resultMap;
		    		}
		    	}else if(   !"01".equals(map.get("cusNature"))   && map.get("icp")==null   ){
		    		resultMap.put("message", "第"+j+"行数据，（网络商户或实体兼网络）ICP备案编号不能为空!");
	    			return resultMap;
		    	}
		    	
		    	if(map.get("contName")!=null){
		    		if( map.get("contName").toString().length()>64 ){
		    			resultMap.put("message", "第"+j+"行数据，商户联系人文字过长!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("contName").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，商户联系人包含非法字符!");
				    	return resultMap;
				    }
		    	}else{
		    		resultMap.put("message", "第"+j+"行数据，商户联系人不能为空!");
	    			return resultMap;
		    	}
		    	if(map.get("contPhone")!=null){
		    		if( !Validator.isMobile(map.get("contPhone").toString())&& !Validator.isPhone(map.get("contPhone").toString())  ){
		    			resultMap.put("message", "第"+j+"行数据，商户联系电话格式不正确!");
		    			return resultMap;
		    		}
		    	}else{
		    		resultMap.put("message", "第"+j+"行数据，商户联系电话不能为空!");
	    			return resultMap;
		    	}
		    	if(map.get("cusEmail")!=null){
		    		if(  !Validator.isEmail(map.get("cusEmail").toString()) ){
		    			resultMap.put("message", "第"+j+"行数据，商户E-mail格式不正确!");
		    			return resultMap;
		    		}
		    	}
		    	if(map.get("occurarea")!=null){
		    		if( map.get("occurarea").toString().length()>256 ){
		    			resultMap.put("message", "第"+j+"行数据，商户经营范围文字过长!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("occurarea").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，商户经营范围包含非法字符!");
				    	return resultMap;
				    }
		    	}else if( ("01".equals(map.get("cusNature"))  ||  "03".equals(map.get("cusNature")) )  && map.get("occurarea")==null   ){
		    		resultMap.put("message", "第"+j+"行数据，（实体或实体兼网络）商户经营范围不能为空!");
	    			return resultMap;
		    	}

		    	if(map.get("networkType")==null){
	    		 	resultMap.put("message", "第"+j+"行数据，清算网络不能为空!");
		    		return resultMap;
		    	}
		    	if(map.get("status")==null){
	    		 	resultMap.put("message", "第"+j+"行数据，商户状态不能为空!");
		    		return resultMap;
		    	}
		    	
		    	if(map.get("startTime")!=null){
		    		if( map.get("startTime").toString().length()>10 ){
		    			resultMap.put("message", "第"+j+"行数据，服务起始时间格式不正确!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("startTime").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，服务起始时间包含非法字符!");
				    	return resultMap;
				    }
		    	}else if( "01".equals(map.get("status")) &&   map.get("startTime")==null   ){
		    		resultMap.put("message", "第"+j+"行数据，（商户启用状态）服务起始时间不能为空!");
	    			return resultMap;
		    	}
		    	
		    	if(map.get("endTime")!=null){
		    		if( map.get("endTime").toString().length()>10 ){
		    			resultMap.put("message", "第"+j+"行数据，服务结束时间格式不正确!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("endTime").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，服务结束时间包含非法字符!");
				    	return resultMap;
				    }
		    	}else if(  ("02".equals(map.get("status"))  ||  "03".equals(map.get("status")) )  && map.get("endTime")==null ) {
		    		resultMap.put("message", "第"+j+"行数据，（商户关闭或注销状态）服务结束时间不能为空!");
	    			return resultMap;
		    	}
		    	
		    	if(map.get("riskStatus")==null){
	    		 	resultMap.put("message", "第"+j+"行数据，合规风险状况不能为空!");
		    		return resultMap;
		    	}
		    	
		    	if( ("01".equals(map.get("cusNature"))  ||  "03".equals(map.get("cusNature")) )  && map.get("openType")==null){
	    		 	resultMap.put("message", "第"+j+"行数据，（实体或实体兼网络商户）开放业务种类不能为空!");
		    		return resultMap;
		    	}
		    	
		    	if( ("01".equals(map.get("cusNature"))  ||  "03".equals(map.get("cusNature")) )  && map.get("chageType")==null){
	    		 	resultMap.put("message", "第"+j+"行数据，（实体或实体兼网络商户）计费类型不能为空!");
		    		return resultMap;
		    	}
		    	
		    	if(map.get("accountType")==null){
	    		 	resultMap.put("message", "第"+j+"行数据，支持账户类型不能为空!");
		    		return resultMap;
		    	}
		    	
		    	if(map.get("expandType")==null){
	    		 	resultMap.put("message", "第"+j+"行数据，拓展方式不能为空!");
		    		return resultMap;
		    	}
		    	
		    	if(map.get("outServiceName")!=null){
		    		if( map.get("outServiceName").toString().length()>64 ){
		    			resultMap.put("message", "第"+j+"行数据，外包服务机构名称文字过长");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("outServiceName").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，外包服务机构名称包含非法字符!");
				    	return resultMap;
				    }
		    	}else if( "02".equals(map.get("expandType")) && map.get("outServiceName")==null) {
		    		resultMap.put("message", "第"+j+"行数据，（拓展方式为外包）外包服务机构名称不能为空");
	    			return resultMap;
		    	}
		    	
		    	if( "02".equals(map.get("expandType")) && map.get("outServiceCardType")==null){
		    			resultMap.put("message", "第"+j+"行数据，（拓展方式为外包）外包服务机构法人证件类型不能为空!");
		    			return resultMap;
		    	}
		    	
		    	if( "02".equals(map.get("expandType")) && map.get("outServicelegCardType")==null){
		    			resultMap.put("message", "第"+j+"行数据，（拓展方式为外包）外包服务机构法定代表人证件类型不能为空!");
		    			return resultMap;
		    	}
		    	
		    	if(map.get("outServiceCardCode")!=null){
		    		if(  map.get("outServiceCardCode").toString().length()>64 ){
		    			resultMap.put("message", "第"+j+"行数据，外包服务机构法人证件号码文字过长!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("outServiceCardCode").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，外包服务机构法人证件号码包含非法字符!");
				    	return resultMap;
				    }
		    	}else if( "02".equals(map.get("expandType")) && map.get("outServiceCardCode")==null) {
		    		resultMap.put("message", "第"+j+"行数据，（拓展方式为外包）外包服务机构法人证件号码不能为空");
	    			return resultMap;
		    	}
		    	
		    	
		    	
		    	if(map.get("outServicelegCardCode")!=null){
		    		if(  map.get("outServicelegCardCode").toString().length()>64 ){
		    			resultMap.put("message", "第"+j+"行数据，外包服务机构法定代表人证件号码文字过长!");
		    			return resultMap;
		    		}else if( "01".equals(map.get("outServicelegCardType")) && !Validator.isIDCard2(map.get("outServicelegCardCode").toString()) ){
		    			resultMap.put("message", "第"+j+"行数据，外包服务机构法定代表人身份证格式不正确!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("outServicelegCardCode").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，外包服务机构法定代表人证件号码包含非法字符!");
				    	return resultMap;
				    }
		    	}else if( "02".equals(map.get("expandType")) && map.get("outServicelegCardCode")==null) {
		    		resultMap.put("message", "第"+j+"行数据，（拓展方式为外包）外包服务机构法定代表人证件号码不能为空");
	    			return resultMap;
		    	}
		    	
		    	if(map.get("orgId")!=null){
		    		if( map.get("orgId").toString().length()>32 ){
		    			resultMap.put("message", "第"+j+"行数据，上报机构编号文字过长!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("orgId").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，上报机构编号包含非法字符!");
				    	return resultMap;
				    }
		    	}else{
		    		resultMap.put("message", "第"+j+"行数据，上报机构编号不能为空!");
	    			return resultMap;
		    	}
		    	if(map.get("fillerTime")!=null){
		    		if( map.get("fillerTime").toString().length()>19 ){
		    			resultMap.put("message", "第"+j+"行数据，填写日期格式不正确!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("fillerTime").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，填写日期包含非法字符!");
				    	return resultMap;
				    }
		    	}else{
		    		resultMap.put("message", "第"+j+"行数据，填写日期不能为空!");
	    			return resultMap;
		    	}
		    	if(map.get("filler")!=null){
		    		if( map.get("filler").toString().length()>10 ){
		    			resultMap.put("message", "第"+j+"行数据，填写人文字过长!");
		    			return resultMap;
		    		}else if ( ifHasInjectChar(map.get("fillerTime").toString()) ){
				    	resultMap.put("message", "第"+j+"行数据，填写人包含非法字符!");
				    	return resultMap;
				    }
		    	}else{
		    		resultMap.put("message", "第"+j+"行数据，填写人不能为空!");
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
	public static void main(String[] args ) throws Exception {
		String result = "{\"respInfo\":{\"resultStatus\":\"01\",\"resultCode\":\"S00000\"}}";
		JSONObject x = (JSONObject)JSONObject.parse(result);
		x.get("respInfo");
		System.out.println(x.toJSONString() );
	}
	
}
