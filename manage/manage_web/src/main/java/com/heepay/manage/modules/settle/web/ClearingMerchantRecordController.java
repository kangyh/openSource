package com.heepay.manage.modules.settle.web;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.*;
import com.heepay.enums.billing.*;
import com.heepay.enums.billing.SettleCyc;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.settle.entity.ClearingMerchantRecord;
import com.heepay.manage.modules.settle.service.ClearingMerchantRecordService;
import com.heepay.manage.modules.settle.web.rpc.client.BillingDateSynClearingDateClient;
import com.heepay.manage.modules.settle.web.util.PayTypeSettle;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 *
 * 描 述： 商户侧清算记录Controller
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
@RequestMapping(value = "${adminPath}/settle/clearingMerchantRecordQuery")
public class ClearingMerchantRecordController extends BaseController {
	
	private static final Logger logger=LogManager.getLogger();

	@Autowired
	private ClearingMerchantRecordService clearingMerchantRecordService;
	
	@Autowired
	private BillingDateSynClearingDateClient billingDateSynClearingDateClient;

	/**
	 * @方法说明：查询商户清算记录List
	 * @时间：2016年9月19日 
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settle:clearingMerchantRecord:view")
	@RequestMapping(value = { "list", "" })
	public String list(ClearingMerchantRecord clearingMerchantRecord, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception{
		try {

			//格式化查询时间条件
			Date endCheckTime = clearingMerchantRecord.getEndCheckTime();
			if(endCheckTime!=null){
				String format = new SimpleDateFormat("yyyy-MM-dd").format(endCheckTime);
				format=format+" 23:59:59";
				try {
					Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
					clearingMerchantRecord.setEndCheckTime(time);
				} catch (ParseException e) {
					logger.error("时间转换异常"+e.getMessage());
				}
			}

			model = clearingMerchantRecordService
					.findClearingMerchantRecordPage(new Page<ClearingMerchantRecord>(request, response), clearingMerchantRecord,model);
			
			return "modules/settle/clearingMerchantRecordList";
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordController list has a error:{查询商户清算记录List出错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * @方法说明：商户清算记录信息导出
	 * @时间：2016年9月19日10:50:33
	 * @param redirectAttributes
	 * @param
	 * @param request
	 * @param response
	 * @创建人：wangdong
	 */
	@RequiresPermissions("settle:clearingMerchantRecord:view")
	@RequestMapping(value = "export")
	public void export(RedirectAttributes redirectAttributes,ClearingMerchantRecord clearingMerchantRecord, HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			//格式化查询时间条件
			Date endCheckTime = clearingMerchantRecord.getEndCheckTime();
			if(endCheckTime!=null){
				String format = new SimpleDateFormat("yyyy-MM-dd").format(endCheckTime);
				format=format+" 23:59:59";
				try {
					Date time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
					clearingMerchantRecord.setEndCheckTime(time);
				} catch (ParseException e) {
					logger.error("时间转换异常"+e.getMessage());
				}
			}
			clearingMerchantRecordService.exportClearingMerchantRecordExcel(clearingMerchantRecord,response,request);
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordController export has a error:{商户清算记录信息导出出错 FIND_ERROR}", e);
			throw new Exception(e);
		}
	}
	
	/**
	 * @方法说明：修复历史数据
	 * @时间：2016年9月19日10:50:33
	 * @创建人：wangdong
	 */
	@RequiresPermissions(value={"user","settle:clearingMerchantRecord:edit"})
	@ResponseBody
	@RequestMapping(value = "editHisData")
	public String editHisData(RedirectAttributes redirectAttributes,ClearingMerchantRecord clearingMerchantRecord, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception{
		try {
			String retStr = billingDateSynClearingDateClient.alldataSynchronize();
			Map<String, String> map = JsonMapperUtil.nonEmptyMapper().fromJson(retStr, Map.class);
			return "返回结果:总条数:"+map.get("total")+",成功条数:"+map.get("success")+",失败条数:"+map.get("fail");
		} catch (Exception e) {
			logger.error("ClearingMerchantRecordController editHisData has a error:{修复历史数据出错 FIND_ERROR }", e);
			throw new Exception(e);
		}
		
	}

	/**
	 * @方法说明：更具ID查询对象
	 * @时间： 2017-04-10 03:07 PM
	 * @创建人：wangl
	 */
	@RequiresPermissions("settle:clearingMerchantRecord:view")
	@RequestMapping(value = "/more/{clearingId}")
	public String more(@PathVariable(value = "clearingId") Long clearingId,
					   HttpServletRequest request,
					   HttpServletResponse response,
					   Model model) throws Exception{

		ClearingMerchantRecord clearingMerchantRecord = clearingMerchantRecordService.getEntityById(clearingId);

		logger.info("商户清算记录查看详情操作--->{查询结果}"+clearingMerchantRecord);
		//商户类型
		if(StringUtils.isNotBlank(clearingMerchantRecord.getMerchantType())){
			clearingMerchantRecord.setMerchantType(UserType.labelOf(clearingMerchantRecord.getMerchantType()));
		}
		//业务类型（交易类型）(product表code字段)
		if(StringUtils.isNotBlank(clearingMerchantRecord.getProductCode())){
			clearingMerchantRecord.setProductCode(ProductType.labelOf(clearingMerchantRecord.getProductCode()));
		}
		//业务类型
		if(StringUtils.isNotBlank(clearingMerchantRecord.getTransType())){
			clearingMerchantRecord.setTransType(TransType.labelOf(clearingMerchantRecord.getTransType()));
		}
		//币种
		if(StringUtils.isNotBlank(clearingMerchantRecord.getCurrency())){
			clearingMerchantRecord.setCurrency(BillingCurrency.labelOf(clearingMerchantRecord.getCurrency()));
		}else{
			clearingMerchantRecord.setCurrency(BillingCurrency.CURRENCY.getContent());
		}
		//手续费扣除方式
		if(StringUtils.isNotBlank(clearingMerchantRecord.getFeeWay())){
			clearingMerchantRecord.setFeeWay(ChargeDeductType.labelOf(clearingMerchantRecord.getFeeWay()));
		}
		//是否分润
		if(StringUtils.isNotBlank(clearingMerchantRecord.getIsProfit())){
			clearingMerchantRecord.setIsProfit(SettleDifferIsProfit.labelOf(clearingMerchantRecord.getIsProfit()));
		}
		//对账状态
		if(StringUtils.isNotBlank(clearingMerchantRecord.getCheckStatus())){
			clearingMerchantRecord.setCheckStatus(ClearingCheckStatus.labelOf(clearingMerchantRecord.getCheckStatus()));
		}
		//已对账状态
		if(StringUtils.isNotBlank(clearingMerchantRecord.getCheckFlg())){
			if(StringUtils.equals(clearingMerchantRecord.getCheckFlg(), BillingYCheckStatus.BCFQSTS.getValue())){
				clearingMerchantRecord.setCheckFlg("平账");
			}else{
				//除了平账都是差异账（产品需求）
				clearingMerchantRecord.setCheckFlg("差异账");
			}
		}
		//结算状态
		if(StringUtils.isNotBlank(clearingMerchantRecord.getSettleStatus())){
			clearingMerchantRecord.setSettleStatus(BillingSettleStatus.labelOf(clearingMerchantRecord.getSettleStatus()));
		}

		//银行卡类型 bankcard_type
		if(StringUtils.isNotBlank(clearingMerchantRecord.getBankcardType())){
			clearingMerchantRecord.setBankcardType(BankcardType.labelOf(clearingMerchantRecord.getBankcardType()));
		}
		//支付类型 pay_type
		if(StringUtils.isNotBlank(clearingMerchantRecord.getPayType())){
			clearingMerchantRecord.setPayType(PayTypeSettle.labelOf(clearingMerchantRecord.getPayType()));
		}
		//手续费结算周期
		if(StringUtils.isNotBlank(clearingMerchantRecord.getFeeSettleCyc())){

			clearingMerchantRecord.setFeeSettleCyc(SettleCyc.labelOf(clearingMerchantRecord.getFeeSettleCyc()));
		}
		//订单结算周期
		if(StringUtils.isNotBlank(clearingMerchantRecord.getSettleCyc())){

			clearingMerchantRecord.setSettleCyc(SettleCyc.labelOf(clearingMerchantRecord.getSettleCyc()));
		}

		model.addAttribute("clearingMerchantRecord",clearingMerchantRecord);

		return "modules/settle/clearmerchentmore";
	}
}