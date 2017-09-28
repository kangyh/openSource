package com.heepay.tpds.entity;

import java.util.ArrayList;
import java.util.List;

public class TpdsObjectTransExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TpdsObjectTransExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andObjIdIsNull() {
            addCriterion("obj_id is null");
            return (Criteria) this;
        }

        public Criteria andObjIdIsNotNull() {
            addCriterion("obj_id is not null");
            return (Criteria) this;
        }

        public Criteria andObjIdEqualTo(String value) {
            addCriterion("obj_id =", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotEqualTo(String value) {
            addCriterion("obj_id <>", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdGreaterThan(String value) {
            addCriterion("obj_id >", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdGreaterThanOrEqualTo(String value) {
            addCriterion("obj_id >=", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdLessThan(String value) {
            addCriterion("obj_id <", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdLessThanOrEqualTo(String value) {
            addCriterion("obj_id <=", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdLike(String value) {
            addCriterion("obj_id like", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotLike(String value) {
            addCriterion("obj_id not like", value, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdIn(List<String> values) {
            addCriterion("obj_id in", values, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotIn(List<String> values) {
            addCriterion("obj_id not in", values, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdBetween(String value1, String value2) {
            addCriterion("obj_id between", value1, value2, "objId");
            return (Criteria) this;
        }

        public Criteria andObjIdNotBetween(String value1, String value2) {
            addCriterion("obj_id not between", value1, value2, "objId");
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

        public Criteria andBusiTradeTypeIsNull() {
            addCriterion("busi_trade_type is null");
            return (Criteria) this;
        }

        public Criteria andBusiTradeTypeIsNotNull() {
            addCriterion("busi_trade_type is not null");
            return (Criteria) this;
        }

        public Criteria andBusiTradeTypeEqualTo(String value) {
            addCriterion("busi_trade_type =", value, "busiTradeType");
            return (Criteria) this;
        }

        public Criteria andBusiTradeTypeNotEqualTo(String value) {
            addCriterion("busi_trade_type <>", value, "busiTradeType");
            return (Criteria) this;
        }

        public Criteria andBusiTradeTypeGreaterThan(String value) {
            addCriterion("busi_trade_type >", value, "busiTradeType");
            return (Criteria) this;
        }

        public Criteria andBusiTradeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("busi_trade_type >=", value, "busiTradeType");
            return (Criteria) this;
        }

        public Criteria andBusiTradeTypeLessThan(String value) {
            addCriterion("busi_trade_type <", value, "busiTradeType");
            return (Criteria) this;
        }

        public Criteria andBusiTradeTypeLessThanOrEqualTo(String value) {
            addCriterion("busi_trade_type <=", value, "busiTradeType");
            return (Criteria) this;
        }

        public Criteria andBusiTradeTypeLike(String value) {
            addCriterion("busi_trade_type like", value, "busiTradeType");
            return (Criteria) this;
        }

        public Criteria andBusiTradeTypeNotLike(String value) {
            addCriterion("busi_trade_type not like", value, "busiTradeType");
            return (Criteria) this;
        }

        public Criteria andBusiTradeTypeIn(List<String> values) {
            addCriterion("busi_trade_type in", values, "busiTradeType");
            return (Criteria) this;
        }

        public Criteria andBusiTradeTypeNotIn(List<String> values) {
            addCriterion("busi_trade_type not in", values, "busiTradeType");
            return (Criteria) this;
        }

        public Criteria andBusiTradeTypeBetween(String value1, String value2) {
            addCriterion("busi_trade_type between", value1, value2, "busiTradeType");
            return (Criteria) this;
        }

        public Criteria andBusiTradeTypeNotBetween(String value1, String value2) {
            addCriterion("busi_trade_type not between", value1, value2, "busiTradeType");
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