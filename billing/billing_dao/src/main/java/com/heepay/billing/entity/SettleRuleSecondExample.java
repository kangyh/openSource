package com.heepay.billing.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SettleRuleSecondExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SettleRuleSecondExample() {
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

        public Criteria andRuleIdIsNull() {
            addCriterion("rule_id is null");
            return (Criteria) this;
        }

        public Criteria andRuleIdIsNotNull() {
            addCriterion("rule_id is not null");
            return (Criteria) this;
        }

        public Criteria andRuleIdEqualTo(Long value) {
            addCriterion("rule_id =", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotEqualTo(Long value) {
            addCriterion("rule_id <>", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdGreaterThan(Long value) {
            addCriterion("rule_id >", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdGreaterThanOrEqualTo(Long value) {
            addCriterion("rule_id >=", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdLessThan(Long value) {
            addCriterion("rule_id <", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdLessThanOrEqualTo(Long value) {
            addCriterion("rule_id <=", value, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdIn(List<Long> values) {
            addCriterion("rule_id in", values, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotIn(List<Long> values) {
            addCriterion("rule_id not in", values, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdBetween(Long value1, Long value2) {
            addCriterion("rule_id between", value1, value2, "ruleId");
            return (Criteria) this;
        }

        public Criteria andRuleIdNotBetween(Long value1, Long value2) {
            addCriterion("rule_id not between", value1, value2, "ruleId");
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

        public Criteria andSplitFlgIsNull() {
            addCriterion("split_flg is null");
            return (Criteria) this;
        }

        public Criteria andSplitFlgIsNotNull() {
            addCriterion("split_flg is not null");
            return (Criteria) this;
        }

        public Criteria andSplitFlgEqualTo(String value) {
            addCriterion("split_flg =", value, "splitFlg");
            return (Criteria) this;
        }

        public Criteria andSplitFlgNotEqualTo(String value) {
            addCriterion("split_flg <>", value, "splitFlg");
            return (Criteria) this;
        }

        public Criteria andSplitFlgGreaterThan(String value) {
            addCriterion("split_flg >", value, "splitFlg");
            return (Criteria) this;
        }

        public Criteria andSplitFlgGreaterThanOrEqualTo(String value) {
            addCriterion("split_flg >=", value, "splitFlg");
            return (Criteria) this;
        }

        public Criteria andSplitFlgLessThan(String value) {
            addCriterion("split_flg <", value, "splitFlg");
            return (Criteria) this;
        }

        public Criteria andSplitFlgLessThanOrEqualTo(String value) {
            addCriterion("split_flg <=", value, "splitFlg");
            return (Criteria) this;
        }

        public Criteria andSplitFlgLike(String value) {
            addCriterion("split_flg like", value, "splitFlg");
            return (Criteria) this;
        }

        public Criteria andSplitFlgNotLike(String value) {
            addCriterion("split_flg not like", value, "splitFlg");
            return (Criteria) this;
        }

        public Criteria andSplitFlgIn(List<String> values) {
            addCriterion("split_flg in", values, "splitFlg");
            return (Criteria) this;
        }

        public Criteria andSplitFlgNotIn(List<String> values) {
            addCriterion("split_flg not in", values, "splitFlg");
            return (Criteria) this;
        }

        public Criteria andSplitFlgBetween(String value1, String value2) {
            addCriterion("split_flg between", value1, value2, "splitFlg");
            return (Criteria) this;
        }

        public Criteria andSplitFlgNotBetween(String value1, String value2) {
            addCriterion("split_flg not between", value1, value2, "splitFlg");
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

        public Criteria andSuccessAmountIsNull() {
            addCriterion("success_amount is null");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountIsNotNull() {
            addCriterion("success_amount is not null");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountEqualTo(String value) {
            addCriterion("success_amount =", value, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountNotEqualTo(String value) {
            addCriterion("success_amount <>", value, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountGreaterThan(String value) {
            addCriterion("success_amount >", value, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountGreaterThanOrEqualTo(String value) {
            addCriterion("success_amount >=", value, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountLessThan(String value) {
            addCriterion("success_amount <", value, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountLessThanOrEqualTo(String value) {
            addCriterion("success_amount <=", value, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountLike(String value) {
            addCriterion("success_amount like", value, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountNotLike(String value) {
            addCriterion("success_amount not like", value, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountIn(List<String> values) {
            addCriterion("success_amount in", values, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountNotIn(List<String> values) {
            addCriterion("success_amount not in", values, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountBetween(String value1, String value2) {
            addCriterion("success_amount between", value1, value2, "successAmount");
            return (Criteria) this;
        }

        public Criteria andSuccessAmountNotBetween(String value1, String value2) {
            addCriterion("success_amount not between", value1, value2, "successAmount");
            return (Criteria) this;
        }

        public Criteria andFeeFreeIsNull() {
            addCriterion("fee_free is null");
            return (Criteria) this;
        }

        public Criteria andFeeFreeIsNotNull() {
            addCriterion("fee_free is not null");
            return (Criteria) this;
        }

        public Criteria andFeeFreeEqualTo(String value) {
            addCriterion("fee_free =", value, "feeFree");
            return (Criteria) this;
        }

        public Criteria andFeeFreeNotEqualTo(String value) {
            addCriterion("fee_free <>", value, "feeFree");
            return (Criteria) this;
        }

        public Criteria andFeeFreeGreaterThan(String value) {
            addCriterion("fee_free >", value, "feeFree");
            return (Criteria) this;
        }

        public Criteria andFeeFreeGreaterThanOrEqualTo(String value) {
            addCriterion("fee_free >=", value, "feeFree");
            return (Criteria) this;
        }

        public Criteria andFeeFreeLessThan(String value) {
            addCriterion("fee_free <", value, "feeFree");
            return (Criteria) this;
        }

        public Criteria andFeeFreeLessThanOrEqualTo(String value) {
            addCriterion("fee_free <=", value, "feeFree");
            return (Criteria) this;
        }

        public Criteria andFeeFreeLike(String value) {
            addCriterion("fee_free like", value, "feeFree");
            return (Criteria) this;
        }

        public Criteria andFeeFreeNotLike(String value) {
            addCriterion("fee_free not like", value, "feeFree");
            return (Criteria) this;
        }

        public Criteria andFeeFreeIn(List<String> values) {
            addCriterion("fee_free in", values, "feeFree");
            return (Criteria) this;
        }

        public Criteria andFeeFreeNotIn(List<String> values) {
            addCriterion("fee_free not in", values, "feeFree");
            return (Criteria) this;
        }

        public Criteria andFeeFreeBetween(String value1, String value2) {
            addCriterion("fee_free between", value1, value2, "feeFree");
            return (Criteria) this;
        }

        public Criteria andFeeFreeNotBetween(String value1, String value2) {
            addCriterion("fee_free not between", value1, value2, "feeFree");
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

        public Criteria andFeeServiceEqualTo(String value) {
            addCriterion("fee_service =", value, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceNotEqualTo(String value) {
            addCriterion("fee_service <>", value, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceGreaterThan(String value) {
            addCriterion("fee_service >", value, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceGreaterThanOrEqualTo(String value) {
            addCriterion("fee_service >=", value, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceLessThan(String value) {
            addCriterion("fee_service <", value, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceLessThanOrEqualTo(String value) {
            addCriterion("fee_service <=", value, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceLike(String value) {
            addCriterion("fee_service like", value, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceNotLike(String value) {
            addCriterion("fee_service not like", value, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceIn(List<String> values) {
            addCriterion("fee_service in", values, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceNotIn(List<String> values) {
            addCriterion("fee_service not in", values, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceBetween(String value1, String value2) {
            addCriterion("fee_service between", value1, value2, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeServiceNotBetween(String value1, String value2) {
            addCriterion("fee_service not between", value1, value2, "feeService");
            return (Criteria) this;
        }

        public Criteria andFeeBankIsNull() {
            addCriterion("fee_bank is null");
            return (Criteria) this;
        }

        public Criteria andFeeBankIsNotNull() {
            addCriterion("fee_bank is not null");
            return (Criteria) this;
        }

        public Criteria andFeeBankEqualTo(String value) {
            addCriterion("fee_bank =", value, "feeBank");
            return (Criteria) this;
        }

        public Criteria andFeeBankNotEqualTo(String value) {
            addCriterion("fee_bank <>", value, "feeBank");
            return (Criteria) this;
        }

        public Criteria andFeeBankGreaterThan(String value) {
            addCriterion("fee_bank >", value, "feeBank");
            return (Criteria) this;
        }

        public Criteria andFeeBankGreaterThanOrEqualTo(String value) {
            addCriterion("fee_bank >=", value, "feeBank");
            return (Criteria) this;
        }

        public Criteria andFeeBankLessThan(String value) {
            addCriterion("fee_bank <", value, "feeBank");
            return (Criteria) this;
        }

        public Criteria andFeeBankLessThanOrEqualTo(String value) {
            addCriterion("fee_bank <=", value, "feeBank");
            return (Criteria) this;
        }

        public Criteria andFeeBankLike(String value) {
            addCriterion("fee_bank like", value, "feeBank");
            return (Criteria) this;
        }

        public Criteria andFeeBankNotLike(String value) {
            addCriterion("fee_bank not like", value, "feeBank");
            return (Criteria) this;
        }

        public Criteria andFeeBankIn(List<String> values) {
            addCriterion("fee_bank in", values, "feeBank");
            return (Criteria) this;
        }

        public Criteria andFeeBankNotIn(List<String> values) {
            addCriterion("fee_bank not in", values, "feeBank");
            return (Criteria) this;
        }

        public Criteria andFeeBankBetween(String value1, String value2) {
            addCriterion("fee_bank between", value1, value2, "feeBank");
            return (Criteria) this;
        }

        public Criteria andFeeBankNotBetween(String value1, String value2) {
            addCriterion("fee_bank not between", value1, value2, "feeBank");
            return (Criteria) this;
        }

        public Criteria andTransStatusIsNull() {
            addCriterion("trans_status is null");
            return (Criteria) this;
        }

        public Criteria andTransStatusIsNotNull() {
            addCriterion("trans_status is not null");
            return (Criteria) this;
        }

        public Criteria andTransStatusEqualTo(String value) {
            addCriterion("trans_status =", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusNotEqualTo(String value) {
            addCriterion("trans_status <>", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusGreaterThan(String value) {
            addCriterion("trans_status >", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusGreaterThanOrEqualTo(String value) {
            addCriterion("trans_status >=", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusLessThan(String value) {
            addCriterion("trans_status <", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusLessThanOrEqualTo(String value) {
            addCriterion("trans_status <=", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusLike(String value) {
            addCriterion("trans_status like", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusNotLike(String value) {
            addCriterion("trans_status not like", value, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusIn(List<String> values) {
            addCriterion("trans_status in", values, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusNotIn(List<String> values) {
            addCriterion("trans_status not in", values, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusBetween(String value1, String value2) {
            addCriterion("trans_status between", value1, value2, "transStatus");
            return (Criteria) this;
        }

        public Criteria andTransStatusNotBetween(String value1, String value2) {
            addCriterion("trans_status not between", value1, value2, "transStatus");
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

        public Criteria andStartRowIsNull() {
            addCriterion("start_row is null");
            return (Criteria) this;
        }

        public Criteria andStartRowIsNotNull() {
            addCriterion("start_row is not null");
            return (Criteria) this;
        }

        public Criteria andStartRowEqualTo(Byte value) {
            addCriterion("start_row =", value, "startRow");
            return (Criteria) this;
        }

        public Criteria andStartRowNotEqualTo(Byte value) {
            addCriterion("start_row <>", value, "startRow");
            return (Criteria) this;
        }

        public Criteria andStartRowGreaterThan(Byte value) {
            addCriterion("start_row >", value, "startRow");
            return (Criteria) this;
        }

        public Criteria andStartRowGreaterThanOrEqualTo(Byte value) {
            addCriterion("start_row >=", value, "startRow");
            return (Criteria) this;
        }

        public Criteria andStartRowLessThan(Byte value) {
            addCriterion("start_row <", value, "startRow");
            return (Criteria) this;
        }

        public Criteria andStartRowLessThanOrEqualTo(Byte value) {
            addCriterion("start_row <=", value, "startRow");
            return (Criteria) this;
        }

        public Criteria andStartRowIn(List<Byte> values) {
            addCriterion("start_row in", values, "startRow");
            return (Criteria) this;
        }

        public Criteria andStartRowNotIn(List<Byte> values) {
            addCriterion("start_row not in", values, "startRow");
            return (Criteria) this;
        }

        public Criteria andStartRowBetween(Byte value1, Byte value2) {
            addCriterion("start_row between", value1, value2, "startRow");
            return (Criteria) this;
        }

        public Criteria andStartRowNotBetween(Byte value1, Byte value2) {
            addCriterion("start_row not between", value1, value2, "startRow");
            return (Criteria) this;
        }

        public Criteria andEndFlgIsNull() {
            addCriterion("end_flg is null");
            return (Criteria) this;
        }

        public Criteria andEndFlgIsNotNull() {
            addCriterion("end_flg is not null");
            return (Criteria) this;
        }

        public Criteria andEndFlgEqualTo(String value) {
            addCriterion("end_flg =", value, "endFlg");
            return (Criteria) this;
        }

        public Criteria andEndFlgNotEqualTo(String value) {
            addCriterion("end_flg <>", value, "endFlg");
            return (Criteria) this;
        }

        public Criteria andEndFlgGreaterThan(String value) {
            addCriterion("end_flg >", value, "endFlg");
            return (Criteria) this;
        }

        public Criteria andEndFlgGreaterThanOrEqualTo(String value) {
            addCriterion("end_flg >=", value, "endFlg");
            return (Criteria) this;
        }

        public Criteria andEndFlgLessThan(String value) {
            addCriterion("end_flg <", value, "endFlg");
            return (Criteria) this;
        }

        public Criteria andEndFlgLessThanOrEqualTo(String value) {
            addCriterion("end_flg <=", value, "endFlg");
            return (Criteria) this;
        }

        public Criteria andEndFlgLike(String value) {
            addCriterion("end_flg like", value, "endFlg");
            return (Criteria) this;
        }

        public Criteria andEndFlgNotLike(String value) {
            addCriterion("end_flg not like", value, "endFlg");
            return (Criteria) this;
        }

        public Criteria andEndFlgIn(List<String> values) {
            addCriterion("end_flg in", values, "endFlg");
            return (Criteria) this;
        }

        public Criteria andEndFlgNotIn(List<String> values) {
            addCriterion("end_flg not in", values, "endFlg");
            return (Criteria) this;
        }

        public Criteria andEndFlgBetween(String value1, String value2) {
            addCriterion("end_flg between", value1, value2, "endFlg");
            return (Criteria) this;
        }

        public Criteria andEndFlgNotBetween(String value1, String value2) {
            addCriterion("end_flg not between", value1, value2, "endFlg");
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

        public Criteria andCreateAuthorIsNull() {
            addCriterion("create_author is null");
            return (Criteria) this;
        }

        public Criteria andCreateAuthorIsNotNull() {
            addCriterion("create_author is not null");
            return (Criteria) this;
        }

        public Criteria andCreateAuthorEqualTo(String value) {
            addCriterion("create_author =", value, "createAuthor");
            return (Criteria) this;
        }

        public Criteria andCreateAuthorNotEqualTo(String value) {
            addCriterion("create_author <>", value, "createAuthor");
            return (Criteria) this;
        }

        public Criteria andCreateAuthorGreaterThan(String value) {
            addCriterion("create_author >", value, "createAuthor");
            return (Criteria) this;
        }

        public Criteria andCreateAuthorGreaterThanOrEqualTo(String value) {
            addCriterion("create_author >=", value, "createAuthor");
            return (Criteria) this;
        }

        public Criteria andCreateAuthorLessThan(String value) {
            addCriterion("create_author <", value, "createAuthor");
            return (Criteria) this;
        }

        public Criteria andCreateAuthorLessThanOrEqualTo(String value) {
            addCriterion("create_author <=", value, "createAuthor");
            return (Criteria) this;
        }

        public Criteria andCreateAuthorLike(String value) {
            addCriterion("create_author like", value, "createAuthor");
            return (Criteria) this;
        }

        public Criteria andCreateAuthorNotLike(String value) {
            addCriterion("create_author not like", value, "createAuthor");
            return (Criteria) this;
        }

        public Criteria andCreateAuthorIn(List<String> values) {
            addCriterion("create_author in", values, "createAuthor");
            return (Criteria) this;
        }

        public Criteria andCreateAuthorNotIn(List<String> values) {
            addCriterion("create_author not in", values, "createAuthor");
            return (Criteria) this;
        }

        public Criteria andCreateAuthorBetween(String value1, String value2) {
            addCriterion("create_author between", value1, value2, "createAuthor");
            return (Criteria) this;
        }

        public Criteria andCreateAuthorNotBetween(String value1, String value2) {
            addCriterion("create_author not between", value1, value2, "createAuthor");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
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

        public Criteria andSettleWayIsNull() {
            addCriterion("settle_way is null");
            return (Criteria) this;
        }

        public Criteria andSettleWayIsNotNull() {
            addCriterion("settle_way is not null");
            return (Criteria) this;
        }

        public Criteria andSettleWayEqualTo(String value) {
            addCriterion("settle_way =", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayNotEqualTo(String value) {
            addCriterion("settle_way <>", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayGreaterThan(String value) {
            addCriterion("settle_way >", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayGreaterThanOrEqualTo(String value) {
            addCriterion("settle_way >=", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayLessThan(String value) {
            addCriterion("settle_way <", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayLessThanOrEqualTo(String value) {
            addCriterion("settle_way <=", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayLike(String value) {
            addCriterion("settle_way like", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayNotLike(String value) {
            addCriterion("settle_way not like", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayIn(List<String> values) {
            addCriterion("settle_way in", values, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayNotIn(List<String> values) {
            addCriterion("settle_way not in", values, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayBetween(String value1, String value2) {
            addCriterion("settle_way between", value1, value2, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayNotBetween(String value1, String value2) {
            addCriterion("settle_way not between", value1, value2, "settleWay");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
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