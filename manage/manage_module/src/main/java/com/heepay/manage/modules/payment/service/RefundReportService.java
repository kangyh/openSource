/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.payment.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.common.util.StringUtil;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.merchant.dao.MerchantDao;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.payment.dao.PaymentRecordDao;
import com.heepay.manage.modules.payment.dao.RefundRecordDao;
import com.heepay.manage.modules.payment.entity.PaymentRecord;
import com.heepay.manage.modules.payment.entity.RefundRecord;
import com.heepay.manage.modules.payment.entity.RefundReport;
import com.heepay.manage.modules.payment.entity.RefundReportRecord;

/**
 *
 * 描    述：退款财务报表统计Service
 *
 * 创 建 者： 刘栋
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
@Service
@Transactional(readOnly = true)
public class RefundReportService {

	@Autowired
	private PaymentRecordDao paymentDao;

    @Autowired
    private RefundRecordDao refundDao;

    @Autowired
    private MerchantDao merchantDao;

	public Page<RefundReport> findPage(Page<RefundReport> page, RefundReport where) {

		//先查询退款单 条件为：商户ID  商户名称  商户来源  起止时间  退款状态
        Map<String, Object> refundParams = new HashMap<>();
        refundParams.put("merchantId", where.getMerchantId());
        refundParams.put("merchantCompany", where.getMerchantCompany());
        refundParams.put("beginCreateTime", where.getBeginCreateTime());
        refundParams.put("endCreateTime", where.getEndCreateTime());
        List<RefundRecord> refundsAuthed = refundDao.getRefundsForReport(refundParams);

		//进行内部商户和外部商户的过滤  查询merchant
        List<Integer> allowedMerchantIds = new ArrayList<>();
        if(!StringUtil.isBlank(where.getMerchantSource())){
            Map<Long, Long> merchantIdsMap = new HashMap<>();
            refundsAuthed.forEach(one -> merchantIdsMap.put(one.getMerchantId(), one.getMerchantId()));
            List<Long> merchantIds = new ArrayList<>(merchantIdsMap.values());
            Map<String, Object> merchantParams = new HashMap<>();
            merchantParams.put("merchantFlag", where.getMerchantSource());
            merchantParams.put("merchantIds", merchantIds);
            List<Merchant> allowedMerchants = merchantDao.getMerchantByMerchantIdsAndSource(merchantParams);
            if(allowedMerchants.isEmpty()){
                allowedMerchantIds.add(-1);
            } else {
                allowedMerchants.forEach(one -> allowedMerchantIds.add(one.getUserId()));
            }
        }

		//进行原支付方式的过滤  查询原支付单payment
        Map<String, List<RefundReportRecord>> resultMap = new HashMap<>();
        List<String> oriPaymentIds = new ArrayList<>();
        refundsAuthed.forEach(one -> {
            oriPaymentIds.add(one.getOriginPaymentId());

            RefundReportRecord record = new RefundReportRecord();
            record.setMerchantId(one.getMerchantId());
            record.setMerchantLoginName(one.getMerchantLoginName());
            record.setMerchantCompany(one.getMerchantCompany());
            record.setRefundId(one.getRefundId());
            record.setOriginPaymentId(one.getOriginPaymentId());
            record.setRefundAmount(new BigDecimal(one.getRefundAmount()));
            record.setIsFeeBack(one.getIsFeeBack());
            List<RefundReportRecord> curRefunds = resultMap.get(one.getOriginPaymentId());
            if(curRefunds == null) {
                List<RefundReportRecord> newList = new ArrayList<>();
                newList.add(record);
                resultMap.put(one.getOriginPaymentId(), newList);
            } else {
                resultMap.get(one.getOriginPaymentId()).add(record);
            }
        });
        Map<String, Object> paymentParams = new HashMap<>();
        if(oriPaymentIds.isEmpty()){
            oriPaymentIds.add("");
        }
        if(!allowedMerchantIds.isEmpty()){
            paymentParams.put("merchantIds", allowedMerchantIds);
        }
        paymentParams.put("paymentIds", oriPaymentIds);
        paymentParams.put("payType", where.getPayType());
        paymentParams.put("channelPartner", where.getChannelPartner());
        paymentParams.put("bankId", where.getBankId());
        List<PaymentRecord> paymentRecords = paymentDao.getOriPaymentsForRefundReport(paymentParams);

        //本次统计的退款单所对应的全部原支付单
        paymentRecords.forEach(one -> {
            List<RefundReportRecord> curList = resultMap.get(one.getPaymentId());
            if(curList != null){
                for(RefundReportRecord cur : curList) {
                    cur.setPayType(one.getPayType());
                    cur.setChannelPartner(one.getChannelCode().substring(3, 9));
                    cur.setBankId(one.getBankId());
                    cur.setChannelCode(one.getChannelCode());
                    cur.setPayAmount(new BigDecimal(one.getPayAmount()));
                    cur.setFeeAmount(new BigDecimal(one.getFeeAmount()));
                }
            }
        });

		//进行相关数据统计包装model
        List<RefundReport> resultList = new ArrayList<>();
        Map<String, List<RefundReportRecord>> statisticMap = new HashMap<>();
        if("merchant".equals(where.getGroupType())) {
            //按商户维度统计，group by merchantId
            for(List<RefundReportRecord> curList : resultMap.values()) {
                for(RefundReportRecord cur: curList) {
                    if (StringUtil.isBlank(cur.getPayType())) {
                        //过滤支付方式、通道合作方、银行名称不符的单子
//                    resultMap.remove(cur.getMerchantId().toString());
                        continue;
                    }

                    List<RefundReportRecord> curStatis = statisticMap.get(cur.getMerchantId().toString());
                    if (curStatis == null) {
                        List<RefundReportRecord> newList = new ArrayList<>();
                        newList.add(cur);
                        statisticMap.put(cur.getMerchantId().toString(), newList);
                    } else {
                        curStatis.add(cur);
                    }
                }
            }

            //分组完毕，开始统计
            statisticValues(statisticMap, resultList);

        } else if("channel".equals(where.getGroupType())) {
            //按通道维度统计，group by bankId + channelPartner
            for(List<RefundReportRecord> curList : resultMap.values()) {
                for(RefundReportRecord cur : curList) {
                    if (StringUtil.isBlank(cur.getPayType())) {
                        //过滤支付方式、通道合作方、银行名称不符的单子
//                    resultMap.remove(cur.getMerchantId().toString());
                        continue;
                    }
                    List<RefundReportRecord> curStatis = statisticMap.get(cur.getBankId() + cur.getChannelPartner());
                    if (curStatis == null) {
                        List<RefundReportRecord> newList = new ArrayList<>();
                        newList.add(cur);
                        statisticMap.put(cur.getBankId() + cur.getChannelPartner(), newList);
                    } else {
                        curStatis.add(cur);
                    }
                }
            }

            //分组完毕，开始统计
            statisticValues(statisticMap, resultList);

        }

        Collections.sort(resultList, (args0, args1) -> (args1.getRefundCounts() - args0.getRefundCounts()));
        //新增合计栏
        int totalCounts = 0;
        BigDecimal totalRefund = BigDecimal.ZERO;
        BigDecimal totalFeeBack = BigDecimal.ZERO;
        for(RefundReport cur : resultList){
            totalCounts += cur.getRefundCounts();
            totalRefund = totalRefund.add(new BigDecimal(cur.getTotalRefundAmount()));
            totalFeeBack = totalFeeBack.add(new BigDecimal(cur.getTotalFeeBackAmount()));
        }
        RefundReport sumReport = new RefundReport();
        sumReport.setRefundCounts(totalCounts);
        sumReport.setTotalRefundAmount(totalRefund.toString());
        sumReport.setTotalFeeBackAmount(totalFeeBack.toString());
        resultList.add(sumReport);

        where.setPage(page);
        page.setCount(resultList.size() - 1);
		page.setList(resultList);
        return page;
	}

	private void statisticValues(Map<String, List<RefundReportRecord>> statisticMap, List<RefundReport> resultList) {

        statisticMap.values().forEach(one -> {
            RefundReport report = new RefundReport();
            report.setMerchantId(one.get(0).getMerchantId());
            report.setMerchantLoginName(one.get(0).getMerchantLoginName());
            report.setMerchantCompany(one.get(0).getMerchantCompany());
            report.setPayType(one.get(0).getPayType());
            report.setChannelPartner(one.get(0).getChannelCode().substring(3, 9));
            report.setBankId(one.get(0).getBankId());
            int refundCounts = 0;
            BigDecimal totalOriPayAmount = BigDecimal.ZERO;
            BigDecimal totalRefundAmount = BigDecimal.ZERO;
            BigDecimal totalOriFeeAmount = BigDecimal.ZERO;
            BigDecimal totalFeeBackAmount = BigDecimal.ZERO;
            for(RefundReportRecord cur : one){
                refundCounts++;
                //TODO 部分退款情况下，原支付单金额多次累加的情况处理
                totalOriPayAmount = totalOriPayAmount.add(cur.getPayAmount());
                totalRefundAmount = totalRefundAmount.add(cur.getRefundAmount());
                totalOriFeeAmount = totalOriFeeAmount.add(cur.getFeeAmount());
                if("Y".equals(cur.getIsFeeBack())){
                    totalFeeBackAmount = totalFeeBackAmount.add(cur.getFeeAmount());
                }
            }
            report.setRefundCounts(refundCounts);
            report.setTotalOriPayAmount(totalOriPayAmount.toString());
            report.setTotalRefundAmount(totalRefundAmount.toString());
            report.setTotalOriFeeAmount(totalOriFeeAmount.toString());
            report.setTotalFeeBackAmount(totalFeeBackAmount.toString());
            resultList.add(report);
        });

    }
	
}