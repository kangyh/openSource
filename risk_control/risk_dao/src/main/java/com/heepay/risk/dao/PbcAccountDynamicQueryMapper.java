package com.heepay.risk.dao;

import java.util.List;

import com.heepay.risk.entity.PbcAccountDynamicQuery;

public interface PbcAccountDynamicQueryMapper {
	void insert(PbcAccountDynamicQuery entity);
	List<PbcAccountDynamicQuery> selectByApplicationCode(String applicationCode);
}
