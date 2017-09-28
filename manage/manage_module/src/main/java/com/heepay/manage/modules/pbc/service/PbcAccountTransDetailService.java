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
import com.heepay.enums.pbc.PbcPaymentAccountFeatureCodes;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.pbc.dao.PbcAccountTransDetailDao;
import com.heepay.manage.modules.pbc.dao.PbcFeedBackDao;
import com.heepay.manage.modules.pbc.entity.PbcAccountDynamic;
import com.heepay.manage.modules.pbc.entity.PbcAccountTransDetail;
import com.heepay.manage.modules.pbc.entity.PbcFeedBack;

/***
 * 
* 
* 描    述：账户交易明细
*
* 创 建 者：wangl
* 创建时间：  Dec 9, 20165:55:05 PM
* 创建描述：
* 
* 修 改 者：  
* 修改时间： 
* 修改描述： 
* 
* 审 核 者：
* 审核时间：
* 审核描述：
*s
 */
@Service
@Transactional(readOnly = true)
public class PbcAccountTransDetailService  extends CrudService<PbcAccountTransDetailDao, PbcAccountTransDetail>{
    
	@Autowired
	private PbcAccountTransDetailDao pbcAccountTransDetailDao;
	
	@Autowired
	private PbcFeedBackDao pbcFeedBackDao;
	
	@Autowired
	private PbcFeedBackService pbcFeedBackService;

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
	public PbcAccountTransDetail getEntityById(int differId) {
		
		return pbcAccountTransDetailDao.getEntityById(differId);
	}
	
	/**
	 * 
	 * @方法说明：根据反馈id查询账户交易明细信息
	 * @时间：2016年12月17日 下午1:23:33
	 * @创建人：wangdong
	 */
	public PbcAccountTransDetail getFeeBackId(Long feedBackId){
		return pbcAccountTransDetailDao.getFeeBackId(feedBackId);
	}
	
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int updateEntity(PbcAccountTransDetail pbcAccountTransDetail) {
		
		return pbcAccountTransDetailDao.updateEntity(pbcAccountTransDetail);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int saveEntity(PbcAccountTransDetail pbcAccountTransDetail){
		
		return pbcAccountTransDetailDao.saveEntity(pbcAccountTransDetail);
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：获取反馈信息中的账户交易明细信息
	 * @时间：2016年12月19日 下午2:42:48
	 * @创建人：wangdong
	 */
	public Model findPbcAccountTransDetailPage(Page<PbcAccountTransDetail> page, PbcAccountTransDetail pbcAccountTransDetail, Model model) throws Exception {
		try {
			Page<PbcAccountTransDetail> pagePbcAccountTransDetail = findPage(page, pbcAccountTransDetail);
			List<PbcAccountTransDetail> pbcAccountTransDetailList = Lists.newArrayList();
			String applicationId = null;
			for(PbcAccountTransDetail pbc : pagePbcAccountTransDetail.getList()){
				applicationId = pbc.getApplicationCode();
				pbcAccountTransDetailList.add(pbc);
			}
			pagePbcAccountTransDetail.setList(pbcAccountTransDetailList);
			
			model.addAttribute("pbcFeedBack", pbcFeedBackService.getEntityByApplicationCode(applicationId));
			model.addAttribute("page", pagePbcAccountTransDetail);
			model.addAttribute("pbcAccountTransDetail",pbcAccountTransDetail);
			return model;
		} catch (Exception e) {
			logger.error("PbcAccountTransDetailService findPbcAccountTransDetailPage has a error:{获取反馈信息中的账户交易明细信息出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @方法说明：可疑交易上报-单笔交易上报 页面分页显示
	 * @时间：Dec 21, 2016
	 * @创建人：wangl
	 */
	public Model findRiskProductQuotaPage(Page<PbcAccountTransDetail> page, PbcAccountTransDetail pbcAccountTransDetail, Model model, String pageNo) {

		logger.info("关联全支付账号------>{查询开始}"+pbcAccountTransDetail.toString());
		if(StringUtils.isNotBlank(pageNo)){
			int pageno=Integer.parseInt(pageNo);
			page.setPageNo(pageno);
		}
		 page.setPageSize(10);
		Page<PbcAccountTransDetail> pbcPayment = findPage(page,pbcAccountTransDetail);
		//pbcPayment.setPageSize(10);
		//page.setCount(list.size());
		/*int num=pbcPayment.getList().size();
        if (page.getPageNo() * page.getPageSize() > num) {
            page.setList(pbcPayment.getList().subList((page.getPageNo() - 1) * page.getPageSize(), num));
        } else {
            page.setList(pbcPayment.getList().subList((page.getPageNo() - 1) * page.getPageSize(), page.getPageNo() * page.getPageSize()));
        }*/
		
		
		List<EnumBean> pbcPaymentAccountFeatureCodes = Lists.newArrayList();
		//事件特征码
		for (PbcPaymentAccountFeatureCodes checkFlg : PbcPaymentAccountFeatureCodes.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			pbcPaymentAccountFeatureCodes.add(ct);
		}
		model.addAttribute("pbcPaymentAccountFeatureCodes", pbcPaymentAccountFeatureCodes);
		
		
		model.addAttribute("pbcAccountTransDetail", pbcAccountTransDetail);
		model.addAttribute("page", pbcPayment);
		return model;
	}
	

	@SuppressWarnings({ "rawtypes", "static-access", "unchecked" })
	@Transactional(readOnly = false)
	public String loadModel(MultipartFile file,String applicationId) throws IOException {
		
	logger.info("可疑交易上报-单笔交易上报入库操作----->{风控系统电信反诈骗}");
			
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
						map.put("applicationCode", applicationId);
						//将导入的数据关联到这个反馈记录
						insertValue = pbcAccountTransDetailDao.saveEntityToMap(map);
					}
					//insertValue = pbcAccountTransDetailDao.insertValue(readExcel2003);
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
						//insertValue = pbcAccountTransDetailDao.insertValue(readExcel2007);
						for (Map map : readExcel2007) {
							map.put("applicationCode", applicationId);
							//将导入的数据关联到这个反馈记录
							insertValue = pbcAccountTransDetailDao.saveEntityToMap(map);
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
	public Model pbcPaymentAccountPage(Page<PbcAccountTransDetail> page, PbcAccountTransDetail pbcAccountTransDetail,
			Model model, String pageNo) {
		
		PbcFeedBack pbcFeedBack = pbcFeedBackDao.getEntityById(pbcAccountTransDetail.getFeedBackId());
		logger.info("关联全支付账号------>{查询开始}"+pbcAccountTransDetail.toString());
		if(StringUtils.isNotBlank(pageNo)){
			int pageno=Integer.parseInt(pageNo);
			page.setPageNo(pageno);
		}
		//设置分页显示多少条数据
		page.setPageSize(10);
		pbcAccountTransDetail.setApplicationCode(pbcFeedBack.getApplicationId());//查询所有和这个反馈相关的账户
		
		Page<PbcAccountTransDetail> pbcPaymentPage = findPage(page,pbcAccountTransDetail);
		
		List<EnumBean> pbcPaymentAccountFeatureCodes = Lists.newArrayList();
		//事件特征码
		for (PbcPaymentAccountFeatureCodes checkFlg : PbcPaymentAccountFeatureCodes.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			pbcPaymentAccountFeatureCodes.add(ct);
		}
		model.addAttribute("pbcPaymentAccountFeatureCodes", pbcPaymentAccountFeatureCodes);
		
		
		pbcAccountTransDetail.setToId(pbcFeedBack.getToId());//接收机构ID
		pbcAccountTransDetail.setTxCode(pbcFeedBack.getTxCode());//交易类型编码
		pbcAccountTransDetail.setTransSerialNumber(pbcFeedBack.getTransSerialNumber());//传输报文流水号
		pbcAccountTransDetail.setApplicationId(pbcFeedBack.getApplicationId());//业务申请编号
		pbcAccountTransDetail.setFeatureCodes(pbcFeedBack.getFeatureCodes());//事件特征码
		pbcAccountTransDetail.setAbnormalDescribe(pbcFeedBack.getAbnormalDescribe());//异常事件描述(事件特征码为9999时必填)
		pbcAccountTransDetail.setReportCodes(pbcFeedBack.getReportCodes());//上报机构编码
		pbcAccountTransDetail.setOperatorName(pbcFeedBack.getOperatorName());//我经办人姓名
		pbcAccountTransDetail.setOperatorPhoneNumber(pbcFeedBack.getOperatorPhoneNumber());//我方经办人电话
		pbcAccountTransDetail.setStatus(pbcFeedBack.getStatus());//上报状态
		pbcAccountTransDetail.setRiskStatus(pbcFeedBack.getRiskStatus());//风控审核状态
		pbcAccountTransDetail.setFeedType(pbcFeedBack.getFeedType());//反馈类型   
		pbcAccountTransDetail.setDealTime(pbcFeedBack.getDealTime()); //风控专员处理时间
		pbcAccountTransDetail.setRiskOperator(pbcFeedBack.getRiskOperator()); //审核人
		pbcAccountTransDetail.setRiskTime(pbcFeedBack.getRiskTime());//审核时间
		pbcAccountTransDetail.setReportTime(pbcFeedBack.getReportTime());//上报时间
		pbcAccountTransDetail.setFeedBackRemark(pbcFeedBack.getFeedBackRemark());  //查询反馈说明
		pbcAccountTransDetail.setFailRemark(pbcFeedBack.getFailRemark());//上报失败原因
		pbcAccountTransDetail.setRemark(pbcFeedBack.getRemark()); //风控审核备注
	
		
		
		if(StringUtils.isNotBlank(pbcAccountTransDetail.getNo())){
			pbcAccountTransDetail.setNo("Y");//用于页面显示判断是否是查询调取数据
		}else{
			pbcAccountTransDetail.setYes("Y");//用于页面显示判断是否是查询调取数据
		}
		
		model.addAttribute("pbcAccountTransDetail", pbcAccountTransDetail);
		model.addAttribute("feedBackId", pbcAccountTransDetail.getFeedBackId());
		model.addAttribute("page", pbcPaymentPage);
		return model;
	}

	/**
	 * 
	 * @方法说明：根据用户查询出关联的银行卡信息
	 * @时间：Dec 22, 2016
	 * @创建人：wangl
	 */
	public List<PbcAccountTransDetail> getListByfeatureCode(String applicationCode) {
		
		return pbcAccountTransDetailDao.getListByfeatureCode(applicationCode);
	}

	
}