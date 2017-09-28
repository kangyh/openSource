/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.tpds.web;

import com.google.common.collect.Lists;
import com.heepay.codec.Aes;
import com.heepay.common.util.Constants;
import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.tpds.entity.TpdsCustomCharge;
import com.heepay.manage.modules.tpds.service.TpdsCustomChargeService;
import com.heepay.tpds.enums.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * 描    述：客户充值信息Controller
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
@RequestMapping(value = "${adminPath}/cuscharge/tpdsCustomCharge")
public class TpdsCustomChargeController extends BaseController {

	@Autowired
    private TpdsCustomChargeService tpdsCustomChargeService;

    private static final Logger logger= LogManager.getLogger();
	
	@RequiresPermissions("cuscharge:tpdsCustomCharge:view")
	@RequestMapping(value = {"list", ""})
	public String list(TpdsCustomCharge tpdsCustomCharge, HttpServletRequest request, HttpServletResponse response, Model model) {

        //格式化查询时间条件
        Date endTime = tpdsCustomCharge.getEndTime();
        if(endTime!=null){
            String format = new SimpleDateFormat("yyyy-MM-dd").format(endTime);
            format=format+" 23:59:59";
            try {
                Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
                tpdsCustomCharge.setEndTime(time);
            } catch (ParseException e) {
                logger.error("时间转换异常"+e.getMessage());
            }
        }


        Map<String,Object> map = tpdsCustomChargeService.findCountAndSum(tpdsCustomCharge);
        model.addAttribute("totalNum", map.get("totalNum"));
        model.addAttribute("successNum", map.get("successNum"));
        model.addAttribute("totalAmount", map.get("totalAmount"));
        model.addAttribute("successAmount", map.get("successAmount"));

		Page<TpdsCustomCharge> page = tpdsCustomChargeService.findPage(new Page<TpdsCustomCharge>(request, response), tpdsCustomCharge);
		List<TpdsCustomCharge> tpdsList = Lists.newArrayList();
		for (TpdsCustomCharge tpds : page.getList()) {
			if(StringUtils.isNotBlank(tpds.getCurrency())){
				tpds.setCurrency(Currency.labelOf(tpds.getCurrency()));
			}
			if(StringUtils.isNotBlank(tpds.getRaType())){
				tpds.setRaType(RType.labelOf(tpds.getRaType()));
			}
			if(StringUtils.isNotBlank(tpds.getOtherAmounttype())){
				tpds.setOtherAmounttype(OtherAmounttype.labelOf(tpds.getOtherAmounttype()));
			}
			if(StringUtils.isNotBlank(tpds.getPayType())){
				tpds.setPayType(PayType.labelOf(tpds.getPayType()));
			}
			if(StringUtils.isNotBlank(tpds.getCardType())){
				tpds.setCardType(CardType.labelOf(tpds.getCardType()));
            }
            if(StringUtils.isNotBlank(tpds.getBankAccountNo())){
                String bankNo = Aes.decryptStr(tpds.getBankAccountNo(), Constants.QuickPay.SYSTEM_KEY);
                tpds.setBankAccountNo(StringUtil.getEncryptedCardNo(bankNo));
            }
            tpdsList.add(tpds);
        }
        page.setList(tpdsList);
        model.addAttribute("page", page);

        List<EnumBean> rlist = new ArrayList<>();
        for (RType rType : RType.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(rType.getValue());
            ct.setName(rType.getContent());
            rlist.add(ct);
        }
        model.addAttribute("rlist", rlist);

        List<EnumBean> plist = new ArrayList<>();
        for (PayType payType : PayType.values()) {
            EnumBean ct = new EnumBean();
            ct.setValue(payType.getValue());
            ct.setName(payType.getContent());
            plist.add(ct);
        }
        model.addAttribute("plist", plist);

		model.addAttribute("tpdsCustomCharge", tpdsCustomCharge);
		return "modules/tpds/tpdsCustomChargeList";
	}

}