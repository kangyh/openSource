/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.bossreport.service;

import com.google.common.collect.Lists;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.ChannelType;
import com.heepay.enums.PayType;
import com.heepay.enums.billing.BillingBecomeEffect;
import com.heepay.enums.bossreport.PayTypeReport;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.bossreport.dao.ReportQueryConditionsDao;
import com.heepay.manage.modules.bossreport.entity.ReportQueryConditions;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.service.BankInfoService;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.sys.entity.Dict;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

/**
 *
 * 描    述：报表条件设置Service
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
public class ReportQueryConditionsService extends CrudService<ReportQueryConditionsDao, ReportQueryConditions> {

	@Autowired
	private ReportQueryConditionsDao reportQueryConditionsDao;

	@Autowired
	private BankInfoService bankInfoService;

	public ReportQueryConditions get(String id) {
		return super.get(id);
	}
	
	public List<ReportQueryConditions> findList(ReportQueryConditions reportQueryConditions) {
		return super.findList(reportQueryConditions);
	}
	
	public Page<ReportQueryConditions> findPage(Page<ReportQueryConditions> page, ReportQueryConditions reportQueryConditions) {
		return super.findPage(page, reportQueryConditions);
	}
	
	@Transactional(readOnly = false)
	public void save(ReportQueryConditions reportQueryConditions) {
		if (null != reportQueryConditions.getReportId()){
			reportQueryConditions.setUpdateAuthor(UserUtils.getUser().getName());
			super.update(reportQueryConditions,false);
		}else{
			reportQueryConditions.setCreator(UserUtils.getUser().getName());
			super.save(reportQueryConditions,false);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ReportQueryConditions reportQueryConditions) {
		super.delete(reportQueryConditions);
	}

	public Model findReportQueryConditionsPage(Page<ReportQueryConditions> page, ReportQueryConditions reportQueryConditions, Model model) throws Exception{
		Page<ReportQueryConditions> pageReportQueryConditions = findPage(page, reportQueryConditions);
		List<ReportQueryConditions> infoList = Lists.newArrayList();
		//循环翻译部分字段
		for (ReportQueryConditions info : pageReportQueryConditions.getList()) {
			//支付类型
			if (StringUtils.isNotBlank(info.getPayType())) {
				info.setPayTypeName(PayTypeReport.labelOf(info.getPayType()));
			}
			//java支付类型
			if (StringUtils.isNotBlank(info.getPayTypeJava())) {
				info.setPayTypeNameJava(PayType.getContentByValue(info.getPayTypeJava()));
			}
			//java银行名称
			if (StringUtils.isNotBlank(info.getBankIdJava())) {
				BankInfo bankInfo = bankInfoService.getBankNameByBankNo(info.getBankIdJava());
				if(bankInfo != null){
					info.setBankNameJava(bankInfo.getBankName());
				}
			}
			//java通道合作方
			if (StringUtils.isNotBlank(info.getChannelPartnerJava())) {
				info.setChannelPartnerNameJava(DictUtils.getDictLabel(info.getChannelPartnerJava(), "ChannelPartner", null));
			}
			//公司名称|结算主体

			if (StringUtils.isNotBlank(info.getStatus())) {
				info.setStatus(BillingBecomeEffect.labelOf(info.getStatus()));
			}
			infoList.add(info);
		}
		pageReportQueryConditions.setList(infoList);

		//前端页面  类型条件显示
		List<EnumBean> payTypeList = Lists.newArrayList();  //支付类型
		for (PayTypeReport type : PayTypeReport.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(type.getValue());
			ct.setName(type.getContent());
			payTypeList.add(ct);
		}
		model.addAttribute("payTypeList", payTypeList);

		List<EnumBean> payType = Lists.newArrayList();  //java支付类型
		for (PayType type : PayType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(type.getValue());
			ct.setName(type.getContent());
			payType.add(ct);
		}
		model.addAttribute("payType", payType);

		List<EnumBean> bankList = Lists.newArrayList();  //java银行
		List<BankInfo> list = bankInfoService.selectList();
		for (BankInfo info : list) {
			EnumBean ct = new EnumBean();
			ct.setValue(info.getBankNo());
			ct.setName(info.getBankName());
			bankList.add(ct);
		}
		model.addAttribute("bankList", bankList);

		List<EnumBean> channelList = Lists.newArrayList();  //java通道合作方
		for(Dict dict : DictUtils.getDictList("ChannelPartner")){
			EnumBean ct = new EnumBean();
			ct.setValue(dict.getValue());
			ct.setName(dict.getLabel());
			channelList.add(ct);
		}
		model.addAttribute("channelList", channelList);


		model.addAttribute("page", pageReportQueryConditions);
		model.addAttribute("reportQueryConditions",reportQueryConditions);
		return model;
	}

	public Integer findExistInfo(ReportQueryConditions reportQueryConditions) {
		return reportQueryConditionsDao.findExistInfo(reportQueryConditions);
	}
}