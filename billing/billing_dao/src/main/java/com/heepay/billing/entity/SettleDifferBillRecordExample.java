package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SettleDifferBillRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SettleDifferBillRecordExample() {
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

        public Criteria andBillDifferIdIsNull() {
            addCriterion("bill_differ_id is null");
            return (Criteria) this;
        }

        public Criteria andBillDifferIdIsNotNull() {
            addCriterion("bill_differ_id is not null");
            return (Criteria) this;
        }

        public Criteria andBillDifferIdEqualTo(Long value) {
            addCriterion("bill_differ_id =", value, "billDifferId");
            return (Criteria) this;
        }

        public Criteria andBillDifferIdNotEqualTo(Long value) {
            addCriterion("bill_differ_id <>", value, "billDifferId");
            return (Criteria) this;
        }

        public Criteria andBillDifferIdGreaterThan(Long value) {
            addCriterion("bill_differ_id >", value, "billDifferId");
            return (Criteria) this;
        }

        public Criteria andBillDifferIdGreaterThanOrEqualTo(Long value) {
            addCriterion("bill_differ_id >=", value, "billDifferId");
            return (Criteria) this;
        }

        public Criteria andBillDifferIdLessThan(Long value) {
            addCriterion("bill_differ_id <", value, "billDifferId");
            return (Criteria) this;
        }

        public Criteria andBillDifferIdLessThanOrEqualTo(Long value) {
            addCriterion("bill_differ_id <=", value, "billDifferId");
            return (Criteria) this;
        }

        public Criteria andBillDifferIdIn(List<Long> values) {
            addCriterion("bill_differ_id in", values, "billDifferId");
            return (Criteria) this;
        }

        public Criteria andBillDifferIdNotIn(List<Long> values) {
            addCriterion("bill_differ_id not in", values, "billDifferId");
            return (Criteria) this;
        }

        public Criteria andBillDifferIdBetween(Long value1, Long value2) {
            addCriterion("bill_differ_id between", value1, value2, "billDifferId");
            return (Criteria) this;
        }

        public Criteria andBillDifferIdNotBetween(Long value1, Long value2) {
            addCriterion("bill_differ_id not between", value1, value2, "billDifferId");
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

        public Criteria andSaveTimeIsNull() {
            addCriterion("save_time is null");
            return (Criteria) this;
        }

        public Criteria andSaveTimeIsNotNull() {
            addCriterion("save_time is not null");
            return (Criteria) this;
        }

        public Criteria andSaveTimeEqualTo(Date value) {
            addCriterion("save_time =", value, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeNotEqualTo(Date value) {
            addCriterion("save_time <>", value, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeGreaterThan(Date value) {
            addCriterion("save_time >", value, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("save_time >=", value, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeLessThan(Date value) {
            addCriterion("save_time <", value, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeLessThanOrEqualTo(Date value) {
            addCriterion("save_time <=", value, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeIn(List<Date> values) {
            addCriterion("save_time in", values, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeNotIn(List<Date> values) {
            addCriterion("save_time not in", values, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeBetween(Date value1, Date value2) {
            addCriterion("save_time between", value1, value2, "saveTime");
            return (Criteria) this;
        }

        public Criteria andSaveTimeNotBetween(Date value1, Date value2) {
            addCriterion("save_time not between", value1, value2, "saveTime");
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

        public Criteria andChannleNoIsNull() {
            addCriterion("channle_no is null");
            return (Criteria) this;
        }

        public Criteria andChannleNoIsNotNull() {
            addCriterion("channle_no is not null");
            return (Criteria) this;
        }

        public Criteria andChannleNoEqualTo(String value) {
            addCriterion("channle_no =", value, "channleNo");
            return (Criteria) this;
        }

        public Criteria andChannleNoNotEqualTo(String value) {
            addCriterion("channle_no <>", value, "channleNo");
            return (Criteria) this;
        }

        public Criteria andChannleNoGreaterThan(String value) {
            addCriterion("channle_no >", value, "channleNo");
            return (Criteria) this;
        }

        public Criteria andChannleNoGreaterThanOrEqualTo(String value) {
            addCriterion("channle_no >=", value, "channleNo");
            return (Criteria) this;
        }

        public Criteria andChannleNoLessThan(String value) {
            addCriterion("channle_no <", value, "channleNo");
            return (Criteria) this;
        }

        public Criteria andChannleNoLessThanOrEqualTo(String value) {
            addCriterion("channle_no <=", value, "channleNo");
            return (Criteria) this;
        }

        public Criteria andChannleNoLike(String value) {
            addCriterion("channle_no like", value, "channleNo");
            return (Criteria) this;
        }

        public Criteria andChannleNoNotLike(String value) {
            addCriterion("channle_no not like", value, "channleNo");
            return (Criteria) this;
        }

        public Criteria andChannleNoIn(List<String> values) {
            addCriterion("channle_no in", values, "channleNo");
            return (Criteria) this;
        }

        public Criteria andChannleNoNotIn(List<String> values) {
            addCriterion("channle_no not in", values, "channleNo");
            return (Criteria) this;
        }

        public Criteria andChannleNoBetween(String value1, String value2) {
            addCriterion("channle_no between", value1, value2, "channleNo");
            return (Criteria) this;
        }

        public Criteria andChannleNoNotBetween(String value1, String value2) {
            addCriterion("channle_no not between", value1, value2, "channleNo");
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

        public Criteria andCheckBathNoIsNull() {
            addCriterion("check_bath_no is null");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoIsNotNull() {
            addCriterion("check_bath_no is not null");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoEqualTo(String value) {
            addCriterion("check_bath_no =", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoNotEqualTo(String value) {
            addCriterion("check_bath_no <>", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoGreaterThan(String value) {
            addCriterion("check_bath_no >", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoGreaterThanOrEqualTo(String value) {
            addCriterion("check_bath_no >=", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoLessThan(String value) {
            addCriterion("check_bath_no <", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoLessThanOrEqualTo(String value) {
            addCriterion("check_bath_no <=", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoLike(String value) {
            addCriterion("check_bath_no like", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoNotLike(String value) {
            addCriterion("check_bath_no not like", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoIn(List<String> values) {
            addCriterion("check_bath_no in", values, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoNotIn(List<String> values) {
            addCriterion("check_bath_no not in", values, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoBetween(String value1, String value2) {
            addCriterion("check_bath_no between", value1, value2, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoNotBetween(String value1, String value2) {
            addCriterion("check_bath_no not between", value1, value2, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andPromotionAmountIsNull() {
            addCriterion("promotion_amount is null");
            return (Criteria) this;
        }

        public Criteria andPromotionAmountIsNotNull() {
            addCriterion("promotion_amount is not null");
            return (Criteria) this;
        }

        public Criteria andPromotionAmountEqualTo(BigDecimal value) {
            addCriterion("promotion_amount =", value, "promotionAmount");
            return (Criteria) this;
        }

        public Criteria andPromotionAmountNotEqualTo(BigDecimal value) {
            addCriterion("promotion_amount <>", value, "promotionAmount");
            return (Criteria) this;
        }

        public Criteria andPromotionAmountGreaterThan(BigDecimal value) {
            addCriterion("promotion_amount >", value, "promotionAmount");
            return (Criteria) this;
        }

        public Criteria andPromotionAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("promotion_amount >=", value, "promotionAmount");
            return (Criteria) this;
        }

        public Criteria andPromotionAmountLessThan(BigDecimal value) {
            addCriterion("promotion_amount <", value, "promotionAmount");
            return (Criteria) this;
        }

        public Criteria andPromotionAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("promotion_amount <=", value, "promotionAmount");
            return (Criteria) this;
        }

        public Criteria andPromotionAmountIn(List<BigDecimal> values) {
            addCriterion("promotion_amount in", values, "promotionAmount");
            return (Criteria) this;
        }

        public Criteria andPromotionAmountNotIn(List<BigDecimal> values) {
            addCriterion("promotion_amount not in", values, "promotionAmount");
            return (Criteria) this;
        }

        public Criteria andPromotionAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("promotion_amount between", value1, value2, "promotionAmount");
            return (Criteria) this;
        }

        public Criteria andPromotionAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("promotion_amount not between", value1, value2, "promotionAmount");
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

        public Criteria andFeeServiceIsNull() {
            addCriterion("fee_service is null");
            return (Criteria) this;
        }

        public Criteria andFeeServiceIsNotNull() {
            addCriterion("fee_service is not null");
            return (Criteria) this;
        }

        public Criteria andFeeServiceEqualTo(BigDecimal value) {
            addCriterion("fee_service =", value, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceNotEqualTo(BigDecimal value) {
            addCriterion("fee_service <>", value, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceGreaterThan(BigDecimal value) {
            addCriterion("fee_service >", value, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("fee_service >=", value, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceLessThan(BigDecimal value) {
            addCriterion("fee_service <", value, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("fee_service <=", value, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceIn(List<BigDecimal> values) {
            addCriterion("fee_service in", values, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceNotIn(List<BigDecimal> values) {
            addCriterion("fee_service not in", values, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee_service between", value1, value2, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("fee_service not between", value1, value2, "feeService");
            return (Criteria) this;
        }

        public Criteria andDifferTypeIsNull() {
            addCriterion("differ_type is null");
            return (Criteria) this;
        }

        public Criteria andDifferTypeIsNotNull() {
            addCriterion("differ_type is not null");
            return (Criteria) this;
        }

        public Criteria andDifferTypeEqualTo(String value) {
            addCriterion("differ_type =", value, "differType");
            return (Criteria) this;
        }

        public Criteria andDifferTypeNotEqualTo(String value) {
            addCriterion("differ_type <>", value, "differType");
            return (Criteria) this;
        }

        public Criteria andDifferTypeGreaterThan(String value) {
            addCriterion("differ_type >", value, "differType");
            return (Criteria) this;
        }

        public Criteria andDifferTypeGreaterThanOrEqualTo(String value) {
            addCriterion("differ_type >=", value, "differType");
            return (Criteria) this;
        }

        public Criteria andDifferTypeLessThan(String value) {
            addCriterion("differ_type <", value, "differType");
            return (Criteria) this;
        }

        public Criteria andDifferTypeLessThanOrEqualTo(String value) {
            addCriterion("differ_type <=", value, "differType");
            return (Criteria) this;
        }

        public Criteria andDifferTypeLike(String value) {
            addCriterion("differ_type like", value, "differType");
            return (Criteria) this;
        }

        public Criteria andDifferTypeNotLike(String value) {
            addCriterion("differ_type not like", value, "differType");
            return (Criteria) this;
        }

        public Criteria andDifferTypeIn(List<String> values) {
            addCriterion("differ_type in", values, "differType");
            return (Criteria) this;
        }

        public Criteria andDifferTypeNotIn(List<String> values) {
            addCriterion("differ_type not in", values, "differType");
            return (Criteria) this;
        }

        public Criteria andDifferTypeBetween(String value1, String value2) {
            addCriterion("differ_type between", value1, value2, "differType");
            return (Criteria) this;
        }

        public Criteria andDifferTypeNotBetween(String value1, String value2) {
            addCriterion("differ_type not between", value1, value2, "differType");
            return (Criteria) this;
        }

        public Criteria andField1IsNull() {
            addCriterion("field1 is null");
            return (Criteria) this;
        }

        public Criteria andField1IsNotNull() {
            addCriterion("field1 is not null");
            return (Criteria) this;
        }

        public Criteria andField1EqualTo(String value) {
            addCriterion("field1 =", value, "field1");
            return (Criteria) this;
        }

        public Criteria andField1NotEqualTo(String value) {
            addCriterion("field1 <>", value, "field1");
            return (Criteria) this;
        }

        public Criteria andField1GreaterThan(String value) {
            addCriterion("field1 >", value, "field1");
            return (Criteria) this;
        }

        public Criteria andField1GreaterThanOrEqualTo(String value) {
            addCriterion("field1 >=", value, "field1");
            return (Criteria) this;
        }

        public Criteria andField1LessThan(String value) {
            addCriterion("field1 <", value, "field1");
            return (Criteria) this;
        }

        public Criteria andField1LessThanOrEqualTo(String value) {
            addCriterion("field1 <=", value, "field1");
            return (Criteria) this;
        }

        public Criteria andField1Like(String value) {
            addCriterion("field1 like", value, "field1");
            return (Criteria) this;
        }

        public Criteria andField1NotLike(String value) {
            addCriterion("field1 not like", value, "field1");
            return (Criteria) this;
        }

        public Criteria andField1In(List<String> values) {
            addCriterion("field1 in", values, "field1");
            return (Criteria) this;
        }

        public Criteria andField1NotIn(List<String> values) {
            addCriterion("field1 not in", values, "field1");
            return (Criteria) this;
        }

        public Criteria andField1Between(String value1, String value2) {
            addCriterion("field1 between", value1, value2, "field1");
            return (Criteria) this;
        }

        public Criteria andField1NotBetween(String value1, String value2) {
            addCriterion("field1 not between", value1, value2, "field1");
            return (Criteria) this;
        }

        public Criteria andField2IsNull() {
            addCriterion("field2 is null");
            return (Criteria) this;
        }

        public Criteria andField2IsNotNull() {
            addCriterion("field2 is not null");
            return (Criteria) this;
        }

        public Criteria andField2EqualTo(String value) {
            addCriterion("field2 =", value, "field2");
            return (Criteria) this;
        }

        public Criteria andField2NotEqualTo(String value) {
            addCriterion("field2 <>", value, "field2");
            return (Criteria) this;
        }

        public Criteria andField2GreaterThan(String value) {
            addCriterion("field2 >", value, "field2");
            return (Criteria) this;
        }

        public Criteria andField2GreaterThanOrEqualTo(String value) {
            addCriterion("field2 >=", value, "field2");
            return (Criteria) this;
        }

        public Criteria andField2LessThan(String value) {
            addCriterion("field2 <", value, "field2");
            return (Criteria) this;
        }

        public Criteria andField2LessThanOrEqualTo(String value) {
            addCriterion("field2 <=", value, "field2");
            return (Criteria) this;
        }

        public Criteria andField2Like(String value) {
            addCriterion("field2 like", value, "field2");
            return (Criteria) this;
        }

        public Criteria andField2NotLike(String value) {
            addCriterion("field2 not like", value, "field2");
            return (Criteria) this;
        }

        public Criteria andField2In(List<String> values) {
            addCriterion("field2 in", values, "field2");
            return (Criteria) this;
        }

        public Criteria andField2NotIn(List<String> values) {
            addCriterion("field2 not in", values, "field2");
            return (Criteria) this;
        }

        public Criteria andField2Between(String value1, String value2) {
            addCriterion("field2 between", value1, value2, "field2");
            return (Criteria) this;
        }

        public Criteria andField2NotBetween(String value1, String value2) {
            addCriterion("field2 not between", value1, value2, "field2");
            return (Criteria) this;
        }

        public Criteria andField3IsNull() {
            addCriterion("field3 is null");
            return (Criteria) this;
        }

        public Criteria andField3IsNotNull() {
            addCriterion("field3 is not null");
            return (Criteria) this;
        }

        public Criteria andField3EqualTo(String value) {
            addCriterion("field3 =", value, "field3");
            return (Criteria) this;
        }

        public Criteria andField3NotEqualTo(String value) {
            addCriterion("field3 <>", value, "field3");
            return (Criteria) this;
        }

        public Criteria andField3GreaterThan(String value) {
            addCriterion("field3 >", value, "field3");
            return (Criteria) this;
        }

        public Criteria andField3GreaterThanOrEqualTo(String value) {
            addCriterion("field3 >=", value, "field3");
            return (Criteria) this;
        }

        public Criteria andField3LessThan(String value) {
            addCriterion("field3 <", value, "field3");
            return (Criteria) this;
        }

        public Criteria andField3LessThanOrEqualTo(String value) {
            addCriterion("field3 <=", value, "field3");
            return (Criteria) this;
        }

        public Criteria andField3Like(String value) {
            addCriterion("field3 like", value, "field3");
            return (Criteria) this;
        }

        public Criteria andField3NotLike(String value) {
            addCriterion("field3 not like", value, "field3");
            return (Criteria) this;
        }

        public Criteria andField3In(List<String> values) {
            addCriterion("field3 in", values, "field3");
            return (Criteria) this;
        }

        public Criteria andField3NotIn(List<String> values) {
            addCriterion("field3 not in", values, "field3");
            return (Criteria) this;
        }

        public Criteria andField3Between(String value1, String value2) {
            addCriterion("field3 between", value1, value2, "field3");
            return (Criteria) this;
        }

        public Criteria andField3NotBetween(String value1, String value2) {
            addCriterion("field3 not between", value1, value2, "field3");
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