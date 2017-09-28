/**
 *  
 */
package com.heepay.manage.modules.payment.web;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.BatchPayStatus;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.BatchPayRecord;
import com.heepay.manage.modules.payment.entity.BatchPayRecordDetail;
import com.heepay.manage.modules.payment.entity.BatchPayRecordSummary;
import com.heepay.manage.modules.payment.service.BatchPayRecordDetailService;
import com.heepay.manage.modules.payment.service.BatchPayRecordService;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 批量付款管理Controller
 * @author zjx
 * @version V1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/payment/batchPayRecord")
public class BatchPayRecordController extends BaseController {

	@Autowired
	private BatchPayRecordService batchPayRecordService;
	
	@Autowired
	private BatchPayRecordDetailService batchPayRecordDetailService;
	
	@ModelAttribute
	public BatchPayRecord get(@RequestParam(required=false) String id) {
		BatchPayRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = batchPayRecordService.get(id);
		}
		if (entity == null){
			entity = new BatchPayRecord();
		}
		return entity;
	}

	@RequiresPermissions("payment:batchPayRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(BatchPayRecord batchPayRecord, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		if(batchPayRecord.getBeginCreateTime() == null){
			//默认当天
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateNow = DateUtils.getDate(true);
			batchPayRecord.setBeginCreateTime(sdf.parse(dateNow + " 00:00:00"));
			batchPayRecord.setEndCreateTime(sdf.parse(dateNow + " 23:59:59"));
		}
		if(batchPayRecord.getSortOrder() == null){
			//默认按照创建时间升序
			batchPayRecord.setSortOrder(SortOrderType.DESC.getValue());
		}
		//查询转账列表
		Page<BatchPayRecord> page = batchPayRecordService.findPage(new Page<BatchPayRecord>(request, response), batchPayRecord);
		/*//获取转账汇总信息
		batchPayRecord.setPage(null);
		List<BatchPayRecord> recordList = batchPayRecordService.findList(batchPayRecord);
		BatchPayRecordSummary bpRecordSummary = getSummaryBatchPayRecordResult(recordList);
		model.addAttribute("bpRecordSummary", bpRecordSummary);*/
		//获取转账明细列表
		if(null == page){
			model.addAttribute("page", page);
			return "modules/payment/batchPayRecordList";
		}
		List<BatchPayRecord> list = page.getList();
		if(null == list || list.isEmpty()){
			model.addAttribute("page", page);
			return "modules/payment/batchPayRecordList";
		}
		List<BatchPayRecord> newRecordList= new ArrayList<BatchPayRecord>();
		for(BatchPayRecord record:list){
			if(BatchPayStatus.PROCES.getValue().equals(record.getStatus())){
				List<String> batchIdList = new ArrayList<String>();
				batchIdList.add(record.getBatchId());
				List<BatchPayRecordDetail> detailList = batchPayRecordDetailService.getDetailsByBatchId(batchIdList);
				if(detailList != null && !detailList.isEmpty()){
					record.setStatus(BatchPayStatus.PREAUD.getValue());
				}
			}
			newRecordList.add(record);
		}
		page.setList(newRecordList);
		model.addAttribute("page", page);
		return "modules/payment/batchPayRecordList";
	}

	@RequiresPermissions("payment:batchPayRecord:view")
	@RequestMapping(value = "form")
	public String form(BatchPayRecord batchPayRecord, HttpServletRequest request, HttpServletResponse response,Model model) {
		//model.addAttribute("batchPayRecord", batchPayRecord);
//		Page<BatchPayRecord> page = batchPayRecordService.findPage(new Page<BatchPayRecord>(request, response), batchPayRecord);
		model.addAttribute("page", null);

		return "modules/payment/batchPayRecordDetail";
	}

	/**
	 * 转账数据汇总
	 * @param page
	 * @return
	 */
	private BatchPayRecordSummary getSummaryBatchPayRecordResult(List<BatchPayRecord> recordList){

		//List<BatchPayRecord> batchPayRecordList = batchPayRecordService.findAllList();
		BatchPayRecordSummary bpSummary = new BatchPayRecordSummary();
		if(null == recordList || recordList.isEmpty()){
			return bpSummary;
		}
		String tempTotal = "0";
		for(BatchPayRecord bpRecord : recordList){
			//转账总笔数
			tempTotal = StringUtil.isBlank(bpRecord.getTotalNum())?"0":bpRecord.getTotalNum();
			bpSummary.setTotalNum(String.valueOf(Integer.parseInt(bpSummary.getTotalNum()) + Integer.parseInt(tempTotal)));
			//成功总笔数
			tempTotal = StringUtil.isBlank(bpRecord.getSuccessTotalNum())?"0":bpRecord.getSuccessTotalNum();
			if(bpRecord.getStatus().equals(BatchPayStatus.FINISH.getValue())){
				bpSummary.setSuccessTotalNum(String.valueOf(Integer.parseInt(bpSummary.getSuccessTotalNum()) + Integer.parseInt(tempTotal)));
			}
			//转账总金额
			bpSummary.setTotalAmount(String.valueOf(new BigDecimal(bpSummary.getTotalAmount()).add(new BigDecimal(bpRecord.getTotalAmount()))));
			//成功转账总金额
			if(bpRecord.getStatus().equals(BatchPayStatus.FINISH.getValue())){
				if(StringUtils.isBlank(bpRecord.getSuccessTotalAmount())){
					bpSummary.setSuccessTotalAmount(String.valueOf(new BigDecimal(bpSummary.getSuccessTotalAmount())));
				}else{
					bpSummary.setSuccessTotalAmount(String.valueOf(new BigDecimal(bpSummary.getSuccessTotalAmount()).add(new BigDecimal(bpRecord.getSuccessTotalAmount()))));
				}
			}
			//转账总手续费
			tempTotal = StringUtil.isBlank(bpRecord.getFeeAmount())?"0.0000":bpRecord.getFeeAmount();
			bpSummary.setTotalFeeAmount(String.valueOf(new BigDecimal(bpSummary.getTotalFeeAmount()).add(new BigDecimal(tempTotal))));

			//失败总笔数
			if(bpRecord.getStatus().equals(BatchPayStatus.FINISH.getValue())){
				int failCount = 0;
				if(StringUtil.notBlank(bpSummary.getFailTotalNum())){
					failCount = Integer.parseInt(bpSummary.getFailTotalNum());
				}
				if(StringUtil.notBlank(bpRecord.getSuccessTotalNum())){
					bpSummary.setFailTotalNum(String.valueOf(failCount + (Integer.parseInt(bpRecord.getTotalNum()) - Integer.parseInt(bpRecord.getSuccessTotalNum()))));
				}else{
					bpSummary.setFailTotalNum(String.valueOf(failCount + (Integer.parseInt(bpRecord.getTotalNum()))));
				}
			}

			//失败转账总金额
			if(bpRecord.getStatus().equals(BatchPayStatus.FINISH.getValue())){
				if(StringUtil.notBlank(bpRecord.getSuccessTotalAmount())){
					BigDecimal t = new BigDecimal(bpRecord.getTotalAmount()).subtract(new BigDecimal(bpRecord.getSuccessTotalAmount()));
					bpSummary.setFailTotalAmount(String.valueOf(new BigDecimal(bpSummary.getFailTotalAmount()).add(t)));
				}else{
					BigDecimal t = new BigDecimal(bpRecord.getTotalAmount());
					bpSummary.setFailTotalAmount(String.valueOf(new BigDecimal(bpSummary.getFailTotalAmount()).add(t)));
				}
			}
		}
		return bpSummary;
	}
	
	
	 @RequiresPermissions("payment:batchPayRecord:view")
	  @RequestMapping(value = "exportExcel")
	  public void exportExcel(String merchantId, String batchId, String merchantBatchNo, String beginCreateTime,
	      String endCreateTime, HttpServletResponse response, HttpServletRequest request) throws Exception{
	    BatchPayRecord batchPayRecord = new BatchPayRecord();
	    if(StringUtils.isNotEmpty(merchantId)){
	      batchPayRecord.setMerchantId(Long.parseLong(merchantId));
	    }
	    if(StringUtils.isNotEmpty(batchId)){
	      batchPayRecord.setBatchId(batchId);
	    }
	    if(StringUtils.isNotEmpty(merchantBatchNo)){
	      batchPayRecord.setMerchantBatchNo(merchantBatchNo);
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    if(StringUtils.isNotEmpty(beginCreateTime)){
	      batchPayRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
	    }
	    if(StringUtils.isNotEmpty(endCreateTime)){
	      batchPayRecord.setEndCreateTime(sdf.parse(endCreateTime));
	    }
	    String title = "转账批次查询数据统计:";
	    if(batchPayRecord.getBeginCreateTime()!=null && batchPayRecord.getEndCreateTime()!=null){
	       title = "转账批次查询数据统计:"+DateUtils.getDateStr(batchPayRecord.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(batchPayRecord.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
	    }
	    String[] headers = new String[] { "商户编码", "商户公司名称", "商户账号", "批次号", "商户批次号", "转账总金额", "转账总笔数",
	        "处理状态", "银行服务类型", "创建时间"};


		 Page<BatchPayRecord> page = new Page<BatchPayRecord>(request, response);
		 page = batchPayRecordService.findPage(page, batchPayRecord);
		 int pageSize = 500;
		 int totalCount = (int)page.getCount();
		 int curPage = 1;//从第一页开始
		 int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
		 List<String[]> contents = new ArrayList<String[]>();
		 for(int i=1;i<=totalpage;i++) {
			 page.setPageNo(curPage);
			 page.setPageSize(pageSize);
			 page = batchPayRecordService.findPage(page, batchPayRecord);
			 List<BatchPayRecord> batchPayRecordList = page.getList();
			 for(BatchPayRecord record : batchPayRecordList){
				 String[] content = new String[headers.length];
				 content[0] = String.valueOf(record.getMerchantId());
				 content[1] = record.getMerchantCompany();
				 content[2] = record.getMerchantLoginName();
				 content[3] = record.getBatchId();
				 content[4] = record.getMerchantBatchNo();
				 content[5] = record.getTotalAmount();
				 content[6] = record.getTotalNum();
				 content[7] = DictUtils.getDictLabel(record.getStatus(), "BatchPayStatus", "");
				 SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
				 if(StringUtils.equals(sdf1.format(record.getIntoaccountTime()), "23:59:59")){
					 content[8] = "T+1";
				 }else{
					 content[8] = "T+0";
				 }
				 content[9] = sdf.format(record.getCreateTime());
				 contents.add(content);
			 }
			 curPage++;
		 }

	   String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
	   ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
	    
	  }
	
	
	 
	 @RequiresPermissions("payment:batchPayRecord:view")
	  @RequestMapping(value="getStatisticsList")
	  @ResponseBody
	  public void getStatisticsList(String merchantId, String batchId, String merchantBatchNo, String beginCreateTime,
        String endCreateTime, HttpServletResponse response) throws ParseException{
	   BatchPayRecord batchPayRecord = new BatchPayRecord();
     if(StringUtils.isNotEmpty(merchantId)){
       batchPayRecord.setMerchantId(Long.parseLong(merchantId));
     }
     if(StringUtils.isNotEmpty(batchId)){
       batchPayRecord.setBatchId(batchId);
     }
     if(StringUtils.isNotEmpty(merchantBatchNo)){
       batchPayRecord.setMerchantBatchNo(merchantBatchNo);
     }
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     if(StringUtils.isNotEmpty(beginCreateTime)){
       batchPayRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
     }
     if(StringUtils.isNotEmpty(endCreateTime)){
       batchPayRecord.setEndCreateTime(sdf.parse(endCreateTime));
     }
	    
     BatchPayRecordSummary bpSummary = new BatchPayRecordSummary();
     String tempTotal = "0";
     List<BatchPayRecord> recordList = batchPayRecordService.findList(batchPayRecord);
     for(BatchPayRecord bpRecord : recordList){
       //转账总笔数
       tempTotal = StringUtil.isBlank(bpRecord.getTotalNum())?"0":bpRecord.getTotalNum();
       bpSummary.setTotalNum(String.valueOf(Integer.parseInt(bpSummary.getTotalNum()) + Integer.parseInt(tempTotal)));
       //成功总笔数
       tempTotal = StringUtil.isBlank(bpRecord.getSuccessTotalNum())?"0":bpRecord.getSuccessTotalNum();
       if(bpRecord.getStatus().equals(BatchPayStatus.FINISH.getValue())){
         bpSummary.setSuccessTotalNum(String.valueOf(Integer.parseInt(bpSummary.getSuccessTotalNum()) + Integer.parseInt(tempTotal)));
       }
       //转账总金额
       bpSummary.setTotalAmount(String.valueOf(new BigDecimal(bpSummary.getTotalAmount()).add(new BigDecimal(bpRecord.getTotalAmount()))));
       //成功转账总金额
       if(bpRecord.getStatus().equals(BatchPayStatus.FINISH.getValue())){
         if(StringUtils.isBlank(bpRecord.getSuccessTotalAmount())){
           bpSummary.setSuccessTotalAmount(String.valueOf(new BigDecimal(bpSummary.getSuccessTotalAmount())));
         }else{
           bpSummary.setSuccessTotalAmount(String.valueOf(new BigDecimal(bpSummary.getSuccessTotalAmount()).add(new BigDecimal(bpRecord.getSuccessTotalAmount()))));
         }
       }
       //转账总手续费
       tempTotal = StringUtil.isBlank(bpRecord.getFeeAmount())?"0.0000":bpRecord.getFeeAmount();
       bpSummary.setTotalFeeAmount(String.valueOf(new BigDecimal(bpSummary.getTotalFeeAmount()).add(new BigDecimal(tempTotal))));

       //失败总笔数
       if(bpRecord.getStatus().equals(BatchPayStatus.FINISH.getValue())){
         int failCount = 0;
         if(StringUtil.notBlank(bpSummary.getFailTotalNum())){
           failCount = Integer.parseInt(bpSummary.getFailTotalNum());
         }
         if(StringUtil.notBlank(bpRecord.getSuccessTotalNum())){
           bpSummary.setFailTotalNum(String.valueOf(failCount + (Integer.parseInt(bpRecord.getTotalNum()) - Integer.parseInt(bpRecord.getSuccessTotalNum()))));
         }else{
           bpSummary.setFailTotalNum(String.valueOf(failCount + (Integer.parseInt(bpRecord.getTotalNum()))));
         }
       }

       //失败转账总金额
       if(bpRecord.getStatus().equals(BatchPayStatus.FINISH.getValue())){
         if(StringUtil.notBlank(bpRecord.getSuccessTotalAmount())){
           BigDecimal t = new BigDecimal(bpRecord.getTotalAmount()).subtract(new BigDecimal(bpRecord.getSuccessTotalAmount()));
           bpSummary.setFailTotalAmount(String.valueOf(new BigDecimal(bpSummary.getFailTotalAmount()).add(t)));
         }else{
           BigDecimal t = new BigDecimal(bpRecord.getTotalAmount());
           bpSummary.setFailTotalAmount(String.valueOf(new BigDecimal(bpSummary.getFailTotalAmount()).add(t)));
         }
       }
     }
	    
	    DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(); 
	    df.applyPattern("￥#,##0.00"); 
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("totalNum", bpSummary.getTotalNum() + "笔");
	    map.put("successTotalNum", bpSummary.getSuccessTotalNum() + "笔");
	    map.put("failTotalNum", bpSummary.getFailTotalNum() + "笔");
	    map.put("totalAmount", df.format(StringUtils.isEmpty(bpSummary.getTotalAmount())?BigDecimal.ZERO:new BigDecimal(bpSummary.getTotalAmount())));
	    map.put("successTotalAmount", df.format(StringUtils.isEmpty(bpSummary.getSuccessTotalAmount())?BigDecimal.ZERO:new BigDecimal(bpSummary.getSuccessTotalAmount())));
	    map.put("failTotalAmount", df.format(StringUtils.isEmpty(bpSummary.getFailTotalAmount())?BigDecimal.ZERO:new BigDecimal(bpSummary.getFailTotalAmount())));
	    map.put("totalFeeAmount", df.format(StringUtils.isEmpty(bpSummary.getTotalFeeAmount())?BigDecimal.ZERO:new BigDecimal(bpSummary.getTotalFeeAmount())));
	    
	    WebUtil.outputJson(map, response);
	    
	    
	  }
	 
	 
	
}