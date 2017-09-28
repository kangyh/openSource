
package com.heepay.risk.service.impl;

import java.math.BigDecimal;
import java.util.Date;


import com.heepay.risk.dao.RiskWarningMapper;
import com.heepay.risk.vo.RiskTransOrderModel;


import com.heepay.enums.risk.*;
import com.heepay.risk.dao.RiskBlockInfoMapper;
import com.heepay.risk.entity.*;
import com.heepay.risk.enums.BlockType;
import com.heepay.risk.enums.MonitorObject;
import com.heepay.risk.service.IpQueryService;
import com.heepay.risk.util.MobileUtil;
import com.heepay.rpc.client.BankCardAuthClient;
import com.heepay.rpc.client.CheckProvinceClient;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.heepay.billingutils.IdClear;
import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.engine.entity.ChannelRiskFact;
import com.heepay.engine.entity.MerchantRiskFact;
import com.heepay.engine.impl.DroolsTemplateRuleEngineService;
import com.heepay.enums.AccountType;
import com.heepay.enums.BankcardOwnerType;
import com.heepay.enums.InterfaceStatus;
import com.heepay.enums.ProductType;
import com.heepay.enums.TransType;
import com.heepay.risk.RiskChannelLogVo;
import com.heepay.risk.dao.RiskChannelMapper;
import com.heepay.risk.dao.RiskOrderMapper;
import com.heepay.risk.dao.RiskRuleDetailMapper;
import com.heepay.risk.factory.ChannelRiskFactFactory;
import com.heepay.risk.factory.MerchantRiskFactFactory;
import com.heepay.risk.factory.RiskFactFactory;
import com.heepay.rpc.risk.model.AsyncMsgVO;
import com.heepay.rpc.risk.model.TransOrderRiskModel;
import com.heepay.rpc.risk.service.TransOrderRiskService;
import com.heepay.rpc.service.RpcService;
/**
 * 
* 
* 描    述：
*
* 创 建 者：   dongzj
* 创建时间： 2016年11月22日 10:47:22 
* 创建描述：风控限额对外接口

* 
*        
 */
@Component
@RpcService(name="ProductQuotaServiceImpl",processor=TransOrderRiskService.Processor.class)
public class ProductQuotaServiceImpl implements com.heepay.rpc.risk.service.TransOrderRiskService.Iface {

	private static final Logger logger = LogManager.getLogger();
	@Autowired
	private DroolsTemplateRuleEngineService droolsTemplateRuleEngineService;
	@Autowired
	private RiskOrderMapper riskOrderMapper;
	@Autowired
	private RiskRuleDetailMapper riskRuleDetailMapper;
	@Autowired
	private RiskWarningMapper riskWarningMapper;

	private CheckProvinceClient checkProvinceClient;
    @Autowired
    private RiskBlockInfoMapper riskBlockInfoMapper;
    @Autowired
    private IpQueryService ipQueryService;
	@Autowired
	private BankCardAuthClient bankCardAuthClient;

	/**
	 * 
	* 
	* 描    述：
	*
	* 创 建 者：   dongzj
	* 创建时间： 2016年11月29日 15:47:22 
	* 创建描述：商户产品禁用接口

	* 
	*        
	*/
	
	@Override
	public com.heepay.rpc.risk.model.AsyncMsgVO ExecuteProductEnableRule(TransOrderRiskModel vo) throws TException {
		return null;
	}
	/**
	 * 执行风控限额规则
	 * @param vo 交易订单信息
	 * @return   风控规则结果
	 */
	@Override
	public com.heepay.rpc.risk.model.AsyncMsgVO ExecuteRule(TransOrderRiskModel vo) throws TException {
		try {
			logger.info("产品订单信息:--->" + JsonMapperUtil.nonDefaultMapper().toJson(vo));
		}
		catch(Exception ex)
		{

		}
		com.heepay.rpc.risk.model.AsyncMsgVO result=new  com.heepay.rpc.risk.model.AsyncMsgVO();
		RiskFactFactory riskFactFactory = null;
		MerchantRiskFact merchantRiskFact=  null;
		if(vo.getBankCardOwnerType().equals(BankcardOwnerType.ENTERPRISE.getValue()))
			vo.setBankCardOwnerType(AccountType.PUBLIC.getValue());
		if(vo.getBankCardOwnerType().equals(BankcardOwnerType.PERSONAL.getValue()))
			vo.setBankCardOwnerType(AccountType.PRIVAT.getValue());
		try
		{
			 riskFactFactory = new MerchantRiskFactFactory();
			 merchantRiskFact=  (MerchantRiskFact) riskFactFactory.create();
			 merchantRiskFact.setMerchantId(vo.getMerchantId());
	         merchantRiskFact.setAccountType(vo.getBankCardOwnerType());
	         merchantRiskFact.setBankCardType(vo.getBankCardType());
	         merchantRiskFact.setProductCode(vo.getProductType());
	         merchantRiskFact.setTrans_type(vo.getTransType());
	         merchantRiskFact.setPerItemAmount(new BigDecimal(vo.getTransAmount()));
	         merchantRiskFact.setMerchantCompany(vo.getMerchantCompany());
			 merchantRiskFact.setTradeIp(vo.getReqIp());
			 merchantRiskFact.setUserType(vo.getUserType());
	         //银行卡号解密
	          if(!StringUtil.isBlank(vo.getBankCardNo())) {
				  try {
					  vo.setBankCardNo(Aes.decryptStr(vo.getBankCardNo(), Constants.QuickPay.SYSTEM_KEY));
				  } catch (Exception ex) {

				  }
			  }
	         //身份证号解密
			if(!StringUtil.isBlank(vo.getBankCardOwnerIdCard())) {
				try {
					vo.setBankCardOwnerIdCard(Aes.decryptStr(vo.getBankCardOwnerIdCard(), Constants.QuickPay.SYSTEM_KEY));
				} catch (Exception ex) {

				}
			}
			//手机号解密
			if(!StringUtil.isBlank(vo.getBankCardOwnerMobile())) {
				try {
					vo.setBankCardOwnerMobile(Aes.decryptStr(vo.getBankCardOwnerMobile(), Constants.QuickPay.SYSTEM_KEY));
				} catch (Exception ex) {

				}
			}
	         merchantRiskFact.setBankCardOwnerIdCard(vo.getBankCardOwnerIdCard());
	         merchantRiskFact.setBankCardNo(vo.getBankCardNo());
	         merchantRiskFact.setBankCardOwnerMobile(vo.getBankCardOwnerMobile());
	         if (!StringUtil.isBlank(vo.getTransferBatchAmount())) //增加 批量转账的限制 add by wyl 20170309
	         {
	        	 merchantRiskFact.setBatPayAmount(new BigDecimal(vo.getTransferBatchAmount()));
	         }
	         merchantRiskFact.setMessage(InterfaceStatus.SUCCESS);
	         //根据银行卡号获取联行号
			if(!StringUtil.isBlank(vo.getBankCardNo())) {
				//String lineNumber = bankCardAuthClient.getLineNoByBankCardNo(Aes.encryptStr(vo.getBankCardNo(),Constants.QuickPay.SYSTEM_KEY));
				String lineNumber="";
				if(!StringUtil.isBlank(lineNumber))
					vo.setAssociateLineNumber(lineNumber);
			}
	         //银行卡号归属省
	         if(!StringUtil.isBlank(vo.getAssociateLineNumber()))
			{
				String provinceJson=checkProvinceClient.selectProvince(vo.getAssociateLineNumber());
				if(!StringUtil.isBlank(provinceJson)) {
					JSONObject json = JSONObject.fromObject(provinceJson);
					merchantRiskFact.setProvinceInBankCard(json.get("provinceName").toString());
				}
			}
			//手机号归属省
			if(!StringUtil.isBlank(merchantRiskFact.getBankCardOwnerMobile()))
			{
				//String location=MobileUtil.GetMobileLocation(merchantRiskFact.getBankCardOwnerMobile());
				String location="";
				logger.info("手机号归属地"+location);
				if(!StringUtil.isBlank(location)) {
                    merchantRiskFact.setProvinceInMobile(location);
                }
			}
			merchantRiskFact.setTradeIp("");
            //ip归属省
            if(!StringUtil.isBlank(merchantRiskFact.getTradeIp()))
            {
                RiskIpbase riskIpbase= ipQueryService.getIpInfo(merchantRiskFact.getTradeIp());
                if(riskIpbase!=null)
                    merchantRiskFact.setProvinceInIp(riskIpbase.getProvince());
            }
            if(!StringUtil.isBlank(merchantRiskFact.getTradeIp()))
                merchantRiskFact.setForeignIp(!ipQueryService.checkIfChineseIp(merchantRiskFact.getTradeIp()));
            else
                merchantRiskFact.setForeignIp(false);
			droolsTemplateRuleEngineService.handleRuleFact(merchantRiskFact);
			logger.info("返回的结果信息:--->"+JsonMapperUtil.nonDefaultMapper().toJson(merchantRiskFact));
			//if (merchantRiskFact.getMessage().getValue()==1000) //不等 1000为风险订单需要入库
			//{
				//addOrderToEs(vo);
			//}
			handleIllegalOrder(vo,merchantRiskFact);

			result.setStatus(merchantRiskFact.getMessage().getValue());
			result.setMsg(merchantRiskFact.getMessage().getContent());
		}
		catch(Exception ex)
		{
			try {
				logger.error("风控限额出现异常:" + ex.getMessage());
				logger.error("风控限额出现异常堆栈:" + ex.getStackTrace());
			}
			catch(Exception ex1)
			{

			}
			result.setStatus(InterfaceStatus.SUCCESS.getValue());
		}
		
		
		return result;
	}
	private void addOrderToEs(TransOrderRiskModel vo)
	{
		StringBuilder sb=new StringBuilder();
		//添加订单信息到es中
		RiskTransOrderModel model=new RiskTransOrderModel();
		model.setBankCardNo(vo.getBankCardNo());
		model.setBankCardOwnerIdCard(vo.getBankCardOwnerIdCard());
		model.setBankCardOwnerMobile(vo.getBankCardOwnerMobile());
		model.setBankCardOwnerName(vo.getBankCardOwnerName());
		model.setBankCardOwnerType(vo.getBankCardOwnerType());
		model.setBankCardType(vo.getBankCardType());
		model.setChannelCode(vo.getChannelCode());
		model.setChannelName(vo.getChannelName());
		model.setChannelTransType(vo.getChannelTransType());
		model.setCreateTime(vo.getCreateTime());
		model.setFeeAmount(vo.getFeeAmount());
		model.setFeeType(vo.getFeeType());
		model.setMerchantCompany(vo.getMerchantCompany());
		model.setMerchantId(vo.getMerchantId());
		model.setMerchantOrderNo(vo.getMerchantOrderNo());
		model.setProductName(vo.getProductName());
		model.setProductType(vo.getProductType());
		model.setReqIp(vo.getReqIp());
		model.setTransAmount(vo.getTransAmount());
		model.setTransferBatchAmount(vo.getTransferBatchAmount());
		model.setTransNo(vo.getTransNo());
		model.setTransType(vo.getTransType());
		sb.append("{\"merchantOrderNo\":");    //必添
		sb.append("\""+model.getMerchantOrderNo().trim()+"\"");
		sb.append(",\"transNo\":"); //必添
		sb.append("\""+model.getTransNo().trim()+"\"");
		sb.append(",\"productType\":"); //必添
		sb.append("\""+model.getProductType().trim()+"\"");
		sb.append(",\"productName\":"); //必添
		sb.append("\""+model.getProductName().trim()+"\"");
		sb.append(",\"merchantId\":"); //必添
		sb.append("\""+model.getMerchantId().trim()+"\"");
		sb.append(",\"merchantCompany\":"); //必添
		sb.append("\""+model.getMerchantCompany().trim()+"\"");
		sb.append(",\"transType\":"); //必添
		sb.append("\""+model.getTransType().trim()+"\"");
		sb.append(",\"transAmount\":"); //必添
		sb.append(model.getTransAmount().trim());
		sb.append(",\"bankCardType\":"); //必添
		sb.append("\""+model.getBankCardType().trim()+"\"");
		sb.append(",\"bankCardNo\":"); //必添
		sb.append("\""+model.getBankCardNo().trim()+"\"");
		sb.append(",\"bankCardOwnerName\":"); //必添
		sb.append("\""+model.getBankCardOwnerName().trim()+"\"");
		sb.append(",\"bankCardOwnerIdCard\":"); //必添
		sb.append("\""+model.getBankCardOwnerIdCard().trim()+"\"");
		sb.append(",\"bankCardOwnerMobile\":"); //必添
		sb.append("\""+model.getBankCardOwnerMobile().trim()+"\"");
		sb.append(",\"channelCode\":"); //必添
		sb.append("\""+model.getChannelCode().trim()+"\"");
		sb.append(",\"channelName\":"); //必添
		sb.append("\""+model.getChannelName().trim()+"\"");
		sb.append(",\"channelTransType\":"); //必添
		sb.append("\""+model.getChannelTransType().trim()+"\"");
		sb.append(",\"feeType\":"); //必添
		sb.append("\""+model.getFeeType().trim()+"\"");
		sb.append(",\"bankCardOwnerType\":"); //必添
		sb.append("\""+model.getBankCardOwnerType().trim()+"\"");
		sb.append(",\"createTime\":"); //必添
		sb.append(model.getCreateTime().trim());
		sb.append(",\"transferBatchAmount\":"); //必添
		sb.append(model.getTransferBatchAmount().trim());
		sb.append(",\"feeAmount\":"); //必添
		sb.append(model.getFeeAmount().trim());
		sb.append(",\"reqIp\":"); //必添
		sb.append("\""+model.getReqIp().trim()+"\"");
		sb.append("}");
		logger.info("添加订单到es:{}",sb.toString());
		riskWarningMapper.indexMerchantOrder(sb.toString());
	}
	/**
	 * 处理风险订单
	 * @param vo
	 */
	private void handleIllegalOrder(TransOrderRiskModel vo,MerchantRiskFact merchantRiskFact)
	{
        if(merchantRiskFact.getProductQuotaType()!=null&&merchantRiskFact.getMessage().getValue()!=1000) {
            logger.info("违反风控规则:--->"+merchantRiskFact.getProductQuotaType().getContent());
            RiskOrder riskOrder = new RiskOrder();
            riskOrder.setCreateTime(vo.getCreateTime() == null ? new Date() : DateUtil.stringToDate(vo.getCreateTime(), "yyyyMMddHHmmss"));
            riskOrder.setRiskNo(IdClear.getRandomString(5));
            riskOrder.setMerchantCode(vo.getMerchantId());
            riskOrder.setMerchantName(vo.getMerchantCompany());
            riskOrder.setMerchantOrderNo(vo.getMerchantOrderNo());
            riskOrder.setProductCode(vo.getProductType());
            riskOrder.setProductName(ProductType.labelOf(vo.getProductType()));
            riskOrder.setTransAmount(new BigDecimal(vo.getTransAmount()));
            riskOrder.setTransNo(vo.getTransNo());
            riskOrder.setTransType(vo.getTransType());
            riskOrder.setOrderDealwith(RiskOrderDealType.RISK_ORDER_DEAL_BLOCK.getValue());
            riskOrder.setOrderStatus(RiskOrderStatus.RISK_ORDER_STATUS_BLOCK.getValue());
            riskOrder.setQuotaId(merchantRiskFact.getQuotaId());
            riskOrder.setQuotaItem(merchantRiskFact.getProductQuotaType() != null ? merchantRiskFact.getProductQuotaType().getValue() : null);
            riskOrder.setQuotaType(merchantRiskFact.getQuotaType() != null ? merchantRiskFact.getQuotaType().getValue() : null);
            String detailCode = RuleDetailType.QUOTA.getValue();
            if (merchantRiskFact.getMessage().getValue() == InterfaceStatus.RISK_MERCHANT_INCOME_Quota.getValue())
                detailCode = RuleDetailType.INCOME.getValue();
            else if (merchantRiskFact.getProductQuotaType() == ProductQuotaType.DISABLE)
                detailCode = RuleDetailType.DISABLE.getValue();
            else if (merchantRiskFact.getProductQuotaType() == ProductQuotaType.BLACK)
                detailCode = RuleDetailType.BLACK.getValue();

            //logger.info("规则详情id:--->{}",detailCode);
            RiskRuleDetail ruleDetail = riskRuleDetailMapper.getRuleDetailByDetailCode(detailCode);
            if (ruleDetail != null)
                riskOrder.setRuleDetailId(ruleDetail.getDetailId());
            riskOrderMapper.insert(riskOrder);
        }
        if(merchantRiskFact.getRuleIdList()!=null&&merchantRiskFact.getRuleIdList().size()>0)
        {
            logger.info("违反风控规则:--->"+JsonMapperUtil.buildNonDefaultMapper().toJson(merchantRiskFact.getRuleIdList()));
            for(String ruleid:merchantRiskFact.getRuleIdList())
            {
                RiskBlockInfo info=new RiskBlockInfo();
                info.setBlockType(merchantRiskFact.getMessage().getValue()==1000?BlockType.WARN.getValue():BlockType.BLOCK.getValue());
                info.setBuziType(RegLoginType.MER_TRANS.getValue());
                info.setRuleId(ruleid);
                info.setCreatetime(new Date());
                info.setMonitorObject(StringUtil.isBlank(merchantRiskFact.getUserType())?MonitorObject.MERCHANT.getValue():MonitorObject.USER.getValue());
                info.setFileds(JsonMapperUtil.buildNonDefaultMapper().toJson(merchantRiskFact));
                riskBlockInfoMapper.insert(info);
            }
        }
	}
	
}


	
	



