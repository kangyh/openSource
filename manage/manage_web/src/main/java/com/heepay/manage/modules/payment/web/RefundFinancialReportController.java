/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.web;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.heepay.enums.PayType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.entity.RefundReport;
import com.heepay.manage.modules.payment.service.RefundReportService;
import com.heepay.manage.modules.sys.utils.BusinessInfoUtils;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.util.ExcelUtil;


/**
 *
 * 描    述：退款财务报表controller
 *
 * 创 建 者：刘栋
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
@RequestMapping(value = "${adminPath}/payment/refundReport")
public class RefundFinancialReportController extends BaseController {

	@Autowired
	private RefundReportService refundReportService;

	@RequiresPermissions("payment:refundReport:view")
	@RequestMapping(value = {"list", ""})
	public String list(RefundReport refundReport, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<RefundReport> page = refundReportService.findPage(new Page<>(request, response), refundReport);
		model.addAttribute("page", page);

        model.addAttribute("where", refundReport);

		return "modules/payment/refundReportList";
	}


	@RequestMapping(value = "export")
	public void refundReportExport(RefundReport refundReport, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Page<RefundReport> page = refundReportService.findPage(new Page<>(request, response), refundReport);


        String title = "";
        String colNames[] = new String[]{""};
        List<String[]> tableList = new ArrayList<>();
        if("merchant".equals(refundReport.getGroupType())) {
            title = "退款财务统计报表--按商户";
            colNames = new String[]{"商户ID", "商户账号", "商户名称", "退款总笔数(笔)", "退款提交总金额(元)", "退支付手续费总金额(元)"};
            for(RefundReport cur : page.getList()){
                String[] dataRow = new String[colNames.length];
                dataRow[0] = cur.getMerchantId()==null?"":cur.getMerchantId().toString();
                dataRow[1] = cur.getMerchantLoginName();
                dataRow[2] = cur.getMerchantCompany();
                dataRow[3] = cur.getRefundCounts().toString();
                dataRow[4] = cur.getTotalRefundAmount();
                dataRow[5] = cur.getTotalFeeBackAmount();
                tableList.add(dataRow);
            }
        } else if("channel".equals(refundReport.getGroupType())) {
            title = "退款财务统计报表--按通道";
            colNames = new String[]{"原支付类型", "通道合作方", "银行名称", "退款总笔数(笔)", "退款提交总金额(元)", "退支付手续费总金额(元)"};
            for(RefundReport cur : page.getList()){
                String[] dataRow = new String[colNames.length];
                dataRow[0] = PayType.getContentByValue(cur.getPayType());
                dataRow[1] = DictUtils.getDictLabel(cur.getChannelPartner(),"ChannelPartner", cur.getChannelPartner());
                dataRow[2] = BusinessInfoUtils.getBankNameByBankId(cur.getBankId());
                dataRow[3] = cur.getRefundCounts().toString();
                dataRow[4] = cur.getTotalRefundAmount();
                dataRow[5] = cur.getTotalFeeBackAmount();
                tableList.add(dataRow);
            }
        }

        String fileName = "退款财务统计报表-"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String returnFileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename="+returnFileName+".xls");
        OutputStream out = response.getOutputStream();
        ExcelUtil.getExportWorkbook(title, colNames, tableList).write(out);
        out.flush();
        out.close();

	}

}