/**
 * 
 */
package com.heepay.manage.modules.pcac.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomUtils;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.PropertiesLoader;
import com.heepay.common.util.StringUtil;
import com.heepay.enums.pcac.RiskType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.utils.HttpClientUtil;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.pcac.entity.PcacBlacklist;
import com.heepay.manage.modules.pcac.entity.PcacResendApply;
import com.heepay.manage.modules.pcac.entity.PcacRiskHintInfo;
import com.heepay.manage.modules.pcac.service.PcacBlacklistService;
import com.heepay.manage.modules.pcac.service.PcacRiskHintInfoService;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/pcac/pcacResendApply/view")
public class PcacResendApplyController extends BaseController {
	
	@Autowired
	private PcacRiskHintInfoService pcacRiskHintInfoService;
	@Autowired
	private PcacBlacklistService pcacBlacklistService;
	private static final Logger logger = LogManager.getLogger();
	
	@RequiresPermissions("pcac:pcacResendApply:view")
	@RequestMapping(value = { "list", "" })
	public String list( PcacResendApply pcacResendApply ,HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		List<EnumBean> riskTypes = Lists.newArrayList();
		for (RiskType en : RiskType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(en.getValue());
			ct.setName(en.getContent());
			riskTypes.add(ct);
		}
		model.addAttribute("riskTypes", riskTypes);
		return "modules/pcac/pcacResendApply";
	}
	
	
	@RequiresPermissions("pcac:pcacResendApply:edit")
	@RequestMapping(value ="resendApply")
	@ResponseBody
	public String resendApply( HttpServletRequest request,RedirectAttributes redirectAttributes,HttpServletResponse response){
		logger.info("补发申请------>{调用接口}----->{开始}");
		String riskType = request.getParameter("riskType");
		String beginDate = request.getParameter("beginDate");
//		String endDate = request.getParameter("endDate");
		boolean resultBool = false;
		Map<String,String> params = new HashMap<String,String>();
		params.put("reqDate", beginDate);
		params.put("riskType", riskType);
		String json = JSONObject.toJSONString(params);
		PropertiesLoader loader = new PropertiesLoader("pcacRpc.properties");
		String path = loader.getProperty("resendUrl");
		String resendUrlStatus = loader.getProperty("resendUrlStatus");
		Map<String,Object> map = new HashMap<String,Object>();
		if("0".equals(resendUrlStatus)){
			map = HttpClientUtil.getInstance(). sendJsonHttpPostWithHeader(path, json);
		}else{
			String resStr = 
//				"{\"resHeader\":{\"version\":\"V1.0.1\",\"identification\":\"201703220000000769\",\"origSender\":\"Z2011611000017\",\"origSenderSID\":\"HYYTJAVA\",\"recSystemId\":\"R0001\",\"trnxCode\":\"TS0004\",\"trnxTime\":\"20170322120015\",\"secretKey\":\"gCbX8f2ca\"},\"body\":"+
//					"{\"respInfo\":{\"resultStatus\":\"01\",\"resultCode\":\"S00000\"},\"queryInfo\":{\"riskType\":\"01\",\"reqDate\":\"2017-03-17\"},\"pcacList\":[{\"riskType\":\"03\",\"cusName\":\"JlF15x3nKCddGWE5BXIR+FtdIXHwBJsLiGbAxCM2BFo=\",\"docCode\":\"111111111111111111111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"111111111111111111111\",\"docType\":\"01\",\"legDocType\":\"06\",\"legDocCode\":\"111111111111111111111\",\"legDocName\":\"111111111111111111111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"111\",\"docType\":\"05\",\"legDocType\":\"02\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"222\",\"docType\":\"05\",\"legDocType\":\"02\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"222\",\"docType\":\"03\",\"legDocType\":\"03\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"03\",\"cusName\":\"JlF15x3nKCddGWE5BXIR+FtdIXHwBJsLiGbAxCM2BFo=\",\"docCode\":\"111111111111111111111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"111111111111111111111\",\"docType\":\"01\",\"legDocType\":\"06\",\"legDocCode\":\"111111111111111111111\",\"legDocName\":\"111111111111111111111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"111\",\"docType\":\"05\",\"legDocType\":\"02\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"222\",\"docType\":\"05\",\"legDocType\":\"02\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"222\",\"docType\":\"03\",\"legDocType\":\"03\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"03\",\"cusName\":\"JlF15x3nKCddGWE5BXIR+FtdIXHwBJsLiGbAxCM2BFo=\",\"docCode\":\"111111111111111111111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"111111111111111111111\",\"docType\":\"01\",\"legDocType\":\"06\",\"legDocCode\":\"111111111111111111111\",\"legDocName\":\"111111111111111111111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"111\",\"docType\":\"05\",\"legDocType\":\"02\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"222\",\"docType\":\"05\",\"legDocType\":\"02\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"222\",\"docType\":\"03\",\"legDocType\":\"03\",\"legDocCode\":\"111\",\"legDocName\":\"111\"}]}"
//					+"}";
			"{\"respInfo\":{\"resultStatus\":\"01\",\"resultCode\":\"S00001\"}}";

			map.put("responseContent", resStr);
		}
		logger.info("调用补发申请接口，响应结果:"+map);
		if(map==null || map.get("responseContent") ==null || "".equals(map.get("responseContent"))){
			logger.info("上报个人商户风险信息失败，返回结果为空");
		}else{
			JSONObject jobj = JSONObject.parseObject(map.get("responseContent").toString());
			
			JSONObject body = JSONObject.parseObject(jobj.getString("body"));
			JSONObject header = JSONObject.parseObject(jobj.getString("resHeader"));
			String respInfo= "";
			String pcacList = "";
			if(body!=null && header!=null ){
				respInfo= body.getString("respInfo");
				pcacList = body.getString("pcacList");
			}
			else{
				respInfo= jobj.getString("respInfo");
			}
			
			JSONObject respInfo_obj = null;
			if( !StringUtil.isBlank(respInfo)){
				respInfo_obj = JSONObject.parseObject(respInfo);
			}
			String createTime = DateUtil.getTodayYYYYMMDD_HHMMSS();
			
			int num = 0;
			if(   respInfo_obj!=null &&    "S00000".equals(respInfo_obj.getString("resultCode"))    ){
				logger.info("调用补发申请接口成功");
				if( StringUtil.isBlank( pcacList )){
					logger.info("调用补发申请接口成功,但没有结果列表！");
				}else{
					if("01".equals(riskType)){
						List<PcacBlacklist>  list = (List<PcacBlacklist>)JSONArray.parseArray(pcacList,PcacBlacklist.class);
						for( PcacBlacklist vo : list){
							if("0".equals(resendUrlStatus)){//调用接口则取响应头
								vo.setBachNo(header.getString("identification"));
							}else{
								vo.setBachNo(getBatchNo());
							}
							vo.setDocName(vo.getLegDocName());
							vo.setCreatetime(DateUtil.stringToDate( createTime, "yyyy-MM-dd HH:mm:ss"));
						}
						num = pcacBlacklistService.insertList(list);
					}else if("02".equals(riskType)){
						List<PcacRiskHintInfo>  list = (List<PcacRiskHintInfo>)JSONArray.parseArray( pcacList,PcacRiskHintInfo.class);
						for( PcacRiskHintInfo vo :list){
							if("0".equals(resendUrlStatus)){//调用接口则取响应头
								vo.setBatchNo(header.getString("identification"));
							}else{
								vo.setBatchNo(getBatchNo());
							}
							vo.setDocName(vo.getLegDocName());
							vo.setCreateTime(DateUtil.stringToDate( createTime, "yyyy-MM-dd HH:mm:ss"));
						}
						num = pcacRiskHintInfoService.insertList(list);
					}
					if(num>0){
						logger.info("调用补发申请接口成功,入库成功！");
					}
					
				}
				
				
				resultBool = true;
			}else{
				if(  respInfo_obj!=null && !"".equals(respInfo_obj.toString())  ){
					logger.info("上报个人商户风险信息失败，返回："+respInfo_obj.getString("resultCode"));
				}else{
					logger.info("上报个人商户风险信息失败，返回：");
				}
			}
		}
		if(resultBool){
			//addMessage(redirectAttributes, "补发成功!");
			return "补发成功！";
		}else{
			//addMessage(redirectAttributes, "补发失败!");
			return "补发失败！";
		}
		//跳回查看的总列表
		//return "redirect:"+Global.getAdminPath()+"/pcac/pcacResendApply/view?random="+RandomUtil.getFourNumber(9999); 
	}
	private  String getBatchNo() {
		return DateUtil.getTodayYYYYMMDDHHmmss()+RandomUtils.nextInt(1111, 9999);
	}
	public static void main(String[] args) throws Exception {
		String result = "{\"respInfo\":{\"resultStatus\":\"01\",\"resultCode\":\"S00000\"},\"queryInfo\":{\"riskType\":\"01\",\"reqDate\":\"2017-03-17\"},\"pcacList\":[{\"riskType\":\"03\",\"cusName\":\"JlF15x3nKCddGWE5BXIR+FtdIXHwBJsLiGbAxCM2BFo=\",\"docCode\":\"111111111111111111111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"111111111111111111111\",\"docType\":\"01\",\"legDocType\":\"06\",\"legDocCode\":\"111111111111111111111\",\"legDocName\":\"111111111111111111111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"111\",\"docType\":\"05\",\"legDocType\":\"02\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"222\",\"docType\":\"05\",\"legDocType\":\"02\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"222\",\"docType\":\"03\",\"legDocType\":\"03\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"03\",\"cusName\":\"JlF15x3nKCddGWE5BXIR+FtdIXHwBJsLiGbAxCM2BFo=\",\"docCode\":\"111111111111111111111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"111111111111111111111\",\"docType\":\"01\",\"legDocType\":\"06\",\"legDocCode\":\"111111111111111111111\",\"legDocName\":\"111111111111111111111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"111\",\"docType\":\"05\",\"legDocType\":\"02\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"222\",\"docType\":\"05\",\"legDocType\":\"02\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"222\",\"docType\":\"03\",\"legDocType\":\"03\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"03\",\"cusName\":\"JlF15x3nKCddGWE5BXIR+FtdIXHwBJsLiGbAxCM2BFo=\",\"docCode\":\"111111111111111111111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"111111111111111111111\",\"docType\":\"01\",\"legDocType\":\"06\",\"legDocCode\":\"111111111111111111111\",\"legDocName\":\"111111111111111111111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"111\",\"docType\":\"05\",\"legDocType\":\"02\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"222\",\"docType\":\"05\",\"legDocType\":\"02\",\"legDocCode\":\"111\",\"legDocName\":\"111\"},{\"riskType\":\"04\",\"cusName\":\"111\",\"docCode\":\"111\",\"level\":\"01\",\"validDate\":\"2017-03-17\",\"validStatus\":\"01\",\"regName\":\"222\",\"docType\":\"03\",\"legDocType\":\"03\",\"legDocCode\":\"111\",\"legDocName\":\"111\"}]}";
		JSONObject jobj = JSONObject.parseObject(result);
		//JSONArray jobj2 = JSONObject.parseArray(jobj.getString("pcacList"));
		List<PcacRiskHintInfo>  sdfds = (List<PcacRiskHintInfo>)JSONArray.parseArray(jobj.getString("pcacList"),PcacRiskHintInfo.class);
		List<PcacBlacklist>  sdfds2 = (List<PcacBlacklist>)JSONArray.parseArray(jobj.getString("pcacList"),PcacBlacklist.class);
		System.out.println("打印list");
	}
}
