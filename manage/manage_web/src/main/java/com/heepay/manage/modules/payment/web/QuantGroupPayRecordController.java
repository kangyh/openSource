/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.enums.tpds.InstallmentType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.entity.QuantGroupPayRecord;
import com.heepay.manage.modules.payment.service.PaymentRecordService;
import com.heepay.manage.modules.payment.service.QuantGroupPayRecordService;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.util.ExcelUtil2007;
import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *
 * 描    述：量化派白条支付记录Controller
 *
 * 创 建 者： @author TangWei
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
@RequestMapping(value = "${adminPath}/payment/quantGroupPayRecord")
public class QuantGroupPayRecordController extends BaseController {

	@Autowired
	private QuantGroupPayRecordService quantGroupPayRecordService;

	@Autowired
	private PaymentRecordService paymentRecordService;
	
	@ModelAttribute
	public QuantGroupPayRecord get(@RequestParam(required=false) String id) {
		QuantGroupPayRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = quantGroupPayRecordService.get(id);
		}
		if (entity == null){
			entity = new QuantGroupPayRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:quantGroupPayRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(QuantGroupPayRecord quantGroupPayRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isBlank(quantGroupPayRecord.getGroupType())){
			quantGroupPayRecord.setGroupType("1");
		}
		Page<QuantGroupPayRecord> page = quantGroupPayRecordService.findPage(new Page<QuantGroupPayRecord>(request, response), quantGroupPayRecord);
		for(QuantGroupPayRecord record:page.getList()){
			PaymentRecord paymentRecord = paymentRecordService.getOne(record.getQuantId());
			if(paymentRecord!=null){
				record.setPaymentId(paymentRecord.getPaymentId());
			}
			//把通道展示出来
			if(InstallmentType.QUANTPAY.getValue().equals(record.getExtData())||InstallmentType.LEBAIFEN.getValue().equals(record.getExtData())){
				record.setExtData(InstallmentType.labelOf(record.getExtData()));
			}else{
				record.setExtData("");
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("groupType", quantGroupPayRecord.getGroupType());
		return "modules/payment/quantGroupPayRecordList";
	}

	@RequiresPermissions("payment:quantGroupPayRecord:view")
	@RequestMapping(value = "exportExcel")
	public void exportExcel(QuantGroupPayRecord quantGroupPayRecord, HttpServletResponse response) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<QuantGroupPayRecord> quantGroupPayRecordDetailList = quantGroupPayRecordService.findList(quantGroupPayRecord);
		Map<String, String> totalMap = getTotalAmount(quantGroupPayRecordDetailList);
		List<String[]> contents = new ArrayList<String[]>();
		String title = "";
		String[] headers = new String[] { "商户订单号", "分期支付通道类型", "分期订单金额", "分期期数", "分期费率", "分期支付成功时间", "支付流水号", "总金额", "总笔数"};

		for(QuantGroupPayRecord record : quantGroupPayRecordDetailList){
			String[] content = new String[headers.length];
			PaymentRecord paymentRecord = paymentRecordService.getOne(record.getQuantId());
			content[0] = "";
			//把通道展示出来
			content[1]="";
			if(InstallmentType.QUANTPAY.getValue().equals(record.getExtData())||InstallmentType.LEBAIFEN.getValue().equals(record.getExtData())){
				content[1]=InstallmentType.labelOf(record.getExtData());
			}
			content[2] = record.getOrderAmount();
			content[3] = DictUtils.getDictLabel(record.getTerm(), "InstallmentType", "");
			content[4] = "";
			//将费率解析出来
			String repaymentPlanData = record.getRepaymentPlanData();
			if(StringUtils.isNotBlank(repaymentPlanData)){
				content[4] = getRate(repaymentPlanData);
			}
			content[5] = String.valueOf(sdf.format(record.getUpdateTime()));
			content[6] = "";
			if(paymentRecord!=null){
				content[0] = paymentRecord.getPaymentId();
				content[6] = paymentRecord.getBankSerialNo();
			}
			content[7] = "";
			content[8] = "";
			if(contents.size()==0){
				content[7] = totalMap.get("totalAmount");
				content[8] = totalMap.get("totalSize");
			}
			contents.add(content);
		}
		Date tempDate = new Date();
		if(quantGroupPayRecord.getBeginCreateTime()!=null){
			tempDate=quantGroupPayRecord.getBeginCreateTime();
		}
		String fileName = "量化派对账文件".concat(DateUtil.dateToString(tempDate, "yyyyMMdd"));
		ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
	}

	/**
	 *
	 * @discription金额汇总
	 * @author tangwei
	 * @created 2017年8月23日 下午4:30:31
	 * @param list
	 * @return
	 */
	private Map<String, String> getTotalAmount(List<QuantGroupPayRecord> list){
		Map<String, String> resultMap = new HashMap<String, String>();

		BigDecimal tempTotal=BigDecimal.ZERO;
		for(QuantGroupPayRecord quantGroupPayRecord : list){
			//转账总金额
			tempTotal = new BigDecimal(quantGroupPayRecord.getOrderAmount()).add(tempTotal);
		}
		resultMap.put("totalAmount", tempTotal.toString());
		resultMap.put("totalSize", String.valueOf(list.size()));
		return resultMap;
	}

	/**
	 *
	 * @discription解析分期费率json
	 * @author tangwei
	 * @created 2017年8月23日 下午4:30:31
	 * @param repaymentPlanData
	 * @return
	 */
	private String getRate(String repaymentPlanData){
		//创建JSON解析对象(两条规则的体现:中括号用JSONArray,注意传入数据对象)
		JSONArray jArray = new JSONArray(repaymentPlanData);
		//取得数组长度
		int length = jArray.length();
		String rate = "";
		//回想数组的取值的方式？ --->for循环遍历数组--->得到值
		for (int i = 0; i < length; i++) {
		//根据解析的数据类型使用该类型的get方法得到该值，打印输出
			JSONObject jsonObject2 = jArray.getJSONObject(i);
			//找到费率字段返回
			if(StringUtils.isNotBlank(jsonObject2.getString("rate"))){
				rate = jsonObject2.getString("rate");
				break;
			}
		}
		return rate;
	}
}