package com.heepay.risk.dao;

import java.util.List;
import java.util.Map;

public interface RiskWarningMapper {
    boolean indexMerchantOrder(String json);
    List<Map> queryMerMoniOrderCount(String equalParam);
    Map<String, List<Map>> getOrderCount(String equalParam1, String equalParam2 );
    Map<String,String> isExistsMerchantOrderLog (String equalParam);
}
