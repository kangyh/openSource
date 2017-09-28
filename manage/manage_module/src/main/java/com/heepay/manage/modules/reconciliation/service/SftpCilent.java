package com.heepay.manage.modules.reconciliation.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.heepay.billingutils.CreateDir;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;

/***
 * 
* 
* 描    述：远程文件下载客户端
*
* 创 建 者： wangl
* 创建时间：  2017年1月24日下午3:51:55
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

@Component
public class SftpCilent {

	//日志打印
	private static final Logger logger = LogManager.getLogger();
	
    public File getFile(String host,String name,String passsword,int port,String filePath){
        
    	//删除本地可能存在的旧文件
    	File fileDel=new File(filePath);
    	fileDel.delete();
    	
    	//创建连接的客户端
    	SftpCilent ftp = new SftpCilent();
    	
    	//将查询出来的地址分割成文件名和路径
        int index = filePath.lastIndexOf("/");
        String fileName = filePath.substring(index + 1, filePath.length());
        String path = filePath.substring(0, index);
        
        File file =null;
        ChannelSftp sftp = ftp.connect(host, port, name, passsword);
        try {
        	logger.info("jsch创建会话,远程服务器下载");
            file = ftp.download(path, fileName, sftp,filePath);
            sftp.cd(path);
            sftp.mkdir("createFolder");
        } catch (Exception e) {
        	logger.info("jsch创建会话,远程服务器下载--->{异常}",e.getMessage());
        }finally{
            try {
	            //如果没有sesstion的disconnect，程序不会退出
	            sftp.getSession().disconnect();
            } catch (JSchException e) {
                e.printStackTrace();
            }
            sftp.disconnect();
            sftp.exit();
        }
		return file;
    }

    
    
    /**
     * 
     * @方法说明：连接服务器
     * @时间：2017年1月24日下午4:14:31
     * @创建人：wangl
     */
    public ChannelSftp connect(String host, int port, String username, String password) {
        ChannelSftp csftp = null;
        JSch jsch = new JSch();
        try {
           com.jcraft.jsch.Session   sshSession = jsch.getSession(username, host, port);
            logger.info("jsch创建会话,用户名: "+username);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no"); // 连接不需要公钥和私钥
            
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            logger.info("会话连接中");
            
            com.jcraft.jsch.Channel   channel = sshSession.openChannel("sftp");
            channel.connect();
            csftp = (ChannelSftp)channel;
            logger.info("远程服务器: "+ host+" 连接正常");
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return csftp;
    }
   
    
    
   /**
    * 
    * @方法说明：远程下载方法，获取该文件
    * @时间：2017年1月24日下午4:14:59
    * @创建人：wangl
    */
    public File download(String path, String fileName, ChannelSftp sftp,String filePath) throws Exception{
        
    	//创建文件保存路径
    	boolean flag = CreateDir.createLiunxDir(path);
    	if(!flag){
    		return null;
    	}
    	
    	File file = new File(filePath);
        try {
            sftp.cd(path);
            sftp.get(fileName, new FileOutputStream(file));
            logger.info("进入远程服务器路径: "+ path+" 目录");
            
        } catch (Exception e) {
        	logger.error("进入远程服务器--->{异常}"+e.getMessage());
        }
        
		return file;
    }
    
    
    /**
     * 
     * @方法说明：删除远程文件
     * @时间：2017年2月6日下午2:29:43
     * @创建人：wangl
     */
    public boolean delete(String path, String fileName, ChannelSftp sftp){
        try {
            sftp.cd(path);
            sftp.rm(fileName);
            logger.info("删除远程文件成功: "+path+"/"+fileName);
        } catch (Exception e) {
        	logger.error("删除远程文件错误--->{异常}"+e.getMessage());
            return false;
        }
        return true;
    }
    
    /**
     * @param path 远程文件路径 
     * @param filePath 本地带文件名的绝对路径
     * 
     * @方法说明：文件上传到远程服务器
     * @时间：14 Feb 201710:10:19
     * @创建人：wangl
     */
    public boolean upload(String path, String filePath, ChannelSftp sftp){
        File file = new File(filePath);
        try {
            sftp.cd(path);
            sftp.put(new FileInputStream(file), file.getName());
            logger.info("上传文件成功: "+filePath);
            return true;
        } catch (Exception e) {
        	logger.info("上传文件失败: "+e.getMessage());
            return false;
        }
    }
    
}

