package com.heepay.manage.modules.risk.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.TransType;
import com.heepay.enums.risk.RiskFreezeRemark;
import com.heepay.enums.risk.RiskFreezeType;
import com.heepay.enums.risk.RiskStatus;
import com.heepay.enums.risk.TransStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.risk.dao.RiskOrderUnfreezeDao;
import com.heepay.manage.modules.risk.entity.RiskOrderUnfreeze;


/***
 * 
* 
* 描    述：订单冻结/解冻表 服务
*
* 创 建 者：wangl
* 创建时间：  Nov 18, 20167:09:59 PM
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
@Service
@Transactional(readOnly = true)
public class RiskOrderUnfreezeService extends CrudService<RiskOrderUnfreezeDao, RiskOrderUnfreeze> {

	
	public Model findRiskMerchantProductQuotaPage(Page<RiskOrderUnfreeze> page, RiskOrderUnfreeze riskOrderUnfreeze,Model model) {
		try {
			Page<RiskOrderUnfreeze> pageRiskProductQuota = findPage(page, riskOrderUnfreeze);
			logger.info("订单冻结查询数据为------>{}"+ pageRiskProductQuota.getList());
			
			List<RiskOrderUnfreeze> clearingCRList = Lists.newArrayList();
			for (RiskOrderUnfreeze clearingCR : pageRiskProductQuota.getList()) {
				
					//trans_statusint(11) NULL成功 或 失败
					if(StringUtils.isNotBlank(clearingCR.getTransStatus())){
						clearingCR.setTransStatus(TransStatus.labelOf(clearingCR.getTransStatus()));
					}
					
					//冻结类型 1：冻结 2：解冻
					if(StringUtils.isNotBlank(clearingCR.getFreezeType())){
						clearingCR.setFreezeType(RiskFreezeType.labelOf(clearingCR.getFreezeType()));
					}
					//statusint(11) NULL冻结/解冻状态 0:待审核 1：已冻结 2：已解冻 3：冻结失败 4：解冻失败
					if(StringUtils.isNotBlank(clearingCR.getStatus())){
						clearingCR.setStatus(RiskStatus.labelOf(clearingCR.getStatus()));
					}
					//冻结/解冻原因 freeze_remark
					if(StringUtils.isNotBlank(clearingCR.getFreezeRemark())){
						clearingCR.setFreezeRemark(RiskFreezeRemark.labelOf(clearingCR.getFreezeRemark()));
					}
					//冻结/解冻原因 fail_reason
					if(StringUtils.isNotBlank(clearingCR.getFailReason())){
						clearingCR.setFailReason(RiskFreezeRemark.labelOf(clearingCR.getFailReason()));
					}
					//交易类型
					if(StringUtils.isNotBlank(clearingCR.getTransType())){
						clearingCR.setTransType(TransType.labelOf(clearingCR.getTransType()));
					}
					clearingCRList.add(clearingCR);
				}
				pageRiskProductQuota.setList(clearingCRList);
				
				//冻结/解冻状态 0:待审核 1：已冻结 2：已解冻 3：冻结失败 4：解冻失败
				List<EnumBean> riskStatus = Lists.newArrayList();
				for (RiskStatus checkFlg : RiskStatus.values()) {
					if(RiskStatus.RISK_STATUS_1.getValue().equals(checkFlg.getValue()) || RiskStatus.RISK_STATUS_2.getValue().equals(checkFlg.getValue())){
						EnumBean ct = new EnumBean();
						ct.setValue(checkFlg.getValue());
						ct.setName(checkFlg.getContent());
						riskStatus.add(ct);
					}
				}
				model.addAttribute("riskStatus", riskStatus);


				List<EnumBean> transType = Lists.newArrayList();
				//冻结类型 1：冻结 2：解冻
				for (TransType checkFlg : TransType.values()) {
					EnumBean ct = new EnumBean();
					ct.setValue(checkFlg.getValue());
					ct.setName(checkFlg.getContent());
					transType.add(ct);
				}
				model.addAttribute("transType", transType);
				model.addAttribute("page", pageRiskProductQuota);
				
				model.addAttribute("riskOrderUnfreeze",riskOrderUnfreeze);
				
				logger.info("订单冻结查询数据为------>{}"+ model);
			return model;
		} catch (Exception e) {
			logger.error("RiskMerchantFreeze  has a error:{订单冻结查询出错 FIND_ERROR }", e);
		}
		logger.error("RiskMerchantFreeze  has a error:{订单冻结查询出错 FIND_ERROR }");
		return null;
	}
}
