package com.heepay.tpds.service.impl;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.rpc.service.RpcService;
import com.heepay.tpds.client.TpdsCommonClient;
import com.heepay.tpds.util.TpdsDataUtils;
import com.heepay.tpds.vo.CommonRequestHeaderMessage;
import com.heepay.tpds.vo.RequestMessage;
import com.heepay.tpds.vo.ServerBankInfo;
import com.heepay.utils.http.WithoutAuthHttpsClient;
import com.heepay.utils.security.SignRSA;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年7月18日下午2:43:32
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
@Component
@RpcService(name="FileAdviceServiceImpl",processor=com.heepay.rpc.tpds.service.FileAdviceService.Processor.class)
public class FileAdviceHttpServiceImpl implements com.heepay.rpc.tpds.service.FileAdviceService.Iface{
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private TpdsCommonClient tpdsCommonClient;

	@Resource(name = "httpsClient")
	WithoutAuthHttpsClient httpsClient;

	@Resource(name = "serverBankInfo")
	ServerBankInfo serverBankInfo;
	
	@Override
	public String adviceHttp(String adviceUrl, String systemNo, String fileName) throws TException {


		// 发送通知

		TreeMap<String, String> inBody = new TreeMap<>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				// 指定排序器按照key升序排列
				return o1.compareTo(o2);
			}
		});

		CommonRequestHeaderMessage reqHeader = new CommonRequestHeaderMessage();
		reqHeader.setVersion("1.0");
		reqHeader.setMerchantCode(systemNo);
		reqHeader.setTxnType("R00003");
		reqHeader.setClientSn(TpdsDataUtils.businessOrderNo());
		reqHeader.setClientDate(DateUtil.getCurrentDate(new Date()));
		reqHeader.setClientTime(new SimpleDateFormat("HHmmss").format(new Date()));
		reqHeader.setFileName(fileName);
		reqHeader.setSignTime(String.valueOf(new Date().getTime()));

		inBody.put("businessSeqNo", TpdsDataUtils.businessSeqNo(systemNo));
		inBody.put("liquDate", DateUtil.getCurrentDate(new Date()));
		inBody.put("fileName", fileName);
		inBody.put("fileDownloadPath", tpdsCommonClient.getBankHostIp() + ":/"
				+ tpdsCommonClient.getBankUploadFilePath() + DateUtil.getCurrentDate(new Date()));
		inBody.put("fileUploadPath", "");

		// 加签
		String value = reqHeader.getSignTime() + "|" + SignRSA.getTreeMap(inBody);
		logger.info("签名串value{}",value);
		logger.info("签名路径{}",this.getClass().getResource("/").getPath()+serverBankInfo.getSignPrivateKey());
		String signature = SignRSA.addSign(value, this.getClass().getResource("/").getPath()+serverBankInfo.getSignPrivateKey());
		inBody.put("busiBranchNo", tpdsCommonClient.getBusiBranchNo());
		
		reqHeader.setSignature(signature);

		RequestMessage requestMessage = new RequestMessage();
		requestMessage.setReqHeader(reqHeader);
		requestMessage.setInBody(inBody);
		JsonMapperUtil jsonUtil = new JsonMapperUtil();
		try {
			return httpsClient.send(adviceUrl, jsonUtil.toJson(requestMessage), "UTF-8");

		} catch (Exception e) {
			logger.error("http对账通知出错{}", e);
		}
		return null;

	
	}

}
