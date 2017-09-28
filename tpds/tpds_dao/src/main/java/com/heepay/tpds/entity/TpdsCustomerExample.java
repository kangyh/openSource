package com.heepay.tpds.entity;

import java.util.ArrayList;
import java.util.List;

public class TpdsCustomerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TpdsCustomerExample() {
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

        public Criteria andCustomerIdIsNull() {
            addCriterion("customer_id is null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIsNotNull() {
            addCriterion("customer_id is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerIdEqualTo(Integer value) {
            addCriterion("customer_id =", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotEqualTo(Integer value) {
            addCriterion("customer_id <>", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThan(Integer value) {
            addCriterion("customer_id >", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("customer_id >=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThan(Integer value) {
            addCriterion("customer_id <", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdLessThanOrEqualTo(Integer value) {
            addCriterion("customer_id <=", value, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdIn(List<Integer> values) {
            addCriterion("customer_id in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotIn(List<Integer> values) {
            addCriterion("customer_id not in", values, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdBetween(Integer value1, Integer value2) {
            addCriterion("customer_id between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("customer_id not between", value1, value2, "customerId");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIsNull() {
            addCriterion("customer_no is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIsNotNull() {
            addCriterion("customer_no is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNoEqualTo(String value) {
            addCriterion("customer_no =", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotEqualTo(String value) {
            addCriterion("customer_no <>", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThan(String value) {
            addCriterion("customer_no >", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoGreaterThanOrEqualTo(String value) {
            addCriterion("customer_no >=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThan(String value) {
            addCriterion("customer_no <", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLessThanOrEqualTo(String value) {
            addCriterion("customer_no <=", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoLike(String value) {
            addCriterion("customer_no like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotLike(String value) {
            addCriterion("customer_no not like", value, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoIn(List<String> values) {
            addCriterion("customer_no in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotIn(List<String> values) {
            addCriterion("customer_no not in", values, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoBetween(String value1, String value2) {
            addCriterion("customer_no between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andCustomerNoNotBetween(String value1, String value2) {
            addCriterion("customer_no not between", value1, value2, "customerNo");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andRoleIsNull() {
            addCriterion("role is null");
            return (Criteria) this;
        }

        public Criteria andRoleIsNotNull() {
            addCriterion("role is not null");
            return (Criteria) this;
        }

        public Criteria andRoleEqualTo(String value) {
            addCriterion("role =", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotEqualTo(String value) {
            addCriterion("role <>", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleGreaterThan(String value) {
            addCriterion("role >", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleGreaterThanOrEqualTo(String value) {
            addCriterion("role >=", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLessThan(String value) {
            addCriterion("role <", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLessThanOrEqualTo(String value) {
            addCriterion("role <=", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLike(String value) {
            addCriterion("role like", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotLike(String value) {
            addCriterion("role not like", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleIn(List<String> values) {
            addCriterion("role in", values, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotIn(List<String> values) {
            addCriterion("role not in", values, "role");
            return (Criteria) this;
        }

        public Criteria andRoleBetween(String value1, String value2) {
            addCriterion("role between", value1, value2, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotBetween(String value1, String value2) {
            addCriterion("role not between", value1, value2, "role");
            return (Criteria) this;
        }

        public Criteria andCertTypeIsNull() {
            addCriterion("cert_type is null");
            return (Criteria) this;
        }

        public Criteria andCertTypeIsNotNull() {
            addCriterion("cert_type is not null");
            return (Criteria) this;
        }

        public Criteria andCertTypeEqualTo(String value) {
            addCriterion("cert_type =", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeNotEqualTo(String value) {
            addCriterion("cert_type <>", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeGreaterThan(String value) {
            addCriterion("cert_type >", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeGreaterThanOrEqualTo(String value) {
            addCriterion("cert_type >=", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeLessThan(String value) {
            addCriterion("cert_type <", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeLessThanOrEqualTo(String value) {
            addCriterion("cert_type <=", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeLike(String value) {
            addCriterion("cert_type like", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeNotLike(String value) {
            addCriterion("cert_type not like", value, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeIn(List<String> values) {
            addCriterion("cert_type in", values, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeNotIn(List<String> values) {
            addCriterion("cert_type not in", values, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeBetween(String value1, String value2) {
            addCriterion("cert_type between", value1, value2, "certType");
            return (Criteria) this;
        }

        public Criteria andCertTypeNotBetween(String value1, String value2) {
            addCriterion("cert_type not between", value1, value2, "certType");
            return (Criteria) this;
        }

        public Criteria andBindFlagIsNull() {
            addCriterion("bind_flag is null");
            return (Criteria) this;
        }

        public Criteria andBindFlagIsNotNull() {
            addCriterion("bind_flag is not null");
            return (Criteria) this;
        }

        public Criteria andBindFlagEqualTo(String value) {
            addCriterion("bind_flag =", value, "bindFlag");
            return (Criteria) this;
        }

        public Criteria andBindFlagNotEqualTo(String value) {
            addCriterion("bind_flag <>", value, "bindFlag");
            return (Criteria) this;
        }

        public Criteria andBindFlagGreaterThan(String value) {
            addCriterion("bind_flag >", value, "bindFlag");
            return (Criteria) this;
        }

        public Criteria andBindFlagGreaterThanOrEqualTo(String value) {
            addCriterion("bind_flag >=", value, "bindFlag");
            return (Criteria) this;
        }

        public Criteria andBindFlagLessThan(String value) {
            addCriterion("bind_flag <", value, "bindFlag");
            return (Criteria) this;
        }

        public Criteria andBindFlagLessThanOrEqualTo(String value) {
            addCriterion("bind_flag <=", value, "bindFlag");
            return (Criteria) this;
        }

        public Criteria andBindFlagLike(String value) {
            addCriterion("bind_flag like", value, "bindFlag");
            return (Criteria) this;
        }

        public Criteria andBindFlagNotLike(String value) {
            addCriterion("bind_flag not like", value, "bindFlag");
            return (Criteria) this;
        }

        public Criteria andBindFlagIn(List<String> values) {
            addCriterion("bind_flag in", values, "bindFlag");
            return (Criteria) this;
        }

        public Criteria andBindFlagNotIn(List<String> values) {
            addCriterion("bind_flag not in", values, "bindFlag");
            return (Criteria) this;
        }

        public Criteria andBindFlagBetween(String value1, String value2) {
            addCriterion("bind_flag between", value1, value2, "bindFlag");
            return (Criteria) this;
        }

        public Criteria andBindFlagNotBetween(String value1, String value2) {
            addCriterion("bind_flag not between", value1, value2, "bindFlag");
            return (Criteria) this;
        }

        public Criteria andUseNameIsNull() {
            addCriterion("use_name is null");
            return (Criteria) this;
        }

        public Criteria andUseNameIsNotNull() {
            addCriterion("use_name is not null");
            return (Criteria) this;
        }

        public Criteria andUseNameEqualTo(String value) {
            addCriterion("use_name =", value, "useName");
            return (Criteria) this;
        }

        public Criteria andUseNameNotEqualTo(String value) {
            addCriterion("use_name <>", value, "useName");
            return (Criteria) this;
        }

        public Criteria andUseNameGreaterThan(String value) {
            addCriterion("use_name >", value, "useName");
            return (Criteria) this;
        }

        public Criteria andUseNameGreaterThanOrEqualTo(String value) {
            addCriterion("use_name >=", value, "useName");
            return (Criteria) this;
        }

        public Criteria andUseNameLessThan(String value) {
            addCriterion("use_name <", value, "useName");
            return (Criteria) this;
        }

        public Criteria andUseNameLessThanOrEqualTo(String value) {
            addCriterion("use_name <=", value, "useName");
            return (Criteria) this;
        }

        public Criteria andUseNameLike(String value) {
            addCriterion("use_name like", value, "useName");
            return (Criteria) this;
        }

        public Criteria andUseNameNotLike(String value) {
            addCriterion("use_name not like", value, "useName");
            return (Criteria) this;
        }

        public Criteria andUseNameIn(List<String> values) {
            addCriterion("use_name in", values, "useName");
            return (Criteria) this;
        }

        public Criteria andUseNameNotIn(List<String> values) {
            addCriterion("use_name not in", values, "useName");
            return (Criteria) this;
        }

        public Criteria andUseNameBetween(String value1, String value2) {
            addCriterion("use_name between", value1, value2, "useName");
            return (Criteria) this;
        }

        public Criteria andUseNameNotBetween(String value1, String value2) {
            addCriterion("use_name not between", value1, value2, "useName");
            return (Criteria) this;
        }

        public Criteria andCertNoIsNull() {
            addCriterion("cert_no is null");
            return (Criteria) this;
        }

        public Criteria andCertNoIsNotNull() {
            addCriterion("cert_no is not null");
            return (Criteria) this;
        }

        public Criteria andCertNoEqualTo(String value) {
            addCriterion("cert_no =", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoNotEqualTo(String value) {
            addCriterion("cert_no <>", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoGreaterThan(String value) {
            addCriterion("cert_no >", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoGreaterThanOrEqualTo(String value) {
            addCriterion("cert_no >=", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoLessThan(String value) {
            addCriterion("cert_no <", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoLessThanOrEqualTo(String value) {
            addCriterion("cert_no <=", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoLike(String value) {
            addCriterion("cert_no like", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoNotLike(String value) {
            addCriterion("cert_no not like", value, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoIn(List<String> values) {
            addCriterion("cert_no in", values, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoNotIn(List<String> values) {
            addCriterion("cert_no not in", values, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoBetween(String value1, String value2) {
            addCriterion("cert_no between", value1, value2, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertNoNotBetween(String value1, String value2) {
            addCriterion("cert_no not between", value1, value2, "certNo");
            return (Criteria) this;
        }

        public Criteria andCertFimageIsNull() {
            addCriterion("cert_fimage is null");
            return (Criteria) this;
        }

        public Criteria andCertFimageIsNotNull() {
            addCriterion("cert_fimage is not null");
            return (Criteria) this;
        }

        public Criteria andCertFimageEqualTo(String value) {
            addCriterion("cert_fimage =", value, "certFimage");
            return (Criteria) this;
        }

        public Criteria andCertFimageNotEqualTo(String value) {
            addCriterion("cert_fimage <>", value, "certFimage");
            return (Criteria) this;
        }

        public Criteria andCertFimageGreaterThan(String value) {
            addCriterion("cert_fimage >", value, "certFimage");
            return (Criteria) this;
        }

        public Criteria andCertFimageGreaterThanOrEqualTo(String value) {
            addCriterion("cert_fimage >=", value, "certFimage");
            return (Criteria) this;
        }

        public Criteria andCertFimageLessThan(String value) {
            addCriterion("cert_fimage <", value, "certFimage");
            return (Criteria) this;
        }

        public Criteria andCertFimageLessThanOrEqualTo(String value) {
            addCriterion("cert_fimage <=", value, "certFimage");
            return (Criteria) this;
        }

        public Criteria andCertFimageLike(String value) {
            addCriterion("cert_fimage like", value, "certFimage");
            return (Criteria) this;
        }

        public Criteria andCertFimageNotLike(String value) {
            addCriterion("cert_fimage not like", value, "certFimage");
            return (Criteria) this;
        }

        public Criteria andCertFimageIn(List<String> values) {
            addCriterion("cert_fimage in", values, "certFimage");
            return (Criteria) this;
        }

        public Criteria andCertFimageNotIn(List<String> values) {
            addCriterion("cert_fimage not in", values, "certFimage");
            return (Criteria) this;
        }

        public Criteria andCertFimageBetween(String value1, String value2) {
            addCriterion("cert_fimage between", value1, value2, "certFimage");
            return (Criteria) this;
        }

        public Criteria andCertFimageNotBetween(String value1, String value2) {
            addCriterion("cert_fimage not between", value1, value2, "certFimage");
            return (Criteria) this;
        }

        public Criteria andCertBimageIsNull() {
            addCriterion("cert_bimage is null");
            return (Criteria) this;
        }

        public Criteria andCertBimageIsNotNull() {
            addCriterion("cert_bimage is not null");
            return (Criteria) this;
        }

        public Criteria andCertBimageEqualTo(String value) {
            addCriterion("cert_bimage =", value, "certBimage");
            return (Criteria) this;
        }

        public Criteria andCertBimageNotEqualTo(String value) {
            addCriterion("cert_bimage <>", value, "certBimage");
            return (Criteria) this;
        }

        public Criteria andCertBimageGreaterThan(String value) {
            addCriterion("cert_bimage >", value, "certBimage");
            return (Criteria) this;
        }

        public Criteria andCertBimageGreaterThanOrEqualTo(String value) {
            addCriterion("cert_bimage >=", value, "certBimage");
            return (Criteria) this;
        }

        public Criteria andCertBimageLessThan(String value) {
            addCriterion("cert_bimage <", value, "certBimage");
            return (Criteria) this;
        }

        public Criteria andCertBimageLessThanOrEqualTo(String value) {
            addCriterion("cert_bimage <=", value, "certBimage");
            return (Criteria) this;
        }

        public Criteria andCertBimageLike(String value) {
            addCriterion("cert_bimage like", value, "certBimage");
            return (Criteria) this;
        }

        public Criteria andCertBimageNotLike(String value) {
            addCriterion("cert_bimage not like", value, "certBimage");
            return (Criteria) this;
        }

        public Criteria andCertBimageIn(List<String> values) {
            addCriterion("cert_bimage in", values, "certBimage");
            return (Criteria) this;
        }

        public Criteria andCertBimageNotIn(List<String> values) {
            addCriterion("cert_bimage not in", values, "certBimage");
            return (Criteria) this;
        }

        public Criteria andCertBimageBetween(String value1, String value2) {
            addCriterion("cert_bimage between", value1, value2, "certBimage");
            return (Criteria) this;
        }

        public Criteria andCertBimageNotBetween(String value1, String value2) {
            addCriterion("cert_bimage not between", value1, value2, "certBimage");
            return (Criteria) this;
        }

        public Criteria andCertInfoIsNull() {
            addCriterion("cert_info is null");
            return (Criteria) this;
        }

        public Criteria andCertInfoIsNotNull() {
            addCriterion("cert_info is not null");
            return (Criteria) this;
        }

        public Criteria andCertInfoEqualTo(String value) {
            addCriterion("cert_info =", value, "certInfo");
            return (Criteria) this;
        }

        public Criteria andCertInfoNotEqualTo(String value) {
            addCriterion("cert_info <>", value, "certInfo");
            return (Criteria) this;
        }

        public Criteria andCertInfoGreaterThan(String value) {
            addCriterion("cert_info >", value, "certInfo");
            return (Criteria) this;
        }

        public Criteria andCertInfoGreaterThanOrEqualTo(String value) {
            addCriterion("cert_info >=", value, "certInfo");
            return (Criteria) this;
        }

        public Criteria andCertInfoLessThan(String value) {
            addCriterion("cert_info <", value, "certInfo");
            return (Criteria) this;
        }

        public Criteria andCertInfoLessThanOrEqualTo(String value) {
            addCriterion("cert_info <=", value, "certInfo");
            return (Criteria) this;
        }

        public Criteria andCertInfoLike(String value) {
            addCriterion("cert_info like", value, "certInfo");
            return (Criteria) this;
        }

        public Criteria andCertInfoNotLike(String value) {
            addCriterion("cert_info not like", value, "certInfo");
            return (Criteria) this;
        }

        public Criteria andCertInfoIn(List<String> values) {
            addCriterion("cert_info in", values, "certInfo");
            return (Criteria) this;
        }

        public Criteria andCertInfoNotIn(List<String> values) {
            addCriterion("cert_info not in", values, "certInfo");
            return (Criteria) this;
        }

        public Criteria andCertInfoBetween(String value1, String value2) {
            addCriterion("cert_info between", value1, value2, "certInfo");
            return (Criteria) this;
        }

        public Criteria andCertInfoNotBetween(String value1, String value2) {
            addCriterion("cert_info not between", value1, value2, "certInfo");
            return (Criteria) this;
        }

        public Criteria andValiDateIsNull() {
            addCriterion("vali_date is null");
            return (Criteria) this;
        }

        public Criteria andValiDateIsNotNull() {
            addCriterion("vali_date is not null");
            return (Criteria) this;
        }

        public Criteria andValiDateEqualTo(String value) {
            addCriterion("vali_date =", value, "valiDate");
            return (Criteria) this;
        }

        public Criteria andValiDateNotEqualTo(String value) {
            addCriterion("vali_date <>", value, "valiDate");
            return (Criteria) this;
        }

        public Criteria andValiDateGreaterThan(String value) {
            addCriterion("vali_date >", value, "valiDate");
            return (Criteria) this;
        }

        public Criteria andValiDateGreaterThanOrEqualTo(String value) {
            addCriterion("vali_date >=", value, "valiDate");
            return (Criteria) this;
        }

        public Criteria andValiDateLessThan(String value) {
            addCriterion("vali_date <", value, "valiDate");
            return (Criteria) this;
        }

        public Criteria andValiDateLessThanOrEqualTo(String value) {
            addCriterion("vali_date <=", value, "valiDate");
            return (Criteria) this;
        }

        public Criteria andValiDateLike(String value) {
            addCriterion("vali_date like", value, "valiDate");
            return (Criteria) this;
        }

        public Criteria andValiDateNotLike(String value) {
            addCriterion("vali_date not like", value, "valiDate");
            return (Criteria) this;
        }

        public Criteria andValiDateIn(List<String> values) {
            addCriterion("vali_date in", values, "valiDate");
            return (Criteria) this;
        }

        public Criteria andValiDateNotIn(List<String> values) {
            addCriterion("vali_date not in", values, "valiDate");
            return (Criteria) this;
        }

        public Criteria andValiDateBetween(String value1, String value2) {
            addCriterion("vali_date between", value1, value2, "valiDate");
            return (Criteria) this;
        }

        public Criteria andValiDateNotBetween(String value1, String value2) {
            addCriterion("vali_date not between", value1, value2, "valiDate");
            return (Criteria) this;
        }

        public Criteria andExpDateIsNull() {
            addCriterion("exp_date is null");
            return (Criteria) this;
        }

        public Criteria andExpDateIsNotNull() {
            addCriterion("exp_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpDateEqualTo(String value) {
            addCriterion("exp_date =", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateNotEqualTo(String value) {
            addCriterion("exp_date <>", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateGreaterThan(String value) {
            addCriterion("exp_date >", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateGreaterThanOrEqualTo(String value) {
            addCriterion("exp_date >=", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateLessThan(String value) {
            addCriterion("exp_date <", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateLessThanOrEqualTo(String value) {
            addCriterion("exp_date <=", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateLike(String value) {
            addCriterion("exp_date like", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateNotLike(String value) {
            addCriterion("exp_date not like", value, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateIn(List<String> values) {
            addCriterion("exp_date in", values, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateNotIn(List<String> values) {
            addCriterion("exp_date not in", values, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateBetween(String value1, String value2) {
            addCriterion("exp_date between", value1, value2, "expDate");
            return (Criteria) this;
        }

        public Criteria andExpDateNotBetween(String value1, String value2) {
            addCriterion("exp_date not between", value1, value2, "expDate");
            return (Criteria) this;
        }

        public Criteria andJobTypeIsNull() {
            addCriterion("job_type is null");
            return (Criteria) this;
        }

        public Criteria andJobTypeIsNotNull() {
            addCriterion("job_type is not null");
            return (Criteria) this;
        }

        public Criteria andJobTypeEqualTo(String value) {
            addCriterion("job_type =", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotEqualTo(String value) {
            addCriterion("job_type <>", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeGreaterThan(String value) {
            addCriterion("job_type >", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeGreaterThanOrEqualTo(String value) {
            addCriterion("job_type >=", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeLessThan(String value) {
            addCriterion("job_type <", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeLessThanOrEqualTo(String value) {
            addCriterion("job_type <=", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeLike(String value) {
            addCriterion("job_type like", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotLike(String value) {
            addCriterion("job_type not like", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeIn(List<String> values) {
            addCriterion("job_type in", values, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotIn(List<String> values) {
            addCriterion("job_type not in", values, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeBetween(String value1, String value2) {
            addCriterion("job_type between", value1, value2, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotBetween(String value1, String value2) {
            addCriterion("job_type not between", value1, value2, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobIsNull() {
            addCriterion("job is null");
            return (Criteria) this;
        }

        public Criteria andJobIsNotNull() {
            addCriterion("job is not null");
            return (Criteria) this;
        }

        public Criteria andJobEqualTo(String value) {
            addCriterion("job =", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotEqualTo(String value) {
            addCriterion("job <>", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobGreaterThan(String value) {
            addCriterion("job >", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobGreaterThanOrEqualTo(String value) {
            addCriterion("job >=", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLessThan(String value) {
            addCriterion("job <", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLessThanOrEqualTo(String value) {
            addCriterion("job <=", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobLike(String value) {
            addCriterion("job like", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotLike(String value) {
            addCriterion("job not like", value, "job");
            return (Criteria) this;
        }

        public Criteria andJobIn(List<String> values) {
            addCriterion("job in", values, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotIn(List<String> values) {
            addCriterion("job not in", values, "job");
            return (Criteria) this;
        }

        public Criteria andJobBetween(String value1, String value2) {
            addCriterion("job between", value1, value2, "job");
            return (Criteria) this;
        }

        public Criteria andJobNotBetween(String value1, String value2) {
            addCriterion("job not between", value1, value2, "job");
            return (Criteria) this;
        }

        public Criteria andPostCodeIsNull() {
            addCriterion("post_code is null");
            return (Criteria) this;
        }

        public Criteria andPostCodeIsNotNull() {
            addCriterion("post_code is not null");
            return (Criteria) this;
        }

        public Criteria andPostCodeEqualTo(String value) {
            addCriterion("post_code =", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotEqualTo(String value) {
            addCriterion("post_code <>", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeGreaterThan(String value) {
            addCriterion("post_code >", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeGreaterThanOrEqualTo(String value) {
            addCriterion("post_code >=", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeLessThan(String value) {
            addCriterion("post_code <", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeLessThanOrEqualTo(String value) {
            addCriterion("post_code <=", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeLike(String value) {
            addCriterion("post_code like", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotLike(String value) {
            addCriterion("post_code not like", value, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeIn(List<String> values) {
            addCriterion("post_code in", values, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotIn(List<String> values) {
            addCriterion("post_code not in", values, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeBetween(String value1, String value2) {
            addCriterion("post_code between", value1, value2, "postCode");
            return (Criteria) this;
        }

        public Criteria andPostCodeNotBetween(String value1, String value2) {
            addCriterion("post_code not between", value1, value2, "postCode");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andNationalIsNull() {
            addCriterion("national is null");
            return (Criteria) this;
        }

        public Criteria andNationalIsNotNull() {
            addCriterion("national is not null");
            return (Criteria) this;
        }

        public Criteria andNationalEqualTo(String value) {
            addCriterion("national =", value, "national");
            return (Criteria) this;
        }

        public Criteria andNationalNotEqualTo(String value) {
            addCriterion("national <>", value, "national");
            return (Criteria) this;
        }

        public Criteria andNationalGreaterThan(String value) {
            addCriterion("national >", value, "national");
            return (Criteria) this;
        }

        public Criteria andNationalGreaterThanOrEqualTo(String value) {
            addCriterion("national >=", value, "national");
            return (Criteria) this;
        }

        public Criteria andNationalLessThan(String value) {
            addCriterion("national <", value, "national");
            return (Criteria) this;
        }

        public Criteria andNationalLessThanOrEqualTo(String value) {
            addCriterion("national <=", value, "national");
            return (Criteria) this;
        }

        public Criteria andNationalLike(String value) {
            addCriterion("national like", value, "national");
            return (Criteria) this;
        }

        public Criteria andNationalNotLike(String value) {
            addCriterion("national not like", value, "national");
            return (Criteria) this;
        }

        public Criteria andNationalIn(List<String> values) {
            addCriterion("national in", values, "national");
            return (Criteria) this;
        }

        public Criteria andNationalNotIn(List<String> values) {
            addCriterion("national not in", values, "national");
            return (Criteria) this;
        }

        public Criteria andNationalBetween(String value1, String value2) {
            addCriterion("national between", value1, value2, "national");
            return (Criteria) this;
        }

        public Criteria andNationalNotBetween(String value1, String value2) {
            addCriterion("national not between", value1, value2, "national");
            return (Criteria) this;
        }

        public Criteria andPhoneNoIsNull() {
            addCriterion("phone_no is null");
            return (Criteria) this;
        }

        public Criteria andPhoneNoIsNotNull() {
            addCriterion("phone_no is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneNoEqualTo(String value) {
            addCriterion("phone_no =", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoNotEqualTo(String value) {
            addCriterion("phone_no <>", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoGreaterThan(String value) {
            addCriterion("phone_no >", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoGreaterThanOrEqualTo(String value) {
            addCriterion("phone_no >=", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoLessThan(String value) {
            addCriterion("phone_no <", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoLessThanOrEqualTo(String value) {
            addCriterion("phone_no <=", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoLike(String value) {
            addCriterion("phone_no like", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoNotLike(String value) {
            addCriterion("phone_no not like", value, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoIn(List<String> values) {
            addCriterion("phone_no in", values, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoNotIn(List<String> values) {
            addCriterion("phone_no not in", values, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoBetween(String value1, String value2) {
            addCriterion("phone_no between", value1, value2, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andPhoneNoNotBetween(String value1, String value2) {
            addCriterion("phone_no not between", value1, value2, "phoneNo");
            return (Criteria) this;
        }

        public Criteria andBusiliceNoIsNull() {
            addCriterion("busiLice_no is null");
            return (Criteria) this;
        }

        public Criteria andBusiliceNoIsNotNull() {
            addCriterion("busiLice_no is not null");
            return (Criteria) this;
        }

        public Criteria andBusiliceNoEqualTo(String value) {
            addCriterion("busiLice_no =", value, "busiliceNo");
            return (Criteria) this;
        }

        public Criteria andBusiliceNoNotEqualTo(String value) {
            addCriterion("busiLice_no <>", value, "busiliceNo");
            return (Criteria) this;
        }

        public Criteria andBusiliceNoGreaterThan(String value) {
            addCriterion("busiLice_no >", value, "busiliceNo");
            return (Criteria) this;
        }

        public Criteria andBusiliceNoGreaterThanOrEqualTo(String value) {
            addCriterion("busiLice_no >=", value, "busiliceNo");
            return (Criteria) this;
        }

        public Criteria andBusiliceNoLessThan(String value) {
            addCriterion("busiLice_no <", value, "busiliceNo");
            return (Criteria) this;
        }

        public Criteria andBusiliceNoLessThanOrEqualTo(String value) {
            addCriterion("busiLice_no <=", value, "busiliceNo");
            return (Criteria) this;
        }

        public Criteria andBusiliceNoLike(String value) {
            addCriterion("busiLice_no like", value, "busiliceNo");
            return (Criteria) this;
        }

        public Criteria andBusiliceNoNotLike(String value) {
            addCriterion("busiLice_no not like", value, "busiliceNo");
            return (Criteria) this;
        }

        public Criteria andBusiliceNoIn(List<String> values) {
            addCriterion("busiLice_no in", values, "busiliceNo");
            return (Criteria) this;
        }

        public Criteria andBusiliceNoNotIn(List<String> values) {
            addCriterion("busiLice_no not in", values, "busiliceNo");
            return (Criteria) this;
        }

        public Criteria andBusiliceNoBetween(String value1, String value2) {
            addCriterion("busiLice_no between", value1, value2, "busiliceNo");
            return (Criteria) this;
        }

        public Criteria andBusiliceNoNotBetween(String value1, String value2) {
            addCriterion("busiLice_no not between", value1, value2, "busiliceNo");
            return (Criteria) this;
        }

        public Criteria andBusiliceDirIsNull() {
            addCriterion("busiLice_dir is null");
            return (Criteria) this;
        }

        public Criteria andBusiliceDirIsNotNull() {
            addCriterion("busiLice_dir is not null");
            return (Criteria) this;
        }

        public Criteria andBusiliceDirEqualTo(String value) {
            addCriterion("busiLice_dir =", value, "busiliceDir");
            return (Criteria) this;
        }

        public Criteria andBusiliceDirNotEqualTo(String value) {
            addCriterion("busiLice_dir <>", value, "busiliceDir");
            return (Criteria) this;
        }

        public Criteria andBusiliceDirGreaterThan(String value) {
            addCriterion("busiLice_dir >", value, "busiliceDir");
            return (Criteria) this;
        }

        public Criteria andBusiliceDirGreaterThanOrEqualTo(String value) {
            addCriterion("busiLice_dir >=", value, "busiliceDir");
            return (Criteria) this;
        }

        public Criteria andBusiliceDirLessThan(String value) {
            addCriterion("busiLice_dir <", value, "busiliceDir");
            return (Criteria) this;
        }

        public Criteria andBusiliceDirLessThanOrEqualTo(String value) {
            addCriterion("busiLice_dir <=", value, "busiliceDir");
            return (Criteria) this;
        }

        public Criteria andBusiliceDirLike(String value) {
            addCriterion("busiLice_dir like", value, "busiliceDir");
            return (Criteria) this;
        }

        public Criteria andBusiliceDirNotLike(String value) {
            addCriterion("busiLice_dir not like", value, "busiliceDir");
            return (Criteria) this;
        }

        public Criteria andBusiliceDirIn(List<String> values) {
            addCriterion("busiLice_dir in", values, "busiliceDir");
            return (Criteria) this;
        }

        public Criteria andBusiliceDirNotIn(List<String> values) {
            addCriterion("busiLice_dir not in", values, "busiliceDir");
            return (Criteria) this;
        }

        public Criteria andBusiliceDirBetween(String value1, String value2) {
            addCriterion("busiLice_dir between", value1, value2, "busiliceDir");
            return (Criteria) this;
        }

        public Criteria andBusiliceDirNotBetween(String value1, String value2) {
            addCriterion("busiLice_dir not between", value1, value2, "busiliceDir");
            return (Criteria) this;
        }

        public Criteria andOrgcodeNoIsNull() {
            addCriterion("orgCode_no is null");
            return (Criteria) this;
        }

        public Criteria andOrgcodeNoIsNotNull() {
            addCriterion("orgCode_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrgcodeNoEqualTo(String value) {
            addCriterion("orgCode_no =", value, "orgcodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgcodeNoNotEqualTo(String value) {
            addCriterion("orgCode_no <>", value, "orgcodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgcodeNoGreaterThan(String value) {
            addCriterion("orgCode_no >", value, "orgcodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgcodeNoGreaterThanOrEqualTo(String value) {
            addCriterion("orgCode_no >=", value, "orgcodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgcodeNoLessThan(String value) {
            addCriterion("orgCode_no <", value, "orgcodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgcodeNoLessThanOrEqualTo(String value) {
            addCriterion("orgCode_no <=", value, "orgcodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgcodeNoLike(String value) {
            addCriterion("orgCode_no like", value, "orgcodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgcodeNoNotLike(String value) {
            addCriterion("orgCode_no not like", value, "orgcodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgcodeNoIn(List<String> values) {
            addCriterion("orgCode_no in", values, "orgcodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgcodeNoNotIn(List<String> values) {
            addCriterion("orgCode_no not in", values, "orgcodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgcodeNoBetween(String value1, String value2) {
            addCriterion("orgCode_no between", value1, value2, "orgcodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgcodeNoNotBetween(String value1, String value2) {
            addCriterion("orgCode_no not between", value1, value2, "orgcodeNo");
            return (Criteria) this;
        }

        public Criteria andOrgcodeDirIsNull() {
            addCriterion("orgCode_dir is null");
            return (Criteria) this;
        }

        public Criteria andOrgcodeDirIsNotNull() {
            addCriterion("orgCode_dir is not null");
            return (Criteria) this;
        }

        public Criteria andOrgcodeDirEqualTo(String value) {
            addCriterion("orgCode_dir =", value, "orgcodeDir");
            return (Criteria) this;
        }

        public Criteria andOrgcodeDirNotEqualTo(String value) {
            addCriterion("orgCode_dir <>", value, "orgcodeDir");
            return (Criteria) this;
        }

        public Criteria andOrgcodeDirGreaterThan(String value) {
            addCriterion("orgCode_dir >", value, "orgcodeDir");
            return (Criteria) this;
        }

        public Criteria andOrgcodeDirGreaterThanOrEqualTo(String value) {
            addCriterion("orgCode_dir >=", value, "orgcodeDir");
            return (Criteria) this;
        }

        public Criteria andOrgcodeDirLessThan(String value) {
            addCriterion("orgCode_dir <", value, "orgcodeDir");
            return (Criteria) this;
        }

        public Criteria andOrgcodeDirLessThanOrEqualTo(String value) {
            addCriterion("orgCode_dir <=", value, "orgcodeDir");
            return (Criteria) this;
        }

        public Criteria andOrgcodeDirLike(String value) {
            addCriterion("orgCode_dir like", value, "orgcodeDir");
            return (Criteria) this;
        }

        public Criteria andOrgcodeDirNotLike(String value) {
            addCriterion("orgCode_dir not like", value, "orgcodeDir");
            return (Criteria) this;
        }

        public Criteria andOrgcodeDirIn(List<String> values) {
            addCriterion("orgCode_dir in", values, "orgcodeDir");
            return (Criteria) this;
        }

        public Criteria andOrgcodeDirNotIn(List<String> values) {
            addCriterion("orgCode_dir not in", values, "orgcodeDir");
            return (Criteria) this;
        }

        public Criteria andOrgcodeDirBetween(String value1, String value2) {
            addCriterion("orgCode_dir between", value1, value2, "orgcodeDir");
            return (Criteria) this;
        }

        public Criteria andOrgcodeDirNotBetween(String value1, String value2) {
            addCriterion("orgCode_dir not between", value1, value2, "orgcodeDir");
            return (Criteria) this;
        }

        public Criteria andTaxregisNoIsNull() {
            addCriterion("taxregis_no is null");
            return (Criteria) this;
        }

        public Criteria andTaxregisNoIsNotNull() {
            addCriterion("taxregis_no is not null");
            return (Criteria) this;
        }

        public Criteria andTaxregisNoEqualTo(String value) {
            addCriterion("taxregis_no =", value, "taxregisNo");
            return (Criteria) this;
        }

        public Criteria andTaxregisNoNotEqualTo(String value) {
            addCriterion("taxregis_no <>", value, "taxregisNo");
            return (Criteria) this;
        }

        public Criteria andTaxregisNoGreaterThan(String value) {
            addCriterion("taxregis_no >", value, "taxregisNo");
            return (Criteria) this;
        }

        public Criteria andTaxregisNoGreaterThanOrEqualTo(String value) {
            addCriterion("taxregis_no >=", value, "taxregisNo");
            return (Criteria) this;
        }

        public Criteria andTaxregisNoLessThan(String value) {
            addCriterion("taxregis_no <", value, "taxregisNo");
            return (Criteria) this;
        }

        public Criteria andTaxregisNoLessThanOrEqualTo(String value) {
            addCriterion("taxregis_no <=", value, "taxregisNo");
            return (Criteria) this;
        }

        public Criteria andTaxregisNoLike(String value) {
            addCriterion("taxregis_no like", value, "taxregisNo");
            return (Criteria) this;
        }

        public Criteria andTaxregisNoNotLike(String value) {
            addCriterion("taxregis_no not like", value, "taxregisNo");
            return (Criteria) this;
        }

        public Criteria andTaxregisNoIn(List<String> values) {
            addCriterion("taxregis_no in", values, "taxregisNo");
            return (Criteria) this;
        }

        public Criteria andTaxregisNoNotIn(List<String> values) {
            addCriterion("taxregis_no not in", values, "taxregisNo");
            return (Criteria) this;
        }

        public Criteria andTaxregisNoBetween(String value1, String value2) {
            addCriterion("taxregis_no between", value1, value2, "taxregisNo");
            return (Criteria) this;
        }

        public Criteria andTaxregisNoNotBetween(String value1, String value2) {
            addCriterion("taxregis_no not between", value1, value2, "taxregisNo");
            return (Criteria) this;
        }

        public Criteria andTaxregisDirIsNull() {
            addCriterion("taxregis_dir is null");
            return (Criteria) this;
        }

        public Criteria andTaxregisDirIsNotNull() {
            addCriterion("taxregis_dir is not null");
            return (Criteria) this;
        }

        public Criteria andTaxregisDirEqualTo(String value) {
            addCriterion("taxregis_dir =", value, "taxregisDir");
            return (Criteria) this;
        }

        public Criteria andTaxregisDirNotEqualTo(String value) {
            addCriterion("taxregis_dir <>", value, "taxregisDir");
            return (Criteria) this;
        }

        public Criteria andTaxregisDirGreaterThan(String value) {
            addCriterion("taxregis_dir >", value, "taxregisDir");
            return (Criteria) this;
        }

        public Criteria andTaxregisDirGreaterThanOrEqualTo(String value) {
            addCriterion("taxregis_dir >=", value, "taxregisDir");
            return (Criteria) this;
        }

        public Criteria andTaxregisDirLessThan(String value) {
            addCriterion("taxregis_dir <", value, "taxregisDir");
            return (Criteria) this;
        }

        public Criteria andTaxregisDirLessThanOrEqualTo(String value) {
            addCriterion("taxregis_dir <=", value, "taxregisDir");
            return (Criteria) this;
        }

        public Criteria andTaxregisDirLike(String value) {
            addCriterion("taxregis_dir like", value, "taxregisDir");
            return (Criteria) this;
        }

        public Criteria andTaxregisDirNotLike(String value) {
            addCriterion("taxregis_dir not like", value, "taxregisDir");
            return (Criteria) this;
        }

        public Criteria andTaxregisDirIn(List<String> values) {
            addCriterion("taxregis_dir in", values, "taxregisDir");
            return (Criteria) this;
        }

        public Criteria andTaxregisDirNotIn(List<String> values) {
            addCriterion("taxregis_dir not in", values, "taxregisDir");
            return (Criteria) this;
        }

        public Criteria andTaxregisDirBetween(String value1, String value2) {
            addCriterion("taxregis_dir between", value1, value2, "taxregisDir");
            return (Criteria) this;
        }

        public Criteria andTaxregisDirNotBetween(String value1, String value2) {
            addCriterion("taxregis_dir not between", value1, value2, "taxregisDir");
            return (Criteria) this;
        }

        public Criteria andUnisoccreCodeIsNull() {
            addCriterion("unisoccre_code is null");
            return (Criteria) this;
        }

        public Criteria andUnisoccreCodeIsNotNull() {
            addCriterion("unisoccre_code is not null");
            return (Criteria) this;
        }

        public Criteria andUnisoccreCodeEqualTo(String value) {
            addCriterion("unisoccre_code =", value, "unisoccreCode");
            return (Criteria) this;
        }

        public Criteria andUnisoccreCodeNotEqualTo(String value) {
            addCriterion("unisoccre_code <>", value, "unisoccreCode");
            return (Criteria) this;
        }

        public Criteria andUnisoccreCodeGreaterThan(String value) {
            addCriterion("unisoccre_code >", value, "unisoccreCode");
            return (Criteria) this;
        }

        public Criteria andUnisoccreCodeGreaterThanOrEqualTo(String value) {
            addCriterion("unisoccre_code >=", value, "unisoccreCode");
            return (Criteria) this;
        }

        public Criteria andUnisoccreCodeLessThan(String value) {
            addCriterion("unisoccre_code <", value, "unisoccreCode");
            return (Criteria) this;
        }

        public Criteria andUnisoccreCodeLessThanOrEqualTo(String value) {
            addCriterion("unisoccre_code <=", value, "unisoccreCode");
            return (Criteria) this;
        }

        public Criteria andUnisoccreCodeLike(String value) {
            addCriterion("unisoccre_code like", value, "unisoccreCode");
            return (Criteria) this;
        }

        public Criteria andUnisoccreCodeNotLike(String value) {
            addCriterion("unisoccre_code not like", value, "unisoccreCode");
            return (Criteria) this;
        }

        public Criteria andUnisoccreCodeIn(List<String> values) {
            addCriterion("unisoccre_code in", values, "unisoccreCode");
            return (Criteria) this;
        }

        public Criteria andUnisoccreCodeNotIn(List<String> values) {
            addCriterion("unisoccre_code not in", values, "unisoccreCode");
            return (Criteria) this;
        }

        public Criteria andUnisoccreCodeBetween(String value1, String value2) {
            addCriterion("unisoccre_code between", value1, value2, "unisoccreCode");
            return (Criteria) this;
        }

        public Criteria andUnisoccreCodeNotBetween(String value1, String value2) {
            addCriterion("unisoccre_code not between", value1, value2, "unisoccreCode");
            return (Criteria) this;
        }

        public Criteria andUnisoccreDirIsNull() {
            addCriterion("unisoccre_dir is null");
            return (Criteria) this;
        }

        public Criteria andUnisoccreDirIsNotNull() {
            addCriterion("unisoccre_dir is not null");
            return (Criteria) this;
        }

        public Criteria andUnisoccreDirEqualTo(String value) {
            addCriterion("unisoccre_dir =", value, "unisoccreDir");
            return (Criteria) this;
        }

        public Criteria andUnisoccreDirNotEqualTo(String value) {
            addCriterion("unisoccre_dir <>", value, "unisoccreDir");
            return (Criteria) this;
        }

        public Criteria andUnisoccreDirGreaterThan(String value) {
            addCriterion("unisoccre_dir >", value, "unisoccreDir");
            return (Criteria) this;
        }

        public Criteria andUnisoccreDirGreaterThanOrEqualTo(String value) {
            addCriterion("unisoccre_dir >=", value, "unisoccreDir");
            return (Criteria) this;
        }

        public Criteria andUnisoccreDirLessThan(String value) {
            addCriterion("unisoccre_dir <", value, "unisoccreDir");
            return (Criteria) this;
        }

        public Criteria andUnisoccreDirLessThanOrEqualTo(String value) {
            addCriterion("unisoccre_dir <=", value, "unisoccreDir");
            return (Criteria) this;
        }

        public Criteria andUnisoccreDirLike(String value) {
            addCriterion("unisoccre_dir like", value, "unisoccreDir");
            return (Criteria) this;
        }

        public Criteria andUnisoccreDirNotLike(String value) {
            addCriterion("unisoccre_dir not like", value, "unisoccreDir");
            return (Criteria) this;
        }

        public Criteria andUnisoccreDirIn(List<String> values) {
            addCriterion("unisoccre_dir in", values, "unisoccreDir");
            return (Criteria) this;
        }

        public Criteria andUnisoccreDirNotIn(List<String> values) {
            addCriterion("unisoccre_dir not in", values, "unisoccreDir");
            return (Criteria) this;
        }

        public Criteria andUnisoccreDirBetween(String value1, String value2) {
            addCriterion("unisoccre_dir between", value1, value2, "unisoccreDir");
            return (Criteria) this;
        }

        public Criteria andUnisoccreDirNotBetween(String value1, String value2) {
            addCriterion("unisoccre_dir not between", value1, value2, "unisoccreDir");
            return (Criteria) this;
        }

        public Criteria andBindTypeIsNull() {
            addCriterion("bind_type is null");
            return (Criteria) this;
        }

        public Criteria andBindTypeIsNotNull() {
            addCriterion("bind_type is not null");
            return (Criteria) this;
        }

        public Criteria andBindTypeEqualTo(String value) {
            addCriterion("bind_type =", value, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeNotEqualTo(String value) {
            addCriterion("bind_type <>", value, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeGreaterThan(String value) {
            addCriterion("bind_type >", value, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeGreaterThanOrEqualTo(String value) {
            addCriterion("bind_type >=", value, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeLessThan(String value) {
            addCriterion("bind_type <", value, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeLessThanOrEqualTo(String value) {
            addCriterion("bind_type <=", value, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeLike(String value) {
            addCriterion("bind_type like", value, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeNotLike(String value) {
            addCriterion("bind_type not like", value, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeIn(List<String> values) {
            addCriterion("bind_type in", values, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeNotIn(List<String> values) {
            addCriterion("bind_type not in", values, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeBetween(String value1, String value2) {
            addCriterion("bind_type between", value1, value2, "bindType");
            return (Criteria) this;
        }

        public Criteria andBindTypeNotBetween(String value1, String value2) {
            addCriterion("bind_type not between", value1, value2, "bindType");
            return (Criteria) this;
        }

        public Criteria andAccTypeIsNull() {
            addCriterion("acc_type is null");
            return (Criteria) this;
        }

        public Criteria andAccTypeIsNotNull() {
            addCriterion("acc_type is not null");
            return (Criteria) this;
        }

        public Criteria andAccTypeEqualTo(String value) {
            addCriterion("acc_type =", value, "accType");
            return (Criteria) this;
        }

        public Criteria andAccTypeNotEqualTo(String value) {
            addCriterion("acc_type <>", value, "accType");
            return (Criteria) this;
        }

        public Criteria andAccTypeGreaterThan(String value) {
            addCriterion("acc_type >", value, "accType");
            return (Criteria) this;
        }

        public Criteria andAccTypeGreaterThanOrEqualTo(String value) {
            addCriterion("acc_type >=", value, "accType");
            return (Criteria) this;
        }

        public Criteria andAccTypeLessThan(String value) {
            addCriterion("acc_type <", value, "accType");
            return (Criteria) this;
        }

        public Criteria andAccTypeLessThanOrEqualTo(String value) {
            addCriterion("acc_type <=", value, "accType");
            return (Criteria) this;
        }

        public Criteria andAccTypeLike(String value) {
            addCriterion("acc_type like", value, "accType");
            return (Criteria) this;
        }

        public Criteria andAccTypeNotLike(String value) {
            addCriterion("acc_type not like", value, "accType");
            return (Criteria) this;
        }

        public Criteria andAccTypeIn(List<String> values) {
            addCriterion("acc_type in", values, "accType");
            return (Criteria) this;
        }

        public Criteria andAccTypeNotIn(List<String> values) {
            addCriterion("acc_type not in", values, "accType");
            return (Criteria) this;
        }

        public Criteria andAccTypeBetween(String value1, String value2) {
            addCriterion("acc_type between", value1, value2, "accType");
            return (Criteria) this;
        }

        public Criteria andAccTypeNotBetween(String value1, String value2) {
            addCriterion("acc_type not between", value1, value2, "accType");
            return (Criteria) this;
        }

        public Criteria andOldbankaccountNoIsNull() {
            addCriterion("oldbankaccount_no is null");
            return (Criteria) this;
        }

        public Criteria andOldbankaccountNoIsNotNull() {
            addCriterion("oldbankaccount_no is not null");
            return (Criteria) this;
        }

        public Criteria andOldbankaccountNoEqualTo(String value) {
            addCriterion("oldbankaccount_no =", value, "oldbankaccountNo");
            return (Criteria) this;
        }

        public Criteria andOldbankaccountNoNotEqualTo(String value) {
            addCriterion("oldbankaccount_no <>", value, "oldbankaccountNo");
            return (Criteria) this;
        }

        public Criteria andOldbankaccountNoGreaterThan(String value) {
            addCriterion("oldbankaccount_no >", value, "oldbankaccountNo");
            return (Criteria) this;
        }

        public Criteria andOldbankaccountNoGreaterThanOrEqualTo(String value) {
            addCriterion("oldbankaccount_no >=", value, "oldbankaccountNo");
            return (Criteria) this;
        }

        public Criteria andOldbankaccountNoLessThan(String value) {
            addCriterion("oldbankaccount_no <", value, "oldbankaccountNo");
            return (Criteria) this;
        }

        public Criteria andOldbankaccountNoLessThanOrEqualTo(String value) {
            addCriterion("oldbankaccount_no <=", value, "oldbankaccountNo");
            return (Criteria) this;
        }

        public Criteria andOldbankaccountNoLike(String value) {
            addCriterion("oldbankaccount_no like", value, "oldbankaccountNo");
            return (Criteria) this;
        }

        public Criteria andOldbankaccountNoNotLike(String value) {
            addCriterion("oldbankaccount_no not like", value, "oldbankaccountNo");
            return (Criteria) this;
        }

        public Criteria andOldbankaccountNoIn(List<String> values) {
            addCriterion("oldbankaccount_no in", values, "oldbankaccountNo");
            return (Criteria) this;
        }

        public Criteria andOldbankaccountNoNotIn(List<String> values) {
            addCriterion("oldbankaccount_no not in", values, "oldbankaccountNo");
            return (Criteria) this;
        }

        public Criteria andOldbankaccountNoBetween(String value1, String value2) {
            addCriterion("oldbankaccount_no between", value1, value2, "oldbankaccountNo");
            return (Criteria) this;
        }

        public Criteria andOldbankaccountNoNotBetween(String value1, String value2) {
            addCriterion("oldbankaccount_no not between", value1, value2, "oldbankaccountNo");
            return (Criteria) this;
        }

        public Criteria andBankaccountNoIsNull() {
            addCriterion("bankaccount_no is null");
            return (Criteria) this;
        }

        public Criteria andBankaccountNoIsNotNull() {
            addCriterion("bankaccount_no is not null");
            return (Criteria) this;
        }

        public Criteria andBankaccountNoEqualTo(String value) {
            addCriterion("bankaccount_no =", value, "bankaccountNo");
            return (Criteria) this;
        }

        public Criteria andBankaccountNoNotEqualTo(String value) {
            addCriterion("bankaccount_no <>", value, "bankaccountNo");
            return (Criteria) this;
        }

        public Criteria andBankaccountNoGreaterThan(String value) {
            addCriterion("bankaccount_no >", value, "bankaccountNo");
            return (Criteria) this;
        }

        public Criteria andBankaccountNoGreaterThanOrEqualTo(String value) {
            addCriterion("bankaccount_no >=", value, "bankaccountNo");
            return (Criteria) this;
        }

        public Criteria andBankaccountNoLessThan(String value) {
            addCriterion("bankaccount_no <", value, "bankaccountNo");
            return (Criteria) this;
        }

        public Criteria andBankaccountNoLessThanOrEqualTo(String value) {
            addCriterion("bankaccount_no <=", value, "bankaccountNo");
            return (Criteria) this;
        }

        public Criteria andBankaccountNoLike(String value) {
            addCriterion("bankaccount_no like", value, "bankaccountNo");
            return (Criteria) this;
        }

        public Criteria andBankaccountNoNotLike(String value) {
            addCriterion("bankaccount_no not like", value, "bankaccountNo");
            return (Criteria) this;
        }

        public Criteria andBankaccountNoIn(List<String> values) {
            addCriterion("bankaccount_no in", values, "bankaccountNo");
            return (Criteria) this;
        }

        public Criteria andBankaccountNoNotIn(List<String> values) {
            addCriterion("bankaccount_no not in", values, "bankaccountNo");
            return (Criteria) this;
        }

        public Criteria andBankaccountNoBetween(String value1, String value2) {
            addCriterion("bankaccount_no between", value1, value2, "bankaccountNo");
            return (Criteria) this;
        }

        public Criteria andBankaccountNoNotBetween(String value1, String value2) {
            addCriterion("bankaccount_no not between", value1, value2, "bankaccountNo");
            return (Criteria) this;
        }

        public Criteria andBankaccountNameIsNull() {
            addCriterion("bankaccount_name is null");
            return (Criteria) this;
        }

        public Criteria andBankaccountNameIsNotNull() {
            addCriterion("bankaccount_name is not null");
            return (Criteria) this;
        }

        public Criteria andBankaccountNameEqualTo(String value) {
            addCriterion("bankaccount_name =", value, "bankaccountName");
            return (Criteria) this;
        }

        public Criteria andBankaccountNameNotEqualTo(String value) {
            addCriterion("bankaccount_name <>", value, "bankaccountName");
            return (Criteria) this;
        }

        public Criteria andBankaccountNameGreaterThan(String value) {
            addCriterion("bankaccount_name >", value, "bankaccountName");
            return (Criteria) this;
        }

        public Criteria andBankaccountNameGreaterThanOrEqualTo(String value) {
            addCriterion("bankaccount_name >=", value, "bankaccountName");
            return (Criteria) this;
        }

        public Criteria andBankaccountNameLessThan(String value) {
            addCriterion("bankaccount_name <", value, "bankaccountName");
            return (Criteria) this;
        }

        public Criteria andBankaccountNameLessThanOrEqualTo(String value) {
            addCriterion("bankaccount_name <=", value, "bankaccountName");
            return (Criteria) this;
        }

        public Criteria andBankaccountNameLike(String value) {
            addCriterion("bankaccount_name like", value, "bankaccountName");
            return (Criteria) this;
        }

        public Criteria andBankaccountNameNotLike(String value) {
            addCriterion("bankaccount_name not like", value, "bankaccountName");
            return (Criteria) this;
        }

        public Criteria andBankaccountNameIn(List<String> values) {
            addCriterion("bankaccount_name in", values, "bankaccountName");
            return (Criteria) this;
        }

        public Criteria andBankaccountNameNotIn(List<String> values) {
            addCriterion("bankaccount_name not in", values, "bankaccountName");
            return (Criteria) this;
        }

        public Criteria andBankaccountNameBetween(String value1, String value2) {
            addCriterion("bankaccount_name between", value1, value2, "bankaccountName");
            return (Criteria) this;
        }

        public Criteria andBankaccountNameNotBetween(String value1, String value2) {
            addCriterion("bankaccount_name not between", value1, value2, "bankaccountName");
            return (Criteria) this;
        }

        public Criteria andBankaccountTelnoIsNull() {
            addCriterion("bankaccount_telno is null");
            return (Criteria) this;
        }

        public Criteria andBankaccountTelnoIsNotNull() {
            addCriterion("bankaccount_telno is not null");
            return (Criteria) this;
        }

        public Criteria andBankaccountTelnoEqualTo(String value) {
            addCriterion("bankaccount_telno =", value, "bankaccountTelno");
            return (Criteria) this;
        }

        public Criteria andBankaccountTelnoNotEqualTo(String value) {
            addCriterion("bankaccount_telno <>", value, "bankaccountTelno");
            return (Criteria) this;
        }

        public Criteria andBankaccountTelnoGreaterThan(String value) {
            addCriterion("bankaccount_telno >", value, "bankaccountTelno");
            return (Criteria) this;
        }

        public Criteria andBankaccountTelnoGreaterThanOrEqualTo(String value) {
            addCriterion("bankaccount_telno >=", value, "bankaccountTelno");
            return (Criteria) this;
        }

        public Criteria andBankaccountTelnoLessThan(String value) {
            addCriterion("bankaccount_telno <", value, "bankaccountTelno");
            return (Criteria) this;
        }

        public Criteria andBankaccountTelnoLessThanOrEqualTo(String value) {
            addCriterion("bankaccount_telno <=", value, "bankaccountTelno");
            return (Criteria) this;
        }

        public Criteria andBankaccountTelnoLike(String value) {
            addCriterion("bankaccount_telno like", value, "bankaccountTelno");
            return (Criteria) this;
        }

        public Criteria andBankaccountTelnoNotLike(String value) {
            addCriterion("bankaccount_telno not like", value, "bankaccountTelno");
            return (Criteria) this;
        }

        public Criteria andBankaccountTelnoIn(List<String> values) {
            addCriterion("bankaccount_telno in", values, "bankaccountTelno");
            return (Criteria) this;
        }

        public Criteria andBankaccountTelnoNotIn(List<String> values) {
            addCriterion("bankaccount_telno not in", values, "bankaccountTelno");
            return (Criteria) this;
        }

        public Criteria andBankaccountTelnoBetween(String value1, String value2) {
            addCriterion("bankaccount_telno between", value1, value2, "bankaccountTelno");
            return (Criteria) this;
        }

        public Criteria andBankaccountTelnoNotBetween(String value1, String value2) {
            addCriterion("bankaccount_telno not between", value1, value2, "bankaccountTelno");
            return (Criteria) this;
        }

        public Criteria andAccNoIsNull() {
            addCriterion("acc_no is null");
            return (Criteria) this;
        }

        public Criteria andAccNoIsNotNull() {
            addCriterion("acc_no is not null");
            return (Criteria) this;
        }

        public Criteria andAccNoEqualTo(String value) {
            addCriterion("acc_no =", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoNotEqualTo(String value) {
            addCriterion("acc_no <>", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoGreaterThan(String value) {
            addCriterion("acc_no >", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoGreaterThanOrEqualTo(String value) {
            addCriterion("acc_no >=", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoLessThan(String value) {
            addCriterion("acc_no <", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoLessThanOrEqualTo(String value) {
            addCriterion("acc_no <=", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoLike(String value) {
            addCriterion("acc_no like", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoNotLike(String value) {
            addCriterion("acc_no not like", value, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoIn(List<String> values) {
            addCriterion("acc_no in", values, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoNotIn(List<String> values) {
            addCriterion("acc_no not in", values, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoBetween(String value1, String value2) {
            addCriterion("acc_no between", value1, value2, "accNo");
            return (Criteria) this;
        }

        public Criteria andAccNoNotBetween(String value1, String value2) {
            addCriterion("acc_no not between", value1, value2, "accNo");
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