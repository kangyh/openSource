/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.enterprises.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.manage.modules.util.ExcelUtil2007;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.modules.enterprises.entity.EnterprisesCertification;
import com.heepay.manage.modules.enterprises.service.EnterprisesCertificationService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *
 * 描    述：企业认证对外服务Controller
 *
 * 创 建 者： @author yangcl
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
@RequestMapping(value = "${adminPath}/enterprises/enterprisesCertification")
public class EnterprisesCertificationController extends BaseController {

	@Autowired
	private EnterprisesCertificationService enterprisesCertificationService;
	
	@ModelAttribute
	public EnterprisesCertification get(@RequestParam(required=false) String id) {
		EnterprisesCertification entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = enterprisesCertificationService.get(id);
		}
		if (entity == null){
			entity = new EnterprisesCertification();
		}
		return entity;
	}
	
	@RequiresPermissions("enterprises:enterprisesCertification:view")
	@RequestMapping(value = {"list", ""})
	public String list(EnterprisesCertification enterprisesCertification, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isEmpty(enterprisesCertification.getGroupType())){
			enterprisesCertification.setGroupType("1");
		}
		Page<EnterprisesCertification> page = enterprisesCertificationService.findPage(new Page<EnterprisesCertification>(request, response), enterprisesCertification); 
		model.addAttribute("page", page);
		model.addAttribute("groupType", enterprisesCertification.getGroupType());
		return "modules/enterprises/enterprisesCertificationList";
	}

	@RequiresPermissions("enterprises:enterprisesCertification:view")
	@RequestMapping(value = "form")
	public String form(EnterprisesCertification enterprisesCertification, Model model) {
		model.addAttribute("enterprisesCertification", enterprisesCertification);
		return "modules/enterprises/enterprisesCertificationForm";
	}

	@RequiresPermissions("enterprises:enterprisesCertification:edit")
	@RequestMapping(value = "save")
	public String save(EnterprisesCertification enterprisesCertification, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, enterprisesCertification)){
			return form(enterprisesCertification, model);
		}
		enterprisesCertificationService.save(enterprisesCertification);
		addMessage(redirectAttributes, "保存企业认证对外服务成功");
		return "redirect:"+Global.getAdminPath()+"/enterprises/enterprisesCertification/?repage";
	}
	
	@RequiresPermissions("enterprises:enterprisesCertification:edit")
	@RequestMapping(value = "delete")
	public String delete(EnterprisesCertification enterprisesCertification, RedirectAttributes redirectAttributes) {
		enterprisesCertificationService.delete(enterprisesCertification);
		addMessage(redirectAttributes, "删除企业认证对外服务成功");
		return "redirect:"+Global.getAdminPath()+"/enterprises/enterprisesCertification/?repage";
	}

	@RequiresPermissions("enterprises:enterprisesCertification:view")
	@RequestMapping(value = {"exportExcel"})
	public void exportExcel(EnterprisesCertification enterprisesCertification, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		List<String[]> contents = new ArrayList<String[]>();
		String title = "企业认证对外服务数据统计:";
		if(enterprisesCertification.getBeginCreateTime()!=null && enterprisesCertification.getEndCreateTime()!=null){
			title = "企业认证对外服务数据统计:"+ DateUtils.getDateStr(enterprisesCertification.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(enterprisesCertification.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
		}
		String[] headers = new String[] { "ID", "商户ID", "公司名称", "工商注册号", "法人代表名称", "法人代表身份证号", "认证结果", "返回信息", "通道流水号", "交易金额",
				"手续费", "公司名称比对结果", "工商注册号比对结果", "法定代表人比对结果", "法定代表人身份证号码比对结果", "通道代码", "创建时间", "更新时间"};

		Page<EnterprisesCertification> page = enterprisesCertificationService.findPage(new Page<EnterprisesCertification>(request, response), enterprisesCertification);
		int pageSize = 500;
		int totalCount = (int)page.getCount();
		int curPage = 1;//从第一页开始
		int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
		for(int i=1;i<=totalpage;i++) {
			page.setPageNo(curPage);
			page.setPageSize(pageSize);
			page = enterprisesCertificationService.findPage(page, enterprisesCertification);
			List<EnterprisesCertification> certificationList = page.getList();
			for(EnterprisesCertification record : certificationList){
				String[] content = new String[headers.length];
				content[0] = record.getId();
				content[1] = String.valueOf(record.getMerchantId());
				content[2] = record.getEntName();
				content[3] = record.getEntRegNo();
				content[4] = record.getRepName();
				content[5] = record.getRepIdNo();
				content[6] = record.getResult();
				content[7] = record.getMessage();
				content[8] = record.getChannelNo();
				content[9] = record.getRequestAmount();
				content[10] = record.getFeeAmount();
				if("0".equals(record.getEntNameMatch())){
					content[11] = "不一致";
				}else if("1".equals(record.getEntNameMatch())){
					content[11] = "一致";
				}
				if("0".equals(record.getEntRegNoMatch())){
					content[12] = "不一致";
				}else if("1".equals(record.getEntRegNoMatch())){
					content[12] = "一致";
				}
				if("0".equals(record.getRepNameMatch())){
					content[13] = "不一致";
				}else if("1".equals(record.getRepNameMatch())){
					content[13] = "一致";
				}
				if("0".equals(record.getRepIdNoMatch())){
					content[14] = "不一致";
				}else if("1".equals(record.getRepIdNoMatch())){
					content[14] = "一致";
				}
				content[15] = record.getChannelCode();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(record.getCreateTime()==null){
					content[16] = "";
				}else{
					content[16] = sdf.format(record.getCreateTime());
				}
				if(record.getUpdateTime()==null){
					content[17] = "";
				}else{
					content[17] = sdf.format(record.getUpdateTime());
				}
				contents.add(content);
			}
			curPage++;
		}
		String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
		ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
	}

	@RequiresPermissions("enterprises:enterprisesCertification:view")
	@RequestMapping(value = {"getStatisticsList"})
	public void getStatisticsList(EnterprisesCertification enterprisesCertification, HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<EnterprisesCertification> certificationList = enterprisesCertificationService.findList(enterprisesCertification);
		//汇总金额
		BigDecimal requestAmountTotalAmount = BigDecimal.ZERO;
		BigDecimal feeAmountTotalAmount = BigDecimal.ZERO;
		if(certificationList != null && !certificationList.isEmpty()){
			for(EnterprisesCertification record : certificationList){
				BigDecimal requestAmount = new BigDecimal(StringUtils.isEmpty(record.getRequestAmount())?"0.00":record.getRequestAmount());
				requestAmountTotalAmount = requestAmountTotalAmount.add(requestAmount).setScale(2, RoundingMode.HALF_UP);

				BigDecimal feeAmount = new BigDecimal(StringUtils.isEmpty(record.getFeeAmount())?"0.00":record.getFeeAmount());
				feeAmountTotalAmount = feeAmountTotalAmount.add(feeAmount).setScale(2, RoundingMode.HALF_UP);
			}
		}

		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		df.applyPattern("￥#,##0.00");
		Map<String, String> map = new HashMap<String, String>();
		map.put("requestAmountTotalAmount", df.format(requestAmountTotalAmount));
		map.put("feeAmountTotalAmount", df.format(feeAmountTotalAmount));

		WebUtil.outputJson(map, response);
	}
}