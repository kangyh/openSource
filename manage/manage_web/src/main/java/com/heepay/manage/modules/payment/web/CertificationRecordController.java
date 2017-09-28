/**
、 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.CertificationResult;
import com.heepay.enums.ChargeRecordStatus;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.CertificationRecord;
import com.heepay.manage.modules.payment.service.CertificationRecordService;
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
 * 描    述：实名认证Controller
 *
 * 创 建 者： @author tyq
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
@RequestMapping(value = "${adminPath}/payment/certificationRecord")
public class CertificationRecordController extends BaseController {

	@Autowired
	private CertificationRecordService certificationRecordService;
	
	@ModelAttribute
	public CertificationRecord get(@RequestParam(required=false) String id) {
		CertificationRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = certificationRecordService.get(id);
		}
		if (entity == null){
			entity = new CertificationRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:certificationRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(CertificationRecord certificationRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if("R1".equals(certificationRecord.getStatus())){
			certificationRecord.setStatus(null);
		}
		if(certificationRecord.getSortOrder()==null){
			certificationRecord.setSortOrder(SortOrderType.DESC.getValue());
		}
		
		Page<CertificationRecord> page = certificationRecordService.findPage(new Page<CertificationRecord>(request, response), certificationRecord); 
		model.addAttribute("page", page);
		return "modules/payment/certificationRecordList";
	}

	@RequiresPermissions("payment:certificationRecord:view")
	@RequestMapping(value = "form")
	public String form(CertificationRecord certificationRecord, Model model) {
		model.addAttribute("certificationRecord", certificationRecord);
		return "modules/payment/certificationRecordForm";
	}

	@RequiresPermissions("payment:certificationRecord:edit")
	@RequestMapping(value = "save")
	public String save(CertificationRecord certificationRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, certificationRecord)){
			return form(certificationRecord, model);
		}
		certificationRecordService.save(certificationRecord);
		addMessage(redirectAttributes, "保存实名认证成功");
		return "redirect:"+Global.getAdminPath()+"/payment/certificationRecord/?repage";
	}
	
	@RequiresPermissions("payment:certificationRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(CertificationRecord certificationRecord, RedirectAttributes redirectAttributes) {
		certificationRecordService.delete(certificationRecord);
		addMessage(redirectAttributes, "删除实名认证成功");
		return "redirect:"+Global.getAdminPath()+"/payment/certificationRecord/?repage";
	}

	
	@RequiresPermissions("payment:certificationRecord:view")
  @RequestMapping(value = "exportExcel")
  public void exportExcel(String certificationId, String merchantId, String merchantCompany, String beginCreateTime,
      String endCreateTime, String status, HttpServletResponse response, HttpServletRequest request) throws Exception{
	  CertificationRecord certificationRecord = new CertificationRecord();
    if(StringUtils.isNotEmpty(certificationId)){
      certificationRecord.setCertificationId(certificationId);
    }
    if(StringUtils.isNotEmpty(merchantId)){
      certificationRecord.setMerchantId(Long.parseLong(merchantId));
    }
    if(StringUtils.isNotEmpty(merchantCompany)){
      certificationRecord.setMerchantCompany(merchantCompany);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(StringUtils.isNotEmpty(beginCreateTime)){
      certificationRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
    }
    if(StringUtils.isNotEmpty(endCreateTime)){
      certificationRecord.setEndCreateTime(sdf.parse(endCreateTime));
    }
    
    if(StringUtils.isNotEmpty(status) && !StringUtils.equals("R1", status)){
      certificationRecord.setStatus(status);
    }
    String title = "实名认证数据统计:";
    if(certificationRecord.getBeginCreateTime()!=null && certificationRecord.getEndCreateTime()!=null){
       title = "实名认证数据统计:"+DateUtils.getDateStr(certificationRecord.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(certificationRecord.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
    }
    String[] headers = new String[] { "商户编码", "商户公司名称", "交易订单号", "认证人姓名", "认证人身份证号", "手续费",
        "认证状态", "创建时间", "失败原因" };

        Page<CertificationRecord> page = new Page<CertificationRecord>(request, response);
        page = certificationRecordService.findPage(page, certificationRecord);
        int pageSize = 500;
        int totalCount = (int)page.getCount();
        int curPage = 1;//从第一页开始
        int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
        List<String[]> contents = new ArrayList<String[]>();
        for(int i=1;i<=totalpage;i++) {
            page.setPageNo(curPage);
            page.setPageSize(pageSize);
            page = certificationRecordService.findPage(page, certificationRecord);
            List<CertificationRecord> certificationRecordList = page.getList();
            for(CertificationRecord record : certificationRecordList){
                String[] content = new String[headers.length];
                content[0] = String.valueOf(record.getMerchantId());
                content[1] = record.getMerchantCompany();
                content[2] = record.getCertificationId();
                content[3] = record.getCertificationName();
                content[4] = record.getCertificationIdcard();
                content[5] = record.getFeeAmount();
                content[6] = DictUtils.getDictLabel(record.getStatus(), "CertificationStatus", "");
                content[7] = sdf.format(record.getCreateTime());
                content[8] = record.getRemark();
                contents.add(content);
            }
            curPage++;
        }

   String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
   ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
    
  }
  
  
  @RequiresPermissions("payment:certificationRecord:view")
  @RequestMapping(value="getStatisticsList")
  @ResponseBody
  public void getStatisticsList(String certificationId, String merchantId, String merchantCompany, String beginCreateTime,
      String endCreateTime, String status, HttpServletResponse response) throws ParseException{
    CertificationRecord certificationRecord = new CertificationRecord();
    if(StringUtils.isNotEmpty(certificationId)){
      certificationRecord.setCertificationId(certificationId);
    }
    if(StringUtils.isNotEmpty(merchantId)){
      certificationRecord.setMerchantId(Long.parseLong(merchantId));
    }
    if(StringUtils.isNotEmpty(merchantCompany)){
      certificationRecord.setMerchantCompany(merchantCompany);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(StringUtils.isNotEmpty(beginCreateTime)){
      certificationRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
    }
    if(StringUtils.isNotEmpty(endCreateTime)){
      certificationRecord.setEndCreateTime(sdf.parse(endCreateTime));
    }
    
    if(StringUtils.isNotEmpty(status) && !StringUtils.equals("R1", status)){
      certificationRecord.setStatus(status);
    }
    //汇总金额
    int successTotalSum = 0;
    int failedTotalSum = 0;
    int errorTotalSum = 0;
    BigDecimal successTotalAmount = BigDecimal.ZERO;
    List<CertificationRecord> certificationRecordList = certificationRecordService.findList(certificationRecord);
    if(certificationRecordList != null && !certificationRecordList.isEmpty()){
      for(CertificationRecord record : certificationRecordList){
        if(StringUtils.equals(record.getStatus(), ChargeRecordStatus.SUCCESS.getValue())){
          successTotalSum++;
          //成功金额
          BigDecimal successAmount = new BigDecimal(StringUtils.isEmpty(record.getFeeAmount())?"0.00":record.getFeeAmount());
          successTotalAmount = successTotalAmount.add(successAmount).setScale(2, RoundingMode.HALF_UP);
          
        }else if(StringUtils.equals(record.getStatus(), CertificationResult.FAILED.getValue())){
          failedTotalSum++;
        }else if(StringUtils.equals(record.getStatus(), CertificationResult.ERRORS.getValue())){
          errorTotalSum++;
        }
      }
    }
    
    DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(); 
    df.applyPattern("￥#,##0.00"); 
    Map<String, String> map = new HashMap<String, String>();
    map.put("successTotalSum", successTotalSum+"笔");
    map.put("failedTotalSum", failedTotalSum+"笔");
    map.put("errorTotalSum", errorTotalSum+"笔");
    map.put("successTotalAmount", df.format(successTotalAmount));
    
    WebUtil.outputJson(map, response);
    
    
  }
  
	
	
}