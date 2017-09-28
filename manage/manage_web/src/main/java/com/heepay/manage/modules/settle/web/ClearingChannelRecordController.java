package com.heepay.manage.modules.settle.web;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.ChargeDeductType;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.*;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.util.ChannelTypeClear;
import com.heepay.manage.modules.route.service.BankInfoService;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecord;
import com.heepay.manage.modules.settle.service.ClearingChannelRecordService;
import com.heepay.manage.modules.settle.web.rpc.client.BillingDateSynClearingDateClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * 描 述： 通道清算记录Controller
 *
 * 创 建 者： @author wangdong 
 * 创建时间： 2016年9月19日
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
@RequestMapping(value = "${adminPath}/settle/clearingChannelRecordQuery")
public class ClearingChannelRecordController extends BaseController {
	
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private ClearingChannelRecordService clearingChannelRecordService;
	
	@Autowired
	private BillingDateSynClearingDateClient billingDateSynClearingDateClient;

	@Autowired
	private BankInfoService bankInfoService;

	/**
	 * @方法说明：查询通道清算记录List
	 * @时间：2016年9月19日
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settle:clearingChannelRecord:view")
	@RequestMapping(value = { "list", "" })
	public String list(ClearingChannelRecord clearingChannelRecord, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception{
		try {
			model = clearingChannelRecordService
					.findClearingChannelRecordPage(new Page<ClearingChannelRecord>(request, response), clearingChannelRecord,model);
			
			return "modules/settle/clearingChannelRecordList";
		} catch (Exception e) {
			logger.error("ClearingChannelRecordController list has a error:{查询通道清算记录List出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * @方法说明：通道清算记录信息导出
	 * @时间：2016年9月19日10:50:33
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settle:clearingChannelRecord:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes,ClearingChannelRecord clearingChannelRecord, HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			clearingChannelRecordService.exportClearingChannelRecordExcel(clearingChannelRecord,request,response);
		} catch (Exception e) {
			logger.error("ClearingChannelRecordController export has a error:{通道清算记录信息导出出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
		
	}
	
	/**
	 * @方法说明：修复历史数据
	 * @时间：2016年9月19日10:50:33
	 * @创建人：wangdong
	 */
	@RequiresPermissions(value={"user","settle:clearingChannelRecord:edit"})
	@ResponseBody
	@RequestMapping(value = "editHisData")
	public String editHisData(RedirectAttributes redirectAttributes,ClearingChannelRecord clearingChannelRecord, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		try {
			String retStr = billingDateSynClearingDateClient.alldataSynchronize();
			Map<String, String> map = JsonMapperUtil.nonEmptyMapper().fromJson(retStr, Map.class);
			return "返回结果:总条数:"+map.get("total")+",成功条数:"+map.get("success")+",失败条数:"+map.get("fail");
		} catch (Exception e) {
			logger.error("ClearingChannelRecordController editHisData has a error:{修复历史数据出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
		
	}

	/**
	 * @方法说明：更具ID查询对象
	 * @时间： 2017-04-10 03:07 PM
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:clearingChannelRecord:view")
	@RequestMapping(value = "/more/{clearingId}")
	public String more(@PathVariable(value = "clearingId") Long clearingId,
					   HttpServletRequest request,
					   HttpServletResponse response,
					   Model model) throws Exception{

		ClearingChannelRecord clearingChannelRecord = clearingChannelRecordService.getEntityById(clearingId);
		logger.info("通道清算记录查看详情操作--->{查询结果}"+clearingChannelRecord);

		//通道业务类型
		if(StringUtils.isNotBlank(clearingChannelRecord.getChannelType())){
			clearingChannelRecord.setChannelType(ChannelTypeClear.labelOf(clearingChannelRecord.getChannelType()));
		}
		//币种
		if(StringUtils.isNotBlank(clearingChannelRecord.getCurrency())){
			clearingChannelRecord.setCurrency(BillingCurrency.labelOf(clearingChannelRecord.getCurrency()));
		}else{
			clearingChannelRecord.setCurrency(BillingCurrency.CURRENCY.getContent());
		}
		//交易类型
		if(StringUtils.isNotBlank(clearingChannelRecord.getTransType())){
			clearingChannelRecord.setTransType(TransType.labelOf(clearingChannelRecord.getTransType()));
		}
		//对账状态
		if(StringUtils.isNotBlank(clearingChannelRecord.getCheckStatus())){
			clearingChannelRecord.setCheckStatus(ClearingCheckStatus.labelOf(clearingChannelRecord.getCheckStatus()));
		}

		//手续费扣除方式
		if(StringUtils.isNotBlank(clearingChannelRecord.getCostWay())){
			clearingChannelRecord.setCostWay(ChargeDeductType.labelOf(clearingChannelRecord.getCostWay()));
		}
		//已对账状态
		if(StringUtils.isNotBlank(clearingChannelRecord.getCheckFlg())){
			if(BillingYCheckStatus.BCFQSTS.getValue().toString().equals(clearingChannelRecord.getCheckFlg())){
				clearingChannelRecord.setCheckFlg("平账");
			}else{
				//除了平账都是差异账（产品需求）
				clearingChannelRecord.setCheckFlg("差异账");
			}
		}
		//结算状态
		if(StringUtils.isNotBlank(clearingChannelRecord.getSettleStatus())){
			clearingChannelRecord.setSettleStatus(BillingSettleStatus.labelOf(clearingChannelRecord.getSettleStatus()));
		}

		//订单结算周期
		if(StringUtils.isNotBlank(clearingChannelRecord.getSettleCyc())){

			clearingChannelRecord.setSettleCyc(SettleCyc.labelOf(clearingChannelRecord.getSettleCyc()));
		}

		//成本结算周期
		if(StringUtils.isNotBlank(clearingChannelRecord.getCostSettleCyc())){

			clearingChannelRecord.setCostSettleCyc(SettleCyc.labelOf(clearingChannelRecord.getCostSettleCyc()));
		}

		model.addAttribute("clearingChannelRecord",clearingChannelRecord);

		return "modules/settle/clearchannelmore";
	}

}