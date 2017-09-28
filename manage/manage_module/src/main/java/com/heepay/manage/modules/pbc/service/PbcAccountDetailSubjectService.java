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
import com.heepay.enums.pbc.PbcBankStatus;
import com.heepay.enums.pbc.PbcBindingDataType;
import com.heepay.enums.risk.RiskStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pbc.dao.PbcAccountDetailSubjectDao;
import com.heepay.manage.modules.pbc.entity.PbcAccountDetailSubject;

/***
 * 
* 
* 描    述：账户主体详情'
*
* 创 建 者：wangl
* 创建时间：  Dec 9, 20165:51:48 PM
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
public class PbcAccountDetailSubjectService  extends CrudService<PbcAccountDetailSubjectDao, PbcAccountDetailSubject>{
 
	@Autowired
	private PbcAccountDetailSubjectDao pbcAccountDetailSubjectDao;

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
	public PbcAccountDetailSubject getEntityById(int differId) {
		
		return pbcAccountDetailSubjectDao.getEntityById(differId);
	}
	
	/**
	 * 
	 * @方法说明：更新数据
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(PbcAccountDetailSubject pbcAccountDetailSubject) {
		
		pbcAccountDetailSubjectDao.updateEntity(pbcAccountDetailSubject);
	}
	
	/**
	 * 
	 * @方法说明：插入保存操作
	 * @时间：Dec 10, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void saveEntity(PbcAccountDetailSubject pbcAccountDetailSubject){
		pbcAccountDetailSubjectDao.saveEntity(pbcAccountDetailSubject);
	}

	/**
	 * 
	 * @方法说明：根据反馈id获取信息
	 * @时间：2016年12月17日 下午2:15:36
	 * @创建人：wangdong
	 */
	public PbcAccountDetailSubject getFeeBackId(Long feedBackId) {
		return pbcAccountDetailSubjectDao.getFeeBackId(feedBackId);
	}

	/**
	 * 
	 * @方法说明：查询商户关联的银行卡
	 * @时间：Dec 19, 2016
	 * @创建人：wangl
	 */
	public Model findRiskProductQuotaPage(Page<PbcAccountDetailSubject> page,PbcAccountDetailSubject pbcAccountDetailSubject, 
										  Model model,String paymentAccountId) {
		
		logger.info("分润明细查询数据为------>{}"+ page.getList());
		//查询页面数据
		Page<PbcAccountDetailSubject> pbcAccountDetail = findPage(page,pbcAccountDetailSubject);
		
		//数据类型
		List<PbcAccountDetailSubject> clearingCRList = Lists.newArrayList();
		for (PbcAccountDetailSubject clearingCR : pbcAccountDetail.getList()) {
			
				//银行卡类型
				if(StringUtils.isNotBlank(clearingCR.getCardType())){
					clearingCR.setCardType(RiskStatus.labelOf(clearingCR.getCardType()));
				}
				//银行卡认证状态（1-已认证;2-未认证;3-已失效）
				if(StringUtils.isNotBlank(clearingCR.getCardValidation())){
					clearingCR.setCardValidation(PbcBankStatus.labelOf(clearingCR.getCardValidation()));
				}
				//数据类型(  01-登录号，填写登录号；  02-QQ号，填写QQ号；  03-微信号，填写微信号；  04-邮箱，填写邮箱；  05-IP，填写IP；  06-设备号，填写设备号)
				if(StringUtils.isNotBlank(clearingCR.getBindingDataType())){
					clearingCR.setBindingDataType(PbcBindingDataType.labelOf(clearingCR.getBindingDataType()));
				}
				
				clearingCRList.add(clearingCR);
			}
			pbcAccountDetail.setList(clearingCRList);
			
		model.addAttribute("page", pbcAccountDetail);
		model.addAttribute("paymentAccountId", paymentAccountId);
		return model;
	}

	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	@Transactional(readOnly = false)
	public String loadModel(MultipartFile file,String paymentAccountId) throws IOException {
		
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
				
				try {
					List<Map> readExcel2003 = excel2003POI.readExcel2003(file);
					for (Map map : readExcel2003) {
						map.put("merchantNumber", paymentAccountId); //商户号
						pbcAccountDetailSubjectDao.saveMap(map);
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
						// pbcAccountDetailSubjectDao.insertValue(readExcel2007);
						for (Map map : readExcel2007) {
							map.put("merchantNumber", paymentAccountId); //商户号
							pbcAccountDetailSubjectDao.saveMap(map);
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
	 * @方法说明：根据用户查询出关联的银行卡信息
	 * @时间：Dec 22, 2016
	 * @创建人：wangl
	 */
	public List<PbcAccountDetailSubject> getListByMerchantNumber(String merchantNumber) {
		
		return pbcAccountDetailSubjectDao.getListByMerchantNumber(merchantNumber);
	}
	
}