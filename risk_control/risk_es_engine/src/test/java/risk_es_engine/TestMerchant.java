/**
 * 
 */
package risk_es_engine;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.heepay.common.util.DateUtil;
import com.heepay.risk_es_engine.EsMerchantService;

/**
 * @author Administrator
 *
 */
public class TestMerchant {
	
	EsMerchantService essearch = null;
	

	@Before
	public void initESClient() {
		essearch = new EsMerchantService();
		essearch.initESClient();
	}
	@After
	public void closeESClient() {
		essearch.closeESClient();
	}
	@Test
	public void indexMerchantOrder() {
		System.out.println("sfasdfsdf");
//		Map<String,String> equalConditionMap = new HashMap<String,String>();
//		equalConditionMap.put("beginTime", "2017-08-03 21:00:05");
//		equalConditionMap.put("endTime", "2017-08-03 22:00:05" );
//		
//		System.out.println(essearch.isExistsMerchantOrderLog(JSONObject.toJSONString(equalConditionMap))); 
		
		TestModel vo = new TestModel();
		vo.setBankCardNo("111");
		vo.setBankCardOwerType("222");
		vo.setBankCardOwnerIdCard("333");
		vo.setBankCardOwnerMobile("444");
		vo.setBankCardOwnerName("555");
		vo.setBankCardType("666");
		vo.setChannelCode("777");
		vo.setChannelName("888");
		vo.setChannelTransType("999");
		Calendar c = Calendar.getInstance();
		System.out.println("毫秒数："+c.getTimeInMillis() );
		vo.setCreateTime(  c.getTimeInMillis() );
		vo.setFeeAmount(new BigDecimal(2));
		vo.setFeeType("33");
		vo.setMerchantCompany("44");
		vo.setMerchantId("55");
		vo.setMerchantOrderNo("66");
		vo.setProductName("77");
		vo.setProductType("88");
		vo.setReqIp("99");
		vo.setTransAmount(  new BigDecimal(32) );
		vo.setTransferBatchAmount( new BigDecimal(32) );
		Random r = new Random();
		vo.setTransNo("444" +r.nextInt()*1000 );
		vo.setTransType("7777");
		vo.setHost("bs123");
//		essearch.indexMerchantOrder( JSONObject.toJSONString(vo)  );1502677875550  1502707875550
		
		
		Map<String,String> equalConditionMap1 = new HashMap<String,String>();
//		equalConditionMap1.put("beginTime", "1499961600000");
//		equalConditionMap1.put("endTime",    "1502812799000");
		equalConditionMap1.put("beginTime", "0");
		equalConditionMap1.put("endTime",    "1602706290419");
//		equalConditionMap1.put("merchant_id", "100005");
//		essearch.querySucMerMoniOrderCount(JSONObject.toJSONString(equalConditionMap1) );
		
		
		Map<String,String> equalConditionMap2 = new HashMap<String,String>();
		//1501516800000 //1502447695181 今天
		//1502467199000 //1502449338857
//		{"merchantId":"100005","beginTime":"1502117446839","endTime":"1502717446839"}
		
		equalConditionMap2.put("beginTime", "0");
		equalConditionMap2.put("endTime",    "1602706290419");
		equalConditionMap2.put("merchantId", "55");
//		equalConditionMap2.put( "productName", "77");
		
//		essearch.queryMerMoniOrderCount( JSONObject.toJSONString(equalConditionMap2)  );

		
		Map<String,List<Map>> map = essearch.getOrderCount(JSONObject.toJSONString(equalConditionMap1), JSONObject.toJSONString(equalConditionMap2));
		System.out.println("reteteterterterte##########");
//		String fields = "channelCode,channelName,channelTransType,createTime,feeAmount,feeType";
//		Map<String,String> equalConditionMap3 = new HashMap<String,String>();1502704611889
//		equalConditionMap3.put("beginTime", "1501516800000");
//		equalConditionMap3.put("endTime", "1502467199000");
//		equalConditionMap3.put("pagefrom", "0");
//		equalConditionMap3.put("pagesize", "20");
//		essearch.queryMerMoniInfoList(fields, JSONObject.toJSONString(equalConditionMap3));
		
	}
	protected class TestModel {
		
		private String merchantOrderNo;//商户订单号
		private String transNo	;//商户交易号
		private String productType	;//产品编码
		private String productName	;//产品名称
		private String merchantId	;//商户编码
		private String merchantCompany	;//商户名称
		private String transType	;//交易类型
		private BigDecimal transAmount	;//交易金额
		private String bankCardType	;//银行卡类型  SAVING:储蓄卡 CREDIT:信用卡
		private String bankCardNo	;//银行卡号
		private String bankCardOwnerName	;//持卡人姓名
		private String bankCardOwnerIdCard	;//持卡人身份证号码
		private String bankCardOwnerMobile	;//持卡人手机号码
		private String channelCode	;//渠道编码
		private String channelName	;//渠道名称
		private String channelTransType	;//渠道交易类型
		private String feeType	;//手续费扣除方式
		private String bankCardOwerType	;//对公还是对私 0：个人 1：企业 当对公时银行卡类型为空
		private Long createTime	;//交易时间
		private BigDecimal transferBatchAmount	;//批量转账金额
		private BigDecimal feeAmount	;//手续费
		private String reqIp	;//请求ip
		private String host;
		
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public BigDecimal getTransAmount() {
			return transAmount;
		}
		public void setTransAmount(BigDecimal transAmount) {
			this.transAmount = transAmount;
		}
		public BigDecimal getTransferBatchAmount() {
			return transferBatchAmount;
		}
		public void setTransferBatchAmount(BigDecimal transferBatchAmount) {
			this.transferBatchAmount = transferBatchAmount;
		}
		public BigDecimal getFeeAmount() {
			return feeAmount;
		}
		public void setFeeAmount(BigDecimal feeAmount) {
			this.feeAmount = feeAmount;
		}
		public String getMerchantOrderNo() {
			return merchantOrderNo;
		}
		public void setMerchantOrderNo(String merchantOrderNo) {
			this.merchantOrderNo = merchantOrderNo;
		}
		public String getTransNo() {
			return transNo;
		}
		public void setTransNo(String transNo) {
			this.transNo = transNo;
		}
		public String getProductType() {
			return productType;
		}
		public void setProductType(String productType) {
			this.productType = productType;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getMerchantId() {
			return merchantId;
		}
		public void setMerchantId(String merchantId) {
			this.merchantId = merchantId;
		}
		public String getMerchantCompany() {
			return merchantCompany;
		}
		public void setMerchantCompany(String merchantCompany) {
			this.merchantCompany = merchantCompany;
		}
		public String getTransType() {
			return transType;
		}
		public void setTransType(String transType) {
			this.transType = transType;
		}
		public String getBankCardType() {
			return bankCardType;
		}
		public void setBankCardType(String bankCardType) {
			this.bankCardType = bankCardType;
		}
		public String getBankCardNo() {
			return bankCardNo;
		}
		public void setBankCardNo(String bankCardNo) {
			this.bankCardNo = bankCardNo;
		}
		public String getBankCardOwnerName() {
			return bankCardOwnerName;
		}
		public void setBankCardOwnerName(String bankCardOwnerName) {
			this.bankCardOwnerName = bankCardOwnerName;
		}
		public String getBankCardOwnerIdCard() {
			return bankCardOwnerIdCard;
		}
		public void setBankCardOwnerIdCard(String bankCardOwnerIdCard) {
			this.bankCardOwnerIdCard = bankCardOwnerIdCard;
		}
		public String getBankCardOwnerMobile() {
			return bankCardOwnerMobile;
		}
		public void setBankCardOwnerMobile(String bankCardOwnerMobile) {
			this.bankCardOwnerMobile = bankCardOwnerMobile;
		}
		public String getChannelCode() {
			return channelCode;
		}
		public void setChannelCode(String channelCode) {
			this.channelCode = channelCode;
		}
		public String getChannelName() {
			return channelName;
		}
		public void setChannelName(String channelName) {
			this.channelName = channelName;
		}
		public String getChannelTransType() {
			return channelTransType;
		}
		public void setChannelTransType(String channelTransType) {
			this.channelTransType = channelTransType;
		}
		public String getFeeType() {
			return feeType;
		}
		public void setFeeType(String feeType) {
			this.feeType = feeType;
		}
		public String getBankCardOwerType() {
			return bankCardOwerType;
		}
		public void setBankCardOwerType(String bankCardOwerType) {
			this.bankCardOwerType = bankCardOwerType;
		}
		
		public Long getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Long createTime) {
			this.createTime = createTime;
		}
		public String getReqIp() {
			return reqIp;
		}
		public void setReqIp(String reqIp) {
			this.reqIp = reqIp;
		}

	}
}
