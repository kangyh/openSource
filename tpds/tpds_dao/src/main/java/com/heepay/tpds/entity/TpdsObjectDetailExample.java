package com.heepay.tpds.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TpdsObjectDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TpdsObjectDetailExample() {
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

        public Criteria andRetIdIsNull() {
            addCriterion("ret_id is null");
            return (Criteria) this;
        }

        public Criteria andRetIdIsNotNull() {
            addCriterion("ret_id is not null");
            return (Criteria) this;
        }

        public Criteria andRetIdEqualTo(Long value) {
            addCriterion("ret_id =", value, "retId");
            return (Criteria) this;
        }

        public Criteria andRetIdNotEqualTo(Long value) {
            addCriterion("ret_id <>", value, "retId");
            return (Criteria) this;
        }

        public Criteria andRetIdGreaterThan(Long value) {
            addCriterion("ret_id >", value, "retId");
            return (Criteria) this;
        }

        public Criteria andRetIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ret_id >=", value, "retId");
            return (Criteria) this;
        }

        public Criteria andRetIdLessThan(Long value) {
            addCriterion("ret_id <", value, "retId");
            return (Criteria) this;
        }

        public Criteria andRetIdLessThanOrEqualTo(Long value) {
            addCriterion("ret_id <=", value, "retId");
            return (Criteria) this;
        }

        public Criteria andRetIdIn(List<Long> values) {
            addCriterion("ret_id in", values, "retId");
            return (Criteria) this;
        }

        public Criteria andRetIdNotIn(List<Long> values) {
            addCriterion("ret_id not in", values, "retId");
            return (Criteria) this;
        }

        public Criteria andRetIdBetween(Long value1, Long value2) {
            addCriterion("ret_id between", value1, value2, "retId");
            return (Criteria) this;
        }

        public Criteria andRetIdNotBetween(Long value1, Long value2) {
            addCriterion("ret_id not between", value1, value2, "retId");
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

        public Criteria andReturnNoIsNull() {
            addCriterion("return_no is null");
            return (Criteria) this;
        }

        public Criteria andReturnNoIsNotNull() {
            addCriterion("return_no is not null");
            return (Criteria) this;
        }

        public Criteria andReturnNoEqualTo(String value) {
            addCriterion("return_no =", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoNotEqualTo(String value) {
            addCriterion("return_no <>", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoGreaterThan(String value) {
            addCriterion("return_no >", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoGreaterThanOrEqualTo(String value) {
            addCriterion("return_no >=", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoLessThan(String value) {
            addCriterion("return_no <", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoLessThanOrEqualTo(String value) {
            addCriterion("return_no <=", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoLike(String value) {
            addCriterion("return_no like", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoNotLike(String value) {
            addCriterion("return_no not like", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoIn(List<String> values) {
            addCriterion("return_no in", values, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoNotIn(List<String> values) {
            addCriterion("return_no not in", values, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoBetween(String value1, String value2) {
            addCriterion("return_no between", value1, value2, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoNotBetween(String value1, String value2) {
            addCriterion("return_no not between", value1, value2, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnDateIsNull() {
            addCriterion("return_date is null");
            return (Criteria) this;
        }

        public Criteria andReturnDateIsNotNull() {
            addCriterion("return_date is not null");
            return (Criteria) this;
        }

        public Criteria andReturnDateEqualTo(Date value) {
            addCriterion("return_date =", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateNotEqualTo(Date value) {
            addCriterion("return_date <>", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateGreaterThan(Date value) {
            addCriterion("return_date >", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateGreaterThanOrEqualTo(Date value) {
            addCriterion("return_date >=", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateLessThan(Date value) {
            addCriterion("return_date <", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateLessThanOrEqualTo(Date value) {
            addCriterion("return_date <=", value, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateIn(List<Date> values) {
            addCriterion("return_date in", values, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateNotIn(List<Date> values) {
            addCriterion("return_date not in", values, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateBetween(Date value1, Date value2) {
            addCriterion("return_date between", value1, value2, "returnDate");
            return (Criteria) this;
        }

        public Criteria andReturnDateNotBetween(Date value1, Date value2) {
            addCriterion("return_date not between", value1, value2, "returnDate");
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