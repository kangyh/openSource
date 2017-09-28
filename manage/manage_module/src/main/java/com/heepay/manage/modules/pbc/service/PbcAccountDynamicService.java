package com.heepay.manage.modules.pbc.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.heepay.billingutils.CreateDir;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.pbc.PbcAccountStatus;
import com.heepay.enums.pbc.PbcAccountType;
import com.heepay.enums.pbc.PbcBorrowingSigns;
import com.heepay.enums.pbc.PbcCurrencyType;
import com.heepay.enums.pbc.PbcDeviceType;
import com.heepay.enums.pbc.PbcPaymentAccountFeatureCodes;
import com.heepay.enums.pbc.PbcPaymentType;
import com.heepay.enums.pbc.PbcTransDetailAccountType;
import com.heepay.enums.pbc.PbcTransType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.pbc.dao.PbcAccountDynamicDao;
import com.heepay.manage.modules.pbc.dao.PbcFeedBackDao;
import com.heepay.manage.modules.pbc.entity.PbcAccountDynamic;
import com.heepay.manage.modules.pbc.entity.PbcFeedBack;

/***
 * 
* 
* 描    述：账户动态
*
* 创 建 者：wangl
* 创建时间：  Dec 9, 20165:52:09 PM
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
public class PbcAccountDynamicService  extends CrudService<PbcAccountDynamicDao, PbcAccountDynamic>{
   
	@Autowired
	private PbcAccountDynamicDao pbcAccountDynamicDao;

	@Autowired 
	private PbcFeedBackService pbcFeedBackService;

	@Autowired
	private PbcFeedBackDao pbcFeedBackDao;
	
	
	@Autowired
	private Excel2013POI excel2013POI;//解析文件
	
	@Autowired
	private Excel2003POI excel2003POI;//解析文件
	
	private static PropertiesLoader loader = new PropertiesLoader("riskPbc.properties");
	
	/**
	 * 
	 * @方法说明：根据id获取对象
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	public PbcAccountDynamic getEntityById(int differId) {
		
		return pbcAccountDynamicDao.getEntityById(differId);
	}
	
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(PbcAccountDynamic pbcAccountDynamic) {
		
		pbcAccountDynamicDao.updateEntity(pbcAccountDynamic);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void saveEntity(PbcAccountDynamic pbcAccountDynamic){
		pbcAccountDynamicDao.saveEntity(pbcAccountDynamic);
	}

	/**
	 * 
	 * @方法说明：根据反馈id获取信息
	 * @时间：2016年12月17日 下午2:18:00
	 * @创建人：wangdong
	 */
	public PbcAccountDynamic getFeeBackId(Long feedBackId) {
		return pbcAccountDynamicDao.getFeeBackId(feedBackId);
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：获取账户交易信息
	 * @时间：2016年12月20日 下午4:34:45
	 * @创建人：wangdong
	 */
	public Model findPbcAccountDynamicPage(Page<PbcAccountDynamic> page, PbcAccountDynamic pbcAccountDynamic,
			Model model) throws Exception {
		try {
			Page<PbcAccountDynamic> pagePbcAccountDynamic = findPage(page, pbcAccountDynamic);
			List<PbcAccountDynamic> pbcAccountDynamicList = Lists.newArrayList();
			String applicationId = null;
			for(PbcAccountDynamic pbc : pagePbcAccountDynamic.getList()){
				applicationId = pbc.getApplicationCode();
				pbcAccountDynamicList.add(pbc);
			}
			pagePbcAccountDynamic.setList(pbcAccountDynamicList);
			
			model.addAttribute("pbcFeedBack", pbcFeedBackService.getEntityByApplicationCode(applicationId));
			model.addAttribute("page", pagePbcAccountDynamic);
			model.addAttribute("pbcAccountDynamic",pbcAccountDynamic);
			return model;
		} catch (Exception e) {
			logger.error("PbcFeedBackService findPbcFeedBackPage has a error:{获取反馈表信息List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	@Transactional(readOnly = false)
	public String loadModel(MultipartFile file, String paymentAccountId,String yes) throws IOException {
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
					try {
						List<Map> readExcel2003 = excel2003POI.readExcel2003(file);
						if(StringUtils.isNotBlank(yes)){//用于判断是不是 单笔交易的上传数据，是就执行
							for (Map map : readExcel2003) {
								map.put("applicationCode", paymentAccountId); //商户号
								pbcAccountDynamicDao.saveMap(map);
							}
						}else{
							for (Map map : readExcel2003) {
								map.put("accountNumber", paymentAccountId); //商户号
								pbcAccountDynamicDao.saveMap(map);
							}
						}
						 //pbcAccountDetailSubjectDao.insertValue(readExcel2003);
						return "文件上传成功";
					} catch (Exception e) {
						logger.error("文件上传失败------>{文件上传入库失败}"+e.getMessage());
						return "文件上传入库失败,请修复";
					}
				}else if(lastIndexfilename.equalsIgnoreCase(".xlsx")){
					
					try {
						//List<List<String>> readXlsx = excel2013POI.readXlsx(file);
						List<Map> readExcel2007 = excel2013POI.readExcel2007(file);
						if(StringUtils.isNotBlank(yes)){//用于判断是不是 单笔交易的上传数据，是就执行
							for (Map map : readExcel2007) {
								map.put("applicationCode", paymentAccountId); //商户号
								pbcAccountDynamicDao.saveMap(map);
							}
						}else{
							for (Map map : readExcel2007) {
								map.put("accountNumber", paymentAccountId); //商户号
								pbcAccountDynamicDao.saveMap(map);
							}
						}
						return "文件上传成功";
					} catch (Exception e) {
						logger.error("文件上传失败------>{文件上传入库失败}"+e.getMessage());
						return "文件上传入库失败,请修复";
					}
			}
	}
		return null;
	}

	/**
	 * 
	 * @方法说明：查询商户关联的银行卡
	 * @时间：Dec 19, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public Model findRiskProductQuotaPage(Page<PbcAccountDynamic> page,PbcAccountDynamic pbcAccountDynamic, Model model, String paymentAccountId) {
		logger.info("分润明细查询数据为------>{}"+ page.getList());
		//查询页面数据
		Page<PbcAccountDynamic> pbcAccountDetail = findPage(page,pbcAccountDynamic);
		
		//数据类型转换
		List<PbcAccountDynamic> clearingCRList = Lists.newArrayList();
		for (PbcAccountDynamic clearingCR : pbcAccountDetail.getList()) {
				//支付账户类型(1-个人帐号;2-企业帐号)
				if(StringUtils.isNotBlank(clearingCR.getSubjectType())){
					clearingCR.setSubjectType(PbcAccountType.labelOf(clearingCR.getSubjectType()));
				}
				
				//币种(CNY或RMB人民币、USD美元、EUR欧元…)
				if(StringUtils.isNotBlank(clearingCR.getCurrency())){
					clearingCR.setCurrency(PbcCurrencyType.labelOf(clearingCR.getCurrency()));
				}
				//账户类型(01-个人一类账户;02-个人二类账户;  03-个人三类账户;04-商户类;09-其他)
				if(StringUtils.isNotBlank(clearingCR.getAccountType())){
					clearingCR.setAccountType(PbcTransDetailAccountType.labelOf(clearingCR.getAccountType()));
				}
				//账户状态（1-正常;2-止付;3-只收不付冻结;4-金额冻结；9-其他）
				if(StringUtils.isNotBlank(clearingCR.getAccountStatus())){
					clearingCR.setAccountStatus(PbcAccountStatus.labelOf(clearingCR.getAccountStatus()));
				}
				//支付类型(  1-网关交易  2-快捷交易  3-POS收单  4-支付机构内其他交易)
				if(StringUtils.isNotBlank(clearingCR.getPaymentType())){
					clearingCR.setPaymentType(PbcPaymentType.labelOf(clearingCR.getPaymentType()));
				}
				//交易类型( 1-支付账户充值; 2-支付账户对支付账户转账; 3-支付账户提现/转账至银行卡 4-支付账户消费; 5-代收 6-代付 9-其他)
				if(StringUtils.isNotBlank(clearingCR.getTransactionType())){
					clearingCR.setTransactionType(PbcTransType.labelOf(clearingCR.getTransactionType()));
				}
				
				//交易主体的出入账标识(0-出账;1-入账)
				if(StringUtils.isNotBlank(clearingCR.getBorrowingSigns())){
					clearingCR.setBorrowingSigns(PbcBorrowingSigns.labelOf(clearingCR.getBorrowingSigns()));
				}
				
				//交易币种(CNY或RMB人民币、USD美元、EUR欧元…)
				if(StringUtils.isNotBlank(clearingCR.getTransCurrency())){
					clearingCR.setTransCurrency(PbcCurrencyType.labelOf(clearingCR.getTransCurrency()));
				}
				//交易设备类型(1-电脑;2-手机;3-其他)
				if(StringUtils.isNotBlank(clearingCR.getTransactionDeviceType())){
					clearingCR.setTransactionDeviceType(PbcDeviceType.labelOf(clearingCR.getTransactionDeviceType()));
				}
				
				clearingCRList.add(clearingCR);
			}
			pbcAccountDetail.setList(clearingCRList);
			
			List<EnumBean> pbcPaymentAccountFeatureCodes = Lists.newArrayList();
			//事件特征码
			for (PbcPaymentAccountFeatureCodes checkFlg : PbcPaymentAccountFeatureCodes.values()) {
				EnumBean ct = new EnumBean();
				ct.setValue(checkFlg.getValue());
				ct.setName(checkFlg.getContent());
				pbcPaymentAccountFeatureCodes.add(ct);
			}
			model.addAttribute("pbcPaymentAccountFeatureCodes", pbcPaymentAccountFeatureCodes);
			
			model.addAttribute("pbcAccountDynamic", pbcAccountDynamic);
			model.addAttribute("page", pbcAccountDetail);
			model.addAttribute("paymentAccountId", paymentAccountId);
			return model;
	}

	/**
	 * 
	 * @方法说明：根据用户查询出关联的银行卡信息
	 * @时间：Dec 22, 2016
	 * @创建人：wangl
	 */
	public List<PbcAccountDynamic> getListByAccountNumber(String merchantNumber) {
		
		return pbcAccountDynamicDao.getListByMerchantNumber(merchantNumber);
	}

	public Model pbcPaymentAccountPage(Page<PbcAccountDynamic> page, PbcAccountDynamic pbcAccountDynamic,
			Model model, String pageNo) {
		
		PbcFeedBack pbcFeedBack = pbcFeedBackService.getEntityById(pbcAccountDynamic.getFeedBackId());
		
		logger.info("关联全支付账号------>{查询开始}");
		if(StringUtils.isNotBlank(pageNo)){
			int pageno=Integer.parseInt(pageNo);
			page.setPageNo(pageno);
		}
		//设置分页显示多少条数据
		page.setPageSize(10);
		pbcAccountDynamic.setApplicationCode(pbcFeedBack.getApplicationId());//查询所有和这个反馈相关的账户
		
		Page<PbcAccountDynamic> pbcPaymentPage = findPage(page,pbcAccountDynamic);
		
		List<EnumBean> pbcPaymentAccountFeatureCodes = Lists.newArrayList();
		//事件特征码
		for (PbcPaymentAccountFeatureCodes checkFlg : PbcPaymentAccountFeatureCodes.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			pbcPaymentAccountFeatureCodes.add(ct);
		}
		model.addAttribute("pbcPaymentAccountFeatureCodes", pbcPaymentAccountFeatureCodes);
		
		
		pbcAccountDynamic.setToId(pbcFeedBack.getToId());//接收机构ID
		pbcAccountDynamic.setTxCode(pbcFeedBack.getTxCode());//交易类型编码
		pbcAccountDynamic.setTransSerialNumber(pbcFeedBack.getTransSerialNumber());//传输报文流水号
		pbcAccountDynamic.setApplicationId(pbcFeedBack.getApplicationId());//业务申请编号
		pbcAccountDynamic.setFeatureCodes(pbcFeedBack.getFeatureCodes());//事件特征码
		pbcAccountDynamic.setAbnormalDescribe(pbcFeedBack.getAbnormalDescribe());//异常事件描述(事件特征码为9999时必填)
		pbcAccountDynamic.setReportCodes(pbcFeedBack.getReportCodes());//上报机构编码
		pbcAccountDynamic.setOperatorName(pbcFeedBack.getOperatorName());//我经办人姓名
		pbcAccountDynamic.setOperatorPhoneNumber(pbcFeedBack.getOperatorPhoneNumber());//我方经办人电话
		pbcAccountDynamic.setStatus(pbcFeedBack.getStatus());//上报状态
		pbcAccountDynamic.setRiskStatus(pbcFeedBack.getRiskStatus());//风控审核状态
		pbcAccountDynamic.setFeedType(pbcFeedBack.getFeedType());//反馈类型   
		pbcAccountDynamic.setDealTime(pbcFeedBack.getDealTime()); //风控专员处理时间
		pbcAccountDynamic.setRiskOperator(pbcFeedBack.getRiskOperator()); //审核人
		pbcAccountDynamic.setRiskTime(pbcFeedBack.getRiskTime());//审核时间
		pbcAccountDynamic.setReportTime(pbcFeedBack.getReportTime());//上报时间
		pbcAccountDynamic.setFeedBackRemark(pbcFeedBack.getFeedbackRemark());  //查询反馈说明
		pbcAccountDynamic.setFailRemark(pbcFeedBack.getFailRemark());//上报失败原因
		pbcAccountDynamic.setRemark(pbcFeedBack.getRemark()); //风控审核备注
		pbcAccountDynamic.setRiskRemark(pbcFeedBack.getRiskRemark()); //审核备注  
		pbcAccountDynamic.setFeedBackOrgName(pbcFeedBack.getFeedBackOrgName());//反馈机构名称
		
		
		if(StringUtils.isNotBlank(pbcAccountDynamic.getNo())){
			pbcAccountDynamic.setNo("Y");//用于页面显示判断是否是查询调取数据
		}else{
			pbcAccountDynamic.setYes("Y");//用于页面显示判断是否是查询调取数据
		}
		
		//数据类型转换
		List<PbcAccountDynamic> clearingCRList = Lists.newArrayList();
		for (PbcAccountDynamic clearingCR : pbcPaymentPage.getList()) {
			//支付账户类型(1-个人帐号;2-企业帐号)
			if(StringUtils.isNotBlank(clearingCR.getSubjectType())){
				clearingCR.setSubjectType(PbcAccountType.labelOf(clearingCR.getSubjectType()));
			}
			
			//币种(CNY或RMB人民币、USD美元、EUR欧元…)
			if(StringUtils.isNotBlank(clearingCR.getCurrency())){
				clearingCR.setCurrency(PbcCurrencyType.labelOf(clearingCR.getCurrency()));
			}
			//账户类型(01-个人一类账户;02-个人二类账户;  03-个人三类账户;04-商户类;09-其他)
			if(StringUtils.isNotBlank(clearingCR.getAccountType())){
				clearingCR.setAccountType(PbcTransDetailAccountType.labelOf(clearingCR.getAccountType()));
			}
			//账户状态（1-正常;2-止付;3-只收不付冻结;4-金额冻结；9-其他）
			if(StringUtils.isNotBlank(clearingCR.getAccountStatus())){
				clearingCR.setAccountStatus(PbcAccountStatus.labelOf(clearingCR.getAccountStatus()));
			}
			//支付类型(  1-网关交易  2-快捷交易  3-POS收单  4-支付机构内其他交易)
			if(StringUtils.isNotBlank(clearingCR.getPaymentType())){
				clearingCR.setPaymentType(PbcPaymentType.labelOf(clearingCR.getPaymentType()));
			}
			//交易类型( 1-支付账户充值; 2-支付账户对支付账户转账; 3-支付账户提现/转账至银行卡 4-支付账户消费; 5-代收 6-代付 9-其他)
			if(StringUtils.isNotBlank(clearingCR.getTransactionType())){
				clearingCR.setTransactionType(PbcTransType.labelOf(clearingCR.getTransactionType()));
			}
			
			//交易主体的出入账标识(0-出账;1-入账)
			if(StringUtils.isNotBlank(clearingCR.getBorrowingSigns())){
				clearingCR.setBorrowingSigns(PbcBorrowingSigns.labelOf(clearingCR.getBorrowingSigns()));
			}
			
			//交易币种(CNY或RMB人民币、USD美元、EUR欧元…)
			if(StringUtils.isNotBlank(clearingCR.getTransCurrency())){
				clearingCR.setTransCurrency(PbcCurrencyType.labelOf(clearingCR.getTransCurrency()));
			}
			//交易设备类型(1-电脑;2-手机;3-其他)
			if(StringUtils.isNotBlank(clearingCR.getTransactionDeviceType())){
				clearingCR.setTransactionDeviceType(PbcDeviceType.labelOf(clearingCR.getTransactionDeviceType()));
			}
			
			clearingCRList.add(clearingCR);
		}
		pbcPaymentPage.setList(clearingCRList);
			
		model.addAttribute("pbcAccountDynamic", pbcAccountDynamic);
		model.addAttribute("feedBackId", pbcAccountDynamic.getFeedBackId());
		model.addAttribute("page", pbcPaymentPage);
		return model;
	}

	/**
	 * 
	 * @方法说明：查询批量子对象
	 * @时间：Dec 26, 2016
	 * @创建人：wangl
	 */
	public List<PbcAccountDynamic> getListByfeatureCode(String applicationCode) {
		
		return pbcAccountDynamicDao.getListByfeatureCode(applicationCode);
	}

	/**
	 * @方法说明：
	 * @时间：Jan 12, 20175:31:46 PM
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int deleteData(long feedBackId) {
		
		PbcFeedBack entityById = pbcFeedBackDao.getEntityById(feedBackId);
		String applicationId = entityById.getApplicationId();
		logger.info("可疑名单上报-异常消费支付账户--->{删除}--->{开始}");
		
		pbcAccountDynamicDao.deleteDataTwo(applicationId);
		
		//删除反馈表的反馈数据
		int num=pbcFeedBackDao.deleteData(feedBackId);
		logger.info("可疑名单上报-异常消费支付账户--->{删除关联全支付账号}--->{条件 feedBackId}="+feedBackId);
		return num;
	}

	
}