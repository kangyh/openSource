package com.heepay.risk.dao.impl;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcQueryInfoMapper;
import com.heepay.risk.entity.PbcQueryInfo;
@Repository
public class PbcQueryInfoDaoImpl extends BaseDaoImpl<PbcQueryInfo> implements PbcQueryInfoMapper {

	// 默认构造方法设置命名空间
				public PbcQueryInfoDaoImpl() {
					super.setNs("com.heepay.risk.dao.PbcQueryInfoMapper");
				}
			
		@Override
		public void insert(PbcQueryInfo entity) {
			// TODO Auto-generated method stub
		super.getSqlSession().insert(this.getNs()+".insert",entity);
		}

		@Override
		public PbcQueryInfo GetQueryInfo(String applicationCode) {
			return super.getSqlSession().selectOne(this.getNs()+".GetQueryInfo",applicationCode);
		}
   
}
