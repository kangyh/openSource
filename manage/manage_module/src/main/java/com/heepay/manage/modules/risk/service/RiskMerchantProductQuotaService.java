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
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.risk.dao.RiskMerchantProductQuotaDao;
import com.heepay.manage.modules.risk.entity.RiskMerchantProductQuota;
import com.heepay.manage.modules.risk.entity.RiskOrder;
import com.heepay.manage.modules.riskLogs.service.RiskLogsService;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.sys.utils.UserUtils;

/**
 * 
 *
 * 描    述：商户产品限额Service
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
public class RiskMerchantProductQuotaService extends CrudService<RiskMerchantProductQuotaDao, RiskMerchantProductQuota> {

	@Autowired
	private RiskMerchantProductQuotaDao riskMerchantProductQuotaDao;
	
	@Autowired
	private MerchantCService merchantCService;
	
	@Autowired
	private RiskLogsService riskLogsService;//记录日志
	
	/**
	 * @方法说明：根据id获取通商户产品限额信息
	 * @author wangdong
	 * @时间： 2016年11月18日17:27:49
	 */
	public RiskMerchantProductQuota get(Long id) {
		return riskMerchantProductQuotaDao.get(id);
	}
	
	/**
	 * @方法说明：获取商户产品限额信息List
	 * @author wangdong
	 * @时间：2016年11月18日17:27:53
	 */
	public List<RiskMerchantProductQuota> findList(RiskMerchantProductQuota riskMerchantProductQuota) {
		return super.findList(riskMerchantProductQuota);
	}
	
	/**
	 * 
	 * @方法说明：获取商户产品限额信息List
	 * @时间：2016年11月18日 下午5:37:43
	 * @创建人：wangdong
	 */
	public Model findRiskMerchantProductQuotaPage(Page<RiskMerchantProductQuota> page,
			RiskMerchantProductQuota riskMerchantProductQuota, Model model) throws Exception{
		try {
			Page<RiskMerchantProductQuota> pageRiskProductQuota = findPage(page, riskMerchantProductQuota);
			List<RiskMerchantProductQuota> riskMPList = Lists.newArrayList();
			for(RiskMerchantProductQuota mer : pageRiskProductQuota.getList()){
				//账户类型
				if(StringUtils.isNotBlank(mer.getStatus())){
					mer.setAccountType(AccountType.labelOf(mer.getAccountType()));
				}
				//银行卡类型
				if(StringUtils.isNotBlank(mer.getBankcardType())){
					mer.setBankcardType(BankcardType.labelOf(mer.getBankcardType()));
				}
				//状态
				if(StringUtils.isNotBlank(mer.getStatus())){
					mer.setStatus(RiskMerChantStatus.labelOf(mer.getStatus()));
				}
				riskMPList.add(mer);
			}
			pageRiskProductQuota.setList(riskMPList);
			
			CommonUtil.enumsShow(model,Constants.Clear.SETTLE);
			
			//操作人查询条件
			List<RiskOrder> uAList = riskMerchantProductQuotaDao.findUpdateAuthor();
			model.addAttribute("uAList", uAList);
			
			model.addAttribute("page", pageRiskProductQuota);
			model.addAttribute("riskMerchantProductQuota",riskMerchantProductQuota);
			return model;
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaService findRiskMerchantProductQuotaPage has a error:{商户产品限额查询List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @方法说明：修改商户产品限额数据页面跳转
	 * @时间：2016年11月18日 下午6:59:22
	 * @创建人：wangdong
	 */
	public Model goToRiskMerchantProductQuotaEditJsp(RiskMerchantProductQuota riskMerchantProductQuota, Model model) throws Exception{
		try {
			//修改查询当前商户限额信息
			if(null != riskMerchantProductQuota && null != riskMerchantProductQuota.getMerProId()){
				//根据主键查询类型信息
				riskMerchantProductQuota = get(riskMerchantProductQuota.getMerProId().toString());
				model.addAttribute("riskMerchantProductQuota", riskMerchantProductQuota);
			}
			CommonUtil.enumsShow(model,Constants.Clear.RISK);
			return model;
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaService goToSettleDicTypeAddJsp has a error:{修改商户产品限额数据页面跳转错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @param request 
	 * @方法说明：保存修改商户产品限额信息
	 * @时间：2016年11月18日 下午7:26:17
	 * @创建人：wangdong
	 */
	@Transactional(readOnly = false)
	public void saveRiskMerchantProductQuota(RiskMerchantProductQuota riskMerchantProductQuota, HttpServletRequest request) throws Exception{
		String message = "";
		try {
			if (null != riskMerchantProductQuota.getMerProId()){
				riskMerchantProductQuota.setUpdateAuthor(UserUtils.getUser().getName());
				riskMerchantProductQuotaDao.update(riskMerchantProductQuota);
				message = "更新";
				//记录日志
				riskLogsService.savaEntity("更新", "更新商户产品限额信息:商户编码:"+riskMerchantProductQuota.getMerchantId()+",产品名称:"+riskMerchantProductQuota.getProductName(), request);
			}else{
				//根据商户号查询商户的信息
				Merchant merchant = merchantCService.get(riskMerchantProductQuota.getMerchantId().toString());
				if(null != merchant){
					riskMerchantProductQuota.setMerchantAccount(merchant.getEmail());//商户账户
					riskMerchantProductQuota.setMerchantName(merchant.getCompanyName());//商户名称
				}
				//如果是对公的，银行类型设置为null
				if(StringUtils.isBlank(riskMerchantProductQuota.getBankcardType())){
					riskMerchantProductQuota.setBankcardType(null);
				}
				riskMerchantProductQuota.setCreateTime(new Date());
				riskMerchantProductQuota.setCreateAuthor(UserUtils.getUser().getName());
				riskMerchantProductQuotaDao.insert(riskMerchantProductQuota);
				message = "插入";
				//记录日志
				riskLogsService.savaEntity("插入", "商户产品限额信息插入:商户编码:"+riskMerchantProductQuota.getMerchantId()+",产品名称:"+riskMerchantProductQuota.getProductName(), request);
			}
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaService saveRiskMerchantProductQuota has a error:{"+message+"商户产品限额信息错误  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @方法说明：验证是否存在相同商户号，产品编码，账户类型，银行卡类型
	 * @时间：2016年11月22日 上午10:07:33
	 * @创建人：wangdong
	 */
	public Integer findRiskMerchantProductQuotaExist(RiskMerchantProductQuota riskMerchantProductQuota) throws Exception{
		try {
			return riskMerchantProductQuotaDao.findRiskMerchantProductQuotaExist(riskMerchantProductQuota);
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaService findRiskMerchantProductQuotaExist has a error:{验证是否存在相同商户号，产品编码，账户类型，银行卡类型错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * 
	 * @方法说明：存在相同商户号，产品编码，账户类型，银行卡类型
	 * @时间：2016年11月22日 上午10:15:54
	 * @创建人：wangdong
	 */
	public Model riskMerchantProductQuotaExist(RiskMerchantProductQuota riskMerchantProductQuota, Model model) throws Exception{
		try {
			model = goToRiskMerchantProductQuotaEditJsp(riskMerchantProductQuota, model);
			model.addAttribute("riskMerchantProductQuota", riskMerchantProductQuota);
			model.addAttribute("message",
					"保存的商户限额，产品名称为：" + riskMerchantProductQuota.getProductName() + "  的重复！");
			return model;
		} catch (Exception e) {
			logger.error("RiskMerchantProductQuotaService riskMerchantProductQuotaExist has a error:{存在相同商户号，产品编码，账户类型，银行卡类型错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
}
