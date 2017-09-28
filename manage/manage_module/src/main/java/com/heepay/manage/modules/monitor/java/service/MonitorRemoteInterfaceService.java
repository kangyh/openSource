package com.heepay.manage.modules.monitor.java.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.heepay.manage.modules.monitor.java.entity.MonitorMachine;
import com.heepay.manage.modules.monitor.java.entity.MonitorMessage;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.comO
 * 创建时间： 2017年1月20日 上午10:17:55
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
public class MonitorRemoteInterfaceService {

	private JSch jsch = null;
	private Session session = null;
	private ChannelExec channelExec = null;
	private BufferedReader reader = null;
	private  int timeout = 60000000;
	private void initResource(MonitorMachine monitorMachine) throws JSchException
	{
		String userName;
		String password;
		String host;
		int port;
		userName = monitorMachine.getUserName();
		password = monitorMachine.getPassword();
		host = monitorMachine.getAccessIp();
		port = monitorMachine.getPort();
		jsch = new JSch(); // 创建JSch对象  
		session = jsch.getSession(userName, host, port);
		session.setPassword(password); // 设置密码  
		Properties config = new Properties();  
	    config.put("StrictHostKeyChecking", "no");  
	    session.setConfig(config); // 为Session对象设置properties  
		session.setTimeout(timeout); // 设置timeout时间 
		session.connect();
	}
	@PreDestroy
	private void destroyResource()
	{
		if(reader !=null)
		{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (channelExec != null)
		{
			channelExec.disconnect();  
		}
        if (session !=null) {  
            session.disconnect();  
        }  
	}
	private BufferedReader getCommandContent(String cmd) throws JSchException, IOException
	{
		channelExec = (ChannelExec) session.openChannel("exec");  
        channelExec.setCommand(cmd);  
        channelExec.setInputStream(null);  
        channelExec.setErrStream(System.err); 
        channelExec.connect();
        InputStream in = channelExec.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));  
        return reader;
	}
	
	
	/**
	 * 根据IP地址信息，用户名 和 密码 端口号 返回 指行的行号
	 * @param monitorMachine
	 * @return
	 * @throws JSchException
	 * @throws IOException
	 */
	public MonitorMessage<String> getFileContentService(MonitorMachine monitorMachine,int rowNmber,String fullNameWithPath) 
	{
		MonitorMessage<String> message = new MonitorMessage<String>();
		String cmd ="";
		if (checkParameterValidate(rowNmber,fullNameWithPath,message)) //参数检查
		{
			return message;
		}
		
		if (fullNameWithPath.substring(fullNameWithPath.lastIndexOf("."),fullNameWithPath.length()).equals(".gz"))
		{
			cmd = String.format("gunzip -c %s | tail -n +%s",fullNameWithPath,String.valueOf(rowNmber));
		}else if (fullNameWithPath.substring(fullNameWithPath.lastIndexOf("."),fullNameWithPath.length()).equals(".log"))
		{
			cmd = String.format("tail -n +%s %s",String.valueOf(rowNmber),fullNameWithPath);
		}else
		{
			message.setHasError(true);
			message.setMessage("suffix is error,only support .gz or.log extention file ");
			return message;
		}
		String buf="";
		StringBuffer sb = new StringBuffer();
		BufferedReader reader;
		Boolean hasError = false;
		String errorMessage = "ok";
		try {
			reader = cmdBuider(monitorMachine,cmd);
			while ((buf = reader.readLine()) != null) {  
				sb.append(buf);  
			}
		} catch (JSchException | IOException e) {
			hasError=true;
			errorMessage = e.getMessage();
		}
		if ("".equals(sb.toString()) && hasError==false)
		{
			hasError = true;
			errorMessage  = String.format("不能打开文件%s读取或没有这样的文件",fullNameWithPath);
		}
		message.setHasError(hasError);
		message.setMessage(errorMessage);
		message.setData(sb.toString());
		return message;
	}
	/**
	 * 检测文件内容参数
	 * @param rowNmber
	 * @param fullNameWithPath
	 * @return true 代表有错误 false 没有错误
	 */
	private Boolean checkParameterValidate(int rowNmber,String fullNameWithPath,MonitorMessage<String> message)
	{
		if (rowNmber<0)
		{
			message.setHasError(true);
			message.setMessage("行数应当是正整数，请检查");
			return true;
		}
		if (!(fullNameWithPath.endsWith(".gz") ||  fullNameWithPath.endsWith(".log")))
		{
			message.setHasError(true);
			message.setMessage("全路径参数必须是后缀名.gz 或 .log，请检查");
			return true;
		}
		return false;
		
	}
	
	
	/**
	 * 检则 主机 是否连通，及路径是否可用
	 * @param monitorMachine
	 * @param fullNameWithPath
	 * @return
	 */
	public MonitorMessage<List<Map<String,String>>> checkMachineStatus(MonitorMachine monitorMachine,String fullNameWithPath)
	{
		
		MonitorMessage<List<Map<String,String>>> message = new MonitorMessage<List<Map<String,String>>>();
		String cmd = "ls -lt --time-style=long-iso "+fullNameWithPath;
		BufferedReader reader = null;
		Boolean hasError = false;
		String errorMessage = "ok";
		StringBuffer sb = new StringBuffer();
		
		try {
			reader = cmdBuider(monitorMachine,cmd);
			String buf;
			buf = reader.readLine(); //只取第一行记录
			sb.append(buf);
		} catch (JSchException e) {
			hasError=true;
			errorMessage = e.getMessage();
		} catch (IOException e) {
			hasError=true;
			errorMessage = e.getMessage();
		}
		if (!(sb.toString().contains("total ")) && hasError ==false)
		{
			hasError = true;
			errorMessage = String.format("不能访问目录：%s 或没有这样的目录",fullNameWithPath) ;
		}
        message.setHasError(hasError);
		message.setMessage(errorMessage);
		return message;
	}
	
	
	private BufferedReader cmdBuider(MonitorMachine monitorMachine,String cmd) throws JSchException, IOException
	{
		BufferedReader reader = null; 
		initResource(monitorMachine);
		reader = getCommandContent(cmd);
		return reader;
	}
	
	
	/**
	 * 根据 ip地址 用户名 密码 和端口 号返回 文件列表信息
	 * @param monitorMachine
	 * @return
	 * @throws JSchException
	 * @throws IOException
	 */
	public MonitorMessage<List<Map<String,String>>> getFileListService(MonitorMachine monitorMachine,String fullNameWithPath)
	{
		MonitorMessage<List<Map<String,String>>> message = new MonitorMessage<List<Map<String,String>>>();
		List<Map<String,String>> fileList= new ArrayList<Map<String,String>>();
		String cmd = "ls -lt --time-style=long-iso "+fullNameWithPath;
		BufferedReader reader = null;
		Boolean hasError = false;
		String errorMessage = "ok";
		try {
			reader = cmdBuider(monitorMachine,cmd);
			String buf;
			buf = reader.readLine(); //第一行 记录是 查询的总条件
			if (buf==null)
			{
				hasError = true;
				errorMessage = String.format("不能访问目录：%s 或没有这样的目录",fullNameWithPath) ;
				message.setHasError(hasError);
				message.setMessage(errorMessage);
				return message;
			}
			while ((buf = reader.readLine()) != null) {  
				Map<String,String> item = new HashMap<String,String>();
			   String[] items = buf.replaceAll("\\s+", ",").split(",");
			   item.put("FileName", items[7]);
			   item.put("FileSize", items[4]);
			   item.put("CreateTime", items[5]);
			   item.put("ModifyTime", items[5]+" "+items[6]);
			   item.put("FileFullPath", fullNameWithPath);
			   fileList.add(item);			
			}
		} catch (JSchException e) {
			hasError=true;
			errorMessage = e.getMessage();
		} catch (IOException e) {
			hasError=true;
			errorMessage = e.getMessage();
		}
        message.setHasError(hasError);
		message.setMessage(errorMessage);
		message.setData(fileList);
		return message;
	}
	public static void main(String[] args)
	{
		testFileContentService();
		//testFileListService();
		//testCheckMachineStatus();
	}
	private static void testFileListService()
	{
		MonitorMessage<List<Map<String,String>>> message = new MonitorMessage<List<Map<String,String>>>();
		String fullPath = "/home/risk/app/risk_server/logs/";
		MonitorMachine monitorMachine = new MonitorMachine();
		monitorMachine.setAccessIp("192.168.162.82");
		monitorMachine.setUserName("risk");
		monitorMachine.setPassword("risk");
		monitorMachine.setPort(22);
		MonitorRemoteInterfaceService monitorRemoteInterfaceService = new MonitorRemoteInterfaceService();
		message = monitorRemoteInterfaceService.getFileListService(monitorMachine, fullPath);
		System.out.println(message.getMessage());
		System.out.println(message.getData());
	}
	
	private static void testCheckMachineStatus()
	{
		MonitorMessage<List<Map<String,String>>> message = new MonitorMessage<List<Map<String,String>>>();
		String fullPath = "/home/risk/app/risk_server/logs1/";
		MonitorMachine monitorMachine = new MonitorMachine();
		monitorMachine.setAccessIp("192.168.162.80");
		monitorMachine.setUserName("risk");
		monitorMachine.setPassword("risk");
		monitorMachine.setPort(22);
		MonitorRemoteInterfaceService monitorRemoteInterfaceService = new MonitorRemoteInterfaceService();
		message = monitorRemoteInterfaceService.checkMachineStatus(monitorMachine, fullPath);
		System.out.println(message.getHasError());
		System.out.println(message.getMessage());
		//System.out.println(message.getData());
	}
	
	
	private static void testFileContentService() 
	{
		String fullNameWithPath = "/home/risk/app/risk_server/logs/risk_info.log";
		//String fullNameWithPath = "/home/risk/app/risk_server/logs/risk_info_2017-01-18_1.log.gz";
		MonitorMessage<String> message = new MonitorMessage<String>();
		MonitorMachine monitorMachine = new MonitorMachine();
		monitorMachine.setAccessIp("192.168.162.80");
		monitorMachine.setUserName("risk");
		monitorMachine.setPassword("risk");
		monitorMachine.setPort(22);
		MonitorRemoteInterfaceService monitorRemoteInterfaceService = new MonitorRemoteInterfaceService();
		message = monitorRemoteInterfaceService.getFileContentService(monitorMachine, 1,fullNameWithPath);
		System.out.println(message.getMessage());
		System.out.println(message.getData());
	}
	
}
