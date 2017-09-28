/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.notifyRecord.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.cbms.entity.CbmsNofityRecord;
import com.heepay.manage.modules.cbms.service.CbmsNofityRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.heepay.manage.common.web.BaseController;
import com.heepay.common.util.StringUtils;



/**
 *
 * 描    述：跨境通知表数据查询Controller
 *
 * 创 建 者： @author 牛俊鹏
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
@RequestMapping(value = "${adminPath}/cbms/cbmsNofityRecord")
public class CbmsNofityRecordController extends BaseController {

	@Autowired
	private CbmsNofityRecordService cbmsNofityRecordService;
	
	@ModelAttribute
	public CbmsNofityRecord get(@RequestParam(required=false) String id) {
		CbmsNofityRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cbmsNofityRecordService.get(id);
		}
		if (entity == null){
			entity = new CbmsNofityRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("cbms:cbmsNofityRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(CbmsNofityRecord cbmsNofityRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CbmsNofityRecord> page = cbmsNofityRecordService.findPage(new Page<CbmsNofityRecord>(request, response), cbmsNofityRecord);
		for(CbmsNofityRecord nofityRecord: page.getList()){
			if(nofityRecord==null || StringUtils.isEmpty(nofityRecord.getStatus())){
				continue;
			}
			switch (nofityRecord.getStatus()){
				case "UNKNOW":nofityRecord.setStatus("未通知") ;break;
				case "SUCCES": nofityRecord.setStatus("通知成功") ;break;
				case "FAILED": nofityRecord.setStatus("通知失败") ;break;
			}
		}
		model.addAttribute("page", page);
		return "modules/cbms/notifyRecord/cbmsNofityRecordList";
	}
}