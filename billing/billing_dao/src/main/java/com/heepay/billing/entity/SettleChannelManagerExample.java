package com.heepay.billing.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SettleChannelManagerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SettleChannelManagerExample() {
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

        public Criteria andChannelManangeIdIsNull() {
            addCriterion("channel_manange_id is null");
            return (Criteria) this;
        }

        public Criteria andChannelManangeIdIsNotNull() {
            addCriterion("channel_manange_id is not null");
            return (Criteria) this;
        }

        public Criteria andChannelManangeIdEqualTo(Long value) {
            addCriterion("channel_manange_id =", value, "channelManangeId");
            return (Criteria) this;
        }

        public Criteria andChannelManangeIdNotEqualTo(Long value) {
            addCriterion("channel_manange_id <>", value, "channelManangeId");
            return (Criteria) this;
        }

        public Criteria andChannelManangeIdGreaterThan(Long value) {
            addCriterion("channel_manange_id >", value, "channelManangeId");
            return (Criteria) this;
        }

        public Criteria andChannelManangeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("channel_manange_id >=", value, "channelManangeId");
            return (Criteria) this;
        }

        public Criteria andChannelManangeIdLessThan(Long value) {
            addCriterion("channel_manange_id <", value, "channelManangeId");
            return (Criteria) this;
        }

        public Criteria andChannelManangeIdLessThanOrEqualTo(Long value) {
            addCriterion("channel_manange_id <=", value, "channelManangeId");
            return (Criteria) this;
        }

        public Criteria andChannelManangeIdIn(List<Long> values) {
            addCriterion("channel_manange_id in", values, "channelManangeId");
            return (Criteria) this;
        }

        public Criteria andChannelManangeIdNotIn(List<Long> values) {
            addCriterion("channel_manange_id not in", values, "channelManangeId");
            return (Criteria) this;
        }

        public Criteria andChannelManangeIdBetween(Long value1, Long value2) {
            addCriterion("channel_manange_id between", value1, value2, "channelManangeId");
            return (Criteria) this;
        }

        public Criteria andChannelManangeIdNotBetween(Long value1, Long value2) {
            addCriterion("channel_manange_id not between", value1, value2, "channelManangeId");
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

        public Criteria andChannelNameIsNull() {
            addCriterion("channel_name is null");
            return (Criteria) this;
        }

        public Criteria andChannelNameIsNotNull() {
            addCriterion("channel_name is not null");
            return (Criteria) this;
        }

        public Criteria andChannelNameEqualTo(String value) {
            addCriterion("channel_name =", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotEqualTo(String value) {
            addCriterion("channel_name <>", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameGreaterThan(String value) {
            addCriterion("channel_name >", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameGreaterThanOrEqualTo(String value) {
            addCriterion("channel_name >=", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLessThan(String value) {
            addCriterion("channel_name <", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLessThanOrEqualTo(String value) {
            addCriterion("channel_name <=", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameLike(String value) {
            addCriterion("channel_name like", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotLike(String value) {
            addCriterion("channel_name not like", value, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameIn(List<String> values) {
            addCriterion("channel_name in", values, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotIn(List<String> values) {
            addCriterion("channel_name not in", values, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameBetween(String value1, String value2) {
            addCriterion("channel_name between", value1, value2, "channelName");
            return (Criteria) this;
        }

        public Criteria andChannelNameNotBetween(String value1, String value2) {
            addCriterion("channel_name not between", value1, value2, "channelName");
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

        public Criteria andCostSettleCycIsNull() {
            addCriterion("cost_settle_cyc is null");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycIsNotNull() {
            addCriterion("cost_settle_cyc is not null");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycEqualTo(String value) {
            addCriterion("cost_settle_cyc =", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycNotEqualTo(String value) {
            addCriterion("cost_settle_cyc <>", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycGreaterThan(String value) {
            addCriterion("cost_settle_cyc >", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycGreaterThanOrEqualTo(String value) {
            addCriterion("cost_settle_cyc >=", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycLessThan(String value) {
            addCriterion("cost_settle_cyc <", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycLessThanOrEqualTo(String value) {
            addCriterion("cost_settle_cyc <=", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycLike(String value) {
            addCriterion("cost_settle_cyc like", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycNotLike(String value) {
            addCriterion("cost_settle_cyc not like", value, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycIn(List<String> values) {
            addCriterion("cost_settle_cyc in", values, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycNotIn(List<String> values) {
            addCriterion("cost_settle_cyc not in", values, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycBetween(String value1, String value2) {
            addCriterion("cost_settle_cyc between", value1, value2, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andCostSettleCycNotBetween(String value1, String value2) {
            addCriterion("cost_settle_cyc not between", value1, value2, "costSettleCyc");
            return (Criteria) this;
        }

        public Criteria andEffectFlgIsNull() {
            addCriterion("effect_flg is null");
            return (Criteria) this;
        }

        public Criteria andEffectFlgIsNotNull() {
            addCriterion("effect_flg is not null");
            return (Criteria) this;
        }

        public Criteria andEffectFlgEqualTo(String value) {
            addCriterion("effect_flg =", value, "effectFlg");
            return (Criteria) this;
        }

        public Criteria andEffectFlgNotEqualTo(String value) {
            addCriterion("effect_flg <>", value, "effectFlg");
            return (Criteria) this;
        }

        public Criteria andEffectFlgGreaterThan(String value) {
            addCriterion("effect_flg >", value, "effectFlg");
            return (Criteria) this;
        }

        public Criteria andEffectFlgGreaterThanOrEqualTo(String value) {
            addCriterion("effect_flg >=", value, "effectFlg");
            return (Criteria) this;
        }

        public Criteria andEffectFlgLessThan(String value) {
            addCriterion("effect_flg <", value, "effectFlg");
            return (Criteria) this;
        }

        public Criteria andEffectFlgLessThanOrEqualTo(String value) {
            addCriterion("effect_flg <=", value, "effectFlg");
            return (Criteria) this;
        }

        public Criteria andEffectFlgLike(String value) {
            addCriterion("effect_flg like", value, "effectFlg");
            return (Criteria) this;
        }

        public Criteria andEffectFlgNotLike(String value) {
            addCriterion("effect_flg not like", value, "effectFlg");
            return (Criteria) this;
        }

        public Criteria andEffectFlgIn(List<String> values) {
            addCriterion("effect_flg in", values, "effectFlg");
            return (Criteria) this;
        }

        public Criteria andEffectFlgNotIn(List<String> values) {
            addCriterion("effect_flg not in", values, "effectFlg");
            return (Criteria) this;
        }

        public Criteria andEffectFlgBetween(String value1, String value2) {
            addCriterion("effect_flg between", value1, value2, "effectFlg");
            return (Criteria) this;
        }

        public Criteria andEffectFlgNotBetween(String value1, String value2) {
            addCriterion("effect_flg not between", value1, value2, "effectFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgIsNull() {
            addCriterion("check_flg is null");
            return (Criteria) this;
        }

        public Criteria andCheckFlgIsNotNull() {
            addCriterion("check_flg is not null");
            return (Criteria) this;
        }

        public Criteria andCheckFlgEqualTo(String value) {
            addCriterion("check_flg =", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgNotEqualTo(String value) {
            addCriterion("check_flg <>", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgGreaterThan(String value) {
            addCriterion("check_flg >", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgGreaterThanOrEqualTo(String value) {
            addCriterion("check_flg >=", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgLessThan(String value) {
            addCriterion("check_flg <", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgLessThanOrEqualTo(String value) {
            addCriterion("check_flg <=", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgLike(String value) {
            addCriterion("check_flg like", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgNotLike(String value) {
            addCriterion("check_flg not like", value, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgIn(List<String> values) {
            addCriterion("check_flg in", values, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgNotIn(List<String> values) {
            addCriterion("check_flg not in", values, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgBetween(String value1, String value2) {
            addCriterion("check_flg between", value1, value2, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andCheckFlgNotBetween(String value1, String value2) {
            addCriterion("check_flg not between", value1, value2, "checkFlg");
            return (Criteria) this;
        }

        public Criteria andSettleCycIsNull() {
            addCriterion("settle_cyc is null");
            return (Criteria) this;
        }

        public Criteria andSettleCycIsNotNull() {
            addCriterion("settle_cyc is not null");
            return (Criteria) this;
        }

        public Criteria andSettleCycEqualTo(String value) {
            addCriterion("settle_cyc =", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycNotEqualTo(String value) {
            addCriterion("settle_cyc <>", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycGreaterThan(String value) {
            addCriterion("settle_cyc >", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycGreaterThanOrEqualTo(String value) {
            addCriterion("settle_cyc >=", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycLessThan(String value) {
            addCriterion("settle_cyc <", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycLessThanOrEqualTo(String value) {
            addCriterion("settle_cyc <=", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycLike(String value) {
            addCriterion("settle_cyc like", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycNotLike(String value) {
            addCriterion("settle_cyc not like", value, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycIn(List<String> values) {
            addCriterion("settle_cyc in", values, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycNotIn(List<String> values) {
            addCriterion("settle_cyc not in", values, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycBetween(String value1, String value2) {
            addCriterion("settle_cyc between", value1, value2, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andSettleCycNotBetween(String value1, String value2) {
            addCriterion("settle_cyc not between", value1, value2, "settleCyc");
            return (Criteria) this;
        }

        public Criteria andRemoteAdressIsNull() {
            addCriterion("remote_adress is null");
            return (Criteria) this;
        }

        public Criteria andRemoteAdressIsNotNull() {
            addCriterion("remote_adress is not null");
            return (Criteria) this;
        }

        public Criteria andRemoteAdressEqualTo(String value) {
            addCriterion("remote_adress =", value, "remoteAdress");
            return (Criteria) this;
        }

        public Criteria andRemoteAdressNotEqualTo(String value) {
            addCriterion("remote_adress <>", value, "remoteAdress");
            return (Criteria) this;
        }

        public Criteria andRemoteAdressGreaterThan(String value) {
            addCriterion("remote_adress >", value, "remoteAdress");
            return (Criteria) this;
        }

        public Criteria andRemoteAdressGreaterThanOrEqualTo(String value) {
            addCriterion("remote_adress >=", value, "remoteAdress");
            return (Criteria) this;
        }

        public Criteria andRemoteAdressLessThan(String value) {
            addCriterion("remote_adress <", value, "remoteAdress");
            return (Criteria) this;
        }

        public Criteria andRemoteAdressLessThanOrEqualTo(String value) {
            addCriterion("remote_adress <=", value, "remoteAdress");
            return (Criteria) this;
        }

        public Criteria andRemoteAdressLike(String value) {
            addCriterion("remote_adress like", value, "remoteAdress");
            return (Criteria) this;
        }

        public Criteria andRemoteAdressNotLike(String value) {
            addCriterion("remote_adress not like", value, "remoteAdress");
            return (Criteria) this;
        }

        public Criteria andRemoteAdressIn(List<String> values) {
            addCriterion("remote_adress in", values, "remoteAdress");
            return (Criteria) this;
        }

        public Criteria andRemoteAdressNotIn(List<String> values) {
            addCriterion("remote_adress not in", values, "remoteAdress");
            return (Criteria) this;
        }

        public Criteria andRemoteAdressBetween(String value1, String value2) {
            addCriterion("remote_adress between", value1, value2, "remoteAdress");
            return (Criteria) this;
        }

        public Criteria andRemoteAdressNotBetween(String value1, String value2) {
            addCriterion("remote_adress not between", value1, value2, "remoteAdress");
            return (Criteria) this;
        }

        public Criteria andRemoteUserNameIsNull() {
            addCriterion("remote_user_name is null");
            return (Criteria) this;
        }

        public Criteria andRemoteUserNameIsNotNull() {
            addCriterion("remote_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andRemoteUserNameEqualTo(String value) {
            addCriterion("remote_user_name =", value, "remoteUserName");
            return (Criteria) this;
        }

        public Criteria andRemoteUserNameNotEqualTo(String value) {
            addCriterion("remote_user_name <>", value, "remoteUserName");
            return (Criteria) this;
        }

        public Criteria andRemoteUserNameGreaterThan(String value) {
            addCriterion("remote_user_name >", value, "remoteUserName");
            return (Criteria) this;
        }

        public Criteria andRemoteUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("remote_user_name >=", value, "remoteUserName");
            return (Criteria) this;
        }

        public Criteria andRemoteUserNameLessThan(String value) {
            addCriterion("remote_user_name <", value, "remoteUserName");
            return (Criteria) this;
        }

        public Criteria andRemoteUserNameLessThanOrEqualTo(String value) {
            addCriterion("remote_user_name <=", value, "remoteUserName");
            return (Criteria) this;
        }

        public Criteria andRemoteUserNameLike(String value) {
            addCriterion("remote_user_name like", value, "remoteUserName");
            return (Criteria) this;
        }

        public Criteria andRemoteUserNameNotLike(String value) {
            addCriterion("remote_user_name not like", value, "remoteUserName");
            return (Criteria) this;
        }

        public Criteria andRemoteUserNameIn(List<String> values) {
            addCriterion("remote_user_name in", values, "remoteUserName");
            return (Criteria) this;
        }

        public Criteria andRemoteUserNameNotIn(List<String> values) {
            addCriterion("remote_user_name not in", values, "remoteUserName");
            return (Criteria) this;
        }

        public Criteria andRemoteUserNameBetween(String value1, String value2) {
            addCriterion("remote_user_name between", value1, value2, "remoteUserName");
            return (Criteria) this;
        }

        public Criteria andRemoteUserNameNotBetween(String value1, String value2) {
            addCriterion("remote_user_name not between", value1, value2, "remoteUserName");
            return (Criteria) this;
        }

        public Criteria andRemotePasswordIsNull() {
            addCriterion("remote_password is null");
            return (Criteria) this;
        }

        public Criteria andRemotePasswordIsNotNull() {
            addCriterion("remote_password is not null");
            return (Criteria) this;
        }

        public Criteria andRemotePasswordEqualTo(String value) {
            addCriterion("remote_password =", value, "remotePassword");
            return (Criteria) this;
        }

        public Criteria andRemotePasswordNotEqualTo(String value) {
            addCriterion("remote_password <>", value, "remotePassword");
            return (Criteria) this;
        }

        public Criteria andRemotePasswordGreaterThan(String value) {
            addCriterion("remote_password >", value, "remotePassword");
            return (Criteria) this;
        }

        public Criteria andRemotePasswordGreaterThanOrEqualTo(String value) {
            addCriterion("remote_password >=", value, "remotePassword");
            return (Criteria) this;
        }

        public Criteria andRemotePasswordLessThan(String value) {
            addCriterion("remote_password <", value, "remotePassword");
            return (Criteria) this;
        }

        public Criteria andRemotePasswordLessThanOrEqualTo(String value) {
            addCriterion("remote_password <=", value, "remotePassword");
            return (Criteria) this;
        }

        public Criteria andRemotePasswordLike(String value) {
            addCriterion("remote_password like", value, "remotePassword");
            return (Criteria) this;
        }

        public Criteria andRemotePasswordNotLike(String value) {
            addCriterion("remote_password not like", value, "remotePassword");
            return (Criteria) this;
        }

        public Criteria andRemotePasswordIn(List<String> values) {
            addCriterion("remote_password in", values, "remotePassword");
            return (Criteria) this;
        }

        public Criteria andRemotePasswordNotIn(List<String> values) {
            addCriterion("remote_password not in", values, "remotePassword");
            return (Criteria) this;
        }

        public Criteria andRemotePasswordBetween(String value1, String value2) {
            addCriterion("remote_password between", value1, value2, "remotePassword");
            return (Criteria) this;
        }

        public Criteria andRemotePasswordNotBetween(String value1, String value2) {
            addCriterion("remote_password not between", value1, value2, "remotePassword");
            return (Criteria) this;
        }

        public Criteria andPortIsNull() {
            addCriterion("port is null");
            return (Criteria) this;
        }

        public Criteria andPortIsNotNull() {
            addCriterion("port is not null");
            return (Criteria) this;
        }

        public Criteria andPortEqualTo(String value) {
            addCriterion("port =", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotEqualTo(String value) {
            addCriterion("port <>", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortGreaterThan(String value) {
            addCriterion("port >", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortGreaterThanOrEqualTo(String value) {
            addCriterion("port >=", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLessThan(String value) {
            addCriterion("port <", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLessThanOrEqualTo(String value) {
            addCriterion("port <=", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLike(String value) {
            addCriterion("port like", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotLike(String value) {
            addCriterion("port not like", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortIn(List<String> values) {
            addCriterion("port in", values, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotIn(List<String> values) {
            addCriterion("port not in", values, "port");
            return (Criteria) this;
        }

        public Criteria andPortBetween(String value1, String value2) {
            addCriterion("port between", value1, value2, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotBetween(String value1, String value2) {
            addCriterion("port not between", value1, value2, "port");
            return (Criteria) this;
        }

        public Criteria andLocalFilePathIsNull() {
            addCriterion("local_file_path is null");
            return (Criteria) this;
        }

        public Criteria andLocalFilePathIsNotNull() {
            addCriterion("local_file_path is not null");
            return (Criteria) this;
        }

        public Criteria andLocalFilePathEqualTo(String value) {
            addCriterion("local_file_path =", value, "localFilePath");
            return (Criteria) this;
        }

        public Criteria andLocalFilePathNotEqualTo(String value) {
            addCriterion("local_file_path <>", value, "localFilePath");
            return (Criteria) this;
        }

        public Criteria andLocalFilePathGreaterThan(String value) {
            addCriterion("local_file_path >", value, "localFilePath");
            return (Criteria) this;
        }

        public Criteria andLocalFilePathGreaterThanOrEqualTo(String value) {
            addCriterion("local_file_path >=", value, "localFilePath");
            return (Criteria) this;
        }

        public Criteria andLocalFilePathLessThan(String value) {
            addCriterion("local_file_path <", value, "localFilePath");
            return (Criteria) this;
        }

        public Criteria andLocalFilePathLessThanOrEqualTo(String value) {
            addCriterion("local_file_path <=", value, "localFilePath");
            return (Criteria) this;
        }

        public Criteria andLocalFilePathLike(String value) {
            addCriterion("local_file_path like", value, "localFilePath");
            return (Criteria) this;
        }

        public Criteria andLocalFilePathNotLike(String value) {
            addCriterion("local_file_path not like", value, "localFilePath");
            return (Criteria) this;
        }

        public Criteria andLocalFilePathIn(List<String> values) {
            addCriterion("local_file_path in", values, "localFilePath");
            return (Criteria) this;
        }

        public Criteria andLocalFilePathNotIn(List<String> values) {
            addCriterion("local_file_path not in", values, "localFilePath");
            return (Criteria) this;
        }

        public Criteria andLocalFilePathBetween(String value1, String value2) {
            addCriterion("local_file_path between", value1, value2, "localFilePath");
            return (Criteria) this;
        }

        public Criteria andLocalFilePathNotBetween(String value1, String value2) {
            addCriterion("local_file_path not between", value1, value2, "localFilePath");
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

        public Criteria andSettleWayIsNull() {
            addCriterion("settle_way is null");
            return (Criteria) this;
        }

        public Criteria andSettleWayIsNotNull() {
            addCriterion("settle_way is not null");
            return (Criteria) this;
        }

        public Criteria andSettleWayEqualTo(String value) {
            addCriterion("settle_way =", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayNotEqualTo(String value) {
            addCriterion("settle_way <>", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayGreaterThan(String value) {
            addCriterion("settle_way >", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayGreaterThanOrEqualTo(String value) {
            addCriterion("settle_way >=", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayLessThan(String value) {
            addCriterion("settle_way <", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayLessThanOrEqualTo(String value) {
            addCriterion("settle_way <=", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayLike(String value) {
            addCriterion("settle_way like", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayNotLike(String value) {
            addCriterion("settle_way not like", value, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayIn(List<String> values) {
            addCriterion("settle_way in", values, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayNotIn(List<String> values) {
            addCriterion("settle_way not in", values, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayBetween(String value1, String value2) {
            addCriterion("settle_way between", value1, value2, "settleWay");
            return (Criteria) this;
        }

        public Criteria andSettleWayNotBetween(String value1, String value2) {
            addCriterion("settle_way not between", value1, value2, "settleWay");
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