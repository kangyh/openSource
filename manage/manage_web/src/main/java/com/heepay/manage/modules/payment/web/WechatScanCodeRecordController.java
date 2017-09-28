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

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.ProductType;
import com.heepay.enums.RefundStatus;
import com.heepay.enums.RefundType;
import com.heepay.enums.WeChatRecordStatus;
import com.heepay.enums.WeChatTradeType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.RefundRecord;
import com.heepay.manage.modules.payment.entity.WechatRecord;
import com.heepay.manage.modules.payment.service.WechatRecordService;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.util.ExcelUtil2007;
import com.heepay.payment.enums.AggregatePayType;
import com.heepay.payment.enums.WechatTradeType;


/**
 *
 * 描    述：微信支付订单Controller
 *
 * 创 建 者： @author 张晨
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
@RequestMapping(value = "${adminPath}/wechatScanCode/wechatScanCodeRecord")
public class WechatScanCodeRecordController extends BaseController {

	@Autowired
	private WechatRecordService wechatRecordService;
	
	@ModelAttribute
	public WechatRecord get(@RequestParam(required=false) String id) {
		WechatRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wechatRecordService.get(id);
		}
		if (entity == null){
			entity = new WechatRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("wechatScanCode:wechatScanCodeRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(WechatRecord wechatRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
	  if(StringUtils.isEmpty(wechatRecord.getGroupType())){
	    wechatRecord.setGroupType("1");
    }
	  Page<WechatRecord> page = wechatRecordService.findPage(new Page<WechatRecord>(request, response), wechatRecord); 
	  model.addAttribute("groupType", wechatRecord.getGroupType());
	  model.addAttribute("page", page);
	  model.addAttribute("beginCreateTime", wechatRecord.getBeginCreateTime());
    model.addAttribute("endCreateTime", wechatRecord.getEndCreateTime());
		return "modules/payment/wechatScanCodeRecordList";
	}

	@RequiresPermissions("wechatScanCode:wechatScanCodeRecord:view")
	@RequestMapping(value = "form")
	public String form(WechatRecord wechatRecord, Model model) {
		model.addAttribute("wechatRecord", wechatRecord);
		return "modules/payment/wechatRecordForm";
	}

	@RequiresPermissions("wechatScanCode:wechatScanCodeRecord:edit")
	@RequestMapping(value = "save")
	public String save(WechatRecord wechatRecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, wechatRecord)){
			return form(wechatRecord, model);
		}
		wechatRecordService.save(wechatRecord);
		addMessage(redirectAttributes, "保存微信支付订单成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/wechatRecord/?repage";
	}
	
	@RequiresPermissions("wechatScanCode:wechatScanCodeRecord:edit")
	@RequestMapping(value = "delete")
	public String delete(WechatRecord wechatRecord, RedirectAttributes redirectAttributes) {
		wechatRecordService.delete(wechatRecord);
		addMessage(redirectAttributes, "删除微信支付订单成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/wechatRecord/?repage";
	}
	
	
	
	@RequiresPermissions("wechatScanCode:wechatScanCodeRecord:view")
  @RequestMapping(value = "exportExcel")
  public void exportExcel(String wechatId, String merchantId, String outTradeNo, String tradeType, String beginCreateTime,
      String endCreateTime, String passTradeNo, String status, String groupType, HttpServletResponse response, HttpServletRequest request) throws Exception{
	  WechatRecord wechatRecord = new WechatRecord();
    if(StringUtils.isNotEmpty(wechatId)){
      wechatRecord.setWechatId(wechatId);
    }
    if(StringUtils.isNotEmpty(merchantId)){
      wechatRecord.setMerchantId(Long.parseLong(merchantId));
    }
    if(StringUtils.isNotEmpty(outTradeNo)){
      wechatRecord.setOutTradeNo(outTradeNo);
    }
    if(StringUtils.isNotEmpty(tradeType)){
      wechatRecord.setTradeType(tradeType);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(StringUtils.isNotEmpty(beginCreateTime)){
      wechatRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
    }
    if(StringUtils.isNotEmpty(endCreateTime)){
      wechatRecord.setEndCreateTime(sdf.parse(endCreateTime));
    }
    
    if(StringUtils.isNotEmpty(passTradeNo)){
      wechatRecord.setPassTradeNo(passTradeNo);
    }
    
    if(StringUtils.isNotEmpty(status)){
      wechatRecord.setStatus(status);
    }
    
    wechatRecord.setGroupType(groupType);
    
    String title = "聚合支付数据统计:";
    if(wechatRecord.getBeginCreateTime()!=null && wechatRecord.getEndCreateTime()!=null){
       title = "聚合支付数据统计:"+DateUtils.getDateStr(wechatRecord.getBeginCreateTime(), DateUtils.DATE_FORMAT_DATE)+"---"+DateUtils.getDateStr(wechatRecord.getEndCreateTime(), DateUtils.DATE_FORMAT_DATE);
    }
    String[] headers = new String[] { "交易订单号", "商户编码", "  交易时间", "商户订单号", "货币类型",
        "总金额", "交易类型", "成功交易金额", "成功交易时间", "交易状态", "手续费金额", "通道订单号", "通道代码", "结算类型"};


        Page<WechatRecord> page = new Page<WechatRecord>(request, response);
        page = wechatRecordService.findPage(page, wechatRecord);
        int pageSize = 500;
        int totalCount = (int)page.getCount();
        int curPage = 1;//从第一页开始
        int totalpage = totalCount/pageSize + ((totalCount % pageSize) > 0 ? 1 : 0);
        List<String[]> contents = new ArrayList<String[]>();
        for(int i=1;i<=totalpage;i++) {
            page.setPageNo(curPage);
            page.setPageSize(pageSize);
            page = wechatRecordService.findPage(page, wechatRecord);
            List<WechatRecord> wechatRecordList = page.getList();
            for(WechatRecord record : wechatRecordList){
                String[] content = new String[headers.length];
                content[0] = record.getWechatId();
                content[1] = String.valueOf(record.getMerchantId());
                content[2] = sdf.format(record.getCreateTime());
                content[3] = record.getOutTradeNo();
                content[4] = record.getCurrency();
                content[5] = record.getTotalFee();
                if(StringUtils.equals(record.getTradeType(), "weixin_qr")){
                    content[6] = "微信扫码支付";
                }else if(StringUtils.equals(record.getTradeType(), "weixin_pub")){
                    content[6] = "微信公众号支付";
                }else if(StringUtils.equals(record.getTradeType(), "weixin_h5")){
                    content[6] = "微信H5支付";
                }else if(StringUtils.equals(record.getTradeType(), "alipay_qr")){
                    content[6] = "支付宝扫码支付";
                }else if(StringUtils.equals(record.getTradeType(), "alipay_wap")){
                    content[6] = "支付宝WAP支付";
                }
                content[7] = record.getSuccessAmount();
                content[8] = sdf.format(record.getSuccessTime());
                content[9] = WeChatRecordStatus.getContentByValue(record.getStatus());
                content[10] = record.getFeeAmount();
                content[11] = record.getPassTradeNo();
                content[12] = record.getChannelCode();
                if(StringUtils.equals(record.getSettleCyc(), "0")){
                    content[13] = "T+0结算";
                }else if(StringUtils.equals(record.getSettleCyc(), "1")){
                    content[13] = "T+1结算";
                }else{
                    content[13] = "T+X结算";
                }
                contents.add(content);
            }
            curPage++;
        }

   String fileName = title.concat(DateUtil.dateToString(new Date(), DateUtil.TIME_FORMAT));
   ExcelUtil2007.exportExcel(title, fileName, "sheet1", headers, response, contents);
    
  }
  
  
  @RequiresPermissions("wechatScanCode:wechatScanCodeRecord:view")
  @RequestMapping(value="getStatisticsList")
  @ResponseBody
  public void getStatisticsList(String wechatId, String merchantId, String outTradeNo, String tradeType, String beginCreateTime,
      String endCreateTime, String passTradeNo, String status, String groupType, HttpServletResponse response) throws ParseException{
    WechatRecord wechatRecord = new WechatRecord();
    if(StringUtils.isNotEmpty(wechatId)){
      wechatRecord.setWechatId(wechatId);
    }
    if(StringUtils.isNotEmpty(merchantId)){
      wechatRecord.setMerchantId(Long.parseLong(merchantId));
    }
    if(StringUtils.isNotEmpty(outTradeNo)){
      wechatRecord.setOutTradeNo(outTradeNo);
    }
    if(StringUtils.isNotEmpty(tradeType)){
      wechatRecord.setTradeType(tradeType);
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if(StringUtils.isNotEmpty(beginCreateTime)){
      wechatRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
    }
    if(StringUtils.isNotEmpty(endCreateTime)){
      wechatRecord.setEndCreateTime(sdf.parse(endCreateTime));
    }
    
    if(StringUtils.isNotEmpty(passTradeNo)){
      wechatRecord.setPassTradeNo(passTradeNo);
    }
    
    if(StringUtils.isNotEmpty(status)){
      wechatRecord.setStatus(status);
    }
    
    wechatRecord.setGroupType(groupType);
    
    //汇总金额
    int successTotalSum = 0;
    BigDecimal successTotalAmount = BigDecimal.ZERO;
    List<WechatRecord> wechatRecordList = wechatRecordService.findList(wechatRecord);
    if(wechatRecordList != null && !wechatRecordList.isEmpty()){
      for(WechatRecord record : wechatRecordList){
        if(StringUtils.equals(record.getStatus(), WeChatRecordStatus.SUCCESS.getValue())){
          //成功金额
          BigDecimal successAmount = new BigDecimal(StringUtils.isEmpty(record.getSuccessAmount())?"0.00":record.getSuccessAmount());
          successTotalAmount = successTotalAmount.add(successAmount).setScale(2, RoundingMode.HALF_UP);
        }
      }
    }
    
    DecimalFormat df = (DecimalFormat)NumberFormat.getInstance(); 
    df.applyPattern("￥#,##0.00"); 
    Map<String, String> map = new HashMap<String, String>();
    map.put("successTotalSum", successTotalSum + "笔");
    map.put("successTotalAmount", df.format(successTotalAmount));
    WebUtil.outputJson(map, response);
    
    
  }
	
	

}