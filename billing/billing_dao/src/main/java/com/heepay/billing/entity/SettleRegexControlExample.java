package com.heepay.billing.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SettleRegexControlExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SettleRegexControlExample() {
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

        public Criteria andRegexControlIdIsNull() {
            addCriterion("regex_control_id is null");
            return (Criteria) this;
        }

        public Criteria andRegexControlIdIsNotNull() {
            addCriterion("regex_control_id is not null");
            return (Criteria) this;
        }

        public Criteria andRegexControlIdEqualTo(Long value) {
            addCriterion("regex_control_id =", value, "regexControlId");
            return (Criteria) this;
        }

        public Criteria andRegexControlIdNotEqualTo(Long value) {
            addCriterion("regex_control_id <>", value, "regexControlId");
            return (Criteria) this;
        }

        public Criteria andRegexControlIdGreaterThan(Long value) {
            addCriterion("regex_control_id >", value, "regexControlId");
            return (Criteria) this;
        }

        public Criteria andRegexControlIdGreaterThanOrEqualTo(Long value) {
            addCriterion("regex_control_id >=", value, "regexControlId");
            return (Criteria) this;
        }

        public Criteria andRegexControlIdLessThan(Long value) {
            addCriterion("regex_control_id <", value, "regexControlId");
            return (Criteria) this;
        }

        public Criteria andRegexControlIdLessThanOrEqualTo(Long value) {
            addCriterion("regex_control_id <=", value, "regexControlId");
            return (Criteria) this;
        }

        public Criteria andRegexControlIdIn(List<Long> values) {
            addCriterion("regex_control_id in", values, "regexControlId");
            return (Criteria) this;
        }

        public Criteria andRegexControlIdNotIn(List<Long> values) {
            addCriterion("regex_control_id not in", values, "regexControlId");
            return (Criteria) this;
        }

        public Criteria andRegexControlIdBetween(Long value1, Long value2) {
            addCriterion("regex_control_id between", value1, value2, "regexControlId");
            return (Criteria) this;
        }

        public Criteria andRegexControlIdNotBetween(Long value1, Long value2) {
            addCriterion("regex_control_id not between", value1, value2, "regexControlId");
            return (Criteria) this;
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

        public Criteria andRuleKeyIsNull() {
            addCriterion("rule_key is null");
            return (Criteria) this;
        }

        public Criteria andRuleKeyIsNotNull() {
            addCriterion("rule_key is not null");
            return (Criteria) this;
        }

        public Criteria andRuleKeyEqualTo(Byte value) {
            addCriterion("rule_key =", value, "ruleKey");
            return (Criteria) this;
        }

        public Criteria andRuleKeyNotEqualTo(Byte value) {
            addCriterion("rule_key <>", value, "ruleKey");
            return (Criteria) this;
        }

        public Criteria andRuleKeyGreaterThan(Byte value) {
            addCriterion("rule_key >", value, "ruleKey");
            return (Criteria) this;
        }

        public Criteria andRuleKeyGreaterThanOrEqualTo(Byte value) {
            addCriterion("rule_key >=", value, "ruleKey");
            return (Criteria) this;
        }

        public Criteria andRuleKeyLessThan(Byte value) {
            addCriterion("rule_key <", value, "ruleKey");
            return (Criteria) this;
        }

        public Criteria andRuleKeyLessThanOrEqualTo(Byte value) {
            addCriterion("rule_key <=", value, "ruleKey");
            return (Criteria) this;
        }

        public Criteria andRuleKeyIn(List<Byte> values) {
            addCriterion("rule_key in", values, "ruleKey");
            return (Criteria) this;
        }

        public Criteria andRuleKeyNotIn(List<Byte> values) {
            addCriterion("rule_key not in", values, "ruleKey");
            return (Criteria) this;
        }

        public Criteria andRuleKeyBetween(Byte value1, Byte value2) {
            addCriterion("rule_key between", value1, value2, "ruleKey");
            return (Criteria) this;
        }

        public Criteria andRuleKeyNotBetween(Byte value1, Byte value2) {
            addCriterion("rule_key not between", value1, value2, "ruleKey");
            return (Criteria) this;
        }

        public Criteria andRegexNameIsNull() {
            addCriterion("regex_name is null");
            return (Criteria) this;
        }

        public Criteria andRegexNameIsNotNull() {
            addCriterion("regex_name is not null");
            return (Criteria) this;
        }

        public Criteria andRegexNameEqualTo(String value) {
            addCriterion("regex_name =", value, "regexName");
            return (Criteria) this;
        }

        public Criteria andRegexNameNotEqualTo(String value) {
            addCriterion("regex_name <>", value, "regexName");
            return (Criteria) this;
        }

        public Criteria andRegexNameGreaterThan(String value) {
            addCriterion("regex_name >", value, "regexName");
            return (Criteria) this;
        }

        public Criteria andRegexNameGreaterThanOrEqualTo(String value) {
            addCriterion("regex_name >=", value, "regexName");
            return (Criteria) this;
        }

        public Criteria andRegexNameLessThan(String value) {
            addCriterion("regex_name <", value, "regexName");
            return (Criteria) this;
        }

        public Criteria andRegexNameLessThanOrEqualTo(String value) {
            addCriterion("regex_name <=", value, "regexName");
            return (Criteria) this;
        }

        public Criteria andRegexNameLike(String value) {
            addCriterion("regex_name like", value, "regexName");
            return (Criteria) this;
        }

        public Criteria andRegexNameNotLike(String value) {
            addCriterion("regex_name not like", value, "regexName");
            return (Criteria) this;
        }

        public Criteria andRegexNameIn(List<String> values) {
            addCriterion("regex_name in", values, "regexName");
            return (Criteria) this;
        }

        public Criteria andRegexNameNotIn(List<String> values) {
            addCriterion("regex_name not in", values, "regexName");
            return (Criteria) this;
        }

        public Criteria andRegexNameBetween(String value1, String value2) {
            addCriterion("regex_name between", value1, value2, "regexName");
            return (Criteria) this;
        }

        public Criteria andRegexNameNotBetween(String value1, String value2) {
            addCriterion("regex_name not between", value1, value2, "regexName");
            return (Criteria) this;
        }

        public Criteria andRegexShowIsNull() {
            addCriterion("regex_show is null");
            return (Criteria) this;
        }

        public Criteria andRegexShowIsNotNull() {
            addCriterion("regex_show is not null");
            return (Criteria) this;
        }

        public Criteria andRegexShowEqualTo(String value) {
            addCriterion("regex_show =", value, "regexShow");
            return (Criteria) this;
        }

        public Criteria andRegexShowNotEqualTo(String value) {
            addCriterion("regex_show <>", value, "regexShow");
            return (Criteria) this;
        }

        public Criteria andRegexShowGreaterThan(String value) {
            addCriterion("regex_show >", value, "regexShow");
            return (Criteria) this;
        }

        public Criteria andRegexShowGreaterThanOrEqualTo(String value) {
            addCriterion("regex_show >=", value, "regexShow");
            return (Criteria) this;
        }

        public Criteria andRegexShowLessThan(String value) {
            addCriterion("regex_show <", value, "regexShow");
            return (Criteria) this;
        }

        public Criteria andRegexShowLessThanOrEqualTo(String value) {
            addCriterion("regex_show <=", value, "regexShow");
            return (Criteria) this;
        }

        public Criteria andRegexShowLike(String value) {
            addCriterion("regex_show like", value, "regexShow");
            return (Criteria) this;
        }

        public Criteria andRegexShowNotLike(String value) {
            addCriterion("regex_show not like", value, "regexShow");
            return (Criteria) this;
        }

        public Criteria andRegexShowIn(List<String> values) {
            addCriterion("regex_show in", values, "regexShow");
            return (Criteria) this;
        }

        public Criteria andRegexShowNotIn(List<String> values) {
            addCriterion("regex_show not in", values, "regexShow");
            return (Criteria) this;
        }

        public Criteria andRegexShowBetween(String value1, String value2) {
            addCriterion("regex_show between", value1, value2, "regexShow");
            return (Criteria) this;
        }

        public Criteria andRegexShowNotBetween(String value1, String value2) {
            addCriterion("regex_show not between", value1, value2, "regexShow");
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

        public Criteria andRuleTypeIsNull() {
            addCriterion("rule_type is null");
            return (Criteria) this;
        }

        public Criteria andRuleTypeIsNotNull() {
            addCriterion("rule_type is not null");
            return (Criteria) this;
        }

        public Criteria andRuleTypeEqualTo(String value) {
            addCriterion("rule_type =", value, "ruleType");
            return (Criteria) this;
        }

        public Criteria andRuleTypeNotEqualTo(String value) {
            addCriterion("rule_type <>", value, "ruleType");
            return (Criteria) this;
        }

        public Criteria andRuleTypeGreaterThan(String value) {
            addCriterion("rule_type >", value, "ruleType");
            return (Criteria) this;
        }

        public Criteria andRuleTypeGreaterThanOrEqualTo(String value) {
            addCriterion("rule_type >=", value, "ruleType");
            return (Criteria) this;
        }

        public Criteria andRuleTypeLessThan(String value) {
            addCriterion("rule_type <", value, "ruleType");
            return (Criteria) this;
        }

        public Criteria andRuleTypeLessThanOrEqualTo(String value) {
            addCriterion("rule_type <=", value, "ruleType");
            return (Criteria) this;
        }

        public Criteria andRuleTypeLike(String value) {
            addCriterion("rule_type like", value, "ruleType");
            return (Criteria) this;
        }

        public Criteria andRuleTypeNotLike(String value) {
            addCriterion("rule_type not like", value, "ruleType");
            return (Criteria) this;
        }

        public Criteria andRuleTypeIn(List<String> values) {
            addCriterion("rule_type in", values, "ruleType");
            return (Criteria) this;
        }

        public Criteria andRuleTypeNotIn(List<String> values) {
            addCriterion("rule_type not in", values, "ruleType");
            return (Criteria) this;
        }

        public Criteria andRuleTypeBetween(String value1, String value2) {
            addCriterion("rule_type between", value1, value2, "ruleType");
            return (Criteria) this;
        }

        public Criteria andRuleTypeNotBetween(String value1, String value2) {
            addCriterion("rule_type not between", value1, value2, "ruleType");
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