package com.heepay.tpds.resource;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.springframework.beans.factory.annotation.Autowired;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.date.DateUtils;
import com.heepay.rpc.client.B2CAsynNoticeClient;
import com.heepay.tpds.vo.ResponseMessage;

/**
 * 
 *
 * 描    述：异步通知接口
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年2月18日 上午10:50:25
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
public class B2CAsynNoticeResource extends BaseServerResource {
	
	private static final Logger log = LogManager.getLogger();
	
	@Autowired
	private B2CAsynNoticeClient b2CAsynNoticeClient;
	
	@Post
	public Representation asynNotice(Representation entity) throws IOException {
		
		log.info("restlet_api{}", "调用B2C网银异步通知接口");
		String routeName = getRequestAttributes().get("routeName").toString();
		JSONObject error = new JSONObject();
		String chargeJSON = null;
		JSONObject resultReqHeader = null;
		log.info("显示【异步通知】调用的路径：method:{},request_entity:{}",routeName,entity);
		
		if(routeName.toUpperCase().equals("CUSTOMERCHARGE")){
			log.info("客户充值异步通知组装报文");
			Map<String,String> mapBody = new HashMap<String,String>();
			Map<String,String> mapHeader = new HashMap<String,String>();
			Form form = new Form(entity);
			mapBody.put("successAmount", form.getFirstValue("successAmount"));//支付金额
			mapBody.put("payAmount", form.getFirstValue("payAmount"));//交易金额
			mapBody.put("transNo", form.getFirstValue("transNo"));//汇付宝订单号
			mapBody.put("result", form.getFirstValue("result"));//支付结果 1000 成功 1002 失败
			mapBody.put("merchantId", form.getFirstValue("merchantId"));//商户在汇付宝的id
			mapBody.put("merchantOrderNo", form.getFirstValue("merchantOrderNo"));//商户的交易号
			mapBody.put("version", form.getFirstValue("version"));//接口版本  1.0
			mapBody.put("sign", form.getFirstValue("sign"));//签名串，规则见说明
			chargeJSON = JsonMapperUtil.nonDefaultMapper().toJson(mapBody);
			log.info("客户充值异步通知组装报文体{}",chargeJSON);
			mapHeader.put("version", "1.0");
			mapHeader.put("merchantCode", "JCT");
			mapHeader.put("txnType", "YBTX01");
			mapHeader.put("clientSn", "jct"+System.currentTimeMillis());
			mapHeader.put("clientDate", DateUtils.getDateStr(new Date(), "yyyyMMdd"));
			mapHeader.put("clientTime", DateUtils.getDateStr(new Date(), "HHmmss"));
			mapHeader.put("fileName", "");
			mapHeader.put("signTime", System.currentTimeMillis()+"");
			resultReqHeader = new JSONObject(mapHeader);
			log.info("客户充值异步通知组装报文 表头{}",resultReqHeader);
		}
		String respJson ="{}";
		//客户充值异步通知
		if (routeName.toUpperCase().equals("CUSTOMERCHARGE")) {
			log.info("交易系统的客户充值异步通知");
			respJson = b2CAsynNoticeClient.customerChargeAsynNotice(resultReqHeader.toString(), chargeJSON);
			return new StringRepresentation(respJson);
		}
		ResponseMessage responseMessage = setResponseMessage(resultReqHeader,error,respJson);
		log.info("method:{},response_entity:{}",routeName,(new JSONObject(responseMessage)).toString());
		return new JsonRepresentation(responseMessage);
	}
}
