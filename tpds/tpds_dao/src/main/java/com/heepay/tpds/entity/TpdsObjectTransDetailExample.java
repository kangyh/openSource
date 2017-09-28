package com.heepay.tpds.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TpdsObjectTransDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TpdsObjectTransDetailExample() {
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

        public Criteria andAccIdIsNull() {
            addCriterion("acc_id is null");
            return (Criteria) this;
        }

        public Criteria andAccIdIsNotNull() {
            addCriterion("acc_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccIdEqualTo(Long value) {
            addCriterion("acc_id =", value, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdNotEqualTo(Long value) {
            addCriterion("acc_id <>", value, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdGreaterThan(Long value) {
            addCriterion("acc_id >", value, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdGreaterThanOrEqualTo(Long value) {
            addCriterion("acc_id >=", value, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdLessThan(Long value) {
            addCriterion("acc_id <", value, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdLessThanOrEqualTo(Long value) {
            addCriterion("acc_id <=", value, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdIn(List<Long> values) {
            addCriterion("acc_id in", values, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdNotIn(List<Long> values) {
            addCriterion("acc_id not in", values, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdBetween(Long value1, Long value2) {
            addCriterion("acc_id between", value1, value2, "accId");
            return (Criteria) this;
        }

        public Criteria andAccIdNotBetween(Long value1, Long value2) {
            addCriterion("acc_id not between", value1, value2, "accId");
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

        public Criteria andObjectIdIsNull() {
            addCriterion("object_id is null");
            return (Criteria) this;
        }

        public Criteria andObjectIdIsNotNull() {
            addCriterion("object_id is not null");
            return (Criteria) this;
        }

        public Criteria andObjectIdEqualTo(String value) {
            addCriterion("object_id =", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdNotEqualTo(String value) {
            addCriterion("object_id <>", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdGreaterThan(String value) {
            addCriterion("object_id >", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdGreaterThanOrEqualTo(String value) {
            addCriterion("object_id >=", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdLessThan(String value) {
            addCriterion("object_id <", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdLessThanOrEqualTo(String value) {
            addCriterion("object_id <=", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdLike(String value) {
            addCriterion("object_id like", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdNotLike(String value) {
            addCriterion("object_id not like", value, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdIn(List<String> values) {
            addCriterion("object_id in", values, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdNotIn(List<String> values) {
            addCriterion("object_id not in", values, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdBetween(String value1, String value2) {
            addCriterion("object_id between", value1, value2, "objectId");
            return (Criteria) this;
        }

        public Criteria andObjectIdNotBetween(String value1, String value2) {
            addCriterion("object_id not between", value1, value2, "objectId");
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