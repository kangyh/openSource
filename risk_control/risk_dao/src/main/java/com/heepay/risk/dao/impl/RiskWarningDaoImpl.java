package com.heepay.risk.dao.impl;

import com.heepay.risk.dao.RiskWarningMapper;
import com.heepay.risk_es_engine.EsMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 描    述：风控系统
 * <p>
 * 创 建 者：dongzhengjiang E-mail:dongzj@9186.com
 * 创建时间： 2017-08-11 11:03
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Repository
public class RiskWarningDaoImpl implements RiskWarningMapper {
    @Autowired
    EsMerchantService esearch;
    @Override
    public boolean indexMerchantOrder(String json) {
       return esearch.indexMerchantOrder(json);
    }

    @Override
    public List<Map> queryMerMoniOrderCount(String equalParam) {
        return esearch.queryMerMoniOrderCount(equalParam);
    }

    @Override
    public Map<String, List<Map>> getOrderCount(String equalParam1, String equalParam2) {
        return esearch.getOrderCount(equalParam1, equalParam2);
    }

    @Override
    public Map<String,String> isExistsMerchantOrderLog(String equalParam) {
        return esearch.isExistsMerchantOrderLog(equalParam);
    }

}
