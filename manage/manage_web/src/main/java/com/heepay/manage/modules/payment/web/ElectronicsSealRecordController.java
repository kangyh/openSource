/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.ChargeRecordStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.ElectronicsSealRecord;
import com.heepay.manage.modules.payment.service.ElectronicsSealRecordService;
import com.heepay.manage.modules.util.ExcelUtil2007;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *
 * 描    述：电子签章订单Controller
 *
 * 创 建 者： @author zjx
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
@Controller
@RequestMapping(value = "${adminPath}/payment/electronicsSealRecord")
public class ElectronicsSealRecordController extends BaseController {

	@Autowired
	private ElectronicsSealRecordService electronicsSealRecordService;
	
	@ModelAttribute
	public ElectronicsSealRecord get(@RequestParam(required=false) String id) {
		ElectronicsSealRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = electronicsSealRecordService.get(id);
		}
		if (entity == null){
			entity = new ElectronicsSealRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:electronicsSealRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(ElectronicsSealRecord electronicsSealRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isEmpty(electronicsSealRecord.getGroupType())){
			electronicsSealRecord.setGroupType("1");
		}
		Page<ElectronicsSealRecord> page = electronicsSealRecordService.findPage(new Page<ElectronicsSealRecord>(request, response), electronicsSealRecord); 
		model.addAttribute("page", page);
		model.addAttribute("groupType", electronicsSealRecord.getGroupType());
		return "modules/payment/electronicsSealRecordList";
	}

	@RequiresPermissions("payment:electronicsSealRecord:view")
	@RequestMapping(value = "form")
	public String form(ElectronicsSealRecord electronicsSealRecord, Model model) {
		model.addAttribute("electronicsSealRecord", electronicsSealRecord);
		return "modules/payment/electronicsSealRecordForm";
	}

	@RequiresPermissions("payment:electronicsSealRecord:edit")
	@RequestMapping(value = "save")
	public String save(ElectronicsSealRecord electronicsSealRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, electronicsSealRecord)){
			return form(electronicsSealRecord, model);
		}
		electronicsSealRecordService.save(electronicsSealRecord);
		addMessage(redirectAttributes, "保存电子签章订单成功");
		return "redirect:"+Global.getAdminPath()+"/payment/electronicsSealRecord/?repage";
	}
	
	@RequiresPermissions("payment:electronicsSealRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(ElectronicsSealRecord electronicsSealRecord, RedirectAttributes redirectAttributes) {
		electronicsSealRecordService.delete(electronicsSealRecord);
		addMessage(redirectAttributes, "删除电子签章订单成功");
		return "redirect:"+Global.getAdminPath()+"/payment/electronicsSealRecord/?repage";
	}

	@RequiresPermissions("payment:electronicsSealRecord:view")
	@RequestMapping(value = {"exportExcel"})
	public void exportExcel(ElectronicsSealRecord electronicsSealRecord, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<String[]> contents = new ArrayList<String[]>();
		String title = "签章订单数据统计:";
		if(electronicsSealRecord.getBeginCreateTime()!=null && electronicsSealRecord.getEndCreateTime()!=null){
			title = "签章订单数据统计:"+ DateUtils.getDateStr(electronicsSealRecord.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(electronicsSealRecord.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
		}
		String[] headers = new String[] { "编号", "签章序列号", "商户编码", "商户公司", "手续费金额",
				"平台来源", "签约产品", "通道编码", "签约类型", "状态", "创建时间", "更新时间", "备注" };
		Page<ElectronicsSealRecord> page = electronicsSealRecordService.findPage(new Page<ElectronicsSealRecord>(request, response), electronicsSealRecord);
		int pageSize = 500;
		int totalCount = (int)page.getCount();
		int curPage = 1;//从第一页开始
		int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
		for(int i=1;i<=totalpage;i++) {
			page.setPageNo(curPage);
			page.setPageSize(pageSize);
			page = electronicsSealRecordService.findPage(page, electronicsSealRecord);
			List<ElectronicsSealRecord> electronicsSealList = page.getList();
			for(ElectronicsSealRecord record : electronicsSealList){
				String[] content = new String[headers.length];
				content[0] = record.getId();
				content[1] = record.getElectronicsSealId();
				content[2] = String.valueOf(record.getMerchantId());
				content[3] = record.getMerchantCompany();
				content[4] = record.getFeeAmount();
				content[5] = record.getSysResource();
				content[6] = record.getChannelCode();
				content[7] = record.getSealType().toString();
				if("SUCCES".equals(record.getStatus())){
					content[8] = "成功";
				}else if("FAILED".equals(record.getStatus())){
					content[8] = "失败";
				}else if("PAYING".equals(record.getStatus())){
					content[8] = "签约中";
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				content[9] = sdf.format(record.getCreateTime());
				content[10] = sdf.format(record.getUpdateTime());
				content[11] = record.getRemark();
				contents.add(content);
			}
			curPage++;
		}
		String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
		ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
	}

	@RequiresPermissions("payment:electronicsSealRecord:view")
	@RequestMapping(value="getStatisticsList")
	@ResponseBody
	public void getStatisticsList(ElectronicsSealRecord electronicsSealRecord, HttpServletResponse response) {
		List<ElectronicsSealRecord> electronicsSealList = electronicsSealRecordService.findList(electronicsSealRecord);
		//汇总金额
		BigDecimal electronicsSealTotalAmount = BigDecimal.ZERO;
		BigDecimal successTotalAmount = BigDecimal.ZERO;
		BigDecimal failedTotalAmount = BigDecimal.ZERO;
		BigDecimal payingTotalAmount = BigDecimal.ZERO;
		if(electronicsSealList != null && !electronicsSealList.isEmpty()) {
			for (ElectronicsSealRecord record : electronicsSealList) {
				BigDecimal electronicsAmount = new BigDecimal(StringUtils.isEmpty(record.getFeeAmount())?"0.00":record.getFeeAmount());
				electronicsSealTotalAmount = electronicsSealTotalAmount.add(electronicsAmount).setScale(2, RoundingMode.HALF_UP);
				if(StringUtils.equals(record.getStatus(), "SUCCES")){
					//成功金额
					BigDecimal successAmount = new BigDecimal(StringUtils.isEmpty(record.getFeeAmount())?"0.00":record.getFeeAmount());
					successTotalAmount = successTotalAmount.add(successAmount).setScale(2, RoundingMode.HALF_UP);
				}else if(StringUtils.equals(record.getStatus(), "FAILED")){
					//失败金额
					BigDecimal failedAmount = new BigDecimal(StringUtils.isEmpty(record.getFeeAmount())?"0.00":record.getFeeAmount());
					failedTotalAmount = failedTotalAmount.add(failedAmount).setScale(2, RoundingMode.HALF_UP);
				}else if(StringUtils.equals(record.getStatus(), "PAYING")){
					//签约中金额
					BigDecimal payingAmount = new BigDecimal(StringUtils.isEmpty(record.getFeeAmount())?"0.00":record.getFeeAmount());
					payingTotalAmount = payingTotalAmount.add(payingAmount).setScale(2, RoundingMode.HALF_UP);
				}
			}
		}
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		df.applyPattern("￥#,##0.00");
		Map<String, String> map = new HashMap<String, String>();
		map.put("electronicsSealTotalAmount", df.format(electronicsSealTotalAmount));
		map.put("successTotalAmount", df.format(successTotalAmount));
		map.put("failedTotalAmount", df.format(failedTotalAmount));
		map.put("payingTotalAmount", df.format(payingTotalAmount));

		WebUtil.outputJson(map, response);
	}
}