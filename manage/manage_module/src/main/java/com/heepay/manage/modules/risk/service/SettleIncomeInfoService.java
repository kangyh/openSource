/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.risk.service;

import com.google.common.collect.Lists;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.ChannelType;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.*;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.risk.dao.SettleIncomeInfoDao;
import com.heepay.manage.modules.risk.entity.SettleIncomeInfo;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

/**
 *
 * 描    述：出入金配置管理Service
 *
 * 创 建 者： @author wangdong
 * 创建时间：
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
public class SettleIncomeInfoService extends CrudService<SettleIncomeInfoDao, SettleIncomeInfo> {

	@Autowired
	private SettleIncomeInfoDao settleIncomeInfoDao;

	public SettleIncomeInfo get(String id) {
		return super.get(id);
	}

	public List<SettleIncomeInfo> findList(SettleIncomeInfo settleIncomeInfo) {
		return super.findList(settleIncomeInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SettleIncomeInfo settleIncomeInfo) {
		if (null != settleIncomeInfo.getIncomeId()){
			settleIncomeInfo.setModifier(UserUtils.getUser().getName());
			super.update(settleIncomeInfo,false);
		}else{
			settleIncomeInfo.setCreator(UserUtils.getUser().getName());
			super.save(settleIncomeInfo,false);
		}
	}

	/**
	 * @方法说明：查询出入金配置list
	 * @时间： 2017/5/17 10:17
	 * @创建人：wangdong
	 */
	public Model findSettleIncomeInfoPage(Page<SettleIncomeInfo> page, SettleIncomeInfo settleIncomeInfo, Model model) throws Exception{
		try {
			Page<SettleIncomeInfo> pageSettleIncomeInfo = findPage(page, settleIncomeInfo);
			List<SettleIncomeInfo> infoList = Lists.newArrayList();
			//循环翻译部分字段
			for (SettleIncomeInfo info : pageSettleIncomeInfo.getList()) {
				if (StringUtils.isNotBlank(info.getIncomeDirection())) {
					info.setIncomeDirection(StringUtils.equals(info.getIncomeDirection(), "in") ? "入金" : "出金");
				}
				if (StringUtils.isNotBlank(info.getBusinessType())) {
					info.setBusinessType(DictUtils.getDictLabel(info.getBusinessType(), "RateBusinessType", null));
				}
				if (StringUtils.isNotBlank(info.getTransType())) {
					info.setTransType(DictUtils.getDictLabel(info.getTransType(), "TransType", null));
				}
				if (StringUtils.isNotBlank(info.getProductCode())) {
					info.setProductCode(DictUtils.getDictLabel(info.getProductCode(), "ProductCodeType", null));
				}
				if (StringUtils.isNotBlank(info.getSettleStatus())) {
					info.setSettleStatus(SettleDifferIsProfit.labelOf(info.getSettleStatus()));
				}
				if (StringUtils.isNotBlank(info.getStatus())) {
					info.setStatus(BillingBecomeEffect.labelOf(info.getStatus()));
				}
				infoList.add(info);
			}
			pageSettleIncomeInfo.setList(infoList);

			//前端页面  类型条件显示
			CommonUtil.enumsShow(model, Constants.Clear.MODEL);

			model.addAttribute("page", pageSettleIncomeInfo);
			model.addAttribute("settleIncomeInfo",settleIncomeInfo);
			return model;
		} catch (Exception e) {
			logger.error("SettleIncomeInfoService findSettleIncomeInfoPage has a error:{查询出入金配置list出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}

	public Integer findExistInfo(SettleIncomeInfo settleIncomeInfo) {
		return settleIncomeInfoDao.findExistInfo(settleIncomeInfo);
	}
}