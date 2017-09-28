package com.heepay.risk.job;

import java.util.*;
import com.heepay.enums.BankcardOwnerType;
import com.heepay.enums.TransType;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.enums.BankcardOwnerType;
import com.heepay.enums.TransType;
import com.heepay.risk.client.*;
import net.sf.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.risk.vo.BankFraudAttachment;
import com.heepay.risk.vo.BankFraudBodyQueryVO;
import com.heepay.risk.vo.BankFraudHeaderQueryVO;
import com.heepay.risk.vo.BankFraudQueryVO;
import com.heepay.risk.vo.SettleBalckListMainVo;
import com.heepay.risk.vo.SettleBlackListRequestBody;
import com.heepay.risk.vo.SettleBlackListVo;
import com.heepay.risk.vo.SettleEntInfo;
import com.heepay.risk.vo.SettleMerchantDifferentRequestBodyVo;
import com.heepay.risk.vo.SettleMerchantDifferentRequestVo;
import com.heepay.risk.vo.SettleMerchantInfoVo;
import com.heepay.risk.vo.SettleRequestHeader;
import com.heepay.rpc.risk.model.AsyncMsgVO;
import com.heepay.rpc.risk.model.FreezeResponse;
import com.heepay.rpc.risk.model.TransOrderRiskModel;

public class Run {
	//static BankFraudClient client=null;
	/**
	  * 
	  * @方法说明：
	  * @author dongzhengjiang
	  * @param 
	  * @return
	  * @时间：2017年3月7日 上午10:18:32
	  */
	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		//client=(BankFraudClient)context.getBean("bankFraudClient");
		//test1();
		RiskQuotaClient client2=(RiskQuotaClient)context.getBean("riskQuotaClient");
		TransOrderRiskModel model=new TransOrderRiskModel();
		model.setMerchantCompany("5555");
		model.setMerchantId("100005");
		model.setAssociateLineNumber("001110012017");
		model.setReqIp("101.254.183.14");
		model.setBankCardOwnerType(BankcardOwnerType.PERSONAL.getValue());
		model.setBankCardOwnerMobile("13120254844");
		model.setBankCardNo("3245345345");
		model.setProductType("CP06");
		model.setTransType(TransType.PAY.getValue());
		model.setTransAmount("1");
		model.setBankCardType(BankcardOwnerType.PERSONAL.getValue());
		model.setBankCardOwnerIdCard("234234324");
		model.setCreateTime(DateUtil.dateToString(new Date(),"yyyyMMddHHmmss"));
		AsyncMsgVO str=client2.ExecuteRule(model);
        /**BankCardAuthClient client2=(BankCardAuthClient)context.getBean("bankCardAuthClient");
        String str=client2.getLineNoByBankCardNo("BD02405E4C540988EDFF035D1C6A3A5BE6541799A4F74E34F962980FB9DE7F16");*/


        /**RegisterAndLoginClient client2=(RegisterAndLoginClient)context.getBean("registerAndLoginClient");
        String str=client2.ExecuteRegisterAndLoginRule("{\"ip\":\"100.1.255.255\",\"regLoginType\":\"2\"}");*/
		//RiskChannelClient client2=(RiskChannelClient)context.getBean("riskChannelClient");
		//String str=client2.getChannelQuotaList("{\"pageIndex\":\"0\",\"pageSize\":\"30\",\"channel_code\":\"104BILPAY_10209\"}");
		//String str=client2.e("{\"pageIndex\":\"0\",\"pageSize\":\"30\"}");

		

	}
	

}
