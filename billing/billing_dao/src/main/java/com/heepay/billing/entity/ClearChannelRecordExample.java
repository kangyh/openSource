package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClearChannelRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ClearChannelRecordExample() {
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

        public Criteria andClearingIdIsNull() {
            addCriterion("clearing_id is null");
            return (Criteria) this;
        }

        public Criteria andClearingIdIsNotNull() {
            addCriterion("clearing_id is not null");
            return (Criteria) this;
        }

        public Criteria andClearingIdEqualTo(Long value) {
            addCriterion("clearing_id =", value, "clearingId");
            return (Criteria) this;
        }

        public Criteria andClearingIdNotEqualTo(Long value) {
            addCriterion("clearing_id <>", value, "clearingId");
            return (Criteria) this;
        }

        public Criteria andClearingIdGreaterThan(Long value) {
            addCriterion("clearing_id >", value, "clearingId");
            return (Criteria) this;
        }

        public Criteria andClearingIdGreaterThanOrEqualTo(Long value) {
            addCriterion("clearing_id >=", value, "clearingId");
            return (Criteria) this;
        }

        public Criteria andClearingIdLessThan(Long value) {
            addCriterion("clearing_id <", value, "clearingId");
            return (Criteria) this;
        }

        public Criteria andClearingIdLessThanOrEqualTo(Long value) {
            addCriterion("clearing_id <=", value, "clearingId");
            return (Criteria) this;
        }

        public Criteria andClearingIdIn(List<Long> values) {
            addCriterion("clearing_id in", values, "clearingId");
            return (Criteria) this;
        }

        public Criteria andClearingIdNotIn(List<Long> values) {
            addCriterion("clearing_id not in", values, "clearingId");
            return (Criteria) this;
        }

        public Criteria andClearingIdBetween(Long value1, Long value2) {
            addCriterion("clearing_id between", value1, value2, "clearingId");
            return (Criteria) this;
        }

        public Criteria andClearingIdNotBetween(Long value1, Long value2) {
            addCriterion("clearing_id not between", value1, value2, "clearingId");
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

        public Criteria andChannelNameIsNull() {
            addCriterion("channel_name is null");
            return (Criteria) this;
        }

        public Criteria andChannelNameIsNotNull() {
            addCriterion("channel_name is not null");
            return (Criteria) this;
        }

        public Criteria andChannelNameEqualTo(String value) {
            addCriterion("channel_name =", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotEqualTo(String value) {
            addCriterion("channel_name <>", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameGreaterThan(String value) {
            addCriterion("channel_name >", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameGreaterThanOrEqualTo(String value) {
            addCriterion("channel_name >=", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLessThan(String value) {
            addCriterion("channel_name <", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLessThanOrEqualTo(String value) {
            addCriterion("channel_name <=", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLike(String value) {
            addCriterion("channel_name like", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotLike(String value) {
            addCriterion("channel_name not like", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameIn(List<String> values) {
            addCriterion("channel_name in", values, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotIn(List<String> values) {
            addCriterion("channel_name not in", values, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameBetween(String value1, String value2) {
            addCriterion("channel_name between", value1, value2, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotBetween(String value1, String value2) {
            addCriterion("channel_name not between", value1, value2, "channelName");
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

        public Criteria andPayTimeIsNull() {
            addCriterion("pay_time is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("pay_time is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(Date value) {
            addCriterion("pay_time =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(Date value) {
            addCriterion("pay_time <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(Date value) {
            addCriterion("pay_time >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("pay_time >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(Date value) {
            addCriterion("pay_time <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(Date value) {
            addCriterion("pay_time <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<Date> values) {
            addCriterion("pay_time in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<Date> values) {
            addCriterion("pay_time not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(Date value1, Date value2) {
            addCriterion("pay_time between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(Date value1, Date value2) {
            addCriterion("pay_time not between", value1, value2, "payTime");
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

        public Criteria andPaymentIdOldIsNull() {
            addCriterion("payment_id_old is null");
            return (Criteria) this;
        }

        public Criteria andPaymentIdOldIsNotNull() {
            addCriterion("payment_id_old is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentIdOldEqualTo(String value) {
            addCriterion("payment_id_old =", value, "paymentIdOld");
            return (Criteria) this;
        }

        public Criteria andPaymentIdOldNotEqualTo(String value) {
            addCriterion("payment_id_old <>", value, "paymentIdOld");
            return (Criteria) this;
        }

        public Criteria andPaymentIdOldGreaterThan(String value) {
            addCriterion("payment_id_old >", value, "paymentIdOld");
            return (Criteria) this;
        }

        public Criteria andPaymentIdOldGreaterThanOrEqualTo(String value) {
            addCriterion("payment_id_old >=", value, "paymentIdOld");
            return (Criteria) this;
        }

        public Criteria andPaymentIdOldLessThan(String value) {
            addCriterion("payment_id_old <", value, "paymentIdOld");
            return (Criteria) this;
        }

        public Criteria andPaymentIdOldLessThanOrEqualTo(String value) {
            addCriterion("payment_id_old <=", value, "paymentIdOld");
            return (Criteria) this;
        }

        public Criteria andPaymentIdOldLike(String value) {
            addCriterion("payment_id_old like", value, "paymentIdOld");
            return (Criteria) this;
        }

        public Criteria andPaymentIdOldNotLike(String value) {
            addCriterion("payment_id_old not like", value, "paymentIdOld");
            return (Criteria) this;
        }

        public Criteria andPaymentIdOldIn(List<String> values) {
            addCriterion("payment_id_old in", values, "paymentIdOld");
            return (Criteria) this;
        }

        public Criteria andPaymentIdOldNotIn(List<String> values) {
            addCriterion("payment_id_old not in", values, "paymentIdOld");
            return (Criteria) this;
        }

        public Criteria andPaymentIdOldBetween(String value1, String value2) {
            addCriterion("payment_id_old between", value1, value2, "paymentIdOld");
            return (Criteria) this;
        }

        public Criteria andPaymentIdOldNotBetween(String value1, String value2) {
            addCriterion("payment_id_old not between", value1, value2, "paymentIdOld");
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

        public Criteria andTransNoOldIsNull() {
            addCriterion("trans_no_old is null");
            return (Criteria) this;
        }

        public Criteria andTransNoOldIsNotNull() {
            addCriterion("trans_no_old is not null");
            return (Criteria) this;
        }

        public Criteria andTransNoOldEqualTo(String value) {
            addCriterion("trans_no_old =", value, "transNoOld");
            return (Criteria) this;
        }

        public Criteria andTransNoOldNotEqualTo(String value) {
            addCriterion("trans_no_old <>", value, "transNoOld");
            return (Criteria) this;
        }

        public Criteria andTransNoOldGreaterThan(String value) {
            addCriterion("trans_no_old >", value, "transNoOld");
            return (Criteria) this;
        }

        public Criteria andTransNoOldGreaterThanOrEqualTo(String value) {
            addCriterion("trans_no_old >=", value, "transNoOld");
            return (Criteria) this;
        }

        public Criteria andTransNoOldLessThan(String value) {
            addCriterion("trans_no_old <", value, "transNoOld");
            return (Criteria) this;
        }

        public Criteria andTransNoOldLessThanOrEqualTo(String value) {
            addCriterion("trans_no_old <=", value, "transNoOld");
            return (Criteria) this;
        }

        public Criteria andTransNoOldLike(String value) {
            addCriterion("trans_no_old like", value, "transNoOld");
            return (Criteria) this;
        }

        public Criteria andTransNoOldNotLike(String value) {
            addCriterion("trans_no_old not like", value, "transNoOld");
            return (Criteria) this;
        }

        public Criteria andTransNoOldIn(List<String> values) {
            addCriterion("trans_no_old in", values, "transNoOld");
            return (Criteria) this;
        }

        public Criteria andTransNoOldNotIn(List<String> values) {
            addCriterion("trans_no_old not in", values, "transNoOld");
            return (Criteria) this;
        }

        public Criteria andTransNoOldBetween(String value1, String value2) {
            addCriterion("trans_no_old between", value1, value2, "transNoOld");
            return (Criteria) this;
        }

        public Criteria andTransNoOldNotBetween(String value1, String value2) {
            addCriterion("trans_no_old not between", value1, value2, "transNoOld");
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

        public Criteria andChannelTimeIsNull() {
            addCriterion("channel_time is null");
            return (Criteria) this;
        }

        public Criteria andChannelTimeIsNotNull() {
            addCriterion("channel_time is not null");
            return (Criteria) this;
        }

        public Criteria andChannelTimeEqualTo(Date value) {
            addCriterion("channel_time =", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeNotEqualTo(Date value) {
            addCriterion("channel_time <>", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeGreaterThan(Date value) {
            addCriterion("channel_time >", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("channel_time >=", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeLessThan(Date value) {
            addCriterion("channel_time <", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeLessThanOrEqualTo(Date value) {
            addCriterion("channel_time <=", value, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeIn(List<Date> values) {
            addCriterion("channel_time in", values, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeNotIn(List<Date> values) {
            addCriterion("channel_time not in", values, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeBetween(Date value1, Date value2) {
            addCriterion("channel_time between", value1, value2, "channelTime");
            return (Criteria) this;
        }

        public Criteria andChannelTimeNotBetween(Date value1, Date value2) {
            addCriterion("channel_time not between", value1, value2, "channelTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIsNull() {
            addCriterion("check_time is null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIsNotNull() {
            addCriterion("check_time is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTimeEqualTo(Date value) {
            addCriterion("check_time =", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotEqualTo(Date value) {
            addCriterion("check_time <>", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThan(Date value) {
            addCriterion("check_time >", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("check_time >=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThan(Date value) {
            addCriterion("check_time <", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeLessThanOrEqualTo(Date value) {
            addCriterion("check_time <=", value, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeIn(List<Date> values) {
            addCriterion("check_time in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotIn(List<Date> values) {
            addCriterion("check_time not in", values, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeBetween(Date value1, Date value2) {
            addCriterion("check_time between", value1, value2, "checkTime");
            return (Criteria) this;
        }

        public Criteria andCheckTimeNotBetween(Date value1, Date value2) {
            addCriterion("check_time not between", value1, value2, "checkTime");
            return (Criteria) this;
        }

        public Criteria andSettleTimeIsNull() {
            addCriterion("settle_time is null");
            return (Criteria) this;
        }

        public Criteria andSettleTimeIsNotNull() {
            addCriterion("settle_time is not null");
            return (Criteria) this;
        }

        public Criteria andSettleTimeEqualTo(Date value) {
            addCriterion("settle_time =", value, "settleTime");
            return (Criteria) this;
        }

        public Criteria andSettleTimeNotEqualTo(Date value) {
            addCriterion("settle_time <>", value, "settleTime");
            return (Criteria) this;
        }

        public Criteria andSettleTimeGreaterThan(Date value) {
            addCriterion("settle_time >", value, "settleTime");
            return (Criteria) this;
        }

        public Criteria andSettleTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("settle_time >=", value, "settleTime");
            return (Criteria) this;
        }

        public Criteria andSettleTimeLessThan(Date value) {
            addCriterion("settle_time <", value, "settleTime");
            return (Criteria) this;
        }

        public Criteria andSettleTimeLessThanOrEqualTo(Date value) {
            addCriterion("settle_time <=", value, "settleTime");
            return (Criteria) this;
        }

        public Criteria andSettleTimeIn(List<Date> values) {
            addCriterion("settle_time in", values, "settleTime");
            return (Criteria) this;
        }

        public Criteria andSettleTimeNotIn(List<Date> values) {
            addCriterion("settle_time not in", values, "settleTime");
            return (Criteria) this;
        }

        public Criteria andSettleTimeBetween(Date value1, Date value2) {
            addCriterion("settle_time between", value1, value2, "settleTime");
            return (Criteria) this;
        }

        public Criteria andSettleTimeNotBetween(Date value1, Date value2) {
            addCriterion("settle_time not between", value1, value2, "settleTime");
            return (Criteria) this;
        }

        public Criteria andSettleNoIsNull() {
            addCriterion("settle_no is null");
            return (Criteria) this;
        }

        public Criteria andSettleNoIsNotNull() {
            addCriterion("settle_no is not null");
            return (Criteria) this;
        }

        public Criteria andSettleNoEqualTo(String value) {
            addCriterion("settle_no =", value, "settleNo");
            return (Criteria) this;
        }

        public Criteria andSettleNoNotEqualTo(String value) {
            addCriterion("settle_no <>", value, "settleNo");
            return (Criteria) this;
        }

        public Criteria andSettleNoGreaterThan(String value) {
            addCriterion("settle_no >", value, "settleNo");
            return (Criteria) this;
        }

        public Criteria andSettleNoGreaterThanOrEqualTo(String value) {
            addCriterion("settle_no >=", value, "settleNo");
            return (Criteria) this;
        }

        public Criteria andSettleNoLessThan(String value) {
            addCriterion("settle_no <", value, "settleNo");
            return (Criteria) this;
        }

        public Criteria andSettleNoLessThanOrEqualTo(String value) {
            addCriterion("settle_no <=", value, "settleNo");
            return (Criteria) this;
        }

        public Criteria andSettleNoLike(String value) {
            addCriterion("settle_no like", value, "settleNo");
            return (Criteria) this;
        }

        public Criteria andSettleNoNotLike(String value) {
            addCriterion("settle_no not like", value, "settleNo");
            return (Criteria) this;
        }

        public Criteria andSettleNoIn(List<String> values) {
            addCriterion("settle_no in", values, "settleNo");
            return (Criteria) this;
        }

        public Criteria andSettleNoNotIn(List<String> values) {
            addCriterion("settle_no not in", values, "settleNo");
            return (Criteria) this;
        }

        public Criteria andSettleNoBetween(String value1, String value2) {
            addCriterion("settle_no between", value1, value2, "settleNo");
            return (Criteria) this;
        }

        public Criteria andSettleNoNotBetween(String value1, String value2) {
            addCriterion("settle_no not between", value1, value2, "settleNo");
            return (Criteria) this;
        }

        public Criteria andSettleTimePlanIsNull() {
            addCriterion("settle_time_plan is null");
            return (Criteria) this;
        }

        public Criteria andSettleTimePlanIsNotNull() {
            addCriterion("settle_time_plan is not null");
            return (Criteria) this;
        }

        public Criteria andSettleTimePlanEqualTo(Date value) {
            addCriterion("settle_time_plan =", value, "settleTimePlan");
            return (Criteria) this;
        }

        public Criteria andSettleTimePlanNotEqualTo(Date value) {
            addCriterion("settle_time_plan <>", value, "settleTimePlan");
            return (Criteria) this;
        }

        public Criteria andSettleTimePlanGreaterThan(Date value) {
            addCriterion("settle_time_plan >", value, "settleTimePlan");
            return (Criteria) this;
        }

        public Criteria andSettleTimePlanGreaterThanOrEqualTo(Date value) {
            addCriterion("settle_time_plan >=", value, "settleTimePlan");
            return (Criteria) this;
        }

        public Criteria andSettleTimePlanLessThan(Date value) {
            addCriterion("settle_time_plan <", value, "settleTimePlan");
            return (Criteria) this;
        }

        public Criteria andSettleTimePlanLessThanOrEqualTo(Date value) {
            addCriterion("settle_time_plan <=", value, "settleTimePlan");
            return (Criteria) this;
        }

        public Criteria andSettleTimePlanIn(List<Date> values) {
            addCriterion("settle_time_plan in", values, "settleTimePlan");
            return (Criteria) this;
        }

        public Criteria andSettleTimePlanNotIn(List<Date> values) {
            addCriterion("settle_time_plan not in", values, "settleTimePlan");
            return (Criteria) this;
        }

        public Criteria andSettleTimePlanBetween(Date value1, Date value2) {
            addCriterion("settle_time_plan between", value1, value2, "settleTimePlan");
            return (Criteria) this;
        }

        public Criteria andSettleTimePlanNotBetween(Date value1, Date value2) {
            addCriterion("settle_time_plan not between", value1, value2, "settleTimePlan");
            return (Criteria) this;
        }

        public Criteria andSettleCycIsNull() {
            addCriterion("settle_cyc is null");
            return (Criteria) this;
        }

        public Criteria andSettleCycIsNotNull() {
            addCriterion("settle_cyc is not null");
            return (Criteria) this;
        }

        public Criteria andSettleCycEqualTo(String value) {
            addCriterion("settle_cyc =", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycNotEqualTo(String value) {
            addCriterion("settle_cyc <>", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycGreaterThan(String value) {
            addCriterion("settle_cyc >", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycGreaterThanOrEqualTo(String value) {
            addCriterion("settle_cyc >=", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycLessThan(String value) {
            addCriterion("settle_cyc <", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycLessThanOrEqualTo(String value) {
            addCriterion("settle_cyc <=", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycLike(String value) {
            addCriterion("settle_cyc like", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycNotLike(String value) {
            addCriterion("settle_cyc not like", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycIn(List<String> values) {
            addCriterion("settle_cyc in", values, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycNotIn(List<String> values) {
            addCriterion("settle_cyc not in", values, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycBetween(String value1, String value2) {
            addCriterion("settle_cyc between", value1, value2, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycNotBetween(String value1, String value2) {
            addCriterion("settle_cyc not between", value1, value2, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleBathIsNull() {
            addCriterion("settle_bath is null");
            return (Criteria) this;
        }

        public Criteria andSettleBathIsNotNull() {
            addCriterion("settle_bath is not null");
            return (Criteria) this;
        }

        public Criteria andSettleBathEqualTo(String value) {
            addCriterion("settle_bath =", value, "settleBath");
            return (Criteria) this;
        }

        public Criteria andSettleBathNotEqualTo(String value) {
            addCriterion("settle_bath <>", value, "settleBath");
            return (Criteria) this;
        }

        public Criteria andSettleBathGreaterThan(String value) {
            addCriterion("settle_bath >", value, "settleBath");
            return (Criteria) this;
        }

        public Criteria andSettleBathGreaterThanOrEqualTo(String value) {
            addCriterion("settle_bath >=", value, "settleBath");
            return (Criteria) this;
        }

        public Criteria andSettleBathLessThan(String value) {
            addCriterion("settle_bath <", value, "settleBath");
            return (Criteria) this;
        }

        public Criteria andSettleBathLessThanOrEqualTo(String value) {
            addCriterion("settle_bath <=", value, "settleBath");
            return (Criteria) this;
        }

        public Criteria andSettleBathLike(String value) {
            addCriterion("settle_bath like", value, "settleBath");
            return (Criteria) this;
        }

        public Criteria andSettleBathNotLike(String value) {
            addCriterion("settle_bath not like", value, "settleBath");
            return (Criteria) this;
        }

        public Criteria andSettleBathIn(List<String> values) {
            addCriterion("settle_bath in", values, "settleBath");
            return (Criteria) this;
        }

        public Criteria andSettleBathNotIn(List<String> values) {
            addCriterion("settle_bath not in", values, "settleBath");
            return (Criteria) this;
        }

        public Criteria andSettleBathBetween(String value1, String value2) {
            addCriterion("settle_bath between", value1, value2, "settleBath");
            return (Criteria) this;
        }

        public Criteria andSettleBathNotBetween(String value1, String value2) {
            addCriterion("settle_bath not between", value1, value2, "settleBath");
            return (Criteria) this;
        }

        public Criteria andCostTimeIsNull() {
            addCriterion("cost_time is null");
            return (Criteria) this;
        }

        public Criteria andCostTimeIsNotNull() {
            addCriterion("cost_time is not null");
            return (Criteria) this;
        }

        public Criteria andCostTimeEqualTo(Date value) {
            addCriterion("cost_time =", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeNotEqualTo(Date value) {
            addCriterion("cost_time <>", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeGreaterThan(Date value) {
            addCriterion("cost_time >", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("cost_time >=", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeLessThan(Date value) {
            addCriterion("cost_time <", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeLessThanOrEqualTo(Date value) {
            addCriterion("cost_time <=", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeIn(List<Date> values) {
            addCriterion("cost_time in", values, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeNotIn(List<Date> values) {
            addCriterion("cost_time not in", values, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeBetween(Date value1, Date value2) {
            addCriterion("cost_time between", value1, value2, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeNotBetween(Date value1, Date value2) {
            addCriterion("cost_time not between", value1, value2, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostAmountIsNull() {
            addCriterion("cost_amount is null");
            return (Criteria) this;
        }

        public Criteria andCostAmountIsNotNull() {
            addCriterion("cost_amount is not null");
            return (Criteria) this;
        }

        public Criteria andCostAmountEqualTo(BigDecimal value) {
            addCriterion("cost_amount =", value, "costAmount");
            return (Criteria) this;
        }

        public Criteria andCostAmountNotEqualTo(BigDecimal value) {
            addCriterion("cost_amount <>", value, "costAmount");
            return (Criteria) this;
        }

        public Criteria andCostAmountGreaterThan(BigDecimal value) {
            addCriterion("cost_amount >", value, "costAmount");
            return (Criteria) this;
        }

        public Criteria andCostAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("cost_amount >=", value, "costAmount");
            return (Criteria) this;
        }

        public Criteria andCostAmountLessThan(BigDecimal value) {
            addCriterion("cost_amount <", value, "costAmount");
            return (Criteria) this;
        }

        public Criteria andCostAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("cost_amount <=", value, "costAmount");
            return (Criteria) this;
        }

        public Criteria andCostAmountIn(List<BigDecimal> values) {
            addCriterion("cost_amount in", values, "costAmount");
            return (Criteria) this;
        }

        public Criteria andCostAmountNotIn(List<BigDecimal> values) {
            addCriterion("cost_amount not in", values, "costAmount");
            return (Criteria) this;
        }

        public Criteria andCostAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cost_amount between", value1, value2, "costAmount");
            return (Criteria) this;
        }

        public Criteria andCostAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("cost_amount not between", value1, value2, "costAmount");
            return (Criteria) this;
        }

        public Criteria andCostWayIsNull() {
            addCriterion("cost_way is null");
            return (Criteria) this;
        }

        public Criteria andCostWayIsNotNull() {
            addCriterion("cost_way is not null");
            return (Criteria) this;
        }

        public Criteria andCostWayEqualTo(String value) {
            addCriterion("cost_way =", value, "costWay");
            return (Criteria) this;
        }

        public Criteria andCostWayNotEqualTo(String value) {
            addCriterion("cost_way <>", value, "costWay");
            return (Criteria) this;
        }

        public Criteria andCostWayGreaterThan(String value) {
            addCriterion("cost_way >", value, "costWay");
            return (Criteria) this;
        }

        public Criteria andCostWayGreaterThanOrEqualTo(String value) {
            addCriterion("cost_way >=", value, "costWay");
            return (Criteria) this;
        }

        public Criteria andCostWayLessThan(String value) {
            addCriterion("cost_way <", value, "costWay");
            return (Criteria) this;
        }

        public Criteria andCostWayLessThanOrEqualTo(String value) {
            addCriterion("cost_way <=", value, "costWay");
            return (Criteria) this;
        }

        public Criteria andCostWayLike(String value) {
            addCriterion("cost_way like", value, "costWay");
            return (Criteria) this;
        }

        public Criteria andCostWayNotLike(String value) {
            addCriterion("cost_way not like", value, "costWay");
            return (Criteria) this;
        }

        public Criteria andCostWayIn(List<String> values) {
            addCriterion("cost_way in", values, "costWay");
            return (Criteria) this;
        }

        public Criteria andCostWayNotIn(List<String> values) {
            addCriterion("cost_way not in", values, "costWay");
            return (Criteria) this;
        }

        public Criteria andCostWayBetween(String value1, String value2) {
            addCriterion("cost_way between", value1, value2, "costWay");
            return (Criteria) this;
        }

        public Criteria andCostWayNotBetween(String value1, String value2) {
            addCriterion("cost_way not between", value1, value2, "costWay");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycIsNull() {
            addCriterion("cost_settle_cyc is null");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycIsNotNull() {
            addCriterion("cost_settle_cyc is not null");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycEqualTo(String value) {
            addCriterion("cost_settle_cyc =", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycNotEqualTo(String value) {
            addCriterion("cost_settle_cyc <>", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycGreaterThan(String value) {
            addCriterion("cost_settle_cyc >", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycGreaterThanOrEqualTo(String value) {
            addCriterion("cost_settle_cyc >=", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycLessThan(String value) {
            addCriterion("cost_settle_cyc <", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycLessThanOrEqualTo(String value) {
            addCriterion("cost_settle_cyc <=", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycLike(String value) {
            addCriterion("cost_settle_cyc like", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycNotLike(String value) {
            addCriterion("cost_settle_cyc not like", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycIn(List<String> values) {
            addCriterion("cost_settle_cyc in", values, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycNotIn(List<String> values) {
            addCriterion("cost_settle_cyc not in", values, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycBetween(String value1, String value2) {
            addCriterion("cost_settle_cyc between", value1, value2, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycNotBetween(String value1, String value2) {
            addCriterion("cost_settle_cyc not between", value1, value2, "costSettleCyc");
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

        public Criteria andCheckFlgIsNull() {
            addCriterion("check_flg is null");
            return (Criteria) this;
        }

        public Criteria andCheckFlgIsNotNull() {
            addCriterion("check_flg is not null");
            return (Criteria) this;
        }

        public Criteria andCheckFlgEqualTo(String value) {
            addCriterion("check_flg =", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgNotEqualTo(String value) {
            addCriterion("check_flg <>", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgGreaterThan(String value) {
            addCriterion("check_flg >", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgGreaterThanOrEqualTo(String value) {
            addCriterion("check_flg >=", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgLessThan(String value) {
            addCriterion("check_flg <", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgLessThanOrEqualTo(String value) {
            addCriterion("check_flg <=", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgLike(String value) {
            addCriterion("check_flg like", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgNotLike(String value) {
            addCriterion("check_flg not like", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgIn(List<String> values) {
            addCriterion("check_flg in", values, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgNotIn(List<String> values) {
            addCriterion("check_flg not in", values, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgBetween(String value1, String value2) {
            addCriterion("check_flg between", value1, value2, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgNotBetween(String value1, String value2) {
            addCriterion("check_flg not between", value1, value2, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andSettleStatusIsNull() {
            addCriterion("settle_status is null");
            return (Criteria) this;
        }

        public Criteria andSettleStatusIsNotNull() {
            addCriterion("settle_status is not null");
            return (Criteria) this;
        }

        public Criteria andSettleStatusEqualTo(String value) {
            addCriterion("settle_status =", value, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusNotEqualTo(String value) {
            addCriterion("settle_status <>", value, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusGreaterThan(String value) {
            addCriterion("settle_status >", value, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusGreaterThanOrEqualTo(String value) {
            addCriterion("settle_status >=", value, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusLessThan(String value) {
            addCriterion("settle_status <", value, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusLessThanOrEqualTo(String value) {
            addCriterion("settle_status <=", value, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusLike(String value) {
            addCriterion("settle_status like", value, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusNotLike(String value) {
            addCriterion("settle_status not like", value, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusIn(List<String> values) {
            addCriterion("settle_status in", values, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusNotIn(List<String> values) {
            addCriterion("settle_status not in", values, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusBetween(String value1, String value2) {
            addCriterion("settle_status between", value1, value2, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andSettleStatusNotBetween(String value1, String value2) {
            addCriterion("settle_status not between", value1, value2, "settleStatus");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNull() {
            addCriterion("finish_time is null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIsNotNull() {
            addCriterion("finish_time is not null");
            return (Criteria) this;
        }

        public Criteria andFinishTimeEqualTo(Date value) {
            addCriterion("finish_time =", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotEqualTo(Date value) {
            addCriterion("finish_time <>", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThan(Date value) {
            addCriterion("finish_time >", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("finish_time >=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThan(Date value) {
            addCriterion("finish_time <", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeLessThanOrEqualTo(Date value) {
            addCriterion("finish_time <=", value, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeIn(List<Date> values) {
            addCriterion("finish_time in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotIn(List<Date> values) {
            addCriterion("finish_time not in", values, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeBetween(Date value1, Date value2) {
            addCriterion("finish_time between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andFinishTimeNotBetween(Date value1, Date value2) {
            addCriterion("finish_time not between", value1, value2, "finishTime");
            return (Criteria) this;
        }

        public Criteria andCheckBathIsNull() {
            addCriterion("check_bath is null");
            return (Criteria) this;
        }

        public Criteria andCheckBathIsNotNull() {
            addCriterion("check_bath is not null");
            return (Criteria) this;
        }

        public Criteria andCheckBathEqualTo(String value) {
            addCriterion("check_bath =", value, "checkBath");
            return (Criteria) this;
        }

        public Criteria andCheckBathNotEqualTo(String value) {
            addCriterion("check_bath <>", value, "checkBath");
            return (Criteria) this;
        }

        public Criteria andCheckBathGreaterThan(String value) {
            addCriterion("check_bath >", value, "checkBath");
            return (Criteria) this;
        }

        public Criteria andCheckBathGreaterThanOrEqualTo(String value) {
            addCriterion("check_bath >=", value, "checkBath");
            return (Criteria) this;
        }

        public Criteria andCheckBathLessThan(String value) {
            addCriterion("check_bath <", value, "checkBath");
            return (Criteria) this;
        }

        public Criteria andCheckBathLessThanOrEqualTo(String value) {
            addCriterion("check_bath <=", value, "checkBath");
            return (Criteria) this;
        }

        public Criteria andCheckBathLike(String value) {
            addCriterion("check_bath like", value, "checkBath");
            return (Criteria) this;
        }

        public Criteria andCheckBathNotLike(String value) {
            addCriterion("check_bath not like", value, "checkBath");
            return (Criteria) this;
        }

        public Criteria andCheckBathIn(List<String> values) {
            addCriterion("check_bath in", values, "checkBath");
            return (Criteria) this;
        }

        public Criteria andCheckBathNotIn(List<String> values) {
            addCriterion("check_bath not in", values, "checkBath");
            return (Criteria) this;
        }

        public Criteria andCheckBathBetween(String value1, String value2) {
            addCriterion("check_bath between", value1, value2, "checkBath");
            return (Criteria) this;
        }

        public Criteria andCheckBathNotBetween(String value1, String value2) {
            addCriterion("check_bath not between", value1, value2, "checkBath");
            return (Criteria) this;
        }

        public Criteria andCheckNumIsNull() {
            addCriterion("check_num is null");
            return (Criteria) this;
        }

        public Criteria andCheckNumIsNotNull() {
            addCriterion("check_num is not null");
            return (Criteria) this;
        }

        public Criteria andCheckNumEqualTo(Integer value) {
            addCriterion("check_num =", value, "checkNum");
            return (Criteria) this;
        }

        public Criteria andCheckNumNotEqualTo(Integer value) {
            addCriterion("check_num <>", value, "checkNum");
            return (Criteria) this;
        }

        public Criteria andCheckNumGreaterThan(Integer value) {
            addCriterion("check_num >", value, "checkNum");
            return (Criteria) this;
        }

        public Criteria andCheckNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_num >=", value, "checkNum");
            return (Criteria) this;
        }

        public Criteria andCheckNumLessThan(Integer value) {
            addCriterion("check_num <", value, "checkNum");
            return (Criteria) this;
        }

        public Criteria andCheckNumLessThanOrEqualTo(Integer value) {
            addCriterion("check_num <=", value, "checkNum");
            return (Criteria) this;
        }

        public Criteria andCheckNumIn(List<Integer> values) {
            addCriterion("check_num in", values, "checkNum");
            return (Criteria) this;
        }

        public Criteria andCheckNumNotIn(List<Integer> values) {
            addCriterion("check_num not in", values, "checkNum");
            return (Criteria) this;
        }

        public Criteria andCheckNumBetween(Integer value1, Integer value2) {
            addCriterion("check_num between", value1, value2, "checkNum");
            return (Criteria) this;
        }

        public Criteria andCheckNumNotBetween(Integer value1, Integer value2) {
            addCriterion("check_num not between", value1, value2, "checkNum");
            return (Criteria) this;
        }

        public Criteria andTransTypeIsNull() {
            addCriterion("trans_type is null");
            return (Criteria) this;
        }

        public Criteria andTransTypeIsNotNull() {
            addCriterion("trans_type is not null");
            return (Criteria) this;
        }

        public Criteria andTransTypeEqualTo(String value) {
            addCriterion("trans_type =", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotEqualTo(String value) {
            addCriterion("trans_type <>", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeGreaterThan(String value) {
            addCriterion("trans_type >", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeGreaterThanOrEqualTo(String value) {
            addCriterion("trans_type >=", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLessThan(String value) {
            addCriterion("trans_type <", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLessThanOrEqualTo(String value) {
            addCriterion("trans_type <=", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeLike(String value) {
            addCriterion("trans_type like", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotLike(String value) {
            addCriterion("trans_type not like", value, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeIn(List<String> values) {
            addCriterion("trans_type in", values, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotIn(List<String> values) {
            addCriterion("trans_type not in", values, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeBetween(String value1, String value2) {
            addCriterion("trans_type between", value1, value2, "transType");
            return (Criteria) this;
        }

        public Criteria andTransTypeNotBetween(String value1, String value2) {
            addCriterion("trans_type not between", value1, value2, "transType");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andIsZipIsNull() {
            addCriterion("is_zip is null");
            return (Criteria) this;
        }

        public Criteria andIsZipIsNotNull() {
            addCriterion("is_zip is not null");
            return (Criteria) this;
        }

        public Criteria andIsZipEqualTo(String value) {
            addCriterion("is_zip =", value, "isZip");
            return (Criteria) this;
        }

        public Criteria andIsZipNotEqualTo(String value) {
            addCriterion("is_zip <>", value, "isZip");
            return (Criteria) this;
        }

        public Criteria andIsZipGreaterThan(String value) {
            addCriterion("is_zip >", value, "isZip");
            return (Criteria) this;
        }

        public Criteria andIsZipGreaterThanOrEqualTo(String value) {
            addCriterion("is_zip >=", value, "isZip");
            return (Criteria) this;
        }

        public Criteria andIsZipLessThan(String value) {
            addCriterion("is_zip <", value, "isZip");
            return (Criteria) this;
        }

        public Criteria andIsZipLessThanOrEqualTo(String value) {
            addCriterion("is_zip <=", value, "isZip");
            return (Criteria) this;
        }

        public Criteria andIsZipLike(String value) {
            addCriterion("is_zip like", value, "isZip");
            return (Criteria) this;
        }

        public Criteria andIsZipNotLike(String value) {
            addCriterion("is_zip not like", value, "isZip");
            return (Criteria) this;
        }

        public Criteria andIsZipIn(List<String> values) {
            addCriterion("is_zip in", values, "isZip");
            return (Criteria) this;
        }

        public Criteria andIsZipNotIn(List<String> values) {
            addCriterion("is_zip not in", values, "isZip");
            return (Criteria) this;
        }

        public Criteria andIsZipBetween(String value1, String value2) {
            addCriterion("is_zip between", value1, value2, "isZip");
            return (Criteria) this;
        }

        public Criteria andIsZipNotBetween(String value1, String value2) {
            addCriterion("is_zip not between", value1, value2, "isZip");
            return (Criteria) this;
        }

        public Criteria andBankSeqIsNull() {
            addCriterion("bank_seq is null");
            return (Criteria) this;
        }

        public Criteria andBankSeqIsNotNull() {
            addCriterion("bank_seq is not null");
            return (Criteria) this;
        }

        public Criteria andBankSeqEqualTo(String value) {
            addCriterion("bank_seq =", value, "bankSeq");
            return (Criteria) this;
        }

        public Criteria andBankSeqNotEqualTo(String value) {
            addCriterion("bank_seq <>", value, "bankSeq");
            return (Criteria) this;
        }

        public Criteria andBankSeqGreaterThan(String value) {
            addCriterion("bank_seq >", value, "bankSeq");
            return (Criteria) this;
        }

        public Criteria andBankSeqGreaterThanOrEqualTo(String value) {
            addCriterion("bank_seq >=", value, "bankSeq");
            return (Criteria) this;
        }

        public Criteria andBankSeqLessThan(String value) {
            addCriterion("bank_seq <", value, "bankSeq");
            return (Criteria) this;
        }

        public Criteria andBankSeqLessThanOrEqualTo(String value) {
            addCriterion("bank_seq <=", value, "bankSeq");
            return (Criteria) this;
        }

        public Criteria andBankSeqLike(String value) {
            addCriterion("bank_seq like", value, "bankSeq");
            return (Criteria) this;
        }

        public Criteria andBankSeqNotLike(String value) {
            addCriterion("bank_seq not like", value, "bankSeq");
            return (Criteria) this;
        }

        public Criteria andBankSeqIn(List<String> values) {
            addCriterion("bank_seq in", values, "bankSeq");
            return (Criteria) this;
        }

        public Criteria andBankSeqNotIn(List<String> values) {
            addCriterion("bank_seq not in", values, "bankSeq");
            return (Criteria) this;
        }

        public Criteria andBankSeqBetween(String value1, String value2) {
            addCriterion("bank_seq between", value1, value2, "bankSeq");
            return (Criteria) this;
        }

        public Criteria andBankSeqNotBetween(String value1, String value2) {
            addCriterion("bank_seq not between", value1, value2, "bankSeq");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNull() {
            addCriterion("bank_name is null");
            return (Criteria) this;
        }

        public Criteria andBankNameIsNotNull() {
            addCriterion("bank_name is not null");
            return (Criteria) this;
        }

        public Criteria andBankNameEqualTo(String value) {
            addCriterion("bank_name =", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotEqualTo(String value) {
            addCriterion("bank_name <>", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThan(String value) {
            addCriterion("bank_name >", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("bank_name >=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThan(String value) {
            addCriterion("bank_name <", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLessThanOrEqualTo(String value) {
            addCriterion("bank_name <=", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameLike(String value) {
            addCriterion("bank_name like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotLike(String value) {
            addCriterion("bank_name not like", value, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameIn(List<String> values) {
            addCriterion("bank_name in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotIn(List<String> values) {
            addCriterion("bank_name not in", values, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameBetween(String value1, String value2) {
            addCriterion("bank_name between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankNameNotBetween(String value1, String value2) {
            addCriterion("bank_name not between", value1, value2, "bankName");
            return (Criteria) this;
        }

        public Criteria andBankCodeIsNull() {
            addCriterion("bank_code is null");
            return (Criteria) this;
        }

        public Criteria andBankCodeIsNotNull() {
            addCriterion("bank_code is not null");
            return (Criteria) this;
        }

        public Criteria andBankCodeEqualTo(String value) {
            addCriterion("bank_code =", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotEqualTo(String value) {
            addCriterion("bank_code <>", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeGreaterThan(String value) {
            addCriterion("bank_code >", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeGreaterThanOrEqualTo(String value) {
            addCriterion("bank_code >=", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLessThan(String value) {
            addCriterion("bank_code <", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLessThanOrEqualTo(String value) {
            addCriterion("bank_code <=", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeLike(String value) {
            addCriterion("bank_code like", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotLike(String value) {
            addCriterion("bank_code not like", value, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeIn(List<String> values) {
            addCriterion("bank_code in", values, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotIn(List<String> values) {
            addCriterion("bank_code not in", values, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeBetween(String value1, String value2) {
            addCriterion("bank_code between", value1, value2, "bankCode");
            return (Criteria) this;
        }

        public Criteria andBankCodeNotBetween(String value1, String value2) {
            addCriterion("bank_code not between", value1, value2, "bankCode");
            return (Criteria) this;
        }

        public Criteria andChannelProviderIsNull() {
            addCriterion("channel_provider is null");
            return (Criteria) this;
        }

        public Criteria andChannelProviderIsNotNull() {
            addCriterion("channel_provider is not null");
            return (Criteria) this;
        }

        public Criteria andChannelProviderEqualTo(String value) {
            addCriterion("channel_provider =", value, "channelProvider");
            return (Criteria) this;
        }

        public Criteria andChannelProviderNotEqualTo(String value) {
            addCriterion("channel_provider <>", value, "channelProvider");
            return (Criteria) this;
        }

        public Criteria andChannelProviderGreaterThan(String value) {
            addCriterion("channel_provider >", value, "channelProvider");
            return (Criteria) this;
        }

        public Criteria andChannelProviderGreaterThanOrEqualTo(String value) {
            addCriterion("channel_provider >=", value, "channelProvider");
            return (Criteria) this;
        }

        public Criteria andChannelProviderLessThan(String value) {
            addCriterion("channel_provider <", value, "channelProvider");
            return (Criteria) this;
        }

        public Criteria andChannelProviderLessThanOrEqualTo(String value) {
            addCriterion("channel_provider <=", value, "channelProvider");
            return (Criteria) this;
        }

        public Criteria andChannelProviderLike(String value) {
            addCriterion("channel_provider like", value, "channelProvider");
            return (Criteria) this;
        }

        public Criteria andChannelProviderNotLike(String value) {
            addCriterion("channel_provider not like", value, "channelProvider");
            return (Criteria) this;
        }

        public Criteria andChannelProviderIn(List<String> values) {
            addCriterion("channel_provider in", values, "channelProvider");
            return (Criteria) this;
        }

        public Criteria andChannelProviderNotIn(List<String> values) {
            addCriterion("channel_provider not in", values, "channelProvider");
            return (Criteria) this;
        }

        public Criteria andChannelProviderBetween(String value1, String value2) {
            addCriterion("channel_provider between", value1, value2, "channelProvider");
            return (Criteria) this;
        }

        public Criteria andChannelProviderNotBetween(String value1, String value2) {
            addCriterion("channel_provider not between", value1, value2, "channelProvider");
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