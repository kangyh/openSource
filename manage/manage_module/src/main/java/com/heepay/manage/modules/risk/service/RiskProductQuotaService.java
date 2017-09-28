package com.heepay.manage.modules.risk.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.common.collect.Lists;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.AccountType;
import com.heepay.enums.BankcardType;
import com.heepay.enums.risk.RiskMerChantStatus;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.risk.dao.RiskProductQuotaDao;
import com.heepay.manage.modules.risk.entity.RiskProductQuota;
import com.heepay.manage.modules.riskLogs.service.RiskLogsService;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
 * 
 *
 * 描    述：产品限额Service
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年11月18日 下午4:55:49
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
public class RiskProductQuotaService extends CrudService<RiskProductQuotaDao, RiskProductQuota> {
	
	@Autowired
	private RiskProductQuotaDao riskProductQuotaDao;
	
	@Autowired
	private RiskLogsService riskLogsService;//记录日志
	
	/**
	 * @方法说明：根据id获取通产品限额信息
	 * @author wangdong
	 * @时间： 2016年11月18日17:27:49
	 */
	public RiskProductQuota get(Long id) {
		return riskProductQuotaDao.get(id);
	}
	
	/**
	 * @方法说明：获取产品限额信息List
	 * @author wangdong
	 * @时间：2016年11月18日17:27:53
	 */
	public List<RiskProductQuota> findList(RiskProductQuota riskProductQuota) {
		return super.findList(riskProductQuota);
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：产品限额查询List
	 * @时间：2016年11月18日 下午5:24:50
	 * @创建人：wangdong
	 */
	public Model findRiskProductQuotaPage(Page<RiskProductQuota> page, RiskProductQuota riskProductQuota, Model model) throws Exception {
		try {
			Page<RiskProductQuota> pageRiskProductQuota = findPage(page, riskProductQuota);
			List<RiskProductQuota> iskProductList = Lists.newArrayList();
			//翻译产品限额中的状态
			for(RiskProductQuota pro : pageRiskProductQuota.getList()){
				//账户类型
				if(StringUtils.isNotBlank(pro.getAccountType())){
					pro.setAccountType(AccountType.labelOf(pro.getAccountType()));
				}
				//银行卡类型
				if(StringUtils.isNotBlank(pro.getBankcardType())){
					 pro.setBankcardType(BankcardType.labelOf(pro.getBankcardType()));
				}
				//状态
				if(StringUtils.isNotBlank(pro.getStatus())){
					pro.setStatus(RiskMerChantStatus.labelOf(pro.getStatus()));
				}
				iskProductList.add(pro);
			}
			pageRiskProductQuota.setList(iskProductList);
			
			//显示产品名称  从产品表中查询
			
			
			
			CommonUtil.enumsShow(model,Constants.Clear.RISK);
			model.addAttribute("page", pageRiskProductQuota);
			model.addAttribute("riskProductQuota",riskProductQuota);
			return model;
		} catch (Exception e) {
			logger.error("RiskProductQuotaService findRiskProductQuotaPage has a error:{产品限额查询List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：添加/修改产品限额页面跳转
	 * @时间：2016年11月18日 下午8:14:17
	 * @创建人：wangdong
	 */
	public Model goToRiskProductQuotaAddOrEditJsp(RiskProductQuota riskProductQuota, Model model) throws Exception {
		try {
			//修改查询当前商户限额信息
			if(null != riskProductQuota && null != riskProductQuota.getProId()){
				riskProductQuota = get(riskProductQuota.getProId().toString());
				model.addAttribute("riskProductQuota", riskProductQuota);
			}
			CommonUtil.enumsShow(model,Constants.Clear.RISK);
			return model;
		} catch (Exception e) {
			logger.error("RiskProductQuotaService goToRiskProductQuotaAddOrEditJsp has a error:{修改商户产品限额数据页面跳转错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @param request 
	 * @throws Exception 
	 * @方法说明：添加/修改产品限额数据
	 * @时间：2016年11月18日 下午8:21:16
	 * @创建人：wangdong
	 */
	@Transactional(readOnly = false)
	public void saveRiskProductQuota(RiskProductQuota riskProductQuota, HttpServletRequest request) throws Exception {
		String message = "";
		try {
			if (null != riskProductQuota.getProId()){
				riskProductQuota.setUpdateAuthor(UserUtils.getUser().getName());
				riskProductQuotaDao.update(riskProductQuota);
				message = "更新";
				//记录日志
				riskLogsService.savaEntity("更新", "产品限额数据更新:产品名称:"+riskProductQuota.getProductName(), request);
			}else{
				//如果是对公的，银行类型设置为null
				if(StringUtils.isBlank(riskProductQuota.getBankcardType())){
					riskProductQuota.setBankcardType(null);
				}
				riskProductQuota.setCreateTime(new Date());
				riskProductQuota.setCreateAuthor(UserUtils.getUser().getName());
				riskProductQuotaDao.insert(riskProductQuota);
				message = "插入";
				//记录日志
				riskLogsService.savaEntity("插入", "产品限额数据插入:产品名称:"+riskProductQuota.getProductName(), request);
			}
		} catch (Exception e) {
			logger.error("RiskProductQuotaService saveRiskProductQuota has a error:{"+message+"产品限额数据错误  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：查询是否存在相同产品和银行卡类型
	 * @时间：2016年11月21日 上午11:29:43
	 * @创建人：wangdong
	 */
	public Integer findByRiskProductQuotaExist(RiskProductQuota riskProductQuota) throws Exception {
		try {
			return riskProductQuotaDao.findByRiskProductQuotaExist(riskProductQuota);
		} catch (Exception e) {
			logger.error("RiskProductQuotaService findByRiskProductQuotaExist has a error:{查询是否存在相同产品和银行卡类型错误  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @throws Exception 
	 * @方法说明：存在相同产品和银行卡类型
	 * @时间：2016年11月21日 上午11:32:40
	 * @创建人：wangdong
	 */
	public Model riskProductQuotaExist(RiskProductQuota riskProductQuota, Model model) throws Exception {
		try {
			//银行卡类型
			CommonUtil.enumsShow(model,Constants.Clear.RISK);
			model.addAttribute("riskProductQuota", riskProductQuota);
			model.addAttribute("message",
					"保存的默认限额，产品名称为：" + riskProductQuota.getProductName() + " 的重复！");
			return model;
		} catch (Exception e) {
			logger.error("RiskProductQuotaService riskProductQuotaExist has a error:{存在相同产品和银行卡类型错误  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
}
