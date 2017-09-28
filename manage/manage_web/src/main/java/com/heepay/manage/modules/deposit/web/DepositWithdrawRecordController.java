/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.deposit.web;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.BankcardType;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.deposit.entity.DepositWithdrawRecord;
import com.heepay.manage.modules.deposit.service.DepositWithdrawRecordService;
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
 * 描    述：查询Controller
 *
 * 创 建 者： @author 王亚洪
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
@RequestMapping(value = "${adminPath}/deposit/depositWithdrawRecord")
public class DepositWithdrawRecordController extends BaseController {

	@Autowired
	private DepositWithdrawRecordService depositWithdrawRecordService;
	
	@ModelAttribute
	public DepositWithdrawRecord get(@RequestParam(required=false) String id) {
		DepositWithdrawRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = depositWithdrawRecordService.get(id);
		}
		if (entity == null){
			entity = new DepositWithdrawRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("deposit:depositWithdrawRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(DepositWithdrawRecord depositWithdrawRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
	  if(depositWithdrawRecord.getSortOrder() == null){
      //默认按照创建时间降序
	    depositWithdrawRecord.setSortOrder(SortOrderType.DESC.getValue());
    }
	  Page<DepositWithdrawRecord> page = depositWithdrawRecordService.findPage(new Page<DepositWithdrawRecord>(request, response), depositWithdrawRecord); 
		model.addAttribute("page", page);
		return "modules/deposit/depositWithdrawRecordList";
	}

	@RequiresPermissions("deposit:depositWithdrawRecord:view")
	@RequestMapping(value = "form")
	public String form(DepositWithdrawRecord depositWithdrawRecord, Model model) {
		model.addAttribute("depositWithdrawRecord", depositWithdrawRecord);
		return "modules/deposit/depositWithdrawRecordForm";
	}

	@RequiresPermissions("deposit:depositWithdrawRecord:edit")
	@RequestMapping(value = "save")
	public String save(DepositWithdrawRecord depositWithdrawRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, depositWithdrawRecord)){
			return form(depositWithdrawRecord, model);
		}
		depositWithdrawRecordService.save(depositWithdrawRecord);
		addMessage(redirectAttributes, "保存查询成功");
		return "redirect:"+Global.getAdminPath()+"/deposit/depositWithdrawRecord/?repage";
	}
	
	@RequiresPermissions("deposit:depositWithdrawRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(DepositWithdrawRecord depositWithdrawRecord, RedirectAttributes redirectAttributes) {
		depositWithdrawRecordService.delete(depositWithdrawRecord);
		addMessage(redirectAttributes, "删除查询成功");
		return "redirect:"+Global.getAdminPath()+"/deposit/depositWithdrawRecord/?repage";
	}
	
	
	
	@RequiresPermissions("deposit:depositWithdrawRecord:view")
  @RequestMapping(value = "exportExcel")
  public void exportExcel(String depositWithdrawId, String paymentId, String businessSeqNo, String merchantId, String beginCreateTime,
      String endCreateTime, String accountId, String status, HttpServletResponse response, HttpServletRequest request) throws Exception{
	  DepositWithdrawRecord depositWithdrawRecord = new DepositWithdrawRecord();
    if(StringUtils.isNotEmpty(depositWithdrawId)){
      depositWithdrawRecord.setDepositWithdrawId(depositWithdrawId);
    }
    if(StringUtils.isNotEmpty(paymentId)){
      depositWithdrawRecord.setPaymentId(paymentId);
    }
    if(StringUtils.isNotEmpty(businessSeqNo)){
      depositWithdrawRecord.setBusinessSeqNo(businessSeqNo);
    }
    if(StringUtils.isNotEmpty(merchantId)){
      depositWithdrawRecord.setMerchantId(Long.parseLong(merchantId));
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(StringUtils.isNotEmpty(beginCreateTime)){
      depositWithdrawRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
    }
    if(StringUtils.isNotEmpty(endCreateTime)){
      depositWithdrawRecord.setEndCreateTime(sdf.parse(endCreateTime));
    }
    
    if(StringUtils.isNotEmpty(accountId)){
      depositWithdrawRecord.setAccountId(Long.parseLong(accountId));
    }
    
    if(StringUtils.isNotEmpty(status)){
      depositWithdrawRecord.setStatus(status);
    }
    
    
    String title = "存管提现数据统计:";
    if(depositWithdrawRecord.getBeginCreateTime()!=null && depositWithdrawRecord.getEndCreateTime()!=null){
       title = "存管提现数据统计:"+DateUtils.getDateStr(depositWithdrawRecord.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(depositWithdrawRecord.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
    }
    String[] headers = new String[] { "交易订单号", "银行流水号", "支付订单号", "商户编码", "商户名称", "商户账户", "账户名称",
        "卡持有人", "开户行支行名称", "银联行号", "银行卡类型", "提现金额", "状态", "创建时间"};


        Page<DepositWithdrawRecord> page = new Page<DepositWithdrawRecord>(request, response);
        page = depositWithdrawRecordService.findPage(page, depositWithdrawRecord);
        int pageSize = 500;
        int totalCount = (int)page.getCount();
        int curPage = 1;//从第一页开始
        int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
        List<String[]> contents = new ArrayList<String[]>();
        for(int i=1;i<=totalpage;i++) {
            page.setPageNo(curPage);
            page.setPageSize(pageSize);
            page = depositWithdrawRecordService.findPage(page, depositWithdrawRecord);
            List<DepositWithdrawRecord> depositWithdrawRecordList = page.getList();
            for(DepositWithdrawRecord record : depositWithdrawRecordList){
                String[] content = new String[headers.length];
                content[0] = record.getDepositWithdrawId();
                content[1] = record.getBusinessSeqNo();
                content[2] = record.getPaymentId();
                content[3] = String.valueOf(record.getMerchantId());
                content[4] = record.getMerchantLoginName();
                content[5] = String.valueOf(record.getAccountId());
                content[6] = record.getAccountName();
                content[7] = record.getRecAccName();
                content[8] = record.getRecOpenAccDept();
                content[9] = record.getRecBankNo();
                content[10] = BankcardType.labelOf(record.getCardTypeCode());
                content[11] = record.getAmount();
                if(StringUtils.equals(record.getStatus(), "PREDRA")){
                    content[12] = "待处理";
                }else if(StringUtils.equals(record.getStatus(), "SUCCES")){
                    content[12] = "成功";
                }else{
                    content[12] = "失败";
                }
                content[13] = sdf.format(record.getCreateTime());
                contents.add(content);
            }
            curPage++;
        }

   String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
   ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
    
  }
  
  
  @RequiresPermissions("deposit:depositWithdrawRecord:view")
  @RequestMapping(value="getStatisticsList")
  @ResponseBody
  public void getStatisticsList(String depositWithdrawId, String paymentId, String businessSeqNo, String merchantId, String beginCreateTime,
      String endCreateTime, String accountId, String status, HttpServletResponse response) throws ParseException{
    DepositWithdrawRecord depositWithdrawRecord = new DepositWithdrawRecord();
    if(StringUtils.isNotEmpty(depositWithdrawId)){
      depositWithdrawRecord.setDepositWithdrawId(depositWithdrawId);
    }
    if(StringUtils.isNotEmpty(paymentId)){
      depositWithdrawRecord.setPaymentId(paymentId);
    }
    if(StringUtils.isNotEmpty(businessSeqNo)){
      depositWithdrawRecord.setBusinessSeqNo(businessSeqNo);
    }
    if(StringUtils.isNotEmpty(merchantId)){
      depositWithdrawRecord.setMerchantId(Long.parseLong(merchantId));
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(StringUtils.isNotEmpty(beginCreateTime)){
      depositWithdrawRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
    }
    if(StringUtils.isNotEmpty(endCreateTime)){
      depositWithdrawRecord.setEndCreateTime(sdf.parse(endCreateTime));
    }
    
    if(StringUtils.isNotEmpty(accountId)){
      depositWithdrawRecord.setAccountId(Long.parseLong(accountId));
    }
    
    if(StringUtils.isNotEmpty(status)){
      depositWithdrawRecord.setStatus(status);
    }
    
    //汇总金额
    int pendingTotalSum = 0;
    int failedTotalSum = 0;
    int successTotalSum = 0;
    BigDecimal pendingTotalAmount = BigDecimal.ZERO;
    BigDecimal failedTotalAmount = BigDecimal.ZERO;
    BigDecimal successTotalAmount = BigDecimal.ZERO;
    List<DepositWithdrawRecord> depositWithdrawRecordList = depositWithdrawRecordService.findList(depositWithdrawRecord);
    if(depositWithdrawRecordList != null && !depositWithdrawRecordList.isEmpty()){
      for(DepositWithdrawRecord record : depositWithdrawRecordList){
        if(StringUtils.equals(record.getStatus(), "PREDRA")){
          //待支付金额
          BigDecimal pendingAmount = new BigDecimal(StringUtils.isEmpty(record.getAmount())?"0.00":record.getAmount());
          pendingTotalAmount = pendingTotalAmount.add(pendingAmount).setScale(2, RoundingMode.HALF_UP);
          pendingTotalSum++;
        }else if(StringUtils.equals(record.getStatus(), "SUCCES")){
          //成功金额
          BigDecimal successAmount = new BigDecimal(StringUtils.isEmpty(record.getAmount())?"0.00":record.getAmount());
          successTotalAmount = successTotalAmount.add(successAmount).setScale(2, RoundingMode.HALF_UP);
          successTotalSum++;
        }else{
          //失败金额
          BigDecimal failedAmount = new BigDecimal(StringUtils.isEmpty(record.getAmount())?"0.00":record.getAmount());
          failedTotalAmount = failedTotalAmount.add(failedAmount).setScale(2, RoundingMode.HALF_UP);
          failedTotalSum++;
        }
      }
    }
    
    DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(); 
    df.applyPattern("￥#,##0.00"); 
    Map<String, String> map = new HashMap<String, String>();
    map.put("pendingTotalSum", pendingTotalSum + "笔");
    map.put("failedTotalSum", failedTotalSum + "笔");
    map.put("successTotalSum", successTotalSum + "笔");
    map.put("pendingTotalAmount", df.format(pendingTotalAmount));
    map.put("failedTotalAmount", df.format(failedTotalAmount));
    map.put("successTotalAmount", df.format(successTotalAmount));
    WebUtil.outputJson(map, response);
    
    
  }
	
	

}