package com.heepay.tpds.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TpdsMerchantExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TpdsMerchantExample() {
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

        public Criteria andMerchantIdIsNull() {
            addCriterion("merchant_id is null");
            return (Criteria) this;
        }

        public Criteria andMerchantIdIsNotNull() {
            addCriterion("merchant_id is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantIdEqualTo(Integer value) {
            addCriterion("merchant_id =", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotEqualTo(Integer value) {
            addCriterion("merchant_id <>", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdGreaterThan(Integer value) {
            addCriterion("merchant_id >", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("merchant_id >=", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdLessThan(Integer value) {
            addCriterion("merchant_id <", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdLessThanOrEqualTo(Integer value) {
            addCriterion("merchant_id <=", value, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdIn(List<Integer> values) {
            addCriterion("merchant_id in", values, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotIn(List<Integer> values) {
            addCriterion("merchant_id not in", values, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdBetween(Integer value1, Integer value2) {
            addCriterion("merchant_id between", value1, value2, "merchantId");
            return (Criteria) this;
        }

        public Criteria andMerchantIdNotBetween(Integer value1, Integer value2) {
            addCriterion("merchant_id not between", value1, value2, "merchantId");
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

        public Criteria andLoginNameIsNull() {
            addCriterion("login_name is null");
            return (Criteria) this;
        }

        public Criteria andLoginNameIsNotNull() {
            addCriterion("login_name is not null");
            return (Criteria) this;
        }

        public Criteria andLoginNameEqualTo(String value) {
            addCriterion("login_name =", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotEqualTo(String value) {
            addCriterion("login_name <>", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameGreaterThan(String value) {
            addCriterion("login_name >", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameGreaterThanOrEqualTo(String value) {
            addCriterion("login_name >=", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLessThan(String value) {
            addCriterion("login_name <", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLessThanOrEqualTo(String value) {
            addCriterion("login_name <=", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameLike(String value) {
            addCriterion("login_name like", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotLike(String value) {
            addCriterion("login_name not like", value, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameIn(List<String> values) {
            addCriterion("login_name in", values, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotIn(List<String> values) {
            addCriterion("login_name not in", values, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameBetween(String value1, String value2) {
            addCriterion("login_name between", value1, value2, "loginName");
            return (Criteria) this;
        }

        public Criteria andLoginNameNotBetween(String value1, String value2) {
            addCriterion("login_name not between", value1, value2, "loginName");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andContryNameIsNull() {
            addCriterion("contry_name is null");
            return (Criteria) this;
        }

        public Criteria andContryNameIsNotNull() {
            addCriterion("contry_name is not null");
            return (Criteria) this;
        }

        public Criteria andContryNameEqualTo(String value) {
            addCriterion("contry_name =", value, "contryName");
            return (Criteria) this;
        }

        public Criteria andContryNameNotEqualTo(String value) {
            addCriterion("contry_name <>", value, "contryName");
            return (Criteria) this;
        }

        public Criteria andContryNameGreaterThan(String value) {
            addCriterion("contry_name >", value, "contryName");
            return (Criteria) this;
        }

        public Criteria andContryNameGreaterThanOrEqualTo(String value) {
            addCriterion("contry_name >=", value, "contryName");
            return (Criteria) this;
        }

        public Criteria andContryNameLessThan(String value) {
            addCriterion("contry_name <", value, "contryName");
            return (Criteria) this;
        }

        public Criteria andContryNameLessThanOrEqualTo(String value) {
            addCriterion("contry_name <=", value, "contryName");
            return (Criteria) this;
        }

        public Criteria andContryNameLike(String value) {
            addCriterion("contry_name like", value, "contryName");
            return (Criteria) this;
        }

        public Criteria andContryNameNotLike(String value) {
            addCriterion("contry_name not like", value, "contryName");
            return (Criteria) this;
        }

        public Criteria andContryNameIn(List<String> values) {
            addCriterion("contry_name in", values, "contryName");
            return (Criteria) this;
        }

        public Criteria andContryNameNotIn(List<String> values) {
            addCriterion("contry_name not in", values, "contryName");
            return (Criteria) this;
        }

        public Criteria andContryNameBetween(String value1, String value2) {
            addCriterion("contry_name between", value1, value2, "contryName");
            return (Criteria) this;
        }

        public Criteria andContryNameNotBetween(String value1, String value2) {
            addCriterion("contry_name not between", value1, value2, "contryName");
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

        public Criteria andCertificateTypeIsNull() {
            addCriterion("certificate_type is null");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeIsNotNull() {
            addCriterion("certificate_type is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeEqualTo(String value) {
            addCriterion("certificate_type =", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeNotEqualTo(String value) {
            addCriterion("certificate_type <>", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeGreaterThan(String value) {
            addCriterion("certificate_type >", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeGreaterThanOrEqualTo(String value) {
            addCriterion("certificate_type >=", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeLessThan(String value) {
            addCriterion("certificate_type <", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeLessThanOrEqualTo(String value) {
            addCriterion("certificate_type <=", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeLike(String value) {
            addCriterion("certificate_type like", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeNotLike(String value) {
            addCriterion("certificate_type not like", value, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeIn(List<String> values) {
            addCriterion("certificate_type in", values, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeNotIn(List<String> values) {
            addCriterion("certificate_type not in", values, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeBetween(String value1, String value2) {
            addCriterion("certificate_type between", value1, value2, "certificateType");
            return (Criteria) this;
        }

        public Criteria andCertificateTypeNotBetween(String value1, String value2) {
            addCriterion("certificate_type not between", value1, value2, "certificateType");
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

        public Criteria andProvinceCodeIsNull() {
            addCriterion("province_code is null");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeIsNotNull() {
            addCriterion("province_code is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeEqualTo(String value) {
            addCriterion("province_code =", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotEqualTo(String value) {
            addCriterion("province_code <>", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeGreaterThan(String value) {
            addCriterion("province_code >", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeGreaterThanOrEqualTo(String value) {
            addCriterion("province_code >=", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeLessThan(String value) {
            addCriterion("province_code <", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeLessThanOrEqualTo(String value) {
            addCriterion("province_code <=", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeLike(String value) {
            addCriterion("province_code like", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotLike(String value) {
            addCriterion("province_code not like", value, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeIn(List<String> values) {
            addCriterion("province_code in", values, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotIn(List<String> values) {
            addCriterion("province_code not in", values, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeBetween(String value1, String value2) {
            addCriterion("province_code between", value1, value2, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceCodeNotBetween(String value1, String value2) {
            addCriterion("province_code not between", value1, value2, "provinceCode");
            return (Criteria) this;
        }

        public Criteria andProvinceNameIsNull() {
            addCriterion("province_name is null");
            return (Criteria) this;
        }

        public Criteria andProvinceNameIsNotNull() {
            addCriterion("province_name is not null");
            return (Criteria) this;
        }

        public Criteria andProvinceNameEqualTo(String value) {
            addCriterion("province_name =", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameNotEqualTo(String value) {
            addCriterion("province_name <>", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameGreaterThan(String value) {
            addCriterion("province_name >", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameGreaterThanOrEqualTo(String value) {
            addCriterion("province_name >=", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameLessThan(String value) {
            addCriterion("province_name <", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameLessThanOrEqualTo(String value) {
            addCriterion("province_name <=", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameLike(String value) {
            addCriterion("province_name like", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameNotLike(String value) {
            addCriterion("province_name not like", value, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameIn(List<String> values) {
            addCriterion("province_name in", values, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameNotIn(List<String> values) {
            addCriterion("province_name not in", values, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameBetween(String value1, String value2) {
            addCriterion("province_name between", value1, value2, "provinceName");
            return (Criteria) this;
        }

        public Criteria andProvinceNameNotBetween(String value1, String value2) {
            addCriterion("province_name not between", value1, value2, "provinceName");
            return (Criteria) this;
        }

        public Criteria andCityCodeIsNull() {
            addCriterion("city_code is null");
            return (Criteria) this;
        }

        public Criteria andCityCodeIsNotNull() {
            addCriterion("city_code is not null");
            return (Criteria) this;
        }

        public Criteria andCityCodeEqualTo(String value) {
            addCriterion("city_code =", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotEqualTo(String value) {
            addCriterion("city_code <>", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeGreaterThan(String value) {
            addCriterion("city_code >", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeGreaterThanOrEqualTo(String value) {
            addCriterion("city_code >=", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLessThan(String value) {
            addCriterion("city_code <", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLessThanOrEqualTo(String value) {
            addCriterion("city_code <=", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeLike(String value) {
            addCriterion("city_code like", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotLike(String value) {
            addCriterion("city_code not like", value, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeIn(List<String> values) {
            addCriterion("city_code in", values, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotIn(List<String> values) {
            addCriterion("city_code not in", values, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeBetween(String value1, String value2) {
            addCriterion("city_code between", value1, value2, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityCodeNotBetween(String value1, String value2) {
            addCriterion("city_code not between", value1, value2, "cityCode");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNull() {
            addCriterion("city_name is null");
            return (Criteria) this;
        }

        public Criteria andCityNameIsNotNull() {
            addCriterion("city_name is not null");
            return (Criteria) this;
        }

        public Criteria andCityNameEqualTo(String value) {
            addCriterion("city_name =", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotEqualTo(String value) {
            addCriterion("city_name <>", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThan(String value) {
            addCriterion("city_name >", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameGreaterThanOrEqualTo(String value) {
            addCriterion("city_name >=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThan(String value) {
            addCriterion("city_name <", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLessThanOrEqualTo(String value) {
            addCriterion("city_name <=", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameLike(String value) {
            addCriterion("city_name like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotLike(String value) {
            addCriterion("city_name not like", value, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameIn(List<String> values) {
            addCriterion("city_name in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotIn(List<String> values) {
            addCriterion("city_name not in", values, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameBetween(String value1, String value2) {
            addCriterion("city_name between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andCityNameNotBetween(String value1, String value2) {
            addCriterion("city_name not between", value1, value2, "cityName");
            return (Criteria) this;
        }

        public Criteria andCountyCodeIsNull() {
            addCriterion("county_code is null");
            return (Criteria) this;
        }

        public Criteria andCountyCodeIsNotNull() {
            addCriterion("county_code is not null");
            return (Criteria) this;
        }

        public Criteria andCountyCodeEqualTo(String value) {
            addCriterion("county_code =", value, "countyCode");
            return (Criteria) this;
        }

        public Criteria andCountyCodeNotEqualTo(String value) {
            addCriterion("county_code <>", value, "countyCode");
            return (Criteria) this;
        }

        public Criteria andCountyCodeGreaterThan(String value) {
            addCriterion("county_code >", value, "countyCode");
            return (Criteria) this;
        }

        public Criteria andCountyCodeGreaterThanOrEqualTo(String value) {
            addCriterion("county_code >=", value, "countyCode");
            return (Criteria) this;
        }

        public Criteria andCountyCodeLessThan(String value) {
            addCriterion("county_code <", value, "countyCode");
            return (Criteria) this;
        }

        public Criteria andCountyCodeLessThanOrEqualTo(String value) {
            addCriterion("county_code <=", value, "countyCode");
            return (Criteria) this;
        }

        public Criteria andCountyCodeLike(String value) {
            addCriterion("county_code like", value, "countyCode");
            return (Criteria) this;
        }

        public Criteria andCountyCodeNotLike(String value) {
            addCriterion("county_code not like", value, "countyCode");
            return (Criteria) this;
        }

        public Criteria andCountyCodeIn(List<String> values) {
            addCriterion("county_code in", values, "countyCode");
            return (Criteria) this;
        }

        public Criteria andCountyCodeNotIn(List<String> values) {
            addCriterion("county_code not in", values, "countyCode");
            return (Criteria) this;
        }

        public Criteria andCountyCodeBetween(String value1, String value2) {
            addCriterion("county_code between", value1, value2, "countyCode");
            return (Criteria) this;
        }

        public Criteria andCountyCodeNotBetween(String value1, String value2) {
            addCriterion("county_code not between", value1, value2, "countyCode");
            return (Criteria) this;
        }

        public Criteria andCountyNameIsNull() {
            addCriterion("county_name is null");
            return (Criteria) this;
        }

        public Criteria andCountyNameIsNotNull() {
            addCriterion("county_name is not null");
            return (Criteria) this;
        }

        public Criteria andCountyNameEqualTo(String value) {
            addCriterion("county_name =", value, "countyName");
            return (Criteria) this;
        }

        public Criteria andCountyNameNotEqualTo(String value) {
            addCriterion("county_name <>", value, "countyName");
            return (Criteria) this;
        }

        public Criteria andCountyNameGreaterThan(String value) {
            addCriterion("county_name >", value, "countyName");
            return (Criteria) this;
        }

        public Criteria andCountyNameGreaterThanOrEqualTo(String value) {
            addCriterion("county_name >=", value, "countyName");
            return (Criteria) this;
        }

        public Criteria andCountyNameLessThan(String value) {
            addCriterion("county_name <", value, "countyName");
            return (Criteria) this;
        }

        public Criteria andCountyNameLessThanOrEqualTo(String value) {
            addCriterion("county_name <=", value, "countyName");
            return (Criteria) this;
        }

        public Criteria andCountyNameLike(String value) {
            addCriterion("county_name like", value, "countyName");
            return (Criteria) this;
        }

        public Criteria andCountyNameNotLike(String value) {
            addCriterion("county_name not like", value, "countyName");
            return (Criteria) this;
        }

        public Criteria andCountyNameIn(List<String> values) {
            addCriterion("county_name in", values, "countyName");
            return (Criteria) this;
        }

        public Criteria andCountyNameNotIn(List<String> values) {
            addCriterion("county_name not in", values, "countyName");
            return (Criteria) this;
        }

        public Criteria andCountyNameBetween(String value1, String value2) {
            addCriterion("county_name between", value1, value2, "countyName");
            return (Criteria) this;
        }

        public Criteria andCountyNameNotBetween(String value1, String value2) {
            addCriterion("county_name not between", value1, value2, "countyName");
            return (Criteria) this;
        }

        public Criteria andBusinessAddressIsNull() {
            addCriterion("business_address is null");
            return (Criteria) this;
        }

        public Criteria andBusinessAddressIsNotNull() {
            addCriterion("business_address is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessAddressEqualTo(String value) {
            addCriterion("business_address =", value, "businessAddress");
            return (Criteria) this;
        }

        public Criteria andBusinessAddressNotEqualTo(String value) {
            addCriterion("business_address <>", value, "businessAddress");
            return (Criteria) this;
        }

        public Criteria andBusinessAddressGreaterThan(String value) {
            addCriterion("business_address >", value, "businessAddress");
            return (Criteria) this;
        }

        public Criteria andBusinessAddressGreaterThanOrEqualTo(String value) {
            addCriterion("business_address >=", value, "businessAddress");
            return (Criteria) this;
        }

        public Criteria andBusinessAddressLessThan(String value) {
            addCriterion("business_address <", value, "businessAddress");
            return (Criteria) this;
        }

        public Criteria andBusinessAddressLessThanOrEqualTo(String value) {
            addCriterion("business_address <=", value, "businessAddress");
            return (Criteria) this;
        }

        public Criteria andBusinessAddressLike(String value) {
            addCriterion("business_address like", value, "businessAddress");
            return (Criteria) this;
        }

        public Criteria andBusinessAddressNotLike(String value) {
            addCriterion("business_address not like", value, "businessAddress");
            return (Criteria) this;
        }

        public Criteria andBusinessAddressIn(List<String> values) {
            addCriterion("business_address in", values, "businessAddress");
            return (Criteria) this;
        }

        public Criteria andBusinessAddressNotIn(List<String> values) {
            addCriterion("business_address not in", values, "businessAddress");
            return (Criteria) this;
        }

        public Criteria andBusinessAddressBetween(String value1, String value2) {
            addCriterion("business_address between", value1, value2, "businessAddress");
            return (Criteria) this;
        }

        public Criteria andBusinessAddressNotBetween(String value1, String value2) {
            addCriterion("business_address not between", value1, value2, "businessAddress");
            return (Criteria) this;
        }

        public Criteria andWebsiteIsNull() {
            addCriterion("website is null");
            return (Criteria) this;
        }

        public Criteria andWebsiteIsNotNull() {
            addCriterion("website is not null");
            return (Criteria) this;
        }

        public Criteria andWebsiteEqualTo(String value) {
            addCriterion("website =", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteNotEqualTo(String value) {
            addCriterion("website <>", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteGreaterThan(String value) {
            addCriterion("website >", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteGreaterThanOrEqualTo(String value) {
            addCriterion("website >=", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteLessThan(String value) {
            addCriterion("website <", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteLessThanOrEqualTo(String value) {
            addCriterion("website <=", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteLike(String value) {
            addCriterion("website like", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteNotLike(String value) {
            addCriterion("website not like", value, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteIn(List<String> values) {
            addCriterion("website in", values, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteNotIn(List<String> values) {
            addCriterion("website not in", values, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteBetween(String value1, String value2) {
            addCriterion("website between", value1, value2, "website");
            return (Criteria) this;
        }

        public Criteria andWebsiteNotBetween(String value1, String value2) {
            addCriterion("website not between", value1, value2, "website");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNoIsNull() {
            addCriterion("business_license_no is null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNoIsNotNull() {
            addCriterion("business_license_no is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNoEqualTo(String value) {
            addCriterion("business_license_no =", value, "businessLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNoNotEqualTo(String value) {
            addCriterion("business_license_no <>", value, "businessLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNoGreaterThan(String value) {
            addCriterion("business_license_no >", value, "businessLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNoGreaterThanOrEqualTo(String value) {
            addCriterion("business_license_no >=", value, "businessLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNoLessThan(String value) {
            addCriterion("business_license_no <", value, "businessLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNoLessThanOrEqualTo(String value) {
            addCriterion("business_license_no <=", value, "businessLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNoLike(String value) {
            addCriterion("business_license_no like", value, "businessLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNoNotLike(String value) {
            addCriterion("business_license_no not like", value, "businessLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNoIn(List<String> values) {
            addCriterion("business_license_no in", values, "businessLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNoNotIn(List<String> values) {
            addCriterion("business_license_no not in", values, "businessLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNoBetween(String value1, String value2) {
            addCriterion("business_license_no between", value1, value2, "businessLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNoNotBetween(String value1, String value2) {
            addCriterion("business_license_no not between", value1, value2, "businessLicenseNo");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseEndTimeIsNull() {
            addCriterion("business_license_end_time is null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseEndTimeIsNotNull() {
            addCriterion("business_license_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseEndTimeEqualTo(Date value) {
            addCriterion("business_license_end_time =", value, "businessLicenseEndTime");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseEndTimeNotEqualTo(Date value) {
            addCriterion("business_license_end_time <>", value, "businessLicenseEndTime");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseEndTimeGreaterThan(Date value) {
            addCriterion("business_license_end_time >", value, "businessLicenseEndTime");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("business_license_end_time >=", value, "businessLicenseEndTime");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseEndTimeLessThan(Date value) {
            addCriterion("business_license_end_time <", value, "businessLicenseEndTime");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("business_license_end_time <=", value, "businessLicenseEndTime");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseEndTimeIn(List<Date> values) {
            addCriterion("business_license_end_time in", values, "businessLicenseEndTime");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseEndTimeNotIn(List<Date> values) {
            addCriterion("business_license_end_time not in", values, "businessLicenseEndTime");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseEndTimeBetween(Date value1, Date value2) {
            addCriterion("business_license_end_time between", value1, value2, "businessLicenseEndTime");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("business_license_end_time not between", value1, value2, "businessLicenseEndTime");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeIsNull() {
            addCriterion("organization_code is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeIsNotNull() {
            addCriterion("organization_code is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeEqualTo(String value) {
            addCriterion("organization_code =", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeNotEqualTo(String value) {
            addCriterion("organization_code <>", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeGreaterThan(String value) {
            addCriterion("organization_code >", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeGreaterThanOrEqualTo(String value) {
            addCriterion("organization_code >=", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeLessThan(String value) {
            addCriterion("organization_code <", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeLessThanOrEqualTo(String value) {
            addCriterion("organization_code <=", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeLike(String value) {
            addCriterion("organization_code like", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeNotLike(String value) {
            addCriterion("organization_code not like", value, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeIn(List<String> values) {
            addCriterion("organization_code in", values, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeNotIn(List<String> values) {
            addCriterion("organization_code not in", values, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeBetween(String value1, String value2) {
            addCriterion("organization_code between", value1, value2, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeNotBetween(String value1, String value2) {
            addCriterion("organization_code not between", value1, value2, "organizationCode");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNoIsNull() {
            addCriterion("tax_registration_certificate_no is null");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNoIsNotNull() {
            addCriterion("tax_registration_certificate_no is not null");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNoEqualTo(String value) {
            addCriterion("tax_registration_certificate_no =", value, "taxRegistrationCertificateNo");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNoNotEqualTo(String value) {
            addCriterion("tax_registration_certificate_no <>", value, "taxRegistrationCertificateNo");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNoGreaterThan(String value) {
            addCriterion("tax_registration_certificate_no >", value, "taxRegistrationCertificateNo");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNoGreaterThanOrEqualTo(String value) {
            addCriterion("tax_registration_certificate_no >=", value, "taxRegistrationCertificateNo");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNoLessThan(String value) {
            addCriterion("tax_registration_certificate_no <", value, "taxRegistrationCertificateNo");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNoLessThanOrEqualTo(String value) {
            addCriterion("tax_registration_certificate_no <=", value, "taxRegistrationCertificateNo");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNoLike(String value) {
            addCriterion("tax_registration_certificate_no like", value, "taxRegistrationCertificateNo");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNoNotLike(String value) {
            addCriterion("tax_registration_certificate_no not like", value, "taxRegistrationCertificateNo");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNoIn(List<String> values) {
            addCriterion("tax_registration_certificate_no in", values, "taxRegistrationCertificateNo");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNoNotIn(List<String> values) {
            addCriterion("tax_registration_certificate_no not in", values, "taxRegistrationCertificateNo");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNoBetween(String value1, String value2) {
            addCriterion("tax_registration_certificate_no between", value1, value2, "taxRegistrationCertificateNo");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNoNotBetween(String value1, String value2) {
            addCriterion("tax_registration_certificate_no not between", value1, value2, "taxRegistrationCertificateNo");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeIsNull() {
            addCriterion("business_scope is null");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeIsNotNull() {
            addCriterion("business_scope is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeEqualTo(String value) {
            addCriterion("business_scope =", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeNotEqualTo(String value) {
            addCriterion("business_scope <>", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeGreaterThan(String value) {
            addCriterion("business_scope >", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeGreaterThanOrEqualTo(String value) {
            addCriterion("business_scope >=", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeLessThan(String value) {
            addCriterion("business_scope <", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeLessThanOrEqualTo(String value) {
            addCriterion("business_scope <=", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeLike(String value) {
            addCriterion("business_scope like", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeNotLike(String value) {
            addCriterion("business_scope not like", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeIn(List<String> values) {
            addCriterion("business_scope in", values, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeNotIn(List<String> values) {
            addCriterion("business_scope not in", values, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeBetween(String value1, String value2) {
            addCriterion("business_scope between", value1, value2, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeNotBetween(String value1, String value2) {
            addCriterion("business_scope not between", value1, value2, "businessScope");
            return (Criteria) this;
        }

        public Criteria andLlegalpersonIsNull() {
            addCriterion("llegalperson is null");
            return (Criteria) this;
        }

        public Criteria andLlegalpersonIsNotNull() {
            addCriterion("llegalperson is not null");
            return (Criteria) this;
        }

        public Criteria andLlegalpersonEqualTo(String value) {
            addCriterion("llegalperson =", value, "llegalperson");
            return (Criteria) this;
        }

        public Criteria andLlegalpersonNotEqualTo(String value) {
            addCriterion("llegalperson <>", value, "llegalperson");
            return (Criteria) this;
        }

        public Criteria andLlegalpersonGreaterThan(String value) {
            addCriterion("llegalperson >", value, "llegalperson");
            return (Criteria) this;
        }

        public Criteria andLlegalpersonGreaterThanOrEqualTo(String value) {
            addCriterion("llegalperson >=", value, "llegalperson");
            return (Criteria) this;
        }

        public Criteria andLlegalpersonLessThan(String value) {
            addCriterion("llegalperson <", value, "llegalperson");
            return (Criteria) this;
        }

        public Criteria andLlegalpersonLessThanOrEqualTo(String value) {
            addCriterion("llegalperson <=", value, "llegalperson");
            return (Criteria) this;
        }

        public Criteria andLlegalpersonLike(String value) {
            addCriterion("llegalperson like", value, "llegalperson");
            return (Criteria) this;
        }

        public Criteria andLlegalpersonNotLike(String value) {
            addCriterion("llegalperson not like", value, "llegalperson");
            return (Criteria) this;
        }

        public Criteria andLlegalpersonIn(List<String> values) {
            addCriterion("llegalperson in", values, "llegalperson");
            return (Criteria) this;
        }

        public Criteria andLlegalpersonNotIn(List<String> values) {
            addCriterion("llegalperson not in", values, "llegalperson");
            return (Criteria) this;
        }

        public Criteria andLlegalpersonBetween(String value1, String value2) {
            addCriterion("llegalperson between", value1, value2, "llegalperson");
            return (Criteria) this;
        }

        public Criteria andLlegalpersonNotBetween(String value1, String value2) {
            addCriterion("llegalperson not between", value1, value2, "llegalperson");
            return (Criteria) this;
        }

        public Criteria andLegalIdcardIsNull() {
            addCriterion("legal_idcard is null");
            return (Criteria) this;
        }

        public Criteria andLegalIdcardIsNotNull() {
            addCriterion("legal_idcard is not null");
            return (Criteria) this;
        }

        public Criteria andLegalIdcardEqualTo(String value) {
            addCriterion("legal_idcard =", value, "legalIdcard");
            return (Criteria) this;
        }

        public Criteria andLegalIdcardNotEqualTo(String value) {
            addCriterion("legal_idcard <>", value, "legalIdcard");
            return (Criteria) this;
        }

        public Criteria andLegalIdcardGreaterThan(String value) {
            addCriterion("legal_idcard >", value, "legalIdcard");
            return (Criteria) this;
        }

        public Criteria andLegalIdcardGreaterThanOrEqualTo(String value) {
            addCriterion("legal_idcard >=", value, "legalIdcard");
            return (Criteria) this;
        }

        public Criteria andLegalIdcardLessThan(String value) {
            addCriterion("legal_idcard <", value, "legalIdcard");
            return (Criteria) this;
        }

        public Criteria andLegalIdcardLessThanOrEqualTo(String value) {
            addCriterion("legal_idcard <=", value, "legalIdcard");
            return (Criteria) this;
        }

        public Criteria andLegalIdcardLike(String value) {
            addCriterion("legal_idcard like", value, "legalIdcard");
            return (Criteria) this;
        }

        public Criteria andLegalIdcardNotLike(String value) {
            addCriterion("legal_idcard not like", value, "legalIdcard");
            return (Criteria) this;
        }

        public Criteria andLegalIdcardIn(List<String> values) {
            addCriterion("legal_idcard in", values, "legalIdcard");
            return (Criteria) this;
        }

        public Criteria andLegalIdcardNotIn(List<String> values) {
            addCriterion("legal_idcard not in", values, "legalIdcard");
            return (Criteria) this;
        }

        public Criteria andLegalIdcardBetween(String value1, String value2) {
            addCriterion("legal_idcard between", value1, value2, "legalIdcard");
            return (Criteria) this;
        }

        public Criteria andLegalIdcardNotBetween(String value1, String value2) {
            addCriterion("legal_idcard not between", value1, value2, "legalIdcard");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateEndTimeIsNull() {
            addCriterion("legal_certificate_end_time is null");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateEndTimeIsNotNull() {
            addCriterion("legal_certificate_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateEndTimeEqualTo(Date value) {
            addCriterion("legal_certificate_end_time =", value, "legalCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateEndTimeNotEqualTo(Date value) {
            addCriterion("legal_certificate_end_time <>", value, "legalCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateEndTimeGreaterThan(Date value) {
            addCriterion("legal_certificate_end_time >", value, "legalCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("legal_certificate_end_time >=", value, "legalCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateEndTimeLessThan(Date value) {
            addCriterion("legal_certificate_end_time <", value, "legalCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("legal_certificate_end_time <=", value, "legalCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateEndTimeIn(List<Date> values) {
            addCriterion("legal_certificate_end_time in", values, "legalCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateEndTimeNotIn(List<Date> values) {
            addCriterion("legal_certificate_end_time not in", values, "legalCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateEndTimeBetween(Date value1, Date value2) {
            addCriterion("legal_certificate_end_time between", value1, value2, "legalCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("legal_certificate_end_time not between", value1, value2, "legalCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andContactorIsNull() {
            addCriterion("contactor is null");
            return (Criteria) this;
        }

        public Criteria andContactorIsNotNull() {
            addCriterion("contactor is not null");
            return (Criteria) this;
        }

        public Criteria andContactorEqualTo(String value) {
            addCriterion("contactor =", value, "contactor");
            return (Criteria) this;
        }

        public Criteria andContactorNotEqualTo(String value) {
            addCriterion("contactor <>", value, "contactor");
            return (Criteria) this;
        }

        public Criteria andContactorGreaterThan(String value) {
            addCriterion("contactor >", value, "contactor");
            return (Criteria) this;
        }

        public Criteria andContactorGreaterThanOrEqualTo(String value) {
            addCriterion("contactor >=", value, "contactor");
            return (Criteria) this;
        }

        public Criteria andContactorLessThan(String value) {
            addCriterion("contactor <", value, "contactor");
            return (Criteria) this;
        }

        public Criteria andContactorLessThanOrEqualTo(String value) {
            addCriterion("contactor <=", value, "contactor");
            return (Criteria) this;
        }

        public Criteria andContactorLike(String value) {
            addCriterion("contactor like", value, "contactor");
            return (Criteria) this;
        }

        public Criteria andContactorNotLike(String value) {
            addCriterion("contactor not like", value, "contactor");
            return (Criteria) this;
        }

        public Criteria andContactorIn(List<String> values) {
            addCriterion("contactor in", values, "contactor");
            return (Criteria) this;
        }

        public Criteria andContactorNotIn(List<String> values) {
            addCriterion("contactor not in", values, "contactor");
            return (Criteria) this;
        }

        public Criteria andContactorBetween(String value1, String value2) {
            addCriterion("contactor between", value1, value2, "contactor");
            return (Criteria) this;
        }

        public Criteria andContactorNotBetween(String value1, String value2) {
            addCriterion("contactor not between", value1, value2, "contactor");
            return (Criteria) this;
        }

        public Criteria andContactorIdcardNoIsNull() {
            addCriterion("contactor_idcard_no is null");
            return (Criteria) this;
        }

        public Criteria andContactorIdcardNoIsNotNull() {
            addCriterion("contactor_idcard_no is not null");
            return (Criteria) this;
        }

        public Criteria andContactorIdcardNoEqualTo(String value) {
            addCriterion("contactor_idcard_no =", value, "contactorIdcardNo");
            return (Criteria) this;
        }

        public Criteria andContactorIdcardNoNotEqualTo(String value) {
            addCriterion("contactor_idcard_no <>", value, "contactorIdcardNo");
            return (Criteria) this;
        }

        public Criteria andContactorIdcardNoGreaterThan(String value) {
            addCriterion("contactor_idcard_no >", value, "contactorIdcardNo");
            return (Criteria) this;
        }

        public Criteria andContactorIdcardNoGreaterThanOrEqualTo(String value) {
            addCriterion("contactor_idcard_no >=", value, "contactorIdcardNo");
            return (Criteria) this;
        }

        public Criteria andContactorIdcardNoLessThan(String value) {
            addCriterion("contactor_idcard_no <", value, "contactorIdcardNo");
            return (Criteria) this;
        }

        public Criteria andContactorIdcardNoLessThanOrEqualTo(String value) {
            addCriterion("contactor_idcard_no <=", value, "contactorIdcardNo");
            return (Criteria) this;
        }

        public Criteria andContactorIdcardNoLike(String value) {
            addCriterion("contactor_idcard_no like", value, "contactorIdcardNo");
            return (Criteria) this;
        }

        public Criteria andContactorIdcardNoNotLike(String value) {
            addCriterion("contactor_idcard_no not like", value, "contactorIdcardNo");
            return (Criteria) this;
        }

        public Criteria andContactorIdcardNoIn(List<String> values) {
            addCriterion("contactor_idcard_no in", values, "contactorIdcardNo");
            return (Criteria) this;
        }

        public Criteria andContactorIdcardNoNotIn(List<String> values) {
            addCriterion("contactor_idcard_no not in", values, "contactorIdcardNo");
            return (Criteria) this;
        }

        public Criteria andContactorIdcardNoBetween(String value1, String value2) {
            addCriterion("contactor_idcard_no between", value1, value2, "contactorIdcardNo");
            return (Criteria) this;
        }

        public Criteria andContactorIdcardNoNotBetween(String value1, String value2) {
            addCriterion("contactor_idcard_no not between", value1, value2, "contactorIdcardNo");
            return (Criteria) this;
        }

        public Criteria andContactorCertificateEndTimeIsNull() {
            addCriterion("contactor_certificate_end_time is null");
            return (Criteria) this;
        }

        public Criteria andContactorCertificateEndTimeIsNotNull() {
            addCriterion("contactor_certificate_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andContactorCertificateEndTimeEqualTo(Date value) {
            addCriterion("contactor_certificate_end_time =", value, "contactorCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andContactorCertificateEndTimeNotEqualTo(Date value) {
            addCriterion("contactor_certificate_end_time <>", value, "contactorCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andContactorCertificateEndTimeGreaterThan(Date value) {
            addCriterion("contactor_certificate_end_time >", value, "contactorCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andContactorCertificateEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("contactor_certificate_end_time >=", value, "contactorCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andContactorCertificateEndTimeLessThan(Date value) {
            addCriterion("contactor_certificate_end_time <", value, "contactorCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andContactorCertificateEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("contactor_certificate_end_time <=", value, "contactorCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andContactorCertificateEndTimeIn(List<Date> values) {
            addCriterion("contactor_certificate_end_time in", values, "contactorCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andContactorCertificateEndTimeNotIn(List<Date> values) {
            addCriterion("contactor_certificate_end_time not in", values, "contactorCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andContactorCertificateEndTimeBetween(Date value1, Date value2) {
            addCriterion("contactor_certificate_end_time between", value1, value2, "contactorCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andContactorCertificateEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("contactor_certificate_end_time not between", value1, value2, "contactorCertificateEndTime");
            return (Criteria) this;
        }

        public Criteria andContactorPhoneIsNull() {
            addCriterion("contactor_phone is null");
            return (Criteria) this;
        }

        public Criteria andContactorPhoneIsNotNull() {
            addCriterion("contactor_phone is not null");
            return (Criteria) this;
        }

        public Criteria andContactorPhoneEqualTo(String value) {
            addCriterion("contactor_phone =", value, "contactorPhone");
            return (Criteria) this;
        }

        public Criteria andContactorPhoneNotEqualTo(String value) {
            addCriterion("contactor_phone <>", value, "contactorPhone");
            return (Criteria) this;
        }

        public Criteria andContactorPhoneGreaterThan(String value) {
            addCriterion("contactor_phone >", value, "contactorPhone");
            return (Criteria) this;
        }

        public Criteria andContactorPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("contactor_phone >=", value, "contactorPhone");
            return (Criteria) this;
        }

        public Criteria andContactorPhoneLessThan(String value) {
            addCriterion("contactor_phone <", value, "contactorPhone");
            return (Criteria) this;
        }

        public Criteria andContactorPhoneLessThanOrEqualTo(String value) {
            addCriterion("contactor_phone <=", value, "contactorPhone");
            return (Criteria) this;
        }

        public Criteria andContactorPhoneLike(String value) {
            addCriterion("contactor_phone like", value, "contactorPhone");
            return (Criteria) this;
        }

        public Criteria andContactorPhoneNotLike(String value) {
            addCriterion("contactor_phone not like", value, "contactorPhone");
            return (Criteria) this;
        }

        public Criteria andContactorPhoneIn(List<String> values) {
            addCriterion("contactor_phone in", values, "contactorPhone");
            return (Criteria) this;
        }

        public Criteria andContactorPhoneNotIn(List<String> values) {
            addCriterion("contactor_phone not in", values, "contactorPhone");
            return (Criteria) this;
        }

        public Criteria andContactorPhoneBetween(String value1, String value2) {
            addCriterion("contactor_phone between", value1, value2, "contactorPhone");
            return (Criteria) this;
        }

        public Criteria andContactorPhoneNotBetween(String value1, String value2) {
            addCriterion("contactor_phone not between", value1, value2, "contactorPhone");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIsNull() {
            addCriterion("supplier_name is null");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIsNotNull() {
            addCriterion("supplier_name is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierNameEqualTo(String value) {
            addCriterion("supplier_name =", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotEqualTo(String value) {
            addCriterion("supplier_name <>", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameGreaterThan(String value) {
            addCriterion("supplier_name >", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_name >=", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLessThan(String value) {
            addCriterion("supplier_name <", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLessThanOrEqualTo(String value) {
            addCriterion("supplier_name <=", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLike(String value) {
            addCriterion("supplier_name like", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotLike(String value) {
            addCriterion("supplier_name not like", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIn(List<String> values) {
            addCriterion("supplier_name in", values, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotIn(List<String> values) {
            addCriterion("supplier_name not in", values, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameBetween(String value1, String value2) {
            addCriterion("supplier_name between", value1, value2, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotBetween(String value1, String value2) {
            addCriterion("supplier_name not between", value1, value2, "supplierName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameIsNull() {
            addCriterion("enterprise_name is null");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameIsNotNull() {
            addCriterion("enterprise_name is not null");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameEqualTo(String value) {
            addCriterion("enterprise_name =", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameNotEqualTo(String value) {
            addCriterion("enterprise_name <>", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameGreaterThan(String value) {
            addCriterion("enterprise_name >", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameGreaterThanOrEqualTo(String value) {
            addCriterion("enterprise_name >=", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameLessThan(String value) {
            addCriterion("enterprise_name <", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameLessThanOrEqualTo(String value) {
            addCriterion("enterprise_name <=", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameLike(String value) {
            addCriterion("enterprise_name like", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameNotLike(String value) {
            addCriterion("enterprise_name not like", value, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameIn(List<String> values) {
            addCriterion("enterprise_name in", values, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameNotIn(List<String> values) {
            addCriterion("enterprise_name not in", values, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameBetween(String value1, String value2) {
            addCriterion("enterprise_name between", value1, value2, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andEnterpriseNameNotBetween(String value1, String value2) {
            addCriterion("enterprise_name not between", value1, value2, "enterpriseName");
            return (Criteria) this;
        }

        public Criteria andElectricityUrlIsNull() {
            addCriterion("electricity_url is null");
            return (Criteria) this;
        }

        public Criteria andElectricityUrlIsNotNull() {
            addCriterion("electricity_url is not null");
            return (Criteria) this;
        }

        public Criteria andElectricityUrlEqualTo(String value) {
            addCriterion("electricity_url =", value, "electricityUrl");
            return (Criteria) this;
        }

        public Criteria andElectricityUrlNotEqualTo(String value) {
            addCriterion("electricity_url <>", value, "electricityUrl");
            return (Criteria) this;
        }

        public Criteria andElectricityUrlGreaterThan(String value) {
            addCriterion("electricity_url >", value, "electricityUrl");
            return (Criteria) this;
        }

        public Criteria andElectricityUrlGreaterThanOrEqualTo(String value) {
            addCriterion("electricity_url >=", value, "electricityUrl");
            return (Criteria) this;
        }

        public Criteria andElectricityUrlLessThan(String value) {
            addCriterion("electricity_url <", value, "electricityUrl");
            return (Criteria) this;
        }

        public Criteria andElectricityUrlLessThanOrEqualTo(String value) {
            addCriterion("electricity_url <=", value, "electricityUrl");
            return (Criteria) this;
        }

        public Criteria andElectricityUrlLike(String value) {
            addCriterion("electricity_url like", value, "electricityUrl");
            return (Criteria) this;
        }

        public Criteria andElectricityUrlNotLike(String value) {
            addCriterion("electricity_url not like", value, "electricityUrl");
            return (Criteria) this;
        }

        public Criteria andElectricityUrlIn(List<String> values) {
            addCriterion("electricity_url in", values, "electricityUrl");
            return (Criteria) this;
        }

        public Criteria andElectricityUrlNotIn(List<String> values) {
            addCriterion("electricity_url not in", values, "electricityUrl");
            return (Criteria) this;
        }

        public Criteria andElectricityUrlBetween(String value1, String value2) {
            addCriterion("electricity_url between", value1, value2, "electricityUrl");
            return (Criteria) this;
        }

        public Criteria andElectricityUrlNotBetween(String value1, String value2) {
            addCriterion("electricity_url not between", value1, value2, "electricityUrl");
            return (Criteria) this;
        }

        public Criteria andIndividualUserIsNull() {
            addCriterion("individual_user is null");
            return (Criteria) this;
        }

        public Criteria andIndividualUserIsNotNull() {
            addCriterion("individual_user is not null");
            return (Criteria) this;
        }

        public Criteria andIndividualUserEqualTo(String value) {
            addCriterion("individual_user =", value, "individualUser");
            return (Criteria) this;
        }

        public Criteria andIndividualUserNotEqualTo(String value) {
            addCriterion("individual_user <>", value, "individualUser");
            return (Criteria) this;
        }

        public Criteria andIndividualUserGreaterThan(String value) {
            addCriterion("individual_user >", value, "individualUser");
            return (Criteria) this;
        }

        public Criteria andIndividualUserGreaterThanOrEqualTo(String value) {
            addCriterion("individual_user >=", value, "individualUser");
            return (Criteria) this;
        }

        public Criteria andIndividualUserLessThan(String value) {
            addCriterion("individual_user <", value, "individualUser");
            return (Criteria) this;
        }

        public Criteria andIndividualUserLessThanOrEqualTo(String value) {
            addCriterion("individual_user <=", value, "individualUser");
            return (Criteria) this;
        }

        public Criteria andIndividualUserLike(String value) {
            addCriterion("individual_user like", value, "individualUser");
            return (Criteria) this;
        }

        public Criteria andIndividualUserNotLike(String value) {
            addCriterion("individual_user not like", value, "individualUser");
            return (Criteria) this;
        }

        public Criteria andIndividualUserIn(List<String> values) {
            addCriterion("individual_user in", values, "individualUser");
            return (Criteria) this;
        }

        public Criteria andIndividualUserNotIn(List<String> values) {
            addCriterion("individual_user not in", values, "individualUser");
            return (Criteria) this;
        }

        public Criteria andIndividualUserBetween(String value1, String value2) {
            addCriterion("individual_user between", value1, value2, "individualUser");
            return (Criteria) this;
        }

        public Criteria andIndividualUserNotBetween(String value1, String value2) {
            addCriterion("individual_user not between", value1, value2, "individualUser");
            return (Criteria) this;
        }

        public Criteria andContryCodeIsNull() {
            addCriterion("contry_code is null");
            return (Criteria) this;
        }

        public Criteria andContryCodeIsNotNull() {
            addCriterion("contry_code is not null");
            return (Criteria) this;
        }

        public Criteria andContryCodeEqualTo(String value) {
            addCriterion("contry_code =", value, "contryCode");
            return (Criteria) this;
        }

        public Criteria andContryCodeNotEqualTo(String value) {
            addCriterion("contry_code <>", value, "contryCode");
            return (Criteria) this;
        }

        public Criteria andContryCodeGreaterThan(String value) {
            addCriterion("contry_code >", value, "contryCode");
            return (Criteria) this;
        }

        public Criteria andContryCodeGreaterThanOrEqualTo(String value) {
            addCriterion("contry_code >=", value, "contryCode");
            return (Criteria) this;
        }

        public Criteria andContryCodeLessThan(String value) {
            addCriterion("contry_code <", value, "contryCode");
            return (Criteria) this;
        }

        public Criteria andContryCodeLessThanOrEqualTo(String value) {
            addCriterion("contry_code <=", value, "contryCode");
            return (Criteria) this;
        }

        public Criteria andContryCodeLike(String value) {
            addCriterion("contry_code like", value, "contryCode");
            return (Criteria) this;
        }

        public Criteria andContryCodeNotLike(String value) {
            addCriterion("contry_code not like", value, "contryCode");
            return (Criteria) this;
        }

        public Criteria andContryCodeIn(List<String> values) {
            addCriterion("contry_code in", values, "contryCode");
            return (Criteria) this;
        }

        public Criteria andContryCodeNotIn(List<String> values) {
            addCriterion("contry_code not in", values, "contryCode");
            return (Criteria) this;
        }

        public Criteria andContryCodeBetween(String value1, String value2) {
            addCriterion("contry_code between", value1, value2, "contryCode");
            return (Criteria) this;
        }

        public Criteria andContryCodeNotBetween(String value1, String value2) {
            addCriterion("contry_code not between", value1, value2, "contryCode");
            return (Criteria) this;
        }

        public Criteria andMerchantPosCodeIsNull() {
            addCriterion("merchant_pos_code is null");
            return (Criteria) this;
        }

        public Criteria andMerchantPosCodeIsNotNull() {
            addCriterion("merchant_pos_code is not null");
            return (Criteria) this;
        }

        public Criteria andMerchantPosCodeEqualTo(String value) {
            addCriterion("merchant_pos_code =", value, "merchantPosCode");
            return (Criteria) this;
        }

        public Criteria andMerchantPosCodeNotEqualTo(String value) {
            addCriterion("merchant_pos_code <>", value, "merchantPosCode");
            return (Criteria) this;
        }

        public Criteria andMerchantPosCodeGreaterThan(String value) {
            addCriterion("merchant_pos_code >", value, "merchantPosCode");
            return (Criteria) this;
        }

        public Criteria andMerchantPosCodeGreaterThanOrEqualTo(String value) {
            addCriterion("merchant_pos_code >=", value, "merchantPosCode");
            return (Criteria) this;
        }

        public Criteria andMerchantPosCodeLessThan(String value) {
            addCriterion("merchant_pos_code <", value, "merchantPosCode");
            return (Criteria) this;
        }

        public Criteria andMerchantPosCodeLessThanOrEqualTo(String value) {
            addCriterion("merchant_pos_code <=", value, "merchantPosCode");
            return (Criteria) this;
        }

        public Criteria andMerchantPosCodeLike(String value) {
            addCriterion("merchant_pos_code like", value, "merchantPosCode");
            return (Criteria) this;
        }

        public Criteria andMerchantPosCodeNotLike(String value) {
            addCriterion("merchant_pos_code not like", value, "merchantPosCode");
            return (Criteria) this;
        }

        public Criteria andMerchantPosCodeIn(List<String> values) {
            addCriterion("merchant_pos_code in", values, "merchantPosCode");
            return (Criteria) this;
        }

        public Criteria andMerchantPosCodeNotIn(List<String> values) {
            addCriterion("merchant_pos_code not in", values, "merchantPosCode");
            return (Criteria) this;
        }

        public Criteria andMerchantPosCodeBetween(String value1, String value2) {
            addCriterion("merchant_pos_code between", value1, value2, "merchantPosCode");
            return (Criteria) this;
        }

        public Criteria andMerchantPosCodeNotBetween(String value1, String value2) {
            addCriterion("merchant_pos_code not between", value1, value2, "merchantPosCode");
            return (Criteria) this;
        }

        public Criteria andCompanyPhoneIsNull() {
            addCriterion("company_phone is null");
            return (Criteria) this;
        }

        public Criteria andCompanyPhoneIsNotNull() {
            addCriterion("company_phone is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyPhoneEqualTo(String value) {
            addCriterion("company_phone =", value, "companyPhone");
            return (Criteria) this;
        }

        public Criteria andCompanyPhoneNotEqualTo(String value) {
            addCriterion("company_phone <>", value, "companyPhone");
            return (Criteria) this;
        }

        public Criteria andCompanyPhoneGreaterThan(String value) {
            addCriterion("company_phone >", value, "companyPhone");
            return (Criteria) this;
        }

        public Criteria andCompanyPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("company_phone >=", value, "companyPhone");
            return (Criteria) this;
        }

        public Criteria andCompanyPhoneLessThan(String value) {
            addCriterion("company_phone <", value, "companyPhone");
            return (Criteria) this;
        }

        public Criteria andCompanyPhoneLessThanOrEqualTo(String value) {
            addCriterion("company_phone <=", value, "companyPhone");
            return (Criteria) this;
        }

        public Criteria andCompanyPhoneLike(String value) {
            addCriterion("company_phone like", value, "companyPhone");
            return (Criteria) this;
        }

        public Criteria andCompanyPhoneNotLike(String value) {
            addCriterion("company_phone not like", value, "companyPhone");
            return (Criteria) this;
        }

        public Criteria andCompanyPhoneIn(List<String> values) {
            addCriterion("company_phone in", values, "companyPhone");
            return (Criteria) this;
        }

        public Criteria andCompanyPhoneNotIn(List<String> values) {
            addCriterion("company_phone not in", values, "companyPhone");
            return (Criteria) this;
        }

        public Criteria andCompanyPhoneBetween(String value1, String value2) {
            addCriterion("company_phone between", value1, value2, "companyPhone");
            return (Criteria) this;
        }

        public Criteria andCompanyPhoneNotBetween(String value1, String value2) {
            addCriterion("company_phone not between", value1, value2, "companyPhone");
            return (Criteria) this;
        }

        public Criteria andLegalMobileIsNull() {
            addCriterion("legal_mobile is null");
            return (Criteria) this;
        }

        public Criteria andLegalMobileIsNotNull() {
            addCriterion("legal_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andLegalMobileEqualTo(String value) {
            addCriterion("legal_mobile =", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileNotEqualTo(String value) {
            addCriterion("legal_mobile <>", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileGreaterThan(String value) {
            addCriterion("legal_mobile >", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileGreaterThanOrEqualTo(String value) {
            addCriterion("legal_mobile >=", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileLessThan(String value) {
            addCriterion("legal_mobile <", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileLessThanOrEqualTo(String value) {
            addCriterion("legal_mobile <=", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileLike(String value) {
            addCriterion("legal_mobile like", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileNotLike(String value) {
            addCriterion("legal_mobile not like", value, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileIn(List<String> values) {
            addCriterion("legal_mobile in", values, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileNotIn(List<String> values) {
            addCriterion("legal_mobile not in", values, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileBetween(String value1, String value2) {
            addCriterion("legal_mobile between", value1, value2, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andLegalMobileNotBetween(String value1, String value2) {
            addCriterion("legal_mobile not between", value1, value2, "legalMobile");
            return (Criteria) this;
        }

        public Criteria andChargeTypeIsNull() {
            addCriterion("charge_type is null");
            return (Criteria) this;
        }

        public Criteria andChargeTypeIsNotNull() {
            addCriterion("charge_type is not null");
            return (Criteria) this;
        }

        public Criteria andChargeTypeEqualTo(Byte value) {
            addCriterion("charge_type =", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeNotEqualTo(Byte value) {
            addCriterion("charge_type <>", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeGreaterThan(Byte value) {
            addCriterion("charge_type >", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("charge_type >=", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeLessThan(Byte value) {
            addCriterion("charge_type <", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeLessThanOrEqualTo(Byte value) {
            addCriterion("charge_type <=", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeIn(List<Byte> values) {
            addCriterion("charge_type in", values, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeNotIn(List<Byte> values) {
            addCriterion("charge_type not in", values, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeBetween(Byte value1, Byte value2) {
            addCriterion("charge_type between", value1, value2, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("charge_type not between", value1, value2, "chargeType");
            return (Criteria) this;
        }

        public Criteria andIpcNoIsNull() {
            addCriterion("ipc_no is null");
            return (Criteria) this;
        }

        public Criteria andIpcNoIsNotNull() {
            addCriterion("ipc_no is not null");
            return (Criteria) this;
        }

        public Criteria andIpcNoEqualTo(String value) {
            addCriterion("ipc_no =", value, "ipcNo");
            return (Criteria) this;
        }

        public Criteria andIpcNoNotEqualTo(String value) {
            addCriterion("ipc_no <>", value, "ipcNo");
            return (Criteria) this;
        }

        public Criteria andIpcNoGreaterThan(String value) {
            addCriterion("ipc_no >", value, "ipcNo");
            return (Criteria) this;
        }

        public Criteria andIpcNoGreaterThanOrEqualTo(String value) {
            addCriterion("ipc_no >=", value, "ipcNo");
            return (Criteria) this;
        }

        public Criteria andIpcNoLessThan(String value) {
            addCriterion("ipc_no <", value, "ipcNo");
            return (Criteria) this;
        }

        public Criteria andIpcNoLessThanOrEqualTo(String value) {
            addCriterion("ipc_no <=", value, "ipcNo");
            return (Criteria) this;
        }

        public Criteria andIpcNoLike(String value) {
            addCriterion("ipc_no like", value, "ipcNo");
            return (Criteria) this;
        }

        public Criteria andIpcNoNotLike(String value) {
            addCriterion("ipc_no not like", value, "ipcNo");
            return (Criteria) this;
        }

        public Criteria andIpcNoIn(List<String> values) {
            addCriterion("ipc_no in", values, "ipcNo");
            return (Criteria) this;
        }

        public Criteria andIpcNoNotIn(List<String> values) {
            addCriterion("ipc_no not in", values, "ipcNo");
            return (Criteria) this;
        }

        public Criteria andIpcNoBetween(String value1, String value2) {
            addCriterion("ipc_no between", value1, value2, "ipcNo");
            return (Criteria) this;
        }

        public Criteria andIpcNoNotBetween(String value1, String value2) {
            addCriterion("ipc_no not between", value1, value2, "ipcNo");
            return (Criteria) this;
        }

        public Criteria andPermitsAccountsIsNull() {
            addCriterion("permits_accounts is null");
            return (Criteria) this;
        }

        public Criteria andPermitsAccountsIsNotNull() {
            addCriterion("permits_accounts is not null");
            return (Criteria) this;
        }

        public Criteria andPermitsAccountsEqualTo(String value) {
            addCriterion("permits_accounts =", value, "permitsAccounts");
            return (Criteria) this;
        }

        public Criteria andPermitsAccountsNotEqualTo(String value) {
            addCriterion("permits_accounts <>", value, "permitsAccounts");
            return (Criteria) this;
        }

        public Criteria andPermitsAccountsGreaterThan(String value) {
            addCriterion("permits_accounts >", value, "permitsAccounts");
            return (Criteria) this;
        }

        public Criteria andPermitsAccountsGreaterThanOrEqualTo(String value) {
            addCriterion("permits_accounts >=", value, "permitsAccounts");
            return (Criteria) this;
        }

        public Criteria andPermitsAccountsLessThan(String value) {
            addCriterion("permits_accounts <", value, "permitsAccounts");
            return (Criteria) this;
        }

        public Criteria andPermitsAccountsLessThanOrEqualTo(String value) {
            addCriterion("permits_accounts <=", value, "permitsAccounts");
            return (Criteria) this;
        }

        public Criteria andPermitsAccountsLike(String value) {
            addCriterion("permits_accounts like", value, "permitsAccounts");
            return (Criteria) this;
        }

        public Criteria andPermitsAccountsNotLike(String value) {
            addCriterion("permits_accounts not like", value, "permitsAccounts");
            return (Criteria) this;
        }

        public Criteria andPermitsAccountsIn(List<String> values) {
            addCriterion("permits_accounts in", values, "permitsAccounts");
            return (Criteria) this;
        }

        public Criteria andPermitsAccountsNotIn(List<String> values) {
            addCriterion("permits_accounts not in", values, "permitsAccounts");
            return (Criteria) this;
        }

        public Criteria andPermitsAccountsBetween(String value1, String value2) {
            addCriterion("permits_accounts between", value1, value2, "permitsAccounts");
            return (Criteria) this;
        }

        public Criteria andPermitsAccountsNotBetween(String value1, String value2) {
            addCriterion("permits_accounts not between", value1, value2, "permitsAccounts");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateFrontIsNull() {
            addCriterion("legal_certificate_front is null");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateFrontIsNotNull() {
            addCriterion("legal_certificate_front is not null");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateFrontEqualTo(String value) {
            addCriterion("legal_certificate_front =", value, "legalCertificateFront");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateFrontNotEqualTo(String value) {
            addCriterion("legal_certificate_front <>", value, "legalCertificateFront");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateFrontGreaterThan(String value) {
            addCriterion("legal_certificate_front >", value, "legalCertificateFront");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateFrontGreaterThanOrEqualTo(String value) {
            addCriterion("legal_certificate_front >=", value, "legalCertificateFront");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateFrontLessThan(String value) {
            addCriterion("legal_certificate_front <", value, "legalCertificateFront");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateFrontLessThanOrEqualTo(String value) {
            addCriterion("legal_certificate_front <=", value, "legalCertificateFront");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateFrontLike(String value) {
            addCriterion("legal_certificate_front like", value, "legalCertificateFront");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateFrontNotLike(String value) {
            addCriterion("legal_certificate_front not like", value, "legalCertificateFront");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateFrontIn(List<String> values) {
            addCriterion("legal_certificate_front in", values, "legalCertificateFront");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateFrontNotIn(List<String> values) {
            addCriterion("legal_certificate_front not in", values, "legalCertificateFront");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateFrontBetween(String value1, String value2) {
            addCriterion("legal_certificate_front between", value1, value2, "legalCertificateFront");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateFrontNotBetween(String value1, String value2) {
            addCriterion("legal_certificate_front not between", value1, value2, "legalCertificateFront");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateBackIsNull() {
            addCriterion("legal_certificate_back is null");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateBackIsNotNull() {
            addCriterion("legal_certificate_back is not null");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateBackEqualTo(String value) {
            addCriterion("legal_certificate_back =", value, "legalCertificateBack");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateBackNotEqualTo(String value) {
            addCriterion("legal_certificate_back <>", value, "legalCertificateBack");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateBackGreaterThan(String value) {
            addCriterion("legal_certificate_back >", value, "legalCertificateBack");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateBackGreaterThanOrEqualTo(String value) {
            addCriterion("legal_certificate_back >=", value, "legalCertificateBack");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateBackLessThan(String value) {
            addCriterion("legal_certificate_back <", value, "legalCertificateBack");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateBackLessThanOrEqualTo(String value) {
            addCriterion("legal_certificate_back <=", value, "legalCertificateBack");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateBackLike(String value) {
            addCriterion("legal_certificate_back like", value, "legalCertificateBack");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateBackNotLike(String value) {
            addCriterion("legal_certificate_back not like", value, "legalCertificateBack");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateBackIn(List<String> values) {
            addCriterion("legal_certificate_back in", values, "legalCertificateBack");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateBackNotIn(List<String> values) {
            addCriterion("legal_certificate_back not in", values, "legalCertificateBack");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateBackBetween(String value1, String value2) {
            addCriterion("legal_certificate_back between", value1, value2, "legalCertificateBack");
            return (Criteria) this;
        }

        public Criteria andLegalCertificateBackNotBetween(String value1, String value2) {
            addCriterion("legal_certificate_back not between", value1, value2, "legalCertificateBack");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateIsNull() {
            addCriterion("tax_registration_certificate is null");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateIsNotNull() {
            addCriterion("tax_registration_certificate is not null");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateEqualTo(String value) {
            addCriterion("tax_registration_certificate =", value, "taxRegistrationCertificate");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNotEqualTo(String value) {
            addCriterion("tax_registration_certificate <>", value, "taxRegistrationCertificate");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateGreaterThan(String value) {
            addCriterion("tax_registration_certificate >", value, "taxRegistrationCertificate");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateGreaterThanOrEqualTo(String value) {
            addCriterion("tax_registration_certificate >=", value, "taxRegistrationCertificate");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateLessThan(String value) {
            addCriterion("tax_registration_certificate <", value, "taxRegistrationCertificate");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateLessThanOrEqualTo(String value) {
            addCriterion("tax_registration_certificate <=", value, "taxRegistrationCertificate");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateLike(String value) {
            addCriterion("tax_registration_certificate like", value, "taxRegistrationCertificate");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNotLike(String value) {
            addCriterion("tax_registration_certificate not like", value, "taxRegistrationCertificate");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateIn(List<String> values) {
            addCriterion("tax_registration_certificate in", values, "taxRegistrationCertificate");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNotIn(List<String> values) {
            addCriterion("tax_registration_certificate not in", values, "taxRegistrationCertificate");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateBetween(String value1, String value2) {
            addCriterion("tax_registration_certificate between", value1, value2, "taxRegistrationCertificate");
            return (Criteria) this;
        }

        public Criteria andTaxRegistrationCertificateNotBetween(String value1, String value2) {
            addCriterion("tax_registration_certificate not between", value1, value2, "taxRegistrationCertificate");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeCertificateIsNull() {
            addCriterion("organization_code_certificate is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeCertificateIsNotNull() {
            addCriterion("organization_code_certificate is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeCertificateEqualTo(String value) {
            addCriterion("organization_code_certificate =", value, "organizationCodeCertificate");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeCertificateNotEqualTo(String value) {
            addCriterion("organization_code_certificate <>", value, "organizationCodeCertificate");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeCertificateGreaterThan(String value) {
            addCriterion("organization_code_certificate >", value, "organizationCodeCertificate");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeCertificateGreaterThanOrEqualTo(String value) {
            addCriterion("organization_code_certificate >=", value, "organizationCodeCertificate");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeCertificateLessThan(String value) {
            addCriterion("organization_code_certificate <", value, "organizationCodeCertificate");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeCertificateLessThanOrEqualTo(String value) {
            addCriterion("organization_code_certificate <=", value, "organizationCodeCertificate");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeCertificateLike(String value) {
            addCriterion("organization_code_certificate like", value, "organizationCodeCertificate");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeCertificateNotLike(String value) {
            addCriterion("organization_code_certificate not like", value, "organizationCodeCertificate");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeCertificateIn(List<String> values) {
            addCriterion("organization_code_certificate in", values, "organizationCodeCertificate");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeCertificateNotIn(List<String> values) {
            addCriterion("organization_code_certificate not in", values, "organizationCodeCertificate");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeCertificateBetween(String value1, String value2) {
            addCriterion("organization_code_certificate between", value1, value2, "organizationCodeCertificate");
            return (Criteria) this;
        }

        public Criteria andOrganizationCodeCertificateNotBetween(String value1, String value2) {
            addCriterion("organization_code_certificate not between", value1, value2, "organizationCodeCertificate");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseFileIsNull() {
            addCriterion("business_license_file is null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseFileIsNotNull() {
            addCriterion("business_license_file is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseFileEqualTo(String value) {
            addCriterion("business_license_file =", value, "businessLicenseFile");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseFileNotEqualTo(String value) {
            addCriterion("business_license_file <>", value, "businessLicenseFile");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseFileGreaterThan(String value) {
            addCriterion("business_license_file >", value, "businessLicenseFile");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseFileGreaterThanOrEqualTo(String value) {
            addCriterion("business_license_file >=", value, "businessLicenseFile");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseFileLessThan(String value) {
            addCriterion("business_license_file <", value, "businessLicenseFile");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseFileLessThanOrEqualTo(String value) {
            addCriterion("business_license_file <=", value, "businessLicenseFile");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseFileLike(String value) {
            addCriterion("business_license_file like", value, "businessLicenseFile");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseFileNotLike(String value) {
            addCriterion("business_license_file not like", value, "businessLicenseFile");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseFileIn(List<String> values) {
            addCriterion("business_license_file in", values, "businessLicenseFile");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseFileNotIn(List<String> values) {
            addCriterion("business_license_file not in", values, "businessLicenseFile");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseFileBetween(String value1, String value2) {
            addCriterion("business_license_file between", value1, value2, "businessLicenseFile");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseFileNotBetween(String value1, String value2) {
            addCriterion("business_license_file not between", value1, value2, "businessLicenseFile");
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

        public Criteria andTrademarkRegisPhotoAddressIsNull() {
            addCriterion("trademark_regis_photo_address is null");
            return (Criteria) this;
        }

        public Criteria andTrademarkRegisPhotoAddressIsNotNull() {
            addCriterion("trademark_regis_photo_address is not null");
            return (Criteria) this;
        }

        public Criteria andTrademarkRegisPhotoAddressEqualTo(String value) {
            addCriterion("trademark_regis_photo_address =", value, "trademarkRegisPhotoAddress");
            return (Criteria) this;
        }

        public Criteria andTrademarkRegisPhotoAddressNotEqualTo(String value) {
            addCriterion("trademark_regis_photo_address <>", value, "trademarkRegisPhotoAddress");
            return (Criteria) this;
        }

        public Criteria andTrademarkRegisPhotoAddressGreaterThan(String value) {
            addCriterion("trademark_regis_photo_address >", value, "trademarkRegisPhotoAddress");
            return (Criteria) this;
        }

        public Criteria andTrademarkRegisPhotoAddressGreaterThanOrEqualTo(String value) {
            addCriterion("trademark_regis_photo_address >=", value, "trademarkRegisPhotoAddress");
            return (Criteria) this;
        }

        public Criteria andTrademarkRegisPhotoAddressLessThan(String value) {
            addCriterion("trademark_regis_photo_address <", value, "trademarkRegisPhotoAddress");
            return (Criteria) this;
        }

        public Criteria andTrademarkRegisPhotoAddressLessThanOrEqualTo(String value) {
            addCriterion("trademark_regis_photo_address <=", value, "trademarkRegisPhotoAddress");
            return (Criteria) this;
        }

        public Criteria andTrademarkRegisPhotoAddressLike(String value) {
            addCriterion("trademark_regis_photo_address like", value, "trademarkRegisPhotoAddress");
            return (Criteria) this;
        }

        public Criteria andTrademarkRegisPhotoAddressNotLike(String value) {
            addCriterion("trademark_regis_photo_address not like", value, "trademarkRegisPhotoAddress");
            return (Criteria) this;
        }

        public Criteria andTrademarkRegisPhotoAddressIn(List<String> values) {
            addCriterion("trademark_regis_photo_address in", values, "trademarkRegisPhotoAddress");
            return (Criteria) this;
        }

        public Criteria andTrademarkRegisPhotoAddressNotIn(List<String> values) {
            addCriterion("trademark_regis_photo_address not in", values, "trademarkRegisPhotoAddress");
            return (Criteria) this;
        }

        public Criteria andTrademarkRegisPhotoAddressBetween(String value1, String value2) {
            addCriterion("trademark_regis_photo_address between", value1, value2, "trademarkRegisPhotoAddress");
            return (Criteria) this;
        }

        public Criteria andTrademarkRegisPhotoAddressNotBetween(String value1, String value2) {
            addCriterion("trademark_regis_photo_address not between", value1, value2, "trademarkRegisPhotoAddress");
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