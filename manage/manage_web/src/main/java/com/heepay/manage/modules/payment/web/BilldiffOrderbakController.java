/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.PaymentRecordStatus;
import com.heepay.enums.ProductType;
import com.heepay.enums.TransType;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.BatchPayRecordDetail;
import com.heepay.manage.modules.payment.entity.BilldiffOrderbak;
import com.heepay.manage.modules.payment.entity.ChargeRecord;
import com.heepay.manage.modules.payment.entity.GatewayRecord;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.entity.RefundRecord;
import com.heepay.manage.modules.payment.entity.WechatRecord;
import com.heepay.manage.modules.payment.entity.WithdrawRecord;
import com.heepay.manage.modules.payment.service.BatchPayRecordDetailService;
import com.heepay.manage.modules.payment.service.BilldiffOrderbakService;
import com.heepay.manage.modules.payment.service.ChargeRecordService;
import com.heepay.manage.modules.payment.service.GatewayRecordService;
import com.heepay.manage.modules.payment.service.PaymentRecordService;
import com.heepay.manage.modules.payment.service.RefundRecordService;
import com.heepay.manage.modules.payment.service.WechatRecordService;
import com.heepay.manage.modules.payment.service.WithdrawRecordService;


/**
 *
 * 描    述：单据备份Controller
 *
 * 创 建 者： @author zc
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
@RequestMapping(value = "${adminPath}/payment/billdiffOrderbak")
public class BilldiffOrderbakController extends BaseController {

	@Autowired
	private BilldiffOrderbakService billdiffOrderbakService;
	@Autowired
	private PaymentRecordService paymentRecordService;
	@Autowired
  private  WechatRecordService wechatRecordService;
	@Autowired
  private  WithdrawRecordService withdrawRecordService;
	@Autowired
  private  ChargeRecordService chargeRecordService;
	@Autowired
  private BatchPayRecordDetailService batchPayRecordDetailService;
	
	@Autowired
	private GatewayRecordService gatewayRecordService;
	
	@Autowired
  private RefundRecordService refundRecordService;
	
	
	@ModelAttribute
	public BilldiffOrderbak get(@RequestParam(required=false) String id) {
		BilldiffOrderbak entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = billdiffOrderbakService.get(id);
		}
		if (entity == null){
			entity = new BilldiffOrderbak();
		}
		return entity;
	}
	

 
  
	@RequiresPermissions("payment:billdiffOrderbak:view")
	@RequestMapping(value = {"list", ""})
	public String list(BilldiffOrderbak billdiffOrderbak, HttpServletRequest request, HttpServletResponse response, Model model) {
	  String qtrans_no = billdiffOrderbak.getQtrans_no();
	  if(!StringUtils.isBlank(qtrans_no)){
	    PaymentRecord  paymentRecord = new PaymentRecord();
	   
	    List<PaymentRecord> findList = paymentRecordService.findListByTransNo(qtrans_no);
	    if(null==findList || findList.isEmpty()){
	      model.addAttribute("info", "没有对应单据");
	    }else{
	      paymentRecord = null;
	      for(PaymentRecord p:findList){
	        if(p.getStatus().equals(PaymentRecordStatus.SUCCESS.getValue())){
	          paymentRecord = p;
	          break;
	        }
	      }
	      if(null==paymentRecord){
	        model.addAttribute("info", "单据状态不是支付成功,无法继续操作");
	      }else{
	        JsonMapperUtil json = new JsonMapperUtil();
	        String content = json.toJson(paymentRecord);
	        String paymentId = paymentRecord.getPaymentId();
	        BilldiffOrderbak billdiff = new BilldiffOrderbak();
	        billdiff.setContent(content);
	        billdiff.setPaymentId(Long.parseLong(paymentId));
	        billdiff.setTransNo(qtrans_no);
	        billdiff.setCreateTime(new Date());
	        
	        String transType=paymentRecord.getTransType();
	        //处理交易
	        if(TransType.PAY.getValue().equals(transType)){
	          int i = paymentRecordService.updatePaymentRecordPaying(paymentId);
	          
	          if(ProductType.XYDXWECHATPAY.getValue().equals(paymentRecord.getProductCode())){
	            i+=wechatRecordService.updatewechatRecordPAYING(qtrans_no);
	            if(i==2){
	              billdiff.setTableName("paymentRecord");
                billdiffOrderbakService.save(billdiff);
                WechatRecord wechatRecord= wechatRecordService.get(qtrans_no);
                content = json.toJson(wechatRecord);
                billdiff.setContent(content);
                billdiff.setTableName("wechatRecord");
                billdiffOrderbakService.save(billdiff);
              }
	          }else{	           
	            i+=gatewayRecordService.updateGatewayRecordPAYING(qtrans_no);
	            if(i==2){
	              billdiff.setTableName("paymentRecord");
	              billdiffOrderbakService.save(billdiff);
	              GatewayRecord gatewayRecord= gatewayRecordService.get(qtrans_no);
	              content = json.toJson(gatewayRecord);
	              billdiff.setTableName("gatewayRecord");
	              billdiff.setContent(content);
	              billdiffOrderbakService.save(billdiff);
	            }
	            
	          }
	        }
	        //处理充值
	        if(TransType.CHARGE.getValue().equals(transType)){
	          int i = paymentRecordService.updatePaymentRecordPaying(paymentId);
	          i+=gatewayRecordService.updateGatewayRecordPAYING(paymentRecord.getTransNo());
	          i+=chargeRecordService.updatechargeRecordCAGING(paymentRecord.getMerchantOrderNo());
	          if(i==3){
              billdiff.setTableName("paymentRecord");
              billdiffOrderbakService.save(billdiff);
              GatewayRecord gatewayRecord= gatewayRecordService.get(paymentRecord.getTransNo());
              content = json.toJson(gatewayRecord);
              billdiff.setTableName("gatewayRecord");
              billdiff.setContent(content);
              billdiffOrderbakService.save(billdiff);
              ChargeRecord chargeRecord = chargeRecordService.get(paymentRecord.getMerchantOrderNo());
              content = json.toJson(chargeRecord);
              billdiff.setTableName("chargeRecord");
              billdiff.setContent(content);
              billdiffOrderbakService.save(billdiff);
            }
          }
	        //处理提现
          if(TransType.WITHDRAW.getValue().equals(transType)){
            int i = paymentRecordService.updatePaymentRecordPaying(paymentId);
            i+= withdrawRecordService.updateWithdrawDRAING(qtrans_no);
            if(i==2){
              billdiff.setTableName("paymentRecord");
              billdiffOrderbakService.save(billdiff);
              WithdrawRecord withdrawRecord = withdrawRecordService.get(qtrans_no);
              content = json.toJson(withdrawRecord);
              billdiff.setTableName("withdrawRecord");
              billdiff.setContent(content);
              billdiffOrderbakService.save(billdiff);
            }
          }
          //处理退款
          if(TransType.REFUND.getValue().equals(transType)){
            int i = paymentRecordService.updatePaymentRecordPaying(paymentId);            
            i+= refundRecordService.updateRefundRecordRFDING(qtrans_no);
            if(i==2){
              billdiff.setTableName("paymentRecord");
              billdiffOrderbakService.save(billdiff);
              RefundRecord refundRecord = refundRecordService.get(qtrans_no);
              content = json.toJson(refundRecord);
              billdiff.setTableName("refundRecord");
              billdiff.setContent(content);
              billdiffOrderbakService.save(billdiff);
            }
          }
          //处理转账
          if(TransType.BATCHPAY.getValue().equals(transType)){
            int i = paymentRecordService.updatePaymentRecordPaying(paymentId);            
            i+= batchPayRecordDetailService.updateBatchPayRecordPROCES(qtrans_no);
            if(i==2){
              billdiff.setTableName("paymentRecord");
              billdiffOrderbakService.save(billdiff);
              BatchPayRecordDetail batchPayRecordDetail = batchPayRecordDetailService.get(qtrans_no);
              content = json.toJson(batchPayRecordDetail);
              billdiff.setTableName("batchPayRecordDetail");
              billdiff.setContent(content);
              billdiffOrderbakService.save(billdiff);
            }
          }
	        
	      }
	    }
	  }
		Page<BilldiffOrderbak> page = billdiffOrderbakService.findPage(new Page<BilldiffOrderbak>(request, response), billdiffOrderbak); 
		model.addAttribute("page", page);
		return "modules/payment/billdiffOrderbakList";
	}

	@RequiresPermissions("payment:billdiffOrderbak:view")
	@RequestMapping(value = "form")
	public String form(BilldiffOrderbak billdiffOrderbak, Model model) {
		model.addAttribute("billdiffOrderbak", billdiffOrderbak);
		return "modules/payment/billdiffOrderbakForm";
	}

	@RequiresPermissions("payment:billdiffOrderbak:edit")
	@RequestMapping(value = "save")
	public String save(BilldiffOrderbak billdiffOrderbak, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, billdiffOrderbak)){
			return form(billdiffOrderbak, model);
		}
		billdiffOrderbakService.save(billdiffOrderbak);
		addMessage(redirectAttributes, "保存单据备份成功");
		return "redirect:"+Global.getAdminPath()+"/payment/billdiffOrderbak/?repage";
	}
	
	
}