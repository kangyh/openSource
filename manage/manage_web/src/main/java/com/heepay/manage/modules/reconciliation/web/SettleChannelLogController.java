package com.heepay.manage.modules.reconciliation.web;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.enums.TransType;
import com.heepay.manage.common.utils.DictList;
import com.heepay.manage.modules.reconciliation.util.ChannelTypeClear;
import com.heepay.manage.modules.reconciliation.web.util.RuleType;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.heepay.billingutils.CreateDir;
import com.heepay.billingutils.util.Constants;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.ChannelType;
import com.heepay.enums.billing.BillingChannelManageCheckFlg;
import com.heepay.enums.billing.BillingCheckStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelLog;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelManager;
import com.heepay.manage.modules.reconciliation.entity.SettleRuleControl;
import com.heepay.manage.modules.reconciliation.service.SettleChannelLogService;
import com.heepay.manage.modules.reconciliation.service.SettleRuleControlService;
import com.heepay.manage.modules.riskLogs.service.RiskLogsService;

/***
 *
 *
 * 描    述：对账日志批量查询
 *
 * 创 建 者： wangl
 * 创建时间：  2016年9月23日下午1:38:03
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
@RequestMapping(value = "${adminPath}/reconciliation/SettleCheckBath")
public class SettleChannelLogController extends BaseController{

	private static final Logger logger = LogManager.getLogger();

	private static PropertiesLoader loader = new PropertiesLoader("billing.properties");
	@Autowired
	private SettleChannelLogService settleChannelLogService;

	@Autowired
	private SettleRuleControlService settleRuleControlService;

	@Autowired
	private BatchExcelExport excelController;//下载方法

	@Autowired
	private RiskLogsService riskLogsService;//日志操作


	//查询方法对账日志显示
	@RequiresPermissions("settle:settleChannelLog:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleChannelLog settleChannelLog, HttpServletRequest request,HttpServletResponse response, Model model) {

		//格式化查询时间条件
		Date endCheckTime = settleChannelLog.getEndOperEndTime();
		if(endCheckTime!=null){
			String format = new SimpleDateFormat("yyyy-MM-dd").format(endCheckTime);
			format=format+" 23:59:59";
			try {
				Date time = endCheckTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);

				settleChannelLog.setEndOperEndTime(time);
			} catch (ParseException e) {
				logger.error("时间转换异常",e.getMessage());
			}
		}
		//根据搜索的条件值 组合成复合的查询条件
		if(BillingCheckStatus.CHECKSTATUSWS.getValue().equals(settleChannelLog.getCheckStatus())){
			settleChannelLog.setCheckStatusTwo(BillingCheckStatus.CHECKSTATUSWSD.getValue());
		}if(BillingCheckStatus.CHECKSTATUSCD.getValue().equals(settleChannelLog.getCheckStatus())){
			settleChannelLog.setCheckStatusTwo(BillingCheckStatus.CHECKSTATUSCDD.getValue());
		}if(BillingCheckStatus.CHECKSTATUSCE.getValue().equals(settleChannelLog.getCheckStatus())){
			settleChannelLog.setCheckStatusTwo(BillingCheckStatus.CHECKSTATUSCE.getValue());
		}

		//根据条件查询出符合的数据，显示到页面
		Page<SettleChannelLog> page = settleChannelLogService.findPage(new Page<SettleChannelLog>(request, response), settleChannelLog);

		//为页面的下拉显示查询名称
		List<SettleChannelManager>	list = settleRuleControlService.getBatchName();
		int size = list.size();

		if(size==0){
			model.addAttribute("batchList",null);
		}else{
			List<SettleRuleControl> clearingList = Lists.newArrayList();
			for (SettleChannelManager settleChannelManager : list) {

				SettleRuleControl settleRule=new SettleRuleControl();
				settleRule.setChannelCode(settleChannelManager.getChannelCode());
				settleRule.setChannelName(settleChannelManager.getChannelName());
				clearingList.add(settleRule);
			}
			model.addAttribute("checklist", clearingList);
		}

		List<EnumBean> dataEntityChannelType = ChannelTypeClear.getList();

		List<SettleChannelLog> clearingCRList = Lists.newArrayList();
		for (SettleChannelLog clearingCR : page.getList()) {
			//通道业务类型
			if(StringUtils.isNotBlank(clearingCR.getChannelType())){
				clearingCR.setChannelTypeName(ChannelTypeClear.labelOf(clearingCR.getChannelType()));
			}
			String channelCode = clearingCR.getChannelCode();
			String channelName=settleRuleControlService.getChannelName(channelCode);
			clearingCR.setChannelName(channelName);
			//对账状态
			if(StringUtils.isNotBlank(clearingCR.getCheckStatus())){
				clearingCR.setCheckStatus(BillingCheckStatus.labelOf(clearingCR.getCheckStatus()));
			}
			//规则类型
			if(StringUtils.isNotBlank(clearingCR.getRuleType())){
				clearingCR.setRuleType(RuleType.labelOf(clearingCR.getRuleType()));
			}

			clearingCRList.add(clearingCR);
		}

		//支付通道类型
		model.addAttribute("checkTypeList", dataEntityChannelType);
		//对账状态
		List<EnumBean> checkStatus = Lists.newArrayList();
		for (BillingCheckStatus checkFlg : BillingCheckStatus.values()) {
			//修改
			EnumBean ct = new EnumBean();
			//为页面显示的下拉选项去除  未开始中  和   对账中的中间状态 选项
			if(checkFlg.getValue().equals(BillingCheckStatus.CHECKSTATUSWSD.getValue()) || checkFlg.getValue().equals(BillingCheckStatus.CHECKSTATUSCDD.getValue())){
				continue;
			}else{
				ct.setValue(checkFlg.getValue());
			}
			ct.setName(checkFlg.getContent());
			checkStatus.add(ct);
		}
		model.addAttribute("checkStatus", checkStatus);

		page.setList(clearingCRList);
		model.addAttribute("page", page);
		model.addAttribute("settleChannelLog", settleChannelLog);

		return "modules/reconciliation/CheckBatchList";
	}


	//记录信息导出
	@RequiresPermissions("settle:settleChannelManager:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes,SettleChannelLog settleChannelLog, HttpServletRequest request, HttpServletResponse response) {

		//格式化查询时间条件
		Date endCheckTime = settleChannelLog.getEndOperEndTime();
		if(endCheckTime!=null){
			String format = new SimpleDateFormat("yyyy-MM-dd").format(endCheckTime);
			format=format+" 23:59:59";
			try {
				Date time = endCheckTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);

				settleChannelLog.setEndOperEndTime(time);
			} catch (ParseException e) {
				logger.error("时间转换异常",e.getMessage());
			}
		}
		// 将 未开始中  和   对账中的中间状态 选项 组合成复合的条件
		if(BillingCheckStatus.CHECKSTATUSWS.getValue().equals(settleChannelLog.getCheckStatus())){
			settleChannelLog.setCheckStatusTwo(BillingCheckStatus.CHECKSTATUSWSD.getValue());
		}if(BillingCheckStatus.CHECKSTATUSCD.getValue().equals(settleChannelLog.getCheckStatus())){
			settleChannelLog.setCheckStatusTwo(BillingCheckStatus.CHECKSTATUSCDD.getValue());
		}

		List<Map<String,Object>> clearingCR = settleChannelLogService.findListExcel(settleChannelLog);

		for (Map<String,Object> map : clearingCR) {
			//通道名称
			String channelCode = (String) map.get("channelCode");
			String channelName=settleRuleControlService.getChannelName(channelCode);
			map.put("channelName", channelName);
		}
		//下载的标题行和表格插入对应的字段值
		String[] headerArray = {"通道合作方","支付通道类型","通道对账日期","对账批次号","入款总笔数","入款总金额","出款总笔数","出款总金额","差异总笔数","差异总金额","总笔数","总金额","规则类型","对账状态"};
		String[] showField = {"channelName","channelTypeName","operEndTime","checkBathNo","inRecordNum","inTotalAmount","outRecordNum","outTotalAmount","errorRecordNum","errorTotalAmount","recordNum","totalAmount","ruleType","checkStatus"};
		/**
		 * 导出为excel表格
		 */
		try {
			excelController.exportExcel("对账批次记录", headerArray,clearingCR,showField,request,response);
		} catch (Exception e) {
			logger.error("ClearingChannelRecordController list has a error:", e);
		}
	}

	/**
	 * @方法说明：上传文件跳转
	 * @时间： 2017-08-24 13:40
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelLog:edit")
	@RequestMapping(value = "/uploadFile")
	public String upload(Model model,SettleChannelLog settleChannelLog){

		List<SettleChannelManager> list=null;
		List<SettleChannelManager> channelType =null;
		try {
			//在文件上传页面查询出符合条件的通道名称和业务类型，条件 Y 生效 和  FI 文件对账
			SettleChannelManager settleChannelManager=new SettleChannelManager();
			settleChannelManager.setEffectFlg(Constants.STATIC_Y); //Y
			settleChannelManager.setCheckFlg(BillingChannelManageCheckFlg.BCMCFF.getValue());//FI

			list = settleRuleControlService.getBatchManagerName(settleChannelManager);
			channelType = settleRuleControlService.getManagerChannelType(settleChannelManager);
		} catch (Exception e) {
			logger.error("上传文件跳转--------->{}",e);
		}
		//根据查询的条数判断业务
		List<SettleRuleControl> clearingList = Lists.newArrayList();
		for (SettleChannelManager settleChannelManager : list) {
			SettleRuleControl settleRule=new SettleRuleControl();
			settleRule.setChannelName(settleChannelManager.getChannelName());
			settleRule.setChannelCode(settleChannelManager.getChannelCode());
			clearingList.add(settleRule);
		}
		model.addAttribute("checklist", clearingList);

		List<SettleRuleControl> channelTypeList = Lists.newArrayList();
		for (SettleChannelManager channelManager : channelType) {
			SettleRuleControl settleRuleChannelType=new SettleRuleControl();
			settleRuleChannelType.setLoadChannelType(channelManager.getChannelType());

			//通道业务类型翻译成中文
			if(StringUtils.isNotBlank(channelManager.getChannelType())){
				settleRuleChannelType.setChannelType(ChannelTypeClear.labelOf(channelManager.getChannelType()));
			}
			channelTypeList.add(settleRuleChannelType);
		}
		model.addAttribute("channelTypeList", channelTypeList);

		logger.info("查询数据结束--->{通道管理}",settleChannelLog);
		model.addAttribute("settleChannelLog", settleChannelLog);
		return "modules/reconciliation/LoadUpdate";
	}


	//文件上传
	@RequestMapping("/upload")
    public String handleFileUpload(SettleChannelLog settleChannelLog,
    							   @RequestParam("file") MultipartFile file,
    							   HttpServletRequest request,
    							   RedirectAttributes redirectAttributes) {

		logger.info("上传文件开始执行！");
		String path = loader.getProperty("localPath");
		logger.info("获取配置文件地址为----->"+path);
		if(StringUtils.isNotBlank(path)){
			if (!file.isEmpty()) {
	            try {
	            	//在原有的目录基础上添加当前的系统时间
	            	String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	            	String format1 = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

	            	String channelCode = settleChannelLog.getChannelCode();
	            	String channelType = settleChannelLog.getChannelType();

	            	path=path+"/"+channelCode+"/"+channelType+"/"+format+"/";
	            	boolean flag = CreateDir.createLiunxDir(path);
	            	if(!flag){
	            		logger.error("路径创建失败-------->{}",!flag);
	            		addMessage(redirectAttributes, "路径创建失败！");
						return "redirect:"+Global.getAdminPath()+"/reconciliation/SettleCheckBath";
	            	}

	            	String filename = file.getOriginalFilename();
	            	long size = file.getSize();
	            	String lastIndex=null;
	            	String  FileName=null;

	            	//根据截取的值是否是-1判断是否是组合命名
	            	int of = filename.lastIndexOf(".");
					if(of != -1){
						lastIndex = filename.substring(of);
						FileName =settleChannelLog.getChannelCode()+"_"+settleChannelLog.getChannelType()+format1+lastIndex;
						logger.error("路径创建名称-------->{}",FileName);
					}else {
						FileName =settleChannelLog.getChannelCode()+"_"+settleChannelLog.getChannelType()+format1;
						logger.error("路径创建名称-------->{}",FileName);
					}
	        		//最终生成的上传地址
	                path=path+FileName;
	                logger.info("文件地址："+path);
	                //判断是否是重复上传
	                boolean booleanPath = settleRuleControlService.getPath(path);
	                if(booleanPath){
	                	addMessage(redirectAttributes, "文件已经上传过不允许重复上传");
						return "redirect:"+Global.getAdminPath()+"/reconciliation/SettleCheckBath";
	                }

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
					addMessage(redirectAttributes, "文件上传成功");
					logger.info("文件上传成功");

					riskLogsService.savaEntity("上传", "上传对账文件，文件名称："+filename, request);
					/*try {
						logger.debug("往对账日志表插入刚才的生成数据");
						//Date date = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
						settleChannelLog.setOperBeginTime(new Date());
						settleChannelLog.setCheckFileName(path);
						settleChannelLog.setCheckStatus(BillingCheckStatus.CHECKSTATUSWS.getValue());
						settleChannelLog.setCheckBathNo(ClarBatch.getRandomString(0));
						settleRuleControlService.addLog(settleChannelLog);
						
						addMessage(redirectAttributes, "文件上传成功");
						logger.info("文件上传成功");
					} catch (Exception e) {
						addMessage(redirectAttributes, "无法完成上传操作,请维护！");
						logger.error("无法完成上传操作,请维护！",e);
					}*/
	            } catch (Exception e) {
	            	logger.error("上传错误---------->",e);
	            }
	        }else {
	        	addMessage(redirectAttributes, "上传失败，因为文件是空的");
	        	logger.debug("上传失败，因为文件是空的");
	        }
		}else {
        	addMessage(redirectAttributes, "上传失败,没有查找到路径请在对账参数中添加维护");
        }
        return "redirect:"+Global.getAdminPath()+"/reconciliation/SettleCheckBath";
	 }

	//根据批次号下载对账文件
	@RequiresPermissions("settle:settleChannelManager:view")
	@RequestMapping(value = "checkBathNo/{checkBathNo}")
	 public void downLoad(@PathVariable(value="checkBathNo") String checkBathNo,
						  HttpServletRequest request,
						  HttpServletResponse response) throws Exception{

		try {
			logger.info("根据批次号验证下载对账文件是否对账--->{条件}"+checkBathNo);
			settleRuleControlService.downLoad(checkBathNo,response);
		} catch (Exception e) {
			logger.error("根据批次号下载对账文件失败--->{文件不存在}");
		}
	}

	/**
	 *
	 * @方法说明：查看详情的方法
	 * @时间：28 Mar 201710:44:10
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelLog:view")
	@RequestMapping(value = "show/{logId}")
	public String show(@PathVariable(value="logId") long logId, Model model){

		SettleChannelLog settleChannelLog = settleChannelLogService.getEntityById(logId);

		//通道业务类型
		if(StringUtils.isNotBlank(settleChannelLog.getChannelType())){
			settleChannelLog.setChannelTypeName(ChannelTypeClear.labelOf(settleChannelLog.getChannelType()));
		}
		String channelCode = settleChannelLog.getChannelCode();
		String channelName=settleRuleControlService.getChannelName(channelCode);
		settleChannelLog.setChannelName(channelName);
		//对账状态
		if(StringUtils.isNotBlank(settleChannelLog.getCheckStatus())){
			settleChannelLog.setCheckStatus(BillingCheckStatus.labelOf(settleChannelLog.getCheckStatus()));
		}

		model.addAttribute("settleChannelLog", settleChannelLog);

		return "modules/reconciliation/settleCheckLog";
	}


	 //根据批次号验证下载对账文件是否对账
	 @RequiresPermissions("user")
	 @ResponseBody
	 @RequestMapping(value = "checkBathNoTwo/{checkBathNo}")
	 public String downLoadTwo(@PathVariable(value="checkBathNo") String checkBathNo,
						       HttpServletResponse response) throws Exception{

		 logger.info("根据批次号验证下载对账文件是否对账--->{条件}"+checkBathNo);
		 String value= settleRuleControlService.downLoadTwo(checkBathNo,response);
		 return value;

	}
}
