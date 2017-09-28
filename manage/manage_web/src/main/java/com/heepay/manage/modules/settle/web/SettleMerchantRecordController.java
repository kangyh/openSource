package com.heepay.manage.modules.settle.web;

import com.heepay.enums.billing.BillingSettleStatus;
import com.heepay.manage.common.config.Global;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.JsonMapper;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.reconciliation.entity.SettleDifferRecord;
import com.heepay.manage.modules.reconciliation.web.util.SaveConditions;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import com.heepay.manage.modules.settle.entity.SettleMerchantRecord;
import com.heepay.manage.modules.settle.entity.rabbit.SettleMerchantRecordRabbit;
import com.heepay.manage.modules.settle.service.SettleMerchantRecordService;
import com.heepay.manage.modules.settle.web.rabbit.ClearMerchantDetailMessage;
import com.heepay.manage.modules.settle.web.rabbit.SettleChannelProducerSender;
import com.heepay.manage.modules.settle.web.rabbit.SettleMerchantMessage;
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
 * 描 述：商户结算记录Controller
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
@RequestMapping(value = "${adminPath}/settle/settleMerchantRecordQuery")
public class SettleMerchantRecordController extends BaseController {
	
	private static final Logger logger=LogManager.getLogger();

	@Autowired
	private SettleMerchantRecordService settleMerchantRecordService;

	@Autowired
	private SettleChannelProducerSender settleChannelProducerSender;

	/**
	 * @方法说明：查询商户结算记录List
	 * @时间：2016年9月19日 
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settle:settleMerchantRecord:view")
	@RequestMapping(value = { "list", "" })
	public String list(SettleMerchantRecord settleMerchantRecord, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception{

		//使用cookie保存查询条件
		settleMerchantRecord = (SettleMerchantRecord) SaveConditions.result(settleMerchantRecord, "settleMerchantRecords", request, response);
		try {
			model = settleMerchantRecordService.findSettleMerchantRecordPage(new Page<SettleMerchantRecord>(request, response), settleMerchantRecord,model);
			model.addAttribute("settleMerchantRecord", settleMerchantRecord);

			return "modules/settle/settleMerchantRecordList";
		} catch (Exception e) {
			logger.error("SettleMerchantRecordController list has a error:{查询商户结算记录List错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * @方法说明：查询商户结算记录详细信息
	 * @时间：2016年9月19日
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settle:settleMerchantRecord:view")
	@RequestMapping(value = "toDetail")
	public String toDetail(SettleMerchantRecord settleMerchantRecord,HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		try {
			ClearingMerchantRecord clearingMerchantRecord = new ClearingMerchantRecord();
			String settleBath = request.getParameter("settleBath");
			//因为  商户侧存在T+0的支付  所以查看详情，根据结算批次查询  2016年12月2日15:09:10
			clearingMerchantRecord.setSettleBath(settleBath);//结算批次
			model = settleMerchantRecordService.findSettleMerchantRecordDetailPage(new Page<ClearingMerchantRecord>(request, response),clearingMerchantRecord,model);
			model.addAttribute("settleBath", settleBath);
		    return "modules/settle/settleMerchantRecordToDetail";
		} catch (Exception e) {
			logger.error("SettleMerchantRecordController toDetail has a error:{商户结算记录详细信息错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * @方法说明：商户清算记录信息导出
	 * @时间：2016年9月19日10:50:33
	 * @param redirectAttributes
	 * @param request
	 * @param response
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settle:settleMerchantRecord:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes,
					   SettleMerchantRecord settleMerchantRecord,
					   HttpServletRequest request,
					   HttpServletResponse response) throws Exception{
		try {
			settleMerchantRecordService.exportSettleMerchantRecordExcel(settleMerchantRecord,response, request);
		} catch (Exception e) {
			logger.error("SettleMerchantRecordController export has a error:{商户清算记录信息导出错误 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}



	/**
	 * @方法说明：取消结算和再次结算操作
	 * @时间： 7/6/2017 3:47 PM
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:settleMerchantRecord:edit")
	@RequestMapping(value = "/settle/{num}/{settleBath}")
	public String settle(@PathVariable(value = "num") Integer num,
						 @PathVariable(value = "settleBath") String settleBath,
						 SettleMerchantRecord settleMerchantRecord,
						 RedirectAttributes redirectAttributes){


		settleMerchantRecord.setSettleBath(settleBath);
		//取消结算
		if(num == 1){
			logger.info("商户侧取消结算操作--->{}",settleBath);
			settleMerchantRecord.setSettleStatus(BillingSettleStatus.SETTLE_STATUS_S.getValue());
			addMessage(redirectAttributes, "商户侧取消结算操作成功!");
		}else{//再次结算

			SettleMerchantRecordRabbit settleMerchant = settleMerchantRecordService.getEntityBySettleBath(settleBath);
			logger.info("商户侧再次结算操作--->{结算数据}--->{}",settleMerchantRecord);
			//发送消息
			SettleMerchantMessage settleMerchantMessage = new SettleMerchantMessage();
			//保存消息
			settleMerchantMessage.setSettleMerchantRecord(settleMerchant); //结算批次

			//根据批次号查询清算明细
			List<ClearMerchantDetailMessage> clearMessageList = new ArrayList<>();
			settleMerchantMessage.setClearMerchantMessage(clearMessageList);     //清算明细

			try {
				settleChannelProducerSender.sendMerchantDataToQueue(settleMerchantMessage);

				settleMerchantRecord.setSettleStatus(BillingSettleStatus.SETTLE_STATUS_C.getValue());//处理中
				addMessage(redirectAttributes, "再次结算操作成功,请等待账务系统处理!");
			} catch (Exception e) {
				logger.error("商户侧再次结算操作--->{发送消息队列异常}--->{}",e.getMessage());
				addMessage(redirectAttributes, "系统异常,请等待维护!");
			}
		}

			settleMerchantRecordService.updateEntity(settleMerchantRecord);

		return "redirect:"+ Global.getAdminPath()+"/settle/settleMerchantRecordQuery?cache=1";
	}

}