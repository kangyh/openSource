/**
 *  
 */
package com.heepay.manage.modules.payment.web;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.SortOrderType;
import com.heepay.enums.WithdrawStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.WithdrawRecord;
import com.heepay.manage.modules.payment.entity.WithdrawRecordSummary;
import com.heepay.manage.modules.payment.service.WithdrawRecordService;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.util.ExcelUtil2007;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 提现管理Controller
 * @author hs
 * @version V1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/payment/withdrawRecordOrderList")
public class WithdrawRecordOrderListController extends BaseController {

	@Autowired
	private WithdrawRecordService withdrawRecordService;
	
	//记录日志
	private static final Logger log = LogManager.getLogger();
	
	@ModelAttribute
	public WithdrawRecord get(@RequestParam(required=false) String id) {
		WithdrawRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = withdrawRecordService.get(id);
		}
		if (entity == null){
			entity = new WithdrawRecord();
		}
		return entity;
	}

	@RequiresPermissions("payment:withdrawRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(WithdrawRecord withdrawRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(withdrawRecord.getSortOrder() == null){
			//默认按照创建时间升序
			withdrawRecord.setSortOrder(SortOrderType.DESC.getValue());
		}
		Page<WithdrawRecord> page = withdrawRecordService.findPage(new Page<WithdrawRecord>(request, response), withdrawRecord);
		model.addAttribute("orderPage", page);
		//获取提现汇总
		withdrawRecord.setPage(null);
		List<WithdrawRecord> recordList = withdrawRecordService.findList(withdrawRecord);
		WithdrawRecordSummary withdrawRecordSummary = getWithdrawRecordSummary(recordList);
		model.addAttribute("withdrawRecordSummary", withdrawRecordSummary);
		return "modules/payment/withdrawRecordOrderList";
	}

	/**
	 * 
	* @discription 获取提现汇总
	* @author yanxb       
	* @created 2016年11月28日 下午2:05:31     
	* @return
	 */
	private WithdrawRecordSummary getWithdrawRecordSummary(List<WithdrawRecord> recordList) {
		WithdrawRecordSummary summary = new WithdrawRecordSummary();
		if(null == recordList || recordList.isEmpty()){
			return summary;
		}
		//成功总笔数
		int successNum = 0;
		//失败总笔数
		int failNum = 0;
		//提现总金额
		BigDecimal totalAmount = new BigDecimal(0);
		//成功总金额
		BigDecimal successTotalAmount = new BigDecimal(0);
		//失败总金额
		BigDecimal failTotalAmount = new BigDecimal(0);
		//提现手续费
		BigDecimal totalFeeAmount = new BigDecimal(0);
		for(WithdrawRecord record : recordList){
			log.info("提现订单查询，汇总提现信息开始！");
			//提现总金额
			totalAmount = totalAmount.add(new BigDecimal(record.getWithdrawAmount()));
			//提现手续费
			totalFeeAmount = totalFeeAmount.add(new BigDecimal(record.getFeeAmount()));
			//提现状态
			String status = record.getStatus();
			if(WithdrawStatus.SUCCESS.getValue().equals(status)){//提现成功
				successNum += 1;//成功笔数+1
				successTotalAmount = successTotalAmount.add(new BigDecimal(record.getWithdrawAmount()));//成功金额增加
			}
			if(WithdrawStatus.FAILED.getValue().equals(status) || WithdrawStatus.AUDREJ.getValue().equals(status)){//提现失败或者拒绝
				failNum += 1;//失败笔数+1
				failTotalAmount = failTotalAmount.add(new BigDecimal(record.getWithdrawAmount()));//失败金额增加
			}
		}
		log.info("提现订单查询，汇总提现信息结束！");
		//总笔数
		summary.setTotalNum(String.valueOf(recordList.size()));
		//成功笔数
		summary.setSuccessTotalNum(String.valueOf(successNum));
		//失败笔数
		summary.setFailTotalNum(String.valueOf(failNum));
		//总金额
		summary.setTotalAmount(String.valueOf(totalAmount));
		//成功金额
		summary.setSuccessTotalAmount(String.valueOf(successTotalAmount));
		//失败金额
		summary.setFailTotalAmount(String.valueOf(failTotalAmount));
		//手续费
		summary.setTotalFeeAmount(String.valueOf(totalFeeAmount));
		return summary;
	}

	@RequiresPermissions("payment:withdrawRecord:view")
	@RequestMapping(value = "form")
	public String form(WithdrawRecord withdrawRecord, Model model) {
		model.addAttribute("withdrawRecord", withdrawRecord);
		return "modules/payment/withdrawRecordForm";
	}
	
	
	
	@RequiresPermissions("payment:chargeRecord:view")
  @RequestMapping(value = "exportExcel")
  public void exportExcel(String withdrawId, String merchantId, String merchantCompany, String merchantLoginName, String beginCreateTime,
      String endCreateTime, String withDrawStatus, String paymentId, HttpServletResponse response, HttpServletRequest request) throws Exception{
	  WithdrawRecord withdrawRecord = new WithdrawRecord();
    if(StringUtils.isNotEmpty(withdrawId)){
      withdrawRecord.setWithdrawId(withdrawId);
    }
    if(StringUtils.isNotEmpty(merchantId)){
      withdrawRecord.setMerchantId(Long.parseLong(merchantId));
    }
    if(StringUtils.isNotEmpty(merchantCompany)){
      withdrawRecord.setMerchantCompany(merchantCompany);
    }
    if(StringUtils.isNotEmpty(merchantLoginName)){
      withdrawRecord.setMerchantLoginName(merchantLoginName);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
    if(StringUtils.isNotEmpty(beginCreateTime)){
      withdrawRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
    }
    if(StringUtils.isNotEmpty(endCreateTime)){
      withdrawRecord.setEndCreateTime(sdf.parse(endCreateTime));
    }
    if(StringUtils.isNotEmpty(withDrawStatus)){
      withdrawRecord.setStatus(withDrawStatus);
    }
    if(StringUtils.isNotEmpty(paymentId)){
      withdrawRecord.setPaymentId(paymentId);
    }
    List<String[]> contents = new ArrayList<String[]>();
    String title = "提现数据统计:";
    if(withdrawRecord.getBeginCreateTime()!=null && withdrawRecord.getEndCreateTime()!=null){
       title = "提现数据统计:"+DateUtils.getDateStr(withdrawRecord.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(withdrawRecord.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
    }
    String[] headers = new String[] { "商户编码", "商户公司名称", "商户账号", "交易订单号", "提现金额", 
        "手续费", "支付单号", "提现银行卡号", "提现状态", "提现模式", "申请时间" };

        Page<WithdrawRecord> page = new Page<WithdrawRecord>(request, response);
        page = withdrawRecordService.findPage(page, withdrawRecord);
        int pageSize = 500;
        int totalCount = (int)page.getCount();
        int curPage = 1;//从第一页开始
        int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
        for(int i=1;i<=totalpage;i++) {
            page.setPageNo(curPage);
            page.setPageSize(pageSize);
            page = withdrawRecordService.findPage(page, withdrawRecord);
            List<WithdrawRecord> withdrawRecordList= page.getList();
            for(WithdrawRecord record : withdrawRecordList){
                String[] content = new String[headers.length];
                content[0] = String.valueOf(record.getMerchantId());
                content[1] = record.getMerchantCompany();
                content[2] = record.getMerchantLoginName();
                content[3] = record.getWithdrawId();
                content[4] = record.getWithdrawAmount();
                content[5] = record.getFeeAmount();
                content[6] = record.getPaymentId();
                content[7] = record.getBankcardNo();
                content[8] = DictUtils.getDictLabel(record.getStatus(), "WithdrawStatus", "");
                String intoaccountTime = sdfTime.format(record.getIntoaccountTime());
                if(StringUtils.equals(intoaccountTime, "23:59:59")){
                    content[9] = "T+1";
                }else{
                    content[9] = "T+0";
                }
                content[10] = String.valueOf(sdf.format(record.getCreateTime()));
                contents.add(content);
            }
            curPage++;
        }
   String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
   ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
    
  }
	
	
	
	@RequiresPermissions("payment:withdrawRecord:view")
  @RequestMapping(value="getStatisticsList")
  @ResponseBody
  public void getStatisticsList(String withdrawId, String merchantId, String merchantCompany, String merchantLoginName, String beginCreateTime,
      String endCreateTime, String withDrawStatus, String paymentId, HttpServletResponse response) throws ParseException{
	  WithdrawRecord withdrawRecord = new WithdrawRecord();
    if(StringUtils.isNoneEmpty(withdrawId)){
      withdrawRecord.setWithdrawId(withdrawId);
    }
    if(StringUtils.isNoneEmpty(merchantId)){
      withdrawRecord.setMerchantId(Long.parseLong(merchantId));
    }
    if(StringUtils.isNoneEmpty(merchantCompany)){
      withdrawRecord.setMerchantCompany(merchantCompany);
    }
    if(StringUtils.isNoneEmpty(merchantLoginName)){
      withdrawRecord.setMerchantLoginName(merchantLoginName);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(StringUtils.isNotEmpty(beginCreateTime)){
      withdrawRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
    }
    if(StringUtils.isNotEmpty(endCreateTime)){
      withdrawRecord.setEndCreateTime(sdf.parse(endCreateTime));
    }
    if(StringUtils.isNoneEmpty(withDrawStatus)){
      withdrawRecord.setStatus(withDrawStatus);
    }
    if(StringUtils.isNoneEmpty(paymentId)){
      withdrawRecord.setPaymentId(paymentId);
    }
    
    WithdrawRecordSummary summary = new WithdrawRecordSummary();
    //成功总笔数
    int successNum = 0;
    //失败总笔数
    int failNum = 0;
    //提现总金额
    BigDecimal totalAmount = new BigDecimal(0);
    //成功总金额
    BigDecimal successTotalAmount = new BigDecimal(0);
    //失败总金额
    BigDecimal failTotalAmount = new BigDecimal(0);
    //提现手续费
    BigDecimal totalFeeAmount = new BigDecimal(0);
    List<WithdrawRecord> withdrawRecordList = withdrawRecordService.findList(withdrawRecord);
    for(WithdrawRecord record : withdrawRecordList){
      log.info("提现订单查询，汇总提现信息开始！");
      //提现总金额
      totalAmount = totalAmount.add(new BigDecimal(record.getWithdrawAmount()));
      //提现手续费
      totalFeeAmount = totalFeeAmount.add(new BigDecimal(record.getFeeAmount()));
      //提现状态
      String status = record.getStatus();
      if(WithdrawStatus.SUCCESS.getValue().equals(status)){//提现成功
        successNum += 1;//成功笔数+1
        successTotalAmount = successTotalAmount.add(new BigDecimal(record.getWithdrawAmount()));//成功金额增加
      }
      if(WithdrawStatus.FAILED.getValue().equals(status) || WithdrawStatus.AUDREJ.getValue().equals(status)){//提现失败或者拒绝
        failNum += 1;//失败笔数+1
        failTotalAmount = failTotalAmount.add(new BigDecimal(record.getWithdrawAmount()));//失败金额增加
      }
    }
    log.info("提现订单查询，汇总提现信息结束！");
    DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(); 
    df.applyPattern("￥#,##0.00"); 
    //总笔数
    summary.setTotalNum(String.valueOf(withdrawRecordList.size()) + "笔");
    //成功笔数
    summary.setSuccessTotalNum(String.valueOf(successNum) + "笔");
    //失败笔数
    summary.setFailTotalNum(String.valueOf(failNum) + "笔");
    //总金额
    summary.setTotalAmount(df.format(totalAmount));
    //成功金额
    summary.setSuccessTotalAmount(df.format(successTotalAmount));
    //失败金额
    summary.setFailTotalAmount(df.format(failTotalAmount));
    //手续费
    summary.setTotalFeeAmount(df.format(totalFeeAmount));
    
    WebUtil.outputJson(summary, response);
    
    
  }
	
	
	
}