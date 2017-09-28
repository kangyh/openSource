package com.heepay.manage.modules.risk.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.risk.RiskFreezeRemark;
import com.heepay.enums.risk.RiskFreezeType;
import com.heepay.enums.risk.RiskStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.risk.dao.RiskMerchantFreezeDao;
import com.heepay.manage.modules.risk.entity.RiskMerchantFreeze;


/***
 * 
* 
* 描    述：商户冻结/解冻表 服务
*
* 创 建 者：wangl
* 创建时间：  Nov 18, 20167:09:43 PM
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
public class RiskMerchantFreezeService extends CrudService<RiskMerchantFreezeDao, RiskMerchantFreeze> {
	
	
	@Autowired
	private RiskMerchantFreezeDao riskMerchantFreezeDao;

	/**
	 * 
	 * @方法说明：商户冻结显现页面查询
	 * @时间：Nov 18, 2016
	 * @创建人：wangl
	 */
	public Model findRiskMerchantProductQuotaPage(Page<RiskMerchantFreeze> page,RiskMerchantFreeze riskMerchantFreeze, Model model) {

		try {
			Page<RiskMerchantFreeze> pageRiskProductQuota = findPage(page, riskMerchantFreeze);
			
			List<RiskMerchantFreeze> clearingCRList = Lists.newArrayList();
			
			logger.info("分润明细查询数据为------>{}"+ page.getList());
			for (RiskMerchantFreeze clearingCR : pageRiskProductQuota.getList()) {
				
					//冻结/解冻状态 0:待审核 1：已冻结 2：已解冻 3：冻结失败 4：解冻失败
					if(StringUtils.isNotBlank(clearingCR.getStatus())){
						clearingCR.setStatus(RiskStatus.labelOf(clearingCR.getStatus()));
					}
					//冻结类型 1：冻结 2：解冻
					if(StringUtils.isNotBlank(clearingCR.getFreezeType())){
						clearingCR.setFreezeType(RiskFreezeType.labelOf(clearingCR.getFreezeType()));
					}
					//冻结/解冻原因 freeze_remark
					if(StringUtils.isNotBlank(clearingCR.getFreezeRemark())){
						clearingCR.setFreezeRemark(RiskFreezeRemark.labelOf(clearingCR.getFreezeRemark()));
					}
					
					clearingCRList.add(clearingCR);
				}
				pageRiskProductQuota.setList(clearingCRList);
				
				
				//冻结/解冻状态 0:待审核 1：已冻结 2：已解冻 3：冻结失败 4：解冻失败
				List<EnumBean> riskStatus = Lists.newArrayList();
				for (RiskStatus checkFlg : RiskStatus.values()) {
					EnumBean ct = new EnumBean();
					ct.setValue(checkFlg.getValue());
					ct.setName(checkFlg.getContent());
					riskStatus.add(ct);
				}
				model.addAttribute("riskStatus", riskStatus);


				List<EnumBean> riskFreezeType = Lists.newArrayList();
				//冻结类型 1：冻结 2：解冻
				for (RiskFreezeType checkFlg : RiskFreezeType.values()) {
					EnumBean ct = new EnumBean();
					ct.setValue(checkFlg.getValue());
					ct.setName(checkFlg.getContent());
					riskFreezeType.add(ct);
				}
				model.addAttribute("riskFreezeType", riskFreezeType);
				
				
				//冻结/解冻原因 freeze_remark
				List<EnumBean> riskFreezeRemark = Lists.newArrayList();
				for (RiskFreezeRemark checkFlg : RiskFreezeRemark.values()) {
					EnumBean ct = new EnumBean();
					ct.setValue(checkFlg.getValue());
					ct.setName(checkFlg.getContent());
					riskFreezeRemark.add(ct);
				}
				model.addAttribute("riskFreezeRemark", riskFreezeRemark);
				
			model.addAttribute("page", pageRiskProductQuota);
			model.addAttribute("riskMerchantFreeze",riskMerchantFreeze);
			return model;
		} catch (Exception e) {
			logger.error("RiskProductQuotaService findRiskProductQuotaPage has a error:{产品限额查询List出错 FIND_ERROR }", e);
		}
		logger.error("RiskProductQuotaService findRiskProductQuotaPage has a error:{产品限额查询List出错 FIND_ERROR }", "产品限额查询List出错  返回值为null");
		return null;
	}

	/**
	 * 
	 * @方法说明：商户冻结规则添加
	 * @时间：Nov 21, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int saveEntity(RiskMerchantFreeze riskMerchantFreeze) {
		int num=0;
		try {
			num = riskMerchantFreezeDao.saveEntity(riskMerchantFreeze);
		} catch (Exception e) {
			logger.error("RiskProductQuotaService findRiskProductQuotaPage has a error:{商户冻结规则添加异常}", e);
		}
		return num;
	}

	
	/**
	 * 
	 * @方法说明：冻结和取消操作
	 * @时间：Nov 21, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int updateEntity(RiskMerchantFreeze riskMerchantFreeze) {
		
		int num=0;
		try {
			num=riskMerchantFreezeDao.updateEntity(riskMerchantFreeze);
		} catch (Exception e) {
			logger.error("RiskProductQuotaService findRiskProductQuotaPage has a error:{商户冻结规则冻结或取消失败}", e);
		}
		return num;
	}

	/**
	 * 
	 * @方法说明：根据id查询整个对象发给交易
	 * @时间：Nov 23, 2016
	 * @创建人：wangl
	 */
	public RiskMerchantFreeze getEntity(int freezeId) {
		
		return riskMerchantFreezeDao.getEntity(freezeId);
	}

	/**
	 * 
	 * @方法说明：文件导出查询所有记录list
	 * @时间：2016年10月20日
	 * @创建人：wangl
	 */
	public List<Map<String, Object>> findListExcel(RiskMerchantFreeze riskMerchantFreeze) {
		
		return riskMerchantFreezeDao.findListExcel(riskMerchantFreeze);
	}

	/**
	 * 
	 * @方法说明：将取出的交易订单号查询看有没有冻结记录
	 * @时间：Dec 5, 2016
	 * @创建人：wangl
	 */
	public boolean getRemark1(String transNo2) {
		// TODO Auto-generated method stub
		return riskMerchantFreezeDao.getRemark1(transNo2);
	}
	
}
