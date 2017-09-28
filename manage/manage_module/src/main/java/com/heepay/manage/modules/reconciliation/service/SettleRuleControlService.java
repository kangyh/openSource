package com.heepay.manage.modules.reconciliation.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.heepay.manage.modules.reconciliation.util.AESCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.reconciliation.dao.SettleChannelLogDao;
import com.heepay.manage.modules.reconciliation.dao.SettleChannelManagerDao;
import com.heepay.manage.modules.reconciliation.dao.SettleRuleControlDao;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelLog;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;
import com.heepay.manage.modules.reconciliation.entity.SettleRuleControl;

/***
 * 
 * 
 * 描    述：对账规则的service层
 *
 * 创 建 者： wangl
 * 创建时间：  2016年9月23日下午4:09:20
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
public class SettleRuleControlService extends CrudService<SettleRuleControlDao, SettleRuleControl> {
	
	@Autowired
	private SftpCilent sftpCilent;
	
	@Autowired
	private SettleChannelManagerDao settleChannelManagerDao;
	
	@Autowired
	private SettleRuleControlDao settleRuleControlDao;
	
	@Autowired
	private SettleChannelLogDao settleChannelLogDao;
	
	public List<SettleRuleControl> findList(SettleRuleControl settleRuleControl) {
		return super.findList(settleRuleControl);
	}

	/**
	 * 
	 * @方法说明：获取通道名称
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public String getChannelName(String channelCode) {
		
		return settleChannelManagerDao.getChannelName(channelCode);
	}

	/**
	 * 
	 * @方法说明：根据id获取整个实体对象
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public SettleRuleControl getEntity(int ruleControlId) {
		
		return settleRuleControlDao.getEntity(ruleControlId);
	}

	/**
	 * 
	 * @方法说明：保存实体对象
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void updateEntity(SettleRuleControl settleRuleControl) {
		settleRuleControlDao.updateEntity(settleRuleControl);
	}

	/**
	 * 
	 * @方法说明：添加实体对象
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int  saveEntity(SettleRuleControl settleRuleControl) {
		return settleRuleControlDao.saveEntity( settleRuleControl);
	}

	/**
	 * 
	 * @方法说明：对账规则查询出已有的通道名称和通道编码
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<SettleChannelManager> getBatchName() {
		
		return settleChannelManagerDao.getBatchName();
	}
	
	/**
	 * 
	 * @方法说明：保存这条数据
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void addLog(SettleChannelLog settleChannelLog) {
		settleChannelLogDao.addLog(settleChannelLog);
	}
	/**
	 * 
	 * @方法说明：对账业务类型
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<SettleChannelManager> getChannelType() {
		
		return settleChannelManagerDao.getChannelType();
	}


	/**
	 * @方法说明：查看是否重复
	 * @时间： 2017-05-12 03:58 PM
	 * @创建人：wangl
	 */
	public int repeat(SettleRuleControl settleRuleControl){

		return settleRuleControlDao.repeat(settleRuleControl);
	}
	/**
	 * 
	 * @方法说明：查询对账文件是否路径存在是否是重复上传
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public boolean getPath(String path) {
		
		return settleChannelLogDao.getPath(path);
	}

	/**
	 * 
	 * @方法说明：为上传文件是选择通道名称和类型获取数据
	 * @时间：Nov 16, 2016
	 * @创建人：wangl
	 */
	public List<SettleChannelManager> getBatchManagerName(SettleChannelManager settleChannelManager) {
		
		return settleChannelManagerDao.getBatchManagerName(settleChannelManager);
	}

	/**
	 * 
	 * @方法说明：为上传文件是选择通道名称和类型获取数据
	 * @时间：Nov 16, 2016
	 * @创建人：wangl
	 */
	public List<SettleChannelManager> getManagerChannelType(SettleChannelManager settleChannelManager) {
		
		return settleChannelManagerDao.getManagerChannelType(settleChannelManager);
	}
	
	
	/**
	 * @方法说明：删除规则
	 * @时间：2017年1月19日上午11:33:09
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int deleteEntity(Long ruleControlId) {
		
		return settleRuleControlDao.deleteEntity(ruleControlId);
	}
	
	

	/**
	 * @方法说明：根据批次号下载对账文件
	 * @时间：2017年1月22日下午4:17:01
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public void downLoad(String checkBathNo,HttpServletResponse response) throws Exception {
		
		logger.info("根据对账批次号查询对账文件--->{条件}"+checkBathNo);
		
		    SettleChannelLog settleChannelLog=settleChannelLogDao.getEntityByNo(checkBathNo);
		    //获取保存在本地的路径
		    String path = settleChannelLog.getCheckFileName();
		    
	        InputStream fis = null;  
	        OutputStream toClient  = null;  
	     try {  
	         if(!StringUtils.isNotEmpty(path)) {
	        	 logger.info("根据对账批次号查询对账文件--->{条件}"+checkBathNo);
	        	 return ;
	         }
	         
		       /*  
		        File file = sftpCilent.getFile(adress,name,password,22,path);;
	         	*/
         		
	            File file =new File(path);
            	// 取得文件名。  
            	String filename = file.getName();  
            	// 以流的形式下载文件。  
            	fis = new BufferedInputStream(new FileInputStream(file));  
            if(fis != null){
            	byte[] buffer = new byte[fis.available()];  
            	fis.read(buffer);  
            	toClient = new BufferedOutputStream(response.getOutputStream());
            	
            	// 设置response的Header  
            	response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));  
            	response.addHeader("Content-Length", "" + file.length());  
            	toClient = new BufferedOutputStream(response.getOutputStream());  
            	response.setContentType("application/octet-stream");  
            	
            	toClient.write(buffer);  
            	toClient.flush();
            logger.info("根据对账批次号查询对账文件--->{下载结束}");
           
            }
	        	
	        }catch (Exception e){
	        	//页面提示 文件不存在
	        	response.getWriter().write("<script type='text/javascript'>parent.showThree();parent.changeThreeName('下载文件不存在');</script>");
	        }finally{  
	               if( fis !=null){  
	                  try{  
	                      fis.close();  
	                      toClient.close();  
	                  }catch (IOException ex) {  
	                 ex.printStackTrace();  
	               }  
	           }  
	      }
	}
	
	
	/**
	 * @throws Exception 
	 * @方法说明：根据批次号下载对账文件
	 * @时间：2017年1月22日下午4:17:01
	 * @创建人：wangl
	 */
	@SuppressWarnings("unused")
	@Transactional(readOnly = false)
	public String downLoadTwo(String checkBathNo,HttpServletResponse response) throws Exception {
		
		logger.info("根据对账批次号查询对账文件--->{条件}"+checkBathNo);
		InputStream fis = null;  
        OutputStream toClient  = null;  
        
			SettleChannelLog settleChannelLog=settleChannelLogDao.getEntityByNo(checkBathNo);
			
		    //获取保存在本地的路径
		    String path = settleChannelLog.getCheckFileName();
		    //通道编码
		    String channelCode = settleChannelLog.getChannelCode();
		    //通道类型
		    String channelType = settleChannelLog.getChannelType();
		    
		    SettleChannelManager changeManager=new SettleChannelManager();
		    changeManager.setChannelCode(channelCode);
		    changeManager.setChannelType(channelType);
		    
		    //生效标识,生效（默认） 无效
		    changeManager.setEffectFlg("Y");
		    //文件对账
		    changeManager.setCheckFlg("FI");
		    
		    List<SettleChannelManager> settleChangeManager=settleChannelManagerDao.getSettleChangeManagerEntity(changeManager);
			
	        File file = null;
         	if(!settleChangeManager.isEmpty()){
		    	for (SettleChannelManager settleChannelManager : settleChangeManager) {
					//远程地址
					String remoteAdress = settleChannelManager.getRemoteAdress();
					String value = remoteAdress.substring(0, 1);
					if(value.equalsIgnoreCase("s")){
						String adress = remoteAdress.substring(4, remoteAdress.length());
						//用户名
						//String name = settleChannelManager.getRemoteUserName();
						String name = AESCode.AESDncode(settleChannelManager.getRemoteUserName());
						//密码
						//String password = settleChannelManager.getRemotePassword();
						String password = AESCode.AESDncode(settleChannelManager.getRemotePassword());
						String port = settleChannelManager.getPort();
						if(StringUtils.isBlank(port)){
							port = "22";
						}
						int portNum = Integer.parseInt(port);
						//远程下载文件
						file=sftpCilent.getFile(adress,name,password,portNum,path);

						if(file.length()>0 && file != null){
							break;
						}
					}
				}
			    }else{
			    	return "0";
			    }
         	
         	//File file = sftpCilent.getFile("192.168.162.80","billing","123456",22,path);
	        
	        //如果文件不存在结束
	        if(file==null || file.length()==0){
	        	return "0";
	        }
	        
		     try {  
		         if(!StringUtils.isNotEmpty(path)) {
		        	 logger.info("根据对账批次号查询对账文件--->{路径为空,无法下载}"+checkBathNo);
		        	 return "0";
		         }
		            
		         
	            	// 以流的形式下载文件。  
	            	fis = new BufferedInputStream(new FileInputStream(file)); 
		            if(fis != null){
		            	return "1";
		            }
		        }catch (Exception e) {
		        	return "0";
				} finally{  
		               if( fis !=null){  
		                  try{  
		                      fis.close();  
		                  }catch (IOException ex) {  
		                 ex.printStackTrace();  
		               }  
		           }  
		      }
			return "1";
	}
	
}
