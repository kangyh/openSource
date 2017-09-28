package com.heepay.tpds.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TpdsCustomChargeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TpdsCustomChargeExample() {
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

        public Criteria andCusIdIsNull() {
            addCriterion("cus_id is null");
            return (Criteria) this;
        }

        public Criteria andCusIdIsNotNull() {
            addCriterion("cus_id is not null");
            return (Criteria) this;
        }

        public Criteria andCusIdEqualTo(Long value) {
            addCriterion("cus_id =", value, "cusId");
            return (Criteria) this;
        }

        public Criteria andCusIdNotEqualTo(Long value) {
            addCriterion("cus_id <>", value, "cusId");
            return (Criteria) this;
        }

        public Criteria andCusIdGreaterThan(Long value) {
            addCriterion("cus_id >", value, "cusId");
            return (Criteria) this;
        }

        public Criteria andCusIdGreaterThanOrEqualTo(Long value) {
            addCriterion("cus_id >=", value, "cusId");
            return (Criteria) this;
        }

        public Criteria andCusIdLessThan(Long value) {
            addCriterion("cus_id <", value, "cusId");
            return (Criteria) this;
        }

        public Criteria andCusIdLessThanOrEqualTo(Long value) {
            addCriterion("cus_id <=", value, "cusId");
            return (Criteria) this;
        }

        public Criteria andCusIdIn(List<Long> values) {
            addCriterion("cus_id in", values, "cusId");
            return (Criteria) this;
        }

        public Criteria andCusIdNotIn(List<Long> values) {
            addCriterion("cus_id not in", values, "cusId");
            return (Criteria) this;
        }

        public Criteria andCusIdBetween(Long value1, Long value2) {
            addCriterion("cus_id between", value1, value2, "cusId");
            return (Criteria) this;
        }

        public Criteria andCusIdNotBetween(Long value1, Long value2) {
            addCriterion("cus_id not between", value1, value2, "cusId");
            return (Criteria) this;
        }

        public Criteria andBusinessSeqNoIsNull() {
            addCriterion("business_seq_no is null");
            return (Criteria) this;
        }

        public Criteria andBusinessSeqNoIsNotNull() {
            addCriterion("business_seq_no is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessSeqNoEqualTo(String value) {
            addCriterion("business_seq_no =", value, "businessSeqNo");
            return (Criteria) this;
        }

        public Criteria andBusinessSeqNoNotEqualTo(String value) {
            addCriterion("business_seq_no <>", value, "businessSeqNo");
            return (Criteria) this;
        }

        public Criteria andBusinessSeqNoGreaterThan(String value) {
            addCriterion("business_seq_no >", value, "businessSeqNo");
            return (Criteria) this;
        }

        public Criteria andBusinessSeqNoGreaterThanOrEqualTo(String value) {
            addCriterion("business_seq_no >=", value, "businessSeqNo");
            return (Criteria) this;
        }

        public Criteria andBusinessSeqNoLessThan(String value) {
            addCriterion("business_seq_no <", value, "businessSeqNo");
            return (Criteria) this;
        }

        public Criteria andBusinessSeqNoLessThanOrEqualTo(String value) {
            addCriterion("business_seq_no <=", value, "businessSeqNo");
            return (Criteria) this;
        }

        public Criteria andBusinessSeqNoLike(String value) {
            addCriterion("business_seq_no like", value, "businessSeqNo");
            return (Criteria) this;
        }

        public Criteria andBusinessSeqNoNotLike(String value) {
            addCriterion("business_seq_no not like", value, "businessSeqNo");
            return (Criteria) this;
        }

        public Criteria andBusinessSeqNoIn(List<String> values) {
            addCriterion("business_seq_no in", values, "businessSeqNo");
            return (Criteria) this;
        }

        public Criteria andBusinessSeqNoNotIn(List<String> values) {
            addCriterion("business_seq_no not in", values, "businessSeqNo");
            return (Criteria) this;
        }

        public Criteria andBusinessSeqNoBetween(String value1, String value2) {
            addCriterion("business_seq_no between", value1, value2, "businessSeqNo");
            return (Criteria) this;
        }

        public Criteria andBusinessSeqNoNotBetween(String value1, String value2) {
            addCriterion("business_seq_no not between", value1, value2, "businessSeqNo");
            return (Criteria) this;
        }

        public Criteria andBusinessOrderNoIsNull() {
            addCriterion("business_order_no is null");
            return (Criteria) this;
        }

        public Criteria andBusinessOrderNoIsNotNull() {
            addCriterion("business_order_no is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessOrderNoEqualTo(String value) {
            addCriterion("business_order_no =", value, "businessOrderNo");
            return (Criteria) this;
        }

        public Criteria andBusinessOrderNoNotEqualTo(String value) {
            addCriterion("business_order_no <>", value, "businessOrderNo");
            return (Criteria) this;
        }

        public Criteria andBusinessOrderNoGreaterThan(String value) {
            addCriterion("business_order_no >", value, "businessOrderNo");
            return (Criteria) this;
        }

        public Criteria andBusinessOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("business_order_no >=", value, "businessOrderNo");
            return (Criteria) this;
        }

        public Criteria andBusinessOrderNoLessThan(String value) {
            addCriterion("business_order_no <", value, "businessOrderNo");
            return (Criteria) this;
        }

        public Criteria andBusinessOrderNoLessThanOrEqualTo(String value) {
            addCriterion("business_order_no <=", value, "businessOrderNo");
            return (Criteria) this;
        }

        public Criteria andBusinessOrderNoLike(String value) {
            addCriterion("business_order_no like", value, "businessOrderNo");
            return (Criteria) this;
        }

        public Criteria andBusinessOrderNoNotLike(String value) {
            addCriterion("business_order_no not like", value, "businessOrderNo");
            return (Criteria) this;
        }

        public Criteria andBusinessOrderNoIn(List<String> values) {
            addCriterion("business_order_no in", values, "businessOrderNo");
            return (Criteria) this;
        }

        public Criteria andBusinessOrderNoNotIn(List<String> values) {
            addCriterion("business_order_no not in", values, "businessOrderNo");
            return (Criteria) this;
        }

        public Criteria andBusinessOrderNoBetween(String value1, String value2) {
            addCriterion("business_order_no between", value1, value2, "businessOrderNo");
            return (Criteria) this;
        }

        public Criteria andBusinessOrderNoNotBetween(String value1, String value2) {
            addCriterion("business_order_no not between", value1, value2, "businessOrderNo");
            return (Criteria) this;
        }

        public Criteria andRTypeIsNull() {
            addCriterion("r_type is null");
            return (Criteria) this;
        }

        public Criteria andRTypeIsNotNull() {
            addCriterion("r_type is not null");
            return (Criteria) this;
        }

        public Criteria andRTypeEqualTo(String value) {
            addCriterion("r_type =", value, "rType");
            return (Criteria) this;
        }

        public Criteria andRTypeNotEqualTo(String value) {
            addCriterion("r_type <>", value, "rType");
            return (Criteria) this;
        }

        public Criteria andRTypeGreaterThan(String value) {
            addCriterion("r_type >", value, "rType");
            return (Criteria) this;
        }

        public Criteria andRTypeGreaterThanOrEqualTo(String value) {
            addCriterion("r_type >=", value, "rType");
            return (Criteria) this;
        }

        public Criteria andRTypeLessThan(String value) {
            addCriterion("r_type <", value, "rType");
            return (Criteria) this;
        }

        public Criteria andRTypeLessThanOrEqualTo(String value) {
            addCriterion("r_type <=", value, "rType");
            return (Criteria) this;
        }

        public Criteria andRTypeLike(String value) {
            addCriterion("r_type like", value, "rType");
            return (Criteria) this;
        }

        public Criteria andRTypeNotLike(String value) {
            addCriterion("r_type not like", value, "rType");
            return (Criteria) this;
        }

        public Criteria andRTypeIn(List<String> values) {
            addCriterion("r_type in", values, "rType");
            return (Criteria) this;
        }

        public Criteria andRTypeNotIn(List<String> values) {
            addCriterion("r_type not in", values, "rType");
            return (Criteria) this;
        }

        public Criteria andRTypeBetween(String value1, String value2) {
            addCriterion("r_type between", value1, value2, "rType");
            return (Criteria) this;
        }

        public Criteria andRTypeNotBetween(String value1, String value2) {
            addCriterion("r_type not between", value1, value2, "rType");
            return (Criteria) this;
        }

        public Criteria andEntrustflagIsNull() {
            addCriterion("entrustflag is null");
            return (Criteria) this;
        }

        public Criteria andEntrustflagIsNotNull() {
            addCriterion("entrustflag is not null");
            return (Criteria) this;
        }

        public Criteria andEntrustflagEqualTo(String value) {
            addCriterion("entrustflag =", value, "entrustflag");
            return (Criteria) this;
        }

        public Criteria andEntrustflagNotEqualTo(String value) {
            addCriterion("entrustflag <>", value, "entrustflag");
            return (Criteria) this;
        }

        public Criteria andEntrustflagGreaterThan(String value) {
            addCriterion("entrustflag >", value, "entrustflag");
            return (Criteria) this;
        }

        public Criteria andEntrustflagGreaterThanOrEqualTo(String value) {
            addCriterion("entrustflag >=", value, "entrustflag");
            return (Criteria) this;
        }

        public Criteria andEntrustflagLessThan(String value) {
            addCriterion("entrustflag <", value, "entrustflag");
            return (Criteria) this;
        }

        public Criteria andEntrustflagLessThanOrEqualTo(String value) {
            addCriterion("entrustflag <=", value, "entrustflag");
            return (Criteria) this;
        }

        public Criteria andEntrustflagLike(String value) {
            addCriterion("entrustflag like", value, "entrustflag");
            return (Criteria) this;
        }

        public Criteria andEntrustflagNotLike(String value) {
            addCriterion("entrustflag not like", value, "entrustflag");
            return (Criteria) this;
        }

        public Criteria andEntrustflagIn(List<String> values) {
            addCriterion("entrustflag in", values, "entrustflag");
            return (Criteria) this;
        }

        public Criteria andEntrustflagNotIn(List<String> values) {
            addCriterion("entrustflag not in", values, "entrustflag");
            return (Criteria) this;
        }

        public Criteria andEntrustflagBetween(String value1, String value2) {
            addCriterion("entrustflag between", value1, value2, "entrustflag");
            return (Criteria) this;
        }

        public Criteria andEntrustflagNotBetween(String value1, String value2) {
            addCriterion("entrustflag not between", value1, value2, "entrustflag");
            return (Criteria) this;
        }

        public Criteria andOderNoIsNull() {
            addCriterion("oder_no is null");
            return (Criteria) this;
        }

        public Criteria andOderNoIsNotNull() {
            addCriterion("oder_no is not null");
            return (Criteria) this;
        }

        public Criteria andOderNoEqualTo(String value) {
            addCriterion("oder_no =", value, "oderNo");
            return (Criteria) this;
        }

        public Criteria andOderNoNotEqualTo(String value) {
            addCriterion("oder_no <>", value, "oderNo");
            return (Criteria) this;
        }

        public Criteria andOderNoGreaterThan(String value) {
            addCriterion("oder_no >", value, "oderNo");
            return (Criteria) this;
        }

        public Criteria andOderNoGreaterThanOrEqualTo(String value) {
            addCriterion("oder_no >=", value, "oderNo");
            return (Criteria) this;
        }

        public Criteria andOderNoLessThan(String value) {
            addCriterion("oder_no <", value, "oderNo");
            return (Criteria) this;
        }

        public Criteria andOderNoLessThanOrEqualTo(String value) {
            addCriterion("oder_no <=", value, "oderNo");
            return (Criteria) this;
        }

        public Criteria andOderNoLike(String value) {
            addCriterion("oder_no like", value, "oderNo");
            return (Criteria) this;
        }

        public Criteria andOderNoNotLike(String value) {
            addCriterion("oder_no not like", value, "oderNo");
            return (Criteria) this;
        }

        public Criteria andOderNoIn(List<String> values) {
            addCriterion("oder_no in", values, "oderNo");
            return (Criteria) this;
        }

        public Criteria andOderNoNotIn(List<String> values) {
            addCriterion("oder_no not in", values, "oderNo");
            return (Criteria) this;
        }

        public Criteria andOderNoBetween(String value1, String value2) {
            addCriterion("oder_no between", value1, value2, "oderNo");
            return (Criteria) this;
        }

        public Criteria andOderNoNotBetween(String value1, String value2) {
            addCriterion("oder_no not between", value1, value2, "oderNo");
            return (Criteria) this;
        }

        public Criteria andDebitAccountNoIsNull() {
            addCriterion("debit_account_no is null");
            return (Criteria) this;
        }

        public Criteria andDebitAccountNoIsNotNull() {
            addCriterion("debit_account_no is not null");
            return (Criteria) this;
        }

        public Criteria andDebitAccountNoEqualTo(String value) {
            addCriterion("debit_account_no =", value, "debitAccountNo");
            return (Criteria) this;
        }

        public Criteria andDebitAccountNoNotEqualTo(String value) {
            addCriterion("debit_account_no <>", value, "debitAccountNo");
            return (Criteria) this;
        }

        public Criteria andDebitAccountNoGreaterThan(String value) {
            addCriterion("debit_account_no >", value, "debitAccountNo");
            return (Criteria) this;
        }

        public Criteria andDebitAccountNoGreaterThanOrEqualTo(String value) {
            addCriterion("debit_account_no >=", value, "debitAccountNo");
            return (Criteria) this;
        }

        public Criteria andDebitAccountNoLessThan(String value) {
            addCriterion("debit_account_no <", value, "debitAccountNo");
            return (Criteria) this;
        }

        public Criteria andDebitAccountNoLessThanOrEqualTo(String value) {
            addCriterion("debit_account_no <=", value, "debitAccountNo");
            return (Criteria) this;
        }

        public Criteria andDebitAccountNoLike(String value) {
            addCriterion("debit_account_no like", value, "debitAccountNo");
            return (Criteria) this;
        }

        public Criteria andDebitAccountNoNotLike(String value) {
            addCriterion("debit_account_no not like", value, "debitAccountNo");
            return (Criteria) this;
        }

        public Criteria andDebitAccountNoIn(List<String> values) {
            addCriterion("debit_account_no in", values, "debitAccountNo");
            return (Criteria) this;
        }

        public Criteria andDebitAccountNoNotIn(List<String> values) {
            addCriterion("debit_account_no not in", values, "debitAccountNo");
            return (Criteria) this;
        }

        public Criteria andDebitAccountNoBetween(String value1, String value2) {
            addCriterion("debit_account_no between", value1, value2, "debitAccountNo");
            return (Criteria) this;
        }

        public Criteria andDebitAccountNoNotBetween(String value1, String value2) {
            addCriterion("debit_account_no not between", value1, value2, "debitAccountNo");
            return (Criteria) this;
        }

        public Criteria andCebitAccountNoIsNull() {
            addCriterion("cebit_account_no is null");
            return (Criteria) this;
        }

        public Criteria andCebitAccountNoIsNotNull() {
            addCriterion("cebit_account_no is not null");
            return (Criteria) this;
        }

        public Criteria andCebitAccountNoEqualTo(String value) {
            addCriterion("cebit_account_no =", value, "cebitAccountNo");
            return (Criteria) this;
        }

        public Criteria andCebitAccountNoNotEqualTo(String value) {
            addCriterion("cebit_account_no <>", value, "cebitAccountNo");
            return (Criteria) this;
        }

        public Criteria andCebitAccountNoGreaterThan(String value) {
            addCriterion("cebit_account_no >", value, "cebitAccountNo");
            return (Criteria) this;
        }

        public Criteria andCebitAccountNoGreaterThanOrEqualTo(String value) {
            addCriterion("cebit_account_no >=", value, "cebitAccountNo");
            return (Criteria) this;
        }

        public Criteria andCebitAccountNoLessThan(String value) {
            addCriterion("cebit_account_no <", value, "cebitAccountNo");
            return (Criteria) this;
        }

        public Criteria andCebitAccountNoLessThanOrEqualTo(String value) {
            addCriterion("cebit_account_no <=", value, "cebitAccountNo");
            return (Criteria) this;
        }

        public Criteria andCebitAccountNoLike(String value) {
            addCriterion("cebit_account_no like", value, "cebitAccountNo");
            return (Criteria) this;
        }

        public Criteria andCebitAccountNoNotLike(String value) {
            addCriterion("cebit_account_no not like", value, "cebitAccountNo");
            return (Criteria) this;
        }

        public Criteria andCebitAccountNoIn(List<String> values) {
            addCriterion("cebit_account_no in", values, "cebitAccountNo");
            return (Criteria) this;
        }

        public Criteria andCebitAccountNoNotIn(List<String> values) {
            addCriterion("cebit_account_no not in", values, "cebitAccountNo");
            return (Criteria) this;
        }

        public Criteria andCebitAccountNoBetween(String value1, String value2) {
            addCriterion("cebit_account_no between", value1, value2, "cebitAccountNo");
            return (Criteria) this;
        }

        public Criteria andCebitAccountNoNotBetween(String value1, String value2) {
            addCriterion("cebit_account_no not between", value1, value2, "cebitAccountNo");
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

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andOtherAmounttypeIsNull() {
            addCriterion("other_amounttype is null");
            return (Criteria) this;
        }

        public Criteria andOtherAmounttypeIsNotNull() {
            addCriterion("other_amounttype is not null");
            return (Criteria) this;
        }

        public Criteria andOtherAmounttypeEqualTo(String value) {
            addCriterion("other_amounttype =", value, "otherAmounttype");
            return (Criteria) this;
        }

        public Criteria andOtherAmounttypeNotEqualTo(String value) {
            addCriterion("other_amounttype <>", value, "otherAmounttype");
            return (Criteria) this;
        }

        public Criteria andOtherAmounttypeGreaterThan(String value) {
            addCriterion("other_amounttype >", value, "otherAmounttype");
            return (Criteria) this;
        }

        public Criteria andOtherAmounttypeGreaterThanOrEqualTo(String value) {
            addCriterion("other_amounttype >=", value, "otherAmounttype");
            return (Criteria) this;
        }

        public Criteria andOtherAmounttypeLessThan(String value) {
            addCriterion("other_amounttype <", value, "otherAmounttype");
            return (Criteria) this;
        }

        public Criteria andOtherAmounttypeLessThanOrEqualTo(String value) {
            addCriterion("other_amounttype <=", value, "otherAmounttype");
            return (Criteria) this;
        }

        public Criteria andOtherAmounttypeLike(String value) {
            addCriterion("other_amounttype like", value, "otherAmounttype");
            return (Criteria) this;
        }

        public Criteria andOtherAmounttypeNotLike(String value) {
            addCriterion("other_amounttype not like", value, "otherAmounttype");
            return (Criteria) this;
        }

        public Criteria andOtherAmounttypeIn(List<String> values) {
            addCriterion("other_amounttype in", values, "otherAmounttype");
            return (Criteria) this;
        }

        public Criteria andOtherAmounttypeNotIn(List<String> values) {
            addCriterion("other_amounttype not in", values, "otherAmounttype");
            return (Criteria) this;
        }

        public Criteria andOtherAmounttypeBetween(String value1, String value2) {
            addCriterion("other_amounttype between", value1, value2, "otherAmounttype");
            return (Criteria) this;
        }

        public Criteria andOtherAmounttypeNotBetween(String value1, String value2) {
            addCriterion("other_amounttype not between", value1, value2, "otherAmounttype");
            return (Criteria) this;
        }

        public Criteria andOtherAmountIsNull() {
            addCriterion("other_amount is null");
            return (Criteria) this;
        }

        public Criteria andOtherAmountIsNotNull() {
            addCriterion("other_amount is not null");
            return (Criteria) this;
        }

        public Criteria andOtherAmountEqualTo(BigDecimal value) {
            addCriterion("other_amount =", value, "otherAmount");
            return (Criteria) this;
        }

        public Criteria andOtherAmountNotEqualTo(BigDecimal value) {
            addCriterion("other_amount <>", value, "otherAmount");
            return (Criteria) this;
        }

        public Criteria andOtherAmountGreaterThan(BigDecimal value) {
            addCriterion("other_amount >", value, "otherAmount");
            return (Criteria) this;
        }

        public Criteria andOtherAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("other_amount >=", value, "otherAmount");
            return (Criteria) this;
        }

        public Criteria andOtherAmountLessThan(BigDecimal value) {
            addCriterion("other_amount <", value, "otherAmount");
            return (Criteria) this;
        }

        public Criteria andOtherAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("other_amount <=", value, "otherAmount");
            return (Criteria) this;
        }

        public Criteria andOtherAmountIn(List<BigDecimal> values) {
            addCriterion("other_amount in", values, "otherAmount");
            return (Criteria) this;
        }

        public Criteria andOtherAmountNotIn(List<BigDecimal> values) {
            addCriterion("other_amount not in", values, "otherAmount");
            return (Criteria) this;
        }

        public Criteria andOtherAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_amount between", value1, value2, "otherAmount");
            return (Criteria) this;
        }

        public Criteria andOtherAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("other_amount not between", value1, value2, "otherAmount");
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

        public Criteria andBusiBranchNoIsNull() {
            addCriterion("busi_branch_no is null");
            return (Criteria) this;
        }

        public Criteria andBusiBranchNoIsNotNull() {
            addCriterion("busi_branch_no is not null");
            return (Criteria) this;
        }

        public Criteria andBusiBranchNoEqualTo(String value) {
            addCriterion("busi_branch_no =", value, "busiBranchNo");
            return (Criteria) this;
        }

        public Criteria andBusiBranchNoNotEqualTo(String value) {
            addCriterion("busi_branch_no <>", value, "busiBranchNo");
            return (Criteria) this;
        }

        public Criteria andBusiBranchNoGreaterThan(String value) {
            addCriterion("busi_branch_no >", value, "busiBranchNo");
            return (Criteria) this;
        }

        public Criteria andBusiBranchNoGreaterThanOrEqualTo(String value) {
            addCriterion("busi_branch_no >=", value, "busiBranchNo");
            return (Criteria) this;
        }

        public Criteria andBusiBranchNoLessThan(String value) {
            addCriterion("busi_branch_no <", value, "busiBranchNo");
            return (Criteria) this;
        }

        public Criteria andBusiBranchNoLessThanOrEqualTo(String value) {
            addCriterion("busi_branch_no <=", value, "busiBranchNo");
            return (Criteria) this;
        }

        public Criteria andBusiBranchNoLike(String value) {
            addCriterion("busi_branch_no like", value, "busiBranchNo");
            return (Criteria) this;
        }

        public Criteria andBusiBranchNoNotLike(String value) {
            addCriterion("busi_branch_no not like", value, "busiBranchNo");
            return (Criteria) this;
        }

        public Criteria andBusiBranchNoIn(List<String> values) {
            addCriterion("busi_branch_no in", values, "busiBranchNo");
            return (Criteria) this;
        }

        public Criteria andBusiBranchNoNotIn(List<String> values) {
            addCriterion("busi_branch_no not in", values, "busiBranchNo");
            return (Criteria) this;
        }

        public Criteria andBusiBranchNoBetween(String value1, String value2) {
            addCriterion("busi_branch_no between", value1, value2, "busiBranchNo");
            return (Criteria) this;
        }

        public Criteria andBusiBranchNoNotBetween(String value1, String value2) {
            addCriterion("busi_branch_no not between", value1, value2, "busiBranchNo");
            return (Criteria) this;
        }

        public Criteria andBankAccountNoIsNull() {
            addCriterion("bank_account_no is null");
            return (Criteria) this;
        }

        public Criteria andBankAccountNoIsNotNull() {
            addCriterion("bank_account_no is not null");
            return (Criteria) this;
        }

        public Criteria andBankAccountNoEqualTo(String value) {
            addCriterion("bank_account_no =", value, "bankAccountNo");
            return (Criteria) this;
        }

        public Criteria andBankAccountNoNotEqualTo(String value) {
            addCriterion("bank_account_no <>", value, "bankAccountNo");
            return (Criteria) this;
        }

        public Criteria andBankAccountNoGreaterThan(String value) {
            addCriterion("bank_account_no >", value, "bankAccountNo");
            return (Criteria) this;
        }

        public Criteria andBankAccountNoGreaterThanOrEqualTo(String value) {
            addCriterion("bank_account_no >=", value, "bankAccountNo");
            return (Criteria) this;
        }

        public Criteria andBankAccountNoLessThan(String value) {
            addCriterion("bank_account_no <", value, "bankAccountNo");
            return (Criteria) this;
        }

        public Criteria andBankAccountNoLessThanOrEqualTo(String value) {
            addCriterion("bank_account_no <=", value, "bankAccountNo");
            return (Criteria) this;
        }

        public Criteria andBankAccountNoLike(String value) {
            addCriterion("bank_account_no like", value, "bankAccountNo");
            return (Criteria) this;
        }

        public Criteria andBankAccountNoNotLike(String value) {
            addCriterion("bank_account_no not like", value, "bankAccountNo");
            return (Criteria) this;
        }

        public Criteria andBankAccountNoIn(List<String> values) {
            addCriterion("bank_account_no in", values, "bankAccountNo");
            return (Criteria) this;
        }

        public Criteria andBankAccountNoNotIn(List<String> values) {
            addCriterion("bank_account_no not in", values, "bankAccountNo");
            return (Criteria) this;
        }

        public Criteria andBankAccountNoBetween(String value1, String value2) {
            addCriterion("bank_account_no between", value1, value2, "bankAccountNo");
            return (Criteria) this;
        }

        public Criteria andBankAccountNoNotBetween(String value1, String value2) {
            addCriterion("bank_account_no not between", value1, value2, "bankAccountNo");
            return (Criteria) this;
        }

        public Criteria andSecBankaccNoIsNull() {
            addCriterion("sec_bankacc_no is null");
            return (Criteria) this;
        }

        public Criteria andSecBankaccNoIsNotNull() {
            addCriterion("sec_bankacc_no is not null");
            return (Criteria) this;
        }

        public Criteria andSecBankaccNoEqualTo(String value) {
            addCriterion("sec_bankacc_no =", value, "secBankaccNo");
            return (Criteria) this;
        }

        public Criteria andSecBankaccNoNotEqualTo(String value) {
            addCriterion("sec_bankacc_no <>", value, "secBankaccNo");
            return (Criteria) this;
        }

        public Criteria andSecBankaccNoGreaterThan(String value) {
            addCriterion("sec_bankacc_no >", value, "secBankaccNo");
            return (Criteria) this;
        }

        public Criteria andSecBankaccNoGreaterThanOrEqualTo(String value) {
            addCriterion("sec_bankacc_no >=", value, "secBankaccNo");
            return (Criteria) this;
        }

        public Criteria andSecBankaccNoLessThan(String value) {
            addCriterion("sec_bankacc_no <", value, "secBankaccNo");
            return (Criteria) this;
        }

        public Criteria andSecBankaccNoLessThanOrEqualTo(String value) {
            addCriterion("sec_bankacc_no <=", value, "secBankaccNo");
            return (Criteria) this;
        }

        public Criteria andSecBankaccNoLike(String value) {
            addCriterion("sec_bankacc_no like", value, "secBankaccNo");
            return (Criteria) this;
        }

        public Criteria andSecBankaccNoNotLike(String value) {
            addCriterion("sec_bankacc_no not like", value, "secBankaccNo");
            return (Criteria) this;
        }

        public Criteria andSecBankaccNoIn(List<String> values) {
            addCriterion("sec_bankacc_no in", values, "secBankaccNo");
            return (Criteria) this;
        }

        public Criteria andSecBankaccNoNotIn(List<String> values) {
            addCriterion("sec_bankacc_no not in", values, "secBankaccNo");
            return (Criteria) this;
        }

        public Criteria andSecBankaccNoBetween(String value1, String value2) {
            addCriterion("sec_bankacc_no between", value1, value2, "secBankaccNo");
            return (Criteria) this;
        }

        public Criteria andSecBankaccNoNotBetween(String value1, String value2) {
            addCriterion("sec_bankacc_no not between", value1, value2, "secBankaccNo");
            return (Criteria) this;
        }

        public Criteria andBankIdIsNull() {
            addCriterion("bank_id is null");
            return (Criteria) this;
        }

        public Criteria andBankIdIsNotNull() {
            addCriterion("bank_id is not null");
            return (Criteria) this;
        }

        public Criteria andBankIdEqualTo(String value) {
            addCriterion("bank_id =", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotEqualTo(String value) {
            addCriterion("bank_id <>", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThan(String value) {
            addCriterion("bank_id >", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdGreaterThanOrEqualTo(String value) {
            addCriterion("bank_id >=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThan(String value) {
            addCriterion("bank_id <", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLessThanOrEqualTo(String value) {
            addCriterion("bank_id <=", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdLike(String value) {
            addCriterion("bank_id like", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotLike(String value) {
            addCriterion("bank_id not like", value, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdIn(List<String> values) {
            addCriterion("bank_id in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotIn(List<String> values) {
            addCriterion("bank_id not in", values, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdBetween(String value1, String value2) {
            addCriterion("bank_id between", value1, value2, "bankId");
            return (Criteria) this;
        }

        public Criteria andBankIdNotBetween(String value1, String value2) {
            addCriterion("bank_id not between", value1, value2, "bankId");
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

        public Criteria andCardTypeIsNull() {
            addCriterion("card_type is null");
            return (Criteria) this;
        }

        public Criteria andCardTypeIsNotNull() {
            addCriterion("card_type is not null");
            return (Criteria) this;
        }

        public Criteria andCardTypeEqualTo(String value) {
            addCriterion("card_type =", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotEqualTo(String value) {
            addCriterion("card_type <>", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThan(String value) {
            addCriterion("card_type >", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeGreaterThanOrEqualTo(String value) {
            addCriterion("card_type >=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThan(String value) {
            addCriterion("card_type <", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLessThanOrEqualTo(String value) {
            addCriterion("card_type <=", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeLike(String value) {
            addCriterion("card_type like", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotLike(String value) {
            addCriterion("card_type not like", value, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeIn(List<String> values) {
            addCriterion("card_type in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotIn(List<String> values) {
            addCriterion("card_type not in", values, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeBetween(String value1, String value2) {
            addCriterion("card_type between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andCardTypeNotBetween(String value1, String value2) {
            addCriterion("card_type not between", value1, value2, "cardType");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("note not between", value1, value2, "note");
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