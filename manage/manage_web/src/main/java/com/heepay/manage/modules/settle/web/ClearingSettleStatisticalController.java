package com.heepay.manage.modules.settle.web;

import com.google.common.collect.Lists;
import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.enums.billing.BillingCheckDate;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.entity.SettleChannelLog;
import com.heepay.manage.modules.reconciliation.service.SettleChannelLogService;
import com.heepay.manage.modules.reconciliation.util.ChannelTypeClear;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 
 *
 * 描    述：清结算统计功能
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年1月16日 上午10:22:37
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
@RequestMapping(value = "${adminPath}/settle/clearingSettleStatisticalQuery")
public class ClearingSettleStatisticalController extends BaseController {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private SettleChannelLogService settleChannelLogService;
	
	/**
	 * @方法说明：页面跳转
	 * @时间：2016年9月19日
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settle:clearingSettleStatistical:view")
	@RequestMapping(value = { "list", "" })
	public String list(ClearingChannelRecord clearingChannelRecord, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception{
		try {
			List<SettleChannelLog> logList = settleChannelLogService.findChannelCodeAndName();
			List<EnumBean> codeList = Lists.newArrayList();
			//遍历通道业务类型
			for (SettleChannelLog code : logList) {
				EnumBean ct = new EnumBean();
				ct.setValue(code.getChannelCode());
				if(code.getChannelName().indexOf("-")!=-1){
					code.setChannelName(code.getChannelName().split("-")[1]);
				}
				ct.setName(code.getChannelName());
				codeList.add(ct);
			}
			model.addAttribute("channelCode", codeList);
			
			return "modules/settle/clearingSettleStatistical";
		} catch (Exception e) {
			logger.error("ClearingChannelRecordController list has a error:{查询通道清算记录List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * @方法说明：根据时间和描述查询对账日志信息表的统计信息
	 * @时间：2016年10月13日 
	 * @创建人：wangdong
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "getInfo")
	public List<SettleChannelLog> getInfo(HttpServletRequest request,HttpServletResponse response, Model model) throws Exception{
		try {
			SettleChannelLog settleChannelLog = new SettleChannelLog();
			String checkDate = request.getParameter("checkDate");
			String channelCode = request.getParameter("channelCode");
			//时间段
			if(StringUtils.isNotBlank(checkDate)){
				//周
				if(StringUtils.equals(BillingCheckDate.CHECKDATE_W.getValue(), checkDate)){
					settleChannelLog.setBeginOperEndTime(DateUtils.getPre7Date(new Date()));
					settleChannelLog.setEndOperEndTime(new Date());
				//一个月
				}else if(StringUtils.equals(BillingCheckDate.CHECKDATE_M.getValue(), checkDate)){
					settleChannelLog.setBeginOperEndTime(DateUtils.getInternalTimeByMonth1(-1));
					settleChannelLog.setEndOperEndTime(new Date());
				//三个月
				}else if(StringUtils.equals(BillingCheckDate.CHECKDATE_3M.getValue(), checkDate)){
					settleChannelLog.setBeginOperEndTime(DateUtils.getInternalTimeByMonth1(-3));
					settleChannelLog.setEndOperEndTime(new Date());
				}
			}
			//通道
			if(StringUtils.isNotBlank(channelCode)){
				settleChannelLog.setChannelCode(channelCode);
			}
			List<SettleChannelLog> settleChannelTypeList = settleChannelLogService.findCheckDateRemarkSettleChannelLog(settleChannelLog);
			for(SettleChannelLog log : settleChannelTypeList){
				log.setChannelTypeName(ChannelTypeClear.labelOf(log.getChannelType()));
				if(log.getChannelName().indexOf("-")!=-1){
					log.setChannelName(log.getChannelName().split("-")[1]);
				}
			}
			return settleChannelTypeList;
		} catch (Exception e) {
			logger.error("SettleDicTypeController getInfo has a error:{根据时间和描述查询对账日志信息表的统计信息错误  FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

}
