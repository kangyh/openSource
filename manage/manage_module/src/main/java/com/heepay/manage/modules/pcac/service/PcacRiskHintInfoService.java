package com.heepay.manage.modules.pcac.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.pcac.dao.PcacRiskHintInfoDao;
import com.heepay.manage.modules.pcac.entity.PcacBlacklist;
import com.heepay.manage.modules.pcac.entity.PcacRiskHintInfo;
import com.heepay.manage.modules.settle.service.util.ExcelService;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年3月6日下午3:22:36
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
public class PcacRiskHintInfoService extends CrudService<PcacRiskHintInfoDao, PcacRiskHintInfo>{
	
	@Autowired
	private PcacRiskHintInfoDao pcacRiskHintInfoDao;
	
	@Autowired
	private ExcelService excelService;
	@Transactional(readOnly = false)
	public int insertList(List<PcacRiskHintInfo> record) {
		return pcacRiskHintInfoDao.insertList(record);
	}
	
	
	/**
	 * 
	 * @方法说明：清算协会风险提示信息导出
	 * @时间：2016年11月16日 下午2:00:58
	 * @创建人：wangdong
	 */
	public void exportPcacBlacklistExcel(PcacRiskHintInfo pcacRiskHintInfo,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		//数据库查询结果
		List<Map<String,Object>> clearingCR = pcacRiskHintInfoDao.findListExcel(pcacRiskHintInfo);
		//导出Excel表格标题行
		String[] headerArray = {"推送批次号","商户名称","商户简称","法人证件类型","法人证件号码","法定代表人姓名","法定代表人证件类型","法定代表人（负责人）证件号码","风险信息等级","个人风险类型","有效期","有效性","推送日期"};
		//导出表格对应的字段名称
		String[] showField = {"bachNo","regName","cusName","docType","docCode","docName","legDocType","legDocCode","level","riskType","validDate","validStatus","createTime"};
		try {
			excelService.exportCusExcel("清算协会风险提示信息", headerArray,clearingCR,showField,response,request,false);
		} catch (Exception e) {
			logger.error("PcacRiskHintInfoService exportPcacBlacklistExcel has a error:{清算协会风险提示信息导出出错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
}
