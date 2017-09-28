package com.heepay.manage.modules.settle.service.util;

import com.google.common.collect.Lists;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.AccountType;
import com.heepay.enums.BankcardType;
import com.heepay.enums.ProductType;
import com.heepay.enums.billing.*;
import com.heepay.enums.risk.RiskMerChantStatus;
import com.heepay.enums.risk.RiskOrderDealType;
import com.heepay.enums.risk.RiskOrderStatus;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.reconciliation.util.ChannelTypeClear;
import com.heepay.manage.modules.sys.entity.Dict;
import com.heepay.manage.modules.sys.utils.DictUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;

import java.util.List;

/**
 * 用于清结算后台管理前端页面类型查询条件的显示
 *
 * 描    述：
 *
 * 创 建 者： @author wangdong
 * 创建时间：2016年9月22日
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
public class CommonUtil {
	
	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * 显示类型的方法
	 * @时间：2016年9月22日
	 * @创建人：wangdong
	 */
	public static Model enumsShow(Model model,String type) throws Exception{
		try {

			List<EnumBean> cSList = Lists.newArrayList();  //对账状态
			List<EnumBean> sSList = Lists.newArrayList();  //结算状态
			List<EnumBean> cYSList = Lists.newArrayList();  //已对账状态
			List<EnumBean> pTList = Lists.newArrayList();  //交易类型
			List<EnumBean> sTList = Lists.newArrayList();  //生效状态
			List<EnumBean> CTList = Lists.newArrayList();  //产品名称
			
			List<EnumBean> rMPSList = Lists.newArrayList();  //风控商户限额状态
			List<EnumBean> rBTList = Lists.newArrayList();  //风控商户限额银行卡类型
			List<EnumBean> rATList = Lists.newArrayList();  //风控商户限额账户类型
			List<EnumBean> rDTList = Lists.newArrayList();  //风控 风险订单处理方式
			List<EnumBean> rSList = Lists.newArrayList();  //风控 风险订单状态

			List<EnumBean> bList = Lists.newArrayList();  //结算模式 业务类型
			List<EnumBean> tList = Lists.newArrayList();  //结算模式 交易类型

			if (StringUtils.equals(Constants.Clear.SETTLE, type)){
				//遍历通道业务类型
				List<EnumBean> list = ChannelTypeClear.getList();
				model.addAttribute("channelType", list);
				//遍历产品名称
				for (ProductType productType : ProductType.values()) {
					EnumBean ct = new EnumBean();
					ct.setValue(productType.getValue());
					ct.setName(productType.getContent());
					CTList.add(ct);
				}
				model.addAttribute("product1Type", CTList);
				for (ClearingCheckStatus checkStatus : ClearingCheckStatus.values()) {
					EnumBean ct = new EnumBean();
					ct.setValue(checkStatus.getValue());
					ct.setName(checkStatus.getContent());
					cSList.add(ct);
				}
				model.addAttribute("checkStatus", cSList);
				//结算状态
				for (BillingSettleStatus settleStatus : BillingSettleStatus.values()) {
					EnumBean ct = new EnumBean();
					ct.setValue(settleStatus.getValue());
					ct.setName(settleStatus.getContent());
					sSList.add(ct);
				}
				model.addAttribute("settleStatus", sSList);
				//已选对账状态
				for (BillingYCheckStatus checkYStatus : BillingYCheckStatus.values()) {
					EnumBean ct = new EnumBean();
					ct.setValue(checkYStatus.getValue());
					ct.setName(checkYStatus.getContent());
					cYSList.add(ct);
				}
				model.addAttribute("checkYStatus", cYSList);

				//交易类型
				for (BillingTransType productType : BillingTransType.values()) {
					EnumBean ct = new EnumBean();
					ct.setValue(productType.getValue());
					ct.setName(productType.getContent());
					pTList.add(ct);
				}
				model.addAttribute("productType", pTList);
				//生效状态
				for(BillingBecomeEffect status : BillingBecomeEffect.values()){
					EnumBean ct = new EnumBean();
					ct.setValue(status.getValue());
					ct.setName(status.getContent());
					sTList.add(ct);
				}
				model.addAttribute("statusList", sTList);
			}
			//风控显示的条件
			else if(StringUtils.equals(Constants.Clear.RISK, type)){
				//风控商户限额状态
				for(RiskMerChantStatus status : RiskMerChantStatus.values()){
					EnumBean ct = new EnumBean();
					ct.setValue(status.getValue());
					ct.setName(status.getContent());
					rMPSList.add(ct);
				}
				model.addAttribute("rMPSList", rMPSList);
				//风控银行卡类型
				for(BankcardType status : BankcardType.values()){
					if(StringUtils.equals(status.getValue(), "SAVING") || StringUtils.equals(status.getValue(), "CREDIT")){
						EnumBean ct = new EnumBean();
						ct.setValue(status.getValue());
						ct.setName(status.getContent());
						rBTList.add(ct);
					}
				}
				model.addAttribute("rBTList", rBTList);
				//风控账户类型
				for(AccountType status : AccountType.values()){
					if(StringUtils.equals(status.getValue(), "PUBLIC") || StringUtils.equals(status.getValue(), "PRIVAT")){
						EnumBean ct = new EnumBean();
						ct.setValue(status.getValue());
						ct.setName(status.getContent());
						rATList.add(ct);
					}
				}
				model.addAttribute("rATList", rATList);
				//风控 风险订单处理方式  
				for(RiskOrderDealType status : RiskOrderDealType.values()){
					EnumBean ct = new EnumBean();
					ct.setValue(status.getValue());
					ct.setName(status.getContent());
					rDTList.add(ct);
				}
				model.addAttribute("rDTList", rDTList);
				//风控 风险订单状态  
				for(RiskOrderStatus status : RiskOrderStatus.values()){
					EnumBean ct = new EnumBean();
					ct.setValue(status.getValue());
					ct.setName(status.getContent());
					rSList.add(ct);
				}
				model.addAttribute("rSList", rSList);
			}
			//结算模式显示条件
			else if (StringUtils.equals(Constants.Clear.MODEL, type)){
				//结算模式 业务类型
				for(Dict dict : DictUtils.getDictList("RateBusinessType")){
					EnumBean ct = new EnumBean();
					ct.setValue(dict.getValue());
					ct.setName(dict.getLabel());
					bList.add(ct);
				}
				model.addAttribute("bList", bList);
				//结算模式 交易类型
				for(Dict dict : DictUtils.getDictList("TransType")){
					EnumBean ct = new EnumBean();
					ct.setValue(dict.getValue());
					ct.setName(dict.getLabel());
					tList.add(ct);
				}
				model.addAttribute("tList", tList);
			}
			return model;
		} catch (Exception e) {
			logger.error("CommonUtil enumsShow has a error {显示类型的方法错误 FIND_ERROR} model:"+model, e);
			throw new Exception(e);
		}
	}
}
