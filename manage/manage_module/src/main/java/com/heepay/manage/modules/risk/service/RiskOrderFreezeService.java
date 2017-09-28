package com.heepay.manage.modules.risk.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.TransType;
import com.heepay.enums.risk.RiskFreezeRemark;
import com.heepay.enums.risk.RiskFreezeStatus;
import com.heepay.enums.risk.RiskFreezeType;
import com.heepay.enums.risk.RiskStatus;
import com.heepay.enums.risk.TransStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.risk.dao.RiskOrderFreezeDao;
import com.heepay.manage.modules.risk.dao.RiskOrderUnfreezeDao;
import com.heepay.manage.modules.risk.entity.RiskOrderFreeze;
import com.heepay.manage.modules.risk.entity.RiskOrderUnfreeze;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.risk.util.DefreezeNo;


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
public class RiskOrderFreezeService extends CrudService<RiskOrderFreezeDao, RiskOrderFreeze> {
	
	@Autowired
	private RiskOrderFreezeDao riskOrderFreezeDao;
	
	@Autowired
	private RiskOrderUnfreezeDao riskOrderUnfreezeDao;

	public Model findRiskMerchantProductQuotaPage(Page<RiskOrderFreeze> page, RiskOrderFreeze riskOrderFreeze,Model model) {


		try {
			Page<RiskOrderFreeze> pageRiskProductQuota = findPage(page, riskOrderFreeze);
			logger.info("订单冻结查询数据为------>{}"+ pageRiskProductQuota.getList());
			
			List<RiskOrderFreeze> clearingCRList = Lists.newArrayList();
			for (RiskOrderFreeze clearingCR : pageRiskProductQuota.getList()) {
				
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
					EnumBean ct = new EnumBean();
					ct.setValue(checkFlg.getValue());
					ct.setName(checkFlg.getContent());
					riskStatus.add(ct);
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
				
				model.addAttribute("riskOrderFreeze",riskOrderFreeze);
				
				logger.info("订单冻结查询数据为------>{}"+ model);
			return model;
		} catch (Exception e) {
			logger.error("RiskMerchantFreeze  has a error:{订单冻结查询出错 FIND_ERROR }", e);
		}
		logger.error("RiskMerchantFreeze  has a error:{订单冻结查询出错 FIND_ERROR }");
		return null;
	}

	/**
	 * 
	 * @方法说明：添加冻结订单
	 * @时间：Nov 21, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int saveEntity(RiskOrderFreeze riskOrderFreeze) {
		
		int num=0;
		try {
			num = riskOrderFreezeDao.saveEntity(riskOrderFreeze);
			logger.info("订单冻结查询查询数据为------>{}"+ num);
		} catch (Exception e) {
			logger.error("RiskMerchantFreeze  has a error:{订单冻结查询List出错 FIND_ERROR }", e);
		}
		return num;
	}

	/**
	 * 
	 * @方法说明：订单冻结冻结和取消操作
	 * @时间：Nov 22, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int updateEntity(RiskOrderFreeze riskOrderFreeze) {
		
		int num=0;
		try {
			logger.info("订单冻结冻结和取消操作开始--------->{操作条件:}"+riskOrderFreeze.toString());
			num=riskOrderFreezeDao.updateEntity(riskOrderFreeze);
		} catch (Exception e) {
			logger.error("RiskMerchantFreeze has a error:{订单冻结冻结和取消操作_ERROR }", e);
		}
		return num;
	}

	/**
	 * 
	 * @param freezeId 
	 * @方法说明：根据id查询整个对象
	 * @时间：Nov 22, 2016
	 * @创建人：wangl
	 */
	public RiskOrderFreeze getEntityById(int freezeId) {
		
		logger.info("订单冻结解冻开始--------->{操作条件:}"+freezeId);
		RiskOrderFreeze riskOrderFreeze = riskOrderFreezeDao.getEntityById(freezeId);
		logger.info("订单冻结解冻结束--------->{}"+riskOrderFreeze);
		 return riskOrderFreeze;
	}

	/**
	 * 
	 * @方法说明：订单冻结解冻操作
	 * @时间：Nov 22, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int updateEntityToJd(RiskOrderFreeze riskOrderFreeze) {
		int num=0;
		try {
			
			logger.info("订单冻结解冻开始--------->{操作条件:}"+riskOrderFreeze.toString());
			
			User user = UserUtils.getUser();
			String name = user.getLoginName();
			logger.info("当前登录用户-------->{}" + name);
			riskOrderFreeze.setCreateAuthor(name);// 添加当前操作的用户名  freeze_type
			riskOrderFreeze.setFreezeType(RiskFreezeType.RISK_FREEZE_TYPE_2.getValue());//冻结类型
			riskOrderFreeze.setDefreezeNo(DefreezeNo.getRandomString());//解冻编号
			riskOrderFreeze.setStatus(RiskFreezeStatus.RISK_RREEZE_STATUS_1.getValue());//解冻成功状态  
			riskOrderFreeze.setUpdateTime(new Date());//更新时间 
			riskOrderFreeze.setUpdateAuthor(name);
			
			//xiugai  是根据交易的返回判断的
			riskOrderFreeze.setTransStatus(TransStatus.TRANS_TYPE_Y.getValue());//Y 成功 或 N 失败
			num=riskOrderFreezeDao.updateEntityToJd(riskOrderFreeze);
			
			//将解冻输出插入RiskOrderUnfreeze表中
			RiskOrderUnfreeze riskOrderUnfreeze=new RiskOrderUnfreeze();
			riskOrderUnfreeze.setUpdateAuthor(name);//操作的用户  
			riskOrderUnfreeze.setUnfreezeRemark(riskOrderFreeze.getFreezeRemark());//冻结原因 
			riskOrderUnfreeze.setUpdateTime(new Date());//更新时间
			riskOrderUnfreeze.setUnfreezeNo(riskOrderFreeze.getDefreezeNo());//解冻单号
			riskOrderUnfreeze.setFreezeNo(riskOrderFreeze.getFreezeNo());// 冻解单号  defreeze_no的随机数  status
			
			//xiugai  是根据交易的返回判断的
			riskOrderUnfreeze.setStatus(RiskFreezeStatus.RISK_RREEZE_STATUS_1.getValue());//解冻成功状态  
			
			riskOrderUnfreezeDao.insert(riskOrderUnfreeze);
		} catch (Exception e) {
			logger.error("RiskMerchantFreeze has a error:{订单冻结 -->解冻操作_ERROR }", e);
		}
		return num;
	}

	/**
	 * 
	 * @方法说明：修改信息
	 * @时间：Nov 23, 2016
	 * @创建人：wangl
	 */
	@Transactional(readOnly = false)
	public int updateOrder(RiskOrderFreeze riskOrderFreeze) {
		int num=0;
		try {
			logger.info("订单冻结冻结和取消操作开始--------->{操作条件:}"+riskOrderFreeze.toString());
			num=riskOrderFreezeDao.updateOrder(riskOrderFreeze);
		} catch (Exception e) {
			logger.error("RiskMerchantFreeze has a error:{订单冻结冻结和取消操作_ERROR }", e);
		}
		return num;
	}
}
