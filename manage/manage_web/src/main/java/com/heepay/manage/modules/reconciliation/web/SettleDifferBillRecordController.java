package com.heepay.manage.modules.reconciliation.web;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.entity.SettleBillRecord;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferBillRecord;
import com.heepay.manage.modules.reconciliation.service.SettleBillRecordService;
import com.heepay.manage.modules.reconciliation.service.SettleDifferBillRecordService;
import com.heepay.manage.modules.reconciliation.util.ChannelTypeClear;
import com.heepay.manage.modules.reconciliation.web.util.DateUtil;
import com.heepay.manage.modules.reconciliation.web.util.DifferType;
import com.heepay.manage.modules.reconciliation.web.util.StringEncrypt;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.service.BankInfoService;
import com.heepay.manage.modules.sys.utils.DictUtils;
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
import java.util.Date;
import java.util.List;

/**
 * 
 *
 * 描    述：异常明细表Controller
 *
 * 创 建 者：   wangl
 * 创建时间：2017年05月08日 下午1:09:26
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
@RequestMapping(value = "${adminPath}/reconciliation/settleDifferBillRecord")
public class SettleDifferBillRecordController extends BaseController {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private SettleDifferBillRecordService settleDifferBillRecordService;

	@Autowired
	private SettleBillRecordService settleBillRecordService;

	@Autowired
	private BankInfoService bankInfoService;

	//通道合作方
	public static final String CHANNEL_PARTNER = "ChannelPartner";

	@RequiresPermissions("settle:settleDifferBillRecord:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleDifferBillRecord settleDifferBillRecord,
					   HttpServletRequest request,
					   HttpServletResponse response, Model model){


		//使用SHA-256加密
		if(StringUtils.isNoneBlank(settleDifferBillRecord.getRemark())){
			String encrypt = StringEncrypt.Encrypt(settleDifferBillRecord.getRemark(), null);
			settleDifferBillRecord.setRemark(encrypt);
		}

		if(null == settleDifferBillRecord.getBeginOperEndTime() && null == settleDifferBillRecord.getEndOperEndTime()){
			String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			try {
				settleDifferBillRecord.setBeginOperEndTime(DateUtil.beginTime(format));
				settleDifferBillRecord.setEndOperEndTime(DateUtil.endTime(format));
			} catch (ParseException e) {
			}
		}
		logger.info("异常明细表Controller--->{查询}--->{条件}"+settleDifferBillRecord);
		Page<SettleDifferBillRecord> page = settleDifferBillRecordService.findPage(new Page<SettleDifferBillRecord>(request, response),settleDifferBillRecord);


		List<SettleBillRecord> list = settleBillRecordService.getChannelCodeEnum();
		//通道编码
		List<EnumBean> channelCodeEnum = Lists.newArrayList();
		for (SettleBillRecord clearingCR : list) {
			EnumBean ct = new EnumBean();
			ct.setValue(clearingCR.getChannelCode());
			//为页面回显返回数据，查分组合命名
			String channelCode = clearingCR.getChannelCode();
			//将组合命名的数据拆分开
			int lastIndexOf = channelCode.lastIndexOf("-");
			if(lastIndexOf !=-1 ){
				String  substring= channelCode.substring(0,lastIndexOf);
				String  bankName= channelCode.substring(lastIndexOf+1,channelCode.length());
				//组合查询
				BankInfo bankNameByBankNo = bankInfoService.getBankNameByBankNo(bankName);
				String name= DictUtils.getDictLabel(substring, CHANNEL_PARTNER, "");

				ct.setName(name+"-"+bankNameByBankNo.getBankName());
			}else{
				ct.setName(DictUtils.getDictLabel(channelCode, CHANNEL_PARTNER, ""));
			}

			channelCodeEnum.add(ct);
		}
		model.addAttribute("channelCodeEnum", channelCodeEnum);

		//查询通道合作方的内容
		List<EnumBean> dataEntityChannelType = ChannelTypeClear.getList();
		model.addAttribute("channelType", dataEntityChannelType);

		//差异类型
		List<EnumBean> differType = Lists.newArrayList();
		for (DifferType checkFlg : DifferType.values()) {
			EnumBean ct = new EnumBean();
			ct.setValue(checkFlg.getValue());
			ct.setName(checkFlg.getContent());
			differType.add(ct);
		}
		model.addAttribute("differType", differType);

		for (SettleDifferBillRecord clearingCR : page.getList()) {
			// 通道编码
			if (StringUtils.isNotBlank(clearingCR.getChannelCode())) {
				//为页面回显返回数据，查分组合命名
				String channelCode = clearingCR.getChannelCode();
				try {
					if(StringUtils.isNotBlank(channelCode)){
						//将组合命名的数据拆分开
						int lastIndexOf = channelCode.lastIndexOf("-");
						if(lastIndexOf !=-1 ){
							String  substring= channelCode.substring(0,lastIndexOf);
							String  bankName= channelCode.substring(lastIndexOf+1,channelCode.length());
							//组合查询
							BankInfo bankNameByBankNo = bankInfoService.getBankNameByBankNo(bankName);
							String name= DictUtils.getDictLabel(substring, CHANNEL_PARTNER, "");
							clearingCR.setChannelCode(name+"-"+bankNameByBankNo.getBankName());
						}else{
							clearingCR.setChannelCode(DictUtils.getDictLabel(channelCode, CHANNEL_PARTNER, ""));
						}
					}
				} catch (Exception e) {
					logger.error("转换失败------>{}"+channelCode,e);
				}
			}
			// 通道业务类型
			if (StringUtils.isNotBlank(clearingCR.getChannelType())) {
				clearingCR.setChannelType(ChannelTypeClear.labelOf(clearingCR.getChannelType()));
			}
			// 差异类型
			if (StringUtils.isNotBlank(clearingCR.getDifferType())) {
				clearingCR.setDifferType(DifferType.labelOf(clearingCR.getDifferType()));
			}
		}

		model.addAttribute("settleDifferBillRecord", settleDifferBillRecord);
		model.addAttribute("page", page);

		return "modules/reconciliation/settleDifferBillRecord";
	}
}