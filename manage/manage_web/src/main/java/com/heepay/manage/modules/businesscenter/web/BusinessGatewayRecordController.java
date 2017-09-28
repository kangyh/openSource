/**
 *  
 */
package com.heepay.manage.modules.businesscenter.web;

import com.heepay.common.util.StringUtils;
import com.heepay.common.util.WebUtil;
import com.heepay.date.DateUtils;
import com.heepay.enums.SortOrderType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.common.web.BaseController;
import com.heepay.manage.modules.payment.dao.NotifyRecordDao;
import com.heepay.manage.modules.payment.dao.RefundRecordDao;
import com.heepay.manage.modules.payment.entity.GatewayRecord;
import com.heepay.manage.modules.payment.entity.NotifyRecord;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.entity.RefundRecord;
import com.heepay.manage.modules.payment.service.GatewayRecordService;
import com.heepay.manage.modules.payment.service.PaymentRecordService;
import com.heepay.manage.modules.share.dao.ShareAccountRecordDetailDao;
import com.heepay.manage.modules.share.entity.ShareAccountRecordDetail;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 交易查询
 * 
 * @author ld
 * @version V1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/business/businessGatewayRecord")
public class BusinessGatewayRecordController extends BaseController {

    @Autowired
    private GatewayRecordService gatewayRecordService;

    @Autowired
    private PaymentRecordService paymentRecordService;

    @Autowired
    private NotifyRecordDao notifyRecordDao;

    @Autowired
    private RefundRecordDao refundDao;

    @Autowired
    private ShareAccountRecordDetailDao shareAccountDetailDao;

    @ModelAttribute
    public GatewayRecord get(@RequestParam(required = false) String id) {
        GatewayRecord entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = gatewayRecordService.get(id);
        }
        if (entity == null) {
            entity = new GatewayRecord();
        }
        return entity;
    }

    @RequiresPermissions("business:gatewayRecord:view")
    @RequestMapping(value = "getGatewayRecordList")
    public String getGatewayRecordList(GatewayRecord gatewayRecord, HttpServletRequest request,
            HttpServletResponse response, Model model) throws Exception {

        if ("R1".equals(gatewayRecord.getFeeType())) {
            gatewayRecord.setFeeType(null);
        }
        if ("R1".equals(gatewayRecord.getStatus())) {
            gatewayRecord.setStatus(null);
        }
        if (gatewayRecord.getSortOrder() == null) {
            gatewayRecord.setSortOrder(SortOrderType.DESC.getValue());
        }

        if (gatewayRecord.getBeginCreateTime() == null) {
            // 默认当天
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateNow = DateUtils.getDate(true);
            gatewayRecord.setBeginCreateTime(sdf.parse(dateNow + " 00:00:00"));
            gatewayRecord.setEndCreateTime(sdf.parse(dateNow + " 23:59:59"));
        }
        // gatewayRecord.setType(TransType.PAY.getValue());

        List<Long> merchantIdList = RandomUtil.getMerchantIdList();
        gatewayRecord.setMerchantIds(merchantIdList);

        Page<GatewayRecord> page = gatewayRecordService.findPage(new Page<GatewayRecord>(request, response),
                gatewayRecord);
        model.addAttribute("page", page);
        return "modules/businesscenter/gatewayRecordList";
    }

    @RequiresPermissions("business:gatewayRecord:view")
    @RequestMapping(value = "form")
    public String form(GatewayRecord gatewayRecord, Model model) {

        if (gatewayRecord == null) {
            return "modules/businesscenter/gatewayRecordForm";
        }

        PaymentRecord paymentRecord = paymentRecordService.getOneByGatewayId(gatewayRecord.getGatewayId());
        if (paymentRecord != null) {
            NotifyRecord NotifyRecord = notifyRecordDao.getByTransNo(paymentRecord.getTransNo());
            model.addAttribute("notifyRecord", NotifyRecord);
            // 退款信息
            RefundRecord entity = new RefundRecord();
            entity.setTransNo(gatewayRecord.getId());
            List<RefundRecord> refundList = refundDao.findList(entity);
            model.addAttribute("refundList", refundList);

            ShareAccountRecordDetail detail = new ShareAccountRecordDetail();
            detail.setTransNo(gatewayRecord.getId());
            List<ShareAccountRecordDetail> shareAccountDetails = shareAccountDetailDao.findList(detail);
            model.addAttribute("shareAccountDetails", shareAccountDetails);
        } else {
            paymentRecord = paymentRecordService.getFailedByGateWayId(gatewayRecord.getGatewayId());
        }

        model.addAttribute("paymentRecord", paymentRecord);
        model.addAttribute("gatewayRecord", gatewayRecord);
        return "modules/businesscenter/gatewayRecordForm";
    }

    @RequiresPermissions("business:gatewayRecord:view")
    @RequestMapping(value = "getStatisticsList")
    @ResponseBody
    public void getStatisticsList(String gatewayId, String merchantId, String merchantCompany, String merchantLoginName,
            String merchantOrderNo, String beginCreateTime, String endCreateTime, String status, String feeType,
            HttpServletResponse response) throws ParseException {
        GatewayRecord gatewayRecord = new GatewayRecord();
        if (StringUtils.isNotEmpty(gatewayId)) {
            gatewayRecord.setGatewayId(gatewayId);
        }
        if (StringUtils.isNotEmpty(merchantId)) {
            gatewayRecord.setMerchantId(Long.parseLong(merchantId));
        }
        if (StringUtils.isNotEmpty(merchantCompany)) {
            gatewayRecord.setMerchantCompany(merchantCompany);
        }
        if (StringUtils.isNotEmpty(merchantLoginName)) {
            gatewayRecord.setMerchantLoginName(merchantLoginName);
        }
        if (StringUtils.isNotEmpty(merchantOrderNo)) {
            gatewayRecord.setMerchantOrderNo(merchantOrderNo);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isNotEmpty(beginCreateTime)) {
            gatewayRecord.setBeginCreateTime(sdf.parse(beginCreateTime));
        }
        if (StringUtils.isNotEmpty(endCreateTime)) {
            gatewayRecord.setEndCreateTime(sdf.parse(endCreateTime));
        }

        if (StringUtils.isNotEmpty(status) && !StringUtils.equals("R1", status)) {
            gatewayRecord.setStatus(status);
        }
        if (StringUtils.isNotEmpty(feeType) && !StringUtils.equals("R1", feeType)) {
            gatewayRecord.setFeeType(feeType);
        }

        List<Long> merchantIdList = RandomUtil.getMerchantIdList();
        gatewayRecord.setMerchantIds(merchantIdList);

        /// 清空分页信息
        List<GatewayRecord> list = gatewayRecordService.findList(gatewayRecord);
        int count = 0;
        BigDecimal totalOrderAmount = BigDecimal.ZERO;
        BigDecimal totalFeeAmount = BigDecimal.ZERO;
        BigDecimal totalRealAmount = BigDecimal.ZERO;
        if (list != null && !list.isEmpty()) {
            count = list.size();
            for (GatewayRecord record : list) {
                BigDecimal orderAmount = new BigDecimal(
                        StringUtils.isEmpty(record.getRequestAmount()) ? "0" : record.getRequestAmount());
                totalOrderAmount = totalOrderAmount.add(orderAmount).setScale(2, RoundingMode.HALF_UP);
                BigDecimal realAmount = new BigDecimal(
                        StringUtils.isEmpty(record.getSuccessAmount()) ? "0" : record.getSuccessAmount());
                totalRealAmount = totalRealAmount.add(realAmount).setScale(2, RoundingMode.HALF_UP);
                BigDecimal feeAmount = new BigDecimal(
                        StringUtils.isEmpty(record.getFeeAmount()) ? "0" : record.getFeeAmount());
                totalFeeAmount = totalFeeAmount.add(feeAmount).setScale(2, RoundingMode.HALF_UP);
            }
        }

        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        df.applyPattern("￥#,##0.00");
        Map<String, String> map = new HashMap<String, String>();
        map.put("count", String.valueOf(count) + "笔");
        map.put("totalOrderAmount", df.format(totalOrderAmount));
        map.put("totalRealAmount", df.format(totalRealAmount));
        map.put("totalFeeAmount", df.format(totalFeeAmount));

        WebUtil.outputJson(map, response);

    }

}