package com.heepay.risk.dao;

import com.heepay.risk.entity.PbcQueryInfo;

public interface  PbcQueryInfoMapper {
	void insert(PbcQueryInfo entity);
	PbcQueryInfo GetQueryInfo(String applicationCode);
}
