/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.tpds.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtil;
import com.heepay.manage.modules.tpds.entity.TpdsCustomCharge;
import com.heepay.manage.modules.tpds.service.TpdsBankWithdrawService;
import com.heepay.tpds.enums.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.modules.tpds.entity.TpdsBankWithdraw;

import java.util.List;
import java.util.Map;


/**
 *
 * 描    述：存管银行提现记录Controller
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
@Controller
@RequestMapping(value = "${adminPath}/tpds/tpdsBankWithdraw")
public class TpdsBankWithdrawController extends BaseController {

	@Autowired
	private TpdsBankWithdrawService tpdsBankWithdrawService;
	
	@RequiresPermissions("tpds:tpdsBankWithdraw:view")
	@RequestMapping(value = {"list", ""})
	public String list(TpdsBankWithdraw tpdsBankWithdraw, HttpServletRequest request, HttpServletResponse response, Model model) {

	    Map<String,Object> map = tpdsBankWithdrawService.findCountAndSum(tpdsBankWithdraw);
        model.addAttribute("totalAmount", map.get("totalAmount"));

	    Page<TpdsBankWithdraw> page = tpdsBankWithdrawService.findPage(new Page<TpdsBankWithdraw>(request, response), tpdsBankWithdraw);
		List<TpdsBankWithdraw> tpdsList = Lists.newArrayList();
		for (TpdsBankWithdraw tpds : page.getList()) {
			if(StringUtils.isNotBlank(tpds.getCurrency())){
				tpds.setCurrency(Currency.labelOf(tpds.getCurrency()));
			}
			if(StringUtils.isNotBlank(tpds.getRaType())){
				tpds.setRaType(WType.labelOf(tpds.getRaType()));
			}
			if(StringUtils.isNotBlank(tpds.getBankAccountNo())){
				String bankNo = Aes.decryptStr(tpds.getBankAccountNo(), Constants.QuickPay.SYSTEM_KEY);
				tpds.setBankAccountNo(StringUtil.getEncryptedCardNo(bankNo));
			}
			tpdsList.add(tpds);
		}
		page.setList(tpdsList);
		model.addAttribute("page", page);
		model.addAttribute("tpdsBankWithdraw", tpdsBankWithdraw);
		return "modules/tpds/tpdsBankWithdrawList";
	}

}