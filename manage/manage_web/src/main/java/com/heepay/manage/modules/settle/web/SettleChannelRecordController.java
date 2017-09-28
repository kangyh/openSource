package com.heepay.manage.modules.settle.web;

import com.heepay.billingutils.util.Constants;
import com.heepay.date.DateUtils;
import com.heepay.enums.TransType;
import com.heepay.enums.billing.BillingSettleStatus;
import com.heepay.enums.billing.BillingYCheckStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecord;
import com.heepay.manage.modules.settle.entity.SettleChannelRecord;
import com.heepay.manage.modules.settle.entity.SettleMerchantRecord;
import com.heepay.manage.modules.settle.service.SettleChannelRecordService;
import com.heepay.manage.modules.settle.web.rabbit.ClearDetailMessage;
import com.heepay.manage.modules.settle.web.rabbit.SettleChannelMessage;
import com.heepay.manage.modules.settle.web.rabbit.SettleChannelProducerSender;
import com.heepay.manage.modules.settle.web.rabbit.SettleChannelRecordPo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 描 述： 通道结算记录Controller
 *
 * 创 建 者： @author wangdong
 * 创建时间：
 * 创建描述：
 *
 * 修 改 者： @author wangl
 * 修改时间： 2017/7/10
 * 修改描述： 添加再次结算和不结算功能
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
@Controller
@RequestMapping(value = "${adminPath}/settle/settleChannelRecordQuery")
public class SettleChannelRecordController extends BaseController {

	private static final Logger logger=LogManager.getLogger();

	@Autowired
	private SettleChannelRecordService settleChannelRecordService;

	@Autowired
	private SettleChannelProducerSender settleChannelProducerSender;

	/**
	 * @方法说明：查询通道结算记录List
	 * @时间：2016年9月19日
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settle:settleChannelRecord:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleChannelRecord settleChannelRecord, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception{

		//使用cookie保存查询条件
		settleChannelRecord = (SettleChannelRecord) SaveConditions.result(settleChannelRecord, "settleChannelRecord", request, response);
		try {
			model = settleChannelRecordService.findSettleChannelRecordPage(new Page<SettleChannelRecord>(request, response), settleChannelRecord,model);

			model.addAttribute("settleChannelRecord", settleChannelRecord);
			return "modules/settle/settleChannelRecordList";
		} catch (Exception e) {
			logger.error("SettleChannelRecordController list has a error:{查询通道结算记录List错误FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**
	 * @方法说明：通道结算记录详细信息
	 * @时间：2016年9月19日
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settle:settleChannelRecord:view")
	@RequestMapping(value = "toDetail")
	public String toDetail(SettleChannelRecord sttleChannelRecord,HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		try {
			String settleBath = request.getParameter("settleBath");
			ClearingChannelRecord clearingChannelRecord = new ClearingChannelRecord();
			clearingChannelRecord.setSettleBath(settleBath);//结算单据
			clearingChannelRecord.setCheckFlg(BillingYCheckStatus.BCFQSTS.getValue());//对账标记：平账
			model = settleChannelRecordService.findSettleChannelRecordDetailPage(clearingChannelRecord,new Page<ClearingChannelRecord>(request, response),model);
			model.addAttribute("settleBath", settleBath);
		    return "modules/settle/settleChannelRecordToDetail";
		} catch (Exception e) {
			logger.error("SettleChannelRecordController toDetail has a error:{通道结算记录详细信息错误FIND_ERROR}", e);
			throw new Exception(e);
		}
	}

	/**  settle:settleChannelRecord:edit
	 * @方法说明：通道结算记录信息导出
	 * @时间：2016年9月19日10:50:33
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settle:settleChannelRecord:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes,SettleChannelRecord settleChannelRecord, HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			settleChannelRecordService.exportSettleChannelRecordExcel(settleChannelRecord,response, request);
		} catch (Exception e) {
			logger.error("SettleChannelRecordController export has a error:{通道结算记录信息导出错误FIND_ERROR}", e);
			throw new Exception(e);
		}
	}


	/**
	 * @方法说明：取消结算和再次结算操作
	 * @时间： 7/6/2017 3:47 PM
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleChannelRecord:edit")
	@RequestMapping(value = "/settle/{num}/{settleBath}")
	public String settle(@PathVariable(value = "num") Integer num,
					   @PathVariable(value = "settleBath") String settleBath,
					   SettleChannelRecord sttleChannelRecord,
					   RedirectAttributes redirectAttributes){


		sttleChannelRecord.setSettleBath(settleBath);
		//取消结算
		if(num == 1){
            logger.info("通道侧取消结算操作--->{}",settleBath);
			sttleChannelRecord.setSettleStatus(BillingSettleStatus.SETTLE_STATUS_S.getValue());
			addMessage(redirectAttributes, "通道侧取消结算操作成功!");
		}else{//再次结算

			sttleChannelRecord = settleChannelRecordService.getEntity(settleBath);
            logger.info("通道侧再次结算操作--->{结算数据}--->{}",sttleChannelRecord);
            //发送消息
			SettleChannelMessage settleChannelMessage = new SettleChannelMessage();
			//保存消息
			// 用作返回数据用
			SettleChannelRecordPo settleChannelRecordPo = new SettleChannelRecordPo();

			settleChannelRecordPo.setChannelCode(sttleChannelRecord.getChannelCode());
			settleChannelRecordPo.setChannelName(sttleChannelRecord.getChannelName());
			settleChannelRecordPo.setChannelType(sttleChannelRecord.getChannelType());
			settleChannelRecordPo.setCheckStatus(Constants.CHECK_STATUS_Y); // 对账状态是已对账
			settleChannelRecordPo.setCostAmount(sttleChannelRecord.getCostAmount()); // 总成本
			settleChannelRecordPo.setCurrency(Constants.CURRENCY_RMB);
			settleChannelRecordPo.setPayNum(sttleChannelRecord.getPayNum());
			settleChannelRecordPo.setSettleBath(sttleChannelRecord.getSettleBath());

			settleChannelRecordPo.setSettleStatus(Constants.SETTLE_STATUS_D); // 结算状态是结算中
			settleChannelRecordPo.setTotalAmount(sttleChannelRecord.getTotalAmount());
			settleChannelRecordPo.setSettleTime(DateUtils.getDate());
			settleChannelRecordPo.setSettleCyc(sttleChannelRecord.getSettleCyc());
			settleChannelRecordPo.setCostTime(sttleChannelRecord.getCostTime());
			settleChannelRecordPo.setCostSettleCyc(sttleChannelRecord.getCostSettleCyc());

			try {
				settleChannelMessage.setSettleChannelRecordPo(settleChannelRecordPo);

				// 查询清算明细
				List<ClearDetailMessage> clearChannelRecords = new ArrayList<>();
				settleChannelMessage.setClearDetailMessage(clearChannelRecords);

				settleChannelProducerSender.sendChannelDataToQueue(settleChannelMessage);
				sttleChannelRecord.setSettleStatus(BillingSettleStatus.SETTLE_STATUS_C.getValue());//处理中
                addMessage(redirectAttributes, "再次结算操作成功,请等待账务系统处理!");
			} catch (Exception e) {
                logger.error("通道侧再次结算操作--->{发送消息队列异常}--->{}",e.getMessage());
				addMessage(redirectAttributes, "系统异常,请等待维护!");
			}
		}

		settleChannelRecordService.updateEntity(sttleChannelRecord);
		return "redirect:"+Global.getAdminPath()+"/settle/settleChannelRecordQuery?cache=1";
	}

}