/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.heepay.billingutils.payment.annotation.ParamConfig;
import com.heepay.codec.Md5;
import com.heepay.common.util.Constants;
import com.heepay.common.util.HttpClientUtils;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.Reflections;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.NotifyRecordStatus;
import com.heepay.enums.ProductType;
import com.heepay.manage.common.cache.PrimaryKeyCreator;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.dao.MerchantProductRateDao;
import com.heepay.manage.modules.merchant.vo.MerchantProductRate;
import com.heepay.manage.modules.payment.entity.NotifyRecord;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.param.BaseParam;
import com.heepay.manage.modules.payment.param.QuicPayResult;
import com.heepay.manage.modules.payment.service.NotifyRecordService;
import com.heepay.manage.modules.payment.service.PaymentRecordService;
import com.heepay.vo.MqBodyVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 *
 * 描    述：异步通知Controller
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
@RequestMapping(value = "${adminPath}/payment/notifyRecord")
public class NotifyRecordController extends BaseController {
  @Autowired
  private PaymentRecordService paymentRecordService;

	@Autowired
	private NotifyRecordService notifyRecordService;
	@Autowired
	private MerchantProductRateDao merchantProductRateDao;

	 
	 
	private static Logger log = LogManager.getLogger();
	@ModelAttribute
	public NotifyRecord get(@RequestParam(required=false) String id) {
		NotifyRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = notifyRecordService.get(id);
		}
		if (entity == null){
			entity = new NotifyRecord();
		}
		return entity;
	}
	 /***
   * 
  * @discription 获取签名前的签名串方法 不含KEY  
  * @author Administrator       
  * @created 2017年1月4日 下午4:02:21     
  * @param param
  * @return
  * @throws Exception
   */
  public static String getSignStr(Object param) throws Exception {
    List<String> list = new ArrayList<String>();

    //签名摘要串，不包含商家key
    String sign = "";
    Field[] fields;

    if(BaseParam.class.getName().equals(param.getClass().getName())
      || QuicPayResult.class.getName().equals(param.getClass().getName())){
      fields = param.getClass().getDeclaredFields();        
    }else{
      fields = param.getClass().getSuperclass().getDeclaredFields();        
    }

    for (Field field : fields) {
      ParamConfig paramAnno = field.getAnnotation(ParamConfig.class);
      String temp = (String)Reflections.invokeGetter(param,field.getName());
      if (paramAnno != null && paramAnno.isSign()){
        list.add(field.getName() + "=" + temp + "&");
      }
    }
    Collections.sort(list);
    for (String signParam : list) {
      sign += signParam;
    }
    return sign;
  }
	 public static String getSign(String key,Object param) throws Exception {
	    String signStr=getSignStr(param);
	    logger.info("签名的字符串 signStr={}", signStr, key);
	      String sign = Md5.encode(signStr+"key="+key);
	    return sign;
	  }
	  public static String getResultSign(String key,Map<String,String> map) throws Exception {

	      QuicPayResult result=new QuicPayResult();
	      result.setMerchantId(map.get("merchantId"));
	      result.setMerchantOrderNo(map.get("merchantOrderNo"));
	      result.setPayAmount(map.get("payAmount"));
	      result.setResult(map.get("result"));
	      result.setSuccessAmount(map.get("successAmount"));
	      result.setTransNo(map.get("transNo"));
	      result.setVersion(map.get("version"));
	    String signStr=getSignStr(result);
	    logger.info("签名的字符串 signStr={}", signStr, key);
	      String sign = Md5.encode(signStr+"key="+key);     
	    return sign;
	  }
	
	public  String getProductKey(String merchantId, String productCode) throws TException {
    switch (productCode) {
    case "CP01":// 充值
        return Constants.RedisKey.CHARGE_PRODUCT_KEY + merchantId;
    case "CP04":// 退款
        return Constants.RedisKey.REFUND_PRODUCT_KEY + merchantId;
    case "CP05":// 提现
        return Constants.RedisKey.WITHDRAW_PRODUCT_KEY + merchantId;
    default:
        MerchantProductRate merchantProductRate = new MerchantProductRate();
        merchantProductRate.setMerchantId(merchantId);
        merchantProductRate.setProductCode(productCode);
        merchantProductRate = merchantProductRateDao.exist(merchantProductRate);
        if (null != merchantProductRate) {
            return merchantProductRate.getAutographKey();
        }
        return "";
    }
}
	
	
	@RequiresPermissions("payment:notifyRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(NotifyRecord notifyRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/payment/notifyRecordList";
	}

	@RequiresPermissions("payment:notifyRecord:view")
	@RequestMapping(value = "form")
	public String form(NotifyRecord notifyRecord, HttpServletRequest request,  Model model,HttpServletResponse response) {
		if("R1".equals(notifyRecord.getStatus())){
			notifyRecord.setStatus(null);
		}
		
	  Page<NotifyRecord> page = notifyRecordService.findPage(new Page<NotifyRecord>(request, response), notifyRecord); 
    
    model.addAttribute("page", page);
		return "modules/payment/notifyRecordList2";
	}
	
	
	@RequiresPermissions("payment:notifyRecord:add")
  @RequestMapping(value = "add")
  public String add(NotifyRecord notifyRecord,  HttpServletRequest request, HttpServletResponse response,Model model) {
	  
    return "modules/payment/notifyRecordadd";
  }
	@RequestMapping(value = "adddo")
  public void adddo(NotifyRecord notifyRecord,HttpServletRequest request, HttpServletResponse response) throws TException {
	  Page<NotifyRecord> page = notifyRecordService.findPage(new Page<NotifyRecord>(request, response), notifyRecord); 
    //如果是根据单据号查询，并且没有找到数据，则要查询一下paymentRecord表，根据状态往NotifyRecord补一条记录
    if(!StringUtil.isBlank(notifyRecord.getTransNo()) && page.getList().size()==0){    
      PaymentRecord paymentRecord = paymentRecordService.getOneByGatewayId(notifyRecord.getTransNo());      
      if(null!=paymentRecord){        
        NotifyRecord ob = new NotifyRecord();
        ob.setMerchantCompany(paymentRecord.getMerchantCompany());
        ob.setMerchantId(paymentRecord.getMerchantId());
        ob.setTransNo(paymentRecord.getTransNo());
        ob.setMerchantOrderNo(paymentRecord.getMerchantOrderNo());
        ob.setSuccessAmount(paymentRecord.getSuccessAmount());
        ob.setPayAmount(paymentRecord.getPayAmount());
        ob.setNotifyUrl(paymentRecord.getNotifyUrl());
        ob.setPayResult("1000");
        ob.setPaymentId(paymentRecord.getPaymentId());
        ob.setNotifyNumber(1);
        ob.setNotifyType("1");
        ob.setNotifyId(PrimaryKeyCreator.getNotifyId());
        ob.setProductCode(paymentRecord.getProductCode());
        ob.setMerchantLoginName(paymentRecord.getMerchantLoginName());
        ob.setBankSerialNo(paymentRecord.getBankSerialNo());
        ob.setUpdateTime(new Date());
        ob.setCreateTime(new Date());
        ob.setNotifyTime(new Date());
        
        JsonMapperUtil json = new JsonMapperUtil();
        Map<String,String> map = new HashMap<String,String>();
        map.put("successAmount", ob.getSuccessAmount());
        map.put("payAmount", ob.getPayAmount());
        map.put("transNo", ob.getTransNo());
        map.put("result", String.valueOf(ob.getPayResult()));
        map.put("notifyUrl", ob.getNotifyUrl());
        map.put("merchantId", String.valueOf(ob.getMerchantId()));
        map.put("merchantOrderNo", ob.getMerchantOrderNo());
        map.put("version", "1.0");
        
        String sign="";
        String pKey ;
        try {
          pKey = getProductKey(String.valueOf(paymentRecord.getMerchantId()),paymentRecord.getProductCode());
          sign = getResultSign(pKey, map);
          map.put("sign", sign);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
        ob.setNotifyRequestParams(json.toJson(map));
        map.remove("notifyUrl");      
        logger.info("{}异步通知接口请求商户参数：{},{}",ob.getTransNo(),ob.getNotifyUrl(),ob.getNotifyRequestParams());
        String rs = HttpClientUtils.requestByPostMethodReturnBody(ob.getNotifyUrl(), map);
        logger.info("{}异步通知接口商户返回：{},sign={}",ob.getTransNo(),rs,sign);
        ob.setNotifyResponse(rs);
        if(Constants.Http.RESPONSE_OK.equals(rs)){          
          ob.setStatus(NotifyRecordStatus.SUCCESS.getValue());
        }else{
          ob.setStatus(NotifyRecordStatus.FAILED.getValue());
        }
        notifyRecordService.save(ob);
      }else{
        WebUtil.output("PaymentRecord通知记录不存在",response,"");
        return;
      }
   }else{
     WebUtil.output("通知记录已存在",response,"");
     return;
   }
	  
	  WebUtil.output("成功",response,"");
    return;
	}
	@RequestMapping(value = "send")
  public void send(HttpServletRequest request, HttpServletResponse response) throws TException {
    String notifyIds = request.getParameter("notifyId");
    JSONArray jsonArray = JSONArray.fromObject(notifyIds);
    Iterator<Object> it = jsonArray.iterator();
    
    int i=0;
    while (it.hasNext()) {
    	 JSONObject obj = (JSONObject) it.next();
    	 String notifyId = obj.getString("notifyId");
	    NotifyRecord notifyRecord = notifyRecordService.get(notifyId);
	    if(null==notifyRecord){
	      log.error("异常处理,参数不完整：notifyId={}",notifyId);
	      String errorMessage = InterfaceStatus.PARAMSERROR.getContent();
	      WebUtil.outputJson("{\"result\":\""+errorMessage+"\"}",response);
	      return;
	    }
    
    String receiveMsg = notifyRecord.getNotifyRequestParams();
    JSONObject myJsonObject = JSONObject.fromObject(receiveMsg);
    logger.info("开始处理结果:{}",myJsonObject);
    JsonMapperUtil jsonMapper=new JsonMapperUtil();
    MqBodyVO mqBodyVO = jsonMapper.fromJson(myJsonObject.toString(), MqBodyVO.class);  
    if(ProductType.XYDXWECHATPAY.getValue().equals(notifyRecord.getProductCode())
        || ProductType.XYDXWECHATPAYTX.getValue().equals(notifyRecord.getProductCode())){
      logger.info("开始微信业务处理");
      String url = notifyRecord.getNotifyUrl();
      String xmlString = notifyRecord.getNotifyRequestParams();
      logger.info("后台补发异步通知接口请求微信商户参数：{},{}",url,xmlString);
      String returns = HttpClientUtils.postWeChatXml(url, xmlString);
      logger.info("后台补发通知接口请求微信商户返回：{}",returns);
      if(null!=returns && returns.indexOf(NotifyRecordStatus.SUCCESS.getValue())>0){
        notifyRecord.setStatus(NotifyRecordStatus.SUCCESS.getValue());
      }else{
        notifyRecord.setStatus(NotifyRecordStatus.FAILED.getValue());
      }
      notifyRecord.setNotifyResponse(returns);
    } else {
        logger.info("开始补发json格式异步通知");
        Map<String, String> paramMap = new HashMap<>();
        Set<String> keys = myJsonObject.keySet();
        for(String key : keys){
            paramMap.put(key, myJsonObject.getString(key));
        }
        logger.info("后台补发异步通知接口请求商户参数：{},{}",notifyRecord.getNotifyUrl(),paramMap);
        String rs = HttpClientUtils.requestByPostMethodReturnBody(
                notifyRecord.getNotifyUrl(), paramMap);
        try {
			rs= URLEncoder.encode(rs,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        logger.info("后台补发异步通知接口请求商户处理返回{}",rs);
        if (Constants.Http.RESPONSE_OK.equals(rs)) {
        	i++;
            notifyRecord.setStatus(NotifyRecordStatus.SUCCESS.getValue());
        } else {
            notifyRecord.setStatus(NotifyRecordStatus.FAILED.getValue());
        }
        notifyRecord.setNotifyResponse(rs);

    }
    notifyRecord.setUpdateTime(new Date());
    notifyRecord.setNotifyType("1");
    notifyRecord.setNotifyNumber(notifyRecord.getNotifyNumber()+1);
    notifyRecordService.updateStatusById(notifyRecord);
    }
    
    
    WebUtil.outputJson("补发通知总数"+jsonArray.size()+"条，成功"+i+"条",response);
    return;
  }
}