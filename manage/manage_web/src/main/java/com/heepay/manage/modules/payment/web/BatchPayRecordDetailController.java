/**
 *  
 */
package com.heepay.manage.modules.payment.web;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.BatchPayRecordDetail;
import com.heepay.manage.modules.payment.entity.BatchPayRecordDetailSummary;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.service.BatchPayRecordDetailService;
import com.heepay.manage.modules.payment.service.PaymentRecordService;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 转账明细Controller
 * @author zjx
 * @version V1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/payment/batchPayRecordDetail")
public class BatchPayRecordDetailController extends BaseController {

	@Autowired
	private BatchPayRecordDetailService batchPayRecordDetailService;
	
	@Autowired
	private PaymentRecordService paymentRecordService;
	
	@ModelAttribute
	public BatchPayRecordDetail get(@RequestParam(required=false) String id) {
		BatchPayRecordDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = batchPayRecordDetailService.get(id);
		}
		if (entity == null){
			entity = new BatchPayRecordDetail();
		}
		return entity;
	}
	
	/**
	 * 
	* @discription转账明细查询
	* @author yanxb       
	* @created 2017年3月15日 下午4:31:04     
	* @param batchPayRecordDetail
	* @param request
	* @param response
	* @param model
	* @return
	 */
	@RequiresPermissions("payment:batchPayRecordDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(BatchPayRecordDetail batchPayRecordDetail, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		if(StringUtils.isEmpty(batchPayRecordDetail.getGroupType())){
			batchPayRecordDetail.setGroupType("1");
		}
		if(batchPayRecordDetail.getBeginCreateTime() == null){
			//默认当天
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateNow = DateUtils.getDate(true);
			batchPayRecordDetail.setBeginCreateTime(sdf.parse(dateNow + " 00:00:00"));
			batchPayRecordDetail.setEndCreateTime(sdf.parse(dateNow + " 23:59:59"));
		}
		Page<BatchPayRecordDetail> page = batchPayRecordDetailService.findPageByStatus(new Page<>(request, response), batchPayRecordDetail);
		model.addAttribute("groupType", batchPayRecordDetail.getGroupType());
		if(null == page){
			return "modules/payment/batchPayRecordDetailsList";
		}
		Page<BatchPayRecordDetail> realPage = getRealPage(page,page.getList());
		model.addAttribute("page", realPage);
		//转账明细汇总信息
		batchPayRecordDetail.setPage(null);
		List<BatchPayRecordDetail> recordList = batchPayRecordDetailService.findListByStatus(batchPayRecordDetail);
		BatchPayRecordDetailSummary batchPayRecordSummary = getSummaryBatchPayRecordDetailResult(recordList);
		model.addAttribute("batchPayRecordDetailSummary", batchPayRecordSummary);
		return "modules/payment/batchPayRecordDetailsList";
	}

	/**
	 * 
	* @discription转账明细汇总
	* @author yanxb       
	* @created 2017年3月15日 下午4:27:32     
	* @param page
	* @param recordList
	* @return
	 */
	private Page<BatchPayRecordDetail> getRealPage(Page<BatchPayRecordDetail> page,List<BatchPayRecordDetail> recordList) {
		List<BatchPayRecordDetail> newDetailList = new ArrayList<>();
		if(null != recordList){
			for(BatchPayRecordDetail detail:recordList){
				String paymentId = detail.getPaymentId();
				PaymentRecord paymentRecord = paymentRecordService.get(paymentId);
				if(paymentRecord != null){
					detail.setChannelCode(StringUtil.isBlank(paymentRecord.getChannelCode())?"":paymentRecord.getChannelCode());
					detail.setChannelName(StringUtil.isBlank(paymentRecord.getChannelName())?"":paymentRecord.getChannelName());
				}
				newDetailList.add(detail);
			}
			page.setList(newDetailList);
		}
		return page;
	}

	@RequiresPermissions("payment:batchPayRecordDetail:view")
	@RequestMapping(value = "form")
	public String form(BatchPayRecordDetail batchPayRecordDetail, Model model) {
        model.addAttribute("batchPayRecordDetail", batchPayRecordDetail);
		return "modules/payment/batchPayDetailRecordForm";
	}

	@RequiresPermissions("payment:batchPayRecordDetail:edit")
	@RequestMapping(value = "save")
	public String save(BatchPayRecordDetail batchPayRecordDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, batchPayRecordDetail)){
			return form(batchPayRecordDetail, model);
		}
		batchPayRecordDetailService.save(batchPayRecordDetail);
		addMessage(redirectAttributes, "保存转账明细成功");
		return "redirect:"+Global.getAdminPath()+"/d/batchPayRecordDetail/?repage";
	}
	
	@RequiresPermissions("payment:batchPayRecordDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(BatchPayRecordDetail batchPayRecordDetail, RedirectAttributes redirectAttributes) {
		batchPayRecordDetailService.delete(batchPayRecordDetail);
		addMessage(redirectAttributes, "删除转账明细成功");
		return "redirect:"+Global.getAdminPath()+"/d/batchPayRecordDetail/?repage";
	}

	@RequestMapping(value = {"detailList"})
	public String detailList(BatchPayRecordDetail batchPayRecordDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		batchPayRecordDetail.setBatchId(request.getParameter("batchId"));
		Page<BatchPayRecordDetail> page = batchPayRecordDetailService.findPage(new Page<>(request, response), batchPayRecordDetail);
		model.addAttribute("page", page);
		BatchPayRecordDetailSummary batchPayRecordSummary = getSummaryBatchPayRecordDetailResult(page.getList());
		model.addAttribute("batchPayRecordDetailSummary", batchPayRecordSummary);
		return "modules/payment/batchPayRecordDetail";
	}

	/**
	 * 
	* @discription金额汇总
	* @author yanxb       
	* @created 2017年3月15日 下午4:30:31     
	* @param list
	* @return
	 */
	private BatchPayRecordDetailSummary getSummaryBatchPayRecordDetailResult(List<BatchPayRecordDetail> list){

		BatchPayRecordDetailSummary bpayRecordDetailSummary = new BatchPayRecordDetailSummary();
		String tempTotal;
		for(BatchPayRecordDetail bpRecordDetail : list){
			//转账总金额
			tempTotal = StringUtil.isBlank(bpRecordDetail.getPayAmount())?"0.0000":bpRecordDetail.getPayAmount();
			bpayRecordDetailSummary.setTotalAmount(String.valueOf(new BigDecimal(bpayRecordDetailSummary.getTotalAmount()).add(new BigDecimal(tempTotal))));

			if("SUCCES".equals(bpRecordDetail.getStatus())){
				//转账成功金额
				tempTotal = StringUtil.isBlank(bpRecordDetail.getSuccessAmount())?"0.0000":bpRecordDetail.getPayAmount();
				bpayRecordDetailSummary.setSuccessTotalAmount(String.valueOf(new BigDecimal(bpayRecordDetailSummary.getSuccessTotalAmount()).add(new BigDecimal(tempTotal))));
			}
			if("SUCCES".equals(bpRecordDetail.getStatus())){
				//转账成功手续费
				tempTotal = StringUtil.isBlank(bpRecordDetail.getFeeAmount())?"0.0000":bpRecordDetail.getFeeAmount();
				bpayRecordDetailSummary.setTotalFeeAmount(String.valueOf(new BigDecimal(bpayRecordDetailSummary.getTotalFeeAmount()).add(new BigDecimal(tempTotal))));
			}
			if("FAILED".equals(bpRecordDetail.getStatus()) || "AUDREJ".equals(bpRecordDetail.getStatus())){
				//失败总金额
				bpayRecordDetailSummary.setFailTotalAmount(String.valueOf(new BigDecimal(bpayRecordDetailSummary.getFailTotalAmount()).add(new BigDecimal(bpRecordDetail.getPayAmount()))));
			}
		}

		logger.info("转账汇总:{}",bpayRecordDetailSummary.toString());

		return bpayRecordDetailSummary;
	}
	
	
	
  @RequiresPermissions("payment:batchPayRecordDetail:view")
  @RequestMapping(value = "exportExcel")
  public void exportExcel(String merchantId, String batchId, String merchantBatchNo, String batchPayId, String merchantPayNo, 
      String beginCreateTime, String endCreateTime, String status, String groupType, HttpServletResponse response, HttpServletRequest request) throws Exception{
    BatchPayRecordDetail batchPayRecordDetail = new BatchPayRecordDetail();
    if(StringUtils.isNotEmpty(merchantId)){
      batchPayRecordDetail.setMerchantId(Long.parseLong(merchantId));
    }
    if(StringUtils.isNotEmpty(batchId)){
      batchPayRecordDetail.setBatchId(batchId);
    }
    if(StringUtils.isNotEmpty(merchantBatchNo)){
      batchPayRecordDetail.setMerchantBatchNo(merchantBatchNo);
    }
    if(StringUtils.isNotEmpty(batchPayId)){
      batchPayRecordDetail.setBatchPayId(batchPayId);
    }
    if(StringUtils.isNotEmpty(merchantPayNo)){
      batchPayRecordDetail.setMerchantPayNo(merchantPayNo);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(StringUtils.isNotEmpty(beginCreateTime)){
      batchPayRecordDetail.setBeginCreateTime(sdf.parse(beginCreateTime));
    }
    if(StringUtils.isNotEmpty(endCreateTime)){
      batchPayRecordDetail.setEndCreateTime(sdf.parse(endCreateTime));
    }
    if(StringUtils.isNotEmpty(status)){
      batchPayRecordDetail.setStatus(status);
    }
	  batchPayRecordDetail.setGroupType(groupType);
    String title = "转账明细查询数据统计:";
    if(batchPayRecordDetail.getBeginCreateTime()!=null && batchPayRecordDetail.getEndCreateTime()!=null){
       title = "转账明细查询数据统计:"+DateUtils.getDateStr(batchPayRecordDetail.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(batchPayRecordDetail.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
    }
    String[] headers = new String[] { "交易订单号", "商户订单号", "商户编码", "付款批次号", "商户批次号", "手续费", "转账金额", "状态", "银行服务类型",
        "创建时间", "处理时间", "付款通道"};

	  Page<BatchPayRecordDetail> page = new Page<BatchPayRecordDetail>(request, response);
	  page = batchPayRecordDetailService.findPage(page, batchPayRecordDetail);
	  int pageSize = 500;
	  int totalCount = (int)page.getCount();
	  int curPage = 1;//从第一页开始
	  int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
	  List<String[]> contents = new ArrayList<String[]>();
	  for(int i=1;i<=totalpage;i++) {
		  page.setPageNo(curPage);
		  page.setPageSize(pageSize);
		  page = batchPayRecordDetailService.findPage(page, batchPayRecordDetail);
		  List<BatchPayRecordDetail> batchPayRecordDetailList = page.getList();
		  for(BatchPayRecordDetail record : batchPayRecordDetailList){
			  String[] content = new String[headers.length];
			  content[0] = record.getBatchPayId();
			  content[1] = record.getMerchantPayNo();
			  content[2] = String.valueOf(record.getMerchantId());
			  content[3] = record.getBatchId();
			  content[4] = record.getMerchantBatchNo();
			  content[5] = record.getFeeAmount();
			  content[6] = record.getPayAmount();
			  content[7] = DictUtils.getDictLabel(record.getStatus(), "BatchPayRecordDetailStatus", "");
			  SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
			  if(StringUtils.equals(sdf1.format(record.getIntoaccountTime()), "23:59:59")){
				  content[8] = "T+1";
			  }else{
				  content[8] = "T+0";
			  }
			  content[9] = String.valueOf(sdf.format(record.getCreateTime()));
			  content[10] = String.valueOf(sdf.format(record.getUpdateTime()));
			  String paymentId = record.getPaymentId();
			  PaymentRecord paymentRecord = paymentRecordService.get(paymentId);
			  if(paymentRecord != null){
				  content[11] = StringUtil.isBlank(paymentRecord.getChannelCode())?"":paymentRecord.getChannelCode();
			  }
			  contents.add(content);
		  }
		  curPage++;
	  }

   String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
   ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
    
  }
	
	
  @RequiresPermissions("payment:batchPayRecordDetail:view")
  @RequestMapping(value="getStatisticsList")
  @ResponseBody
  public void getStatisticsList(String merchantId, String batchId, String merchantBatchNo, String batchPayId, String merchantPayNo,
		String beginCreateTime, String endCreateTime, String status, String bankName, String bankcardType, String bankcardOwnerType, String groupType,
		HttpServletResponse response) throws ParseException{
    BatchPayRecordDetail batchPayRecordDetail = new BatchPayRecordDetail();
    if(StringUtils.isNotEmpty(merchantId)){
      batchPayRecordDetail.setMerchantId(Long.parseLong(merchantId));
    }
    if(StringUtils.isNotEmpty(batchId)){
      batchPayRecordDetail.setBatchId(batchId);
    }
    if(StringUtils.isNotEmpty(merchantBatchNo)){
      batchPayRecordDetail.setMerchantBatchNo(merchantBatchNo);
    }
    if(StringUtils.isNotEmpty(batchPayId)){
      batchPayRecordDetail.setBatchPayId(batchPayId);
    }
    if(StringUtils.isNotEmpty(merchantPayNo)){
      batchPayRecordDetail.setMerchantPayNo(merchantPayNo);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(StringUtils.isNotEmpty(beginCreateTime)){
      batchPayRecordDetail.setBeginCreateTime(sdf.parse(beginCreateTime));
    }
    if(StringUtils.isNotEmpty(endCreateTime)){
      batchPayRecordDetail.setEndCreateTime(sdf.parse(endCreateTime));
    }
    if(StringUtils.isNotEmpty(status)){
      batchPayRecordDetail.setStatus(status);
    }
	if(StringUtils.isNotEmpty(bankName)){
	  batchPayRecordDetail.setBankName(bankName);
	}
	if(StringUtils.isNotEmpty(bankcardType)){
	  batchPayRecordDetail.setBankcardType(bankcardType);
	}
	if(StringUtils.isNotEmpty(bankcardOwnerType)){
	  batchPayRecordDetail.setBankcardOwnerType(bankcardOwnerType);
	}
	  batchPayRecordDetail.setGroupType(groupType);
    List<BatchPayRecordDetail> batchPayRecordDetailList = batchPayRecordDetailService.findList(batchPayRecordDetail);
    BatchPayRecordDetailSummary bpayRecordDetailSummary = new BatchPayRecordDetailSummary();
    String tempTotal;
    for(BatchPayRecordDetail bpRecordDetail : batchPayRecordDetailList){
      //转账总金额
      tempTotal = StringUtil.isBlank(bpRecordDetail.getPayAmount())?"0.0000":bpRecordDetail.getPayAmount();
      bpayRecordDetailSummary.setTotalAmount(String.valueOf(new BigDecimal(bpayRecordDetailSummary.getTotalAmount()).add(new BigDecimal(tempTotal))));

      if("SUCCES".equals(bpRecordDetail.getStatus())){
        //转账成功金额
        tempTotal = StringUtil.isBlank(bpRecordDetail.getSuccessAmount())?"0.0000":bpRecordDetail.getPayAmount();
        bpayRecordDetailSummary.setSuccessTotalAmount(String.valueOf(new BigDecimal(bpayRecordDetailSummary.getSuccessTotalAmount()).add(new BigDecimal(tempTotal))));
      }
      if("SUCCES".equals(bpRecordDetail.getStatus())){
        //转账成功手续费
        tempTotal = StringUtil.isBlank(bpRecordDetail.getFeeAmount())?"0.0000":bpRecordDetail.getFeeAmount();
        bpayRecordDetailSummary.setTotalFeeAmount(String.valueOf(new BigDecimal(bpayRecordDetailSummary.getTotalFeeAmount()).add(new BigDecimal(tempTotal))));
      }
      if("FAILED".equals(bpRecordDetail.getStatus()) || "AUDREJ".equals(bpRecordDetail.getStatus())){
        //失败总金额
        bpayRecordDetailSummary.setFailTotalAmount(String.valueOf(new BigDecimal(bpayRecordDetailSummary.getFailTotalAmount()).add(new BigDecimal(bpRecordDetail.getPayAmount()))));
      }
    }
    
    DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(); 
    df.applyPattern("￥#,##0.00"); 
    Map<String, String> map = new HashMap<>();
    map.put("totalAmount", df.format(StringUtils.isEmpty(bpayRecordDetailSummary.getTotalAmount())?BigDecimal.ZERO:new BigDecimal(bpayRecordDetailSummary.getTotalAmount())));
    map.put("successTotalAmount", df.format(StringUtils.isEmpty(bpayRecordDetailSummary.getSuccessTotalAmount())?BigDecimal.ZERO:new BigDecimal(bpayRecordDetailSummary.getSuccessTotalAmount())));
    map.put("failTotalAmount", df.format(StringUtils.isEmpty(bpayRecordDetailSummary.getFailTotalAmount())?BigDecimal.ZERO:new BigDecimal(bpayRecordDetailSummary.getFailTotalAmount())));
    map.put("totalFeeAmount", df.format(StringUtils.isEmpty(bpayRecordDetailSummary.getTotalFeeAmount())?BigDecimal.ZERO:new BigDecimal(bpayRecordDetailSummary.getTotalFeeAmount())));
    
    WebUtil.outputJson(map, response);
    
    
  }
  
	
}