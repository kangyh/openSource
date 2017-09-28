package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClearMerchantRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ClearMerchantRecordExample() {
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

        public Criteria andMerchantIdIsNull() {
            addCriterion("merchant_id is null");
            return (Criteria) this;
        }

        public Criteria andMerchantIdIsNotNull() {
            addCriterion("merchant_id is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantIdEqualTo(Integer value) {
            addCriterion("merchant_id =", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotEqualTo(Integer value) {
            addCriterion("merchant_id <>", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdGreaterThan(Integer value) {
            addCriterion("merchant_id >", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("merchant_id >=", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdLessThan(Integer value) {
            addCriterion("merchant_id <", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdLessThanOrEqualTo(Integer value) {
            addCriterion("merchant_id <=", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdIn(List<Integer> values) {
            addCriterion("merchant_id in", values, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotIn(List<Integer> values) {
            addCriterion("merchant_id not in", values, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdBetween(Integer value1, Integer value2) {
            addCriterion("merchant_id between", value1, value2, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotBetween(Integer value1, Integer value2) {
            addCriterion("merchant_id not between", value1, value2, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantTypeIsNull() {
            addCriterion("merchant_type is null");
            return (Criteria) this;
        }

        public Criteria andMerchantTypeIsNotNull() {
            addCriterion("merchant_type is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantTypeEqualTo(String value) {
            addCriterion("merchant_type =", value, "merchantType");
            return (Criteria) this;
        }

        public Criteria andMerchantTypeNotEqualTo(String value) {
            addCriterion("merchant_type <>", value, "merchantType");
            return (Criteria) this;
        }

        public Criteria andMerchantTypeGreaterThan(String value) {
            addCriterion("merchant_type >", value, "merchantType");
            return (Criteria) this;
        }

        public Criteria andMerchantTypeGreaterThanOrEqualTo(String value) {
            addCriterion("merchant_type >=", value, "merchantType");
            return (Criteria) this;
        }

        public Criteria andMerchantTypeLessThan(String value) {
            addCriterion("merchant_type <", value, "merchantType");
            return (Criteria) this;
        }

        public Criteria andMerchantTypeLessThanOrEqualTo(String value) {
            addCriterion("merchant_type <=", value, "merchantType");
            return (Criteria) this;
        }

        public Criteria andMerchantTypeLike(String value) {
            addCriterion("merchant_type like", value, "merchantType");
            return (Criteria) this;
        }

        public Criteria andMerchantTypeNotLike(String value) {
            addCriterion("merchant_type not like", value, "merchantType");
            return (Criteria) this;
        }

        public Criteria andMerchantTypeIn(List<String> values) {
            addCriterion("merchant_type in", values, "merchantType");
            return (Criteria) this;
        }

        public Criteria andMerchantTypeNotIn(List<String> values) {
            addCriterion("merchant_type not in", values, "merchantType");
            return (Criteria) this;
        }

        public Criteria andMerchantTypeBetween(String value1, String value2) {
            addCriterion("merchant_type between", value1, value2, "merchantType");
            return (Criteria) this;
        }

        public Criteria andMerchantTypeNotBetween(String value1, String value2) {
            addCriterion("merchant_type not between", value1, value2, "merchantType");
            return (Criteria) this;
        }

        public Criteria andProductCodeIsNull() {
            addCriterion("product_code is null");
            return (Criteria) this;
        }

        public Criteria andProductCodeIsNotNull() {
            addCriterion("product_code is not null");
            return (Criteria) this;
        }

        public Criteria andProductCodeEqualTo(String value) {
            addCriterion("product_code =", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotEqualTo(String value) {
            addCriterion("product_code <>", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThan(String value) {
            addCriterion("product_code >", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeGreaterThanOrEqualTo(String value) {
            addCriterion("product_code >=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThan(String value) {
            addCriterion("product_code <", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLessThanOrEqualTo(String value) {
            addCriterion("product_code <=", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeLike(String value) {
            addCriterion("product_code like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotLike(String value) {
            addCriterion("product_code not like", value, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeIn(List<String> values) {
            addCriterion("product_code in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotIn(List<String> values) {
            addCriterion("product_code not in", values, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeBetween(String value1, String value2) {
            addCriterion("product_code between", value1, value2, "productCode");
            return (Criteria) this;
        }

        public Criteria andProductCodeNotBetween(String value1, String value2) {
            addCriterion("product_code not between", value1, value2, "productCode");
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

        public Criteria andRequestAmountIsNull() {
            addCriterion("request_amount is null");
            return (Criteria) this;
        }

        public Criteria andRequestAmountIsNotNull() {
            addCriterion("request_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRequestAmountEqualTo(BigDecimal value) {
            addCriterion("request_amount =", value, "requestAmount");
            return (Criteria) this;
        }

        public Criteria andRequestAmountNotEqualTo(BigDecimal value) {
            addCriterion("request_amount <>", value, "requestAmount");
            return (Criteria) this;
        }

        public Criteria andRequestAmountGreaterThan(BigDecimal value) {
            addCriterion("request_amount >", value, "requestAmount");
            return (Criteria) this;
        }

        public Criteria andRequestAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("request_amount >=", value, "requestAmount");
            return (Criteria) this;
        }

        public Criteria andRequestAmountLessThan(BigDecimal value) {
            addCriterion("request_amount <", value, "requestAmount");
            return (Criteria) this;
        }

        public Criteria andRequestAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("request_amount <=", value, "requestAmount");
            return (Criteria) this;
        }

        public Criteria andRequestAmountIn(List<BigDecimal> values) {
            addCriterion("request_amount in", values, "requestAmount");
            return (Criteria) this;
        }

        public Criteria andRequestAmountNotIn(List<BigDecimal> values) {
            addCriterion("request_amount not in", values, "requestAmount");
            return (Criteria) this;
        }

        public Criteria andRequestAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("request_amount between", value1, value2, "requestAmount");
            return (Criteria) this;
        }

        public Criteria andRequestAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("request_amount not between", value1, value2, "requestAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeIsNull() {
            addCriterion("success_time is null");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeIsNotNull() {
            addCriterion("success_time is not null");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeEqualTo(Date value) {
            addCriterion("success_time =", value, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeNotEqualTo(Date value) {
            addCriterion("success_time <>", value, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeGreaterThan(Date value) {
            addCriterion("success_time >", value, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("success_time >=", value, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeLessThan(Date value) {
            addCriterion("success_time <", value, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeLessThanOrEqualTo(Date value) {
            addCriterion("success_time <=", value, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeIn(List<Date> values) {
            addCriterion("success_time in", values, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeNotIn(List<Date> values) {
            addCriterion("success_time not in", values, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeBetween(Date value1, Date value2) {
            addCriterion("success_time between", value1, value2, "successTime");
            return (Criteria) this;
        }

        public Criteria andSuccessTimeNotBetween(Date value1, Date value2) {
            addCriterion("success_time not between", value1, value2, "successTime");
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

        public Criteria andSettleAmountPlanIsNull() {
            addCriterion("settle_amount_plan is null");
            return (Criteria) this;
        }

        public Criteria andSettleAmountPlanIsNotNull() {
            addCriterion("settle_amount_plan is not null");
            return (Criteria) this;
        }

        public Criteria andSettleAmountPlanEqualTo(BigDecimal value) {
            addCriterion("settle_amount_plan =", value, "settleAmountPlan");
            return (Criteria) this;
        }

        public Criteria andSettleAmountPlanNotEqualTo(BigDecimal value) {
            addCriterion("settle_amount_plan <>", value, "settleAmountPlan");
            return (Criteria) this;
        }

        public Criteria andSettleAmountPlanGreaterThan(BigDecimal value) {
            addCriterion("settle_amount_plan >", value, "settleAmountPlan");
            return (Criteria) this;
        }

        public Criteria andSettleAmountPlanGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("settle_amount_plan >=", value, "settleAmountPlan");
            return (Criteria) this;
        }

        public Criteria andSettleAmountPlanLessThan(BigDecimal value) {
            addCriterion("settle_amount_plan <", value, "settleAmountPlan");
            return (Criteria) this;
        }

        public Criteria andSettleAmountPlanLessThanOrEqualTo(BigDecimal value) {
            addCriterion("settle_amount_plan <=", value, "settleAmountPlan");
            return (Criteria) this;
        }

        public Criteria andSettleAmountPlanIn(List<BigDecimal> values) {
            addCriterion("settle_amount_plan in", values, "settleAmountPlan");
            return (Criteria) this;
        }

        public Criteria andSettleAmountPlanNotIn(List<BigDecimal> values) {
            addCriterion("settle_amount_plan not in", values, "settleAmountPlan");
            return (Criteria) this;
        }

        public Criteria andSettleAmountPlanBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("settle_amount_plan between", value1, value2, "settleAmountPlan");
            return (Criteria) this;
        }

        public Criteria andSettleAmountPlanNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("settle_amount_plan not between", value1, value2, "settleAmountPlan");
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

        public Criteria andFeeTimeIsNull() {
            addCriterion("fee_time is null");
            return (Criteria) this;
        }

        public Criteria andFeeTimeIsNotNull() {
            addCriterion("fee_time is not null");
            return (Criteria) this;
        }

        public Criteria andFeeTimeEqualTo(Date value) {
            addCriterion("fee_time =", value, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeNotEqualTo(Date value) {
            addCriterion("fee_time <>", value, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeGreaterThan(Date value) {
            addCriterion("fee_time >", value, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("fee_time >=", value, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeLessThan(Date value) {
            addCriterion("fee_time <", value, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeLessThanOrEqualTo(Date value) {
            addCriterion("fee_time <=", value, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeIn(List<Date> values) {
            addCriterion("fee_time in", values, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeNotIn(List<Date> values) {
            addCriterion("fee_time not in", values, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeBetween(Date value1, Date value2) {
            addCriterion("fee_time between", value1, value2, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeTimeNotBetween(Date value1, Date value2) {
            addCriterion("fee_time not between", value1, value2, "feeTime");
            return (Criteria) this;
        }

        public Criteria andFeeIsNull() {
            addCriterion("fee is null");
            return (Criteria) this;
        }

        public Criteria andFeeIsNotNull() {
            addCriterion("fee is not null");
            return (Criteria) this;
        }

        public Criteria andFeeEqualTo(BigDecimal value) {
            addCriterion("fee =", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotEqualTo(BigDecimal value) {
            addCriterion("fee <>", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThan(BigDecimal value) {
            addCriterion("fee >", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("fee >=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThan(BigDecimal value) {
            addCriterion("fee <", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("fee <=", value, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeIn(List<BigDecimal> values) {
            addCriterion("fee in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotIn(List<BigDecimal> values) {
            addCriterion("fee not in", values, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee between", value1, value2, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee not between", value1, value2, "fee");
            return (Criteria) this;
        }

        public Criteria andFeeWayIsNull() {
            addCriterion("fee_way is null");
            return (Criteria) this;
        }

        public Criteria andFeeWayIsNotNull() {
            addCriterion("fee_way is not null");
            return (Criteria) this;
        }

        public Criteria andFeeWayEqualTo(String value) {
            addCriterion("fee_way =", value, "feeWay");
            return (Criteria) this;
        }

        public Criteria andFeeWayNotEqualTo(String value) {
            addCriterion("fee_way <>", value, "feeWay");
            return (Criteria) this;
        }

        public Criteria andFeeWayGreaterThan(String value) {
            addCriterion("fee_way >", value, "feeWay");
            return (Criteria) this;
        }

        public Criteria andFeeWayGreaterThanOrEqualTo(String value) {
            addCriterion("fee_way >=", value, "feeWay");
            return (Criteria) this;
        }

        public Criteria andFeeWayLessThan(String value) {
            addCriterion("fee_way <", value, "feeWay");
            return (Criteria) this;
        }

        public Criteria andFeeWayLessThanOrEqualTo(String value) {
            addCriterion("fee_way <=", value, "feeWay");
            return (Criteria) this;
        }

        public Criteria andFeeWayLike(String value) {
            addCriterion("fee_way like", value, "feeWay");
            return (Criteria) this;
        }

        public Criteria andFeeWayNotLike(String value) {
            addCriterion("fee_way not like", value, "feeWay");
            return (Criteria) this;
        }

        public Criteria andFeeWayIn(List<String> values) {
            addCriterion("fee_way in", values, "feeWay");
            return (Criteria) this;
        }

        public Criteria andFeeWayNotIn(List<String> values) {
            addCriterion("fee_way not in", values, "feeWay");
            return (Criteria) this;
        }

        public Criteria andFeeWayBetween(String value1, String value2) {
            addCriterion("fee_way between", value1, value2, "feeWay");
            return (Criteria) this;
        }

        public Criteria andFeeWayNotBetween(String value1, String value2) {
            addCriterion("fee_way not between", value1, value2, "feeWay");
            return (Criteria) this;
        }

        public Criteria andFeeSettleCycIsNull() {
            addCriterion("fee_settle_cyc is null");
            return (Criteria) this;
        }

        public Criteria andFeeSettleCycIsNotNull() {
            addCriterion("fee_settle_cyc is not null");
            return (Criteria) this;
        }

        public Criteria andFeeSettleCycEqualTo(String value) {
            addCriterion("fee_settle_cyc =", value, "feeSettleCyc");
            return (Criteria) this;
        }

        public Criteria andFeeSettleCycNotEqualTo(String value) {
            addCriterion("fee_settle_cyc <>", value, "feeSettleCyc");
            return (Criteria) this;
        }

        public Criteria andFeeSettleCycGreaterThan(String value) {
            addCriterion("fee_settle_cyc >", value, "feeSettleCyc");
            return (Criteria) this;
        }

        public Criteria andFeeSettleCycGreaterThanOrEqualTo(String value) {
            addCriterion("fee_settle_cyc >=", value, "feeSettleCyc");
            return (Criteria) this;
        }

        public Criteria andFeeSettleCycLessThan(String value) {
            addCriterion("fee_settle_cyc <", value, "feeSettleCyc");
            return (Criteria) this;
        }

        public Criteria andFeeSettleCycLessThanOrEqualTo(String value) {
            addCriterion("fee_settle_cyc <=", value, "feeSettleCyc");
            return (Criteria) this;
        }

        public Criteria andFeeSettleCycLike(String value) {
            addCriterion("fee_settle_cyc like", value, "feeSettleCyc");
            return (Criteria) this;
        }

        public Criteria andFeeSettleCycNotLike(String value) {
            addCriterion("fee_settle_cyc not like", value, "feeSettleCyc");
            return (Criteria) this;
        }

        public Criteria andFeeSettleCycIn(List<String> values) {
            addCriterion("fee_settle_cyc in", values, "feeSettleCyc");
            return (Criteria) this;
        }

        public Criteria andFeeSettleCycNotIn(List<String> values) {
            addCriterion("fee_settle_cyc not in", values, "feeSettleCyc");
            return (Criteria) this;
        }

        public Criteria andFeeSettleCycBetween(String value1, String value2) {
            addCriterion("fee_settle_cyc between", value1, value2, "feeSettleCyc");
            return (Criteria) this;
        }

        public Criteria andFeeSettleCycNotBetween(String value1, String value2) {
            addCriterion("fee_settle_cyc not between", value1, value2, "feeSettleCyc");
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

        public Criteria andIsProfitIsNull() {
            addCriterion("is_profit is null");
            return (Criteria) this;
        }

        public Criteria andIsProfitIsNotNull() {
            addCriterion("is_profit is not null");
            return (Criteria) this;
        }

        public Criteria andIsProfitEqualTo(String value) {
            addCriterion("is_profit =", value, "isProfit");
            return (Criteria) this;
        }

        public Criteria andIsProfitNotEqualTo(String value) {
            addCriterion("is_profit <>", value, "isProfit");
            return (Criteria) this;
        }

        public Criteria andIsProfitGreaterThan(String value) {
            addCriterion("is_profit >", value, "isProfit");
            return (Criteria) this;
        }

        public Criteria andIsProfitGreaterThanOrEqualTo(String value) {
            addCriterion("is_profit >=", value, "isProfit");
            return (Criteria) this;
        }

        public Criteria andIsProfitLessThan(String value) {
            addCriterion("is_profit <", value, "isProfit");
            return (Criteria) this;
        }

        public Criteria andIsProfitLessThanOrEqualTo(String value) {
            addCriterion("is_profit <=", value, "isProfit");
            return (Criteria) this;
        }

        public Criteria andIsProfitLike(String value) {
            addCriterion("is_profit like", value, "isProfit");
            return (Criteria) this;
        }

        public Criteria andIsProfitNotLike(String value) {
            addCriterion("is_profit not like", value, "isProfit");
            return (Criteria) this;
        }

        public Criteria andIsProfitIn(List<String> values) {
            addCriterion("is_profit in", values, "isProfit");
            return (Criteria) this;
        }

        public Criteria andIsProfitNotIn(List<String> values) {
            addCriterion("is_profit not in", values, "isProfit");
            return (Criteria) this;
        }

        public Criteria andIsProfitBetween(String value1, String value2) {
            addCriterion("is_profit between", value1, value2, "isProfit");
            return (Criteria) this;
        }

        public Criteria andIsProfitNotBetween(String value1, String value2) {
            addCriterion("is_profit not between", value1, value2, "isProfit");
            return (Criteria) this;
        }

        public Criteria andMerchantOrderNoIsNull() {
            addCriterion("merchant_order_no is null");
            return (Criteria) this;
        }

        public Criteria andMerchantOrderNoIsNotNull() {
            addCriterion("merchant_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantOrderNoEqualTo(String value) {
            addCriterion("merchant_order_no =", value, "merchantOrderNo");
            return (Criteria) this;
        }

        public Criteria andMerchantOrderNoNotEqualTo(String value) {
            addCriterion("merchant_order_no <>", value, "merchantOrderNo");
            return (Criteria) this;
        }

        public Criteria andMerchantOrderNoGreaterThan(String value) {
            addCriterion("merchant_order_no >", value, "merchantOrderNo");
            return (Criteria) this;
        }

        public Criteria andMerchantOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("merchant_order_no >=", value, "merchantOrderNo");
            return (Criteria) this;
        }

        public Criteria andMerchantOrderNoLessThan(String value) {
            addCriterion("merchant_order_no <", value, "merchantOrderNo");
            return (Criteria) this;
        }

        public Criteria andMerchantOrderNoLessThanOrEqualTo(String value) {
            addCriterion("merchant_order_no <=", value, "merchantOrderNo");
            return (Criteria) this;
        }

        public Criteria andMerchantOrderNoLike(String value) {
            addCriterion("merchant_order_no like", value, "merchantOrderNo");
            return (Criteria) this;
        }

        public Criteria andMerchantOrderNoNotLike(String value) {
            addCriterion("merchant_order_no not like", value, "merchantOrderNo");
            return (Criteria) this;
        }

        public Criteria andMerchantOrderNoIn(List<String> values) {
            addCriterion("merchant_order_no in", values, "merchantOrderNo");
            return (Criteria) this;
        }

        public Criteria andMerchantOrderNoNotIn(List<String> values) {
            addCriterion("merchant_order_no not in", values, "merchantOrderNo");
            return (Criteria) this;
        }

        public Criteria andMerchantOrderNoBetween(String value1, String value2) {
            addCriterion("merchant_order_no between", value1, value2, "merchantOrderNo");
            return (Criteria) this;
        }

        public Criteria andMerchantOrderNoNotBetween(String value1, String value2) {
            addCriterion("merchant_order_no not between", value1, value2, "merchantOrderNo");
            return (Criteria) this;
        }

        public Criteria andBusiTimeIsNull() {
            addCriterion("busi_time is null");
            return (Criteria) this;
        }

        public Criteria andBusiTimeIsNotNull() {
            addCriterion("busi_time is not null");
            return (Criteria) this;
        }

        public Criteria andBusiTimeEqualTo(Date value) {
            addCriterion("busi_time =", value, "busiTime");
            return (Criteria) this;
        }

        public Criteria andBusiTimeNotEqualTo(Date value) {
            addCriterion("busi_time <>", value, "busiTime");
            return (Criteria) this;
        }

        public Criteria andBusiTimeGreaterThan(Date value) {
            addCriterion("busi_time >", value, "busiTime");
            return (Criteria) this;
        }

        public Criteria andBusiTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("busi_time >=", value, "busiTime");
            return (Criteria) this;
        }

        public Criteria andBusiTimeLessThan(Date value) {
            addCriterion("busi_time <", value, "busiTime");
            return (Criteria) this;
        }

        public Criteria andBusiTimeLessThanOrEqualTo(Date value) {
            addCriterion("busi_time <=", value, "busiTime");
            return (Criteria) this;
        }

        public Criteria andBusiTimeIn(List<Date> values) {
            addCriterion("busi_time in", values, "busiTime");
            return (Criteria) this;
        }

        public Criteria andBusiTimeNotIn(List<Date> values) {
            addCriterion("busi_time not in", values, "busiTime");
            return (Criteria) this;
        }

        public Criteria andBusiTimeBetween(Date value1, Date value2) {
            addCriterion("busi_time between", value1, value2, "busiTime");
            return (Criteria) this;
        }

        public Criteria andBusiTimeNotBetween(Date value1, Date value2) {
            addCriterion("busi_time not between", value1, value2, "busiTime");
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

        public Criteria andAgentNameIsNull() {
            addCriterion("agent_name is null");
            return (Criteria) this;
        }

        public Criteria andAgentNameIsNotNull() {
            addCriterion("agent_name is not null");
            return (Criteria) this;
        }

        public Criteria andAgentNameEqualTo(String value) {
            addCriterion("agent_name =", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotEqualTo(String value) {
            addCriterion("agent_name <>", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThan(String value) {
            addCriterion("agent_name >", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("agent_name >=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThan(String value) {
            addCriterion("agent_name <", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThanOrEqualTo(String value) {
            addCriterion("agent_name <=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLike(String value) {
            addCriterion("agent_name like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotLike(String value) {
            addCriterion("agent_name not like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameIn(List<String> values) {
            addCriterion("agent_name in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotIn(List<String> values) {
            addCriterion("agent_name not in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameBetween(String value1, String value2) {
            addCriterion("agent_name between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotBetween(String value1, String value2) {
            addCriterion("agent_name not between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentCodeIsNull() {
            addCriterion("agent_code is null");
            return (Criteria) this;
        }

        public Criteria andAgentCodeIsNotNull() {
            addCriterion("agent_code is not null");
            return (Criteria) this;
        }

        public Criteria andAgentCodeEqualTo(Long value) {
            addCriterion("agent_code =", value, "agentCode");
            return (Criteria) this;
        }

        public Criteria andAgentCodeNotEqualTo(Long value) {
            addCriterion("agent_code <>", value, "agentCode");
            return (Criteria) this;
        }

        public Criteria andAgentCodeGreaterThan(Long value) {
            addCriterion("agent_code >", value, "agentCode");
            return (Criteria) this;
        }

        public Criteria andAgentCodeGreaterThanOrEqualTo(Long value) {
            addCriterion("agent_code >=", value, "agentCode");
            return (Criteria) this;
        }

        public Criteria andAgentCodeLessThan(Long value) {
            addCriterion("agent_code <", value, "agentCode");
            return (Criteria) this;
        }

        public Criteria andAgentCodeLessThanOrEqualTo(Long value) {
            addCriterion("agent_code <=", value, "agentCode");
            return (Criteria) this;
        }

        public Criteria andAgentCodeIn(List<Long> values) {
            addCriterion("agent_code in", values, "agentCode");
            return (Criteria) this;
        }

        public Criteria andAgentCodeNotIn(List<Long> values) {
            addCriterion("agent_code not in", values, "agentCode");
            return (Criteria) this;
        }

        public Criteria andAgentCodeBetween(Long value1, Long value2) {
            addCriterion("agent_code between", value1, value2, "agentCode");
            return (Criteria) this;
        }

        public Criteria andAgentCodeNotBetween(Long value1, Long value2) {
            addCriterion("agent_code not between", value1, value2, "agentCode");
            return (Criteria) this;
        }

        public Criteria andBankcardTypeIsNull() {
            addCriterion("bankcard_type is null");
            return (Criteria) this;
        }

        public Criteria andBankcardTypeIsNotNull() {
            addCriterion("bankcard_type is not null");
            return (Criteria) this;
        }

        public Criteria andBankcardTypeEqualTo(String value) {
            addCriterion("bankcard_type =", value, "bankcardType");
            return (Criteria) this;
        }

        public Criteria andBankcardTypeNotEqualTo(String value) {
            addCriterion("bankcard_type <>", value, "bankcardType");
            return (Criteria) this;
        }

        public Criteria andBankcardTypeGreaterThan(String value) {
            addCriterion("bankcard_type >", value, "bankcardType");
            return (Criteria) this;
        }

        public Criteria andBankcardTypeGreaterThanOrEqualTo(String value) {
            addCriterion("bankcard_type >=", value, "bankcardType");
            return (Criteria) this;
        }

        public Criteria andBankcardTypeLessThan(String value) {
            addCriterion("bankcard_type <", value, "bankcardType");
            return (Criteria) this;
        }

        public Criteria andBankcardTypeLessThanOrEqualTo(String value) {
            addCriterion("bankcard_type <=", value, "bankcardType");
            return (Criteria) this;
        }

        public Criteria andBankcardTypeLike(String value) {
            addCriterion("bankcard_type like", value, "bankcardType");
            return (Criteria) this;
        }

        public Criteria andBankcardTypeNotLike(String value) {
            addCriterion("bankcard_type not like", value, "bankcardType");
            return (Criteria) this;
        }

        public Criteria andBankcardTypeIn(List<String> values) {
            addCriterion("bankcard_type in", values, "bankcardType");
            return (Criteria) this;
        }

        public Criteria andBankcardTypeNotIn(List<String> values) {
            addCriterion("bankcard_type not in", values, "bankcardType");
            return (Criteria) this;
        }

        public Criteria andBankcardTypeBetween(String value1, String value2) {
            addCriterion("bankcard_type between", value1, value2, "bankcardType");
            return (Criteria) this;
        }

        public Criteria andBankcardTypeNotBetween(String value1, String value2) {
            addCriterion("bankcard_type not between", value1, value2, "bankcardType");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNull() {
            addCriterion("product_name is null");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNotNull() {
            addCriterion("product_name is not null");
            return (Criteria) this;
        }

        public Criteria andProductNameEqualTo(String value) {
            addCriterion("product_name =", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotEqualTo(String value) {
            addCriterion("product_name <>", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThan(String value) {
            addCriterion("product_name >", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThanOrEqualTo(String value) {
            addCriterion("product_name >=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThan(String value) {
            addCriterion("product_name <", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThanOrEqualTo(String value) {
            addCriterion("product_name <=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLike(String value) {
            addCriterion("product_name like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotLike(String value) {
            addCriterion("product_name not like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameIn(List<String> values) {
            addCriterion("product_name in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotIn(List<String> values) {
            addCriterion("product_name not in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameBetween(String value1, String value2) {
            addCriterion("product_name between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotBetween(String value1, String value2) {
            addCriterion("product_name not between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameIsNull() {
            addCriterion("merchant_name is null");
            return (Criteria) this;
        }

        public Criteria andMerchantNameIsNotNull() {
            addCriterion("merchant_name is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantNameEqualTo(String value) {
            addCriterion("merchant_name =", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotEqualTo(String value) {
            addCriterion("merchant_name <>", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameGreaterThan(String value) {
            addCriterion("merchant_name >", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameGreaterThanOrEqualTo(String value) {
            addCriterion("merchant_name >=", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameLessThan(String value) {
            addCriterion("merchant_name <", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameLessThanOrEqualTo(String value) {
            addCriterion("merchant_name <=", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameLike(String value) {
            addCriterion("merchant_name like", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotLike(String value) {
            addCriterion("merchant_name not like", value, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameIn(List<String> values) {
            addCriterion("merchant_name in", values, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotIn(List<String> values) {
            addCriterion("merchant_name not in", values, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameBetween(String value1, String value2) {
            addCriterion("merchant_name between", value1, value2, "merchantName");
            return (Criteria) this;
        }

        public Criteria andMerchantNameNotBetween(String value1, String value2) {
            addCriterion("merchant_name not between", value1, value2, "merchantName");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNull() {
            addCriterion("pay_type is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            addCriterion("pay_type is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(String value) {
            addCriterion("pay_type =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(String value) {
            addCriterion("pay_type <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(String value) {
            addCriterion("pay_type >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(String value) {
            addCriterion("pay_type >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(String value) {
            addCriterion("pay_type <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(String value) {
            addCriterion("pay_type <=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLike(String value) {
            addCriterion("pay_type like", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotLike(String value) {
            addCriterion("pay_type not like", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(List<String> values) {
            addCriterion("pay_type in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(List<String> values) {
            addCriterion("pay_type not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(String value1, String value2) {
            addCriterion("pay_type between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(String value1, String value2) {
            addCriterion("pay_type not between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andAccountNoIsNull() {
            addCriterion("account_no is null");
            return (Criteria) this;
        }

        public Criteria andAccountNoIsNotNull() {
            addCriterion("account_no is not null");
            return (Criteria) this;
        }

        public Criteria andAccountNoEqualTo(String value) {
            addCriterion("account_no =", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotEqualTo(String value) {
            addCriterion("account_no <>", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoGreaterThan(String value) {
            addCriterion("account_no >", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoGreaterThanOrEqualTo(String value) {
            addCriterion("account_no >=", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoLessThan(String value) {
            addCriterion("account_no <", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoLessThanOrEqualTo(String value) {
            addCriterion("account_no <=", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoLike(String value) {
            addCriterion("account_no like", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotLike(String value) {
            addCriterion("account_no not like", value, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoIn(List<String> values) {
            addCriterion("account_no in", values, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotIn(List<String> values) {
            addCriterion("account_no not in", values, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoBetween(String value1, String value2) {
            addCriterion("account_no between", value1, value2, "accountNo");
            return (Criteria) this;
        }

        public Criteria andAccountNoNotBetween(String value1, String value2) {
            addCriterion("account_no not between", value1, value2, "accountNo");
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