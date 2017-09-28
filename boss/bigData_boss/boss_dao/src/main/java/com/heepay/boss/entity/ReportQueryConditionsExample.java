package com.heepay.boss.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 *
 * 描 述：BOSS报表条件设置
 *
 * 创 建 者：wangdong
 * 创建时间：2017/6/1 14:16
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
public class ReportQueryConditionsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ReportQueryConditionsExample() {
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

        public Criteria andReportIdIsNull() {
            addCriterion("report_id is null");
            return (Criteria) this;
        }

        public Criteria andReportIdIsNotNull() {
            addCriterion("report_id is not null");
            return (Criteria) this;
        }

        public Criteria andReportIdEqualTo(Long value) {
            addCriterion("report_id =", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdNotEqualTo(Long value) {
            addCriterion("report_id <>", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdGreaterThan(Long value) {
            addCriterion("report_id >", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdGreaterThanOrEqualTo(Long value) {
            addCriterion("report_id >=", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdLessThan(Long value) {
            addCriterion("report_id <", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdLessThanOrEqualTo(Long value) {
            addCriterion("report_id <=", value, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdIn(List<Long> values) {
            addCriterion("report_id in", values, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdNotIn(List<Long> values) {
            addCriterion("report_id not in", values, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdBetween(Long value1, Long value2) {
            addCriterion("report_id between", value1, value2, "reportId");
            return (Criteria) this;
        }

        public Criteria andReportIdNotBetween(Long value1, Long value2) {
            addCriterion("report_id not between", value1, value2, "reportId");
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

        public Criteria andPayTypeNameIsNull() {
            addCriterion("pay_type_name is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameIsNotNull() {
            addCriterion("pay_type_name is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameEqualTo(String value) {
            addCriterion("pay_type_name =", value, "payTypeName");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameNotEqualTo(String value) {
            addCriterion("pay_type_name <>", value, "payTypeName");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameGreaterThan(String value) {
            addCriterion("pay_type_name >", value, "payTypeName");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("pay_type_name >=", value, "payTypeName");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameLessThan(String value) {
            addCriterion("pay_type_name <", value, "payTypeName");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameLessThanOrEqualTo(String value) {
            addCriterion("pay_type_name <=", value, "payTypeName");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameLike(String value) {
            addCriterion("pay_type_name like", value, "payTypeName");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameNotLike(String value) {
            addCriterion("pay_type_name not like", value, "payTypeName");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameIn(List<String> values) {
            addCriterion("pay_type_name in", values, "payTypeName");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameNotIn(List<String> values) {
            addCriterion("pay_type_name not in", values, "payTypeName");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameBetween(String value1, String value2) {
            addCriterion("pay_type_name between", value1, value2, "payTypeName");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameNotBetween(String value1, String value2) {
            addCriterion("pay_type_name not between", value1, value2, "payTypeName");
            return (Criteria) this;
        }

        public Criteria andBankProviderIsNull() {
            addCriterion("bank_provider is null");
            return (Criteria) this;
        }

        public Criteria andBankProviderIsNotNull() {
            addCriterion("bank_provider is not null");
            return (Criteria) this;
        }

        public Criteria andBankProviderEqualTo(String value) {
            addCriterion("bank_provider =", value, "bankProvider");
            return (Criteria) this;
        }

        public Criteria andBankProviderNotEqualTo(String value) {
            addCriterion("bank_provider <>", value, "bankProvider");
            return (Criteria) this;
        }

        public Criteria andBankProviderGreaterThan(String value) {
            addCriterion("bank_provider >", value, "bankProvider");
            return (Criteria) this;
        }

        public Criteria andBankProviderGreaterThanOrEqualTo(String value) {
            addCriterion("bank_provider >=", value, "bankProvider");
            return (Criteria) this;
        }

        public Criteria andBankProviderLessThan(String value) {
            addCriterion("bank_provider <", value, "bankProvider");
            return (Criteria) this;
        }

        public Criteria andBankProviderLessThanOrEqualTo(String value) {
            addCriterion("bank_provider <=", value, "bankProvider");
            return (Criteria) this;
        }

        public Criteria andBankProviderLike(String value) {
            addCriterion("bank_provider like", value, "bankProvider");
            return (Criteria) this;
        }

        public Criteria andBankProviderNotLike(String value) {
            addCriterion("bank_provider not like", value, "bankProvider");
            return (Criteria) this;
        }

        public Criteria andBankProviderIn(List<String> values) {
            addCriterion("bank_provider in", values, "bankProvider");
            return (Criteria) this;
        }

        public Criteria andBankProviderNotIn(List<String> values) {
            addCriterion("bank_provider not in", values, "bankProvider");
            return (Criteria) this;
        }

        public Criteria andBankProviderBetween(String value1, String value2) {
            addCriterion("bank_provider between", value1, value2, "bankProvider");
            return (Criteria) this;
        }

        public Criteria andBankProviderNotBetween(String value1, String value2) {
            addCriterion("bank_provider not between", value1, value2, "bankProvider");
            return (Criteria) this;
        }

        public Criteria andBankProviderNameIsNull() {
            addCriterion("bank_provider_name is null");
            return (Criteria) this;
        }

        public Criteria andBankProviderNameIsNotNull() {
            addCriterion("bank_provider_name is not null");
            return (Criteria) this;
        }

        public Criteria andBankProviderNameEqualTo(String value) {
            addCriterion("bank_provider_name =", value, "bankProviderName");
            return (Criteria) this;
        }

        public Criteria andBankProviderNameNotEqualTo(String value) {
            addCriterion("bank_provider_name <>", value, "bankProviderName");
            return (Criteria) this;
        }

        public Criteria andBankProviderNameGreaterThan(String value) {
            addCriterion("bank_provider_name >", value, "bankProviderName");
            return (Criteria) this;
        }

        public Criteria andBankProviderNameGreaterThanOrEqualTo(String value) {
            addCriterion("bank_provider_name >=", value, "bankProviderName");
            return (Criteria) this;
        }

        public Criteria andBankProviderNameLessThan(String value) {
            addCriterion("bank_provider_name <", value, "bankProviderName");
            return (Criteria) this;
        }

        public Criteria andBankProviderNameLessThanOrEqualTo(String value) {
            addCriterion("bank_provider_name <=", value, "bankProviderName");
            return (Criteria) this;
        }

        public Criteria andBankProviderNameLike(String value) {
            addCriterion("bank_provider_name like", value, "bankProviderName");
            return (Criteria) this;
        }

        public Criteria andBankProviderNameNotLike(String value) {
            addCriterion("bank_provider_name not like", value, "bankProviderName");
            return (Criteria) this;
        }

        public Criteria andBankProviderNameIn(List<String> values) {
            addCriterion("bank_provider_name in", values, "bankProviderName");
            return (Criteria) this;
        }

        public Criteria andBankProviderNameNotIn(List<String> values) {
            addCriterion("bank_provider_name not in", values, "bankProviderName");
            return (Criteria) this;
        }

        public Criteria andBankProviderNameBetween(String value1, String value2) {
            addCriterion("bank_provider_name between", value1, value2, "bankProviderName");
            return (Criteria) this;
        }

        public Criteria andBankProviderNameNotBetween(String value1, String value2) {
            addCriterion("bank_provider_name not between", value1, value2, "bankProviderName");
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

        public Criteria andBankNameReIsNull() {
            addCriterion("bank_name_re is null");
            return (Criteria) this;
        }

        public Criteria andBankNameReIsNotNull() {
            addCriterion("bank_name_re is not null");
            return (Criteria) this;
        }

        public Criteria andBankNameReEqualTo(String value) {
            addCriterion("bank_name_re =", value, "bankNameRe");
            return (Criteria) this;
        }

        public Criteria andBankNameReNotEqualTo(String value) {
            addCriterion("bank_name_re <>", value, "bankNameRe");
            return (Criteria) this;
        }

        public Criteria andBankNameReGreaterThan(String value) {
            addCriterion("bank_name_re >", value, "bankNameRe");
            return (Criteria) this;
        }

        public Criteria andBankNameReGreaterThanOrEqualTo(String value) {
            addCriterion("bank_name_re >=", value, "bankNameRe");
            return (Criteria) this;
        }

        public Criteria andBankNameReLessThan(String value) {
            addCriterion("bank_name_re <", value, "bankNameRe");
            return (Criteria) this;
        }

        public Criteria andBankNameReLessThanOrEqualTo(String value) {
            addCriterion("bank_name_re <=", value, "bankNameRe");
            return (Criteria) this;
        }

        public Criteria andBankNameReLike(String value) {
            addCriterion("bank_name_re like", value, "bankNameRe");
            return (Criteria) this;
        }

        public Criteria andBankNameReNotLike(String value) {
            addCriterion("bank_name_re not like", value, "bankNameRe");
            return (Criteria) this;
        }

        public Criteria andBankNameReIn(List<String> values) {
            addCriterion("bank_name_re in", values, "bankNameRe");
            return (Criteria) this;
        }

        public Criteria andBankNameReNotIn(List<String> values) {
            addCriterion("bank_name_re not in", values, "bankNameRe");
            return (Criteria) this;
        }

        public Criteria andBankNameReBetween(String value1, String value2) {
            addCriterion("bank_name_re between", value1, value2, "bankNameRe");
            return (Criteria) this;
        }

        public Criteria andBankNameReNotBetween(String value1, String value2) {
            addCriterion("bank_name_re not between", value1, value2, "bankNameRe");
            return (Criteria) this;
        }

        public Criteria andPayTypeJavaIsNull() {
            addCriterion("pay_type_java is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeJavaIsNotNull() {
            addCriterion("pay_type_java is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeJavaEqualTo(String value) {
            addCriterion("pay_type_java =", value, "payTypeJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeJavaNotEqualTo(String value) {
            addCriterion("pay_type_java <>", value, "payTypeJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeJavaGreaterThan(String value) {
            addCriterion("pay_type_java >", value, "payTypeJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeJavaGreaterThanOrEqualTo(String value) {
            addCriterion("pay_type_java >=", value, "payTypeJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeJavaLessThan(String value) {
            addCriterion("pay_type_java <", value, "payTypeJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeJavaLessThanOrEqualTo(String value) {
            addCriterion("pay_type_java <=", value, "payTypeJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeJavaLike(String value) {
            addCriterion("pay_type_java like", value, "payTypeJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeJavaNotLike(String value) {
            addCriterion("pay_type_java not like", value, "payTypeJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeJavaIn(List<String> values) {
            addCriterion("pay_type_java in", values, "payTypeJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeJavaNotIn(List<String> values) {
            addCriterion("pay_type_java not in", values, "payTypeJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeJavaBetween(String value1, String value2) {
            addCriterion("pay_type_java between", value1, value2, "payTypeJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeJavaNotBetween(String value1, String value2) {
            addCriterion("pay_type_java not between", value1, value2, "payTypeJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameJavaIsNull() {
            addCriterion("pay_type_name_java is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameJavaIsNotNull() {
            addCriterion("pay_type_name_java is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameJavaEqualTo(String value) {
            addCriterion("pay_type_name_java =", value, "payTypeNameJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameJavaNotEqualTo(String value) {
            addCriterion("pay_type_name_java <>", value, "payTypeNameJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameJavaGreaterThan(String value) {
            addCriterion("pay_type_name_java >", value, "payTypeNameJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameJavaGreaterThanOrEqualTo(String value) {
            addCriterion("pay_type_name_java >=", value, "payTypeNameJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameJavaLessThan(String value) {
            addCriterion("pay_type_name_java <", value, "payTypeNameJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameJavaLessThanOrEqualTo(String value) {
            addCriterion("pay_type_name_java <=", value, "payTypeNameJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameJavaLike(String value) {
            addCriterion("pay_type_name_java like", value, "payTypeNameJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameJavaNotLike(String value) {
            addCriterion("pay_type_name_java not like", value, "payTypeNameJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameJavaIn(List<String> values) {
            addCriterion("pay_type_name_java in", values, "payTypeNameJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameJavaNotIn(List<String> values) {
            addCriterion("pay_type_name_java not in", values, "payTypeNameJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameJavaBetween(String value1, String value2) {
            addCriterion("pay_type_name_java between", value1, value2, "payTypeNameJava");
            return (Criteria) this;
        }

        public Criteria andPayTypeNameJavaNotBetween(String value1, String value2) {
            addCriterion("pay_type_name_java not between", value1, value2, "payTypeNameJava");
            return (Criteria) this;
        }

        public Criteria andBankIdJavaIsNull() {
            addCriterion("bank_id_java is null");
            return (Criteria) this;
        }

        public Criteria andBankIdJavaIsNotNull() {
            addCriterion("bank_id_java is not null");
            return (Criteria) this;
        }

        public Criteria andBankIdJavaEqualTo(String value) {
            addCriterion("bank_id_java =", value, "bankIdJava");
            return (Criteria) this;
        }

        public Criteria andBankIdJavaNotEqualTo(String value) {
            addCriterion("bank_id_java <>", value, "bankIdJava");
            return (Criteria) this;
        }

        public Criteria andBankIdJavaGreaterThan(String value) {
            addCriterion("bank_id_java >", value, "bankIdJava");
            return (Criteria) this;
        }

        public Criteria andBankIdJavaGreaterThanOrEqualTo(String value) {
            addCriterion("bank_id_java >=", value, "bankIdJava");
            return (Criteria) this;
        }

        public Criteria andBankIdJavaLessThan(String value) {
            addCriterion("bank_id_java <", value, "bankIdJava");
            return (Criteria) this;
        }

        public Criteria andBankIdJavaLessThanOrEqualTo(String value) {
            addCriterion("bank_id_java <=", value, "bankIdJava");
            return (Criteria) this;
        }

        public Criteria andBankIdJavaLike(String value) {
            addCriterion("bank_id_java like", value, "bankIdJava");
            return (Criteria) this;
        }

        public Criteria andBankIdJavaNotLike(String value) {
            addCriterion("bank_id_java not like", value, "bankIdJava");
            return (Criteria) this;
        }

        public Criteria andBankIdJavaIn(List<String> values) {
            addCriterion("bank_id_java in", values, "bankIdJava");
            return (Criteria) this;
        }

        public Criteria andBankIdJavaNotIn(List<String> values) {
            addCriterion("bank_id_java not in", values, "bankIdJava");
            return (Criteria) this;
        }

        public Criteria andBankIdJavaBetween(String value1, String value2) {
            addCriterion("bank_id_java between", value1, value2, "bankIdJava");
            return (Criteria) this;
        }

        public Criteria andBankIdJavaNotBetween(String value1, String value2) {
            addCriterion("bank_id_java not between", value1, value2, "bankIdJava");
            return (Criteria) this;
        }

        public Criteria andBankNameJavaIsNull() {
            addCriterion("bank_name_java is null");
            return (Criteria) this;
        }

        public Criteria andBankNameJavaIsNotNull() {
            addCriterion("bank_name_java is not null");
            return (Criteria) this;
        }

        public Criteria andBankNameJavaEqualTo(String value) {
            addCriterion("bank_name_java =", value, "bankNameJava");
            return (Criteria) this;
        }

        public Criteria andBankNameJavaNotEqualTo(String value) {
            addCriterion("bank_name_java <>", value, "bankNameJava");
            return (Criteria) this;
        }

        public Criteria andBankNameJavaGreaterThan(String value) {
            addCriterion("bank_name_java >", value, "bankNameJava");
            return (Criteria) this;
        }

        public Criteria andBankNameJavaGreaterThanOrEqualTo(String value) {
            addCriterion("bank_name_java >=", value, "bankNameJava");
            return (Criteria) this;
        }

        public Criteria andBankNameJavaLessThan(String value) {
            addCriterion("bank_name_java <", value, "bankNameJava");
            return (Criteria) this;
        }

        public Criteria andBankNameJavaLessThanOrEqualTo(String value) {
            addCriterion("bank_name_java <=", value, "bankNameJava");
            return (Criteria) this;
        }

        public Criteria andBankNameJavaLike(String value) {
            addCriterion("bank_name_java like", value, "bankNameJava");
            return (Criteria) this;
        }

        public Criteria andBankNameJavaNotLike(String value) {
            addCriterion("bank_name_java not like", value, "bankNameJava");
            return (Criteria) this;
        }

        public Criteria andBankNameJavaIn(List<String> values) {
            addCriterion("bank_name_java in", values, "bankNameJava");
            return (Criteria) this;
        }

        public Criteria andBankNameJavaNotIn(List<String> values) {
            addCriterion("bank_name_java not in", values, "bankNameJava");
            return (Criteria) this;
        }

        public Criteria andBankNameJavaBetween(String value1, String value2) {
            addCriterion("bank_name_java between", value1, value2, "bankNameJava");
            return (Criteria) this;
        }

        public Criteria andBankNameJavaNotBetween(String value1, String value2) {
            addCriterion("bank_name_java not between", value1, value2, "bankNameJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerJavaIsNull() {
            addCriterion("channel_partner_java is null");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerJavaIsNotNull() {
            addCriterion("channel_partner_java is not null");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerJavaEqualTo(String value) {
            addCriterion("channel_partner_java =", value, "channelPartnerJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerJavaNotEqualTo(String value) {
            addCriterion("channel_partner_java <>", value, "channelPartnerJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerJavaGreaterThan(String value) {
            addCriterion("channel_partner_java >", value, "channelPartnerJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerJavaGreaterThanOrEqualTo(String value) {
            addCriterion("channel_partner_java >=", value, "channelPartnerJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerJavaLessThan(String value) {
            addCriterion("channel_partner_java <", value, "channelPartnerJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerJavaLessThanOrEqualTo(String value) {
            addCriterion("channel_partner_java <=", value, "channelPartnerJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerJavaLike(String value) {
            addCriterion("channel_partner_java like", value, "channelPartnerJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerJavaNotLike(String value) {
            addCriterion("channel_partner_java not like", value, "channelPartnerJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerJavaIn(List<String> values) {
            addCriterion("channel_partner_java in", values, "channelPartnerJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerJavaNotIn(List<String> values) {
            addCriterion("channel_partner_java not in", values, "channelPartnerJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerJavaBetween(String value1, String value2) {
            addCriterion("channel_partner_java between", value1, value2, "channelPartnerJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerJavaNotBetween(String value1, String value2) {
            addCriterion("channel_partner_java not between", value1, value2, "channelPartnerJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerNameJavaIsNull() {
            addCriterion("channel_partner_name_java is null");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerNameJavaIsNotNull() {
            addCriterion("channel_partner_name_java is not null");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerNameJavaEqualTo(String value) {
            addCriterion("channel_partner_name_java =", value, "channelPartnerNameJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerNameJavaNotEqualTo(String value) {
            addCriterion("channel_partner_name_java <>", value, "channelPartnerNameJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerNameJavaGreaterThan(String value) {
            addCriterion("channel_partner_name_java >", value, "channelPartnerNameJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerNameJavaGreaterThanOrEqualTo(String value) {
            addCriterion("channel_partner_name_java >=", value, "channelPartnerNameJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerNameJavaLessThan(String value) {
            addCriterion("channel_partner_name_java <", value, "channelPartnerNameJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerNameJavaLessThanOrEqualTo(String value) {
            addCriterion("channel_partner_name_java <=", value, "channelPartnerNameJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerNameJavaLike(String value) {
            addCriterion("channel_partner_name_java like", value, "channelPartnerNameJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerNameJavaNotLike(String value) {
            addCriterion("channel_partner_name_java not like", value, "channelPartnerNameJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerNameJavaIn(List<String> values) {
            addCriterion("channel_partner_name_java in", values, "channelPartnerNameJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerNameJavaNotIn(List<String> values) {
            addCriterion("channel_partner_name_java not in", values, "channelPartnerNameJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerNameJavaBetween(String value1, String value2) {
            addCriterion("channel_partner_name_java between", value1, value2, "channelPartnerNameJava");
            return (Criteria) this;
        }

        public Criteria andChannelPartnerNameJavaNotBetween(String value1, String value2) {
            addCriterion("channel_partner_name_java not between", value1, value2, "channelPartnerNameJava");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIsNull() {
            addCriterion("company_id is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIsNotNull() {
            addCriterion("company_id is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyIdEqualTo(String value) {
            addCriterion("company_id =", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotEqualTo(String value) {
            addCriterion("company_id <>", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThan(String value) {
            addCriterion("company_id >", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdGreaterThanOrEqualTo(String value) {
            addCriterion("company_id >=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThan(String value) {
            addCriterion("company_id <", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLessThanOrEqualTo(String value) {
            addCriterion("company_id <=", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdLike(String value) {
            addCriterion("company_id like", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotLike(String value) {
            addCriterion("company_id not like", value, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdIn(List<String> values) {
            addCriterion("company_id in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotIn(List<String> values) {
            addCriterion("company_id not in", values, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdBetween(String value1, String value2) {
            addCriterion("company_id between", value1, value2, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyIdNotBetween(String value1, String value2) {
            addCriterion("company_id not between", value1, value2, "companyId");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNull() {
            addCriterion("company_name is null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNotNull() {
            addCriterion("company_name is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameEqualTo(String value) {
            addCriterion("company_name =", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotEqualTo(String value) {
            addCriterion("company_name <>", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThan(String value) {
            addCriterion("company_name >", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThanOrEqualTo(String value) {
            addCriterion("company_name >=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThan(String value) {
            addCriterion("company_name <", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThanOrEqualTo(String value) {
            addCriterion("company_name <=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLike(String value) {
            addCriterion("company_name like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotLike(String value) {
            addCriterion("company_name not like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIn(List<String> values) {
            addCriterion("company_name in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotIn(List<String> values) {
            addCriterion("company_name not in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameBetween(String value1, String value2) {
            addCriterion("company_name between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotBetween(String value1, String value2) {
            addCriterion("company_name not between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
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