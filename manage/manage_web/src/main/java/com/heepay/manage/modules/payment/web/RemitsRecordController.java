/**
 *  
 */
package com.heepay.manage.modules.payment.web;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.ChargeRecordStatus;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.MerchantAccountType;
import com.heepay.enums.PayType;
import com.heepay.enums.SortOrderType;
import com.heepay.enums.WeChatRecordStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.account.entity.MerchantAccount;
import com.heepay.manage.modules.account.service.MerchantAccountService;
import com.heepay.manage.modules.payment.dao.NotifyRecordDao;
import com.heepay.manage.modules.payment.entity.ChargeRecord;
import com.heepay.manage.modules.payment.entity.NotifyRecord;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.entity.RemitsChargeRecord;
import com.heepay.manage.modules.payment.producer.IMqConsume;
import com.heepay.manage.modules.payment.rpc.client.ChargeRecordServiceClient;
import com.heepay.manage.modules.payment.service.ChargeRecordService;
import com.heepay.manage.modules.payment.service.PaymentRecordService;
import com.heepay.manage.modules.payment.service.RemitsChargeRecordService;
import com.heepay.manage.modules.util.ExcelUtil2007;

/**
 * 充值管理Controller
 * @author ld
 * @version V1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/payment/remitsChargeRecord")
public class RemitsRecordController extends BaseController {

	@Autowired
	private ChargeRecordService chargeRecordService;
	
	@Autowired
	private RemitsChargeRecordService remitsChargeRecordService;
	@Autowired
	private ChargeRecordServiceClient chargeClient;
	
	@Autowired
	private PaymentRecordService paymentRecordService;
	
	@Autowired
	private NotifyRecordDao notifyRecrodDao;
	
	@Autowired
	IMqConsume iMqConsume;
	
	@Autowired
	private MerchantAccountService merchantAccountService;
	
	
	
	@ModelAttribute
	public ChargeRecord get(@RequestParam(required=false) String id) {
		ChargeRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = chargeRecordService.get(id);
		}
		if (entity == null){
			entity = new ChargeRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:chargeRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(ChargeRecord chargeRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		if("R1".equals(chargeRecord.getStatus())){
			chargeRecord.setStatus(null);
		}
		if(chargeRecord.getSortOrder()==null){
			chargeRecord.setSortOrder(SortOrderType.DESC.getValue());
		}
		
		//chargeRecord.setStatus(ChargeRecordStatus.AUDING.getValue());
		chargeRecord.setPayType(PayType.REMITS.getValue());
		
		Page<ChargeRecord> page = chargeRecordService.findPage(new Page<ChargeRecord>(request, response), chargeRecord); 
		List<ChargeRecord> recordList = page.getList();
		List<ChargeRecord> resultList = new ArrayList<ChargeRecord>(); 
		for(ChargeRecord record:recordList){
			MerchantAccount merchantAccount = merchantAccountService.get(String.valueOf(record.getAccountId()));
			record.setMerchantLoginName(merchantAccount.getMerchantLoginName());
			if(PayType.REMITS.getValue().equals(record.getPayType())){
				RemitsChargeRecord remitsRecord = remitsChargeRecordService.get(record.getId());
				if( remitsRecord != null ){
					record.setPayBankName(remitsRecord.getPayBankName());
				}
			}
			resultList.add(record);
		}
		
		page.setList(resultList);
		model.addAttribute("page", page);
    return "modules/payment/remitsChargeRecordList";
	}

	@RequiresPermissions("payment:chargeRecord:view")
	@RequestMapping(value = "form")
	public String form(ChargeRecord chargeRecord, Model model) {
		
		if(chargeRecord==null){
			return "modules/payment/remitsChargeRecordDetail";
		}
		if(PayType.REMITS.getValue().equals(chargeRecord.getPayType())){
			RemitsChargeRecord remitsRecord = remitsChargeRecordService.get(chargeRecord.getId());
			chargeRecord.setPayBankName(remitsRecord.getPayBankName());
			chargeRecord.setChannelCode(remitsRecord.getChannelCode());
			chargeRecord.setChannelName(remitsRecord.getChannelName());
			chargeRecord.setBankSerialnumber(remitsRecord.getBankSerialnumber());
		}
		PaymentRecord paymentRecord = paymentRecordService.getOneByChargeId(String.valueOf(chargeRecord.getId()));
		if(paymentRecord!=null){
			NotifyRecord notifyRecord = notifyRecrodDao.getByPaymentId(paymentRecord.getPaymentId());
			model.addAttribute("notifyRecord", notifyRecord);
		}
		model.addAttribute("paymentRecord", paymentRecord);
		model.addAttribute("chargeRecord", chargeRecord);
		return "modules/payment/remitsChargeRecordDetail";

	}

	@RequiresPermissions("payment:chargeRecord:edit")
	@RequestMapping(value = "save")
	public String save(ChargeRecord chargeRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, chargeRecord)){
			return form(chargeRecord, model);
		}
		chargeRecordService.save(chargeRecord);
		addMessage(redirectAttributes, "保存充值管理成功");
		return "redirect:"+Global.getAdminPath()+"/payment/chargeRecord/?repage";
	}
	
	@RequiresPermissions("payment:chargeRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(ChargeRecord chargeRecord, RedirectAttributes redirectAttributes) {
		chargeRecordService.delete(chargeRecord);
		addMessage(redirectAttributes, "删除充值管理成功");
		return "redirect:"+Global.getAdminPath()+"/payment/chargeRecord/?repage";
	}
	
	
	@RequiresPermissions("payment:chargeRecord:view")
  @RequestMapping(value = "exportExcel")
	public void exportExcel(String chargeId, String merchantId, String merchantCompany, String merchantLoginName, String beginCreateTime,
	    String endCreateTime, HttpServletResponse response, HttpServletRequest request) throws Exception{
	  ChargeRecord chargeRecord = new ChargeRecord();
	  if(StringUtils.isNotEmpty(chargeId)){
	    chargeRecord.setChargeId(chargeId);
	  }
	  if(StringUtils.isNotEmpty(merchantId)){
      chargeRecord.setMerchantId(Long.parseLong(merchantId));
    }
	  if(StringUtils.isNotEmpty(merchantCompany)){
      chargeRecord.setMerchantCompany(merchantCompany);
    }
	  if(StringUtils.isNotEmpty(merchantLoginName)){
      chargeRecord.setMerchantLoginName(merchantLoginName);
    }
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  if(StringUtils.isNotEmpty(beginCreateTime)){
	    chargeRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
	  }
	  if(StringUtils.isNotEmpty(endCreateTime)){
	    chargeRecord.setEndCreateTime(sdf.parse(endCreateTime));
	  }
	  
	  chargeRecord.setStatus(ChargeRecordStatus.AUDING.getValue());
    String title = "充值数据统计:";
    if(chargeRecord.getBeginCreateTime()!=null && chargeRecord.getEndCreateTime()!=null){
       title = "充值数据统计:"+DateUtils.getDateStr(chargeRecord.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(chargeRecord.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
    }
    String[] headers = new String[] { "商户编码", "商户公司名称", "商户账号", "交易订单号", "创建时间", 
        "充值金额", "手续费", "实际支付金额", "状态" };


		Page<ChargeRecord> page = new Page<ChargeRecord>(request, response);
		page = chargeRecordService.findPage(page, chargeRecord);
		int pageSize = 500;
		int totalCount = (int)page.getCount();
		int curPage = 1;//从第一页开始
		int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
		List<String[]> contents = new ArrayList<String[]>();
		for(int i=1;i<=totalpage;i++) {
			page.setPageNo(curPage);
			page.setPageSize(pageSize);
			page = chargeRecordService.findPage(page, chargeRecord);
			List<ChargeRecord> chargeRecordList = page.getList();
			for(ChargeRecord record : chargeRecordList){
				String[] content = new String[headers.length];
				content[0] = String.valueOf(record.getMerchantId());
				content[1] = record.getMerchantCompany();
				content[2] = record.getMerchantLoginName();
				content[3] = record.getId();
				content[4] = sdf.format(record.getCreateDate());
				content[5] = record.getChargeAmount();
				content[6] = record.getFeeAmount();
				content[7] = record.getRealAmount();
				content[8] = ChargeRecordStatus.getContentByValue(record.getStatus());
				contents.add(content);
			}
			curPage++;
		}

   String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
   ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
	  
	}
	
	
	@RequiresPermissions("payment:chargeRecord:view")
	@RequestMapping(value="getStatisticsList")
	@ResponseBody
	public void getStatisticsList(String chargeId, String merchantId, String merchantCompany, String merchantLoginName, String beginCreateTime,
      String endCreateTime, HttpServletResponse response) throws ParseException{
	  ChargeRecord chargeRecord = new ChargeRecord();
	  if(StringUtils.isNoneEmpty(chargeId)){
	    chargeRecord.setChargeId(chargeId);
	  }
	  if(StringUtils.isNoneEmpty(merchantId)){
	    chargeRecord.setMerchantId(Long.parseLong(merchantId));
	  }
	  if(StringUtils.isNoneEmpty(merchantCompany)){
	    chargeRecord.setMerchantCompany(merchantCompany);
	  }
	  if(StringUtils.isNoneEmpty(merchantLoginName)){
	    chargeRecord.setMerchantLoginName(merchantLoginName);
	  }
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(StringUtils.isNotEmpty(beginCreateTime)){
      chargeRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
    }
    if(StringUtils.isNotEmpty(endCreateTime)){
      chargeRecord.setEndCreateTime(sdf.parse(endCreateTime));
    }
    
    chargeRecord.setStatus(ChargeRecordStatus.AUDING.getValue());
    
	  //汇总金额
    BigDecimal chargeTotalAmount = BigDecimal.ZERO;
    BigDecimal successTotalAmount = BigDecimal.ZERO;
    BigDecimal failedTotalAmount = BigDecimal.ZERO;
    BigDecimal feeTotalAmount = BigDecimal.ZERO;
    List<ChargeRecord> chargeRecordList = chargeRecordService.findList(chargeRecord);
    if(chargeRecordList != null && !chargeRecordList.isEmpty()){
      for(ChargeRecord record : chargeRecordList){
        BigDecimal chargeAmount = new BigDecimal(StringUtils.isEmpty(record.getChargeAmount())?"0.00":record.getChargeAmount());
        chargeTotalAmount = chargeTotalAmount.add(chargeAmount).setScale(2, RoundingMode.HALF_UP);
        if(StringUtils.equals(record.getStatus(), ChargeRecordStatus.SUCCESS.getValue())){
          //成功金额
          BigDecimal successAmount = new BigDecimal(StringUtils.isEmpty(record.getChargeAmount())?"0.00":record.getChargeAmount());
          successTotalAmount = successTotalAmount.add(successAmount).setScale(2, RoundingMode.HALF_UP);
          //手续费
          BigDecimal feeAmount = new BigDecimal(StringUtils.isEmpty(record.getFeeAmount())?"0.00":record.getFeeAmount());
          feeTotalAmount = feeTotalAmount.add(feeAmount).setScale(2, RoundingMode.HALF_UP);
          
        }else if(StringUtils.equals(record.getStatus(), ChargeRecordStatus.FAILED.getValue())){
          //失败金额
          BigDecimal failedAmount = new BigDecimal(StringUtils.isEmpty(record.getChargeAmount())?"0.00":record.getChargeAmount());
          failedTotalAmount = failedTotalAmount.add(failedAmount).setScale(2, RoundingMode.HALF_UP);
        }
        
        
      }
    }
    
    DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(); 
    df.applyPattern("￥#,##0.00"); 
    Map<String, String> map = new HashMap<String, String>();
    map.put("chargeTotalAmount", df.format(chargeTotalAmount));
    map.put("successTotalAmount", df.format(successTotalAmount));
    map.put("failedTotalAmount", df.format(failedTotalAmount));
    map.put("feeTotalAmount", df.format(feeTotalAmount));
    
    WebUtil.outputJson(map, response);
    
	  
	}
	
	
	@RequiresPermissions("payment:chargeRecord:view")
  @RequestMapping(value = "toReview")
  public String toReview(ChargeRecord chargeRecord, Model model) {
    
    if(chargeRecord==null){
      return "modules/payment/remitsChargeRecordDetail";
    }
    if(PayType.REMITS.getValue().equals(chargeRecord.getPayType())){
      RemitsChargeRecord remitsRecord = remitsChargeRecordService.get(chargeRecord.getId());
      chargeRecord.setPayBankName(remitsRecord.getPayBankName() == null ? "" : remitsRecord.getPayBankName());
      chargeRecord.setChannelCode(remitsRecord.getChannelCode() == null ? "" : remitsRecord.getChannelCode());
      chargeRecord.setChannelName(remitsRecord.getChannelName() == null ? "" : remitsRecord.getChannelName());
      chargeRecord.setBankSerialnumber(remitsRecord.getBankSerialnumber() == null ? "" : remitsRecord.getBankSerialnumber());
    }
    PaymentRecord paymentRecord = paymentRecordService.getOneByChargeId(String.valueOf(chargeRecord.getId()));
    if(paymentRecord!=null){
      NotifyRecord notifyRecord = notifyRecrodDao.getByPaymentId(paymentRecord.getPaymentId());
      model.addAttribute("notifyRecord", notifyRecord);
    }
    model.addAttribute("paymentRecord", paymentRecord);
    model.addAttribute("chargeRecord", chargeRecord);
    return "modules/payment/remitsChargeRecordReview";

  }
	
	
	
	/**
	 * 
	 * @description审核功能
	 * @author TianYanqing      
	 * @created 2017年6月26日 下午8:02:16     
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("payment:chargeRecord:view")
	@RequestMapping(value="auditingRemit")
	public void AuditingRemitsCharge(HttpServletRequest request, HttpServletResponse response){
		String chargeId = request.getParameter("chargeId");
		String status = request.getParameter("status");
		String remark = request.getParameter("remark");
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(chargeId)){
			logger.error("汇充值审核功能失败，传入充值id为空");
			map.put("flag", false);
			map.put("msg", "传入的参数为空");
			WebUtil.outputJson(map, response);
			return ;
		}
		try {
			String result = this.chargeClient.auditingRemitsCharge(chargeId, status, remark);
			if(StringUtils.equals(result, InterfaceStatus.SUCCESS.getContent())){
			  map.put("flag", true);
	      map.put("msg", "处理成功");
	      WebUtil.outputJson(map, response);
	      return;
			}
			 map.put("flag", true);
       map.put("msg", result);
       WebUtil.outputJson(map, response);
       return;
		} catch (TException e) {
			logger.error("调用审核功能异常，原因{}",e.toString());
			e.printStackTrace();
			map.put("flag", true);
      map.put("msg", "内部服务器错误");
      WebUtil.outputJson(map, response);
      return;
		}

	}
	
	
}