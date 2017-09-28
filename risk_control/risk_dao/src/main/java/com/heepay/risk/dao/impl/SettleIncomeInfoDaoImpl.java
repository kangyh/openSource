package com.heepay.risk.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.risk.dao.PbcAccountDynamicQueryMapper;
import com.heepay.risk.dao.SettleIncomeInfoMapper;
import com.heepay.risk.entity.PbcAccountDynamicQuery;
import com.heepay.risk.entity.SettleIncomeInfo;
@Repository
public class SettleIncomeInfoDaoImpl extends BaseDaoImpl<SettleIncomeInfo> implements SettleIncomeInfoMapper  {
	   
		// 默认构造方法设置命名空间
			public SettleIncomeInfoDaoImpl() {
				super.setNs("com.heepay.risk.dao.SettleIncomeInfoMapper");
			}
		@Override
		public int insert(SettleIncomeInfo entity) {		
			return super.getSqlSession().insert(this.getNs()+".insert",entity);
		}
		@Override
		public int update(SettleIncomeInfo record) {
			return super.getSqlSession().update(this.getNs()+".update",record);
		}
		@Override
		public List<SettleIncomeInfo> getlist() {
			return super.getSqlSession().selectList(this.getNs()+".getlist");
		}
		@Override
		public SettleIncomeInfo get(Long id) {
			return super.getSqlSession().selectOne(this.getNs()+".get",id);
		}
		
	}
