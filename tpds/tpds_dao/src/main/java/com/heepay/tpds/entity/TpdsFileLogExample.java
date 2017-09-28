package com.heepay.tpds.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TpdsFileLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TpdsFileLogExample() {
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

        public Criteria andLogIdIsNull() {
            addCriterion("log_id is null");
            return (Criteria) this;
        }

        public Criteria andLogIdIsNotNull() {
            addCriterion("log_id is not null");
            return (Criteria) this;
        }

        public Criteria andLogIdEqualTo(Integer value) {
            addCriterion("log_id =", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotEqualTo(Integer value) {
            addCriterion("log_id <>", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdGreaterThan(Integer value) {
            addCriterion("log_id >", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("log_id >=", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdLessThan(Integer value) {
            addCriterion("log_id <", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdLessThanOrEqualTo(Integer value) {
            addCriterion("log_id <=", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdIn(List<Integer> values) {
            addCriterion("log_id in", values, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotIn(List<Integer> values) {
            addCriterion("log_id not in", values, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdBetween(Integer value1, Integer value2) {
            addCriterion("log_id between", value1, value2, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotBetween(Integer value1, Integer value2) {
            addCriterion("log_id not between", value1, value2, "logId");
            return (Criteria) this;
        }

        public Criteria andMerchantNoIsNull() {
            addCriterion("merchant_no is null");
            return (Criteria) this;
        }

        public Criteria andMerchantNoIsNotNull() {
            addCriterion("merchant_no is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantNoEqualTo(String value) {
            addCriterion("merchant_no =", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotEqualTo(String value) {
            addCriterion("merchant_no <>", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoGreaterThan(String value) {
            addCriterion("merchant_no >", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoGreaterThanOrEqualTo(String value) {
            addCriterion("merchant_no >=", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoLessThan(String value) {
            addCriterion("merchant_no <", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoLessThanOrEqualTo(String value) {
            addCriterion("merchant_no <=", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoLike(String value) {
            addCriterion("merchant_no like", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotLike(String value) {
            addCriterion("merchant_no not like", value, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoIn(List<String> values) {
            addCriterion("merchant_no in", values, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotIn(List<String> values) {
            addCriterion("merchant_no not in", values, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoBetween(String value1, String value2) {
            addCriterion("merchant_no between", value1, value2, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andMerchantNoNotBetween(String value1, String value2) {
            addCriterion("merchant_no not between", value1, value2, "merchantNo");
            return (Criteria) this;
        }

        public Criteria andCheckFileFromIsNull() {
            addCriterion("check_file_from is null");
            return (Criteria) this;
        }

        public Criteria andCheckFileFromIsNotNull() {
            addCriterion("check_file_from is not null");
            return (Criteria) this;
        }

        public Criteria andCheckFileFromEqualTo(String value) {
            addCriterion("check_file_from =", value, "checkFileFrom");
            return (Criteria) this;
        }

        public Criteria andCheckFileFromNotEqualTo(String value) {
            addCriterion("check_file_from <>", value, "checkFileFrom");
            return (Criteria) this;
        }

        public Criteria andCheckFileFromGreaterThan(String value) {
            addCriterion("check_file_from >", value, "checkFileFrom");
            return (Criteria) this;
        }

        public Criteria andCheckFileFromGreaterThanOrEqualTo(String value) {
            addCriterion("check_file_from >=", value, "checkFileFrom");
            return (Criteria) this;
        }

        public Criteria andCheckFileFromLessThan(String value) {
            addCriterion("check_file_from <", value, "checkFileFrom");
            return (Criteria) this;
        }

        public Criteria andCheckFileFromLessThanOrEqualTo(String value) {
            addCriterion("check_file_from <=", value, "checkFileFrom");
            return (Criteria) this;
        }

        public Criteria andCheckFileFromLike(String value) {
            addCriterion("check_file_from like", value, "checkFileFrom");
            return (Criteria) this;
        }

        public Criteria andCheckFileFromNotLike(String value) {
            addCriterion("check_file_from not like", value, "checkFileFrom");
            return (Criteria) this;
        }

        public Criteria andCheckFileFromIn(List<String> values) {
            addCriterion("check_file_from in", values, "checkFileFrom");
            return (Criteria) this;
        }

        public Criteria andCheckFileFromNotIn(List<String> values) {
            addCriterion("check_file_from not in", values, "checkFileFrom");
            return (Criteria) this;
        }

        public Criteria andCheckFileFromBetween(String value1, String value2) {
            addCriterion("check_file_from between", value1, value2, "checkFileFrom");
            return (Criteria) this;
        }

        public Criteria andCheckFileFromNotBetween(String value1, String value2) {
            addCriterion("check_file_from not between", value1, value2, "checkFileFrom");
            return (Criteria) this;
        }

        public Criteria andCheckFileWhereIsNull() {
            addCriterion("check_file_where is null");
            return (Criteria) this;
        }

        public Criteria andCheckFileWhereIsNotNull() {
            addCriterion("check_file_where is not null");
            return (Criteria) this;
        }

        public Criteria andCheckFileWhereEqualTo(String value) {
            addCriterion("check_file_where =", value, "checkFileWhere");
            return (Criteria) this;
        }

        public Criteria andCheckFileWhereNotEqualTo(String value) {
            addCriterion("check_file_where <>", value, "checkFileWhere");
            return (Criteria) this;
        }

        public Criteria andCheckFileWhereGreaterThan(String value) {
            addCriterion("check_file_where >", value, "checkFileWhere");
            return (Criteria) this;
        }

        public Criteria andCheckFileWhereGreaterThanOrEqualTo(String value) {
            addCriterion("check_file_where >=", value, "checkFileWhere");
            return (Criteria) this;
        }

        public Criteria andCheckFileWhereLessThan(String value) {
            addCriterion("check_file_where <", value, "checkFileWhere");
            return (Criteria) this;
        }

        public Criteria andCheckFileWhereLessThanOrEqualTo(String value) {
            addCriterion("check_file_where <=", value, "checkFileWhere");
            return (Criteria) this;
        }

        public Criteria andCheckFileWhereLike(String value) {
            addCriterion("check_file_where like", value, "checkFileWhere");
            return (Criteria) this;
        }

        public Criteria andCheckFileWhereNotLike(String value) {
            addCriterion("check_file_where not like", value, "checkFileWhere");
            return (Criteria) this;
        }

        public Criteria andCheckFileWhereIn(List<String> values) {
            addCriterion("check_file_where in", values, "checkFileWhere");
            return (Criteria) this;
        }

        public Criteria andCheckFileWhereNotIn(List<String> values) {
            addCriterion("check_file_where not in", values, "checkFileWhere");
            return (Criteria) this;
        }

        public Criteria andCheckFileWhereBetween(String value1, String value2) {
            addCriterion("check_file_where between", value1, value2, "checkFileWhere");
            return (Criteria) this;
        }

        public Criteria andCheckFileWhereNotBetween(String value1, String value2) {
            addCriterion("check_file_where not between", value1, value2, "checkFileWhere");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNull() {
            addCriterion("file_name is null");
            return (Criteria) this;
        }

        public Criteria andFileNameIsNotNull() {
            addCriterion("file_name is not null");
            return (Criteria) this;
        }

        public Criteria andFileNameEqualTo(String value) {
            addCriterion("file_name =", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotEqualTo(String value) {
            addCriterion("file_name <>", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThan(String value) {
            addCriterion("file_name >", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("file_name >=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThan(String value) {
            addCriterion("file_name <", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLessThanOrEqualTo(String value) {
            addCriterion("file_name <=", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameLike(String value) {
            addCriterion("file_name like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotLike(String value) {
            addCriterion("file_name not like", value, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameIn(List<String> values) {
            addCriterion("file_name in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotIn(List<String> values) {
            addCriterion("file_name not in", values, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameBetween(String value1, String value2) {
            addCriterion("file_name between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileNameNotBetween(String value1, String value2) {
            addCriterion("file_name not between", value1, value2, "fileName");
            return (Criteria) this;
        }

        public Criteria andFileDirIsNull() {
            addCriterion("file_dir is null");
            return (Criteria) this;
        }

        public Criteria andFileDirIsNotNull() {
            addCriterion("file_dir is not null");
            return (Criteria) this;
        }

        public Criteria andFileDirEqualTo(String value) {
            addCriterion("file_dir =", value, "fileDir");
            return (Criteria) this;
        }

        public Criteria andFileDirNotEqualTo(String value) {
            addCriterion("file_dir <>", value, "fileDir");
            return (Criteria) this;
        }

        public Criteria andFileDirGreaterThan(String value) {
            addCriterion("file_dir >", value, "fileDir");
            return (Criteria) this;
        }

        public Criteria andFileDirGreaterThanOrEqualTo(String value) {
            addCriterion("file_dir >=", value, "fileDir");
            return (Criteria) this;
        }

        public Criteria andFileDirLessThan(String value) {
            addCriterion("file_dir <", value, "fileDir");
            return (Criteria) this;
        }

        public Criteria andFileDirLessThanOrEqualTo(String value) {
            addCriterion("file_dir <=", value, "fileDir");
            return (Criteria) this;
        }

        public Criteria andFileDirLike(String value) {
            addCriterion("file_dir like", value, "fileDir");
            return (Criteria) this;
        }

        public Criteria andFileDirNotLike(String value) {
            addCriterion("file_dir not like", value, "fileDir");
            return (Criteria) this;
        }

        public Criteria andFileDirIn(List<String> values) {
            addCriterion("file_dir in", values, "fileDir");
            return (Criteria) this;
        }

        public Criteria andFileDirNotIn(List<String> values) {
            addCriterion("file_dir not in", values, "fileDir");
            return (Criteria) this;
        }

        public Criteria andFileDirBetween(String value1, String value2) {
            addCriterion("file_dir between", value1, value2, "fileDir");
            return (Criteria) this;
        }

        public Criteria andFileDirNotBetween(String value1, String value2) {
            addCriterion("file_dir not between", value1, value2, "fileDir");
            return (Criteria) this;
        }

        public Criteria andOperPersonIsNull() {
            addCriterion("oper_person is null");
            return (Criteria) this;
        }

        public Criteria andOperPersonIsNotNull() {
            addCriterion("oper_person is not null");
            return (Criteria) this;
        }

        public Criteria andOperPersonEqualTo(String value) {
            addCriterion("oper_person =", value, "operPerson");
            return (Criteria) this;
        }

        public Criteria andOperPersonNotEqualTo(String value) {
            addCriterion("oper_person <>", value, "operPerson");
            return (Criteria) this;
        }

        public Criteria andOperPersonGreaterThan(String value) {
            addCriterion("oper_person >", value, "operPerson");
            return (Criteria) this;
        }

        public Criteria andOperPersonGreaterThanOrEqualTo(String value) {
            addCriterion("oper_person >=", value, "operPerson");
            return (Criteria) this;
        }

        public Criteria andOperPersonLessThan(String value) {
            addCriterion("oper_person <", value, "operPerson");
            return (Criteria) this;
        }

        public Criteria andOperPersonLessThanOrEqualTo(String value) {
            addCriterion("oper_person <=", value, "operPerson");
            return (Criteria) this;
        }

        public Criteria andOperPersonLike(String value) {
            addCriterion("oper_person like", value, "operPerson");
            return (Criteria) this;
        }

        public Criteria andOperPersonNotLike(String value) {
            addCriterion("oper_person not like", value, "operPerson");
            return (Criteria) this;
        }

        public Criteria andOperPersonIn(List<String> values) {
            addCriterion("oper_person in", values, "operPerson");
            return (Criteria) this;
        }

        public Criteria andOperPersonNotIn(List<String> values) {
            addCriterion("oper_person not in", values, "operPerson");
            return (Criteria) this;
        }

        public Criteria andOperPersonBetween(String value1, String value2) {
            addCriterion("oper_person between", value1, value2, "operPerson");
            return (Criteria) this;
        }

        public Criteria andOperPersonNotBetween(String value1, String value2) {
            addCriterion("oper_person not between", value1, value2, "operPerson");
            return (Criteria) this;
        }

        public Criteria andOperTimeIsNull() {
            addCriterion("oper_time is null");
            return (Criteria) this;
        }

        public Criteria andOperTimeIsNotNull() {
            addCriterion("oper_time is not null");
            return (Criteria) this;
        }

        public Criteria andOperTimeEqualTo(Date value) {
            addCriterion("oper_time =", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeNotEqualTo(Date value) {
            addCriterion("oper_time <>", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeGreaterThan(Date value) {
            addCriterion("oper_time >", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("oper_time >=", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeLessThan(Date value) {
            addCriterion("oper_time <", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeLessThanOrEqualTo(Date value) {
            addCriterion("oper_time <=", value, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeIn(List<Date> values) {
            addCriterion("oper_time in", values, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeNotIn(List<Date> values) {
            addCriterion("oper_time not in", values, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeBetween(Date value1, Date value2) {
            addCriterion("oper_time between", value1, value2, "operTime");
            return (Criteria) this;
        }

        public Criteria andOperTimeNotBetween(Date value1, Date value2) {
            addCriterion("oper_time not between", value1, value2, "operTime");
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