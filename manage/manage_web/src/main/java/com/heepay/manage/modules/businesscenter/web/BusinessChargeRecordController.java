/**
 *  
 */
package com.heepay.manage.modules.businesscenter.web;

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

import com.heepay.manage.common.utils.RandomUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.heepay.enums.PayType;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.dao.NotifyRecordDao;
import com.heepay.manage.modules.payment.entity.ChargeRecord;
import com.heepay.manage.modules.payment.entity.NotifyRecord;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.entity.RemitsChargeRecord;
import com.heepay.manage.modules.payment.producer.IMqConsume;
import com.heepay.manage.modules.payment.service.ChargeRecordService;
import com.heepay.manage.modules.payment.service.PaymentRecordService;
import com.heepay.manage.modules.payment.service.RemitsChargeRecordService;
import com.heepay.manage.modules.util.ExcelUtil2007;

/**
 * 
* 
* 描    述：
*
* 创 建 者： 王亚洪  
* 创建时间： 2017年8月17日 下午5:18:00 
* 创建描述：商务中心-充值查询
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
@RequestMapping(value = "${adminPath}/business/businessChargeRecord")
public class BusinessChargeRecordController extends BaseController {

	@Autowired
	private ChargeRecordService chargeRecordService;
	
	@Autowired
	private RemitsChargeRecordService remitsChargeRecordService;
	
	@Autowired
	private PaymentRecordService paymentRecordService;
	
	@Autowired
	private NotifyRecordDao notifyRecrodDao;
	
	@Autowired
	IMqConsume iMqConsume;
	
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
	
	@RequiresPermissions("business:chargeRecord:view")
	@RequestMapping(value = "getChargeRecordList")
	public String getChargeRecordList(ChargeRecord chargeRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		if("R1".equals(chargeRecord.getStatus())){
			chargeRecord.setStatus(null);
		}
		if(chargeRecord.getSortOrder()==null){
			chargeRecord.setSortOrder(SortOrderType.DESC.getValue());
		}
		//获取该用户下对应的商户列表
		List<Long> merchantIdList = RandomUtil.getMerchantIdList();
		chargeRecord.setMerchantIds(merchantIdList);
		Page<ChargeRecord> page = chargeRecordService.findPage(new Page<ChargeRecord>(request, response), chargeRecord); 
		List<ChargeRecord> recordList = page.getList();
		List<ChargeRecord> resultList = new ArrayList<ChargeRecord>();
		for(ChargeRecord record:recordList){
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
    return "modules/businesscenter/chargeRecordList";
	}

	@RequiresPermissions("business:chargeRecord:view")
	@RequestMapping(value = "form")
	public String form(ChargeRecord chargeRecord, Model model) {
		
		if(chargeRecord==null){
			return "modules/businesscenter/chargeRecordDetail";
		}
		if(PayType.REMITS.getValue().equals(chargeRecord.getPayType())){
			RemitsChargeRecord remitsRecord = remitsChargeRecordService.get(chargeRecord.getId());
			chargeRecord.setPayBankName(remitsRecord.getPayBankName());
		}
		PaymentRecord paymentRecord = paymentRecordService.getOneByChargeId(String.valueOf(chargeRecord.getId()));
		if(paymentRecord!=null){
			NotifyRecord notifyRecord = notifyRecrodDao.getByPaymentId(paymentRecord.getPaymentId());
			model.addAttribute("notifyRecord", notifyRecord);
		}
		model.addAttribute("paymentRecord", paymentRecord);
		model.addAttribute("chargeRecord", chargeRecord);
		return "modules/businesscenter/chargeRecordDetail";

	}

	
	
	
	@RequiresPermissions("business:chargeRecord:view")
	@RequestMapping(value="getStatisticsList")
	@ResponseBody
	public void getStatisticsList(String chargeId, String merchantId, String merchantCompany, String merchantLoginName, String beginCreateTime,
      String endCreateTime, String status, HttpServletResponse response) throws ParseException{
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
    if(StringUtils.isNotEmpty(status) && !"R1".equals(status)){
      chargeRecord.setStatus(status);
    }

    //获取该用户下对应的商户列表
	List<Long> merchantIdList = RandomUtil.getMerchantIdList();
	chargeRecord.setMerchantIds(merchantIdList);

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
	
	
}