/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.ChargeRecordStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.BatchCollectionRecordDetail;
import com.heepay.manage.modules.payment.service.BatchCollectionRecordDetailService;
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
 * 描    述：批量代收Controller
 *
 * 创 建 者： @author 杨春龙
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
@RequestMapping(value = "${adminPath}/payment/batchCollectionRecordDetail")
public class BatchCollectionRecordDetailController extends BaseController {

	@Autowired
	private BatchCollectionRecordDetailService batchCollectionRecordDetailService;
	
	@ModelAttribute
	public BatchCollectionRecordDetail get(@RequestParam(required=false) String id) {
		BatchCollectionRecordDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = batchCollectionRecordDetailService.get(id);
		}
		if (entity == null){
			entity = new BatchCollectionRecordDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("payment:batchCollectionRecordDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(BatchCollectionRecordDetail batchCollectionRecordDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		if("R1".equals(batchCollectionRecordDetail.getStatus())){
			batchCollectionRecordDetail.setStatus(null);
		}

        if(StringUtils.isEmpty(batchCollectionRecordDetail.getGroupType())){
            batchCollectionRecordDetail.setGroupType("1");
        }
        model.addAttribute("groupType", batchCollectionRecordDetail.getGroupType());
		Page<BatchCollectionRecordDetail> page = batchCollectionRecordDetailService.findPage(new Page<BatchCollectionRecordDetail>(request, response), batchCollectionRecordDetail); 
		
		List<BatchCollectionRecordDetail> list=page.getList();
		
/*		for(BatchCollectionRecordDetail detail:list){
			
			String cardNo=Aes.decryptStr(detail.getBankcardNo(),Constants.QuickPay.SYSTEM_KEY);
			cardNo=cardNo.substring(0, 6)+"******"+cardNo.substring(cardNo.length()-4);
			detail.setBankcardNo(cardNo);
			
			String idcard=Aes.decryptStr(detail.getBankcardOwnerIdcard(),Constants.QuickPay.SYSTEM_KEY);
			idcard=idcard.substring(0, 3)+"******"+idcard.substring(idcard.length()-4);
			detail.setBankcardOwnerIdcard(idcard);
			
			String mobile=Aes.decryptStr(detail.getBankcardOwnerMobile(),Constants.QuickPay.SYSTEM_KEY);
			mobile=mobile.substring(0, 3)+"******"+mobile.substring(mobile.length()-4);
			
			detail.setBankcardOwnerMobile(mobile);
		}*/
		
		model.addAttribute("page", page);
		return "modules/payment/batchCollectionRecordDetailList";
	}

	@RequiresPermissions("payment:batchCollectionRecordDetail:view")
	@RequestMapping(value = "form")
	public String form(BatchCollectionRecordDetail batchCollectionRecordDetail, Model model) {
		model.addAttribute("batchCollectionRecordDetail", batchCollectionRecordDetail);
		return "modules/payment/batchCollectionRecordDetailForm";
	}

	@RequiresPermissions("payment:batchCollectionRecordDetail:edit")
	@RequestMapping(value = "save")
	public String save(BatchCollectionRecordDetail batchCollectionRecordDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, batchCollectionRecordDetail)){
			return form(batchCollectionRecordDetail, model);
		}
		batchCollectionRecordDetailService.save(batchCollectionRecordDetail);
		addMessage(redirectAttributes, "保存批量代收成功");
		return "redirect:"+Global.getAdminPath()+"/payment/batchCollectionRecordDetail/?repage";
	}
	
	@RequiresPermissions("payment:batchCollectionRecordDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(BatchCollectionRecordDetail batchCollectionRecordDetail, RedirectAttributes redirectAttributes) {
		batchCollectionRecordDetailService.delete(batchCollectionRecordDetail);
		addMessage(redirectAttributes, "删除批量代收成功");
		return "redirect:"+Global.getAdminPath()+"/payment/batchCollectionRecordDetail/?repage";
	}
	
	@RequiresPermissions("payment:batchCollectionRecordDetail:view")
  @RequestMapping(value = "exportExcel")
  public void exportExcel(String merchantId, String merchantUserId, String bankcardOwnerMobile, String bankcardOwnerName, String bankcardOwnerIdcard,
      String merchantBatchNo, String merchantOrderNo, String status, String groupType, HttpServletResponse response, HttpServletRequest request) throws Exception{
	  BatchCollectionRecordDetail batchCollectionRecordDetail = new BatchCollectionRecordDetail();
    if(StringUtils.isNotEmpty(merchantId)){
      batchCollectionRecordDetail.setMerchantId(Long.parseLong(merchantId));
    }
    if(StringUtils.isNotEmpty(merchantUserId)){
      batchCollectionRecordDetail.setMerchantUserId(merchantUserId);
    }
    if(StringUtils.isNotEmpty(bankcardOwnerMobile)){
      batchCollectionRecordDetail.setBankcardOwnerMobile(bankcardOwnerMobile);
    }
    if(StringUtils.isNotEmpty(bankcardOwnerName)){
      batchCollectionRecordDetail.setBankcardOwnerName(bankcardOwnerName);
    }
    if(StringUtils.isNotEmpty(bankcardOwnerIdcard)){
      batchCollectionRecordDetail.setBankcardOwnerIdcard(bankcardOwnerIdcard);
    }
    if(StringUtils.isNotEmpty(merchantBatchNo)){
      batchCollectionRecordDetail.setMerchantBatchNo(merchantBatchNo);
    }
    if(StringUtils.isNotEmpty(status) && !StringUtils.equals("R1", status)){
      batchCollectionRecordDetail.setStatus(status);
    }
      batchCollectionRecordDetail.setGroupType(groupType);
    String title = "代收管理数据统计:";
    if(batchCollectionRecordDetail.getBeginCreateTime()!=null && batchCollectionRecordDetail.getEndCreateTime()!=null){
       title = "代收管理数据统计:"+DateUtils.getDateStr(batchCollectionRecordDetail.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(batchCollectionRecordDetail.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
    }
    String[] headers = new String[] { "代收明细ID", "商户编码", "用户编码", "申请时间", "修改时间", "收款金额", "银行卡号", "银行预留手机号", "持卡人姓名",
        "持卡人身份证号", "银行名称", "银行号", "商户批次号", "商户订单号", "状态", "成功时间", "成功金额",  "手续费金额" };
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Page<BatchCollectionRecordDetail> page = new Page<BatchCollectionRecordDetail>(request, response);
        page = batchCollectionRecordDetailService.findPage(page, batchCollectionRecordDetail);
        int pageSize = 500;
        int totalCount = (int)page.getCount();
        int curPage = 1;//从第一页开始
        int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
        List<String[]> contents = new ArrayList<String[]>();
        for(int i=1;i<=totalpage;i++) {
            page.setPageNo(curPage);
            page.setPageSize(pageSize);
            page = batchCollectionRecordDetailService.findPage(page, batchCollectionRecordDetail);
            List<BatchCollectionRecordDetail> batchCollectionRecordDetailList = page.getList();
            for(BatchCollectionRecordDetail record : batchCollectionRecordDetailList){
                String[] content = new String[headers.length];
                content[0] = record.getCollectId();
                content[1] = String.valueOf(record.getMerchantId());
                content[2] = record.getMerchantUserId();
                content[3] = sdf.format(record.getCreateTime());
                content[4] = sdf.format(record.getUpdateTime());
                content[5] = record.getCollectAmount();
                content[6] = record.getBankcardNo();
                content[7] = record.getBankcardOwnerMobile();
                content[8] = record.getBankcardOwnerName();
                content[9] = record.getBankcardOwnerIdcard();
                content[10] = record.getBankName();
                content[11] = record.getBankId();
                content[12] = record.getMerchantBatchNo();
                content[13] = record.getMerchantOrderNo();
                content[14] = DictUtils.getDictLabel(record.getStatus(), "PaymentRecordStatus", "");
                content[15] = record.getSuccessTime()==null?"-":sdf.format(record.getSuccessTime());
                content[16] = record.getSuccessAmount();
                content[17] = record.getFeeAmount();
                contents.add(content);
            }
            curPage++;
        }

   String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
   ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
    
  }
  
  
  @RequiresPermissions("payment:batchCollectionRecordDetail:view")
  @RequestMapping(value="getStatisticsList")
  @ResponseBody
  public void getStatisticsList(String merchantId, String merchantUserId, String bankcardOwnerMobile, String bankcardOwnerName, String bankcardOwnerIdcard,
      String merchantBatchNo, String merchantOrderNo, String status, String groupType, HttpServletResponse response) throws ParseException{
    BatchCollectionRecordDetail batchCollectionRecordDetail = new BatchCollectionRecordDetail();
    if(StringUtils.isNotEmpty(merchantId)){
      batchCollectionRecordDetail.setMerchantId(Long.parseLong(merchantId));
    }
    if(StringUtils.isNotEmpty(merchantUserId)){
      batchCollectionRecordDetail.setMerchantUserId(merchantUserId);
    }
    if(StringUtils.isNotEmpty(bankcardOwnerMobile)){
      batchCollectionRecordDetail.setBankcardOwnerMobile(bankcardOwnerMobile);
    }
    if(StringUtils.isNotEmpty(bankcardOwnerName)){
      batchCollectionRecordDetail.setBankcardOwnerName(bankcardOwnerName);
    }
    if(StringUtils.isNotEmpty(bankcardOwnerIdcard)){
      batchCollectionRecordDetail.setBankcardOwnerIdcard(bankcardOwnerIdcard);
    }
    if(StringUtils.isNotEmpty(merchantBatchNo)){
      batchCollectionRecordDetail.setMerchantBatchNo(merchantBatchNo);
    }
    if(StringUtils.isNotEmpty(status) && !StringUtils.equals("R1", status)){
      batchCollectionRecordDetail.setStatus(status);
    }
      batchCollectionRecordDetail.setGroupType(groupType);
    
    //汇总金额
    BigDecimal collectTotalAmount = BigDecimal.ZERO;
    BigDecimal successTotalAmount = BigDecimal.ZERO;
    BigDecimal feeTotalAmount = BigDecimal.ZERO;
    BigDecimal failedTotalAmount = BigDecimal.ZERO;
    List<BatchCollectionRecordDetail> batchCollectionRecordDetailList = batchCollectionRecordDetailService.findList(batchCollectionRecordDetail);
    if(batchCollectionRecordDetailList != null && !batchCollectionRecordDetailList.isEmpty()){
      for(BatchCollectionRecordDetail record : batchCollectionRecordDetailList){
        BigDecimal collectAmount = new BigDecimal(StringUtils.isEmpty(record.getCollectAmount())?"0.00":record.getCollectAmount());
        collectTotalAmount = collectTotalAmount.add(collectAmount).setScale(2, RoundingMode.HALF_UP);
        if(StringUtils.equals(record.getStatus(), ChargeRecordStatus.SUCCESS.getValue())){
          //成功金额
          BigDecimal successAmount = new BigDecimal(StringUtils.isEmpty(record.getSuccessAmount())?"0.00":record.getSuccessAmount());
          successTotalAmount = successTotalAmount.add(successAmount).setScale(2, RoundingMode.HALF_UP);
          //手续费
          BigDecimal feeAmount = new BigDecimal(StringUtils.isEmpty(record.getFeeAmount())?"0.00":record.getFeeAmount());
          feeTotalAmount = feeTotalAmount.add(feeAmount).setScale(2, RoundingMode.HALF_UP);
          
        }else if(StringUtils.equals(record.getStatus(), ChargeRecordStatus.FAILED.getValue())){
          //失败金额
          BigDecimal failedAmount = new BigDecimal(StringUtils.isEmpty(record.getCollectAmount())?"0.00":record.getCollectAmount());
          failedTotalAmount = failedTotalAmount.add(failedAmount).setScale(2, RoundingMode.HALF_UP);
        }
        
        
      }
    }
    
    DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(); 
    df.applyPattern("￥#,##0.00"); 
    Map<String, String> map = new HashMap<String, String>();
    map.put("collectTotalAmount", df.format(collectTotalAmount));
    map.put("successTotalAmount", df.format(successTotalAmount));
    map.put("feeTotalAmount", df.format(feeTotalAmount));
    map.put("failedTotalAmount", df.format(failedTotalAmount));
    
    WebUtil.outputJson(map, response);
    
    
  }
	
	
}