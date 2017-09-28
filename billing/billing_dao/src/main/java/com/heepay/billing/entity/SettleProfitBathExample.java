package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SettleProfitBathExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SettleProfitBathExample() {
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

        public Criteria andSettleProfitIdIsNull() {
            addCriterion("settle_profit_id is null");
            return (Criteria) this;
        }

        public Criteria andSettleProfitIdIsNotNull() {
            addCriterion("settle_profit_id is not null");
            return (Criteria) this;
        }

        public Criteria andSettleProfitIdEqualTo(Long value) {
            addCriterion("settle_profit_id =", value, "settleProfitId");
            return (Criteria) this;
        }

        public Criteria andSettleProfitIdNotEqualTo(Long value) {
            addCriterion("settle_profit_id <>", value, "settleProfitId");
            return (Criteria) this;
        }

        public Criteria andSettleProfitIdGreaterThan(Long value) {
            addCriterion("settle_profit_id >", value, "settleProfitId");
            return (Criteria) this;
        }

        public Criteria andSettleProfitIdGreaterThanOrEqualTo(Long value) {
            addCriterion("settle_profit_id >=", value, "settleProfitId");
            return (Criteria) this;
        }

        public Criteria andSettleProfitIdLessThan(Long value) {
            addCriterion("settle_profit_id <", value, "settleProfitId");
            return (Criteria) this;
        }

        public Criteria andSettleProfitIdLessThanOrEqualTo(Long value) {
            addCriterion("settle_profit_id <=", value, "settleProfitId");
            return (Criteria) this;
        }

        public Criteria andSettleProfitIdIn(List<Long> values) {
            addCriterion("settle_profit_id in", values, "settleProfitId");
            return (Criteria) this;
        }

        public Criteria andSettleProfitIdNotIn(List<Long> values) {
            addCriterion("settle_profit_id not in", values, "settleProfitId");
            return (Criteria) this;
        }

        public Criteria andSettleProfitIdBetween(Long value1, Long value2) {
            addCriterion("settle_profit_id between", value1, value2, "settleProfitId");
            return (Criteria) this;
        }

        public Criteria andSettleProfitIdNotBetween(Long value1, Long value2) {
            addCriterion("settle_profit_id not between", value1, value2, "settleProfitId");
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

        public Criteria andProfitBathIsNull() {
            addCriterion("profit_bath is null");
            return (Criteria) this;
        }

        public Criteria andProfitBathIsNotNull() {
            addCriterion("profit_bath is not null");
            return (Criteria) this;
        }

        public Criteria andProfitBathEqualTo(String value) {
            addCriterion("profit_bath =", value, "profitBath");
            return (Criteria) this;
        }

        public Criteria andProfitBathNotEqualTo(String value) {
            addCriterion("profit_bath <>", value, "profitBath");
            return (Criteria) this;
        }

        public Criteria andProfitBathGreaterThan(String value) {
            addCriterion("profit_bath >", value, "profitBath");
            return (Criteria) this;
        }

        public Criteria andProfitBathGreaterThanOrEqualTo(String value) {
            addCriterion("profit_bath >=", value, "profitBath");
            return (Criteria) this;
        }

        public Criteria andProfitBathLessThan(String value) {
            addCriterion("profit_bath <", value, "profitBath");
            return (Criteria) this;
        }

        public Criteria andProfitBathLessThanOrEqualTo(String value) {
            addCriterion("profit_bath <=", value, "profitBath");
            return (Criteria) this;
        }

        public Criteria andProfitBathLike(String value) {
            addCriterion("profit_bath like", value, "profitBath");
            return (Criteria) this;
        }

        public Criteria andProfitBathNotLike(String value) {
            addCriterion("profit_bath not like", value, "profitBath");
            return (Criteria) this;
        }

        public Criteria andProfitBathIn(List<String> values) {
            addCriterion("profit_bath in", values, "profitBath");
            return (Criteria) this;
        }

        public Criteria andProfitBathNotIn(List<String> values) {
            addCriterion("profit_bath not in", values, "profitBath");
            return (Criteria) this;
        }

        public Criteria andProfitBathBetween(String value1, String value2) {
            addCriterion("profit_bath between", value1, value2, "profitBath");
            return (Criteria) this;
        }

        public Criteria andProfitBathNotBetween(String value1, String value2) {
            addCriterion("profit_bath not between", value1, value2, "profitBath");
            return (Criteria) this;
        }

        public Criteria andProfitDateIsNull() {
            addCriterion("profit_date is null");
            return (Criteria) this;
        }

        public Criteria andProfitDateIsNotNull() {
            addCriterion("profit_date is not null");
            return (Criteria) this;
        }

        public Criteria andProfitDateEqualTo(Date value) {
            addCriterion("profit_date =", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateNotEqualTo(Date value) {
            addCriterion("profit_date <>", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateGreaterThan(Date value) {
            addCriterion("profit_date >", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateGreaterThanOrEqualTo(Date value) {
            addCriterion("profit_date >=", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateLessThan(Date value) {
            addCriterion("profit_date <", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateLessThanOrEqualTo(Date value) {
            addCriterion("profit_date <=", value, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateIn(List<Date> values) {
            addCriterion("profit_date in", values, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateNotIn(List<Date> values) {
            addCriterion("profit_date not in", values, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateBetween(Date value1, Date value2) {
            addCriterion("profit_date between", value1, value2, "profitDate");
            return (Criteria) this;
        }

        public Criteria andProfitDateNotBetween(Date value1, Date value2) {
            addCriterion("profit_date not between", value1, value2, "profitDate");
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

        public Criteria andProfitAmountIsNull() {
            addCriterion("profit_amount is null");
            return (Criteria) this;
        }

        public Criteria andProfitAmountIsNotNull() {
            addCriterion("profit_amount is not null");
            return (Criteria) this;
        }

        public Criteria andProfitAmountEqualTo(BigDecimal value) {
            addCriterion("profit_amount =", value, "profitAmount");
            return (Criteria) this;
        }

        public Criteria andProfitAmountNotEqualTo(BigDecimal value) {
            addCriterion("profit_amount <>", value, "profitAmount");
            return (Criteria) this;
        }

        public Criteria andProfitAmountGreaterThan(BigDecimal value) {
            addCriterion("profit_amount >", value, "profitAmount");
            return (Criteria) this;
        }

        public Criteria andProfitAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("profit_amount >=", value, "profitAmount");
            return (Criteria) this;
        }

        public Criteria andProfitAmountLessThan(BigDecimal value) {
            addCriterion("profit_amount <", value, "profitAmount");
            return (Criteria) this;
        }

        public Criteria andProfitAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("profit_amount <=", value, "profitAmount");
            return (Criteria) this;
        }

        public Criteria andProfitAmountIn(List<BigDecimal> values) {
            addCriterion("profit_amount in", values, "profitAmount");
            return (Criteria) this;
        }

        public Criteria andProfitAmountNotIn(List<BigDecimal> values) {
            addCriterion("profit_amount not in", values, "profitAmount");
            return (Criteria) this;
        }

        public Criteria andProfitAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit_amount between", value1, value2, "profitAmount");
            return (Criteria) this;
        }

        public Criteria andProfitAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit_amount not between", value1, value2, "profitAmount");
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

        public Criteria andProfitStatusIsNull() {
            addCriterion("profit_status is null");
            return (Criteria) this;
        }

        public Criteria andProfitStatusIsNotNull() {
            addCriterion("profit_status is not null");
            return (Criteria) this;
        }

        public Criteria andProfitStatusEqualTo(String value) {
            addCriterion("profit_status =", value, "profitStatus");
            return (Criteria) this;
        }

        public Criteria andProfitStatusNotEqualTo(String value) {
            addCriterion("profit_status <>", value, "profitStatus");
            return (Criteria) this;
        }

        public Criteria andProfitStatusGreaterThan(String value) {
            addCriterion("profit_status >", value, "profitStatus");
            return (Criteria) this;
        }

        public Criteria andProfitStatusGreaterThanOrEqualTo(String value) {
            addCriterion("profit_status >=", value, "profitStatus");
            return (Criteria) this;
        }

        public Criteria andProfitStatusLessThan(String value) {
            addCriterion("profit_status <", value, "profitStatus");
            return (Criteria) this;
        }

        public Criteria andProfitStatusLessThanOrEqualTo(String value) {
            addCriterion("profit_status <=", value, "profitStatus");
            return (Criteria) this;
        }

        public Criteria andProfitStatusLike(String value) {
            addCriterion("profit_status like", value, "profitStatus");
            return (Criteria) this;
        }

        public Criteria andProfitStatusNotLike(String value) {
            addCriterion("profit_status not like", value, "profitStatus");
            return (Criteria) this;
        }

        public Criteria andProfitStatusIn(List<String> values) {
            addCriterion("profit_status in", values, "profitStatus");
            return (Criteria) this;
        }

        public Criteria andProfitStatusNotIn(List<String> values) {
            addCriterion("profit_status not in", values, "profitStatus");
            return (Criteria) this;
        }

        public Criteria andProfitStatusBetween(String value1, String value2) {
            addCriterion("profit_status between", value1, value2, "profitStatus");
            return (Criteria) this;
        }

        public Criteria andProfitStatusNotBetween(String value1, String value2) {
            addCriterion("profit_status not between", value1, value2, "profitStatus");
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