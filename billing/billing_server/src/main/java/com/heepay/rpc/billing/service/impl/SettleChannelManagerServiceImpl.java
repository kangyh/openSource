package com.heepay.rpc.billing.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.dao.SettleChannelLogMapper;
import com.heepay.billing.dao.SettleChannelManagerMapper;
import com.heepay.billing.entity.SettleChannelLog;
import com.heepay.billing.entity.SettleChannelManager;
import com.heepay.billingutils.CreateDir;
import com.heepay.common.util.AESCode;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.IpUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.billing.BillingBillStatus;
import com.heepay.enums.billing.BillingChannelManageCheckFlg;
import com.heepay.enums.billing.BillingCheckStatus;
import com.heepay.enums.billing.RemoteAdressPath;
import com.heepay.rpc.billing.service.ICheckBillRecordService;
import com.heepay.rpc.billing.service.IInsertLogAndAdultService;
import com.heepay.rpc.billing.service.impl.checkbill.BillCompareServiceImpl;
import com.heepay.rpc.client.BillingCommonClient;
import com.heepay.rpc.client.PayChannelCacheServiceClient;
import com.heepay.rpc.service.RpcService;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * *
 * 
 * 
 * 描 述：下载对账文件，写入日记，开始对账
 *
 * 创 建 者： wangjie 创建时间： 2016年9月25日下午3:37:24 创建描述：
 * 
 * 修 改 者： 修改时间： 修改描述：
 * 
 * 审 核 者： 审核时间： 审核描述：
 *
 */

@Component
@RpcService(name = "SettleChannelManagerServiceImplWD", processor = com.heepay.rpc.billing.service.SettleChannelManagerService.Processor.class)
public class SettleChannelManagerServiceImpl
		implements com.heepay.rpc.billing.service.SettleChannelManagerService.Iface {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	SettleChannelManagerMapper settleChannelManagerDaoImpl;

	@Autowired
	SettleChannelLogMapper settleChannelLogDaoImpl;

	@Autowired
	ClearChannelRecordMapper clearChannelRecordDaoImpl;

	@Autowired
	SettleMerchantService settleMerchantService;

	@Autowired
	GetChannelBatchData getChannelBatchData;

	@Autowired
	IInsertLogAndAdultService iInsertLogAndAdultService;
	
	@Autowired
	PayChannelCacheServiceClient payChannelCacheServiceClient;
	
	@Autowired
	ICheckBillRecordService billCompareServiceImpl;

	@Resource(name = "billingCommonClient")
	BillingCommonClient billingCommonClient;

	// 下载对账文件入口
	@Override
	public void query() throws TException {
		// 定时任务入口，开始下载对账文件
		downChannelManager();

		// 开始对账
		billCompare();		

		// 通道侧结算记录和清算明细
		getChannelBatchData.getSettleChannelRecordMessage();

	}

	// 定时任务入口，开始下载对账文件
	public void downChannelManager() {

		// 查询通道管理数据
		List<SettleChannelManager> list = settleChannelManagerDaoImpl.querySettleChannelManagerDetail(
				BillingChannelManageCheckFlg.BCMCFF.getValue(), BillingBillStatus.BBSTATUSY.getValue());
		for (SettleChannelManager settleChannelManager : list) {
			String channelCode = settleChannelManager.getChannelCode(); // 通道编码
			String channelType = settleChannelManager.getChannelType(); // 通道类型
			String remoteAdress = settleChannelManager.getRemoteAdress(); // 服务器地址
//			String remoteUserName = settleChannelManager.getRemoteUserName(); // 用户名
//			String remotePassword = settleChannelManager.getRemotePassword(); // 密码
			String remoteUserName = AESCode.AESDncode(settleChannelManager.getRemoteUserName());
			String remotePassword = AESCode.AESDncode(settleChannelManager.getRemotePassword());
			String localFilePath = settleChannelManager.getLocalFilePath(); // 文件存放路径
			String settleWay = settleChannelManager.getSettleWay(); // 对账方式
			String ruleType = settleChannelManager.getRuleType(); // 对账规则
			String port = settleChannelManager.getPort();
			String format = DateUtil.getTodayYYYYMMDD();
			// FTP方式下载
			if (RemoteAdressPath.FTP.getValue().equals(remoteAdress.substring(0, 3))) {
				boolean ok = downloadFileFtp(channelCode, channelType, remoteAdress, remoteUserName, remotePassword,
						localFilePath, settleWay,ruleType, port);
				// SFTP文件下载
			} else if (RemoteAdressPath.SFTP.getValue().equals(remoteAdress.substring(0, 4))) {
				boolean ok = downloadFileSftp(channelCode, channelType, remoteAdress, remoteUserName, remotePassword,
						localFilePath, settleWay,ruleType, port, format);
			}
		}
	}

	// ftp文件下载
	public boolean downloadFileFtp(String channelCode, String channelType, String remoteAdress, String remoteUserName,
			String remotePassword, String localFilePath, String settleWay,String ruleType, String port) {

		// 获取当前日期格式yyyy-MM-dd
		String format = DateUtil.getTodayYYYYMMDD();
		// 对账文件存放目录
		String path = billingCommonClient.getFilePath() + "/" + channelCode + "/" + channelType + "/" + format;
		String file = "";
		boolean f = false;

		FTPClient ftpClient = new FTPClient();
		FTPFile[] remoteFiles = null;
		OutputStream out = null;
		try {
			ftpClient.connect(remoteAdress.substring(3)); // 截取ip以ftp方式客户端连接
			if (ftpClient.login(remoteUserName, remotePassword)) { // 登录
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 设置读取文件方式
				// 切到指定目录
				if (ftpClient.changeWorkingDirectory(path)) {
					logger.info("进入服务器目录:" + path);
					ftpClient.enterLocalPassiveMode();
					remoteFiles = ftpClient.listFiles();
					logger.info("获取对账文件文件名成功" + remoteFiles);
				} else {
					logger.info("服务器没有要下载对账文件的目录" + path);
				}

			} else {
				f = false;
				logger.info("连接FTP服务器失败，用户名或密码错误!" + "用户名:" + remoteUserName, "密码:" + remotePassword);
			}

		} catch (SocketException e) {
			logger.error("连接FTP服务器异常：" , e);
		} catch (IOException e) {
			logger.error("FTP方式下载异常：" , e);
		}

		// 便历目录中文件
		if (remoteFiles != null) {
			for (int i = 0; i <= remoteFiles.length - 1; i++) {
				String name = remoteFiles[i].getName().toString();

				try {
					// 检索该文件是否已经入库过
					file = localFilePath + format + "/" + name;
					if (settleChannelLogDaoImpl.fingSettleChannelLog(file)) {
						logger.info("文件已下载过" + file);
						continue;
					}
					// 创建要把文件下载到指定的目录
					if (CreateDir.createLiunxDir(localFilePath + format)) {
						out = new FileOutputStream(file);
						try {
							boolean flag = ftpClient.retrieveFile(name, out); // 文件下载
							out.flush();

							if (flag) {
								String checkFileWhere = IpUtil.getHostIp() + ":" + localFilePath + format + "/" + name ;    	   //文件存储位置
								String checkFileFrom = remoteAdress.substring(3)+ ":" + path + "/" + name ;     //文件来源
								// 插入日记，通知开始解析文件
								f = iInsertLogAndAdultService.insertAndAdult(checkFileWhere, checkFileFrom, channelCode, channelType, name, localFilePath, settleWay,ruleType, format);

							}

						} catch (IOException e) {
							logger.error("下载文件失败：" , e);
						} finally {
							out.close();
						}
					} else {
						logger.info("创建要存放文件的目录失败，不能下载文件" + localFilePath + format);
					}
				} catch (IOException e) {
					logger.error("下载对账文件异常:", e);
				}
			}
			try {
				// 防止服务器提前关闭
				ftpClient.disconnect();
			} catch (IOException e) {
				logger.error("FTP服务器关闭异常：", e);
			}
		}
		return f;
	}

	// sftp下载
	public boolean downloadFileSftp(String channelCode, String channelType, String remoteAdress, String remoteUserName,
			String remotePassword, String localFilePath, String settleWay,String ruleType, String port, String format) {

		ChannelSftp sftp = null;
		Vector v = null;
		String file1 = "";
		List<Object> fileList = new ArrayList<Object>();

		try {
			int port1;
			if(StringUtils.isBlank(port)) {
				port1 = 22;
			}else {
				port1 = Integer.parseInt(port);
			}
			JSch jsch = new JSch(); // 创建JSch客户端
			jsch.getSession(remoteUserName, remoteAdress.substring(4), port1); // 获取session
			Session sshSession = jsch.getSession(remoteUserName, remoteAdress.substring(4), port1);
			logger.info("Session created." + sshSession);
			sshSession.setPassword(remotePassword);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no"); // 连接不需要公钥和私钥
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect(); // sftp客户端连接
			sftp = (ChannelSftp) channel;
			logger.info("Connected to " + remoteAdress.substring(4) + "服务器.");

			//String format = DateUtil.getTodayYYYYMMDD();
			// 拼接要下载对账文件的目录
			String spath = billingCommonClient.getFilePath() + "/" + channelCode + "/" + channelType + "/" + format;
			logger.info("SFTP文件下载的目录："+spath);
			try {
				sftp.cd(spath);
				v = sftp.ls(spath); // 获取目录中文件
				for(Iterator it = v.iterator();it.hasNext();){
					LsEntry le=(LsEntry)it.next();
					if(".".equals(le.getFilename()) || "..".equals(le.getFilename())){
						continue;
					}else if(le.getAttrs().isDir()){
						continue;
					}else{
						fileList.add(le.getFilename());
					}
		        }
			} catch (SftpException e) {
				logger.debug("服务器上面没有要下载文件的目录,执行下一条记录" + e);
				if (null != sftp) {
					try {// 关闭session
						sftp.getSession().disconnect();
					} catch (JSchException e1) {
						logger.error("SFTP服务器session关闭出错:" , e1);
					}
					// 关闭sftp连接
					sftp.disconnect();
				}
				return false;
			}

			// 便历目录中文件
			if (null != fileList && fileList.size() > 0) {
				for (int i = 0; i < fileList.size(); i++) {
					if (CreateDir.createLiunxDir(localFilePath + format + "/")) {
						// 检索该文件是否已经入库过
						file1 = localFilePath + format + "/" + fileList.get(i).toString();
						if (settleChannelLogDaoImpl.fingSettleChannelLog(file1)) {
							logger.info("文件已下载过 ," + file1);
							continue;
						}
						try {
							File file = new File(localFilePath + format + "/" + fileList.get(i).toString());

							OutputStream out = new FileOutputStream(file);
							// 下载文件
							sftp.get(fileList.get(i).toString(), out);
							out.flush();
							out.close();
							logger.info("SFTP文件下载完毕" + file);

							// 判断对账文件是否为空文件
							if (file.length() > 0) {
								
								String checkFileWhere = IpUtil.getHostIp() + ":" + localFilePath + format + "/" + fileList.get(i).toString() ;    	   //文件存储位置
								String checkFileFrom = remoteAdress.substring(4)+ ":" + spath + "/" + fileList.get(i).toString() ;     //文件来源
								// 插入日记，通知开始解析文件
								iInsertLogAndAdultService.insertAndAdult(checkFileWhere, checkFileFrom, channelCode, channelType, fileList.get(i).toString(), localFilePath, settleWay,ruleType, format);
							} else {
								logger.info("对账文件中没有内容,文件大小{}" + file.length());
							}

						} catch (Exception e) {

							logger.error("SFTP文件下载失败：" , e);
						}

					}

				}
			} else {
				logger.info("SFTP下载方式目录里面没有文件" + spath);
			}

		} catch (Exception e) {
			logger.error("SFTP服务器连接异常：" , e);
		} finally {

			if (null != sftp) {
				try {// 关闭session
					sftp.getSession().disconnect();
				} catch (JSchException e) {
					logger.error("SFTP服务器session关闭出错:" , e);
				}
				// 关闭sftp连接
				sftp.disconnect();
			}

		}

		return true;
	}
	/**
	 * 
	 * @方法说明：清结算系统对账
	 * @author xuangang
	 * @时间：2016年11月23日上午11:42:19
	 */
	@SuppressWarnings("unused")
	private void billCompare(){
		try{
			Map<String, Object>  paraMap = new HashMap<String, Object>(); 
			paraMap.put("checkStatus", BillingCheckStatus.CHECKSTATUSCD.getValue()); //对账中
			
			//查询对账日志表中，对账状态为“对账中”的对账批次
			List<SettleChannelLog> list = settleChannelLogDaoImpl.querySettleChannelLogByCheckStatus(paraMap);
			
			if(list == null || list.size() == 0){
				logger.info("-----不存在要对账的对账批次-------");
				return;
			}else{
				logger.info("清结算系统对账开始，共"+list.size()+"条对账批次需要对账！");
				for(SettleChannelLog settleChannlelog: list){
					String checkBatch = "";
					try{
						checkBatch = settleChannlelog.getCheckBathNo(); //获取对账批次号				
						if(StringUtil.notBlank(checkBatch)){							
								
								logger.info("清结算对账优化代码开始执行，批次号：{}", checkBatch);								
								billCompareServiceImpl.billCompare(checkBatch);							
							
							   logger.info("清结算对账成功，批次号为{}", checkBatch);
						}else{
							continue;
						}					
					}
					catch(Exception e){
						logger.error("清结算对账失败，批次号为{}", checkBatch);
					}				
				}
			}
		}catch(Exception e){
			logger.error("清结算系统对账异常", e);
		}
		
	}

}
