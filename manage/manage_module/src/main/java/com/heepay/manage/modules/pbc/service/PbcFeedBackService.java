package com.heepay.manage.modules.pbc.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.enums.pbc.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.billingutils.CreateDir;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.importexcel.ReadExcel;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.pbc.dao.PbcFeedBackDao;
import com.heepay.manage.modules.pbc.entity.PbcAccountDetail;
import com.heepay.manage.modules.pbc.entity.PbcAccountDynamic;
import com.heepay.manage.modules.pbc.entity.PbcAccountInfo;
import com.heepay.manage.modules.pbc.entity.PbcAccountTransDetail;
import com.heepay.manage.modules.pbc.entity.PbcBankInfo;
import com.heepay.manage.modules.pbc.entity.PbcFeedBack;
import com.heepay.manage.modules.pbc.entity.PbcMeasureInfo;
import com.heepay.manage.modules.pbc.entity.PbcPaymentAccount;
import com.heepay.manage.modules.pbc.entity.PbcPaymentAccountBack;
import com.heepay.manage.modules.pbc.entity.PbcQueryInfo;
import com.heepay.manage.modules.pbc.entity.PbcReleaseFeedback;
import com.heepay.manage.modules.pbc.entity.PbcTransCardPaymentAccount;
import com.heepay.manage.modules.pbc.entity.PbcTransInfo;
import com.heepay.manage.modules.riskLogs.service.RiskLogsService;
import com.heepay.manage.modules.settle.service.util.ExcelService;
import com.heepay.manage.modules.sys.utils.UserUtils;

/***
 * 
* 
* 描    述：反馈表
*
* 创 建 者：wangl
* 创建时间：  Dec 9, 20165:51:19 PM
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
@Service
@Transactional(readOnly = true)
public class PbcFeedBackService  extends CrudService<PbcFeedBackDao, PbcFeedBack>{
   
	@Autowired
	private PbcFeedBackDao pbcFeedBackDao;
	
	@Autowired
	private ExcelService excelService;
	
	@Autowired
	private Excel2013POI excel2013POI;//解析文件
	
	@Autowired
	private Excel2003POI excel2003POI;//解析文件
	
	private static PropertiesLoader loader = new PropertiesLoader("riskPbc.properties");

	@Autowired
	private PbcTransInfoService pbcTransInfoService;//交易信息
	@Autowired
	private PbcBankInfoService pbcBankInfoService;//银行卡
	@Autowired
	private PbcMeasureInfoService pbcMeasureInfoService;//措施信息
	@Autowired
	private PbcAccountInfoService pbcAccountInfoService;//账户信息
	@Autowired
	private PbcAccountDetailService pbcAccountDetailService;//账户主体详情
	@Autowired
	private PbcPaymentAccountBackService pbcPaymentAccountBackService;//关联全支付账号信息
	@Autowired
	private PbcTransCardPaymentAccountService pbcTransCardPaymentAccountService;//按交易流水号查询银行卡/支付账号信息
	@Autowired
	private PbcReleaseFeedbackService pbcReleaseFeedbackService;
	@Autowired
	private PbcQueryInfoService pbcQueryInfoService;
	@Autowired
	private RiskLogsService riskLogsService;
	/**
	 * 
	 * @方法说明：获取反馈表信息List
	 * @时间：2016年12月16日 下午8:23:07
	 * @创建人：wangdong
	 */
	public List<PbcFeedBack> findList(PbcFeedBack PbcFeedBack) {
		return super.findList(PbcFeedBack);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void saveEntity(PbcFeedBack PbcFeedBack){
		
		pbcFeedBackDao.save(PbcFeedBack);
	}
	
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int updateEntity(PbcFeedBack PbcFeedBack) {
		
		return pbcFeedBackDao.updateEntity(PbcFeedBack);
	}

	/**
	 * 
	 * @方法说明：获取实体类主键查询
	 * @时间：2016年12月16日 下午8:23:07
	 * @创建人：wangdong
	 */
	public PbcFeedBack getEntityById(Long feedBackId) {
		return pbcFeedBackDao.getEntityById(feedBackId);
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：获取反馈表信息List
	 * @时间：2016年12月16日 下午8:24:59
	 * @创建人：wangdong
	 */
	public Model findPbcFeedBackPage(Page<PbcFeedBack> page, PbcFeedBack PbcFeedBack, Model model) throws Exception {
		try {
			Page<PbcFeedBack> pagePbcFeedBack = findPage(page, PbcFeedBack);
			List<PbcFeedBack> pbcFeedBackList = Lists.newArrayList();
			for(PbcFeedBack pbc : pagePbcFeedBack.getList()){
				//参数类型
				if(StringUtils.isNotBlank(pbc.getParamType())){
					if (StringUtils.equals(pbc.getRequestEventType(), RequestEventType.CASE_REPORT.getValue())) {
						// 支付账户交易明细查询
						pbc.setParamType(PbcStaticParamType.labelOf(pbc.getParamType()));
					}else if (StringUtils.equals(pbc.getRequestEventType(),RequestEventType.ABNORMAL_ACCOUNTS.getValue())) {
						// 账户主体详情查询
						pbc.setParamType(PbcStaticParamType.labelOf(pbc.getParamType()));
					}else if (StringUtils.equals(pbc.getRequestEventType(),RequestEventType.ACCOUNTS_INVOLVED.getValue())) {
						// 账户动态查询
						pbc.setParamType(PbcDynamicParamType.labelOf(pbc.getParamType()));
					}else if (StringUtils.equals(pbc.getRequestEventType(),RequestEventType.EXCEPTION_EVENTS.getValue())) {
						// 账户动态查询解除
					}else if (StringUtils.equals(pbc.getRequestEventType(),RequestEventType.TRANSNO_ACCOUNT.getValue())) {
						// 按交易流水号查询银行卡/支付帐号
						pbc.setParamType(PbcTransCardParamType.labelOf(pbc.getParamType()));
					}else if (StringUtils.equals(pbc.getRequestEventType(),RequestEventType.PAYMENY_ACCOUNT.getValue())) {
						// 关联全支付账号查询
						pbc.setParamType(PbcAccountParamType.labelOf(pbc.getParamType()));
					}
				}
				//请求事件类型
				if(StringUtils.isNotBlank(pbc.getRequestEventType())){
					pbc.setRequestEventType(RequestEventType.labelOf(pbc.getRequestEventType()));
				}
				//上报状态
				if(StringUtils.isNotBlank(pbc.getStatus())){
					pbc.setStatus(ReportStatus.labelOf(pbc.getStatus()));
				}
				//风控审核状态
				if(StringUtils.isNotBlank(pbc.getRiskStatus())){
					pbc.setRiskStatus(RiskAuditStatus.labelOf(pbc.getRiskStatus()));
				}
				
				pbcFeedBackList.add(pbc);
			}
			pagePbcFeedBack.setList(pbcFeedBackList);
			
			List<EnumBean> rSList = Lists.newArrayList();
			int num = 0;
			for (RiskAuditStatus rStatus : RiskAuditStatus.values()) {
				num++;
				if(num<=2){
					EnumBean ct = new EnumBean();
					ct.setValue(rStatus.getValue());
					ct.setName(rStatus.getContent());
					rSList.add(ct);
				}
			}
			model.addAttribute("rSList", rSList);
			
			List<EnumBean> rEList = Lists.newArrayList();
			for (RequestEventType rStatus : RequestEventType.values()) {
				EnumBean ct = new EnumBean();
				ct.setValue(rStatus.getValue());
				ct.setName(rStatus.getContent());
				rEList.add(ct);
			}
			model.addAttribute("rEList", rEList);

			List<EnumBean> rSsList = Lists.newArrayList();
			for (ReportStatus rStatus : ReportStatus.values()) {
				EnumBean ct = new EnumBean();
				ct.setValue(rStatus.getValue());
				ct.setName(rStatus.getContent());
				rSsList.add(ct);
			}
			model.addAttribute("rSsList", rSsList);
			
			model.addAttribute("page", pagePbcFeedBack);
			model.addAttribute("PbcFeedBack",PbcFeedBack);
			return model;
		} catch (Exception e) {
			logger.error("PbcFeedBackService findPbcFeedBackPage has a error:{获取反馈表信息List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @方法说明：反馈信息导出
	 * @时间：2016年12月23日17:05:38
	 * @创建人：wangdong
	 */
	public void export(PbcFeedBack pbcFeedBack, HttpServletRequest request,HttpServletResponse response) throws Exception{
		//数据库查询结果
		List<Map<String,Object>> pbcFeedBackList = pbcFeedBackDao.findListExcel(pbcFeedBack);
		//导出Excel表格标题行
		String[] headerArray = {"传输报文流水号","业务申请编号","申请时间","请求事件类型","申请机构编码","申请机构名称","参数类型","传入参数","上报状态","上报失败原因","风控审核状态"};
		//导出表格对应的字段名称
		String[] showField = {"trans_serial_number","application_id","application_time","request_event_type","application_org_code","application_org_name","param_type","params","status","fail_remark","risk_status"};
		try {
			excelService.exportExcel("反馈表信息", headerArray,pbcFeedBackList,showField,response,request,true);
		} catch (Exception e) {
			logger.error("PbcFeedBackService export has a error:{反馈信息导出出错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @方法说明：上传文件和入库
	 * @时间：2016年12月24日 下午3:26:46
	 * @创建人：wangdong
	 */
	@Transactional(readOnly = false)
	public void upLoadFileAndInsert(MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			String flg = request.getParameter("flg");
			String applicationId = request.getParameter("applicationId");
			if (StringUtils.isNotBlank(flg) && StringUtils.isNotBlank(applicationId)) {
				if (StringUtils.equals(flg, "bank")) {//银行卡信息
					List<Map<String,Object>> list = ReadExcel.readExcel(file, loader, new PbcBankInfo());
					for(Map<String,Object> map : list){
						map.put("applicationCode", applicationId);
						pbcBankInfoService.saveMap(map);
						riskLogsService.savaEntity("插入", "银行卡信息插入:业务申请编号:"+applicationId, request);
					}
				}
				if (StringUtils.equals(flg, "trans")) {//交易流水
					List<Map<String,Object>> list = ReadExcel.readExcel(file, loader, new PbcTransInfo());
					for(Map<String,Object> map : list){
						if(null != map.get("currency") && "" != map.get("currency") && StringUtils.isNotBlank(map.get("currency").toString())){
							map.put("currency",map.get("currency").toString().toUpperCase());
						}
						map.put("applicationCode", applicationId);
						pbcTransInfoService.saveMap(map);
						riskLogsService.savaEntity("插入", "交易流水信息插入:业务申请编号:"+applicationId, request);
					}
				}
				if (StringUtils.equals(flg, "measure")) {//措施信息
					List<Map<String,Object>> list = ReadExcel.readExcel(file, loader, new PbcMeasureInfo());
					for(Map<String,Object> map : list){
						map.put("applicationCode", applicationId);
						if(null != map.get("currency") && "" != map.get("currency") && StringUtils.isNotBlank(map.get("currency").toString())){
							map.put("currency",map.get("currency").toString().toUpperCase());
						}
						pbcMeasureInfoService.saveMap(map);
						riskLogsService.savaEntity("插入", "措施信息插入:业务申请编号:"+applicationId, request);
					}
				}
				if (StringUtils.equals(flg, "transAccount")) {//账户信息
					List<Map<String,Object>> list = ReadExcel.readExcel(file, loader, new PbcAccountInfo());
					for(Map<String,Object> map : list){
						if(null != map.get("currency") && "" != map.get("currency") && StringUtils.isNotBlank(map.get("currency").toString())){
							map.put("currency",map.get("currency").toString().toUpperCase());
						}
						map.put("applicationCode", applicationId);
						pbcAccountInfoService.saveMap(map);
						riskLogsService.savaEntity("插入", "账户信息插入:业务申请编号:"+applicationId, request);
					}
				}
				if (StringUtils.equals(flg, "accountDetail")) {//账户主体详情
					List<Map<String,Object>> list = ReadExcel.readExcel(file, loader, new PbcAccountDetail());
					PbcQueryInfo pbcQueryInfo = pbcQueryInfoService.getEntityByApplicationId(applicationId);
					for(Map<String,Object> map : list){
						map.put("applicationCode", applicationId);
						map.put("data", pbcQueryInfo.getData());
						map.put("dataType", pbcQueryInfo.getDataType());
						pbcAccountDetailService.saveMap(map);
						riskLogsService.savaEntity("插入", "账户主体详情信息插入:业务申请编号:"+applicationId, request);
					}
				}
				if (StringUtils.equals(flg, "paymenyId")) {//关联全支付信息
					List<Map<String,Object>> list = ReadExcel.readExcel(file, loader, new PbcPaymentAccountBack());
					for(Map<String,Object> map : list){
						map.put("applicationCode", applicationId);
						pbcPaymentAccountBackService.saveMap(map);
						riskLogsService.savaEntity("插入", "关联全支付信息插入:业务申请编号:"+applicationId, request);
					}
				}
				if (StringUtils.equals(flg, "bankCark")) {//按交易流水号查询银行卡/支付账号信息
					List<Map<String,Object>> list = ReadExcel.readExcel(file, loader, new PbcTransCardPaymentAccount());
					for(Map<String,Object> map : list){
						if(null != map.get("currency") && "" != map.get("currency") && StringUtils.isNotBlank(map.get("currency").toString())){
							map.put("currency",map.get("currency").toString().toUpperCase());
						}
						map.put("applicationCode", applicationId);
						pbcTransCardPaymentAccountService.saveMap(map);
						riskLogsService.savaEntity("插入", "按交易流水号查询银行卡/支付账号信息插入:业务申请编号:"+applicationId, request);
					}
				}
				if (StringUtils.equals(flg, "remove")) {//动态解除
					List<Map<String,Object>> list = ReadExcel.readExcel(file, loader, new PbcReleaseFeedback());
					for(Map<String,Object> map : list){
						map.put("applicationCode", applicationId);
						pbcReleaseFeedbackService.saveMap(map);
						riskLogsService.savaEntity("插入", "动态解除信息插入:业务申请编号:"+applicationId, request);
					}
				}
				//更新状态为待审核
				PbcFeedBack pbcFeedBack = pbcFeedBackDao.getEntityByApplicationCode(applicationId);
				PbcFeedBack updatePbcFeedBack = new PbcFeedBack();
				updatePbcFeedBack.setRiskStatus(RiskAuditStatus.TQS_D.getValue());
				updatePbcFeedBack.setFeedBackId(pbcFeedBack.getFeedBackId());
				pbcFeedBackDao.updateEntity(updatePbcFeedBack);
				riskLogsService.savaEntity("更新", "反馈信息主表更新:业务申请编号:"+applicationId+",数据ID:"+pbcFeedBack.getFeedBackId(), request);
			}
		} catch (Exception e) {
			logger.error("PbcFeedBackService upLoadFileAndInsert has a error:{上传文件和入库出错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	
	
	/**
	 * 
	 * @throws Exception 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@SuppressWarnings({ "static-access", "rawtypes" })
	@Transactional(readOnly = false)
	public String saveEntity(PbcFeedBack PbcFeedBack, 
							 HttpServletRequest request,
							 RedirectAttributes redirectAttributes, 
							 Model model,String inter,String addValue,MultipartFile file) throws Exception{
		
		//User user = UserUtils.getUser();
		//String name = user.getName();
		//PbcFeedBack.setCreateUser(name);
		//PbcFeedBack.setStatus(Constants.STATIC_Y);
		//PbcFeedBack.setCreateTime(new Date());

		int num =0;
		if(StringUtils.isNotBlank(inter)){//上传按钮，调取网关接口把数据传给网关，然后存到数据库
			
			logger.info("上传操作,调取网关接口把数据传给网关,然后存到数据库----->{风控系统电信反诈骗}}"+inter);
		}
		if(StringUtils.isNotBlank(addValue)){//添加页面入库
			
			logger.info("添加页面入库操作----->{风控系统电信反诈骗}"+addValue);
			
			
			try {//添加交易信息
				/*num = pbcTransactionDao.saveEntity(pbcTransaction);
				Long pbcId = PbcFeedBack.getPbcId();
				System.out.println(pbcId);
				*/
				if(num !=0){
					logger.info("反馈表------>{插入成功}"+PbcFeedBack.toString());
					return  "反馈表插入成功";
				}
			} catch (Exception e) {
				logger.error("反馈表------>{插入失败}"+e.getMessage());
				return  "反馈表插入失败";
			}
			
		}else{// 普通数据入库
			
			logger.info("普通添加页面入库操作----->{风控系统电信反诈骗}");
			if(PbcEventType.ACCOUNTS_INVOLVED.getValue().equals(PbcFeedBack.getChangeType())){ // PbcFeedBack.getAccountType()
				logger.info("可疑名单上报-涉案账户页面入库操作----->{风控系统电信反诈骗}"+PbcFeedBack.toString());
				
				String lastIndex="";
            	String  filename="";
            	
				//上传文件
				if (!file.isEmpty()) {
					String path = loader.getProperty("localPath");
					String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					path=path+"/"+format+"/";
					boolean flag = CreateDir.createLiunxDir(path);
					if(!flag){
	            		logger.error("路径创建失败-------->{}",!flag);
						return "路径创建失败";
	            	}
					 filename = file.getOriginalFilename();
	            	long size = file.getSize();
	            	
	            	
	            	//根据截取的值是否是-1判断是否是组合命名
	            	int of = filename.lastIndexOf(".");
	            	if(of != -1){
						lastIndex = filename.substring(of);
						
						//判断这个是不是标准的Excel表格
						filename ="电信反诈骗"+format+lastIndex;
						logger.error("路径创建名称-------->{}",filename);
					}else {
						
						return "上传文件格式错误";
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
	                
	                InputStream is=file.getInputStream();
	                FileOutputStream fos = new FileOutputStream(path);
	                
					try {
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
						while((length = is.read(buffer))>0){
						    //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
						    fos.write(buffer, 0, length);
						}
					}finally{
						is.close();
		                fos.close();
					}
					logger.info("文件上传成功");
					//addMessage(redirectAttributes, "文件上传成功");
					int index = filename.lastIndexOf(".");
					String lastIndexfilename = filename.substring(index);
					if(lastIndexfilename.equalsIgnoreCase(".xls")){
						int insertValue=0;
						try {
							List<Map> readExcel2003 = excel2003POI.readExcel2003(file);
							for (Map map : readExcel2003) {
								System.out.println(map);
							}
							insertValue = pbcFeedBackDao.insertValue(readExcel2003);
						} catch (Exception e) {
							logger.error("文件上传失败------>{文件上传入库失败}"+e.getMessage());
						}
						
						if(insertValue==0){
							return "文件上传入库失败,请修复";
						}
						
						}else if(lastIndexfilename.equalsIgnoreCase(".xlsx")){
							//List<List<String>> readXlsx = excel2013POI.readXlsx(file);
							List<Map> readExcel2007 = excel2013POI.readExcel2007(file);
							for (Map map : readExcel2007) {
								System.out.println(map);
							}
							
					}
						
					
				}
				
				
				
				try {
					/*num = pbcFeedbackDao.saveEntity(PbcFeedBack);
					Long pbcId = PbcFeedBack.getPbcId();//插入时返回的主键id
					
					PbcPerAccount pbcPerAccount=new PbcPerAccount();//个人账户表保存数据
					pbcPerAccount.setFeedbackId(pbcId);//反馈ID
					pbcPerAccount.setAccountType(PbcFeedBack.getAccountType());//账户类型
					pbcPerAccount.setAccountName(PbcFeedBack.getAccountName());//查询账户名
					pbcPerAccount.setAccountOwnerName(PbcFeedBack.getAccountOwnerName());//户主姓名
					pbcPerAccount.setAccountOwnerIdType(PbcFeedBack.getAccountOwnerIdType());//户主证件类型
					pbcPerAccount.setAccountOwnerId(PbcFeedBack.getAccountOwnerId());//户主证件号
					pbcPerAccount.setPhoneNumber(PbcFeedBack.getPhoneNumber());//联系手机号
					pbcPerAccount.setAddress(PbcFeedBack.getAddress());//常用通讯地址
					pbcPerAccount.setPostCode(PbcFeedBack.getPostCode());//邮政编码
					pbcPerAccount.setCreateUser(name);//创建者
					pbcPerAccount.setCreateTime(new Date());//创建时间
					pbcPerAccount.setStatus(Constants.STATIC_Y);//状态
*/					if(StringUtils.isNotBlank(filename)){
						//pbcPerAccount.setLocalPath(filename);//上传地址
					}
					try {//案件举报-添加案件信息
						//num = pbcFeedBackDao.saveEntity(pbcPerAccount);
						
						if(num !=0){
							logger.info("个人账户表插入成功------>{风控系统电信反诈骗}"+PbcFeedBack.toString());
							return "插入成功";
						}
					} catch (Exception e) {
						logger.error("个人账户表插入失败------>{风控系统电信反诈骗}"+e.getMessage());
						return  "个人账户表插入失败";
					}
				} catch (Exception e) {
					logger.error("反馈表插入失败------>{风控系统电信反诈骗}"+e.getMessage());
					return   "反馈表插入失败";
				}
				
			}else{
				try {//案件举报-添加案件信息
					//num = pbcFeedBackDao.saveEntity(pbcFeedBack);
					//Long pbcId = PbcFeedBack.getPbcId();
					//System.out.println(pbcId);
					if(num !=0){
						logger.info("反馈表------>{插入成功}"+PbcFeedBack.toString());
						return  "反馈表插入成功";
					}
				} catch (Exception e) {
					logger.error("反馈表------>{插入失败}"+e.getMessage());
					return  "反馈表插入失败";
				}
			}
		}
		
		return null;
	}

	/**
	 * 
	 * @方法说明：数据入库操作
	 * @时间：Dec 19, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int saveEntityByPaymentAccount(PbcPaymentAccount pbcPaymentAccount) {
		
		PbcFeedBack pbcFeedBack=new PbcFeedBack();
		
		pbcFeedBack.setToId(pbcPaymentAccount.getToId());//接收机构ID
		pbcFeedBack.setTxCode(pbcPaymentAccount.getTxCode());//交易类型编码
		pbcFeedBack.setTransSerialNumber(pbcPaymentAccount.getTransSerialNumber());//传输报文流水号
		pbcFeedBack.setApplicationId(pbcPaymentAccount.getApplicationId());//业务申请编号
		pbcFeedBack.setFeatureCodes(pbcPaymentAccount.getFeatureCodes());//事件特征码
		pbcFeedBack.setAbnormalDescribe(pbcPaymentAccount.getAbnormalDescribe());//异常事件描述(事件特征码为9999时必填)
		pbcFeedBack.setReportCodes(pbcPaymentAccount.getReportCodes());//上报机构编码
		pbcFeedBack.setOperatorName(pbcPaymentAccount.getOperatorName());//我经办人姓名
		pbcFeedBack.setOperatorPhoneNumber(pbcPaymentAccount.getOperatorPhoneNumber());//我方经办人电话
		pbcFeedBack.setStatus(pbcPaymentAccount.getStatus());//上报状态
		pbcFeedBack.setRiskStatus(pbcPaymentAccount.getRiskStatus());//风控审核状态
		pbcFeedBack.setFeedType(pbcPaymentAccount.getFeedType());//反馈类型   
		pbcFeedBack.setDealTime(pbcPaymentAccount.getDealTime()); //风控专员处理时间
		pbcFeedBack.setRiskOperator(pbcPaymentAccount.getRiskOperator()); //审核人
		pbcFeedBack.setRiskTime(pbcPaymentAccount.getRiskTime());//审核时间
		pbcFeedBack.setReportTime(pbcPaymentAccount.getReportTime());//上报时间
		pbcFeedBack.setFeedBackRemark(pbcPaymentAccount.getFeedBackRemark());  //查询反馈说明
		pbcFeedBack.setFailRemark(pbcPaymentAccount.getFailRemark());//上报失败原因
		pbcFeedBack.setRemark(pbcPaymentAccount.getRemark()); //风控审核备注
		pbcFeedBack.setStatus(ReportStatus.NOREPORTED.getValue());//未上报
		pbcFeedBack.setFeedBackOrgName(pbcPaymentAccount.getFeedBackOrgName());//反馈机构名称
		pbcFeedBack.setApplicationTime(new Date());//申请时间
		pbcFeedBack.setRiskRemark(pbcPaymentAccount.getRiskRemark()); //风控审核备注
		
		//查询过来的数据更新到数据库中
		//int feedBackId= (int)pbcPaymentAccount.getFeedBackId();
		if(pbcPaymentAccount.getFeedBackId()>0){
			try {
				//更行这条数据
				pbcFeedBack.setFeedBackId(pbcPaymentAccount.getFeedBackId());
				int updateEntity = pbcFeedBackDao.updateEntity(pbcFeedBack);
				logger.info("反馈表------>{更新成功}"+pbcFeedBack.toString());
				return updateEntity;
			} catch (Exception e) {
				logger.error("反馈表------>{更新失败}"+e.getMessage());
				return 0;
			}
		}else{
			try {
				int saveEntity = pbcFeedBackDao.saveEntity(pbcFeedBack);
				logger.info("反馈表------>{插入成功}"+pbcFeedBack.toString());
				
				return saveEntity;
			} catch (Exception e) {
				logger.error("个人账户表插入失败------>{风控系统电信反诈骗}"+e.getMessage());
				return 0;
				
			}
		}
		
	}

	/**
	 * 
	 * @方法说明：根据业务申请编码获取实体
	 * @时间：2016年12月21日 上午10:43:34
	 * @创建人：wangdong
	 */
	public PbcFeedBack getEntityByApplicationCode(String applicationId) {
		return pbcFeedBackDao.getEntityByApplicationCode(applicationId);
	}

	/**
	 * 
	 * @方法说明：可疑交易上报-单笔交易上报 类型的反馈入库
	 * @时间：Dec 21, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int saveEntityByPaymentAccount(PbcAccountTransDetail pbcAccountTransDetail) {
		PbcFeedBack pbcFeedBack=new PbcFeedBack();
		
		pbcFeedBack.setToId(pbcAccountTransDetail.getToId());//接收机构ID
		pbcFeedBack.setTxCode(pbcAccountTransDetail.getTxCode());//交易类型编码
		pbcFeedBack.setTransSerialNumber(pbcAccountTransDetail.getTransSerialNumber());//传输报文流水号
		pbcFeedBack.setApplicationId(pbcAccountTransDetail.getApplicationId());//业务申请编号
		pbcFeedBack.setFeatureCodes(pbcAccountTransDetail.getFeatureCodes());//事件特征码
		pbcFeedBack.setAbnormalDescribe(pbcAccountTransDetail.getAbnormalDescribe());//异常事件描述(事件特征码为9999时必填)
		pbcFeedBack.setReportCodes(pbcAccountTransDetail.getReportCodes());//上报机构编码
		pbcFeedBack.setOperatorName(pbcAccountTransDetail.getOperatorName());//我经办人姓名
		pbcFeedBack.setOperatorPhoneNumber(pbcAccountTransDetail.getOperatorPhoneNumber());//我方经办人电话
		pbcFeedBack.setStatus(pbcAccountTransDetail.getStatus());//上报状态
		pbcFeedBack.setRiskStatus(pbcAccountTransDetail.getRiskStatus());//风控审核状态
		pbcFeedBack.setFeedType(pbcAccountTransDetail.getFeedType());//反馈类型   
		pbcFeedBack.setDealTime(pbcAccountTransDetail.getDealTime()); //风控专员处理时间
		pbcFeedBack.setRiskOperator(pbcAccountTransDetail.getRiskOperator()); //审核人
		pbcFeedBack.setRiskTime(pbcAccountTransDetail.getRiskTime());//审核时间
		pbcFeedBack.setReportTime(pbcAccountTransDetail.getReportTime());//上报时间
		pbcFeedBack.setFeedBackRemark(pbcAccountTransDetail.getFeedBackRemark());  //查询反馈说明
		pbcFeedBack.setFailRemark(pbcAccountTransDetail.getFailRemark());//上报失败原因
		pbcFeedBack.setRemark(pbcAccountTransDetail.getRemark()); //风控审核备注
		
		try {
			int saveEntity = pbcFeedBackDao.saveEntity(pbcFeedBack);
			logger.info("反馈表------>{插入成功}"+pbcFeedBack.toString());
			
			return saveEntity;
		} catch (Exception e) {
			logger.error("个人账户表插入失败------>{风控系统电信反诈骗}"+e.getMessage());
			return 0;
			
		}
		
	}
/*
	*//**
	 * 
	 * @方法说明：getEntityById
	 * @时间：Dec 21, 2016
	 * @创建人：wangl
	 *//*
	public Model getEntity(long feedBackIds,Model model,Page<PbcPaymentAccount> page, 
								PbcPaymentAccount pbcPaymentAccount,
								String pageNo) {
		//根据id查询出对象
		PbcFeedBack pbcFeedBack = pbcFeedBackDao.getEntityById(feedBackIds);
		
		
		logger.info("关联全支付账号------>{查询开始}");
		if(StringUtils.isNotBlank(pageNo)){
			int pageno=Integer.parseInt(pageNo);
			page.setPageNo(pageno);
		}
		 page.setPageSize(10);
		 
		 page = findPage(page,pbcPaymentAccount);
		//page.setPageSize(10);
		
		
		//映射所有的值到 PbcPaymentAccount对象
		PbcPaymentAccount pbcPayment=new  PbcPaymentAccount();
		 
		pbcPayment.setToId(pbcFeedBack.getToId());//接收机构ID
		pbcPayment.setTransTypeCode(pbcFeedBack.getTransTypeCode());//交易类型编码
		pbcPayment.setTransSerialNumber(pbcFeedBack.getTransSerialNumber());//传输报文流水号
		pbcPayment.setApplicationId(pbcFeedBack.getApplicationId());//业务申请编号
		pbcPayment.setFeatureCodes(pbcFeedBack.getFeatureCodes());//事件特征码
		pbcPayment.setAbnormalDescribe(pbcFeedBack.getAbnormalDescribe());//异常事件描述(事件特征码为9999时必填)
		pbcPayment.setReportCodes(pbcFeedBack.getReportCodes());//上报机构编码
		pbcPayment.setOperatorName(pbcFeedBack.getOperatorName());//我经办人姓名
		pbcPayment.setOperatorPhoneNumber(pbcFeedBack.getOperatorPhoneNumber());//我方经办人电话
		pbcPayment.setStatus(pbcFeedBack.getStatus());//上报状态
		pbcPayment.setRiskStatus(pbcFeedBack.getRiskStatus());//风控审核状态
		pbcPayment.setFeedType(pbcFeedBack.getFeedType());//反馈类型   
		pbcPayment.setDealTime(pbcFeedBack.getDealTime()); //风控专员处理时间
		pbcPayment.setRiskOperator(pbcFeedBack.getRiskOperator()); //审核人
		pbcPayment.setRiskTime(pbcFeedBack.getRiskTime());//审核时间
		pbcPayment.setReportTime(pbcFeedBack.getReportTime());//上报时间
		pbcPayment.setFeedBackRemark(pbcFeedBack.getFeedBackRemark());  //查询反馈说明
		pbcPayment.setFailRemark(pbcFeedBack.getFailRemark());//上报失败原因
		pbcPayment.setRemark(pbcFeedBack.getRemark()); //风控审核备注
	
		pbcPayment.setYes("Y");//用于页面显示判断是否是查询调取数据
		
		model.addAttribute("pbcPaymentAccount", pbcPayment);
		return model;
	}

	*/

	@Transactional(readOnly = false)
	public int saveEntityByFeedBank(PbcAccountDynamic pbcAccountDynamic) {
			
			PbcFeedBack pbcFeedBack=new PbcFeedBack();
			
			pbcFeedBack.setToId(pbcAccountDynamic.getToId());//接收机构ID
			pbcFeedBack.setTxCode(pbcAccountDynamic.getTxCode());//交易类型编码
			pbcFeedBack.setTransSerialNumber(pbcAccountDynamic.getTransSerialNumber());//传输报文流水号
			pbcFeedBack.setApplicationId(pbcAccountDynamic.getApplicationId());//业务申请编号
			pbcFeedBack.setFeatureCodes(pbcAccountDynamic.getFeatureCodes());//事件特征码
			pbcFeedBack.setAbnormalDescribe(pbcAccountDynamic.getAbnormalDescribe());//异常事件描述(事件特征码为9999时必填)
			pbcFeedBack.setReportCodes(pbcAccountDynamic.getReportCodes());//上报机构编码
			pbcFeedBack.setOperatorName(pbcAccountDynamic.getOperatorName());//我经办人姓名
			pbcFeedBack.setOperatorPhoneNumber(pbcAccountDynamic.getOperatorPhoneNumber());//我方经办人电话
			pbcFeedBack.setStatus(pbcAccountDynamic.getStatus());//上报状态
			pbcFeedBack.setRiskStatus(pbcAccountDynamic.getRiskStatus());//风控审核状态
			pbcFeedBack.setFeedType(pbcAccountDynamic.getFeedType());//反馈类型   
			pbcFeedBack.setDealTime(pbcAccountDynamic.getDealTime()); //风控专员处理时间
			pbcFeedBack.setRiskOperator(pbcAccountDynamic.getRiskOperator()); //审核人
			pbcFeedBack.setRiskTime(pbcAccountDynamic.getRiskTime());//审核时间
			pbcFeedBack.setReportTime(pbcAccountDynamic.getReportTime());//上报时间
			pbcFeedBack.setFeedBackRemark(pbcAccountDynamic.getFeedBackRemark());  //查询反馈说明
			pbcFeedBack.setFailRemark(pbcAccountDynamic.getFailRemark());//上报失败原因
			pbcFeedBack.setRemark(pbcAccountDynamic.getRemark()); //风控审核备注
			pbcFeedBack.setStatus(ReportStatus.NOREPORTED.getValue());//未上报
			pbcFeedBack.setFeedBackOrgName(pbcAccountDynamic.getFeedBackOrgName());//反馈机构名称
			pbcFeedBack.setApplicationTime(new Date());//申请时间
			pbcFeedBack.setRiskRemark(pbcAccountDynamic.getRiskRemark()); //风控审核备注
			
			//查询过来的数据更新到数据库中
			//int feedBackId= (int)pbcAccountDynamic.getFeedBackId();
			if(pbcAccountDynamic.getFeedBackId()>0){
				try {
					//更行这条数据
					pbcFeedBack.setFeedBackId(pbcAccountDynamic.getFeedBackId());
					int updateEntity = pbcFeedBackDao.updateEntity(pbcFeedBack);
					logger.info("反馈表------>{更新成功}"+pbcFeedBack.toString());
					return updateEntity;
				} catch (Exception e) {
					logger.error("反馈表------>{更新失败}"+e.getMessage());
					return 0;
				}
			}else{
				try {
					int saveEntity = pbcFeedBackDao.saveEntity(pbcFeedBack);
					logger.info("反馈表------>{插入成功}"+pbcFeedBack.toString());
					
					return saveEntity;
				} catch (Exception e) {
					logger.error("个人账户表插入失败------>{风控系统电信反诈骗}"+e.getMessage());
					return 0;
					
				}
			}
		}

	/**
	 * 
	 * @方法说明：页面审核时 选择失败后需要入库
	 * @时间：Dec 27, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int updateEntityByLoad(PbcPaymentAccount pbcPaymentAccount) {
		
		PbcFeedBack pbcFeedBack=new PbcFeedBack();
		pbcFeedBack.setFeedBackRemark(pbcPaymentAccount.getFeedBackRemark());//查询反馈说明
		pbcFeedBack.setAbnormalDescribe(pbcPaymentAccount.getAbnormalDescribe());//异常事件描述(事件特征码为9999时必填)  feed_back_remark
		pbcFeedBack.setFeedBackRemark(pbcPaymentAccount.getFeedBackRemark());  //查询反馈说明
		pbcFeedBack.setRiskRemark(pbcPaymentAccount.getRiskRemark()); //风控审核备注
		pbcFeedBack.setRiskStatus(pbcPaymentAccount.getRiskStatus());//风控审核状态
		//pbcFeedBack.setStatus(ReportStatus.FREPORTED.getValue());//上报失败
		
		pbcFeedBack.setDealTime(new Date());//风控专员处理时间
		pbcFeedBack.setRiskOperator(UserUtils.getUser().getName());//风控审核人
		pbcFeedBack.setFeedBackId(pbcPaymentAccount.getFeedBackId()); //id 主键
		
		int updateEntity = pbcFeedBackDao.updateEntity(pbcFeedBack);
		return updateEntity;
	}

	/**
	 * 
	 * @方法说明：页面审核时 选择失败后需要入库  单笔交易的页面
	 * @时间：Dec 27, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int updateEntityByLoadTwo(PbcAccountDynamic pbcAccountDynamic) {
		
		PbcFeedBack pbcFeedBack=new PbcFeedBack();
		pbcFeedBack.setFeedBackRemark(pbcAccountDynamic.getFeedBackRemark());//查询反馈说明
		pbcFeedBack.setAbnormalDescribe(pbcAccountDynamic.getAbnormalDescribe());//异常事件描述(事件特征码为9999时必填)
		pbcFeedBack.setFeedBackRemark(pbcAccountDynamic.getFeedBackRemark());  //查询反馈说明
		pbcFeedBack.setRiskRemark(pbcAccountDynamic.getRiskRemark()); //风控审核备注
		pbcFeedBack.setRiskStatus(pbcAccountDynamic.getRiskStatus());//风控审核状态
		
		
		pbcFeedBack.setDealTime(new Date());//风控专员处理时间
		pbcFeedBack.setRiskOperator(UserUtils.getUser().getName());//风控审核人
		pbcFeedBack.setFeedBackId(pbcAccountDynamic.getFeedBackId()); //id 主键
		
		int updateEntity = pbcFeedBackDao.updateEntity(pbcFeedBack);
		return updateEntity;
	}

}