/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import com.heepay.codec.Aes;
import com.heepay.common.util.*;
import com.heepay.date.DateUtils;
import com.heepay.enums.RefundStatus;
import com.heepay.enums.RefundType;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.dao.NotifyRecordDao;
import com.heepay.manage.modules.payment.entity.NotifyRecord;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.entity.RefundRecord;
import com.heepay.manage.modules.payment.service.PaymentRecordService;
import com.heepay.manage.modules.payment.service.RefundRecordService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
 * 描    述：退款Controller
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
@RequestMapping(value = "${adminPath}/payment/refundRecord")
public class RefundRecordController extends BaseController {

	@Autowired
	private RefundRecordService refundRecordService;
	
	@Autowired
	private PaymentRecordService paymentRecordService;
	
	@Autowired
	private NotifyRecordDao notifyRecrodDao;
	
	@ModelAttribute
	public RefundRecord get(@RequestParam(required=false) String id) {
		RefundRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = refundRecordService.get(id);
		}
		if (entity == null){
			entity = new RefundRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:refundRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(RefundRecord refundRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
	  if(refundRecord.getStatus() != null && "R1".equals(refundRecord.getStatus())){
	    refundRecord.setStatus(null);
	    }
		  if(refundRecord.getType() != null && "R1".equals(refundRecord.getType()) ){
	      refundRecord.setType(null);
	    }
	  if(refundRecord.getRefundFrom() != null && "R1".equals(refundRecord.getRefundFrom()) ){
	      refundRecord.setRefundFrom(null);
	    }
	  if(refundRecord.getSortOrder()==null){
		  refundRecord.setSortOrder(SortOrderType.DESC.getValue());
	  }
	  if(StringUtils.isEmpty(refundRecord.getGroupType())){
	    refundRecord.setGroupType("1");
	  }
		Page<RefundRecord> page = refundRecordService.findPage(new Page<RefundRecord>(request, response), refundRecord); 
		model.addAttribute("groupType", refundRecord.getGroupType());
		model.addAttribute("page", page);
		model.addAttribute("beginCreateTime", refundRecord.getBeginCreateTime());
		model.addAttribute("endCreateTime", refundRecord.getEndCreateTime());
		return "modules/payment/refundRecordList";
	}

	@RequiresPermissions("payment:refundRecord:view")
	@RequestMapping(value = "form")
	public String form(RefundRecord refundRecord, Model model) {
		if(refundRecord==null){
			return "modules/payment/refundRecordForm";
		}
		
		PaymentRecord originPayment = paymentRecordService.get(refundRecord.getOriginPaymentId());
		PaymentRecord paymentRecord = paymentRecordService.getOne(refundRecord.getRefundId());
		if(paymentRecord!=null){
			if(!StringUtil.isBlank(paymentRecord.getBankcardNo())) {
				String cipherCardNo = StringUtil.getEncryptedCardNo(Aes.decryptStr(paymentRecord.getBankcardNo(), Constants.QuickPay.SYSTEM_KEY));
				paymentRecord.setBankcardNo(cipherCardNo);
			}
			model.addAttribute("paymentRecord", paymentRecord);
			NotifyRecord notifyRecord =notifyRecrodDao.getByPaymentId(paymentRecord.getPaymentId());
			model.addAttribute("notifyRecord", notifyRecord);
		}
		model.addAttribute("refundRecord", refundRecord);
		model.addAttribute("originPayment", originPayment);
		return "modules/payment/refundRecordForm";
	}

	@RequiresPermissions("payment:refundRecord:edit")
	@RequestMapping(value = "save")
	public String save(RefundRecord refundRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, refundRecord)){
			return form(refundRecord, model);
		}
		refundRecordService.save(refundRecord);
		addMessage(redirectAttributes, "保存退款成功");
		return "redirect:"+Global.getAdminPath()+"/payment/refundRecord/?repage";
	}
	
	@RequiresPermissions("payment:refundRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(RefundRecord refundRecord, RedirectAttributes redirectAttributes) {
		refundRecordService.delete(refundRecord);
		addMessage(redirectAttributes, "删除退款成功");
		return "redirect:"+Global.getAdminPath()+"/payment/refundRecord/?repage";
	}
	
	
	@RequiresPermissions("payment:refundRecord:view")
  @RequestMapping(value = "exportExcel")
  public void exportExcel(String refundId, String merchantId, String merchantOrderNo, String merchantLoginName, String beginCreateTime,
      String endCreateTime, String refundType, String refundFrom, String refundStatus, String groupType, HttpServletResponse response, HttpServletRequest request) throws Exception{
	  RefundRecord refundRecord = new RefundRecord();
    if(StringUtils.isNotEmpty(refundId)){
      refundRecord.setRefundId(refundId);
    }
    if(StringUtils.isNotEmpty(merchantId)){
      refundRecord.setMerchantId(Long.parseLong(merchantId));
    }
    if(StringUtils.isNotEmpty(merchantOrderNo)){
      refundRecord.setMerchantOrderNo(merchantOrderNo);
    }
    if(StringUtils.isNotEmpty(merchantLoginName)){
      refundRecord.setMerchantLoginName(merchantLoginName);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(StringUtils.isNotEmpty(beginCreateTime)){
      refundRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
    }
    if(StringUtils.isNotEmpty(endCreateTime)){
      refundRecord.setEndCreateTime(sdf.parse(endCreateTime));
    }
    
    if(StringUtils.isNotEmpty(refundType) && !StringUtils.equals("R1", refundType)){
      refundRecord.setType(refundType);
    }
    
    if(StringUtils.isNotEmpty(refundFrom) && !StringUtils.equals("R1", refundFrom)){
      refundRecord.setRefundFrom(refundFrom);
    }
    
    if(StringUtils.isNotEmpty(refundStatus) && !StringUtils.equals("R1", refundStatus)){
      refundRecord.setStatus(refundStatus);
    }
    
    refundRecord.setGroupType(groupType);
    
    String title = "退款数据统计:";
    if(refundRecord.getBeginCreateTime()!=null && refundRecord.getEndCreateTime()!=null){
       title = "退款数据统计:"+DateUtils.getDateStr(refundRecord.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(refundRecord.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
    }
    String[] headers = new String[] { "商户编码", "商户公司名称", "商户账号", "交易订单号", "原交易订单号", "原商户订单号",
        "退款金额", "退款来源", "创建时间", "退款类型", "状态" };

        Page<RefundRecord> page = new Page<RefundRecord>(request, response);
        page = refundRecordService.findPage(page, refundRecord);
        int pageSize = 500;
        int totalCount = (int)page.getCount();
        int curPage = 1;//从第一页开始
        int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
        List<String[]> contents = new ArrayList<String[]>();
        for(int i=1;i<=totalpage;i++) {
            page.setPageNo(curPage);
            page.setPageSize(pageSize);
            page = refundRecordService.findPage(page, refundRecord);
            List<RefundRecord> refundRecordList = page.getList();
            for(RefundRecord record : refundRecordList){
                String[] content = new String[headers.length];
                content[0] = String.valueOf(record.getMerchantId());
                content[1] = record.getMerchantCompany();
                content[2] = record.getMerchantLoginName();
                content[3] = record.getRefundId();
                content[4] = record.getTransNo();
                if(record.getType().equals(RefundType.CONSUM.getValue())){
                    content[5] = record.getMerchantOrderNo();
                }else{
                    content[5] = "";
                }
                content[6] = record.getRefundAmount();
                content[7] = DictUtils.getDictLabel(record.getRefundFrom(), "RefundFrom", "");
                if(record.getRefundTime() == null){
                    content[8] = "";
                }else{
                    content[8] = sdf.format(record.getRefundTime());
                }
                content[9] = DictUtils.getDictLabel(record.getType(), "RefundType", "");
                content[10] = DictUtils.getDictLabel(record.getStatus(), "RefundStatus", "");
                contents.add(content);
            }
            curPage++;
        }

   String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
   ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
    
  }
	//导出退款对账文件
	@RequiresPermissions("payment:refundRecord:view")
	@RequestMapping(value = "exportBalanceAccountExcel")
	public void exportBalanceAccountExcel(String refundId, String merchantId, String merchantOrderNo, String merchantLoginName,
			String beginCreateTime, String endCreateTime, String refundType, String refundFrom, String refundStatus,
			String groupType, HttpServletResponse response, HttpServletRequest request) throws Exception {
		RefundRecord refundRecord = new RefundRecord();
		if (StringUtils.isNotEmpty(refundId)) {
			refundRecord.setRefundId(refundId);
		}
		if (StringUtils.isNotEmpty(merchantId)) {
			refundRecord.setMerchantId(Long.parseLong(merchantId));
		}
		if (StringUtils.isNotEmpty(merchantOrderNo)) {
			refundRecord.setMerchantOrderNo(merchantOrderNo);
		}
		if (StringUtils.isNotEmpty(merchantLoginName)) {
			refundRecord.setMerchantLoginName(merchantLoginName);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (StringUtils.isNotEmpty(beginCreateTime)) {
			refundRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
		}
		if (StringUtils.isNotEmpty(endCreateTime)) {
			refundRecord.setEndCreateTime(sdf.parse(endCreateTime));
		}

		if (StringUtils.isNotEmpty(refundType) && !StringUtils.equals("R1", refundType)) {
			refundRecord.setType(refundType);
		}

		if (StringUtils.isNotEmpty(refundFrom) && !StringUtils.equals("R1", refundFrom)) {
			refundRecord.setRefundFrom(refundFrom);
		}

		if (StringUtils.isNotEmpty(refundStatus) && !StringUtils.equals("R1", refundStatus)) {
			refundRecord.setStatus(refundStatus);
		}

		refundRecord.setGroupType(groupType);

		String title = "退款对账文件:";
		if (refundRecord.getBeginCreateTime() != null && refundRecord.getEndCreateTime() != null) {
			title = "退款对账文件:" + DateUtils.getDateStr(refundRecord.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)
					+ "---" + DateUtils.getDateStr(refundRecord.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
		}
		String[] headers = new String[] { "退款订单号", "订单金额", "退款金额", "退款成功时间", "退款状态","原交易订单号"};

		Page<RefundRecord> page = new Page<RefundRecord>(request, response);
		page = refundRecordService.findPage(page, refundRecord);
		int pageSize = 500;
		int totalCount = (int) page.getCount();
		int curPage = 1;// 从第一页开始
		int totalpage = totalCount / pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
		List<String[]> contents = new ArrayList<String[]>();
		BigDecimal totalAmount=BigDecimal.ZERO;
		for (int i = 1; i <= totalpage; i++) {
			page.setPageNo(curPage);
			page.setPageSize(pageSize);
			page = refundRecordService.findPage(page, refundRecord);
			List<RefundRecord> refundRecordList = page.getList();
			for (RefundRecord record : refundRecordList) {
				String[] content = new String[headers.length];
				content[0] = String.valueOf(record.getPaymentId());
				content[1] = record.getRefundAmount();
				content[2] = record.getRefundSuccessAmount();
				content[3] = DateUtil.dateToString(record.getRefundSuccessTime(),DateUtil.DATETIME_FORMAT);
				content[4] = DictUtils.getDictLabel(record.getStatus(), "RefundStatus", "");
				content[5] = String.valueOf(record.getOriginPaymentId());
				contents.add(content);
				if(!StringUtil.isBlank(record.getRefundSuccessAmount())){
					totalAmount=totalAmount.add(new BigDecimal(record.getRefundSuccessAmount()));
				}
			}
			curPage++;
		}
		String[] content1 = new String[headers.length];
		content1[0] = "总笔数";
		content1[1] = String.valueOf(totalCount);
		contents.add(content1);
		String[] content2 = new String[headers.length];
		content2[0] = "总金额";
		content2[1] = totalAmount.setScale(2, RoundingMode.HALF_DOWN).toString();
		contents.add(content2);
		String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
		ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);

	}
	
  @RequiresPermissions("payment:refundRecord:view")
  @RequestMapping(value="getStatisticsList")
  @ResponseBody
  public void getStatisticsList(String refundId, String merchantId, String merchantOrderNo, String merchantLoginName, String beginCreateTime,
      String endCreateTime, String refundType, String refundFrom, String refundStatus, String groupType, HttpServletResponse response) throws ParseException{
    RefundRecord refundRecord = new RefundRecord();
    if(StringUtils.isNotEmpty(refundId)){
      refundRecord.setRefundId(refundId);
    }
    if(StringUtils.isNotEmpty(merchantId)){
      refundRecord.setMerchantId(Long.parseLong(merchantId));
    }
    if(StringUtils.isNotEmpty(merchantOrderNo)){
      refundRecord.setMerchantOrderNo(merchantOrderNo);
    }
    if(StringUtils.isNotEmpty(merchantLoginName)){
      refundRecord.setMerchantLoginName(merchantLoginName);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(StringUtils.isNotEmpty(beginCreateTime)){
      refundRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
    }
    if(StringUtils.isNotEmpty(endCreateTime)){
      refundRecord.setEndCreateTime(sdf.parse(endCreateTime));
    }
    
    if(StringUtils.isNotEmpty(refundType) && !StringUtils.equals("R1", refundType)){
      refundRecord.setType(refundType);
    }
    
    if(StringUtils.isNotEmpty(refundFrom) && !StringUtils.equals("R1", refundFrom)){
      refundRecord.setRefundFrom(refundFrom);
    }
    
    if(StringUtils.isNotEmpty(refundStatus) && !StringUtils.equals("R1", refundStatus)){
      refundRecord.setStatus(refundStatus);
    }
    
    refundRecord.setGroupType(groupType);
    
    //汇总金额
    BigDecimal preautTotalAmount = BigDecimal.ZERO;
    BigDecimal prerfdTotalAmount = BigDecimal.ZERO;
    BigDecimal rfdingTotalAmount = BigDecimal.ZERO;
    BigDecimal failedTotalAmount = BigDecimal.ZERO;
    BigDecimal rejectTotalAmount = BigDecimal.ZERO;
    BigDecimal successTotalAmount = BigDecimal.ZERO;
    List<RefundRecord> refundRecordList = refundRecordService.findList(refundRecord);
    if(refundRecordList != null && !refundRecordList.isEmpty()){
      for(RefundRecord record : refundRecordList){
        if(StringUtils.equals(record.getStatus(), RefundStatus.PREAUTH.getValue())){
          //成功金额
          BigDecimal preautAmount = new BigDecimal(StringUtils.isEmpty(record.getRefundAmount())?"0.00":record.getRefundAmount());
          preautTotalAmount = preautTotalAmount.add(preautAmount).setScale(2, RoundingMode.HALF_UP);
        }else if(StringUtils.equals(record.getStatus(), RefundStatus.PREREFUND.getValue())){
          //待审核金额
          BigDecimal prerfdAmount = new BigDecimal(StringUtils.isEmpty(record.getRefundAmount())?"0.00":record.getRefundAmount());
          prerfdTotalAmount = prerfdTotalAmount.add(prerfdAmount).setScale(2, RoundingMode.HALF_UP);
        }else if(StringUtils.equals(record.getStatus(), RefundStatus.REFUNDING.getValue())){
          //退款中金额
          BigDecimal rfdingAmount = new BigDecimal(StringUtils.isEmpty(record.getRefundAmount())?"0.00":record.getRefundAmount());
          rfdingTotalAmount = rfdingTotalAmount.add(rfdingAmount).setScale(2, RoundingMode.HALF_UP);
        }else if(StringUtils.equals(record.getStatus(), RefundStatus.REJECT.getValue())){
          //失败金额
          BigDecimal rejectAmount = new BigDecimal(StringUtils.isEmpty(record.getRefundAmount())?"0.00":record.getRefundAmount());
          rejectTotalAmount = rejectTotalAmount.add(rejectAmount).setScale(2, RoundingMode.HALF_UP);
        }else if(StringUtils.equals(record.getStatus(), RefundStatus.FAILED.getValue())){
          //失败金额
          BigDecimal failedAmount = new BigDecimal(StringUtils.isEmpty(record.getRefundAmount())?"0.00":record.getRefundAmount());
          failedTotalAmount = failedTotalAmount.add(failedAmount).setScale(2, RoundingMode.HALF_UP);
        }else if(StringUtils.equals(record.getStatus(), RefundStatus.SUCCESS.getValue())){
          //成功金额
          BigDecimal successAmount = new BigDecimal(StringUtils.isEmpty(record.getRefundAmount())?"0.00":record.getRefundAmount());
          successTotalAmount = successTotalAmount.add(successAmount).setScale(2, RoundingMode.HALF_UP);
        }
      }
    }
    
    DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(); 
    df.applyPattern("￥#,##0.00"); 
    Map<String, String> map = new HashMap<String, String>();
    map.put("preautTotalAmount", df.format(preautTotalAmount));
    map.put("prerfdTotalAmount", df.format(prerfdTotalAmount));
    map.put("rfdingTotalAmount", df.format(rfdingTotalAmount));
    map.put("rejectTotalAmount", df.format(rejectTotalAmount));
    map.put("failedTotalAmount", df.format(failedTotalAmount));
    map.put("successTotalAmount", df.format(successTotalAmount));
    
    WebUtil.outputJson(map, response);
    
    
  }

}