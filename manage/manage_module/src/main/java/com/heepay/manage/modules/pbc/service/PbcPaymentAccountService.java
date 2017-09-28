package com.heepay.manage.modules.pbc.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.google.common.collect.Lists;
import com.heepay.billingutils.CreateDir;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.pbc.PbcBankStatus;
import com.heepay.enums.pbc.PbcCardType;
import com.heepay.enums.pbc.PbcCurrencyType;
import com.heepay.enums.pbc.PbcDynamicParamType;
import com.heepay.enums.pbc.PbcMeasureType;
import com.heepay.enums.pbc.PbcPaymentAccountFeatureCodes;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.pbc.dao.PbcAccountDetailSubjectDao;
import com.heepay.manage.modules.pbc.dao.PbcAccountDynamicDao;
import com.heepay.manage.modules.pbc.dao.PbcFeedBackDao;
import com.heepay.manage.modules.pbc.dao.PbcPaymentAccountDao;
import com.heepay.manage.modules.pbc.entity.PbcFeedBack;
import com.heepay.manage.modules.pbc.entity.PbcPaymentAccount;

/***
 * 
* 
* 描    述：关联全支付账号
*
* 创 建 者：wangl
* 创建时间：  Dec 9, 20165:49:53 PM
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
public class PbcPaymentAccountService  extends CrudService<PbcPaymentAccountDao, PbcPaymentAccount>{
  
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private PbcPaymentAccountDao pbcPaymentAccountDao;
	
	@Autowired
	private PbcFeedBackDao pbcFeedBackDao;
	
	//账户交易明细表pbc_account_dynamic
	@Autowired
	private PbcAccountDynamicDao pbcAccountDynamicDao;
	
	//账户主体详情表
	@Autowired
	private PbcAccountDetailSubjectDao pbcAccountDetailSubjectDao;
	
	@Autowired
	private Excel2013POI excel2013POI;//解析文件
	
	@Autowired
	private Excel2003POI excel2003POI;//解析文件
	
	private static PropertiesLoader loader = new PropertiesLoader("riskPbc.properties");
	
	/**
	 * 
	 * @方法说明：//查询所有记录list
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public List<PbcPaymentAccount> findList(PbcPaymentAccount pbcPaymentAccount) {
		return super.findList(pbcPaymentAccount);
	}
	
	

	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public PbcPaymentAccount getEntityById(int differId) {
		
		return pbcPaymentAccountDao.getEntityById(differId);
	}
	
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void update(PbcPaymentAccount pbcPaymentAccount) {
		
		pbcPaymentAccountDao.update(pbcPaymentAccount);
	}
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(PbcPaymentAccount pbcPaymentAccount) {
		
		pbcPaymentAccountDao.updateEntity(pbcPaymentAccount);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void save(PbcPaymentAccount pbcPaymentAccount){
		pbcPaymentAccountDao.save(pbcPaymentAccount);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void saveEntity(PbcPaymentAccount pbcPaymentAccount){
		pbcPaymentAccountDao.saveEntity(pbcPaymentAccount);
	}

	/**
	 * 
	 * @方法说明：根据反馈id获取信息
	 * @时间：2016年12月17日 下午2:23:47
	 * @创建人：wangdong
	 */
	public PbcPaymentAccount getFeeBackId(Long feedBackId) {
		return pbcPaymentAccountDao.getFeeBackId(feedBackId);
	}


	/**
	 * @方法说明：
	 * @时间：Dec 17, 2016
	 * @创建人：wangl
	 */
	public Model findRiskProductQuotaPage(Page<PbcPaymentAccount> page, 
										  PbcPaymentAccount pbcPaymentAccount,
										  Model model,String pageNo) {
		
		logger.info("关联全支付账号------>{查询开始}"+pbcPaymentAccount.toString());
		if(StringUtils.isNotBlank(pageNo)){
			int pageno=Integer.parseInt(pageNo);
			page.setPageNo(pageno);
		}
		page.setPageSize(10);
		
		Page<PbcPaymentAccount> pbcPayment = findPage(page,pbcPaymentAccount);
		pbcPayment.setPageSize(10);
		//page.setCount(list.size());
		/*int num=pbcPayment.getList().size();
        if (page.getPageNo() * page.getPageSize() > num) {
            page.setList(pbcPayment.getList().subList((page.getPageNo() - 1) * page.getPageSize(), num));
        } else {
            page.setList(pbcPayment.getList().subList((page.getPageNo() - 1) * page.getPageSize(), page.getPageNo() * page.getPageSize()));
        }*/
		List<PbcPaymentAccount> clearingCRList = Lists.newArrayList();
		for (PbcPaymentAccount clearingCR : pbcPayment.getList()) {
			//支付账户类型(1-个人帐号;2-企业帐号)
			if(StringUtils.isNotBlank(clearingCR.getSubjectType())){
				clearingCR.setSubjectType(PbcDynamicParamType.labelOf(clearingCR.getSubjectType()));
			}
			
			//币种(CNY或RMB人民币、USD美元、EUR欧元…)
			if(StringUtils.isNotBlank(clearingCR.getCardType())){
				clearingCR.setCardType(PbcCardType.labelOf(clearingCR.getCardType()));
			}
			
			//银行卡类型（1-借记卡;2-贷记卡）
			if(StringUtils.isNotBlank(clearingCR.getCurrency())){
				clearingCR.setCurrency(PbcCurrencyType.labelOf(clearingCR.getCurrency()));
			}
			
			//银行卡认证状态（1-已认证;2-未认证;3-已失效）
			if(StringUtils.isNotBlank(clearingCR.getCardValidation())){
				clearingCR.setCardValidation(PbcBankStatus.labelOf(clearingCR.getCardValidation()));
			}
			//措施类型(1-只收不付;2-不收不付;  3-限额冻结; 9-其他)
			if(StringUtils.isNotBlank(clearingCR.getMeasureType())){
				clearingCR.setMeasureType(PbcMeasureType.labelOf(clearingCR.getMeasureType()));
			}
			
			clearingCRList.add(clearingCR);
		}
		pbcPayment.setList(clearingCRList);
			
		
		List<EnumBean> pbcPaymentAccountFeatureCodes = Lists.newArrayList();
		//事件特征码
		for (PbcPaymentAccountFeatureCodes checkFlg : PbcPaymentAccountFeatureCodes.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			pbcPaymentAccountFeatureCodes.add(ct);
		}
		model.addAttribute("pbcPaymentAccountFeatureCodes", pbcPaymentAccountFeatureCodes);
		
		model.addAttribute("feedBackId", pbcPaymentAccount.getFeedBackId());
		model.addAttribute("pbcPaymentAccount", pbcPaymentAccount);
		model.addAttribute("page", pbcPayment);
		return model;
	}



	/**
	 * 
	 * @throws Exception 
	 * @方法说明：上传文件入库
	 * @时间：Dec 19, 2016
	 * @创建人：wangl
	 */
	
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	@Transactional(readOnly = false)
	public String loadModel(MultipartFile file,String applicationId) throws Exception {
		
		logger.info("关联全支付账号入库操作----->{风控系统电信反诈骗}");
		
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
					//insertValue = pbcPaymentAccountDao.insertValue(readExcel2003);
					for (Map map : readExcel2003) {
						map.put("applicationCode", applicationId);
						//将导入的数据关联到这个反馈记录
						insertValue = pbcPaymentAccountDao.saveEntityToMap(map);
					}
					return "文件上传成功";
				} catch (Exception e) {
					logger.error("文件上传失败------>{文件上传入库失败}"+e.getMessage());
				}
				
				if(insertValue==0){
					return "文件上传入库失败,请修复";
				}
				
				}else if(lastIndexfilename.equalsIgnoreCase(".xlsx")){
					int insertValue=0;
					try {
						//List<List<String>> readXlsx = excel2013POI.readXlsx(file);
						List<Map> readExcel2007 = excel2013POI.readExcel2007(file);
						insertValue = pbcPaymentAccountDao.insertValue(readExcel2007);
						for (Map map : readExcel2007) {
							map.put("applicationCode", applicationId);
							//将导入的数据关联到这个反馈记录
							insertValue = pbcPaymentAccountDao.saveEntityToMap(map);
						}
						return "文件上传成功";
					} catch (Exception e) {
						logger.error("文件上传失败------>{文件上传入库失败}"+e.getMessage());
					}
					if(insertValue==0){
						return "文件上传入库失败,请修复";
					}
			}
	}
		return null;

	}



	/**
	 * 
	 * @方法说明： 审核和查看过来的分页显示
	 * @时间：Dec 23, 2016
	 * @创建人：wangl
	 */
	public Model pbcPaymentAccountPage(Page<PbcPaymentAccount> page, PbcPaymentAccount pbcPaymentAccount, Model model,String pageNo) {
		
		PbcFeedBack pbcFeedBack = pbcFeedBackDao.getEntityById(pbcPaymentAccount.getFeedBackId());
		
		logger.info("关联全支付账号------>{查询开始}"+pbcPaymentAccount.toString());
		if(StringUtils.isNotBlank(pageNo)){
			int pageno=Integer.parseInt(pageNo);
			page.setPageNo(pageno);
		}
		//设置分页显示多少条数据
		page.setPageSize(10);
		pbcPaymentAccount.setApplicationCode(pbcFeedBack.getApplicationId());//查询所有和这个反馈相关的账户
		Page<PbcPaymentAccount> pbcPaymentPage = findPage(page,pbcPaymentAccount);
		
		
		List<EnumBean> pbcPaymentAccountFeatureCodes = Lists.newArrayList();
		//事件特征码
		for (PbcPaymentAccountFeatureCodes checkFlg : PbcPaymentAccountFeatureCodes.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			pbcPaymentAccountFeatureCodes.add(ct);
		}
		model.addAttribute("pbcPaymentAccountFeatureCodes", pbcPaymentAccountFeatureCodes);
		
		
		/*//映射所有的值到 PbcPaymentAccount对象
		PbcPaymentAccount pbcPayment=new  PbcPaymentAccount();*/
		 
		pbcPaymentAccount.setToId(pbcFeedBack.getToId());//接收机构ID
		pbcPaymentAccount.setTxCode(pbcFeedBack.getTxCode());//交易类型编码
		pbcPaymentAccount.setTransSerialNumber(pbcFeedBack.getTransSerialNumber());//传输报文流水号
		pbcPaymentAccount.setApplicationId(pbcFeedBack.getApplicationId());//业务申请编号
		pbcPaymentAccount.setFeatureCodes(pbcFeedBack.getFeatureCodes());//事件特征码
		pbcPaymentAccount.setAbnormalDescribe(pbcFeedBack.getAbnormalDescribe());//异常事件描述(事件特征码为9999时必填)
		pbcPaymentAccount.setReportCodes(pbcFeedBack.getReportCodes());//上报机构编码
		pbcPaymentAccount.setOperatorName(pbcFeedBack.getOperatorName());//我经办人姓名
		pbcPaymentAccount.setOperatorPhoneNumber(pbcFeedBack.getOperatorPhoneNumber());//我方经办人电话
		pbcPaymentAccount.setStatus(pbcFeedBack.getStatus());//上报状态
		pbcPaymentAccount.setRiskStatus(pbcFeedBack.getRiskStatus());//风控审核状态
		pbcPaymentAccount.setFeedType(pbcFeedBack.getFeedType());//反馈类型   
		pbcPaymentAccount.setDealTime(pbcFeedBack.getDealTime()); //风控专员处理时间
		pbcPaymentAccount.setRiskOperator(pbcFeedBack.getRiskOperator()); //审核人
		pbcPaymentAccount.setRiskTime(pbcFeedBack.getRiskTime());//审核时间
		pbcPaymentAccount.setReportTime(pbcFeedBack.getReportTime());//上报时间
		pbcPaymentAccount.setFailRemark(pbcFeedBack.getFailRemark());//上报失败原因
		pbcPaymentAccount.setRemark(pbcFeedBack.getRemark()); //风控审核备注
		pbcPaymentAccount.setRiskRemark(pbcFeedBack.getRiskRemark()); //审核备注  
		pbcPaymentAccount.setFeedBackRemark(pbcFeedBack.getFeedbackRemark());  //查询反馈说明  
		pbcPaymentAccount.setFeedBackOrgName(pbcFeedBack.getFeedBackOrgName());  //反馈机构名称  
		
		List<PbcPaymentAccount> clearingCRList = Lists.newArrayList();
		for (PbcPaymentAccount clearingCR : pbcPaymentPage.getList()) {
			//支付账户类型(01-个人帐号;02-企业帐号)
			if(StringUtils.isNotBlank(clearingCR.getSubjectType())){
				clearingCR.setSubjectType(PbcDynamicParamType.labelOf(clearingCR.getSubjectType()));
			}
			
			//币种(CNY或RMB人民币、USD美元、EUR欧元…)
			if(StringUtils.isNotBlank(clearingCR.getCardType())){
				clearingCR.setCardType(PbcCardType.labelOf(clearingCR.getCardType()));
			}
			
			//银行卡类型（1-借记卡;2-贷记卡）
			if(StringUtils.isNotBlank(clearingCR.getCurrency())){
				clearingCR.setCurrency(PbcCurrencyType.labelOf(clearingCR.getCurrency()));
			}
			
			//银行卡认证状态（1-已认证;2-未认证;3-已失效）
			if(StringUtils.isNotBlank(clearingCR.getCardValidation())){
				clearingCR.setCardValidation(PbcBankStatus.labelOf(clearingCR.getCardValidation()));
			}
			//措施类型(1-只收不付;2-不收不付;  3-限额冻结; 9-其他)
			if(StringUtils.isNotBlank(clearingCR.getMeasureType())){
				clearingCR.setMeasureType(PbcMeasureType.labelOf(clearingCR.getMeasureType()));
			}
			
			clearingCRList.add(clearingCR);
			}
		pbcPaymentPage.setList(clearingCRList);
			
		
		if(StringUtils.isNotBlank(pbcPaymentAccount.getNo())){
			pbcPaymentAccount.setNo("Y");//用于页面显示判断是否是查询调取数据
		}else{
			pbcPaymentAccount.setYes("Y");//用于页面显示判断是否是查询调取数据
		}
		
		model.addAttribute("pbcPaymentAccount", pbcPaymentAccount);
		model.addAttribute("feedBackId", pbcPaymentAccount.getFeedBackId());
		model.addAttribute("page", pbcPaymentPage);
		return model;
	}



	/**
	 * 
	 * @方法说明：根据featureCode查询出所有数据发送给网关
	 * @时间：Dec 22, 2016
	 * @创建人：wangl
	 */
	public List<PbcPaymentAccount> getListByfeatureCode(String applicationCode) {
		
		return pbcPaymentAccountDao.getListByfeatureCode(applicationCode);
	}



	/**
	 * @方法说明：删除未上报的数据
	 * @时间：Jan 12, 20174:05:58 PM
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int deleteData(long feedBackId) {
		
		PbcFeedBack entityById = pbcFeedBackDao.getEntityById(feedBackId);
		
		String applicationId = entityById.getApplicationId();
		logger.info("单笔交易--->{删除}--->{开始}");
		
		List<PbcPaymentAccount> pbcPaymentAccount = pbcPaymentAccountDao.getListByfeatureCode(applicationId);
		if(!pbcPaymentAccount.isEmpty()){
			for (PbcPaymentAccount pbcPayment : pbcPaymentAccount) {
				logger.info("单笔交易--->{查询关联全支付账号}--->{结果}"+pbcPayment.toString());
				
				String accountNumber = pbcPayment.getAccountNumber();
				logger.info("单笔交易--->{删除业务申请编码}--->{条件 accountNumber}="+accountNumber);
				pbcAccountDetailSubjectDao.deleteData(accountNumber);
				
			}
		}
		pbcPaymentAccountDao.deleteData(applicationId);
		
		//删除反馈表的反馈数据
		int num=pbcFeedBackDao.deleteData(feedBackId);
		logger.info("单笔交易--->{删除关联全支付账号}--->{条件 feedBackId}="+feedBackId);
		return num;
	}



	/**
	 * @方法说明：删除未上报的数据
	 * @时间：Jan 12, 20175:10:02 PM
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int deleteDataTwo(long feedBackId) {

		PbcFeedBack entityById = pbcFeedBackDao.getEntityById(feedBackId);
		
		String applicationId = entityById.getApplicationId();
		logger.info("可疑名单上报-涉案账户/可疑名单上报-涉案账户历史明细--->{删除}--->{开始}");
		
		List<PbcPaymentAccount> pbcPaymentAccount = pbcPaymentAccountDao.getListByfeatureCode(applicationId);
		if(!pbcPaymentAccount.isEmpty()){
			for (PbcPaymentAccount pbcPayment : pbcPaymentAccount) {
				logger.info("可疑名单上报-涉案账户/可疑名单上报-涉案账户历史明细--->{查询关联全支付账号}--->{结果}"+pbcPayment.toString());
				
				String accountNumber = pbcPayment.getAccountNumber();
				logger.info("可疑名单上报-涉案账户/可疑名单上报-涉案账户历史明细--->{删除业务申请编码}--->{条件 accountNumber}="+accountNumber);
				pbcAccountDetailSubjectDao.deleteData(accountNumber);
				////账户交易明细表pbc_account_dynamic
				pbcAccountDynamicDao.deleteData(accountNumber);
				
			}
		}
		pbcPaymentAccountDao.deleteData(applicationId);
		
		//删除反馈表的反馈数据
		int num=pbcFeedBackDao.deleteData(feedBackId);
		logger.info("可疑名单上报-涉案账户/可疑名单上报-涉案账户历史明细--->{删除关联全支付账号}--->{条件 feedBackId}="+feedBackId);
		return num;
	}
}