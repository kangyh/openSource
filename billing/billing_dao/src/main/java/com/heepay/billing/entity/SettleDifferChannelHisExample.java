package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SettleDifferChannelHisExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SettleDifferChannelHisExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andHisIdIsNull() {
            addCriterion("his_id is null");
            return (Criteria) this;
        }

        public Criteria andHisIdIsNotNull() {
            addCriterion("his_id is not null");
            return (Criteria) this;
        }

        public Criteria andHisIdEqualTo(Long value) {
            addCriterion("his_id =", value, "hisId");
            return (Criteria) this;
        }

        public Criteria andHisIdNotEqualTo(Long value) {
            addCriterion("his_id <>", value, "hisId");
            return (Criteria) this;
        }

        public Criteria andHisIdGreaterThan(Long value) {
            addCriterion("his_id >", value, "hisId");
            return (Criteria) this;
        }

        public Criteria andHisIdGreaterThanOrEqualTo(Long value) {
            addCriterion("his_id >=", value, "hisId");
            return (Criteria) this;
        }

        public Criteria andHisIdLessThan(Long value) {
            addCriterion("his_id <", value, "hisId");
            return (Criteria) this;
        }

        public Criteria andHisIdLessThanOrEqualTo(Long value) {
            addCriterion("his_id <=", value, "hisId");
            return (Criteria) this;
        }

        public Criteria andHisIdIn(List<Long> values) {
            addCriterion("his_id in", values, "hisId");
            return (Criteria) this;
        }

        public Criteria andHisIdNotIn(List<Long> values) {
            addCriterion("his_id not in", values, "hisId");
            return (Criteria) this;
        }

        public Criteria andHisIdBetween(Long value1, Long value2) {
            addCriterion("his_id between", value1, value2, "hisId");
            return (Criteria) this;
        }

        public Criteria andHisIdNotBetween(Long value1, Long value2) {
            addCriterion("his_id not between", value1, value2, "hisId");
            return (Criteria) this;
        }

        public Criteria andDifferChanbillIdIsNull() {
            addCriterion("differ_chanbill_id is null");
            return (Criteria) this;
        }

        public Criteria andDifferChanbillIdIsNotNull() {
            addCriterion("differ_chanbill_id is not null");
            return (Criteria) this;
        }

        public Criteria andDifferChanbillIdEqualTo(Long value) {
            addCriterion("differ_chanbill_id =", value, "differChanbillId");
            return (Criteria) this;
        }

        public Criteria andDifferChanbillIdNotEqualTo(Long value) {
            addCriterion("differ_chanbill_id <>", value, "differChanbillId");
            return (Criteria) this;
        }

        public Criteria andDifferChanbillIdGreaterThan(Long value) {
            addCriterion("differ_chanbill_id >", value, "differChanbillId");
            return (Criteria) this;
        }

        public Criteria andDifferChanbillIdGreaterThanOrEqualTo(Long value) {
            addCriterion("differ_chanbill_id >=", value, "differChanbillId");
            return (Criteria) this;
        }

        public Criteria andDifferChanbillIdLessThan(Long value) {
            addCriterion("differ_chanbill_id <", value, "differChanbillId");
            return (Criteria) this;
        }

        public Criteria andDifferChanbillIdLessThanOrEqualTo(Long value) {
            addCriterion("differ_chanbill_id <=", value, "differChanbillId");
            return (Criteria) this;
        }

        public Criteria andDifferChanbillIdIn(List<Long> values) {
            addCriterion("differ_chanbill_id in", values, "differChanbillId");
            return (Criteria) this;
        }

        public Criteria andDifferChanbillIdNotIn(List<Long> values) {
            addCriterion("differ_chanbill_id not in", values, "differChanbillId");
            return (Criteria) this;
        }

        public Criteria andDifferChanbillIdBetween(Long value1, Long value2) {
            addCriterion("differ_chanbill_id between", value1, value2, "differChanbillId");
            return (Criteria) this;
        }

        public Criteria andDifferChanbillIdNotBetween(Long value1, Long value2) {
            addCriterion("differ_chanbill_id not between", value1, value2, "differChanbillId");
            return (Criteria) this;
        }

        public Criteria andChannelCodeIsNull() {
            addCriterion("channel_code is null");
            return (Criteria) this;
        }

        public Criteria andChannelCodeIsNotNull() {
            addCriterion("channel_code is not null");
            return (Criteria) this;
        }

        public Criteria andChannelCodeEqualTo(String value) {
            addCriterion("channel_code =", value, "channelCode");
            return (Criteria) this;
        }

        public Criteria andChannelCodeNotEqualTo(String value) {
            addCriterion("channel_code <>", value, "channelCode");
            return (Criteria) this;
        }

        public Criteria andChannelCodeGreaterThan(String value) {
            addCriterion("channel_code >", value, "channelCode");
            return (Criteria) this;
        }

        public Criteria andChannelCodeGreaterThanOrEqualTo(String value) {
            addCriterion("channel_code >=", value, "channelCode");
            return (Criteria) this;
        }

        public Criteria andChannelCodeLessThan(String value) {
            addCriterion("channel_code <", value, "channelCode");
            return (Criteria) this;
        }

        public Criteria andChannelCodeLessThanOrEqualTo(String value) {
            addCriterion("channel_code <=", value, "channelCode");
            return (Criteria) this;
        }

        public Criteria andChannelCodeLike(String value) {
            addCriterion("channel_code like", value, "channelCode");
            return (Criteria) this;
        }

        public Criteria andChannelCodeNotLike(String value) {
            addCriterion("channel_code not like", value, "channelCode");
            return (Criteria) this;
        }

        public Criteria andChannelCodeIn(List<String> values) {
            addCriterion("channel_code in", values, "channelCode");
            return (Criteria) this;
        }

        public Criteria andChannelCodeNotIn(List<String> values) {
            addCriterion("channel_code not in", values, "channelCode");
            return (Criteria) this;
        }

        public Criteria andChannelCodeBetween(String value1, String value2) {
            addCriterion("channel_code between", value1, value2, "channelCode");
            return (Criteria) this;
        }

        public Criteria andChannelCodeNotBetween(String value1, String value2) {
            addCriterion("channel_code not between", value1, value2, "channelCode");
            return (Criteria) this;
        }

        public Criteria andChannelTypeIsNull() {
            addCriterion("channel_type is null");
            return (Criteria) this;
        }

        public Criteria andChannelTypeIsNotNull() {
            addCriterion("channel_type is not null");
            return (Criteria) this;
        }

        public Criteria andChannelTypeEqualTo(String value) {
            addCriterion("channel_type =", value, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeNotEqualTo(String value) {
            addCriterion("channel_type <>", value, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeGreaterThan(String value) {
            addCriterion("channel_type >", value, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeGreaterThanOrEqualTo(String value) {
            addCriterion("channel_type >=", value, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeLessThan(String value) {
            addCriterion("channel_type <", value, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeLessThanOrEqualTo(String value) {
            addCriterion("channel_type <=", value, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeLike(String value) {
            addCriterion("channel_type like", value, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeNotLike(String value) {
            addCriterion("channel_type not like", value, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeIn(List<String> values) {
            addCriterion("channel_type in", values, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeNotIn(List<String> values) {
            addCriterion("channel_type not in", values, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeBetween(String value1, String value2) {
            addCriterion("channel_type between", value1, value2, "channelType");
            return (Criteria) this;
        }

        public Criteria andChannelTypeNotBetween(String value1, String value2) {
            addCriterion("channel_type not between", value1, value2, "channelType");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNull() {
            addCriterion("currency is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNotNull() {
            addCriterion("currency is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyEqualTo(String value) {
            addCriterion("currency =", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotEqualTo(String value) {
            addCriterion("currency <>", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThan(String value) {
            addCriterion("currency >", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThanOrEqualTo(String value) {
            addCriterion("currency >=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThan(String value) {
            addCriterion("currency <", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThanOrEqualTo(String value) {
            addCriterion("currency <=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLike(String value) {
            addCriterion("currency like", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotLike(String value) {
            addCriterion("currency not like", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyIn(List<String> values) {
            addCriterion("currency in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotIn(List<String> values) {
            addCriterion("currency not in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyBetween(String value1, String value2) {
            addCriterion("currency between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotBetween(String value1, String value2) {
            addCriterion("currency not between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andErrorBathIsNull() {
            addCriterion("error_bath is null");
            return (Criteria) this;
        }

        public Criteria andErrorBathIsNotNull() {
            addCriterion("error_bath is not null");
            return (Criteria) this;
        }

        public Criteria andErrorBathEqualTo(String value) {
            addCriterion("error_bath =", value, "errorBath");
            return (Criteria) this;
        }

        public Criteria andErrorBathNotEqualTo(String value) {
            addCriterion("error_bath <>", value, "errorBath");
            return (Criteria) this;
        }

        public Criteria andErrorBathGreaterThan(String value) {
            addCriterion("error_bath >", value, "errorBath");
            return (Criteria) this;
        }

        public Criteria andErrorBathGreaterThanOrEqualTo(String value) {
            addCriterion("error_bath >=", value, "errorBath");
            return (Criteria) this;
        }

        public Criteria andErrorBathLessThan(String value) {
            addCriterion("error_bath <", value, "errorBath");
            return (Criteria) this;
        }

        public Criteria andErrorBathLessThanOrEqualTo(String value) {
            addCriterion("error_bath <=", value, "errorBath");
            return (Criteria) this;
        }

        public Criteria andErrorBathLike(String value) {
            addCriterion("error_bath like", value, "errorBath");
            return (Criteria) this;
        }

        public Criteria andErrorBathNotLike(String value) {
            addCriterion("error_bath not like", value, "errorBath");
            return (Criteria) this;
        }

        public Criteria andErrorBathIn(List<String> values) {
            addCriterion("error_bath in", values, "errorBath");
            return (Criteria) this;
        }

        public Criteria andErrorBathNotIn(List<String> values) {
            addCriterion("error_bath not in", values, "errorBath");
            return (Criteria) this;
        }

        public Criteria andErrorBathBetween(String value1, String value2) {
            addCriterion("error_bath between", value1, value2, "errorBath");
            return (Criteria) this;
        }

        public Criteria andErrorBathNotBetween(String value1, String value2) {
            addCriterion("error_bath not between", value1, value2, "errorBath");
            return (Criteria) this;
        }

        public Criteria andErrorDateIsNull() {
            addCriterion("error_date is null");
            return (Criteria) this;
        }

        public Criteria andErrorDateIsNotNull() {
            addCriterion("error_date is not null");
            return (Criteria) this;
        }

        public Criteria andErrorDateEqualTo(Date value) {
            addCriterion("error_date =", value, "errorDate");
            return (Criteria) this;
        }

        public Criteria andErrorDateNotEqualTo(Date value) {
            addCriterion("error_date <>", value, "errorDate");
            return (Criteria) this;
        }

        public Criteria andErrorDateGreaterThan(Date value) {
            addCriterion("error_date >", value, "errorDate");
            return (Criteria) this;
        }

        public Criteria andErrorDateGreaterThanOrEqualTo(Date value) {
            addCriterion("error_date >=", value, "errorDate");
            return (Criteria) this;
        }

        public Criteria andErrorDateLessThan(Date value) {
            addCriterion("error_date <", value, "errorDate");
            return (Criteria) this;
        }

        public Criteria andErrorDateLessThanOrEqualTo(Date value) {
            addCriterion("error_date <=", value, "errorDate");
            return (Criteria) this;
        }

        public Criteria andErrorDateIn(List<Date> values) {
            addCriterion("error_date in", values, "errorDate");
            return (Criteria) this;
        }

        public Criteria andErrorDateNotIn(List<Date> values) {
            addCriterion("error_date not in", values, "errorDate");
            return (Criteria) this;
        }

        public Criteria andErrorDateBetween(Date value1, Date value2) {
            addCriterion("error_date between", value1, value2, "errorDate");
            return (Criteria) this;
        }

        public Criteria andErrorDateNotBetween(Date value1, Date value2) {
            addCriterion("error_date not between", value1, value2, "errorDate");
            return (Criteria) this;
        }

        public Criteria andDealTimeIsNull() {
            addCriterion("deal_time is null");
            return (Criteria) this;
        }

        public Criteria andDealTimeIsNotNull() {
            addCriterion("deal_time is not null");
            return (Criteria) this;
        }

        public Criteria andDealTimeEqualTo(Date value) {
            addCriterion("deal_time =", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeNotEqualTo(Date value) {
            addCriterion("deal_time <>", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeGreaterThan(Date value) {
            addCriterion("deal_time >", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("deal_time >=", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeLessThan(Date value) {
            addCriterion("deal_time <", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeLessThanOrEqualTo(Date value) {
            addCriterion("deal_time <=", value, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeIn(List<Date> values) {
            addCriterion("deal_time in", values, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeNotIn(List<Date> values) {
            addCriterion("deal_time not in", values, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeBetween(Date value1, Date value2) {
            addCriterion("deal_time between", value1, value2, "dealTime");
            return (Criteria) this;
        }

        public Criteria andDealTimeNotBetween(Date value1, Date value2) {
            addCriterion("deal_time not between", value1, value2, "dealTime");
            return (Criteria) this;
        }

        public Criteria andPaymentIdIsNull() {
            addCriterion("payment_id is null");
            return (Criteria) this;
        }

        public Criteria andPaymentIdIsNotNull() {
            addCriterion("payment_id is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentIdEqualTo(String value) {
            addCriterion("payment_id =", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdNotEqualTo(String value) {
            addCriterion("payment_id <>", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdGreaterThan(String value) {
            addCriterion("payment_id >", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdGreaterThanOrEqualTo(String value) {
            addCriterion("payment_id >=", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdLessThan(String value) {
            addCriterion("payment_id <", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdLessThanOrEqualTo(String value) {
            addCriterion("payment_id <=", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdLike(String value) {
            addCriterion("payment_id like", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdNotLike(String value) {
            addCriterion("payment_id not like", value, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdIn(List<String> values) {
            addCriterion("payment_id in", values, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdNotIn(List<String> values) {
            addCriterion("payment_id not in", values, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdBetween(String value1, String value2) {
            addCriterion("payment_id between", value1, value2, "paymentId");
            return (Criteria) this;
        }

        public Criteria andPaymentIdNotBetween(String value1, String value2) {
            addCriterion("payment_id not between", value1, value2, "paymentId");
            return (Criteria) this;
        }

        public Criteria andTransNoIsNull() {
            addCriterion("trans_no is null");
            return (Criteria) this;
        }

        public Criteria andTransNoIsNotNull() {
            addCriterion("trans_no is not null");
            return (Criteria) this;
        }

        public Criteria andTransNoEqualTo(String value) {
            addCriterion("trans_no =", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoNotEqualTo(String value) {
            addCriterion("trans_no <>", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoGreaterThan(String value) {
            addCriterion("trans_no >", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoGreaterThanOrEqualTo(String value) {
            addCriterion("trans_no >=", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoLessThan(String value) {
            addCriterion("trans_no <", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoLessThanOrEqualTo(String value) {
            addCriterion("trans_no <=", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoLike(String value) {
            addCriterion("trans_no like", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoNotLike(String value) {
            addCriterion("trans_no not like", value, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoIn(List<String> values) {
            addCriterion("trans_no in", values, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoNotIn(List<String> values) {
            addCriterion("trans_no not in", values, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoBetween(String value1, String value2) {
            addCriterion("trans_no between", value1, value2, "transNo");
            return (Criteria) this;
        }

        public Criteria andTransNoNotBetween(String value1, String value2) {
            addCriterion("trans_no not between", value1, value2, "transNo");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountIsNull() {
            addCriterion("success_amount is null");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountIsNotNull() {
            addCriterion("success_amount is not null");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountEqualTo(BigDecimal value) {
            addCriterion("success_amount =", value, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountNotEqualTo(BigDecimal value) {
            addCriterion("success_amount <>", value, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountGreaterThan(BigDecimal value) {
            addCriterion("success_amount >", value, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("success_amount >=", value, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountLessThan(BigDecimal value) {
            addCriterion("success_amount <", value, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("success_amount <=", value, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountIn(List<BigDecimal> values) {
            addCriterion("success_amount in", values, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountNotIn(List<BigDecimal> values) {
            addCriterion("success_amount not in", values, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("success_amount between", value1, value2, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("success_amount not between", value1, value2, "successAmount");
            return (Criteria) this;
        }

        public Criteria andCostIsNull() {
            addCriterion("cost is null");
            return (Criteria) this;
        }

        public Criteria andCostIsNotNull() {
            addCriterion("cost is not null");
            return (Criteria) this;
        }

        public Criteria andCostEqualTo(BigDecimal value) {
            addCriterion("cost =", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostNotEqualTo(BigDecimal value) {
            addCriterion("cost <>", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostGreaterThan(BigDecimal value) {
            addCriterion("cost >", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cost >=", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostLessThan(BigDecimal value) {
            addCriterion("cost <", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cost <=", value, "cost");
            return (Criteria) this;
        }

        public Criteria andCostIn(List<BigDecimal> values) {
            addCriterion("cost in", values, "cost");
            return (Criteria) this;
        }

        public Criteria andCostNotIn(List<BigDecimal> values) {
            addCriterion("cost not in", values, "cost");
            return (Criteria) this;
        }

        public Criteria andCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cost between", value1, value2, "cost");
            return (Criteria) this;
        }

        public Criteria andCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cost not between", value1, value2, "cost");
            return (Criteria) this;
        }

        public Criteria andBillTypeIsNull() {
            addCriterion("bill_type is null");
            return (Criteria) this;
        }

        public Criteria andBillTypeIsNotNull() {
            addCriterion("bill_type is not null");
            return (Criteria) this;
        }

        public Criteria andBillTypeEqualTo(String value) {
            addCriterion("bill_type =", value, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeNotEqualTo(String value) {
            addCriterion("bill_type <>", value, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeGreaterThan(String value) {
            addCriterion("bill_type >", value, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeGreaterThanOrEqualTo(String value) {
            addCriterion("bill_type >=", value, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeLessThan(String value) {
            addCriterion("bill_type <", value, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeLessThanOrEqualTo(String value) {
            addCriterion("bill_type <=", value, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeLike(String value) {
            addCriterion("bill_type like", value, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeNotLike(String value) {
            addCriterion("bill_type not like", value, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeIn(List<String> values) {
            addCriterion("bill_type in", values, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeNotIn(List<String> values) {
            addCriterion("bill_type not in", values, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeBetween(String value1, String value2) {
            addCriterion("bill_type between", value1, value2, "billType");
            return (Criteria) this;
        }

        public Criteria andBillTypeNotBetween(String value1, String value2) {
            addCriterion("bill_type not between", value1, value2, "billType");
            return (Criteria) this;
        }

        public Criteria andErrorStatusIsNull() {
            addCriterion("error_status is null");
            return (Criteria) this;
        }

        public Criteria andErrorStatusIsNotNull() {
            addCriterion("error_status is not null");
            return (Criteria) this;
        }

        public Criteria andErrorStatusEqualTo(String value) {
            addCriterion("error_status =", value, "errorStatus");
            return (Criteria) this;
        }

        public Criteria andErrorStatusNotEqualTo(String value) {
            addCriterion("error_status <>", value, "errorStatus");
            return (Criteria) this;
        }

        public Criteria andErrorStatusGreaterThan(String value) {
            addCriterion("error_status >", value, "errorStatus");
            return (Criteria) this;
        }

        public Criteria andErrorStatusGreaterThanOrEqualTo(String value) {
            addCriterion("error_status >=", value, "errorStatus");
            return (Criteria) this;
        }

        public Criteria andErrorStatusLessThan(String value) {
            addCriterion("error_status <", value, "errorStatus");
            return (Criteria) this;
        }

        public Criteria andErrorStatusLessThanOrEqualTo(String value) {
            addCriterion("error_status <=", value, "errorStatus");
            return (Criteria) this;
        }

        public Criteria andErrorStatusLike(String value) {
            addCriterion("error_status like", value, "errorStatus");
            return (Criteria) this;
        }

        public Criteria andErrorStatusNotLike(String value) {
            addCriterion("error_status not like", value, "errorStatus");
            return (Criteria) this;
        }

        public Criteria andErrorStatusIn(List<String> values) {
            addCriterion("error_status in", values, "errorStatus");
            return (Criteria) this;
        }

        public Criteria andErrorStatusNotIn(List<String> values) {
            addCriterion("error_status not in", values, "errorStatus");
            return (Criteria) this;
        }

        public Criteria andErrorStatusBetween(String value1, String value2) {
            addCriterion("error_status between", value1, value2, "errorStatus");
            return (Criteria) this;
        }

        public Criteria andErrorStatusNotBetween(String value1, String value2) {
            addCriterion("error_status not between", value1, value2, "errorStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNull() {
            addCriterion("check_status is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("check_status is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(String value) {
            addCriterion("check_status =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(String value) {
            addCriterion("check_status <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(String value) {
            addCriterion("check_status >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(String value) {
            addCriterion("check_status >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(String value) {
            addCriterion("check_status <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(String value) {
            addCriterion("check_status <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLike(String value) {
            addCriterion("check_status like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotLike(String value) {
            addCriterion("check_status not like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<String> values) {
            addCriterion("check_status in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<String> values) {
            addCriterion("check_status not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(String value1, String value2) {
            addCriterion("check_status between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(String value1, String value2) {
            addCriterion("check_status not between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckMessageIsNull() {
            addCriterion("check_message is null");
            return (Criteria) this;
        }

        public Criteria andCheckMessageIsNotNull() {
            addCriterion("check_message is not null");
            return (Criteria) this;
        }

        public Criteria andCheckMessageEqualTo(String value) {
            addCriterion("check_message =", value, "checkMessage");
            return (Criteria) this;
        }

        public Criteria andCheckMessageNotEqualTo(String value) {
            addCriterion("check_message <>", value, "checkMessage");
            return (Criteria) this;
        }

        public Criteria andCheckMessageGreaterThan(String value) {
            addCriterion("check_message >", value, "checkMessage");
            return (Criteria) this;
        }

        public Criteria andCheckMessageGreaterThanOrEqualTo(String value) {
            addCriterion("check_message >=", value, "checkMessage");
            return (Criteria) this;
        }

        public Criteria andCheckMessageLessThan(String value) {
            addCriterion("check_message <", value, "checkMessage");
            return (Criteria) this;
        }

        public Criteria andCheckMessageLessThanOrEqualTo(String value) {
            addCriterion("check_message <=", value, "checkMessage");
            return (Criteria) this;
        }

        public Criteria andCheckMessageLike(String value) {
            addCriterion("check_message like", value, "checkMessage");
            return (Criteria) this;
        }

        public Criteria andCheckMessageNotLike(String value) {
            addCriterion("check_message not like", value, "checkMessage");
            return (Criteria) this;
        }

        public Criteria andCheckMessageIn(List<String> values) {
            addCriterion("check_message in", values, "checkMessage");
            return (Criteria) this;
        }

        public Criteria andCheckMessageNotIn(List<String> values) {
            addCriterion("check_message not in", values, "checkMessage");
            return (Criteria) this;
        }

        public Criteria andCheckMessageBetween(String value1, String value2) {
            addCriterion("check_message between", value1, value2, "checkMessage");
            return (Criteria) this;
        }

        public Criteria andCheckMessageNotBetween(String value1, String value2) {
            addCriterion("check_message not between", value1, value2, "checkMessage");
            return (Criteria) this;
        }

        public Criteria andDateZipIsNull() {
            addCriterion("date_zip is null");
            return (Criteria) this;
        }

        public Criteria andDateZipIsNotNull() {
            addCriterion("date_zip is not null");
            return (Criteria) this;
        }

        public Criteria andDateZipEqualTo(Date value) {
            addCriterion("date_zip =", value, "dateZip");
            return (Criteria) this;
        }

        public Criteria andDateZipNotEqualTo(Date value) {
            addCriterion("date_zip <>", value, "dateZip");
            return (Criteria) this;
        }

        public Criteria andDateZipGreaterThan(Date value) {
            addCriterion("date_zip >", value, "dateZip");
            return (Criteria) this;
        }

        public Criteria andDateZipGreaterThanOrEqualTo(Date value) {
            addCriterion("date_zip >=", value, "dateZip");
            return (Criteria) this;
        }

        public Criteria andDateZipLessThan(Date value) {
            addCriterion("date_zip <", value, "dateZip");
            return (Criteria) this;
        }

        public Criteria andDateZipLessThanOrEqualTo(Date value) {
            addCriterion("date_zip <=", value, "dateZip");
            return (Criteria) this;
        }

        public Criteria andDateZipIn(List<Date> values) {
            addCriterion("date_zip in", values, "dateZip");
            return (Criteria) this;
        }

        public Criteria andDateZipNotIn(List<Date> values) {
            addCriterion("date_zip not in", values, "dateZip");
            return (Criteria) this;
        }

        public Criteria andDateZipBetween(Date value1, Date value2) {
            addCriterion("date_zip between", value1, value2, "dateZip");
            return (Criteria) this;
        }

        public Criteria andDateZipNotBetween(Date value1, Date value2) {
            addCriterion("date_zip not between", value1, value2, "dateZip");
            return (Criteria) this;
        }

        public Criteria andUpdateAuthorIsNull() {
            addCriterion("update_author is null");
            return (Criteria) this;
        }

        public Criteria andUpdateAuthorIsNotNull() {
            addCriterion("update_author is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateAuthorEqualTo(String value) {
            addCriterion("update_author =", value, "updateAuthor");
            return (Criteria) this;
        }

        public Criteria andUpdateAuthorNotEqualTo(String value) {
            addCriterion("update_author <>", value, "updateAuthor");
            return (Criteria) this;
        }

        public Criteria andUpdateAuthorGreaterThan(String value) {
            addCriterion("update_author >", value, "updateAuthor");
            return (Criteria) this;
        }

        public Criteria andUpdateAuthorGreaterThanOrEqualTo(String value) {
            addCriterion("update_author >=", value, "updateAuthor");
            return (Criteria) this;
        }

        public Criteria andUpdateAuthorLessThan(String value) {
            addCriterion("update_author <", value, "updateAuthor");
            return (Criteria) this;
        }

        public Criteria andUpdateAuthorLessThanOrEqualTo(String value) {
            addCriterion("update_author <=", value, "updateAuthor");
            return (Criteria) this;
        }

        public Criteria andUpdateAuthorLike(String value) {
            addCriterion("update_author like", value, "updateAuthor");
            return (Criteria) this;
        }

        public Criteria andUpdateAuthorNotLike(String value) {
            addCriterion("update_author not like", value, "updateAuthor");
            return (Criteria) this;
        }

        public Criteria andUpdateAuthorIn(List<String> values) {
            addCriterion("update_author in", values, "updateAuthor");
            return (Criteria) this;
        }

        public Criteria andUpdateAuthorNotIn(List<String> values) {
            addCriterion("update_author not in", values, "updateAuthor");
            return (Criteria) this;
        }

        public Criteria andUpdateAuthorBetween(String value1, String value2) {
            addCriterion("update_author between", value1, value2, "updateAuthor");
            return (Criteria) this;
        }

        public Criteria andUpdateAuthorNotBetween(String value1, String value2) {
            addCriterion("update_author not between", value1, value2, "updateAuthor");
            return (Criteria) this;
        }

        public Criteria andChannelSeqIsNull() {
            addCriterion("channel_seq is null");
            return (Criteria) this;
        }

        public Criteria andChannelSeqIsNotNull() {
            addCriterion("channel_seq is not null");
            return (Criteria) this;
        }

        public Criteria andChannelSeqEqualTo(String value) {
            addCriterion("channel_seq =", value, "channelSeq");
            return (Criteria) this;
        }

        public Criteria andChannelSeqNotEqualTo(String value) {
            addCriterion("channel_seq <>", value, "channelSeq");
            return (Criteria) this;
        }

        public Criteria andChannelSeqGreaterThan(String value) {
            addCriterion("channel_seq >", value, "channelSeq");
            return (Criteria) this;
        }

        public Criteria andChannelSeqGreaterThanOrEqualTo(String value) {
            addCriterion("channel_seq >=", value, "channelSeq");
            return (Criteria) this;
        }

        public Criteria andChannelSeqLessThan(String value) {
            addCriterion("channel_seq <", value, "channelSeq");
            return (Criteria) this;
        }

        public Criteria andChannelSeqLessThanOrEqualTo(String value) {
            addCriterion("channel_seq <=", value, "channelSeq");
            return (Criteria) this;
        }

        public Criteria andChannelSeqLike(String value) {
            addCriterion("channel_seq like", value, "channelSeq");
            return (Criteria) this;
        }

        public Criteria andChannelSeqNotLike(String value) {
            addCriterion("channel_seq not like", value, "channelSeq");
            return (Criteria) this;
        }

        public Criteria andChannelSeqIn(List<String> values) {
            addCriterion("channel_seq in", values, "channelSeq");
            return (Criteria) this;
        }

        public Criteria andChannelSeqNotIn(List<String> values) {
            addCriterion("channel_seq not in", values, "channelSeq");
            return (Criteria) this;
        }

        public Criteria andChannelSeqBetween(String value1, String value2) {
            addCriterion("channel_seq between", value1, value2, "channelSeq");
            return (Criteria) this;
        }

        public Criteria andChannelSeqNotBetween(String value1, String value2) {
            addCriterion("channel_seq not between", value1, value2, "channelSeq");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}