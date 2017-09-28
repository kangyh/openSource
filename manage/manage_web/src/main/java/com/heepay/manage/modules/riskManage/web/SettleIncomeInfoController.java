/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.riskManage.web;

import com.google.common.collect.Lists;
import com.heepay.common.util.Constants;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.merchant.rpc.client.RiskServiceClient;
import com.heepay.manage.modules.risk.entity.SettleIncomeInfo;
import com.heepay.manage.modules.risk.service.SettleIncomeInfoService;
import com.heepay.manage.modules.settle.service.util.CommonUtil;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


/**
 *
 * 描    述：出入金配置管理Controller
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
@RequestMapping(value = "${adminPath}/riskManage/settleIncomeInfo")
public class SettleIncomeInfoController extends BaseController {

	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private SettleIncomeInfoService settleIncomeInfoService;

	@Autowired
	private RiskServiceClient riskServiceClient;

	/**
	 * @方法说明：查询出入金配置列表
	 * @时间： 2017/5/16 16:57
	 * @创建人：wangdong
	 */
	@RequiresPermissions("riskManage:settleIncomeInfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(SettleIncomeInfo settleIncomeInfo, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		model = settleIncomeInfoService
				.findSettleIncomeInfoPage(new Page<SettleIncomeInfo>(request, response), settleIncomeInfo,model);

		List<EnumBean> ioList = Lists.newArrayList();  //结算模式 出入金
		String[] iovalue = {"入金","出金"};
		String[] iokey = {"in","out"};
		for(int i = 0; i < iovalue.length; i++){
			EnumBean ct = new EnumBean();
			ct.setValue(iokey[i]);
			ct.setName(iovalue[i]);
			ioList.add(ct);
		}
		model.addAttribute("ioList", ioList);
		return "modules/riskManage/settleIncomeInfoList";
	}

	/**
	 * @方法说明：跳转出入金加页面
	 * @时间： 2017/5/16 16:57
	 * @创建人：wangdong
	 */
	@RequiresPermissions("riskManage:settleIncomeInfo:view")
	@RequestMapping(value = "form")
	public String form(SettleIncomeInfo settleIncomeInfo, Model model) {
		try {
			if (null != settleIncomeInfo.getIncomeId()){
				settleIncomeInfo = settleIncomeInfoService.get(settleIncomeInfo);
			}
			//前端页面  类型条件显示
			CommonUtil.enumsShow(model, Constants.Clear.MODEL);
			model.addAttribute("settleIncomeInfo", settleIncomeInfo);
			return "modules/riskManage/settleIncomeInfoForm";
		}catch (Exception e){
			logger.error("跳转出入金加页面 出现错误！{FIND_ERROR}--->{}",e);
		}
		return null;
	}

	/**
	 * @方法说明：保存出入金配置
	 * @时间： 2017/5/16 16:58
	 * @创建人：wangdong
	 */
	@RequiresPermissions("riskManage:settleIncomeInfo:edit")
	@RequestMapping(value = "save")
	public String save(SettleIncomeInfo settleIncomeInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		try {
			Integer count = settleIncomeInfoService.findExistInfo(settleIncomeInfo);
			if (count>0){
				model.addAttribute("message","插入信息已存在");
				CommonUtil.enumsShow(model, Constants.Clear.MODEL);
				model.addAttribute("settleIncomeInfo", settleIncomeInfo);
				return "modules/riskManage/settleIncomeInfoForm";
			}else {
				if(null != settleIncomeInfo.getIncomeId()){
					settleIncomeInfo.setModifier(UserUtils.getUser().getName());
					settleIncomeInfo.setUpdateTime(DateUtil.getDate());
					riskServiceClient.editSettleIncomeInfo(JsonMapperUtil.nonEmptyMapper().toJson(settleIncomeInfo));
				}else{
					settleIncomeInfo.setCreator(UserUtils.getUser().getName());
					settleIncomeInfo.setCreateTime(DateUtil.getDate());
					riskServiceClient.addSettleIncomeInfo(JsonMapperUtil.nonEmptyMapper().toJson(settleIncomeInfo));
				}
//				settleIncomeInfoService.save(settleIncomeInfo);
				if (null != settleIncomeInfo.getIncomeId()){
					addMessage(redirectAttributes, "修改配置成功");
				}else {
					addMessage(redirectAttributes, "保存配置成功");
				}
				return "redirect:"+Global.getAdminPath()+"/riskManage/settleIncomeInfo/?repage";
			}
		}catch (Exception e){
			logger.error("保存出入金配置 出现错误！{FIND_ERROR}--->{}",e);
		}
		return null;
	}
	
}