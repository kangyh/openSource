package com.heepay.manage.modules.monitor.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.heepay.codec.MD5Util;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.manage.modules.monitor.java.entity.MonitorMachine;
import com.heepay.manage.modules.monitor.java.entity.MonitorMessage;
import com.heepay.manage.modules.monitor.java.enums.ReponseStatus;
import com.heepay.manage.modules.monitor.java.service.MonitorMachineService;
import com.heepay.manage.modules.monitor.java.service.MonitorRemoteInterfaceService;
import com.heepay.manage.modules.monitor.java.vo.FileContentVo;
import com.heepay.manage.modules.monitor.java.vo.FileListVo;
import com.heepay.manage.modules.monitor.java.vo.MachineListVo;
import com.heepay.manage.modules.monitor.java.vo.MachineResponseVo;

/***
 * 
 * 
 * 描    述：通道管理表查询操作
 *
 * 创 建 者： dongzj
 * 创建时间：  2017年1月20日
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
@Controller
@RequestMapping(value = "/static/JavaMonitor")
public class MonitorLogController {
	//日志打印
		private static final Logger logger = LogManager.getLogger();
		
		@Autowired
		private MonitorMachineService monitorMachineService;
		@Autowired
		private MonitorRemoteInterfaceService monitorRemoteInterfaceService;
		
		
		@RequestMapping(value = { "GetAllPcList", "" },method=RequestMethod.POST)
		@ResponseBody
		public String GetMachineList(@RequestBody MachineListVo vo  )
		{   
			List<MachineResponseVo> result2=new ArrayList<MachineResponseVo>();
			List<MonitorMachine> result=monitorMachineService.getAll();
			if(result!=null&&result.size()>0)
			{
				for(MonitorMachine machine:result)
				{
					MachineResponseVo machineVo=new MachineResponseVo();
					machineVo.setPcNo(machine.getMachineId());
					machineVo.setPcName(machine.getMachineName());
					machineVo.setInnerIP(machine.getInnerIp());
					machineVo.setOuterIP(machine.getOuterIp());
					machineVo.setGroupType(machine.getMachineGroupId());
					machineVo.setDbOrWeb(machine.getIsDb()==0?false:true);
					result2.add(machineVo);
				}
			}
			MonitorMessage<List<MachineResponseVo>> res=new MonitorMessage<List<MachineResponseVo>>();
			res.setHasError(false);
			res.setData(result2);
			res.setMessage(ReponseStatus.SUCCESS.getContent());
			return JsonMapperUtil.buildNonDefaultMapper().toJson(res);
		     
		}
		/**
		 * 
		 * @方法说明：获取文件列表接口
		 * @时间：2017年1月22
		 * @创建人：dongzj
		 */
		@RequestMapping(value = { "GetFileList", "" },method=RequestMethod.POST)
		@ResponseBody
		public String GetFileList(@RequestBody FileListVo vo  )
		{   
			
			List<Map<String, String>> newList=new  ArrayList<Map<String, String>>();
			MonitorMachine machine=monitorMachineService.getEntityById(vo.getPcNo());
			if(machine==null)
			{
				MonitorMessage<String> result=new MonitorMessage<String>();
				result.setHasError(true);
				result.setReturnCode(String.valueOf(ReponseStatus.NOTVALID.getValue()));
				result.setMessage(ReponseStatus.NOTVALID.getContent());
				return JsonMapperUtil.buildNonDefaultMapper().toJson(result);
			}
			try
			{
			MonitorMessage<List<Map<String, String>>> result= monitorRemoteInterfaceService.getFileListService(machine, machine.getFolder());
			if(result!=null&&result.getData()!=null)
			{
				for(Map<String, String> map: result.getData())
				{   
					HashMap<String, String> newMap=new  HashMap<String, String>();
					for(String key :map.keySet())
					{
						newMap.put(key, map.get(key));
					}
					newMap.put("FileMD5", MD5Util.md5(map.get("FileFullPath")+"/"+map.get("FileName")));
					newList.add(newMap);
				}
			}
			result.setData(newList);
			
			return JsonMapperUtil.buildNonDefaultMapper().toJson(result);
			}
			catch(Exception ex)
			{   
				logger.error("获取文件列表接口出现异常:{}",ex.getMessage());
				MonitorMessage<String> result=new MonitorMessage<String>();
				result.setHasError(true);
				return JsonMapperUtil.buildNonDefaultMapper().toJson(result);
			}
		}
		/**
		 * 
		 * @方法说明：检查机器状态
		 * @时间：2017年1月22
		 * @创建人：dongzj
		 */
		@RequestMapping(value = { "CheckMachineStatus", ""})
		public String CheckMachineStatus( MonitorMachine monitorMachine)
		{   
			try
			{
			MonitorMessage<List<Map<String, String>>> result= monitorRemoteInterfaceService.checkMachineStatus(monitorMachine, monitorMachine.getFolder());
			return JsonMapperUtil.buildNonDefaultMapper().toJson(result);
			}
			catch(Exception ex)
			{   
				logger.error("检查机器状态接口出现异常:{}",ex.getMessage());
				MonitorMessage<String> result=new MonitorMessage<String>();
				result.setHasError(true);
				return JsonMapperUtil.buildNonDefaultMapper().toJson(result);
			}
		}
		/**
		 * 
		 * @方法说明：获取文件内容
		 * @时间：2017年1月22
		 * @创建人：dongzj
		 */
		@RequestMapping(value = { "GetFileContent", "" },method=RequestMethod.POST)
		@ResponseBody
		public String GetFileContent(@RequestBody FileContentVo vo  )
		{   
			MonitorMachine machine=monitorMachineService.getEntityById(vo.getPcNo());
			if(machine==null)
			{
				MonitorMessage<String> result=new MonitorMessage<String>();
				result.setHasError(true);
				result.setReturnCode(String.valueOf(ReponseStatus.NOTVALID.getValue()));
				result.setMessage(ReponseStatus.NOTVALID.getContent());
				return JsonMapperUtil.buildNonDefaultMapper().toJson(result);
			}
			try
			{
			MonitorMessage<String> result=monitorRemoteInterfaceService.getFileContentService(machine, vo.getLastLookLogLine(), vo.getFullName());
			return JsonMapperUtil.buildNonDefaultMapper().toJson(result);
			}
			catch(Exception ex)
			{   
				logger.error("获取文件内容接口出现异常:{}",ex.getMessage());
				MonitorMessage<String> result=new MonitorMessage<String>();
				result.setHasError(true);
				return JsonMapperUtil.buildNonDefaultMapper().toJson(result);
			}
		}
}
