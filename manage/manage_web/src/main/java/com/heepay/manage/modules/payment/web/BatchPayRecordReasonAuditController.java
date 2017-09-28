/**
 *  
 */
package com.heepay.manage.modules.payment.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.BatchPayRecord;
import com.heepay.manage.modules.payment.service.BatchPayRecordDetailService;
import com.heepay.manage.modules.payment.service.BatchPayRecordService;

/**
 * 批量付款管理Controller
 * @author zjx
 * @version V1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/payment/batchPayRecordReasonList")
public class BatchPayRecordReasonAuditController extends BaseController {


	@Autowired
	private BatchPayRecordService batchPayRecordService;

	@Autowired
	private BatchPayRecordDetailService batchPayRecordDetailService;

	@ModelAttribute
	public BatchPayRecord get(@RequestParam(required=false) String id) {
		BatchPayRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = batchPayRecordService.get(id);
		}
		if (entity == null){
			entity = new BatchPayRecord();
		}
		return entity;
	}

	@RequiresPermissions("payment:batchPayRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(BatchPayRecord batchPayRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
//		//查询转账待审核列表
//		if(batchPayRecord.getStatus() == null){
//			batchPayRecord.setStatus("R1");
//		}
		if(batchPayRecord.getSortOrder() == null){
			batchPayRecord.setSortOrder(SortOrderType.DESC.getValue());
		}
//		Page<BatchPayRecord> page = batchPayRecordService.findPage(new Page<BatchPayRecord>(request, response), batchPayRecord);
//		List<BatchPayRecord> batchPayRecordList = page.getList();
//		List<BatchPayRecord> newRecordList = new ArrayList<BatchPayRecord>();
//		//批次是在处理中，明细有待审核，并且明细异常状态是正常
//		if(null != batchPayRecordList){
//			for(BatchPayRecord payRecord:batchPayRecordList){
//				String batchId = payRecord.getBatchId();
//				List<BatchPayRecordDetail> detailList = batchPayRecordDetailService.getDetailsByBatchId(Collections.singletonList(batchId));
//				if(null != detailList && !detailList.isEmpty()){
//					newRecordList.add(payRecord);
//				}
//			}
//			page.setList(newRecordList);
//			page.setCount(newRecordList.size());
//		}

		Page<BatchPayRecord> page = batchPayRecordService.getPreAuthList(new Page<>(request, response), batchPayRecord);
		model.addAttribute("page", page);

		return "modules/payment/batchPayRecordReasonAuditList";
	}

	@RequiresPermissions("payment:batchPayRecord:view")
	@RequestMapping(value = "form")
	public String form(BatchPayRecord batchPayRecord, HttpServletRequest request, HttpServletResponse response,Model model) {
		model.addAttribute("page", null);

		return "modules/payment/batchPayRecordAuditList";
	}

}