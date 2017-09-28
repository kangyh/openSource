/**
 * 
 */
package com.heepay.manage.modules.pcac.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;

import com.heepay.common.util.DateUtil;

import com.heepay.common.util.StringUtil;
import com.heepay.enums.pcac.AccountType;
import com.heepay.enums.pcac.ChageType;
import com.heepay.enums.pcac.CusNature;
import com.heepay.enums.pcac.CusType;
import com.heepay.enums.pcac.DocType;
import com.heepay.enums.pcac.ExpandType;
import com.heepay.enums.pcac.LegDocType;
import com.heepay.enums.pcac.NetworkType;
import com.heepay.enums.pcac.OpenType;
import com.heepay.enums.pcac.PcacCusStatus;
import com.heepay.enums.pcac.PcacProvince;
import com.heepay.enums.pcac.PcacReportOpStatus;
import com.heepay.enums.pcac.RiskStatus;
import com.heepay.manage.common.config.Global;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.pcac.entity.PcacMerchantReport;
import com.heepay.manage.modules.pcac.service.PcacMerchantReportService;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
 * *
 * 
* 
* 描    述：企业商户风险信息管理
*
* 创 建 者： 李震
* 创建时间：2017年3月3日
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
@RequestMapping(value = "${adminPath}/pcac/pcacMerchantReport/view")
public class PcacMerchantReportController extends BaseController {
	
	private static final Logger logger = LogManager.getLogger();
	
	
	@Autowired
	private PcacMerchantReportService pcacMerchantReportService;//
	
	/**
	 * 
	 * @方法说明：企业商户风险信息 - 分页查询
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	@RequiresPermissions("pcac:pcacMerchantReport:view")
	@RequestMapping(value = { "list", "" })
	public String list(PcacMerchantReport pcacMerchantReport, HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
		logger.info("企业商户风险信息分页查询------>{查询}----->{开始}"+printLogKeys(request));
		try {
			String pageNo = request.getParameter("pageNo");
			if( StringUtil.isBlank(pcacMerchantReport.getOpStatus()) ){
				pcacMerchantReport.setOpStatus(PcacReportOpStatus.READY_SYNDIC.getValue());
			}
			if(PcacReportOpStatus.PASS_SYNDIC_REPORTSUC.getValue().equals(pcacMerchantReport.getOpStatus())  ||
					PcacReportOpStatus.PASS_SYNDIC_REPORTFAIL.getValue().equals(pcacMerchantReport.getOpStatus())  ){
				model = pcacMerchantReportService.findPcacMerchantReportPageByBatch(new Page<PcacMerchantReport>(request, response), pcacMerchantReport,
						model,pageNo);
			}else if(PcacReportOpStatus.DELETE_REPORT.getValue().equals(pcacMerchantReport.getOpStatus() ) ){
				model = pcacMerchantReportService.findDeleteListGroupByBatch(new Page<PcacMerchantReport>(request, response), pcacMerchantReport,
						model,pageNo);
			}else{
				model = pcacMerchantReportService.findPcacMerchantReportPage(new Page<PcacMerchantReport>(request, response), pcacMerchantReport,
						model,pageNo);
			}
			model.addAttribute("enumMap", this.getEnumMapForQuery());
			model.addAttribute("pcacMerchantReport", pcacMerchantReport);
			logger.info("企业商户风险信息分页------>{查询}----->{结束}"+printLogKeys(request));
			return "modules/pcac/pcacMerchantReportQuery";
		} catch (Exception e) {
			logger.error("PcacMerchantReportController list has a error:{查询企业商户风险信息异常}"+printLogKeys(request), e);
			throw new Exception(e);
		}
		
	}
	
	/**
	 * 
	 * @方法说明：企业商户风险信息处理 - 分页查询
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	@RequiresPermissions("pcac:pcacMerchantReport:view")
	@RequestMapping(value = "handle")
	public String handle(PcacMerchantReport pcacMerchantReport, HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
		logger.info("企业商户风险信息处理分页------>{查询}----->{开始}"+printLogKeys(request));
		try {
			String pageNo = request.getParameter("pageNo");
			if( StringUtil.isBlank(pcacMerchantReport.getOpStatus()) ){
				pcacMerchantReport.setOpStatus(PcacReportOpStatus.READY_SYNDIC.getValue());
			}
			if(PcacReportOpStatus.PASS_SYNDIC_REPORTSUC.getValue().equals(pcacMerchantReport.getOpStatus())  ||
					PcacReportOpStatus.PASS_SYNDIC_REPORTFAIL.getValue().equals(pcacMerchantReport.getOpStatus())  ){
				model = pcacMerchantReportService.findPcacMerchantReportPageByBatch(new Page<PcacMerchantReport>(request, response), pcacMerchantReport,
						model,pageNo);
			}else if(PcacReportOpStatus.DELETE_REPORT.getValue().equals(pcacMerchantReport.getOpStatus() ) ){
				model = pcacMerchantReportService.findDeleteListGroupByBatch(new Page<PcacMerchantReport>(request, response), pcacMerchantReport,
						model,pageNo);
			}else{
				model = pcacMerchantReportService.findPcacMerchantReportPage(new Page<PcacMerchantReport>(request, response), pcacMerchantReport,
						model,pageNo);
			}
			model.addAttribute("enumMap", this.getEnumMapForQuery());
			model.addAttribute("pcacMerchantReport", pcacMerchantReport);
			logger.info("企业商户风险信息处理分页------>{查询}----->{结束}"+printLogKeys(request));
			return "modules/pcac/pcacMerchantReportHandle";
		} catch (Exception e) {
			logger.error("PcacMerchantReportController handle has a error:{查询企业商户风险信息异常}"+printLogKeys(request), e);
			throw new Exception(e);
		}
	}
	
	
	
	/**
	 * 
	 * @方法说明：查看企业商户风险信息详情
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	@RequiresPermissions("pcac:pcacMerchantReport:view")
	@RequestMapping(value = "viewDetail")
	public String viewDetail(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
		logger.info("查看企业商户风险信息详情------>{查询}----->{开始}"+printLogKeys(request));
		try {
			String merchantReportId = request.getParameter("merchantReportId");
			PcacMerchantReport pcacMerchantReport = pcacMerchantReportService.getPcacMerchantReportById(Long.parseLong(merchantReportId));
			model.addAttribute("pcacMerchantReport", pcacMerchantReport);
			model.addAttribute("fromWhere",request.getParameter("fromWhere"));
			model.addAttribute("enumMap", this.getEnumMapForDetail());
			request.setAttribute("type", request.getParameter("type"));
			logger.info("查看企业商户风险信息详情------>{查询}----->{结束}"+printLogKeys(request));
			return "modules/pcac/pcacMerchantReportViewDetail";
		} catch (Exception e) {
			logger.error("PcacMerchantReportController viewDetail has a error:{查看企业商户风险信息详情异常}"+printLogKeys(request), e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @方法说明：企业商户风险信息审核 初始化
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	@RequiresPermissions("pcac:pcacMerchantReport:edit")
	@RequestMapping(value = "initSyndicBatch")
	public String initSyndicBatch(PcacMerchantReport pcacMerchantReport, HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
		logger.info("企业商户风险信息审核初始化------>{查询}----->{开始}"+printLogKeys(request));
		try {
			String reportIds = request.getParameter("reportIds");
			int length = 0;
			if(reportIds!=null && reportIds.length()>0) {
				length = reportIds.split(",").length;
			}
			request.setAttribute("reportIds", reportIds);
			request.setAttribute("reportIdsLength", length);
			model.addAttribute("pcacMerchantReport", pcacMerchantReport);
			logger.info("企业商户风险信息审核初始化------>{查询}----->{结束}"+printLogKeys(request));
			return "modules/pcac/pcacMerchantBatchSyndic";
		} catch (Exception e) {
			logger.error("PcacMerchantReportController initSyndicBatch has a error:{查看企业商户风险信息审核初始化异常}"+printLogKeys(request), e);
			throw new Exception(e);
		}
	}
	
	
	
	/**
	 * 
	 * @方法说明：企业商户风险信息批量审核
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	@RequiresPermissions("pcac:pcacMerchantReport:edit")
	@RequestMapping(value = "syndicBatch")
	@ResponseBody
	public String syndicBatch(PcacMerchantReport syndicEntity,HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
		try {
			String currentDate = DateUtil.getTodayYYYYMMDD_HHMMSS();
			String username = UserUtils.getUser().getName();
			syndicEntity.setReviewer(username);
			syndicEntity.setReviewTime(DateUtil.stringToDate(currentDate, "yyyy-MM-dd HH:mm:ss")  );
			int num = pcacMerchantReportService.updatePcacMerchantReport(syndicEntity);
			model.addAttribute("pcacMerchantReport", syndicEntity);
			model.addAttribute("enumMap", this.getEnumMapForQuery());
			//return "modules/pcac/pcacMerchantReportHandle";
			if(num>0){
				return "审核成功"+num+"条记录！";
			}else{
				return "审核失败"+num+"条记录！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("PcacMerchantReportController viewDetail has a error:{企业商户风险信息审核异常}", e);
			return "审核失败！";
		}
	}
	/**
	 * 
	 * @方法说明：
	 * @时间：
	 * @创建人：
	 */
	@RequiresPermissions("pcac:pcacMerchantReport:view")
	@RequestMapping(value = "viewBatchDetail")
	public String viewBatchDetail(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
		try {
			String pageNo = request.getParameter("pageNo");
			String batchNo =request.getParameter("batchNo");
			String opStatus =request.getParameter("opStatus");
			String reportIds_q =request.getParameter("reportIds");
			PcacMerchantReport pcacMerchantReport = new PcacMerchantReport();
			pcacMerchantReport.setOpStatus(opStatus);
			StringBuffer sb = new StringBuffer();
			if("06".equals(opStatus)){
				if( !StringUtil.isBlank(reportIds_q)){
					pcacMerchantReport.setReportIds(reportIds_q.split(","));
				}else{
					List<String> reportIds = pcacMerchantReportService.getReportIdsByDelBatchNo(batchNo);
					String[] array = null;
					if(reportIds!=null&&reportIds.size()>0){
						for(String reportId : reportIds){
							sb.append(reportId+",");
						}
						array = sb.toString().split(",");
					}
					pcacMerchantReport.setReportIds(array);
				}
				model = pcacMerchantReportService.findPcacMerchantReportPage(new Page<PcacMerchantReport>(request, response), pcacMerchantReport,
							model,pageNo);
			}else{
				pcacMerchantReport.setBatchNo(batchNo);
				if( !StringUtil.isBlank(reportIds_q)){
					pcacMerchantReport.setReportIds(reportIds_q.split(","));
				}else{
					List<String> reportIds = pcacMerchantReportService.getReportIdsByRepBatchNo(batchNo);
					String[] array = null;
					if(reportIds!=null&&reportIds.size()>0){
						for(String reportId : reportIds){
							sb.append(reportId+",");
						}
						array = sb.toString().split(",");
					}
					pcacMerchantReport.setReportIds(array);
				}
				model = pcacMerchantReportService.findPcacMerchantReportPage(new Page<PcacMerchantReport>(request, response), pcacMerchantReport,
						model,pageNo);
			}
//			request.setAttribute("batchNo", batchNo);
			request.setAttribute("opStatus", opStatus);
			request.setAttribute("reportIds", sb.toString());
			request.setAttribute("type", request.getParameter("type"));
			model.addAttribute("fromWhere","batchDetail");
			model.addAttribute("enumMap", this.getEnumMapForQuery());
			model.addAttribute("pcacMerchantReport", pcacMerchantReport);
			return "modules/pcac/pcacMerchantBatchDetail";
		} catch (Exception e) {
			logger.error("PcacMerchantReportController viewBatchDetail has a error:{查询企业商户上报批次信息异常}", e);
			throw new Exception(e);
		}
	}
	/**
	 * @方法说明：导出企业商户信息记录
	 * @时间：
	 * @创建人：
	 */
	@RequiresPermissions("pcac:pcacMerchantReport:edit")
	@RequestMapping(value = "export")
	public void export(HttpServletRequest request, HttpServletResponse response, PcacMerchantReport pcacMerchantReport)
			throws Exception {
		try {
			// 显示要查询的信息
			String exportParam = request.getParameter("exportParam");
			String[] merchantReportIds = null;
			if(!StringUtil.isBlank(exportParam)){
				merchantReportIds = exportParam.split(",");
			}
			pcacMerchantReportService.export(pcacMerchantReport, request, response,merchantReportIds);
		} catch (Exception e) {
			logger.error("PcacMerchantReportController export has a error:{企业商户上报信息表导出错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	
	/**
	 * 
	 * @方法说明：批量删除数据操作
	 * @时间：
	 * @创建人：
	 */
	@RequiresPermissions("pcac:pcacMerchantReport:edit")
	@RequestMapping(value ="deleteManyData")
	@ResponseBody 
	public String deleteManyData( HttpServletRequest request,RedirectAttributes redirectAttributes,HttpServletResponse response){
		logger.info("批量------>{删除}----->{开始}");
		String reportIds = request.getParameter("reportIds");
		PcacMerchantReport report = null;
		report = new PcacMerchantReport();
		report.setReportIds(reportIds.split(","));
		String msg = "";
		int num = pcacMerchantReportService.deleteManyPcacMerchantReport( report  );
		if(num>0){
			msg = "删除成功"+num+"条数据!";
			//addMessage(redirectAttributes, "删除成功"+num+"条数据!");
		}else{
			msg = "删除失败"+num+"条数据!";
			//addMessage(redirectAttributes, "删除失败"+num+"条数据!");
		}
		logger.info("批量------>{删除}----->{结束}");
		//跳回查看的总列表
		//return "redirect:"+Global.getAdminPath()+"/pcac/pcacMerchantReport/view/handle"; 
		return msg;
	}

	/**
	 * 
	 * @方法说明：上报企业商户信息（批量）
	 * @时间：2017-3-14
	 * @创建人：lizhen
	 */
	@RequiresPermissions("pcac:pcacMerchantReport:edit")
	@RequestMapping(value ="reportManyData")
	@ResponseBody
	public String reportManyData( HttpServletRequest request,RedirectAttributes redirectAttributes,HttpServletResponse response){
		logger.info("批量------>{上报}----->{开始}");
		String reportIds = request.getParameter("reportIds");
		PcacMerchantReport report = null;
		report = new PcacMerchantReport();
		report.setReportIds(reportIds.split(","));
		String msg = "";
		boolean result = pcacMerchantReportService.reportData(report);
		if(result){
			msg = "上报成功"+report.getReportIds().length+"条数据!";
			//addMessage(redirectAttributes, "上报成功"+report.getReportIds().length+"条数据!");
		}else{
			msg = "上报失败"+report.getReportIds().length+"条数据!";
			//addMessage(redirectAttributes, "上报失败"+report.getReportIds().length+"条数据!");
		}
		//跳回查看的总列表
		//return "redirect:"+Global.getAdminPath()+"/pcac/pcacMerchantReport/view/handle"; 
		return msg;
	}

	/**
	 * 
	 * @方法说明：接口删除企业商户信息（批量）
	 * @时间：2017-3-14
	 * @创建人：李震
	 */
	@RequiresPermissions("pcac:pcacMerchantReport:edit")
	@RequestMapping(value ="reportManyDelData")
	@ResponseBody
	public String reportManyDelData( HttpServletRequest request,RedirectAttributes redirectAttributes,HttpServletResponse response){
		logger.info("批量------>{接口删除}----->{开始}");
		//调用删除接口
		String reportIds = request.getParameter("reportIds");
		PcacMerchantReport report = null;
		report = new PcacMerchantReport();
		report.setReportIds(reportIds.split(","));
		String msg = "";
		boolean result = pcacMerchantReportService.reportDelData(report);
		if(result){
			msg ="接口删除成功"+report.getReportIds().length+"条数据！" ;
			//addMessage(redirectAttributes, "接口删除成功"+report.getReportIds().length+"条数据！");
		}else{
			msg = "接口删除失败"+report.getReportIds().length+"条数据！";
			//addMessage(redirectAttributes, "接口删除失败"+report.getReportIds().length+"条数据！");
		}
		//跳回查看的总列表
		//return "redirect:"+Global.getAdminPath()+"/pcac/pcacMerchantReport/view/handle"; 
		return msg;
	}
	
	/**
	 * 
	 * @方法说明：上传文件入库
	 * @时间：
	 * @创建人：
	 */
	@RequiresPermissions("pcac:pcacMerchantReport:edit")
	@RequestMapping(value ="readyUploadFile")
	public String readyUploadFile( HttpServletRequest request , HttpServletResponse response, Model model) throws IOException{
		logger.info("企业商户信息上传初始化");
		String reportIds = request.getParameter("reportIds");
		if(!StringUtil.isBlank(reportIds)){
			String pageNo = request.getParameter("pageNo");
			PcacMerchantReport pcacMerchantReport = null;
			pcacMerchantReport = new PcacMerchantReport();
			String[] array = reportIds.split(",");
			pcacMerchantReport.setReportIds(array);
			model = pcacMerchantReportService.findPcacMerchantReportPage(new Page<PcacMerchantReport>(request, response), pcacMerchantReport,model,pageNo);
		}
		model.addAttribute("enumMap", this.getEnumMapForQuery());
		model.addAttribute("reportIds", reportIds);
		return "modules/pcac/pcacMerchantReportUpload";
	}
	/**
	 * 
	 * @方法说明：上传文件入库
	 * @时间：
	 * @创建人：
	 */
	@RequiresPermissions("pcac:pcacMerchantReport:edit")
	@RequestMapping(value ="loadModel")
	public String loadModel(RedirectAttributes redirectAttributes,@RequestParam("file") MultipartFile file,HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		String reportIds = null;
		logger.info("企业商户信息----->{上传文件入库}--->{applicationId}=");
			Map<String,Object> msg=pcacMerchantReportService.loadModel(file);
			reportIds = msg.get("reportIds")==null?"":msg.get("reportIds").toString();
			addMessage(redirectAttributes, msg.get("msg").toString());
			logger.info("企业商户信息上传结果:"+msg.get("msg").toString());
		//添加页面操作
		return "redirect:"+Global.getAdminPath()+"/pcac/pcacMerchantReport/view/readyUploadFile?reportIds="+reportIds; 
		
	}
	/**
	 * @方法说明：日志中打印关键字，sessionid,loginname
	 * @时间：2017年3月21日
	 * @创建人：李震
	 */
	private String printLogKeys(HttpServletRequest request){
		return ";loginName="+UserUtils.getUser().getLoginName()+",sessionId="+request.getSession().getId();
	}
	private Map<String,List<EnumBean>> getEnumMapForQuery() {
		 Map<String,List<EnumBean>> enumMap = null;
		 enumMap =  new HashMap<String,List<EnumBean>>();
		 List<EnumBean> opStatus = Lists.newArrayList();
		 List<EnumBean> legDocType = Lists.newArrayList();
		 List<EnumBean> docType = Lists.newArrayList();
		for (PcacReportOpStatus en : PcacReportOpStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			opStatus.add(ct);
		}
		for (LegDocType en : LegDocType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			legDocType.add(ct);
		}
		for (DocType en : DocType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			docType.add(ct);
		}
		enumMap.put("opStatus", opStatus);
		enumMap.put("legDocType", legDocType);
		enumMap.put("docType", docType);

		 return enumMap;
	}
	private Map<String,List<EnumBean>> getEnumMapForDetail() {
		 Map<String,List<EnumBean>> enumMap = null;
		 enumMap =  new HashMap<String,List<EnumBean>>();
		 List<EnumBean> opStatus = Lists.newArrayList();
		 List<EnumBean> cusType = Lists.newArrayList();
		 List<EnumBean> cusNature = Lists.newArrayList();
		 List<EnumBean> legDocType = Lists.newArrayList();
		 List<EnumBean> docType = Lists.newArrayList();
		 List<EnumBean> pcacCusStatus = Lists.newArrayList();
		 List<EnumBean> riskStatus = Lists.newArrayList();
		 List<EnumBean> openType = Lists.newArrayList();
		 List<EnumBean> accountType = Lists.newArrayList();
		 List<EnumBean> chageType = Lists.newArrayList();
		 List<EnumBean> expandType = Lists.newArrayList();
		 List<EnumBean> networkType = Lists.newArrayList();
		 List<EnumBean> pcacProvince = Lists.newArrayList();
		for (PcacReportOpStatus en : PcacReportOpStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			opStatus.add(ct);
		}
		for (CusType en : CusType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			cusType.add(ct);
		}
		for (CusNature en : CusNature.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			cusNature.add(ct);
		}
		for (LegDocType en : LegDocType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			legDocType.add(ct);
		}
		for (DocType en : DocType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			docType.add(ct);
		}
		for (PcacCusStatus en : PcacCusStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			pcacCusStatus.add(ct);
		}
		for (RiskStatus en : RiskStatus.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			riskStatus.add(ct);
		}
		for (OpenType en : OpenType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			openType.add(ct);
		}
		for (AccountType en : AccountType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			accountType.add(ct);
		}
		for (ChageType en : ChageType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			chageType.add(ct);
		}
		for (ExpandType en : ExpandType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			expandType.add(ct);
		}
		for (NetworkType en : NetworkType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			networkType.add(ct);
		}
		for(PcacProvince en: PcacProvince.values()){
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			pcacProvince.add(ct);
		}
		enumMap.put("opStatus", opStatus);
		enumMap.put("cusType", cusType);
		enumMap.put("cusNature", cusNature);
		enumMap.put("legDocType", legDocType);
		enumMap.put("docType", docType);
		enumMap.put("pcacCusStatus", pcacCusStatus);
		enumMap.put("riskStatus", riskStatus);
		enumMap.put("accountType", accountType);
		enumMap.put("chageType", chageType);
		enumMap.put("expandType", expandType);
		enumMap.put("networkType", networkType);
		enumMap.put("pcacProvince", pcacProvince);
		enumMap.put("openType", openType);
		 return enumMap;
	}
}
