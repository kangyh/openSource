package com.heepay.billing.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SettleChannelLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SettleChannelLogExample() {
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

        public Criteria andLogIdEqualTo(Long value) {
            addCriterion("log_id =", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotEqualTo(Long value) {
            addCriterion("log_id <>", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdGreaterThan(Long value) {
            addCriterion("log_id >", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdGreaterThanOrEqualTo(Long value) {
            addCriterion("log_id >=", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdLessThan(Long value) {
            addCriterion("log_id <", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdLessThanOrEqualTo(Long value) {
            addCriterion("log_id <=", value, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdIn(List<Long> values) {
            addCriterion("log_id in", values, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotIn(List<Long> values) {
            addCriterion("log_id not in", values, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdBetween(Long value1, Long value2) {
            addCriterion("log_id between", value1, value2, "logId");
            return (Criteria) this;
        }

        public Criteria andLogIdNotBetween(Long value1, Long value2) {
            addCriterion("log_id not between", value1, value2, "logId");
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

        public Criteria andCheckBathNoIsNull() {
            addCriterion("check_bath_no is null");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoIsNotNull() {
            addCriterion("check_bath_no is not null");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoEqualTo(String value) {
            addCriterion("check_bath_no =", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoNotEqualTo(String value) {
            addCriterion("check_bath_no <>", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoGreaterThan(String value) {
            addCriterion("check_bath_no >", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoGreaterThanOrEqualTo(String value) {
            addCriterion("check_bath_no >=", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoLessThan(String value) {
            addCriterion("check_bath_no <", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoLessThanOrEqualTo(String value) {
            addCriterion("check_bath_no <=", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoLike(String value) {
            addCriterion("check_bath_no like", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoNotLike(String value) {
            addCriterion("check_bath_no not like", value, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoIn(List<String> values) {
            addCriterion("check_bath_no in", values, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoNotIn(List<String> values) {
            addCriterion("check_bath_no not in", values, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoBetween(String value1, String value2) {
            addCriterion("check_bath_no between", value1, value2, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andCheckBathNoNotBetween(String value1, String value2) {
            addCriterion("check_bath_no not between", value1, value2, "checkBathNo");
            return (Criteria) this;
        }

        public Criteria andOperBeginTimeIsNull() {
            addCriterion("oper_begin_time is null");
            return (Criteria) this;
        }

        public Criteria andOperBeginTimeIsNotNull() {
            addCriterion("oper_begin_time is not null");
            return (Criteria) this;
        }

        public Criteria andOperBeginTimeEqualTo(Date value) {
            addCriterion("oper_begin_time =", value, "operBeginTime");
            return (Criteria) this;
        }

        public Criteria andOperBeginTimeNotEqualTo(Date value) {
            addCriterion("oper_begin_time <>", value, "operBeginTime");
            return (Criteria) this;
        }

        public Criteria andOperBeginTimeGreaterThan(Date value) {
            addCriterion("oper_begin_time >", value, "operBeginTime");
            return (Criteria) this;
        }

        public Criteria andOperBeginTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("oper_begin_time >=", value, "operBeginTime");
            return (Criteria) this;
        }

        public Criteria andOperBeginTimeLessThan(Date value) {
            addCriterion("oper_begin_time <", value, "operBeginTime");
            return (Criteria) this;
        }

        public Criteria andOperBeginTimeLessThanOrEqualTo(Date value) {
            addCriterion("oper_begin_time <=", value, "operBeginTime");
            return (Criteria) this;
        }

        public Criteria andOperBeginTimeIn(List<Date> values) {
            addCriterion("oper_begin_time in", values, "operBeginTime");
            return (Criteria) this;
        }

        public Criteria andOperBeginTimeNotIn(List<Date> values) {
            addCriterion("oper_begin_time not in", values, "operBeginTime");
            return (Criteria) this;
        }

        public Criteria andOperBeginTimeBetween(Date value1, Date value2) {
            addCriterion("oper_begin_time between", value1, value2, "operBeginTime");
            return (Criteria) this;
        }

        public Criteria andOperBeginTimeNotBetween(Date value1, Date value2) {
            addCriterion("oper_begin_time not between", value1, value2, "operBeginTime");
            return (Criteria) this;
        }

        public Criteria andOperEndTimeIsNull() {
            addCriterion("oper_end_time is null");
            return (Criteria) this;
        }

        public Criteria andOperEndTimeIsNotNull() {
            addCriterion("oper_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andOperEndTimeEqualTo(Date value) {
            addCriterion("oper_end_time =", value, "operEndTime");
            return (Criteria) this;
        }

        public Criteria andOperEndTimeNotEqualTo(Date value) {
            addCriterion("oper_end_time <>", value, "operEndTime");
            return (Criteria) this;
        }

        public Criteria andOperEndTimeGreaterThan(Date value) {
            addCriterion("oper_end_time >", value, "operEndTime");
            return (Criteria) this;
        }

        public Criteria andOperEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("oper_end_time >=", value, "operEndTime");
            return (Criteria) this;
        }

        public Criteria andOperEndTimeLessThan(Date value) {
            addCriterion("oper_end_time <", value, "operEndTime");
            return (Criteria) this;
        }

        public Criteria andOperEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("oper_end_time <=", value, "operEndTime");
            return (Criteria) this;
        }

        public Criteria andOperEndTimeIn(List<Date> values) {
            addCriterion("oper_end_time in", values, "operEndTime");
            return (Criteria) this;
        }

        public Criteria andOperEndTimeNotIn(List<Date> values) {
            addCriterion("oper_end_time not in", values, "operEndTime");
            return (Criteria) this;
        }

        public Criteria andOperEndTimeBetween(Date value1, Date value2) {
            addCriterion("oper_end_time between", value1, value2, "operEndTime");
            return (Criteria) this;
        }

        public Criteria andOperEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("oper_end_time not between", value1, value2, "operEndTime");
            return (Criteria) this;
        }

        public Criteria andInRecordNumIsNull() {
            addCriterion("in_record_num is null");
            return (Criteria) this;
        }

        public Criteria andInRecordNumIsNotNull() {
            addCriterion("in_record_num is not null");
            return (Criteria) this;
        }

        public Criteria andInRecordNumEqualTo(Long value) {
            addCriterion("in_record_num =", value, "inRecordNum");
            return (Criteria) this;
        }

        public Criteria andInRecordNumNotEqualTo(Long value) {
            addCriterion("in_record_num <>", value, "inRecordNum");
            return (Criteria) this;
        }

        public Criteria andInRecordNumGreaterThan(Long value) {
            addCriterion("in_record_num >", value, "inRecordNum");
            return (Criteria) this;
        }

        public Criteria andInRecordNumGreaterThanOrEqualTo(Long value) {
            addCriterion("in_record_num >=", value, "inRecordNum");
            return (Criteria) this;
        }

        public Criteria andInRecordNumLessThan(Long value) {
            addCriterion("in_record_num <", value, "inRecordNum");
            return (Criteria) this;
        }

        public Criteria andInRecordNumLessThanOrEqualTo(Long value) {
            addCriterion("in_record_num <=", value, "inRecordNum");
            return (Criteria) this;
        }

        public Criteria andInRecordNumIn(List<Long> values) {
            addCriterion("in_record_num in", values, "inRecordNum");
            return (Criteria) this;
        }

        public Criteria andInRecordNumNotIn(List<Long> values) {
            addCriterion("in_record_num not in", values, "inRecordNum");
            return (Criteria) this;
        }

        public Criteria andInRecordNumBetween(Long value1, Long value2) {
            addCriterion("in_record_num between", value1, value2, "inRecordNum");
            return (Criteria) this;
        }

        public Criteria andInRecordNumNotBetween(Long value1, Long value2) {
            addCriterion("in_record_num not between", value1, value2, "inRecordNum");
            return (Criteria) this;
        }

        public Criteria andInTotalAmountIsNull() {
            addCriterion("in_total_amount is null");
            return (Criteria) this;
        }

        public Criteria andInTotalAmountIsNotNull() {
            addCriterion("in_total_amount is not null");
            return (Criteria) this;
        }

        public Criteria andInTotalAmountEqualTo(BigDecimal value) {
            addCriterion("in_total_amount =", value, "inTotalAmount");
            return (Criteria) this;
        }

        public Criteria andInTotalAmountNotEqualTo(BigDecimal value) {
            addCriterion("in_total_amount <>", value, "inTotalAmount");
            return (Criteria) this;
        }

        public Criteria andInTotalAmountGreaterThan(BigDecimal value) {
            addCriterion("in_total_amount >", value, "inTotalAmount");
            return (Criteria) this;
        }

        public Criteria andInTotalAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("in_total_amount >=", value, "inTotalAmount");
            return (Criteria) this;
        }

        public Criteria andInTotalAmountLessThan(BigDecimal value) {
            addCriterion("in_total_amount <", value, "inTotalAmount");
            return (Criteria) this;
        }

        public Criteria andInTotalAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("in_total_amount <=", value, "inTotalAmount");
            return (Criteria) this;
        }

        public Criteria andInTotalAmountIn(List<BigDecimal> values) {
            addCriterion("in_total_amount in", values, "inTotalAmount");
            return (Criteria) this;
        }

        public Criteria andInTotalAmountNotIn(List<BigDecimal> values) {
            addCriterion("in_total_amount not in", values, "inTotalAmount");
            return (Criteria) this;
        }

        public Criteria andInTotalAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("in_total_amount between", value1, value2, "inTotalAmount");
            return (Criteria) this;
        }

        public Criteria andInTotalAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("in_total_amount not between", value1, value2, "inTotalAmount");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNull() {
            addCriterion("check_status is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("check_status is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(String value) {
            addCriterion("check_status =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(String value) {
            addCriterion("check_status <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(String value) {
            addCriterion("check_status >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(String value) {
            addCriterion("check_status >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(String value) {
            addCriterion("check_status <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(String value) {
            addCriterion("check_status <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLike(String value) {
            addCriterion("check_status like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotLike(String value) {
            addCriterion("check_status not like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<String> values) {
            addCriterion("check_status in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<String> values) {
            addCriterion("check_status not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(String value1, String value2) {
            addCriterion("check_status between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(String value1, String value2) {
            addCriterion("check_status not between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameIsNull() {
            addCriterion("check_file_name is null");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameIsNotNull() {
            addCriterion("check_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameEqualTo(String value) {
            addCriterion("check_file_name =", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameNotEqualTo(String value) {
            addCriterion("check_file_name <>", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameGreaterThan(String value) {
            addCriterion("check_file_name >", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("check_file_name >=", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameLessThan(String value) {
            addCriterion("check_file_name <", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameLessThanOrEqualTo(String value) {
            addCriterion("check_file_name <=", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameLike(String value) {
            addCriterion("check_file_name like", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameNotLike(String value) {
            addCriterion("check_file_name not like", value, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameIn(List<String> values) {
            addCriterion("check_file_name in", values, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameNotIn(List<String> values) {
            addCriterion("check_file_name not in", values, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameBetween(String value1, String value2) {
            addCriterion("check_file_name between", value1, value2, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andCheckFileNameNotBetween(String value1, String value2) {
            addCriterion("check_file_name not between", value1, value2, "checkFileName");
            return (Criteria) this;
        }

        public Criteria andOutRecordNumIsNull() {
            addCriterion("out_record_num is null");
            return (Criteria) this;
        }

        public Criteria andOutRecordNumIsNotNull() {
            addCriterion("out_record_num is not null");
            return (Criteria) this;
        }

        public Criteria andOutRecordNumEqualTo(Long value) {
            addCriterion("out_record_num =", value, "outRecordNum");
            return (Criteria) this;
        }

        public Criteria andOutRecordNumNotEqualTo(Long value) {
            addCriterion("out_record_num <>", value, "outRecordNum");
            return (Criteria) this;
        }

        public Criteria andOutRecordNumGreaterThan(Long value) {
            addCriterion("out_record_num >", value, "outRecordNum");
            return (Criteria) this;
        }

        public Criteria andOutRecordNumGreaterThanOrEqualTo(Long value) {
            addCriterion("out_record_num >=", value, "outRecordNum");
            return (Criteria) this;
        }

        public Criteria andOutRecordNumLessThan(Long value) {
            addCriterion("out_record_num <", value, "outRecordNum");
            return (Criteria) this;
        }

        public Criteria andOutRecordNumLessThanOrEqualTo(Long value) {
            addCriterion("out_record_num <=", value, "outRecordNum");
            return (Criteria) this;
        }

        public Criteria andOutRecordNumIn(List<Long> values) {
            addCriterion("out_record_num in", values, "outRecordNum");
            return (Criteria) this;
        }

        public Criteria andOutRecordNumNotIn(List<Long> values) {
            addCriterion("out_record_num not in", values, "outRecordNum");
            return (Criteria) this;
        }

        public Criteria andOutRecordNumBetween(Long value1, Long value2) {
            addCriterion("out_record_num between", value1, value2, "outRecordNum");
            return (Criteria) this;
        }

        public Criteria andOutRecordNumNotBetween(Long value1, Long value2) {
            addCriterion("out_record_num not between", value1, value2, "outRecordNum");
            return (Criteria) this;
        }

        public Criteria andOutTotalAmountIsNull() {
            addCriterion("out_total_amount is null");
            return (Criteria) this;
        }

        public Criteria andOutTotalAmountIsNotNull() {
            addCriterion("out_total_amount is not null");
            return (Criteria) this;
        }

        public Criteria andOutTotalAmountEqualTo(BigDecimal value) {
            addCriterion("out_total_amount =", value, "outTotalAmount");
            return (Criteria) this;
        }

        public Criteria andOutTotalAmountNotEqualTo(BigDecimal value) {
            addCriterion("out_total_amount <>", value, "outTotalAmount");
            return (Criteria) this;
        }

        public Criteria andOutTotalAmountGreaterThan(BigDecimal value) {
            addCriterion("out_total_amount >", value, "outTotalAmount");
            return (Criteria) this;
        }

        public Criteria andOutTotalAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("out_total_amount >=", value, "outTotalAmount");
            return (Criteria) this;
        }

        public Criteria andOutTotalAmountLessThan(BigDecimal value) {
            addCriterion("out_total_amount <", value, "outTotalAmount");
            return (Criteria) this;
        }

        public Criteria andOutTotalAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("out_total_amount <=", value, "outTotalAmount");
            return (Criteria) this;
        }

        public Criteria andOutTotalAmountIn(List<BigDecimal> values) {
            addCriterion("out_total_amount in", values, "outTotalAmount");
            return (Criteria) this;
        }

        public Criteria andOutTotalAmountNotIn(List<BigDecimal> values) {
            addCriterion("out_total_amount not in", values, "outTotalAmount");
            return (Criteria) this;
        }

        public Criteria andOutTotalAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("out_total_amount between", value1, value2, "outTotalAmount");
            return (Criteria) this;
        }

        public Criteria andOutTotalAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("out_total_amount not between", value1, value2, "outTotalAmount");
            return (Criteria) this;
        }

        public Criteria andRecordNumIsNull() {
            addCriterion("record_num is null");
            return (Criteria) this;
        }

        public Criteria andRecordNumIsNotNull() {
            addCriterion("record_num is not null");
            return (Criteria) this;
        }

        public Criteria andRecordNumEqualTo(Long value) {
            addCriterion("record_num =", value, "recordNum");
            return (Criteria) this;
        }

        public Criteria andRecordNumNotEqualTo(Long value) {
            addCriterion("record_num <>", value, "recordNum");
            return (Criteria) this;
        }

        public Criteria andRecordNumGreaterThan(Long value) {
            addCriterion("record_num >", value, "recordNum");
            return (Criteria) this;
        }

        public Criteria andRecordNumGreaterThanOrEqualTo(Long value) {
            addCriterion("record_num >=", value, "recordNum");
            return (Criteria) this;
        }

        public Criteria andRecordNumLessThan(Long value) {
            addCriterion("record_num <", value, "recordNum");
            return (Criteria) this;
        }

        public Criteria andRecordNumLessThanOrEqualTo(Long value) {
            addCriterion("record_num <=", value, "recordNum");
            return (Criteria) this;
        }

        public Criteria andRecordNumIn(List<Long> values) {
            addCriterion("record_num in", values, "recordNum");
            return (Criteria) this;
        }

        public Criteria andRecordNumNotIn(List<Long> values) {
            addCriterion("record_num not in", values, "recordNum");
            return (Criteria) this;
        }

        public Criteria andRecordNumBetween(Long value1, Long value2) {
            addCriterion("record_num between", value1, value2, "recordNum");
            return (Criteria) this;
        }

        public Criteria andRecordNumNotBetween(Long value1, Long value2) {
            addCriterion("record_num not between", value1, value2, "recordNum");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIsNull() {
            addCriterion("total_amount is null");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIsNotNull() {
            addCriterion("total_amount is not null");
            return (Criteria) this;
        }

        public Criteria andTotalAmountEqualTo(BigDecimal value) {
            addCriterion("total_amount =", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotEqualTo(BigDecimal value) {
            addCriterion("total_amount <>", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountGreaterThan(BigDecimal value) {
            addCriterion("total_amount >", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_amount >=", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountLessThan(BigDecimal value) {
            addCriterion("total_amount <", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_amount <=", value, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountIn(List<BigDecimal> values) {
            addCriterion("total_amount in", values, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotIn(List<BigDecimal> values) {
            addCriterion("total_amount not in", values, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_amount between", value1, value2, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andTotalAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_amount not between", value1, value2, "totalAmount");
            return (Criteria) this;
        }

        public Criteria andErrorRecordNumIsNull() {
            addCriterion("error_record_num is null");
            return (Criteria) this;
        }

        public Criteria andErrorRecordNumIsNotNull() {
            addCriterion("error_record_num is not null");
            return (Criteria) this;
        }

        public Criteria andErrorRecordNumEqualTo(Long value) {
            addCriterion("error_record_num =", value, "errorRecordNum");
            return (Criteria) this;
        }

        public Criteria andErrorRecordNumNotEqualTo(Long value) {
            addCriterion("error_record_num <>", value, "errorRecordNum");
            return (Criteria) this;
        }

        public Criteria andErrorRecordNumGreaterThan(Long value) {
            addCriterion("error_record_num >", value, "errorRecordNum");
            return (Criteria) this;
        }

        public Criteria andErrorRecordNumGreaterThanOrEqualTo(Long value) {
            addCriterion("error_record_num >=", value, "errorRecordNum");
            return (Criteria) this;
        }

        public Criteria andErrorRecordNumLessThan(Long value) {
            addCriterion("error_record_num <", value, "errorRecordNum");
            return (Criteria) this;
        }

        public Criteria andErrorRecordNumLessThanOrEqualTo(Long value) {
            addCriterion("error_record_num <=", value, "errorRecordNum");
            return (Criteria) this;
        }

        public Criteria andErrorRecordNumIn(List<Long> values) {
            addCriterion("error_record_num in", values, "errorRecordNum");
            return (Criteria) this;
        }

        public Criteria andErrorRecordNumNotIn(List<Long> values) {
            addCriterion("error_record_num not in", values, "errorRecordNum");
            return (Criteria) this;
        }

        public Criteria andErrorRecordNumBetween(Long value1, Long value2) {
            addCriterion("error_record_num between", value1, value2, "errorRecordNum");
            return (Criteria) this;
        }

        public Criteria andErrorRecordNumNotBetween(Long value1, Long value2) {
            addCriterion("error_record_num not between", value1, value2, "errorRecordNum");
            return (Criteria) this;
        }

        public Criteria andErrorTotalAmountIsNull() {
            addCriterion("error_total_amount is null");
            return (Criteria) this;
        }

        public Criteria andErrorTotalAmountIsNotNull() {
            addCriterion("error_total_amount is not null");
            return (Criteria) this;
        }

        public Criteria andErrorTotalAmountEqualTo(BigDecimal value) {
            addCriterion("error_total_amount =", value, "errorTotalAmount");
            return (Criteria) this;
        }

        public Criteria andErrorTotalAmountNotEqualTo(BigDecimal value) {
            addCriterion("error_total_amount <>", value, "errorTotalAmount");
            return (Criteria) this;
        }

        public Criteria andErrorTotalAmountGreaterThan(BigDecimal value) {
            addCriterion("error_total_amount >", value, "errorTotalAmount");
            return (Criteria) this;
        }

        public Criteria andErrorTotalAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("error_total_amount >=", value, "errorTotalAmount");
            return (Criteria) this;
        }

        public Criteria andErrorTotalAmountLessThan(BigDecimal value) {
            addCriterion("error_total_amount <", value, "errorTotalAmount");
            return (Criteria) this;
        }

        public Criteria andErrorTotalAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("error_total_amount <=", value, "errorTotalAmount");
            return (Criteria) this;
        }

        public Criteria andErrorTotalAmountIn(List<BigDecimal> values) {
            addCriterion("error_total_amount in", values, "errorTotalAmount");
            return (Criteria) this;
        }

        public Criteria andErrorTotalAmountNotIn(List<BigDecimal> values) {
            addCriterion("error_total_amount not in", values, "errorTotalAmount");
            return (Criteria) this;
        }

        public Criteria andErrorTotalAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("error_total_amount between", value1, value2, "errorTotalAmount");
            return (Criteria) this;
        }

        public Criteria andErrorTotalAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("error_total_amount not between", value1, value2, "errorTotalAmount");
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