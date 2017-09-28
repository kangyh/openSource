package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClearProfitRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ClearProfitRecordExample() {
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

        public Criteria andProfitIdIsNull() {
            addCriterion("profit_id is null");
            return (Criteria) this;
        }

        public Criteria andProfitIdIsNotNull() {
            addCriterion("profit_id is not null");
            return (Criteria) this;
        }

        public Criteria andProfitIdEqualTo(Long value) {
            addCriterion("profit_id =", value, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdNotEqualTo(Long value) {
            addCriterion("profit_id <>", value, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdGreaterThan(Long value) {
            addCriterion("profit_id >", value, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdGreaterThanOrEqualTo(Long value) {
            addCriterion("profit_id >=", value, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdLessThan(Long value) {
            addCriterion("profit_id <", value, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdLessThanOrEqualTo(Long value) {
            addCriterion("profit_id <=", value, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdIn(List<Long> values) {
            addCriterion("profit_id in", values, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdNotIn(List<Long> values) {
            addCriterion("profit_id not in", values, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdBetween(Long value1, Long value2) {
            addCriterion("profit_id between", value1, value2, "profitId");
            return (Criteria) this;
        }

        public Criteria andProfitIdNotBetween(Long value1, Long value2) {
            addCriterion("profit_id not between", value1, value2, "profitId");
            return (Criteria) this;
        }

        public Criteria andShareDetailIdIsNull() {
            addCriterion("share_detail_id is null");
            return (Criteria) this;
        }

        public Criteria andShareDetailIdIsNotNull() {
            addCriterion("share_detail_id is not null");
            return (Criteria) this;
        }

        public Criteria andShareDetailIdEqualTo(Long value) {
            addCriterion("share_detail_id =", value, "shareDetailId");
            return (Criteria) this;
        }

        public Criteria andShareDetailIdNotEqualTo(Long value) {
            addCriterion("share_detail_id <>", value, "shareDetailId");
            return (Criteria) this;
        }

        public Criteria andShareDetailIdGreaterThan(Long value) {
            addCriterion("share_detail_id >", value, "shareDetailId");
            return (Criteria) this;
        }

        public Criteria andShareDetailIdGreaterThanOrEqualTo(Long value) {
            addCriterion("share_detail_id >=", value, "shareDetailId");
            return (Criteria) this;
        }

        public Criteria andShareDetailIdLessThan(Long value) {
            addCriterion("share_detail_id <", value, "shareDetailId");
            return (Criteria) this;
        }

        public Criteria andShareDetailIdLessThanOrEqualTo(Long value) {
            addCriterion("share_detail_id <=", value, "shareDetailId");
            return (Criteria) this;
        }

        public Criteria andShareDetailIdIn(List<Long> values) {
            addCriterion("share_detail_id in", values, "shareDetailId");
            return (Criteria) this;
        }

        public Criteria andShareDetailIdNotIn(List<Long> values) {
            addCriterion("share_detail_id not in", values, "shareDetailId");
            return (Criteria) this;
        }

        public Criteria andShareDetailIdBetween(Long value1, Long value2) {
            addCriterion("share_detail_id between", value1, value2, "shareDetailId");
            return (Criteria) this;
        }

        public Criteria andShareDetailIdNotBetween(Long value1, Long value2) {
            addCriterion("share_detail_id not between", value1, value2, "shareDetailId");
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

        public Criteria andProfitAmountPlanIsNull() {
            addCriterion("profit_amount_plan is null");
            return (Criteria) this;
        }

        public Criteria andProfitAmountPlanIsNotNull() {
            addCriterion("profit_amount_plan is not null");
            return (Criteria) this;
        }

        public Criteria andProfitAmountPlanEqualTo(BigDecimal value) {
            addCriterion("profit_amount_plan =", value, "profitAmountPlan");
            return (Criteria) this;
        }

        public Criteria andProfitAmountPlanNotEqualTo(BigDecimal value) {
            addCriterion("profit_amount_plan <>", value, "profitAmountPlan");
            return (Criteria) this;
        }

        public Criteria andProfitAmountPlanGreaterThan(BigDecimal value) {
            addCriterion("profit_amount_plan >", value, "profitAmountPlan");
            return (Criteria) this;
        }

        public Criteria andProfitAmountPlanGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("profit_amount_plan >=", value, "profitAmountPlan");
            return (Criteria) this;
        }

        public Criteria andProfitAmountPlanLessThan(BigDecimal value) {
            addCriterion("profit_amount_plan <", value, "profitAmountPlan");
            return (Criteria) this;
        }

        public Criteria andProfitAmountPlanLessThanOrEqualTo(BigDecimal value) {
            addCriterion("profit_amount_plan <=", value, "profitAmountPlan");
            return (Criteria) this;
        }

        public Criteria andProfitAmountPlanIn(List<BigDecimal> values) {
            addCriterion("profit_amount_plan in", values, "profitAmountPlan");
            return (Criteria) this;
        }

        public Criteria andProfitAmountPlanNotIn(List<BigDecimal> values) {
            addCriterion("profit_amount_plan not in", values, "profitAmountPlan");
            return (Criteria) this;
        }

        public Criteria andProfitAmountPlanBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit_amount_plan between", value1, value2, "profitAmountPlan");
            return (Criteria) this;
        }

        public Criteria andProfitAmountPlanNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit_amount_plan not between", value1, value2, "profitAmountPlan");
            return (Criteria) this;
        }

        public Criteria andProfitFeeIsNull() {
            addCriterion("profit_fee is null");
            return (Criteria) this;
        }

        public Criteria andProfitFeeIsNotNull() {
            addCriterion("profit_fee is not null");
            return (Criteria) this;
        }

        public Criteria andProfitFeeEqualTo(BigDecimal value) {
            addCriterion("profit_fee =", value, "profitFee");
            return (Criteria) this;
        }

        public Criteria andProfitFeeNotEqualTo(BigDecimal value) {
            addCriterion("profit_fee <>", value, "profitFee");
            return (Criteria) this;
        }

        public Criteria andProfitFeeGreaterThan(BigDecimal value) {
            addCriterion("profit_fee >", value, "profitFee");
            return (Criteria) this;
        }

        public Criteria andProfitFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("profit_fee >=", value, "profitFee");
            return (Criteria) this;
        }

        public Criteria andProfitFeeLessThan(BigDecimal value) {
            addCriterion("profit_fee <", value, "profitFee");
            return (Criteria) this;
        }

        public Criteria andProfitFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("profit_fee <=", value, "profitFee");
            return (Criteria) this;
        }

        public Criteria andProfitFeeIn(List<BigDecimal> values) {
            addCriterion("profit_fee in", values, "profitFee");
            return (Criteria) this;
        }

        public Criteria andProfitFeeNotIn(List<BigDecimal> values) {
            addCriterion("profit_fee not in", values, "profitFee");
            return (Criteria) this;
        }

        public Criteria andProfitFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit_fee between", value1, value2, "profitFee");
            return (Criteria) this;
        }

        public Criteria andProfitFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit_fee not between", value1, value2, "profitFee");
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

        public Criteria andProfitTimeIsNull() {
            addCriterion("profit_time is null");
            return (Criteria) this;
        }

        public Criteria andProfitTimeIsNotNull() {
            addCriterion("profit_time is not null");
            return (Criteria) this;
        }

        public Criteria andProfitTimeEqualTo(Date value) {
            addCriterion("profit_time =", value, "profitTime");
            return (Criteria) this;
        }

        public Criteria andProfitTimeNotEqualTo(Date value) {
            addCriterion("profit_time <>", value, "profitTime");
            return (Criteria) this;
        }

        public Criteria andProfitTimeGreaterThan(Date value) {
            addCriterion("profit_time >", value, "profitTime");
            return (Criteria) this;
        }

        public Criteria andProfitTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("profit_time >=", value, "profitTime");
            return (Criteria) this;
        }

        public Criteria andProfitTimeLessThan(Date value) {
            addCriterion("profit_time <", value, "profitTime");
            return (Criteria) this;
        }

        public Criteria andProfitTimeLessThanOrEqualTo(Date value) {
            addCriterion("profit_time <=", value, "profitTime");
            return (Criteria) this;
        }

        public Criteria andProfitTimeIn(List<Date> values) {
            addCriterion("profit_time in", values, "profitTime");
            return (Criteria) this;
        }

        public Criteria andProfitTimeNotIn(List<Date> values) {
            addCriterion("profit_time not in", values, "profitTime");
            return (Criteria) this;
        }

        public Criteria andProfitTimeBetween(Date value1, Date value2) {
            addCriterion("profit_time between", value1, value2, "profitTime");
            return (Criteria) this;
        }

        public Criteria andProfitTimeNotBetween(Date value1, Date value2) {
            addCriterion("profit_time not between", value1, value2, "profitTime");
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

        public Criteria andShareIdIsNull() {
            addCriterion("share_id is null");
            return (Criteria) this;
        }

        public Criteria andShareIdIsNotNull() {
            addCriterion("share_id is not null");
            return (Criteria) this;
        }

        public Criteria andShareIdEqualTo(Long value) {
            addCriterion("share_id =", value, "shareId");
            return (Criteria) this;
        }

        public Criteria andShareIdNotEqualTo(Long value) {
            addCriterion("share_id <>", value, "shareId");
            return (Criteria) this;
        }

        public Criteria andShareIdGreaterThan(Long value) {
            addCriterion("share_id >", value, "shareId");
            return (Criteria) this;
        }

        public Criteria andShareIdGreaterThanOrEqualTo(Long value) {
            addCriterion("share_id >=", value, "shareId");
            return (Criteria) this;
        }

        public Criteria andShareIdLessThan(Long value) {
            addCriterion("share_id <", value, "shareId");
            return (Criteria) this;
        }

        public Criteria andShareIdLessThanOrEqualTo(Long value) {
            addCriterion("share_id <=", value, "shareId");
            return (Criteria) this;
        }

        public Criteria andShareIdIn(List<Long> values) {
            addCriterion("share_id in", values, "shareId");
            return (Criteria) this;
        }

        public Criteria andShareIdNotIn(List<Long> values) {
            addCriterion("share_id not in", values, "shareId");
            return (Criteria) this;
        }

        public Criteria andShareIdBetween(Long value1, Long value2) {
            addCriterion("share_id between", value1, value2, "shareId");
            return (Criteria) this;
        }

        public Criteria andShareIdNotBetween(Long value1, Long value2) {
            addCriterion("share_id not between", value1, value2, "shareId");
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