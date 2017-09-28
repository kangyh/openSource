/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import com.heepay.codec.Aes;
import com.heepay.common.util.*;
import com.heepay.date.DateUtils;
import com.heepay.enums.PaymentRecordStatus;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.service.*;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.util.ExcelUtil2007;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
 * 描    述：交易管理Controller
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
@RequestMapping(value = "${adminPath}/payment/paymentFundRecord")
public class PaymentFundRecordController extends BaseController {

	@Autowired
	private PaymentRecordService paymentRecordService;

	/**
	 * 充值Service
	 */
	@Autowired
	private ChargeRecordService chargeRecordService;

	/**
	 * 委托收款Service
	 */
	@Autowired
	private BatchCollectionRecordService batchCollectionRecordService;

	/**
	 * 转账明细Service
	 */
	@Autowired
	private BatchCollectionRecordDetailService batchCollectionRecordDetailService;

	/**
	 * 网关Service
	 */
	@Autowired
	private GatewayRecordService gatewayRecordService;

	/**
	 * 提现Service
	 */
	@Autowired
	private WithdrawRecordService withdrawRecordService;

	/**
	 * 转账Service
	 */
	@Autowired
	private BatchPayRecordService batchPayRecordService;

	/**
	 * 退款Service
	 */
	@Autowired
	private RefundRecordService refundRecordService;


	/**
	 * 内转Service
	 */
	@Autowired
	private InnerTransferRecordService innerTransferRecordService;


	@ModelAttribute
	public PaymentRecord get(@RequestParam(required=false) String id) {
		PaymentRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = paymentRecordService.get(id);
		}
		if (entity == null){
			entity = new PaymentRecord();
		}
		return entity;
	}

//	@RequiresPermissions("payment:paymentRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(PaymentRecord paymentRecord, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		if(paymentRecord.getTransType() == null){
			paymentRecord.setTransType("R1");
		}
		if(paymentRecord.getStatus() == null){
			paymentRecord.setStatus("R1");
		}
		if(paymentRecord.getCheckStatus()== null){
			paymentRecord.setCheckStatus("R1");
		}
		if(paymentRecord.getSortOrder()==null){
			paymentRecord.setSortOrder(SortOrderType.DESC.getValue());
		}
		if(StringUtils.isEmpty(paymentRecord.getGroupType())){
			paymentRecord.setGroupType("1");
		}
		if(paymentRecord.getBeginCreateTime() == null){
		    //默认当天
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String dateNow = DateUtils.getDate(true);
		    paymentRecord.setBeginCreateTime(sdf.parse(dateNow + " 00:00:00"));
		    paymentRecord.setEndCreateTime(sdf.parse(dateNow + " 23:59:59"));
		}
		Page<PaymentRecord> page = paymentRecordService.findPage(new Page<PaymentRecord>(request, response), paymentRecord);
		model.addAttribute("page", page);
		model.addAttribute("groupType", paymentRecord.getGroupType());
		model.addAttribute("beginCreateTime", paymentRecord.getBeginCreateTime());
		model.addAttribute("endCreateTime", paymentRecord.getEndCreateTime());
		return "modules/payment/paymentFundRecordList";
	}

	@RequestMapping(value = "form")
	public String form(PaymentRecord paymentRecord, Model model) {
		if(paymentRecord==null){
			return "modules/payment/paymentFundRecordForm";
		}

		PaymentRecord payment = paymentRecordService.get(paymentRecord.getPaymentId());
		if(!StringUtil.isBlank(payment.getBankcardNo())){
			payment.setBankcardNo(Aes.decryptStr(payment.getBankcardNo(), Constants.QuickPay.SYSTEM_KEY));
		}
		model.addAttribute("paymentRecord", payment);
		return "modules/payment/paymentFundRecordForm";
	}

	@RequiresPermissions("payment:paymentRecord:view")
	@RequestMapping(value = "exportExcel")
	public void exportExcel(String bankSerialNo, String paymentId, String transNo, String merchantLoginName, String merchantId, String transType, String status,
							String checkStatus, String bankName, String bankcardType, String sortOrder, String beginCreateTime, String endCreateTime, String groupType,
							String channelPartner, HttpServletResponse response, HttpServletRequest request) throws Exception{
		PaymentRecord paymentRecord = new PaymentRecord();
		if(StringUtils.isNotEmpty(bankSerialNo)){
			paymentRecord.setBankSerialNo(bankSerialNo);
		}
		if(StringUtils.isNotEmpty(paymentId)){
			paymentRecord.setPaymentId(paymentId);
		}
		if(StringUtils.isNotEmpty(transNo)){
			paymentRecord.setTransNo(transNo);
		}
		if(StringUtils.isNotEmpty(merchantLoginName)){
			paymentRecord.setMerchantLoginName(merchantLoginName);
		}
		if(StringUtils.isNotEmpty(merchantId)){
			paymentRecord.setMerchantId(Long.valueOf(merchantId));
		}
		if(StringUtils.isNotEmpty(transType)){
			paymentRecord.setTransType(transType);
		}
		if(StringUtils.isNotEmpty(status)){
			paymentRecord.setStatus(status);
		}
		if(StringUtils.isNotEmpty(checkStatus)){
			paymentRecord.setCheckStatus(checkStatus);
		}
		if(StringUtils.isNotEmpty(bankName)){
			paymentRecord.setBankName(bankName);
		}
		if(StringUtils.isNotEmpty(bankcardType) && !StringUtils.equals("R1", bankcardType)){
			paymentRecord.setBankcardType(bankcardType);
		}
		if(StringUtils.isNotEmpty(sortOrder) && !StringUtils.equals("R1", bankcardType)){
			paymentRecord.setSortOrder(sortOrder);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StringUtils.isNotEmpty(beginCreateTime)){
			paymentRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
		}
		if(StringUtils.isNotEmpty(endCreateTime)){
			paymentRecord.setEndCreateTime(sdf.parse(endCreateTime));
		}

		paymentRecord.setGroupType(groupType);
		paymentRecord.setChannelPartner(channelPartner);
		String title = "银行订单数据统计:";
		if(paymentRecord.getBeginCreateTime()!=null && paymentRecord.getEndCreateTime()!=null){
			title = "银行订单数据统计:"+ DateUtils.getDateStr(paymentRecord.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(paymentRecord.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
		}
		String[] headers = new String[] { "商户编码", "商户公司名称", "商户账号", "支付单号", "通道合作方", "银行名称", "银行卡类型", "银行流水号", "交易类型", "扣款时间",
				"订单金额", "实际支付金额", "扣款状态", "交易订单号", "商户订单号", "核对状态", "提交时间"};

		Page<PaymentRecord> page = new Page<PaymentRecord>(request, response);
		page = paymentRecordService.findPage(page, paymentRecord);
		int pageSize = 500;
		int totalCount = (int)page.getCount();
		int curPage = 1;//从第一页开始
		int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
		List<String[]> contents = new ArrayList<String[]>();
		for(int i=1;i<=totalpage;i++) {
			page.setPageNo(curPage);
			page.setPageSize(pageSize);
			page = paymentRecordService.findPage(page, paymentRecord);
			List<PaymentRecord> paymentRecordList = page.getList();
			for(PaymentRecord record : paymentRecordList){
				String[] content = new String[headers.length];
				content[0] = String.valueOf(record.getMerchantId());
				content[1] = record.getMerchantCompany();
				content[2] = record.getMerchantLoginName();
				content[3] = record.getPaymentId();
				content[4] = DictUtils.getDictLabel(record.getChannelPartner(), "ChannelPartner", "");
				content[5] = record.getBankName();
				content[6] = DictUtils.getDictLabel(record.getBankcardType(), "BankcardType", "");
				content[7] = record.getBankSerialNo();
				content[8] = DictUtils.getDictLabel(record.getTransType(), "TransType", "");
				if(record.getSuccessTime()==null){
					content[9] = "";
				}else{
					content[9] = sdf.format(record.getSuccessTime());
				}
				content[10] = record.getPayAmount();
				content[11] = record.getSuccessAmount();
				content[12] = DictUtils.getDictLabel(record.getStatus(), "PaymentRecordStatus", "");
				content[13] = record.getTransNo();
				content[14] = record.getMerchantOrderNo();
				content[15] = DictUtils.getDictLabel(record.getCheckStatus(), "CheckStatus", "");
				if(record.getPayTime()==null){
					content[16] = "";
				}else{
					content[16] = sdf.format(record.getPayTime());
				}
				contents.add(content);
			}
			curPage++;
		}
		String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
		ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
	}


	@RequiresPermissions("payment:paymentRecord:view")
	@RequestMapping(value="getStatisticsList")
	@ResponseBody
	public void getStatisticsList(String bankSerialNo, String paymentId, String transNo, String merchantLoginName, String merchantId, String transType, String status,
								  String checkStatus, String bankName, String bankcardType, String sortOrder, String beginCreateTime, String endCreateTime, String groupType,
								  String channelPartner, HttpServletResponse response) throws ParseException {
		PaymentRecord paymentRecord = new PaymentRecord();
		if(StringUtils.isNotEmpty(bankSerialNo)){
			paymentRecord.setBankSerialNo(bankSerialNo);
		}
		if(StringUtils.isNotEmpty(paymentId)){
			paymentRecord.setPaymentId(paymentId);
		}
		if(StringUtils.isNotEmpty(transNo)){
			paymentRecord.setTransNo(transNo);
		}
		if(StringUtils.isNotEmpty(merchantLoginName)){
			paymentRecord.setMerchantLoginName(merchantLoginName);
		}
		if(StringUtils.isNotEmpty(merchantId)){
			paymentRecord.setMerchantId(Long.valueOf(merchantId));
		}
		if(StringUtils.isNotEmpty(transType)){
			paymentRecord.setTransType(transType);
		}
		if(StringUtils.isNotEmpty(status)){
			paymentRecord.setStatus(status);
		}
		if(StringUtils.isNotEmpty(checkStatus)){
			paymentRecord.setCheckStatus(checkStatus);
		}
		if(StringUtils.isNotEmpty(bankName)){
			paymentRecord.setBankName(bankName);
		}
		if(StringUtils.isNotEmpty(bankcardType) && !StringUtils.equals("R1", bankcardType)){
			paymentRecord.setBankcardType(bankcardType);
		}
		if(StringUtils.isNotEmpty(sortOrder) && !StringUtils.equals("R1", sortOrder)){
			paymentRecord.setSortOrder(sortOrder);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StringUtils.isNotEmpty(beginCreateTime)){
			paymentRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
		}
		if(StringUtils.isNotEmpty(endCreateTime)){
			paymentRecord.setEndCreateTime(sdf.parse(endCreateTime));
		}

		paymentRecord.setGroupType(groupType);
		paymentRecord.setChannelPartner(channelPartner);
		List<PaymentRecord> paymentRecordList = paymentRecordService.findList(paymentRecord);
		//汇总金额
		BigDecimal paymentTotalAmount = BigDecimal.ZERO;
		BigDecimal prepayTotalAmount = BigDecimal.ZERO;
		BigDecimal payingTotalAmount = BigDecimal.ZERO;
		BigDecimal failedTotalAmount = BigDecimal.ZERO;
		BigDecimal timoutTotalAmount = BigDecimal.ZERO;
		BigDecimal succesTotalAmount = BigDecimal.ZERO;
		if(paymentRecordList != null && !paymentRecordList.isEmpty()){
			for(PaymentRecord record : paymentRecordList){
				BigDecimal paymentAmount = new BigDecimal(StringUtils.isEmpty(record.getPayAmount())?"0.00":record.getPayAmount());
				paymentTotalAmount = paymentTotalAmount.add(paymentAmount).setScale(2, RoundingMode.HALF_UP);
				if(StringUtils.equals(record.getStatus(), PaymentRecordStatus.PREPAY.getValue())){
					//成功金额
					BigDecimal prepayAmount = new BigDecimal(StringUtils.isEmpty(record.getPayAmount())?"0.00":record.getPayAmount());
					prepayTotalAmount = prepayTotalAmount.add(prepayAmount).setScale(2, RoundingMode.HALF_UP);
				}else if(StringUtils.equals(record.getStatus(), PaymentRecordStatus.PAYING.getValue())){
					//待审核金额
					BigDecimal payingAmount = new BigDecimal(StringUtils.isEmpty(record.getPayAmount())?"0.00":record.getPayAmount());
					payingTotalAmount = payingTotalAmount.add(payingAmount).setScale(2, RoundingMode.HALF_UP);
				}else if(StringUtils.equals(record.getStatus(), PaymentRecordStatus.FAILED.getValue())){
					//退款中金额
					BigDecimal failedAmount = new BigDecimal(StringUtils.isEmpty(record.getPayAmount())?"0.00":record.getPayAmount());
					failedTotalAmount = failedTotalAmount.add(failedAmount).setScale(2, RoundingMode.HALF_UP);
				}else if(StringUtils.equals(record.getStatus(), PaymentRecordStatus.TIMEOUT.getValue())){
					//失败金额
					BigDecimal timoutAmount = new BigDecimal(StringUtils.isEmpty(record.getPayAmount())?"0.00":record.getPayAmount());
					timoutTotalAmount = timoutTotalAmount.add(timoutAmount).setScale(2, RoundingMode.HALF_UP);
				}else if(StringUtils.equals(record.getStatus(), PaymentRecordStatus.SUCCESS.getValue())){
					//失败金额
					BigDecimal succesAmount = new BigDecimal(StringUtils.isEmpty(record.getPayAmount())?"0.00":record.getPayAmount());
					succesTotalAmount = succesTotalAmount.add(succesAmount).setScale(2, RoundingMode.HALF_UP);
				}
			}
		}

		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		df.applyPattern("￥#,##0.00");
		Map<String, String> map = new HashMap<String, String>();
		map.put("paymentTotalAmount", df.format(paymentTotalAmount));
		map.put("prepayTotalAmount", df.format(prepayTotalAmount));
		map.put("payingTotalAmount", df.format(payingTotalAmount));
		map.put("failedTotalAmount", df.format(failedTotalAmount));
		map.put("timoutTotalAmount", df.format(timoutTotalAmount));
		map.put("succesTotalAmount", df.format(succesTotalAmount));

		WebUtil.outputJson(map, response);


	}
}