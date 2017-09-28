/**
 *  
 */
package com.heepay.manage.modules.payment.web;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.PaymentRecordStatus;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.QualificationRecord;
import com.heepay.manage.modules.payment.service.QualificationRecordService;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.util.ExcelUtil2007;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
* 
* 描    述：打款认证管理
*
* 创 建 者： yanxb  
* 创建时间： 2016年10月28日 下午2:06:19 
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
@RequestMapping(value = "${adminPath}/payment/qualificationRecord")
public class QualificationRecordController extends BaseController {

	@Autowired
	private QualificationRecordService qualificationRecordService;

	@RequestMapping(value = {"list", ""})
	public String list(QualificationRecord qualificationRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(qualificationRecord.getSortOrder() == null){
			//默认按照创建时间降序
			qualificationRecord.setSortOrder(SortOrderType.DESC.getValue());
		}
		//查询转账列表
		Page<QualificationRecord> page = qualificationRecordService.findPage(new Page<QualificationRecord>(request, response), qualificationRecord);
		model.addAttribute("page", page);

		return "modules/payment/qualificationRecordList";
	}

	@RequestMapping(value = "exportExcel")
	public void exportExcel(String merchantId, String merchantLoginName, String beginCreateTime, String endCreateTime, String qualifyId, String status
			, HttpServletResponse response, HttpServletRequest request) throws Exception{
		QualificationRecord qualificationRecord = new QualificationRecord();
		if(StringUtils.isNotEmpty(merchantId)){
			qualificationRecord.setMerchantId(Long.valueOf(merchantId));
		}
		if(StringUtils.isNotEmpty(merchantLoginName)){
			qualificationRecord.setMerchantLoginName(merchantLoginName);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StringUtils.isNotEmpty(beginCreateTime)){
			qualificationRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
		}
		if(StringUtils.isNotEmpty(endCreateTime)){
			qualificationRecord.setEndCreateTime(sdf.parse(endCreateTime));
		}
		if(StringUtils.isNotEmpty(qualifyId)){
			qualificationRecord.setQualifyId(qualifyId);
		}
		if(StringUtils.isNotEmpty(status)){
			qualificationRecord.setStatus(status);
		}
		String title = "打款认证数据统计:";
		if(qualificationRecord.getBeginCreateTime()!=null && qualificationRecord.getEndCreateTime()!=null){
			title = "打款认证数据统计:"+ DateUtils.getDateStr(qualificationRecord.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(qualificationRecord.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
		}
		String[] headers = new String[] { "交易订单号", "商户编码", "商户账号", "商户公司名称", "银行卡号", "开户行名称", "打款金额", "状态", "打款时间"};

		Page<QualificationRecord> page = new Page<QualificationRecord>(request, response);
		page = qualificationRecordService.findPage(page, qualificationRecord);
		int pageSize = 500;
		int totalCount = (int)page.getCount();
		int curPage = 1;//从第一页开始
		int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
		List<String[]> contents = new ArrayList<String[]>();
		for(int i=1;i<=totalpage;i++) {
			page.setPageNo(curPage);
			page.setPageSize(pageSize);
			page = qualificationRecordService.findPage(page, qualificationRecord);
			List<QualificationRecord> qualificationRecordList = page.getList();
			for(QualificationRecord record : qualificationRecordList){
				String[] content = new String[headers.length];
				content[0] = record.getQualifyId();
				content[1] = String.valueOf(record.getMerchantId());
				content[2] = record.getMerchantLoginName();
				content[3] = record.getMerchantName();
				content[4] = record.getBankcardNo();
				content[5] = record.getOpenbankName();
				content[6] = record.getPayAmount();
				content[7] = DictUtils.getDictLabel(record.getStatus(), "QualificationRecordStatus", "");
				if(record.getCreateTime()==null){
					content[8] = "";
				}else{
					content[8] = sdf.format(record.getCreateTime());
				}
				contents.add(content);
			}
			curPage++;
		}

		String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
		ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
	}

	@RequestMapping(value="getStatisticsList")
	@ResponseBody
	public void getStatisticsList(String merchantId, String merchantLoginName, String beginCreateTime, String endCreateTime, String qualifyId, String status
			, HttpServletResponse response) throws ParseException {
		QualificationRecord qualificationRecord = new QualificationRecord();
		if(StringUtils.isNotEmpty(merchantId)){
			qualificationRecord.setMerchantId(Long.valueOf(merchantId));
		}
		if(StringUtils.isNotEmpty(merchantLoginName)){
			qualificationRecord.setMerchantLoginName(merchantLoginName);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StringUtils.isNotEmpty(beginCreateTime)){
			qualificationRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
		}
		if(StringUtils.isNotEmpty(endCreateTime)){
			qualificationRecord.setEndCreateTime(sdf.parse(endCreateTime));
		}
		if(StringUtils.isNotEmpty(qualifyId)){
			qualificationRecord.setQualifyId(qualifyId);
		}
		if(StringUtils.isNotEmpty(status)){
			qualificationRecord.setStatus(status);
		}
		List<QualificationRecord> qualificationRecordList = qualificationRecordService.findList(qualificationRecord);
		//汇总金额
		BigDecimal qualificationTotalAmount = BigDecimal.ZERO;
		BigDecimal payingTotalAmount = BigDecimal.ZERO;
		BigDecimal failedTotalAmount = BigDecimal.ZERO;
		BigDecimal succesTotalAmount = BigDecimal.ZERO;
		if(qualificationRecordList != null && !qualificationRecordList.isEmpty()){
			for(QualificationRecord record : qualificationRecordList){
				BigDecimal qualificationAmount = new BigDecimal(StringUtils.isEmpty(record.getPayAmount())?"0.00":record.getPayAmount());
				qualificationTotalAmount = qualificationTotalAmount.add(qualificationAmount).setScale(2, RoundingMode.HALF_UP);
				if(StringUtils.equals(record.getStatus(), PaymentRecordStatus.PAYING.getValue())) {
					//打款中金额
					BigDecimal payingAmount = new BigDecimal(StringUtils.isEmpty(record.getPayAmount()) ? "0.00" : record.getPayAmount());
					payingTotalAmount = payingTotalAmount.add(payingAmount).setScale(2, RoundingMode.HALF_UP);
				}else if(StringUtils.equals(record.getStatus(), PaymentRecordStatus.FAILED.getValue())){
					//打款失败金额
					BigDecimal failedAmount = new BigDecimal(StringUtils.isEmpty(record.getPayAmount()) ? "0.00" : record.getPayAmount());
					failedTotalAmount = failedTotalAmount.add(failedAmount).setScale(2, RoundingMode.HALF_UP);
				}else if(StringUtils.equals(record.getStatus(), PaymentRecordStatus.SUCCESS.getValue())){
					//打款成功金额
					BigDecimal successAmount = new BigDecimal(StringUtils.isEmpty(record.getPayAmount()) ? "0.00" : record.getPayAmount());
					succesTotalAmount = succesTotalAmount.add(successAmount).setScale(2, RoundingMode.HALF_UP);
				}
			}
		}

		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		df.applyPattern("￥#,##0.00");
		Map<String, String> map = new HashMap<String, String>();
		map.put("qualificationTotalAmount", df.format(qualificationTotalAmount));
		map.put("payingTotalAmount", df.format(payingTotalAmount));
		map.put("failedTotalAmount", df.format(failedTotalAmount));
		map.put("succesTotalAmount", df.format(succesTotalAmount));

		WebUtil.outputJson(map, response);

	}
}