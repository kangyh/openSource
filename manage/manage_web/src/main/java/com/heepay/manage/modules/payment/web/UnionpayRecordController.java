/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.ChargeRecordStatus;
import com.heepay.manage.modules.payment.entity.ChargeRecord;
import com.heepay.manage.modules.payment.entity.UnionpayRecord;
import com.heepay.manage.modules.payment.service.UnionpayRecordService;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.util.ExcelUtil2007;
import com.heepay.payment.enums.UnionPayStatus;


/**
 *
 * 描    述：银联扫码支付Controller
 *
 * 创 建 者： @author ld
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
@RequestMapping(value = "${adminPath}/payment/unionpayRecord")
public class UnionpayRecordController extends BaseController {

	@Autowired
	private UnionpayRecordService unionpayRecordService;
	
	@ModelAttribute
	public UnionpayRecord get(@RequestParam(required=false) String id) {
		UnionpayRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = unionpayRecordService.get(id);
		}
		if (entity == null){
			entity = new UnionpayRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:unionpayRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(UnionpayRecord unionpayRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
	  if(StringUtils.isEmpty(unionpayRecord.getGroupType())){
	    unionpayRecord.setGroupType("1");
    }
	  Page<UnionpayRecord> page = new Page<>(request, response);
		page.setOrderBy("create_time desc");
		page = unionpayRecordService.findPage(page, unionpayRecord);
		model.addAttribute("page", page);
		model.addAttribute("groupType", unionpayRecord.getGroupType());
		return "modules/payment/unionpayRecordList";
	}

	@RequiresPermissions("payment:unionpayRecord:view")
	@RequestMapping(value = "form")
	public String form(UnionpayRecord unionpayRecord, Model model) {
		model.addAttribute("unionpayRecord", unionpayRecord);
		return "modules/payment/unionpayRecordForm";
	}

	@RequiresPermissions("payment:unionpayRecord:edit")
	@RequestMapping(value = "save")
	public String save(UnionpayRecord unionpayRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, unionpayRecord)){
			return form(unionpayRecord, model);
		}
		unionpayRecordService.save(unionpayRecord);
		addMessage(redirectAttributes, "保存银联扫码支付成功");
		return "redirect:"+Global.getAdminPath()+"/payment/unionpayRecord/?repage";
	}
	
	@RequiresPermissions("payment:unionpayRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(UnionpayRecord unionpayRecord, RedirectAttributes redirectAttributes) {
		unionpayRecordService.delete(unionpayRecord);
		addMessage(redirectAttributes, "删除银联扫码支付成功");
		return "redirect:"+Global.getAdminPath()+"/payment/unionpayRecord/?repage";
	}
	
	
	
	@RequiresPermissions("payment:unionpayRecord:view")
  @RequestMapping(value = "exportExcel")
  public void exportExcel(String unionpayId, String paymentId, String merchantId, String merchantOrderNo, String beginCreateTime,
      String endCreateTime, String type, String status, String groupType, HttpServletRequest request, HttpServletResponse response) throws Exception{
	  UnionpayRecord unionpayRecord = new UnionpayRecord();
    if(StringUtils.isNotEmpty(unionpayId)){
      unionpayRecord.setUnionpayId(unionpayId);
    }
    if(StringUtils.isNotEmpty(merchantId)){
      unionpayRecord.setMerchantId(Long.parseLong(merchantId));
    }
    if(StringUtils.isNotEmpty(paymentId)){
      unionpayRecord.setPaymentId(paymentId);
    }
    if(StringUtils.isNotEmpty(merchantOrderNo)){
      unionpayRecord.setMerchantOrderNo(merchantOrderNo);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(StringUtils.isNotEmpty(beginCreateTime)){
      unionpayRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
    }
    if(StringUtils.isNotEmpty(endCreateTime)){
      unionpayRecord.setEndCreateTime(sdf.parse(endCreateTime));
    }
    if(StringUtils.isNotEmpty(type)){
      unionpayRecord.setType(type);
    }
    if(StringUtils.isNotEmpty(status)){
      unionpayRecord.setStatus(status);
    }
    unionpayRecord.setGroupType(groupType);
    List<String[]> contents = new ArrayList<String[]>();
    String title = "银联扫码数据统计:";
    if(unionpayRecord.getBeginCreateTime()!=null && unionpayRecord.getEndCreateTime()!=null){
       title = "银联扫码数据统计:"+DateUtils.getDateStr(unionpayRecord.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(unionpayRecord.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
    }
    String[] headers = new String[] { "交易号", "预下单号", "支付单号", "商户ID", "商户登录账号", "商户公司", "商户订单号",
        "来源方式", "交易时间", "交易金额", "交易类型", "交易状态", "手续费金额" };
    
    //先查出来总的记录数
    int pageSize = 500;
    int totalCount = unionpayRecordService.getCountByParams(unionpayRecord);
    int curPage = 1;//从第一页开始
    int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
    for(int i=1;i<=totalpage;i++){
      Page<UnionpayRecord> page = new Page<UnionpayRecord>(request, response);
      page.setPageNo(curPage);
      page.setPageSize(pageSize);
      page = unionpayRecordService.findPage(page, unionpayRecord);
      List<UnionpayRecord> list = page.getList();
      for(UnionpayRecord record : list){
        String[] content = new String[headers.length];
        content[0] = record.getUnionpayId();
        content[1] = record.getPrepayId();
        content[2] = record.getPaymentId();
        content[3] = String.valueOf(record.getMerchantId());
        content[4] = record.getMerchantLoginName();
        content[5] = record.getMerchantCompany();
        content[6] = record.getMerchantOrderNo();
        content[7] = DictUtils.getDictLabel(record.getRequestType(), "RequestType", "");
        content[8] = sdf.format(record.getCreateTime());
        content[9] = record.getRequestAmount();
        content[10] = DictUtils.getDictLabel(record.getType(), "UnionpayType", "");
        content[11] = DictUtils.getDictLabel(record.getStatus(), "UnionpayStatus", "");
        content[12] = record.getFeeAmount();
        contents.add(content);
      }
      curPage++;
      
    }
   String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
   ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
    
  }
  
  
  @RequiresPermissions("payment:unionpayRecord:view")
  @RequestMapping(value="getStatisticsList")
  @ResponseBody
  public void getStatisticsList(String unionpayId, String paymentId, String merchantId, String merchantOrderNo, String beginCreateTime,
      String endCreateTime, String type, String status, String groupType, HttpServletResponse response) throws ParseException{
    UnionpayRecord unionpayRecord = new UnionpayRecord();
    if(StringUtils.isNotEmpty(unionpayId)){
      unionpayRecord.setUnionpayId(unionpayId);
    }
    if(StringUtils.isNotEmpty(merchantId)){
      unionpayRecord.setMerchantId(Long.parseLong(merchantId));
    }
    if(StringUtils.isNotEmpty(paymentId)){
      unionpayRecord.setPaymentId(paymentId);
    }
    if(StringUtils.isNotEmpty(merchantOrderNo)){
      unionpayRecord.setMerchantOrderNo(merchantOrderNo);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(StringUtils.isNotEmpty(beginCreateTime)){
      unionpayRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
    }
    if(StringUtils.isNotEmpty(endCreateTime)){
      unionpayRecord.setEndCreateTime(sdf.parse(endCreateTime));
    }
    if(StringUtils.isNotEmpty(type)){
      unionpayRecord.setType(type);
    }
    if(StringUtils.isNotEmpty(status)){
      unionpayRecord.setStatus(status);
    }
    unionpayRecord.setGroupType(groupType);
    
    //汇总金额
    BigDecimal totalAmount = BigDecimal.ZERO;
    BigDecimal failedTotalAmount = BigDecimal.ZERO;
    BigDecimal successTotalAmount = BigDecimal.ZERO;
    BigDecimal feeTotalAmount = BigDecimal.ZERO;
    List<UnionpayRecord> unionpayRecordList = unionpayRecordService.findList(unionpayRecord);
    
    if(unionpayRecordList != null && !unionpayRecordList.isEmpty()){
      for(UnionpayRecord record : unionpayRecordList){
        totalAmount = totalAmount.add(new BigDecimal(record.getRequestAmount()));
        if(StringUtils.equals(record.getStatus(), UnionPayStatus.SUCCES.getValue())){
          //成功金额
          BigDecimal successAmount = new BigDecimal(StringUtils.isEmpty(record.getSuccessAmount())?"0.00":record.getSuccessAmount());
          successTotalAmount = successTotalAmount.add(successAmount).setScale(2, RoundingMode.HALF_UP);
          //手续费
          BigDecimal feeAmount = new BigDecimal(StringUtils.isEmpty(record.getFeeAmount())?"0.00":record.getFeeAmount());
          feeTotalAmount = feeTotalAmount.add(feeAmount).setScale(2, RoundingMode.HALF_UP);
          
        }else if(StringUtils.equals(record.getStatus(), UnionPayStatus.FAILED.getValue())){
          //失败金额
          BigDecimal failedAmount = new BigDecimal(StringUtils.isEmpty(record.getRequestAmount())?"0.00":record.getRequestAmount());
          failedTotalAmount = failedTotalAmount.add(failedAmount).setScale(2, RoundingMode.HALF_UP);
        }
        
        
      }
    }
    
    DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(); 
    df.applyPattern("￥#,##0.00"); 
    Map<String, String> map = new HashMap<String, String>();
    map.put("totalAmount", df.format(totalAmount));
    map.put("successTotalAmount", df.format(successTotalAmount));
    map.put("failedTotalAmount", df.format(failedTotalAmount));
    map.put("feeTotalAmount", df.format(feeTotalAmount));
    
    WebUtil.outputJson(map, response);
    
    
  }
	
	
	

}