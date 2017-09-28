package com.heepay.risk.dao.impl;

import com.heepay.risk.dao.RiskBlockInfoMapper;
import com.heepay.risk.entity.RiskBlockInfo;
import org.springframework.stereotype.Repository;

@Repository
public class RiskBlockInfoDaoImpl
    extends BaseDaoImpl<RiskBlockInfo> implements RiskBlockInfoMapper{

        // 默认构造方法设置命名空间
		public RiskBlockInfoDaoImpl() {
        super.setNs("com.heepay.risk.dao.RiskBlockInfoMapper");
    }


    @Override
    public int insert(RiskBlockInfo record) {
        return super.getSqlSession().insert(this.getNs()+".insert",record);
    }
}
