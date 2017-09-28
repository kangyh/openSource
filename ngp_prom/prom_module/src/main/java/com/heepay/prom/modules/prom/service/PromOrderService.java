/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.service;

import com.heepay.billingutils.CreateDir;
import com.heepay.prom.common.persistence.Page;
import com.heepay.prom.common.service.CrudService;
import com.heepay.prom.modules.prom.dao.PromOrderDao;
import com.heepay.prom.modules.prom.entity.PromOrder;
import com.heepay.prom.modules.prom.utils.Excel2003POI;
import com.heepay.prom.modules.prom.utils.Excel2013POI;
import com.heepay.prom.modules.prom.utils.ImportBathUtils;
import com.heepay.prom.modules.sys.utils.UserUtils;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 描    述：订单管理Service
 *
 * 创 建 者： @author wj
 * 创建时间：
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
public class PromOrderService extends CrudService<PromOrderDao, PromOrder> {
	
	@Autowired
	private PromOrderDao promOrderDao;
	@Autowired
	private Excel2013POI excel2013POI;//解析文件
	@Autowired
	private Excel2003POI excel2003POI;//解析文件
	
	public PromOrder get(String id) {
		return super.get(id);
	}
	
	public List<PromOrder> findList(PromOrder promOrder) {
		return super.findList(promOrder);
	}
	
	public Page<PromOrder> findPage(Page<PromOrder> page, PromOrder promOrder) {
		return super.findPage(page, promOrder);
	}
	
	public void save(PromOrder promOrder) {
		super.save(promOrder,false);
	}
	
	public void delete(PromOrder promOrder) {
		super.delete(promOrder);
	}
	
	@Transactional(readOnly = false)
	public void insert(PromOrder promOrder){
		promOrderDao.insert(promOrder);
	}
	
	@Transactional(readOnly = false)
	public PromOrder selectByOrderId(String orderId){
		return promOrderDao.selectByOrderId(orderId);
	}
	
	//上传文件
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	@Transactional(readOnly = false)
	public Map<String,Object> loadModel(MultipartFile file) {
		String lastIndex="";
    	String  filename="";
    	Map<String,Object> resultMap = null;resultMap = new HashMap<String,Object>() ;
		//上传文件
		if (!file.isEmpty()) {
			String path = "/hy/data/prom/order/";
			String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			path=path+format+"/";
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
						readExcel =  excel2003POI.readExcel2003ForPromOrder(file);
					}else if(lastIndexfilename.equalsIgnoreCase(".xlsx")){
						readExcel =  excel2013POI.readExcel2003ForPromOrder(file);
					}
					
					//insertValue = pbcPaymentAccountDao.insertValue(readExcel2003);
					StringBuffer reportIds = new StringBuffer();
					String bath = ImportBathUtils.getImportBath();
					for (int i=0;i<(readExcel==null?0:readExcel.size());i++) {
						errorIndex = i;
						if(readExcel.get(i)!=null){
							readExcel.get(i).put("orderId" ,   readExcel.get(i).get("orderId"));
							readExcel.get(i).put("warrantyId" ,   readExcel.get(i).get("warrantyId"));
							readExcel.get(i).put("spreadId" ,   readExcel.get(i).get("spreadId"));
							readExcel.get(i).put("spreadName" ,   readExcel.get(i).get("spreadName"));
							readExcel.get(i).put("productName" ,   readExcel.get(i).get("productName"));
							readExcel.get(i).put("spreadWay" ,   readExcel.get(i).get("spreadWay"));
							readExcel.get(i).put("commissionStatus" ,   readExcel.get(i).get("commissionStatus"));
							readExcel.get(i).put("cocerPeople" ,   readExcel.get(i).get("cocerPeople"));
							readExcel.get(i).put("coverAddress" ,   readExcel.get(i).get("coverAddress"));
							readExcel.get(i).put("spreadMoney" ,   readExcel.get(i).get("spreadMoney"));
							readExcel.get(i).put("effectTime" ,   readExcel.get(i).get("effectTime"));
							readExcel.get(i).put("companyName" ,   readExcel.get(i).get("companyName"));
							readExcel.get(i).put("orderBath" ,   bath);
							readExcel.get(i).put("dealTime" ,   new Date());
							readExcel.get(i).put("dealPeople" ,   UserUtils.getUser().getName());
							insertValue = promOrderDao.insertMapReport(readExcel.get(i));
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
	
}